/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * @author Fanny
 *
 */
public class GenReportLineSetSeq extends SvrProcess {

	private ArrayList<Integer> 			importLine = new ArrayList<>();
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Get Data
		String sql = "SELECT I_ReportLine_ID FROM I_ReportLine " +
				"WHERE AD_Client_ID = ? " +
				"AND Processed = 'N' " +
				"ORDER BY I_ReportLine_ID ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				importLine.add(rs.getInt(1));
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
		// UPDATE SEQUENCE
		int no = 10;
		for(Integer i : importLine)
		{			
			String update = "UPDATE I_ReportLine SET SeqNo = ? WHERE I_ReportLine_ID = ? ";
			int n = DB.executeUpdate(get_Trx(), update, no, i);
			if(n>=0)
				no = no+10;
		}
		return "UPDATE SEQUENCE COMPLETE";
	}

}
