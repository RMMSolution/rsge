/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_InvLineDepreciation;

/**
 * @author FANNY R
 *
 */
public class MInvLineDepreciation extends X_XX_InvLineDepreciation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_InvLineDepreciation_ID
	 * @param trx
	 */
	public MInvLineDepreciation(Ctx ctx, int XX_InvLineDepreciation_ID, Trx trx) {
		super(ctx, XX_InvLineDepreciation_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MInvLineDepreciation(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
