/**
 * 
 */
package rsge.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.MOrg;
import org.compiere.util.DB;
import org.compiere.util.Trx;

/**
 * @author Fanny
 *	Collection of method regarding organization
 */
public class OrgUtils {

	/**
	 * Get Parent ID based on Client ID, Node Type and record ID
	 * Node Type :
	 * - AY for Activity
	 * - BP for Business Partner
	 * - MC for Campaign
	 * - EV for Element Value 
	 * - PR for Product
	 * - PJ for Project
	 * - SR for Sales Region
	 * 
	 * @param AD_Client_ID
	 * @param NodeType
	 * @param child_ID
	 * @return
	 */	
	public static String 			nodeType_Activity = "AY";
	public static String 			nodeType_BPartner = "BP";
	public static String 			nodeType_Campaign = "MC";
	public static String 			nodeType_ElementValue = "EV";
	public static String 			nodeType_Organization = "OO";
	public static String 			nodeType_Product = "PR";
	public static String 			nodeType_Project = "PJ";
	public static String 			nodeType_SalesRegion = "SR";
	
	public static int getParentID(int AD_Client_ID, String NodeType, int child_ID, Trx trx)
	{		
		int parentID = 0;
		String tableName = null;		
		String tableNode = "AD_TreeNode";
		StringBuilder from = new StringBuilder();
				
		if(NodeType.equals(nodeType_BPartner)) // Business Partner
		{
			tableName = "C_BPartner";
			tableNode = tableNode + "BP";
			from.append("FROM " + tableNode +  " tn " +
					"INNER JOIN AD_ClientInfo ci ON (tn.AD_Tree_ID = ci.AD_Tree_BPartner_ID) ");
		}
		else if(NodeType.equals(nodeType_Product)) // Product
		{
			tableName = "M_Product";
			tableNode = tableNode + "PR";
			from.append("FROM " + tableNode +  " tn " +
					"INNER JOIN AD_ClientInfo ci ON (tn.AD_Tree_ID = ci.AD_Tree_Product_ID) ");
		}		
		else
		{
			if(NodeType.equals(nodeType_Activity)) // Activity
			{
				tableName = "C_Activity";
				from.append("FROM " + tableNode +  " tn " +
						"INNER JOIN AD_ClientInfo ci ON (tn.AD_Tree_ID = ci.AD_Tree_Activity_ID) ");
			}
			else if(NodeType.equals(nodeType_Campaign)) // Campaign
			{
				tableName = "C_Campaign";
				from.append("FROM " + tableNode +  " tn " +
						"INNER JOIN AD_ClientInfo ci ON (tn.AD_Tree_ID = ci.AD_Tree_Campaign_ID) ");
			}
			else if(NodeType.equals(nodeType_ElementValue)) // Element Value
			{
				tableName = "C_ElementValue";
				from.append("FROM " + tableNode +  " tn " +
						"INNER JOIN C_Element e ON (e.AD_Tree_ID = tn.AD_Tree_ID) " +
						"INNER JOIN C_AcctSchema_Element acse ON (acse.C_Element_ID = e.C_Element_ID) " +
						"INNER JOIN AD_ClientInfo ci ON (ci.C_AcctSchema1_ID = acse.C_AcctSchema_ID AND acse.ElementType = 'AC') ");
			}
			else if(NodeType.equals(nodeType_Organization)) // Organization
			{
				tableName = "AD_Org";
				from.append("FROM " + tableNode +  " tn " +
					"INNER JOIN AD_ClientInfo ci ON (tn.AD_Tree_ID = ci.AD_Tree_Org_ID) ");
			}
			else if(NodeType.equals(nodeType_Project)) // Project
			{
				tableName = "C_Project";
				from.append("FROM " + tableNode +  " tn " +
					"INNER JOIN AD_ClientInfo ci ON (tn.AD_Tree_ID = ci.AD_Tree_Project_ID) ");
			}
			else if(NodeType.equals(nodeType_SalesRegion)) // Sales Region
			{
				tableName = "C_SalesRegion";
				from.append("FROM " + tableNode +  " tn " +
					"INNER JOIN AD_ClientInfo ci ON (tn.AD_Tree_ID = ci.AD_Tree_SalesRegion_ID) ");
			}			
		}
		
		StringBuilder sql = new StringBuilder("SELECT n2." + tableName + "_ID ");
		sql.append(from);
		sql.append("INNER JOIN " + tableName + " n1 ON (tn.Node_ID = n1." + tableName + "_ID) ");
		sql.append("INNER JOIN " + tableName + " n2 ON (tn.Parent_ID = n2." + tableName + "_ID) ");
		sql.append("WHERE tn.AD_Client_ID = ? AND tn.Node_ID = ? ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setInt(2, child_ID);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				parentID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}		
		return parentID; // no parent 
	}	
	
	
	public static Integer[] getOrgs(MOrg org, int AD_Tree_Org_ID)
	{
		// Get Parent Org
		int parentID = OrgUtils.getParentID(org.getAD_Client_ID(), OrgUtils.nodeType_Organization, org.getAD_Org_ID(), org.get_Trx());
		ArrayList<Integer> orgSummary = new ArrayList<Integer>();
		ArrayList<Integer> orgList = new ArrayList<Integer>();
		orgList.add(org.getAD_Org_ID());
		orgSummary = getListOfOrg(parentID, AD_Tree_Org_ID, orgSummary, true, org.getAD_Org_ID(), org.get_Trx());
		orgList = getListOfOrg(parentID, AD_Tree_Org_ID, orgList, false, org.getAD_Org_ID(), org.get_Trx());
		ArrayList<Integer> newSummary = new ArrayList<Integer>();
		// Get summary orgs
		boolean loop = true;
		do{
			for(Integer summary : orgSummary)
			{
				// Get Org
				orgList = getListOfOrg(summary.intValue(), AD_Tree_Org_ID, orgList, false, org.getAD_Org_ID(), org.get_Trx());
				newSummary = getListOfOrg(summary.intValue(), AD_Tree_Org_ID, newSummary, true, org.getAD_Org_ID(), org.get_Trx());				
			}			
			orgSummary = new ArrayList<Integer>();
			orgSummary = newSummary;
			newSummary = new ArrayList<Integer>();
			if(orgSummary.size()==0)
				loop = false;
		}while(loop);
		Integer[]retValue = new Integer[orgList.size()];
		orgList.toArray(retValue);
		return retValue;
	}
	
	public static ArrayList<Integer> getListOfOrg(int orgID, int AD_Tree_Org_ID, ArrayList<Integer> orgList, boolean isSummary, int excludeOrg, Trx trx)
	{
		StringBuilder sql = new StringBuilder("SELECT o.AD_Org_ID FROM AD_Org o " +
				"INNER JOIN AD_TreeNode tn ON (o.AD_Org_ID = tn.Node_ID) " +
				"WHERE tn.Parent_ID = ? AND tn.AD_Tree_ID = ? ");
		if(isSummary)
			sql.append("AND o.IsSummary = 'Y' ");
		else
			sql.append("AND o.IsSummary = 'N' ");
		
		if(excludeOrg!=0)
		{
			sql.append("AND o.AD_Org_ID != ");
			sql.append(excludeOrg);
		}
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, orgID);
			pstmt.setInt(2, AD_Tree_Org_ID);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				Integer org = Integer.valueOf(rs.getInt(1));
				orgList.add(org);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return orgList;
	}
}
