package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_MaterialRequestLine;

public class MMaterialRequestLine extends X_XX_MaterialRequestLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MMaterialRequestLine(Ctx ctx, int XX_MaterialRequestLine_ID, Trx trx) {
		super(ctx, XX_MaterialRequestLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MMaterialRequestLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
}
