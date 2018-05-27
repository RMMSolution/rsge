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
/** Generated Model for XX_DownPaymentPlan
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_DownPaymentPlan extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_DownPaymentPlan_ID id
    @param trx transaction
    */
    public X_XX_DownPaymentPlan (Ctx ctx, int XX_DownPaymentPlan_ID, Trx trx)
    {
        super (ctx, XX_DownPaymentPlan_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_DownPaymentPlan_ID == 0)
        {
            setAllocatedAmt (Env.ZERO);	// 0
            setC_Currency_ID (0);	// @C_Currency_ID@
            setIsDifferentCurrency (false);	// N
            setIsTaxPayment (false);	// N
            setIsTrxPayment (false);	// N
            setPaymentRule (null);	// T
            setProcessed (false);	// N
            setXX_DownPaymentPlan_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_DownPaymentPlan (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27809478536641L;
    /** Last Updated Timestamp 2018-05-26 23:46:59.852 */
    public static final long updatedMS = 1527353219852L;
    /** AD_Table_ID=1000141 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_DownPaymentPlan");
        
    }
    ;
    
    /** TableName=XX_DownPaymentPlan */
    public static final String Table_Name="XX_DownPaymentPlan";
    
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
        if (AllocatedAmt == null) throw new IllegalArgumentException ("AllocatedAmt is mandatory.");
        set_Value ("AllocatedAmt", AllocatedAmt);
        
    }
    
    /** Get Allocated Amount.
    @return Amount allocated to this document */
    public java.math.BigDecimal getAllocatedAmt() 
    {
        return get_ValueAsBigDecimal("AllocatedAmt");
        
    }
    
    /** Set Bank Account.
    @param C_BankAccount_ID Account at the Bank */
    public void setC_BankAccount_ID (int C_BankAccount_ID)
    {
        if (C_BankAccount_ID <= 0) set_Value ("C_BankAccount_ID", null);
        else
        set_Value ("C_BankAccount_ID", Integer.valueOf(C_BankAccount_ID));
        
    }
    
    /** Get Bank Account.
    @return Account at the Bank */
    public int getC_BankAccount_ID() 
    {
        return get_ValueAsInt("C_BankAccount_ID");
        
    }
    
    /** Set Cash Journal.
    @param C_Cash_ID Cash Journal */
    public void setC_Cash_ID (int C_Cash_ID)
    {
        if (C_Cash_ID <= 0) set_Value ("C_Cash_ID", null);
        else
        set_Value ("C_Cash_ID", Integer.valueOf(C_Cash_ID));
        
    }
    
    /** Get Cash Journal.
    @return Cash Journal */
    public int getC_Cash_ID() 
    {
        return get_ValueAsInt("C_Cash_ID");
        
    }
    
    /** Set Cash Book.
    @param C_CashBook_ID Cash Book for recording petty cash transactions */
    public void setC_CashBook_ID (int C_CashBook_ID)
    {
        if (C_CashBook_ID <= 0) set_Value ("C_CashBook_ID", null);
        else
        set_Value ("C_CashBook_ID", Integer.valueOf(C_CashBook_ID));
        
    }
    
    /** Get Cash Book.
    @return Cash Book for recording petty cash transactions */
    public int getC_CashBook_ID() 
    {
        return get_ValueAsInt("C_CashBook_ID");
        
    }
    
    /** Set Rate Type.
    @param C_ConversionType_ID Currency Conversion Rate Type */
    public void setC_ConversionType_ID (int C_ConversionType_ID)
    {
        if (C_ConversionType_ID <= 0) set_Value ("C_ConversionType_ID", null);
        else
        set_Value ("C_ConversionType_ID", Integer.valueOf(C_ConversionType_ID));
        
    }
    
    /** Get Rate Type.
    @return Currency Conversion Rate Type */
    public int getC_ConversionType_ID() 
    {
        return get_ValueAsInt("C_ConversionType_ID");
        
    }
    
    /** Set Currency.
    @param C_Currency_ID The Currency for this record */
    public void setC_Currency_ID (int C_Currency_ID)
    {
        if (C_Currency_ID < 1) throw new IllegalArgumentException ("C_Currency_ID is mandatory.");
        set_Value ("C_Currency_ID", Integer.valueOf(C_Currency_ID));
        
    }
    
    /** Get Currency.
    @return The Currency for this record */
    public int getC_Currency_ID() 
    {
        return get_ValueAsInt("C_Currency_ID");
        
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
    
    /** Set Check No.
    @param CheckNo Check Number */
    public void setCheckNo (String CheckNo)
    {
        set_Value ("CheckNo", CheckNo);
        
    }
    
    /** Get Check No.
    @return Check Number */
    public String getCheckNo() 
    {
        return (String)get_Value("CheckNo");
        
    }
    
    /** Set Converted Amount.
    @param ConvertedAmt Converted Amount */
    public void setConvertedAmt (java.math.BigDecimal ConvertedAmt)
    {
        set_Value ("ConvertedAmt", ConvertedAmt);
        
    }
    
    /** Get Converted Amount.
    @return Converted Amount */
    public java.math.BigDecimal getConvertedAmt() 
    {
        return get_ValueAsBigDecimal("ConvertedAmt");
        
    }
    
    /** Set Description.
    @param Description Optional short description of the record */
    public void setDescription (String Description)
    {
        set_Value ("Description", Description);
        
    }
    
    /** Get Description.
    @return Optional short description of the record */
    public String getDescription() 
    {
        return (String)get_Value("Description");
        
    }
    
    /** Set Different Currency.
    @param IsDifferentCurrency Invoice currency is different than payment currency */
    public void setIsDifferentCurrency (boolean IsDifferentCurrency)
    {
        set_Value ("IsDifferentCurrency", Boolean.valueOf(IsDifferentCurrency));
        
    }
    
    /** Get Different Currency.
    @return Invoice currency is different than payment currency */
    public boolean isDifferentCurrency() 
    {
        return get_ValueAsBoolean("IsDifferentCurrency");
        
    }
    
    /** Set Tax Payment.
    @param IsTaxPayment This payment is intended for tax */
    public void setIsTaxPayment (boolean IsTaxPayment)
    {
        set_Value ("IsTaxPayment", Boolean.valueOf(IsTaxPayment));
        
    }
    
    /** Get Tax Payment.
    @return This payment is intended for tax */
    public boolean isTaxPayment() 
    {
        return get_ValueAsBoolean("IsTaxPayment");
        
    }
    
    /** Set Transaction Payment.
    @param IsTrxPayment This payment is intended for transaction */
    public void setIsTrxPayment (boolean IsTrxPayment)
    {
        set_Value ("IsTrxPayment", Boolean.valueOf(IsTrxPayment));
        
    }
    
    /** Get Transaction Payment.
    @return This payment is intended for transaction */
    public boolean isTrxPayment() 
    {
        return get_ValueAsBoolean("IsTrxPayment");
        
    }
    
    /** Down Payment = D */
    public static final String PAYMENTRULE_DownPayment = X_Ref__Down_Payment_Rule.DOWN_PAYMENT.getValue();
    /** Check = S */
    public static final String PAYMENTRULE_Check = X_Ref__Down_Payment_Rule.CHECK.getValue();
    /** Direct Deposit = T */
    public static final String PAYMENTRULE_DirectDeposit = X_Ref__Down_Payment_Rule.DIRECT_DEPOSIT.getValue();
    /** Set Payment Method.
    @param PaymentRule How you pay the invoice */
    public void setPaymentRule (String PaymentRule)
    {
        if (PaymentRule == null) throw new IllegalArgumentException ("PaymentRule is mandatory");
        if (!X_Ref__Down_Payment_Rule.isValid(PaymentRule))
        throw new IllegalArgumentException ("PaymentRule Invalid value - " + PaymentRule + " - Reference_ID=1000111 - D - S - T");
        set_ValueNoCheck ("PaymentRule", PaymentRule);
        
    }
    
    /** Get Payment Method.
    @return How you pay the invoice */
    public String getPaymentRule() 
    {
        return (String)get_Value("PaymentRule");
        
    }
    
    /** Set Processed.
    @param Processed The document has been processed */
    public void setProcessed (boolean Processed)
    {
        set_Value ("Processed", Boolean.valueOf(Processed));
        
    }
    
    /** Get Processed.
    @return The document has been processed */
    public boolean isProcessed() 
    {
        return get_ValueAsBoolean("Processed");
        
    }
    
    /** Set Rate.
    @param Rate Rate or Tax or Exchange */
    public void setRate (java.math.BigDecimal Rate)
    {
        set_Value ("Rate", Rate);
        
    }
    
    /** Get Rate.
    @return Rate or Tax or Exchange */
    public java.math.BigDecimal getRate() 
    {
        return get_ValueAsBigDecimal("Rate");
        
    }
    
    /** Set Remaining Amt.
    @param RemainingAmt Remaining Amount */
    public void setRemainingAmt (java.math.BigDecimal RemainingAmt)
    {
        set_Value ("RemainingAmt", RemainingAmt);
        
    }
    
    /** Get Remaining Amt.
    @return Remaining Amount */
    public java.math.BigDecimal getRemainingAmt() 
    {
        return get_ValueAsBigDecimal("RemainingAmt");
        
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
    
    /** Set Down Payment Plan.
    @param XX_DownPaymentPlan_ID Down payment plan */
    public void setXX_DownPaymentPlan_ID (int XX_DownPaymentPlan_ID)
    {
        if (XX_DownPaymentPlan_ID < 1) throw new IllegalArgumentException ("XX_DownPaymentPlan_ID is mandatory.");
        set_ValueNoCheck ("XX_DownPaymentPlan_ID", Integer.valueOf(XX_DownPaymentPlan_ID));
        
    }
    
    /** Get Down Payment Plan.
    @return Down payment plan */
    public int getXX_DownPaymentPlan_ID() 
    {
        return get_ValueAsInt("XX_DownPaymentPlan_ID");
        
    }
    
    
}
