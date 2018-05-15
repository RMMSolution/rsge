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
/** Generated Model for T_StatementOfCashFlow
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_T_StatementOfCashFlow extends PO
{
    /** Standard Constructor
    @param ctx context
    @param T_StatementOfCashFlow_ID id
    @param trx transaction
    */
    public X_T_StatementOfCashFlow (Ctx ctx, int T_StatementOfCashFlow_ID, Trx trx)
    {
        super (ctx, T_StatementOfCashFlow_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (T_StatementOfCashFlow_ID == 0)
        {
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_T_StatementOfCashFlow (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27796054415175L;
    /** Last Updated Timestamp 2017-12-22 14:51:38.386 */
    public static final long updatedMS = 1513929098386L;
    /** AD_Table_ID=1000103 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("T_StatementOfCashFlow");
        
    }
    ;
    
    /** TableName=T_StatementOfCashFlow */
    public static final String Table_Name="T_StatementOfCashFlow";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Growth (%) .
    @param AnnualGrowth Growth in percentage */
    public void setAnnualGrowth (java.math.BigDecimal AnnualGrowth)
    {
        set_Value ("AnnualGrowth", AnnualGrowth);
        
    }
    
    /** Get Growth (%) .
    @return Growth in percentage */
    public java.math.BigDecimal getAnnualGrowth() 
    {
        return get_ValueAsBigDecimal("AnnualGrowth");
        
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
    
    /** Set Period.
    @param C_Period_ID Period of the Calendar */
    public void setC_Period_ID (int C_Period_ID)
    {
        if (C_Period_ID <= 0) set_Value ("C_Period_ID", null);
        else
        set_Value ("C_Period_ID", Integer.valueOf(C_Period_ID));
        
    }
    
    /** Get Period.
    @return Period of the Calendar */
    public int getC_Period_ID() 
    {
        return get_ValueAsInt("C_Period_ID");
        
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
    
    /** Direct = D */
    public static final String CASHFLOWMETHOD_Direct = X_Ref_Cash_Flow_Method.DIRECT.getValue();
    /** Indirect = I */
    public static final String CASHFLOWMETHOD_Indirect = X_Ref_Cash_Flow_Method.INDIRECT.getValue();
    /** Set Cash Flow Method.
    @param CashFlowMethod Cash Flow Method */
    public void setCashFlowMethod (String CashFlowMethod)
    {
        if (!X_Ref_Cash_Flow_Method.isValid(CashFlowMethod))
        throw new IllegalArgumentException ("CashFlowMethod Invalid value - " + CashFlowMethod + " - Reference_ID=1000130 - D - I");
        set_Value ("CashFlowMethod", CashFlowMethod);
        
    }
    
    /** Get Cash Flow Method.
    @return Cash Flow Method */
    public String getCashFlowMethod() 
    {
        return (String)get_Value("CashFlowMethod");
        
    }
    
    /** Set Col_0.
    @param Col_0 Col_0 */
    public void setCol_0 (java.math.BigDecimal Col_0)
    {
        set_Value ("Col_0", Col_0);
        
    }
    
    /** Get Col_0.
    @return Col_0 */
    public java.math.BigDecimal getCol_0() 
    {
        return get_ValueAsBigDecimal("Col_0");
        
    }
    
    /** Set Col_1.
    @param Col_1 Col_1 */
    public void setCol_1 (java.math.BigDecimal Col_1)
    {
        set_Value ("Col_1", Col_1);
        
    }
    
    /** Get Col_1.
    @return Col_1 */
    public java.math.BigDecimal getCol_1() 
    {
        return get_ValueAsBigDecimal("Col_1");
        
    }
    
    /** Set Col_2.
    @param Col_2 Col_2 */
    public void setCol_2 (java.math.BigDecimal Col_2)
    {
        set_Value ("Col_2", Col_2);
        
    }
    
    /** Get Col_2.
    @return Col_2 */
    public java.math.BigDecimal getCol_2() 
    {
        return get_ValueAsBigDecimal("Col_2");
        
    }
    
    /** Set Col_3.
    @param Col_3 Col_3 */
    public void setCol_3 (java.math.BigDecimal Col_3)
    {
        set_Value ("Col_3", Col_3);
        
    }
    
    /** Get Col_3.
    @return Col_3 */
    public java.math.BigDecimal getCol_3() 
    {
        return get_ValueAsBigDecimal("Col_3");
        
    }
    
    /** Set Col_4.
    @param Col_4 Col_4 */
    public void setCol_4 (java.math.BigDecimal Col_4)
    {
        set_Value ("Col_4", Col_4);
        
    }
    
    /** Get Col_4.
    @return Col_4 */
    public java.math.BigDecimal getCol_4() 
    {
        return get_ValueAsBigDecimal("Col_4");
        
    }
    
    /** Set Achievement (%).
    @param CurrentAchievement Achievement in percentage */
    public void setCurrentAchievement (java.math.BigDecimal CurrentAchievement)
    {
        set_Value ("CurrentAchievement", CurrentAchievement);
        
    }
    
    /** Get Achievement (%).
    @return Achievement in percentage */
    public java.math.BigDecimal getCurrentAchievement() 
    {
        return get_ValueAsBigDecimal("CurrentAchievement");
        
    }
    
    /** Set Current Month (Act).
    @param CurrentMonthAct Current month (actual) */
    public void setCurrentMonthAct (java.math.BigDecimal CurrentMonthAct)
    {
        set_Value ("CurrentMonthAct", CurrentMonthAct);
        
    }
    
    /** Get Current Month (Act).
    @return Current month (actual) */
    public java.math.BigDecimal getCurrentMonthAct() 
    {
        return get_ValueAsBigDecimal("CurrentMonthAct");
        
    }
    
    /** Set Current Budget.
    @param CurrentPeriodBudget Current budget */
    public void setCurrentPeriodBudget (java.math.BigDecimal CurrentPeriodBudget)
    {
        set_Value ("CurrentPeriodBudget", CurrentPeriodBudget);
        
    }
    
    /** Get Current Budget.
    @return Current budget */
    public java.math.BigDecimal getCurrentPeriodBudget() 
    {
        return get_ValueAsBigDecimal("CurrentPeriodBudget");
        
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
    
    /** Set Growth (%).
    @param MonthGrowth Growth in percentage */
    public void setMonthGrowth (int MonthGrowth)
    {
        set_Value ("MonthGrowth", Integer.valueOf(MonthGrowth));
        
    }
    
    /** Get Growth (%).
    @return Growth in percentage */
    public int getMonthGrowth() 
    {
        return get_ValueAsInt("MonthGrowth");
        
    }
    
    /** Set Prev Year (Act).
    @param PrevYearAct Prev Year (Act) */
    public void setPrevYearAct (java.math.BigDecimal PrevYearAct)
    {
        set_Value ("PrevYearAct", PrevYearAct);
        
    }
    
    /** Get Prev Year (Act).
    @return Prev Year (Act) */
    public java.math.BigDecimal getPrevYearAct() 
    {
        return get_ValueAsBigDecimal("PrevYearAct");
        
    }
    
    /** Set YTD Achievement (%).
    @param YTDAchievement Year to date achievement in percentage */
    public void setYTDAchievement (java.math.BigDecimal YTDAchievement)
    {
        set_Value ("YTDAchievement", YTDAchievement);
        
    }
    
    /** Get YTD Achievement (%).
    @return Year to date achievement in percentage */
    public java.math.BigDecimal getYTDAchievement() 
    {
        return get_ValueAsBigDecimal("YTDAchievement");
        
    }
    
    /** Set YTD Budget.
    @param YTDBudget Budget year to date */
    public void setYTDBudget (java.math.BigDecimal YTDBudget)
    {
        set_Value ("YTDBudget", YTDBudget);
        
    }
    
    /** Get YTD Budget.
    @return Budget year to date */
    public java.math.BigDecimal getYTDBudget() 
    {
        return get_ValueAsBigDecimal("YTDBudget");
        
    }
    
    /** Set Current YTD.
    @param YTDCurrentAct Current Year to Date */
    public void setYTDCurrentAct (java.math.BigDecimal YTDCurrentAct)
    {
        set_Value ("YTDCurrentAct", YTDCurrentAct);
        
    }
    
    /** Get Current YTD.
    @return Current Year to Date */
    public java.math.BigDecimal getYTDCurrentAct() 
    {
        return get_ValueAsBigDecimal("YTDCurrentAct");
        
    }
    
    /** Set Prev YTD.
    @param YTDPrevAct Previous year Year to Date amount */
    public void setYTDPrevAct (java.math.BigDecimal YTDPrevAct)
    {
        set_Value ("YTDPrevAct", YTDPrevAct);
        
    }
    
    /** Get Prev YTD.
    @return Previous year Year to Date amount */
    public java.math.BigDecimal getYTDPrevAct() 
    {
        return get_ValueAsBigDecimal("YTDPrevAct");
        
    }
    
    
}
