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
/** Generated Model for XX_DRConfirmationLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_DRConfirmationLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_DRConfirmationLine_ID id
    @param trx transaction
    */
    public X_XX_DRConfirmationLine (Ctx ctx, int XX_DRConfirmationLine_ID, Trx trx)
    {
        super (ctx, XX_DRConfirmationLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_DRConfirmationLine_ID == 0)
        {
            setAmount (Env.ZERO);	// 0
            setPaymentRule (null);	// B
            setXX_DRConfirmation_ID (0);
            setXX_DRConfirmationLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_DRConfirmationLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27801798356280L;
    /** Last Updated Timestamp 2018-02-27 02:23:59.491 */
    public static final long updatedMS = 1519673039491L;
    /** AD_Table_ID=1000206 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_DRConfirmationLine");
        
    }
    ;
    
    /** TableName=XX_DRConfirmationLine */
    public static final String Table_Name="XX_DRConfirmationLine";
    
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
    
    /** Set Bank.
    @param C_Bank_ID Bank */
    public void setC_Bank_ID (int C_Bank_ID)
    {
        if (C_Bank_ID <= 0) set_Value ("C_Bank_ID", null);
        else
        set_Value ("C_Bank_ID", Integer.valueOf(C_Bank_ID));
        
    }
    
    /** Get Bank.
    @return Bank */
    public int getC_Bank_ID() 
    {
        return get_ValueAsInt("C_Bank_ID");
        
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
    
    /** Set Bank statement line.
    @param C_BankStatementLine_ID Line on a statement from this Bank */
    public void setC_BankStatementLine_ID (int C_BankStatementLine_ID)
    {
        if (C_BankStatementLine_ID <= 0) set_Value ("C_BankStatementLine_ID", null);
        else
        set_Value ("C_BankStatementLine_ID", Integer.valueOf(C_BankStatementLine_ID));
        
    }
    
    /** Get Bank statement line.
    @return Line on a statement from this Bank */
    public int getC_BankStatementLine_ID() 
    {
        return get_ValueAsInt("C_BankStatementLine_ID");
        
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
    
    /** Set Cash Journal Line.
    @param C_CashLine_ID Cash Journal Line */
    public void setC_CashLine_ID (int C_CashLine_ID)
    {
        if (C_CashLine_ID <= 0) set_Value ("C_CashLine_ID", null);
        else
        set_Value ("C_CashLine_ID", Integer.valueOf(C_CashLine_ID));
        
    }
    
    /** Get Cash Journal Line.
    @return Cash Journal Line */
    public int getC_CashLine_ID() 
    {
        return get_ValueAsInt("C_CashLine_ID");
        
    }
    
    /** Bank Transfer = B */
    public static final String PAYMENTRULE_BankTransfer = X_Ref__Payment_Rule__Bank_or_Cash_.BANK_TRANSFER.getValue();
    /** Cash Payment = C */
    public static final String PAYMENTRULE_CashPayment = X_Ref__Payment_Rule__Bank_or_Cash_.CASH_PAYMENT.getValue();
    /** Set Payment Method.
    @param PaymentRule How you pay the invoice */
    public void setPaymentRule (String PaymentRule)
    {
        if (PaymentRule == null) throw new IllegalArgumentException ("PaymentRule is mandatory");
        if (!X_Ref__Payment_Rule__Bank_or_Cash_.isValid(PaymentRule))
        throw new IllegalArgumentException ("PaymentRule Invalid value - " + PaymentRule + " - Reference_ID=1000300 - B - C");
        set_Value ("PaymentRule", PaymentRule);
        
    }
    
    /** Get Payment Method.
    @return How you pay the invoice */
    public String getPaymentRule() 
    {
        return (String)get_Value("PaymentRule");
        
    }
    
    /** Set DR Confirmation.
    @param XX_DRConfirmation_ID Confirmation of disbursement realization */
    public void setXX_DRConfirmation_ID (int XX_DRConfirmation_ID)
    {
        if (XX_DRConfirmation_ID < 1) throw new IllegalArgumentException ("XX_DRConfirmation_ID is mandatory.");
        set_Value ("XX_DRConfirmation_ID", Integer.valueOf(XX_DRConfirmation_ID));
        
    }
    
    /** Get DR Confirmation.
    @return Confirmation of disbursement realization */
    public int getXX_DRConfirmation_ID() 
    {
        return get_ValueAsInt("XX_DRConfirmation_ID");
        
    }
    
    /** Set XX_DRConfirmationLine_ID.
    @param XX_DRConfirmationLine_ID XX_DRConfirmationLine_ID */
    public void setXX_DRConfirmationLine_ID (int XX_DRConfirmationLine_ID)
    {
        if (XX_DRConfirmationLine_ID < 1) throw new IllegalArgumentException ("XX_DRConfirmationLine_ID is mandatory.");
        set_ValueNoCheck ("XX_DRConfirmationLine_ID", Integer.valueOf(XX_DRConfirmationLine_ID));
        
    }
    
    /** Get XX_DRConfirmationLine_ID.
    @return XX_DRConfirmationLine_ID */
    public int getXX_DRConfirmationLine_ID() 
    {
        return get_ValueAsInt("XX_DRConfirmationLine_ID");
        
    }
    
    
}
