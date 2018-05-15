/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.api.UICallout;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCurrency;
import org.compiere.model.MElementValue;
import org.compiere.model.MPeriod;
import org.compiere.model.X_C_ElementValue;
import org.compiere.util.CThreadUtil;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_BudgetRevision;
import rsge.po.X_XX_BudgetRevisionAccount;
import rsge.po.X_XX_PurchaseRequisition;
import rsge.tools.BudgetCalculation;
import rsge.utils.BudgetUtils;
import rsge.utils.CheckBudget;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY R
 *
 */
public class MBudgetRevisionAccount extends X_XX_BudgetRevisionAccount {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_BudgetRevisionAccount_ID
	 * @param trx
	 */
	public MBudgetRevisionAccount(Ctx ctx, int XX_BudgetRevisionAccount_ID,
			Trx trx) {
		super(ctx, XX_BudgetRevisionAccount_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MBudgetRevisionAccount(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}	
	
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		MBudgetRevision br = new MBudgetRevision(getCtx(), getXX_BudgetRevision_ID(), get_Trx());		
		// Set Budget Account Type
		if(getBudgetType(getAccount_ID()) == 1)
		{
			setIsInvestmentAccount(true);
		}
		
		// Get Accounting Schema from Budget basic info
		MBudgetInfo info = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), get_Trx());
		
		Timestamp checkDate = getCheckDate();
		// Set Reference
		setC_Activity_ID(br.getC_Activity_ID());
		setC_BPartner_ID(br.getC_BPartner_ID());
		setC_Campaign_ID(br.getC_Campaign_ID());
		setM_Product_ID(br.getM_Product_ID());
		setM_Product_ID(br.getM_Product_ID());
		setC_Project_ID(br.getC_Project_ID());
		setC_SalesRegion_ID(br.getC_SalesRegion_ID());
		
		// Get Remaining Budget Amount		
		boolean checkAsStartDate = false;
		boolean currentPeriodOnly = false;
		if(br.getBudgetRevisionType().equals(X_XX_BudgetRevision.BUDGETREVISIONTYPE_Transfer) && br.isPeriod())
		{	
			currentPeriodOnly = true;
			checkAsStartDate = true;
		}
		
		if(br.getDateEffective().before(info.getStartDate()))
			return true;
		BudgetCalculation bc = null;
		if(br.getDocStatus().equals(X_XX_PurchaseRequisition.DOCSTATUS_Drafted)
				|| br.getDocStatus().equals(X_XX_PurchaseRequisition.DOCSTATUS_NotApproved))
		{
			bc = checkLineBudget(this, currentPeriodOnly, checkDate);
			if(bc.getBudgetAmt()==null) // No Budget defined
				setRemainingBudget(BigDecimal.ZERO);
			else
				setRemainingBudget(bc.getRemainsAmt());
			// End Check Budget			
		}


		// Set Percentage to percentage allowed if Percentage is bigger than percentage allowed by Source Reserve - for Budget Transfer only
		if(br.getBudgetRevisionType().equals(X_XX_BudgetRevision.BUDGETREVISIONTYPE_Transfer))
		{
			BigDecimal percentageAllowed = BigDecimal.valueOf(100).subtract(info.getSourceReserve());
			if(getPercentage().compareTo(percentageAllowed)==1 || getAppliedAmt().compareTo(getRemainingBudget())==1)
			{
				setPercentage(percentageAllowed);
				recalculateLine(info.getC_AcctSchema_ID());
			}
		}

