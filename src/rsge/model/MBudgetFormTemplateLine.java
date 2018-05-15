/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_BudgetFormTemplateLine;

/**
 * @author FANNY
 *
 */
public class MBudgetFormTemplateLine extends X_XX_BudgetFormTemplateLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param XX_BudgetFormTemplateLine_ID
	 * @param trx
	 */
	public MBudgetFormTemplateLine(Ctx ctx, int XX_BudgetFormTemplateLine_ID,
			Trx trx) {
		super(ctx, XX_BudgetFormTemplateLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MBudgetFormTemplateLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.framework.PO#afterSave(boolean, boolean)
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// Reset is valid to not valid
		updateIsValid();
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.framework.PO#afterDelete(boolean)
	 */
	@Override
	protected boolean afterDelete(boolean success) {
		// Reset is valid to not valid
		updateIsValid();
		return true;
	}

	private void updateIsValid()
	{
		String update = "Update XX_BudgetFormTemplate " +
				"SET IsValid = 'N' " +
				"WHERE XX_BudgetFormTemplate_ID = ? ";
		DB.executeUpdate(get_Trx(), update, getXX_BudgetFormTemplate_ID());
	}
}
