package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import rsge.model.MSystemClient;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class UpdateGroupTenant extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		int no = 0;
		String sql = "SELECT AD_Client_ID, Name FROM AD_Client " +
				"WHERE AD_Client_ID NOT IN (0, 11) ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MSystemClient.update(getCtx(), rs.getInt(1), rs.getString(2), get_Trx());
				no++;
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return "Updated record(s) : " + no;
	}

}
