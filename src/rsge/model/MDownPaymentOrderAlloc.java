package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_DownPaymentOrderAlloc;

public class MDownPaymentOrderAlloc extends X_XX_DownPaymentOrderAlloc {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MDownPaymentOrderAlloc(Ctx ctx, int XX_DownPaymentOrderAlloc_ID, Trx trx) {
		super(ctx, XX_DownPaymentOrderAlloc_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MDownPaymentOrderAlloc(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		if(updateParent())
			updateHeader();
		return true;
	}
	
	private boolean updateParent()
	{
		if(getXX_DownPaymentOrder_ID()==0)
			return true;
		BigDecimal allocatedAmt = BigDecimal.ZERO;
		String sql = "SELECT COALESCE(SUM(AllocatedAmt),0) AS AllocatedAmt "
				+ "FROM XX_DownPaymentOrderAlloc "
				+ "WHERE XX_DownPaymentOrder_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_DownPaymentOrder_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				allocatedAmt = rs.getBigDecimal("AllocatedAmt");
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		MDownPaymentOrder dpo = new MDownPaymentOrder(getCtx(), getXX_DownPaymentOrder_ID(), get_Trx());
		dpo.setAllocatedAmt(allocatedAmt);
		dpo.save();
		
//		String update = "UPDATE XX_DownPaymentOrder "
//				+ "SET AllocatedAmt = ? "
//				+ "WHERE XX_DownPaymentOrder_ID = ? ";
//		DB.executeUpdate(get_Trx(), update, allocatedAmt, getXX_DownPaymentOrder_ID());
		return true;
	}
	
	private boolean updateHeader()
	{
//		MDownPaymentOrder dpo = new MDownPaymentOrder(getCtx(), getXX_DownPaymentOrder_ID(), get_Trx());		
		BigDecimal allocatedAmt = BigDecimal.ZERO;
		String sql = "SELECT SUM(AllocatedAmt) AS AllocatedAmt FROM "
				+ "(SELECT COALESCE(SUM(AllocatedAmt),0) AS AllocatedAmt "
				+ "FROM XX_DownPaymentOrder "
				+ "WHERE XX_DownPaymentOrder_ID IN ( "
				+ "SELECT XX_DownPaymentOrder_ID FROM XX_DownPaymentOrderAlloc "
				+ "WHERE XX_DownPaymentOrderAlloc_ID = ?) "
				+ "UNION "
				+ "SELECT COALESCE(SUM(AllocatedAmt),0) AS AllocatedAmt "
				+ "FROM XX_DownPaymentOrderAlloc "
				+ "WHERE XX_DownPayment_ID IN ("
				+ "SELECT XX_DownPayment_ID FROM XX_DownPaymentOrderAlloc "
				+ "WHERE XX_DownPaymentOrderAlloc_ID = ?) "
				+ ")";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_DownPaymentOrderAlloc_ID());
			pstmt.setInt(2, getXX_DownPaymentOrderAlloc_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				allocatedAmt = rs.getBigDecimal("AllocatedAmt");
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		int downPaymentID = 0;
		if(getXX_DownPaymentOrder_ID()!=0)
			downPaymentID = new MDownPaymentOrder(getCtx(), getXX_DownPaymentOrder_ID(), get_Trx()).getXX_DownPayment_ID();
		else if(getXX_DownPayment_ID()!=0)
			downPaymentID = getXX_DownPayment_ID();

		MDownPayment dp = new MDownPayment(getCtx(), downPaymentID, get_Trx());
		dp.setAllocatedAmt(allocatedAmt);
		dp.save();
		return true;
	}

	
	public static MDownPaymentOrderAlloc get(MDownPaymentOrder dpo, int C_Invoice_ID)
	{
		MDownPaymentOrderAlloc retValue = null;
		StringBuilder sql = new StringBuilder("SELECT oa.* FROM XX_DownPaymentOrderAlloc oa "
				+ "INNER JOIN XX_DownPaymentOrder dpo ON oa.XX_DownPaymentOrder_ID = dpo.XX_DownPaymentOrder_ID "
				+ "WHERE dpo.C_Order_ID = ? ");
		sql.append("AND oa.C_Invoice_ID = ? ");

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), dpo.get_Trx());
		ResultSet rs = null;
		try{
			int index = 1;
			pstmt.setInt(index++, dpo.getC_Order_ID());
			pstmt.setInt(index++, C_Invoice_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				retValue = new MDownPaymentOrderAlloc(dpo.getCtx(), rs, dpo.get_Trx());
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(retValue==null)
		{
			retValue = new MDownPaymentOrderAlloc(dpo.getCtx(), 0, dpo.get_Trx());
			retValue.setClientOrg(dpo);
			retValue.setXX_DownPaymentOrder_ID(dpo.getXX_DownPaymentOrder_ID());
			retValue.setC_Invoice_ID(C_Invoice_ID);
			retValue.save();
		}
		return retValue;
	}

}
