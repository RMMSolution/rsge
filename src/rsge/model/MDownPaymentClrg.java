/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_DownPaymentClrg;

/**
 * @author Fanny
 *
 */
public class MDownPaymentClrg extends X_XX_DownPaymentClrg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_DownPaymentClrg_ID
	 * @param trx
	 */
	public MDownPaymentClrg(Ctx ctx, int XX_DownPaymentClrg_ID, Trx trx) {
		super(ctx, XX_DownPaymentClrg_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MDownPaymentClrg(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public static int getClrgAccount(Ctx ctx, int AD_Client_ID, int C_Currency_ID, Trx trx)
	{
		int clrgAcctID = 0;		
		String sql = "SELECT C_BankAccount_ID FROM XX_DownPaymentClrg " +
				"WHERE AD_Client_ID = ? AND C_Currency_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setInt(2, C_Currency_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				clrgAcctID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return clrgAcctID;
	}

}
