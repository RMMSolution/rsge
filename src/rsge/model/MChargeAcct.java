package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MAcctSchema;
import org.compiere.model.X_C_Charge_Acct;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

public class MChargeAcct extends X_C_Charge_Acct {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MChargeAcct(Ctx ctx, int C_Charge_Acct_ID, Trx trx) {
		super(ctx, C_Charge_Acct_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MChargeAcct(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

    /** Set Customer Prepayment.
    @param C_Prepayment_Acct Account for customer prepayments */
    public void setC_Prepayment_Acct (int C_Prepayment_Acct)
    {
        set_Value ("C_Prepayment_Acct", Integer.valueOf(C_Prepayment_Acct));
        
    }
    
    /** Get Customer Prepayment.
    @return Account for customer prepayments */
    public int getC_Prepayment_Acct() 
    {
        return get_ValueAsInt("C_Prepayment_Acct");
        
    }
    
    /** Set Vendor Prepayment.
    @param V_Prepayment_Acct Account for Vendor Prepayments */
    public void setV_Prepayment_Acct (int V_Prepayment_Acct)
    {
        set_Value ("V_Prepayment_Acct", Integer.valueOf(V_Prepayment_Acct));
        
    }
    
    /** Get Vendor Prepayment.
    @return Account for Vendor Prepayments */
    public int getV_Prepayment_Acct() 
    {
        return get_ValueAsInt("V_Prepayment_Acct");
        
    }
    
    public static MChargeAcct get(int C_Charge_ID, MAcctSchema as)
    {
    	MChargeAcct ca = null;
    	
    	String sql = "SELECT * FROM C_Charge_Acct " +
    			"WHERE C_Charge_ID = ? AND C_AcctSchema_ID = ? ";
    	PreparedStatement pstmt = DB.prepareStatement(sql, as.get_Trx());
    	ResultSet rs = null;
    	
    	try{
    		pstmt.setInt(1, C_Charge_ID);
    		pstmt.setInt(2, as.getC_AcctSchema_ID());
    		rs = pstmt.executeQuery();
    		if(rs.next())
    			ca = new MChargeAcct(as.getCtx(), rs, as.get_Trx());
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		DB.closeResultSet(rs);
    		DB.closeStatement(pstmt);
    	}
		return ca;    	
    }
}
