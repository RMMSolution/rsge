/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import rsge.model.MBudgetFormTemplate;

/**
 * @author FANNY
 *
 */
public class ValidateTemplate extends SvrProcess {

	/**************************************************************************
	 * Standard Constructor
	 * 
	 */
	public ValidateTemplate() {
		// TODO Auto-generated constructor stub
	}

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
		// Check lines
		String sql = "SELECT 1 FROM XX_BudgetFormTemplateLine " +
				"WHERE XX_BudgetFormTemplate_ID = " + getRecord_ID();
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			if(!rs.next())
			{				
				return Msg.getMsg(getCtx(), "NoLineFound"); // No Lines found
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		// Validate record
		MBudgetFormTemplate template = new MBudgetFormTemplate(getCtx(), getRecord_ID(), get_Trx());
		template.setIsValid(true);
		template.save();
		return "";
	}

}
