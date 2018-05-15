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
/** Generated Model for XX_AdvPaymentOrderLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_AdvPaymentOrderLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_AdvPaymentOrderLine_ID id
    @param trx transaction
    */
    public X_XX_AdvPaymentOrderLine (Ctx ctx, int XX_AdvPaymentOrderLine_ID, Trx trx)
    {
        super (ctx, XX_AdvPaymentOrderLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_AdvPaymentOrderLine_ID == 0)
        {
            setAmount (Env.ZERO);	// 0
            setIsSeparateTaxPayment (false);	// @IsSeparateTaxPayment@
            setXX_AdvPaymentOrderLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_AdvPaymentOrderLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27676715704789L;
    /** Last Updated Timestamp 2014-03-12 09:13:08.0 */
    public static final long updatedMS = 1394590388000L;
    /** AD_Table_ID=1000201 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_AdvPaymentOrderLine");
        
    }
    ;
    
    /** TableName=XX_AdvPaymentOrderLine */
    public static final String Table_Name="XX_AdvPaymentOrderLine";
    
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
        if (Amount == null) throw new IllegalArgumentException ("Amount is mandatory.");
        set_Value ("Amount", Amount);
        
    }
    
    /** Get Amount.
    @return Amount in a defined currency */
    public java.math.BigDecimal getAmount() 
    {
        return get_ValueAsBigDecimal("Amount");
        
    }
    
    /** Set Charge.
    @param C_Charge_ID Additional document charges */
    public void setC_Charge_ID (int C_Charge_ID)
    {
        if (C_Charge_ID <= 0) set_Value ("C_Charge_ID", null);
        else
        set_Value ("C_Charge_ID", Integer.valueOf(C_Charge_ID));
        
    }
    
    /** Get Charge.
    @return Additional document charges */
    public int getC_Charge_ID() 
    {
        return get_ValueAsInt("C_Charge_ID");
        
    }
    
    /** Set Currency.
    @param C_Currency_ID The Currency for this record */
    public void setC_Currency_ID (int C_Currency_ID)
    {
        if (C_Currency_ID <= 0) set_Value ("C_Currency_ID", null);
        else
        set_Value ("C_Currency_ID", Integer.valueOf(C_Currency_ID));
        
    }
    
    /** Get Currency.
    @return The Currency for this record */
    public int getC_Currency_ID() 
    {
        return get_ValueAsInt("C_Currency_ID");
        
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
    
    /** Set Discount Amount.
    @param DiscountAmt Calculated amount of discount */
    public void setDiscountAmt (java.math.BigDecimal DiscountAmt)
    {
        set_Value ("DiscountAmt", DiscountAmt);
        
    }
    
    /** Get Discount Amount.
    @return Calculated amount of discount */
    public java.math.BigDecimal getDiscountAmt() 
    {
        return get_ValueAsBigDecimal("DiscountAmt");
        
    }
    
    /** Set Separate Tax Payment.
    @param IsSeparateTaxPayment Separate between transaction and tax payment */
    public void setIsSeparateTaxPayment (boolean IsSeparateTaxPayment)
    {
        set_Value ("IsSeparateTaxPayment", Boolean.valueOf(IsSeparateTaxPayment));
        
    }
    
    /** Get Separate Tax Payment.
    @return Separate between transaction and tax payment */
    public boolean isSeparateTaxPayment() 
    {
        return get_ValueAsBoolean("IsSeparateTaxPayment");
        
    }
    
    /** Set Payment amount.
    @param PayAmt Amount being paid */
    public void setPayAmt (java.math.BigDecimal PayAmt)
    {
        set_Value ("PayAmt", PayAmt);
        
    }
    
    /** Get Payment amount.
    @return Amount being paid */
    public java.math.BigDecimal getPayAmt() 
    {
        return get_ValueAsBigDecimal("PayAmt");
        
    }
    
    /** Set Withholding Amount.
    @param WithholdingAmt Withholding amount of transaction (tax withholding) */
    public void setWithholdingAmt (java.math.BigDecimal WithholdingAmt)
    {
        set_Value ("WithholdingAmt", WithholdingAmt);
        
    }
    
    /** Get Withholding Amount.
    @return Withholding amount of transaction (tax withholding) */
    public java.math.BigDecimal getWithholdingAmt() 
    {
        return get_ValueAsBigDecimal("WithholdingAmt");
        
    }
    
    /** Set Write-off Amount.
    @param WriteOffAmt Amount to write-off */
    public void setWriteOffAmt (java.math.BigDecimal WriteOffAmt)
    {
        set_Value ("WriteOffAmt", WriteOffAmt);
        
    }
    
    /** Get Write-off Amount.
    @return Amount to write-off */
    public java.math.BigDecimal getWriteOffAmt() 
    {
        return get_ValueAsBigDecimal("WriteOffAmt");
        
    }
    
    /** Set Payment Order.
    @param XX_AdvancePaymentOrder_ID Order to issue payment */
    public void setXX_AdvancePaymentOrder_ID (int XX_AdvancePaymentOrder_ID)
    {
        if (XX_AdvancePaymentOrder_ID <= 0) set_Value ("XX_AdvancePaymentOrder_ID", null);
        else
        set_Value ("XX_AdvancePaymentOrder_ID", Integer.valueOf(XX_AdvancePaymentOrder_ID));
        
    }
    
    /** Get Payment Order.
    @return Order to issue payment */
    public int getXX_AdvancePaymentOrder_ID() 
    {
        return get_ValueAsInt("XX_AdvancePaymentOrder_ID");
        
    }
    
    /** Set Payment Order Line.
    @param XX_AdvPaymentOrderLine_ID Payment Order Line */
    public void setXX_AdvPaymentOrderLine_ID (int XX_AdvPaymentOrderLine_ID)
    {
        if (XX_AdvPaymentOrderLine_ID < 1) throw new IllegalArgumentException ("XX_AdvPaymentOrderLine_ID is mandatory.");
        set_ValueNoCheck ("XX_AdvPaymentOrderLine_ID", Integer.valueOf(XX_AdvPaymentOrderLine_ID));
        
    }
    
    /** Get Payment Order Line.
    @return Payment Order Line */
    public int getXX_AdvPaymentOrderLine_ID() 
    {
        return get_ValueAsInt("XX_AdvPaymentOrderLine_ID");
        
    }
    
    
}
