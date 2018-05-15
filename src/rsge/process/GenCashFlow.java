package rsge.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.compiere.model.MElementValue;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.model.MCashFlowElement;
import rsge.po.X_XX_CashFlowSection;

public class GenCashFlow extends SvrProcess {

	private int				p_C_Year_ID = 0;
	private int				p_C_Period_ID = 0;
	private String			p_CashFlowMethod = null;
	
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
			else if (name.equals("C_Year_ID"))
				p_C_Year_ID = element.getParameterAsInt();
			else if (name.equals("C_Period_ID"))
				p_C_Period_ID = element.getParameterAsInt();
			else if (name.equals("CashFlowMethod"))
				p_CashFlowMethod = (String)element.getParameter();
		}
	}

	@Override
	protected String doIt() throws Exception {
		generateStatementOfCashFlow(getCtx(), getAD_Client_ID(), p_C_Period_ID, p_C_Year_ID, p_CashFlowMethod, get_Trx());
		return "Report Generated";
	}
	
	public static boolean generateStatementOfCashFlow(Ctx ctx, int AD_Client_ID, int C_Period_ID, int C_Year_ID, String cashFlowMethod, Trx trx)
	{
		// Delete Table
		String delete = "DELETE T_StatementOfCashFlow " +
				"WHERE AD_Client_ID = ? ";
		DB.executeUpdate(trx, delete, AD_Client_ID);
		int line = 1;
		String sectionName = null;
		MCashFlowElement[] cfeList = null;
		
		MPeriod p = new MPeriod(ctx, C_Period_ID, trx);
		Timestamp monthEndDate = p.getEndDate();
		Timestamp monthStartDate = p.getStartDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(monthEndDate);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);
		Timestamp annualStartDate = new Timestamp(cal.getTimeInMillis());
		cal.add(Calendar.YEAR, -1);
		Timestamp prevAnnualStartDate = new Timestamp(cal.getTimeInMillis());
		
		cal.setTime(monthEndDate);
		cal.add(Calendar.YEAR, -1);
		Timestamp prevMonthEndDate = new Timestamp(cal.getTimeInMillis());
		cal.setTime(monthStartDate);
		cal.add(Calendar.YEAR, -1);
		Timestamp prevMonthStartDate = new Timestamp(cal.getTimeInMillis());
				
		// Operating Activities
		BigDecimal currentMonthActOp = BigDecimal.ZERO;  
		BigDecimal prevYearMonthActOp = BigDecimal.ZERO;
		BigDecimal ytdActOp = BigDecimal.ZERO; 
		BigDecimal prevYtdActOp = BigDecimal.ZERO;
		BigDecimal budgetAmtOp = BigDecimal.ZERO;
		BigDecimal ytdBudgetAmtOp = BigDecimal.ZERO;
		BigDecimal col_0 = BigDecimal.ZERO;
		BigDecimal col_1 = BigDecimal.ZERO;
		BigDecimal col_2 = BigDecimal.ZERO;
		BigDecimal col_3 = BigDecimal.ZERO;
		BigDecimal col_4 = BigDecimal.ZERO;
		

		// Insert section
		sectionName = getSectionName(AD_Client_ID, false, "01", trx);
		if(insertLine(AD_Client_ID, 0, line, sectionName,  C_Year_ID, C_Period_ID, cashFlowMethod, trx))
			line++;
		cfeList = getCashFlowElement(ctx, AD_Client_ID, "1", true, false, false, true, false, trx); // Receipt From Customer
		if(cfeList!=null)
		{
			for(MCashFlowElement cfe : cfeList)
			{		
				BigDecimal currentMonthAct = getReceiptAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), monthStartDate, monthEndDate, trx); 
				BigDecimal prevYearMonthAct = getReceiptAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), prevMonthStartDate, prevMonthEndDate, trx);
				BigDecimal ytdAct = getReceiptAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), annualStartDate, monthEndDate, trx); 
				BigDecimal prevYtdAct = getReceiptAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), prevAnnualStartDate, prevMonthEndDate, trx);
				col_0 = getReceiptAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), tgl("2016-01-01 00:00:00"), tgl("2016-12-31 00:00:00"), trx);
				col_1 = getReceiptAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), tgl("2017-01-01 00:00:00"), tgl("2017-12-31 00:00:00"), trx);
				col_2 = getReceiptAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), tgl("2018-01-01 00:00:00"), tgl("2018-12-31 00:00:00"), trx);
				col_3 = getReceiptAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), tgl("2019-01-01 00:00:00"), tgl("2019-12-31 00:00:00"), trx);
				col_4 = getReceiptAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), tgl("2020-01-01 00:00:00"), tgl("2020-12-31 00:00:00"), trx);

				BigDecimal budgetAmt = BigDecimal.ZERO;
				BigDecimal ytdBudgetAmt = BigDecimal.ZERO;
				BigDecimal growth = getGrowthPercentage(currentMonthAct, prevYearMonthAct);
				BigDecimal growth2 = getGrowthPercentage(ytdAct, prevYtdAct);

				BigDecimal achievement = getBudgetAchievement(budgetAmt, currentMonthAct);
				BigDecimal achievement2 = getBudgetAchievement(ytdBudgetAmt, ytdAct);
				
				StringBuilder desc = new StringBuilder("  ");
				desc.append(cfe.getDescription());		
//				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
//						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod, trx));
				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,
						col_2,col_3,col_4, trx));
					line++;				
				
				currentMonthActOp = currentMonthActOp.add(currentMonthAct);  
				prevYearMonthActOp = prevYearMonthActOp.add(prevYearMonthAct);
				ytdActOp = ytdActOp.add(ytdAct); 
				prevYtdActOp = prevYtdActOp.add(prevYtdAct);
				budgetAmtOp = budgetAmtOp.add(budgetAmt);
				ytdBudgetAmtOp = ytdBudgetAmtOp.add(ytdBudgetAmt);

			}
		}
		
		cfeList = getCashFlowElement(ctx, AD_Client_ID, "1", false, true, false, false, false, trx); // Paid to Vendor
		if(cfeList!=null)
		{
			for(MCashFlowElement cfe : cfeList)
			{
				BigDecimal currentMonthAct = getPayAmount(AD_Client_ID, cfe.getVendorGroup_ID(), monthStartDate, monthEndDate, trx); 
				BigDecimal prevYearMonthAct = getPayAmount(AD_Client_ID, cfe.getVendorGroup_ID(), prevMonthStartDate, prevMonthEndDate, trx);
				BigDecimal ytdAct = getPayAmount(AD_Client_ID, cfe.getVendorGroup_ID(), annualStartDate, monthEndDate, trx); 
				BigDecimal prevYtdAct = getPayAmount(AD_Client_ID, cfe.getVendorGroup_ID(), prevAnnualStartDate, prevMonthEndDate, trx);
				col_0 = getPayAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), tgl("2016-01-01 00:00:00"), tgl("2016-12-31 00:00:00"), trx);
				col_1 = getPayAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), tgl("2017-01-01 00:00:00"), tgl("2017-12-31 00:00:00"), trx);
				col_2 = getPayAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), tgl("2018-01-01 00:00:00"), tgl("2018-12-31 00:00:00"), trx);
				col_3 = getPayAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), tgl("2019-01-01 00:00:00"), tgl("2019-12-31 00:00:00"), trx);
				col_4 = getPayAmount(AD_Client_ID, cfe.getC_BP_Group_ID(), tgl("2020-01-01 00:00:00"), tgl("2020-12-31 00:00:00"), trx);
				BigDecimal budgetAmt = BigDecimal.ZERO;
				BigDecimal ytdBudgetAmt = BigDecimal.ZERO;
				
				BigDecimal growth = getGrowthPercentage(currentMonthAct, prevYearMonthAct);
				BigDecimal growth2 = getGrowthPercentage(ytdAct, prevYtdAct);

				BigDecimal achievement = getBudgetAchievement(budgetAmt, currentMonthAct);
				BigDecimal achievement2 = getBudgetAchievement(ytdBudgetAmt, ytdAct);

				StringBuilder desc = new StringBuilder("  ");
				desc.append(cfe.getDescription());
