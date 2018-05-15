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
import java.util.Date;

import org.compiere.process.DocAction;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.util.Env.QueryParams;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_XX_InvRequisition;

/**
 * @author FANNY
 *
 */
public class MInvRequisition extends X_XX_InvRequisition implements DocAction {

    /** Logger for class MInvRequisition */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MInvRequisition.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_InvRequisition_ID
	 * @param trx
	 */
	public MInvRequisition(Ctx ctx, int XX_InvRequisition_ID, Trx trx) {
		super(ctx, XX_InvRequisition_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MInvRequisition(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	/**	Process Message 			*/
	private String		m_processMsg = null;
	
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());		
		setProcessing(false);
		return true;
	}

	/**
	 * 	Invalidate Document
	 * 	@return true if success
	 */
	public boolean invalidateIt()
	{
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}	//	invalidateIt

	@Override
	public String prepareIt() {
		String sql = "SELECT 1 " +
				"FROM XX_InvRequisitionLine " +
				"WHERE XX_InvRequisition_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_InvRequisition_ID());
			rs = pstmt.executeQuery();
			
			if(!rs.next())
			{
				m_processMsg = "Document has no lines";
				return DocActionConstants.STATUS_Invalid;
			}
		
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(isOverBudget())
		{
			m_processMsg = Msg.getMsg(getCtx(), "NoRemainingBudget");
			return DocActionConstants.STATUS_Invalid;			
		}
		//	if (!DOCACTION_Complete.equals(getDocAction()))		don't set for just prepare
		//		setDocAction(DOCACTION_Complete);
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
		return DocActionConstants.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	//	voidIt


	@Override
	public boolean closeIt() {
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	//	closeIt

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSummary() {
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		//	: Grand Total = 123.00 (#1)
		sb.append(": ").
		append(Msg.translate(getCtx(),"GrandTotal")).append("=").append(getEstTotalAmt());
		//	 - Description
		if ((getDescription() != null) && (getDescription().length() > 0))
			sb.append(" - ").append(getDescription());
		return sb.toString();
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

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return getRequestBy_ID();
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return getEstTotalAmt();
	}

	@Override
	public void setProcessMsg(String processMsg) {
		m_processMsg = processMsg;	
	}

	@Override
	public Timestamp getDocumentDate() {
		// TODO Auto-generated method stub
		return getDateDoc();
	}

	@Override
	public String getDocBaseType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryParams getLineOrgsQueryInfo() {
		// TODO Auto-generated method stub
		return null;
	}	
}
