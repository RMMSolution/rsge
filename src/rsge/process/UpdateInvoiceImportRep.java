/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.model.X_I_Invoice;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * @author Fanny
 *
 */
public class UpdateInvoiceImportRep extends SvrProcess {

	private int						p_AD_Org_ID = 0;
	private int 					p_AD_User_ID = 0;
	
	private ArrayList<String> 		orgList = null;
	
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
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = element.getParameterAsInt();
			else if (name.equals("AD_User_ID"))
				p_AD_User_ID = element.getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

		// TODO Auto-generated method stub		
		StringBuilder sql = new StringBuilder("SELECT Value FROM AD_Org WHERE AD_Client_ID = ? ");
		if(p_AD_Org_ID!=0)
		{
			sql.append("AND AD_Org_ID = ");
			sql.append(p_AD_Org_ID);
		}
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				orgList.add(rs.getString(1));
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
		// 
		for(String orgValue : orgList)
		{
			String sql = "SELECT * FROM I_Invoice WHERE I_IsImported = 'N' " +
				"AND AD_Client_ID = ? " +
				"AND OrgValue = ? "; 
			
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
			ResultSet rs = null;
			try{
				pstmt.setInt(1, getAD_Client_ID());
				pstmt.setString(2, orgValue);
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					X_I_Invoice inv = new X_I_Invoice(getCtx(), rs, get_Trx());
					inv.setSalesRep_ID(p_AD_User_ID);
					inv.save();
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
		return "Update Complete";
	}
}
