/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

/**
 * @author Fanny
 *
 */
public class MProductCategoryAcct extends org.compiere.model.MProductCategoryAcct{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MProductCategoryAcct(Ctx ctx, int ignored, Trx trx) {
		super(ctx, ignored, trx);
		// TODO Auto-generated constructor stub
	}

	public MProductCategoryAcct(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
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

    public static MProductCategoryAcct get(Ctx ctx, int M_Product_ID, int C_AcctSchema_ID, Trx trx)
    {
    	MProductCategoryAcct pca = null;
    	String sql = "SELECT * FROM M_Product_Category_Acct pca "
    			+ "INNER JOIN M_Product p ON pca.M_Product_Category_ID = p.M_Product_Category_ID "
    			+ "WHERE p.M_Product_ID = ? "
    			+ "AND pca.C_AcctSchema_ID = ? ";
    	PreparedStatement pstmt = DB.prepareStatement(sql, trx);
    	ResultSet rs = null;
    	try{
    		pstmt.setInt(1, M_Product_ID);
    		pstmt.setInt(2, C_AcctSchema_ID);
    		rs = pstmt.executeQuery();
    		if(rs.next())
    			pca = new MProductCategoryAcct(ctx, rs, trx);
    		rs.close();
    		pstmt.close();
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return pca;
    }
}