		// Set Applied Amount if applied amount is bigger than remaining budget - Budget Cut Only
		if(br.getBudgetRevisionType().equals(X_XX_BudgetRevision.BUDGETREVISIONTYPE_Cut))
		{
			if(getAppliedAmt().compareTo(getRemainingBudget())>=1)
			{
				setAppliedAmt(getRemainingBudget());
				setPercentage(BigDecimal.valueOf(100));
				setRevisedAmt(BigDecimal.ZERO);
			}
			if(isProduct() && getAppliedQty().compareTo(getRemainingBudgetQty())>=1)
			{
				setAppliedQty(getRemainingBudgetQty());				
				setRevisedQty(BigDecimal.ZERO);
			}
		}			
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {		
		
		MBudgetRevision br = new MBudgetRevision(getCtx(), getXX_BudgetRevision_ID(), get_Trx());

		// Create or update Budget Revision Target if Same Account is true		
		if(br.isSameAccount())
		{
			int ID = 0;
			if(!newRecord)
			{
				// Check 
				StringBuilder sql = new StringBuilder("SELECT XX_BudgetRevisionTargetAcct_ID " +
						"FROM XX_BudgetRevisionTargetAcct " +
						"WHERE XX_BudgetRevisionAccount_ID = ? ");
				
				PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
				ResultSet rs = null;
				
				try{
					pstmt.setInt(1, getXX_BudgetRevisionAccount_ID());
					rs = pstmt.executeQuery();
					if(rs.next())
					{
						ID = rs.getInt(1);
					}
					rs.close();
					pstmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			MBudgetRevisionTargetAcct target = new MBudgetRevisionTargetAcct(getCtx(), ID, get_Trx());
			
			if(ID == 0)
			{
				target.setXX_BudgetRevisionAccount_ID(getXX_BudgetRevisionAccount_ID());				
			}
			
			target.setXX_BudgetRevision_ID(getXX_BudgetRevision_ID());			
			target.setAccount_ID(getAccount_ID());
			target.setAppliedAmt(getAppliedAmt());
			target.save();
		}
		
		updateHeader();
		// Re-check Header Lock
		MBudgetRevision.checkHeaderLock(getCtx(), getXX_BudgetRevision_ID(), get_Trx());
		return true;
	}
	
	int XX_BudgetRevision_ID = 0;

	@Override
	protected boolean beforeDelete() {		
		XX_BudgetRevision_ID = getXX_BudgetRevisionAccount_ID();		
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		updateHeader();
		// Re-check Header Lock
		MBudgetRevision.checkHeaderLock(getCtx(), getXX_BudgetRevision_ID(), get_Trx());
		return true;
	}
	
	@UICallout public void setXX_BudgetRevision_ID(String oldXX_BudgetRevision_ID, String newXX_BudgetRevision_ID, int windowNo) throws Exception
	{		
		// Set Is Product
		MBudgetRevision br = new MBudgetRevision(getCtx(), getXX_BudgetRevision_ID(), get_Trx());
		if(br.getM_Product_ID()==0)
			return;
		
		MProduct product = new MProduct(getCtx(), br.getM_Product_ID(), get_Trx());
		if(!product.isSummary())
			setIsProduct(true);
		else
			setIsProduct(false);
		
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
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, br.getM_Product_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				MAccount account = new MAccount(getCtx(), rs.getInt(1), get_Trx());
				setAccount_ID(account.getAccount_ID());
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
			String getPrice = "SELECT COALESCE(pp.PriceStd, 0) AS Price FROM M_ProductPrice pp " +
					"INNER JOIN XX_BudgetInfo bi ON (pp.M_Pricelist_Version_ID = bi.M_Pricelist_Version_ID AND pp.AD_Client_ID = bi.AD_Client_ID) " +
					"WHERE pp.M_Product_ID = " + br.getM_Product_ID();
			pstmt = DB.prepareStatement(getPrice, get_Trx());
			rs = null;
			try{
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					setPrice(rs.getBigDecimal(1));
				}
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}

		}

	}

	/**
	 * Set Revised Amount based on Remaining Budget and applied amount
	 * @param oldAppliedAmt
	 * @param newAppliedAmt
	 * @param windowNo
	 * @throws Exception
	 */
	@UICallout public void setAppliedAmt(String oldAppliedAmt,
			String newAppliedAmt, int windowNo) throws Exception
	{		
		if((newAppliedAmt == null) || (newAppliedAmt.length() == 0))
			return;
		
		BigDecimal appliedAmt = new BigDecimal(newAppliedAmt);
		super.setAppliedAmt(appliedAmt);
		setAmt("AppliedAmt");
	}	
	
	@UICallout public void setAppliedQty(String oldAppliedQty,
			String newAppliedQty, int windowNo) throws Exception
	{
		if((newAppliedQty == null) || (newAppliedQty.length() == 0))
			return;			
		calculatePriceQty();
	}	
	
	@UICallout public void setPrice(String oldPrice,
			String newPrice, int windowNo) throws Exception
	{
		if((newPrice == null) || (newPrice.length() == 0))
			return;			
		calculatePriceQty();
	}

