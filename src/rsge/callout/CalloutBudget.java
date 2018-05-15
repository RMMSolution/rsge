/**
 * 
 */
package rsge.callout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.X_GL_BudgetControl;
import rsge.model.MAssetGroup;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.model.MBudgetForm;
import rsge.model.MBudgetInfo;
import rsge.model.MBudgetPlanning;
import rsge.model.MBudgetRevision;
import rsge.model.MCorpBudgetPlan;
import rsge.model.MInvestmentBudget;
import rsge.model.MInvestmentBudgetLine;
import rsge.model.MProduct;
import rsge.po.X_XX_BudgetRevision;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY
 *
 */
public class CalloutBudget extends CalloutEngine {

	/** Callout for Budget Info
	 * 
	 * Define Accounting Schema from selected Budget Control
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setGL_BudgetControl_ID (Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{		
		if(value == null)
			return "";
		
		int GL_BudgetControl_ID = (Integer) value;
		if(GL_BudgetControl_ID == 0)
			return "";
		
		String sql = "SELECT C_AcctSchema_ID " +
				"FROM GL_BudgetControl " +
				"WHERE GL_BudgetControl_ID = " + GL_BudgetControl_ID;
		
		PreparedStatement pstmt = DB.prepareStatement(sql,(Trx)null);
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				mTab.setValue("C_AcctSchema_ID", rs.getInt(1));				
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}		
		return "";
	}

	public String setRevisedQty(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{		
		BigDecimal qty = (BigDecimal) mTab.getValue("RevisedQty");
		mTab.setValue("Qty", qty);
		return "";
	}
	
	/** Callout for Budget Plan					*/
	/**
	 * Set GL Budget, Planning Period and Period From based on selected Corporate Budget Plan
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setCorpBudgetPlan(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{		
		if(value == null)
			return "";
		
		int XX_CorpBudgetPlan_ID = (Integer) value;
		if(XX_CorpBudgetPlan_ID == 0)
			return "";
		
		MCorpBudgetPlan cbp = new MCorpBudgetPlan(ctx, XX_CorpBudgetPlan_ID, (Trx)null);
		mTab.setValue("GL_Budget_ID", cbp.getGL_Budget_ID());
		mTab.setValue("PlanningPeriod", cbp.getPlanningPeriod());
		mTab.setValue("C_Period_From_ID", cbp.getC_From_Period_ID());
		
		return "";
	}
	
	public String setPrice (Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
//		if (isCalloutActive() || value == null )
//			return "";
//		setCalloutActive(true);
		Integer budgetFormID = (Integer)mTab.getValue("XX_BudgetForm_ID");
		MBudgetForm bf = new MBudgetForm(ctx, budgetFormID.intValue(), (Trx)null);
		
		if(!bf.isProduct()
				|| (bf.isProduct() && bf.isUsedProductTree()))
			return "";
		
		BigDecimal price = (BigDecimal) mTab.getValue("Price");
		
		BigDecimal period1 = (BigDecimal) mTab.getValue("QtyPeriod1");
		BigDecimal period1Amt = period1.multiply(price);
		mTab.setValue("Period1", period1Amt);
		
		BigDecimal period2 = (BigDecimal) mTab.getValue("QtyPeriod2");
		BigDecimal period2Amt = period2.multiply(price);
		mTab.setValue("Period2", period2Amt);
		
		BigDecimal period3 = (BigDecimal) mTab.getValue("QtyPeriod3");
		BigDecimal period3Amt = period3.multiply(price);
		mTab.setValue("Period3", period3Amt);
		
		BigDecimal period4 = (BigDecimal) mTab.getValue("QtyPeriod4");
		BigDecimal period4Amt = period4.multiply(price);
		mTab.setValue("Period4", period4Amt);
		
		BigDecimal period5 = (BigDecimal) mTab.getValue("QtyPeriod5");
		BigDecimal period5Amt = period5.multiply(price);
		mTab.setValue("Period5", period5Amt);
		
		BigDecimal period6 = (BigDecimal) mTab.getValue("QtyPeriod6");
		BigDecimal period6Amt = period6.multiply(price);
		mTab.setValue("Period6", period6Amt);
		
		BigDecimal period7 = (BigDecimal) mTab.getValue("QtyPeriod7");
		BigDecimal period7Amt = period7.multiply(price);
		mTab.setValue("Period7", period7Amt);
		
		BigDecimal period8 = (BigDecimal) mTab.getValue("QtyPeriod8");
		BigDecimal period8Amt = period8.multiply(price);
		mTab.setValue("Period8", period8Amt);
		
		BigDecimal period9 = (BigDecimal) mTab.getValue("QtyPeriod9");
		BigDecimal period9Amt = period9.multiply(price);
		mTab.setValue("Period9", period9Amt);
		
		BigDecimal period10 = (BigDecimal) mTab.getValue("QtyPeriod10");
		BigDecimal period10Amt = period10.multiply(price);
		mTab.setValue("Period10", period10Amt);
		
		BigDecimal period11 = (BigDecimal) mTab.getValue("QtyPeriod11");
		BigDecimal period11Amt = period11.multiply(price);
		mTab.setValue("Period11", period11Amt);
		
		BigDecimal period12 = (BigDecimal) mTab.getValue("QtyPeriod12");
		BigDecimal period12Amt = period12.multiply(price);
		mTab.setValue("Period12", period12Amt);
		return "";
	}
	
	
	/**
	 * Callout for Budget Form
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setTotalQty (Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer budgetFormID = (Integer)mTab.getValue("XX_BudgetForm_ID");
		MBudgetForm bf = new MBudgetForm(ctx, budgetFormID.intValue(), (Trx)null);
		
		BigDecimal price = BigDecimal.ONE;
		if(bf.isProduct()
				|| (bf.isProduct() && !bf.isUsedProductTree()))
			price = (BigDecimal) mTab.getValue("Price");
		BigDecimal qty = BigDecimal.ZERO;
		BigDecimal periodAmt = BigDecimal.ZERO;
		if(mField.getColumnName().equals("QtyPeriod1"))
		{
			qty = (BigDecimal)mTab.getValue("QtyPeriod1");
			periodAmt = qty.multiply(price);
			mTab.setValue("Period1", periodAmt);
		}
		else if(mField.getColumnName().equals("QtyPeriod2"))
		{
			qty = (BigDecimal)mTab.getValue("QtyPeriod2");
			periodAmt = qty.multiply(price);
			mTab.setValue("Period2", periodAmt);
		}
		else if(mField.getColumnName().equals("QtyPeriod3"))
		{
			qty = (BigDecimal)mTab.getValue("QtyPeriod3");
			periodAmt = qty.multiply(price);
			mTab.setValue("Period3", periodAmt);
		}
		else if(mField.getColumnName().equals("QtyPeriod4"))
		{
			qty = (BigDecimal)mTab.getValue("QtyPeriod4");
			periodAmt = qty.multiply(price);
			mTab.setValue("Period4", periodAmt);
		}
		else if(mField.getColumnName().equals("QtyPeriod5"))
		{
			qty = (BigDecimal)mTab.getValue("QtyPeriod5");
			periodAmt = qty.multiply(price);
			mTab.setValue("Period5", periodAmt);
		}
		else if(mField.getColumnName().equals("QtyPeriod6"))
		{
			qty = (BigDecimal)mTab.getValue("QtyPeriod6");
			periodAmt = qty.multiply(price);
			mTab.setValue("Period6", periodAmt);
		}
		else if(mField.getColumnName().equals("QtyPeriod7"))
		{
			qty = (BigDecimal)mTab.getValue("QtyPeriod7");
			periodAmt = qty.multiply(price);
			mTab.setValue("Period7", periodAmt);
		}
		else if(mField.getColumnName().equals("QtyPeriod8"))
		{
			qty = (BigDecimal)mTab.getValue("QtyPeriod8");
			periodAmt = qty.multiply(price);
			mTab.setValue("Period8", periodAmt);
		}
		else if(mField.getColumnName().equals("QtyPeriod9"))
		{
			qty = (BigDecimal)mTab.getValue("QtyPeriod9");
			periodAmt = qty.multiply(price);
			mTab.setValue("Period9", periodAmt);
		}
		else if(mField.getColumnName().equals("QtyPeriod10"))
		{
			qty = (BigDecimal)mTab.getValue("QtyPeriod10");
			periodAmt = qty.multiply(price);
			mTab.setValue("Period10", periodAmt);
		}
		else if(mField.getColumnName().equals("QtyPeriod11"))
		{
			qty = (BigDecimal)mTab.getValue("QtyPeriod11");
			periodAmt = qty.multiply(price);
			mTab.setValue("Period11", periodAmt);
		}
		else if(mField.getColumnName().equals("QtyPeriod12"))
		{
			qty = (BigDecimal)mTab.getValue("QtyPeriod12");
			periodAmt = qty.multiply(price);
			mTab.setValue("Period12", periodAmt);
		}

		return "";
	}

	
	/**
	 * Callout to set Planning Period and GL Budget in Budget Form
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setXX_BudgetPlanning_ID(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer XX_BudgetPlanning_ID = (Integer) value;
		if(XX_BudgetPlanning_ID == null || XX_BudgetPlanning_ID == 0)
			return "";
		
		MBudgetPlanning bplan = new MBudgetPlanning(ctx, XX_BudgetPlanning_ID, (Trx)null);
		mTab.setValue("PlanningPeriod", bplan.getPlanningPeriod());		
		mTab.setValue("GL_Budget_ID", bplan.getGL_Budget_ID());
		
		return "";
	}
	
	/** 
	 * Set Budget Form based on selected template
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setXX_BudgetFormTemplate_ID(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer XX_BudgetFormTemplate_ID = (Integer) value;

		if(XX_BudgetFormTemplate_ID == null || XX_BudgetFormTemplate_ID == 0)
			return"";
		
		// Get data from Template
		String sql = "SELECT IsProduct, IsSoTrx, TransactionType, IsUsedProductTree " +
				"FROM XX_BudgetFormTemplate " +
				"WHERE XX_BudgetFormTemplate_ID = " + XX_BudgetFormTemplate_ID;
		
		PreparedStatement pstmt = DB.prepareStatement(sql, (Trx)null);
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				mTab.setValue("IsProduct", "Y".equals(rs.getString(1)));
				mTab.setValue("IsSoTrx", "Y".equals(rs.getString(2)));
				mTab.setValue("TransactionType", rs.getString(3));
				if(rs.getString(4).equalsIgnoreCase("Y"))
				{
					mTab.setValue("IsUsedProductTree", true);	
				}
				else	
				{
					mTab.setValue("IsUsedProductTree", false);	
				}

			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}		
		
		return "";
	}

	/** Callout for XX_BudgetPlanningLinePeriod				*/	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setPlanningLineTotalAmt(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		BigDecimal AmtAcctCr = (BigDecimal) mTab.getValue("AmtAcctCr");
		BigDecimal AmtRevCr = (BigDecimal) mTab.getValue("AmtRevCr");
		BigDecimal AmtAcctDr = (BigDecimal) mTab.getValue("AmtAcctDr");
		BigDecimal AmtRevDr = (BigDecimal) mTab.getValue("AmtRevDr");
		
		mTab.setValue("TotalCr", AmtAcctCr.add(AmtRevCr));
		mTab.setValue("TotalDr", AmtAcctDr.add(AmtRevDr));
		
		return "";
	}
	
