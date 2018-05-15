/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_ProcCriteriaSet;

/**
 * @author FANNY R
 *
 */
public class MProcCriteriaSet extends X_XX_ProcCriteriaSet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_ProcCriteriaSet_ID
	 * @param trx
	 */
	public MProcCriteriaSet(Ctx ctx, int XX_ProcCriteriaSet_ID, Trx trx) {
		super(ctx, XX_ProcCriteriaSet_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MProcCriteriaSet(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