//				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
//						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2,  C_Year_ID, C_Period_ID, cashFlowMethod, trx));
				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2,  C_Year_ID, C_Period_ID, cashFlowMethod,
						col_0,col_1,col_2,col_3,col_4,trx));
					line++;				

				currentMonthActOp = currentMonthActOp.subtract(currentMonthAct);  
				prevYearMonthActOp = prevYearMonthActOp.subtract(prevYearMonthAct);
				ytdActOp = ytdActOp.subtract(ytdAct); 
				prevYtdActOp = prevYtdActOp.subtract(prevYtdAct);
				budgetAmtOp = budgetAmtOp.subtract(budgetAmt);
				ytdBudgetAmtOp = ytdBudgetAmtOp.subtract(ytdBudgetAmt);
			}
		}
		
		cfeList = getCashFlowElement(ctx, AD_Client_ID, "1", false, false, true, false, false, trx); // Other operating expense
		if(cfeList!=null)
		{
			for(MCashFlowElement cfe : cfeList)
			{
				BigDecimal currentMonthAct = getNonChargeActivitiesAmt(AD_Client_ID, true, monthStartDate, monthEndDate, trx); 
				BigDecimal prevYearMonthAct = getNonChargeActivitiesAmt(AD_Client_ID, true, prevMonthStartDate, prevMonthEndDate, trx);
				BigDecimal ytdAct = getNonChargeActivitiesAmt(AD_Client_ID, true, annualStartDate, monthEndDate, trx); 
				BigDecimal prevYtdAct = getNonChargeActivitiesAmt(AD_Client_ID, true, prevAnnualStartDate, prevMonthEndDate, trx);
				col_0 = getNonChargeActivitiesAmt(AD_Client_ID, true, tgl("2016-01-01 00:00:00"), tgl("2016-12-31 00:00:00"), trx);
				col_1 = getNonChargeActivitiesAmt(AD_Client_ID, true, tgl("2017-01-01 00:00:00"), tgl("2017-12-31 00:00:00"), trx);
				col_2 = getNonChargeActivitiesAmt(AD_Client_ID, true, tgl("2018-01-01 00:00:00"), tgl("2018-12-31 00:00:00"), trx);
				col_3 = getNonChargeActivitiesAmt(AD_Client_ID, true, tgl("2019-01-01 00:00:00"), tgl("2019-12-31 00:00:00"), trx);
				col_4 = getNonChargeActivitiesAmt(AD_Client_ID, true, tgl("2020-01-01 00:00:00"), tgl("2020-12-31 00:00:00"), trx);
				BigDecimal budgetAmt = BigDecimal.ZERO;
				BigDecimal ytdBudgetAmt = BigDecimal.ZERO;
				
				BigDecimal growth = getGrowthPercentage(currentMonthAct, prevYearMonthAct);
				BigDecimal growth2 = getGrowthPercentage(ytdAct, prevYtdAct);

				BigDecimal achievement = getBudgetAchievement(budgetAmt, currentMonthAct);
				BigDecimal achievement2 = getBudgetAchievement(ytdBudgetAmt, ytdAct);

				StringBuilder desc = new StringBuilder("  ");
				desc.append(cfe.getDescription());
//				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
//						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
					line++;				
				currentMonthActOp = currentMonthActOp.add(currentMonthAct);  
				prevYearMonthActOp = prevYearMonthActOp.add(prevYearMonthAct);
				ytdActOp = ytdActOp.add(ytdAct); 
				prevYtdActOp = prevYtdActOp.add(prevYtdAct);
				budgetAmtOp = budgetAmtOp.add(budgetAmt);
				ytdBudgetAmtOp = ytdBudgetAmtOp.add(ytdBudgetAmt);
			}
		}
		
		cfeList = getCashFlowElement(ctx, AD_Client_ID, "1", false, false, false, false, false, trx); 
		if(cfeList!=null)
		{
			for(MCashFlowElement cfe : cfeList)
			{
				BigDecimal currentMonthAct = getNonChargeActivitiesAmt(AD_Client_ID, false, monthStartDate, monthEndDate, trx); 
				BigDecimal prevYearMonthAct = getNonChargeActivitiesAmt(AD_Client_ID, false, prevMonthStartDate, prevMonthEndDate, trx);
				BigDecimal ytdAct = getNonChargeActivitiesAmt(AD_Client_ID, false, annualStartDate, monthEndDate, trx); 
				BigDecimal prevYtdAct = getNonChargeActivitiesAmt(AD_Client_ID, false, prevAnnualStartDate, prevMonthEndDate, trx);
				col_0 = getNonChargeActivitiesAmt(AD_Client_ID, false, tgl("2016-01-01 00:00:00"), tgl("2016-12-31 00:00:00"), trx);
				col_1 = getNonChargeActivitiesAmt(AD_Client_ID, false, tgl("2017-01-01 00:00:00"), tgl("2017-12-31 00:00:00"), trx);
				col_2 = getNonChargeActivitiesAmt(AD_Client_ID, false, tgl("2018-01-01 00:00:00"), tgl("2018-12-31 00:00:00"), trx);
				col_3 = getNonChargeActivitiesAmt(AD_Client_ID, false, tgl("2019-01-01 00:00:00"), tgl("2019-12-31 00:00:00"), trx);
				col_4 = getNonChargeActivitiesAmt(AD_Client_ID, false, tgl("2020-01-01 00:00:00"), tgl("2020-12-31 00:00:00"), trx);
				BigDecimal budgetAmt = BigDecimal.ZERO;
				BigDecimal ytdBudgetAmt = BigDecimal.ZERO;
				
				BigDecimal growth = getGrowthPercentage(currentMonthAct, prevYearMonthAct);
				BigDecimal growth2 = getGrowthPercentage(ytdAct, prevYtdAct);

				BigDecimal achievement = getBudgetAchievement(budgetAmt, currentMonthAct);
				BigDecimal achievement2 = getBudgetAchievement(ytdBudgetAmt, ytdAct);

				StringBuilder desc = new StringBuilder("  ");
				desc.append(cfe.getDescription());
//				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
//						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
					line++;				
				currentMonthActOp = currentMonthActOp.add(currentMonthAct);  
				prevYearMonthActOp = prevYearMonthActOp.add(prevYearMonthAct);
				ytdActOp = ytdActOp.add(ytdAct); 
				prevYtdActOp = prevYtdActOp.add(prevYtdAct);
				budgetAmtOp = budgetAmtOp.add(budgetAmt);
				ytdBudgetAmtOp = ytdBudgetAmtOp.add(ytdBudgetAmt);

			}
		}

		// Insert Separator
		if(insertLine(AD_Client_ID, 0, line, null,  C_Year_ID, C_Period_ID, cashFlowMethod, trx))
			line++;

		// Cash Flow from Operation
		sectionName = getSectionName(AD_Client_ID, false, "02", trx);
		BigDecimal growthAcc = BigDecimal.ZERO;
		BigDecimal achievementAcc = BigDecimal.ZERO;
		BigDecimal growth2Acc = BigDecimal.ZERO;
		BigDecimal achievement2Acc = BigDecimal.ZERO;
		
