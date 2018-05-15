package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_OrgProductReceipt;

public class MOrgProductReceipt extends X_XX_OrgProductReceipt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MOrgProductReceipt(Ctx ctx, int XX_OrgProductReceipt_ID, Trx trx) {
		super(ctx, XX_OrgProductReceipt_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MOrgProductReceipt(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}	

}
