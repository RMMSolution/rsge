package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MMovementLine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_MaterialTransferOrderLine;

public class MMaterialTransferOrderLine extends X_XX_MaterialTransferOrderLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MMaterialTransferOrderLine(Ctx ctx, int XX_MaterialTransferOrderLine_ID, Trx trx) {
		super(ctx, XX_MaterialTransferOrderLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MMaterialTransferOrderLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public MMaterialTransferOrderLine(MMaterialTransferOrder mto) {
		super(mto.getCtx(), 0, mto.get_Trx());
		setClientOrg(mto);
		setXX_MaterialTransferOrder_ID(mto.getXX_MaterialTransferOrder_ID());
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		String sql = "SELECT COALESCE(SUM(Qty),0) AS Qty "
				+ "FROM XX_MaterialTransferOrderLine "
				+ "WHERE XX_MaterialTransferOrder_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_MaterialTransferOrder_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				MMaterialTransferOrder mto = new MMaterialTransferOrder(getCtx(), getXX_MaterialTransferOrder_ID(), get_Trx());
				mto.setQtyDelivered(rs.getBigDecimal(1));
				mto.save();
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
