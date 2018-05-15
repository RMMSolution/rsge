/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

/**
 * @author Fanny
 *
 */
public class MBankAccount extends org.compiere.model.MBankAccount {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param C_BankAccount_ID
	 * @param trx
	 */
	public MBankAccount(Ctx ctx, int C_BankAccount_ID, Trx trx) {
		super(ctx, C_BankAccount_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MBankAccount(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
    /** Set Auto Reconcile.
    @param IsAutoReconcile Indicate any payment using this account will automatically create Bank Statement transaction */
    public void setIsAutoReconcile (boolean IsAutoReconcile)
    {
        set_Value ("IsAutoReconcile", Boolean.valueOf(IsAutoReconcile));
        
    }
    
    /** Get Auto Reconcile.
    @return Indicate any payment using this account will automatically create Bank Statement transaction */
    public boolean isAutoReconcile() 
    {
        return get_ValueAsBoolean("IsAutoReconcile");
        
    }


}
