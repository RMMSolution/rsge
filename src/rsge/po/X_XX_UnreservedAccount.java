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
/** Generated Model for XX_UnreservedAccount
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_UnreservedAccount extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_UnreservedAccount_ID id
    @param trx transaction
    */
    public X_XX_UnreservedAccount (Ctx ctx, int XX_UnreservedAccount_ID, Trx trx)
    {
        super (ctx, XX_UnreservedAccount_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_UnreservedAccount_ID == 0)
        {
            setAccount_ID (0);
            setXX_UnreservedAccount_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_UnreservedAccount (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664550554789L;
    /** Last Updated Timestamp 2013-10-22 14:00:38.0 */
    public static final long updatedMS = 1382425238000L;
    /** AD_Table_ID=1000191 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_UnreservedAccount");
        
    }
    ;
    
    /** TableName=XX_UnreservedAccount */
    public static final String Table_Name="XX_UnreservedAccount";
    
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
        set_ValueNoCheck ("Account_ID", Integer.valueOf(Account_ID));
        
    }
    
    /** Get Account.
    @return Account used */
    public int getAccount_ID() 
    {
        return get_ValueAsInt("Account_ID");
        
    }
    
    /** Set XX_UnreservedAccount_ID.
    @param XX_UnreservedAccount_ID XX_UnreservedAccount_ID */
    public void setXX_UnreservedAccount_ID (int XX_UnreservedAccount_ID)
    {
        if (XX_UnreservedAccount_ID < 1) throw new IllegalArgumentException ("XX_UnreservedAccount_ID is mandatory.");
        set_ValueNoCheck ("XX_UnreservedAccount_ID", Integer.valueOf(XX_UnreservedAccount_ID));
        
    }
    
    /** Get XX_UnreservedAccount_ID.
    @return XX_UnreservedAccount_ID */
    public int getXX_UnreservedAccount_ID() 
    {
        return get_ValueAsInt("XX_UnreservedAccount_ID");
        
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
