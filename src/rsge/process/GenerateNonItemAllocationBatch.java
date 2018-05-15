package rsge.process;

import org.compiere.process.SvrProcess;

import rsge.model.MInOut;

public class GenerateNonItemAllocationBatch extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		int no = MInOut.generateNonItemAllocation(getCtx(), 0, get_Trx());
		return "Generated document " + no;
	}

}
