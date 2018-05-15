/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.MOrg;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import rsge.po.X_XX_ClientOrg;

/**
 * @author FANNY
 *
 */
public class ValidateTenantOrg extends SvrProcess {

	private ArrayList<Integer>    			orgList = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Prepare Org data
		String sql = "SELECT o.AD_Org_ID " +
				"FROM AD_Org o " +
				"WHERE o.AD_Org_ID NOT IN (SELECT co.Org_ID FROM XX_ClientOrg co " +
				"WHERE o.AD_Client_ID = co.AD_Client_ID) " +
				"AND o.AD_Client_ID = ? " +
				"ORDER BY o.AD_Org_ID ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		orgList = new ArrayList<Integer>();
		
		try{
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				orgList.add(rs.getInt(1));				
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// Process Data
		if(orgList.size() > 0)
		{
			for(int i = 0; i < orgList.size(); i++)
			{
				MOrg org = new MOrg(getCtx(), orgList.get(i), get_Trx());
				
				X_XX_ClientOrg co = new X_XX_ClientOrg(getCtx(), 0, get_Trx());
				co.setAD_Client_ID(getAD_Client_ID());
				co.setAD_Org_ID(0);
				co.setOrg_ID(org.getAD_Org_ID());
				co.setOrgValue(org.getValue());
				co.setOrgName(org.getName());
				co.save();
			}
		}
		return Msg.getMsg(getCtx(), "Tenant Org Validated");
	}

}
