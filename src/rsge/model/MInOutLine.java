/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;

import org.compiere.model.MInOut;
import org.compiere.model.MOrderLine;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.X_M_Product;

import rsge.model.MProduct;
import org.compiere.util.Ctx;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

/**
 * @author Fanny
 *
 */
public class MInOutLine extends org.compiere.model.MInOutLine {

    /** Logger for class MInOutLine */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MInOutLine.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MInOutLine(Ctx ctx, int M_InOutLine_ID, Trx trx) {
		super(ctx, M_InOutLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MInOutLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	public MInOutLine(MInOut inout) {
		super(inout);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// Check Qty Receipt Tolerance. 
		MInOut io = new MInOut(getCtx(), getM_InOut_ID(), get_Trx());
		if(!io.isSOTrx()&&!io.isReturnTrx()&&getM_Product_ID()!=0)
		{
			MProduct product = new MProduct(getCtx(), getM_Product_ID(), get_Trx());
			if(getC_OrderLine_ID()!=0 && product.getProductType().equals(X_M_Product.PRODUCTTYPE_Item))
			{
				if(product.getReceiptQtyTolerance().signum()>0)
				{
					MOrderLine ol = new MOrderLine(getCtx(), getC_OrderLine_ID(), get_Trx());
					BigDecimal remainQty = ol.getQtyOrdered().subtract(ol.getQtyDelivered());
					// Receipt Qty in Product UOM
					BigDecimal receiptQty = MUOMConversion.convertProductTo(getCtx(), getM_Product_ID(), getC_UOM_ID(), getQtyEntered());
					if(remainQty.compareTo(receiptQty)<0)
					{
						// Get Tolerance Qty
						MUOM uom = new MUOM(getCtx(), getC_UOM_ID(), get_Trx());
						BigDecimal tolerancePct = product.getReceiptQtyTolerance().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
						BigDecimal toleranceQty = remainQty.add(ol.getQtyOrdered().multiply(tolerancePct));
						if(toleranceQty.compareTo(getQtyEntered())<0)
						{
							StringBuilder msg = new StringBuilder(Msg.getMsg(getCtx(), "QtyReceipt Exceed Tolerance Qty"));
							msg.append(toleranceQty);
							msg.append(" ");
							msg.append(uom.getX12DE355());
							log.saveError("Error", msg.toString());
							return false;
						}
					}
				}
				
			}
		}
		
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if(super.afterSave(newRecord, success))
		{
//			// Generate Org Allocation if order is not sales trx
//			if(getC_OrderLine_ID()>0)
//			{
//				MOrderLine ol = new MOrderLine(getCtx(), getC_OrderLine_ID(), get_Trx());
//				MOrder o = new MOrder(getCtx(), ol.getC_Order_ID(), get_Trx());
//				if(!o.isSOTrx())
//				{
//					String sql = "SELECT * FROM XX_CostDistributionOrder " +
//							"WHERE C_OrderLine_ID = ? " +
//							"AND AD_Org_ID NOT IN (SELECT AD_Org_ID FROM XX_OrgReceiptAllocation " +
//							"WHERE M_InOutLine_ID = ? )";
//					PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
//					ResultSet rs = null;
//					try{
//						pstmt.setInt(1, getC_OrderLine_ID());
//						pstmt.setInt(2, getM_InOutLine_ID());
//						rs = pstmt.executeQuery();
//						while(rs.next())
//						{
//							MCostDistributionOrder cdo = new MCostDistributionOrder(getCtx(), rs, get_Trx());
//							MOrgReceiptAllocation ora = new MOrgReceiptAllocation(getCtx(), 0, get_Trx());
//							MUOM uom = new MUOM(getCtx(), getC_UOM_ID(), get_Trx());
//							BigDecimal qtyAllocated = cdo.getQty().multiply(getQtyEntered().divide(ol.getQtyEntered(), uom.getStdPrecision(), RoundingMode.HALF_EVEN));
//							qtyAllocated = qtyAllocated.setScale(uom.getStdPrecision(), RoundingMode.HALF_EVEN);
//							ora.setAD_Org_ID(cdo.getAD_Org_ID());
//							MInOut io = new MInOut(getCtx(), getM_InOut_ID(), get_Trx());
//							ora.setDateDoc(io.getMovementDate());
//							ora.setM_InOutLine_ID(getM_InOutLine_ID());
//							ora.setQtyAllocated(qtyAllocated);
//							ora.setC_UOM_ID(getC_UOM_ID());
//							ora.save();
//						}
//					}catch(Exception e)
//					{
//						e.printStackTrace();
//					}
//					finally
//					{
//						DB.closeResultSet(rs);
//						DB.closeStatement(pstmt);
//					}
//				}
//				
//			}
		}
		return true;
	}
}
