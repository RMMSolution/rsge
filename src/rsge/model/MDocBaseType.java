/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

/**
 * @author FANNY R
 *
 */
public class MDocBaseType extends org.compiere.model.MDocBaseType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Work Order Transaction - XWO **/
	public static final String DOCBASETYPE_WriteOffTrx = "XWO";
	
	/**
	 * @param ctx
	 * @param C_DocBaseType_ID
	 * @param trx
	 */
	public MDocBaseType(Ctx ctx, int C_DocBaseType_ID, Trx trx) {
		super(ctx, C_DocBaseType_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MDocBaseType(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