	/** Callout for Investment Budget	 					*/	
	/**
	 * Callout to set Planning Period in Investment Budget
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setPlanningPeriod(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer XX_BudgetPlanning_ID = (Integer) value;
		if(XX_BudgetPlanning_ID == null || XX_BudgetPlanning_ID == 0)
			return "";
		
		MBudgetPlanning bplan = new MBudgetPlanning(ctx, XX_BudgetPlanning_ID, (Trx)null);
		mTab.setValue("PlanningPeriod", bplan.getPlanningPeriod());		
		
		return "";
	}
	
	/** Callout for Investment Budget Line 					*/

	/**
	 * Set Converted Amount
	 */
	public String setIsDepreciated(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer A_Asset_Group_ID = (Integer) value;
		if(A_Asset_Group_ID == null || A_Asset_Group_ID == 0)
			return "";
		
		MAssetGroup ag = new MAssetGroup(ctx, A_Asset_Group_ID, (Trx)null);
		mTab.setValue("IsDepreciated", ag.isDepreciated());
		mTab.setValue("BudgetAssetDepreciation_Acct", ag.getBudgetAssetDepreciation_Acct());
		return "";
	}
	
	/** Callout for Investment Budget Line Item				*/
	
	public String setTotalAmt(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		BigDecimal price = (BigDecimal) mTab.getValue("PriceEntered");
		Integer qty = (Integer) mTab.getValue("Qty");
		Integer C_Currency_ID = (Integer) mTab.getValue("C_Currency_ID");
		
		if(price.compareTo(BigDecimal.ZERO)==0 || qty == 0)
			return "";		
		BigDecimal totalAmt = GeneralEnhancementUtils.calculateTotalAmt(ctx, price, qty, C_Currency_ID, (Trx)null);		
		mTab.setValue("TotalAmt", totalAmt);
		return "";
	}
	
