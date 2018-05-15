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
/** Generated Model for XX_EliminatedAccount
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_EliminatedAccount extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_EliminatedAccount_ID id
    @param trx transaction
    */
    public X_XX_EliminatedAccount (Ctx ctx, int XX_EliminatedAccount_ID, Trx trx)
    {
        super (ctx, XX_EliminatedAccount_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_EliminatedAccount_ID == 0)
        {
            setAccount_ID (0);
            setXX_EliminatedAccount_ID (0);
            setXX_GeneralSetup_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_EliminatedAccount (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27746241316789L;
    /** Last Updated Timestamp 2016-05-25 01:53:20.0 */
    public static final long updatedMS = 1464116000000L;
    /** AD_Table_ID=1000145 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_EliminatedAccount");
        
    }
    ;
    
    /** TableName=XX_EliminatedAccount */
    public static final String Table_Name="XX_EliminatedAccount";
    
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
    
    /** Set Eliminated Account.
    @param XX_EliminatedAccount_ID Account need to be eliminated for consolidation */
    public void setXX_EliminatedAccount_ID (int XX_EliminatedAccount_ID)
    {
        if (XX_EliminatedAccount_ID < 1) throw new IllegalArgumentException ("XX_EliminatedAccount_ID is mandatory.");
        set_ValueNoCheck ("XX_EliminatedAccount_ID", Integer.valueOf(XX_EliminatedAccount_ID));
        
    }
    
    /** Get Eliminated Account.
    @return Account need to be eliminated for consolidation */
    public int getXX_EliminatedAccount_ID() 
    {
        return get_ValueAsInt("XX_EliminatedAccount_ID");
        
    }
    
    /** Set General Setup.
    @param XX_GeneralSetup_ID Basic setup for general enhancement extension */
    public void setXX_GeneralSetup_ID (int XX_GeneralSetup_ID)
    {
        if (XX_GeneralSetup_ID < 1) throw new IllegalArgumentException ("XX_GeneralSetup_ID is mandatory.");
        set_Value ("XX_GeneralSetup_ID", Integer.valueOf(XX_GeneralSetup_ID));
        
    }
    
    /** Get General Setup.
    @return Basic setup for general enhancement extension */
    public int getXX_GeneralSetup_ID() 
    {
        return get_ValueAsInt("XX_GeneralSetup_ID");
        
    }
    
    
}
