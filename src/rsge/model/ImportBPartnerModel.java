package rsge.model;

import java.sql.ResultSet;

import org.compiere.model.X_I_BPartner;
import org.compiere.util.Ctx;
import org.compiere.util.Trx;

public class ImportBPartnerModel extends X_I_BPartner {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImportBPartnerModel(Ctx ctx, int I_BPartner_ID, Trx trx) {
		super(ctx, I_BPartner_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public ImportBPartnerModel(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
    /** Set City.
    @param C_City_ID City */
    public void setC_City_ID (int C_City_ID)
    {
        if (C_City_ID <= 0) set_Value ("C_City_ID", null);
        else
        set_Value ("C_City_ID", Integer.valueOf(C_City_ID));
        
    }
    
    /** Get City.
    @return City */
    public int getC_City_ID() 
    {
        return get_ValueAsInt("C_City_ID");
        
    }
    
    /** Set District.
    @param XX_District_ID District */
    public void setXX_District_ID (int XX_District_ID)
    {
        if (XX_District_ID <= 0) set_Value ("XX_District_ID", null);
        else
        set_Value ("XX_District_ID", Integer.valueOf(XX_District_ID));
        
    }
    
    /** Get District.
    @return District */
    public int getXX_District_ID() 
    {
        return get_ValueAsInt("XX_District_ID");
        
    }
    
    /** Set DistrictName.
    @param DistrictName Alphanumeric identifier of the entity */
    public void setDistrictName (String DistrictName)
    {
        set_Value ("DistrictName", DistrictName);
        
    }
    
    /** Get DistrictName.
    @return Alphanumeric identifier of the entity */
    public String getDistrictName() 
    {
        return (String)get_Value("DistrictName");
        
    }
    
    /** Set SubDistrictName.
    @param SubDistrictName Alphanumeric identifier of the entity */
    public void setSubDistrictName (String SubDistrictName)
    {
        set_Value ("SubDistrictName", SubDistrictName);
        
    }
    
    /** Get SubDistrictName.
    @return Alphanumeric identifier of the entity */
    public String getSubDistrictName() 
    {
        return (String)get_Value("SubDistrictName");
        
    }



    /** Set Sub District.
    @param XX_SubDistrict_ID Sub District */
    public void setXX_SubDistrict_ID (int XX_SubDistrict_ID)
    {
        if (XX_SubDistrict_ID <= 0) set_Value ("XX_SubDistrict_ID", null);
        else
        set_Value ("XX_SubDistrict_ID", Integer.valueOf(XX_SubDistrict_ID));
        
    }
    
    /** Get Sub District.
    @return Sub District */
    public int getXX_SubDistrict_ID() 
    {
        return get_ValueAsInt("XX_SubDistrict_ID");
        
    }


}
