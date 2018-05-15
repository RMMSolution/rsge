/**
 * 
 */
package rsge.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

import org.compiere.model.MConversionRate;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import rsge.model.MPayment;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author Fanny
 *
 */
public class EnterNewRate extends SvrProcess {

	private int						p_conversionType_ID = 0;
	private BigDecimal				p_Rate = BigDecimal.ZERO;
	private	MPayment				payment = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		payment = new MPayment(getCtx(), getRecord_ID(), get_Trx());
		
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null && element.getParameter_To() == null)
				;
			else if (name.equals("C_ConversionType_ID"))
				p_conversionType_ID = element.getParameterAsInt();
			else if (name.equals("Rate"))
				p_Rate = (BigDecimal)element.getParameter();
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		if(p_Rate.compareTo(BigDecimal.ZERO)==0)
			return "No rate entered. Process canceled";
		
		System.out.println(p_Rate);
		System.out.println(payment.getRate());
		if(p_Rate.compareTo(payment.getRate())==0)
		{
			return "New rate has same value as old rate. Process canceled";
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(payment.getDateTrx());
		cal = GeneralEnhancementUtils.resetDate(cal);
		Timestamp validDate = new Timestamp(cal.getTimeInMillis());
		MConversionRate cr = new MConversionRate(payment, p_conversionType_ID, payment.getC_Currency_ID(), 
				payment.getC_Currency_Bank_ID(), p_Rate, validDate);
		cr.save();
		if(p_conversionType_ID!=payment.getC_ConversionType_ID())
			payment.setC_ConversionType_ID(p_conversionType_ID);
		payment.setRate(p_Rate);
		
		return "Rate Updated";
	}

}