	private void calculatePriceQty()
	{
		BigDecimal price = getPrice();
		BigDecimal qty = getAppliedQty();
		if(price.signum()==0 && qty.signum()==0)
			return;
		
		BigDecimal appliedAmt = price.multiply(qty);
		MBudgetInfo bi = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), get_Trx());
		MAcctSchema as = new MAcctSchema(getCtx(), bi.getC_AcctSchema_ID(),get_Trx());
		int C_Currency_ID = as.getC_Currency_ID(); 
		MCurrency currency = new MCurrency(getCtx(), C_Currency_ID, get_Trx());
		
		appliedAmt.setScale(currency.getStdPrecision(), RoundingMode.FLOOR);
		setAppliedAmt(appliedAmt);
	}
	
	private void setAmt(String columnName)
	{
		if(CThreadUtil.isCalloutActive())
			return;		
		BigDecimal remainingBudget = getRemainingBudget();
		if(remainingBudget.compareTo(BigDecimal.ZERO) == 0)
			return;		
		MAcctSchema as = new MAcctSchema(getCtx(), BudgetUtils.getBudgetAcctSchemaID(getCtx(), getAD_Client_ID(), get_Trx()), get_Trx()); 
		MCurrency currency = new MCurrency(getCtx(), as.getC_Currency_ID(), get_Trx());
		
		BigDecimal appliedAmt = getAppliedAmt();
		appliedAmt.setScale(currency.getStdPrecision(), RoundingMode.FLOOR);
		
		if(columnName.equals("AppliedAmt"))
		{
			if(appliedAmt.compareTo(remainingBudget) == 1)
			{
				appliedAmt = remainingBudget;
				setAppliedAmt(appliedAmt);
			}
		}		
		
		// Update Revised Amout
		// Get Revision Type
		MBudgetRevision revision = new MBudgetRevision(getCtx(), getXX_BudgetRevision_ID(), get_Trx());
		String revisionType = revision.getBudgetRevisionType();

		// Product
		if(revisionType.equals(X_XX_BudgetRevision.BUDGETREVISIONTYPE_Increase))
		{
			setRevisedAmt(getRemainingBudget().add(appliedAmt));
			if(revision.isNotSummary())
			{
				setRevisedQty(getRemainingBudgetQty().add(getAppliedQty()));
			}
			else
				setRevisedQty(getRemainingBudgetQty());
		}
		else
		{
			setRevisedAmt(getRemainingBudget().subtract(appliedAmt));
			if(revision.isNotSummary())
			{
				setRevisedQty(getRemainingBudgetQty().subtract(getAppliedQty()));
			}
			else
				setRevisedQty(getRemainingBudgetQty());
		}
	}
	
	/**
	 * Update Total Amount in Budget Revision
	 */
	private void updateHeader()
	{		
		// Update Header
		StringBuilder sql = new StringBuilder("Update XX_BudgetRevision br " +
				"SET br.TotalAmt = (COALESCE((SELECT SUM(acct.AppliedAmt) FROM XX_BudgetRevisionAccount acct " +
				"WHERE acct.XX_BudgetRevision_ID = br.XX_BudgetRevision_ID),0)) " +
				"WHERE br.XX_BudgetRevision_ID = ? ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_BudgetRevision_ID());
			rs = pstmt.executeQuery();
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			sql = new StringBuilder("Update XX_BudgetRevision br " +
					"SET br.TotalAmt = 0 " +
					"WHERE br.XX_BudgetRevision_ID = ? ");			
			
			DB.executeUpdate(get_Trx(), sql.toString(), XX_BudgetRevision_ID);
		}
	}
	
	/**
	 * Recalculate Applied Amt, Percentage and Revision Amount
	 */
	private void recalculateLine(int C_AcctSchema_ID)
	{
		BigDecimal percentage = BigDecimal.ZERO;
		BigDecimal remainingBudget = getRemainingBudget();
		BigDecimal appliedAmtAllowed = BigDecimal.ZERO;

		percentage = getPercentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
		appliedAmtAllowed = remainingBudget.multiply(percentage);		
		
		MAcctSchema schema = new MAcctSchema(getCtx(), C_AcctSchema_ID, get_Trx());
		MCurrency currency = new MCurrency(getCtx(), schema.getC_Currency_ID(), get_Trx());
		
		appliedAmtAllowed = appliedAmtAllowed.setScale(currency.getStdPrecision(), RoundingMode.HALF_UP);
		
		setAppliedAmt(appliedAmtAllowed);
		setRevisedAmt(remainingBudget.subtract(appliedAmtAllowed));			
	}
	
	/**
	 * Return Budget Type based on account 
	 * 1 for Investment
	 * 2 for Expense
	 * 0 for Error
	 * @param Account_ID
	 * @return
	 */
	private int getBudgetType(int Account_ID)
	{
		int budgetType = 0;
		
		MElementValue ev = new MElementValue(getCtx(), Account_ID, get_Trx());
		if(ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Asset)
				|| ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Liability)
				|| ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_OwnerSEquity))
			budgetType = 1;
		else if(ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Expense)
				|| ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Revenue)
				|| ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Memo))
			budgetType = 2;		
		return budgetType;
	}
	
	private Timestamp getCheckDate()
	{
		MBudgetRevision br = new MBudgetRevision(getCtx(), getXX_BudgetRevision_ID(), get_Trx());
		
		// If Budget Revision is Period based, get first date of From Period
		if(br.isPeriod())
		{
			MPeriod period = new MPeriod(getCtx(), br.getC_Period_From_ID(), get_Trx());
			return period.getStartDate();
		}
		
		return br.getDateEffective();
	}
	
	public BudgetCalculation checkLineBudget(MBudgetRevisionAccount bra, boolean calculateCurrentPeriodOnly, Timestamp checkDate)
	{
		MBudgetRevision br = new MBudgetRevision(getCtx(), bra.getXX_BudgetRevision_ID(), get_Trx());
		Timestamp dateTrx = br.getDateEffective();
		if(br.isPeriod())
			dateTrx = checkDate;
		BudgetCalculation bc = new BudgetCalculation(getCtx(), getAD_Org_ID(), br.getDateEffective(), dateTrx, null, null, calculateCurrentPeriodOnly, bra.getAccount_ID(), 
				br.getC_Activity_ID(), br.getC_Campaign_ID(), br.getC_Project_ID(), 0, 0, 0, 0, 0, false, get_Trx());			
		return bc;
	}

}
