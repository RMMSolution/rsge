/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MElementValue;
import org.compiere.model.X_PA_ReportLine;
import org.compiere.model.X_PA_ReportSource;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.report.MReportLine;
import org.compiere.report.MReportLineSet;
import org.compiere.report.MReportSource;
import org.compiere.util.DB;

/**
 * @author Fanny
 *
 */
public class GenerateReportLineSet extends SvrProcess {

	private int 				p_C_Element_ID = 0;
	private String				p_Name = null;
	private int					p_C_AcctSchema_ID = 0;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		ProcessInfoParameter[] params = getParameter();
		for(ProcessInfoParameter param : params){
			String name = param.getParameterName();			
			if(name.equalsIgnoreCase("C_Element_ID"))
				p_C_Element_ID = param.getParameterAsInt();
			else if(name.equalsIgnoreCase("C_AcctSchema_ID"))
				p_C_AcctSchema_ID = param.getParameterAsInt();
			else if(name.equalsIgnoreCase("Name"))
				p_Name = (String) param.getParameter();

			else
				System.out.println("Param " + name + " not found...");
			}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// Check if name is exists
		boolean isExists = false;
		String checkExists = "SELECT 1 FROM PA_ReportLineSet " +
				"WHERE Name = ? AND AD_Client_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(checkExists, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setString(1, p_Name);
			pstmt.setInt(2, p_C_Element_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				isExists = true;
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(isExists)
		{
			return "Report Line Set Already Exists";
		}
		
		// Create Report Line Set
		MReportLineSet rls = new MReportLineSet(getCtx(), 0, get_Trx());
		rls.setName(p_Name);
		rls.setC_AcctSchema_ID(p_C_AcctSchema_ID);
		rls.save();		
		
		// Get Element Value
		String ev = "SELECT * FROM C_ElementValue  " +
				"WHERE C_Element_ID = ? " +
				"AND IsSummary = 'N' AND PostActual = 'Y' " +
				"ORDER BY Value ";
		
		pstmt = DB.prepareStatement(ev, get_Trx());
		rs = null;
		try{
			pstmt.setInt(1, p_C_Element_ID);
			rs = pstmt.executeQuery();
			int seq = 0;
			while(rs.next())
			{
				seq = seq + 10;
				MElementValue evo = new MElementValue(getCtx(), rs, get_Trx());
				// Create Report Line
				MReportLine rl = new MReportLine(getCtx(), 0, get_Trx());
				rl.setPA_ReportLineSet_ID(rls.getPA_ReportLineSet_ID());
				rl.setName(evo.getValue());
				rl.setDescription(evo.getName());
				rl.setSeqNo(seq);
				rl.setIsPrinted(true);
				rl.setLineType(X_PA_ReportLine.LINETYPE_SegmentValue);
				rl.save();
				
				// Create report line source
				MReportSource s = new MReportSource(getCtx(), 0, get_Trx());
				s.setPA_ReportLine_ID(rl.getPA_ReportLine_ID());
				s.setElementType(X_PA_ReportSource.ELEMENTTYPE_Account);
				s.setC_ElementValue_ID(evo.getC_ElementValue_ID());
				s.save();
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return "Line Set Complete";
	}

}
