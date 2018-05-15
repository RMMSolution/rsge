/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_ClientOrg;

/**
 * @author Fanny
 *
 */
public class MClientOrg extends X_XX_ClientOrg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_ClientOrg_ID
	 * @param trx
	 */
	public MClientOrg(Ctx ctx, int XX_ClientOrg_ID, Trx trx) {
		super(ctx, XX_ClientOrg_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MClientOrg(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public static void updateClientOrg(MOrg org)
	{
		MClientOrg co = null;
		String sql = "SELECT * FROM XX_ClientOrg WHERE Org_ID = " + org.getAD_Org_ID();
		PreparedStatement pstmt = DB.prepareStatement(sql, org.get_Trx());
		ResultSet rs = null;
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
				co = new MClientOrg(org.getCtx(), rs, org.get_Trx());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		// Create new Client Org
		if(co == null)
		{
			co = new MClientOrg(org.getCtx(), 0, org.get_Trx());
			co.setOrg_ID(org.getAD_Org_ID());
			co.setAD_Client_ID(org.getAD_Client_ID());
		}
		co.setOrgValue(org.getValue());
		co.setOrgName(org.getName());
		co.save();
		return;
	}
	
	public static int get(int XX_ClientOrg_ID, Trx trx)
	{
		int id = 0;
		String sql = "SELECT Org_ID FROM XX_ClientOrg WHERE XX_ClientOrg_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, XX_ClientOrg_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				id = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return id;
	}
}
