package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

public class MProductCategory extends org.compiere.model.MProductCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MProductCategory(Ctx ctx, int M_Product_Category_ID, Trx trx) {
		super(ctx, M_Product_Category_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MProductCategory(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		if(isNoBudgetCheck())
		{
			setHasOwnProductBudget(false);
			setCheckBudgetQty(false);
		}
		return true;
	}
	
    /** Set Check Budget Quantity.
    @param CheckBudgetQty Indicate for all product under this category, budget quantity must be considered in budget check. Only for type of Item */
    public void setCheckBudgetQty (boolean CheckBudgetQty)
    {
        set_Value ("CheckBudgetQty", Boolean.valueOf(CheckBudgetQty));
        
    }
    
    /** Get Check Budget Quantity.
    @return Indicate for all product under this category, budget quantity must be considered in budget check. Only for type of Item */
    public boolean isCheckBudgetQty() 
    {
        return get_ValueAsBoolean("CheckBudgetQty");
        
    }
    
    /** Set Charge.
    @param C_Charge_ID Additional document charges */
    public void setC_Charge_ID (int C_Charge_ID)
    {
        if (C_Charge_ID <= 0) set_Value ("C_Charge_ID", null);
        else
        set_Value ("C_Charge_ID", Integer.valueOf(C_Charge_ID));
        
    }
    
    /** Get Charge.
    @return Additional document charges */
    public int getC_Charge_ID() 
    {
        return get_ValueAsInt("C_Charge_ID");
        
    }

    
    /** Set Has Own Budget.
    @param HasOwnProductBudget Indicate for all product under this category, has their own budget which must be considered in budget check */
    public void setHasOwnProductBudget (boolean HasOwnProductBudget)
    {
        set_Value ("HasOwnProductBudget", Boolean.valueOf(HasOwnProductBudget));
        
    }
    
    /** Get Has Own Budget.
    @return Indicate for all product under this category, has their own budget which must be considered in budget check */
    public boolean isHasOwnProductBudget() 
    {
        return get_ValueAsBoolean("HasOwnProductBudget");
        
    }
    
    /** Set Auto Expensed.
    @param IsAutoExpensed Automatically expensed product under this category */
    public void setIsAutoExpensed (boolean IsAutoExpensed)
    {
        set_Value ("IsAutoExpensed", Boolean.valueOf(IsAutoExpensed));
        
    }
    
    /** Get Auto Expensed.
    @return Automatically expensed product under this category */
    public boolean isAutoExpensed() 
    {
        return get_ValueAsBoolean("IsAutoExpensed");
        
    }
    
    /**
    @param IsAutoTransfer Automatically Transfer product under this category */
    public void setIsAutoTransfer (boolean IsAutoTransfer)
    {
        set_Value ("IsAutoTransfer", Boolean.valueOf(IsAutoTransfer));
        
    }
    
    /** Get Auto Transfer.
    @return Automatically Transfer product under this category */
    public boolean isAutoTransfer() 
    {
        return get_ValueAsBoolean("IsAutoTransfer");
        
    }


    /** Set No Budget Check.
    @param NoBudgetCheck No budget check for transaction regarding product under this category */
    public void setNoBudgetCheck (boolean NoBudgetCheck)
    {
        set_Value ("NoBudgetCheck", Boolean.valueOf(NoBudgetCheck));
        
    }
    
    /** Get No Budget Check.
    @return No budget check for transaction regarding product under this category */
    public boolean isNoBudgetCheck() 
    {
        return get_ValueAsBoolean("NoBudgetCheck");
        
    }

}
