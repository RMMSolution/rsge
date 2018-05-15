package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_DRConfirmationLine;

public class MDRConfirmationLine extends X_XX_DRConfirmationLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MDRConfirmationLine(Ctx ctx, int XX_DRConfirmationLine_ID, Trx trx) {
		super(ctx, XX_DRConfirmationLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MDRConfirmationLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		updateHeader();
		return super.afterSave(newRecord, success);
	}
	
	@Override
	protected boolean afterDelete(boolean success) {
		// TODO Auto-generated method stub
		updateHeader();
		return super.afterDelete(success);
	}
	
	private void updateHeader()
	{
		BigDecimal totalAmt = BigDecimal.ZERO;
		String sql = "SELECT SUM(Amount) FROM XX_DRConfirmationLine "
				+ "WHERE XX_DRConfirmation_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_DRConfirmation_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				totalAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		MDRConfirmation dc = new MDRConfirmation(getCtx(), getXX_DRConfirmation_ID(), get_Trx());
		BigDecimal diffAmt = dc.getAmount().subtract(totalAmt);
		String update = "UPDATE XX_DRConfirmation "
				+ "SET ReturnAmt = ?, DifferenceAmt = ? WHERE XX_DRConfirmation_ID = ? ";
		DB.executeUpdate(get_Trx(), update, totalAmt, diffAmt, getXX_DRConfirmation_ID());
	}

	
	public static MDRConfirmationLine[] getLines(MDRConfirmation dc)
	{
		ArrayList<MDRConfirmationLine> list = new ArrayList<>();
		
		String sql = "SELECT * FROM XX_DRConfirmationLine "
				+ "WHERE XX_DRConfirmation_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, dc.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, dc.getXX_DRConfirmation_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				list.add(new MDRConfirmationLine(dc.getCtx(), rs, dc.get_Trx()));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		MDRConfirmationLine[] retValue = new MDRConfirmationLine[list.size()];
		list.toArray(retValue);
		return retValue;
	}
}
