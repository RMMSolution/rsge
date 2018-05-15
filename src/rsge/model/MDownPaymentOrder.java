package rsge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_DownPaymentOrder;

public class MDownPaymentOrder extends X_XX_DownPaymentOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MDownPaymentOrder(Ctx ctx, int XX_DownPaymentOrder_ID, Trx trx) {
		super(ctx, XX_DownPaymentOrder_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MDownPaymentOrder(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// Get Order
		if(is_ValueChanged("C_Order_ID"))
		{
			MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_Trx());
			setTotalLines(order.getTotalLines());
			setGrandTotal(order.getGrandTotal());			
			
			// Set Down Payment
			BigDecimal dpAmt = BigDecimal.ZERO;
			MDownPayment dp = new MDownPayment(getCtx(), getXX_DownPayment_ID(), get_Trx());
			if(dp.isDPPercentage())
				dpAmt = MDownPayment.calculateDPPercentage(dp, order);
			setDPAmount(dpAmt);
		}		
		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
//		if(is_ValueChanged("C_Order_ID"))
//		{
			BigDecimal totalLines = BigDecimal.ZERO;
			BigDecimal grandTotal = BigDecimal.ZERO;
			BigDecimal dpAmount = BigDecimal.ZERO;
			String sql = "SELECT COALESCE(SUM(TotalLines),0) AS TotalLines, " +
					"COALESCE(SUM(GrandTotal),0) AS GrandTotal, COALESCE(SUM(DPAmount),0) AS DPAmount FROM XX_DownPaymentOrder " +
					"WHERE XX_DownPayment_ID = ? ";
			PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
			ResultSet rs = null;
			try{
				pstmt.setInt(1, getXX_DownPayment_ID());
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					totalLines = rs.getBigDecimal("TotalLines");
					grandTotal = rs.getBigDecimal("GrandTotal");
					dpAmount = rs.getBigDecimal("DPAmount");
				}
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			System.out.println("dpAmount now " + dpAmount);
			MDownPayment.updateDownPayment(new MDownPayment(getCtx(), getXX_DownPayment_ID(), get_Trx()), dpAmount, totalLines, grandTotal);
//			String update = "UPDATE XX_DownPayment " +
//					"SET TotalAmt = ?, GrandTotal = ? " +
//					"WHERE XX_DownPayment_ID = ? ";
//			DB.executeUpdate(get_Trx(), update, totalLines, grandTotal, getXX_DownPayment_ID());
//		}
		return true;
	}

}