//		if(insertLine(AD_Client_ID, 0, line, sectionName, currentMonthActOp, prevYearMonthActOp, growthAcc, budgetAmtOp, achievementAcc,  
//				ytdActOp, prevYtdActOp, growth2Acc, ytdBudgetAmtOp, achievement2Acc, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
		if(insertLine(AD_Client_ID, 0, line, sectionName, currentMonthActOp, prevYearMonthActOp, growthAcc, budgetAmtOp, achievementAcc,  
				ytdActOp, prevYtdActOp, growth2Acc, ytdBudgetAmtOp, achievement2Acc, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
			line++;				
		
		cfeList = getCashFlowElement(ctx, AD_Client_ID, "1", false, false, false, true, true, trx);
		if(cfeList!=null)
		{
			for(MCashFlowElement cfe : cfeList)
			{
				BigDecimal currentMonthAct = getNonChargeActivitiesAmt(AD_Client_ID, false, monthStartDate, monthEndDate, trx); 
				BigDecimal prevYearMonthAct = getNonChargeActivitiesAmt(AD_Client_ID, false, prevMonthStartDate, prevMonthEndDate, trx);
				BigDecimal ytdAct = getNonChargeActivitiesAmt(AD_Client_ID, false, annualStartDate, monthEndDate, trx); 
				BigDecimal prevYtdAct = getNonChargeActivitiesAmt(AD_Client_ID, false, prevAnnualStartDate, prevMonthEndDate, trx);
				col_0 = getNonChargeActivitiesAmt(AD_Client_ID, false, tgl("2016-01-01 00:00:00"), tgl("2016-12-31 00:00:00"), trx);
				col_1 = getNonChargeActivitiesAmt(AD_Client_ID, false, tgl("2017-01-01 00:00:00"), tgl("2017-12-31 00:00:00"), trx);
				col_2 = getNonChargeActivitiesAmt(AD_Client_ID, false, tgl("2018-01-01 00:00:00"), tgl("2018-12-31 00:00:00"), trx);
				col_3 = getNonChargeActivitiesAmt(AD_Client_ID, false, tgl("2019-01-01 00:00:00"), tgl("2019-12-31 00:00:00"), trx);
				col_4 = getNonChargeActivitiesAmt(AD_Client_ID, false, tgl("2020-01-01 00:00:00"), tgl("2020-12-31 00:00:00"), trx);
				BigDecimal budgetAmt = BigDecimal.ZERO;
				BigDecimal ytdBudgetAmt = BigDecimal.ZERO;
				
				BigDecimal growth = getGrowthPercentage(currentMonthAct, prevYearMonthAct);
				BigDecimal growth2 = getGrowthPercentage(ytdAct, prevYtdAct);

				BigDecimal achievement = getBudgetAchievement(budgetAmt, currentMonthAct);
				BigDecimal achievement2 = getBudgetAchievement(ytdBudgetAmt, ytdAct);

				StringBuilder desc = new StringBuilder("  ");
				desc.append(cfe.getDescription());
//				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
//						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
					line++;				
					
				currentMonthActOp = currentMonthActOp.add(currentMonthAct);  
				prevYearMonthActOp = prevYearMonthActOp.add(prevYearMonthAct);
				ytdActOp = ytdActOp.add(ytdAct); 
				prevYtdActOp = prevYtdActOp.add(prevYtdAct);
				budgetAmtOp = budgetAmtOp.add(budgetAmt);
				ytdBudgetAmtOp = ytdBudgetAmtOp.add(ytdBudgetAmt);
			}
		}
		
		// Cash flow from charge - Payment
		cfeList = getCashFlowElement(ctx, AD_Client_ID, "1", false, false, false, false, true, trx);
		if(cfeList!=null)
		{
			for(MCashFlowElement cfe : cfeList)
			{
				BigDecimal currentMonthAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), false, monthStartDate, monthEndDate, trx); 
				BigDecimal prevYearMonthAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), false, prevMonthStartDate, prevMonthEndDate, trx);
				BigDecimal ytdAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), false, annualStartDate, monthEndDate, trx); 
				BigDecimal prevYtdAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), false, prevAnnualStartDate, prevMonthEndDate, trx);
				col_0 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2016-01-01 00:00:00"), tgl("2016-12-31 00:00:00"), trx);
				col_1 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2017-01-01 00:00:00"), tgl("2017-12-31 00:00:00"), trx);
				col_2 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2018-01-01 00:00:00"), tgl("2018-12-31 00:00:00"), trx);
				col_3 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2019-01-01 00:00:00"), tgl("2019-12-31 00:00:00"), trx);
				col_4 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2020-01-01 00:00:00"), tgl("2020-12-31 00:00:00"), trx);
				BigDecimal budgetAmt = BigDecimal.ZERO;
				BigDecimal ytdBudgetAmt = BigDecimal.ZERO;
				
				BigDecimal growth = getGrowthPercentage(currentMonthAct, prevYearMonthAct);
				BigDecimal growth2 = getGrowthPercentage(ytdAct, prevYtdAct);

				BigDecimal achievement = getBudgetAchievement(budgetAmt, currentMonthAct);
				BigDecimal achievement2 = getBudgetAchievement(ytdBudgetAmt, ytdAct);

				
				StringBuilder desc = new StringBuilder("  ");
				desc.append(cfe.getDescription());
//				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
//						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
					line++;				
					
				currentMonthActOp = currentMonthActOp.subtract(currentMonthAct);  
				prevYearMonthActOp = prevYearMonthActOp.subtract(prevYearMonthAct);
				ytdActOp = ytdActOp.subtract(ytdAct); 
				prevYtdActOp = prevYtdActOp.subtract(prevYtdAct);
				budgetAmtOp = budgetAmtOp.subtract(budgetAmt);
				ytdBudgetAmtOp = ytdBudgetAmtOp.subtract(ytdBudgetAmt);

			}
		}
		
		// Cash flow from charge - Receipt

		sectionName = getSectionName(AD_Client_ID, true, "01", trx);
//		if(insertLine(AD_Client_ID, 0, line, sectionName, currentMonthActOp, prevYearMonthActOp, growthAcc, budgetAmtOp, achievementAcc,  
//				ytdActOp, prevYtdActOp, growth2Acc, ytdBudgetAmtOp, achievement2Acc, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
		if(insertLine(AD_Client_ID, 0, line, sectionName, currentMonthActOp, prevYearMonthActOp, growthAcc, budgetAmtOp, achievementAcc,  
				ytdActOp, prevYtdActOp, growth2Acc, ytdBudgetAmtOp, achievement2Acc, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
			line++;				
		// End Operating Activities
		
		
		// Investing Activities
		BigDecimal currentMonthActAct = BigDecimal.ZERO;  
		BigDecimal prevYearMonthActAct = BigDecimal.ZERO;
		BigDecimal ytdActAct = BigDecimal.ZERO; 
		BigDecimal prevYtdActAct = BigDecimal.ZERO;
		BigDecimal budgetAmtAct = BigDecimal.ZERO;
		BigDecimal ytdBudgetAmtAct = BigDecimal.ZERO;

		// Insert Separator
		if(insertLine(AD_Client_ID, 0, line, null,  C_Year_ID, C_Period_ID, cashFlowMethod, trx))
			line++;
		
		sectionName = getSectionName(AD_Client_ID, false, "03", trx);
		if(insertLine(AD_Client_ID, 0, line, sectionName, C_Year_ID, C_Period_ID, cashFlowMethod, trx))
			line++;
		
		cfeList = getCashFlowElement(ctx, AD_Client_ID, "2", false, false, false, true, true, trx);
		if(cfeList!=null)
		{
			for(MCashFlowElement cfe : cfeList)
			{
				BigDecimal currentMonthAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), true, monthStartDate, monthEndDate, trx); 
				BigDecimal prevYearMonthAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), true, prevMonthStartDate, prevMonthEndDate, trx);
				BigDecimal ytdAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), true, annualStartDate, monthEndDate, trx); 
				BigDecimal prevYtdAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), true, prevAnnualStartDate, prevMonthEndDate, trx);
				col_0 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), true, tgl("2016-01-01 00:00:00"), tgl("2016-12-31 00:00:00"), trx);
				col_1 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), true, tgl("2017-01-01 00:00:00"), tgl("2017-12-31 00:00:00"), trx);
				col_2 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), true, tgl("2018-01-01 00:00:00"), tgl("2018-12-31 00:00:00"), trx);
				col_3 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), true, tgl("2019-01-01 00:00:00"), tgl("2019-12-31 00:00:00"), trx);
				col_4 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), true, tgl("2020-01-01 00:00:00"), tgl("2020-12-31 00:00:00"), trx);
				BigDecimal budgetAmt = BigDecimal.ZERO;
				BigDecimal ytdBudgetAmt = BigDecimal.ZERO;
				
				BigDecimal growth = getGrowthPercentage(currentMonthAct, prevYearMonthAct);
				BigDecimal growth2 = getGrowthPercentage(ytdAct, prevYtdAct);

				BigDecimal achievement = getBudgetAchievement(budgetAmt, currentMonthAct);
				BigDecimal achievement2 = getBudgetAchievement(ytdBudgetAmt, ytdAct);

				
				StringBuilder desc = new StringBuilder("  ");
				desc.append(cfe.getDescription());
