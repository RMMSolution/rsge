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
/** Generated Model for XX_OrgProductReceiptLog
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_OrgProductReceiptLog extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_OrgProductReceiptLog_ID id
    @param trx transaction
    */
    public X_XX_OrgProductReceiptLog (Ctx ctx, int XX_OrgProductReceiptLog_ID, Trx trx)
    {
        super (ctx, XX_OrgProductReceiptLog_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_OrgProductReceiptLog_ID == 0)
        {
            setXX_OrgProductReceipt_ID (0);
            setXX_OrgProductReceiptLog_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_OrgProductReceiptLog (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27782989032357L;
    /** Last Updated Timestamp 2017-07-24 09:35:15.568 */
    public static final long updatedMS = 1500863715568L;
    /** AD_Table_ID=1006574 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_OrgProductReceiptLog");
        
    }
    ;
    
    /** TableName=XX_OrgProductReceiptLog */
    public static final String Table_Name="XX_OrgProductReceiptLog";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Move Line.
    @param M_MovementLine_ID Inventory Move document Line */
    public void setM_MovementLine_ID (int M_MovementLine_ID)
    {
        if (M_MovementLine_ID <= 0) set_Value ("M_MovementLine_ID", null);
        else
        set_Value ("M_MovementLine_ID", Integer.valueOf(M_MovementLine_ID));
        
    }
    
    /** Get Move Line.
    @return Inventory Move document Line */
    public int getM_MovementLine_ID() 
    {
        return get_ValueAsInt("M_MovementLine_ID");
        
    }
    
    /** Set Quantity.
    @param Qty Quantity */
    public void setQty (java.math.BigDecimal Qty)
    {
        set_Value ("Qty", Qty);
        
    }
    
    /** Get Quantity.
    @return Quantity */
    public java.math.BigDecimal getQty() 
    {
        return get_ValueAsBigDecimal("Qty");
        
    }
    
    /** Set XX_OrgProductReceipt_ID.
    @param XX_OrgProductReceipt_ID XX_OrgProductReceipt_ID */
    public void setXX_OrgProductReceipt_ID (int XX_OrgProductReceipt_ID)
    {
        if (XX_OrgProductReceipt_ID < 1) throw new IllegalArgumentException ("XX_OrgProductReceipt_ID is mandatory.");
        set_Value ("XX_OrgProductReceipt_ID", Integer.valueOf(XX_OrgProductReceipt_ID));
        
    }
    
    /** Get XX_OrgProductReceipt_ID.
    @return XX_OrgProductReceipt_ID */
    public int getXX_OrgProductReceipt_ID() 
    {
        return get_ValueAsInt("XX_OrgProductReceipt_ID");
        
    }
    
    /** Set XX_OrgProductReceiptLog_ID.
    @param XX_OrgProductReceiptLog_ID XX_OrgProductReceiptLog_ID */
    public void setXX_OrgProductReceiptLog_ID (int XX_OrgProductReceiptLog_ID)
    {
        if (XX_OrgProductReceiptLog_ID < 1) throw new IllegalArgumentException ("XX_OrgProductReceiptLog_ID is mandatory.");
        set_ValueNoCheck ("XX_OrgProductReceiptLog_ID", Integer.valueOf(XX_OrgProductReceiptLog_ID));
        
    }
    
    /** Get XX_OrgProductReceiptLog_ID.
    @return XX_OrgProductReceiptLog_ID */
    public int getXX_OrgProductReceiptLog_ID() 
    {
        return get_ValueAsInt("XX_OrgProductReceiptLog_ID");
        
    }
    
    
}
