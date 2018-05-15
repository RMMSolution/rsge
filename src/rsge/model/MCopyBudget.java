/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.model.MPeriod;
import org.compiere.util.Ctx;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

import rsge.po.X_XX_CopyBudget;

/**
 * @author bang
 *
 */
public class MCopyBudget extends X_XX_CopyBudget {
	private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MCopyBudget.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_CopyBudget_ID
	 * @param trx
	 */
	public MCopyBudget(Ctx ctx, int XX_CopyBudget_ID, Trx trx) {
		super(ctx, XX_CopyBudget_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MCopyBudget(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		MPeriod mPeriod = new MPeriod(getCtx(), getC_Period_ID(), get_Trx());
		MPeriod period = new MPeriod(getCtx(), getC_Period_To_ID(), get_Trx());
		if(mPeriod.getStartDate().after(period.getStartDate())){
			log.saveError("Error", Msg.getMsg(getCtx(), "Target period must be in greater than"));
			return false;
		}
		
		return true;
	}

}
