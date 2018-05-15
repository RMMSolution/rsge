/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.model.MExpenseType;
import org.compiere.model.MResource;
import org.compiere.model.MResourceType;
import org.compiere.model.X_AD_Tree;
import org.compiere.model.X_I_Product;
import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.utils.GeneralEnhancementUtils;

/**
 * @author Fanny
 *
 */
public class MProduct extends org.compiere.model.MProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MProduct(Ctx ctx, int M_Product_ID, Trx trx) {
		super(ctx, M_Product_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MProduct(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	public MProduct(int M_Product_ID, X_I_Product impP) {
		super(M_Product_ID, impP);
		// TODO Auto-generated constructor stub
	}

	public MProduct(MExpenseType et) {
		super(et);
		// TODO Auto-generated constructor stub
	}

	public MProduct(MResource resource, MResourceType resourceType) {
		super(resource, resourceType);
		// TODO Auto-generated constructor stub
	}
	
    /** Set Acceptable Receipt Qty Difference.
    @param ReceiptQtyTolerance Acceptable percentage difference between order/invoice Quantity and the actual receipt Quantity */
    public void setReceiptQtyTolerance (java.math.BigDecimal ReceiptQtyTolerance)
    {
        if (ReceiptQtyTolerance == null) throw new IllegalArgumentException ("ReceiptQtyTolerance is mandatory.");
        set_Value ("ReceiptQtyTolerance", ReceiptQtyTolerance);
        
    }
    
    /** Get Acceptable Receipt Qty Difference.
    @return Acceptable percentage difference between order/invoice Quantity and the actual receipt Quantity */
    public java.math.BigDecimal getReceiptQtyTolerance() 
    {
        return get_ValueAsBigDecimal("ReceiptQtyTolerance");
        
    }

    /** Set Parent.
    @param Parent_ID Parent of Entity */
    public void setParent_ID (int Parent_ID)
    {
        if (Parent_ID <= 0) set_Value ("Parent_ID", null);
        else
        set_Value ("Parent_ID", Integer.valueOf(Parent_ID));
        
    }
    
    /** Get Parent.
    @return Parent of Entity */
    public int getParent_ID() 
    {
        return get_ValueAsInt("Parent_ID");
        
    }
    
    @Override
    protected boolean beforeSave(boolean newRecord) {
    	// TODO Auto-generated method stub
    	if(super.beforeSave(newRecord))
    	{
    		if(is_ValueChanged("IsSummary"))
    		{
    			if(isSummary())
    				setParent_ID(0);
    		}
    	}
    	return true;
    }
    
    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {
    	// TODO Auto-generated method stub
    	if(super.afterSave(newRecord, success))
    	{
    		if(is_ValueChanged("Parent_ID"))
    		{
        		int parentID = 0;
        		if(getParent_ID()>0)
        			parentID = getParent_ID();
        		GeneralEnhancementUtils.updateEntityParent(X_AD_Tree.TREETYPE_Product, getM_Product_ID(), parentID, getAD_Client_ID(), get_Trx());
    		}
    		
    	}
    	return true;
    }

}
