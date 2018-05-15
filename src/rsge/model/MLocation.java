/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

/**
 * @author Fanny
 *
 */
public class MLocation extends org.compiere.model.MLocation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param C_Location_ID
	 * @param trx
	 */
	public MLocation(Ctx ctx, int C_Location_ID, Trx trx) {
		super(ctx, C_Location_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MLocation(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 	Full Constructor
	 *	@param ctx context
	 *	@param C_Country_ID country
	 *	@param C_Region_ID region
	 *	@param city city
	 *	@param trx transaction
	 */
	public MLocation (Ctx ctx, int C_Country_ID, int C_Region_ID, String city, Trx trx)
	{
		this(ctx, 0, trx);
		if (C_Country_ID != 0)
			setC_Country_ID(C_Country_ID);
		if (C_Region_ID != 0)
			setC_Region_ID(C_Region_ID);
		setCity(city);
	}	//	MLocation


    /** Set District.
    @param XX_District_ID District of region */
    public void setXX_District_ID (int XX_District_ID)
    {
        if (XX_District_ID <= 0) set_Value ("XX_District_ID", null);
        else
        set_Value ("XX_District_ID", Integer.valueOf(XX_District_ID));
        
    }
    
    /** Get District.
    @return District of region */
    public int getXX_District_ID() 
    {
        return get_ValueAsInt("XX_District_ID");
        
    }
    
    /** Set Sub District.
    @param XX_SubDistrict_ID Sub district of district */
    public void setXX_SubDistrict_ID (int XX_SubDistrict_ID)
    {
        if (XX_SubDistrict_ID <= 0) set_Value ("XX_SubDistrict_ID", null);
        else
        set_Value ("XX_SubDistrict_ID", Integer.valueOf(XX_SubDistrict_ID));
        
    }
    
    /** Get Sub District.
    @return Sub district of district */
    public int getXX_SubDistrict_ID() 
    {
        return get_ValueAsInt("XX_SubDistrict_ID");
        
    }

}
