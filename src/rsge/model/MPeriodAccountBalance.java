package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MElementValue;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_PeriodAccountBalance;

public class MPeriodAccountBalance extends X_XX_PeriodAccountBalance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MPeriodAccountBalance(Ctx ctx, int XX_PeriodAccountBalance_ID,
			Trx trx) {
		super(ctx, XX_PeriodAccountBalance_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MPeriodAccountBalance(Ctx ctx, ResultSet rs, Trx trx) {
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
		// TODO Auto-generated method stub
		updateHeader();
		return true;
	}
	
	private void updateHeader()
	{
		BigDecimal totalDr = BigDecimal.ZERO;
		BigDecimal totalCr = BigDecimal.ZERO;
		BigDecimal diff = BigDecimal.ZERO;
		
		String sql = "SELECT COALESCE(SUM(AmtAcctDr),0) AS AmtDr, COALESCE(SUM(AmtAcctCr),0) AS AmtCr FROM XX_PeriodAccountBalance " +
				"WHERE XX_SubsidiaryPeriodBalance_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_SubsidiaryPeriodBalance_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				totalDr = rs.getBigDecimal(1);
				totalCr = rs.getBigDecimal(2);
				diff = totalDr.subtract(totalCr);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		String update = "UPDATE XX_SubsidiaryPeriodBalance " +
				"SET TotalDr = ?, TotalCr = ?, DifferenceAmt=? " +
				"WHERE XX_SubsidiaryPeriodBalance_ID = ? ";
		DB.executeUpdate(get_Trx(), update, totalDr, totalCr, diff, getXX_SubsidiaryPeriodBalance_ID());
	}
	
	public static void update(Ctx ctx, int AccountID, int AD_Org_ID, int C_Period_ID, BigDecimal drAmt, BigDecimal crAmt, Trx trx)
	{
		MPeriodAccountBalance pab = new MPeriodAccountBalance(ctx, 0, trx);
		MElementValue ev = new MElementValue(ctx, AccountID, trx);
		pab.setDescription(ev.getDescription());
		pab.setAccount_ID(AccountID);
		pab.setAmtAcctDr(drAmt);
		pab.setAmtAcctCr(crAmt);
		int s = MSubsidiaryPeriodBalance.get(ctx, AD_Org_ID, C_Period_ID, trx);
		pab.setXX_SubsidiaryPeriodBalance_ID(s);
		pab.setAD_Org_ID(AD_Org_ID);
		pab.save();
	}

}
