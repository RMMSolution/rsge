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
/** Generated Model for XX_BudgetRevenueProduct
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_BudgetRevenueProduct extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_BudgetRevenueProduct_ID id
    @param trx transaction
    */
    public X_XX_BudgetRevenueProduct (Ctx ctx, int XX_BudgetRevenueProduct_ID, Trx trx)
    {
        super (ctx, XX_BudgetRevenueProduct_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_BudgetRevenueProduct_ID == 0)
        {
            setM_Product_ID (0);
            setPeriod1 (Env.ZERO);	// 0
            setPeriod10 (Env.ZERO);	// 0
            setPeriod11 (Env.ZERO);	// 0
            setPeriod12 (Env.ZERO);	// 0
            setPeriod2 (Env.ZERO);	// 0
            setPeriod3 (Env.ZERO);	// 0
            setPeriod4 (Env.ZERO);	// 0
            setPeriod5 (Env.ZERO);	// 0
            setPeriod6 (Env.ZERO);	// 0
            setPeriod7 (Env.ZERO);	// 0
            setPeriod8 (Env.ZERO);	// 0
            setPeriod9 (Env.ZERO);	// 0
            setTotalAmt (Env.ZERO);	// 0
            setXX_BudgetRevenueProduct_ID (0);
            setXX_RevenueBudgetAcct_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_BudgetRevenueProduct (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27728816602360L;
    /** Last Updated Timestamp 2015-11-05 09:41:25.571 */
    public static final long updatedMS = 1446691285571L;
    /** AD_Table_ID=1008781 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_BudgetRevenueProduct");
        
    }
    ;
    
    /** TableName=XX_BudgetRevenueProduct */
    public static final String Table_Name="XX_BudgetRevenueProduct";
    
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
    @param M_Product_ID Product, Service, Item */
    public void setM_Product_ID (int M_Product_ID)
    {
        if (M_Product_ID < 1) throw new IllegalArgumentException ("M_Product_ID is mandatory.");
        set_Value ("M_Product_ID", Integer.valueOf(M_Product_ID));
        
    }
    
    /** Get Product.
    @return Product, Service, Item */
    public int getM_Product_ID() 
    {
        return get_ValueAsInt("M_Product_ID");
        
    }
    
    /** Set 1st Period.
    @param Period1 First period budget amount */
    public void setPeriod1 (java.math.BigDecimal Period1)
    {
        if (Period1 == null) throw new IllegalArgumentException ("Period1 is mandatory.");
        set_Value ("Period1", Period1);
        
    }
    
    /** Get 1st Period.
    @return First period budget amount */
    public java.math.BigDecimal getPeriod1() 
    {
        return get_ValueAsBigDecimal("Period1");
        
    }
    
    /** Set 10th Period.
    @param Period10 Tenth period budget amount */
    public void setPeriod10 (java.math.BigDecimal Period10)
    {
        if (Period10 == null) throw new IllegalArgumentException ("Period10 is mandatory.");
        set_Value ("Period10", Period10);
        
    }
    
    /** Get 10th Period.
    @return Tenth period budget amount */
    public java.math.BigDecimal getPeriod10() 
    {
        return get_ValueAsBigDecimal("Period10");
        
    }
    
    /** Set 11th Period.
    @param Period11 Eleventh period budget amount */
    public void setPeriod11 (java.math.BigDecimal Period11)
    {
        if (Period11 == null) throw new IllegalArgumentException ("Period11 is mandatory.");
        set_Value ("Period11", Period11);
        
    }
    
    /** Get 11th Period.
    @return Eleventh period budget amount */
    public java.math.BigDecimal getPeriod11() 
    {
        return get_ValueAsBigDecimal("Period11");
        
    }
    
    /** Set 12th Period.
    @param Period12 Twelveth period budget amount */
    public void setPeriod12 (java.math.BigDecimal Period12)
    {
        if (Period12 == null) throw new IllegalArgumentException ("Period12 is mandatory.");
        set_Value ("Period12", Period12);
        
    }
    
    /** Get 12th Period.
    @return Twelveth period budget amount */
    public java.math.BigDecimal getPeriod12() 
    {
        return get_ValueAsBigDecimal("Period12");
        
    }
    
    /** Set 2nd Period.
    @param Period2 Second period budget amount */
    public void setPeriod2 (java.math.BigDecimal Period2)
    {
        if (Period2 == null) throw new IllegalArgumentException ("Period2 is mandatory.");
        set_Value ("Period2", Period2);
        
    }
    
    /** Get 2nd Period.
    @return Second period budget amount */
    public java.math.BigDecimal getPeriod2() 
    {
        return get_ValueAsBigDecimal("Period2");
        
    }
    
    /** Set 3rd Period.
    @param Period3 Third period budget amount */
    public void setPeriod3 (java.math.BigDecimal Period3)
    {
        if (Period3 == null) throw new IllegalArgumentException ("Period3 is mandatory.");
        set_Value ("Period3", Period3);
        
    }
    
    /** Get 3rd Period.
    @return Third period budget amount */
    public java.math.BigDecimal getPeriod3() 
    {
        return get_ValueAsBigDecimal("Period3");
        
    }
    
    /** Set 4th Period.
    @param Period4 Fourth period budget amount */
    public void setPeriod4 (java.math.BigDecimal Period4)
    {
        if (Period4 == null) throw new IllegalArgumentException ("Period4 is mandatory.");
        set_Value ("Period4", Period4);
        
    }
    
    /** Get 4th Period.
    @return Fourth period budget amount */
    public java.math.BigDecimal getPeriod4() 
    {
        return get_ValueAsBigDecimal("Period4");
        
    }
    
    /** Set 5th Period.
    @param Period5 Fifth period budget amount */
    public void setPeriod5 (java.math.BigDecimal Period5)
    {
        if (Period5 == null) throw new IllegalArgumentException ("Period5 is mandatory.");
        set_Value ("Period5", Period5);
        
    }
    
    /** Get 5th Period.
    @return Fifth period budget amount */
    public java.math.BigDecimal getPeriod5() 
    {
        return get_ValueAsBigDecimal("Period5");
        
    }
    
    /** Set 6th Period.
    @param Period6 Sixth period budget amount */
    public void setPeriod6 (java.math.BigDecimal Period6)
    {
        if (Period6 == null) throw new IllegalArgumentException ("Period6 is mandatory.");
        set_Value ("Period6", Period6);
        
    }
    
    /** Get 6th Period.
    @return Sixth period budget amount */
    public java.math.BigDecimal getPeriod6() 
    {
        return get_ValueAsBigDecimal("Period6");
        
    }
    
    /** Set 7th Period.
    @param Period7 Seventh period budget amount */
    public void setPeriod7 (java.math.BigDecimal Period7)
    {
        if (Period7 == null) throw new IllegalArgumentException ("Period7 is mandatory.");
        set_Value ("Period7", Period7);
        
    }
    
    /** Get 7th Period.
    @return Seventh period budget amount */
    public java.math.BigDecimal getPeriod7() 
    {
        return get_ValueAsBigDecimal("Period7");
        
    }
    
    /** Set 8th Period.
    @param Period8 Eighth period budget amount */
    public void setPeriod8 (java.math.BigDecimal Period8)
    {
        if (Period8 == null) throw new IllegalArgumentException ("Period8 is mandatory.");
        set_Value ("Period8", Period8);
        
    }
    
    /** Get 8th Period.
    @return Eighth period budget amount */
    public java.math.BigDecimal getPeriod8() 
    {
        return get_ValueAsBigDecimal("Period8");
        
    }
    
    /** Set 9th Period.
    @param Period9 Ninth period budget amount */
    public void setPeriod9 (java.math.BigDecimal Period9)
    {
        if (Period9 == null) throw new IllegalArgumentException ("Period9 is mandatory.");
        set_Value ("Period9", Period9);
        
    }
    
    /** Get 9th Period.
    @return Ninth period budget amount */
    public java.math.BigDecimal getPeriod9() 
    {
        return get_ValueAsBigDecimal("Period9");
        
    }
    
    /** Set Total Amount.
    @param TotalAmt Total Amount */
    public void setTotalAmt (java.math.BigDecimal TotalAmt)
    {
        if (TotalAmt == null) throw new IllegalArgumentException ("TotalAmt is mandatory.");
        set_Value ("TotalAmt", TotalAmt);
        
    }
    
    /** Get Total Amount.
    @return Total Amount */
    public java.math.BigDecimal getTotalAmt() 
    {
        return get_ValueAsBigDecimal("TotalAmt");
        
    }
    
    /** Set Budget Product.
    @param XX_BudgetRevenueProduct_ID Budget product */
    public void setXX_BudgetRevenueProduct_ID (int XX_BudgetRevenueProduct_ID)
    {
        if (XX_BudgetRevenueProduct_ID < 1) throw new IllegalArgumentException ("XX_BudgetRevenueProduct_ID is mandatory.");
        set_ValueNoCheck ("XX_BudgetRevenueProduct_ID", Integer.valueOf(XX_BudgetRevenueProduct_ID));
        
    }
    
    /** Get Budget Product.
    @return Budget product */
    public int getXX_BudgetRevenueProduct_ID() 
    {
        return get_ValueAsInt("XX_BudgetRevenueProduct_ID");
        
    }
    
    /** Set Revenue Account.
    @param XX_RevenueBudgetAcct_ID Revenue Account */
    public void setXX_RevenueBudgetAcct_ID (int XX_RevenueBudgetAcct_ID)
    {
        if (XX_RevenueBudgetAcct_ID < 1) throw new IllegalArgumentException ("XX_RevenueBudgetAcct_ID is mandatory.");
        set_Value ("XX_RevenueBudgetAcct_ID", Integer.valueOf(XX_RevenueBudgetAcct_ID));
        
    }
    
    /** Get Revenue Account.
    @return Revenue Account */
    public int getXX_RevenueBudgetAcct_ID() 
    {
        return get_ValueAsInt("XX_RevenueBudgetAcct_ID");
        
    }
    
    
}
