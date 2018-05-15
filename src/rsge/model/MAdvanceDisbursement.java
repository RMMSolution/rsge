/**
 * 
 */
package rsge.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import org.compiere.model.MCurrency;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env.QueryParams;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_XX_AdvanceDisbursement;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY
 * 
 */
public class MAdvanceDisbursement extends X_XX_AdvanceDisbursement implements
		DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Logger for class MAdvanceDisbursement */
	private static final org.compiere.util.CLogger log = org.compiere.util.CLogger
			.getCLogger(MAdvanceDisbursement.class);

	/**
	 * @param ctx
	 * @param XX_AdvanceDisbursement_ID
	 * @param trx
	 */
	public MAdvanceDisbursement(Ctx ctx, int XX_AdvanceDisbursement_ID, Trx trx) {
		super(ctx, XX_AdvanceDisbursement_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MAdvanceDisbursement(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}	
	
	public static MAdvanceDisbursement getByPayment(MPayment payment)
	{
		MAdvanceDisbursement ad = null;
		String sql = "SELECT * FROM XX_AdvanceDisbursement "
				+ "WHERE C_Payment_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, payment.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, payment.getC_Payment_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				ad = new MAdvanceDisbursement(payment.getCtx(), rs, payment.get_Trx());
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ad;
	}
	
	public static MAdvanceDisbursement getByCashJournal(MCash cash)
	{
		MAdvanceDisbursement ad = null;
		String sql = "SELECT * FROM XX_AdvanceDisbursement "
				+ "WHERE C_Cash_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, cash.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, cash.getC_Cash_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				ad = new MAdvanceDisbursement(cash.getCtx(), rs, cash.get_Trx());
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ad;
	}
	
	public static boolean recordUnrealizedBudget(MAdvanceDisbursement ad, MCash cash, MPayment payment)
	{
		BigDecimal payAmt = BigDecimal.ZERO;
		BigDecimal ratio = BigDecimal.ONE;
		MCurrency currency = new MCurrency(ad.getCtx(), ad.getC_Currency_ID(), ad.get_Trx());
		int tableID = 0;
		int recordID = 0;
		
		if(cash!=null)
		{
			
			tableID = cash.get_Table_ID();
			recordID = cash.getC_Cash_ID();
		}
		else if(payment!=null)
		{
			payAmt = payment.getPayAmt();
			tableID = payment.get_Table_ID();
			recordID = payment.getC_Payment_ID();
		}
		if(payAmt.compareTo(ad.getTotalAmt())<0)
			ratio = (payAmt.divide(ad.getTotalAmt(), 4, RoundingMode.HALF_EVEN)).setScale(2, RoundingMode.HALF_EVEN);			
		
		// Get lines
		MAdvanceDisbursementLine[] lines = MAdvanceDisbursementLine.getLines(ad);
		for(MAdvanceDisbursementLine line : lines)
		{
			BigDecimal lineAmt = line.getConvertedAmt();
			if(ratio.compareTo(BigDecimal.ONE)!=0)
			{
				lineAmt = lineAmt.multiply(ratio);
				lineAmt = lineAmt.setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
			}
			line.recordUnrealizedLineTransaction(lineAmt, tableID, recordID);
		}
		
		return true;
	}

	/**
	 * Unlock Document.
	 * 
	 * @return true if success
	 */
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	} // unlockIt

	@Override
	/**
	 * 	Invalidate Document
	 * 	@return true if success
	 */
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		return true;
	} // invalidateIt

	@Override
	public String prepareIt() {
		
//		if(getC_Charge_ID()==0)
//		{
//			m_processMsg = "No Charge selected";
//			return DocActionConstants.STATUS_Invalid;			
//		}
		if(getEmployee_ID()==0)
		{
			m_processMsg = "No Employee selected";
			return DocActionConstants.STATUS_Invalid;			
		}

		// Check if all Lines has Charge or Disbursement Type. If not Invalidate
		// it
		String sql = "SELECT 1 FROM XX_AdvanceDisbursementLine "
				+ "WHERE XX_AdvanceDisbursement_ID = ? "
				+ "AND (XX_DisbursementType_ID = 0 OR XX_DisbursementType_ID IS NULL) "
				+ "AND (C_Charge_ID = 0 OR C_Charge_ID IS NULL) ";

		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;

		try {
			pstmt.setInt(1, getXX_AdvanceDisbursement_ID());
			rs = pstmt.executeQuery();

			if (rs.next()) // Found Line with no Charge or disbursement type
			{
				m_processMsg = "@FoundLineWithNoCharge@";
				return DocActionConstants.STATUS_Invalid;
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(isLineOverBudget())
		{
			String overBudgetMsg = MBudgetInfo.runPreliminaryBudgetCheck(getCtx(), getAD_Client_ID(), get_Trx());
			if(overBudgetMsg!=null)
			{
				m_processMsg = overBudgetMsg;
				return DocActionConstants.STATUS_Invalid;				
			}
		}

		return DocActionConstants.STATUS_InProgress;
	}


	/**
	 * Approve Document
	 * 
	 * @return true if success
	 */
	public boolean approveIt() {
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	} // approveIt

	/**
	 * Reject Approval
	 * 
	 * @return true if success
	 */
	public boolean rejectIt() {
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	} // rejectIt

	@Override
	public String completeIt() {
		// Record Budget Transaction
		if(GeneralEnhancementUtils.budgetStart(getCtx(), getDateDoc(), get_Trx()))
		{
			MAdvanceDisbursementLine[] lines = MAdvanceDisbursementLine.getLines(this);
			for(MAdvanceDisbursementLine line : lines)
				line.recordLineTransaction();
		}
		return DocActionConstants.STATUS_Completed;
	}

	/**
	 * Void Document. Same as Close.
	 * 
	 * @return true if success
	 */
	public boolean voidIt() {
		log.info("voidIt - " + toString());
		// Not Processed
		if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Approved.equals(getDocStatus())
				|| DOCSTATUS_NotApproved.equals(getDocStatus())) {

		} else
			return DocumentEngine.processIt(this,
					DocActionConstants.ACTION_Close);

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	} // voidIt

	/**
	 * Close Document. Cancel not delivered Qunatities
	 * 
	 * @return true if success
	 */
	public boolean closeIt() {
		log.info("closeIt - " + toString());
		return true;
	} // closeIt

	/**
	 * Reverse Correction
	 * 
	 * @return true if success
	 */
	public boolean reverseCorrectIt() {
		log.info("reverseCorrectIt - " + toString());
		return false;
	} // reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 * 
	 * @return true if success
	 */
	public boolean reverseAccrualIt() {
		log.info("reverseAccrualIt - " + toString());
		return false;
	} // reverseAccrualIt

	/**
	 * Re-activate
	 * 
	 * @return true if success
	 */
	public boolean reActivateIt() {
		log.info("reActivateIt - " + toString());
		// setProcessed(false);
		return DocumentEngine.processIt(this,
				DocActionConstants.ACTION_Reverse_Correct);
	} // reActivateIt

	/*************************************************************************
	 * Get Summary
	 * 
	 * @return Summary of Document
	 */
	public String getSummary() {
		StringBuilder message = new StringBuilder();
		message.append(getDocumentNo() + "-");
		message.append(getIDR(getTotalAmt()) + " ");
		if (getDescription() != null)
			message.append("' " + getDescription() + " '");

		return message.toString();
	} // getSummary
	
	public static String getIDR(BigDecimal Amount) {
		Locale idr = new Locale("in", "ID");
		DecimalFormat formatter = (DecimalFormat) NumberFormat
				.getCurrencyInstance(idr);

		DecimalFormatSymbols symbol = new DecimalFormatSymbols(idr);
		symbol.setCurrencySymbol("Rp. ");

		formatter.setDecimalFormatSymbols(symbol);
		return formatter.format(Amount);
	}


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

	/** Process Message */
	private String m_processMsg = null;

	/**
	 * Get Process Message
	 * 
	 * @return clear text error message
	 */
	public String getProcessMsg() {
		return m_processMsg;
	} // getProcessMsg

	/**
	 * Get Document Owner
	 * 
	 * @return AD_User_ID
	 */
	public int getDoc_User_ID() {
		return getAD_User_ID();
	}

	/**
	 * Get Document Approval Amount
	 * 
	 * @return amount
	 */
	public BigDecimal getApprovalAmt() {
		return getTotalAmt();
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
		return new QueryParams(
				"SELECT DISTINCT AD_Org_ID FROM XX_AdvanceDisbursementLine WHERE XX_AdvanceDisbursement_ID = ?",
				new Object[] { getXX_AdvanceDisbursement_ID() });
	}

}
