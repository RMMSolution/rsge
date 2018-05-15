package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.MElementValue;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class GenFinReportLineSet extends SvrProcess {
	
	private String						reportName = null;
	private String 						reportType = null;
	private String						scopeType = null;
	private ArrayList<MElementValue>	evList = null;

	@Override
	protected void prepare() {
		ProcessInfoParameter params[] = getParameter();
		for(ProcessInfoParameter param : params){
			String name = param.getParameterName();
			if(name.equals("Name"))
				reportName = (String) param.getParameter();
			if(name.equals("ReportType"))
				reportType = (String) param.getParameter();
			if(name.equals("ScopeType"))
				scopeType = (String) param.getParameter();
		}

		deleteReportLineSet();
		StringBuilder sql = new StringBuilder("SELECT * FROM C_ElementValue ");
		if(reportType.equalsIgnoreCase("BS"))
			sql.append("WHERE AccountType IN ('A', 'L', 'O') ");
		else
			sql.append("WHERE AccountType NOT IN ('A', 'L', 'O') ");
		sql.append("AND IsActive = 'Y' AND AD_Client_ID = ? ORDER BY Value");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				if(evList==null)
					evList = new ArrayList<>();
				MElementValue ev = new MElementValue(getCtx(), rs, get_Trx());
				evList.add(ev);
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
		// TODO Auto-generated method stub
		int size = 0;
		size = evList.size();
		for(int i=0; i<size; i++)
		{
			MElementValue ev = evList.get(i);
		}
		return null;
	}
	
	private boolean deleteReportLineSet()
	{
		String delete = "DELETE PA_ReportSource WHERE PA_ReportSource_ID IN (SELECT rs.PA_ReportSource_ID FROM PA_ReportSource rs " +
				"INNER JOIN PA_ReportLine rl ON (rs.PA_ReportLine_ID = rl.PA_ReportLine_ID) " +
				"INNER JOIN PA_ReportLineSet rls ON (rl.PA_ReportLineSet_ID = rls.PA_ReportLineSet_ID) " +
				"WHERE rls.Name = ? AND rls.AD_Client_ID = ?) ";
		DB.executeUpdate(get_Trx(), delete, reportName, getAD_Client_ID());
		
		delete = "DELETE PA_ReportLine WHERE PA_ReportLine_ID IN (SELECT rl.PA_ReportLine_ID FROM PA_ReportLine rl " +
				"INNER JOIN PA_ReportLineSet rls ON (rl.PA_ReportLineSet_ID = rls.PA_ReportLineSet_ID) " +
				"WHERE rls.Name = ? AND rls.AD_Client_ID = ?) ";
		DB.executeUpdate(get_Trx(), delete, reportName, getAD_Client_ID());

		delete = "DELETE PA_ReportLineSet " +
				"WHERE Name = ? AND AD_Client_ID = ? ";
		DB.executeUpdate(get_Trx(), delete, reportName, getAD_Client_ID());

		return true;
	}

}
