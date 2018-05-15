/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.process.DocumentEngine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MBudgetForm;
import rsge.po.X_XX_BudgetForm;

/**
 * @author bang
 *
 */
public class CompleteBudgetFormAll extends SvrProcess {

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
		String sql = "SELECT * FROM XX_BudgetForm WHERE AD_Client_ID = "+getAD_Client_ID()+" AND DocStatus NOT IN ('CO','CL')";
		PreparedStatement ps = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			while(rs.next()){
				MBudgetForm form = new MBudgetForm(getCtx(), rs, get_Trx());
				form.setDocAction(X_XX_BudgetForm.DOCACTION_Complete);
				if(form.save())
				{
					DocumentEngine.processIt(form, form.getDocAction());
					form.save();
				}
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
