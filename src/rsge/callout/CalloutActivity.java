/**
 * 
 */
package rsge.callout;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

/**
 * @author FANNY
 *
 */
public class CalloutActivity extends CalloutEngine {

	/**
	 * Set Employee Activity based on user
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */	
	public String setActivityByUser(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer User_ID = (Integer)value;
		if(User_ID == null || User_ID.intValue() == 0)
			return "";

		String sql = "SELECT bp.C_Activity_ID, ac.HasAccessOtherActivity " +
				"FROM C_BPartner bp " +
				"INNER JOIN AD_User u ON (u.C_BPartner_ID = bp.C_BPartner_ID) " +
				"INNER JOIN C_Activity ac ON (bp.C_Activity_ID = ac.C_Activity_ID) " +
				"WHERE bp.IsEmployee = 'Y' " +
				"AND u.AD_User_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, (Trx)null);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, User_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{	
				mTab.setValue("C_EmpActivity_ID", rs.getInt(1));
				mTab.setValue("C_Activity_ID", rs.getInt(1));
				mTab.setValue("IsOnBehalf", rs.getString(2));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		finally{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		return "";
	}
	
	// For Web UI
//	public void setActivityByUser(PO po, UIField field, String oldValue, String newValue) {
//		
//		Integer User_ID = (Integer)po.get_Value("AD_User_ID");
//		if(User_ID == null || User_ID.intValue() == 0)
//			return;
//		
//		String sql = "SELECT bp.C_Activity_ID, ac.HasAccessOtherActivity " +
//				"FROM C_BPartner bp " +
//				"INNER JOIN AD_User u ON (u.C_BPartner_ID = bp.C_BPartner_ID) " +
//				"INNER JOIN C_Activity ac ON (bp.C_Activity_ID = ac.C_Activity_ID) " +
//				"WHERE bp.IsEmployee = 'Y' " +
//				"AND u.AD_User_ID = ? ";
//		
//		PreparedStatement pstmt = DB.prepareStatement(sql, (Trx)null);
//		ResultSet rs = null;
//		
//		try{
//			pstmt.setInt(1, User_ID);
//			rs = pstmt.executeQuery();
//			
//			if(rs.next())
//			{
//				po.set_Value("C_EmpActivity_ID", rs.getInt(1));
//				po.set_Value("C_Activity_ID", rs.getInt(1));
//				if(rs.getString(2).equalsIgnoreCase("Y"))
//				{
//					po.set_Value("IsOnBehalf", true);
//				}
//				else po.set_Value("IsOnBehalf", false);
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		finally{
//			DB.closeResultSet(rs);
//			DB.closeStatement(pstmt);
//		}
//	}

}
