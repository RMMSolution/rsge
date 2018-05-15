package rsge.tools;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;

import org.compiere.model.MElementValue;
import org.compiere.model.MPeriod;
import org.compiere.model.X_C_ElementValue;
import org.compiere.model.X_GL_BudgetControl;
import org.compiere.model.X_GL_Journal;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;

import rsge.model.MAcctElementBudgetControl;
import rsge.model.MAdvanceDisbursementLine;
import rsge.model.MBudgetInfo;
import rsge.model.MCashLine;
import rsge.model.MPurchaseRequisition;
import rsge.po.X_XX_BudgetInfo;

public class BudgetCalculation {

	// Parameter
	private boolean 					p_PeriodOnly = false;
	private Ctx							p_Ctx = null;
	private Trx							p_Trx = null;
	
	private int							p_AD_Org_ID = 0;
	private int							p_C_AcctSchema_ID = 0;	
	private	int							p_AccountID = 0;
	
	private int							budgetCalendarID = 0;
	private boolean						p_IsAccountOnly = false;
	
	private int							p_Activity_ID = 0; 
	private int							p_Campaign_ID = 0; 
	private int							p_Product_ID = 0; 
	private int							p_Project_ID = 0; 
	private int							p_BPartner_ID = 0;
	private int							p_Element1_ID = 0;
	private int							p_Element2_ID = 0;
	private int							p_SalesRegion_ID = 0;
	
	private int							p_GL_Budget_ID = 0;
	
	private boolean						checkBudget = false;
	private String						budgetScope = null;
	
	private	Timestamp 					checkDate = null;
	private Timestamp					startDate = null;
	private Timestamp					endDate = null;
	
	private Timestamp					startCutOffDate = null;
	private Timestamp 					endCutOffDate = null;
	
	private BigDecimal					realizedAmt = BigDecimal.ZERO;
	private BigDecimal					realizedQty = BigDecimal.ZERO;
	
	private BigDecimal					remainsAmt = BigDecimal.ZERO;
	private BigDecimal					remainsQty = BigDecimal.ZERO;
	
	private BigDecimal					unrealizedAmt = BigDecimal.ZERO;
	private BigDecimal					unrealizedQty = BigDecimal.ZERO;

	private BigDecimal					budgetAmt = BigDecimal.ZERO;
	private BigDecimal					budgetQty = null;
	
	private BigDecimal					reservedAmt = BigDecimal.ZERO;
	private BigDecimal					reservedQty = BigDecimal.ZERO;
	
	private BigDecimal					commitedAmt = BigDecimal.ZERO;
	private BigDecimal					commitedQty = BigDecimal.ZERO;
	
	private BigDecimal					pendingAmt = BigDecimal.ZERO;
	
	private String						processMsg = null;
	
