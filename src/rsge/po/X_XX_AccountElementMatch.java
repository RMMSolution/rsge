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
/** Generated Model for XX_AccountElementMatch
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_AccountElementMatch extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_AccountElementMatch_ID id
    @param trx transaction
    */
    public X_XX_AccountElementMatch (Ctx ctx, int XX_AccountElementMatch_ID, Trx trx)
    {
        super (ctx, XX_AccountElementMatch_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_AccountElementMatch_ID == 0)
        {
            setAccount_ID (0);
            setC_Element_ID (0);
            setTargetAccount_ID (0);
            setTargetElement_ID (0);
            setXX_AccountElementMatch_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_AccountElementMatch (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27747465528789L;
    /** Last Updated Timestamp 2016-06-08 05:56:52.0 */
    public static final long updatedMS = 1465340212000L;
    /** AD_Table_ID=1000105 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_AccountElementMatch");
        
    }
    ;
    
    /** TableName=XX_AccountElementMatch */
    public static final String Table_Name="XX_AccountElementMatch";
    
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
    
    /** Set Element.
    @param C_Element_ID Accounting Element */
    public void setC_Element_ID (int C_Element_ID)
    {
        if (C_Element_ID < 1) throw new IllegalArgumentException ("C_Element_ID is mandatory.");
        set_Value ("C_Element_ID", Integer.valueOf(C_Element_ID));
        
    }
    
    /** Get Element.
    @return Accounting Element */
    public int getC_Element_ID() 
    {
        return get_ValueAsInt("C_Element_ID");
        
    }
    
    /** Set Account 2.
    @param TargetAccount_ID Second account */
    public void setTargetAccount_ID (int TargetAccount_ID)
    {
        if (TargetAccount_ID < 1) throw new IllegalArgumentException ("TargetAccount_ID is mandatory.");
        set_Value ("TargetAccount_ID", Integer.valueOf(TargetAccount_ID));
        
    }
    
    /** Get Account 2.
    @return Second account */
    public int getTargetAccount_ID() 
    {
        return get_ValueAsInt("TargetAccount_ID");
        
    }
    
    /** Get Record ID/ColumnName
    @return ID/ColumnName pair */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getTargetAccount_ID()));
        
    }
    
    /** Set Element 2.
    @param TargetElement_ID Second element */
    public void setTargetElement_ID (int TargetElement_ID)
    {
        if (TargetElement_ID < 1) throw new IllegalArgumentException ("TargetElement_ID is mandatory.");
        set_Value ("TargetElement_ID", Integer.valueOf(TargetElement_ID));
        
    }
    
    /** Get Element 2.
    @return Second element */
    public int getTargetElement_ID() 
    {
        return get_ValueAsInt("TargetElement_ID");
        
    }
    
    /** Set Matched Accounts.
    @param XX_AccountElementMatch_ID Matched Accounts */
    public void setXX_AccountElementMatch_ID (int XX_AccountElementMatch_ID)
    {
        if (XX_AccountElementMatch_ID < 1) throw new IllegalArgumentException ("XX_AccountElementMatch_ID is mandatory.");
        set_ValueNoCheck ("XX_AccountElementMatch_ID", Integer.valueOf(XX_AccountElementMatch_ID));
        
    }
    
    /** Get Matched Accounts.
    @return Matched Accounts */
    public int getXX_AccountElementMatch_ID() 
    {
        return get_ValueAsInt("XX_AccountElementMatch_ID");
        
    }
    
    
}
