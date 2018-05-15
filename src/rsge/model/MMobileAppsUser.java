package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_MobileAppsUser;

public class MMobileAppsUser extends X_XX_MobileAppsUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MMobileAppsUser(Ctx ctx, int XX_MobileAppsUser_ID, Trx trx) {
		super(ctx, XX_MobileAppsUser_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MMobileAppsUser(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public static String validateLogin(Ctx ctx, String mobileUser, String password, String mobileAppsID, Trx trx)
	{
		String retValue = null;
		String sql = "SELECT mu.XX_MobileUser_ID, COALESCE(au.password, mu.password) AS UserPwd FROM XX_MobileAppsUser au " +
				"INNER JOIN XX_MobileUser mu ON (au.XX_MobileUser_ID = mu.XX_MobileUser_ID) " +
				"INNER JOIN XX_MobileApps ma ON (au.XX_MobileApps_ID = ma.XX_MobileApps_ID) " +
				"WHERE mu.MobileUserName = ? AND ma.Value = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setString(1, mobileUser);
			pstmt.setString(2, mobileAppsID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				if(!password.equals(rs.getString(2)))
					retValue = "Username/password do not match";
				else
					retValue = (Integer.valueOf(rs.getInt(1))).toString();
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(retValue==null)
			retValue = "User do not have access to application";
		return retValue;
	}

}
