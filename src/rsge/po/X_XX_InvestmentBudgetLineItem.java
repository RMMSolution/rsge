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
/** Generated Model for XX_InvestmentBudgetLineItem
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_InvestmentBudgetLineItem extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_InvestmentBudgetLineItem_ID id
    @param trx transaction
    */
    public X_XX_InvestmentBudgetLineItem (Ctx ctx, int XX_InvestmentBudgetLineItem_ID, Trx trx)
    {
        super (ctx, XX_InvestmentBudgetLineItem_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_InvestmentBudgetLineItem_ID == 0)
        {
            setC_Currency_ID (0);	// @SQL=SELECT C_Currency_ID FROM C_AcctSchema WHERE C_AcctSchema_ID = (SELECT C_AcctSchema_ID FROM XX_BudgetInfo WHERE AD_Client_ID = @#AD_Client_ID@)
            setConvertedAmt (Env.ZERO);	// 0
            setName (null);
            setPeriod1 (false);	// N
            setPeriod10 (false);	// N
            setPeriod11 (false);	// N
            setPeriod12 (false);	// N
            setPeriod2 (false);	// N
            setPeriod3 (false);	// N
            setPeriod4 (false);	// N
            setPeriod5 (false);	// N
            setPeriod6 (false);	// N
            setPeriod7 (false);	// N
            setPeriod8 (false);	// N
            setPeriod9 (false);	// N
            setPriceEntered (Env.ZERO);	// 0
            setQty (0);	// 1
            setTotalAmt (Env.ZERO);	// 0
            setXX_InvestmentBudgetLineItem_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_InvestmentBudgetLineItem (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27683092689789L;
    /** Last Updated Timestamp 2014-05-25 04:36:13.0 */
    public static final long updatedMS = 1400967373000L;
    /** AD_Table_ID=1000153 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_InvestmentBudgetLineItem");
        
    }
    ;
    
    /** TableName=XX_InvestmentBudgetLineItem */
    public static final String Table_Name="XX_InvestmentBudgetLineItem";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Currency.
    @param C_Currency_ID The Currency for this record */
    public void setC_Currency_ID (int C_Currency_ID)
    {
        if (C_Currency_ID < 1) throw new IllegalArgumentException ("C_Currency_ID is mandatory.");
        set_Value ("C_Currency_ID", Integer.valueOf(C_Currency_ID));
        
    }
    
    /** Get Currency.
    @return The Currency for this record */
    public int getC_Currency_ID() 
    {
        return get_ValueAsInt("C_Currency_ID");
        
    }
    
    /** Set Converted Amount.
    @param ConvertedAmt Converted Amount */
    public void setConvertedAmt (java.math.BigDecimal ConvertedAmt)
    {
        if (ConvertedAmt == null) throw new IllegalArgumentException ("ConvertedAmt is mandatory.");
        set_Value ("ConvertedAmt", ConvertedAmt);
        
    }
    
    /** Get Converted Amount.
    @return Converted Amount */
    public java.math.BigDecimal getConvertedAmt() 
    {
        return get_ValueAsBigDecimal("ConvertedAmt");
        
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
    
    /** Set 1st Period.
    @param Period1 First period budget amount */
    public void setPeriod1 (boolean Period1)
    {
        set_Value ("Period1", Boolean.valueOf(Period1));
        
    }
    
    /** Get 1st Period.
    @return First period budget amount */
    public boolean isPeriod1() 
    {
        return get_ValueAsBoolean("Period1");
        
    }
    
    /** Set 10th Period.
    @param Period10 Tenth period budget amount */
    public void setPeriod10 (boolean Period10)
    {
        set_Value ("Period10", Boolean.valueOf(Period10));
        
    }
    
    /** Get 10th Period.
    @return Tenth period budget amount */
    public boolean isPeriod10() 
    {
        return get_ValueAsBoolean("Period10");
        
    }
    
    /** Set 11th Period.
    @param Period11 Eleventh period budget amount */
    public void setPeriod11 (boolean Period11)
    {
        set_Value ("Period11", Boolean.valueOf(Period11));
        
    }
    
    /** Get 11th Period.
    @return Eleventh period budget amount */
    public boolean isPeriod11() 
    {
        return get_ValueAsBoolean("Period11");
        
    }
    
    /** Set 12th Period.
    @param Period12 Twelveth period budget amount */
    public void setPeriod12 (boolean Period12)
    {
        set_Value ("Period12", Boolean.valueOf(Period12));
        
    }
    
    /** Get 12th Period.
    @return Twelveth period budget amount */
    public boolean isPeriod12() 
    {
        return get_ValueAsBoolean("Period12");
        
    }
    
    /** Set 2nd Period.
    @param Period2 Second period budget amount */
    public void setPeriod2 (boolean Period2)
    {
        set_Value ("Period2", Boolean.valueOf(Period2));
        
    }
    
    /** Get 2nd Period.
    @return Second period budget amount */
    public boolean isPeriod2() 
    {
        return get_ValueAsBoolean("Period2");
        
    }
    
    /** Set 3rd Period.
    @param Period3 Third period budget amount */
    public void setPeriod3 (boolean Period3)
    {
        set_Value ("Period3", Boolean.valueOf(Period3));
        
    }
    
    /** Get 3rd Period.
    @return Third period budget amount */
    public boolean isPeriod3() 
    {
        return get_ValueAsBoolean("Period3");
        
    }
    
    /** Set 4th Period.
    @param Period4 Fourth period budget amount */
    public void setPeriod4 (boolean Period4)
    {
        set_Value ("Period4", Boolean.valueOf(Period4));
        
    }
    
    /** Get 4th Period.
    @return Fourth period budget amount */
    public boolean isPeriod4() 
    {
        return get_ValueAsBoolean("Period4");
        
    }
    
    /** Set 5th Period.
    @param Period5 Fifth period budget amount */
    public void setPeriod5 (boolean Period5)
    {
        set_Value ("Period5", Boolean.valueOf(Period5));
        
    }
    
    /** Get 5th Period.
    @return Fifth period budget amount */
    public boolean isPeriod5() 
    {
        return get_ValueAsBoolean("Period5");
        
    }
    
    /** Set 6th Period.
    @param Period6 Sixth period budget amount */
    public void setPeriod6 (boolean Period6)
    {
        set_Value ("Period6", Boolean.valueOf(Period6));
        
    }
    
    /** Get 6th Period.
    @return Sixth period budget amount */
    public boolean isPeriod6() 
    {
        return get_ValueAsBoolean("Period6");
        
    }
    
    /** Set 7th Period.
    @param Period7 Seventh period budget amount */
    public void setPeriod7 (boolean Period7)
    {
        set_Value ("Period7", Boolean.valueOf(Period7));
        
    }
    
    /** Get 7th Period.
    @return Seventh period budget amount */
    public boolean isPeriod7() 
    {
        return get_ValueAsBoolean("Period7");
        
    }
    
    /** Set 8th Period.
    @param Period8 Eighth period budget amount */
    public void setPeriod8 (boolean Period8)
    {
        set_Value ("Period8", Boolean.valueOf(Period8));
        
    }
    
    /** Get 8th Period.
    @return Eighth period budget amount */
    public boolean isPeriod8() 
    {
        return get_ValueAsBoolean("Period8");
        
    }
    
    /** Set 9th Period.
    @param Period9 Ninth period budget amount */
    public void setPeriod9 (boolean Period9)
    {
        set_Value ("Period9", Boolean.valueOf(Period9));
        
    }
    
    /** Get 9th Period.
    @return Ninth period budget amount */
    public boolean isPeriod9() 
    {
        return get_ValueAsBoolean("Period9");
        
    }
    
    /** Set Price.
    @param PriceEntered Price Entered - the price based on the selected/base UoM */
    public void setPriceEntered (java.math.BigDecimal PriceEntered)
    {
        if (PriceEntered == null) throw new IllegalArgumentException ("PriceEntered is mandatory.");
        set_Value ("PriceEntered", PriceEntered);
        
    }
    
    /** Get Price.
    @return Price Entered - the price based on the selected/base UoM */
    public java.math.BigDecimal getPriceEntered() 
    {
        return get_ValueAsBigDecimal("PriceEntered");
        
    }
    
    /** Set Quantity.
    @param Qty Quantity */
    public void setQty (int Qty)
    {
        set_Value ("Qty", Integer.valueOf(Qty));
        
    }
    
    /** Get Quantity.
    @return Quantity */
    public int getQty() 
    {
        return get_ValueAsInt("Qty");
        
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
    
    /** Set Investment Line.
    @param XX_InvestmentBudgetLine_ID Investment line */
    public void setXX_InvestmentBudgetLine_ID (int XX_InvestmentBudgetLine_ID)
    {
        if (XX_InvestmentBudgetLine_ID <= 0) set_Value ("XX_InvestmentBudgetLine_ID", null);
        else
        set_Value ("XX_InvestmentBudgetLine_ID", Integer.valueOf(XX_InvestmentBudgetLine_ID));
        
    }
    
    /** Get Investment Line.
    @return Investment line */
    public int getXX_InvestmentBudgetLine_ID() 
    {
        return get_ValueAsInt("XX_InvestmentBudgetLine_ID");
        
    }
    
    /** Set Investment Line Item.
    @param XX_InvestmentBudgetLineItem_ID Investment line item */
    public void setXX_InvestmentBudgetLineItem_ID (int XX_InvestmentBudgetLineItem_ID)
    {
        if (XX_InvestmentBudgetLineItem_ID < 1) throw new IllegalArgumentException ("XX_InvestmentBudgetLineItem_ID is mandatory.");
        set_ValueNoCheck ("XX_InvestmentBudgetLineItem_ID", Integer.valueOf(XX_InvestmentBudgetLineItem_ID));
        
    }
    
    /** Get Investment Line Item.
    @return Investment line item */
    public int getXX_InvestmentBudgetLineItem_ID() 
    {
        return get_ValueAsInt("XX_InvestmentBudgetLineItem_ID");
        
    }
    
    
}
