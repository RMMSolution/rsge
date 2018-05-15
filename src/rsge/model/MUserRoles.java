/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

/**
 * @author FANNY
 *
 */
public class MUserRoles extends org.compiere.model.MUserRoles {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param ignored
	 * @param trx
	 */
	public MUserRoles(Ctx ctx, int ignored, Trx trx) {
		super(ctx, ignored, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MUserRoles(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param AD_User_ID
	 * @param AD_Role_ID
	 * @param trx
	 */
	public MUserRoles(Ctx ctx, int AD_User_ID, int AD_Role_ID, Trx trx) {
		super(ctx, AD_User_ID, AD_Role_ID, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
//		if(super.beforeSave(newRecord))
//		{
//			System.out.println("Compiere Core");
//		}
		System.out.println("Add Check here");
		return true;
	}

}
