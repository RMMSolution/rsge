package rsge.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import rsge.model.MPeriodAccountBalance;
import rsge.model.MSubsidiaryCompany;
import rsge.model.MSystemClient;

import org.compiere.model.MElementValue;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Ctx;
import org.compiere.util.DB;

public class GenerateSubsidiaryPeriodBalance extends SvrProcess {

	private int						p_C_Period_ID = 0;
	private int						p_XX_SystemClient_ID = 0;
	private ArrayList<Integer>		accountList = null;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null && element.getParameter_To() == null)
				;
			else if (name.equals("C_Period_ID"))
				p_C_Period_ID = element.getParameterAsInt();
			else if (name.equals("XX_SystemClient_ID"))
				p_XX_SystemClient_ID = element.getParameterAsInt();
		}	
		
		String sql = "SELECT ev.C_ElementValue_ID AS Account_ID FROM C_ElementValue ev " +
				"INNER JOIN C_AcctSchema_Element ase ON (ev.C_Element_ID = ase.C_Element_ID) " +
				"INNER JOIN AD_ClientInfo ci ON (ase.C_AcctSchema_ID = ci.C_AcctSchema1_ID) " +
				"WHERE ci.AD_Client_ID = ? " +
				"AND ev.IsSummary = 'N' " +
				"AND ev.IsActive = 'Y' " +
				"ORDER BY ev.Value ";
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				if(accountList==null)
					accountList = new ArrayList<Integer>();
				accountList.add(rs.getInt(1));
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected String doIt() throws Exception {
		MPeriod period = new MPeriod(getCtx(), p_C_Period_ID, get_Trx());
		// Get System Client
		ArrayList<MSubsidiaryCompany>					subsidiaryList = null;
		StringBuilder sql = new StringBuilder("SELECT * FROM XX_SubsidiaryCompany WHERE AD_Client_ID = ? ");
		if(p_XX_SystemClient_ID!=0)
		{
			sql.append("AND XX_SystemClient_ID = ");
			sql.append(p_XX_SystemClient_ID);
		}
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				if(subsidiaryList==null)
					subsidiaryList = new ArrayList<MSubsidiaryCompany>();
				MSubsidiaryCompany sc = new MSubsidiaryCompany(getCtx(), rs, get_Trx());
				subsidiaryList.add(sc);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		for(MSubsidiaryCompany sc : subsidiaryList)
		{
			int size = accountList.size();
			for(int i=0;i<size;i++)
			{
				MSystemClient s = new MSystemClient(getCtx(), sc.getXX_SystemClient_ID(), get_Trx());
				int accountID = accountList.get(i);
				String accounts = getAccounts(accountID, s.getClient_ID());
				if(accounts == null)
					continue;
				sql = new StringBuilder("SELECT COALESCE(SUM(fab.AmtAcctDr),0), COALESCE(SUM(fab.AmtAcctCr),0) FROM Fact_Acct_Balance fab " +
						"INNER JOIN C_ElementValue ev ON (fab.Account_ID = ev.C_ElementValue_ID) " +
						"INNER JOIN Fact_Accumulation fa ON (fab.Fact_Accumulation_ID = fa.Fact_Accumulation_ID) " +
						"INNER JOIN AD_ClientInfo ci ON (fab.C_AcctSchema_ID = ci.C_AcctSchema1_ID) " +
						"WHERE fa.BalanceAccumulation = 'M' " +
						"AND fab.PostingType = 'A' " +
						"AND ci.AD_Client_ID = ? " +
						"AND fab.DateAcct BETWEEN ? AND ? " +
						"AND fab.Account_ID IN (");
				sql.append(accounts);
				sql.append(") ");
				
				pstmt = DB.prepareStatement(sql.toString(), get_Trx());
				rs = null;
				try{
					System.out.println(sql);
					System.out.println(s.getClient_ID());
					System.out.println(period.getStartDate());
					System.out.println(period.getEndDate());
					pstmt.setInt(1, s.getClient_ID());
					pstmt.setTimestamp(2, period.getStartDate());
					pstmt.setTimestamp(3, period.getEndDate());
					rs = pstmt.executeQuery();
					while(rs.next())
					{
						System.out.println("HERE");
						BigDecimal dr = rs.getBigDecimal(1);
						BigDecimal cr = rs.getBigDecimal(2);
						System.out.println("DR " + dr);
						System.out.println("CR " + cr);
						if(dr.signum()!=0 || cr.signum()!=0)
							MPeriodAccountBalance.update(getCtx(), accountID, sc.getAD_Org_ID(), p_C_Period_ID, rs.getBigDecimal(1), rs.getBigDecimal(2), get_Trx());
					}
					rs.close();
					pstmt.close();
				}catch(Exception e)
				{
					e.printStackTrace();
				}

			}
		}
		return "Process Complete";
	}
	
	private String getAccounts(int accountID, int AD_Client_ID)
	{
		String accounts = null;
		MElementValue ev = new MElementValue(getCtx(), accountID, get_Trx());
		// Get account ID from target Client
		int ID = 0;		
		String sql = "SELECT ev.C_ElementValue_ID FROM C_ElementValue ev " +
				"INNER JOIN C_AcctSchema_Element e ON (ev.C_Element_ID = e.C_Element_ID) " +
				"INNER JOIN AD_ClientInfo ci ON (e.C_AcctSchema_ID = ci.C_AcctSchema1_ID) " +
				"WHERE ci.AD_Client_ID = ? AND ev.Value = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setString(2, ev.getValue());
			rs = pstmt.executeQuery();
			if(rs.next())
				ID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		StringBuilder accountList = null;
		if(ID!=0)
		{
			accountList = getListOfAccount(accountList, AD_Client_ID, ID);
			if(accountList!=null)
				accounts = accountList.toString();
		}		
		return accounts;
	}
	
	private StringBuilder getListOfAccount(StringBuilder accounts, int AD_Client_ID, int accountID)
	{
		Ctx ctx = new Ctx();
		ctx.setAD_Client_ID(AD_Client_ID);
		
		MElementValue ev = new MElementValue(getCtx(), accountID, get_Trx());
		if(!ev.isSummary())
		{
			accounts = updateAccountList(accountID, accounts);
		}
		else
		{
			String sql = "SELECT tn.Node_ID AS Account_ID FROM AD_ClientInfo ci " +
					"INNER JOIN C_AcctSchema_Element ase ON (ci.C_AcctSchema1_ID = ase.C_AcctSchema_ID) " +
					"INNER JOIN C_Element e ON (ase.C_Element_ID = e.C_Element_ID) " +
					"INNER JOIN AD_TreeNode tn ON (e.AD_Tree_ID = tn.AD_Tree_ID) " +
					"WHERE ci.AD_Client_ID = ? " +
					"AND tn.Parent_ID = ? ";

			PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
			ResultSet rs = null;
			try{
				pstmt.setInt(1, AD_Client_ID);
				pstmt.setInt(2, accountID);
				rs = pstmt.executeQuery();
				while(rs.next())
					accounts = getListOfAccount(accounts, AD_Client_ID, rs.getInt(1));
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}

		}
		return accounts;
	}
	
	private StringBuilder updateAccountList(int accountID, StringBuilder accounts)
	{
		if(accounts==null)
		{
			accounts = new StringBuilder();
			accounts.append(accountID);
		}
		else
		{
			accounts.append(", ");
			accounts.append(accountID);
		}
		return accounts;
	}

}
