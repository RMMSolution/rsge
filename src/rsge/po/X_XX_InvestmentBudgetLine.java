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
/** Generated Model for XX_InvestmentBudgetLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_InvestmentBudgetLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_InvestmentBudgetLine_ID id
    @param trx transaction
    */
    public X_XX_InvestmentBudgetLine (Ctx ctx, int XX_InvestmentBudgetLine_ID, Trx trx)
    {
        super (ctx, XX_InvestmentBudgetLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_InvestmentBudgetLine_ID == 0)
        {
            setA_Asset_Group_ID (0);
            setIsDepreciated (false);	// N
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
            setTotalQty (0);	// 0
            setXX_InvestmentBudgetLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_InvestmentBudgetLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27685548058789L;
    /** Last Updated Timestamp 2014-06-22 14:39:02.0 */
    public static final long updatedMS = 1403422742000L;
    /** AD_Table_ID=1000152 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_InvestmentBudgetLine");
        
    }
    ;
    
    /** TableName=XX_InvestmentBudgetLine */
    public static final String Table_Name="XX_InvestmentBudgetLine";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Asset Group.
    @param A_Asset_Group_ID Group of Assets */
    public void setA_Asset_Group_ID (int A_Asset_Group_ID)
    {
        if (A_Asset_Group_ID < 1) throw new IllegalArgumentException ("A_Asset_Group_ID is mandatory.");
        set_ValueNoCheck ("A_Asset_Group_ID", Integer.valueOf(A_Asset_Group_ID));
        
    }
    
    /** Get Asset Group.
    @return Group of Assets */
    public int getA_Asset_Group_ID() 
    {
        return get_ValueAsInt("A_Asset_Group_ID");
        
    }
    
    /** Set Asset Depreciation.
    @param BudgetAssetDepreciation_Acct Account to record asset''s depreciation */
    public void setBudgetAssetDepreciation_Acct (int BudgetAssetDepreciation_Acct)
    {
        set_Value ("BudgetAssetDepreciation_Acct", Integer.valueOf(BudgetAssetDepreciation_Acct));
        
    }
    
    /** Get Asset Depreciation.
    @return Account to record asset''s depreciation */
    public int getBudgetAssetDepreciation_Acct() 
    {
        return get_ValueAsInt("BudgetAssetDepreciation_Acct");
        
    }
    
    /** Set Depreciate.
    @param IsDepreciated The asset will be depreciated */
    public void setIsDepreciated (boolean IsDepreciated)
    {
        set_Value ("IsDepreciated", Boolean.valueOf(IsDepreciated));
        
    }
    
    /** Get Depreciate.
    @return The asset will be depreciated */
    public boolean isDepreciated() 
    {
        return get_ValueAsBoolean("IsDepreciated");
        
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
    
    /** Set Qty Period 1.
    @param QtyPeriod1 Qty in first period */
    public void setQtyPeriod1 (java.math.BigDecimal QtyPeriod1)
    {
        set_Value ("QtyPeriod1", QtyPeriod1);
        
    }
    
    /** Get Qty Period 1.
    @return Qty in first period */
    public java.math.BigDecimal getQtyPeriod1() 
    {
        return get_ValueAsBigDecimal("QtyPeriod1");
        
    }
    
    /** Set Qty Period 10.
    @param QtyPeriod10 Qty in tenth period */
    public void setQtyPeriod10 (java.math.BigDecimal QtyPeriod10)
    {
        set_Value ("QtyPeriod10", QtyPeriod10);
        
    }
    
    /** Get Qty Period 10.
    @return Qty in tenth period */
    public java.math.BigDecimal getQtyPeriod10() 
    {
        return get_ValueAsBigDecimal("QtyPeriod10");
        
    }
    
    /** Set Qty Period 11.
    @param QtyPeriod11 Qty in eleventh period */
    public void setQtyPeriod11 (java.math.BigDecimal QtyPeriod11)
    {
        set_Value ("QtyPeriod11", QtyPeriod11);
        
    }
    
    /** Get Qty Period 11.
    @return Qty in eleventh period */
    public java.math.BigDecimal getQtyPeriod11() 
    {
        return get_ValueAsBigDecimal("QtyPeriod11");
        
    }
    
    /** Set Qty Period 12.
    @param QtyPeriod12 Qty in twelveth period */
    public void setQtyPeriod12 (java.math.BigDecimal QtyPeriod12)
    {
        set_Value ("QtyPeriod12", QtyPeriod12);
        
    }
    
    /** Get Qty Period 12.
    @return Qty in twelveth period */
    public java.math.BigDecimal getQtyPeriod12() 
    {
        return get_ValueAsBigDecimal("QtyPeriod12");
        
    }
    
    /** Set Qty Period 2.
    @param QtyPeriod2 Qty in second period */
    public void setQtyPeriod2 (java.math.BigDecimal QtyPeriod2)
    {
        set_Value ("QtyPeriod2", QtyPeriod2);
        
    }
    
    /** Get Qty Period 2.
    @return Qty in second period */
    public java.math.BigDecimal getQtyPeriod2() 
    {
        return get_ValueAsBigDecimal("QtyPeriod2");
        
    }
    
    /** Set Qty Period 3.
    @param QtyPeriod3 Qty in third period */
    public void setQtyPeriod3 (java.math.BigDecimal QtyPeriod3)
    {
        set_Value ("QtyPeriod3", QtyPeriod3);
        
    }
    
    /** Get Qty Period 3.
    @return Qty in third period */
    public java.math.BigDecimal getQtyPeriod3() 
    {
        return get_ValueAsBigDecimal("QtyPeriod3");
        
    }
    
    /** Set Qty Period 4.
    @param QtyPeriod4 Qty in forth period */
    public void setQtyPeriod4 (java.math.BigDecimal QtyPeriod4)
    {
        set_Value ("QtyPeriod4", QtyPeriod4);
        
    }
    
    /** Get Qty Period 4.
    @return Qty in forth period */
    public java.math.BigDecimal getQtyPeriod4() 
    {
        return get_ValueAsBigDecimal("QtyPeriod4");
        
    }
    
    /** Set Qty Period 5.
    @param QtyPeriod5 Qty in fifth period */
    public void setQtyPeriod5 (java.math.BigDecimal QtyPeriod5)
    {
        set_Value ("QtyPeriod5", QtyPeriod5);
        
    }
    
    /** Get Qty Period 5.
    @return Qty in fifth period */
    public java.math.BigDecimal getQtyPeriod5() 
    {
        return get_ValueAsBigDecimal("QtyPeriod5");
        
    }
    
    /** Set Qty Period 6.
    @param QtyPeriod6 Qty in sixth period */
    public void setQtyPeriod6 (java.math.BigDecimal QtyPeriod6)
    {
        set_Value ("QtyPeriod6", QtyPeriod6);
        
    }
    
    /** Get Qty Period 6.
    @return Qty in sixth period */
    public java.math.BigDecimal getQtyPeriod6() 
    {
        return get_ValueAsBigDecimal("QtyPeriod6");
        
    }
    
    /** Set Qty Period 7.
    @param QtyPeriod7 Qty in seventh period */
    public void setQtyPeriod7 (java.math.BigDecimal QtyPeriod7)
    {
        set_Value ("QtyPeriod7", QtyPeriod7);
        
    }
    
    /** Get Qty Period 7.
    @return Qty in seventh period */
    public java.math.BigDecimal getQtyPeriod7() 
    {
        return get_ValueAsBigDecimal("QtyPeriod7");
        
    }
    
    /** Set Qty Period 8.
    @param QtyPeriod8 Qty in eight period */
    public void setQtyPeriod8 (java.math.BigDecimal QtyPeriod8)
    {
        set_Value ("QtyPeriod8", QtyPeriod8);
        
    }
    
    /** Get Qty Period 8.
    @return Qty in eight period */
    public java.math.BigDecimal getQtyPeriod8() 
    {
        return get_ValueAsBigDecimal("QtyPeriod8");
        
    }
    
    /** Set Qty Period 9.
    @param QtyPeriod9 Qty in ninth period */
    public void setQtyPeriod9 (java.math.BigDecimal QtyPeriod9)
    {
        set_Value ("QtyPeriod9", QtyPeriod9);
        
    }
    
    /** Get Qty Period 9.
    @return Qty in ninth period */
    public java.math.BigDecimal getQtyPeriod9() 
    {
        return get_ValueAsBigDecimal("QtyPeriod9");
        
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
    
    /** Set Total Quantity.
    @param TotalQty Total Quantity */
    public void setTotalQty (int TotalQty)
    {
        set_Value ("TotalQty", Integer.valueOf(TotalQty));
        
    }
    
    /** Get Total Quantity.
    @return Total Quantity */
    public int getTotalQty() 
    {
        return get_ValueAsInt("TotalQty");
        
    }
    
    /** Set Investment Budget.
    @param XX_InvestmentBudget_ID Investment Budget */
    public void setXX_InvestmentBudget_ID (int XX_InvestmentBudget_ID)
    {
        if (XX_InvestmentBudget_ID <= 0) set_Value ("XX_InvestmentBudget_ID", null);
        else
        set_Value ("XX_InvestmentBudget_ID", Integer.valueOf(XX_InvestmentBudget_ID));
        
    }
    
    /** Get Investment Budget.
    @return Investment Budget */
    public int getXX_InvestmentBudget_ID() 
    {
        return get_ValueAsInt("XX_InvestmentBudget_ID");
        
    }
    
    /** Set Investment Line.
    @param XX_InvestmentBudgetLine_ID Investment line */
    public void setXX_InvestmentBudgetLine_ID (int XX_InvestmentBudgetLine_ID)
    {
        if (XX_InvestmentBudgetLine_ID < 1) throw new IllegalArgumentException ("XX_InvestmentBudgetLine_ID is mandatory.");
        set_ValueNoCheck ("XX_InvestmentBudgetLine_ID", Integer.valueOf(XX_InvestmentBudgetLine_ID));
        
    }
    
    /** Get Investment Line.
    @return Investment line */
    public int getXX_InvestmentBudgetLine_ID() 
    {
        return get_ValueAsInt("XX_InvestmentBudgetLine_ID");
        
    }
    
    
}
