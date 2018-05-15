package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_CashFlowElement;

public class MCashFlowElement extends X_XX_CashFlowElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MCashFlowElement(Ctx ctx, int XX_CashFlowElement_ID, Trx trx) {
		super(ctx, XX_CashFlowElement_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MCashFlowElement(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
