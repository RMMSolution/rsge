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
/** Generated Model for XX_TransferReceiptLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_TransferReceiptLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_TransferReceiptLine_ID id
    @param trx transaction
    */
    public X_XX_TransferReceiptLine (Ctx ctx, int XX_TransferReceiptLine_ID, Trx trx)
    {
        super (ctx, XX_TransferReceiptLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_TransferReceiptLine_ID == 0)
        {
            setXX_TransferReceipt_ID (0);
            setXX_TransferReceiptLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_TransferReceiptLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27807308126116L;
    /** Last Updated Timestamp 2018-05-01 20:53:29.327 */
    public static final long updatedMS = 1525182809327L;
    /** AD_Table_ID=1000412 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_TransferReceiptLine");
        
    }
    ;
    
    /** TableName=XX_TransferReceiptLine */
    public static final String Table_Name="XX_TransferReceiptLine";
    
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
    
    /** Set Quantity Allocated.
    @param QtyAllocated Quantity that has been picked and is awaiting shipment */
    public void setQtyAllocated (java.math.BigDecimal QtyAllocated)
    {
        set_Value ("QtyAllocated", QtyAllocated);
        
    }
    
    /** Get Quantity Allocated.
    @return Quantity that has been picked and is awaiting shipment */
    public java.math.BigDecimal getQtyAllocated() 
    {
        return get_ValueAsBigDecimal("QtyAllocated");
        
    }
    
    /** Set Requisition Line.
    @param XX_PurchaseRequisitionLine_ID Requisition Line */
    public void setXX_PurchaseRequisitionLine_ID (int XX_PurchaseRequisitionLine_ID)
    {
        if (XX_PurchaseRequisitionLine_ID <= 0) set_Value ("XX_PurchaseRequisitionLine_ID", null);
        else
        set_Value ("XX_PurchaseRequisitionLine_ID", Integer.valueOf(XX_PurchaseRequisitionLine_ID));
        
    }
    
    /** Get Requisition Line.
    @return Requisition Line */
    public int getXX_PurchaseRequisitionLine_ID() 
    {
        return get_ValueAsInt("XX_PurchaseRequisitionLine_ID");
        
    }
    
    /** Set Transfer Receipt.
    @param XX_TransferReceipt_ID Transfer receipt */
    public void setXX_TransferReceipt_ID (int XX_TransferReceipt_ID)
    {
        if (XX_TransferReceipt_ID < 1) throw new IllegalArgumentException ("XX_TransferReceipt_ID is mandatory.");
        set_Value ("XX_TransferReceipt_ID", Integer.valueOf(XX_TransferReceipt_ID));
        
    }
    
    /** Get Transfer Receipt.
    @return Transfer receipt */
    public int getXX_TransferReceipt_ID() 
    {
        return get_ValueAsInt("XX_TransferReceipt_ID");
        
    }
    
    /** Set Transfer Receipt Line.
    @param XX_TransferReceiptLine_ID Transfer Receipt Line */
    public void setXX_TransferReceiptLine_ID (int XX_TransferReceiptLine_ID)
    {
        if (XX_TransferReceiptLine_ID < 1) throw new IllegalArgumentException ("XX_TransferReceiptLine_ID is mandatory.");
        set_ValueNoCheck ("XX_TransferReceiptLine_ID", Integer.valueOf(XX_TransferReceiptLine_ID));
        
    }
    
    /** Get Transfer Receipt Line.
    @return Transfer Receipt Line */
    public int getXX_TransferReceiptLine_ID() 
    {
        return get_ValueAsInt("XX_TransferReceiptLine_ID");
        
    }
    
    
}
