/**
 * 
 */
package rsge.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCurrency;
import org.compiere.model.MJournal;
import org.compiere.model.MPeriod;
import org.compiere.model.X_C_ElementValue;
import org.compiere.model.X_GL_BudgetControl;
import org.compiere.model.X_GL_Journal;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import rsge.model.MBudgetInfo;
import rsge.model.MReservedBudget;
import rsge.model.MReservedBudgetAcct;
import rsge.model.MUnusedBudgetRule;
import rsge.utils.BudgetUtils;
import rsge.utils.CheckBudget;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY R
 *
 */
public class RunUnusedBudgetAllocation extends SvrProcess {

	/** Initialize Variable						*/
	
	/** Date Processed							*/
	private Timestamp 				p_DateProcessed = null;
	/** Budget Calendar							*/
	private int						BudgetCalendar_ID = 0;
	/** Accounting Schema						*/
	private int						C_AcctSchema_ID = 0;
	/** Budget Currency							*/
	private int						C_Currency_ID = 0;
	/** Budget Conversion Type					*/
	private int						C_ConversionType_ID = 0;
	/** Budget Clearing Account 				*/
	private int						BudgetReserved_Acct = 0;
	
	/** Current Year 							*/
	private int 					currentYear_ID = 0;
	/** Current Year Start Date					*/
	private Timestamp				currentYearFirstDate = null;
	/** Current Fiscal Year						*/
	private String					FiscalYear	= null;
	
	/** Current Period No						*/
	private int						currentPeriodNo = 0;
	/** Current Period Start Date				*/
	private Timestamp				currentStartDate = null;
	
	/** Previous Period							*/
	private int						previousPeriod_ID = 0;
	/** Previous Start Date						*/
	private Timestamp				previousEndDate = null;
	
	/** Budget Account List						*/
	private ArrayList<int[]>     	accountList = null;
	/** Account Element							*/
	private int[]					accountElement;
	
	/** Unused Budget Rule						*/
	private int						UnusedBudgetRule_ID = 0;
	/** Reserved Percentage						*/
	private BigDecimal				reservedPercentage = Env.ZERO;
	/** GL Budget 								*/
	private int						GL_Budget_ID = 0;
	
	/** GL Journal 								*/
	private int						GL_Journal_ID = 0;
	
	/** User Element 1							*/
	private String 					userElement1 = null;
	/** User Element 2							*/
	private String 					userElement2 = null;
	/** Budget Info								*/
	private MBudgetInfo 			info = null;
	
	/**
	 * 
	 */
	public RunUnusedBudgetAllocation() {
		// TODO Auto-generated constructor stub
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
			if(name.equals("DateProcessed"))
				p_DateProcessed = (Timestamp) param.getParameter();
		
		info = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), get_Trx());

