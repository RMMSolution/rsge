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
/** Generated Model for XX_BudgetPlanningLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_BudgetPlanningLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_BudgetPlanningLine_ID id
    @param trx transaction
    */
    public X_XX_BudgetPlanningLine (Ctx ctx, int XX_BudgetPlanningLine_ID, Trx trx)
    {
        super (ctx, XX_BudgetPlanningLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_BudgetPlanningLine_ID == 0)
        {
            setAmtAcctCr (Env.ZERO);	// 0
            setAmtAcctDr (Env.ZERO);	// 0
            setIsGenerated (false);	// N
            setXX_BudgetPlanningLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_BudgetPlanningLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27795199362929L;
    /** Last Updated Timestamp 2017-12-12 17:20:46.14 */
    public static final long updatedMS = 1513074046140L;
    /** AD_Table_ID=1000117 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_BudgetPlanningLine");
        
    }
    ;
    
    /** TableName=XX_BudgetPlanningLine */
    public static final String Table_Name="XX_BudgetPlanningLine";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Account.
    @param Account_ID Account used */
    public void setAccount_ID (int Account_ID)
    {
        if (Account_ID <= 0) set_Value ("Account_ID", null);
        else
        set_Value ("Account_ID", Integer.valueOf(Account_ID));
        
    }
    
    /** Get Account.
    @return Account used */
    public int getAccount_ID() 
    {
        return get_ValueAsInt("Account_ID");
        
    }
    
    /** Set Accounted Credit.
    @param AmtAcctCr Accounted Credit Amount */
    public void setAmtAcctCr (java.math.BigDecimal AmtAcctCr)
    {
        if (AmtAcctCr == null) throw new IllegalArgumentException ("AmtAcctCr is mandatory.");
        set_Value ("AmtAcctCr", AmtAcctCr);
        
    }
    
    /** Get Accounted Credit.
    @return Accounted Credit Amount */
    public java.math.BigDecimal getAmtAcctCr() 
    {
        return get_ValueAsBigDecimal("AmtAcctCr");
        
    }
    
    /** Set Accounted Debit.
    @param AmtAcctDr Accounted Debit Amount */
    public void setAmtAcctDr (java.math.BigDecimal AmtAcctDr)
    {
        if (AmtAcctDr == null) throw new IllegalArgumentException ("AmtAcctDr is mandatory.");
        set_Value ("AmtAcctDr", AmtAcctDr);
        
    }
    
    /** Get Accounted Debit.
    @return Accounted Debit Amount */
    public java.math.BigDecimal getAmtAcctDr() 
    {
        return get_ValueAsBigDecimal("AmtAcctDr");
        
    }
    
    /** Set Activity.
    @param C_Activity_ID Business Activity */
    public void setC_Activity_ID (int C_Activity_ID)
    {
        if (C_Activity_ID <= 0) set_Value ("C_Activity_ID", null);
        else
        set_Value ("C_Activity_ID", Integer.valueOf(C_Activity_ID));
        
    }
    
    /** Get Activity.
    @return Business Activity */
    public int getC_Activity_ID() 
    {
        return get_ValueAsInt("C_Activity_ID");
        
    }
    
    /** Set Campaign.
    @param C_Campaign_ID Marketing Campaign */
    public void setC_Campaign_ID (int C_Campaign_ID)
    {
        if (C_Campaign_ID <= 0) set_Value ("C_Campaign_ID", null);
        else
        set_Value ("C_Campaign_ID", Integer.valueOf(C_Campaign_ID));
        
    }
    
    /** Get Campaign.
    @return Marketing Campaign */
    public int getC_Campaign_ID() 
    {
        return get_ValueAsInt("C_Campaign_ID");
        
    }
    
    /** Set Project.
    @param C_Project_ID Financial Project */
    public void setC_Project_ID (int C_Project_ID)
    {
        if (C_Project_ID <= 0) set_Value ("C_Project_ID", null);
        else
        set_Value ("C_Project_ID", Integer.valueOf(C_Project_ID));
        
    }
    
    /** Get Project.
    @return Financial Project */
    public int getC_Project_ID() 
    {
        return get_ValueAsInt("C_Project_ID");
        
    }
    
    /** Set Sales Region.
    @param C_SalesRegion_ID Sales coverage region */
    public void setC_SalesRegion_ID (int C_SalesRegion_ID)
    {
        if (C_SalesRegion_ID <= 0) set_Value ("C_SalesRegion_ID", null);
        else
        set_Value ("C_SalesRegion_ID", Integer.valueOf(C_SalesRegion_ID));
        
    }
    
    /** Get Sales Region.
    @return Sales coverage region */
    public int getC_SalesRegion_ID() 
    {
        return get_ValueAsInt("C_SalesRegion_ID");
        
    }
    
    /** Set Element_X1.
    @param Element_X1 Element_X1 */
    public void setElement_X1 (int Element_X1)
    {
        set_Value ("Element_X1", Integer.valueOf(Element_X1));
        
    }
    
    /** Get Element_X1.
    @return Element_X1 */
    public int getElement_X1() 
    {
        return get_ValueAsInt("Element_X1");
        
    }
    
    /** Set Element_X2.
    @param Element_X2 Element_X2 */
    public void setElement_X2 (int Element_X2)
    {
        set_Value ("Element_X2", Integer.valueOf(Element_X2));
        
    }
    
    /** Get Element_X2.
    @return Element_X2 */
    public int getElement_X2() 
    {
        return get_ValueAsInt("Element_X2");
        
    }
    
    /** Set Generated.
    @param IsGenerated This Line is generated */
    public void setIsGenerated (boolean IsGenerated)
    {
        set_Value ("IsGenerated", Boolean.valueOf(IsGenerated));
        
    }
    
    /** Get Generated.
    @return This Line is generated */
    public boolean isGenerated() 
    {
        return get_ValueAsBoolean("IsGenerated");
        
    }
    
    /** Set Product.
    @param M_Product_ID Product, Service, Item */
    public void setM_Product_ID (int M_Product_ID)
    {
        if (M_Product_ID <= 0) set_Value ("M_Product_ID", null);
        else
        set_Value ("M_Product_ID", Integer.valueOf(M_Product_ID));
        
    }
    
    /** Get Product.
    @return Product, Service, Item */
    public int getM_Product_ID() 
    {
        return get_ValueAsInt("M_Product_ID");
        
    }
    
    /** Set Quantity.
    @param Qty Quantity */
    public void setQty (java.math.BigDecimal Qty)
    {
        set_Value ("Qty", Qty);
        
    }
    
    /** Get Quantity.
    @return Quantity */
    public java.math.BigDecimal getQty() 
    {
        return get_ValueAsBigDecimal("Qty");
        
    }
    
    /** Set Search Key.
    @param Value Search key for the record in the format required - must be unique */
    public void setValue (String Value)
    {
        set_Value ("Value", Value);
        
    }
    
    /** Get Search Key.
    @return Search key for the record in the format required - must be unique */
    public String getValue() 
    {
        return (String)get_Value("Value");
        
    }
    
    /** Set Budget Plan.
    @param XX_BudgetPlanning_ID Budget plan of organization */
    public void setXX_BudgetPlanning_ID (int XX_BudgetPlanning_ID)
    {
        if (XX_BudgetPlanning_ID <= 0) set_Value ("XX_BudgetPlanning_ID", null);
        else
        set_Value ("XX_BudgetPlanning_ID", Integer.valueOf(XX_BudgetPlanning_ID));
        
    }
    
    /** Get Budget Plan.
    @return Budget plan of organization */
    public int getXX_BudgetPlanning_ID() 
    {
        return get_ValueAsInt("XX_BudgetPlanning_ID");
        
    }
    
    /** Set Plan Line.
    @param XX_BudgetPlanningLine_ID Plan line */
    public void setXX_BudgetPlanningLine_ID (int XX_BudgetPlanningLine_ID)
    {
        if (XX_BudgetPlanningLine_ID < 1) throw new IllegalArgumentException ("XX_BudgetPlanningLine_ID is mandatory.");
        set_ValueNoCheck ("XX_BudgetPlanningLine_ID", Integer.valueOf(XX_BudgetPlanningLine_ID));
        
    }
    
    /** Get Plan Line.
    @return Plan line */
    public int getXX_BudgetPlanningLine_ID() 
    {
        return get_ValueAsInt("XX_BudgetPlanningLine_ID");
        
    }
    
    
}
