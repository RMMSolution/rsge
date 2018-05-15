package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_TransferReceiptLine;

public class MTransferReceiptLine extends X_XX_TransferReceiptLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MTransferReceiptLine(Ctx ctx, int XX_TransferReceiptLine_ID, Trx trx) {
		super(ctx, XX_TransferReceiptLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MTransferReceiptLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		BigDecimal totalQty = BigDecimal.ZERO;
		String sql = "SELECT COALESCE(SUM(QtyAllocated),0) AS Qty FROM XX_TransferReceiptLine "
				+ "WHERE XX_TransferReceipt_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_TransferReceipt_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				totalQty = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		String update = "UPDATE XX_TransferReceipt "
				+ "SET Qty = ? "
				+ "WHERE XX_TransferReceipt_ID = ? ";
		DB.executeUpdate(get_Trx(), update, totalQty, getXX_TransferReceipt_ID());
		return true;
	}
	
	public static MTransferReceiptLine[] getLines(MTransferReceipt tr)
	{
		ArrayList<MTransferReceiptLine> list = new ArrayList<>();
		String sql = "SELECT * " +
				"FROM XX_TransferReceiptLine " +
				"WHERE XX_TransferReceipt_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, tr.get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, tr.getXX_TransferReceipt_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				list.add(new MTransferReceiptLine(tr.getCtx(), rs, tr.get_Trx()));
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
				
		MTransferReceiptLine[] retValue = new MTransferReceiptLine[list.size()];
		list.toArray(retValue);
		return retValue;
	}


}
