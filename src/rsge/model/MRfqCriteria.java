/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_RfqCriteria;

/**
 * @author FANNY R
 *
 */
public class MRfqCriteria extends X_XX_RfqCriteria {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_RfqCriteria_ID
	 * @param trx
	 */
	public MRfqCriteria(Ctx ctx, int XX_RfqCriteria_ID, Trx trx) {
		super(ctx, XX_RfqCriteria_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MRfqCriteria(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
