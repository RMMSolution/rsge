/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.model.X_AD_Tree;
import org.compiere.model.X_C_Activity;
import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY
 *
 */
public class MActivity extends X_C_Activity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param C_Activity_ID
	 * @param trx
	 */
	public MActivity(Ctx ctx, int C_Activity_ID, Trx trx) {
		super(ctx, C_Activity_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MActivity(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
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
        		GeneralEnhancementUtils.updateEntityParent(X_AD_Tree.TREETYPE_Activity, getC_Activity_ID(), parentID, getAD_Client_ID(), get_Trx());
    		}
    		
    	}
    	return true;
    }

}
