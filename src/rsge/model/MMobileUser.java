package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_MobileUser;

public class MMobileUser extends X_XX_MobileUser {

	public MMobileUser(Ctx ctx, int XX_MobileUser_ID, Trx trx) {
		super(ctx, XX_MobileUser_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MMobileUser(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
