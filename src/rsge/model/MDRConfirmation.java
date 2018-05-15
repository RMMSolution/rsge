/**
 * 
 */
package rsge.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.api.UICallout;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MCash;
import org.compiere.model.MCashBook;
import org.compiere.model.MCashLine;
import org.compiere.model.X_C_BankStatement;
import org.compiere.model.X_C_CashLine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.Trx;
import org.compiere.util.Env.QueryParams;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_XX_DRConfirmation;
import rsge.po.X_XX_DRConfirmationLine;

/**
 * @author FANNY
 *
 */
public class MDRConfirmation extends X_XX_DRConfirmation implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /** Logger for class MDRConfirmation */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MDRConfirmation.class);


	/**
	 * @param ctx
	 * @param XX_DRConfirmation_ID
	 * @param trx
	 */
	public MDRConfirmation(Ctx ctx, int XX_DRConfirmation_ID, Trx trx) {
		super(ctx, XX_DRConfirmation_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MDRConfirmation(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 	Set XX_DisbursementRealization_ID - Callout
	 *  
	 *	@param oldXX_DisbursementRealization_ID old XX_DisbursementRealization_ID
	 *	@param newXX_DisbursementRealization_ID new XX_DisbursementRealization_ID
	 *	@param windowNo window no
	 */
	@UICallout public void setXX_DisbursementRealization_ID (String oldXX_DisbursementRealization_ID,
			String newXX_DisbursementRealization_ID, int windowNo) throws Exception
	{
		if(newXX_DisbursementRealization_ID == null || newXX_DisbursementRealization_ID.length() == 0)		
			return;
		
		Integer DR_ID = Integer.valueOf(newXX_DisbursementRealization_ID);
		
		MDisbursementRealization dr = new MDisbursementRealization(getCtx(), DR_ID, get_Trx());
		setC_Currency_ID(dr.getC_Currency_ID());
		
		// For Return Transaction
		if(dr.getTotalAmt().compareTo(dr.getRealizedAmt())== 1)
		{
			setDRConfirmationType(DRCONFIRMATIONTYPE_Return);
			setAmount(dr.getTotalAmt().subtract(dr.getRealizedAmt()));
		}
		else // For Reimburse Transaction
		{
			setDRConfirmationType(DRCONFIRMATIONTYPE_Reimburse);
			setAmount(dr.getRealizedAmt().subtract(dr.getTotalAmt()));
		}
	}
	
	/**
	 * 	Unlock Document.
	 * 	@return true if success
	 */
	public boolean unlockIt()
	{
		log.info("unlockIt - " + toString());
		return true;
	}	//	unlockIt
	@Override
	
	/**
	 * 	Invalidate Document
	 * 	@return true if success
	 */
	public boolean invalidateIt()
	{
		log.info("invalidateIt - " + toString());
		return true;
	}	//	invalidateIt

	@Override
	public String prepareIt() {
		
		return DocActionConstants.STATUS_InProgress;
	}

	/**
	 * 	Approve Document
	 * 	@return true if success
	 */
	public boolean  approveIt()
	{
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}	//	approveIt

	/**
	 * 	Reject Approval
	 * 	@return true if success
	 */
	public boolean rejectIt()
	{
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}	//	rejectIt

	@Override
	public String completeIt() {
		
		MDRConfirmationLine[] lines = MDRConfirmationLine.getLines(this);
		int advChargeID = MGeneralSetup.get(getCtx(), getAD_Client_ID(), get_Trx()).getAdvDisbursementClrg_ID();
		for(MDRConfirmationLine line : lines)
		{
			if(line.getPaymentRule().equals(X_XX_DRConfirmationLine.PAYMENTRULE_BankTransfer))
				line.setC_BankStatementLine_ID(createBankStatement(line, advChargeID));
			else if(line.getPaymentRule().equals(X_XX_DRConfirmationLine.PAYMENTRULE_CashPayment))
				line.setC_CashLine_ID(createCashJournal(line, advChargeID));
		}

		// Update Disbursement Realization
		MDisbursementRealization dr= new MDisbursementRealization(getCtx(), getXX_DisbursementRealization_ID(), get_Trx());
		dr.setIsSettled(true);
		dr.setXX_DRConfirmation_ID(getXX_DRConfirmation_ID());
		dr.save();		
		return DocActionConstants.STATUS_Completed;	
	}
	
	private int createBankStatement(MDRConfirmationLine cl, int C_Charge_ID)
	{
		int bankStatementLine = 0;
		
		// Create Bank Statement
		MBankStatement bs = new MBankStatement(getCtx(), 0, get_Trx());
		bs.setClientOrg(cl);
		bs.setC_BankAccount_ID(cl.getC_BankAccount_ID());
		MBankAccount ba = new MBankAccount(getCtx(), cl.getC_BankAccount_ID(), get_Trx());
		bs.setC_Currency_Bank_ID(ba.getC_Currency_ID());
		if(getDRConfirmationType().equals(DRCONFIRMATIONTYPE_Return))
			bs.setName("Receipt Disbursement Realization #" + getDocumentNo());
		else
			bs.setName("Reimbursement Disbursement Realization #" + getDocumentNo());
		bs.setStatementDate(getDateDoc());
		if(bs.save())
		{
			MBankStatementLine line = new MBankStatementLine(bs);
			line.setClientOrg(bs);
			line.setLine(10);
			line.setStatementLineDate(getDateDoc());
			line.setDateAcct(getDateDoc());
			line.setEftStatementLineDate(getDateDoc());
			
			BigDecimal amount = cl.getAmount();
			if(getDRConfirmationType().equals(DRCONFIRMATIONTYPE_Reimburse))
				amount = amount.negate();
			line.setStmtAmt(amount);
			line.setChargeAmt(amount);
			line.setC_Charge_ID(C_Charge_ID);
			line.setC_Currency_ID(ba.getC_Currency_ID());
			if(line.save())
				bankStatementLine = line.getC_BankStatementLine_ID();
		}
		
		// Process
		DocumentEngine.processIt(bs, X_C_BankStatement.DOCACTION_Complete);
		bs.save();
		return bankStatementLine;
	}
	
	private int createCashJournal(MDRConfirmationLine cl, int C_Charge_ID)
	{
		int cashLineID = 0;

		MDisbursementRealization dr = new MDisbursementRealization(getCtx(), getXX_DisbursementRealization_ID(), get_Trx());
		MCashBook cb = new MCashBook(getCtx(), cl.getC_CashBook_ID(), get_Trx());
		MCash cash = new MCash(cb, getDateDoc());
		cash.setC_Activity_ID(dr.getC_Activity_ID());
		cash.setC_Campaign_ID(dr.getC_Campaign_ID());
		cash.setC_Project_ID(dr.getC_Project_ID());		
		if(getDRConfirmationType().equals(DRCONFIRMATIONTYPE_Return))
		{
			cash.setName("Receipt Disbursement Realization #" + dr.getDocumentNo());
		}
		else
		{
			cash.setName("Reimbursement Disbursement Realization #" + dr.getDocumentNo());
		}
		if(cash.save())
		{
			// Create Cash Journal Line
			MCashLine line = new MCashLine(cash);
			line.setCashType(X_C_CashLine.CASHTYPE_Charge);
			line.setC_Charge_ID(C_Charge_ID);
			BigDecimal amount = cl.getAmount();
			if(getDRConfirmationType().equals(DRCONFIRMATIONTYPE_Reimburse))
				amount = amount.negate();
			line.setAmount(amount);
			if(line.save())
				cashLineID = line.getC_CashLine_ID();			
		}
		DocumentEngine.processIt(cash, DOCACTION_Complete);
		cash.save();
		
		return cashLineID;
	}

	/**
	 * 	Void Document.
	 * 	Same as Close.
	 * 	@return true if success
	 */
	public boolean voidIt()
	{
		log.info("voidIt - " + toString());
		//	Not Processed
		if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Approved.equals(getDocStatus())
				|| DOCSTATUS_NotApproved.equals(getDocStatus()) )
		{

		}
		else
			return DocumentEngine.processIt(this, DocActionConstants.ACTION_Close);
		
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	//	voidIt

	/**
	 * 	Close Document.
	 * 	Cancel not delivered Qunatities
	 * 	@return true if success
	 */
	public boolean closeIt()
	{
		log.info("closeIt - " + toString());	
		return true;
	}	//	closeIt

	/**
	 * 	Reverse Correction
	 * 	@return true if success
	 */
	public boolean reverseCorrectIt()
	{
		log.info("reverseCorrectIt - " + toString());
		return false;
	}	//	reverseCorrectionIt

	/**
	 * 	Reverse Accrual - none
	 * 	@return true if success
	 */
	public boolean reverseAccrualIt()
	{
		log.info("reverseAccrualIt - " + toString());
		return false;
	}	//	reverseAccrualIt

	/**
	 * 	Re-activate
	 * 	@return true if success
	 */
	public boolean reActivateIt()
	{
		log.info("reActivateIt - " + toString());
	//	setProcessed(false);
		return DocumentEngine.processIt(this, DocActionConstants.ACTION_Reverse_Correct);
	}	//	reActivateIt

	/*************************************************************************
	 * 	Get Summary
	 *	@return Summary of Document
	 */
	public String getSummary()
	{
		return null;
	} // getSummary		
	
	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/**	Process Message 			*/
	private String			m_processMsg = null;
	
	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg

	/**
	 * 	Get Document Owner
	 *	@return AD_User_ID
	 */
	public int getDoc_User_ID()
	{
		return getAD_User_ID();
	}

	/**
	 * 	Get Document Approval Amount
	 *	@return amount
	 */
	public BigDecimal getApprovalAmt()
	{
		return getAmount();
	}

	@Override
	public void setProcessMsg(String processMsg) {
		m_processMsg = processMsg;
	}

	@Override
	public Timestamp getDocumentDate() {
		return getDateDoc();
	}

	@Override
	public String getDocBaseType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryParams getLineOrgsQueryInfo() {
		return new QueryParams("SELECT DISTINCT AD_Org_ID FROM XX_DRConfirmation WHERE XX_DRConfirmation_ID = ?",
		new Object[] { getXX_DRConfirmation_ID() });
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return getC_Currency_ID();
	}

}
