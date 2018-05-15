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
/** Generated Model for XX_BankTransfer
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_BankTransfer extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_BankTransfer_ID id
    @param trx transaction
    */
    public X_XX_BankTransfer (Ctx ctx, int XX_BankTransfer_ID, Trx trx)
    {
        super (ctx, XX_BankTransfer_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_BankTransfer_ID == 0)
        {
            setXX_BankTransfer_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_BankTransfer (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664550549789L;
    /** Last Updated Timestamp 2013-10-22 14:00:33.0 */
    public static final long updatedMS = 1382425233000L;
    /** AD_Table_ID=1000110 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_BankTransfer");
        
    }
    ;
    
    /** TableName=XX_BankTransfer */
    public static final String Table_Name="XX_BankTransfer";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Bank Transfer.
    @param XX_BankTransfer_ID Transfer between bank */
    public void setXX_BankTransfer_ID (int XX_BankTransfer_ID)
    {
        if (XX_BankTransfer_ID < 1) throw new IllegalArgumentException ("XX_BankTransfer_ID is mandatory.");
        set_ValueNoCheck ("XX_BankTransfer_ID", Integer.valueOf(XX_BankTransfer_ID));
        
    }
    
    /** Get Bank Transfer.
    @return Transfer between bank */
    public int getXX_BankTransfer_ID() 
    {
        return get_ValueAsInt("XX_BankTransfer_ID");
        
    }
    
    
}
