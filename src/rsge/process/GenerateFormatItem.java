/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.MColumn;
import org.compiere.model.X_AD_Element;
import org.compiere.model.X_AD_PrintFormatItem;
import org.compiere.print.MPrintFormat;
import org.compiere.print.MPrintFormatItem;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * @author Fanny
 *
 */
public class GenerateFormatItem extends SvrProcess {

	MPrintFormat pf = null;
	ArrayList<MColumn> columns = new ArrayList<>();
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		pf = new MPrintFormat(getCtx(), getRecord_ID(), get_Trx());
		// Get table columns
		String sql = "SELECT col.AD_Column_ID FROM AD_Column col " +
				"WHERE col.AD_Column_ID NOT IN (SELECT pfi.AD_Column_ID " +
				"FROM AD_PrintFormatItem pfi " +
				"INNER JOIN AD_PrintFormat pf ON (pf.AD_PrintFormat_ID = pfi.AD_PrintFormat_ID) " +
				"WHERE pf.AD_Table_ID = col.AD_Table_ID)" +
				"AND col.AD_Table_ID = " + pf.getAD_Table_ID();
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt = DB.prepareStatement(sql, get_Trx());
			rs = pstmt.executeQuery();
			while(rs.next())
			{				
				MColumn column = new MColumn(getCtx(), rs.getInt(1), get_Trx());
				columns.add(column);
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
		// If no list found, cancel process
		if(columns.size()>0)
		{
			int seqNo = 10;
			for(int i=0; i<columns.size(); i++)
			{
				MColumn col = columns.get(i);				
				MPrintFormatItem pfi = new MPrintFormatItem(getCtx(), 0, get_Trx());
				pfi.setAD_Client_ID(pf.getAD_Client_ID());
				pfi.setAD_Org_ID(pf.getAD_Org_ID());
				pfi.setAD_PrintFormat_ID(pf.getAD_PrintFormat_ID());
				
				System.out.println("Name " + col.getName());
				pfi.setName(col.getName());
				pfi.setPrintName(col.getName());
				pfi.setPrintFormatType(X_AD_PrintFormatItem.PRINTFORMATTYPE_Field);
				pfi.setAD_Column_ID(col.getAD_Column_ID());
				System.out.println("AD_Column_ID " + col.getAD_Column_ID());
				pfi.setFieldAlignmentType(X_AD_PrintFormatItem.FIELDALIGNMENTTYPE_LeadingLeft);
				pfi.setMaxHeight(0);
				pfi.setMaxWidth(0);
				
				// If column is AD_Client_ID or AD_Org_ID, dont't display it
				if(col.getColumnName().equals("AD_Client_ID"))
					pfi.setSeqNo(5);
				else if(col.getColumnName().equals("AD_Org_ID"))
					pfi.setSeqNo(6);
				else
				{
					pfi.setSeqNo(seqNo);
					seqNo = seqNo + 10;
				}
				
				pfi.save();
			}
		}
		return null;
	}

}
