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
/** Generated Model for XX_DownPaymentSettlementLn
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_DownPaymentSettlementLn extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_DownPaymentSettlementLn_ID id
    @param trx transaction
    */
    public X_XX_DownPaymentSettlementLn (Ctx ctx, int XX_DownPaymentSettlementLn_ID, Trx trx)
    {
        super (ctx, XX_DownPaymentSettlementLn_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_DownPaymentSettlementLn_ID == 0)
        {
            setXX_DownPaymentSettlement_ID (0);
            setXX_DownPaymentSettlementLn_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_DownPaymentSettlementLn (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27809534032199L;
    /** Last Updated Timestamp 2018-05-27 15:11:55.41 */
    public static final long updatedMS = 1527408715410L;
    /** AD_Table_ID=1000143 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_DownPaymentSettlementLn");
        
    }
    ;
    
    /** TableName=XX_DownPaymentSettlementLn */
    public static final String Table_Name="XX_DownPaymentSettlementLn";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Amount.
    @param Amount Amount in a defined currency */
    public void setAmount (java.math.BigDecimal Amount)
    {
        set_Value ("Amount", Amount);
        
    }
    
    /** Get Amount.
    @return Amount in a defined currency */
    public java.math.BigDecimal getAmount() 
    {
        return get_ValueAsBigDecimal("Amount");
        
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
    
    /** Set Order.
    @param C_Order_ID Order */
    public void setC_Order_ID (int C_Order_ID)
    {
        if (C_Order_ID <= 0) set_Value ("C_Order_ID", null);
        else
        set_Value ("C_Order_ID", Integer.valueOf(C_Order_ID));
        
    }
    
    /** Get Order.
    @return Order */
    public int getC_Order_ID() 
    {
        return get_ValueAsInt("C_Order_ID");
        
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
    
    /** Set XX_DownPaymentSettlement_ID.
    @param XX_DownPaymentSettlement_ID XX_DownPaymentSettlement_ID */
    public void setXX_DownPaymentSettlement_ID (int XX_DownPaymentSettlement_ID)
    {
        if (XX_DownPaymentSettlement_ID < 1) throw new IllegalArgumentException ("XX_DownPaymentSettlement_ID is mandatory.");
        set_Value ("XX_DownPaymentSettlement_ID", Integer.valueOf(XX_DownPaymentSettlement_ID));
        
    }
    
    /** Get XX_DownPaymentSettlement_ID.
    @return XX_DownPaymentSettlement_ID */
    public int getXX_DownPaymentSettlement_ID() 
    {
        return get_ValueAsInt("XX_DownPaymentSettlement_ID");
        
    }
    
    /** Set XX_DownPaymentSettlementLn_ID.
    @param XX_DownPaymentSettlementLn_ID XX_DownPaymentSettlementLn_ID */
    public void setXX_DownPaymentSettlementLn_ID (int XX_DownPaymentSettlementLn_ID)
    {
        if (XX_DownPaymentSettlementLn_ID < 1) throw new IllegalArgumentException ("XX_DownPaymentSettlementLn_ID is mandatory.");
        set_ValueNoCheck ("XX_DownPaymentSettlementLn_ID", Integer.valueOf(XX_DownPaymentSettlementLn_ID));
        
    }
    
    /** Get XX_DownPaymentSettlementLn_ID.
    @return XX_DownPaymentSettlementLn_ID */
    public int getXX_DownPaymentSettlementLn_ID() 
    {
        return get_ValueAsInt("XX_DownPaymentSettlementLn_ID");
        
    }
    
    
}
