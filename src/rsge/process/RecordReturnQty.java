/**
 * 
 */
package rsge.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import rsge.model.MQuickReturnLine;
import rsge.model.MQuickReturnLineBatch;

/**
 * @author Fanny
 *
 */
public class RecordReturnQty extends SvrProcess {

	private BigDecimal				p_Qty = BigDecimal.ZERO;
	private int						p_C_UOM_ID = 0;
	private String					p_batchID = null;
	private Timestamp				p_GuaranteeDate = null;
	
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
			else if (name.equals("Qty"))
				p_Qty = (BigDecimal) element.getParameter();			
			else if (name.equals("C_UOM_ID"))
				p_C_UOM_ID = element.getParameterAsInt();	
			else if (name.equals("BatchID"))
				p_batchID = (String) element.getParameter();			
			else if (name.equals("GuaranteeDate"))
				p_GuaranteeDate = (Timestamp) element.getParameter();			
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// Record
		String msg = null;
		MQuickReturnLine line = new MQuickReturnLine(getCtx(), getRecord_ID(), get_Trx());
		msg = MQuickReturnLineBatch.insertProductBatch(getCtx(), line, p_batchID, p_Qty, p_C_UOM_ID, p_GuaranteeDate, get_Trx());
		if(msg==null)
			msg = "Record Saved";
		
		return msg;
	}

}
