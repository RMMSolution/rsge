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
/** Generated Model for XX_CashFlowSection
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_CashFlowSection extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_CashFlowSection_ID id
    @param trx transaction
    */
    public X_XX_CashFlowSection (Ctx ctx, int XX_CashFlowSection_ID, Trx trx)
    {
        super (ctx, XX_CashFlowSection_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_CashFlowSection_ID == 0)
        {
            setCashFlowSection (null);
            setName (null);
            setXX_CashFlowSection_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_CashFlowSection (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27757718092986L;
    /** Last Updated Timestamp 2016-10-04 21:52:56.197 */
    public static final long updatedMS = 1475592776197L;
    /** AD_Table_ID=1000125 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_CashFlowSection");
        
    }
    ;
    
    /** TableName=XX_CashFlowSection */
    public static final String Table_Name="XX_CashFlowSection";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Operating Activities Section = 01 */
    public static final String CASHFLOWSECTION_OperatingActivitiesSection = X_Ref__Cash_Flow_Section.OPERATING_ACTIVITIES_SECTION.getValue();
    /** Cash From Operation = 02 */
    public static final String CASHFLOWSECTION_CashFromOperation = X_Ref__Cash_Flow_Section.CASH_FROM_OPERATION.getValue();
    /** Investing Activities = 03 */
    public static final String CASHFLOWSECTION_InvestingActivities = X_Ref__Cash_Flow_Section.INVESTING_ACTIVITIES.getValue();
    /** Financing Activities = 04 */
    public static final String CASHFLOWSECTION_FinancingActivities = X_Ref__Cash_Flow_Section.FINANCING_ACTIVITIES.getValue();
    /** Net increase in cash and cash equivalents = 05 */
    public static final String CASHFLOWSECTION_NetIncreaseInCashAndCashEquivalents = X_Ref__Cash_Flow_Section.NET_INCREASE_IN_CASH_AND_CASH_EQUIVALENTS.getValue();
    /** Cash and cash equivalents at beginning of period = 06 */
    public static final String CASHFLOWSECTION_CashAndCashEquivalentsAtBeginningOfPeriod = X_Ref__Cash_Flow_Section.CASH_AND_CASH_EQUIVALENTS_AT_BEGINNING_OF_PERIOD.getValue();
    /** Cash and cash equivalents at end of period = 07 */
    public static final String CASHFLOWSECTION_CashAndCashEquivalentsAtEndOfPeriod = X_Ref__Cash_Flow_Section.CASH_AND_CASH_EQUIVALENTS_AT_END_OF_PERIOD.getValue();
    /** Set Section.
    @param CashFlowSection Section of cash flow */
    public void setCashFlowSection (String CashFlowSection)
    {
        if (CashFlowSection == null) throw new IllegalArgumentException ("CashFlowSection is mandatory");
        if (!X_Ref__Cash_Flow_Section.isValid(CashFlowSection))
        throw new IllegalArgumentException ("CashFlowSection Invalid value - " + CashFlowSection + " - Reference_ID=1000108 - 01 - 02 - 03 - 04 - 05 - 06 - 07");
        set_Value ("CashFlowSection", CashFlowSection);
        
    }
    
    /** Get Section.
    @return Section of cash flow */
    public String getCashFlowSection() 
    {
        return (String)get_Value("CashFlowSection");
        
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
    
    /** Set Total Description.
    @param TotalDescription Description of total of section */
    public void setTotalDescription (String TotalDescription)
    {
        set_Value ("TotalDescription", TotalDescription);
        
    }
    
    /** Get Total Description.
    @return Description of total of section */
    public String getTotalDescription() 
    {
        return (String)get_Value("TotalDescription");
        
    }
    
    /** Set Cash Flow Section.
    @param XX_CashFlowSection_ID Cash Flow Section */
    public void setXX_CashFlowSection_ID (int XX_CashFlowSection_ID)
    {
        if (XX_CashFlowSection_ID < 1) throw new IllegalArgumentException ("XX_CashFlowSection_ID is mandatory.");
        set_ValueNoCheck ("XX_CashFlowSection_ID", Integer.valueOf(XX_CashFlowSection_ID));
        
    }
    
    /** Get Cash Flow Section.
    @return Cash Flow Section */
    public int getXX_CashFlowSection_ID() 
    {
        return get_ValueAsInt("XX_CashFlowSection_ID");
        
    }
    
    
}
