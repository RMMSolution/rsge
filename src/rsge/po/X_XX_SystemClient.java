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
/** Generated Model for XX_SystemClient
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_SystemClient extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_SystemClient_ID id
    @param trx transaction
    */
    public X_XX_SystemClient (Ctx ctx, int XX_SystemClient_ID, Trx trx)
    {
        super (ctx, XX_SystemClient_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_SystemClient_ID == 0)
        {
            setName (null);
            setXX_SystemClient_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_SystemClient (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27746225917789L;
    /** Last Updated Timestamp 2016-05-24 21:36:41.0 */
    public static final long updatedMS = 1464100601000L;
    /** AD_Table_ID=1000190 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_SystemClient");
        
    }
    ;
    
    /** TableName=XX_SystemClient */
    public static final String Table_Name="XX_SystemClient";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Client.
    @param Client_ID Client */
    public void setClient_ID (int Client_ID)
    {
        if (Client_ID <= 0) set_Value ("Client_ID", null);
        else
        set_Value ("Client_ID", Integer.valueOf(Client_ID));
        
    }
    
    /** Get Client.
    @return Client */
    public int getClient_ID() 
    {
        return get_ValueAsInt("Client_ID");
        
    }
    
    /** Set Name.
    @param Name Alphanumeric identifier of the entity */
    public void setName (String Name)
    {
        if (Name == null) throw new IllegalArgumentException ("Name is mandatory.");
        set_Value ("Name", Name);
        
    }
    
    /** Get Name.
    @return Alphanumeric identifier of the entity */
    public String getName() 
    {
        return (String)get_Value("Name");
        
    }
    
    /** Set System's Client.
    @param XX_SystemClient_ID System's Client */
    public void setXX_SystemClient_ID (int XX_SystemClient_ID)
    {
        if (XX_SystemClient_ID < 1) throw new IllegalArgumentException ("XX_SystemClient_ID is mandatory.");
        set_ValueNoCheck ("XX_SystemClient_ID", Integer.valueOf(XX_SystemClient_ID));
        
    }
    
    /** Get System's Client.
    @return System's Client */
    public int getXX_SystemClient_ID() 
    {
        return get_ValueAsInt("XX_SystemClient_ID");
        
    }
    
    
}
