/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.X_I_ReportLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * @author Fanny
 *
 */
public class UpdateReportLineSeq extends SvrProcess {

	private int							p_StartNo = 0;
	private int							p_Increment = 0;
	private ArrayList<X_I_ReportLine>	p_RlList = new ArrayList<X_I_ReportLine>();
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null && element.getParameter_To() == null)
				;
			else if (name.equals("StartNo"))
				p_StartNo = element.getParameterAsInt();		
			else if (name.equals("Increment"))
				p_Increment = element.getParameterAsInt();			
		}
		
		String sql = "SELECT * FROM I_ReportLine " +
				"WHERE I_IsImported = 'N' " +
				"AND AD_Client_ID = ? " +
				"ORDER BY I_ReportLine_ID ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				X_I_ReportLine rl = new X_I_ReportLine(getCtx(), rs, get_Trx());
				p_RlList.add(rl);
			}
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

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {		
		for(X_I_ReportLine rl : p_RlList)
		{
			rl.setSeqNo(p_StartNo);
			rl.save();
			p_StartNo = p_StartNo + p_Increment;
		}
		return "Update Complete";
	}

}