	/**
	 * Set Converted Amount
	 */
	public String setConvertedAmt(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		BigDecimal TotalAmt = (BigDecimal) value;
		if (TotalAmt.compareTo(BigDecimal.ZERO)==0)
			return "";
		
		// Get Budget info currency
		MBudgetInfo bi = GeneralEnhancementUtils.getBudgetInfo(ctx, ctx.getAD_Client_ID(), (Trx)null);
		X_GL_BudgetControl bc = new X_GL_BudgetControl(ctx, bi.getGL_BudgetControl_ID(), (Trx)null);
		MAcctSchema schema = new MAcctSchema(ctx, bc.getC_AcctSchema_ID(), (Trx)null);
		
		int budgetCurrency_ID = schema.getC_Currency_ID();
		Integer C_Currency_ID = (Integer) mTab.getValue("C_Currency_ID");
		
		if(budgetCurrency_ID != C_Currency_ID) // Convert Total Amount to Budget Currency
		{
			Integer XX_InvestmentBudgetLine_ID = (Integer) mTab.getValue("XX_InvestmentBudgetLine_ID");
			MInvestmentBudgetLine ibl = new MInvestmentBudgetLine(ctx, XX_InvestmentBudgetLine_ID, (Trx)null);
			MInvestmentBudget ib = new MInvestmentBudget(ctx, ibl.getXX_InvestmentBudget_ID(), (Trx)null);
			BigDecimal total = MConversionRate.convert(ctx, TotalAmt, C_Currency_ID, budgetCurrency_ID, ib.getDateDoc(), bi.getC_ConversionType_ID(), 
					ctx.getAD_Client_ID(), ctx.getAD_Org_ID());
			
			MCurrency currency = new MCurrency(ctx, budgetCurrency_ID, (Trx)null);
			total = total.setScale(currency.getStdPrecision(), RoundingMode.HALF_UP);
			mTab.setValue("ConvertedAmt", total);
		}
		else // Set Converted Amount = TotalAmt
		{
			mTab.setValue("ConvertedAmt", TotalAmt); 
		}
		return "";
	}	
	
