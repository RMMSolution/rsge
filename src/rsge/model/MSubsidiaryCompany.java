package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_SubsidiaryCompany;

public class MSubsidiaryCompany extends X_XX_SubsidiaryCompany {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MSubsidiaryCompany(Ctx ctx, int XX_SubsidiaryCompany_ID, Trx trx) {
		super(ctx, XX_SubsidiaryCompany_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MSubsidiaryCompany(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public static String				entityStatus_Parent = "Parent Company";
	public static String				entityStatus_HQ = "Head Quarter";
	public static String				entityStatus_Sister = "Sister Company";
	public static String				entityStatus_Child = "Child Company";
	public static String				entityStatus_Branch = "Branch";
	public static String				entityStatus_NoRelation = "No Relation";

	/**
	 * Get Entity status compare to other entity
	 * @param ctx
	 * @param client1
	 * @param client2
	 * @param trx
	 * @return
	 */
	public static String getEntityStatus(Ctx ctx, int client1, int client2, Trx trx)
	{
		String entityStatus = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// Check whether entity1 is parent company of entity 2
		boolean isParent=false;
		boolean isHQ = false;
		sql = "SELECT c.* FROM XX_SubsidiaryCompany c " +
				"INNER JOIN XX_SystemClient s ON (c.XX_SystemClient_ID = s.XX_SystemClient_ID) " +
				"WHERE c.AD_Client_ID = ? " +
				"AND s.Client_ID = ? ";
		pstmt = DB.prepareStatement(sql, trx);
		rs =null;
		try{
			pstmt.setInt(1, client1);
			pstmt.setInt(2, client2);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				isParent = true;
				MSubsidiaryCompany c = new MSubsidiaryCompany(ctx, rs, trx);
				if(c.getSubsidiaryType().equals(SUBSIDIARYTYPE_Branch))
					isHQ = true;				
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(isParent)
		{
			if(isHQ)
				return entityStatus_HQ;
			else
				return entityStatus_Parent;
		}

		// Check whether entity1 is subsidiary company of entity 2
		MSubsidiaryCompany sc = null;
		sql = "SELECT c.* FROM XX_SubsidiaryCompany c " +
				"INNER JOIN XX_SystemClient s ON (c.XX_SystemClient_ID = s.XX_SystemClient_ID) " +
				"WHERE c.AD_Client_ID = ? " +
				"AND s.Client_ID = ? ";
		pstmt = DB.prepareStatement(sql, trx);
		rs =null;
		try{
			pstmt.setInt(1, client2);
			pstmt.setInt(2, client1);
			rs = pstmt.executeQuery();
			if(rs.next())
				sc = new MSubsidiaryCompany(ctx, rs, trx);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(sc!=null) // Subsidiary Company
		{
			// Check whether entity is Child or Branch
			if(sc.getSubsidiaryType().equals(X_XX_SubsidiaryCompany.SUBSIDIARYTYPE_Branch))
				entityStatus = entityStatus_Branch;
			else if(sc.getSubsidiaryType().equals(X_XX_SubsidiaryCompany.SUBSIDIARYTYPE_ChildCompany))
			entityStatus = entityStatus_Child;			
		}
		else
		{
			// Get Parent Client of Entity 1
			int parent1 = 0;
			sql = "SELECT c.AD_Client_ID FROM XX_SubsidiaryCompany c " +
					"INNER JOIN XX_SystemClient s ON (c.XX_SystemClient_ID = s.XX_SystemClient_ID) " +
					"WHERE s.Client_ID = ?  ";
			pstmt = DB.prepareStatement(sql, trx);
			rs =null;
			try{
				pstmt.setInt(1, client1);
				rs = pstmt.executeQuery();
				if(rs.next())
					parent1 = rs.getInt(1);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			int parent2 = 0;
			sql = "SELECT c.AD_Client_ID FROM XX_SubsidiaryCompany c " +
					"INNER JOIN XX_SystemClient s ON (c.XX_SystemClient_ID = s.XX_SystemClient_ID) " +
					"WHERE s.Client_ID = ?  ";
			pstmt = DB.prepareStatement(sql, trx);
			rs =null;
			try{
				pstmt.setInt(1, client2);
				rs = pstmt.executeQuery();
				if(rs.next())
					parent2 = rs.getInt(1);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DB.closeResultSet(rs);
				DB.closeStatement(pstmt);
			}

			if(parent1==parent2)
				entityStatus = entityStatus_Sister;
			else
				entityStatus = entityStatus_NoRelation;
		}

		return entityStatus;
	}

}