//				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
//						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
					line++;				
				
				currentMonthActAct = currentMonthActAct.add(currentMonthAct);  
				prevYearMonthActAct = prevYearMonthActAct.add(prevYearMonthAct);
				ytdActAct = ytdActAct.add(ytdAct); 
				prevYtdActAct = prevYtdActAct.add(prevYtdAct);
				budgetAmtAct = budgetAmtAct.add(budgetAmt);
				ytdBudgetAmtAct = ytdBudgetAmtAct.add(ytdBudgetAmt);
			}
		}

		cfeList = getCashFlowElement(ctx, AD_Client_ID, "2", false, false, false, false, true, trx);
		if(cfeList!=null)
		{
			for(MCashFlowElement cfe : cfeList)
			{
				BigDecimal currentMonthAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), false, monthStartDate, monthEndDate, trx); 
				BigDecimal prevYearMonthAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), false, prevMonthStartDate, prevMonthEndDate, trx);
				BigDecimal ytdAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), false, annualStartDate, monthEndDate, trx); 
				BigDecimal prevYtdAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), false, prevAnnualStartDate, prevMonthEndDate, trx);
				col_0 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2016-01-01 00:00:00"), tgl("2016-12-31 00:00:00"), trx);
				col_1 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2017-01-01 00:00:00"), tgl("2017-12-31 00:00:00"), trx);
				col_2 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2018-01-01 00:00:00"), tgl("2018-12-31 00:00:00"), trx);
				col_3 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2019-01-01 00:00:00"), tgl("2019-12-31 00:00:00"), trx);
				col_4 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2020-01-01 00:00:00"), tgl("2020-12-31 00:00:00"), trx);
				BigDecimal budgetAmt = BigDecimal.ZERO;
				BigDecimal ytdBudgetAmt = BigDecimal.ZERO;
				
				BigDecimal growth = getGrowthPercentage(currentMonthAct, prevYearMonthAct);
				BigDecimal growth2 = getGrowthPercentage(ytdAct, prevYtdAct);

				BigDecimal achievement = getBudgetAchievement(budgetAmt, currentMonthAct);
				BigDecimal achievement2 = getBudgetAchievement(ytdBudgetAmt, ytdAct);

				
				StringBuilder desc = new StringBuilder("  ");
				desc.append(cfe.getDescription());
//				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
//						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
					line++;				
				currentMonthActAct = currentMonthActAct.subtract(currentMonthAct);  
				prevYearMonthActAct = prevYearMonthActAct.subtract(prevYearMonthAct);
				ytdActAct = ytdActAct.subtract(ytdAct); 
				prevYtdActAct = prevYtdActAct.subtract(prevYtdAct);
				budgetAmtAct = budgetAmtAct.subtract(budgetAmt);
				ytdBudgetAmtAct = ytdBudgetAmtAct.subtract(ytdBudgetAmt);
			}
		}

		growthAcc = BigDecimal.ZERO;
		achievementAcc = BigDecimal.ZERO;
		growth2Acc = BigDecimal.ZERO;
		achievement2Acc = BigDecimal.ZERO;

		sectionName = getSectionName(AD_Client_ID, true, "03", trx);
//		if(insertLine(AD_Client_ID, 0, line, sectionName, currentMonthActAct, prevYearMonthActAct, growthAcc, budgetAmtAct, achievementAcc,  
//				ytdActAct, prevYtdActAct, growth2Acc, ytdBudgetAmtAct, achievement2Acc, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
		if(insertLine(AD_Client_ID, 0, line, sectionName, currentMonthActAct, prevYearMonthActAct, growthAcc, budgetAmtAct, achievementAcc,  
				ytdActAct, prevYtdActAct, growth2Acc, ytdBudgetAmtAct, achievement2Acc, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));

		// End Investing Activities
		
		// Financing Activities
		BigDecimal currentMonthActFin = BigDecimal.ZERO;  
		BigDecimal prevYearMonthActFin = BigDecimal.ZERO;
		BigDecimal ytdActFin = BigDecimal.ZERO; 
		BigDecimal prevYtdActFin = BigDecimal.ZERO;
		BigDecimal budgetAmtFin = BigDecimal.ZERO;
		BigDecimal ytdBudgetAmtFin = BigDecimal.ZERO;

		// Insert Separator
		if(insertLine(AD_Client_ID, 0, line, null,  C_Year_ID, C_Period_ID, cashFlowMethod, trx))
			line++;

		sectionName = getSectionName(AD_Client_ID, false, "04", trx);
		if(insertLine(AD_Client_ID, 0, line, sectionName, C_Year_ID, C_Period_ID, cashFlowMethod, trx))
			line++;
		
		cfeList = getCashFlowElement(ctx, AD_Client_ID, "3", false, false, false, true, true, trx);
		if(cfeList!=null)
		{
			for(MCashFlowElement cfe : cfeList)
			{
				BigDecimal currentMonthAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), true, monthStartDate, monthEndDate, trx); 
				BigDecimal prevYearMonthAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), true, prevMonthStartDate, prevMonthEndDate, trx);
				BigDecimal ytdAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), true, annualStartDate, monthEndDate, trx); 
				BigDecimal prevYtdAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), true, prevAnnualStartDate, prevMonthEndDate, trx);
				col_0 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), true, tgl("2016-01-01 00:00:00"), tgl("2016-12-31 00:00:00"), trx);
				col_1 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), true, tgl("2017-01-01 00:00:00"), tgl("2017-12-31 00:00:00"), trx);
				col_2 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), true, tgl("2018-01-01 00:00:00"), tgl("2018-12-31 00:00:00"), trx);
				col_3 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), true, tgl("2019-01-01 00:00:00"), tgl("2019-12-31 00:00:00"), trx);
				col_4 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), true, tgl("2020-01-01 00:00:00"), tgl("2020-12-31 00:00:00"), trx);
				BigDecimal budgetAmt = BigDecimal.ZERO;
				BigDecimal ytdBudgetAmt = BigDecimal.ZERO;
				
				BigDecimal growth = getGrowthPercentage(currentMonthAct, prevYearMonthAct);
				BigDecimal growth2 = getGrowthPercentage(ytdAct, prevYtdAct);

				BigDecimal achievement = getBudgetAchievement(budgetAmt, currentMonthAct);
				BigDecimal achievement2 = getBudgetAchievement(ytdBudgetAmt, ytdAct);

				
				StringBuilder desc = new StringBuilder("  ");
				desc.append(cfe.getDescription());
//				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
//						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
					line++;				
				currentMonthActFin = currentMonthActFin.add(currentMonthAct);  
				prevYearMonthActFin = prevYearMonthActFin.add(prevYearMonthAct);
				ytdActFin = ytdActFin.add(ytdAct); 
				prevYtdActFin = prevYtdActFin.add(prevYtdAct);
				budgetAmtFin = budgetAmtFin.add(budgetAmt);
				ytdBudgetAmtFin = ytdBudgetAmtFin.add(ytdBudgetAmt);

			}
		}

		cfeList = getCashFlowElement(ctx, AD_Client_ID, "3", false, false, false, false, true, trx);
		if(cfeList!=null)
		{
			for(MCashFlowElement cfe : cfeList)
			{
				BigDecimal currentMonthAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), false, monthStartDate, monthEndDate, trx); 
				BigDecimal prevYearMonthAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), false, prevMonthStartDate, prevMonthEndDate, trx);
				BigDecimal ytdAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), false, annualStartDate, monthEndDate, trx); 
				BigDecimal prevYtdAct = getChargeActivitiesAmt(AD_Client_ID, cfe.getXX_CashFlowElement_ID(), false, prevAnnualStartDate, prevMonthEndDate, trx);
				col_0 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2016-01-01 00:00:00"), tgl("2016-12-31 00:00:00"), trx);
				col_1 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2017-01-01 00:00:00"), tgl("2017-12-31 00:00:00"), trx);
				col_2 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2018-01-01 00:00:00"), tgl("2018-12-31 00:00:00"), trx);
				col_3 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2019-01-01 00:00:00"), tgl("2019-12-31 00:00:00"), trx);
				col_4 = getChargeActivitiesAmt(AD_Client_ID,cfe.getXX_CashFlowElement_ID(), false, tgl("2020-01-01 00:00:00"), tgl("2020-12-31 00:00:00"), trx);
				BigDecimal budgetAmt = BigDecimal.ZERO;
				BigDecimal ytdBudgetAmt = BigDecimal.ZERO;
				
				BigDecimal growth = getGrowthPercentage(currentMonthAct, prevYearMonthAct);
				BigDecimal growth2 = getGrowthPercentage(ytdAct, prevYtdAct);

				BigDecimal achievement = getBudgetAchievement(budgetAmt, currentMonthAct);
				BigDecimal achievement2 = getBudgetAchievement(ytdBudgetAmt, ytdAct);

				
				StringBuilder desc = new StringBuilder("  ");
				desc.append(cfe.getDescription());