	/** Callout for Budget Revision								*/

	/**
	 * Set Current Period with period of Date Effective
	 */
	public String setPeriod(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Timestamp date = (Timestamp) value;
		if (date == null)
			return "";
		
		Integer currentPeriod = null;
		StringBuilder sql = new StringBuilder("SELECT p.C_Period_ID " +
				"FROM C_Period p " +
				"INNER JOIN C_Year y On (p.C_Year_ID = y.C_Year_ID) " +
				"INNER JOIN XX_BudgetInfo bi ON (y.C_Calendar_ID = bi.BudgetCalendar_ID) " +
				"WHERE bi.AD_Client_ID = ? " +
				"AND ? BETWEEN p.StartDate AND p.EndDate ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), (Trx)null);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, (Integer) mTab.getValue("AD_Client_ID"));
			pstmt.setTimestamp(2, date);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				currentPeriod = rs.getInt(1);
			}		
			rs.close();
			pstmt.close();			
		}catch (Exception e) {
			e.printStackTrace();
		}			
		mTab.setValue("C_Current_Period_ID", currentPeriod);

		return "";
	}
	
	/**
	 * Set Applied and Percentage
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setAppliedAmt(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		BigDecimal remainingBudget = (BigDecimal) mTab.getValue("RemainingBudget");
		if(remainingBudget.compareTo(BigDecimal.ZERO) == 0)
			return "";
		
		MBudgetInfo bi = GeneralEnhancementUtils.getBudgetInfo(ctx, ctx.getAD_Client_ID(), (Trx)null);
		MAcctSchema as = new MAcctSchema(ctx, bi.getC_AcctSchema_ID(),(Trx)null);
		int C_Currency_ID = as.getC_Currency_ID(); 
		MCurrency currency = new MCurrency(ctx, C_Currency_ID, (Trx)null);
		
		BigDecimal appliedAmt = (BigDecimal)mTab.getValue("AppliedAmt");
		appliedAmt = appliedAmt.setScale(currency.getStdPrecision(), RoundingMode.FLOOR);
		// Get Revision Type
		int XX_BudgetRevision_ID = (Integer) mTab.getValue("XX_BudgetRevision_ID");
		MBudgetRevision revision = new MBudgetRevision(ctx, XX_BudgetRevision_ID, (Trx)null);
		String revisionType = revision.getBudgetRevisionType();
		BigDecimal remainingAmt = (BigDecimal) mTab.getValue("RemainingBudget");
		
		// Product
		BigDecimal remainingQty = (BigDecimal)mTab.getValue("RemainingBudgetQty");
		BigDecimal appliedQty = (BigDecimal)mTab.getValue("AppliedQty");
		if(revisionType.equals(X_XX_BudgetRevision.BUDGETREVISIONTYPE_Increase))
		{
			mTab.setValue("RevisedAmt", remainingAmt.add(appliedAmt));
			if(revision.isNotSummary())
			{
				mTab.setValue("RevisedQty", remainingQty.add(appliedQty));
			}
			else
				mTab.setValue("RevisedQty", remainingQty);
		}
		else
		{
			mTab.setValue("RevisedAmt", remainingAmt.subtract(appliedAmt));
			if(revision.isNotSummary())
			{
				mTab.setValue("RevisedQty", remainingQty.subtract(appliedQty));
			}
			else
				mTab.setValue("RevisedQty", remainingQty);
		}				
		return "";
	}

	public String setAppliedPriceQty(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		BigDecimal priceQty = (BigDecimal) value;
		if(priceQty==null)
			return "";
		
		BigDecimal price = (BigDecimal) mTab.getValue("Price");
		BigDecimal qty = (BigDecimal) mTab.getValue("AppliedQty");
		if(price.compareTo(BigDecimal.ZERO)==0 && qty.compareTo(BigDecimal.ZERO)==0)
			return "";
		
		BigDecimal appliedAmt = price.multiply(qty);
		MBudgetInfo bi = GeneralEnhancementUtils.getBudgetInfo(ctx, ctx.getAD_Client_ID(), (Trx)null);
		MAcctSchema as = new MAcctSchema(ctx, bi.getC_AcctSchema_ID(),(Trx)null);
		int C_Currency_ID = as.getC_Currency_ID(); 
		MCurrency currency = new MCurrency(ctx, C_Currency_ID, (Trx)null);
		
		appliedAmt.setScale(currency.getStdPrecision(), RoundingMode.FLOOR);
		mTab.setValue("AppliedAmt", appliedAmt);
		return "";
	}
	
	public String setXX_BudgetRevision_ID(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer ID = (Integer) value;
		if(ID==null)
			return "";
		
		// Set Is Product
		MBudgetRevision br = new MBudgetRevision(ctx, ID.intValue(), (Trx)null);
		if(br.getM_Product_ID()==0)
			return "";
		
		MProduct product = new MProduct(ctx, br.getM_Product_ID(), (Trx)null);
		if(!product.isSummary())
			mTab.setValue("IsProduct", "Y");
		else
			mTab.setValue("IsProduct", "N");
		
		// Set Account from Product Acct
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		if(br.getTransactionType().equals(X_XX_BudgetRevision.TRANSACTIONTYPE_SalesPurchase))
		{
			if(br.isSOTrx())
			{
				sql.append("pa.P_Revenue_Acct ");				
			}
			else
			{
				sql.append("pa.P_Asset_Acct ");
			}
		}
		else if(br.getTransactionType().equals(X_XX_BudgetRevision.TRANSACTIONTYPE_Return))
		{
			sql.append("(CASE WHEN IsUseReturnAccount = 'Y' THEN pa.P_SalesReturn_Acct ELSE pa.P_Revenue_Acct END) AS Return_Acct ");
		}
		else if(br.getTransactionType().equals(X_XX_BudgetRevision.TRANSACTIONTYPE_Discount))
		{
			sql.append("pa.P_TradeDiscountGrant_Acct ");
		}
		sql.append("FROM M_Product_Acct pa " +
				"INNER JOIN XX_BudgetInfo bi ON (pa.C_AcctSchema_ID = bi.C_AcctSchema_ID AND pa.AD_Client_ID = bi.AD_Client_ID) " +
				"WHERE pa.M_Product_ID = ? ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), (Trx)null);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, br.getM_Product_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				MAccount account = new MAccount(ctx, rs.getInt(1), (Trx)null);
				mTab.setValue("Account_ID", account.getAccount_ID());
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		// Get Price from Price List
		if(br.isNotSummary())
		{
			BigDecimal price = getProductPrice(br.getM_Product_ID(), (Trx)null);
			mTab.setValue("Price", price);
		}
		return "";
	}
	
	public String setM_Product_ID(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer ID = (Integer) value;
		if(ID==null)
			return "";
		
		// Set Is Product
		MProduct product = new MProduct(ctx, ID, (Trx)null);
		if(!product.isSummary())
			mTab.setValue("IsNotSummary", "Y");
		else
			mTab.setValue("IsNotSummary", "N");

		// Set Transaction Type
		mTab.setValue("TransactionType", "1");
		return "";
	}
	
	public String setProduct(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer ID = (Integer) value;
		if(ID==null)
			return "";
		
		BigDecimal price = getProductPrice(ID, (Trx)null);
		mTab.setValue("Price", price);
		return "";
	}


	public String setAppliedQty(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer headerID = (Integer) mTab.getValue("XX_BudgetRevision_ID");
		MBudgetRevision br = new MBudgetRevision(ctx, headerID.intValue(), (Trx)null);
		if(br.isNotSummary())
		{
			BigDecimal price = (BigDecimal) mTab.getValue("Price");
			BigDecimal qty = (BigDecimal)mTab.getValue("AppliedQty");
			if(price!=null && qty !=null)
				mTab.setValue("AppliedAmt", price.multiply(qty));
		}
		return "";
	}

	public static BigDecimal getProductPrice(int M_Product_ID, Trx trx)
	{
		BigDecimal price = BigDecimal.ZERO;
		String getPrice = "SELECT COALESCE(pp.PriceStd, 0) AS Price FROM M_ProductPrice pp " +
				"INNER JOIN XX_BudgetInfo bi ON (pp.M_Pricelist_Version_ID = bi.M_Pricelist_Version_ID AND pp.AD_Client_ID = bi.AD_Client_ID) " +
				"WHERE pp.M_Product_ID = " + M_Product_ID;
		PreparedStatement pstmt = DB.prepareStatement(getPrice, (Trx)null);
		ResultSet rs = null;
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				price = rs.getBigDecimal(1);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return price;
	}

}
