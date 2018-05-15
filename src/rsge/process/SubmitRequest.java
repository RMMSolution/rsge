/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import rsge.model.MInternalUseRequest;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;


/**
 * @author FANNY
 *
 */
public class SubmitRequest extends SvrProcess {

	MInternalUseRequest request = null;
	
	@Override
	protected void prepare() {
		// Get Request Object
		request = new MInternalUseRequest(getCtx(), getRecord_ID(), get_Trx());
	}

	@Override
	protected String doIt() throws Exception {
		// Check if request has line(s)
		String sql = "SELECT 1 " +
				"FROM XX_InternalUseRequestLine " +
				"WHERE XX_InternalUseRequest_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, request.getXX_InternalUseRequest_ID());
			rs = pstmt.executeQuery();
			if(!rs.next())
			{
				// No Lines Found. Cancel Process
				return Msg.getMsg(getCtx(), "NoLines");
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setIsRequestSubmitted(true);
		request.save(get_Trx());
		return Msg.getMsg(getCtx(), "RequestSubmitted");
	}
}
