package rsge.process;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class GenerateBudgetPeriodReport extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

		resetTable();
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void resetTable()
	{
		String reset = "DELETE T_BudgetPeriodReport WHERE AD_Client_ID = ? ";
		DB.executeUpdate(get_Trx(), reset, getAD_Client_ID());		
	}

}
