/**
 * 
 */
package rsge.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MConversionRate;
import org.compiere.model.MPayment;
import org.compiere.util.Ctx;
import org.compiere.util.Trx;

/**
 * @author FANNY R
 *
 */
public class CreateDocument {


	public static int createPayment(Ctx ctx, int AD_Client_ID, int AD_Org_ID, int C_BankAccount_ID, boolean IsSoTrx, Timestamp DateTrx, Timestamp DateAcct, boolean IsPrepayment, int C_BPartner_ID, 
			int C_Order_ID, int C_Invoice_ID, int C_Charge_ID, int C_Campaign_ID, String Description, int C_Currency_ID, int TrxCurrency_ID, 
			BigDecimal TrxAmt, BigDecimal DiscountAmt, BigDecimal WriteOffAmt, boolean IsOverUnderPayment, BigDecimal OverUnderAmt, int C_ConversionType_ID, String TenderType, Trx trx)
	{
		MPayment payment = new MPayment(ctx, 0, trx);
		payment.setC_BankAccount_ID(C_BankAccount_ID);
		payment.setC_DocType_ID(IsSoTrx);
		payment.setIsReceipt(IsSoTrx);
		payment.setDateTrx(DateTrx);
		payment.setDateAcct(DateAcct);
		payment.setIsPrepayment(IsPrepayment);
		payment.setC_Order_ID(C_Order_ID);
		payment.setC_BPartner_ID(C_BPartner_ID);
		payment.setDescription(Description);	
		
		// Set Currency and Amount
		payment.setC_Currency_ID(C_Currency_ID);
		
		// Get Conversion Type of Order
		payment.setC_ConversionType_ID(C_ConversionType_ID);
		
		if(TrxCurrency_ID != C_Currency_ID)
		{
			BigDecimal payAmt = MConversionRate.convert(ctx, TrxAmt, 
					TrxCurrency_ID, C_Currency_ID, DateTrx, C_ConversionType_ID, AD_Client_ID, AD_Org_ID);
			payment.setPayAmt(payAmt);					
		}
		else
		{
			payment.setPayAmt(TrxAmt);
		}
		
		if(DiscountAmt != null)
		{
			payment.setDiscountAmt(DiscountAmt);			
		}
		
		if(WriteOffAmt != null)
		{
			payment.setWriteOffAmt(WriteOffAmt);			
		}
		
		payment.setIsOverUnderPayment(IsOverUnderPayment);
		
		if(OverUnderAmt != null)
		{
			payment.setOverUnderAmt(OverUnderAmt);
		}
	
		payment.setC_Currency_ID(C_Currency_ID);
		
		// Set Document Detail
		payment.setTenderType(TenderType);				
		
		int payment_ID = 0;
		
		if(payment.save()) 
		{
			payment_ID = payment.getC_Payment_ID();
		}
		return payment_ID;
	}
}