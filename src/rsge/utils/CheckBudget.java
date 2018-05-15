/**

 * 

 */

package rsge.utils;

import java.math.BigDecimal;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.Timestamp;

import java.util.Calendar;

import org.compiere.model.MAccount;

import org.compiere.model.MAcctSchema;

import org.compiere.model.MCurrency;

import org.compiere.model.MElementValue;

import org.compiere.model.X_C_ElementValue;

import org.compiere.model.X_GL_BudgetControl;

import org.compiere.model.X_GL_Journal;

import org.compiere.model.X_M_Product;

import org.compiere.util.Ctx;

import org.compiere.util.DB;

import org.compiere.util.Env;

import org.compiere.util.Trx;

import rsge.model.MAcctElementBudgetControl;
import rsge.model.MAssetGroup;

import rsge.model.MBudgetInfo;

import rsge.model.MProduct;

import rsge.model.MProductAcct;

/**
 * 
 * @author FANNY R
 * 
 * 
 */

public class CheckBudget {

	/** Logger for class Check Budget */

	private static final org.compiere.util.CLogger log = org.compiere.util.CLogger
			.getCLogger(CheckBudget.class);

	/** Ctx and Trx */

	Ctx ctx = null;

	Trx trx = null;

	private boolean allowedRun = true;

	/** AD_Org_ID */

	private int AD_Org_ID = 0;

	/** Account_ID */

	private int Account_ID = 0;

	/** Product ID */

	private int M_Product_ID = 0;

	/** Date Transaction */

	private Timestamp DateTrx = null;

	/** Budget Basic Info */

	/** Accounting Schema */

	private int C_AcctSchema_ID = 0;

	/** Budget Currency */

	private int C_Currency_ID = 0;

	/** Budget Calendar */

	private int BudgetCalendar_ID = 0;

	/** GL Budget */

	private int GL_Budget_ID = 0;

	/** C_Activity_ID */

	private int C_Activity_ID = 0;

	/** C_BPartner_ID */

	private int C_BPartner_ID = 0;

	/** C_Campaign_ID */

	private int C_Campaign_ID = 0;

	/** C_Project_ID */

	private int C_Project_ID = 0;

	/** User Element 1 */

	private String userElement1 = null;

	/** User Element 2 */

	private String userElement2 = null;

	/** User Element 1 Value */

	private int userElement1Value = 0;

	/** Commitment Type */

	private String commitmentType = null;

	/** Control Scope */

	private String budgetControlScope;

	/** Remaining Budget Amount */

	private BigDecimal remainingAmount = Env.ZERO;

	/** Remaining Budget Qty */

	private BigDecimal remainingQty = Env.ZERO;

	/** Start Date (Budget Period) */

	private Timestamp startDate = null;
	private boolean checkDateIsStartDate = false;

	/** End Date (Budget Period */

	private Timestamp endDate = null;

	private boolean isOrgProductQty = false;

	/** Not Budgeted (Account) */

	private boolean notBudgeted = false;

	/** Budget Info */

	private MBudgetInfo info = null;

	/**

         * 

         */

