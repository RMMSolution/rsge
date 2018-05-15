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
/** Generated Model for XX_MobileAppsUser
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_MobileAppsUser extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_MobileAppsUser_ID id
    @param trx transaction
    */
    public X_XX_MobileAppsUser (Ctx ctx, int XX_MobileAppsUser_ID, Trx trx)
    {
        super (ctx, XX_MobileAppsUser_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_MobileAppsUser_ID == 0)
        {
            setXX_MobileApps_ID (0);
            setXX_MobileUser_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_MobileAppsUser (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27756747358980L;
    /** Last Updated Timestamp 2016-09-23 16:14:02.191 */
    public static final long updatedMS = 1474622042191L;
    /** AD_Table_ID=1000163 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_MobileAppsUser");
        
    }
    ;
    
    /** TableName=XX_MobileAppsUser */
    public static final String Table_Name="XX_MobileAppsUser";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Confirmation Code.
    @param MobileConfirmationCode Confirmation code for mobile transaction */
    public void setMobileConfirmationCode (String MobileConfirmationCode)
    {
        set_Value ("MobileConfirmationCode", MobileConfirmationCode);
        
    }
    
    /** Get Confirmation Code.
    @return Confirmation code for mobile transaction */
    public String getMobileConfirmationCode() 
    {
        return (String)get_Value("MobileConfirmationCode");
        
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
    
    /** Set Mobile Apps.
    @param XX_MobileApps_ID Mobile apps */
    public void setXX_MobileApps_ID (int XX_MobileApps_ID)
    {
        if (XX_MobileApps_ID < 1) throw new IllegalArgumentException ("XX_MobileApps_ID is mandatory.");
        set_ValueNoCheck ("XX_MobileApps_ID", Integer.valueOf(XX_MobileApps_ID));
        
    }
    
    /** Get Mobile Apps.
    @return Mobile apps */
    public int getXX_MobileApps_ID() 
    {
        return get_ValueAsInt("XX_MobileApps_ID");
        
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
