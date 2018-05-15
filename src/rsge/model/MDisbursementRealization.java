/**
 * 
 */
package rsge.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.compiere.api.UICallout;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MStorageDetail;
import org.compiere.model.MUOMConversion;
import org.compiere.model.X_Ref_Quantity_Type;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env.QueryParams;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_XX_DisbursementRealization;

/**
 * @author FANNY
 *
 */
public class MDisbursementRealization extends X_XX_DisbursementRealization implements DocAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /** Logger for class MDisbursementRealization */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MDisbursementRealization.class);


	/**
	 * @param ctx
	 * @param XX_DisbursementRealization_ID
	 * @param trx
	 */
	public MDisbursementRealization(Ctx ctx, int XX_DisbursementRealization_ID,
			Trx trx) {
		super(ctx, XX_DisbursementRealization_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MDisbursementRealization(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}	
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// Get data from Advance Disbursement
		String sql = "SELECT adl.Line, adl.XX_AdvanceDisbursementLine_ID, adl.Description, " +
				"adl.XX_DisbursementType_ID, adl.C_Charge_ID, adl.Amount,adl.AD_Org_ID " +
				"FROM XX_AdvanceDisbursementLine adl " +
				"WHERE adl.XX_AdvanceDisbursement_ID = ? " +
				"AND NOT EXISTS (SELECT * FROM XX_DisRealizationLine drl " +
				"WHERE adl.XX_AdvanceDisbursementLine_ID = drl.XX_AdvanceDisbursementLine_ID) " +
				"ORDER BY adl.Line ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_AdvanceDisbursement_ID());
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				// Create Disbursement Realization Line
				MDisRealizationLine drl = new MDisRealizationLine(getCtx(), 0, get_Trx());
				drl.setAD_Org_ID(rs.getInt(7));
				drl.setLine(rs.getInt(1));
				drl.setXX_AdvanceDisbursementLine_ID(rs.getInt(2));
				drl.setDescription(rs.getString(3));
				drl.setXX_DisbursementType_ID(rs.getInt(4));
				drl.setC_Charge_ID(rs.getInt(5));
				drl.setAmount(rs.getBigDecimal(6));
				
				drl.setIsGenerated(true);
				drl.setXX_DisbursementRealization_ID(getXX_DisbursementRealization_ID());
				
				drl.save();
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 	Set XX_AdvanceDisbursement_ID - Callout
	 *  
	 *	@param oldXX_AdvanceDisbursement_ID old XX_AdvanceDisbursement_ID
	 *	@param newXX_AdvanceDisbursement_ID new XX_AdvanceDisbursement_ID
	 *	@param windowNo window no
	 */
	@UICallout public void setXX_AdvanceDisbursement_ID (String oldXX_AdvanceDisbursement_ID,
			String newXX_AdvanceDisbursement_ID, int windowNo) throws Exception
	{
		if(newXX_AdvanceDisbursement_ID == null || newXX_AdvanceDisbursement_ID.length() == 0)		
			return;
		
		Integer AdvanceDisbursement_ID = Integer.valueOf(newXX_AdvanceDisbursement_ID);
		
		MAdvanceDisbursement ad = new MAdvanceDisbursement(getCtx(), AdvanceDisbursement_ID, get_Trx());
		setAD_Org_ID(ad.getAD_Org_ID());
		setC_Currency_ID(ad.getC_Currency_ID());
		setC_Activity_ID(ad.getC_Activity_ID());
		setC_Campaign_ID(ad.getC_Campaign_ID());
		setC_Project_ID(ad.getC_Project_ID());
		
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
		// Make sure the document has line
		String sql = "SELECT 1 " +
				"FROM XX_DisRealizationLine " +
				"WHERE XX_DisbursementRealization_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_DisbursementRealization_ID());
			rs = pstmt.executeQuery();
			if(!rs.next())
			{
				m_processMsg = "@NoLines@";
				return DocActionConstants.STATUS_Invalid;
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
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
		
		// Set Is Settled as True if DifferenceAmount is 0
		if(getDifferenceAmt().compareTo(BigDecimal.ZERO)==0)
			setIsSettled(true);		
		// Check 
		MatchDisbursementRealizationInvoice();
		updateStorage();	
		
		// Update Budget
		updateBudgetTransaction();
		
		return DocActionConstants.STATUS_Completed;	
	}
	
	private boolean updateBudgetTransaction()
	{
		MDisRealizationLine[] lines = MDisRealizationLine.getLines(this);
		for(MDisRealizationLine line : lines)
		{
			MAdvanceDisbursementLine drl = new MAdvanceDisbursementLine(getCtx(), line.getXX_AdvanceDisbursementLine_ID(), get_Trx());
			drl.recordRealizedLineTransaction(getRealizedAmt(), line.get_Table_ID(), line.getXX_DisRealizationLine_ID());
		}
		return true;
	}
	
	private void MatchDisbursementRealizationInvoice()
	{
		String sql = "SELECT li.* FROM XX_DisRealizationLineInv li " +
				"INNER JOIN XX_DisRealizationLine l ON (li.XX_DisRealizationLine_ID = l.XX_DisRealizationLine_ID) " +
				"WHERE l.XX_DisbursementRealization_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_DisbursementRealization_ID());
			rs = pstmt.executeQuery();
			MAllocationHdr hdr = null;
			while(rs.next())
			{
				if(hdr==null)
				{
					hdr = new MAllocationHdr(getCtx(), 0, get_Trx());
					hdr.setClientOrg(this);
					hdr.setC_Currency_ID(getC_Currency_ID());
					hdr.setDateTrx(getDateDoc());
					hdr.setDateAcct(getDateDoc());
					hdr.setDescription("Disbursement Realization#" + getDocumentNo());
					hdr.save();
				}
				MDisRealizationLineInv li = new MDisRealizationLineInv(getCtx(), rs, get_Trx());
				MAllocationLine al = new MAllocationLine(hdr);
				al.setAmount(li.getAllocatedAmt().negate());
				al.setC_BPartner_ID(li.getC_BPartner_ID());
				al.setC_Invoice_ID(li.getC_Invoice_ID());
				al.setDateTrx(getDateDoc());
//				al.setXX_DisRealizationLineInv_ID(li.getXX_DisRealizationLineInv_ID());				
				if(al.save())
				{
					li.setC_AllocationLine_ID(al.getC_AllocationLine_ID());
					li.save();
				}
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void updateStorage()
	{
		String sql = "SELECT p.* FROM XX_DisRealizationLineProd p " +
				"INNER JOIN XX_DisRealizationLine l ON (p.XX_DisRealizationLine_ID = l.XX_DisRealizationLine_ID) " +
				"WHERE l.XX_DisbursementRealization_ID = ? "
				+ "AND p.XX_DisRealizationLineProd_ID NOT IN (SELECT XX_DisRealizationLineProd_ID FROM M_Transaction "
				+ "WHERE XX_DisRealizationLineProd_ID IS NOT NULL) ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_DisbursementRealization_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MDisRealizationLineProd line = new MDisRealizationLineProd(getCtx(), rs, get_Trx());
				BigDecimal qty = line.getQty();
				MProduct p = new MProduct(getCtx(), line.getM_Product_ID(), get_Trx());
				if(line.getC_UOM_ID()!=p.getC_UOM_ID())
					qty = MUOMConversion.convertProductFrom(getCtx(), line.getM_Product_ID(), line.getC_UOM_ID(), qty);
				MTransaction mtrx = new MTransaction(getCtx(), getAD_Org_ID(), MTransaction.MOVEMENTTYPE_InventoryIn, 
						line.getM_Locator_ID(), line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(), qty, getDateDoc(), get_Trx(), false);
				mtrx.setXX_DisRealizationLineProd_ID(line.getXX_DisRealizationLineProd_ID());				
				mtrx.save();
				
				MStorageDetail sd = MStorageDetail.getCreate(getCtx(), 
						line.getM_Locator_ID(), line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(), X_Ref_Quantity_Type.ON_HAND, get_Trx());
				sd.setQty(sd.getQty().add(qty));
				sd.save();

			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
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
		return new QueryParams("SELECT DISTINCT AD_Org_ID FROM XX_DisRealizationLine WHERE XX_DisbursementRealization_ID = ?",
		new Object[] { getXX_DisbursementRealization_ID() });
	}
	
	public static MDisRealizationLine[] getLines(MDisbursementRealization dr)
	{
		ArrayList<MDisRealizationLine> list = null;
		String sql = "SELECT * " +
				"FROM XX_DisRealizationLine " +
				"WHERE XX_DisbursementRealization_ID = ? " +
				"ORDER BY Line ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, dr.get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, dr.getXX_DisbursementRealization_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				if(list==null)
					list = new ArrayList<MDisRealizationLine>();
				MDisRealizationLine drl = new MDisRealizationLine(dr.getCtx(), rs, dr.get_Trx());
				list.add(drl);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
				
		MDisRealizationLine[] retValue = null;
		if(list!=null)
		{
			retValue = new MDisRealizationLine[list.size()];
			list.toArray(retValue);
		}
		return retValue;
	}

}
