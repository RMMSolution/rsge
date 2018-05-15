/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.X_GL_BudgetControl;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.QueryUtil;
import org.compiere.util.Trx;

import rsge.po.X_XX_AdvanceDisbursementLine;
import rsge.po.X_XX_PurchaseRequisition;
import rsge.tools.BudgetCalculation;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY
 *
 */
public class MAdvanceDisbursementLine extends X_XX_AdvanceDisbursementLine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_AdvanceDisbursementLine_ID
	 * @param trx
	 */
	public MAdvanceDisbursementLine(Ctx ctx, int XX_AdvanceDisbursementLine_ID,
			Trx trx) {
		super(ctx, XX_AdvanceDisbursementLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MAdvanceDisbursementLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(getXX_DisbursementType_ID()!=0)
		{
			MDisbursementType dt = new MDisbursementType(getCtx(), getXX_DisbursementType_ID(), get_Trx());
			setC_Charge_ID(dt.getC_Charge_ID());
			setAdvDisbursementCharge_ID(dt.getAdvDisbursementCharge_ID());
		}
		// Check Budget
		MBudgetInfo info = MBudgetInfo.get(getCtx(), getAD_Client_ID(), get_Trx());
		// If no info found means no budget check
		if(info!=null)
		{
			BigDecimal convertedAmt = calculateConvertedAmt(getAmount());		
			setConvertedAmt(convertedAmt);
			MAdvanceDisbursement ad = new MAdvanceDisbursement(getCtx(), getXX_AdvanceDisbursement_ID(), get_Trx());
			if(info.getStartDate()!=null && ad.getDateExpected()!=null)
				if(ad.getDateExpected().before(info.getStartDate()))
					return true;
			BudgetCalculation bc = null;
			if(ad.getDocStatus().equals(X_XX_PurchaseRequisition.DOCSTATUS_Drafted)
					|| ad.getDocStatus().equals(X_XX_PurchaseRequisition.DOCSTATUS_NotApproved))
			{
				bc = checkLineBudget(ad);
				if(bc.getBudgetAmt()==null) // No Budget defined
				{
					setRemainingBudget(BigDecimal.ZERO);
					setIsOverBudget(false);
					setOverBudgetAmt(BigDecimal.ZERO);
				}
				else
				{
					bc.calculateUsedAmt();
					BigDecimal remains = bc.getBudgetAmt().subtract(bc.getUsedAmt());
					setRemainingBudget(remains);
					BigDecimal usedAmt = getRemainingBudget().subtract(getConvertedAmt());
					if(usedAmt.signum()<0)
					{
						setIsOverBudget(true);
						setOverBudgetAmt(usedAmt.negate());
					}
					else
					{
						setIsOverBudget(false);
						setOverBudgetAmt(BigDecimal.ZERO);
					}				
				}
				// End Check Budget			
			}

		}
		
		//	Get Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM XX_AdvanceDisbursementLine WHERE XX_AdvanceDisbursement_ID=?";
			int ii = QueryUtil.getSQLValue (get_Trx(), sql, getXX_AdvanceDisbursement_ID());
			setLine (ii);
		}
		
		return true;
	}
	
	public BudgetCalculation checkLineBudget(MAdvanceDisbursement ad)
	{
		// Get Account ID
		BudgetCalculation bc = new BudgetCalculation(getCtx(), getAD_Org_ID(), ad.getDateDoc(), ad.getDateExpected(), null, null, false, getAccountID(), 
				ad.getC_Activity_ID(), ad.getC_Campaign_ID(), ad.getC_Project_ID(), 0, 0, 0, 0, 0, false, get_Trx());			
		return bc;
	}

	private int getAccountID()
	{
		int accountID = 0;
		String sql = "SELECT vc.Account_ID FROM C_ValidCombination vc "
				+ "INNER JOIN C_Charge_Acct ca ON vc.C_ValidCombination_ID = ca.CH_Expense_Acct "
				+ "WHERE ca.C_Charge_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getC_Charge_ID());
			rs = pstmt.executeQuery();			
			if(rs.next())
				accountID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return accountID;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		// Update Header
		updateHeader();
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// Update Header
		updateHeader();
		return true;
	}

	private void updateHeader()
	{
		BigDecimal OverBudgetAmount = Env.ZERO;
		// Check if there is any over budget line
		String checkOverBudget = "SELECT SUM(OverBudgetAmt) " +
				"FROM XX_AdvanceDisbursementLine " +
				"WHERE IsOverBudget = 'Y' " +
				"AND XX_AdvanceDisbursement_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(checkOverBudget, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_AdvanceDisbursement_ID());
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				OverBudgetAmount = rs.getBigDecimal(1);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		StringBuffer sql = new StringBuffer();
		String lineOverBudget = null;
		BigDecimal overBudget = Env.ZERO;
		
		if(OverBudgetAmount == null)
		{
			lineOverBudget ="N";
		}
		else 
		{
			lineOverBudget = "Y";
			overBudget = OverBudgetAmount;				
		}
		
		sql.append("UPDATE XX_AdvanceDisbursement ad "
			+ "SET ad.TotalAmt= "
			+ "(SELECT COALESCE(SUM(adl.Amount),0) FROM XX_AdvanceDisbursementLine adl "
			+ "WHERE ad.XX_AdvanceDisbursement_ID=adl.XX_AdvanceDisbursement_ID) ");
			sql.append(", IsLineOverBudget = ?, " +
					"OverBudgetAmt = ? " );
		sql.append("WHERE XX_AdvanceDisbursement_ID= ? ");		
		
		DB.executeUpdate(get_Trx(), sql.toString(), lineOverBudget, overBudget, getXX_AdvanceDisbursement_ID());
		return;
	}
	
	/**
	 * Calculate Converted Line Amount
	 * @param lineNetAmount
	 * @return
	 */
	private BigDecimal calculateConvertedAmt(BigDecimal lineNetAmount)
	{
		// Get Requisition Currency
		MAdvanceDisbursement ad = new MAdvanceDisbursement(getCtx(), getXX_AdvanceDisbursement_ID(), get_Trx());
		
		// Get Budget Info Currency
		MBudgetInfo info = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), get_Trx());
		X_GL_BudgetControl bc = new X_GL_BudgetControl(getCtx(), info.getGL_BudgetControl_ID(), get_Trx());
		MAcctSchema schema = new MAcctSchema(getCtx(), bc.getC_AcctSchema_ID(), get_Trx());
		
		if(schema.getC_Currency_ID()!= ad.getC_Currency_ID())
		{
			BigDecimal convertedAmt = MConversionRate.convert(getCtx(), lineNetAmount, ad.getC_Currency_ID(), schema.getC_Currency_ID(), 
					ad.getDateExpected(), info.getC_ConversionType_ID(), getAD_Client_ID(), getAD_Org_ID());
			
			MCurrency currency = new MCurrency(getCtx(), schema.getC_Currency_ID(), get_Trx());
			convertedAmt = convertedAmt.setScale(currency.getStdPrecision(), RoundingMode.HALF_UP);
			
			return convertedAmt;
		}		
		else return lineNetAmount; 			
	}
	
	public static BigDecimal getReservedAmt(int AD_Org_ID, int accountID, Timestamp startDate, Timestamp endDate, int C_Activity_ID, int C_Campaign_ID, int C_Project_ID, Trx trx)
	{
		BigDecimal reservedAmt = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(adl.ConvertedAmt),0) AS ConvertedAmt FROM XX_AdvanceDisbursementLine adl "
				+ "INNER JOIN XX_AdvanceDisbursement ad ON adl.XX_AdvanceDisbursement_ID = ad.XX_AdvanceDisbursement_ID "
				+ "INNER JOIN C_Charge_Acct ca ON adl.C_Charge_ID = ca.C_Charge_ID "
				+ "INNER JOIN C_ValidCombination vc ON ca.CH_Expense_Acct = vc.C_ValidCombination_ID "
				+ "WHERE ad.DocStatus IN ('CO', 'CL') "
				+ "AND adl.XX_AdvanceDisbursementLine_ID NOT IN (SELECT l.XX_AdvanceDisbursementLine_ID FROM XX_DisRealizationLine l "
				+ "INNER JOIN XX_DisbursementRealization a ON l.XX_DisbursementRealization_ID = a.XX_DisbursementRealization_ID "
				+ "WHERE a.DocStatus IN ('CO', 'CL')) "
				+ "AND ad.AD_Org_ID = ? "
				+ "AND vc.Account_ID = ? "
				+ "AND ad.DateExpected BETWEEN ? AND ? ");
		if(C_Activity_ID!=0)
			sql.append("AND ad.C_Activity_ID = ? ");
		else
			sql.append("AND (ad.C_Activity_ID = 0 OR ad.C_Activity_ID IS NULL) ");
		if(C_Campaign_ID!=0)
			sql.append("AND ad.C_Campaign_ID = ? ");
		else
			sql.append("AND (ad.C_Campaign_ID = 0 OR ad.C_Campaign_ID IS NULL) ");
		if(C_Project_ID!=0)
			sql.append("AND ad.C_Project_ID = ? ");
		else
			sql.append("AND (ad.C_Project_ID = 0 OR ad.C_Project_ID IS NULL) ");

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		
		try{
			int index = 1;
			pstmt.setInt(index++, AD_Org_ID);
			pstmt.setInt(index++, accountID);
			pstmt.setTimestamp(index++, startDate);
			pstmt.setTimestamp(index++, endDate);
			
			if(C_Activity_ID!=0)
				pstmt.setInt(index++, C_Activity_ID);
			if(C_Campaign_ID!=0)
				pstmt.setInt(index++, C_Campaign_ID);
			if(C_Project_ID!=0)
				pstmt.setInt(index++, C_Project_ID);

			rs = pstmt.executeQuery();
			
			if(rs.next())
				reservedAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return reservedAmt;
	}
	
	public static BigDecimal getPendingAmt(int AD_Org_ID, int accountID, Timestamp startDate, Timestamp endDate, int C_Activity_ID, int C_Campaign_ID, int C_Project_ID, Trx trx)
	{
		BigDecimal pendingAmt = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(adl.ConvertedAmt),0) AS ConvertedAmt FROM XX_AdvanceDisbursementLine adl "
				+ "INNER JOIN XX_AdvanceDisbursement ad ON adl.XX_AdvanceDisbursement_ID = ad.XX_AdvanceDisbursement_ID "
				+ "INNER JOIN C_Charge_Acct ca ON adl.C_Charge_ID = ca.C_Charge_ID "
				+ "INNER JOIN C_ValidCombination vc ON ca.CH_Expense_Acct = vc.C_ValidCombination_ID "
				+ "WHERE ad.DocStatus IN ('IP', 'AP') ");
		if(AD_Org_ID!=0)
			sql.append("AND ad.AD_Org_ID = ? ");
		sql.append("AND vc.Account_ID = ? "
				+ "AND ad.DateExpected BETWEEN ? AND ? ");
		if(C_Activity_ID!=0)
			sql.append("AND ad.C_Activity_ID = ? ");
		else
			sql.append("AND (ad.C_Activity_ID = 0 OR ad.C_Activity_ID IS NULL) ");
		if(C_Campaign_ID!=0)
			sql.append("AND ad.C_Campaign_ID = ? ");
		else
			sql.append("AND (ad.C_Campaign_ID = 0 OR ad.C_Campaign_ID IS NULL) ");
		if(C_Project_ID!=0)
			sql.append("AND ad.C_Project_ID = ? ");
		else
			sql.append("AND (ad.C_Project_ID = 0 OR ad.C_Project_ID IS NULL) ");

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		
		try{
			int index = 1;
			if(AD_Org_ID!=0)
				pstmt.setInt(index++, AD_Org_ID);
			pstmt.setInt(index++, accountID);
			pstmt.setTimestamp(index++, startDate);
			pstmt.setTimestamp(index++, endDate);
			
			if(C_Activity_ID!=0)
				pstmt.setInt(index++, C_Activity_ID);
			if(C_Campaign_ID!=0)
				pstmt.setInt(index++, C_Campaign_ID);
			if(C_Project_ID!=0)
				pstmt.setInt(index++, C_Project_ID);

			rs = pstmt.executeQuery();
			
			if(rs.next())
				pendingAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return pendingAmt;
	}
	
	public static MAdvanceDisbursementLine[] getLines(MAdvanceDisbursement ad)
	{
		ArrayList<MAdvanceDisbursementLine> list = new ArrayList<>();
		String sql = "SELECT * " +
				"FROM XX_AdvanceDisbursementLine " +
				"WHERE XX_AdvanceDisbursement_ID = ? " +
				"ORDER BY Line ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, ad.get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, ad.getXX_AdvanceDisbursement_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				list.add(new MAdvanceDisbursementLine(ad.getCtx(), rs, ad.get_Trx()));
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
				
		MAdvanceDisbursementLine[] retValue = new MAdvanceDisbursementLine[list.size()];
		list.toArray(retValue);
		return retValue;
	}
	
	private BigDecimal getUnrealizedAmt()
	{
		BigDecimal unrealizedAmt = BigDecimal.ZERO;
		String sql = "SELECT COALESCE(SUM(UnrealizedAmt),0) AS UnrealizedAmt " +
				"FROM XX_BudgetTransactionLine " +
				"WHERE SourceTable_ID = ? " +
				"AND SourceRecord_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, get_Table_ID());
			pstmt.setInt(2, getXX_AdvanceDisbursementLine_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				unrealizedAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}		
		return unrealizedAmt;
	}
	
	public boolean recordLineTransaction()
	{
		MAdvanceDisbursement ad = new MAdvanceDisbursement(getCtx(), getXX_AdvanceDisbursement_ID(), get_Trx());		
		MBudgetTransactionLine bLine = MBudgetTransactionLine.createLine(getCtx(), get_Table_ID(), getXX_AdvanceDisbursementLine_ID(), 0, 0, getAD_Org_ID(), ad.getDateDoc(), getAccountID(), 0, getC_Activity_ID(), 0, ad.getC_Campaign_ID(), 0, 0, ad.getC_Project_ID(), 0, 0, 0, 0, 0, 0, 0, 0, 0, get_Trx());
		bLine.setReservedAmt(getConvertedAmt());
		bLine.save();
		return true;
	}
	
	public boolean recordUnrealizedLineTransaction(BigDecimal unrealizedAmt, int tableID, int recordID)
	{
		MAdvanceDisbursement ad = new MAdvanceDisbursement(getCtx(), getXX_AdvanceDisbursement_ID(), get_Trx());		
		MBudgetTransactionLine bLine = MBudgetTransactionLine.createLine(getCtx(), tableID, recordID, get_Table_ID(), getXX_AdvanceDisbursementLine_ID(), getAD_Org_ID(), ad.getDateDoc(), getAccountID(), 0, getC_Activity_ID(), 0, ad.getC_Campaign_ID(), 0, 0, ad.getC_Project_ID(), 0, 0, 0, 0, 0, 0, 0, 0, 0, get_Trx());
		bLine.setReservedAmt(getConvertedAmt().negate());
		bLine.setUnrealizedAmt(getUnrealizedAmt());
		bLine.save();
		return true;
	}
	
	public boolean recordRealizedLineTransaction(BigDecimal realizedAmt, int tableID, int recordID)
	{
		MAdvanceDisbursement ad = new MAdvanceDisbursement(getCtx(), getXX_AdvanceDisbursement_ID(), get_Trx());		
		MBudgetTransactionLine bLine = MBudgetTransactionLine.createLine(getCtx(), tableID, recordID, get_Table_ID(), getXX_AdvanceDisbursementLine_ID(), getAD_Org_ID(), ad.getDateDoc(), getAccountID(), 0, getC_Activity_ID(), 0, ad.getC_Campaign_ID(), 0, 0, ad.getC_Project_ID(), 0, 0, 0, 0, 0, 0, 0, 0, 0, get_Trx());
		bLine.setUnrealizedAmt(getUnrealizedAmt().negate());
		bLine.setRealizedAmt(realizedAmt);
		bLine.save();
		return true;
	}



	
