/**
 * 
 */
package rsge.callout;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.model.MInvoice;
import rsge.model.MOrder;

/**
 * @author bang
 *
 */
public class CalloutPaymentOrder extends CalloutEngine {

	public String setInvoice(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer User_ID = (Integer)value;
		if(User_ID == null || User_ID.intValue() == 0)
			return "";
		
		MInvoice invoice = new MInvoice(ctx, User_ID, (Trx)null);
		mTab.setValue("C_Currency_ID", invoice.getC_Currency_ID());
		mTab.setValue("PayAmt", invoice.getGrandTotal());
		mTab.setValue("C_BPartner_ID", invoice.getC_BPartner_ID());
		
		return "";
	}
	
	public String setOrder(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer User_ID = (Integer)value;
		if(User_ID == null || User_ID.intValue() == 0)
			return "";

		MOrder order = new MOrder(ctx, User_ID, (Trx)null);
		mTab.setValue("C_Currency_ID", order.getC_Currency_ID());
		mTab.setValue("PayAmt", order.getGrandTotal());
		mTab.setValue("C_BPartner_ID", order.getC_BPartner_ID());
		
		return "";
	}


}
