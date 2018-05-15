package rsge.model;

import java.math.BigDecimal;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

public class MPriceList extends org.compiere.model.MPriceList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MPriceList(Ctx ctx, int M_PriceList_ID, Trx trx) {
		super(ctx, M_PriceList_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MPriceList(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
    /** Set Calculate Service Charge.
    @param IsCalculateServiceCharge If this price list is selected then service charge will be calculated */
    public void setIsCalculateServiceCharge (boolean IsCalculateServiceCharge)
    {
        set_Value ("IsCalculateServiceCharge", Boolean.valueOf(IsCalculateServiceCharge));
        
    }
    
    /** Get Calculate Service Charge.
    @return If this price list is selected then service charge will be calculated */
    public boolean isCalculateServiceCharge() 
    {
        return get_ValueAsBoolean("IsCalculateServiceCharge");
        
    }
    
    /** Set Service Charge.
    @param ServiceChargeRate Service charge rate */
    public void setServiceChargeRate (java.math.BigDecimal ServiceChargeRate)
    {
        set_Value ("ServiceChargeRate", ServiceChargeRate);
        
    }
    
    /** Get Service Charge.
    @return Service charge rate */
    public java.math.BigDecimal getServiceChargeRate() 
    {
        return get_ValueAsBigDecimal("ServiceChargeRate");
        
    }
    
    @Override
    protected boolean beforeSave(boolean newRecord) {
    	// TODO Auto-generated method stub
    	if(!isSOPriceList())
    	{
    		setIsCalculateServiceCharge(false);
    		setServiceChargeRate(BigDecimal.ZERO);
    	}
    	return true;
    }


}
