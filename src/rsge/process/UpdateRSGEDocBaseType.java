package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MDocBaseType;

public class UpdateRSGEDocBaseType extends SvrProcess {

	private String				p_action = null;
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null && element.getParameter_To() == null)
				;
			else if (name.equals("Action"))
				p_action = (String)element.getParameter();		
		}
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM C_DocBaseType WHERE DocBaseType IN ('ARI') ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MDocBaseType dbt = new MDocBaseType(getCtx(), rs, get_Trx());
				String classname = null;
				if(dbt.getDocBaseType().equals(org.compiere.model.MDocBaseType.DOCBASETYPE_ARInvoice))
				{
					if(p_action.equalsIgnoreCase("1"))
						classname = "rsge.acct.Doc_Invoice";
					else if(p_action.equalsIgnoreCase("2"))
						classname = "org.compiere.acct.Doc_Invoice";
				}
				
				dbt.setAccountingClassname(classname);
				dbt.save();
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "Process Complete";
	}

}
