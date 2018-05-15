package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_MobileApps;

public class MMobileApps extends X_XX_MobileApps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MMobileApps(Ctx ctx, int XX_MobileApps_ID, Trx trx) {
		super(ctx, XX_MobileApps_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MMobileApps(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