//	/**
//	 * Check Remaining Budget of an account
//	 * @return Remaining Budget
//	 * Return -1 to indicate the account is not budgeted
//	 */
//	private BigDecimal checkRemainingBudget()
//	{
//		MAdvanceDisbursement ad = new MAdvanceDisbursement(getCtx(), getXX_AdvanceDisbursement_ID(), get_Trx());
//		
//		// Get Accounting Schema from Budget basic info
//		MBudgetInfo info = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), get_Trx());
//
//		X_GL_BudgetControl bc = new X_GL_BudgetControl(getCtx(), info.getGL_BudgetControl_ID(), get_Trx());
//		
//		int Account_ID = 0;
//
//		int Charge_ID = getC_Charge_ID();			
//		
//		String sql = "SELECT vc.Account_ID " +
//			"FROM C_Charge_Acct ca " +
//			"INNER JOIN C_ValidCombination vc ON (ca.Ch_Expense_Acct = vc.C_ValidCombination_ID) " +
//			"WHERE ca.C_Charge_ID = ? " +
//			"AND ca.C_AcctSchema_ID = ? ";
//			
//		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
//		ResultSet rs = null;
//			
//		try{
//			pstmt.setInt(1, Charge_ID);
//			pstmt.setInt(2, bc.getC_AcctSchema_ID());
//				
//			rs = pstmt.executeQuery();
//				
//			if(rs.next())
//				Account_ID = rs.getInt(1);				
//			rs.close();
//			pstmt.close();
//		}catch (Exception e) {
//				e.printStackTrace();
//		}			
//		
//		int element1Value = 0;
//		String element1 = null;
//		element1 = GeneralEnhancementUtils.getElementColumn(info.getC_AcctSchema_ID(), 1, get_Trx());
//		
//		if((element1 != null) && (GeneralEnhancementUtils.checkColumn("XX_AdvanceDisbursementLine", element1, get_Trx())))
//		{
//			element1Value = GeneralEnhancementUtils.getElementValue(element1, "XX_AdvanceDisbursementLine", getXX_AdvanceDisbursementLine_ID(), get_Trx());
//		}
//		else if((element1 != null) && (GeneralEnhancementUtils.checkColumn("XX_AdvanceDisbursement", element1, get_Trx())))
//		{
//			element1Value = GeneralEnhancementUtils.getElementValue(element1, "XX_AdvanceDisbursement", getXX_AdvanceDisbursement_ID(), get_Trx());
//		}
//		
//		int element2Value = 0;
//		String element2 = null;
//		element2 = GeneralEnhancementUtils.getElementColumn(info.getC_AcctSchema_ID(), 2, get_Trx());
//		
//		if((element2 != null) && (GeneralEnhancementUtils.checkColumn("XX_AdvanceDisbursementLine", element2, get_Trx())))
//		{
//			element2Value = GeneralEnhancementUtils.getElementValue(element2, "XX_AdvanceDisbursementLine", getXX_AdvanceDisbursementLine_ID(), get_Trx());
//		}
//		else if((element2 != null) && (GeneralEnhancementUtils.checkColumn("XX_AdvanceDisbursement", element2, get_Trx())))
//		{
//			element2Value = GeneralEnhancementUtils.getElementValue(element2, "XX_AdvanceDisbursement", getXX_AdvanceDisbursement_ID(), get_Trx());
//		}		
//		
//		CheckBudget check = new CheckBudget(getCtx(), false, getAD_Org_ID(), Account_ID, ad.getDateExpected(),false, info.getBudgetCalendar_ID(), 
//				ad.getC_Activity_ID(), getC_BPartner_ID(), ad.getC_Campaign_ID(), ad.getC_Project_ID(), 0, element1Value, element2Value, get_Trx());
//		check.runBudgetCheck();
//		BigDecimal remainingBudget = check.getRemainingBudget();
//		if(check.getAccountNotBudgeted())
//			remainingBudget = BigDecimal.ONE.negate();  
//		return remainingBudget;		
//	}
}
