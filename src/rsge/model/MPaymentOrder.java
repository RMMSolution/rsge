/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.api.UICallout;
import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.po.X_XX_PaymentOrder;

/**
 * @author Fanny
 *
 */
public class MPaymentOrder extends X_XX_PaymentOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_PaymentOrder_ID
	 * @param trx
	 */
	public MPaymentOrder(Ctx ctx, int XX_PaymentOrder_ID, Trx trx) {
		super(ctx, XX_PaymentOrder_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MPaymentOrder(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		if(getC_Invoice_ID() > 0){
			MInvoice invoice = new MInvoice(getCtx(), getC_Invoice_ID(), get_Trx());
			setC_Currency_ID(invoice.getC_Currency_ID());
			setPayAmt(invoice.getGrandTotal());
			setC_BPartner_ID(invoice.getC_BPartner_ID());
		}
		
		if(getC_Order_ID() > 0){
			MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_Trx());
			setC_Currency_ID(order.getC_Currency_ID());
			setPayAmt(order.getGrandTotal());
			setC_BPartner_ID(order.getC_BPartner_ID());
		}
		return true;
	}
	
	
	@UICallout public void setC_Invoice_ID(String oldC_Invoice_ID, String newC_Invoice_ID, int windowNo) throws Exception
	{		
		if((newC_Invoice_ID == null) || (newC_Invoice_ID.length() == 0))
			return;		
		MInvoice invoice = new MInvoice(getCtx(), getC_Invoice_ID(), get_Trx());
		setC_Currency_ID(invoice.getC_Currency_ID());
		setPayAmt(invoice.getGrandTotal());
		setC_BPartner_ID(invoice.getC_BPartner_ID());
	}
	
	@UICallout public void setC_Order_ID(String oldC_Order_ID, String newC_Invoice_ID, int windowNo) throws Exception
	{		
		if((newC_Invoice_ID == null) || (newC_Invoice_ID.length() == 0))
			return;		
		MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_Trx());
		setC_Currency_ID(order.getC_Currency_ID());
		setPayAmt(order.getGrandTotal());
		setC_BPartner_ID(order.getC_BPartner_ID());
	}

}
