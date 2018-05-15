/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.MClientInfo;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.utils.OrgUtils;

/**
 * @author Fanny
 *
 */
public class MOrg extends org.compiere.model.MOrg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param AD_Org_ID
	 * @param trx
	 */
	public MOrg(Ctx ctx, int AD_Org_ID, Trx trx) {
		super(ctx, AD_Org_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MOrg(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
    /** Set Main Organization.
    @param IsMainOrganization Indicate this organization is main organization */
    public void setIsMainOrg (boolean IsMainOrg)
    {
        set_Value ("IsMainOrg", Boolean.valueOf(IsMainOrg));
        
    }
    
    /** Get Main Organization.
    @return Indicate this organization is main organization */
    public boolean isMainOrg() 
    {
        return get_ValueAsBoolean("IsMainOrg");
        
    }
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		if(super.afterSave(newRecord, success))
		{
			if (newRecord || (is_ValueChanged("Value") || is_ValueChanged("Name"))) {
				MClientOrg.updateClientOrg(this);
			}
		}
		return true;
	}
	
	public static int getMainOrgID(Ctx ctx, int AD_Org_ID, Trx trx)
	{
		int mainOrgID = 0;
		MOrg org = new MOrg(ctx, AD_Org_ID, trx);
		// Check if organization in main organization or not
		if(org.isMainOrg())
			mainOrgID = org.getAD_Org_ID();
		
		// Check main organization based on organization tree
		if(mainOrgID==0)
		{
			int parentOrg_ID = 0;
			int orgID = AD_Org_ID;
			MClientInfo ci = MClientInfo.get(ctx);
			ArrayList<Integer> orgList = new ArrayList<Integer>();
			boolean loop = true;
			do{
				parentOrg_ID=OrgUtils.getParentID(ctx.getAD_Client_ID(), OrgUtils.nodeType_Organization, orgID, trx);
				orgList = OrgUtils.getListOfOrg(parentOrg_ID, ci.getAD_Tree_Org_ID(), orgList, false, orgID, trx);
				for(int i=0; i<orgList.size(); i++)
				{
					MOrg orgMember = new MOrg(ctx, orgList.get(i), trx);
					if(orgMember.isMainOrg())
					{
						mainOrgID=orgList.get(i);
						loop = false;
					}
					
				}
			}while(loop);
		}
		
		return mainOrgID;
	}
	
	public static int getClientMainOrg(Ctx ctx, int AD_Client_ID, Trx trx)
	{
		int mainOrgID = 0;
		String sql = "SELECT AD_Org_ID FROM AD_Org WHERE IsMainOrg = 'Y' " +
				"AND AD_Client_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Client_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				mainOrgID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return mainOrgID;
	}

}
