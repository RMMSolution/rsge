package rsge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.compiere.model.MCurrency;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_DownPaymentOrder;

public class MDownPaymentOrder extends X_XX_DownPaymentOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MDownPaymentOrder.class);


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
		
		// Set Maximum Allocation
		MDownPayment dp = new MDownPayment(getCtx(), getXX_DownPayment_ID(), get_Trx());
		if(dp.isDPPercentage())
			setMaxAllocationPct(BigDecimal.valueOf(100));
			
		// Get Order
		if(is_ValueChanged("C_Order_ID"))
		{
			// Check if same order is already exists in another open down payment
			if(!checkOrderValidity(newRecord))
			{
				log.saveError("Error", "Order already exists in Open Down Payment");
				return false;
			}
			BigDecimal totalLines = BigDecimal.ZERO;
			BigDecimal grandTotal = BigDecimal.ZERO;

			Map<BigDecimal, BigDecimal> remains = getRemainsAmt();
			for(Map.Entry<BigDecimal, BigDecimal> entry : remains.entrySet())
			{
				totalLines = entry.getKey();
				grandTotal = entry.getValue();
			}
			setTotalLines(totalLines);
			setGrandTotal(grandTotal);			
			
			// Set Down Payment
//			BigDecimal dpAmt = BigDecimal.ZERO;
//			MDownPayment dp = new MDownPayment(getCtx(), getXX_DownPayment_ID(), get_Trx());
//			if(dp.isDPPercentage())
//				dpAmt = MDownPayment.calculateDPPercentage(dp, order);
//			setDPAmount(dpAmt);
		}		
		return true;
	}
	
	private Map<BigDecimal,BigDecimal> getRemainsAmt()
	{
		Map<BigDecimal, BigDecimal> remains = new HashMap<BigDecimal, BigDecimal>();
		String sql = "SELECT COALESCE(SUM(DPAmount),0) FROM XX_DownPaymentOrder "
				+ "WHERE C_Order_ID = ? "
				+ "AND XX_DownPaymentOrder_ID != ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		try{
			int index = 1;
			pstmt.setInt(index++, getC_Order_ID());
			pstmt.setInt(index++, getXX_DownPaymentOrder_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				BigDecimal dpAmt = rs.getBigDecimal(1);
				MOrder o = new MOrder(getCtx(), getC_Order_ID(), get_Trx());
				BigDecimal grandTotalRemains = o.getGrandTotal().subtract(dpAmt);
				BigDecimal ratio = o.getTotalLines().divide(o.getGrandTotal(), 4, RoundingMode.HALF_EVEN);
				BigDecimal dpLines = dpAmt.multiply(ratio);
				MCurrency currency = new MCurrency(getCtx(), o.getC_Currency_ID(), get_Trx());
				dpLines = dpLines.setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
				BigDecimal totalLinesRemains = o.getTotalLines().subtract(dpLines);
				remains.put(totalLinesRemains, grandTotalRemains);
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}		
		return remains;
	}
	
	private boolean checkOrderValidity(boolean newRecord)
	{
		boolean isValid = true;
		StringBuilder sql = new StringBuilder("SELECT 1 FROM XX_DownPaymentOrder dpo "
				+ "INNER JOIN XX_DownPayment dp ON dpo.XX_DownPayment_ID = dp.XX_DownPayment_ID "
				+ "WHERE dp.DocStatus NOT IN ('CL', 'CO') "
				+ "AND dpo.C_Order_ID = ? ");
		if(!newRecord)
			sql.append("AND XX_DownPaymentOrder_ID != ? ");
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		try{
			int index = 1;
			pstmt.setInt(index++, getC_Order_ID());
			if(!newRecord)
				pstmt.setInt(index++, getXX_DownPaymentOrder_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				isValid = false;
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isValid;
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
		return true;
	}
	
	public static BigDecimal getDownPaymentReserved(MDownPayment downPayment)
	{
		BigDecimal reservedAmt = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(DPAmount),0) AS ReservedAmt "
				+ "FROM XX_DownPaymentOrder "
				+ "WHERE XX_DownPayment_ID = ? ");

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), downPayment.get_Trx());
		ResultSet rs = null;
		try{
			int index = 1;
			pstmt.setInt(index++, downPayment.getXX_DownPayment_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				reservedAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return reservedAmt;
		
	}


}
