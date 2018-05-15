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
/** Generated Model for XX_ActivityAccess
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_ActivityAccess extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_ActivityAccess_ID id
    @param trx transaction
    */
    public X_XX_ActivityAccess (Ctx ctx, int XX_ActivityAccess_ID, Trx trx)
    {
        super (ctx, XX_ActivityAccess_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_ActivityAccess_ID == 0)
        {
            setC_Activity_ID (0);
            setC_ActivityTarget_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_ActivityAccess (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664543692789L;
    /** Last Updated Timestamp 2013-10-22 12:06:16.0 */
    public static final long updatedMS = 1382418376000L;
    /** AD_Table_ID=1000514 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_ActivityAccess");
        
    }
    ;
    
    /** TableName=XX_ActivityAccess */
    public static final String Table_Name="XX_ActivityAccess";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Activity.
    @param C_Activity_ID Business Activity */
    public void setC_Activity_ID (int C_Activity_ID)
    {
        if (C_Activity_ID < 1) throw new IllegalArgumentException ("C_Activity_ID is mandatory.");
        set_ValueNoCheck ("C_Activity_ID", Integer.valueOf(C_Activity_ID));
        
    }
    
    /** Get Activity.
    @return Business Activity */
    public int getC_Activity_ID() 
    {
        return get_ValueAsInt("C_Activity_ID");
        
    }
    
    /** Set Target Activity.
    @param C_ActivityTarget_ID Target activity */
    public void setC_ActivityTarget_ID (int C_ActivityTarget_ID)
    {
        if (C_ActivityTarget_ID < 1) throw new IllegalArgumentException ("C_ActivityTarget_ID is mandatory.");
        set_ValueNoCheck ("C_ActivityTarget_ID", Integer.valueOf(C_ActivityTarget_ID));
        
    }
    
    /** Get Target Activity.
    @return Target activity */
    public int getC_ActivityTarget_ID() 
    {
        return get_ValueAsInt("C_ActivityTarget_ID");
        
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
    
    
}
