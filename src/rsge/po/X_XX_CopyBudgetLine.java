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
/** Generated Model for XX_CopyBudgetLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_CopyBudgetLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_CopyBudgetLine_ID id
    @param trx transaction
    */
    public X_XX_CopyBudgetLine (Ctx ctx, int XX_CopyBudgetLine_ID, Trx trx)
    {
        super (ctx, XX_CopyBudgetLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_CopyBudgetLine_ID == 0)
        {
            setAccount_ID (0);
            setFact_Acct_ID (0);
            setXX_CopyBudgetLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_CopyBudgetLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27745274483789L;
    /** Last Updated Timestamp 2016-05-13 21:19:27.0 */
    public static final long updatedMS = 1463149167000L;
    /** AD_Table_ID=1000131 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_CopyBudgetLine");
        
    }
    ;
    
    /** TableName=XX_CopyBudgetLine */
    public static final String Table_Name="XX_CopyBudgetLine";
    
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
        if (Account_ID < 1) throw new IllegalArgumentException ("Account_ID is mandatory.");
        set_Value ("Account_ID", Integer.valueOf(Account_ID));
        
    }
    
    /** Get Account.
    @return Account used */
    public int getAccount_ID() 
    {
        return get_ValueAsInt("Account_ID");
        
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
    
    /** Set Accounting Fact.
    @param Fact_Acct_ID Accounting Fact */
    public void setFact_Acct_ID (int Fact_Acct_ID)
    {
        if (Fact_Acct_ID < 1) throw new IllegalArgumentException ("Fact_Acct_ID is mandatory.");
        set_Value ("Fact_Acct_ID", Integer.valueOf(Fact_Acct_ID));
        
    }
    
    /** Get Accounting Fact.
    @return Accounting Fact */
    public int getFact_Acct_ID() 
    {
        return get_ValueAsInt("Fact_Acct_ID");
        
    }
    
    /** Set Budget Copy.
    @param XX_CopyBudget_ID Copy budget from one budget to another */
    public void setXX_CopyBudget_ID (int XX_CopyBudget_ID)
    {
        if (XX_CopyBudget_ID <= 0) set_Value ("XX_CopyBudget_ID", null);
        else
        set_Value ("XX_CopyBudget_ID", Integer.valueOf(XX_CopyBudget_ID));
        
    }
    
    /** Get Budget Copy.
    @return Copy budget from one budget to another */
    public int getXX_CopyBudget_ID() 
    {
        return get_ValueAsInt("XX_CopyBudget_ID");
        
    }
    
    /** Set Budget Copy Line.
    @param XX_CopyBudgetLine_ID Budget Copy Line */
    public void setXX_CopyBudgetLine_ID (int XX_CopyBudgetLine_ID)
    {
        if (XX_CopyBudgetLine_ID < 1) throw new IllegalArgumentException ("XX_CopyBudgetLine_ID is mandatory.");
        set_ValueNoCheck ("XX_CopyBudgetLine_ID", Integer.valueOf(XX_CopyBudgetLine_ID));
        
    }
    
    /** Get Budget Copy Line.
    @return Budget Copy Line */
    public int getXX_CopyBudgetLine_ID() 
    {
        return get_ValueAsInt("XX_CopyBudgetLine_ID");
        
    }
    
    
}
