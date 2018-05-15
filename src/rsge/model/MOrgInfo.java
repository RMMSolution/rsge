/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MOrg;
import org.compiere.model.X_AD_Tree;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.utils.GeneralEnhancementUtils;

/**
 * @author Fanny
 *
 */
public class MOrgInfo extends org.compiere.model.MOrgInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /** Logger for class MOrgInfo */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MOrgInfo.class);


	public MOrgInfo(Ctx ctx, int AD_Org_ID, Trx trx) {
		super(ctx, AD_Org_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MOrgInfo(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	public MOrgInfo(MOrg org) {
		super(org);
		// TODO Auto-generated constructor stub
	}
	
    /** Set Purchasing Point.
    @param IsPurchasingPoint Organization which represent other organization in purchasing activity */
    public void setIsPurchasingPoint (boolean IsPurchasingPoint)
    {
        set_Value ("IsPurchasingPoint", Boolean.valueOf(IsPurchasingPoint));
        
    }
    
    /** Get Purchasing Point.
    @return Organization which represent other organization in purchasing activity */
    public boolean isPurchasingPoint() 
    {
        return get_ValueAsBoolean("IsPurchasingPoint");
        
    }


	/**
	 * 
	 * Get Organization Info
	 */
	public static MOrgInfo get(Ctx ctx, int AD_Org_ID, Trx trx)
	{
		MOrgInfo info = null;
		String sql = "SELECT * FROM AD_OrgInfo WHERE AD_Org_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, AD_Org_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				info = new MOrgInfo(ctx, rs, trx);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return info;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		if(isPurchasingPoint() && getM_Warehouse_ID()==0)
		{
			log.saveError("Error", "Warehouse is mandatory for Purchasing Point");			
			return false;
		}
		return true;
	}
	
    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {
    	// TODO Auto-generated method stub
    	if(super.afterSave(newRecord, success))
    	{
    		if(is_ValueChanged("Parent_Org_ID"))
    		{
        		int parentID = 0;
        		if(getParent_Org_ID()>0)
        			parentID = getParent_Org_ID();
    			String update = "UPDATE AD_TreeNode SET Parent_ID = ? " +
    					"WHERE Node_ID = ? AND AD_Tree_ID = (" +
    					"SELECT AD_Tree_Org_ID FROM AD_ClientInfo WHERE AD_Client_ID = ? )";
    			DB.executeUpdate(get_Trx(), update, parentID, getAD_Org_ID(), getAD_Client_ID());
        		GeneralEnhancementUtils.updateEntityParent(X_AD_Tree.TREETYPE_Organization, getAD_Org_ID(), parentID, getAD_Client_ID(), get_Trx());
    		}    		
    	}
    	return true;
    }
    
    public static MOrgInfo getPurchasingPoint(Ctx ctx, Trx trx)
    {
    	int orgID = 0;
    	String sql = "SELECT AD_Org_ID FROM AD_OrgInfo "
    			+ "WHERE IsPurchasingPoint = 'Y' "
    			+ "AND AD_Client_ID = ? ";
    	PreparedStatement pstmt = DB.prepareStatement(sql, trx);
    	ResultSet rs = null;
    	try{
    		System.out.println(sql);
    		System.out.println(ctx.getAD_Client_ID());
    		pstmt.setInt(1, ctx.getAD_Client_ID());
    		rs = pstmt.executeQuery();
    		if(rs.next())
    			orgID = rs.getInt(1);
    		rs.close();
    		pstmt.close();
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	
    	MOrgInfo oi = null;
    	if(orgID!=0)
    		oi = MOrgInfo.get(ctx, orgID, trx);
    	return oi;
    }

}
