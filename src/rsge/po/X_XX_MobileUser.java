/******************************************************************************
 * Product: Compiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2008 Compiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us at *
 * Compiere, Inc., 3600 Bridge Parkway #102, Redwood City, CA 94065, USA      *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package rsge.po;

/** Generated Model - DO NOT CHANGE */
import java.sql.*;
import org.compiere.framework.*;
import org.compiere.util.*;
/** Generated Model for XX_MobileUser
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_MobileUser extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_MobileUser_ID id
    @param trx transaction
    */
    public X_XX_MobileUser (Ctx ctx, int XX_MobileUser_ID, Trx trx)
    {
        super (ctx, XX_MobileUser_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_MobileUser_ID == 0)
        {
            setMobileUserName (null);
            setXX_MobileUser_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_MobileUser (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27756747010645L;
    /** Last Updated Timestamp 2016-09-23 16:08:13.856 */
    public static final long updatedMS = 1474621693856L;
    /** AD_Table_ID=1000164 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_MobileUser");
        
    }
    ;
    
    /** TableName=XX_MobileUser */
    public static final String Table_Name="XX_MobileUser";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set User/Contact.
    @param AD_User_ID User within the system - Internal or Business Partner Contact */
    public void setAD_User_ID (int AD_User_ID)
    {
        if (AD_User_ID <= 0) set_Value ("AD_User_ID", null);
        else
        set_Value ("AD_User_ID", Integer.valueOf(AD_User_ID));
        
    }
    
    /** Get User/Contact.
    @return User within the system - Internal or Business Partner Contact */
    public int getAD_User_ID() 
    {
        return get_ValueAsInt("AD_User_ID");
        
    }
    
    /** Set Business Partner.
    @param C_BPartner_ID Identifies a Business Partner */
    public void setC_BPartner_ID (int C_BPartner_ID)
    {
        if (C_BPartner_ID <= 0) set_Value ("C_BPartner_ID", null);
        else
        set_Value ("C_BPartner_ID", Integer.valueOf(C_BPartner_ID));
        
    }
    
    /** Get Business Partner.
    @return Identifies a Business Partner */
    public int getC_BPartner_ID() 
    {
        return get_ValueAsInt("C_BPartner_ID");
        
    }
    
    /** Set Description.
    @param Description Optional short description of the record */
    public void setDescription (String Description)
    {
        set_Value ("Description", Description);
        
    }
    
    /** Get Description.
    @return Optional short description of the record */
    public String getDescription() 
    {
        return (String)get_Value("Description");
        
    }
    
    /** Set Username.
    @param MobileUserName Username of mobile's user */
    public void setMobileUserName (String MobileUserName)
    {
        if (MobileUserName == null) throw new IllegalArgumentException ("MobileUserName is mandatory.");
        set_Value ("MobileUserName", MobileUserName);
        
    }
    
    /** Get Username.
    @return Username of mobile's user */
    public String getMobileUserName() 
    {
        return (String)get_Value("MobileUserName");
        
    }
    
    /** Set Password.
    @param Password Password of any length (case sensitive) */
    public void setPassword (String Password)
    {
        set_Value ("Password", Password);
        
    }
    
    /** Get Password.
    @return Password of any length (case sensitive) */
    public String getPassword() 
    {
        return (String)get_Value("Password");
        
    }
    
    /** Set Mobile User.
    @param XX_MobileUser_ID Mobile user of mobile apps */
    public void setXX_MobileUser_ID (int XX_MobileUser_ID)
    {
        if (XX_MobileUser_ID < 1) throw new IllegalArgumentException ("XX_MobileUser_ID is mandatory.");
        set_ValueNoCheck ("XX_MobileUser_ID", Integer.valueOf(XX_MobileUser_ID));
        
    }
    
    /** Get Mobile User.
    @return Mobile user of mobile apps */
    public int getXX_MobileUser_ID() 
    {
        return get_ValueAsInt("XX_MobileUser_ID");
        
    }
    
    
}
