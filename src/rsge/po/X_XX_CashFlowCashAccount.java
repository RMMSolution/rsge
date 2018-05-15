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
/** Generated Model for XX_CashFlowCashAccount
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_CashFlowCashAccount extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_CashFlowCashAccount_ID id
    @param trx transaction
    */
    public X_XX_CashFlowCashAccount (Ctx ctx, int XX_CashFlowCashAccount_ID, Trx trx)
    {
        super (ctx, XX_CashFlowCashAccount_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_CashFlowCashAccount_ID == 0)
        {
            setAccount_ID (0);
            setC_AcctSchema_ID (0);
            setXX_CashFlowCashAccount_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_CashFlowCashAccount (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27766303401356L;
    /** Last Updated Timestamp 2017-01-12 06:41:24.567 */
    public static final long updatedMS = 1484178084567L;
    /** AD_Table_ID=1000122 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_CashFlowCashAccount");
        
    }
    ;
    
    /** TableName=XX_CashFlowCashAccount */
    public static final String Table_Name="XX_CashFlowCashAccount";
    
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
    
    /** Set XX_CashFlowCashAccount_ID.
    @param XX_CashFlowCashAccount_ID XX_CashFlowCashAccount_ID */
    public void setXX_CashFlowCashAccount_ID (int XX_CashFlowCashAccount_ID)
    {
        if (XX_CashFlowCashAccount_ID < 1) throw new IllegalArgumentException ("XX_CashFlowCashAccount_ID is mandatory.");
        set_ValueNoCheck ("XX_CashFlowCashAccount_ID", Integer.valueOf(XX_CashFlowCashAccount_ID));
        
    }
    
    /** Get XX_CashFlowCashAccount_ID.
    @return XX_CashFlowCashAccount_ID */
    public int getXX_CashFlowCashAccount_ID() 
    {
        return get_ValueAsInt("XX_CashFlowCashAccount_ID");
        
    }
    
    
}
