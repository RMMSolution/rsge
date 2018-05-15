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
/** Generated Model for XX_APLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_APLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_APLine_ID id
    @param trx transaction
    */
    public X_XX_APLine (Ctx ctx, int XX_APLine_ID, Trx trx)
    {
        super (ctx, XX_APLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_APLine_ID == 0)
        {
            setC_Invoice_ID (0);
            setConvertedAmt (Env.ZERO);	// 0
            setXX_APLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_APLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27673756460789L;
    /** Last Updated Timestamp 2014-02-06 03:12:24.0 */
    public static final long updatedMS = 1391631144000L;
    /** AD_Table_ID=1000511 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_APLine");
        
    }
    ;
    
    /** TableName=XX_APLine */
    public static final String Table_Name="XX_APLine";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Applied Amount.
    @param AppliedAmt Amount applied to this document */
    public void setAppliedAmt (java.math.BigDecimal AppliedAmt)
    {
        set_Value ("AppliedAmt", AppliedAmt);
        
    }
    
    /** Get Applied Amount.
    @return Amount applied to this document */
    public java.math.BigDecimal getAppliedAmt() 
    {
        return get_ValueAsBigDecimal("AppliedAmt");
        
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
        if (C_Invoice_ID < 1) throw new IllegalArgumentException ("C_Invoice_ID is mandatory.");
        set_Value ("C_Invoice_ID", Integer.valueOf(C_Invoice_ID));
        
    }
    
    /** Get Invoice.
    @return Invoice Identifier */
    public int getC_Invoice_ID() 
    {
        return get_ValueAsInt("C_Invoice_ID");
        
    }
    
    /** Set Converted Amount.
    @param ConvertedAmt Converted Amount */
    public void setConvertedAmt (java.math.BigDecimal ConvertedAmt)
    {
        if (ConvertedAmt == null) throw new IllegalArgumentException ("ConvertedAmt is mandatory.");
        set_Value ("ConvertedAmt", ConvertedAmt);
        
    }
    
    /** Get Converted Amount.
    @return Converted Amount */
    public java.math.BigDecimal getConvertedAmt() 
    {
        return get_ValueAsBigDecimal("ConvertedAmt");
        
    }
    
    /** Set Payment.
    @param C_Payment_ID Payment identifier */
    public void setC_Payment_ID (int C_Payment_ID)
    {
        if (C_Payment_ID <= 0) set_Value ("C_Payment_ID", null);
        else
        set_Value ("C_Payment_ID", Integer.valueOf(C_Payment_ID));
        
    }
    
    /** Get Payment.
    @return Payment identifier */
    public int getC_Payment_ID() 
    {
        return get_ValueAsInt("C_Payment_ID");
        
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
    
    /** Set Account Payable.
    @param XX_APLine_ID Account Payable */
    public void setXX_APLine_ID (int XX_APLine_ID)
    {
        if (XX_APLine_ID < 1) throw new IllegalArgumentException ("XX_APLine_ID is mandatory.");
        set_ValueNoCheck ("XX_APLine_ID", Integer.valueOf(XX_APLine_ID));
        
    }
    
    /** Get Account Payable.
    @return Account Payable */
    public int getXX_APLine_ID() 
    {
        return get_ValueAsInt("XX_APLine_ID");
        
    }
    
    /** Set AR/AP Offset.
    @param XX_ARAPOffset_ID Account Receivable - Account Payable Offset */
    public void setXX_ARAPOffset_ID (int XX_ARAPOffset_ID)
    {
        if (XX_ARAPOffset_ID <= 0) set_Value ("XX_ARAPOffset_ID", null);
        else
        set_Value ("XX_ARAPOffset_ID", Integer.valueOf(XX_ARAPOffset_ID));
        
    }
    
    /** Get AR/AP Offset.
    @return Account Receivable - Account Payable Offset */
    public int getXX_ARAPOffset_ID() 
    {
        return get_ValueAsInt("XX_ARAPOffset_ID");
        
    }
    
    
}
