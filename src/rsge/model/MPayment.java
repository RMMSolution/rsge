/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import rsge.model.MBankAccount;

import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MBPBankAccount;
import org.compiere.model.MBPartner;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MConversionRate;
import org.compiere.model.X_C_AllocationHdr;
import org.compiere.model.X_C_BankStatement;
import org.compiere.model.X_I_Payment;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

/**
 * @author FANNY R
 *
 */
public class MPayment extends org.compiere.model.MPayment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param C_Payment_ID
	 * @param trx
	 */
	public MPayment(Ctx ctx, int C_Payment_ID, Trx trx) {
		super(ctx, C_Payment_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MPayment(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param imp
	 */
	public MPayment(X_I_Payment imp) {
		super(imp);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		if(newRecord)
		{
			if(!isReceipt() && getC_BPartner_ID()!=0 && (getTenderType().equals(TENDERTYPE_DirectDebit) || getTenderType().equals(TENDERTYPE_DirectDeposit)))
			{
				MBPBankAccount[] accounts = MBPBankAccount.getOfBPartner(getCtx(), getC_BPartner_ID());
				MBPartner bp = new MBPartner(getCtx(), getC_BPartner_ID(), get_Trx());
				for(MBPBankAccount account : accounts)
				{
					MBankAccount ba = new MBankAccount(getCtx(), account.getC_BP_BankAccount_ID(), get_Trx());
					if(ba.getC_Currency_ID()==getC_Currency_ID())
					{
						setAccountNo(ba.getAccountNo());
						setA_Name(bp.getName());
						break;
					}
				}
			}
		}
		if(super.beforeSave(newRecord))
		{
			// Set Bank Currency and Rate
			MBankAccount ba = new MBankAccount(getCtx(), getC_BankAccount_ID(), get_Trx());
			setC_Currency_Bank_ID(ba.getC_Currency_ID());
			setRate(MConversionRate.getRate(getC_Currency_ID(), getC_Currency_Bank_ID(), getDateTrx(), getC_ConversionType_ID(), getAD_Client_ID(), getAD_Org_ID()));				
			
		}		
		return true;
	}
	
	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		resetAdvanceDisbursement();
		return super.beforeDelete();
	}		
	
	private boolean resetAdvanceDisbursement()
	{
		// Check related Advance Disbursement
    	MAdvanceDisbursement ad = MAdvanceDisbursement.getByPayment(this);
    	if(ad!=null)
    	{
    		ad.setIsPaid(false);
    		ad.setC_Payment_ID(0);
    		ad.save();
    	}
		return true;
	}
	
    /** Set Currency.
    @param C_Currency_Bank_ID Bank Account Currency */
    public void setC_Currency_Bank_ID (int C_Currency_Bank_ID)
    {
        if (C_Currency_Bank_ID <= 0) set_Value ("C_Currency_Bank_ID", null);
        else
        set_Value ("C_Currency_Bank_ID", Integer.valueOf(C_Currency_Bank_ID));
        
    }
    
    /** Get Currency.
    @return Bank Account Currency */
    public int getC_Currency_Bank_ID() 
    {
        return get_ValueAsInt("C_Currency_Bank_ID");
        
    }
    
    /** Set Enter New Rate.
    @param EnterNewRate Enter new rate for conversion type */
    public void setEnterNewRate (String EnterNewRate)
    {
        set_Value ("EnterNewRate", EnterNewRate);
        
    }
    
    /** Get Enter New Rate.
    @return Enter new rate for conversion type */
    public String getEnterNewRate() 
    {
        return (String)get_Value("EnterNewRate");
        
    }
    
    /** Set Rate.
    @param Rate Rate or Tax or Exchange */
    public void setRate (java.math.BigDecimal Rate)
    {
        set_Value ("Rate", Rate);
        
    }
    
    /** Get Rate.
    @return Rate or Tax or Exchange */
    public java.math.BigDecimal getRate() 
    {
        return get_ValueAsBigDecimal("Rate");
        
    }
    
    @Override
    public String completeIt() {
    	// TODO Auto-generated method stub
    	if(super.completeIt().equals(DocActionConstants.STATUS_Completed))
    	{
    		MBankAccount ba = new MBankAccount(getCtx(), getC_BankAccount_ID(), get_Trx());
    		if(MPaymentAutoAllocation.checkAutoAllocation(this))
    		{
    			MBankStatement bs = new MBankStatement(ba);
    			bs.setStatementDate(getDateTrx());
    			bs.setName("Auto Settlement : "+getDocumentNo());
				BigDecimal stmtamt = getPayAmt(true); 
				bs.setStatementDifference(stmtamt);
				bs.setEndingBalance(bs.getBeginningBalance().add(stmtamt));
    			if(bs.save())
    			{
    				MBankStatementLine bsl = new MBankStatementLine(bs);
    				if(getC_Charge_ID()>0){
    					bsl.setC_Payment_ID (getC_Payment_ID());
    					bsl.setC_Currency_ID (getC_Currency_ID());
    					bsl.setC_ConversionType_ID(getC_ConversionType_ID());
    					bsl.setC_BPartner_ID(getC_BPartner_ID());
    					//
    					BigDecimal amt = getPayAmt(true); 
    					bsl.setTrxAmt(amt);
    					bsl.setStmtAmt(amt);
    					//
    					bsl.setDescription(getDescription());
    					bsl.save();
    				}else{
    					bsl.setPayment(this);
        				bsl.setStatementLineDate(getDateTrx());
        				bsl.setDateAcct(getDateAcct());
        				bsl.save();	
    				}
    				
    			}
    			DocumentEngine.processIt(bs, X_C_BankStatement.DOCACTION_Complete);
    			bs.save();
    		}
//    		checkDownPayment();
    	}
    	
    	// Check related Advance Disbursement
    	MAdvanceDisbursement ad = MAdvanceDisbursement.getByPayment(this);
    	if(ad!=null)
    	{
    		ad.setIsPaid(true);
    		if(ad.save())
    		{
    			// Record Budget Transaction
    			MAdvanceDisbursement.recordUnrealizedBudget(ad, null, this);
    		}
    	}
		return DocActionConstants.STATUS_Completed;
    }    
    
    @Override
    public boolean voidIt() {
    	// TODO Auto-generated method stub
    	if(super.voidIt())
    		resetAdvanceDisbursement();
    	return true;
    }
    
    @Override
    public boolean reverseCorrectIt() {
    	// TODO Auto-generated method stub
    	if(super.reverseCorrectIt())
    	{
    		// Check related Advance Disbursement
        	MAdvanceDisbursement ad = MAdvanceDisbursement.getByPayment(this);
        	if(ad!=null)
        	{
        		ad.setIsPaid(false);
        		ad.setC_Payment_ID(0);
        		ad.save();
        	}
    	}
    	
    	return true;
    }
    
    private void checkDownPayment()
    {
    	MAllocationHdr header = null;
		String sql = "SELECT dpo.* FROM XX_DownPaymentPlan dpp " +
				"INNER JOIN XX_DownPaymentOrder dpo ON (dpp.XX_DownPayment_ID = dpo.XX_DownPayment_ID) " +
				"WHERE dpp.C_Payment_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			System.out.println(sql);
			System.out.println(getC_Payment_ID());
			pstmt.setInt(1, getC_Payment_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MDownPaymentOrder dpo = new MDownPaymentOrder(getCtx(), rs, get_Trx());
				if(header==null)
				{
					header = new MAllocationHdr(getCtx(), 0, get_Trx());
					header.setClientOrg(this);
					header.setC_Currency_ID(getC_Currency_ID());
					header.setDateTrx(getDateTrx());
					header.setDateAcct(getDateAcct());
					header.save();
				}
				
				MAllocationLine line = new MAllocationLine(header);
				line.setC_BPartner_ID(getC_BPartner_ID());
				line.setC_Order_ID(dpo.getC_Order_ID());
				line.setC_Payment_ID(getC_Payment_ID());
				BigDecimal payAmt = dpo.getDPAmount();
				if(!isReceipt())
					payAmt = payAmt.negate();
				line.setAmount(payAmt);
				line.save();
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(header!=null)
		{
			DocumentEngine.processIt(header, X_C_AllocationHdr.DOCSTATUS_Completed);
			header.save();
		}
    }

  }
