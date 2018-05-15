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
/** Generated Model for XX_DownPaymentOrderAlloc
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_DownPaymentOrderAlloc extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_DownPaymentOrderAlloc_ID id
    @param trx transaction
    */
    public X_XX_DownPaymentOrderAlloc (Ctx ctx, int XX_DownPaymentOrderAlloc_ID, Trx trx)
    {
        super (ctx, XX_DownPaymentOrderAlloc_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_DownPaymentOrderAlloc_ID == 0)
        {
            setXX_DownPaymentOrderAlloc_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_DownPaymentOrderAlloc (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27807931175849L;
    /** Last Updated Timestamp 2018-05-09 01:57:39.06 */
    public static final long updatedMS = 1525805859060L;
    /** AD_Table_ID=1000611 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_DownPaymentOrderAlloc");
        
    }
    ;
    
    /** TableName=XX_DownPaymentOrderAlloc */
    public static final String Table_Name="XX_DownPaymentOrderAlloc";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Allocated Amount.
    @param AllocatedAmt Amount allocated to this document */
    public void setAllocatedAmt (java.math.BigDecimal AllocatedAmt)
    {
        set_Value ("AllocatedAmt", AllocatedAmt);
        
    }
    
    /** Get Allocated Amount.
    @return Amount allocated to this document */
    public java.math.BigDecimal getAllocatedAmt() 
    {
        return get_ValueAsBigDecimal("AllocatedAmt");
        
    }
    
    /** Set Invoice.
    @param C_Invoice_ID Invoice Identifier */
    public void setC_Invoice_ID (int C_Invoice_ID)
    {
        if (C_Invoice_ID <= 0) set_Value ("C_Invoice_ID", null);
        else
        set_Value ("C_Invoice_ID", Integer.valueOf(C_Invoice_ID));
        
    }
    
    /** Get Invoice.
    @return Invoice Identifier */
    public int getC_Invoice_ID() 
    {
        return get_ValueAsInt("C_Invoice_ID");
        
    }
    
    /** Set Down Payment.
    @param XX_DownPayment_ID Down payment of an order */
    public void setXX_DownPayment_ID (int XX_DownPayment_ID)
    {
        if (XX_DownPayment_ID <= 0) set_Value ("XX_DownPayment_ID", null);
        else
        set_Value ("XX_DownPayment_ID", Integer.valueOf(XX_DownPayment_ID));
        
    }
    
    /** Get Down Payment.
    @return Down payment of an order */
    public int getXX_DownPayment_ID() 
    {
        return get_ValueAsInt("XX_DownPayment_ID");
        
    }
    
    /** Set XX_DownPaymentOrder_ID.
    @param XX_DownPaymentOrder_ID XX_DownPaymentOrder_ID */
    public void setXX_DownPaymentOrder_ID (int XX_DownPaymentOrder_ID)
    {
        if (XX_DownPaymentOrder_ID <= 0) set_Value ("XX_DownPaymentOrder_ID", null);
        else
        set_Value ("XX_DownPaymentOrder_ID", Integer.valueOf(XX_DownPaymentOrder_ID));
        
    }
    
    /** Get XX_DownPaymentOrder_ID.
    @return XX_DownPaymentOrder_ID */
    public int getXX_DownPaymentOrder_ID() 
    {
        return get_ValueAsInt("XX_DownPaymentOrder_ID");
        
    }
    
    /** Set XX_DownPaymentOrderAlloc_ID.
    @param XX_DownPaymentOrderAlloc_ID XX_DownPaymentOrderAlloc_ID */
    public void setXX_DownPaymentOrderAlloc_ID (int XX_DownPaymentOrderAlloc_ID)
    {
        if (XX_DownPaymentOrderAlloc_ID < 1) throw new IllegalArgumentException ("XX_DownPaymentOrderAlloc_ID is mandatory.");
        set_ValueNoCheck ("XX_DownPaymentOrderAlloc_ID", Integer.valueOf(XX_DownPaymentOrderAlloc_ID));
        
    }
    
    /** Get XX_DownPaymentOrderAlloc_ID.
    @return XX_DownPaymentOrderAlloc_ID */
    public int getXX_DownPaymentOrderAlloc_ID() 
    {
        return get_ValueAsInt("XX_DownPaymentOrderAlloc_ID");
        
    }
    
    /** Set XX_DownPaymentSettlement_ID.
    @param XX_DownPaymentSettlement_ID XX_DownPaymentSettlement_ID */
    public void setXX_DownPaymentSettlement_ID (int XX_DownPaymentSettlement_ID)
    {
        if (XX_DownPaymentSettlement_ID <= 0) set_Value ("XX_DownPaymentSettlement_ID", null);
        else
        set_Value ("XX_DownPaymentSettlement_ID", Integer.valueOf(XX_DownPaymentSettlement_ID));
        
    }
    
    /** Get XX_DownPaymentSettlement_ID.
    @return XX_DownPaymentSettlement_ID */
    public int getXX_DownPaymentSettlement_ID() 
    {
        return get_ValueAsInt("XX_DownPaymentSettlement_ID");
        
    }
    
    
}
