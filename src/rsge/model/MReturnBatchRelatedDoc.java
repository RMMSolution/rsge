/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.po.X_XX_ReturnBatchRelatedDoc;

/**
 * @author Fanny
 *
 */
public class MReturnBatchRelatedDoc extends X_XX_ReturnBatchRelatedDoc {
    /** Logger for class MReturnBatchRelatedDoc */
//    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MReturnBatchRelatedDoc.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_ReturnBatchRelatedDoc_ID
	 * @param trx
	 */
	public MReturnBatchRelatedDoc(Ctx ctx, int XX_ReturnBatchRelatedDoc_ID,
			Trx trx) {
		super(ctx, XX_ReturnBatchRelatedDoc_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MReturnBatchRelatedDoc(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		MQuickReturnLineBatch batch = new MQuickReturnLineBatch(getCtx(), getXX_QuickReturnLineBatch_ID(), get_Trx());
		MQuickReturnLine line = new MQuickReturnLine(getCtx(), batch.getXX_QuickReturnLine_ID(), get_Trx());
		DB.executeUpdate(get_Trx(), "UPDATE XX_QuickReturnLine SET AllocatedQty = (SELECT COALESCE(SUM(Qty),0) FROM XX_ReturnBatchRelatedDoc " +
				"WHERE XX_QuickReturnLineBatch_ID IN " +
				"(SELECT XX_QuickReturnLineBatch_ID FROM XX_QuickReturnLineBatch " +
				"WHERE XX_QuickReturnLine_ID = "+line.getXX_QuickReturnLine_ID()+")) WHERE XX_QuickReturnLine_ID = "+line.getXX_QuickReturnLine_ID());
		DB.executeUpdate(get_Trx(), "UPDATE XX_QuickReturnLine SET UnAllocatedQty = "+line.getQty()+" - AllocatedQty " +
				"WHERE XX_QuickReturnLine_ID = "+line.getXX_QuickReturnLine_ID());
		return true;
	}
	
	@Override
	protected boolean afterDelete(boolean success) {
		// TODO Auto-generated method stub
		MQuickReturnLineBatch batch = new MQuickReturnLineBatch(getCtx(), getXX_QuickReturnLineBatch_ID(), get_Trx());
		MQuickReturnLine line = new MQuickReturnLine(getCtx(), batch.getXX_QuickReturnLine_ID(), get_Trx());
		DB.executeUpdate(get_Trx(), "UPDATE XX_QuickReturnLine SET AllocatedQty = (SELECT COALESCE(SUM(Qty),0) FROM XX_ReturnBatchRelatedDoc " +
				"WHERE XX_QuickReturnLineBatch_ID IN " +
				"(SELECT XX_QuickReturnLineBatch_ID FROM XX_QuickReturnLineBatch " +
				"WHERE XX_QuickReturnLine_ID = "+line.getXX_QuickReturnLine_ID()+")) WHERE XX_QuickReturnLine_ID = "+line.getXX_QuickReturnLine_ID());
		DB.executeUpdate(get_Trx(), "UPDATE XX_QuickReturnLine SET UnAllocatedQty = "+line.getQty()+" - AllocatedQty " +
				"WHERE XX_QuickReturnLine_ID = "+line.getXX_QuickReturnLine_ID());
		return true;
	}
	
	public static MReturnBatchRelatedDoc createRelatedDoc(Ctx ctx, MQuickReturnLineBatch line, int C_OrderLine_ID, BigDecimal QtyDelivered, BigDecimal QtyOrdered, BigDecimal QtyReturned, Trx trx){
		MReturnBatchRelatedDoc relatedDoc = null;
		BigDecimal Qty = BigDecimal.ZERO;
		BigDecimal Tampung = BigDecimal.ZERO;
		if(QtyReturned.compareTo(Env.ZERO) > 0){
			Qty = QtyReturned;
		}else{
			Tampung = (QtyOrdered.subtract(QtyDelivered));
			if(Tampung.compareTo(line.getQty()) > 0){
				Qty = line.getQty();
			}else{
				Qty = Tampung;
			}
		}
		MOrderLine orderLine = new MOrderLine(ctx, C_OrderLine_ID, trx);
		String sql = "SELECT * FROM XX_ReturnBatchRelatedDoc WHERE AD_Client_ID = "+ctx.getAD_Client_ID()+" AND C_OrderLine_ID = "+orderLine.getC_OrderLine_ID()+" " +
				"AND XX_QuickReturnLineBatch_ID = "+line.getXX_QuickReturnLineBatch_ID()+"";
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if(rs.next()){
				relatedDoc = new MReturnBatchRelatedDoc(ctx, rs, trx);
				relatedDoc.setQty(Qty);
				relatedDoc.setQtyEntered(Qty);
				relatedDoc.save();
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(relatedDoc == null){
			relatedDoc = new MReturnBatchRelatedDoc(ctx, 0, trx);
			relatedDoc.setAD_Client_ID(line.getAD_Client_ID());
			relatedDoc.setAD_Org_ID(line.getAD_Org_ID());
			relatedDoc.setQty(Qty);
			relatedDoc.setQtyEntered(Qty);
			relatedDoc.setXX_QuickReturnLineBatch_ID(line.getXX_QuickReturnLineBatch_ID());
			relatedDoc.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
			relatedDoc.setM_InOutLine_ID(LineInOut(ctx, orderLine.getC_OrderLine_ID(), line.getM_AttributeSetInstance_ID(), trx));
			relatedDoc.setOrig_OrderLine_ID(orderLine.getOrig_OrderLine_ID());
			relatedDoc.setOrig_InOutLine_ID(orderLine.getOrig_InOutLine_ID());
			relatedDoc.setC_UOM_ID(line.getC_UOM_ID());
			relatedDoc.save();
		}
		return relatedDoc;
	}
	
	public static int LineInOut(Ctx ctx, int C_OrderLine_ID, int M_AttributeSetInstance_ID, Trx trx){
		String sql = "SELECT M_InOutLine_ID FROM M_InOutLine WHERE M_AttributeSetInstance_ID = "+M_AttributeSetInstance_ID+" " +
				"AND C_OrderLine_ID = "+C_OrderLine_ID;
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

}