//				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
//						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
				if(insertLine(AD_Client_ID, 0, line, desc.toString(), currentMonthAct, prevYearMonthAct, growth, budgetAmt, achievement,  
						ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
					line++;				
				currentMonthActFin = currentMonthActFin.subtract(currentMonthAct);  
				prevYearMonthActFin = prevYearMonthActFin.subtract(prevYearMonthAct);
				ytdActFin = ytdActFin.subtract(ytdAct); 
				prevYtdActFin = prevYtdActFin.subtract(prevYtdAct);
				budgetAmtFin = budgetAmtFin.subtract(budgetAmt);
				ytdBudgetAmtFin = ytdBudgetAmtFin.subtract(ytdBudgetAmt);

			}
		}
		
		growthAcc = BigDecimal.ZERO;
		achievementAcc = BigDecimal.ZERO;
		growth2Acc = BigDecimal.ZERO;
		achievement2Acc = BigDecimal.ZERO;

		sectionName = getSectionName(AD_Client_ID, true, "04", trx);
//		if(insertLine(AD_Client_ID, 0, line, sectionName, currentMonthActFin, prevYearMonthActFin, growthAcc, budgetAmtFin, achievementAcc,  
//				ytdActFin, prevYtdActFin, growth2Acc, ytdBudgetAmtFin, achievement2Acc, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
		if(insertLine(AD_Client_ID, 0, line, sectionName, currentMonthActFin, prevYearMonthActFin, growthAcc, budgetAmtFin, achievementAcc,  
				ytdActFin, prevYtdActFin, growth2Acc, ytdBudgetAmtFin, achievement2Acc, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
		
		// End Financing Activities		
		String accountList = getAccountList(ctx, trx);
		// Insert Separator
		if(insertLine(AD_Client_ID, 0, line, null,  C_Year_ID, C_Period_ID, cashFlowMethod, trx))
			line++;
		
		BigDecimal monthActTotal = currentMonthActAct.add(currentMonthActFin).add(currentMonthActOp); 
		BigDecimal prevMonthActTotal = prevYearMonthActAct.add(prevYearMonthActFin).add(prevYearMonthActOp);		
		BigDecimal ytdActTotal = ytdActAct.add(ytdActFin).add(ytdActOp); 
		BigDecimal prevYtdActTotal = prevYtdActAct.add(prevYtdActFin).add(prevYearMonthActOp);
		BigDecimal budgetAmtTotal = budgetAmtAct.add(budgetAmtFin).add(budgetAmtOp);
		BigDecimal ytdBudgetAmtTotal = ytdBudgetAmtAct.add(ytdBudgetAmtFin).add(ytdBudgetAmtOp);		

		sectionName = getSectionName(AD_Client_ID, false, X_XX_CashFlowSection.CASHFLOWSECTION_NetIncreaseInCashAndCashEquivalents, trx);
//		if(insertLine(AD_Client_ID, 0, line, sectionName, monthActTotal, prevMonthActTotal, growthAcc, budgetAmtTotal, achievementAcc,  
//				ytdActTotal, prevYtdActTotal, growth2Acc, ytdBudgetAmtTotal, achievement2Acc, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
		if(insertLine(AD_Client_ID, 0, line, sectionName, monthActTotal, prevMonthActTotal, growthAcc, budgetAmtTotal, achievementAcc,  
				ytdActTotal, prevYtdActTotal, growth2Acc, ytdBudgetAmtTotal, achievement2Acc, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
		
		sectionName = getSectionName(AD_Client_ID, false, X_XX_CashFlowSection.CASHFLOWSECTION_CashAndCashEquivalentsAtBeginningOfPeriod, trx);
		
		// Change Date 
		
		cal.setTime(monthStartDate);
		Timestamp endDate = new Timestamp(cal.getTimeInMillis());		
		BigDecimal currentMonthAct = getCashAccountAmt(accountList, null, endDate, trx); 

		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		endDate = new Timestamp(cal.getTimeInMillis());
		BigDecimal ytdAct = getCashAccountAmt(accountList, null, endDate, trx); 

		cal.setTime(monthStartDate);
		cal.add(Calendar.YEAR, -1);
		endDate = new Timestamp(cal.getTimeInMillis());		
		BigDecimal prevMonthAcct = getCashAccountAmt(accountList, null, endDate, trx);

		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		endDate = new Timestamp(cal.getTimeInMillis());
		BigDecimal prevYtdAct = getCashAccountAmt(accountList, null, endDate, trx);		
		
		BigDecimal budgetAmt = BigDecimal.ZERO;
		BigDecimal ytdBudgetAmt = BigDecimal.ZERO;
		
		BigDecimal growth = getGrowthPercentage(ytdAct, prevYtdAct);
		BigDecimal growth2 = getGrowthPercentage(ytdAct, prevYtdAct);

		BigDecimal achievement = getBudgetAchievement(budgetAmt, currentMonthAct);
		BigDecimal achievement2 = getBudgetAchievement(ytdBudgetAmt, ytdAct);

		
//		if(insertLine(AD_Client_ID, 0, line, sectionName, currentMonthAct, prevMonthAcct, growth, budgetAmt, achievement,  
//				ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
		if(insertLine(AD_Client_ID, 0, line, sectionName, currentMonthAct, prevMonthAcct, growth, budgetAmt, achievement,  
				ytdAct, prevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
			line++;				
		
		sectionName = getSectionName(AD_Client_ID, false, X_XX_CashFlowSection.CASHFLOWSECTION_CashAndCashEquivalentsAtEndOfPeriod, trx);
		monthStartDate = monthEndDate;
		monthEndDate = p.getEndDate();
		cal.setTime(monthStartDate);
		cal.add(Calendar.YEAR, -1);
		prevAnnualStartDate = new Timestamp(cal.getTimeInMillis());
		cal.set(Calendar.DATE, 31);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		prevMonthEndDate = new Timestamp(cal.getTimeInMillis());
		
//		currentMonthAct = getCashAccountAmt(accountList, monthStartDate, monthEndDate, trx); 
//		BigDecimal ytdAct = getCashAccountAmt(accountList, annualStartDate, monthEndDate, trx); 
//		ytdAct = BigDecimal.ZERO;
		prevMonthAcct = getCashAccountAmt(accountList, prevAnnualStartDate, prevMonthEndDate, trx);
//		prevYtdAct = BigDecimal.ZERO;
//		BigDecimal prevYtdAct = getCashAccountAmt(accountList, prevAnnualStartDate, prevMonthEndDate, trx);
		budgetAmt = BigDecimal.ZERO;
		ytdBudgetAmt = BigDecimal.ZERO;
		

		achievement = getBudgetAchievement(budgetAmt, currentMonthAct);
		achievement2 = getBudgetAchievement(ytdBudgetAmt, ytdAct);
		
		BigDecimal endMonthAct = monthActTotal.add(currentMonthAct);
		BigDecimal endPrevMonthAct = prevMonthActTotal.add(prevMonthAcct);		
		BigDecimal endYtdAct = ytdActTotal.add(ytdAct);
		BigDecimal endPrevYtdAct = prevYtdActTotal.add(prevYtdAct);
		
		growth = getGrowthPercentage(endMonthAct, endPrevMonthAct);
		growth2 = getGrowthPercentage(endYtdAct, endPrevYtdAct);
		
//		if(insertLine(AD_Client_ID, 0, line, sectionName, endMonthAct, endPrevMonthAct, growth, budgetAmt, achievement,  
//				endYtdAct, endPrevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,  trx));
		if(insertLine(AD_Client_ID, 0, line, sectionName, endMonthAct, endPrevMonthAct, growth, budgetAmt, achievement,  
				endYtdAct, endPrevYtdAct, growth2, ytdBudgetAmt, achievement2, C_Year_ID, C_Period_ID, cashFlowMethod,col_0,col_1,col_2,col_3,col_4,  trx));
			line++;				

		return true;
	}
	
	private static String getAccountList(Ctx ctx, Trx trx)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		StringBuilder accountList = null;
		String sql = "SELECT Account_ID FROM XX_CashFlowCashAccount ca " +
				"INNER JOIN AD_ClientInfo ci ON (ca.C_AcctSchema_ID = ci.C_AcctSchema1_ID) " +
				"WHERE ci.AD_Client_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, ctx.getAD_Client_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				list.add(rs.getInt(1));
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		for(Integer i : list)
		{
			MElementValue ev = new MElementValue(ctx, i.intValue(), trx);
			System.out.println("Account " + ev.getName());
			accountList = getAccountList(ctx, i.intValue(), accountList, trx);
		}
		String retValue = null;		
		if(accountList!=null)
		{
//			accountList.append(") ");
			retValue = accountList.toString();
		}
		return retValue;
	}	
	
	private static StringBuilder getAccountList(Ctx ctx, int Account_ID, StringBuilder accountList, Trx trx)
	{
		MElementValue ev = new MElementValue(ctx, Account_ID, trx);
		boolean isFirstRecord = false;
		if(accountList==null)
			isFirstRecord = true;
		
		if(!ev.isSummary())
		{
			if(!isFirstRecord)
				accountList.append(",");
			else
			{
				accountList = new StringBuilder();
				isFirstRecord = false;
			}
			accountList.append(Account_ID);
			return accountList;
		}
		
		StringBuilder sql = new StringBuilder("SELECT tn.Node_ID FROM AD_TreeNode tn " +
				"INNER JOIN C_Element e ON (tn.AD_Tree_ID = e.AD_Tree_ID) " +
				"INNER JOIN C_AcctSchema_Element ase ON (e.C_Element_ID = ase.C_Element_ID) " +
				"INNER JOIN C_AcctSchema a ON (ase.C_AcctSchema_ID = a.C_AcctSchema_ID) " +
				"WHERE tn.Parent_ID = ? ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, Account_ID);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ev = new MElementValue(ctx, rs.getInt(1), trx);
				if(ev.isSummary())
					accountList = getAccountList(ctx, rs.getInt(1), accountList, trx);
				else
				{
					if(!isFirstRecord)
						accountList.append(",");
					else
					{
						accountList = new StringBuilder();
						isFirstRecord = false;
					}
					accountList.append(rs.getInt(1));
				}
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}			

		return accountList;
	}
	
	private static BigDecimal getGrowthPercentage(BigDecimal currentAmt, BigDecimal prevAmt)
	{
		BigDecimal growth = BigDecimal.ZERO;
		BigDecimal delta = currentAmt.subtract(prevAmt);
		if(prevAmt.signum()!=0 && delta.signum()!=0)
		{			
			BigDecimal growthPct = BigDecimal.ZERO;
			growthPct = delta.divide(prevAmt, 4, RoundingMode.HALF_EVEN);
			growthPct = growthPct.multiply(BigDecimal.valueOf(100));
			growthPct = growthPct.setScale(0, RoundingMode.HALF_EVEN);
			growth = growthPct;
		}
		return growth;
	}
	
	private static BigDecimal getBudgetAchievement(BigDecimal budgetAmt, BigDecimal actualAmt)
	{
		BigDecimal achievement = BigDecimal.ZERO;
		if(budgetAmt.signum()!=0)
		{
			BigDecimal delta = budgetAmt.subtract(actualAmt);
			if(delta.signum()!=0)
			{	
				BigDecimal achievementPct = BigDecimal.ZERO;
				achievementPct = delta.divide(budgetAmt, 4, RoundingMode.HALF_EVEN);
				achievementPct = delta.multiply(BigDecimal.valueOf(100));
				achievementPct = achievementPct.setScale(0, RoundingMode.HALF_EVEN);
				achievement = achievementPct;			
			}
		}
		return achievement;
	}

	
	private static boolean insertLine(int AD_Client_ID, int AD_Org_ID, int line, String description, int C_Year_ID, int C_Period_ID, String cashFlowMethod,
			Trx trx)
	{		
//		StringBuilder insert = new StringBuilder("INSERT INTO T_StatementOfCashFlow " +
//				"(AD_Client_ID, AD_Org_ID, Line, Description, C_Year_ID, C_Period_ID, CashFlowMethod");
//		insert.append(") ");
//		StringBuilder value = new StringBuilder(" VALUES (?, ?, ?, ?, ?, ?, ? ");
//		value.append(") ");
//		StringBuilder sql = insert.append(value);
//		DB.executeUpdate(trx, sql.toString(), AD_Client_ID, AD_Org_ID, line, description, C_Year_ID, C_Period_ID, cashFlowMethod);
		
		
		
//		insertLine(AD_Client_ID, AD_Org_ID, line, description, 
//				null, null, null, null, null, null, null, null, null, null, C_Year_ID, C_Period_ID, cashFlowMethod, trx);
		
		insertLine(AD_Client_ID, AD_Org_ID, line, description, 
				null, null, null, null, null, null, null, null, null, null, C_Year_ID, C_Period_ID, cashFlowMethod,
				null,null,null,null,null,trx);
		return true;
	}
	
//	private static boolean insertLine(int AD_Client_ID, int AD_Org_ID, int line, String description, 
//			BigDecimal currentMonthAct, BigDecimal prevYearAct, BigDecimal monthGrowth, BigDecimal monthlyBudget, BigDecimal monthAchievement, BigDecimal YtdCurrentAct, BigDecimal YtdPrevAct, 
//			BigDecimal AnnualGrowth, BigDecimal YTDBudget, BigDecimal YTDAchievement, int C_Year_ID, int C_Period_ID, String cashFlowMethod, 
//			Trx trx)
	private static boolean insertLine(int AD_Client_ID, int AD_Org_ID, int line, String description, 
			BigDecimal currentMonthAct, BigDecimal prevYearAct, BigDecimal monthGrowth, BigDecimal monthlyBudget, BigDecimal monthAchievement, BigDecimal YtdCurrentAct, BigDecimal YtdPrevAct, 
			BigDecimal AnnualGrowth, BigDecimal YTDBudget, BigDecimal YTDAchievement, int C_Year_ID, int C_Period_ID, String cashFlowMethod,
			BigDecimal Col_0,BigDecimal Col_1,BigDecimal Col_2,BigDecimal Col_3,BigDecimal Col_4,
			Trx trx)
	{		
		StringBuilder insert = new StringBuilder("INSERT INTO T_StatementOfCashFlow " +
				"(AD_Client_ID, AD_Org_ID, Line, Description, CurrentMonthAct, PrevYearAct, MonthGrowth, CurrentPeriodBudget, CurrentAchievement, YtdCurrentAct, YtdPrevAct, AnnualGrowth, YTDBudget, YTDAchievement, " +
				"C_Year_ID, C_Period_ID, CashFlowMethod");
		
		insert.append(",Col_0,Col_1,Col_2,Col_3,Col_4 ");
		
		insert.append(") ");
		StringBuilder value = new StringBuilder(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?");
		
		value.append(",?,?,?,?,? ");
		
		value.append(") ");
		StringBuilder sql = insert.append(value);
//		DB.executeUpdate(trx, sql.toString(), AD_Client_ID, AD_Org_ID, line, description, currentMonthAct, prevYearAct, monthGrowth, monthlyBudget, monthAchievement, 
//				 YtdCurrentAct, YtdPrevAct, AnnualGrowth, YTDBudget, YTDAchievement, C_Year_ID, C_Period_ID, cashFlowMethod);
		
		DB.executeUpdate(trx, sql.toString(), AD_Client_ID, AD_Org_ID, line, description, currentMonthAct, prevYearAct, monthGrowth, monthlyBudget, monthAchievement, 
				 YtdCurrentAct, YtdPrevAct, AnnualGrowth, YTDBudget, YTDAchievement, C_Year_ID, C_Period_ID, cashFlowMethod, Col_0,Col_1,Col_2,Col_3,Col_4);
		return true;
	}

	
	private static MCashFlowElement[] getCashFlowElement(Ctx ctx, int AD_Client_ID, String cashFlowActivities, 
			boolean isReceiptFromCustomer, boolean isPaidToVendor, boolean isOtherOperatingExpense, boolean isReceipt, boolean isCharge, Trx trx)
	{
		ArrayList<MCashFlowElement> cfeList = null;
		StringBuilder sql = new StringBuilder("SELECT cfe.* FROM XX_CashFlowElement cfe " +
				"WHERE AD_Client_ID = ? AND CashFlowActivities = ?");
		if(isReceiptFromCustomer)
			sql.append(" AND IsReceiptFromCustomer = 'Y'");
		else
			sql.append(" AND IsReceiptFromCustomer = 'N'");

		if(isPaidToVendor)
			sql.append(" AND IsPaidToVendor = 'Y'");
		else
			sql.append(" AND IsPaidToVendor = 'N'");

		if(isOtherOperatingExpense)
			sql.append(" AND IsOtherOperatingExpense = 'Y'");
		else
			sql.append(" AND IsOtherOperatingExpense = 'N'");

		if(isReceipt)
			sql.append(" AND IsReceipt = 'Y'");
		else
			sql.append(" AND IsReceipt = 'N'");

		if(isCharge)
			sql.append(" AND cfe.XX_CashFlowElement_ID IN (SELECT XX_CashFlowElement_ID FROM XX_CashFlowElementCharge)");		
		else
			sql.append(" AND cfe.XX_CashFlowElement_ID NOT IN (SELECT XX_CashFlowElement_ID FROM XX_CashFlowElementCharge)");		

		sql.append(" ORDER BY cfe.SeqNo ");

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setString(2, cashFlowActivities);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				if(cfeList==null)
					cfeList = new ArrayList<MCashFlowElement>();
				MCashFlowElement cfe = new MCashFlowElement(ctx, rs, trx);
				cfeList.add(cfe);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		MCashFlowElement[] retValue = null;
		if(cfeList!=null)
		{
			retValue = new MCashFlowElement[cfeList.size()];
			cfeList.toArray(retValue);
		}
		return retValue;
	}

	
	private static String getSectionName(int AD_Client_ID, boolean isTotalSection, String cashFlowSection, Trx trx)
	{
		String sectionName = null;
		String sql = "SELECT Name, TotalDescription FROM XX_CashFlowSection " +
				"WHERE AD_Client_ID = ? AND CashFlowSection = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setString(2, cashFlowSection);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				if(!isTotalSection)
					sectionName = rs.getString(1);
				else
					sectionName = rs.getString(2);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return sectionName;
	}
	
	private static BigDecimal getReceiptAmount(int AD_Client_ID, int C_BPartner_Group_ID, Timestamp startDate, Timestamp endDate, Trx trx)
	{
		BigDecimal receiptAmt = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(Amt),0) AS Amt FROM " +
				"(" +
				"SELECT COALESCE(SUM(p.PayAmt),0) AS Amt FROM C_Payment p " +
				"INNER JOIN C_AllocationLine al ON (p.C_Payment_ID = al.C_Payment_ID) " +
				"INNER JOIN C_Invoice i ON (al.C_Invoice_ID = i.C_Invoice_ID) " +
				"INNER JOIN C_BPartner bp ON (i.C_BPartner_ID = bp.C_BPartner_ID) " +
				"WHERE p.DateTrx BETWEEN ? AND ? " +
				"AND i.IsSOTrx = 'Y' " +
				"AND p.IsReceipt = 'Y' " +
				"AND p.AD_Client_ID = ?");
		if(C_BPartner_Group_ID!=0)
		{
			sql.append(" AND bp.C_BPartner_Group_ID = ");
			sql.append(C_BPartner_Group_ID);
		}
		sql.append(" UNION ");
		sql.append("SELECT COALESCE(SUM(cl.Amount),0) AS Amt FROM C_CashLine cl " +
				"INNER JOIN C_Cash c ON (c.C_Cash_ID = cl.C_Cash_ID) " +
				"INNER JOIN C_Invoice i ON (cl.C_Invoice_ID = i.C_Invoice_ID) " +
				"INNER JOIN C_BPartner bp ON (i.C_BPartner_ID = bp.C_BPartner_ID) " +
				"WHERE c.StatementDate BETWEEN ? AND ? " +
				"AND i.IsSOTrx = 'Y'");
		if(C_BPartner_Group_ID!=0)
		{
			sql.append(" AND bp.C_BPartner_Group_ID = ");
			sql.append(C_BPartner_Group_ID);
		}		
		sql.append(" AND cl.AD_Client_ID = ? )");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			pstmt.setTimestamp(1, startDate);
			pstmt.setTimestamp(2, endDate);
			pstmt.setInt(3, AD_Client_ID);
			pstmt.setTimestamp(4, startDate);
			pstmt.setTimestamp(5, endDate);
			pstmt.setInt(6, AD_Client_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				receiptAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return receiptAmt;
	}
	
	private static BigDecimal getPayAmount(int AD_Client_ID, int C_BPartner_Group_ID, Timestamp startDate, Timestamp endDate, Trx trx)
	{
		BigDecimal payAmt = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(Amt),0) AS Amt FROM " +
				"(" +
				"SELECT COALESCE(SUM(p.PayAmt),0) AS Amt FROM C_Payment p " +
				"INNER JOIN C_AllocationLine al ON (p.C_Payment_ID = al.C_Payment_ID) " +
				"INNER JOIN C_Invoice i ON (al.C_Invoice_ID = i.C_Invoice_ID) " +
				"INNER JOIN C_BPartner bp ON (i.C_BPartner_ID = bp.C_BPartner_ID) " +
				"WHERE p.DateTrx BETWEEN ? AND ? " +
				"AND i.IsSOTrx = 'N' " +
				"AND p.IsReceipt = 'N' " +
				"AND p.AD_Client_ID = ?");
		
		if(C_BPartner_Group_ID!=0)
		{
			sql.append(" AND bp.C_BPartner_Group_ID = ");
			sql.append(C_BPartner_Group_ID);
		}
		sql.append(" UNION ");
		sql.append("SELECT COALESCE(SUM(cl.Amount),0) AS Amt FROM C_CashLine cl " +
				"INNER JOIN C_Cash c ON (c.C_Cash_ID = cl.C_Cash_ID) " +
				"INNER JOIN C_Invoice i ON (cl.C_Invoice_ID = i.C_Invoice_ID) " +
				"INNER JOIN C_BPartner bp ON (i.C_BPartner_ID = bp.C_BPartner_ID) " +
				"WHERE c.StatementDate BETWEEN ? AND ? " +
				"AND i.IsSOTrx = 'N'");
		if(C_BPartner_Group_ID!=0)
		{
			sql.append(" AND bp.C_BPartner_Group_ID = ");
			sql.append(C_BPartner_Group_ID);
		}
		sql.append(" AND cl.AD_Client_ID = ? )");
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			pstmt.setTimestamp(1, startDate);
			pstmt.setTimestamp(2, endDate);
			pstmt.setInt(3, AD_Client_ID);
			pstmt.setTimestamp(4, startDate);
			pstmt.setTimestamp(5, endDate);
			pstmt.setInt(6, AD_Client_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				payAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return payAmt;
	}

	private static BigDecimal getNonChargeActivitiesAmt(int AD_Client_ID, boolean isReceipt, Timestamp startDate, Timestamp endDate, Trx trx)
	{		
		BigDecimal totalAmt = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(Amt),0) AS Amt FROM " +
				"(SELECT ");
		if(isReceipt)
			sql.append("COALESCE(SUM(p.PayAmt),0) ");
		else
			sql.append("COALESCE(SUM(p.PayAmt),0)*-1 ");
		sql.append("AS Amt FROM C_Payment p " +
			"WHERE p.C_Payment_ID NOT IN (SELECT C_Payment_ID FROM C_AllocationLine) " +
			"AND p.C_Charge_ID NOT IN (SELECT fec.C_Charge_ID FROM  XX_CashFlowElementCharge fec " +
			"INNER JOIN XX_CashFlowElement fe ON (fec.XX_CashFlowElement_ID = fe.XX_CashFlowElement_ID) " +
			"WHERE p.DateTrx BETWEEN ? AND ? ) ");			
		if(isReceipt)
			sql.append("AND p.IsReceipt = 'Y' ");
		else
			sql.append("AND p.IsReceipt = 'N' ");			
		sql.append("AND p.AD_Client_ID = ?");
		sql.append(" UNION ");
		sql.append("SELECT ");
		if(isReceipt)
			sql.append("COALESCE(SUM(cl.Amount),0) ");
		else
			sql.append("COALESCE(SUM(cl.Amount),0)*-1 ");			
		sql.append("AS Amt FROM C_CashLine cl " +
				"INNER JOIN C_Cash c ON (c.C_Cash_ID = cl.C_Cash_ID) " +
				"WHERE c.StatementDate BETWEEN ? AND ? " +
				"AND cl.C_CashLine_ID NOT IN (SELECT C_CashLine_ID FROM C_AllocationLine) " +
				"AND cl.C_Charge_ID NOT IN (SELECT fec.C_Charge_ID FROM  XX_CashFlowElementCharge fec " +
				"INNER JOIN XX_CashFlowElement fe ON (fec.XX_CashFlowElement_ID = fe.XX_CashFlowElement_ID)) ");
		if(isReceipt)
			sql.append("AND cl.Amount > 0 ");
		else
			sql.append("AND cl.Amount < 0 ");			
		sql.append(" AND cl.AD_Client_ID = ? )");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			
			pstmt.setTimestamp(1, startDate);
			pstmt.setTimestamp(2, endDate);
			pstmt.setInt(3, AD_Client_ID);
			pstmt.setTimestamp(4, startDate);
			pstmt.setTimestamp(5, endDate);
			pstmt.setInt(6, AD_Client_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				totalAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return totalAmt;
	}

	private static BigDecimal getChargeActivitiesAmt(int AD_Client_ID, int XX_CashFlowElement_ID, boolean isReceipt, Timestamp startDate, Timestamp endDate, Trx trx)
	{		
		BigDecimal totalAmt = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(Amt),0) AS Amt FROM " +
				"(" +
				"SELECT COALESCE(SUM(p.PayAmt),0) AS Amt FROM C_Payment p " +
				"WHERE NOT EXISTS (SELECT al.C_Payment_ID FROM C_AllocationLine al WHERE al.C_Payment_ID = p.C_Payment_ID) " +
				"AND p.C_Charge_ID IN (SELECT fec.C_Charge_ID FROM  XX_CashFlowElementCharge fec " +
					"INNER JOIN XX_CashFlowElement fe ON (fec.XX_CashFlowElement_ID = fe.XX_CashFlowElement_ID) " +
					"WHERE fe.XX_CashFlowElement_ID = ?) " +
					"AND p.DateTrx BETWEEN ? AND ? ");			
		if(isReceipt)
			sql.append("AND p.IsReceipt = 'Y' ");
		else
			sql.append("AND p.IsReceipt = 'N' ");			
		sql.append("AND p.AD_Client_ID = ?");
		sql.append(" UNION ");
		
		sql.append("SELECT ");
		if(isReceipt)
			sql.append("COALESCE(SUM(bsl.StmtAmt),0) ");
		else
			sql.append("COALESCE(SUM(bsl.StmtAmt),0)*-1 ");
		sql.append("AS Amt FROM C_BankStatementLine bsl ");
		sql.append("INNER JOIN C_BankStatement bs ON (bsl.C_BankStatement_ID = bs.C_BankStatement_ID) " +
				"WHERE bsl.C_Payment_ID IS NULL " +
				"AND bsl.StatementLineDate BETWEEN ? AND ? " +
				"AND bsl.C_Charge_ID IN (SELECT fec.C_Charge_ID FROM  XX_CashFlowElementCharge fec " +
				"INNER JOIN XX_CashFlowElement fe ON (fec.XX_CashFlowElement_ID = fe.XX_CashFlowElement_ID) " +
				"AND fe.XX_CashFlowElement_ID = ?) " +
				"AND bsl.AD_Client_ID = ? ");
		if(isReceipt)
			sql.append("AND bsl.StmtAmt > 0 ");
		else
			sql.append("AND bsl.StmtAmt < 0 ");			

		sql.append(" UNION ");		
		sql.append("SELECT ");
		if(isReceipt)
			sql.append("COALESCE(SUM(cl.Amount),0) ");
		else
			sql.append("COALESCE(SUM(cl.Amount),0)*-1 ");			
		sql.append("AS Amt FROM C_CashLine cl " +
				"INNER JOIN C_Cash c ON (c.C_Cash_ID = cl.C_Cash_ID) " +
				"WHERE c.StatementDate BETWEEN ? AND ? " +
				"AND cl.C_CashLine_ID NOT IN (SELECT C_CashLine_ID FROM C_AllocationLine) " +
				"AND cl.C_Charge_ID IN (SELECT fec.C_Charge_ID FROM  XX_CashFlowElementCharge fec " +
				"INNER JOIN XX_CashFlowElement fe ON (fec.XX_CashFlowElement_ID = fe.XX_CashFlowElement_ID) " +
				"AND fe.XX_CashFlowElement_ID = ?) ");
		if(isReceipt)
			sql.append("AND cl.Amount > 0 ");
		else
			sql.append("AND cl.Amount < 0 ");			
		sql.append(" AND cl.AD_Client_ID = ? )");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, XX_CashFlowElement_ID);
			pstmt.setTimestamp(2, startDate);
			pstmt.setTimestamp(3, endDate);
			pstmt.setInt(4, AD_Client_ID);
			pstmt.setTimestamp(5, startDate);
			pstmt.setTimestamp(6, endDate);
			pstmt.setInt(7, XX_CashFlowElement_ID);
			pstmt.setInt(8, AD_Client_ID);
			pstmt.setTimestamp(9, startDate);
			pstmt.setTimestamp(10, endDate);
			pstmt.setInt(11, XX_CashFlowElement_ID);
			pstmt.setInt(12, AD_Client_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				totalAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return totalAmt;
	}
	
	private static BigDecimal getCashAccountAmt(String accountList, Timestamp startDate, Timestamp endDate, Trx trx)
	{		
		BigDecimal totalAmt = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(AmtAcctDr-AmtAcctCr),0) AS Amt " +
				"FROM Fact_Acct " +
				"WHERE Account_ID IN (");
		sql.append(accountList);
		sql.append(")");
		if(startDate!=null && endDate!=null)
			sql.append(" AND DateAcct BETWEEN ? AND ? ");
		else if(startDate==null && endDate!=null)
			sql.append(" AND DateAcct < ? ");
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			System.out.println(sql);
			int index=1;
			if(startDate!=null && endDate!=null)
			{
				System.out.println(startDate);
				
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			}
			else if(startDate==null && endDate!=null)
				pstmt.setTimestamp(index++, endDate);
			
			System.out.println(endDate);

			rs = pstmt.executeQuery();
			if(rs.next())
				totalAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return totalAmt;
	}

	
	public static Timestamp tgl(String Tgl){
		Timestamp date1 = null;
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		    Date parsedDate = (Date) dateFormat.parse(Tgl);
		    date1 = new java.sql.Timestamp(parsedDate.getTime());
		} catch(Exception e) { //this generic but you can control another types of exception
		    // look the origin of excption 
			e.printStackTrace();
		}
		return date1;
	}

}
