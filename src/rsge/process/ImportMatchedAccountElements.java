/**
 * 
 */
package rsge.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MAccountElementMatch;
import rsge.po.X_I_MatchedAccountElement;

/**
 * @author Fanny
 *
 */
public class ImportMatchedAccountElements extends SvrProcess {

	/**	Delete old Imported				*/
	private boolean			p_deleteOldImported = false;
	private static final String STD_CLIENT_CHECK = " AND AD_Client_ID=?";	

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] params = getParameter();
		for(ProcessInfoParameter param : params){
			String name = param.getParameterName();
			if (name.equals("DeleteOldImported"))
				p_deleteOldImported = "Y".equals(param.getParameter());
			else
				System.out.println("Param " + name + " not found...");
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		StringBuilder sql = null;
		int no = 0;

		//	****	Prepare	****

		//	Delete Old Imported
		if (p_deleteOldImported)
		{
			sql = new StringBuilder("DELETE FROM I_MatchedAccountElement "
				  + "WHERE I_IsImported='Y'" + STD_CLIENT_CHECK);
			no = DB.executeUpdate(get_Trx(), sql.toString(), 0);
			log.fine("Delete Old Impored =" + no);
		}
		
		String ts = DB.isPostgreSQL() ? "COALESCE(I_ErrorMsg,'')" 
				: "I_ErrorMsg";  //java bug, it could not be used directly
		
		//	Set Client, Org, IsActive, Created/Updated
		sql = new StringBuilder("UPDATE I_MatchedAccountElement "
			  + "SET AD_Client_ID = COALESCE (AD_Client_ID, ?),"
			  + " AD_Org_ID = COALESCE (AD_Org_ID,?),"
			  +	" IsActive = COALESCE (IsActive, 'Y'),"
			  + " Created = COALESCE (Created, SysDate),"
			  + " CreatedBy = COALESCE (CreatedBy, 0),"
			  + " Updated = COALESCE (Updated, SysDate),"
			  + " UpdatedBy = COALESCE (UpdatedBy, 0),"
			  + " I_ErrorMsg = NULL,"
			  + " I_IsImported = 'N' "
			  + "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL OR AD_Client_ID IS NULL OR AD_Org_ID IS NULL OR AD_Client_ID=0 OR AD_Org_ID=0");
		//Object[] params = new Object[]{p_AD_Client_ID,p_AD_Org_ID};
		no = DB.executeUpdate(get_Trx(), sql.toString(),0,0);
		log.info ("Reset=" + no);
		
		sql = new StringBuilder ("UPDATE I_MatchedAccountElement i "
				+ "SET i.XX_AccountElementMatch_ID =(SELECT ae.XX_AccountElementMatch_ID FROM XX_AccountElementMatch ae " +
				"WHERE ae.Account_ID= i.Account_ID AND ae.TargetAccount_ID = i.TargetAccount_ID AND ae.AD_Client_ID = i.AD_Client_ID ").append(") "
				+ "WHERE XX_AccountElementMatch_ID IS NULL"
				+ " AND I_IsImported<>'Y'").append(STD_CLIENT_CHECK);
			no = DB.executeUpdate(get_TrxName(), sql.toString(),getAD_Client_ID());
			log.fine("Set Accounts =" + no);

		sql = new StringBuilder ("UPDATE I_MatchedAccountElement i "
				+ "SET i.C_Element_ID =(SELECT e.C_Element_ID FROM C_Element e " +
				"WHERE e.Name = i.ElementValue AND e.AD_Client_ID = i.AD_Client_ID ").append(") "
				+ "WHERE C_Element_ID IS NULL"
				+ " AND I_IsImported<>'Y'").append(STD_CLIENT_CHECK);
			no = DB.executeUpdate(get_TrxName(), sql.toString(),getAD_Client_ID());
			log.fine("Set Element =" + no);

		sql = new StringBuilder ("UPDATE I_MatchedAccountElement i "
				+ "SET i.Account_ID =(SELECT ev.C_ElementValue_ID FROM C_ElementValue ev " +
				"WHERE ev.Value = i.AccountValue AND ev.AD_Client_ID = i.AD_Client_ID ").append(") "
				+ "WHERE Account_ID IS NULL"
				+ " AND I_IsImported<>'Y'").append(STD_CLIENT_CHECK);
			no = DB.executeUpdate(get_TrxName(), sql.toString(),getAD_Client_ID());
			log.fine("Set Account =" + no);
			
		sql = new StringBuilder ("UPDATE I_MatchedAccountElement i "
				+ "SET i.TargetElement_ID =(SELECT e.C_Element_ID FROM C_Element e " +
				"WHERE e.Name = i.ElementValue2 AND e.AD_Client_ID = i.AD_Client_ID ").append(") "
				+ "WHERE TargetElement_ID IS NULL"
				+ " AND I_IsImported<>'Y'").append(STD_CLIENT_CHECK);
			no = DB.executeUpdate(get_TrxName(), sql.toString(),getAD_Client_ID());
			log.fine("Set Target Element =" + no);

		sql = new StringBuilder ("UPDATE I_MatchedAccountElement i "
				+ "SET i.TargetAccount_ID =(SELECT ev.C_ElementValue_ID FROM C_ElementValue ev " +
				"WHERE ev.Value = i.AccountValue2 AND ev.AD_Client_ID = i.AD_Client_ID ").append(") "
				+ "WHERE TargetAccount_ID IS NULL"
				+ " AND I_IsImported<>'Y'").append(STD_CLIENT_CHECK);
			no = DB.executeUpdate(get_TrxName(), sql.toString(),getAD_Client_ID());
			log.fine("Set Target Account =" + no);


		commit();

		//	-------------------------------------------------------------------
		int insertNo = 0;
		int updateNo = 0;
		
		//	Go through Records
		sql = new StringBuilder ("SELECT * "
			+ "FROM I_MatchedAccountElement "
			+ "WHERE I_IsImported='N'").append(STD_CLIENT_CHECK)
			.append(" ORDER BY I_MatchedAccountElement_ID");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				X_I_MatchedAccountElement imp = new X_I_MatchedAccountElement(getCtx(), rs, get_Trx());
				int Account_ID = imp.getAccount_ID();
				int I_MatchedAccountElement_ID = imp.getI_MatchedAccountElement_ID();
				//	****	Create/Update ElementValue
				if (Account_ID == 0)		//	New
				{
					MAccountElementMatch a = new MAccountElementMatch(getCtx(), 0, get_Trx());
					a.setClientOrg(imp);
					a.setC_Element_ID(imp.getC_Element_ID());
					a.setAccount_ID(imp.getAccount_ID());
					a.setTargetElement_ID(imp.getTargetElement_ID());
					a.setTargetAccount_ID(imp.getTargetAccount_ID());
					if (a.save())
					{
						insertNo++;
						imp.setXX_AccountElementMatch_ID(a.getXX_AccountElementMatch_ID());
						imp.setI_IsImported(true);
						imp.save();
					}
					else
					{
						sql = new StringBuilder ("UPDATE I_MatchedAccountElement i "
							+ "SET I_IsImported='N', I_ErrorMsg="+ts +"||").append(DB.TO_STRING("Insert Account "))
							.append(" WHERE I_MatchedAccountElement_ID= ?");
						DB.executeUpdate(get_TrxName(), sql.toString(),I_MatchedAccountElement_ID);
					}

				}
				else
				{
					MAccountElementMatch a = new MAccountElementMatch(getCtx(), imp.getXX_AccountElementMatch_ID(), get_Trx());
					a.setC_Element_ID(imp.getC_Element_ID());
					a.setAccount_ID(imp.getAccount_ID());
					a.setTargetElement_ID(imp.getTargetElement_ID());
					a.setTargetAccount_ID(imp.getTargetAccount_ID());
					if (a.save())
					{
						updateNo++;
						imp.setI_IsImported(true);
						imp.save();
					}
					else
					{
						sql = new StringBuilder ("UPDATE I_MatchedAccountElement i "
							+ "SET I_IsImported='N', I_ErrorMsg="+ts +"||").append(DB.TO_STRING("Update Matched Account"))
							.append("WHERE I_MatchedAccountElement_ID= ? ");
						DB.executeUpdate(get_TrxName(), sql.toString(),I_MatchedAccountElement_ID);
					}
				}
			}
		}catch(Exception e)
		{
			throw new Exception ("create", e);
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		//	Set Error to indicator to not imported
		sql = new StringBuilder ("UPDATE I_MatchedAccountElement "
			+ "SET I_IsImported='N', Updated=SysDate "
			+ "WHERE I_IsImported<>'Y'").append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_TrxName(), sql.toString(),getAD_Client_ID());
		addLog (0, null, new BigDecimal (no), "@Errors@");
		addLog (0, null, new BigDecimal (insertNo), "@XX_AccountElementMatch_ID@: @Inserted@");
		addLog (0, null, new BigDecimal (updateNo), "@XX_AccountElementMatch_ID@: @Updated@");

		commit();

		//	Reset Processing Flag
		sql = new StringBuilder ("UPDATE I_MatchedAccountElement "
			+ "SET Processing='-'"
			+ "WHERE I_IsImported='Y' AND Processed='N'"
			+ " AND XX_AccountElementMatch_ID IS NOT NULL")
			.append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_TrxName(), sql.toString(),getAD_Client_ID());
		log.fine("Reset Processing Flag=" + no);

		//	Done
		sql = new StringBuilder ("UPDATE I_MatchedAccountElement "
			+ "SET Processing='N', Processed='Y'"
			+ "WHERE I_IsImported='Y'")
			.append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_TrxName(), sql.toString(),getAD_Client_ID());
		log.fine("Processed=" + no);
		
		return "";
	}

}
