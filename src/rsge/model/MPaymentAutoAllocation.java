package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_PaymentAutoAllocation;

public class MPaymentAutoAllocation extends X_XX_PaymentAutoAllocation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MPaymentAutoAllocation(Ctx ctx, int XX_PaymentAutoAllocation_ID,
			Trx trx) {
		super(ctx, XX_PaymentAutoAllocation_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MPaymentAutoAllocation(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public static boolean checkAutoAllocation(MPayment payment)
	{
		boolean isAutoAllocation = false;
		String sql = "SELECT 1 FROM XX_PaymentAutoAllocation " +
				"WHERE TenderType = ? " +
				"AND AD_Client_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, payment.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setString(1, payment.getTenderType());
			pstmt.setInt(2, payment.getAD_Client_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				isAutoAllocation = true;
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return isAutoAllocation;
	}

}
