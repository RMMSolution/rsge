/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_ReservedBudget;

/**
 * @author FANNY R
 *
 */
public class MReservedBudget extends X_XX_ReservedBudget {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_ReservedBudget_ID
	 * @param trx
	 */
	public MReservedBudget(Ctx ctx, int XX_ReservedBudget_ID, Trx trx) {
		super(ctx, XX_ReservedBudget_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MReservedBudget(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
