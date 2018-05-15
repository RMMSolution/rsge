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
/** Generated Model for XX_BudgetPlanningLinePeriod
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_BudgetPlanningLinePeriod extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_BudgetPlanningLinePeriod_ID id
    @param trx transaction
    */
    public X_XX_BudgetPlanningLinePeriod (Ctx ctx, int XX_BudgetPlanningLinePeriod_ID, Trx trx)
    {
        super (ctx, XX_BudgetPlanningLinePeriod_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_BudgetPlanningLinePeriod_ID == 0)
        {
            setAmtAcctCr (Env.ZERO);	// 0
            setAmtAcctDr (Env.ZERO);	// 0
            setIsGenerated (false);	// N
            setProcessed (false);	// N
            setTotalCr (Env.ZERO);	// 0
            setTotalDr (Env.ZERO);	// 0
            setXX_BudgetPlanningLinePeriod_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_BudgetPlanningLinePeriod (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27685556176789L;
    /** Last Updated Timestamp 2014-06-22 16:54:20.0 */
    public static final long updatedMS = 1403430860000L;
    /** AD_Table_ID=1000118 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_BudgetPlanningLinePeriod");
        
    }
    ;
    
    /** TableName=XX_BudgetPlanningLinePeriod */
    public static final String Table_Name="XX_BudgetPlanningLinePeriod";
    
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
    
    /** Set Revised Credit.
    @param AmtRevCr Revised Credit Amount */
    public void setAmtRevCr (java.math.BigDecimal AmtRevCr)
    {
        set_Value ("AmtRevCr", AmtRevCr);
        
    }
    
    /** Get Revised Credit.
    @return Revised Credit Amount */
    public java.math.BigDecimal getAmtRevCr() 
    {
        return get_ValueAsBigDecimal("AmtRevCr");
        
    }
    
    /** Set Revised Debit.
    @param AmtRevDr Revised Debit Amount */
    public void setAmtRevDr (java.math.BigDecimal AmtRevDr)
    {
        set_Value ("AmtRevDr", AmtRevDr);
        
    }
    
    /** Get Revised Debit.
    @return Revised Debit Amount */
    public java.math.BigDecimal getAmtRevDr() 
    {
        return get_ValueAsBigDecimal("AmtRevDr");
        
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
    
    /** Set Document No.
    @param DocumentNo Document sequence number of the document */
    public void setDocumentNo (String DocumentNo)
    {
        set_Value ("DocumentNo", DocumentNo);
        
    }
    
    /** Get Document No.
    @return Document sequence number of the document */
    public String getDocumentNo() 
    {
        return (String)get_Value("DocumentNo");
        
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
    
    /** Set Period No.
    @param PeriodNo Unique Period Number */
    public void setPeriodNo (int PeriodNo)
    {
        set_Value ("PeriodNo", Integer.valueOf(PeriodNo));
        
    }
    
    /** Get Period No.
    @return Unique Period Number */
    public int getPeriodNo() 
    {
        return get_ValueAsInt("PeriodNo");
        
    }
    
    /** Set Posted.
    @param Posted Posting status */
    public void setPosted (boolean Posted)
    {
        set_Value ("Posted", Boolean.valueOf(Posted));
        
    }
    
    /** Get Posted.
    @return Posting status */
    public boolean isPosted() 
    {
        return get_ValueAsBoolean("Posted");
        
    }
    
    /** Set Processed.
    @param Processed The document has been processed */
    public void setProcessed (boolean Processed)
    {
        set_Value ("Processed", Boolean.valueOf(Processed));
        
    }
    
    /** Get Processed.
    @return The document has been processed */
    public boolean isProcessed() 
    {
        return get_ValueAsBoolean("Processed");
        
    }
    
    /** Set Process Now.
    @param Processing Process Now */
    public void setProcessing (boolean Processing)
    {
        set_Value ("Processing", Boolean.valueOf(Processing));
        
    }
    
    /** Get Process Now.
    @return Process Now */
    public boolean isProcessing() 
    {
        return get_ValueAsBoolean("Processing");
        
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
    
    /** Set Revised Qty.
    @param RevisedQty Revised quantity */
    public void setRevisedQty (java.math.BigDecimal RevisedQty)
    {
        set_Value ("RevisedQty", RevisedQty);
        
    }
    
    /** Get Revised Qty.
    @return Revised quantity */
    public java.math.BigDecimal getRevisedQty() 
    {
        return get_ValueAsBigDecimal("RevisedQty");
        
    }
    
    /** Set Total Credit.
    @param TotalCr Total Credit in document currency */
    public void setTotalCr (java.math.BigDecimal TotalCr)
    {
        if (TotalCr == null) throw new IllegalArgumentException ("TotalCr is mandatory.");
        set_Value ("TotalCr", TotalCr);
        
    }
    
    /** Get Total Credit.
    @return Total Credit in document currency */
    public java.math.BigDecimal getTotalCr() 
    {
        return get_ValueAsBigDecimal("TotalCr");
        
    }
    
    /** Set Total Debit.
    @param TotalDr Total debit in document currency */
    public void setTotalDr (java.math.BigDecimal TotalDr)
    {
        if (TotalDr == null) throw new IllegalArgumentException ("TotalDr is mandatory.");
        set_Value ("TotalDr", TotalDr);
        
    }
    
    /** Get Total Debit.
    @return Total debit in document currency */
    public java.math.BigDecimal getTotalDr() 
    {
        return get_ValueAsBigDecimal("TotalDr");
        
    }
    
    /** Set Plan Line.
    @param XX_BudgetPlanningLine_ID Plan line */
    public void setXX_BudgetPlanningLine_ID (int XX_BudgetPlanningLine_ID)
    {
        if (XX_BudgetPlanningLine_ID <= 0) set_Value ("XX_BudgetPlanningLine_ID", null);
        else
        set_Value ("XX_BudgetPlanningLine_ID", Integer.valueOf(XX_BudgetPlanningLine_ID));
        
    }
    
    /** Get Plan Line.
    @return Plan line */
    public int getXX_BudgetPlanningLine_ID() 
    {
        return get_ValueAsInt("XX_BudgetPlanningLine_ID");
        
    }
    
    /** Set Line Period.
    @param XX_BudgetPlanningLinePeriod_ID Period of line */
    public void setXX_BudgetPlanningLinePeriod_ID (int XX_BudgetPlanningLinePeriod_ID)
    {
        if (XX_BudgetPlanningLinePeriod_ID < 1) throw new IllegalArgumentException ("XX_BudgetPlanningLinePeriod_ID is mandatory.");
        set_ValueNoCheck ("XX_BudgetPlanningLinePeriod_ID", Integer.valueOf(XX_BudgetPlanningLinePeriod_ID));
        
    }
    
    /** Get Line Period.
    @return Period of line */
    public int getXX_BudgetPlanningLinePeriod_ID() 
    {
        return get_ValueAsInt("XX_BudgetPlanningLinePeriod_ID");
        
    }
    
    
}
