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
/** Generated Model for XX_AnnualFinRecord
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_AnnualFinRecord extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_AnnualFinRecord_ID id
    @param trx transaction
    */
    public X_XX_AnnualFinRecord (Ctx ctx, int XX_AnnualFinRecord_ID, Trx trx)
    {
        super (ctx, XX_AnnualFinRecord_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_AnnualFinRecord_ID == 0)
        {
            setC_AcctSchema_ID (0);
            setXX_AnnualFinRecord_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_AnnualFinRecord (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27770883454317L;
    /** Last Updated Timestamp 2017-03-06 06:55:37.528 */
    public static final long updatedMS = 1488758137528L;
    /** AD_Table_ID=1000109 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_AnnualFinRecord");
        
    }
    ;
    
    /** TableName=XX_AnnualFinRecord */
    public static final String Table_Name="XX_AnnualFinRecord";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Accounting Schema.
    @param C_AcctSchema_ID Rules for accounting */
    public void setC_AcctSchema_ID (int C_AcctSchema_ID)
    {
        if (C_AcctSchema_ID < 1) throw new IllegalArgumentException ("C_AcctSchema_ID is mandatory.");
        set_Value ("C_AcctSchema_ID", Integer.valueOf(C_AcctSchema_ID));
        
    }
    
    /** Get Accounting Schema.
    @return Rules for accounting */
    public int getC_AcctSchema_ID() 
    {
        return get_ValueAsInt("C_AcctSchema_ID");
        
    }
    
    /** Set Currency.
    @param C_Currency_ID The Currency for this record */
    public void setC_Currency_ID (int C_Currency_ID)
    {
        if (C_Currency_ID <= 0) set_Value ("C_Currency_ID", null);
        else
        set_Value ("C_Currency_ID", Integer.valueOf(C_Currency_ID));
        
    }
    
    /** Get Currency.
    @return The Currency for this record */
    public int getC_Currency_ID() 
    {
        return get_ValueAsInt("C_Currency_ID");
        
    }
    
    /** Set Year.
    @param C_Year_ID Calendar Year */
    public void setC_Year_ID (int C_Year_ID)
    {
        if (C_Year_ID <= 0) set_Value ("C_Year_ID", null);
        else
        set_Value ("C_Year_ID", Integer.valueOf(C_Year_ID));
        
    }
    
    /** Get Year.
    @return Calendar Year */
    public int getC_Year_ID() 
    {
        return get_ValueAsInt("C_Year_ID");
        
    }
    
    /** Set Current Asset.
    @param CurrentAssetAmt Current asset amount */
    public void setCurrentAssetAmt (java.math.BigDecimal CurrentAssetAmt)
    {
        set_Value ("CurrentAssetAmt", CurrentAssetAmt);
        
    }
    
    /** Get Current Asset.
    @return Current asset amount */
    public java.math.BigDecimal getCurrentAssetAmt() 
    {
        return get_ValueAsBigDecimal("CurrentAssetAmt");
        
    }
    
    /** Set Current Liability.
    @param CurrentLiabilityAmt Current liability amount */
    public void setCurrentLiabilityAmt (java.math.BigDecimal CurrentLiabilityAmt)
    {
        set_Value ("CurrentLiabilityAmt", CurrentLiabilityAmt);
        
    }
    
    /** Get Current Liability.
    @return Current liability amount */
    public java.math.BigDecimal getCurrentLiabilityAmt() 
    {
        return get_ValueAsBigDecimal("CurrentLiabilityAmt");
        
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
    
    /** Set Earning After Tax.
    @param EATAmt Earnint after tax */
    public void setEATAmt (java.math.BigDecimal EATAmt)
    {
        set_Value ("EATAmt", EATAmt);
        
    }
    
    /** Get Earning After Tax.
    @return Earnint after tax */
    public java.math.BigDecimal getEATAmt() 
    {
        return get_ValueAsBigDecimal("EATAmt");
        
    }
    
    /** Set EBIT.
    @param EBITAmt Earning before Interest, Tax amount */
    public void setEBITAmt (java.math.BigDecimal EBITAmt)
    {
        set_Value ("EBITAmt", EBITAmt);
        
    }
    
    /** Get EBIT.
    @return Earning before Interest, Tax amount */
    public java.math.BigDecimal getEBITAmt() 
    {
        return get_ValueAsBigDecimal("EBITAmt");
        
    }
    
    /** Set EBITDA.
    @param EBITDAAmt Earning Before Interes, Tax, Depreciation and Amortization amount */
    public void setEBITDAAmt (java.math.BigDecimal EBITDAAmt)
    {
        set_Value ("EBITDAAmt", EBITDAAmt);
        
    }
    
    /** Get EBITDA.
    @return Earning Before Interes, Tax, Depreciation and Amortization amount */
    public java.math.BigDecimal getEBITDAAmt() 
    {
        return get_ValueAsBigDecimal("EBITDAAmt");
        
    }
    
    /** Set Gross Profit.
    @param GrossProfitAmt Gross profit amount */
    public void setGrossProfitAmt (java.math.BigDecimal GrossProfitAmt)
    {
        set_Value ("GrossProfitAmt", GrossProfitAmt);
        
    }
    
    /** Get Gross Profit.
    @return Gross profit amount */
    public java.math.BigDecimal getGrossProfitAmt() 
    {
        return get_ValueAsBigDecimal("GrossProfitAmt");
        
    }
    
    /** Set Operating Profit.
    @param OperatingProfitAmt Operating profit amount */
    public void setOperatingProfitAmt (java.math.BigDecimal OperatingProfitAmt)
    {
        set_Value ("OperatingProfitAmt", OperatingProfitAmt);
        
    }
    
    /** Get Operating Profit.
    @return Operating profit amount */
    public java.math.BigDecimal getOperatingProfitAmt() 
    {
        return get_ValueAsBigDecimal("OperatingProfitAmt");
        
    }
    
    /** Set Total Asset.
    @param TotalAssetAmt Total asset amount */
    public void setTotalAssetAmt (java.math.BigDecimal TotalAssetAmt)
    {
        set_Value ("TotalAssetAmt", TotalAssetAmt);
        
    }
    
    /** Get Total Asset.
    @return Total asset amount */
    public java.math.BigDecimal getTotalAssetAmt() 
    {
        return get_ValueAsBigDecimal("TotalAssetAmt");
        
    }
    
    /** Set Total COGM.
    @param TotalCOGMAmt Total cost of goods manufactured amount */
    public void setTotalCOGMAmt (java.math.BigDecimal TotalCOGMAmt)
    {
        set_Value ("TotalCOGMAmt", TotalCOGMAmt);
        
    }
    
    /** Get Total COGM.
    @return Total cost of goods manufactured amount */
    public java.math.BigDecimal getTotalCOGMAmt() 
    {
        return get_ValueAsBigDecimal("TotalCOGMAmt");
        
    }
    
    /** Set Total COGS.
    @param TotalCOGSAmt Total Cost of Goods Sold amount */
    public void setTotalCOGSAmt (java.math.BigDecimal TotalCOGSAmt)
    {
        set_Value ("TotalCOGSAmt", TotalCOGSAmt);
        
    }
    
    /** Get Total COGS.
    @return Total Cost of Goods Sold amount */
    public java.math.BigDecimal getTotalCOGSAmt() 
    {
        return get_ValueAsBigDecimal("TotalCOGSAmt");
        
    }
    
    /** Set Total Equity.
    @param TotalEquityAmt Total equity amount */
    public void setTotalEquityAmt (java.math.BigDecimal TotalEquityAmt)
    {
        set_Value ("TotalEquityAmt", TotalEquityAmt);
        
    }
    
    /** Get Total Equity.
    @return Total equity amount */
    public java.math.BigDecimal getTotalEquityAmt() 
    {
        return get_ValueAsBigDecimal("TotalEquityAmt");
        
    }
    
    /** Set Total Expense.
    @param TotalExpenseAmt Total expense amount */
    public void setTotalExpenseAmt (java.math.BigDecimal TotalExpenseAmt)
    {
        set_Value ("TotalExpenseAmt", TotalExpenseAmt);
        
    }
    
    /** Get Total Expense.
    @return Total expense amount */
    public java.math.BigDecimal getTotalExpenseAmt() 
    {
        return get_ValueAsBigDecimal("TotalExpenseAmt");
        
    }
    
    /** Set Total Liability.
    @param TotalLiabilityAmt Total liability amount */
    public void setTotalLiabilityAmt (java.math.BigDecimal TotalLiabilityAmt)
    {
        set_Value ("TotalLiabilityAmt", TotalLiabilityAmt);
        
    }
    
    /** Get Total Liability.
    @return Total liability amount */
    public java.math.BigDecimal getTotalLiabilityAmt() 
    {
        return get_ValueAsBigDecimal("TotalLiabilityAmt");
        
    }
    
    /** Set Total Revenue.
    @param TotalRevenuAmt Total Revenue Amount */
    public void setTotalRevenuAmt (java.math.BigDecimal TotalRevenuAmt)
    {
        set_Value ("TotalRevenuAmt", TotalRevenuAmt);
        
    }
    
    /** Get Total Revenue.
    @return Total Revenue Amount */
    public java.math.BigDecimal getTotalRevenuAmt() 
    {
        return get_ValueAsBigDecimal("TotalRevenuAmt");
        
    }
    
    /** Set Total Revenue.
    @param TotalRevenueAmt Total revenue amount */
    public void setTotalRevenueAmt (java.math.BigDecimal TotalRevenueAmt)
    {
        set_Value ("TotalRevenueAmt", TotalRevenueAmt);
        
    }
    
    /** Get Total Revenue.
    @return Total revenue amount */
    public java.math.BigDecimal getTotalRevenueAmt() 
    {
        return get_ValueAsBigDecimal("TotalRevenueAmt");
        
    }
    
    /** Set Annual Financial Record.
    @param XX_AnnualFinRecord_ID Annual Financial Record */
    public void setXX_AnnualFinRecord_ID (int XX_AnnualFinRecord_ID)
    {
        if (XX_AnnualFinRecord_ID < 1) throw new IllegalArgumentException ("XX_AnnualFinRecord_ID is mandatory.");
        set_ValueNoCheck ("XX_AnnualFinRecord_ID", Integer.valueOf(XX_AnnualFinRecord_ID));
        
    }
    
    /** Get Annual Financial Record.
    @return Annual Financial Record */
    public int getXX_AnnualFinRecord_ID() 
    {
        return get_ValueAsInt("XX_AnnualFinRecord_ID");
        
    }
    
    
}
