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
/** Generated Model for XX_ClientOrg
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_ClientOrg extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_ClientOrg_ID id
    @param trx transaction
    */
    public X_XX_ClientOrg (Ctx ctx, int XX_ClientOrg_ID, Trx trx)
    {
        super (ctx, XX_ClientOrg_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_ClientOrg_ID == 0)
        {
            setOrg_ID (0);
            setOrgName (null);
            setXX_ClientOrg_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_ClientOrg (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27807101345325L;
    /** Last Updated Timestamp 2018-04-29 11:27:08.536 */
    public static final long updatedMS = 1524976028536L;
    /** AD_Table_ID=1000126 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_ClientOrg");
        
    }
    ;
    
    /** TableName=XX_ClientOrg */
    public static final String Table_Name="XX_ClientOrg";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Org.
    @param Org_ID Organizational entity within client */
    public void setOrg_ID (int Org_ID)
    {
        if (Org_ID < 1) throw new IllegalArgumentException ("Org_ID is mandatory.");
        set_ValueNoCheck ("Org_ID", Integer.valueOf(Org_ID));
        
    }
    
    /** Get Org.
    @return Organizational entity within client */
    public int getOrg_ID() 
    {
        return get_ValueAsInt("Org_ID");
        
    }
    
    /** Set Organization Name.
    @param OrgName Name of the Organization */
    public void setOrgName (String OrgName)
    {
        if (OrgName == null) throw new IllegalArgumentException ("OrgName is mandatory.");
        set_ValueNoCheck ("OrgName", OrgName);
        
    }
    
    /** Get Organization Name.
    @return Name of the Organization */
    public String getOrgName() 
    {
        return (String)get_Value("OrgName");
        
    }
    
    /** Set Organization Key.
    @param OrgValue Key of the Organization */
    public void setOrgValue (String OrgValue)
    {
        set_Value ("OrgValue", OrgValue);
        
    }
    
    /** Get Organization Key.
    @return Key of the Organization */
    public String getOrgValue() 
    {
        return (String)get_Value("OrgValue");
        
    }
    
    /** Set Client Organization.
    @param XX_ClientOrg_ID Client organization */
    public void setXX_ClientOrg_ID (int XX_ClientOrg_ID)
    {
        if (XX_ClientOrg_ID < 1) throw new IllegalArgumentException ("XX_ClientOrg_ID is mandatory.");
        set_ValueNoCheck ("XX_ClientOrg_ID", Integer.valueOf(XX_ClientOrg_ID));
        
    }
    
    /** Get Client Organization.
    @return Client organization */
    public int getXX_ClientOrg_ID() 
    {
        return get_ValueAsInt("XX_ClientOrg_ID");
        
    }
    
    
}