	public BudgetCalculation(Ctx ctx, int AD_Org_ID, Timestamp dateDoc, Timestamp dateTrx, Timestamp start, Timestamp end, boolean calculatePeriodOnly, int accountID, 
			int C_Activity_ID, int C_Campaign_ID, int C_Project_ID, int C_BPartner_ID, int M_Product_ID, int C_SalesRegion_ID, int UserElement1_ID, int UserElement2_ID, boolean isAccountOnly, Trx trx)
	{
		// Get Budget Info
		MBudgetInfo info = MBudgetInfo.get(ctx, ctx.getAD_Client_ID(), trx);
		if(info!=null && info.getStartDate()!=null)
		{	
			// Set Ctx and Trx
			p_Ctx = ctx;
			p_Trx = trx;
			
			X_GL_BudgetControl defaultControl = new X_GL_BudgetControl(p_Ctx, info.getGL_BudgetControl_ID(), p_Trx);			
			p_GL_Budget_ID = defaultControl.getGL_Budget_ID();
			budgetScope = defaultControl.getBudgetControlScope();
			p_AD_Org_ID = AD_Org_ID;
			p_C_AcctSchema_ID = info.getC_AcctSchema_ID();
			
			MAcctElementBudgetControl elementControl = getBudgetControlID(info, C_Campaign_ID, C_Project_ID, trx);
			if(elementControl!=null)
			{
				if(elementControl.isAnyOrg())
					p_AD_Org_ID = 0;
				if(elementControl.isAllYear())
					budgetScope = X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total;
			}
			
			// Set Budget Calendar 
			budgetCalendarID = info.getBudgetCalendar_ID();
			p_IsAccountOnly = isAccountOnly;
			if(info.getBudgetCheckRate().equals(X_XX_BudgetInfo.BUDGETCHECKRATE_Transaction) && dateTrx!=null)
				checkDate = dateTrx;
			else
				checkDate = dateDoc;

			if(!checkDate.before(info.getStartDate()))
				checkBudget = true;
			else
				processMsg = "Document or transaction date is before budget info start date. Budget check waived.";

			// Set Start and End Date
			p_PeriodOnly = calculatePeriodOnly;
			setDate(start, end);
			
			// Set Cut off Date 
			if(info.getBudgetCutOffDate()!=0)
			{
				Calendar cal = Calendar.getInstance();
				cal.setTime(checkDate);
				int checkMonth = cal.get(Calendar.MONTH);
				int year = cal.get(Calendar.YEAR);
				
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				
				int month = Integer.valueOf(info.getBudgetCutOffMonth());
				System.out.println("Check Month " + checkMonth);
				System.out.println("Cut off Month " + month);
				if(month>checkMonth)
					year = year+1;
				int date = info.getBudgetCutOffDate();
				if(year%4!=0 && date == 29)
					date = 28;				
				cal.set(Calendar.DATE, date);
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.YEAR, year);
				endCutOffDate = new Timestamp(cal.getTimeInMillis());

				// Get First day of cut off year
				cal.set(Calendar.DATE, 1);
				cal.set(Calendar.MONTH, Calendar.JANUARY);
				cal.set(Calendar.HOUR_OF_DAY, 00);
				cal.set(Calendar.MINUTE, 00);
				cal.set(Calendar.SECOND, 00);
				startCutOffDate = new Timestamp(cal.getTimeInMillis());
				
				System.out.println("Cut off dates " + startCutOffDate + " - " + endCutOffDate);

			}
			
			// Set parameter
			if(C_Activity_ID!=0)
				p_Activity_ID = C_Activity_ID;
			if(C_Campaign_ID!=0)
				p_Campaign_ID = C_Campaign_ID;
			if(C_BPartner_ID!=0)
				p_BPartner_ID = C_BPartner_ID;
			if(C_Project_ID!=0)
				p_Project_ID = C_Project_ID;
			if(C_SalesRegion_ID!=0)
				p_SalesRegion_ID = C_SalesRegion_ID;
			if(UserElement1_ID!=0)
				p_Element1_ID = UserElement1_ID;
			if(UserElement2_ID!=0)
				p_Element2_ID = UserElement2_ID;
			
			p_AccountID = accountID;
			System.out.println("POINT A");
			// Get Budget method
			if(checkBudget)
				runBudgetCalculation(info);
		}
	}
	
	private void runBudgetCalculation(MBudgetInfo info)
	{
		// Get Budget Amount
		calculateBudget(p_IsAccountOnly);
		System.out.println("Budget Amt " + budgetAmt);		
		if(budgetAmt.signum()==0)
		{
			if(checkDate.after(startCutOffDate) && checkDate.before(endCutOffDate))
			{
				System.out.println("POINT 2");
				setStartExtBudgetDate(checkDate);
				System.out.println("Start Date " + startDate + "- End Date " + endDate);
				calculateBudget(p_IsAccountOnly);
			}
		}
		if(budgetAmt==null) // No budget defined
			return;
	}	
	
	public String			trxTypeAll = "A";
	public String			trxTypeUnrealized = "U";
	public String			trxTypeRealized = "R";
	public String			trxTypeReserved = "V";
	
	public boolean calculateUsedAmt()
	{
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(ReservedAmt),0) AS ReservedAmt, COALESCE(SUM(UnrealizedAmt),0) AS UnrealizedAmt, COALESCE(SUM(RealizedAmt),0) AS RealizedAmt ");
		sql.append("FROM XX_BudgetTransaction "
				+ "WHERE Account_ID = ? ");
		if(p_AD_Org_ID!=0)
			sql.append("AND AD_Org_ID = ? ");
		sql.append("AND BudgetDate BETWEEN ? AND ? ");
		
		if(p_Activity_ID!=0)
			sql.append("AND C_Activity_ID = ? ");
		if(p_Campaign_ID!=0)
			sql.append("AND C_Campaign_ID = ? ");
		if(p_BPartner_ID!=0)
			sql.append("AND C_BPartner_ID = ? ");
		if(p_Project_ID!=0)
			sql.append("AND C_Project_ID = ? ");
		if(p_SalesRegion_ID!=0)
			sql.append("AND C_SalesRegion_ID = ? ");
		if(p_Element1_ID!=0)
			sql.append("AND UserElement1_ID = ? ");
		if(p_Element2_ID!=0)
			sql.append("AND UserElement2_ID = ? ");

		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), p_Trx);
		ResultSet rs = null;
		try{
			int index = 1;
			pstmt.setInt(index++, p_AccountID);
			if(p_AD_Org_ID!=0)
				pstmt.setInt(index++, p_AD_Org_ID);
			pstmt.setTimestamp(index++, startDate);
			pstmt.setTimestamp(index++, endDate);
			
			if(p_Activity_ID!=0)
				pstmt.setInt(index++, p_Activity_ID);
			if(p_Campaign_ID!=0)
				pstmt.setInt(index++, p_Campaign_ID);
			if(p_BPartner_ID!=0)
				pstmt.setInt(index++, p_BPartner_ID);
			if(p_Project_ID!=0)
				pstmt.setInt(index++, p_Project_ID);
			if(p_SalesRegion_ID!=0)
				pstmt.setInt(index++, p_SalesRegion_ID);
			if(p_Element1_ID!=0)
				pstmt.setInt(index++, p_Element1_ID);
			if(p_Element2_ID!=0)
				pstmt.setInt(index++, p_Element2_ID);			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				setReservedAmt(rs.getBigDecimal("ReservedAmt"));
				setUnrealizedAmt(rs.getBigDecimal("UnrealizedAmt"));
				setRealizedAmt(rs.getBigDecimal("RealizedAmt"));
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}	
		checkPendingDocuments();
		return true;
	}
	
	public void checkPendingDocuments()
	{
		BigDecimal pendingAmt = BigDecimal.ZERO;
		BigDecimal requisitionPending = getRequisitionPending();
		BigDecimal advanceDisbursementPending = MAdvanceDisbursementLine.getPendingAmt(p_AD_Org_ID, p_AccountID, startDate, endDate, p_Activity_ID, p_Campaign_ID, p_Project_ID, p_Trx);
		BigDecimal cashJournalPending = MCashLine.getPendingAmt(p_AD_Org_ID, p_AccountID, startDate, endDate, p_Activity_ID, p_Campaign_ID, p_Project_ID, p_Trx);
		pendingAmt = requisitionPending.add(advanceDisbursementPending).add(cashJournalPending);
		setPendingAmt(pendingAmt);
	}	
	
	private BigDecimal getRequisitionPending()
	{
		BigDecimal purchaseRequisitionPending = MPurchaseRequisition.getBudgetPendingAmt(p_AD_Org_ID, p_AccountID, startDate, endDate, p_Activity_ID, p_Campaign_ID, p_Project_ID, p_Product_ID, p_Trx);
		return purchaseRequisitionPending;
	}
	
	private boolean calculateBudget(boolean isAccountOnly)
	{
		StringBuilder sql = new StringBuilder();		
		sql.append(getSelectSyntax(p_Ctx, p_AccountID, false, true, p_Trx));
		sql.append(getFromFactSyntax(p_AccountID, p_C_AcctSchema_ID, p_AD_Org_ID, "B"));
		sql.append(getWhereSyntax("B", "fa", p_Activity_ID, p_Campaign_ID, p_Project_ID, p_BPartner_ID, p_Product_ID, p_SalesRegion_ID, p_Element1_ID, p_Element2_ID, p_GL_Budget_ID, isAccountOnly));
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), p_Trx);
        ResultSet rs = null;
	    try{	    	
	    	int index = 1;	   
	    	System.out.println(sql);
	    	System.out.println(p_C_AcctSchema_ID);

	    	pstmt.setInt(index++, p_C_AcctSchema_ID);
	    	if(p_AD_Org_ID!=0)
	    	{
	    		System.out.println(p_AD_Org_ID);

	    		pstmt.setInt(index++, p_AD_Org_ID);
	    	}
	    	System.out.println(p_AccountID);
	    	System.out.println(startDate);
	    	System.out.println(endDate);

	    	pstmt.setInt(index++, p_AccountID);
	    	pstmt.setTimestamp(index++, startDate);
    		pstmt.setTimestamp(index++, endDate);
	    	rs = pstmt.executeQuery();
	    	if(rs.next())
	    	{
	    		budgetAmt = rs.getBigDecimal(1);
	    		setBudgetAmt(budgetAmt);
	    	}
	    	rs.close();
	    	pstmt.close();
	    }catch (Exception e) {
	    	e.printStackTrace();
		}	    
		return true;
	}
	
	private void setDate(Timestamp start, Timestamp end)
	{
		if(start!=null && end !=null)
		{
			startDate = start;
			endDate = end;
		}
		else
		{
			// Get Transaction Period
			MPeriod trxPeriod = new MPeriod(p_Ctx, getPeriod(checkDate), p_Trx);
			endDate = trxPeriod.getEndDate();
			Calendar cal = Calendar.getInstance();
			if(endDate==null)
			{
				cal.setTime(checkDate);
				// Get End Date
		        if(budgetScope.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly) || p_PeriodOnly)
		        {
		        	cal.set(Calendar.DATE, 1);
		        	startDate = new Timestamp(cal.getTimeInMillis());        
		            
		        	cal.add(Calendar.MONTH, 1);
					cal.add(Calendar.DATE, -1);
					endDate = new Timestamp(cal.getTimeInMillis());					
		        }
		        else if(budgetScope.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate))
		        {
		        	cal.set(Calendar.DATE, 1);
		        	cal.set(Calendar.MONTH, Calendar.JANUARY);
		            startDate = new Timestamp(cal.getTimeInMillis());        

		        	cal.set(Calendar.MONTH, 12);
					cal.set(Calendar.DATE, 1);
					endDate = new Timestamp(cal.getTimeInMillis());					
		        }
				else
				{ 	// Set start date to 1/1/2000
		        	cal.set(Calendar.DATE, 1);
		        	cal.set(Calendar.MONTH, Calendar.JANUARY);
		        	cal.set(Calendar.YEAR, 2000);
		            startDate = new Timestamp(cal.getTimeInMillis());       
		            endDate = checkDate;
				}
			}
			else
			{
		        cal.setTime(endDate);
		        if(budgetScope.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly) || p_PeriodOnly)
		        	startDate = trxPeriod.getStartDate();
		        else if(budgetScope.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate))
					startDate = getMaxMinYearDate(true, trxPeriod.getC_Year_ID());
				else
				{ 	// Set start date to 1/1/2000
		        	cal.set(Calendar.DATE, 1);
		        	cal.set(Calendar.MONTH, Calendar.JANUARY);
		        	cal.set(Calendar.YEAR, 2000);
		            startDate = new Timestamp(cal.getTimeInMillis());        
				}
		        // Get maximum date of year
		        Timestamp maxDate = getMaxMinYearDate(false, trxPeriod.getC_Year_ID());
		        // If max date is before end date -> end Date == max date
		        if(maxDate.before(endDate))
		           endDate = maxDate;
		        
			}
	        // Reset Date
	        startDate = TimeUtil.getDay(startDate);
	        cal.setTime(endDate);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			endDate = new Timestamp(cal.getTimeInMillis());									
		}		
	}
	
	private void setStartExtBudgetDate(Timestamp checkDate)
	{
		// Get Transaction Period
		MPeriod trxPeriod = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(checkDate);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        if(budgetScope.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly) || p_PeriodOnly)
        {
        	cal.add(Calendar.MONTH, -1);
        	checkDate = new Timestamp(cal.getTimeInMillis());
        	trxPeriod = new MPeriod(p_Ctx, getPeriod(checkDate), p_Trx);
        	startDate = trxPeriod.getStartDate();
        	if(startDate == null)
        	{
        		cal.set(Calendar.DATE, 1);
        		startDate = new Timestamp(cal.getTimeInMillis());
        	}
        }
        else if(budgetScope.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate))
        {
        	cal.add(Calendar.YEAR, -1);
        	checkDate = new Timestamp(cal.getTimeInMillis());
        	trxPeriod = new MPeriod(p_Ctx, getPeriod(checkDate), p_Trx);
        	if(trxPeriod.getC_Year_ID()!=0)
        		startDate = getMaxMinYearDate(true, trxPeriod.getC_Year_ID());
        	else
        	{
        		cal.set(Calendar.DATE, 1);
        		cal.set(Calendar.MONTH, Calendar.JANUARY);
        		startDate = new Timestamp(cal.getTimeInMillis());
        	}
        }

	}
	
	private Timestamp getMaxMinYearDate(boolean IsStartDate, int C_Year_ID)
	{
		Timestamp date = null;
        // Get maximum date of year
        StringBuilder sql = new StringBuilder("SELECT "); 
        if(IsStartDate)
        	sql.append("MIN(StartDate) ");
        else
        	sql.append("Max(EndDate) ");
        sql.append("FROM C_Period WHERE C_Year_ID = ? ");
        PreparedStatement pstmt = DB.prepareStatement(sql.toString(), p_Trx);
        ResultSet rs = null;
        try{
            pstmt.setInt(1, C_Year_ID);                        
            rs = pstmt.executeQuery();                        
            if(rs.next())
               date = rs.getTimestamp(1);
            rs.close();
            pstmt.close();
        }catch (Exception e) {
           e.printStackTrace();
        }

		return date;
	}
	
	private int getPeriod(Timestamp date)
	{
		int periodID = 0;
        String sql = "SELECT p.C_Period_ID "
        		+ "FROM C_Period p "
        		+ "INNER JOIN C_Year y ON (p.C_Year_ID = y.C_Year_ID) "
        		+ "WHERE y.C_Calendar_ID = ? "
        		+ "AND ? BETWEEN p.StartDate AND p.EndDate ";

        PreparedStatement pstmt = DB.prepareStatement(sql, p_Trx);
        ResultSet rs = null;
        try{
        	pstmt.setInt(1, budgetCalendarID);
        	pstmt.setTimestamp(2, date);
        	rs = pstmt.executeQuery();
	        if(rs.next())	
	        	periodID = rs.getInt(1);
	        rs.close();
	        pstmt.close();
        }catch (Exception e) {
           e.printStackTrace();
        }		
		return periodID;
	}
	
	
	/**
	 * Get SELECT syntax for SQL Query
	 * @return
	 */
	private static StringBuilder getSelectSyntax(Ctx ctx, int accountID, boolean DrCrOnly, boolean isBudget, Trx trx)
	{
		StringBuilder select = new StringBuilder();
		// Get Account's natural sign
		String accountSign = getAccountSign(ctx, accountID, trx);
		// End get account's natural sign		

		// Set Invoice Spent Amount Syntax Sql 
		if(accountSign!=null && accountSign.equals("Cr"))
		{
			if(!DrCrOnly)
			{
				if(!isBudget)
					select.append("SELECT COALESCE(SUM(fa.AmtAcctCr-fa.AmtAcctDr),0) AS Amt ");
				else
					select.append("SELECT COALESCE(SUM(fa.AmtAcctCr-fa.AmtAcctDr),0) AS Amt ");
			}
			else
			{
				if(!isBudget)
					select.append("SELECT COALESCE(SUM(fa.AmtAcctCr),0) AS Amt ");
				else
					select.append("SELECT COALESCE(SUM(fa.AmtAcctCr),0) AS Amt ");
			}
		}
		else
		{
			if(!DrCrOnly)
			{
				if(!isBudget)
					select.append("SELECT COALESCE(SUM(fa.AmtAcctDr-fa.AmtAcctCr),0) AS Amt ");
				else
					select.append("SELECT COALESCE(SUM(fa.AmtAcctDr-fa.AmtAcctCr),0) AS Amt ");
			}
			else
			{
				if(!isBudget)
					select.append("SELECT COALESCE(SUM(fa.AmtAcctDr),0) AS Amt ");
				else
					select.append("SELECT COALESCE(SUM(fa.AmtAcctDr),0) AS Amt ");

			}
		}
		return select;
	}
	
	/**
	 * Get FROM Syntax for Fact_Acct table
	 * @param PostingType
	 * @return
	 */
	private static StringBuilder getFromFactSyntax(int Account_ID, int C_AcctSchema_ID, int AD_Org_ID, String PostingType)
	{
		StringBuilder from = new StringBuilder();
		from.append("FROM Fact_Acct fa " +				
		"WHERE  fa.C_AcctSchema_ID = ? "); //1
		if(AD_Org_ID!=0)		
			from.append("AND fa.AD_Org_ID = ? "); //2		
		from.append("AND fa.PostingType = '" + PostingType + "' " +
		"AND fa.Account_ID = ? " + //3		
		"AND fa.DateAcct BETWEEN ? AND ? "); // 4, 5		
		return from;
	}
	
	/**
	 * Get WHERE syntax for sql query
	 * 
	 * Check Types :
	 * - B Budget Check
	 * - S Invoice Check
	 * - Reservation Check
	 * - C Commitment Check	 
	 * - A Advance Disbursement Check 
	 * - I Inventory Requisition Check
	 * 
	 * @param PostingType
	 * @return
	 */
	private static StringBuilder getWhereSyntax(String PostingType, String tableAlias, Integer activity, Integer campaign, Integer project, Integer bpartner,  
			Integer product, Integer salesRegion, Integer element1, Integer element2, int GL_Budget_ID, boolean isAccountOnly)
	{
		StringBuilder where = new StringBuilder();		

		if(PostingType!=null)
		{
			if(PostingType.equals(X_GL_Journal.POSTINGTYPE_Budget) & GL_Budget_ID != 0)
			{
				where.append("AND " + tableAlias + ".GL_Budget_ID = " + GL_Budget_ID + " ");
			}
			else if(PostingType.equals(X_GL_Journal.POSTINGTYPE_Budget) & GL_Budget_ID == 0)
			{
				where.append("AND (" + tableAlias + ".GL_Budget_ID = 0 OR " + tableAlias + ".GL_Budget_ID IS NULL) ");
			}			
		}		
		
		if(!isAccountOnly)
		{
			if(activity!=null)
			{
				if(activity > 0)
				{
					where.append("AND " + tableAlias + ".C_Activity_ID = " + activity + " ");
				}
				else 
				{
					where.append("AND (" + tableAlias + ".C_Activity_ID = 0 OR " + tableAlias + ".C_Activity_ID IS NULL) ");
				}			
			}
			
			if(campaign!=null)
			{
				if(campaign != 0)
				{
					where.append("AND " + tableAlias + ".C_Campaign_ID = " + campaign + " ");
				}
				else 
				{
					where.append("AND (" + tableAlias + ".C_Campaign_ID = 0 OR " + tableAlias + ".C_Campaign_ID IS NULL) ");
				}			
			}
			
			if(project!=null)
			{
				if(project != 0)
				{
					where.append("AND " + tableAlias + ".C_Project_ID = " + project + " ");
				}
				else 
				{
					where.append("AND (" + tableAlias + ".C_Project_ID = 0 OR " + tableAlias + ".C_Project_ID IS NULL) ");
				}			
			}
			if(bpartner!=null)
			{
				if(bpartner!=0)
				{
					where.append("AND " + tableAlias + ".C_BPartner_ID = " + bpartner + " ");
				}
				else
					where.append("AND (" + tableAlias + ".C_BPartner_ID = 0 OR " + tableAlias + ".C_BPartner_ID IS NULL) ");
			}
			if(product!=null)
			{
				if(product!=0)
				{
					where.append("AND " + tableAlias + ".M_Product_ID = " + product + " ");
				}
				else
					where.append("AND (" + tableAlias + ".M_Product_ID = 0 OR " + tableAlias + ".M_Product_ID IS NULL) ");
			}
			if(salesRegion!=null)
			{
				if(salesRegion!=0)
				{
					where.append("AND " + tableAlias + ".C_SalesRegion_ID = " + salesRegion + " ");
				}
				else
					where.append("AND (" + tableAlias + ".C_SalesRegion_ID = 0 OR " + tableAlias + ".C_SalesRegion_ID IS NULL) ");
			}
			if(element1!=null)
			{
				if(element1!=0)
				{
					where.append("AND " + tableAlias + ".UserElement1_ID = " + element1 + " ");
				}
				else
					where.append("AND (" + tableAlias + ".UserElement1_ID = 0 OR " + tableAlias + ".UserElement1_ID IS NULL) ");
			}
			if(element2!=null)
			{
				if(element2!=0)
				{
					where.append("AND " + tableAlias + ".UserElement2_ID = " + element2 + " ");
				}
				else
					where.append("AND (" + tableAlias + ".UserElement2_ID = 0 OR " + tableAlias + ".UserElement2_ID IS NULL) ");
			}
			
		}

		return where;	
	}

	/**
	 * Get natural sign of account 
	 * Debit - Dr
	 * Credit - Cr
	 * @return
	 */
	private static String getAccountSign(Ctx ctx, int accountID, Trx trx)
	{
		String sign = null;
		if(accountID==0)
			return sign;
		MElementValue ev = new MElementValue(ctx, accountID, trx);
		if((ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Asset)) 
				|| (ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Expense)))
			sign = "Dr";
		else if ((ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Liability)) 
				|| (ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Revenue))
				|| (ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_OwnerSEquity))) 
			sign = "Cr";
		else sign = ev.getAccountSign(); // For Memo
		
		return sign;
	}
	
	public BigDecimal getPendingAmt() {
		return pendingAmt;
	}

	public void setPendingAmt(BigDecimal pendingAmt) {
		this.pendingAmt = pendingAmt;
	}

	public BigDecimal getRealizedAmt() {
		return realizedAmt;
	}

	public void setRealizedAmt(BigDecimal realizedAmt) {
		this.realizedAmt = realizedAmt;
	}

	public BigDecimal getRealizedQty() {
		return realizedQty;
	}

	public void setRealizedQty(BigDecimal realizedQty) {
		this.realizedQty = realizedQty;
	}
	
	public BigDecimal getUnrealizedAmt() {
		return unrealizedAmt;
	}

	public void setUnrealizedAmt(BigDecimal unrealizedAmt) {
		this.unrealizedAmt = unrealizedAmt;
	}

	public BigDecimal getUnrealizedQty() {
		return unrealizedQty;
	}

	public void setUnrealizedQty(BigDecimal unrealizedQty) {
		this.unrealizedQty = unrealizedQty;
	}


	public BigDecimal getRemainsAmt() {
		return remainsAmt;
	}

	public void setRemainsAmt(BigDecimal remainsAmt) {
		this.remainsAmt = remainsAmt;
	}

	public BigDecimal getRemainsQty() {
		return remainsQty;
	}

	public void setRemainsQty(BigDecimal remainsQty) {
		this.remainsQty = remainsQty;
	}

	public BigDecimal getBudgetAmt() {
		return budgetAmt;
	}

	public void setBudgetAmt(BigDecimal budgetAmt) {
		this.budgetAmt = budgetAmt;
	}

	public BigDecimal getBudgetQty() {
		return budgetQty;
	}

	public void setBudgetQty(BigDecimal budgetQty) {
		this.budgetQty = budgetQty;
	}

	public BigDecimal getReservedAmt() {
		return reservedAmt;
	}

	public void setReservedAmt(BigDecimal reservedAmt) {
		this.reservedAmt = reservedAmt;
	}

	public BigDecimal getReservedQty() {
		return reservedQty;
	}

	public void setReservedQty(BigDecimal reservedQty) {
		this.reservedQty = reservedQty;
	}

	public BigDecimal getCommitedAmt() {
		return commitedAmt;
	}

	public void setCommitedAmt(BigDecimal commitedAmt) {
		this.commitedAmt = commitedAmt;
	}

	public BigDecimal getCommitedQty() {
		return commitedQty;
	}

	public void setCommitedQty(BigDecimal commitedQty) {
		this.commitedQty = commitedQty;
	}

	public String getProcessMsg() {
		return processMsg;
	}

	public void setProcessMsg(String processMsg) {
		this.processMsg = processMsg;
	}

	private MAcctElementBudgetControl getBudgetControlID(MBudgetInfo info, int C_Campaign_ID, int C_Project_ID, Trx trx)
	{
		MAcctElementBudgetControl control = null;
		if(C_Campaign_ID!=0)
			control = MAcctElementBudgetControl.get(info.getCtx(), info.getXX_BudgetInfo_ID(), MAcctElementBudgetControl.ELEMENTTYPE_Campaign, trx);
		if(C_Project_ID!=0)
			control = MAcctElementBudgetControl.get(info.getCtx(), info.getXX_BudgetInfo_ID(), MAcctElementBudgetControl.ELEMENTTYPE_Project, trx);
		return control;
	}
	
	public BigDecimal getUsedAmt()
	{
		BigDecimal usedAmt = BigDecimal.ZERO;
		if(getReservedAmt()!=null)
			usedAmt = usedAmt.add(getReservedAmt());
		if(getUnrealizedAmt()!=null)
			usedAmt = usedAmt.add(getUnrealizedAmt());
		if(getRealizedAmt()!=null)
			usedAmt = usedAmt.add(getRealizedAmt());
		if(getPendingAmt()!=null)
			usedAmt = usedAmt.add(getPendingAmt());
		return usedAmt;		
	}


}
