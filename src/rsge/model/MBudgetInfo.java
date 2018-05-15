/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;

import org.compiere.model.MClientInfo;
import org.compiere.model.X_GL_Budget;
import org.compiere.model.X_GL_BudgetControl;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

import rsge.po.X_XX_BudgetInfo;

/**
 * @author FANNY R
 *
 */
public class MBudgetInfo extends X_XX_BudgetInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MBudgetInfo.class);


	/**
	 * @param ctx
	 * @param XX_BudgetInfo_ID
	 * @param trx
	 */
	public MBudgetInfo(Ctx ctx, int XX_BudgetInfo_ID, Trx trx) {
		super(ctx, XX_BudgetInfo_ID, trx);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MBudgetInfo(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
//	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		if(getBudgetCutOffDate()!=0)
		{
			if(getBudgetCutOffMonth()==null)
			{
				log.saveError("Error", "Month is mandatory");
				return false;
			}
			if(getBudgetCutOffMonth().equals(BUDGETCUTOFFMONTH_February) && getBudgetCutOffDate()>29)
			{
				log.saveError("Error", "Maximum date for February is 29");
				return false;				
			}
			if(getBudgetCutOffMonth().equals(BUDGETCUTOFFMONTH_April) || getBudgetCutOffMonth().equals(BUDGETCUTOFFMONTH_June) 
					|| getBudgetCutOffMonth().equals(BUDGETCUTOFFMONTH_September) || getBudgetCutOffMonth().equals(BUDGETCUTOFFMONTH_November))
			{
				if(getBudgetCutOffDate()>30)
				{
					if(getBudgetCutOffMonth().equals(BUDGETCUTOFFMONTH_April))
						log.saveError("Error", "Maximum date for April is 30");
					if(getBudgetCutOffMonth().equals(BUDGETCUTOFFMONTH_June))
						log.saveError("Error", "Maximum date for June is 30");
					if(getBudgetCutOffMonth().equals(BUDGETCUTOFFMONTH_September))
						log.saveError("Error", "Maximum date for September is 30");
					if(getBudgetCutOffMonth().equals(BUDGETCUTOFFMONTH_November))
						log.saveError("Error", "Maximum date for November is 30");
					return false;				
				}
			}

		}
		return true;
	}
	
	public static MBudgetInfo get(Ctx ctx, int AD_Client_ID, Trx trx)
	{
		MBudgetInfo bi = null;
		String sql = "SELECT * FROM XX_BudgetInfo WHERE AD_Client_ID = " + AD_Client_ID;
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
				bi = new MBudgetInfo(ctx, rs, trx);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		return bi;
	}
	
	public static String runPreliminaryBudgetCheck(Ctx ctx, int AD_Client_ID, Trx trx)
	{
		String message = null;
		MBudgetInfo bi = MBudgetInfo.get(ctx, AD_Client_ID, trx);
//		if(bi.getOverBudgetRule().equals(X_XX_BudgetInfo.OVERBUDGETRULE_NotAllowed))
		if(bi.getOverBudgetRule().equals(X_XX_BudgetInfo.OVERBUDGETRULE_Workflow))
			message = Msg.getMsg(ctx, "DocumentOverBudget");
		return message;
	}
		
	/**
	 * Set GL Budget Control
	 * 
	 * @param oldGL_BudgetControl_ID
	 * @param newGL_BudgetControl_ID
	 * @param windowNo
	 * @throws Exception
	 */
	public void setGL_BudgetControl_ID(String oldGL_BudgetControl_ID,
			String newGL_BudgetControl_ID, int windowNo) throws Exception
	{
		if ((newGL_BudgetControl_ID == null) || (newGL_BudgetControl_ID.length() == 0))
			return;
		Integer GL_BudgetControl_ID = Integer.valueOf(newGL_BudgetControl_ID);
		if(GL_BudgetControl_ID == 0)
			return;
		
		X_GL_BudgetControl bc = getBudgetControl(this);		
		setC_AcctSchema_ID(bc.getC_AcctSchema_ID());		
	}		
	
	public String getBudgetControlScope()
	{
		X_GL_BudgetControl bc = getBudgetControl(this);
		return bc.getBudgetControlScope();
	}
	
	public static void createBudgetInfo(MGeneralSetup setup)
	{
		boolean isExists = false;
		String sql = "SELECT 1 FROM XX_BudgetInfo WHERE AD_Client_ID = " + setup.getAD_Client_ID();
		PreparedStatement pstmt = DB.prepareStatement(sql, setup.get_Trx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				isExists = true;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		if(isExists)
			return;
		
		// Create Budget Info
		MBudgetInfo bi = new MBudgetInfo(setup.getCtx(), 0, setup.get_Trx());
		bi.setAD_Client_ID(setup.getAD_Client_ID());
		bi.setAD_Org_ID(setup.getAD_Org_ID());
		bi.setXX_GeneralSetup_ID(setup.getXX_GeneralSetup_ID());
		bi.setOverBudgetRule(X_XX_BudgetInfo.OVERBUDGETRULE_Workflow);
		
		// Set Accounting Schema 
		MClientInfo ci = new MClientInfo(setup.getCtx(), setup.getAD_Client_ID(), setup.get_Trx());
		bi.setC_AcctSchema_ID(ci.getC_AcctSchema1_ID());
		bi.setBudgetCalendar_ID(ci.getC_Calendar_ID());
		bi.setReservedBudgetCalendar_ID(ci.getC_Calendar_ID());
	
		sql = "SELECT C_ConversionType_ID FROM C_ConversionType WHERE IsDefault = 'Y' ";
		pstmt = DB.prepareStatement(sql, setup.get_Trx());
		rs = null;
		
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				bi.setC_ConversionType_ID(rs.getInt(1));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Budget Control
		int budgetControl = 0;
		sql = "SELECT GL_BudgetControl_ID FROM GL_BudgetControl WHERE AD_Client_ID = " + setup.getAD_Client_ID();
		pstmt = DB.prepareStatement(sql, setup.get_Trx());
		rs = null;
		
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				budgetControl = rs.getInt(1);
			}
			else
			{
				// Create Budget Control
				// Step 1 - Create GL Budget
				X_GL_Budget budget = new X_GL_Budget(setup.getCtx(), 0, setup.get_Trx());
				budget.setName("Default Budget");
				budget.save();
				
				// Step 2 - Create Budget Control
				X_GL_BudgetControl bc = new X_GL_BudgetControl(setup.getCtx(), 0, setup.get_Trx());
				bc.setName("Default Budget Control");
				bc.setGL_Budget_ID(budget.getGL_Budget_ID());
				bc.setC_AcctSchema_ID(ci.getC_AcctSchema1_ID());
				bc.setCommitmentType(X_GL_BudgetControl.COMMITMENTTYPE_None);
				bc.setBudgetControlScope(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly);
				if(bc.save())
				{
					budgetControl = bc.getGL_BudgetControl_ID();
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		bi.setGL_BudgetControl_ID(budgetControl);
		bi.setFuturePeriodTransfer(0);
		bi.setSourceReserve(BigDecimal.ZERO);
		bi.save();
	}
	
	private X_GL_BudgetControl getBudgetControl(MBudgetInfo info)
	{
		X_GL_BudgetControl bc = new X_GL_BudgetControl(info.getCtx(), info.getGL_BudgetControl_ID(), info.get_Trx());
		return bc;
	}
	
	public Timestamp getCutOffDate(Timestamp checkDate)
	{
		Timestamp cutOffDate = null;
		if(getBudgetCutOffDate()==0)
			return cutOffDate;
		Calendar cal = Calendar.getInstance();
		cal.setTime(checkDate);
		int year = cal.get(Calendar.YEAR);		
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		int month = Integer.valueOf(getBudgetCutOffMonth());
		int date = getBudgetCutOffDate();
		if(year%4!=0 && month == Calendar.FEBRUARY && date == 29 )
			date = 28;				
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		cutOffDate = new Timestamp(cal.getTimeInMillis());

		return cutOffDate;
	}
	
}
