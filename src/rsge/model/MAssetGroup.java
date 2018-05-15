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
 * @author FANNY R
 *
 */
public class MAssetGroup extends org.compiere.model.MAssetGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param ctx
	 * @param A_Asset_Group_ID
	 * @param trx
	 */
	public MAssetGroup(Ctx ctx, int A_Asset_Group_ID, Trx trx) {
		super(ctx, A_Asset_Group_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MAssetGroup(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
    
    /** Set Asset Depreciation.
    @param BudgetAssetDepreciation_Acct Account to record asset''s depreciation */
    public void setBudgetAssetDepreciation_Acct (int BudgetAssetDepreciation_Acct)
    {
        set_Value ("BudgetAssetDepreciation_Acct", Integer.valueOf(BudgetAssetDepreciation_Acct));
        
    }
    
    /** Get Asset Depreciation.
    @return Account to record asset''s depreciation */
    public int getBudgetAssetDepreciation_Acct() 
    {
        return get_ValueAsInt("BudgetAssetDepreciation_Acct");
        
    }
    
    /** Set Life Month.
    @param BudgetAssetLifeMonth Life Month */
    public void setBudgetAssetLifeMonth (int BudgetAssetLifeMonth)
    {
        set_Value ("BudgetAssetLifeMonth", Integer.valueOf(BudgetAssetLifeMonth));
        
    }
    
    /** Get Life Month.
    @return Life Month */
    public int getBudgetAssetLifeMonth() 
    {
        return get_ValueAsInt("BudgetAssetLifeMonth");
        
    }
    
    /** Set Life Year.
    @param BudgetAssetLifeYear Life year of asset */
    public void setBudgetAssetLifeYear (int BudgetAssetLifeYear)
    {
        set_Value ("BudgetAssetLifeYear", Integer.valueOf(BudgetAssetLifeYear));
        
    }
    
    /** Get Life Year.
    @return Life year of asset */
    public int getBudgetAssetLifeYear() 
    {
        return get_ValueAsInt("BudgetAssetLifeYear");
        
    }    
    
    /** Get Depreciation Method.
    @return Depreciation method for budget investment purpose */
    public String getBudgetDepreciationMethod() 
    {
        return (String)get_Value("BudgetDepreciationMethod");
        
    }
    
    /** Set Product.
    @param M_Product_Category_ID Product, Service, Item */
    public void setM_Product_Category_ID (int M_Product_Category_ID)
    {
        if (M_Product_Category_ID <= 0) set_Value ("M_Product_Category_ID", null);
        else
        set_Value ("M_Product_Category_ID", Integer.valueOf(M_Product_Category_ID));
        
    }
    
    /** Get Product.
    @return Product, Service, Item */
    public int getM_Product_Category_ID() 
    {
        return get_ValueAsInt("M_Product_Category_ID");
        
    }
    
    public static boolean checkProductAsset(MProduct product)
    {
    	boolean isAsset = false;
    	String sql = "SELECT 1 FROM A_Asset_Group ag "
    			+ "INNER JOIN M_Product p ON ag.M_Product_Category_ID = p.M_Product_Category_ID " +
    			"WHERE p.M_Product_ID = " + product.getM_Product_ID();
    	PreparedStatement pstmt = DB.prepareStatement(sql, product.get_Trx());
    	ResultSet rs = null;
    	try{
    		rs = pstmt.executeQuery();
    		if(rs.next())
    			isAsset = true;
    		rs.close();
    		pstmt.close();
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return isAsset;
    }
}
