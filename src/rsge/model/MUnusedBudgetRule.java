/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_UnusedBudgetRule;

/**
 * @author FANNY R
 *
 */
public class MUnusedBudgetRule extends X_XX_UnusedBudgetRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_UnusedBudgetRule_ID
	 * @param trx
	 */
	public MUnusedBudgetRule(Ctx ctx, int XX_UnusedBudgetRule_ID, Trx trx) {
		super(ctx, XX_UnusedBudgetRule_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MUnusedBudgetRule(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
