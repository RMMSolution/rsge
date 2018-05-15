/**
 * 
 */
package rsge.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

import org.compiere.model.MElementValue;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;

import rsge.model.MProduct;
import rsge.utils.BudgetUtils;
import rsge.utils.CheckBudget;

/**
 * @author FANNY R
 *
 */
public class CheckAccountBudget extends SvrProcess {

	/**
	 * 
	 */
	
	/** Initialize Variable						*/
	private int						Account_ID = 0;
	private Timestamp				DateTrx = null;

	/** AD_Org_ID							*/
	private int 					AD_Org_ID = 0;
	/** C_Activity_ID							*/
	private int 					C_Activity_ID = 0;
	/** C_Campaign_ID							*/
	private int 					C_Campaign_ID = 0;
	/** C_Project_ID							*/
	private int 					C_Project_ID = 0;
//	/** C_SalesRegionID							*/
//	private int 					C_SalesRegion_ID = 0;
	/** Check Amount only						*/
	private boolean					IsCheckAmtOnly = false;
	/** Product ID								*/
	private int						M_Product_ID = 0;
	
	/** UserElement1							*/
	private int						p_UserElement1_ID = 0;
	/** User Element 2							*/
	private int						p_UserElement2_ID = 0;
	
	
	/** Account Code							*/
	private String					accountCode = null;
	/** Account Name							*/
	private String					accountName = null;
	/** Currency ISO							*/
	private String					ISOCode = null;
	/** Remaining Budget						*/
	private BigDecimal				remainingBudget = null;	
	/** Remaining Qty						*/
	private BigDecimal				remainingBudgetQty = null;	
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		CheckBudget check = new CheckBudget(getCtx(), false, AD_Org_ID, Account_ID, DateTrx, false,BudgetUtils.getBudgetCalendar(getCtx(), getAD_Client_ID(), get_Trx()), 
				C_Activity_ID, 0, C_Campaign_ID, C_Project_ID, M_Product_ID, p_UserElement1_ID, p_UserElement2_ID, get_Trx());	
		check.runBudgetCheck();
		if(M_Product_ID!=0)
		{
			MProduct p = new MProduct(getCtx(), M_Product_ID, get_Trx());
			accountName = p.getName();			
		}
		else
		{
			MElementValue ev = new MElementValue(getCtx(), Account_ID, get_Trx());
			accountCode = ev.getValue();
			accountName = ev.getName();			
		}
		ISOCode = check.getCurrencyISOCode();
		remainingBudget = check.getRemainingBudget();
		remainingBudgetQty = check.getRemainingBudgetQty();
		return getMessage();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {		
		// Get Parameter
		ProcessInfoParameter params[] = getParameter();
		for(ProcessInfoParameter param : params){
			String name = param.getParameterName();
			if(name.equals("AD_Org_ID"))
				AD_Org_ID = param.getParameterAsInt();			
			else if(name.equals("Account_ID"))
				Account_ID = param.getParameterAsInt();
			else if(name.equals("DateTrx"))
				DateTrx = (Timestamp) param.getParameter();
			else if(name.equals("C_Activity_ID"))
				C_Activity_ID = param.getParameterAsInt();
			else if(name.equals("C_Campaign_ID"))
				C_Campaign_ID = param.getParameterAsInt();
			else if(name.equals("C_Project_ID"))
				C_Project_ID = param.getParameterAsInt();
//			else if(name.equals("C_SalesRegion_ID"))
//				C_SalesRegion_ID = param.getParameterAsInt();
			else if(name.equals("M_Product_ID"))
				M_Product_ID = param.getParameterAsInt();
			else if(name.equals("UserElement1_ID"))
				p_UserElement1_ID = param.getParameterAsInt();
			else if(name.equals("UserElement2_ID"))
				p_UserElement2_ID = param.getParameterAsInt();				
			else if(name.equals("IsCheckAmtOnly"))
				IsCheckAmtOnly = "Y".equals(param.getParameter());

		}
	}// End Prepare It

	private String getMessage()
	{
		StringBuilder msg = new StringBuilder();
		if(M_Product_ID!=0)
		{
			msg.append(Msg.getMsg(getCtx(), "RemainingProductBudget"));
			msg.append(accountName +" ");
			
		}
		else
		{
			msg.append(Msg.getMsg(getCtx(), "RemainingAccountBudget"));
			msg.append(accountCode).append("-").append(accountName +" ");
			
		}
		String remainBudget = "-";
		if(remainingBudget!=null)
			remainBudget = convertAmount(remainingBudget);
		msg.append(ISOCode).append(" ").append(remainBudget);
		String remainQty = "-";
		if(remainingBudgetQty!=null)
			remainQty = convertAmount(remainingBudgetQty);
		msg.append(" - Product Qty : ").append(remainQty);		

//		if(M_Product_ID!=0 && !IsCheckAmtOnly)
//			msg.append(" - Product Qty : ").append(convertAmount(remainingBudgetQty));		
		// Remaining Budget of this account dated xxx is xxx		
		return msg.toString();
	}
	
	public static String convertAmount(BigDecimal amount)
	{		
		amount = amount.setScale(2, RoundingMode.HALF_EVEN);
		String amtInString = amount.toString();
		int numberOfChar = amtInString.length();

		int separator = 0;
		String selectedChar = null;

		int digitFocused = numberOfChar;
		String amt = null;
		
		for(int x = 0; x < numberOfChar; x++)
		{			
			separator = separator + 1;
			selectedChar = amtInString.substring(digitFocused-1, digitFocused);
			
			digitFocused = digitFocused-1;
			
			if(amt != null && separator == 1)
			{
				amt = selectedChar +"," + amt;
			}
			else if(amt != null )
			{
				amt = selectedChar + amt;
			}
			else amt = selectedChar;

			if(selectedChar.equals("."))
			{
				separator = -3;
			}			
			else if(separator == 3)
			{
				separator = 0;
			}
		}
		return amt;
	}
}
