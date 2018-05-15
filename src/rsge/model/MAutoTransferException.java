package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_AutoTransferException;

public class MAutoTransferException extends X_XX_AutoTransferException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MAutoTransferException(Ctx ctx, int XX_AutoTransferException_ID, Trx trx) {
		super(ctx, XX_AutoTransferException_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MAutoTransferException(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public static boolean isExist(Ctx ctx, int M_Product_Category_ID, int AD_Org_ID, Trx trx)
	{
		boolean isExists = false;
		String sql = "SELECT 1 FROM XX_AutoTransferException "
				+ "WHERE IsActive = 'Y' "
				+ "AND M_Product_Category_ID = ? "
				+ "AND AD_Org_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, M_Product_Category_ID);
			pstmt.setInt(2, AD_Org_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				isExists = true;
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return isExists;
	}

}
