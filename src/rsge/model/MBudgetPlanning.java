/**
 * 
 */
package rsge.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.compiere.api.UICallout;
import org.compiere.model.MPeriod;
import org.compiere.process.DocAction;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env.QueryParams;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_XX_BudgetPlanning;
import rsge.utils.BudgetUtils;

/**
 * @author FANNY
 *
 */
public class MBudgetPlanning extends X_XX_BudgetPlanning implements DocAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Logger for class MBudgetPlanning */
	private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MBudgetPlanning.class);


	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param XX_BudgetPlanning_ID
	 * @param trx
	 */
	public MBudgetPlanning(Ctx ctx, int XX_BudgetPlanning_ID, Trx trx) {
		super(ctx, XX_BudgetPlanning_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MBudgetPlanning(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.framework.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) {	
		// Check if system has already have a record with same AD_Org_ID, GL_Budget_ID
		// If yes, cancel save process
		if(newRecord)
		{		
			boolean recordExist = false;
			
			String check = "SELECT 1 " +
					"FROM XX_BudgetPlanning " +
					"WHERE AD_Org_ID = ? " +
					"AND GL_Budget_ID = ? " +
					"AND XX_CorpBudgetPlan_ID = ? " +
					"AND DocStatus NOT IN ('CO', 'CL') ";
			
			PreparedStatement pstmt = DB.prepareStatement(check, get_Trx());
			ResultSet rs = null;
			
			try{
				pstmt.setInt(1, getAD_Org_ID());
				pstmt.setInt(2, getGL_Budget_ID());
				pstmt.setInt(3, getXX_CorpBudgetPlan_ID());
				
				rs = pstmt.executeQuery();
				
				if(rs.next())
				{
					recordExist = true;;
				}
				
				rs.close();
				pstmt.close();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			if(recordExist)
			{
				log.saveError("Open Record Exists", Msg.getMsg(getCtx(), "OpenRecordExists"));
				return false;				
			}
			
			MPeriod period = new MPeriod(getCtx(), getC_Period_From_ID(), get_Trx());
			if(getDateDoc().after(period.getStartDate()))
			{
			 	log.saveError("Error",Msg.getMsg(getCtx(), "InvalidBudgetPlanPeriod"));			
				return false;
			}			
		}		
		
		return true;
	}
	
	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String prepareIt() {
			return DocActionConstants.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		log.info(toString());
		setIsApproved(true);
		return true;
	}	//	approveIt


	/**
	 * 	Reject Approval
	 * 	@return true if success
	 */
	public boolean rejectIt()
	{
		log.info(toString());
		setIsApproved(false);
		return true;
	}	//	rejectIt

	@Override
	public String completeIt() {
		
		// Set Processed 
		String update = "UPDATE XX_BudgetPlanningLinePeriod " +
				"SET Processed = 'Y' " +
				"WHERE XX_BudgetPlanningLinePeriod_ID IN (" +
				"SELECT bplp.XX_BudgetPlanningLinePeriod_ID FROM XX_BudgetPlanningLinePeriod bplp " +
				"INNER JOIN XX_BudgetPlanningLine bpl ON (bplp.XX_BudgetPlanningLine_ID = bpl.XX_BudgetPlanningLine_ID) " +
				"INNER JOIN XX_BudgetPlanning bp ON (bpl.XX_BudgetPlanning_ID = bp.XX_BudgetPlanning_ID) " +
				"WHERE bp.XX_BudgetPlanning_ID = ?) ";
		
		DB.executeUpdate(get_Trx(), update, getXX_BudgetPlanning_ID());		
		return DocActionConstants.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		// Document info
		String DocumentInfo = "Budget Planning #" + getDocumentNo();		 
		return DocumentInfo;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return getUpdatedBy();
	}

	@Override
	public int getC_Currency_ID() {
		return BudgetUtils.getBudgetCurrencyID(getCtx(), getAD_Client_ID(), get_Trx());
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProcessMsg(String processMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getDocumentDate() {
		// TODO Auto-generated method stub
		return null;
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
	
	@UICallout 
	public void setXX_CorpBudgetPlan_ID (String oldXX_CorpBudgetPlan_ID,
			String newXX_CorpBudgetPlan_ID, int windowNo) throws Exception
	{
		if(newXX_CorpBudgetPlan_ID == null || newXX_CorpBudgetPlan_ID.length() == 0)		
			return;
		
		int XX_CorpBudgetPlan_ID = Integer.valueOf(newXX_CorpBudgetPlan_ID);
		
		// Set data based from selected Corp Budget Plan
		MCorpBudgetPlan plan = new MCorpBudgetPlan(getCtx(), XX_CorpBudgetPlan_ID, get_Trx());
		setPlanningPeriod(plan.getPlanningPeriod());
		setC_Period_From_ID(plan.getC_From_Period_ID());
		setGL_Budget_ID(plan.getGL_Budget_ID());
	}
	
}
