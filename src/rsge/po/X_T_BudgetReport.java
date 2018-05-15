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
/** Generated Model for T_BudgetReport
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_T_BudgetReport extends PO
{
    /** Standard Constructor
    @param ctx context
    @param T_BudgetReport_ID id
    @param trx transaction
    */
    public X_T_BudgetReport (Ctx ctx, int T_BudgetReport_ID, Trx trx)
    {
        super (ctx, T_BudgetReport_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (T_BudgetReport_ID == 0)
        {
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_T_BudgetReport (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27793404421378L;
    /** Last Updated Timestamp 2017-11-21 22:45:04.589 */
    public static final long updatedMS = 1511279104589L;
    /** AD_Table_ID=1006423 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("T_BudgetReport");
        
    }
    ;
    
    /** TableName=T_BudgetReport */
    public static final String Table_Name="T_BudgetReport";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Account.
    @param Account_ID Account used */
    public void setAccount_ID (int Account_ID)
    {
        if (Account_ID <= 0) set_Value ("Account_ID", null);
        else
        set_Value ("Account_ID", Integer.valueOf(Account_ID));
        
    }
    
    /** Get Account.
    @return Account used */
    public int getAccount_ID() 
    {
        return get_ValueAsInt("Account_ID");
        
    }
    
    /** Set Budget Amount.
    @param BudgetAmt Budget amount for this entity */
    public void setBudgetAmt (java.math.BigDecimal BudgetAmt)
    {
        set_Value ("BudgetAmt", BudgetAmt);
        
    }
    
    /** Get Budget Amount.
    @return Budget amount for this entity */
    public java.math.BigDecimal getBudgetAmt() 
    {
        return get_ValueAsBigDecimal("BudgetAmt");
        
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
    
    /** Set Committed Amount.
    @param CommittedAmt The (legal) commitment amount */
    public void setCommittedAmt (java.math.BigDecimal CommittedAmt)
    {
        set_Value ("CommittedAmt", CommittedAmt);
        
    }
    
    /** Get Committed Amount.
    @return The (legal) commitment amount */
    public java.math.BigDecimal getCommittedAmt() 
    {
        return get_ValueAsBigDecimal("CommittedAmt");
        
    }
    
    /** Set Report Date.
    @param DateReport Expense/Time Report Date */
    public void setDateReport (Timestamp DateReport)
    {
        set_Value ("DateReport", DateReport);
        
    }
    
    /** Get Report Date.
    @return Expense/Time Report Date */
    public Timestamp getDateReport() 
    {
        return (Timestamp)get_Value("DateReport");
        
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
    
    /** Set Pending Amount.
    @param PendingAmt Pending amount */
    public void setPendingAmt (java.math.BigDecimal PendingAmt)
    {
        set_Value ("PendingAmt", PendingAmt);
        
    }
    
    /** Get Pending Amount.
    @return Pending amount */
    public java.math.BigDecimal getPendingAmt() 
    {
        return get_ValueAsBigDecimal("PendingAmt");
        
    }
    
    /** Set Realized Amount.
    @param RealizedAmt Realized amount */
    public void setRealizedAmt (java.math.BigDecimal RealizedAmt)
    {
        set_Value ("RealizedAmt", RealizedAmt);
        
    }
    
    /** Get Realized Amount.
    @return Realized amount */
    public java.math.BigDecimal getRealizedAmt() 
    {
        return get_ValueAsBigDecimal("RealizedAmt");
        
    }
    
    /** Set Remaining Amt.
    @param RemainingAmt Remaining Amount */
    public void setRemainingAmt (java.math.BigDecimal RemainingAmt)
    {
        set_Value ("RemainingAmt", RemainingAmt);
        
    }
    
    /** Get Remaining Amt.
    @return Remaining Amount */
    public java.math.BigDecimal getRemainingAmt() 
    {
        return get_ValueAsBigDecimal("RemainingAmt");
        
    }
    
    /** Set Reserved Budget Amount.
    @param ReservedBudgetAmt Reserved budget amount */
    public void setReservedBudgetAmt (java.math.BigDecimal ReservedBudgetAmt)
    {
        set_Value ("ReservedBudgetAmt", ReservedBudgetAmt);
        
    }
    
    /** Get Reserved Budget Amount.
    @return Reserved budget amount */
    public java.math.BigDecimal getReservedBudgetAmt() 
    {
        return get_ValueAsBigDecimal("ReservedBudgetAmt");
        
    }
    
    /** Set Unrealized Amount.
    @param UnrealizedAmt Unrealized amount */
    public void setUnrealizedAmt (java.math.BigDecimal UnrealizedAmt)
    {
        set_Value ("UnrealizedAmt", UnrealizedAmt);
        
    }
    
    /** Get Unrealized Amount.
    @return Unrealized amount */
    public java.math.BigDecimal getUnrealizedAmt() 
    {
        return get_ValueAsBigDecimal("UnrealizedAmt");
        
    }
    
    
}
