/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import org.compiere.model.MPeriod;
import org.compiere.model.MPeriodControl;
import org.compiere.model.X_C_PeriodControl;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

import rsge.po.X_XX_CorpBudgetPlan;

/**
 * @author FANNY R
 *
 */
public class MCorpBudgetPlan extends X_XX_CorpBudgetPlan {

    /** Logger for class MCorpBudgetPlan */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MCorpBudgetPlan.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_CorpBudgetPlan_ID
	 * @param trx
	 */
	public MCorpBudgetPlan(Ctx ctx, int XX_CorpBudgetPlan_ID, Trx trx) {
		super(ctx, XX_CorpBudgetPlan_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MCorpBudgetPlan(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(is_ValueChanged("BudgetStatus"))
		{
			if(getBudgetStatus().equals(BUDGETSTATUS_Approved))
			{
				// Check whether Tenant's budget has a full year periods ahead
				boolean periodValid = false;
				MPeriod startPeriod = new MPeriod(getCtx(), getC_From_Period_ID(), get_Trx());
				Calendar cal = Calendar.getInstance();
				cal.setTime(startPeriod.getStartDate());
				cal.set(Calendar.HOUR, 00);
				cal.set(Calendar.MINUTE, 00);
				cal.set(Calendar.SECOND, 00);				
				Timestamp iDate = new Timestamp(cal.getTimeInMillis());

				// Get Last year of budget year
				cal.add(Calendar.YEAR, 1);
				cal.add(Calendar.DAY_OF_YEAR, -1);
				cal.set(Calendar.HOUR, 00);
				cal.set(Calendar.MINUTE, 00);
				cal.set(Calendar.SECOND, 00);				
				Timestamp sDate = new Timestamp(cal.getTimeInMillis());
				cal.set(Calendar.HOUR, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);				
				Timestamp eDate = new Timestamp(cal.getTimeInMillis());

				String sql = "SELECT p.C_Period_ID FROM C_Period p " +
						"INNER JOIN C_Year y ON (p.C_Year_ID = y.C_Year_ID) " +
						"INNER JOIN AD_ClientInfo ci ON (y.C_Calendar_ID = ci.C_Calendar_ID) " +
						"WHERE p.EndDate BETWEEN ? AND ? " +
						"AND ci.AD_Client_ID = ? ";
				
				PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
				ResultSet rs = null;
				try{
					pstmt.setTimestamp(1, sDate);
					pstmt.setTimestamp(2, eDate);
					pstmt.setInt(3, getAD_Client_ID());
					
					rs = pstmt.executeQuery();
					if(rs.next())
					{
						periodValid = true;						
					}

				}catch(Exception e)
				{
					e.printStackTrace();
				}
				
				if(!periodValid)
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "BudgetPeriodNotSufficent"));
					return false;
				}
				
				sql = "SELECT pc.* FROM C_PeriodControl pc " +
						"INNER JOIN C_Period p ON (pc.C_Period_ID = p.C_Period_ID) " +
						"INNER JOIN C_Year y ON (p.C_Year_ID = y.C_Year_ID) " +
						"INNER JOIN AD_ClientInfo ci ON (y.C_Calendar_ID = ci.C_Calendar_ID) " +
						"WHERE pc.DocBaseType IN ('XBP', 'XBR', 'XRP') " +
						"AND p.AD_Client_ID = ? " +
						"AND pc.PeriodStatus NOT IN ('O') " +
						"AND p.StartDate BETWEEN ? AND ? " +
						"AND p.EndDate BETWEEN ? AND ? ";
				pstmt = DB.prepareStatement(sql, get_Trx());
				rs = null;
				try{
					pstmt.setInt(1, getAD_Client_ID());
					pstmt.setTimestamp(2, iDate);
					pstmt.setTimestamp(3, eDate);
					pstmt.setTimestamp(4, iDate);
					pstmt.setTimestamp(5, eDate);
					
					rs = pstmt.executeQuery();
					while(rs.next())
					{
						MPeriodControl pc = new MPeriodControl(getCtx(), rs, get_Trx());
						pc.setPeriodStatus(X_C_PeriodControl.PERIODSTATUS_Open);
						pc.setPeriodAction(X_C_PeriodControl.PERIODACTION_NoAction);
						pc.save();
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
			}
		}
		return true;
	}

}
