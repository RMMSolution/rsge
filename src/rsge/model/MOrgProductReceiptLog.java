package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_OrgProductReceiptLog;

public class MOrgProductReceiptLog extends X_XX_OrgProductReceiptLog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MOrgProductReceiptLog(Ctx ctx, int XX_OrgProductReceiptLog_ID,
			Trx trx) {
		super(ctx, XX_OrgProductReceiptLog_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MOrgProductReceiptLog(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