	public CheckBudget(Ctx ctx, boolean currentPeriodOnly, Timestamp DateTrx,
			boolean checkDateAsStartDate, int C_Calendar_ID, int AD_Client_ID,
			int AD_Org_ID, int Account_ID, int C_SubAcct_ID,int M_Product_ID, int C_BPartner_ID, 
			int AD_OrgTrx_ID,int C_LocFrom_ID, int C_LocTo_ID, 
			int C_BPartner_Location_ID,	int C_Project_ID, int C_Campaign_ID, 
			int C_Activity_ID,int User1_ID, int User2_ID, int UserElement1_ID,
			int UserElement2_ID, Trx trx) {

		log.info("Budget Check start ");

		this.ctx = ctx;

		this.trx = trx;

		this.AD_Org_ID = AD_Org_ID;

		this.Account_ID = Account_ID;

		this.M_Product_ID = M_Product_ID;

		this.C_BPartner_ID = C_BPartner_ID;

		this.C_Project_ID = C_Project_ID;

		this.C_Campaign_ID = C_Campaign_ID;

		this.C_Activity_ID = C_Activity_ID;

		this.userElement1Value = UserElement1_ID;

		this.DateTrx = DateTrx;

		this.checkDateIsStartDate = checkDateAsStartDate;

		// Prepare requisite data

		info = GeneralEnhancementUtils.getBudgetInfo(ctx, AD_Client_ID, trx);

		if (info.getStartDate() != null
				&& this.DateTrx.before(info.getStartDate()))

			allowedRun = false;

		isOrgProductQty = info.isCheckBudgetQty();

		if (C_Calendar_ID > 0)

			BudgetCalendar_ID = C_Calendar_ID;

		else
			info.getBudgetCalendar_ID();

		C_AcctSchema_ID = info.getC_AcctSchema_ID();

		C_Currency_ID = BudgetUtils.getBudgetCurrencyID(ctx, AD_Client_ID, trx);

		// Check User Element in Accounting Schema

		userElement1 = GeneralEnhancementUtils.getElementColumn(
				C_AcctSchema_ID, 1, trx);

		userElement2 = GeneralEnhancementUtils.getElementColumn(
				C_AcctSchema_ID, 2, trx);

		// Get Budget Control info
		int budgetControlID = 0;
		//getBudgetControlID(info, C_Campaign_ID, C_Project_ID, trx);
		X_GL_BudgetControl control = new X_GL_BudgetControl(ctx,budgetControlID,trx);

		if (Integer.toString(control.getGL_Budget_ID()).length() != 0)
			GL_Budget_ID = control.getGL_Budget_ID();
		commitmentType = control.getCommitmentType();
		budgetControlScope = control.getBudgetControlScope();

		if (Account_ID != 0)
		{
			// If Budget Control Scope is Total, but account is Balance Sheet
			// element, change budget Control Scope to Year to Date
			MElementValue ev = new MElementValue(ctx, Account_ID, trx);
			if ((ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Asset)
					|| ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Liability)
					|| ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_OwnerSEquity))
					&& budgetControlScope.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total))
				budgetControlScope = X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate;
		}
	}

	public CheckBudget(Ctx ctx, boolean currentPeriodOnly, int AD_Org_ID,
			int Account_ID, Timestamp DateTrx, boolean checkBudgetAsStartDate,
			int C_Calendar_ID, int C_Activity_ID, int C_BPartner_ID,

			int C_Campaign_ID, int C_Project_ID, int M_Product_ID,
			int UserElement1Value, int UserElement2Value, Trx trx) {

		this(ctx, currentPeriodOnly, DateTrx, checkBudgetAsStartDate,
				C_Calendar_ID, ctx.getAD_Client_ID(), AD_Org_ID, Account_ID, 0,
				M_Product_ID, C_BPartner_ID, 0, 0, 0, 0,

				C_Project_ID, C_Campaign_ID, C_Activity_ID, 0, 0,
				UserElement1Value, UserElement2Value, trx);

	}

	// End Constructor

	/**
	 * 
	 * Run Budget Check
	 * 
	 * @return result status
	 * 
	 *         Result :
	 * 
	 *         - 0 - Budget Check complete with no problem
	 * 
	 *         - 1 - Budget check is not run because transaction is before start
	 *         date of budget module
	 * 
	 * 
	 */

	public int runBudgetCheck()
	{
		if (!allowedRun)
			return 1;
		budgetCheck();
		return 0;
	}

	private void budgetCheck()
	{
		// Get Start Date and End Date of current Period
		getPeriodDates(checkDateIsStartDate);
		// Check Budget
		int product = M_Product_ID;
		int accountID = Account_ID;
		MAccount ac = null;
		if (M_Product_ID != 0)
		{
			MProduct p = new MProduct(ctx, product, trx);
			MAcctSchema as = new MAcctSchema(ctx, C_AcctSchema_ID, trx);
			MProductAcct pa = MProductAcct.get(M_Product_ID, as);
			if (p.getProductType().equals(X_M_Product.PRODUCTTYPE_Item))
			{
				boolean isAsset = MAssetGroup.checkProductAsset(p);
				if (isAsset)
				{
					ac = new MAccount(ctx, pa.getP_Asset_Acct(), trx);
					accountID = ac.getAccount_ID();
				}
				else
				{
					ac = new MAccount(ctx, pa.getP_Expense_Acct(), trx);
					accountID = ac.getAccount_ID();
					if (isOrgProductQty)
						accountID = 0;
				}
			}
			if (!p.getProductType().equals(X_M_Product.PRODUCTTYPE_Item))
			{
				ac = new MAccount(ctx, pa.getP_Expense_Acct(), trx);
				accountID = ac.getAccount_ID();
				if (isOrgProductQty)
					accountID = 0;
				product = 0;
			}
		}
		checkBudgetAmount(AD_Org_ID, accountID, C_AcctSchema_ID, C_Activity_ID,
				C_Campaign_ID, C_Project_ID, product, C_BPartner_ID,
				checkDateIsStartDate);
		checkAdvanceDisbursement(AD_Org_ID, accountID, C_AcctSchema_ID,
				C_Activity_ID, C_Campaign_ID, C_Project_ID, product,
				C_BPartner_ID, checkDateIsStartDate);
		checkBudgetRevisionAmt(AD_Org_ID, accountID, C_AcctSchema_ID,
				C_Activity_ID, C_Campaign_ID, C_Project_ID, product,
				C_BPartner_ID, checkDateIsStartDate);
		checkPRAmt(AD_Org_ID, accountID, C_AcctSchema_ID, C_Activity_ID,
				C_Campaign_ID, C_Project_ID, product, C_BPartner_ID,
				checkDateIsStartDate);
		checkPOAmt(AD_Org_ID, accountID, C_AcctSchema_ID, C_Activity_ID,
				C_Campaign_ID, C_Project_ID, product, C_BPartner_ID,
				checkDateIsStartDate);
		checkCashAmt(AD_Org_ID, accountID, C_AcctSchema_ID, C_Activity_ID,
				C_Campaign_ID, C_Project_ID, product, C_BPartner_ID,
				checkDateIsStartDate);
		checkJournalAmt(AD_Org_ID, accountID, C_AcctSchema_ID, C_Activity_ID,
				C_Campaign_ID, C_Project_ID, product, C_BPartner_ID,
				checkDateIsStartDate);

		checkRealizedAmt(AD_Org_ID, accountID, C_AcctSchema_ID, C_Activity_ID,
				C_Campaign_ID, C_Project_ID, product, checkDateIsStartDate);

		if (remainingAmount == null) // If no budget allocated, return
			return;

		product = M_Product_ID;

		if (commitmentType
				.equals(X_GL_BudgetControl.COMMITMENTTYPE_CommitmentOnly))
		{
			// checkCommitment(AD_Org_ID, accountID, C_AcctSchema_ID,
			// C_Activity_ID, C_Campaign_ID,
			// C_Project_ID, product, checkDateIsStartDate);
			checkPOCompleteNoInvoiceAmt(ctx.getAD_Client_ID(), AD_Org_ID,
					accountID, C_AcctSchema_ID, C_Activity_ID, C_Campaign_ID,
					C_Project_ID, product, C_BPartner_ID, checkDateIsStartDate);
		}
		else if (commitmentType
				.equals(X_GL_BudgetControl.COMMITMENTTYPE_CommitmentReservation))
		{
			// checkCommitment(AD_Org_ID, accountID, C_AcctSchema_ID,
			// C_Activity_ID, C_Campaign_ID, C_Project_ID,
			// product, checkDateIsStartDate);
			checkPOCompleteNoInvoiceAmt(ctx.getAD_Client_ID(), AD_Org_ID,
					accountID, C_AcctSchema_ID, C_Activity_ID, C_Campaign_ID,
					C_Project_ID, product, C_BPartner_ID, checkDateIsStartDate);
			checkReservation(AD_Org_ID, accountID, C_AcctSchema_ID,
					C_Activity_ID, C_Campaign_ID, C_Project_ID, product,
					checkDateIsStartDate);
		}
	}

	/**
	 * 
	 * Check Budget Amount for non product based on selected account
	 * 
	 * 
	 */

	private void checkBudgetAmount(int AD_Org_ID, int accountID,
			int C_AcctSchema_ID, int activity, int campaign, int project,
			int product, int bpartner, boolean checkPeriodIsStartPeriod)

	{

		log.info("======= Check Budget Amount ======");

		/** Budget Amount */

		BigDecimal budgetAmount = null;

		/** Budget Quantity */

		BigDecimal budgetQty = null;

		StringBuilder sql = new StringBuilder();

		sql.append(getSelectSyntax(accountID)).append(
				getFromFactSyntax(accountID, C_AcctSchema_ID, AD_Org_ID,
						X_GL_Journal.POSTINGTYPE_Budget));

		sql.append(getWhereSyntax(X_GL_Journal.POSTINGTYPE_Budget, "fa",
				activity, campaign, project, product, bpartner));

		// Check User Element

		if (userElement1 != null)

			sql.append(getUserElementSyntax("fa", "UserElement1_ID",
					userElement1Value));

		if (userElement2 != null)

			sql.append(getUserElementSyntax("fa", "UserElement2_ID",
					userElement1Value));

		sql.append(getTimeSyntax("DateAcct", "fa"));

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);

		ResultSet rs = null;

		try {

			log.info(sql.toString());

			int index = 1;

			log.info(String.valueOf(C_AcctSchema_ID));

			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, C_AcctSchema_ID);

			pstmt.setInt(index++, AD_Org_ID);

			if (accountID != 0)

			{

				log.info(String.valueOf(accountID));

				pstmt.setInt(index++, accountID);

			}

			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly))

			{

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate))

			{
				startDate = getYearStartDate(checkPeriodIsStartPeriod);

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total))

			{

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, endDate);

			}

			rs = pstmt.executeQuery();

			if (rs.next())

			{

				budgetAmount = rs.getBigDecimal(1);

				budgetQty = rs.getBigDecimal(2);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		finally

		{

			DB.closeResultSet(rs);

			DB.closeStatement(pstmt);

		}

		// Set Budget Amount to zero if result is less than 0

		if (budgetAmount == null)

		{

			notBudgeted = true;

		}

		if (budgetAmount != null && budgetQty.signum() == 0)

			budgetQty = null;

		// Set Remaining Amount with Budget

		log.info("Budget Amount   : " + budgetAmount);

		log.info("Budget Quantity : " + budgetQty);

		log.info("--------------- End Budget Section -----------------");

		log.info(" ");

		remainingAmount = budgetAmount;

		if (budgetQty != null)

			remainingQty = budgetQty;

	}// End Check Budget

	/**
	 * 
	 * Check Realized Amount
	 * 
	 * - For Product Item, check based on invoiced receipt
	 * 
	 * - For Other, check based on accounting entries
	 */

	private void checkRealizedAmt(int AD_Org_ID, int accountID,
			int C_AcctSchema_ID, int activity, int campaign, int project,
			int product, boolean checkPeriodIsStartPeriod)

	{

		log.info("======= Check Realized Amount ======");

		// Check from fact acct

		StringBuilder sql = new StringBuilder();

		sql.append(getSelectSyntax(accountID))
				.append(getFromFactSyntax(accountID, C_AcctSchema_ID,
						AD_Org_ID, X_GL_Journal.POSTINGTYPE_Actual))

				.append(getWhereSyntax(X_GL_Journal.POSTINGTYPE_Actual, "fa",
						null, null, null, null, null));

		// Check User Element

		if (userElement1 != null)

			sql.append(getUserElementSyntax("fa", "UserElement1_ID",
					userElement1Value));

		if (userElement2 != null)

			sql.append(getUserElementSyntax("fa", "UserElement2_ID",
					userElement1Value));

		sql.append(getTimeSyntax("DateAcct", "fa"));

//		sql.append("AND fa.AD_Table_ID IN (318,319) ");

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);

		ResultSet rs = null;

		BigDecimal factAmt = Env.ZERO;

		BigDecimal qty = Env.ZERO;

		try {

			int index = 1;

			log.info(sql.toString());

			log.info(String.valueOf(C_AcctSchema_ID));

			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, C_AcctSchema_ID);

			pstmt.setInt(index++, AD_Org_ID);

			// pstmt.setInt(1, C_AcctSchema_ID);

			// pstmt.setInt(2, AD_Org_ID);

			if (accountID != 0)

			{

				log.info(String.valueOf(accountID));

				pstmt.setInt(index++, accountID);

			}

			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly))

			{

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

				// pstmt.setTimestamp(3, startDate);

				// pstmt.setTimestamp(4, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate))

			{

				startDate = getYearStartDate(checkPeriodIsStartPeriod);

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

				// pstmt.setTimestamp(3, startDate);

				// pstmt.setTimestamp(4, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total))

			{

				log.info(String.valueOf(endDate));

				// pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

				// pstmt.setTimestamp(3, endDate);

			}

			rs = pstmt.executeQuery();

			if (rs.next())

			{

				factAmt = rs.getBigDecimal(1);

				if (remainingQty.signum() > 0)

					qty = rs.getBigDecimal(2);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		remainingAmount = remainingAmount.subtract(factAmt);

		remainingQty = remainingQty.subtract(qty);

		log.info("Realized Amt : " + factAmt);

		log.info("Remaining Amt : " + remainingAmount);

		log.info("Remaining Qty : " + remainingQty);

		log.info(" ");

	}

	// }

	private void checkCommitment(int AD_Org_ID, int accountID,
			int C_AcctSchema_ID, int activity, int campaign, int project,
			int product, boolean checkPeriodIsStartPeriod)

	{

		log.info("======= Check Commitment Amount ======");

		StringBuilder sql = new StringBuilder();

		sql.append(getSelectSyntax(accountID)).append(
				getFromFactSyntax(accountID, C_AcctSchema_ID, AD_Org_ID,
						X_GL_Journal.POSTINGTYPE_Commitment));

		sql.append(getWhereSyntax(X_GL_Journal.POSTINGTYPE_Commitment, "fa",
				activity, campaign, project, product, null));

		BigDecimal commitmentAmt = BigDecimal.ZERO;

		BigDecimal qty = BigDecimal.ZERO;

		// Check User Element

		if (userElement1 != null)

			sql.append(getUserElementSyntax("fa", "UserElement1_ID",
					userElement1Value));

		if (userElement2 != null)

			sql.append(getUserElementSyntax("fa", "UserElement2_ID",
					userElement1Value));

		sql.append(getTimeSyntax("DateAcct", "fa"));

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);

		ResultSet rs = null;

		try {

			log.info(sql.toString());

			int index = 1;

			log.info(String.valueOf(C_AcctSchema_ID));

			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, C_AcctSchema_ID);

			pstmt.setInt(index++, AD_Org_ID);

			if (accountID != 0)

			{

				log.info(String.valueOf(accountID));

				pstmt.setInt(index++, accountID);

			}

			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly))

			{

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate))

			{

				startDate = getYearStartDate(checkPeriodIsStartPeriod);

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total))

			{

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, endDate);

			}

			rs = pstmt.executeQuery();

			if (rs.next())

			{

				commitmentAmt = rs.getBigDecimal(1);

				if (remainingQty.signum() > 0)

					qty = rs.getBigDecimal(2);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		finally

		{

			DB.closeResultSet(rs);

			DB.closeStatement(pstmt);

		}

		remainingAmount = remainingAmount.subtract(commitmentAmt);

		remainingQty = remainingQty.subtract(qty);

		log.info("Commitment Amount " + commitmentAmt);

		log.info("Remaining Amt : " + remainingAmount);

		log.info("Remaining Qty : " + remainingQty);

		log.info(" ");

	}

	private void checkReservation(int AD_Org_ID, int accountID,
			int C_AcctSchema_ID, int activity, int campaign, int project,
			int product, boolean checkPeriodIsStartPeriod)

	{

		log.info("======= Check Reservation Amount ======");

		StringBuilder sql = new StringBuilder();

		BigDecimal reservationAmt = BigDecimal.ZERO;

		BigDecimal qty = BigDecimal.ZERO;

		sql.append(getSelectSyntax(accountID)).append(
				getFromFactSyntax(accountID, C_AcctSchema_ID, AD_Org_ID,
						X_GL_Journal.POSTINGTYPE_Reservation));

		sql.append(getWhereSyntax(X_GL_Journal.POSTINGTYPE_Reservation, "fa",
				activity, campaign, project, product, null));

		// Check User Element

		if (userElement1 != null)

			sql.append(getUserElementSyntax("fa", "UserElement1_ID",
					userElement1Value));

		if (userElement2 != null)

			sql.append(getUserElementSyntax("fa", "UserElement2_ID",
					userElement1Value));

		sql.append(getTimeSyntax("DateAcct", "fa"));

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);

		ResultSet rs = null;

		try {

			log.info(sql.toString());

			int index = 1;

			log.info(String.valueOf(C_AcctSchema_ID));

			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, C_AcctSchema_ID);

			pstmt.setInt(index++, AD_Org_ID);

			if (accountID != 0)

			{

				log.info(String.valueOf(accountID));

				pstmt.setInt(index++, accountID);

			}

			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly))

			{

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate))

			{

				startDate = getYearStartDate(checkPeriodIsStartPeriod);

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total))

			{

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, endDate);

			}

			rs = pstmt.executeQuery();

			if (rs.next())

			{

				reservationAmt = rs.getBigDecimal(1);

				if (remainingQty.signum() > 0)

					qty = rs.getBigDecimal(2);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		finally

		{

			DB.closeResultSet(rs);

			DB.closeStatement(pstmt);

		}

		remainingAmount = remainingAmount.subtract(reservationAmt);

		remainingQty = remainingQty.subtract(qty);

		log.info("Reservation Amount " + reservationAmt);

		log.info("Remaining Amt : " + remainingAmount);

		log.info(" ");

	}

	private void checkAdvanceDisbursement(int AD_Org_ID, int accountID,
			int C_AcctSchema_ID, int activity, int campaign, int project,
			int product, int bpartner, boolean checkPeriodIsStartPeriod)

	{

		log.info("======= Check Advance Disbursement Amount ======");

		StringBuilder sql = new StringBuilder();

		BigDecimal advanceDisbursementAmt = BigDecimal.ZERO;

		BigDecimal qty = BigDecimal.ZERO;
		sql.append("SELECT COALESCE(SUM(ConvertedAmt),0) FROM ( ");
		sql.append("SELECT COALESCE(SUM(adl.RealizedAmt),0) AS ConvertedAmt FROM XX_DisRealizationLine adl "
				+

				"INNER JOIN XX_DisbursementRealization ad "
				+

				"ON (adl.XX_DisbursementRealization_ID = ad.XX_DisbursementRealization_ID) "
				+

				"INNER JOIN C_Charge_Acct ca ON (adl.C_Charge_ID = ca.C_Charge_ID) "
				+

				"INNER JOIN C_ValidCombination vc ON (ca.CH_Expense_Acct = vc.C_ValidCombination_ID) "
				+

				"WHERE  ad.XX_AdvanceDisbursement_ID IN ("
				+

				"SELECT ad.XX_AdvanceDisbursement_ID FROM XX_AdvanceDisbursementLine adl "
				+

				"INNER JOIN XX_AdvanceDisbursement ad ON (adl.XX_AdvanceDisbursement_ID = ad.XX_AdvanceDisbursement_ID) "
				+

				"INNER JOIN C_Charge_Acct ca ON (adl.C_Charge_ID = ca.C_Charge_ID) "
				+

				"INNER JOIN C_ValidCombination vc ON (ca.CH_Expense_Acct = vc.C_ValidCombination_ID) "
				+

				" AND ad.Processed = 'Y' " +

				"AND ca.C_AcctSchema_ID = ? " +

				"AND adl.AD_Org_ID = ? ");

		if (accountID != 0)

		{
			sql.append(" AND vc.Account_ID = ? ");
		}

		sql.append(getTimeSyntax("DateDoc", "ad"));

		sql.append(" ) " +

		"AND ad.Processed = 'Y' " +

		"AND ca.C_AcctSchema_ID = ? " +

		"AND adl.AD_Org_ID = ? ");

		if (accountID != 0)

		{
			sql.append(" AND vc.Account_ID = ? ");
		}

		sql.append(getTimeSyntax("DateDoc", "ad"));

		sql.append(" UNION ");

		sql.append("SELECT COALESCE(SUM(adl.ConvertedAmt),0) AS ConvertedAmt FROM XX_AdvanceDisbursementLine adl "
				+

				"INNER JOIN XX_AdvanceDisbursement ad ON (adl.XX_AdvanceDisbursement_ID = ad.XX_AdvanceDisbursement_ID) "
				+

				"INNER JOIN C_Charge_Acct ca ON (adl.C_Charge_ID = ca.C_Charge_ID) "
				+

				"INNER JOIN C_ValidCombination vc ON (ca.CH_Expense_Acct = vc.C_ValidCombination_ID) "
				+

				"WHERE NOT EXISTS ("
				+

				"SELECT drl.* FROM XX_DisRealizationLine drl "
				+

				"INNER JOIN XX_DisbursementRealization dr ON (drl.XX_DisbursementRealization_ID = dr.XX_DisbursementRealization_ID) "
				+

				"WHERE adl.XX_AdvanceDisbursementLine_ID = drl.XX_AdvanceDisbursementLine_ID "
				+

				"AND dr.Processed = 'Y' AND DocStatus IN ('CO', 'CL')" +

				") " +

				"AND ad.Processed = 'Y' " +

				"AND ca.C_AcctSchema_ID = ? " +

				"AND adl.AD_Org_ID = ? ");

		if (accountID != 0)

		{
			sql.append(" AND vc.Account_ID = ? ");
		}

		sql.append(getTimeSyntax("DateExpected", "ad"));

		sql.append(" )");
		Integer productID = null;

		if (product != 0)

			productID = project;

		// sql.append(getWhereSyntax(null, "adl", activity, campaign, project,
		// productID, bpartner));

		//

		// // Check User Element

		// if(userElement1!=null)

		// sql.append(getUserElementSyntax("adl", "UserElement1_ID",
		// userElement1Value));

		// if(userElement2!=null)

		// sql.append(getUserElementSyntax("adl", "UserElement2_ID",
		// userElement1Value));

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);

		ResultSet rs = null;

		try {

			log.info(sql.toString());

			int index = 1;

			log.info(String.valueOf(C_AcctSchema_ID));

			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, C_AcctSchema_ID);

			pstmt.setInt(index++, AD_Org_ID);

			if (accountID != 0)

			{

				log.info(String.valueOf(accountID));

				pstmt.setInt(index++, accountID);

			}

			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly))

			{

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate))

			{

				startDate = getYearStartDate(checkPeriodIsStartPeriod);

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total))

			{

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, endDate);

			}

			log.info(String.valueOf(C_AcctSchema_ID));

			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, C_AcctSchema_ID);

			pstmt.setInt(index++, AD_Org_ID);

			if (accountID != 0)

			{

				log.info(String.valueOf(accountID));

				pstmt.setInt(index++, accountID);

			}

			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly))

			{

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate))

			{

				startDate = getYearStartDate(checkPeriodIsStartPeriod);

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total))

			{

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, endDate);

			}

			log.info(String.valueOf(C_AcctSchema_ID));

			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, C_AcctSchema_ID);

			pstmt.setInt(index++, AD_Org_ID);

			if (accountID != 0)

			{

				log.info(String.valueOf(accountID));

				pstmt.setInt(index++, accountID);

			}

			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly))

			{

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate))

			{

				startDate = getYearStartDate(checkPeriodIsStartPeriod);

				log.info(String.valueOf(startDate));

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, startDate);

				pstmt.setTimestamp(index++, endDate);

			}

			else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total))

			{

				log.info(String.valueOf(endDate));

				pstmt.setTimestamp(index++, endDate);

			}

			rs = pstmt.executeQuery();

			if (rs.next())

			{

				advanceDisbursementAmt = rs.getBigDecimal(1);

				// if(remainingQty.signum()>0)

				// qty = rs.getBigDecimal(2);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		finally

		{

			DB.closeResultSet(rs);

			DB.closeStatement(pstmt);

		}

		remainingAmount = remainingAmount.subtract(advanceDisbursementAmt);

		remainingQty = remainingQty.subtract(qty);

		log.info("Advance Disbursement Amount " + advanceDisbursementAmt);

		log.info("Remaining Amt : " + remainingAmount);

		log.info(" ");

	}

	private void checkBudgetRevisionAmt(int AD_Org_ID, int accountID,
			int C_AcctSchema_ID, int activity, int campaign, int project,
			int product, int bpartner, boolean checkPeriodIsStartPeriod) {
		log.info("======= Check Budget Revision (In Progress) Amount ======");
		StringBuilder sql = new StringBuilder();
		BigDecimal budgetRevisionAmt = BigDecimal.ZERO;

		sql.append("SELECT COALESCE(SUM(bra.AppliedAmt),0) AS AppliedAmt FROM XX_BudgetRevision br "
				+ "INNER JOIN XX_BudgetRevisionAccount bra ON br.XX_BudgetRevision_ID = bra.XX_BudgetRevision_ID "
				+ "WHERE br.DocStatus IN ('IP', 'AP') "
				+ "AND br.BudgetRevisionType IN ('C', 'T') "
				+ " AND br.AD_Org_ID = ? ");
		if (accountID != 0)

		{
			sql.append(" AND bra.Account_ID = ? ");
		}

		sql.append(getWhereSyntax(null, "br", activity, campaign, project,
				null, null));

		// Check User Element
		// if(userElement1!=null)
		// sql.append(getUserElementSyntax("adl", "UserElement1_ID",
		// userElement1Value));
		// if(userElement2!=null)
		// sql.append(getUserElementSyntax("adl", "UserElement2_ID",
		// userElement1Value));

		sql.append(getTimeSyntax("DateEffective", "br"));

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try {
			log.info(sql.toString());
			int index = 1;
			log.info(String.valueOf(accountID));
			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, AD_Org_ID);
			if (accountID != 0)

			{
				pstmt.setInt(index++, accountID);
			}

			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			rs = pstmt.executeQuery();

			if (rs.next())
				budgetRevisionAmt = rs.getBigDecimal(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		remainingAmount = remainingAmount.subtract(budgetRevisionAmt);

		log.info("Budget Revision Amount " + budgetRevisionAmt);
		log.info("Remaining Amt : " + remainingAmount);
		log.info(" ");

	}

	private void checkCashAmt(int AD_Org_ID, int accountID,
			int C_AcctSchema_ID, int activity, int campaign, int project,
			int product, int bpartner, boolean checkPeriodIsStartPeriod) {
		log.info("======= Check Cash Journal (In Progress) Amount ======");
		StringBuilder sql = new StringBuilder();
		BigDecimal budgetRevisionAmt = BigDecimal.ZERO;

		sql.append("SELECT COALESCE(SUM(ol.Amount),0) FROM C_CashLine ol "
				+ "INNER JOIN C_Cash o ON o.C_Cash_ID = ol.C_Cash_ID "
				+ "INNER JOIN C_Charge_Acct pa ON pa.C_Charge_ID = ol.C_Charge_ID "
				+ "INNER JOIN C_ValidCombination vc ON vc.C_ValidCombination_ID = pa.Ch_Expense_Acct "
				+ "WHERE  ol.AD_Org_ID = ? "
				+ "AND o.DocStatus IN ('IP', 'AP') ");

		if (accountID != 0)

		{
			sql.append(" AND vc.Account_ID = ? ");
		}

		sql.append(getWhereSyntax(null, "o", null, null, null, null, null));
		sql.append(getTimeSyntax("DateAcct", "o"));

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try {
			log.info(sql.toString());
			int index = 1;
			log.info(String.valueOf(accountID));
			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, AD_Org_ID);
			if (accountID != 0)

			{
				pstmt.setInt(index++, accountID);
			}
			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			rs = pstmt.executeQuery();

			if (rs.next())
				budgetRevisionAmt = rs.getBigDecimal(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		remainingAmount = remainingAmount.subtract(budgetRevisionAmt);

		log.info("Cash Journal Amount " + budgetRevisionAmt);
		log.info("Remaining Amt : " + remainingAmount);
		log.info(" ");

	}

	private void checkJournalAmt(int AD_Org_ID, int accountID,
			int C_AcctSchema_ID, int activity, int campaign, int project,
			int product, int bpartner, boolean checkPeriodIsStartPeriod) {
		log.info("======= Check GL Journal (In Progress) Amount ======");
		StringBuilder sql = new StringBuilder();
		BigDecimal budgetRevisionAmt = BigDecimal.ZERO;
		sql.append("SELECT COALESCE(SUM(Total),0) FROM ( ");
		sql.append("SELECT COALESCE(SUM(ol.AmtAcctCr),0) As Total FROM GL_JournalLine ol "
				+ "INNER JOIN GL_Journal o ON o.GL_Journal_ID = ol.GL_Journal_ID "
				+ "INNER JOIN C_ValidCombination vc ON vc.C_ValidCombination_ID = ol.C_ValidCombination_ID "
				+ "WHERE  ol.AmtAcctCr>0 "
				+ "AND ol.AD_Org_ID = ? "
				+ "AND o.DocStatus IN ('IP', 'AP') ");
		if (accountID != 0)

		{
			sql.append(" AND vc.Account_ID = ? ");
		}
		sql.append(getWhereSyntax(null, "o", null, null, null, null, null));
		sql.append(getTimeSyntax("DateAcct", "o"));

		sql.append("UNION "
				+ "SELECT COALESCE(SUM(ol.AmtAcctDr),0) As Total FROM GL_JournalLine ol "
				+ "INNER JOIN GL_Journal o ON o.GL_Journal_ID = ol.GL_Journal_ID "
				+ "INNER JOIN C_ValidCombination vc ON vc.C_ValidCombination_ID = ol.C_ValidCombination_ID "
				+ "WHERE  ol.AmtAcctDr>0 " + "AND ol.AD_Org_ID = ? "
				+ "AND o.DocStatus IN ('IP', 'AP') ");
		if (accountID != 0)

		{
			sql.append(" AND vc.Account_ID = ? ");
		}
		sql.append(getWhereSyntax(null, "o", null, null, null, null, null));
		sql.append(getTimeSyntax("DateAcct", "o"));
		sql.append(" ) ");
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try {
			log.info(sql.toString());
			int index = 1;
			log.info(String.valueOf(accountID));
			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, accountID);

			if (accountID != 0)

			{
				pstmt.setInt(index++, AD_Org_ID);
			}

			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			pstmt.setInt(index++, accountID);

			if (accountID != 0)

			{
				pstmt.setInt(index++, AD_Org_ID);
			}

			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			rs = pstmt.executeQuery();

			if (rs.next())
				budgetRevisionAmt = rs.getBigDecimal(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		remainingAmount = remainingAmount.subtract(budgetRevisionAmt);

		log.info("GL Journal Amount " + budgetRevisionAmt);
		log.info("Remaining Amt : " + remainingAmount);
		log.info(" ");

	}

	private void checkPOAmt(int AD_Org_ID, int accountID, int C_AcctSchema_ID,
			int activity, int campaign, int project, int product, int bpartner,
			boolean checkPeriodIsStartPeriod) {
		log.info("======= Check Purchase Order (In Progress) Amount ======");
		StringBuilder sql = new StringBuilder();
		BigDecimal budgetRevisionAmt = BigDecimal.ZERO;
		sql.append("SELECT COALESCE(SUM(Total),0) FROM (");
		sql.append("SELECT COALESCE(SUM(ol.LineNetAmt),0) As Total FROM C_OrderLine ol "
				+ "INNER JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID "
				+ "INNER JOIN M_PRoduct_Acct pa ON pa.M_PRoduct_ID = ol.M_Product_ID "
				+ "INNER JOIN C_ValidCombination vc ON vc.C_ValidCombination_ID = pa.P_Expense_Acct "
				+ "WHERE  ol.AD_Org_ID = ? "
				+ "AND o.DocStatus IN ('IP', 'AP') ");
		if (accountID != 0)

		{
			sql.append(" AND vc.Account_ID = ? ");
		}
		sql.append(getWhereSyntax(null, "o", null, null, null, null, null));
		sql.append(getTimeSyntax("DateOrdered", "o"));

		sql.append("UNION "
				+ "SELECT COALESCE(SUM(ol.LineNetAmt),0) As Total FROM C_OrderLine ol "
				+ "INNER JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID "
				+ "INNER JOIN C_Charge_Acct pa ON pa.C_Charge_ID = ol.C_Charge_ID "
				+ "INNER JOIN C_ValidCombination vc ON vc.C_ValidCombination_ID = pa.Ch_Expense_Acct "
				+ "WHERE  ol.AD_Org_ID = ? "
				+ "AND o.DocStatus IN ('IP', 'AP') ");
		if (accountID != 0)

		{
			sql.append(" AND vc.Account_ID = ? ");
		}
		sql.append(getWhereSyntax(null, "o", null, null, null, null, null));
		sql.append(getTimeSyntax("DateOrdered", "o"));
		sql.append(" )");
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try {
			log.info(sql.toString());
			int index = 1;
			log.info(String.valueOf(accountID));
			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, AD_Org_ID);
			if (accountID != 0)

			{
				pstmt.setInt(index++, accountID);
			}
			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			log.info(String.valueOf(accountID));
			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, AD_Org_ID);
			if (accountID != 0)

			{
				pstmt.setInt(index++, accountID);
			}
			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			rs = pstmt.executeQuery();

			if (rs.next())
				budgetRevisionAmt = rs.getBigDecimal(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		remainingAmount = remainingAmount.subtract(budgetRevisionAmt);

		log.info("Purchase Order Amount " + budgetRevisionAmt);
		log.info("Remaining Amt : " + remainingAmount);
		log.info(" ");

	}

	private void checkPOCompleteNoInvoiceAmt(int AD_CLient_ID, int AD_Org_ID,
			int accountID, int C_AcctSchema_ID, int activity, int campaign,
			int project, int product, int bpartner,
			boolean checkPeriodIsStartPeriod) {
		log.info("======= Check Purchase Order (In Progress) Amount ======");
		StringBuilder sql = new StringBuilder();
		BigDecimal budgetRevisionAmt = BigDecimal.ZERO;
		sql.append("SELECT COALESCE(SUM(Total),0) FROM (");
		sql.append("SELECT COALESCE(SUM(ol.LineNetAmt),0) As Total FROM C_OrderLine ol "
				+ "INNER JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID "
				+ "INNER JOIN M_PRoduct_Acct pa ON pa.M_PRoduct_ID = ol.M_Product_ID "
				+ "INNER JOIN C_ValidCombination vc ON vc.C_ValidCombination_ID = pa.P_Expense_Acct "
				+ "WHERE ol.AD_Org_ID = ? "
				+ "AND o.DocStatus IN ('CO', 'CL') "
				+ "AND o.C_Order_ID NOT IN "
				+ "(SELECT C_Order_ID FROM C_Invoice WHERE AD_Client_ID = ? AND IsSOTrx = 'N' "
				+ "AND DocStatus IN ('CO','CL') ) ");
		if (accountID != 0) {
			sql.append(" AND vc.Account_ID = ? ");
		}
		sql.append(getWhereSyntax(null, "o", null, null, null, null, null));
		sql.append(getTimeSyntax("DateOrdered", "o"));

		sql.append("UNION "
				+ "SELECT COALESCE(SUM(ol.LineNetAmt),0) As Total FROM C_OrderLine ol "
				+ "INNER JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID "
				+ "INNER JOIN C_Charge_Acct pa ON pa.C_Charge_ID = ol.C_Charge_ID "
				+ "INNER JOIN C_ValidCombination vc ON vc.C_ValidCombination_ID = pa.Ch_Expense_Acct "
				+ "WHERE ol.AD_Org_ID = ? "
				+ "AND o.DocStatus IN ('CO', 'CL') "
				+ "AND o.C_Order_ID NOT IN "
				+ "(SELECT C_Order_ID FROM C_Invoice WHERE AD_Client_ID = ? AND IsSOTrx = 'N' "
				+ "AND DocStatus IN ('CO','CL')) ");
		if (accountID != 0) {
			sql.append(" AND vc.Account_ID = ? ");
		}
		sql.append(getWhereSyntax(null, "o", null, null, null, null, null));
		sql.append(getTimeSyntax("DateOrdered", "o"));
		sql.append(" )");
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try {
			log.info(sql.toString());
			int index = 1;
			log.info(String.valueOf(accountID));
			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, AD_Org_ID);
			pstmt.setInt(index++, AD_CLient_ID);
			if (accountID != 0) {
				pstmt.setInt(index++, accountID);
			}
			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			log.info(String.valueOf(accountID));
			log.info(String.valueOf(AD_Org_ID));

			pstmt.setInt(index++, AD_Org_ID);
			pstmt.setInt(index++, AD_CLient_ID);
			if (accountID != 0) {
				pstmt.setInt(index++, accountID);
			}
			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			rs = pstmt.executeQuery();

			if (rs.next())
				budgetRevisionAmt = rs.getBigDecimal(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		remainingAmount = remainingAmount.subtract(budgetRevisionAmt);

		log.info("Purchase Order Amount " + budgetRevisionAmt);
		log.info("Remaining Amt : " + remainingAmount);
		log.info(" ");

	}

	private void checkPRAmt(int AD_Org_ID, int accountID, int C_AcctSchema_ID,
			int activity, int campaign, int project, int product, int bpartner,
			boolean checkPeriodIsStartPeriod) {
		log.info("======= Check Purchase Requisition (In Progress) Amount ======");
		StringBuilder sql = new StringBuilder();
		BigDecimal budgetRevisionAmt = BigDecimal.ZERO;
		sql.append("SELECT COALESCE(SUM(Total),0) FROM (");
		sql.append("SELECT COALESCE(SUM(ol.LineNetAmt),0) As Total FROM XX_PurchaseRequisitionLine ol "
				+ "INNER JOIN XX_PurchaseRequisition o ON o.XX_PurchaseRequisition_ID = ol.XX_PurchaseRequisition_ID "
				+ "INNER JOIN M_PRoduct_Acct pa ON pa.M_PRoduct_ID = ol.M_Product_ID "
				+ "INNER JOIN C_ValidCombination vc ON vc.C_ValidCombination_ID = pa.P_Expense_Acct "
				+ "WHERE  ol.IsChargedToAccount = 'N' AND ol.Account_ID IS NULL "
				+ "AND ol.AD_Org_ID = ? " + "AND o.DocStatus IN ('IP', 'AP') ");
		if (accountID > 0) {
			sql.append(" AND vc.Account_ID = ? ");
		}
		sql.append(getWhereSyntax(null, "o", null, null, null, null, null));
		sql.append(getTimeSyntax("DateDoc", "o"));

		sql.append("UNION "
				+ "SELECT COALESCE(SUM(ol.LineNetAmt),0) As Total FROM XX_PurchaseRequisitionLine ol "
				+ "INNER JOIN XX_PurchaseRequisition o ON o.XX_PurchaseRequisition_ID = ol.XX_PurchaseRequisition_ID "
				+ "WHERE ol.IsChargedToAccount = 'Y' AND ol.Account_ID >0 "
				+ "AND ol.AD_Org_ID = ? " + "AND o.DocStatus IN ('IP', 'AP') ");

		if (accountID > 0) {
			sql.append(" AND ol.Account_ID = ? ");
		}
		sql.append(getWhereSyntax(null, "o", null, null, null, null, null));
		sql.append(getTimeSyntax("DateDoc", "o"));

		sql.append("UNION "
				+ "SELECT COALESCE(SUM(ol.LineNetAmt),0) As Total FROM XX_PurchaseRequisitionLine ol "
				+ "INNER JOIN XX_PurchaseRequisition o ON o.XX_PurchaseRequisition_ID = ol.XX_PurchaseRequisition_ID "
				+ "INNER JOIN C_Charge_Acct pa ON pa.C_Charge_ID = ol.C_Charge_ID "
				+ "INNER JOIN C_ValidCombination vc ON vc.C_ValidCombination_ID = pa.Ch_Expense_Acct "
				+ "WHERE  ol.AD_Org_ID = ? "
				+ "AND o.DocStatus IN ('IP', 'AP') ");
		if (accountID > 0) {
			sql.append(" AND vc.Account_ID = ? ");
		}
		sql.append(getWhereSyntax(null, "o", null, null, null, null, null));
		sql.append(getTimeSyntax("DateDoc", "o"));

		sql.append("UNION "
				+ "SELECT COALESCE(SUM(ol.LineNetAmt),0) As Total FROM XX_PurchaseRequisitionLine ol "
				+ "INNER JOIN XX_PurchaseRequisition o ON o.XX_PurchaseRequisition_ID = ol.XX_PurchaseRequisition_ID "
				+ "INNER JOIN M_PRoduct_Acct pa ON pa.M_PRoduct_ID = ol.M_Product_ID "
				+ "INNER JOIN C_ValidCombination vc ON vc.C_ValidCombination_ID = pa.P_Expense_Acct "
				+ "WHERE ol.IsChargedToAccount = 'N' AND ol.Account_ID IS NULL "
				+ "AND ol.AD_Org_ID = ? "
				+ "AND o.DocStatus IN ('CO', 'CL') AND ol.C_OrderLine_ID IS NULL ");
		if (accountID > 0) {
			sql.append(" AND vc.Account_ID = ? ");
		}

		sql.append(getWhereSyntax(null, "o", null, null, null, null, null));
		sql.append(getTimeSyntax("DateDoc", "o"));

		sql.append("UNION "
				+ "SELECT COALESCE(SUM(ol.LineNetAmt),0) As Total FROM XX_PurchaseRequisitionLine ol "
				+ "INNER JOIN XX_PurchaseRequisition o ON o.XX_PurchaseRequisition_ID = ol.XX_PurchaseRequisition_ID "
				+ "WHERE  ol.IsChargedToAccount = 'Y' AND ol.Account_ID >0 "
				+ "AND ol.AD_Org_ID = ? "
				+ "AND o.DocStatus IN ('CO', 'CL') AND ol.C_OrderLine_ID IS NULL ");
		if (accountID > 0) {
			sql.append(" AND ol.Account_ID = ? ");
		}
		sql.append(getWhereSyntax(null, "o", null, null, null, null, null));
		sql.append(getTimeSyntax("DateDoc", "o"));

		sql.append("UNION "
				+ "SELECT COALESCE(SUM(ol.LineNetAmt),0) As Total FROM XX_PurchaseRequisitionLine ol "
				+ "INNER JOIN XX_PurchaseRequisition o ON o.XX_PurchaseRequisition_ID = ol.XX_PurchaseRequisition_ID "
				+ "INNER JOIN C_Charge_Acct pa ON pa.C_Charge_ID = ol.C_Charge_ID "
				+ "INNER JOIN C_ValidCombination vc ON vc.C_ValidCombination_ID = pa.Ch_Expense_Acct "
				+ "WHERE ol.AD_Org_ID = ? "
				+ "AND o.DocStatus IN ('CO', 'CL') AND ol.C_OrderLine_ID IS NULL ");

		if(accountID>0){
			sql.append(" AND vc.Account_ID = ? ");
		}
		
		sql.append(getWhereSyntax(null, "o", null, null, null, null, null));
		sql.append(getTimeSyntax("DateDoc", "o"));
		sql.append(" ) ");
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try {
			log.info(sql.toString());
			int index = 1;
			log.info(String.valueOf(accountID));
			log.info(String.valueOf(AD_Org_ID));

			
			pstmt.setInt(index++, AD_Org_ID);
			if(accountID>0){
				pstmt.setInt(index++, accountID);	
			}
			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			log.info(String.valueOf(accountID));
			log.info(String.valueOf(AD_Org_ID));

			
			pstmt.setInt(index++, AD_Org_ID);
			if(accountID>0){
				pstmt.setInt(index++, accountID);	
			}
			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			log.info(String.valueOf(accountID));
			log.info(String.valueOf(AD_Org_ID));

			
			pstmt.setInt(index++, AD_Org_ID);
if(accountID>0){
	pstmt.setInt(index++, accountID);
}
			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			
			pstmt.setInt(index++, AD_Org_ID);
			if(accountID>0){
				pstmt.setInt(index++, accountID);	
			}
			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			log.info(String.valueOf(accountID));
			log.info(String.valueOf(AD_Org_ID));

			
			pstmt.setInt(index++, AD_Org_ID);

			if(accountID>0){
				pstmt.setInt(index++, accountID);	
			}
			
			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			log.info(String.valueOf(accountID));
			log.info(String.valueOf(AD_Org_ID));

		
			pstmt.setInt(index++, AD_Org_ID);

			if(accountID>0){
				pstmt.setInt(index++, accountID);		
			}
			
			if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly)) {
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)) {
				startDate = getYearStartDate(checkPeriodIsStartPeriod);
				log.info(String.valueOf(startDate));
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, startDate);
				pstmt.setTimestamp(index++, endDate);
			} else if (budgetControlScope
					.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total)) {
				log.info(String.valueOf(endDate));
				pstmt.setTimestamp(index++, endDate);
			}

			rs = pstmt.executeQuery();

			if (rs.next())
				budgetRevisionAmt = rs.getBigDecimal(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		remainingAmount = remainingAmount.subtract(budgetRevisionAmt);

		log.info("Purchase Requisition Amount " + budgetRevisionAmt);
		log.info("Remaining Amt : " + remainingAmount);
		log.info(" ");

	}

	/**
	 * 
	 * Get Start Date of fiscal year
	 * 
	 * @return
	 */

	private Timestamp getYearStartDate(boolean checkPeriodIsStartPeriod)

	{

		Timestamp firstDate = null;

		String sql = "SELECT StartDate " +

		"FROM C_Period " +

		"WHERE PeriodNo = 1 " +

		"AND C_Year_ID = (SELECT p.C_Year_ID " +

		"FROM C_Period p " +

		"INNER JOIN C_Year y ON (p.C_Year_ID = y.C_Year_ID) " +

		"WHERE p.PeriodType = 'S' AND y.C_Calendar_ID = ? " +

		"AND ? BETWEEN p.StartDate AND p.EndDate) ";

		PreparedStatement pstmt = DB.prepareStatement(sql, trx);

		ResultSet rs = null;

		try {

			pstmt.setInt(1, BudgetCalendar_ID);

			pstmt.setTimestamp(2, DateTrx);

			rs = pstmt.executeQuery();

			if (rs.next())

			{
				firstDate = rs.getTimestamp(1);
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		finally

		{

			DB.closeResultSet(rs);

			DB.closeStatement(pstmt);

		}

		if (checkPeriodIsStartPeriod) {
			firstDate = DateTrx;
		}

		Calendar cal = Calendar.getInstance();

		cal.setTime(firstDate);

		cal = GeneralEnhancementUtils.resetDate(cal);

		return new Timestamp(cal.getTimeInMillis());

	}

	/**
	 * 
	 * Get Start Date and End Date for selected period
	 */

	private void getPeriodDates(boolean checkPeriodIsStartPeriod)

	{

		int yearID = 0;

		String sql = "SELECT p.StartDate, p.EndDate, p.C_Year_ID " +

		"FROM C_Period p " +

		"INNER JOIN C_Year y ON (p.C_Year_ID = y.C_Year_ID) " +

		"WHERE y.C_Calendar_ID = ? " +

		"AND ? BETWEEN p.StartDate AND p.EndDate ";

		PreparedStatement pstmt = DB.prepareStatement(sql, trx);

		ResultSet rs = null;

		try {

			pstmt.setInt(1, BudgetCalendar_ID);

			pstmt.setTimestamp(2, DateTrx);

			rs = pstmt.executeQuery();

			if (rs.next())

			{

				Timestamp start = null;
				if (checkPeriodIsStartPeriod)
					start = DateTrx;
				else
					start = rs.getTimestamp(1);

				Calendar startCal = Calendar.getInstance();

				startCal.setTime(start);

				startCal = GeneralEnhancementUtils.resetDate(startCal);

				startDate = new Timestamp(startCal.getTimeInMillis());

				Timestamp end = rs.getTimestamp(2);

				Calendar endCal = Calendar.getInstance();

				endCal.setTime(end);

				// if(!currentPeriodOnly && info.getBudgetRange()>0)

				// {

				// endCal.add(Calendar.MONTH, info.getBudgetRange());

				// }

				endCal = GeneralEnhancementUtils.resetDate(endCal);

				endDate = new Timestamp(endCal.getTimeInMillis());

				yearID = rs.getInt(3);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		// Get maximum date of year

		Timestamp maxDate = null;

		sql = "SELECT Max(EndDate) " +

		"FROM C_Period  " +

		"WHERE C_Year_ID = ? ";

		pstmt = DB.prepareStatement(sql, trx);

		rs = null;

		try {

			pstmt.setInt(1, yearID);

			rs = pstmt.executeQuery();

			if (rs.next())

			{

				maxDate = rs.getTimestamp(1);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		finally

		{

			DB.closeResultSet(rs);

			DB.closeStatement(pstmt);

		}

		// If max date is before end date -> end Date == max date

		if (maxDate.before(endDate))

			endDate = maxDate;

	}

	/**
	 * 
	 * Get SELECT syntax for SQL Query
	 * 
	 * @return
	 */

	private StringBuilder getSelectSyntax(int accountID)

	{

		StringBuilder select = new StringBuilder();

		// Get Account's natural sign

		String accountSign = getAccountSign(accountID);

		// End get account's natural sign

		// Set Invoice Spent Amount Syntax Sql

		if (accountSign != null && accountSign.equals("Cr"))

			select.append("SELECT COALESCE(SUM(fa.AmtAcctCr-fa.AmtAcctDr),0) AS Amt, COALESCE(SUM(fa.Qty),0) AS Qty ");

		else

			select.append("SELECT COALESCE(SUM(fa.AmtAcctDr-fa.AmtAcctCr),0) AS Amt, COALESCE(SUM(fa.Qty),0) AS Qty ");

		return select;

	}

	/**
	 * 
	 * Get FROM Syntax for Fact_Acct table
	 * 
	 * @param PostingType
	 * 
	 * @return
	 */

	private StringBuilder getFromFactSyntax(int Account_ID,
			int C_AcctSchema_ID, int AD_Org_ID, String PostingType)

	{

		StringBuilder from = new StringBuilder();

		from.append("FROM Fact_Acct fa " +

		"WHERE  fa.C_AcctSchema_ID = ? " + // 1

				"AND fa.AD_Org_ID = ? " + // 2

				"AND fa.PostingType = '" + PostingType + "' ");

		if (Account_ID != 0)

			from.append("AND fa.Account_ID = ? "); // 3

		return from;

	}

	/**
	 * 
	 * Get WHERE syntax for sql query
	 * 
	 * 
	 * 
	 * Check Types :
	 * 
	 * - B Budget Check
	 * 
	 * - S Invoice Check
	 * 
	 * - Reservation Check
	 * 
	 * - C Commitment Check
	 * 
	 * - A Advance Disbursement Check
	 * 
	 * - I Inventory Requisition Check
	 * 
	 * 
	 * 
	 * @param PostingType
	 * 
	 * @return
	 */

	private StringBuilder getWhereSyntax(String PostingType, String tableAlias,
			Integer activity, Integer campaign, Integer project,

			Integer product, Integer bpartner)

	{

		StringBuilder where = new StringBuilder();

		if (PostingType != null)

		{

			if (PostingType.equals(X_GL_Journal.POSTINGTYPE_Budget)
					& GL_Budget_ID != 0)

			{

				where.append("AND " + tableAlias + ".GL_Budget_ID = "
						+ GL_Budget_ID + " ");

			}

			else if (PostingType.equals(X_GL_Journal.POSTINGTYPE_Budget)
					& GL_Budget_ID == 0)

			{

				where.append("AND (" + tableAlias + ".GL_Budget_ID = 0 OR "
						+ tableAlias + ".GL_Budget_ID IS NULL) ");

			}

		}

		if (activity != null)

		{

			if (activity > 0)

			{

				where.append("AND " + tableAlias + ".C_Activity_ID = "
						+ activity + " ");

			}

			else

			{

				where.append("AND (" + tableAlias + ".C_Activity_ID = 0 OR "
						+ tableAlias + ".C_Activity_ID IS NULL) ");

			}

		}

		if (campaign != null)

		{

			if (campaign != 0)

			{

				where.append("AND " + tableAlias + ".C_Campaign_ID = "
						+ campaign + " ");

			}

			else

			{

				where.append("AND (" + tableAlias + ".C_Campaign_ID = 0 OR "
						+ tableAlias + ".C_Campaign_ID IS NULL) ");

			}

		}

		if (project != null)

		{

			if (project != 0)

			{

				where.append("AND " + tableAlias + ".C_Project_ID = " + project
						+ " ");

			}

			else

			{

				where.append("AND (" + tableAlias + ".C_Project_ID = 0 OR "
						+ tableAlias + ".C_Project_ID IS NULL) ");

			}

		}

		if (product != null)

		{

			if (product != 0)

			{

				where.append("AND " + tableAlias + ".M_Product_ID = " + product
						+ " ");

			}

			else

				where.append("AND (" + tableAlias + ".M_Product_ID = 0 OR "
						+ tableAlias + ".M_Product_ID IS NULL) ");

		}

		if (bpartner != null)

		{

			if (bpartner != 0)

			{

				where.append("AND " + tableAlias + ".C_BPartner_ID = "
						+ bpartner + " ");

			}

			else

				where.append("AND (" + tableAlias + ".C_BPartner_ID = 0 OR "
						+ tableAlias + ".C_BPartner_ID IS NULL) ");

		}

		return where;

	}

	/**
	 * 
	 * Get syntax if user element exists
	 * 
	 * 
	 * 
	 * @param tableAlias
	 * 
	 * @param columnName
	 * 
	 * @param columnValue
	 * 
	 * @return
	 */

	private StringBuilder getUserElementSyntax(String tableAlias,
			String columnName, int columnValue)

	{

		StringBuilder syntax = new StringBuilder();

		if (columnValue > 0)

		{

			syntax.append("AND " + tableAlias + "." + columnName + " = "
					+ columnValue + " ");

		}

		else

		{

			syntax.append("AND (" + tableAlias + "." + columnName + " = 0 OR "
					+ tableAlias + "." + columnName + " IS NULL) ");

		}

		return syntax;

	}

	/**
	 * 
	 * Get Time Syntax for SQL query
	 * 
	 * @return
	 */

	private StringBuilder getTimeSyntax(String DateColumnName, String tableAlias)

	{

		StringBuilder timeSyntax = new StringBuilder();

		// Set Time Syntax SQL

		if ((budgetControlScope
				.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_PeriodOnly))

				|| (budgetControlScope
						.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_YearToDate)))

		{

			if (tableAlias == null)

				timeSyntax
						.append("AND " + DateColumnName + " BETWEEN ? AND ? ");

			else

				timeSyntax.append("AND " + tableAlias + "." + DateColumnName
						+ " BETWEEN ? AND ? ");

		}

		else if (budgetControlScope
				.equals(X_GL_BudgetControl.BUDGETCONTROLSCOPE_Total))

		{

			if (tableAlias == null)

				timeSyntax.append("AND " + DateColumnName + " < ? ");

			else

				timeSyntax.append("AND " + tableAlias + "." + DateColumnName
						+ " < ? ");

		}

		return timeSyntax;

	}

	/**
	 * 
	 * Get natural sign of account
	 * 
	 * Debit - Dr
	 * 
	 * Credit - Cr
	 * 
	 * @return
	 */

	private String getAccountSign(int accountID)

	{

		String sign = null;

		if (accountID == 0)

			return sign;

		MElementValue ev = new MElementValue(ctx, Account_ID, trx);

		if ((ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Asset))

		|| (ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Expense)))

			sign = "Dr";

		else if ((ev.getAccountType()
				.equals(X_C_ElementValue.ACCOUNTTYPE_Liability))

				|| (ev.getAccountType()
						.equals(X_C_ElementValue.ACCOUNTTYPE_Revenue))

				|| (ev.getAccountType()
						.equals(X_C_ElementValue.ACCOUNTTYPE_OwnerSEquity)))

			sign = "Cr";

		else
			sign = ev.getAccountSign(); // For Memo

		return sign;

	}

	/**
	 * 
	 * Get C_Currency_ID
	 * 
	 * @return
	 */

	public int getC_Currency_ID()

	{

		return C_Currency_ID;

	}

	/**
	 * 
	 * Get ISO Currency code for a currency
	 * 
	 * @return
	 */

	public String getCurrencyISOCode()

	{

		MCurrency currency = new MCurrency(ctx, C_Currency_ID, trx);

		// return currency.getISO_Code();

		return currency.getCurSymbol();

	}

	/**
	 * 
	 * Get Remaining Budget
	 * 
	 * @return
	 */

	public BigDecimal getRemainingBudget() {
		return remainingAmount;
	}

	/**
	 * 
	 * Get Remaining Budget Qty
	 * 
	 * @return remaining qty
	 */

	public BigDecimal getRemainingBudgetQty() {
		return remainingQty;
	}

	public boolean getAccountNotBudgeted() {
		return notBudgeted;
	}
	
//	private int getBudgetControlID(MBudgetInfo info, int C_Campaign_ID, int C_Project_ID, Trx trx)
//	{
//		int controlID = 0;
//		if(C_Campaign_ID!=0)
//			controlID = MAcctElementBudgetControl.getBudgetControl(info.getXX_BudgetInfo_ID(), MAcctElementBudgetControl.ELEMENTTYPE_Campaign, trx);
//		if(C_Project_ID!=0)
//			controlID = MAcctElementBudgetControl.getBudgetControl(info.getXX_BudgetInfo_ID(), MAcctElementBudgetControl.ELEMENTTYPE_Project, trx);
//		if(controlID==0)
//			controlID = info.getGL_BudgetControl_ID();
//		return controlID;
//	}

}
