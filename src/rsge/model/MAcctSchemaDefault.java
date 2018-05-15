/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

/**
 * @author Fanny
 *
 */
public class MAcctSchemaDefault extends org.compiere.model.MAcctSchemaDefault{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MAcctSchemaDefault(Ctx ctx, int C_AcctSchema_ID, Trx trx) {
		super(ctx, C_AcctSchema_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MAcctSchemaDefault(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

    /** Set Copy Enhancement Account.
    @param CopyAdditionalAccount Copy additional enhancement account */
    public void setCopyAdditionalAccount (String CopyAdditionalAccount)
    {
        set_Value ("CopyAdditionalAccount", CopyAdditionalAccount);
        
    }
    
    /** Get Copy Enhancement Account.
    @return Copy additional enhancement account */
    public String getCopyAdditionalAccount() 
    {
        return (String)get_Value("CopyAdditionalAccount");
        
    }
    
    /** Set Use Return Account.
    @param IsUseReturnAccount Use separate account to record product return */
    public void setIsUseReturnAccount (boolean IsUseReturnAccount)
    {
        set_Value ("IsUseReturnAccount", Boolean.valueOf(IsUseReturnAccount));
        
    }
    
    /** Get Use Return Account.
    @return Use separate account to record product return */
    public boolean isUseReturnAccount() 
    {
        return get_ValueAsBoolean("IsUseReturnAccount");
        
    }
    
    /** Set Unallocated Return .
    @param UnallocatedReturn_Acct Account for Unallocated from Customer (Sales) */
    public void setUnallocatedReturn_Acct (int UnallocatedReturn_Acct)
    {
        set_Value ("P_SalesReturn_Acct", Integer.valueOf(UnallocatedReturn_Acct));
        
    }
    
    /** Get Unallocated Return .
    @return Account for Unallocated Return from Customer (Sales) */
    public int getUnallocatedReturn_Acct() 
    {
        return get_ValueAsInt("UnallocatedReturn_Acct");
        
    }
    
    /** Set Returned Product Asset.
    @param P_AssetReturn_Acct Account for Returned Product Asset  (Inventory) */
    public void setP_AssetReturn_Acct (int P_AssetReturn_Acct)
    {
        set_Value ("P_AssetReturn_Acct", Integer.valueOf(P_AssetReturn_Acct));
        
    }
    
    /** Get Returned Product Asset.
    @return Account for Returned Product Asset  (Inventory) */
    public int getP_AssetReturn_Acct() 
    {
        return get_ValueAsInt("P_AssetReturn_Acct");
        
    }
    
    /** Set Returned Product COGS.
    @param P_COGSReturn_Acct Account for Returned Product Cost of Goods Sold */
    public void setP_COGSReturn_Acct (int P_COGSReturn_Acct)
    {
        set_Value ("P_COGSReturn_Acct", Integer.valueOf(P_COGSReturn_Acct));
        
    }
    
    /** Get Returned Product COGS.
    @return Account for Returned Product Cost of Goods Sold */
    public int getP_COGSReturn_Acct() 
    {
        return get_ValueAsInt("P_COGSReturn_Acct");
        
    }
    
    /** Set Product Sales Return .
    @param P_SalesReturn_Acct Account for Product Return from Customer (Sales) */
    public void setP_SalesReturn_Acct (int P_SalesReturn_Acct)
    {
        set_Value ("P_SalesReturn_Acct", Integer.valueOf(P_SalesReturn_Acct));
        
    }
    
    /** Get Product Sales Return .
    @return Account for Product Return from Customer (Sales) */
    public int getP_SalesReturn_Acct() 
    {
        return get_ValueAsInt("P_SalesReturn_Acct");
        
    }
    
    /** Set Service Charge.
    @param ServiceCharge_Acct Account for Service Charge */
    public void setServiceCharge_Acct (int ServiceCharge_Acct)
    {
        set_Value ("ServiceCharge_Acct", Integer.valueOf(ServiceCharge_Acct));
        
    }
    
    /** Get Service Charge Account
    @return Account for Service Charge */
    public int getServiceCharge_Acct() 
    {
        return get_ValueAsInt("ServiceCharge_Acct");
        
    }

    
	/**
	 * 	Get Accounting Schema Default Info
	 *	@param ctx context
	 *	@param C_AcctSchema_ID id
	 *	@return defaults
	 */
	public static MAcctSchemaDefault get (Ctx ctx, int C_AcctSchema_ID)
	{
		MAcctSchemaDefault retValue = null;
		String sql = "SELECT * FROM C_AcctSchema_Default WHERE C_AcctSchema_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, (Trx) null);
			pstmt.setInt(1, C_AcctSchema_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
				retValue = new MAcctSchemaDefault (ctx, rs, null);
		}
		catch (Exception e) {
			s_log.log(Level.SEVERE, sql, e);
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}

		return retValue;
	}	//	get

}
