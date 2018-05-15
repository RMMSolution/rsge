package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_AccountElementMatch;

public class MAccountElementMatch extends X_XX_AccountElementMatch {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MAccountElementMatch(Ctx ctx, int XX_AccountElementMatch_ID, Trx trx) {
		super(ctx, XX_AccountElementMatch_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MAccountElementMatch(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
