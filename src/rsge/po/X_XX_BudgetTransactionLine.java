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
/** Generated Model for XX_BudgetTransactionLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_BudgetTransactionLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_BudgetTransactionLine_ID id
    @param trx transaction
    */
    public X_XX_BudgetTransactionLine (Ctx ctx, int XX_BudgetTransactionLine_ID, Trx trx)
    {
        super (ctx, XX_BudgetTransactionLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_BudgetTransactionLine_ID == 0)
        {
            setAD_Table_ID (0);
            setRecord_ID (0);
            setXX_BudgetTransaction_ID (0);
            setXX_BudgetTransactionLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_BudgetTransactionLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27803744745698L;
    /** Last Updated Timestamp 2018-03-21 15:03:48.909 */
    public static final long updatedMS = 1521619428909L;
    /** AD_Table_ID=1000410 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_BudgetTransactionLine");
        
    }
    ;
    
    /** TableName=XX_BudgetTransactionLine */
    public static final String Table_Name="XX_BudgetTransactionLine";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Table.
    @param AD_Table_ID Database Table information */
    public void setAD_Table_ID (int AD_Table_ID)
    {
        if (AD_Table_ID < 1) throw new IllegalArgumentException ("AD_Table_ID is mandatory.");
        set_Value ("AD_Table_ID", Integer.valueOf(AD_Table_ID));
        
    }
    
    /** Get Table.
    @return Database Table information */
    public int getAD_Table_ID() 
    {
        return get_ValueAsInt("AD_Table_ID");
        
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
    
    /** Set Record ID.
    @param Record_ID Direct internal record ID */
    public void setRecord_ID (int Record_ID)
    {
        if (Record_ID < 0) throw new IllegalArgumentException ("Record_ID is mandatory.");
        set_Value ("Record_ID", Integer.valueOf(Record_ID));
        
    }
    
    /** Get Record ID.
    @return Direct internal record ID */
    public int getRecord_ID() 
    {
        return get_ValueAsInt("Record_ID");
        
    }
    
    /** Set Reserved Amount.
    @param ReservedAmt Amount which already reserved by purchase requisition */
    public void setReservedAmt (java.math.BigDecimal ReservedAmt)
    {
        set_Value ("ReservedAmt", ReservedAmt);
        
    }
    
    /** Get Reserved Amount.
    @return Amount which already reserved by purchase requisition */
    public java.math.BigDecimal getReservedAmt() 
    {
        return get_ValueAsBigDecimal("ReservedAmt");
        
    }
    
    /** Set Source Record.
    @param SourceRecord_ID Source Record */
    public void setSourceRecord_ID (int SourceRecord_ID)
    {
        if (SourceRecord_ID <= 0) set_Value ("SourceRecord_ID", null);
        else
        set_Value ("SourceRecord_ID", Integer.valueOf(SourceRecord_ID));
        
    }
    
    /** Get Source Record.
    @return Source Record */
    public int getSourceRecord_ID() 
    {
        return get_ValueAsInt("SourceRecord_ID");
        
    }
    
    /** Set Source Table.
    @param SourceTable_ID Source table */
    public void setSourceTable_ID (int SourceTable_ID)
    {
        if (SourceTable_ID <= 0) set_Value ("SourceTable_ID", null);
        else
        set_Value ("SourceTable_ID", Integer.valueOf(SourceTable_ID));
        
    }
    
    /** Get Source Table.
    @return Source table */
    public int getSourceTable_ID() 
    {
        return get_ValueAsInt("SourceTable_ID");
        
    }
    
    /** Set Unrealized amount.
    @param UnrealizedAmt Amount which has not been realized yet */
    public void setUnrealizedAmt (java.math.BigDecimal UnrealizedAmt)
    {
        set_Value ("UnrealizedAmt", UnrealizedAmt);
        
    }
    
    /** Get Unrealized amount.
    @return Amount which has not been realized yet */
    public java.math.BigDecimal getUnrealizedAmt() 
    {
        return get_ValueAsBigDecimal("UnrealizedAmt");
        
    }
    
    /** Set Budget Transaction.
    @param XX_BudgetTransaction_ID Budget transaction */
    public void setXX_BudgetTransaction_ID (int XX_BudgetTransaction_ID)
    {
        if (XX_BudgetTransaction_ID < 1) throw new IllegalArgumentException ("XX_BudgetTransaction_ID is mandatory.");
        set_Value ("XX_BudgetTransaction_ID", Integer.valueOf(XX_BudgetTransaction_ID));
        
    }
    
    /** Get Budget Transaction.
    @return Budget transaction */
    public int getXX_BudgetTransaction_ID() 
    {
        return get_ValueAsInt("XX_BudgetTransaction_ID");
        
    }
    
    /** Set Transaction Line.
    @param XX_BudgetTransactionLine_ID Transaction Line */
    public void setXX_BudgetTransactionLine_ID (int XX_BudgetTransactionLine_ID)
    {
        if (XX_BudgetTransactionLine_ID < 1) throw new IllegalArgumentException ("XX_BudgetTransactionLine_ID is mandatory.");
        set_ValueNoCheck ("XX_BudgetTransactionLine_ID", Integer.valueOf(XX_BudgetTransactionLine_ID));
        
    }
    
    /** Get Transaction Line.
    @return Transaction Line */
    public int getXX_BudgetTransactionLine_ID() 
    {
        return get_ValueAsInt("XX_BudgetTransactionLine_ID");
        
    }
    
    
}
