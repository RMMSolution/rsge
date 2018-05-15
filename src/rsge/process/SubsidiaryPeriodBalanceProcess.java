package rsge.process;

import rsge.model.MSubsidiaryPeriodBalance;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class SubsidiaryPeriodBalanceProcess extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		String update = "UPDATE XX_PeriodAccountBalance " +
				"SET Processed = 'Y' WHERE XX_SubsidiaryPeriodBalance_ID = ? ";
		int no = DB.executeUpdate(get_Trx(), update, getRecord_ID());
		if(no>-1)
		{
			MSubsidiaryPeriodBalance spb = new MSubsidiaryPeriodBalance(getCtx(), getRecord_ID(), get_Trx());
			spb.setProcessed(true);
			spb.save();
		}
		return "Process Complete";
	}

}
