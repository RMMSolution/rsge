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
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.X_GL_BudgetControl;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_PurchaseRequisition;
import rsge.tools.BudgetCalculation;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author Fanny
 *
 */
public class MCashLine extends org.compiere.model.MCashLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MCashLine.class);

	/** Set Amount Only.
    @param IsCheckAmtOnly This budget check will not check budget quantity */
    public void setIsCheckAmtOnly (boolean IsCheckAmtOnly)
    {
        set_Value ("IsCheckAmtOnly", Boolean.valueOf(IsCheckAmtOnly));
        
    }
    
    /** Get Amount Only.
    @return This budget check will not check budget quantity */
    public boolean isCheckAmtOnly() 
    {
        return get_ValueAsBoolean("IsCheckAmtOnly");
        
    }
    
    /** Set Over Budget.
    @param IsOverBudget This record is over budget */
    public void setIsOverBudget (boolean IsOverBudget)
    {
        set_Value ("IsOverBudget", Boolean.valueOf(IsOverBudget));
        
    }
    
    /** Get Over Budget.
    @return This record is over budget */
    public boolean isOverBudget() 
    {
        return get_ValueAsBoolean("IsOverBudget");
        
    }
    
    /** Set Quantity Over Budget.
    @param IsQtyOverBudget Quantity over budget */
    public void setIsQtyOverBudget (boolean IsQtyOverBudget)
    {
        set_Value ("IsQtyOverBudget", Boolean.valueOf(IsQtyOverBudget));
        
    }
    
    /** Get Quantity Over Budget.
    @return Quantity over budget */
    public boolean isQtyOverBudget() 
    {
        return get_ValueAsBoolean("IsQtyOverBudget");
        
    }
    
    /** Set Recheck Budget.
    @param IsRecheckBudget Recheck budget */
    public void setIsRecheckBudget (boolean IsRecheckBudget)
    {
        set_Value ("IsRecheckBudget", Boolean.valueOf(IsRecheckBudget));
        
    }
    
    /** Get Recheck Budget.
    @return Recheck budget */
    public boolean isRecheckBudget() 
    {
        return get_ValueAsBoolean("IsRecheckBudget");
        
    }
        
    /** Set Over Budget Amount.
    @param OverBudgetAmt Amount exceed budget allocated */
    public void setOverBudgetAmt (java.math.BigDecimal OverBudgetAmt)
    {
        set_Value ("OverBudgetAmt", OverBudgetAmt);
        
    }
    
    /** Get Over Budget Amount.
    @return Amount exceed budget allocated */
    public java.math.BigDecimal getOverBudgetAmt() 
    {
        return get_ValueAsBigDecimal("OverBudgetAmt");
        
    }
    
    /** Set Over Budget Quantity.
    @param OverBudgetQty Quantity exceed budget allocated */
    public void setOverBudgetQty (java.math.BigDecimal OverBudgetQty)
    {
        set_Value ("OverBudgetQty", OverBudgetQty);
        
    }
    
    /** Get Over Budget Quantity.
    @return Quantity exceed budget allocated */
    public java.math.BigDecimal getOverBudgetQty() 
    {
        return get_ValueAsBigDecimal("OverBudgetQty");
        
    }
    
    /** Set Remaining Budget.
    @param RemainingBudget Remaining budget amount for selected account */
    public void setRemainingBudget (java.math.BigDecimal RemainingBudget)
    {
        set_Value ("RemainingBudget", RemainingBudget);
        
    }
    
    /** Get Remaining Budget.
    @return Remaining budget amount for selected account */
    public java.math.BigDecimal getRemainingBudget() 
    {
        return get_ValueAsBigDecimal("RemainingBudget");
        
    }
    
    /** Set Remaining Qty.
    @param RemainingBudgetQty Remaning Quantity */
    public void setRemainingBudgetQty (java.math.BigDecimal RemainingBudgetQty)
    {
        set_Value ("RemainingBudgetQty", RemainingBudgetQty);
        
    }
    
    /** Get Remaining Qty.
    @return Remaning Quantity */
    public java.math.BigDecimal getRemainingBudgetQty() 
    {
        return get_ValueAsBigDecimal("RemainingBudgetQty");
        
    }
    
    public void setIsCalculateBudget (boolean IsCalculateBudget)
    {
        set_Value ("IsCalculateBudget", Boolean.valueOf(IsCalculateBudget));
        
    }
    
    public boolean isCalculateBudget() 
    {
        return get_ValueAsBoolean("IsCalculateBudget");
        
    }
    
	/**
	 * Set Receipt.
	 * 
	 * @param IsReceipt
	 *            This is a sales transaction (receipt)
	 */
	public void setIsReceipt(boolean IsReceipt) {
		set_Value("IsReceipt", Boolean.valueOf(IsReceipt));

	}

	/**
	 * Get Receipt.
	 * 
	 * @return This is a sales transaction (receipt)
	 */
	public boolean isReceipt() {
		return get_ValueAsBoolean("IsReceipt");

	}
    
    /** Set Converted Amount.
    @param ConvertedAmt Converted Amount */
    public void setConvertedAmt (java.math.BigDecimal ConvertedAmt)
    {
        set_Value ("ConvertedAmt", ConvertedAmt);
        
    }
    
    /** Get Converted Amount.
    @return Converted Amount */
    public java.math.BigDecimal getConvertedAmt() 
    {
        return get_ValueAsBigDecimal("ConvertedAmt");
        
    }
    
    /** Set Target Organization.
    @param TargetOrg_ID Target organization for this transaction */
    public void setTargetOrg_ID (int TargetOrg_ID)
    {
        if (TargetOrg_ID <= 0) set_Value ("TargetOrg_ID", null);
        else
        set_Value ("TargetOrg_ID", Integer.valueOf(TargetOrg_ID));
        
    }
    
    /** Get Target Organization.
    @return Target organization for this transaction */
    public int getTargetOrg_ID() 
    {
        return get_ValueAsInt("TargetOrg_ID");
        
    }


	/**
	 * @param ctx
	 * @param C_CashLine_ID
	 * @param trx
	 */
	public MCashLine(Ctx ctx, int C_CashLine_ID, Trx trx) {
		super(ctx, C_CashLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MCashLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
    /** Set Cash Amount.
    @param CashAmount Amount in cash */
    public void setCashAmount (java.math.BigDecimal CashAmount)
    {
        if (CashAmount == null) throw new IllegalArgumentException ("CashAmount is mandatory.");
        set_Value ("CashAmount", CashAmount);
        
    }
    
    /** Get Cash Amount.
    @return Amount in cash */
    public java.math.BigDecimal getCashAmount() 
    {
        return get_ValueAsBigDecimal("CashAmount");
        
    }
    
    
    /** Get Payment Cash Type.
    @return Cash type of payment */
    public String getPaymentCashType() 
    {
        return (String)get_Value("PaymentCashType");
        
    }
    
    /** Get Receipt Cash Type.
    @return Cash type of receipt */
    public String getReceiptCashType() 
    {
        return (String)get_Value("ReceiptCashType");
        
    }

	@UICallout public void setPaymentCashType(String oldPaymentCashType, 
			String newPaymentCashType, int windowNo) throws Exception
	{		
		if((newPaymentCashType == null) || (newPaymentCashType.length() == 0))
			return;	
		setCashType(getPaymentCashType());
	}

	@UICallout public void setReceiptCashType(String oldReceiptCashType, 
			String newReceiptCashType, int windowNo) throws Exception
	{		
		if((newReceiptCashType == null) || (newReceiptCashType.length() == 0))
			return;
		setCashType(getReceiptCashType());
	}
	
	public static boolean checkBudgetInfoExisting(int AD_Client_ID, Trx trx)
	{
		boolean isExists=false;
		String sql = "SELECT 1 FROM XX_BudgetInfo WHERE AD_Client_ID = " + AD_Client_ID;
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		try{
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				isExists = true;
			pstmt.close();
			rs.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}			
		return isExists;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		MCash cash = new MCash(getCtx(), getC_Cash_ID(), get_Trx());
		
		// Set IsReceipt
		if(getCashType().equals(CASHTYPE_Difference) || getCashType().equals(CASHTYPE_Expense))
			setIsReceipt(false);
		else if(getCashType().equals(CASHTYPE_Receipt))
			setIsReceipt(true);
		else if(getCashType().equals(CASHTYPE_Invoice) && getC_Invoice_ID()!=0)
		{
			MInvoice inv = new MInvoice(getCtx(), getC_Invoice_ID(), get_Trx());
			if(inv.isSOTrx())
			{
				if(!inv.isReturnTrx())
					setIsReceipt(true);
				else
					setIsReceipt(false);
			}
			else
			{
				if(!inv.isReturnTrx())
					setIsReceipt(false);
				else
					setIsReceipt(true);
			}
		}
		
		// Set Amount
		if(getCashAmount()!=null)
		{
			if(isReceipt())
				setAmount(getCashAmount());
			else
				setAmount(getCashAmount().negate());
		}
		setConvertedAmt(calculateConvertedAmt(getCashAmount()));		
		
		// Check budget if cash type is charge
		if(getCashType().equals(CASHTYPE_Charge) && !isReceipt())
		{
			// Check Budget
			MBudgetInfo bi = MBudgetInfo.get(getCtx(), getAD_Client_ID(), get_Trx());
			// If no info found means no budget check
			if(bi!=null)
			{
				if(bi.getStartDate()==null)
					return true;
				if(cash.getDateAcct().before(bi.getStartDate()))
					return true;
				if(getCashType().equals(CASHTYPE_Charge) && getC_Charge_ID()==0)
					return true;

				int accountID = 0;					
				MAcctSchema as = new MAcctSchema(getCtx(), bi.getC_AcctSchema_ID(), get_Trx());
				MChargeAcct ca = MChargeAcct.get(getC_Charge_ID(), as);
				MAccount account = MAccount.get(getCtx(), ca.getCh_Expense_Acct());
				accountID = account.getAccount_ID();
				
				BudgetCalculation bc = null;
				if(cash.getDocStatus().equals(X_XX_PurchaseRequisition.DOCSTATUS_Drafted)
						|| cash.getDocStatus().equals(X_XX_PurchaseRequisition.DOCSTATUS_NotApproved))
				{
					bc = checkLineBudget(accountID, 0);
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
						System.out.println("Converted Amt " + getConvertedAmt());
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

		}
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		checkDocumentBudget();
		return super.afterSave(newRecord, success);
	}
	
	private void checkDocumentBudget()
	{
		BigDecimal overBudgetAmt = BigDecimal.ZERO;
		String sql = "SELECT COALESCE(SUM(OverBudgetAmt),0) FROM C_CashLine "
				+ "WHERE IsOverBudget = 'Y' "
				+ "AND C_Cash_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getC_Cash_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				overBudgetAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(overBudgetAmt.signum()!=0)
		{
			String update = "UPDATE C_Cash "
					+ "SET IsLineOverBudget = 'Y', OverBudgetAmt = ? WHERE C_Cash_ID = ? ";
			DB.executeUpdate(get_Trx(), update, overBudgetAmt, getC_Cash_ID());
		}
		else
		{
			String update = "UPDATE C_Cash "
					+ "SET IsLineOverBudget = 'N', OverBudgetAmt = 0 WHERE C_Cash_ID = ? ";
			DB.executeUpdate(get_Trx(), update, getC_Cash_ID());
		}
		
	}
	
	public BudgetCalculation checkLineBudget(int accountID, int productID)
	{
		MCash cash = new MCash(getCtx(), getC_Cash_ID(), get_Trx());
		int orgID = 0;
		if(getTargetOrg_ID()!=0)
			orgID = MClientOrg.get(getTargetOrg_ID(), get_Trx());
		else
			orgID = getAD_Org_ID();
		System.out.println(new MOrg(getCtx(), orgID, get_Trx()).getName());		
		BudgetCalculation bc = new BudgetCalculation(getCtx(), orgID, cash.getDateAcct(), cash.getDateAcct(), null, null, false, accountID, 
				getC_Activity_ID(), cash.getC_Campaign_ID(), cash.getC_Project_ID(), 0, productID, 0, 0, 0, false, 
				get_Trx());
		System.out.println("BC " + bc);
		return bc;
	}

	/**
	 * Calculate Converted Line Amount
	 * @param lineNetAmount
	 * @return
	 */
	private BigDecimal calculateConvertedAmt(BigDecimal lineNetAmount)
	{
		// Get Requisition Currency
		MCash cash = new MCash(getCtx(), getC_Cash_ID(), get_Trx());
		
		// Get Budget Info Currency
		MBudgetInfo info = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), get_Trx());
		X_GL_BudgetControl bc = new X_GL_BudgetControl(getCtx(), info.getGL_BudgetControl_ID(), get_Trx());
		MAcctSchema schema = new MAcctSchema(getCtx(), bc.getC_AcctSchema_ID(), get_Trx());
		
		if(schema.getC_Currency_ID()!= cash.getC_Currency_ID())
		{
			BigDecimal convertedAmt = MConversionRate.convert(getCtx(), lineNetAmount, cash.getC_Currency_ID(), schema.getC_Currency_ID(), 
					cash.getDateAcct(), info.getC_ConversionType_ID(), getAD_Client_ID(), getAD_Org_ID());
			
			MCurrency currency = new MCurrency(getCtx(), schema.getC_Currency_ID(), get_Trx());
			convertedAmt = convertedAmt.setScale(currency.getStdPrecision(), RoundingMode.HALF_UP);
			
			return convertedAmt;
		}		
		else return lineNetAmount; 			
	}	

	public static BigDecimal getPendingAmt(int AD_Org_ID, int accountID, Timestamp startDate, Timestamp endDate, int C_Activity_ID, int C_Campaign_ID, int C_Project_ID, Trx trx)
	{
		BigDecimal pendingAmt = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(cl.ConvertedAmt),0) AS ConvertedAmt FROM C_CashLine cl "
				+ "INNER JOIN C_Cash c ON cl.C_Cash_ID = c.C_Cash_ID "
				+ "INNER JOIN C_Charge_Acct ca ON cl.C_Charge_ID = ca.C_Charge_ID "
				+ "INNER JOIN C_ValidCombination vc ON ca.CH_Expense_Acct = vc.C_ValidCombination_ID "
				+ "INNER JOIN XX_ClientOrg co ON cl.TargetOrg_ID = co.XX_ClientOrg_ID "
				+ "WHERE c.DocStatus IN ('IP', 'AP') "
				+ "AND cl.CashType = 'C' "
				+ "AND c.IsReceipt = 'N' ");
		if(AD_Org_ID!=0)
			sql.append("AND co.Org_ID = ? ");
		sql.append("AND vc.Account_ID = ? "
				+ "AND c.DateAcct BETWEEN ? AND ? ");
		if(C_Activity_ID!=0)
			sql.append("AND c.C_Activity_ID = ? ");
		else
			sql.append("AND (c.C_Activity_ID = 0 OR c.C_Activity_ID IS NULL) ");
		if(C_Campaign_ID!=0)
			sql.append("AND c.C_Campaign_ID = ? ");
		else
			sql.append("AND (c.C_Campaign_ID = 0 OR c.C_Campaign_ID IS NULL) ");
		if(C_Project_ID!=0)
			sql.append("AND c.C_Project_ID = ? ");
		else
			sql.append("AND (c.C_Project_ID = 0 OR c.C_Project_ID IS NULL) ");

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


}
