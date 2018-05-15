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
/** Generated Model for C_CashLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_C_CashLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param C_CashLine_ID id
    @param trx transaction
    */
    public X_C_CashLine (Ctx ctx, int C_CashLine_ID, Trx trx)
    {
        super (ctx, C_CashLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (C_CashLine_ID == 0)
        {
            setIsOverBudget (false);	// N
            setIsReceipt (false);	// N
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_C_CashLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27807106743632L;
    /** Last Updated Timestamp 2018-04-29 12:57:06.843 */
    public static final long updatedMS = 1524981426843L;
    /** AD_Table_ID=410 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("C_CashLine");
        
    }
    ;
    
    /** TableName=C_CashLine */
    public static final String Table_Name="C_CashLine";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Amount.
    @param CashAmount Amount in defined currency */
    public void setCashAmount (java.math.BigDecimal CashAmount)
    {
        set_Value ("CashAmount", CashAmount);
        
    }
    
    /** Get Amount.
    @return Amount in defined currency */
    public java.math.BigDecimal getCashAmount() 
    {
        return get_ValueAsBigDecimal("CashAmount");
        
    }
    
    /** Set Converted Amount.
    @param ConvertedAmt Converted Amount */
    public void setConvertedAmt (java.math.BigDecimal ConvertedAmt)
    {
        set_Value ("ConvertedAmt", ConvertedAmt);
        
    }
    
    /** Get Converted Amount.
    @return Converted Amount */
    public java.math.BigDecimal getConvertedAmt() 
    {
        return get_ValueAsBigDecimal("ConvertedAmt");
        
    }
    
    /** Set Over Budget.
    @param IsOverBudget This record is over budget */
    public void setIsOverBudget (boolean IsOverBudget)
    {
        set_Value ("IsOverBudget", Boolean.valueOf(IsOverBudget));
        
    }
    
    /** Get Over Budget.
    @return This record is over budget */
    public boolean isOverBudget() 
    {
        return get_ValueAsBoolean("IsOverBudget");
        
    }
    
    /** Set Receipt.
    @param IsReceipt This is a sales transaction (receipt) */
    public void setIsReceipt (boolean IsReceipt)
    {
        set_Value ("IsReceipt", Boolean.valueOf(IsReceipt));
        
    }
    
    /** Get Receipt.
    @return This is a sales transaction (receipt) */
    public boolean isReceipt() 
    {
        return get_ValueAsBoolean("IsReceipt");
        
    }
    
    /** Set Over Budget Amount.
    @param OverBudgetAmt Amount exceed budget allocated */
    public void setOverBudgetAmt (java.math.BigDecimal OverBudgetAmt)
    {
        set_Value ("OverBudgetAmt", OverBudgetAmt);
        
    }
    
    /** Get Over Budget Amount.
    @return Amount exceed budget allocated */
    public java.math.BigDecimal getOverBudgetAmt() 
    {
        return get_ValueAsBigDecimal("OverBudgetAmt");
        
    }
    
    /** Set Remaining Budget.
    @param RemainingBudget Remaining budget amount for selected account */
    public void setRemainingBudget (java.math.BigDecimal RemainingBudget)
    {
        set_Value ("RemainingBudget", RemainingBudget);
        
    }
    
    /** Get Remaining Budget.
    @return Remaining budget amount for selected account */
    public java.math.BigDecimal getRemainingBudget() 
    {
        return get_ValueAsBigDecimal("RemainingBudget");
        
    }
    
    /** Set Target Organization.
    @param TargetOrg_ID Target organization for this transaction */
    public void setTargetOrg_ID (int TargetOrg_ID)
    {
        if (TargetOrg_ID <= 0) set_Value ("TargetOrg_ID", null);
        else
        set_Value ("TargetOrg_ID", Integer.valueOf(TargetOrg_ID));
        
    }
    
    /** Get Target Organization.
    @return Target organization for this transaction */
    public int getTargetOrg_ID() 
    {
        return get_ValueAsInt("TargetOrg_ID");
        
    }
    
    
}
