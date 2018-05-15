/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MBudgetFormTemplate;

/**
 * @author bang
 *
 */
public class ValidTemplateAll extends SvrProcess {

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT XX_BudgetFormTemplate_ID FROM XX_BudgetFormTemplate WHERE IsValid = 'N' AND AD_Client_ID = "+getAD_Client_ID();
		PreparedStatement ps = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			while(rs.next()){
				// Check lines
				String sql1 = "SELECT 1 FROM XX_BudgetFormTemplateLine " +
						"WHERE XX_BudgetFormTemplate_ID = " + rs.getInt(1);
				
				PreparedStatement pstmt = DB.prepareStatement(sql1, get_Trx());
				ResultSet rs1 = null;
				
				try{
					rs1 = pstmt.executeQuery();
					if(!rs1.next())
					{				
						continue;
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				finally
				{
					DB.closeResultSet(rs1);
					DB.closeStatement(pstmt);
				}
				
				// Validate record
				MBudgetFormTemplate template = new MBudgetFormTemplate(getCtx(), rs.getInt(1), get_Trx());
				template.setIsValid(true);
				template.save();
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "Complete";
	}

}
