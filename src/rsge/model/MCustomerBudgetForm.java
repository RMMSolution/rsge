/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_CustomerBudgetForm;

/**
 * @author bang
 *
 */
public class MCustomerBudgetForm extends X_XX_CustomerBudgetForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_CustomerBudgetForm_ID
	 * @param trx
	 */
	public MCustomerBudgetForm(Ctx ctx, int XX_CustomerBudgetForm_ID, Trx trx) {
		super(ctx, XX_CustomerBudgetForm_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MCustomerBudgetForm(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
