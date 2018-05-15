package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_ClosingPeriodLine;

public class MClosingPeriodLine extends X_XX_ClosingPeriodLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MClosingPeriodLine(Ctx ctx, int XX_ClosingPeriodLine_ID,
			Trx trx) {
		super(ctx, XX_ClosingPeriodLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MClosingPeriodLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		updateHeader();
		return true;
	}
	
	@Override
	protected boolean afterDelete(boolean success) {
		updateHeader();
		return true;
	}

	private void updateHeader()
	{
		BigDecimal totalDr = BigDecimal.ZERO;
		BigDecimal totalCr = BigDecimal.ZERO;
		String sql = "SELECT COALESCE(SUM(AmtAcctDr),0) AS TotalDr, COALESCE(SUM(AmtAcctCr),0) AS TotalCr " +
				"FROM XX_ClosingPeriodLine WHERE XX_ClosingPeriod_ID = " + getXX_ClosingPeriod_ID();
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				totalDr = rs.getBigDecimal(1);
				totalCr = rs.getBigDecimal(2);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		String update = "UPDATE XX_ClosingPeriod SET TotalDr = ?, TotalCr = ? " +
				"WHERE XX_ClosingPeriod_ID = ? ";
		DB.executeUpdate(get_Trx(), update, totalDr, totalCr, getXX_ClosingPeriod_ID());
	}
}
