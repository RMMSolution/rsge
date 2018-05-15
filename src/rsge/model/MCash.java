/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import rsge.model.MCashBook;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

/**
 * @author Fanny
 * 
 */
public class MCash extends org.compiere.model.MCash {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param C_Cash_ID
	 * @param trx
	 */
	public MCash(Ctx ctx, int C_Cash_ID, Trx trx) {
		super(ctx, C_Cash_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MCash(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cb
	 * @param today
	 */
	public MCash(MCashBook cb, Timestamp today) {
		super(cb, today);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		if (newRecord) {
			MCashBook cb = new MCashBook(getCtx(), getC_CashBook_ID(),
					get_Trx());
			setBeginningBalance(cb.getCashBookSetAmt());
		}
		return super.beforeSave(newRecord);
	}

	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
		if (super.completeIt().equals(DocActionConstants.STATUS_Completed)) {
			// Update Cash Book Balance
			MCashBook cb = new MCashBook(getCtx(), getC_CashBook_ID(),
					get_Trx());
			cb.setCashBookSetAmt(getEndingBalance());
			cb.save();
		}
		
    	// Check related Advance Disbursement
    	MAdvanceDisbursement ad = MAdvanceDisbursement.getByCashJournal(this);
    	if(ad!=null)
    	{
    		ad.setIsPaid(true);
    		ad.save();
    	}
    	
    	// If it's charge for other organization, generate expense transfer
    	boolean createExpenseTransfer = false;
    	BigDecimal transferAmt = BigDecimal.ZERO;
    	String sql = "SELECT AD_Org_ID, TargetOrg_ID, CashAmount, C_CashLine_ID FROM C_CashLine "
    			+ "WHERE TargetOrg_ID IS NOT NULL "
    			+ "AND C_Cash_ID = ? ";
    	PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
    	ResultSet rs = null;
    	try{
    		pstmt.setInt(1, getC_Cash_ID());
    		rs = pstmt.executeQuery();
    		while(rs.next())
    		{
    			if(!createExpenseTransfer)
    			{
    				if(rs.getInt(1)!=MClientOrg.get(rs.getInt(2), get_Trx()))
    					createExpenseTransfer = true;    		
    			}    			
    			transferAmt = transferAmt.add(rs.getBigDecimal(3));	
    			// Update Budget Transaction
    			MCashLine cl = new MCashLine(getCtx(), rs.getInt(4), get_Trx());
    			
    			// Check Budget
    			MBudgetInfo bi = MBudgetInfo.get(getCtx(), getAD_Client_ID(), get_Trx());
    			if(bi!=null)
    			{
    				MAcctSchema as = new MAcctSchema(getCtx(), bi.getC_AcctSchema_ID(), get_Trx());
    				MChargeAcct ca = MChargeAcct.get(cl.getC_Charge_ID(), as);
    				MAccount account = MAccount.get(getCtx(), ca.getCh_Expense_Acct());
        			MBudgetTransactionLine budgetLine = MBudgetTransactionLine.createLine(getCtx(), cl.get_Table_ID(), cl.getC_CashLine_ID(), 0, 0, MClientOrg.get(rs.getInt(2), get_Trx()), getDateAcct(), account.getAccount_ID(), 0, getC_Activity_ID(), 0, getC_Campaign_ID(), 0, 0, getC_Project_ID(), 0, 0, 0, 0, 0, 0, 0, 0, 0, get_Trx());    	
        			budgetLine.setRealizedAmt(cl.getCashAmount());
        			budgetLine.save();
    			}

    		}
    		rs.close();
    		pstmt.close();
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
    	if(createExpenseTransfer)
    	{
    		MExpenseTransfer et = new MExpenseTransfer(getCtx(), 0, get_Trx());
    		et.setClientOrg(this);
    		et.setC_Cash_ID(getC_Cash_ID());
    		et.setC_Currency_ID(new MCashBook(getCtx(), getC_CashBook_ID(), get_Trx()).getC_Currency_ID());
    		et.setAmount(transferAmt);
    		et.setDateAcct(getDateAcct());
    		et.setDateDoc(getDateAcct());
    		et.setProcessing(true);
    		et.setProcessed(true);
    		et.save();
    	}

		return DocActionConstants.STATUS_Completed;
	}
	
    @Override
    public boolean voidIt() {
    	// TODO Auto-generated method stub
    	if(super.voidIt())
    	{	
    		// Check related Advance Disbursement
        	MAdvanceDisbursement ad = MAdvanceDisbursement.getByCashJournal(this);
        	if(ad!=null)
        	{
        		ad.setIsPaid(false);
        		ad.setC_Payment_ID(0);
        		ad.save();
        	}
    	}	
    	return true;
    }
    
    @Override
    public boolean reverseCorrectIt() {
    	// TODO Auto-generated method stub
    	if(super.reverseCorrectIt())
    	{
    		// Check related Advance Disbursement
        	MAdvanceDisbursement ad = MAdvanceDisbursement.getByCashJournal(this);
        	if(ad!=null)
        	{
        		ad.setIsPaid(false);
        		ad.setC_Payment_ID(0);
        		ad.save();
        	}
    	}
    	
    	return true;
    }
    
    /** Set Line Over Budget.
    @param IsLineOverBudget One of record''s line is over budget */
    public void setIsLineOverBudget (boolean IsLineOverBudget)
    {
        set_Value ("IsLineOverBudget", Boolean.valueOf(IsLineOverBudget));
        
    }
    
    /** Get Line Over Budget.
    @return One of record''s line is over budget */
    public boolean isLineOverBudget() 
    {
        return get_ValueAsBoolean("IsLineOverBudget");
        
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


}
