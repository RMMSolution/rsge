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
/** Generated Model for XX_ActualDL_Acct
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_ActualDL_Acct extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_ActualDL_Acct_ID id
    @param trx transaction
    */
    public X_XX_ActualDL_Acct (Ctx ctx, int XX_ActualDL_Acct_ID, Trx trx)
    {
        super (ctx, XX_ActualDL_Acct_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_ActualDL_Acct_ID == 0)
        {
            setXX_ActualDL_Acct_ID (0);
            setXX_GeneralSetup_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_ActualDL_Acct (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27731314722146L;
    /** Last Updated Timestamp 2015-12-04 07:36:45.357 */
    public static final long updatedMS = 1449189405357L;
    /** AD_Table_ID=1008995 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_ActualDL_Acct");
        
    }
    ;
    
    /** TableName=XX_ActualDL_Acct */
    public static final String Table_Name="XX_ActualDL_Acct";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
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
    
    /** Set Labor.
    @param LaborElement_ID Labor Cost Element */
    public void setLaborElement_ID (int LaborElement_ID)
    {
        if (LaborElement_ID <= 0) set_Value ("LaborElement_ID", null);
        else
        set_Value ("LaborElement_ID", Integer.valueOf(LaborElement_ID));
        
    }
    
    /** Get Labor.
    @return Labor Cost Element */
    public int getLaborElement_ID() 
    {
        return get_ValueAsInt("LaborElement_ID");
        
    }
    
    /** Set Direct Labour Account.
    @param XX_ActualDL_Acct_ID Direct Labour Account */
    public void setXX_ActualDL_Acct_ID (int XX_ActualDL_Acct_ID)
    {
        if (XX_ActualDL_Acct_ID < 1) throw new IllegalArgumentException ("XX_ActualDL_Acct_ID is mandatory.");
        set_ValueNoCheck ("XX_ActualDL_Acct_ID", Integer.valueOf(XX_ActualDL_Acct_ID));
        
    }
    
    /** Get Direct Labour Account.
    @return Direct Labour Account */
    public int getXX_ActualDL_Acct_ID() 
    {
        return get_ValueAsInt("XX_ActualDL_Acct_ID");
        
    }
    
    /** Set General Setup.
    @param XX_GeneralSetup_ID Basic setup for general enhancement extension */
    public void setXX_GeneralSetup_ID (int XX_GeneralSetup_ID)
    {
        if (XX_GeneralSetup_ID < 1) throw new IllegalArgumentException ("XX_GeneralSetup_ID is mandatory.");
        set_Value ("XX_GeneralSetup_ID", Integer.valueOf(XX_GeneralSetup_ID));
        
    }
    
    /** Get General Setup.
    @return Basic setup for general enhancement extension */
    public int getXX_GeneralSetup_ID() 
    {
        return get_ValueAsInt("XX_GeneralSetup_ID");
        
    }
    
    
}
