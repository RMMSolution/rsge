package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.MElementValue;
import org.compiere.model.X_PA_ReportLine;
import org.compiere.model.X_PA_ReportSource;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.report.MReportLine;
import org.compiere.report.MReportLineSet;
import org.compiere.report.MReportSource;
import org.compiere.util.DB;

public class ReportLineSetGenerator extends SvrProcess {

	private String 						p_Name = null;
	private int							p_C_AcctSchema_ID = 0;
	private String						p_ReportType = null;
	private String						p_AccountFrom = null;
	private String						p_AccountTo = null;
	private String						p_Combination = null;
	private int							p_Level = 1;
	private	int 						sequence = 0;
	private MReportLineSet 				rls = null;

	private ArrayList<MElementValue>	p_ElementValueList = null;
	
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null && element.getParameter_To() == null)
				;
			else if (name.equals("Name"))
				p_Name = (String)element.getParameter();			
			else if (name.equals("ReportType"))
				p_ReportType = (String)element.getParameter();			
			else if (name.equals("Combination"))
				p_Combination = (String)element.getParameter();			
			else if (name.equals("Account"))
			{
				p_AccountFrom = (String)element.getParameter();			
				p_AccountTo = (String)element.getParameter_To();			
			}
			else if (name.equals("LevelNo"))
				p_Level = element.getParameterAsInt();			
		}
		
		StringBuilder sql = new StringBuilder("SELECT ev.C_ElementValue_ID, ci.C_AcctSchema1_ID FROM C_ElementValue ev " +
				"INNER JOIN C_AcctSchema_Element ase ON (ev.C_Element_ID = ase.C_Element_ID) " +
				"INNER JOIN AD_ClientInfo ci ON (ase.C_AcctSchema_ID = ci.C_AcctSchema1_ID) " +
				"WHERE ci.IsActive = 'Y' " +
				"AND ci.AD_Client_ID = ? ");
		
		if(p_ReportType.equalsIgnoreCase("AE"))
		{
			if(p_AccountFrom==null)
			{
				prepareErrorMsg = "No Account From found";
			}
			else if(p_AccountTo!=null)
			{
				sql.append("AND ev.Value BETWEEN ");
				sql.append(p_AccountFrom);
				sql.append(" AND ");
				sql.append(p_AccountTo);				
			}
			else
			{
				sql.append("AND ev.Value >= ");
				sql.append(p_AccountFrom);
			}
			
		}
		else if(p_ReportType.equalsIgnoreCase("BS"))
		{
			sql.append("AND ev.AccountType IN ('A', 'L', 'O') ");			
//			sql.append("AND ev.AccountType IN ('L') ");			
		}
		else if(p_ReportType.equalsIgnoreCase("PL"))
		{
			sql.append("AND ev.AccountType IN ('R', 'E') ");						
		}
		
		sql.append("ORDER BY ev.Value ");
		
		if(prepareErrorMsg==null)
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
			ResultSet rs= null;
			try{
				pstmt.setInt(1, getAD_Client_ID());
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					if(p_ElementValueList==null)
						p_ElementValueList = new ArrayList<MElementValue>();
					MElementValue ev = new MElementValue(getCtx(), rs.getInt(1), get_Trx());
					p_ElementValueList.add(ev);
					if(p_C_AcctSchema_ID==0)
						p_C_AcctSchema_ID = rs.getInt(2);
				}
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}			
		}
	}
	
	private String prepareErrorMsg = null;

	@Override
	protected String doIt() throws Exception {
		
		if(prepareErrorMsg!=null)
			return prepareErrorMsg;
		
		int size = p_ElementValueList.size();
		if(size>0)
		{
			rls = new MReportLineSet(getCtx(), 0, get_Trx());
			rls.setAD_Client_ID(getAD_Client_ID());
			rls.setName(p_Name);			
			if(p_C_AcctSchema_ID!=0)
				rls.setC_AcctSchema_ID(p_C_AcctSchema_ID);
			rls.save();
		}
		
		int lastLvl = 1;
		int currentLvl = 1;
		
		
		int parentID = 0;
		int currentParentID = 0;
		
		int lastAccount_ID = 0;
		
		for(MElementValue ev : p_ElementValueList)
		{			
			System.out.println("Account Name " + ev.getName());
			int accountID = ev.getC_ElementValue_ID();
			parentID = getParentID(accountID);
			int accountLvl = getAccountLevel(ev.getC_ElementValue_ID());
			if(accountLvl>p_Level)
				continue;
			currentLvl = getAccountLevel(ev.getC_ElementValue_ID());
			if(currentParentID != parentID)
			{		
				if(lastLvl!=currentLvl)
				{
					if(lastLvl>currentLvl) // Create Total
					{
						sequence = generateTotal(sequence, currentParentID, lastLvl, currentLvl);
					}		
					lastLvl = currentLvl;
				}
				currentParentID = parentID;				
			}

			// Generate Report Line
			sequence = sequence+10;
			
			MReportLine rl = new MReportLine(getCtx(), 0, get_Trx());
			rl.setClientOrg(rls);
			rl.setPA_ReportLineSet_ID(rls.getPA_ReportLineSet_ID());
			rl.setName(ev.getValue());
			if(p_Combination.equalsIgnoreCase("V - N"))
				rl.setDescription(ev.getName());
			else if(p_Combination.equalsIgnoreCase("V - D"))
				rl.setDescription(ev.getDescription());
			rl.setIsPrinted(true);
			rl.setSeqNo(sequence);
			rl.setLineType(X_PA_ReportLine.LINETYPE_SegmentValue);
			rl.save();
			
			// Generate Report Line Source
			if(!ev.isSummary() 
					|| (ev.isSummary() && currentLvl==p_Level))
			{
				MReportSource rs = new MReportSource(getCtx(), 0, get_Trx());
				rs.setClientOrg(rl);
				rs.setPA_ReportLine_ID(rl.getPA_ReportLine_ID());
				rs.setElementType(X_PA_ReportSource.ELEMENTTYPE_Account);
				rs.setC_ElementValue_ID(ev.getC_ElementValue_ID());
				rs.save();
			}
			lastAccount_ID = ev.getC_ElementValue_ID();
		}
		
		lastAccount_ID = getParentID(lastAccount_ID);
		currentLvl = getAccountLevel(lastAccount_ID);		 
		sequence = generateTotal(sequence, lastAccount_ID, currentLvl, 0);
		
		return "Report Line Set Generated";
	}
	
	private int generateTotal(int currentSeq, int parentID, int currentLvl, int targetLvl)
	{
		int accountID = parentID;
		boolean loop = true;
		do{
			if(accountID!=0)
			{
				MElementValue ev = new MElementValue(getCtx(), accountID, get_Trx());
				currentSeq = currentSeq + 10;
				currentLvl = currentLvl-1;				
				
				MReportLine rl = new MReportLine(getCtx(), 0, get_Trx());
				rl.setClientOrg(rls);
				rl.setPA_ReportLineSet_ID(rls.getPA_ReportLineSet_ID());
				rl.setName("---");
				if(p_Combination.equalsIgnoreCase("V - N"))
					rl.setDescription("Total " + ev.getName());
				else if(p_Combination.equalsIgnoreCase("V - D"))
					rl.setDescription("Total " + ev.getDescription());
				rl.setIsPrinted(true);
				rl.setSeqNo(sequence);
				rl.setLineType(X_PA_ReportLine.LINETYPE_SegmentValue);
				rl.save();
				
				// Create Source
				MReportSource rs = new MReportSource(getCtx(), 0, get_Trx());
				rs.setClientOrg(rl);
				rs.setPA_ReportLine_ID(rl.getPA_ReportLine_ID());
				rs.setElementType(X_PA_ReportSource.ELEMENTTYPE_Account);
				rs.setC_ElementValue_ID(ev.getC_ElementValue_ID());
				rs.save();
				
				accountID = getParentID(accountID);
				if(currentLvl==targetLvl)
					loop = false;
			}
			else
				loop = false;
		}while(loop);
			
		return currentSeq;
	}
	
	private int getParentID(int accountID)
	{
		int parentID = 0;
		String sql = "SELECT tn.Parent_ID FROM AD_ClientInfo ci " +
				"INNER JOIN C_AcctSchema_Element ase ON (ci.C_AcctSchema1_ID = ase.C_AcctSchema_ID AND ase.ElementType = 'AC') " +
				"INNER JOIN C_Element e ON (ase.C_Element_ID = e.C_Element_ID) " +
				"INNER JOIN AD_TreeNode tn ON (e.AD_Tree_ID = tn.AD_Tree_ID) " +
				"WHERE ci.AD_Client_ID = ? AND tn.Node_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getAD_Client_ID());
			pstmt.setInt(2, accountID);
			rs = pstmt.executeQuery();
			if(rs.next())
				parentID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return parentID;
	}
	
	private int getAccountLevel(int accountID)
	{
		int level = 0;
		boolean loop = true;
		do{
			level++;
			String sql = "SELECT tn.Parent_ID FROM AD_ClientInfo ci " +
					"INNER JOIN C_AcctSchema_Element ase ON (ci.C_AcctSchema1_ID = ase.C_AcctSchema_ID AND ase.ElementType = 'AC') " +
					"INNER JOIN C_Element e ON (ase.C_Element_ID = e.C_Element_ID) " +
					"INNER JOIN AD_TreeNode tn ON (e.AD_Tree_ID = tn.AD_Tree_ID) " +
					"WHERE ci.AD_Client_ID = ? AND tn.Node_ID = ? ";
			PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
			ResultSet rs = null;
			try{
				System.out.println(sql);
				System.out.println(getAD_Client_ID());
				System.out.println(accountID);
				pstmt.setInt(1, getAD_Client_ID());
				pstmt.setInt(2, accountID);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					if(rs.getInt(1)==0)
						loop = false;
					else
					{
						accountID = rs.getInt(1);
						System.out.println("Parent " + accountID);
					}
				}
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			System.out.println("loop ");
			if(accountID==0)
				loop = false;
		}while(loop);
		return level;
	}


}
