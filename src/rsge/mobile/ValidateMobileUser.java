package rsge.mobile;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import rsge.model.MMobileAppsUser;

public class ValidateMobileUser extends SvrProcess {

	private String 					mobileUser = null;
	private String					password = null;
	private String					mobileApps = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Payroll Parameter
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null && element.getParameter_To() == null)
				;
			else if (name.equals("MobileUserName"))
				mobileUser = (String)element.getParameter();			
			else if (name.equals("Password"))
				password = (String)element.getParameter();			
			else if (name.equals("MobileAppsID"))
				mobileApps = (String)element.getParameter();			
		}
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		return MMobileAppsUser.validateLogin(getCtx(), mobileUser, password, mobileApps, get_Trx());
	}

}
