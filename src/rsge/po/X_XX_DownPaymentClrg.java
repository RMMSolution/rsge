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
/** Generated Model for XX_DownPaymentClrg
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_DownPaymentClrg extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_DownPaymentClrg_ID id
    @param trx transaction
    */
    public X_XX_DownPaymentClrg (Ctx ctx, int XX_DownPaymentClrg_ID, Trx trx)
    {
        super (ctx, XX_DownPaymentClrg_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_DownPaymentClrg_ID == 0)
        {
            setC_BankAccount_ID (0);
            setC_Currency_ID (0);
            setXX_GeneralSetup_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_DownPaymentClrg (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27678154571789L;
    /** Last Updated Timestamp 2014-03-29 00:54:15.0 */
    public static final long updatedMS = 1396029255000L;
    /** AD_Table_ID=1000139 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_DownPaymentClrg");
        
    }
    ;
    
    /** TableName=XX_DownPaymentClrg */
    public static final String Table_Name="XX_DownPaymentClrg";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Bank Account.
    @param C_BankAccount_ID Account at the Bank */
    public void setC_BankAccount_ID (int C_BankAccount_ID)
    {
        if (C_BankAccount_ID < 1) throw new IllegalArgumentException ("C_BankAccount_ID is mandatory.");
        set_ValueNoCheck ("C_BankAccount_ID", Integer.valueOf(C_BankAccount_ID));
        
    }
    
    /** Get Bank Account.
    @return Account at the Bank */
    public int getC_BankAccount_ID() 
    {
        return get_ValueAsInt("C_BankAccount_ID");
        
    }
    
    /** Set Currency.
    @param C_Currency_ID The Currency for this record */
    public void setC_Currency_ID (int C_Currency_ID)
    {
        if (C_Currency_ID < 1) throw new IllegalArgumentException ("C_Currency_ID is mandatory.");
        set_ValueNoCheck ("C_Currency_ID", Integer.valueOf(C_Currency_ID));
        
    }
    
    /** Get Currency.
    @return The Currency for this record */
    public int getC_Currency_ID() 
    {
        return get_ValueAsInt("C_Currency_ID");
        
    }
    
    /** Set General Setup.
    @param XX_GeneralSetup_ID Basic setup for general enhancement extension */
    public void setXX_GeneralSetup_ID (int XX_GeneralSetup_ID)
    {
        if (XX_GeneralSetup_ID < 1) throw new IllegalArgumentException ("XX_GeneralSetup_ID is mandatory.");
        set_ValueNoCheck ("XX_GeneralSetup_ID", Integer.valueOf(XX_GeneralSetup_ID));
        
    }
    
    /** Get General Setup.
    @return Basic setup for general enhancement extension */
    public int getXX_GeneralSetup_ID() 
    {
        return get_ValueAsInt("XX_GeneralSetup_ID");
        
    }
    
    
}
