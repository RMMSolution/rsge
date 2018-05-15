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
/** Generated Model for XX_UnusedBudgetRule
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_UnusedBudgetRule extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_UnusedBudgetRule_ID id
    @param trx transaction
    */
    public X_XX_UnusedBudgetRule (Ctx ctx, int XX_UnusedBudgetRule_ID, Trx trx)
    {
        super (ctx, XX_UnusedBudgetRule_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_UnusedBudgetRule_ID == 0)
        {
            setName (null);
            setReservedPercentage (Env.ZERO);	// 0
            setValue (null);
            setXX_UnusedBudgetRule_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_UnusedBudgetRule (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27673006273789L;
    /** Last Updated Timestamp 2014-01-28 10:49:17.0 */
    public static final long updatedMS = 1390880957000L;
    /** AD_Table_ID=1000192 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_UnusedBudgetRule");
        
    }
    ;
    
    /** TableName=XX_UnusedBudgetRule */
    public static final String Table_Name="XX_UnusedBudgetRule";
    
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
    
    /** Set Reserved Percentage.
    @param ReservedPercentage Percentage of unused budget from previous period which is reserved */
    public void setReservedPercentage (java.math.BigDecimal ReservedPercentage)
    {
        if (ReservedPercentage == null) throw new IllegalArgumentException ("ReservedPercentage is mandatory.");
        set_Value ("ReservedPercentage", ReservedPercentage);
        
    }
    
    /** Get Reserved Percentage.
    @return Percentage of unused budget from previous period which is reserved */
    public java.math.BigDecimal getReservedPercentage() 
    {
        return get_ValueAsBigDecimal("ReservedPercentage");
        
    }
    
    /** Set Search Key.
    @param Value Search key for the record in the format required - must be unique */
    public void setValue (String Value)
    {
        if (Value == null) throw new IllegalArgumentException ("Value is mandatory.");
        set_Value ("Value", Value);
        
    }
    
    /** Get Search Key.
    @return Search key for the record in the format required - must be unique */
    public String getValue() 
    {
        return (String)get_Value("Value");
        
    }
    
    /** Set Unused Budget Rule.
    @param XX_UnusedBudgetRule_ID Rule which apply to part of budget which is not used */
    public void setXX_UnusedBudgetRule_ID (int XX_UnusedBudgetRule_ID)
    {
        if (XX_UnusedBudgetRule_ID < 1) throw new IllegalArgumentException ("XX_UnusedBudgetRule_ID is mandatory.");
        set_ValueNoCheck ("XX_UnusedBudgetRule_ID", Integer.valueOf(XX_UnusedBudgetRule_ID));
        
    }
    
    /** Get Unused Budget Rule.
    @return Rule which apply to part of budget which is not used */
    public int getXX_UnusedBudgetRule_ID() 
    {
        return get_ValueAsInt("XX_UnusedBudgetRule_ID");
        
    }
    
    
}
