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
/** Generated Model for XX_PlanningLineSrc
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_PlanningLineSrc extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_PlanningLineSrc_ID id
    @param trx transaction
    */
    public X_XX_PlanningLineSrc (Ctx ctx, int XX_PlanningLineSrc_ID, Trx trx)
    {
        super (ctx, XX_PlanningLineSrc_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_PlanningLineSrc_ID == 0)
        {
            setXX_PlanningLineSrc_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_PlanningLineSrc (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27685330747789L;
    /** Last Updated Timestamp 2014-06-20 02:17:11.0 */
    public static final long updatedMS = 1403205431000L;
    /** AD_Table_ID=1000169 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_PlanningLineSrc");
        
    }
    ;
    
    /** TableName=XX_PlanningLineSrc */
    public static final String Table_Name="XX_PlanningLineSrc";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Accounted Credit.
    @param AmtAcctCr Accounted Credit Amount */
    public void setAmtAcctCr (java.math.BigDecimal AmtAcctCr)
    {
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
        set_Value ("AmtAcctDr", AmtAcctDr);
        
    }
    
    /** Get Accounted Debit.
    @return Accounted Debit Amount */
    public java.math.BigDecimal getAmtAcctDr() 
    {
        return get_ValueAsBigDecimal("AmtAcctDr");
        
    }
    
    /** Set Source Amount.
    @param AmtSource Amount Balance in Source Currency */
    public void setAmtSource (java.math.BigDecimal AmtSource)
    {
        set_Value ("AmtSource", AmtSource);
        
    }
    
    /** Get Source Amount.
    @return Amount Balance in Source Currency */
    public java.math.BigDecimal getAmtSource() 
    {
        return get_ValueAsBigDecimal("AmtSource");
        
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
    
    /** Set Budget Form Line.
    @param XX_BudgetFormLine_ID Form Line */
    public void setXX_BudgetFormLine_ID (int XX_BudgetFormLine_ID)
    {
        if (XX_BudgetFormLine_ID <= 0) set_Value ("XX_BudgetFormLine_ID", null);
        else
        set_Value ("XX_BudgetFormLine_ID", Integer.valueOf(XX_BudgetFormLine_ID));
        
    }
    
    /** Get Budget Form Line.
    @return Form Line */
    public int getXX_BudgetFormLine_ID() 
    {
        return get_ValueAsInt("XX_BudgetFormLine_ID");
        
    }
    
    /** Set Line Period.
    @param XX_BudgetPlanningLinePeriod_ID Period of line */
    public void setXX_BudgetPlanningLinePeriod_ID (int XX_BudgetPlanningLinePeriod_ID)
    {
        if (XX_BudgetPlanningLinePeriod_ID <= 0) set_Value ("XX_BudgetPlanningLinePeriod_ID", null);
        else
        set_Value ("XX_BudgetPlanningLinePeriod_ID", Integer.valueOf(XX_BudgetPlanningLinePeriod_ID));
        
    }
    
    /** Get Line Period.
    @return Period of line */
    public int getXX_BudgetPlanningLinePeriod_ID() 
    {
        return get_ValueAsInt("XX_BudgetPlanningLinePeriod_ID");
        
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
    
    /** Set Asset Group Depreciation.
    @param XX_InvLineDepreciation_ID Asset Group Depreciation */
    public void setXX_InvLineDepreciation_ID (int XX_InvLineDepreciation_ID)
    {
        if (XX_InvLineDepreciation_ID <= 0) set_Value ("XX_InvLineDepreciation_ID", null);
        else
        set_Value ("XX_InvLineDepreciation_ID", Integer.valueOf(XX_InvLineDepreciation_ID));
        
    }
    
    /** Get Asset Group Depreciation.
    @return Asset Group Depreciation */
    public int getXX_InvLineDepreciation_ID() 
    {
        return get_ValueAsInt("XX_InvLineDepreciation_ID");
        
    }
    
    /** Set XX_PlanningLineSrc_ID.
    @param XX_PlanningLineSrc_ID XX_PlanningLineSrc_ID */
    public void setXX_PlanningLineSrc_ID (int XX_PlanningLineSrc_ID)
    {
        if (XX_PlanningLineSrc_ID < 1) throw new IllegalArgumentException ("XX_PlanningLineSrc_ID is mandatory.");
        set_ValueNoCheck ("XX_PlanningLineSrc_ID", Integer.valueOf(XX_PlanningLineSrc_ID));
        
    }
    
    /** Get XX_PlanningLineSrc_ID.
    @return XX_PlanningLineSrc_ID */
    public int getXX_PlanningLineSrc_ID() 
    {
        return get_ValueAsInt("XX_PlanningLineSrc_ID");
        
    }
    
    
}
