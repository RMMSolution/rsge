/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.MClient;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MClientOrg;
import rsge.model.MOrg;
import rsge.model.MDocBaseType;
import rsge.model.MGeneralSetup;

/**
 * @author Fanny
 *
 */
public class InitialRSGESetup extends SvrProcess {

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		checkRecord();
		// Check Doc Base Type. Change 
		checkDoc();		
		// Check Import.
		checkImport();
		// Validate Organization
		validateClientOrganization();
		return "Process Complete";
	}
	
	private void validateClientOrganization() {
		// TODO Auto-generated method stub
		ArrayList<MOrg> orgList = new ArrayList<MOrg>();
		String sql = "SELECT AD_Org_ID FROM (" +
				"SELECT AD_Org_ID FROM AD_Org " +
				"WHERE AD_Org_ID NOT IN (" +
				"SELECT Org_ID FROM XX_ClientOrg " +
				"WHERE AD_Client_ID = ?) " +
				"UNION " +
				"SELECT o.AD_Org_ID FROM AD_Org o " +
				"INNER JOIN XX_ClientOrg co ON (o.AD_Org_ID = co.Org_ID) " +
				"WHERE o.AD_Client_ID = ? " +
				"AND " +
				"(o.Value != co.OrgValue " +
				"OR o.Name!=co.OrgName) " +
				") " +
				"WHERE AD_Org_ID != 0 ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getAD_Client_ID());
			pstmt.setInt(2, getAD_Client_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MOrg org = new MOrg(getCtx(), rs.getInt(1), get_Trx());
				orgList.add(org);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		for(MOrg org : orgList)
		{
			MClientOrg.updateClientOrg(org);
		}
		
	}

	private void checkRecord()
	{
		String sql = "SELECT * " +
				"FROM XX_GeneralSetup " +
				"WHERE AD_Client_ID = " + getAD_Client_ID();
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			if(!rs.next())
			{
				MGeneralSetup setup = new MGeneralSetup(getCtx(), 0, get_Trx());
				setup.setAD_Client_ID(getAD_Client_ID());
				MClient client = new MClient(getCtx(), getAD_Client_ID(), get_Trx());
				StringBuilder desc = new StringBuilder("General Setup ");
				desc.append(client.getName());
				setup.setName(client.getName());
				setup.setDescription(desc.toString());
				setup.save();
			}
			pstmt.close();
			rs.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}			
		
	}
	
	private void checkDoc()
	{
		String sql = "SELECT * " +
				"FROM C_DocBaseType " +
				"WHERE DocBaseType IN ('ARC') ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MDocBaseType dbt = new MDocBaseType(getCtx(), rs.getInt("C_DocBaseType_ID"), get_Trx());
				if(!dbt.getAccountingClassname().equals("rsge.acct.Doc_Invoice"))
				{
					dbt.setAccountingClassname("rsge.acct.Doc_Invoice");
					dbt.save();
				}
			}
			pstmt.close();
			rs.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}			
		
		sql = "SELECT * " +
				"FROM C_DocBaseType " +
				"WHERE DocBaseType IN ('MMM') ";
		pstmt = DB.prepareStatement(sql, get_Trx());
		rs = null;
		
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MDocBaseType dbt = new MDocBaseType(getCtx(), rs.getInt("C_DocBaseType_ID"), get_Trx());
				if(!dbt.getAccountingClassname().equals("rsge.acct.Doc_Movement"))
				{
					dbt.setAccountingClassname("rsge.acct.Doc_Movement");
					dbt.save();
				}
			}
			pstmt.close();
			rs.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		sql = "SELECT * " +
				"FROM C_DocBaseType " +
				"WHERE DocBaseType IN ('MXP') ";
		pstmt = DB.prepareStatement(sql, get_Trx());
		rs = null;
		
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MDocBaseType dbt = new MDocBaseType(getCtx(), rs.getInt("C_DocBaseType_ID"), get_Trx());
				if(!dbt.getAccountingClassname().equals("rsge.acct.Doc_MatchPO"))
				{
					dbt.setAccountingClassname("rsge.acct.Doc_MatchPO");
					dbt.save();
				}
			}
			pstmt.close();
			rs.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
	}
	
	private void checkImport()
	{
		//UPDATE IMPORT INVOICE
		String update = "UPDATE AD_Process SET ClassName = 'rsge.process.ImportInvoice' " +
				"WHERE Value = 'Import_Invoice' " +
				"AND (AD_Client_ID = ? OR AD_Client_ID IS NULL) ";
		DB.executeUpdate(get_Trx(), update, 0);
	}

}
