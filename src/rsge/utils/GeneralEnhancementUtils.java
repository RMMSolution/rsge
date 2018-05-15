/**
 * 
 */
package rsge.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.MElementValue;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalLine;
import org.compiere.model.MPeriod;
import org.compiere.model.MPriceList;
import org.compiere.model.MYear;
import org.compiere.model.X_AD_Tree;
import org.compiere.model.X_C_ElementValue;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.model.MBudgetInfo;
import rsge.model.MPurchaseRequisition;
import rsge.model.MPurchaseRequisitionLine;

/**
 * @author FANNY
 *
 */
public class GeneralEnhancementUtils {

	/** Ctx and Trx						*/
	Ctx 						ctx = null;
	Trx							trx = null;
	
	/**
	 * 
	 */
	public GeneralEnhancementUtils (Ctx ctx, Trx trx) {
		this.ctx = ctx;
		this.trx = trx;
	}
	
	/**
	 * Get next period for Budget Planning
	 */
	public static int getNextPeriod(Ctx ctx, int currentPeriod_ID, String PlanningPeriod, Trx trx)
	{
		int nextPeriod_ID = 0;
		int numberOfPeriod = 0;
		int periodNo = 0;		
		
		// Get number of periods in one planning period. Not include current period itself
		if(PlanningPeriod.equals("M"))
			numberOfPeriod = 1;
		else if(PlanningPeriod.equals("Q"))
			numberOfPeriod = 3;
		else if(PlanningPeriod.equals("S"))
			numberOfPeriod = 6;
		 
		//Get Period no of currentPeriod_ID
		MPeriod p = new MPeriod(ctx, currentPeriod_ID, trx);
		periodNo = p.getPeriodNo();
		
		// Get maximum period of year
		MYear y = new MYear(ctx, p.getC_Year_ID(), trx);
		int maxPeriod = 0;
		
		String sql = "SELECT p.PeriodNo " +
				"FROM C_Period p " +
				"INNER JOIN C_Year y ON (p.C_Year_ID = y.C_Year_ID) " +
				"WHERE y.FiscalYear = ? " + // 1
				"AND y.C_Calendar_ID = ? " + // 2
				"AND p.PeriodNo = (SELECT MAX(p2.PeriodNo) FROM C_Period p2 " +
				"WHERE p2.C_Year_ID = p.C_Year_ID) ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			pstmt.setString(1, y.getFiscalYear());
			pstmt.setInt(2, y.getC_Calendar_ID());
			rs = pstmt.executeQuery();			
			if(rs.next())
				maxPeriod = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}	
		// End get maximum period of year
		
		if((periodNo+numberOfPeriod) <= maxPeriod) // Get next period of current year
		{
			// Get next period of same year
			sql = "SELECT p.C_Period_ID " +
					"FROM C_Period p " +
					"WHERE p.C_Year_ID = ? " + //1
					"AND p.PeriodNo = ? "; //2
			
			pstmt = DB.prepareStatement(sql, trx);
			rs = null;

			try{
				pstmt.setInt(1, p.getC_Year_ID());
				pstmt.setInt(2, (periodNo+numberOfPeriod));

				rs = pstmt.executeQuery();
				
				if(rs.next())
				{
					nextPeriod_ID = rs.getInt(1);
				}			
				rs.close();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}			
		}
		else // Get Next Period of next year
		{
			int nextYearPeriod = (periodNo+numberOfPeriod) - maxPeriod;
			int currentYear = Integer.valueOf(y.getFiscalYear());	
			String nextYear = Integer.toString(currentYear + 1);

			sql = "SELECT p.C_Period_ID " +
					"FROM C_Period p " +
					"INNER JOIN C_Year y ON (p.C_Year_ID = y.C_Year_ID) " +
					"WHERE y.FiscalYear = ? " + // 1
					"AND y.C_Calendar_ID = ? " + // 2
					"AND p.PeriodNo = ? ";
			
			pstmt = DB.prepareStatement(sql, trx);
			ResultSet rs2 = null;
			
			try{
				pstmt.setString(1, nextYear);
				pstmt.setInt(2, y.getC_Calendar_ID());
				pstmt.setInt(3, nextYearPeriod);

				rs2 = pstmt.executeQuery();
				
				if(rs2.next())
				{
					nextPeriod_ID = rs2.getInt(1);
				}			
				rs2.close();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}						
		}	
		
