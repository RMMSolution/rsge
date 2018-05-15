/**
 * 
 */
package rsge.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MInvoice;
import org.compiere.process.DocumentEngine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.vos.DocActionConstants;

/**
 * @author FANNY R
 *
 */
public class CheckDownPayment extends SvrProcess {

	/**
	 * 
	 */
	public CheckDownPayment() {
		// TODO Auto-generated constructor stub
	}

	/** Invoice 									*/
	private MInvoice 						invoice = null;
	
	/** Order ID									*/
	private int								C_Order_ID = 0;
	/** Payment Currency							*/
	private int								paymentCurrency_ID = 0;
	
	/** Business Partner ID							*/
	private int								C_BPartner_ID = 0;
	
	/** Allocation Document Action	*/
	private String		p_docAction = DocActionConstants.ACTION_Complete;
	/** Return Msg									*/
	private String							returnMsg = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		invoice = new MInvoice(getCtx(), getRecord_ID(), get_Trx());
		
		C_Order_ID = invoice.getC_Order_ID();
//		trxCurrency_ID = invoice.getC_Currency_ID();	
		C_BPartner_ID = invoice.getC_BPartner_ID();
		
		// 
		String sql = "UPDATE XX_DownPaymentPlan dpp " +
				"SET dpp.IsCancelled = 'Y' " +
				"WHERE dpp.XX_DownPayment_ID IN (" +
				"SELECT XX_DownPayment_ID " +
				"FROM XX_DownPayment " +
				"WHERE C_Order_ID = ?) " +
				"AND (dpp.C_Order_ID IS NULL OR dpp.C_Payment_ID = 0) ";
		
		DB.executeUpdate(get_Trx(), sql, C_Order_ID);
		
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		return null;
	}
	
	private String getMessage(String processMsg)
	{
		String msg = null;
		msg = Msg.getMsg(getCtx(), processMsg);
		return msg;
	}

	private Timestamp getSystemDate()
	{
		return new Timestamp (System.currentTimeMillis());		
	}
}
