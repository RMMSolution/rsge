/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.model.MJournal;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.utils.BudgetUtils;
import rsge.utils.CheckBudget;
import rsge.utils.GeneralEnhancementUtils;


/**
 * @author bang
 *
 */
public class MJournalLine extends org.compiere.model.MJournalLine {

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
	 * @param ctx
	 * @param C_CashLine_ID
	 * @param trx
	 */
	public MJournalLine(Ctx ctx, int C_CashLine_ID, Trx trx) {
		super(ctx, C_CashLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MJournalLine(Ctx ctx, ResultSet rs, Trx trx) {
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
	
	private BigDecimal[] checkRemainingBudget()
	{
		BigDecimal[] remainsBudget = new BigDecimal[2];
		MJournal r = new MJournal(getCtx(), getGL_Journal_ID(), get_Trx());
		// Get Accounting Schema from Budget basic info
		MBudgetInfo info = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), get_Trx());
		
		int Account_ID = accountID();
			if(getC_ValidCombination_ID()> 0)
			{
				String sql = "SELECT vc.Account_ID " +
					"FROM C_ValidCombination vc " +
					"WHERE vc.C_ValidCombination_ID = ?  ";
				
				PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
				ResultSet rs = null;
				
				try{
					pstmt.setInt(1, getC_ValidCombination_ID());
					rs = pstmt.executeQuery();			
					if(rs.next())				{
						Account_ID = rs.getInt(1);
					}				
					rs.close();
					pstmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}			
			}
			else
			{
				String sql = "SELECT vc.Account_ID " +
				"FROM C_AcctSchema_Default asd " +
				"INNER JOIN C_ValidCombination vc ON (asd.P_Expense_Acct = vc.C_ValidCombination_ID) " +
				"WHERE asd.C_AcctSchema_ID = ? ";
			
			PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
			ResultSet rs = null;
			
				try{
					pstmt.setInt(1, BudgetUtils.getBudgetAcctSchemaID(getCtx(), getAD_Client_ID(), get_Trx()));
				
					rs = pstmt.executeQuery();
				
					if(rs.next())
					{
						Account_ID = rs.getInt(1);
					}
				
					rs.close();
					pstmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}		
			}			
		
		int element1Value = 0;
		String element1 = null;
		element1 = GeneralEnhancementUtils.getElementColumn(info.getC_AcctSchema_ID(), 1, get_Trx());
		
		if((element1 != null) && (GeneralEnhancementUtils.checkColumn("GL_JournalLine", element1, get_Trx())))
		{
			element1Value = GeneralEnhancementUtils.getElementValue(element1, "GL_JournalLine", getGL_JournalLine_ID(), get_Trx());
		}
		else if((element1 != null) && (GeneralEnhancementUtils.checkColumn("GL_Journal", element1, get_Trx())))
		{
			element1Value = GeneralEnhancementUtils.getElementValue(element1, "GL_Journal", getGL_Journal_ID(), get_Trx());
		}
		
		int element2Value = 0;
		String element2 = null;
		element2 = GeneralEnhancementUtils.getElementColumn(info.getC_AcctSchema_ID(), 2, get_Trx());
		
		if((element2 != null) && (GeneralEnhancementUtils.checkColumn("GL_JournalLine", element2, get_Trx())))
		{
			element2Value = GeneralEnhancementUtils.getElementValue(element2, "GL_JournalLine", getGL_JournalLine_ID(), get_Trx());
		}
		else if((element2 != null) && (GeneralEnhancementUtils.checkColumn("GL_Journal", element2, get_Trx())))
		{
			element2Value = GeneralEnhancementUtils.getElementValue(element2, "GL_Journal", getGL_Journal_ID(), get_Trx());
		}
		
		CheckBudget check = new CheckBudget(getCtx(), false, getAD_Org_ID(), Account_ID, r.getDateAcct(),false, info.getBudgetCalendar_ID(),
				0, 0, 0, 0, 0, element1Value, element2Value, get_Trx());
		check.runBudgetCheck();
		
		BigDecimal remainingBudget = check.getRemainingBudget();
		BigDecimal remainingBudgetQty = check.getRemainingBudgetQty();
		remainsBudget[0] = remainingBudget;
		remainsBudget[1] = remainingBudgetQty;

		return remainsBudget;	
	}
	
	private int accountID(){
		String sql = "SELECT Account_ID FROM C_ValidCombination WHERE AD_Client_ID = "+getAD_Client_ID()+" " +
				"AND C_ValidCombination_ID = ?";
		PreparedStatement ps = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try {
			ps.setInt(1, getC_ValidCombination_ID());
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	private BigDecimal getLinesAmount()
	{
		BigDecimal linesAmt = BigDecimal.ZERO;
		
		int C_ValidCombination_ID = 0;
		
		if(getC_ValidCombination_ID() > 0)
		{
			C_ValidCombination_ID = getC_ValidCombination_ID();			
		}

		String sql = "SELECT COALESCE(SUM(AmtAcctCr), 0) " +
				"FROM GL_JournalLine rl " +
				"WHERE (C_ValidCombination_ID = ?) " +
				"AND GL_JournalLine_ID NOT IN (?) " +
				"AND GL_Journal_ID IN (?) AND AmtAcctCr>0 " +
				"UNION " +
				"SELECT COALESCE(SUM(AmtAcctDr), 0) " +
				"FROM GL_JournalLine rl " +
				"WHERE (C_ValidCombination_ID = ?) " +
				"AND GL_JournalLine_ID NOT IN (?) " +
				"AND GL_Journal_ID IN (?) AND AmtAcctDr>0";
				
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, C_ValidCombination_ID);
			pstmt.setInt(2, getGL_JournalLine_ID());
			pstmt.setInt(3, getGL_Journal_ID());
			
			pstmt.setInt(1, C_ValidCombination_ID);
			pstmt.setInt(2, getGL_JournalLine_ID());
			pstmt.setInt(3, getGL_Journal_ID());
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				linesAmt = rs.getBigDecimal(1);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return linesAmt;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
//		if(super.beforeSave(newRecord))
//		{
			
			if(is_ValueChanged("AmtAcctCr") && !isCalculateBudget() || is_ValueChanged("AmtAcctDr") && !isCalculateBudget()){
				setIsCalculateBudget(true);
			}

			if(isCalculateBudget()){
				// Check Budget			
				if(checkBudgetInfoExisting(getAD_Client_ID(), get_Trx()))
				{
					BigDecimal[] remainsBudget = checkRemainingBudget();
					
					BigDecimal remainingBudget = remainsBudget[0];
					BigDecimal remainingBudgetQty = remainsBudget[1];
					
					System.out.println("Remaining Budget Qty " + remainingBudgetQty);
					
					if(remainingBudget!=null) // if account has budget
					{
						remainingBudget = remainingBudget.subtract(getLinesAmount());
						
						setRemainingBudget(remainingBudget);
						setRemainingBudgetQty(remainingBudgetQty);
						
						BigDecimal overBudgetAmt = Env.ZERO;
						BigDecimal Amount = BigDecimal.ZERO;
						if(getAmtAcctCr().compareTo(BigDecimal.ZERO)>0){
							Amount = getAmtAcctCr();
						}
						
						if(getAmtAcctDr().compareTo(BigDecimal.ZERO)>0){
							Amount = getAmtAcctDr();
						}
						
						if(Amount.compareTo(remainingBudget) == 1)
						{
							overBudgetAmt = Amount.subtract(remainingBudget);
							setOverBudgetAmt(overBudgetAmt);
							setIsOverBudget(true);
						}
						else
						{
							setOverBudgetAmt(overBudgetAmt);
							setIsOverBudget(false);
						}							
						if(remainingBudgetQty!=null)
						{
							BigDecimal overBudgetQty = Env.ZERO;
							if(Env.ONE.compareTo(remainingBudgetQty)>0)
							{
								overBudgetQty = Env.ONE.subtract(remainingBudgetQty);
								setOverBudgetQty(overBudgetQty);
								setIsQtyOverBudget(true);
							}
							else
							{
								setOverBudgetQty(overBudgetQty);
								setIsQtyOverBudget(false);
							}
							log.log(Level.ALL, "Over Budget Qty " + overBudgetQty);
							
						}
						else
						{
							setOverBudgetQty(null);
							setIsQtyOverBudget(false);
						}
						
						log.log(Level.ALL, "Over Budget Amount " + overBudgetAmt);
						log.log(Level.ALL, "Amount " + Amount);
					}
					else // Account has no budget
					{
						setRemainingBudget(null);
						setRemainingBudgetQty(null);
						setOverBudgetAmt(null);
						setIsOverBudget(false);					
						setIsQtyOverBudget(false);
						setOverBudgetQty(null);
					}
				}
				// End Check Budget
				setIsCalculateBudget(false);
			}
//		}
		return true;
	}

}
