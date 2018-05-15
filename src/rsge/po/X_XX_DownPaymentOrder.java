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
/** Generated Model for XX_DownPaymentOrder
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_DownPaymentOrder extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_DownPaymentOrder_ID id
    @param trx transaction
    */
    public X_XX_DownPaymentOrder (Ctx ctx, int XX_DownPaymentOrder_ID, Trx trx)
    {
        super (ctx, XX_DownPaymentOrder_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_DownPaymentOrder_ID == 0)
        {
            setC_Order_ID (0);
            setIsInvoiced (false);	// N
            setXX_DownPaymentOrder_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_DownPaymentOrder (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27806070938821L;
    /** Last Updated Timestamp 2018-04-17 13:13:42.032 */
    public static final long updatedMS = 1523945622032L;
    /** AD_Table_ID=1000140 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_DownPaymentOrder");
        
    }
    ;
    
    /** TableName=XX_DownPaymentOrder */
    public static final String Table_Name="XX_DownPaymentOrder";
    
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
    
    /** Set Order.
    @param C_Order_ID Order */
    public void setC_Order_ID (int C_Order_ID)
    {
        if (C_Order_ID < 1) throw new IllegalArgumentException ("C_Order_ID is mandatory.");
        set_Value ("C_Order_ID", Integer.valueOf(C_Order_ID));
        
    }
    
    /** Get Order.
    @return Order */
    public int getC_Order_ID() 
    {
        return get_ValueAsInt("C_Order_ID");
        
    }
    
    /** Set Down Payment.
    @param DPAmount Down Payment of transaction (exclude tax) */
    public void setDPAmount (java.math.BigDecimal DPAmount)
    {
        set_Value ("DPAmount", DPAmount);
        
    }
    
    /** Get Down Payment.
    @return Down Payment of transaction (exclude tax) */
    public java.math.BigDecimal getDPAmount() 
    {
        return get_ValueAsBigDecimal("DPAmount");
        
    }
    
    /** Set Grand Total.
    @param GrandTotal Total amount of document */
    public void setGrandTotal (java.math.BigDecimal GrandTotal)
    {
        set_Value ("GrandTotal", GrandTotal);
        
    }
    
    /** Get Grand Total.
    @return Total amount of document */
    public java.math.BigDecimal getGrandTotal() 
    {
        return get_ValueAsBigDecimal("GrandTotal");
        
    }
    
    /** Set Invoiced.
    @param IsInvoiced Is this invoiced? */
    public void setIsInvoiced (boolean IsInvoiced)
    {
        set_Value ("IsInvoiced", Boolean.valueOf(IsInvoiced));
        
    }
    
    /** Get Invoiced.
    @return Is this invoiced? */
    public boolean isInvoiced() 
    {
        return get_ValueAsBoolean("IsInvoiced");
        
    }
    
    /** Set Tax Amount.
    @param TaxAmt Tax Amount for a document */
    public void setTaxAmt (java.math.BigDecimal TaxAmt)
    {
        set_Value ("TaxAmt", TaxAmt);
        
    }
    
    /** Get Tax Amount.
    @return Tax Amount for a document */
    public java.math.BigDecimal getTaxAmt() 
    {
        return get_ValueAsBigDecimal("TaxAmt");
        
    }
    
    /** Set SubTotal.
    @param TotalLines Total of all document lines (excluding Tax) */
    public void setTotalLines (java.math.BigDecimal TotalLines)
    {
        set_Value ("TotalLines", TotalLines);
        
    }
    
    /** Get SubTotal.
    @return Total of all document lines (excluding Tax) */
    public java.math.BigDecimal getTotalLines() 
    {
        return get_ValueAsBigDecimal("TotalLines");
        
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
        if (XX_DownPaymentOrder_ID < 1) throw new IllegalArgumentException ("XX_DownPaymentOrder_ID is mandatory.");
        set_ValueNoCheck ("XX_DownPaymentOrder_ID", Integer.valueOf(XX_DownPaymentOrder_ID));
        
    }
    
    /** Get XX_DownPaymentOrder_ID.
    @return XX_DownPaymentOrder_ID */
    public int getXX_DownPaymentOrder_ID() 
    {
        return get_ValueAsInt("XX_DownPaymentOrder_ID");
        
    }
    
    
}
