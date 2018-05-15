package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_DisbursementType;

public class MDisbursementType extends X_XX_DisbursementType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MDisbursementType(Ctx ctx, int XX_DisbursementType_ID, Trx trx) {
		super(ctx, XX_DisbursementType_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MDisbursementType(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

}
