package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

public class MMovementLine extends org.compiere.model.MMovementLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MMovementLine(Ctx ctx, int M_MovementLine_ID, Trx trx) {
		super(ctx, M_MovementLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MMovementLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public static boolean generateMovementLine(MTransferReceiptLine trLine, int M_Product_ID, int M_AttributeSetInstance_ID, int movementID, int sourceLocator, 
			int targetLocatorID, int lineNo)
	{
		if(sourceLocator==targetLocatorID)
			return false;
		MMovementLine ml = new MMovementLine(trLine.getCtx(), 0, trLine.get_Trx());
		ml.setM_Movement_ID(movementID);
		ml.setAD_Org_ID(trLine.getAD_Org_ID());
		ml.setConfirmedQty(trLine.getQtyAllocated());
		ml.setLine(lineNo);
		ml.setM_Locator_ID(sourceLocator);
		ml.setM_LocatorTo_ID(targetLocatorID);
		ml.setM_Product_ID(M_Product_ID);
		ml.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		ml.setMovementQty(trLine.getQtyAllocated());
		ml.setTargetQty(trLine.getQtyAllocated());
		ml.save();
		return true;
	}	

}
