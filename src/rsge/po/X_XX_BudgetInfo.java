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
/** Generated Model for XX_BudgetInfo
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_BudgetInfo extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_BudgetInfo_ID id
    @param trx transaction
    */
    public X_XX_BudgetInfo (Ctx ctx, int XX_BudgetInfo_ID, Trx trx)
    {
        super (ctx, XX_BudgetInfo_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_BudgetInfo_ID == 0)
        {
            setBudgetCalendar_ID (0);
            setBudgetCheckRate (null);	// T
            setBudgetRange (0);	// 1
            setC_AcctSchema_ID (0);
            setC_ConversionType_ID (0);
            setFuturePeriodTransfer (0);	// 0
            setGL_BudgetControl_ID (0);
            setIsAllowFutureTransfer (false);	// N
            setIsCheckBudgetQty (true);	// Y
            setIsUseComparisonCalendar (false);	// N
            setOverBudgetRule (null);	// W
            setXX_BudgetInfo_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_BudgetInfo (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27807277336717L;
    /** Last Updated Timestamp 2018-05-01 12:20:19.928 */
    public static final long updatedMS = 1525152019928L;
    /** AD_Table_ID=1000115 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_BudgetInfo");
        
    }
    ;
    
    /** TableName=XX_BudgetInfo */
    public static final String Table_Name="XX_BudgetInfo";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Budget Calendar.
    @param BudgetCalendar_ID Calendar used for budget check */
    public void setBudgetCalendar_ID (int BudgetCalendar_ID)
    {
        if (BudgetCalendar_ID < 1) throw new IllegalArgumentException ("BudgetCalendar_ID is mandatory.");
        set_Value ("BudgetCalendar_ID", Integer.valueOf(BudgetCalendar_ID));
        
    }
    
    /** Get Budget Calendar.
    @return Calendar used for budget check */
    public int getBudgetCalendar_ID() 
    {
        return get_ValueAsInt("BudgetCalendar_ID");
        
    }
    
    /** Budget Check = C */
    public static final String BUDGETCHECKRATE_BudgetCheck = X_Ref__Budget_Check_Rate.BUDGET_CHECK.getValue();
    /** Transaction = T */
    public static final String BUDGETCHECKRATE_Transaction = X_Ref__Budget_Check_Rate.TRANSACTION.getValue();
    /** Set Budget Check Date.
    @param BudgetCheckRate Method to select conversion rate use in budget check */
    public void setBudgetCheckRate (String BudgetCheckRate)
    {
        if (BudgetCheckRate == null) throw new IllegalArgumentException ("BudgetCheckRate is mandatory");
        if (!X_Ref__Budget_Check_Rate.isValid(BudgetCheckRate))
        throw new IllegalArgumentException ("BudgetCheckRate Invalid value - " + BudgetCheckRate + " - Reference_ID=1000101 - C - T");
        set_Value ("BudgetCheckRate", BudgetCheckRate);
        
    }
    
    /** Get Budget Check Date.
    @return Method to select conversion rate use in budget check */
    public String getBudgetCheckRate() 
    {
        return (String)get_Value("BudgetCheckRate");
        
    }
    
    /** Set Budget Clearing Account.
    @param BudgetClearing_Acct Account for budget clearing */
    public void setBudgetClearing_Acct (int BudgetClearing_Acct)
    {
        set_Value ("BudgetClearing_Acct", Integer.valueOf(BudgetClearing_Acct));
        
    }
    
    /** Get Budget Clearing Account.
    @return Account for budget clearing */
    public int getBudgetClearing_Acct() 
    {
        return get_ValueAsInt("BudgetClearing_Acct");
        
    }
    
    /** Set Cut off Date.
    @param BudgetCutOffDate Budget cut off date */
    public void setBudgetCutOffDate (int BudgetCutOffDate)
    {
        set_Value ("BudgetCutOffDate", Integer.valueOf(BudgetCutOffDate));
        
    }
    
    /** Get Cut off Date.
    @return Budget cut off date */
    public int getBudgetCutOffDate() 
    {
        return get_ValueAsInt("BudgetCutOffDate");
        
    }
    
    /** Jan = 00 */
    public static final String BUDGETCUTOFFMONTH_Jan = X_Ref__Budget_Month.JAN.getValue();
    /** February = 01 */
    public static final String BUDGETCUTOFFMONTH_February = X_Ref__Budget_Month.FEBRUARY.getValue();
    /** March = 02 */
    public static final String BUDGETCUTOFFMONTH_March = X_Ref__Budget_Month.MARCH.getValue();
    /** April = 03 */
    public static final String BUDGETCUTOFFMONTH_April = X_Ref__Budget_Month.APRIL.getValue();
    /** May = 04 */
    public static final String BUDGETCUTOFFMONTH_May = X_Ref__Budget_Month.MAY.getValue();
    /** June = 05 */
    public static final String BUDGETCUTOFFMONTH_June = X_Ref__Budget_Month.JUNE.getValue();
    /** July = 06 */
    public static final String BUDGETCUTOFFMONTH_July = X_Ref__Budget_Month.JULY.getValue();
    /** August = 07 */
    public static final String BUDGETCUTOFFMONTH_August = X_Ref__Budget_Month.AUGUST.getValue();
    /** September = 08 */
    public static final String BUDGETCUTOFFMONTH_September = X_Ref__Budget_Month.SEPTEMBER.getValue();
    /** October = 09 */
    public static final String BUDGETCUTOFFMONTH_October = X_Ref__Budget_Month.OCTOBER.getValue();
    /** November = 10 */
    public static final String BUDGETCUTOFFMONTH_November = X_Ref__Budget_Month.NOVEMBER.getValue();
    /** December = 11 */
    public static final String BUDGETCUTOFFMONTH_December = X_Ref__Budget_Month.DECEMBER.getValue();
    /** Set Month.
    @param BudgetCutOffMonth Cut off month */
    public void setBudgetCutOffMonth (String BudgetCutOffMonth)
    {
        if (!X_Ref__Budget_Month.isValid(BudgetCutOffMonth))
        throw new IllegalArgumentException ("BudgetCutOffMonth Invalid value - " + BudgetCutOffMonth + " - Reference_ID=1000402 - 00 - 01 - 02 - 03 - 04 - 05 - 06 - 07 - 08 - 09 - 10 - 11");
        set_Value ("BudgetCutOffMonth", BudgetCutOffMonth);
        
    }
    
    /** Get Month.
    @return Cut off month */
    public String getBudgetCutOffMonth() 
    {
        return (String)get_Value("BudgetCutOffMonth");
        
    }
    
    /** Set Budget Range.
    @param BudgetRange Period's range of budget */
    public void setBudgetRange (int BudgetRange)
    {
        set_Value ("BudgetRange", Integer.valueOf(BudgetRange));
        
    }
    
    /** Get Budget Range.
    @return Period's range of budget */
    public int getBudgetRange() 
    {
        return get_ValueAsInt("BudgetRange");
        
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
    
    /** Get Record ID/ColumnName
    @return ID/ColumnName pair */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_AcctSchema_ID()));
        
    }
    
    /** Set Rate Type.
    @param C_ConversionType_ID Currency Conversion Rate Type */
    public void setC_ConversionType_ID (int C_ConversionType_ID)
    {
        if (C_ConversionType_ID < 1) throw new IllegalArgumentException ("C_ConversionType_ID is mandatory.");
        set_Value ("C_ConversionType_ID", Integer.valueOf(C_ConversionType_ID));
        
    }
    
    /** Get Rate Type.
    @return Currency Conversion Rate Type */
    public int getC_ConversionType_ID() 
    {
        return get_ValueAsInt("C_ConversionType_ID");
        
    }
    
    /** Set Comparison Calendar.
    @param ComparisonCalendar_ID Comparison budget calendar */
    public void setComparisonCalendar_ID (int ComparisonCalendar_ID)
    {
        if (ComparisonCalendar_ID <= 0) set_Value ("ComparisonCalendar_ID", null);
        else
        set_Value ("ComparisonCalendar_ID", Integer.valueOf(ComparisonCalendar_ID));
        
    }
    
    /** Get Comparison Calendar.
    @return Comparison budget calendar */
    public int getComparisonCalendar_ID() 
    {
        return get_ValueAsInt("ComparisonCalendar_ID");
        
    }
    
    /** Set Future Period Allowed.
    @param FuturePeriodTransfer Future period allowed for budget transfer. Set 0 for no restriction */
    public void setFuturePeriodTransfer (int FuturePeriodTransfer)
    {
        set_Value ("FuturePeriodTransfer", Integer.valueOf(FuturePeriodTransfer));
        
    }
    
    /** Get Future Period Allowed.
    @return Future period allowed for budget transfer. Set 0 for no restriction */
    public int getFuturePeriodTransfer() 
    {
        return get_ValueAsInt("FuturePeriodTransfer");
        
    }
    
    /** Set Budget Control.
    @param GL_BudgetControl_ID Budget Control */
    public void setGL_BudgetControl_ID (int GL_BudgetControl_ID)
    {
        if (GL_BudgetControl_ID < 1) throw new IllegalArgumentException ("GL_BudgetControl_ID is mandatory.");
        set_Value ("GL_BudgetControl_ID", Integer.valueOf(GL_BudgetControl_ID));
        
    }
    
    /** Get Budget Control.
    @return Budget Control */
    public int getGL_BudgetControl_ID() 
    {
        return get_ValueAsInt("GL_BudgetControl_ID");
        
    }
    
    /** Set Allow Transfer from Current Period.
    @param IsAllowFutureTransfer Allow budget transfer from current period to future period */
    public void setIsAllowFutureTransfer (boolean IsAllowFutureTransfer)
    {
        set_Value ("IsAllowFutureTransfer", Boolean.valueOf(IsAllowFutureTransfer));
        
    }
    
    /** Get Allow Transfer from Current Period.
    @return Allow budget transfer from current period to future period */
    public boolean isAllowFutureTransfer() 
    {
        return get_ValueAsBoolean("IsAllowFutureTransfer");
        
    }
    
    /** Set Organization Qty.
    @param IsCheckBudgetQty This rule will check all quantity of product in an organization */
    public void setIsCheckBudgetQty (boolean IsCheckBudgetQty)
    {
        set_Value ("IsCheckBudgetQty", Boolean.valueOf(IsCheckBudgetQty));
        
    }
    
    /** Get Organization Qty.
    @return This rule will check all quantity of product in an organization */
    public boolean isCheckBudgetQty() 
    {
        return get_ValueAsBoolean("IsCheckBudgetQty");
        
    }
    
    /** Set Use Comparison Calendar.
    @param IsUseComparisonCalendar Use comparison calendar to compare amount between two differenct budget calendar */
    public void setIsUseComparisonCalendar (boolean IsUseComparisonCalendar)
    {
        set_Value ("IsUseComparisonCalendar", Boolean.valueOf(IsUseComparisonCalendar));
        
    }
    
    /** Get Use Comparison Calendar.
    @return Use comparison calendar to compare amount between two differenct budget calendar */
    public boolean isUseComparisonCalendar() 
    {
        return get_ValueAsBoolean("IsUseComparisonCalendar");
        
    }
    
    /** Not allowed = N */
    public static final String OVERBUDGETRULE_NotAllowed = X_Ref_Over_Budget_Rule.NOT_ALLOWED.getValue();
    /** Workflow = W */
    public static final String OVERBUDGETRULE_Workflow = X_Ref_Over_Budget_Rule.WORKFLOW.getValue();
    /** Set Over Budget Rule.
    @param OverBudgetRule Rule when over budget condition met */
    public void setOverBudgetRule (String OverBudgetRule)
    {
        if (OverBudgetRule == null) throw new IllegalArgumentException ("OverBudgetRule is mandatory");
        if (!X_Ref_Over_Budget_Rule.isValid(OverBudgetRule))
        throw new IllegalArgumentException ("OverBudgetRule Invalid value - " + OverBudgetRule + " - Reference_ID=1000134 - N - W");
        set_Value ("OverBudgetRule", OverBudgetRule);
        
    }
    
    /** Get Over Budget Rule.
    @return Rule when over budget condition met */
    public String getOverBudgetRule() 
    {
        return (String)get_Value("OverBudgetRule");
        
    }
    
    /** Set Reserved Budget Account.
    @param ReservedBudget_Acct Account used for reserved budget */
    public void setReservedBudget_Acct (int ReservedBudget_Acct)
    {
        set_Value ("ReservedBudget_Acct", Integer.valueOf(ReservedBudget_Acct));
        
    }
    
    /** Get Reserved Budget Account.
    @return Account used for reserved budget */
    public int getReservedBudget_Acct() 
    {
        return get_ValueAsInt("ReservedBudget_Acct");
        
    }
    
    /** Set Reserved Budget Calendar.
    @param ReservedBudgetCalendar_ID Calendar to maintain reserved budget */
    public void setReservedBudgetCalendar_ID (int ReservedBudgetCalendar_ID)
    {
        if (ReservedBudgetCalendar_ID <= 0) set_Value ("ReservedBudgetCalendar_ID", null);
        else
        set_Value ("ReservedBudgetCalendar_ID", Integer.valueOf(ReservedBudgetCalendar_ID));
        
    }
    
    /** Get Reserved Budget Calendar.
    @return Calendar to maintain reserved budget */
    public int getReservedBudgetCalendar_ID() 
    {
        return get_ValueAsInt("ReservedBudgetCalendar_ID");
        
    }
    
    /** Set Source Reserve (%).
    @param SourceReserve Part of remaining budget which is reserve and cannot be transfered */
    public void setSourceReserve (java.math.BigDecimal SourceReserve)
    {
        set_Value ("SourceReserve", SourceReserve);
        
    }
    
    /** Get Source Reserve (%).
    @return Part of remaining budget which is reserve and cannot be transfered */
    public java.math.BigDecimal getSourceReserve() 
    {
        return get_ValueAsBigDecimal("SourceReserve");
        
    }
    
    /** Set Start Date.
    @param StartDate First effective day (inclusive) */
    public void setStartDate (Timestamp StartDate)
    {
        set_Value ("StartDate", StartDate);
        
    }
    
    /** Get Start Date.
    @return First effective day (inclusive) */
    public Timestamp getStartDate() 
    {
        return (Timestamp)get_Value("StartDate");
        
    }
    
    /** Set Budget Info.
    @param XX_BudgetInfo_ID Budget Info */
    public void setXX_BudgetInfo_ID (int XX_BudgetInfo_ID)
    {
        if (XX_BudgetInfo_ID < 1) throw new IllegalArgumentException ("XX_BudgetInfo_ID is mandatory.");
        set_ValueNoCheck ("XX_BudgetInfo_ID", Integer.valueOf(XX_BudgetInfo_ID));
        
    }
    
    /** Get Budget Info.
    @return Budget Info */
    public int getXX_BudgetInfo_ID() 
    {
        return get_ValueAsInt("XX_BudgetInfo_ID");
        
    }
    
    /** Set General Setup.
    @param XX_GeneralSetup_ID Basic setup for general enhancement extension */
    public void setXX_GeneralSetup_ID (int XX_GeneralSetup_ID)
    {
        if (XX_GeneralSetup_ID <= 0) set_Value ("XX_GeneralSetup_ID", null);
        else
        set_Value ("XX_GeneralSetup_ID", Integer.valueOf(XX_GeneralSetup_ID));
        
    }
    
    /** Get General Setup.
    @return Basic setup for general enhancement extension */
    public int getXX_GeneralSetup_ID() 
    {
        return get_ValueAsInt("XX_GeneralSetup_ID");
        
    }
    
    /** Set Unused Budget Rule.
    @param XX_UnusedBudgetRule_ID Rule which apply to part of budget which is not used */
    public void setXX_UnusedBudgetRule_ID (int XX_UnusedBudgetRule_ID)
    {
        if (XX_UnusedBudgetRule_ID <= 0) set_Value ("XX_UnusedBudgetRule_ID", null);
        else
        set_Value ("XX_UnusedBudgetRule_ID", Integer.valueOf(XX_UnusedBudgetRule_ID));
        
    }
    
    /** Get Unused Budget Rule.
    @return Rule which apply to part of budget which is not used */
    public int getXX_UnusedBudgetRule_ID() 
    {
        return get_ValueAsInt("XX_UnusedBudgetRule_ID");
        
    }
    
    
}