//		BudgetCalendar_ID = info.getBudgetCalendar_ID();
		BudgetCalendar_ID = info.getReservedBudgetCalendar_ID();

		UnusedBudgetRule_ID = info.getXX_UnusedBudgetRule_ID();
		C_AcctSchema_ID = BudgetUtils.getBudgetAcctSchemaID(getCtx(), getAD_Client_ID(), get_Trx());
		C_ConversionType_ID = BudgetUtils.getConversionTypeID(getAD_Client_ID(), get_Trx());
		BudgetReserved_Acct = info.getReservedBudget_Acct();
		
		MAcctSchema schema = new MAcctSchema(getCtx(), C_AcctSchema_ID, get_Trx());
		C_Currency_ID = schema.getC_Currency_ID();
		
		// Get Accounting Schema User Element
		userElement1 = GeneralEnhancementUtils.getElementColumn(C_AcctSchema_ID, 1, get_Trx());
		userElement2 = GeneralEnhancementUtils.getElementColumn(C_AcctSchema_ID, 2, get_Trx());
		
		MUnusedBudgetRule rule = new MUnusedBudgetRule(getCtx(), UnusedBudgetRule_ID, get_Trx());
		reservedPercentage = (rule.getReservedPercentage()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
		
		X_GL_BudgetControl control = new X_GL_BudgetControl(getCtx(), info.getGL_BudgetControl_ID(), get_Trx());
		GL_Budget_ID = control.getGL_Budget_ID();
		
		// Get Fiscal Year of current date
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT period.PeriodNo, year.C_Year_ID, year.FiscalYear, period.StartDate " +
				"FROM C_Period period " +
				"INNER JOIN C_Year year ON (period.C_Year_ID = year.C_Year_ID) " +
				"WHERE year.C_Calendar_ID = ? " +
				"AND ? BETWEEN period.StartDate AND period.EndDate ");

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, BudgetCalendar_ID);
			pstmt.setTimestamp(2, p_DateProcessed);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				currentPeriodNo = rs.getInt(1);
				currentYear_ID = rs.getInt(2);
				FiscalYear = rs.getString(3);
				currentStartDate = rs.getTimestamp(4);
			}
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		// Get Last Period
		if(currentPeriodNo-1 != 0)
		{
			int periodNo = currentPeriodNo - 1;
			StringBuffer prevPeriod = new StringBuffer();
			prevPeriod.append("SELECT period.C_Period_ID, period.EndDate " +
					"FROM C_Period period " +
					"INNER JOIN C_Year year ON (period.C_Year_ID = year.C_Year_ID) " +
					"WHERE year.C_Calendar_ID = ? " +
					"AND period.PeriodNo = ? ");
			
			pstmt = DB.prepareStatement(prevPeriod.toString(), get_Trx());
			rs = null;
	
			
			try{
				pstmt.setInt(1, BudgetCalendar_ID);
				pstmt.setInt(2, periodNo);
				
				rs = pstmt.executeQuery();
				
				if(rs.next())
				{
					previousPeriod_ID = rs.getInt(1);
					previousEndDate = rs.getTimestamp(2);
				}
				
				rs.close();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}		
		}
		}
		
		// Get current year first date
		currentYearFirstDate = getStartDateYear(currentYear_ID);
		
		accountList = new ArrayList<int[]>();
		
		StringBuffer listOfAccount = new StringBuffer();
		listOfAccount.append("SELECT fa.AD_Org_ID, fa.Account_ID, " +
				"fa.C_Activity_ID, fa.C_Campaign_ID, fa.C_Project_ID, fa.C_SalesRegion_ID," +
				"fa.M_Product_ID ");
		
		if(userElement1!=null)
		{
			listOfAccount.append(", fa.UserElement1_ID ");
		}
		if(userElement2!=null)
		{
			listOfAccount.append(", fa.UserElement2_ID ");
		}
		
		listOfAccount.append("FROM Fact_Acct fa " +
				"WHERE fa.PostingType = 'B' " +
				"AND fa.C_AcctSchema_ID = ? " +
				"AND fa.Account_ID NOT IN (SELECT ua.Account_ID FROM XX_UnreservedAccount ua WHERE ua.XX_UnusedBudgetRule_ID = ?) " +
				"AND fa.Account_ID NOT IN (SELECT vc.Account_ID FROM XX_BudgetInfo bi " +
				"INNER JOIN C_ValidCombination vc ON (vc.C_ValidCombination_ID = bi.BudgetClearing_Acct) " +
				"WHERE fa.AD_Client_ID = vc.AD_Client_ID) " +
				"AND (fa.AmtAcctDr IS NOT NULL OR fa.AmtAcctCr IS NOT NULL) " +
				"AND fa.DateAcct BETWEEN ? AND ? " +
				"GROUP BY fa.AD_Org_ID, fa.Account_ID, fa.C_Activity_ID, fa.C_Campaign_ID, fa.C_Project_ID, fa.C_SalesRegion_ID, fa.M_Product_ID ");
		
		if(userElement1!=null)
		{
			listOfAccount.append(", fa.UserElement1_ID");
		}
		if(userElement2!=null)
		{
			listOfAccount.append(", fa.UserElement2_ID");
		}
		
		PreparedStatement pstmt = DB.prepareStatement(listOfAccount.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, C_AcctSchema_ID);
			pstmt.setInt(2, UnusedBudgetRule_ID);
			pstmt.setTimestamp(3, currentYearFirstDate);
			pstmt.setTimestamp(4, previousEndDate);

			rs = pstmt.executeQuery();
			while(rs.next())
			{
				int x = 7;
				if(userElement1!=null || userElement2!=null)
				{
					x = 9;
				}
				
				accountElement = new int[x];
				
				accountElement[0] = rs.getInt(1);
				accountElement[1] = rs.getInt(2);
				accountElement[2] = rs.getInt(3);
				accountElement[3] = rs.getInt(4);
				accountElement[4] = rs.getInt(5);
				accountElement[5] = rs.getInt(6);
				accountElement[6] = rs.getInt(7);
				
				if(userElement1!=null)
				{
					accountElement[7] = rs.getInt(8);
				}
				if(userElement2!=null)
				{
					accountElement[8] = rs.getInt(9);
				}			
				
				accountList.add(accountElement);
			}
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}// End PrepareIt
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// Return if previos period no is 0
		if(currentPeriodNo-1 == 0)
			return "";
		
		// Return if there is already Reserved Budget record with previous period ID
		String sql = "SELECT 1 " +
				"FROM XX_ReservedBudget " +
				"WHERE C_Period_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, previousPeriod_ID);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				return "";
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}// End Tet
		
		int AD_Org_ID = 0;
		int XX_ReservedBudget_ID = 0;		
		
		BigDecimal totalSourceDr = null;
		BigDecimal totalSourceCr = null;
		
		for(int x = 0; x < accountList.size(); x++)
		{
			int a[] = accountList.get(x);
			
			int Account_ID = a[1];
			int C_Activity_ID = a[2];
			int C_Campaign_ID = a[3];
			int C_Project_ID = a[4];
			int C_SalesRegion_ID = a[5];
			int M_Product_ID = a[6];
			
			int element1 = 0;
			int element2 = 0;
			
			if(userElement1!=null)
			{
				element1 = a[7];
			}
			if(userElement2!=null)
			{
				element1 = a[8];
			}
			
			//Check if an account has remaining budget. If not, skip
			CheckBudget check = new CheckBudget(getCtx(), false, a[0], Account_ID, previousEndDate,false, info.getBudgetCalendar_ID(),
					C_Activity_ID, 0, C_Campaign_ID, C_Project_ID, M_Product_ID, element1, element2, get_Trx());
			
			BigDecimal remainingBudget = check.getRemainingBudget();			

			if(remainingBudget.compareTo(BigDecimal.ZERO)==1)
			{
				if(AD_Org_ID != a[0])
				{
					if(XX_ReservedBudget_ID != 0)
					{
						processDoc(AD_Org_ID, XX_ReservedBudget_ID, C_Activity_ID, C_Campaign_ID, C_Project_ID, C_SalesRegion_ID, M_Product_ID,  
								totalSourceDr, totalSourceCr);
					}
					AD_Org_ID = a[0];
					XX_ReservedBudget_ID = createReservedBudget(AD_Org_ID);
					totalSourceDr = BigDecimal.ZERO;
					totalSourceCr = BigDecimal.ZERO;
				}
				MReservedBudgetAcct rba = new MReservedBudgetAcct(getCtx(), 0, get_Trx());
				rba.setXX_ReservedBudget_ID(XX_ReservedBudget_ID);
				rba.setAD_Org_ID(AD_Org_ID);
				rba.setAccount_ID(Account_ID);
				rba.setC_Activity_ID(C_Activity_ID);
				rba.setC_Campaign_ID(C_Campaign_ID);
				rba.setC_Project_ID(C_Project_ID);
				rba.setC_SalesRegion_ID(C_SalesRegion_ID);
				rba.setM_Product_ID(M_Product_ID);
				rba.setRemainingBudget(remainingBudget);				
				
				// Calculate Reserved Budget
				BigDecimal reservedBudget = remainingBudget.multiply(reservedPercentage);
				MCurrency currency = new MCurrency(getCtx(), C_Currency_ID, get_Trx());
				
				reservedBudget = reservedBudget.setScale(currency.getStdPrecision(), RoundingMode.HALF_UP);
				rba.setReservedBudgetAmt(reservedBudget);

				String accountSign = GeneralEnhancementUtils.getAccountNaturalSign(getCtx(), Account_ID, get_Trx());
				BigDecimal AmtSourceDr = BigDecimal.ZERO;
				BigDecimal AmtSourceCr = BigDecimal.ZERO;
				
				// Allocate Reserved Amount to Source Amount by using different sign than natural sign of an account
				if(accountSign.equals(X_C_ElementValue.ACCOUNTSIGN_Debit))
				{
					AmtSourceCr = reservedBudget;
					totalSourceCr = totalSourceCr.add(AmtSourceCr);
				}
				else if(accountSign.equals(X_C_ElementValue.ACCOUNTSIGN_Credit))
				{
					AmtSourceDr = reservedBudget;
					totalSourceDr = totalSourceDr.add(AmtSourceDr);
				}					
				
				if(rba.save())
					{
						if((userElement1!=null) && (GeneralEnhancementUtils.checkColumn("XX_ReservedBudgetAcct", userElement1, get_Trx())))
						{
							updateUserElement("XX_ReservedBudgetAcct", rba.getXX_ReservedBudgetAcct_ID(), userElement1, element1);
						}
						if((userElement2!=null) && (GeneralEnhancementUtils.checkColumn("XX_ReservedBudgetAcct", userElement2, get_Trx())))
						{
							updateUserElement("XX_ReservedBudgetAcct", rba.getXX_ReservedBudgetAcct_ID(), userElement2, element2);
						}
						GeneralEnhancementUtils.createGLJournalLine(getCtx(), getAD_Client_ID(), AD_Org_ID, GL_Journal_ID, C_AcctSchema_ID, Account_ID, C_ConversionType_ID, C_Currency_ID, currentStartDate, "", 
								AmtSourceDr, AmtSourceCr, 0, M_Product_ID, 0, 0, 0, 0, C_SalesRegion_ID, C_Project_ID, C_Campaign_ID, C_Activity_ID, 0, 0, element1, element2, get_Trx());
					}								
			}
			if(x == (accountList.size()-1)) // Process Last Document
			{
				processDoc(AD_Org_ID, XX_ReservedBudget_ID, C_Activity_ID, C_Campaign_ID, C_Project_ID, C_SalesRegion_ID, M_Product_ID,  
						totalSourceDr, totalSourceCr);
			}
		}
		return "";
	}
	
	/**
	 * Create Reserved Budget header
	 * @return XX_ReservedBudget_ID
	 */
	private int createReservedBudget(int AD_Org_ID)
	{
		int XX_ReservedBudget_ID = 0;
		
		MReservedBudget rb = new MReservedBudget(getCtx(), 0, get_Trx());
		rb.setAD_Org_ID(AD_Org_ID);
		rb.setC_Period_ID(previousPeriod_ID);
		rb.setDateTrx(currentStartDate);
		
		MPeriod p = new MPeriod(getCtx(), previousPeriod_ID, get_Trx());
		
		rb.setDescription("Reserved Budget for " + p.getName() +
				" of " + FiscalYear);
		rb.setGL_Budget_ID(GL_Budget_ID);
		
		if(rb.save())
		{
			XX_ReservedBudget_ID = rb.getXX_ReservedBudget_ID();
		}
		
		// Create GL Journal
		GL_Journal_ID = GeneralEnhancementUtils.createGLJournal(getCtx(), getAD_Client_ID(), AD_Org_ID, 0, C_AcctSchema_ID, "Reserved Budget #" + rb.getDocumentNo(), X_GL_Journal.POSTINGTYPE_Budget, 
				GL_Budget_ID, currentStartDate, currentStartDate, C_Currency_ID, C_ConversionType_ID, get_Trx());
		
		return XX_ReservedBudget_ID;
	}
	
	private void createBudgetReservedLine(int AD_Org_ID, int Account_ID, BigDecimal AmtSourceDr, BigDecimal AmtSourceCr, int C_Activity_ID, int C_Campaign_ID,
			int C_Project_ID, int C_SalesRegion_ID, int M_Product_ID)
	{
	
		MJournal journal = new MJournal(getCtx(), GL_Journal_ID, get_Trx());
		
		String Description = "Budget Reserved for GL Journal " + journal.getDocumentNo();
		
		GeneralEnhancementUtils.createGLJournalLine(getCtx(), getAD_Client_ID(), AD_Org_ID, GL_Journal_ID, C_AcctSchema_ID, Account_ID, C_ConversionType_ID, C_Currency_ID, currentStartDate, Description, 
				AmtSourceDr, AmtSourceCr, 0, M_Product_ID, 0, 0, 0, 0, C_SalesRegion_ID, C_Project_ID, C_Campaign_ID, C_Activity_ID, 0, 0, 0, 0, get_Trx());		
	}
	
	private void processDoc(int AD_Org_ID, int XX_ReservedBudget_ID, int C_Activity_ID, int C_Campaign_ID, int C_Project_ID, int C_SalesRegion_ID, int M_Product_ID, 
			BigDecimal totalSourceDr, BigDecimal totalSourceCr)
	{
		// Get Budget Clearing Amount
		if(totalSourceDr.compareTo(totalSourceCr) == 1) // if Dr is bigger than Cr
		{
			BigDecimal crAmount = totalSourceDr.subtract(totalSourceCr);
			MAccount account = new MAccount(getCtx(), BudgetReserved_Acct, get_Trx());
			createBudgetReservedLine(AD_Org_ID, account.getAccount_ID(), BigDecimal.ZERO, crAmount, C_Activity_ID, C_Campaign_ID, C_Project_ID, C_SalesRegion_ID, M_Product_ID);
		}
		else if(totalSourceCr.compareTo(totalSourceDr) == 1) // if Cr is bigger than Dr
		{
			BigDecimal drAmount = totalSourceCr.subtract(totalSourceDr);
			MAccount account = new MAccount(getCtx(), BudgetReserved_Acct, get_Trx());
			createBudgetReservedLine(AD_Org_ID, account.getAccount_ID(), drAmount, BigDecimal.ZERO, C_Activity_ID, C_Campaign_ID, C_Project_ID, C_SalesRegion_ID, M_Product_ID);
		}
		GeneralEnhancementUtils.processGLJournal(GL_Journal_ID, get_Trx());
		
		// Update Reserved Budget
		MReservedBudget rb = new MReservedBudget(getCtx(), XX_ReservedBudget_ID, get_Trx());
		rb.setGL_Journal_ID(GL_Journal_ID);
		rb.setProcessed(true);
		rb.save();
	}
	
	/**
	 * Get Start Date of a year
	 * @param C_Year_ID
	 * @return
	 */
	private Timestamp getStartDateYear(int C_Year_ID)
	{
		Timestamp startDate = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT p.StartDate " +
				"FROM C_Period p " +
				"WHERE p.C_Year_ID = ? " +
				"AND p.StartDate = (SELECT Min(p2.StartDate) FROM C_Period p2 WHERE p.C_Year_ID = p2.C_Year_ID) ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, currentYear_ID);	
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				startDate = rs.getTimestamp(1);
			}
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return startDate;
	}	
	
	private void updateUserElement(String tableName, int tableID, String element1, int elementValue2)
	{
		StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
		sql.append(element1 + " = " + elementValue2 + " WHERE " + tableName + "_ID = ? ");
		
		DB.executeUpdate(get_Trx(), sql.toString(), elementValue2);		
		
	}

}
