/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MElementValue;
import org.compiere.model.MPeriod;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

import rsge.po.X_XX_BudgetPlanning;
import rsge.po.X_XX_BudgetPlanningLine;
import rsge.utils.BudgetUtils;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY
 *
 */
public class MBudgetPlanningLine extends X_XX_BudgetPlanningLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Logger for class MBudgetPlanningLine */
	private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MBudgetPlanningLine.class);


	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param XX_BudgetPlanningLine_ID
	 * @param trx
	 */
	public MBudgetPlanningLine(Ctx ctx, int XX_BudgetPlanningLine_ID, Trx trx) {
		super(ctx, XX_BudgetPlanningLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MBudgetPlanningLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}	

	
	
	/* (non-Javadoc)
	 * @see org.compiere.framework.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) {
	
		boolean checkAccount = checkAccountSegment(getAccount_ID());
		if (!checkAccount)
		{
		 	log.saveError("Error",Msg.getMsg(getCtx(), "AccountAlreadyExist"));			
			return false;							
		}
		
//		// Return if Product is populated
//		if(getM_Product_ID()>0)
//		{
//			setValue("");
//			return true;
//		}
//		
		// Get search key of selected account
		MElementValue ev = new MElementValue(getCtx(), getAccount_ID(), get_Trx());
		setValue(ev.getValue());
		return true;
	}
	
	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		String delete = "DELETE XX_PlanningLineSrc " +
				"WHERE XX_BudgetPlanningLinePeriod_ID IN (" +
				"SELECT pl.XX_BudgetPlanningLinePeriod_ID FROM XX_PlanningLineSrc pl " +
				"INNER JOIN XX_BudgetPlanningLinePeriod lp ON (pl.XX_BudgetPlanningLinePeriod_ID = lp.XX_BudgetPlanningLinePeriod_ID) " +
				"WHERE lp.XX_BudgetPlanningLine_ID = ?) ";
		DB.executeUpdate(get_Trx(), delete, getXX_BudgetPlanningLine_ID());
		
		delete = "DELETE Fact_Acct fa " +
				"WHERE fa.AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'XX_PlanningLineSrc') " +
				"AND fa.Record_ID IN (SELECT lp.XX_BudgetPlanningLinePeriod_ID FROM XX_BudgetPlanningLinePeriod lp " +
				"WHERE lp.XX_BudgetPlanningLine_ID = ?) ";
		DB.executeUpdate(get_Trx(), delete, getXX_BudgetPlanningLine_ID());

		delete = "DELETE XX_BudgetPlanningLinePeriod " +
				"WHERE XX_BudgetPlanningLine_ID = ? ";
		DB.executeUpdate(get_Trx(), delete, getXX_BudgetPlanningLine_ID());
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.framework.PO#afterSave(boolean, boolean)
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		// Create Planning period (if not exist)
		createPlanningPeriod();
		return true;
	}
	
	/**
	 * Check if account segment is already exists
	 */
	private boolean checkAccountSegment(int Account_ID)
	{		
		// Check if new account segment is not exists
		
		/** User Element 1 Name						*/
		String					element1 = null;
		/** User Element 2 Name						*/
		String					element2 = null;

		
		/** User Element 1 Exists in Table	*/
		boolean					element1Exists = false;
		/** User Element 2 Exists in Table	*/
		boolean					element2Exists = false;
		
		/** User Element 1 value					*/
		Integer					elementValue1 = null;
		/** User Element 2 value					*/
		Integer					elementValue2 = null;
		
		int C_AcctSchema_ID = BudgetUtils.getBudgetAcctSchemaID(getCtx(), getAD_Client_ID(), get_Trx());
		
		//Get Element Column
		element1 = GeneralEnhancementUtils.getElementColumn(C_AcctSchema_ID, 1, get_Trx());
		element2 = GeneralEnhancementUtils.getElementColumn(C_AcctSchema_ID, 2, get_Trx());
		
		if(element1!=null)
		{
			element1Exists = GeneralEnhancementUtils.checkColumn("XX_BudgetPlanningLine", element1, get_Trx());			
		}
		if(element2!=null)
		{
			element2Exists = GeneralEnhancementUtils.checkColumn("XX_BudgetPlanningLine", element2, get_Trx());			
		}

		if(element1Exists)
		{
			elementValue1 = getElement_X1();
		}
		if(element2Exists)
		{
			elementValue2 = getElement_X2();
		}
				
		StringBuilder sql = new StringBuilder("SELECT 1 " +
				"FROM XX_BudgetPlanningLine " +
				"WHERE XX_BudgetPlanning_ID = ? " +
				"AND Account_ID = ?");
		
		if(getC_Activity_ID()>0)
		{
			sql.append(" AND C_Activity_ID = " + getC_Activity_ID());
		}
		else 
		{
			sql.append(" AND (C_Activity_ID = 0 OR C_Activity_ID IS NULL)");
		}
		if(getC_Project_ID()>0)
		{
			sql.append(" AND C_Project_ID = " + getC_Project_ID());
		}
		else
		{
			sql.append(" AND (C_Project_ID = 0 OR C_Project_ID IS NULL)");
		}
		if(getC_Campaign_ID()>0)
		{
			sql.append(" AND C_Campaign_ID = " + getC_Campaign_ID());
		}
		else
		{
			sql.append(" AND (C_Campaign_ID = 0 OR C_Campaign_ID IS NULL)");
		}
		if(getM_Product_ID()>0)
		{
			sql.append(" AND M_Product_ID = " + getM_Product_ID());
		}
		else
		{
			sql.append(" AND (M_Product_ID = 0 OR M_Product_ID IS NULL)");
		}
		if(getC_SalesRegion_ID()>0)
		{
			sql.append(" AND C_SalesRegion_ID = " + getC_SalesRegion_ID());
		}
		else
		{
			sql.append(" AND (C_SalesRegion_ID = 0 OR C_SalesRegion_ID IS NULL)");
		}
		
		// For User Element
		if(element1Exists && elementValue1>0)
		{
			sql.append(" AND " + element1 + " = " + elementValue1);
		}
		else if(element1Exists && (elementValue1 == 0 || elementValue1 == null))
		{
			sql.append(" AND (" + element1 +" = 0 OR " + element1 + " IS NULL)");
		}
		if(element2Exists && elementValue2>0)
		{
			sql.append(" AND " + element2 + " = " + elementValue2);
		}
		else if(element2Exists && (elementValue2 == 0 || elementValue2 == null))
		{
			sql.append(" AND (" + element2 +" = 0 OR " + element2 + " IS NULL)");
		}
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_BudgetPlanning_ID());
			pstmt.setInt(2, Account_ID);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		return true;
	}

	/**
	 * Create Budget Planning Line Period based on Planning Period in header
	 */
	private void createPlanningPeriod()
	{
		// Check if planning line period is exists, cancel process
		String sql = "SELECT 1 FROM XX_BudgetPlanningLinePeriod " +
				"WHERE XX_BudgetPlanningLine_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_BudgetPlanningLine_ID());
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				return;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		// Get Total Period
		int period = 0;
		
		MBudgetPlanning plan = new MBudgetPlanning(getCtx(), getXX_BudgetPlanning_ID(), get_Trx());
		
		if(plan.getPlanningPeriod().equals(X_XX_BudgetPlanning.PLANNINGPERIOD_Monthly))
			period = 12;
		else if(plan.getPlanningPeriod().equals(X_XX_BudgetPlanning.PLANNINGPERIOD_Quarterly))
			period = 4;
		else if(plan.getPlanningPeriod().equals(X_XX_BudgetPlanning.PLANNINGPERIOD_Semester))
			period = 2;			
		// End get total period
		
		// Create Period
		int period_ID = plan.getC_Period_From_ID();
		MPeriod mPeriod = new MPeriod(getCtx(), period_ID, get_Trx());
		period_ID = GeneralEnhancementUtils.PeriodID(1, mPeriod.getC_Year_ID(), get_Trx());
		int periodProcessed = 0;
		
		do
		{
			periodProcessed++;
			MBudgetPlanningLinePeriod bplp = new MBudgetPlanningLinePeriod(getCtx(), 0, get_Trx());
			bplp.setAD_Org_ID(plan.getAD_Org_ID());
			bplp.setXX_BudgetPlanningLine_ID(getXX_BudgetPlanningLine_ID());	
			bplp.setPeriodNo(periodProcessed);
			bplp.setC_Period_ID(period_ID);
			bplp.setAmtAcctDr(Env.ZERO);
			bplp.setAmtAcctCr(Env.ZERO);
			bplp.setAmtRevCr(Env.ZERO);
			bplp.setAmtRevDr(Env.ZERO);
			bplp.setTotalDr(Env.ZERO);
			bplp.setTotalCr(Env.ZERO);
			bplp.setIsGenerated(true);
			bplp.save();
			
			// Get next period
			if(periodProcessed < period)
			{
				period_ID = GeneralEnhancementUtils.getNextPeriod(getCtx(), period_ID, plan.getPlanningPeriod(), get_Trx());
			}
		}while(periodProcessed<period || period_ID == 0);
			
	}
}