		return nextPeriod_ID;
	}

	/**
	 * Get Valid Combination based on selected account
	 * 
	 * @param Account_ID
	 * @param C_Activity_ID
	 * @param C_Project_ID
	 * @param C_Campaign_ID
	 * @param M_Product_ID
	 * @param C_SalesRegion_ID
	 * @return
	 */
	public int getValidCombination(int Account_ID, int AD_Org_ID, int C_Activity_ID, int C_Project_ID, int C_Campaign_ID, int M_Product_ID, int C_SalesRegion_ID)
	{
		int ValidCombination_ID = 0;
		
		String sql = "SELECT C_ValidCombination_ID " +
				"FROM C_ValidCombination " +
				"WHERE AD_Client_ID = ? " +
				"AND AD_Org_ID = ? ";
		
		if(C_Activity_ID!=0)
		{
			sql = sql + "AND C_Activity_ID = " + C_Activity_ID + " ";
		}
		else 
		{
			sql = sql + "AND (C_Activity_ID = 0 OR C_Activity_ID IS NULL) ";
		}
		if(C_Project_ID!=0)
		{
			sql = sql + "AND C_Project_ID = " + C_Project_ID + " ";
		}
		else
		{
			sql = sql + "AND (C_Project_ID = 0 OR C_Project_ID IS NULL) ";
		}
		if(C_Campaign_ID!=0)
		{
			sql = sql + "AND C_Campaign_ID = " + C_Campaign_ID + " ";
		}
		else
		{
			sql = sql + "AND (C_Campaign_ID = 0 OR C_Campaign_ID IS NULL) ";
		}
		if(M_Product_ID!=0)
		{
			sql = sql + "AND M_Product_ID = " + M_Product_ID + " ";
		}
		else
		{
			sql = sql + "AND (M_Product_ID = 0 OR M_Product_ID IS NULL) ";
		}
		if(C_SalesRegion_ID!=0)
		{
			sql = sql + "AND C_SalesRegion_ID = " + C_SalesRegion_ID + " ";
		}
		else
		{
			sql = sql + "AND (C_SalesRegion_ID = 0 OR C_SalesRegion_ID IS NULL) ";
		}

		PreparedStatement pstmt = DB.prepareStatement(sql, (Trx)null);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, ctx.getAD_Client_ID());
			pstmt.setInt(2, AD_Org_ID);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				ValidCombination_ID = rs.getInt(1);
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		if(ValidCombination_ID == 0) // Create new valid combination if not exists
		{
			MAccount vc = new MAccount(ctx, 0, trx);
					
			vc.setAccount_ID(Account_ID);
			vc.setAD_Client_ID(ctx.getAD_Client_ID());
			vc.setAD_Org_ID(AD_Org_ID);
			
			if(C_Activity_ID != 0)
			{
				vc.setC_Activity_ID(C_Activity_ID);
			}
			if(C_Project_ID != 0)
			{
				vc.setC_Project_ID(C_Project_ID);
			}
			if(C_Campaign_ID != 0)
			{
				vc.setC_Campaign_ID(C_Campaign_ID);
			}
			if(M_Product_ID != 0)
			{
				vc.setM_Product_ID(M_Product_ID);
			}
			if(C_SalesRegion_ID != 0)
			{
				vc.setC_SalesRegion_ID(C_SalesRegion_ID);
			}
		
			if(vc.save())
			{
				ValidCombination_ID = vc.getC_ValidCombination_ID();
			}
		}
		return ValidCombination_ID;
	}
	
	/**
	 * Create GL Journal
	 * 
	 * @param AD_Org_ID
	 * @param GL_JournalBatch_ID
	 * @param C_AcctSchema_ID
	 * @param Description
	 * @param PostingType
	 * @param GL_Budget_ID
	 * @param DateDoc
	 * @param DateAcct
	 * @param C_Period_ID
	 * @param C_Currency_ID
	 * @param C_ConversionType_ID
	 * @return
	 */
	public static int createGLJournal(Ctx ctx, int AD_Client_ID, int AD_Org_ID, int GL_JournalBatch_ID, int C_AcctSchema_ID, String Description,
			String PostingType, int GL_Budget_ID, Timestamp DateDoc, Timestamp DateAcct, int C_Currency_ID, int C_ConversionType_ID, Trx trx)
	{
		int GL_Journal_ID = 0;
		MJournal journal = new MJournal(ctx, 0, trx);
		
		if(GL_JournalBatch_ID != 0) // Set GL Journal Batch ID
			journal.setGL_JournalBatch_ID(GL_JournalBatch_ID);
		journal.setAD_Org_ID(AD_Org_ID);
		journal.setC_AcctSchema_ID(C_AcctSchema_ID);
		journal.setDescription(Description);
		journal.setPostingType(PostingType);
		if(GL_Budget_ID != 0)
			journal.setGL_Budget_ID(GL_Budget_ID);
		
		int C_DocType_ID = 0;
		
		String sql = "SELECT C_DocType_ID " +
		"FROM C_DocType " +
		"WHERE C_DocType.DocBaseType='GLJ' " +
		"AND AD_Client_ID = " + AD_Client_ID;

		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;

		try{
			rs = pstmt.executeQuery();
	
			if(rs.next())
			{
				C_DocType_ID = rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} 
		journal.setC_DocType_ID(C_DocType_ID);
		// End Get C_DocType_ID
		
		// Get GL_Category
		int GL_Category_ID = 0;
		
		sql = "SELECT GL_Category_ID " +
				"FROM GL_Category " +
				"WHERE CategoryType='M' " +
				"AND AD_Client_ID IN (?, 0) " + //1
				"ORDER BY AD_Client_ID DESC ";
		pstmt = DB.prepareStatement(sql, trx);
		rs = null;
		
		try{
			pstmt.setInt(1, ctx.getAD_Client_ID()); 
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				GL_Category_ID = rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		journal.setGL_Category_ID(GL_Category_ID);
		 // End Get GL_Category_ID
		
		journal.setDateDoc(DateDoc);
		journal.setDateAcct(DateAcct);
		
		int C_Period_ID = getFinancialPeriod(AD_Client_ID, DateAcct, trx);
		journal.setC_Period_ID(C_Period_ID);
		journal.setC_Currency_ID(C_Currency_ID);
		journal.setC_ConversionType_ID(C_ConversionType_ID);

		// Get Accounting Schema's Currency
		MAcctSchema schema = new MAcctSchema(ctx, C_AcctSchema_ID, trx);
		int acctSchemaCurrency_ID = schema.getC_Currency_ID();
		
		if(acctSchemaCurrency_ID != C_Currency_ID)
		{
			BigDecimal rate = MConversionRate.getRate(C_Currency_ID, acctSchemaCurrency_ID, DateAcct, C_ConversionType_ID, ctx.getAD_Client_ID(), AD_Org_ID);
			journal.setCurrencyRate(rate);
		}
		else journal.setCurrencyRate(BigDecimal.ONE);
		
		if(journal.save())
		{
			GL_Journal_ID = journal.getGL_Journal_ID();
		}
		return GL_Journal_ID;
	}
	// End Create GL Journal

	public static int createGLJournalLine(Ctx ctx, int AD_Client_ID, int AD_Org_ID, int GL_Journal_ID, int C_AcctSchema_ID, int Account_ID, int C_ConversionType_ID, int C_Currency_ID, 
			Timestamp DateAcct, String Description, BigDecimal AmtSourceDr, BigDecimal AmtSourceCr, 
			int C_SubAccount_ID, int M_Product_ID, int C_BPartner_ID,
			int AD_OrgTrx_ID, int C_LocFrom_ID, int C_LocTo_ID, int C_SalesRegion_ID, int C_Project_ID, int C_Campaign_ID, int C_Activity_ID, 
			int User1_ID, int User2_ID, int UserElement1_ID, int UserElement2_ID, Trx trx)
	{
		int GL_JournalLine_ID = 0;
		
		MJournalLine jl = new MJournalLine(ctx, 0, trx);
		jl.setGL_Journal_ID(GL_Journal_ID);
		jl.setAD_Org_ID(AD_Org_ID);
		jl.setLine(getDocumentLine("GL_Journal", "GL_JournalLine", GL_Journal_ID, trx));
		
		// Get Valid combination
		MAcctSchema acs = new MAcctSchema(ctx, C_AcctSchema_ID, trx);
		MAccount account = new MAccount(acs);
		
		account = MAccount.get(ctx, AD_Client_ID, AD_Org_ID, C_AcctSchema_ID, Account_ID, C_SubAccount_ID, M_Product_ID, C_BPartner_ID, AD_OrgTrx_ID, 
				C_LocFrom_ID, C_LocTo_ID, C_SalesRegion_ID, C_Project_ID, C_Campaign_ID, C_Activity_ID, User1_ID, User2_ID, UserElement1_ID, UserElement1_ID);
						
		jl.setC_ValidCombination_ID(account.getC_ValidCombination_ID());
		jl.setC_ConversionType_ID(C_ConversionType_ID);
		jl.setC_Currency_ID(C_Currency_ID);
		jl.setDateAcct(DateAcct);
		
		// Set Description - If null, description is set to Account Description
		if(Description == null || Description == "")
		{
			Description = account.getDescription();
		}
		else jl.setDescription(Description);
		
		jl.setIsGenerated(true);
		
		// Get Accounting Schema's Currency
		MAcctSchema schema = new MAcctSchema(ctx, C_AcctSchema_ID, trx);
		int acctSchemaCurrency_ID = schema.getC_Currency_ID();
		
		BigDecimal currencyRate;
		
		if(acctSchemaCurrency_ID != C_Currency_ID)
		{
			currencyRate = MConversionRate.getRate(C_Currency_ID, acctSchemaCurrency_ID, DateAcct, C_ConversionType_ID, ctx.getAD_Client_ID(), AD_Org_ID);
		}
		currencyRate = BigDecimal.ONE;

		jl.setCurrencyRate(currencyRate);

		// Set Amount Debit
		if(AmtSourceDr == null)
			AmtSourceDr = BigDecimal.ZERO;
		jl.setAmtSourceDr(AmtSourceDr);
		jl.setAmtAcctDr(AmtSourceDr.multiply(currencyRate));
		
		// Set Amount Credit
		if(AmtSourceCr == null)
			AmtSourceCr = BigDecimal.ZERO;
		jl.setAmtSourceCr(AmtSourceCr);
		jl.setAmtAcctCr(AmtSourceCr.multiply(currencyRate));
		
		if(jl.save())
		{
			GL_JournalLine_ID = jl.getGL_JournalLine_ID();
		}
		
		// Update GL Journal
		String sql = "UPDATE GL_Journal j " +
			"SET j.TotalDr = (SELECT SUM(AmtSourceDr) " +
			"FROM GL_JournalLine jl1 " +
			"WHERE jl1.GL_Journal_ID = j.GL_Journal_ID), " +
			"j.TotalCr = (SELECT SUM(AmtSourceCr) " +
			"FROM GL_JournalLine jl2 " +
			"WHERE jl2.GL_Journal_ID = j.GL_Journal_ID) " +
			"WHERE j.GL_Journal_ID = ? ";

		PreparedStatement pstmt = DB.prepareStatement(sql, trx);

		try{
			pstmt.setInt(1, GL_Journal_ID);
			pstmt.executeUpdate();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return GL_JournalLine_ID;
	}
	
	public static String getAccountNaturalSign(Ctx ctx, int Account_ID, Trx trx)
	{
		String accountSign = null;
		
		MElementValue ev = new MElementValue(ctx, Account_ID, trx);
		String accountType = ev.getAccountType();
		
		if((accountType.equals(X_C_ElementValue.ACCOUNTTYPE_Asset))
				|| (accountType.equals(X_C_ElementValue.ACCOUNTTYPE_Expense)))
		{
			accountSign = X_C_ElementValue.ACCOUNTSIGN_Debit;
		}
		else if((accountType.equals(X_C_ElementValue.ACCOUNTTYPE_Liability))
				|| (accountType.equals(X_C_ElementValue.ACCOUNTTYPE_OwnerSEquity))
				|| (accountType.equals(X_C_ElementValue.ACCOUNTTYPE_Revenue)))
		{
			accountSign = X_C_ElementValue.ACCOUNTSIGN_Credit;
		}
		else // For Memo only
		{
			accountSign = ev.getAccountSign();
		}
		
		return accountSign;
	}
	
	public static boolean processGLJournal(int GL_Journal_ID, Trx trx)
	{
		// Update GL Journal Line to processed
		String sql = "UPDATE GL_JournalLine " +
				"SET Processed = 'Y' " +
				"WHERE GL_Journal_ID = ? " ;
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, GL_Journal_ID);
			rs = pstmt.executeQuery();			
		}catch (Exception e) {
			e.printStackTrace();
		}		
		
		// Update GL Journal - Override workflow
		sql = "UPDATE GL_Journal " +
				"SET DocAction = 'CL', DocStatus = 'CO', Processed = 'Y', Processing = 'Y', IsApproved = 'Y' " +
				"WHERE GL_Journal_ID = ? ";
		
		pstmt = DB.prepareStatement(sql, trx);
		rs = null;
		
		try{
			pstmt.setInt(1, GL_Journal_ID);
			rs = pstmt.executeQuery();			
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}		

		return true;
	}
	
	/**
	 * Set Document Controlled to N
	 * @param Account_ID
	 */
	public void unlockAccount(int Account_ID)
	{			
		MElementValue ev = new MElementValue(ctx, Account_ID, trx);
		ev.setIsDocControlled(false);
		ev.save();		
	}
	
	/**
	 * Set Document Controlled to Y
	 * @param Account_ID
	 */
	public void lockAccount(int Account_ID)
	{			
		MElementValue ev = new MElementValue(ctx, Account_ID, trx);
		ev.setIsDocControlled(true);
		ev.save();		
	}
	
	public static int getDocumentLine(String parentTableName, String tableName, int parentRecordID, Trx trx)
	{
		int line = 0;
					
		String sql = "SELECT NVL(MAX(Line),0)+10 AS DefaultValue " +
				"FROM " + tableName + // tableName
				" WHERE " + parentTableName + "_ID = " 
				+ parentRecordID;
			
			PreparedStatement pstmt = DB.prepareStatement(sql, trx);
			ResultSet rs = null;
			
			try{
				rs = pstmt.executeQuery();
				if(rs.next())
					line = rs.getInt(1);
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				DB.closeResultSet(rs);
				DB.closeStatement(pstmt);
			}	
		
		return line;
	}
	
	public static int getMaxPeriodYear(int C_Period_ID, Trx trx)
	{
		int maxPeriod = 0;
		String sql = "SELECT MAX(PeriodNo) FROM C_Period p1 " +
				"WHERE p1.C_Year_ID IN (SELECT p2.C_Year_ID FROM C_Period p2 " +
				"WHERE p2.C_Period_ID = ?) ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, C_Period_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				maxPeriod = rs.getInt(1);			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return maxPeriod;
	}
	
	public static int getFinancialPeriod(int AD_Client_ID, Timestamp DateTrx, Trx trx)
	{
		int C_Period_ID = 0;
		
		String sql = "SELECT p.C_Period_ID " +
				"FROM C_Period p " +
				"INNER JOIN C_Year y ON (p.C_Year_ID = y.C_Year_ID) " +
				"INNER JOIN AD_ClientInfo ci ON (ci.C_Calendar_ID = y.C_Calendar_ID) " +
				"WHERE ci.AD_Client_ID = ? " +
				"AND ? BETWEEN p.StartDate AND p.EndDate ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setTimestamp(2, DateTrx);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				C_Period_ID = rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}	
		return C_Period_ID;
	}
	
	/**
	 * Calculate Total Amount
	 * @param price
	 * @param qty
	 * @param C_Currency_ID
	 * @return
	 */
	public static BigDecimal calculateTotalAmt(Ctx ctx, BigDecimal price, int qty, int C_Currency_ID, Trx trx)
	{
		BigDecimal totalAmt = BigDecimal.ZERO;
		
		MCurrency currency = new MCurrency(ctx, C_Currency_ID, trx);
		totalAmt = (price.multiply(new BigDecimal(qty))).setScale(currency.getStdPrecision(), RoundingMode.HALF_UP);
		
		return totalAmt;		
	}	
	

	
	/**
	 * Get Budget Info of selected Client
	 * @param AD_Client_ID
	 * @return
	 */
	public int getXX_BudgetInfo_ID(int AD_Client_ID)
	{
		int ID = 0;
		
		String sql = "SELECT XX_BudgetInfo_ID " +
		"FROM XX_BudgetInfo " +
		"WHERE AD_Client_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;

		try{
				pstmt.setInt(1, AD_Client_ID);
	
				rs = pstmt.executeQuery();
	
				if(rs.next())
				{
					ID = rs.getInt(1);
				}
		}catch (Exception e) {
			System.out.println("No Budget Info found. Make sure General Setup model has been completed");;
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}			
		return ID;
	}
	
	/**
	 * Get User Element Column Name from Accounting Schema Element
	 * @return
	 */
	public static String getElementColumn(int C_AcctSchema_ID, int elementNo, Trx trx)
	{
		String columnName = null;
		
		StringBuilder sql = new StringBuilder("SELECT ac.ColumnName " +
				"FROM C_AcctSchema_Element ae " +
				"INNER JOIN AD_Column ac ON (ae.AD_Column_ID = ac.AD_Column_ID) " +
				"WHERE ae.C_AcctSchema_ID = ? " +
				"AND ae.ElementType = 'X").append(Integer.toString(elementNo)+"' ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, C_AcctSchema_ID);			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				columnName = rs.getString(1);
			}
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return columnName;
	}
	
	/**
	 * Get value of User Element from specific record
	 * @param columnName
	 * @param tableName
	 * @param tableID
	 * @return
	 */
	public static int getElementValue(String columnName, String tableName, int tableID, Trx trx)
	{
		int value = 0;
		
		StringBuilder sql = new StringBuilder("SELECT ");
		sql.append(columnName);
		sql.append(" FROM " + tableName);
		sql.append(" WHERE " + tableName + "_ID = ? ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, tableID);
			
			rs = pstmt.executeQuery();
			
			if(!rs.next())
			{
				value = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	/**
	 * Check if element column is exists in target table	 * 
	 * @return
	 */
	public static boolean checkColumn(String tableName, String columnName, Trx trx)
	{
		StringBuilder sql = new StringBuilder("SELECT 1 FROM AD_Column c " +
				"INNER JOIN AD_Table t ON (c.AD_Table_ID = t.AD_Table_ID) " +
				"WHERE t.TableName = ");
		sql.append("'"+ tableName +"' ");
		sql.append("AND c.ColumnName = '" + columnName + "' " );
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			
			if(!rs.next())
			{
				return false;
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public static void updateElementValue(String tableName, int tableID, boolean element1Exists, String element1, Integer element1Value, 
			boolean element2Exists, String element2, Integer element2Value, Trx trx)
	{
		StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
		
		
		if((element1Exists && element1Value > 0)&& (element2Exists && element2Value > 0))
		{
			sql.append(element1 +" = " + element1Value + ", " + element2 + " = " + element2Value + " ");
		}
		else if((element1Exists && element1Value > 0) && !element2Exists)
		{
			sql.append(element1 +" = " + element1Value + " ");
		}
		else if(!element1Exists && (element2Exists && element2Value > 0))
		{
			sql.append(element2 +" = " + element2Value + " ");
		}
		else return;
		
		sql.append("WHERE " + tableName + "_ID = ? ");
		
		DB.executeUpdate(trx, sql.toString(), tableID);		
		return;
	}	
	
	public static int getDocLines(String parentTable, String childTable, int parentID, Trx trx)
	{
		int line = 0;
		
		String parentColumn = parentTable.concat("_ID");
		StringBuilder sql = new StringBuilder("SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM ");
		sql.append(childTable + " WHERE ");
		sql.append(parentColumn + " = ? ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, parentID);
			rs = pstmt.executeQuery();
			if(rs.next())
				line = rs.getInt(1);			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}

	public static MBudgetInfo getBudgetInfo(Ctx ctx, int AD_Client_ID, Trx trx)
	{
		MBudgetInfo info = null;
		String sql = "SELECT * FROM XX_BudgetInfo " +
				"WHERE AD_Client_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Client_ID);	
			rs = pstmt.executeQuery();	
			if(rs.next())
				info = new MBudgetInfo(ctx, rs, trx);
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}			

		return info;
	}

	/**
	 * Get Fiscal Period based on period given (period is not fiscal period stated in Client info)
	 * @param ctx
	 * @param C_Period_ID
	 * @param AD_Client_ID
	 * @param trx
	 * @return
	 */
	public static int getFiscalPeriodByPeriod(Ctx ctx, int C_Period_ID, int AD_Client_ID, Trx trx)
	{
		int periodID = 0;		
		// Get period start date
		MPeriod period = new MPeriod(ctx, C_Period_ID, trx);
		periodID = getFiscalPeriodByDate(ctx, period.getStartDate(), AD_Client_ID, trx);
		return periodID;
	}
	
	/**
	 * Get Fiscal Period Based on date given
	 * @param ctx
	 * @param periodDate
	 * @param AD_Client_ID
	 * @param trx
	 * @return
	 */
	public static int getFiscalPeriodByDate(Ctx ctx, Timestamp periodDate, int AD_Client_ID, Trx trx)
	{
		int periodID = 0;
		
		String sql = "SELECT p.C_Period_ID " +
				"FROM C_Period p " +
				"INNER JOIN C_Year y ON (p.C_Year_ID = y.C_Year_ID) " +
				"INNER JOIN AD_ClientInfo ci ON (y.C_Calendar_ID = y.C_Calendar_ID) " +
				"WHERE ci.AD_Client_ID = ? " +
				"AND ? BETWEEN p.StartDate AND p.EndDate ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs =null;
		
		try{
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setTimestamp(2, periodDate);
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
	
	public static boolean checkRequisitionBasedOnActivity(int AD_Client_ID, Trx trx)
	{
		String sql = "SELECT IsRequisitionBasedonActivity " +
		"FROM XX_GeneralSetup " +
		"WHERE AD_Client_ID = ? ";

		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;

		try{
			pstmt.setInt(1, AD_Client_ID);
			rs = pstmt.executeQuery();
	
			if(rs.next())
			{
				if(rs.getString(1).equalsIgnoreCase("N"))
				return false;
			}    			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
	
	// Reset Calendar (Hour, Minute, Second, Milisecond to 0
	public static Calendar resetDate(Calendar cal)
	{
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}	
	
	public static boolean updateEntityParent(String treeType, int node_ID, int parent_ID, int AD_Client_ID, Trx trx)
	{
		StringBuilder update = new StringBuilder("UPDATE ");	
		String clientTree = null;
		if(treeType.equals(X_AD_Tree.TREETYPE_Product))
		{
			update.append("AD_TreeNodePR ");
			clientTree = "AD_Tree_Product_ID ";
		}
		else if(treeType.equals(X_AD_Tree.TREETYPE_BPartner))
		{
			update.append("AD_TreeNodeBP ");
			clientTree = "AD_Tree_BPartner_ID ";
		}
		else if(treeType.equals(X_AD_Tree.TREETYPE_Organization))
		{
			update.append("AD_TreeNode ");
			clientTree = "AD_Tree_Org_ID ";
		}
		else if(treeType.equals(X_AD_Tree.TREETYPE_Activity))
		{
			update.append("AD_TreeNode ");
			clientTree = "AD_Tree_Activity_ID ";
		}
		else if(treeType.equals(X_AD_Tree.TREETYPE_Campaign))
		{
			update.append("AD_TreeNode ");
			clientTree = "AD_Tree_Campaign_ID ";
		}
		else if(treeType.equals(X_AD_Tree.TREETYPE_Project))
		{
			update.append("AD_TreeNode ");
			clientTree = "AD_Tree_Project_ID ";
		}
		else if(treeType.equals(X_AD_Tree.TREETYPE_SalesRegion))
		{
			update.append("AD_TreeNode ");
			clientTree = "AD_Tree_Project_ID ";
		}
		
		update.append("SET Parent_ID = ? ");
		update.append("WHERE Node_ID = ? AND AD_Tree_ID = (SELECT ");
		update.append(clientTree);
		update.append("FROM AD_ClientInfo WHERE AD_Client_ID = ? )");
		DB.executeUpdate(trx, update.toString(), parent_ID, node_ID, AD_Client_ID);
		return true;
	}
	
	public static String	entityProduct = "PR";
	public static int updateParent(Ctx ctx, int AD_Client_ID, String typeEntity, Trx trx)
	{
		int record = 0;
		StringBuilder sql = new StringBuilder();
		if(typeEntity.equals(entityProduct))
		{
			sql.append("SELECT M_Product_ID FROM M_Product ");
		}
		sql.append("WHERE AD_Client_ID = ? ");
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Client_ID);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				int n = 0;
				int entityID = rs.getInt(1);
				if(typeEntity.equals(entityProduct))
				{
					n = updateParentField(ctx, AD_Client_ID, entityID, trx);					
					 
				}
				record = record+n;
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return record;
	}
	
	public static int updateParentField(Ctx ctx, int AD_Client_ID, int M_Product_ID, Trx trx)
	{		
		int retValue = 0;
		int parentID = 0;
		StringBuilder sql = new StringBuilder("SELECT Parent_ID FROM");
		String clientTree = null;
		if(M_Product_ID!=0)
		{
			sql.append(" AD_TreeNodePR ");
			clientTree = "AD_Tree_Product_ID ";
		}
		sql.append("WHERE Node_ID = ? AND AD_Tree_ID = (SELECT ");
		sql.append(clientTree);
		sql.append("FROM AD_ClientInfo WHERE AD_Client_ID = ? )");
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, M_Product_ID);
			pstmt.setInt(2, AD_Client_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				parentID = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Update Parent Field
		if(parentID!=0)
		{
			String tableName = null;
			int nodeID = 0;
			if(M_Product_ID!=0)
			{
				tableName = "M_Product";
				nodeID = M_Product_ID;
			}
			
			StringBuilder update = new StringBuilder("UPDATE ");
			update.append(tableName);
			update.append(" SET Parent_ID = ? WHERE ");
			update.append(tableName);
			update.append("_ID = ? ");
			DB.executeUpdate(trx, update.toString(), parentID, nodeID);
			retValue = 1;
		}
		return retValue;
	}
	
	/**
	 * Check if a date is a business day or not
	 * @param ctx
	 * @param dayDate
	 * @param AD_Org_ID
	 * @param trx
	 * @return true if its business day 
	 */
	public static boolean checkBusinessDay(Ctx ctx, Timestamp dayDate, int AD_Org_ID, Trx trx)
	{
		Calendar day = Calendar.getInstance();
		day.setTime(dayDate);

		// Default 1st weekend is saturday, 2nd is sunday
		int	firstWeekEnd 	= GeneralEnhancementUtils.getWeekEndDay(ctx, AD_Org_ID, true, trx);
		int	secondWeekEnd 	= GeneralEnhancementUtils.getWeekEndDay(ctx, AD_Org_ID, false, trx);

		
		int dayInWeek = day.get(Calendar.DAY_OF_WEEK);
		// Check if day is in weekend
		if(dayInWeek==firstWeekEnd)
			return false;
		if(dayInWeek==secondWeekEnd)
			return false;
		
		// Check in Non Business Day calendar
		boolean isNonBusinessDay = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dayDate);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Timestamp start = new Timestamp(cal.getTimeInMillis());
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		Timestamp end = new Timestamp(cal.getTimeInMillis());
		
		String sql = "SELECT 1 FROM C_NonBusinessDay " +
				"WHERE C_Calendar_ID IN " +
				"(SELECT C_Calendar_ID FROM AD_OrgInfo " +
				"WHERE AD_Org_ID = ? AND AD_Client_ID = ? " +
				"UNION " +
				"SELECT C_Calendar_ID FROM AD_ClientInfo " +
				"WHERE AD_Client_ID = ?) " +
				"AND Date1 BETWEEN ? AND ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, AD_Org_ID);
			pstmt.setInt(2, ctx.getAD_Client_ID());
			pstmt.setInt(3, ctx.getAD_Client_ID());
			pstmt.setTimestamp(4, start);
			pstmt.setTimestamp(5, end);
			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				isNonBusinessDay = true;
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}								

		if(isNonBusinessDay)
			return false;
		return true;
	}
	
	private static int convertDays(String days)
	{
		if(days==null)
			return 0;
		else if(days.equalsIgnoreCase("7")) // Sunday
		{
			return 1;
		}
		else 
		{
			int i = Integer.valueOf(days).intValue();
			return i+1;
		}
	}
	
	public static int getWeekEndDay(Ctx ctx, int AD_Org_ID, boolean firstWeekDay, Trx trx)
	{
		int day = 0;
		if(firstWeekDay)
			day = 7;
		else 
			day = 1;
		
		// Check if system has RSPI or not
		boolean rspiExists = false;
		String sql = "SELECT 1 FROM AD_Table WHERE TableName = 'XX_PayrollCenterCoverage' "; 
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				rspiExists = true;
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(rspiExists)
		{
			sql = "SELECT WorkingTimeType, WeekEnd1, WeekEnd2 FROM XX_PayrollCenterCoverage " +
					"WHERE AD_OrgTrx_ID = ? ";
			pstmt = DB.prepareStatement(sql, trx);
			rs = null;
			try{
				pstmt.setInt(1, AD_Org_ID);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					if(firstWeekDay)
						day = convertDays(rs.getString("WeekEnd1"));
					else
					{
						if(rs.getString(1).equalsIgnoreCase("02")) // 5 days a week
							day = convertDays(rs.getString("WeekEnd2"));
						else
							day = 0;						
					}
				}
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}	
		}

		return day;
	}
	
	public static int daysBetween(Date d1, Date d2)
   {
      return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
   }
	
	public static int getOtherClientDefaultPeriod(int AD_Client_ID, MPeriod period)
	{		
		int periodID = 0;
		String sql = "SELECT p.C_Period_ID FROM C_Period p " +
				"INNER JOIN C_Year y ON (p.C_Year_ID = y.C_Year_ID) " +
				"INNER JOIN AD_ClientInfo ci ON (ci.C_Calendar_ID = y.C_Calendar_ID) " +
				"WHERE ci.AD_Client_ID = ? " +
				"AND p.StartDate <= ? " +
				"AND p.EndDate >= ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, period.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setTimestamp(2, period.getStartDate());
			pstmt.setTimestamp(3, period.getEndDate());
			rs = pstmt.executeQuery();
			if(rs.next())
				periodID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return periodID;
	}
	
	public static int PeriodID(int PeriodNo, int C_Year_ID, Trx trx){
		int period = 0;
		String sql = "SELECT C_Period_ID FROM C_Period WHERE PeriodNo = ? AND C_Year_ID = ?";
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			ps.setInt(1, PeriodNo);
			ps.setInt(2, C_Year_ID);
			rs = ps.executeQuery();
			if(rs.next())
				period = rs.getInt(1);
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return period;
	}
	
	public static StringBuilder getAccount(Ctx ctx, StringBuilder list, int C_ElementValue_ID, Trx trx)
	{
		MElementValue ev = new MElementValue(ctx, C_ElementValue_ID, trx);
		if(ev.isSummary())
		{
			String sql = "SELECT tn.Node_ID AS Account_ID FROM C_AcctSchema_Element ase " +
					"INNER JOIN C_Element e ON (ase.C_Element_ID = e.C_Element_ID) " +
					"INNER JOIN AD_TreeNode tn ON (e.AD_Tree_ID = tn.AD_Tree_ID) " +
					"INNER JOIN AD_ClientInfo ci ON (ase.AD_Client_ID = ci.AD_Client_ID) " +
					"WHERE ci.AD_Client_ID = ? " +
					"AND ase.ElementType = 'AC' " +
					"AND tn.Parent_ID = ?  ";
			PreparedStatement pstmt = DB.prepareStatement(sql, trx);
			ResultSet rs = null;
			try{
				pstmt.setInt(1, ctx.getAD_Client_ID());
				pstmt.setInt(2, C_ElementValue_ID);
				rs = pstmt.executeQuery();
				while(rs.next())
					list = GeneralEnhancementUtils.getAccount(ctx, list, rs.getInt(1), trx);
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		else
		{
			if(list==null)
				list = new StringBuilder("(");
			else
				list.append(", ");
			list.append(C_ElementValue_ID);
		}		
		
		return list;
	}

	public static ArrayList<Integer> getAccountList(Ctx ctx, ArrayList<Integer> list, int C_ElementValue_ID, Trx trx)
	{
		MElementValue ev = new MElementValue(ctx, C_ElementValue_ID, trx);
		if(ev.isSummary())
		{
			String sql = "SELECT tn.Node_ID AS Account_ID FROM C_AcctSchema_Element ase " +
					"INNER JOIN C_Element e ON (ase.C_Element_ID = e.C_Element_ID) " +
					"INNER JOIN AD_TreeNode tn ON (e.AD_Tree_ID = tn.AD_Tree_ID) " +
					"INNER JOIN AD_ClientInfo ci ON (ase.AD_Client_ID = ci.AD_Client_ID) " +
					"WHERE ci.AD_Client_ID = ? " +
					"AND ase.ElementType = 'AC' " +
					"AND tn.Parent_ID = ?  ";
			PreparedStatement pstmt = DB.prepareStatement(sql, trx);
			ResultSet rs = null;
			try{
				pstmt.setInt(1, ctx.getAD_Client_ID());
				pstmt.setInt(2, C_ElementValue_ID);
				rs = pstmt.executeQuery();
				while(rs.next())
					list = GeneralEnhancementUtils.getAccountList(ctx, list, rs.getInt(1), trx);
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		else
		{
			if(list==null)
				list = new ArrayList<Integer>();
			list.add(C_ElementValue_ID);
		}		
		
		return list;
	}

	public static String convertAmountToString(BigDecimal amount, String currency)
	{
		String retValue = null;
		// Convert amount to string
		String a = amount.toString();
		// Get Length
		int length = a.length();
		StringBuilder decimal = null;
		boolean hasDecimal = false;
		// Get Decimal part
		for(int i =0; i<length; i++)
		{
			String c = a.substring(i, i+1);
			if(hasDecimal)
				decimal.append(c);
			if(c.equalsIgnoreCase(".") || c.equalsIgnoreCase(","))
			{
				if(decimal==null)
					decimal = new StringBuilder(",");
				hasDecimal = true;
			}
		}
		
		// Deduct Length by decimal length
		if(decimal!=null)
		{
			length = length-decimal.length();
			retValue = decimal.toString();
		}
		
		//
		int sepNo = 0;
		for(int i=length; i>0; i--)
		{
			if(sepNo==3)
			{
				retValue = ".".concat(retValue);
				sepNo=0;
			}
			String b = a.substring(i-1, i);
			if(retValue==null)
				retValue = b;
			else
				retValue = b.concat(retValue);
			sepNo++;
		}
		
		if(currency!=null)
			retValue = currency.concat(" ").concat(retValue); 
		return retValue;
	}
	
	public static boolean checkDocumentPeriodControl(String docBaseType, Timestamp docDate, int AD_Client_ID, Trx trx)
	{
		boolean isOpen = false;
		String sql = "SELECT 1 FROM C_PeriodControl pc "
				+ "INNER JOIN C_Period p ON pc.C_Period_ID = p.C_Period_ID "
				+ "INNER JOIN C_Year y ON p.C_Year_ID = y.C_Year_ID "
				+ "INNER JOIN AD_ClientInfo ci ON y.C_Calendar_ID = ci.C_Calendar_ID "
				+ "WHERE pc.DocBaseType = ? "
				+ "AND pc.PeriodStatus = 'O' "
				+ "AND ci.AD_Client_ID = ? "
				+ "AND ? BETWEEN p.StartDate AND p.EndDate ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setString(1, docBaseType);
			pstmt.setInt(2, AD_Client_ID);
			pstmt.setTimestamp(3, docDate);
			rs = pstmt.executeQuery();
			if(rs.next())
				isOpen = true;
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isOpen;
	}
	
	public static boolean budgetStart(Ctx ctx, Timestamp date, Trx trx)
	{
		boolean budgetStart = false;
		MBudgetInfo bi = MBudgetInfo.get(ctx, ctx.getAD_Client_ID(), trx);
		if(bi!=null)
			if(bi.getStartDate()!=null)
				if(!bi.getStartDate().after(date))
					budgetStart = true;
		return budgetStart;
	}
	
	public static BigDecimal calculateBudgetAmt(Ctx ctx, BigDecimal amount, int AD_Org_ID, int currencyFromID, Timestamp dateTrx, Trx trx) {
		
		MBudgetInfo bi = MBudgetInfo.get(ctx, ctx.getAD_Client_ID(), trx);
		MAcctSchema as = new MAcctSchema(ctx, bi.getC_AcctSchema_ID(), trx);
		BigDecimal convertedAmt = MConversionRate.convert(ctx,amount, currencyFromID, as.getC_Currency_ID(), dateTrx, bi.getC_ConversionType_ID(),
				ctx.getAD_Client_ID(), AD_Org_ID);

		MCurrency currency = new MCurrency(ctx,as.getC_Currency_ID(), trx);
		convertedAmt = convertedAmt.setScale(currency.getStdPrecision(),RoundingMode.HALF_UP);

		return convertedAmt;
	}



}
