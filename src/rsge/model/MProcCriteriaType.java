/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_ProcCriteriaType;

/**
 * @author FANNY R
 *
 */
public class MProcCriteriaType extends X_XX_ProcCriteriaType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_ProcCriteriaType_ID
	 * @param trx
	 */
	public MProcCriteriaType(Ctx ctx, int XX_ProcCriteriaType_ID, Trx trx) {
		super(ctx, XX_ProcCriteriaType_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MProcCriteriaType(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
