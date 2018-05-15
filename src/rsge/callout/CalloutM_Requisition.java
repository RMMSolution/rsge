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

import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY
 *
 */
public class CalloutM_Requisition extends CalloutEngine {

	/**
	 * Set Activity based on AD_User_ID	 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */	
	public String setAD_User_ID(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer userID = (Integer)value;
		if(userID == null || userID.intValue() == 0)
			return "";	
		
		// Check if requisition is using Activity 
		boolean basedOnActivity = GeneralEnhancementUtils.checkRequisitionBasedOnActivity(ctx.getAD_Client_ID(), (Trx)null);
		if(!basedOnActivity)			
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
			pstmt.setInt(1, userID);
			rs = pstmt.executeQuery();
					
			if(rs.next())
			{	
				boolean isOnBehalf = false;
				
				// Set Activity
				int activityID = rs.getInt(1);
				mTab.setValue("C_EmpActivity_ID", activityID);
				mTab.setValue("C_Activity_ID", activityID);
				
				// Set On Behalf
				if(rs.getString(2).equalsIgnoreCase("Y"))
				{
					isOnBehalf = true;
				}
				mTab.setValue("IsOnBehalf", isOnBehalf);
			}
			rs.close();
			pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}		
		
		return "";
	}
}
