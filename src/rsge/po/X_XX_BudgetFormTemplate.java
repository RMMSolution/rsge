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
/** Generated Model for XX_BudgetFormTemplate
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_BudgetFormTemplate extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_BudgetFormTemplate_ID id
    @param trx transaction
    */
    public X_XX_BudgetFormTemplate (Ctx ctx, int XX_BudgetFormTemplate_ID, Trx trx)
    {
        super (ctx, XX_BudgetFormTemplate_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_BudgetFormTemplate_ID == 0)
        {
            setIsProduct (false);	// N
            setIsSOTrx (false);	// N
            setIsUsedProductTree (false);	// N
            setIsValid (false);	// N
            setName (null);
            setValue (null);
            setXX_BudgetFormTemplate_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_BudgetFormTemplate (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27686314351789L;
    /** Last Updated Timestamp 2014-07-01 11:30:35.0 */
    public static final long updatedMS = 1404189035000L;
    /** AD_Table_ID=1000113 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_BudgetFormTemplate");
        
    }
    ;
    
    /** TableName=XX_BudgetFormTemplate */
    public static final String Table_Name="XX_BudgetFormTemplate";
    
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
    
    /** Set Product.
    @param IsProduct Product */
    public void setIsProduct (boolean IsProduct)
    {
        set_Value ("IsProduct", Boolean.valueOf(IsProduct));
        
    }
    
    /** Get Product.
    @return Product */
    public boolean isProduct() 
    {
        return get_ValueAsBoolean("IsProduct");
        
    }
    
    /** Set Sales Transaction.
    @param IsSOTrx This is a Sales Transaction */
    public void setIsSOTrx (boolean IsSOTrx)
    {
        set_Value ("IsSOTrx", Boolean.valueOf(IsSOTrx));
        
    }
    
    /** Get Sales Transaction.
    @return This is a Sales Transaction */
    public boolean isSOTrx() 
    {
        return get_ValueAsBoolean("IsSOTrx");
        
    }
    
    /** Set Use Product Tree.
    @param IsUsedProductTree Use product tree in check product budget */
    public void setIsUsedProductTree (boolean IsUsedProductTree)
    {
        set_Value ("IsUsedProductTree", Boolean.valueOf(IsUsedProductTree));
        
    }
    
    /** Get Use Product Tree.
    @return Use product tree in check product budget */
    public boolean isUsedProductTree() 
    {
        return get_ValueAsBoolean("IsUsedProductTree");
        
    }
    
    /** Set Valid.
    @param IsValid Element is valid */
    public void setIsValid (boolean IsValid)
    {
        set_Value ("IsValid", Boolean.valueOf(IsValid));
        
    }
    
    /** Get Valid.
    @return Element is valid */
    public boolean isValid() 
    {
        return get_ValueAsBoolean("IsValid");
        
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
    
    /** Sales/Purchase = 1 */
    public static final String TRANSACTIONTYPE_SalesPurchase = X_Ref__Product_Trx_Type.SALES_PURCHASE.getValue();
    /** Return = 2 */
    public static final String TRANSACTIONTYPE_Return = X_Ref__Product_Trx_Type.RETURN.getValue();
    /** Discount = 3 */
    public static final String TRANSACTIONTYPE_Discount = X_Ref__Product_Trx_Type.DISCOUNT.getValue();
    /** Set Transaction Type.
    @param TransactionType Type of transaction */
    public void setTransactionType (String TransactionType)
    {
        if (!X_Ref__Product_Trx_Type.isValid(TransactionType))
        throw new IllegalArgumentException ("TransactionType Invalid value - " + TransactionType + " - Reference_ID=1000117 - 1 - 2 - 3");
        set_Value ("TransactionType", TransactionType);
        
    }
    
    /** Get Transaction Type.
    @return Type of transaction */
    public String getTransactionType() 
    {
        return (String)get_Value("TransactionType");
        
    }
    
    /** Set Validate Template.
    @param ValidateTemplate ValidateTemplate Validate template so it can be used immediately */
    public void setValidateTemplate (String ValidateTemplate)
    {
        set_Value ("ValidateTemplate", ValidateTemplate);
        
    }
    
    /** Get Validate Template.
    @return ValidateTemplate Validate template so it can be used immediately */
    public String getValidateTemplate() 
    {
        return (String)get_Value("ValidateTemplate");
        
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
    
    /** Set Budget Form Template.
    @param XX_BudgetFormTemplate_ID Template for budget form */
    public void setXX_BudgetFormTemplate_ID (int XX_BudgetFormTemplate_ID)
    {
        if (XX_BudgetFormTemplate_ID < 1) throw new IllegalArgumentException ("XX_BudgetFormTemplate_ID is mandatory.");
        set_ValueNoCheck ("XX_BudgetFormTemplate_ID", Integer.valueOf(XX_BudgetFormTemplate_ID));
        
    }
    
    /** Get Budget Form Template.
    @return Template for budget form */
    public int getXX_BudgetFormTemplate_ID() 
    {
        return get_ValueAsInt("XX_BudgetFormTemplate_ID");
        
    }
    
    
}
