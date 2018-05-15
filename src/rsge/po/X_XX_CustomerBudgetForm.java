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
/** Generated Model for XX_CustomerBudgetForm
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_CustomerBudgetForm extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_CustomerBudgetForm_ID id
    @param trx transaction
    */
    public X_XX_CustomerBudgetForm (Ctx ctx, int XX_CustomerBudgetForm_ID, Trx trx)
    {
        super (ctx, XX_CustomerBudgetForm_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_CustomerBudgetForm_ID == 0)
        {
            setLine (0);	// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM XX_CustomerBudgetForm WHERE XX_SalesBudgetForm_ID = @XX_SalesBudgetForm_ID@
            setXX_CustomerBudgetForm_ID (0);
            setXX_SalesBudgetForm_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_CustomerBudgetForm (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27760205788442L;
    /** Last Updated Timestamp 2016-11-02 16:54:31.653 */
    public static final long updatedMS = 1478080471653L;
    /** AD_Table_ID=1000133 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_CustomerBudgetForm");
        
    }
    ;
    
    /** TableName=XX_CustomerBudgetForm */
    public static final String Table_Name="XX_CustomerBudgetForm";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Customer.
    @param Customer_ID Customer */
    public void setCustomer_ID (int Customer_ID)
    {
        if (Customer_ID <= 0) set_Value ("Customer_ID", null);
        else
        set_Value ("Customer_ID", Integer.valueOf(Customer_ID));
        
    }
    
    /** Get Customer.
    @return Customer */
    public int getCustomer_ID() 
    {
        return get_ValueAsInt("Customer_ID");
        
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
    
    /** Set Line No.
    @param Line Unique line for this document */
    public void setLine (int Line)
    {
        set_Value ("Line", Integer.valueOf(Line));
        
    }
    
    /** Get Line No.
    @return Unique line for this document */
    public int getLine() 
    {
        return get_ValueAsInt("Line");
        
    }
    
    /** Set Total Amount.
    @param TotalAmt Total Amount */
    public void setTotalAmt (java.math.BigDecimal TotalAmt)
    {
        set_Value ("TotalAmt", TotalAmt);
        
    }
    
    /** Get Total Amount.
    @return Total Amount */
    public java.math.BigDecimal getTotalAmt() 
    {
        return get_ValueAsBigDecimal("TotalAmt");
        
    }
    
    /** Set XX_CustomerBudgetForm_ID.
    @param XX_CustomerBudgetForm_ID XX_CustomerBudgetForm_ID */
    public void setXX_CustomerBudgetForm_ID (int XX_CustomerBudgetForm_ID)
    {
        if (XX_CustomerBudgetForm_ID < 1) throw new IllegalArgumentException ("XX_CustomerBudgetForm_ID is mandatory.");
        set_ValueNoCheck ("XX_CustomerBudgetForm_ID", Integer.valueOf(XX_CustomerBudgetForm_ID));
        
    }
    
    /** Get XX_CustomerBudgetForm_ID.
    @return XX_CustomerBudgetForm_ID */
    public int getXX_CustomerBudgetForm_ID() 
    {
        return get_ValueAsInt("XX_CustomerBudgetForm_ID");
        
    }
    
    /** Set Sales Budget Form.
    @param XX_SalesBudgetForm_ID Sales Budget Form */
    public void setXX_SalesBudgetForm_ID (int XX_SalesBudgetForm_ID)
    {
        if (XX_SalesBudgetForm_ID < 1) throw new IllegalArgumentException ("XX_SalesBudgetForm_ID is mandatory.");
        set_Value ("XX_SalesBudgetForm_ID", Integer.valueOf(XX_SalesBudgetForm_ID));
        
    }
    
    /** Get Sales Budget Form.
    @return Sales Budget Form */
    public int getXX_SalesBudgetForm_ID() 
    {
        return get_ValueAsInt("XX_SalesBudgetForm_ID");
        
    }
    
    
}
