package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_ExpenseTransfer;

public class MExpenseTransfer extends X_XX_ExpenseTransfer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MExpenseTransfer(Ctx ctx, int XX_ExpenseTransfer_ID, Trx trx) {
		super(ctx, XX_ExpenseTransfer_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MExpenseTransfer(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}	

}
