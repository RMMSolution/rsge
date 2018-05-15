/**
 * 
 */
package rsge.callout;

import java.math.BigDecimal;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MConversionRate;
import org.compiere.model.MOrder;
import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.model.MDownPayment;

/**
 * @author Fanny
 *
 */
public class CalloutDownPayment extends CalloutEngine {
	
	/**
	 * Set Order info
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */	
	public String setOrder(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_Order_ID = (Integer)value;
		if(C_Order_ID == null || C_Order_ID.intValue() == 0)
			return "";
		MOrder order = new MOrder(ctx, C_Order_ID, (Trx)null);
		
		mTab.setValue("C_BPartner_ID", order.getC_BPartner_ID());
		
		if(order.isSOTrx())
			mTab.setValue("IsSOTrx", "Y");
		else
			mTab.setValue("IsSOTrx", "N");
		
		mTab.setValue("C_Currency_ID", order.getC_Currency_ID());
		mTab.setValue("TotalAmt", order.getTotalLines());
		mTab.setValue("GrandTotal", order.getGrandTotal());
		
		return "";
	}
	
	public String setCurrency(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer currencyID = (Integer)value;
		if(currencyID == null || currencyID.intValue() == 0)
			return "";
		
		MDownPayment dp = new MDownPayment(ctx, (Integer) mTab.getValue("XX_DownPayment_ID"), (Trx)null);
		if(dp.getC_Currency_ID()!=(Integer)mTab.getValue("C_Currency_ID"))
		{
			mTab.setValue("IsDifferentCurrency", "Y");
		}
		else
		{
			mTab.setValue("IsDifferentCurrency", "N");
		}
		BigDecimal rate = MConversionRate.getRate(dp.getC_Currency_ID(), (Integer)mTab.getValue("C_Currency_ID"), dp.getDateTrx(), 
				(Integer)mTab.getValue("C_ConversionType_ID"), (Integer)mTab.getValue("AD_Client_ID"), (Integer)mTab.getValue("AD_Org_ID"));
		if(rate == null)
			rate = BigDecimal.ZERO;
		
		mTab.setValue("Rate", rate);
		return "";
	}
		
}
