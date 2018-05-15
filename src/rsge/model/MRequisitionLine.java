package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

public class MRequisitionLine extends org.compiere.model.MRequisitionLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MRequisitionLine(Ctx ctx, int M_RequisitionLine_ID, Trx trx) {
		super(ctx, M_RequisitionLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MRequisitionLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}	
	
	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		String reset = "UPDATE XX_PurchaseRequisitionLine "
				+ "SET M_RequisitionLine_ID = NULL "
				+ "WHERE M_RequisitionLine_ID = ? ";
		DB.executeUpdate(get_Trx(), reset, getM_RequisitionLine_ID());
		return true;
	}
	

}
