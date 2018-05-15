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
/** Generated Model for XX_DownPayment
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_DownPayment extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_DownPayment_ID id
    @param trx transaction
    */
    public X_XX_DownPayment (Ctx ctx, int XX_DownPayment_ID, Trx trx)
    {
        super (ctx, XX_DownPayment_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_DownPayment_ID == 0)
        {
            setC_BPartner_ID (0);
            setC_Currency_ID (0);
            setDateTrx (new Timestamp(System.currentTimeMillis()));	// @#Date@
            setDocumentNo (null);
            setIsApproved (false);	// N
            setIsDPFixedAmt (false);	// N
            setIsDPPercentage (false);	// N
            setIsGenerateDPInvoice (false);	// N
            setIsSeparateTaxPayment (false);	// N
            setIsSOTrx (false);	// N
            setProcessed (false);	// N
            setTotalDPAmount (Env.ZERO);	// 0
            setXX_DownPayment_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_DownPayment (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27807403664803L;
    /** Last Updated Timestamp 2018-05-02 23:25:48.014 */
    public static final long updatedMS = 1525278348014L;
    /** AD_Table_ID=1000138 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_DownPayment");
        
    }
    ;
    
    /** TableName=XX_DownPayment */
    public static final String Table_Name="XX_DownPayment";
    
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
    
    /** Set Business Partner.
    @param C_BPartner_ID Identifies a Business Partner */
    public void setC_BPartner_ID (int C_BPartner_ID)
    {
        if (C_BPartner_ID < 1) throw new IllegalArgumentException ("C_BPartner_ID is mandatory.");
        set_ValueNoCheck ("C_BPartner_ID", Integer.valueOf(C_BPartner_ID));
        
    }
    
    /** Get Business Partner.
    @return Identifies a Business Partner */
    public int getC_BPartner_ID() 
    {
        return get_ValueAsInt("C_BPartner_ID");
        
    }
    
    /** Set Partner Location.
    @param C_BPartner_Location_ID Identifies the (ship to) address for this Business Partner */
    public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
    {
        if (C_BPartner_Location_ID <= 0) set_Value ("C_BPartner_Location_ID", null);
        else
        set_Value ("C_BPartner_Location_ID", Integer.valueOf(C_BPartner_Location_ID));
        
    }
    
    /** Get Partner Location.
    @return Identifies the (ship to) address for this Business Partner */
    public int getC_BPartner_Location_ID() 
    {
        return get_ValueAsInt("C_BPartner_Location_ID");
        
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
        set_ValueNoCheck ("C_Currency_ID", Integer.valueOf(C_Currency_ID));
        
    }
    
    /** Get Currency.
    @return The Currency for this record */
    public int getC_Currency_ID() 
    {
        return get_ValueAsInt("C_Currency_ID");
        
    }
    
    /** Set Order.
    @param C_Order_ID Order */
    public void setC_Order_ID (int C_Order_ID)
    {
        if (C_Order_ID <= 0) set_ValueNoCheck ("C_Order_ID", null);
        else
        set_ValueNoCheck ("C_Order_ID", Integer.valueOf(C_Order_ID));
        
    }
    
    /** Get Order.
    @return Order */
    public int getC_Order_ID() 
    {
        return get_ValueAsInt("C_Order_ID");
        
    }
    
    /** Set Payment Term.
    @param C_PaymentTerm_ID The terms of Payment (timing, discount) */
    public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
    {
        if (C_PaymentTerm_ID <= 0) set_Value ("C_PaymentTerm_ID", null);
        else
        set_Value ("C_PaymentTerm_ID", Integer.valueOf(C_PaymentTerm_ID));
        
    }
    
    /** Get Payment Term.
    @return The terms of Payment (timing, discount) */
    public int getC_PaymentTerm_ID() 
    {
        return get_ValueAsInt("C_PaymentTerm_ID");
        
    }
    
    /** Set Tax.
    @param C_Tax_ID Tax identifier */
    public void setC_Tax_ID (int C_Tax_ID)
    {
        if (C_Tax_ID <= 0) set_Value ("C_Tax_ID", null);
        else
        set_Value ("C_Tax_ID", Integer.valueOf(C_Tax_ID));
        
    }
    
    /** Get Tax.
    @return Tax identifier */
    public int getC_Tax_ID() 
    {
        return get_ValueAsInt("C_Tax_ID");
        
    }
    
    /** Set Transaction Date.
    @param DateTrx Transaction Date */
    public void setDateTrx (Timestamp DateTrx)
    {
        if (DateTrx == null) throw new IllegalArgumentException ("DateTrx is mandatory.");
        set_Value ("DateTrx", DateTrx);
        
    }
    
    /** Get Transaction Date.
    @return Transaction Date */
    public Timestamp getDateTrx() 
    {
        return (Timestamp)get_Value("DateTrx");
        
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
    
    /** Complete = CO */
    public static final String DOCACTION_Complete = X_Ref__Document_Action.COMPLETE.getValue();
    /** Approve = AP */
    public static final String DOCACTION_Approve = X_Ref__Document_Action.APPROVE.getValue();
    /** Reject = RJ */
    public static final String DOCACTION_Reject = X_Ref__Document_Action.REJECT.getValue();
    /** Post = PO */
    public static final String DOCACTION_Post = X_Ref__Document_Action.POST.getValue();
    /** Void = VO */
    public static final String DOCACTION_Void = X_Ref__Document_Action.VOID.getValue();
    /** Close = CL */
    public static final String DOCACTION_Close = X_Ref__Document_Action.CLOSE.getValue();
    /** Reverse - Correct = RC */
    public static final String DOCACTION_Reverse_Correct = X_Ref__Document_Action.REVERSE__CORRECT.getValue();
    /** Reverse - Accrual = RA */
    public static final String DOCACTION_Reverse_Accrual = X_Ref__Document_Action.REVERSE__ACCRUAL.getValue();
    /** Invalidate = IN */
    public static final String DOCACTION_Invalidate = X_Ref__Document_Action.INVALIDATE.getValue();
    /** Re-activate = RE */
    public static final String DOCACTION_Re_Activate = X_Ref__Document_Action.RE__ACTIVATE.getValue();
    /** <None> = -- */
    public static final String DOCACTION_None = X_Ref__Document_Action.NONE.getValue();
    /** Prepare = PR */
    public static final String DOCACTION_Prepare = X_Ref__Document_Action.PREPARE.getValue();
    /** Unlock = XL */
    public static final String DOCACTION_Unlock = X_Ref__Document_Action.UNLOCK.getValue();
    /** Wait Complete = WC */
    public static final String DOCACTION_WaitComplete = X_Ref__Document_Action.WAIT_COMPLETE.getValue();
    /** Set Document Action.
    @param DocAction The targeted status of the document */
    public void setDocAction (String DocAction)
    {
        if (!X_Ref__Document_Action.isValid(DocAction))
        throw new IllegalArgumentException ("DocAction Invalid value - " + DocAction + " - Reference_ID=135 - CO - AP - RJ - PO - VO - CL - RC - RA - IN - RE - -- - PR - XL - WC");
        set_Value ("DocAction", DocAction);
        
    }
    
    /** Get Document Action.
    @return The targeted status of the document */
    public String getDocAction() 
    {
        return (String)get_Value("DocAction");
        
    }
    
    /** Drafted = DR */
    public static final String DOCSTATUS_Drafted = X_Ref__Document_Status.DRAFTED.getValue();
    /** Completed = CO */
    public static final String DOCSTATUS_Completed = X_Ref__Document_Status.COMPLETED.getValue();
    /** Approved = AP */
    public static final String DOCSTATUS_Approved = X_Ref__Document_Status.APPROVED.getValue();
    /** Not Approved = NA */
    public static final String DOCSTATUS_NotApproved = X_Ref__Document_Status.NOT_APPROVED.getValue();
    /** Voided = VO */
    public static final String DOCSTATUS_Voided = X_Ref__Document_Status.VOIDED.getValue();
    /** Invalid = IN */
    public static final String DOCSTATUS_Invalid = X_Ref__Document_Status.INVALID.getValue();
    /** Reversed = RE */
    public static final String DOCSTATUS_Reversed = X_Ref__Document_Status.REVERSED.getValue();
    /** Closed = CL */
    public static final String DOCSTATUS_Closed = X_Ref__Document_Status.CLOSED.getValue();
    /** Unknown = ?? */
    public static final String DOCSTATUS_Unknown = X_Ref__Document_Status.UNKNOWN.getValue();
    /** In Progress = IP */
    public static final String DOCSTATUS_InProgress = X_Ref__Document_Status.IN_PROGRESS.getValue();
    /** Waiting Payment = WP */
    public static final String DOCSTATUS_WaitingPayment = X_Ref__Document_Status.WAITING_PAYMENT.getValue();
    /** Waiting Confirmation = WC */
    public static final String DOCSTATUS_WaitingConfirmation = X_Ref__Document_Status.WAITING_CONFIRMATION.getValue();
    /** Set Document Status.
    @param DocStatus The current status of the document */
    public void setDocStatus (String DocStatus)
    {
        if (!X_Ref__Document_Status.isValid(DocStatus))
        throw new IllegalArgumentException ("DocStatus Invalid value - " + DocStatus + " - Reference_ID=131 - DR - CO - AP - NA - VO - IN - RE - CL - ?? - IP - WP - WC");
        set_Value ("DocStatus", DocStatus);
        
    }
    
    /** Get Document Status.
    @return The current status of the document */
    public String getDocStatus() 
    {
        return (String)get_Value("DocStatus");
        
    }
    
    /** Set Document No.
    @param DocumentNo Document sequence number of the document */
    public void setDocumentNo (String DocumentNo)
    {
        if (DocumentNo == null) throw new IllegalArgumentException ("DocumentNo is mandatory.");
        set_Value ("DocumentNo", DocumentNo);
        
    }
    
    /** Get Document No.
    @return Document sequence number of the document */
    public String getDocumentNo() 
    {
        return (String)get_Value("DocumentNo");
        
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
    
    /** Set Invoice (DP).
    @param DPInvoice_ID Down Payment's invoice */
    public void setDPInvoice_ID (int DPInvoice_ID)
    {
        if (DPInvoice_ID <= 0) set_Value ("DPInvoice_ID", null);
        else
        set_Value ("DPInvoice_ID", Integer.valueOf(DPInvoice_ID));
        
    }
    
    /** Get Invoice (DP).
    @return Down Payment's invoice */
    public int getDPInvoice_ID() 
    {
        return get_ValueAsInt("DPInvoice_ID");
        
    }
    
    /** Set Down Payment Tax.
    @param DPTaxAmount Down Payment for tax charged to selected transaction */
    public void setDPTaxAmount (java.math.BigDecimal DPTaxAmount)
    {
        set_Value ("DPTaxAmount", DPTaxAmount);
        
    }
    
    /** Get Down Payment Tax.
    @return Down Payment for tax charged to selected transaction */
    public java.math.BigDecimal getDPTaxAmount() 
    {
        return get_ValueAsBigDecimal("DPTaxAmount");
        
    }
    
    /** Set Down Payment Transaction.
    @param DPTrxAmount Down payment for transaction only (exclude tax) */
    public void setDPTrxAmount (java.math.BigDecimal DPTrxAmount)
    {
        set_Value ("DPTrxAmount", DPTrxAmount);
        
    }
    
    /** Get Down Payment Transaction.
    @return Down payment for transaction only (exclude tax) */
    public java.math.BigDecimal getDPTrxAmount() 
    {
        return get_ValueAsBigDecimal("DPTrxAmount");
        
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
    
    /** Set Approved.
    @param IsApproved Indicates if this document requires approval */
    public void setIsApproved (boolean IsApproved)
    {
        set_Value ("IsApproved", Boolean.valueOf(IsApproved));
        
    }
    
    /** Get Approved.
    @return Indicates if this document requires approval */
    public boolean isApproved() 
    {
        return get_ValueAsBoolean("IsApproved");
        
    }
    
    /** Set Fixed Amount.
    @param IsDPFixedAmt Down payment is fixed amount */
    public void setIsDPFixedAmt (boolean IsDPFixedAmt)
    {
        set_Value ("IsDPFixedAmt", Boolean.valueOf(IsDPFixedAmt));
        
    }
    
    /** Get Fixed Amount.
    @return Down payment is fixed amount */
    public boolean isDPFixedAmt() 
    {
        return get_ValueAsBoolean("IsDPFixedAmt");
        
    }
    
    /** Set Percentage.
    @param IsDPPercentage Down Payment is calculated based on percentage */
    public void setIsDPPercentage (boolean IsDPPercentage)
    {
        set_Value ("IsDPPercentage", Boolean.valueOf(IsDPPercentage));
        
    }
    
    /** Get Percentage.
    @return Down Payment is calculated based on percentage */
    public boolean isDPPercentage() 
    {
        return get_ValueAsBoolean("IsDPPercentage");
        
    }
    
    /** Set Generate Invoice.
    @param IsGenerateDPInvoice Generate down payment's invoice */
    public void setIsGenerateDPInvoice (boolean IsGenerateDPInvoice)
    {
        set_Value ("IsGenerateDPInvoice", Boolean.valueOf(IsGenerateDPInvoice));
        
    }
    
    /** Get Generate Invoice.
    @return Generate down payment's invoice */
    public boolean isGenerateDPInvoice() 
    {
        return get_ValueAsBoolean("IsGenerateDPInvoice");
        
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
    
    /** Set Sales Transaction.
    @param IsSOTrx This is a Sales Transaction */
    public void setIsSOTrx (boolean IsSOTrx)
    {
        set_Value ("IsSOTrx", Boolean.valueOf(IsSOTrx));
        
    }
    
    /** Get Sales Transaction.
    @return This is a Sales Transaction */
    public boolean isSOTrx() 
    {
        return get_ValueAsBoolean("IsSOTrx");
        
    }
    
    /** Set Price List.
    @param M_PriceList_ID Unique identifier of a Price List */
    public void setM_PriceList_ID (int M_PriceList_ID)
    {
        if (M_PriceList_ID <= 0) set_Value ("M_PriceList_ID", null);
        else
        set_Value ("M_PriceList_ID", Integer.valueOf(M_PriceList_ID));
        
    }
    
    /** Get Price List.
    @return Unique identifier of a Price List */
    public int getM_PriceList_ID() 
    {
        return get_ValueAsInt("M_PriceList_ID");
        
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
    
    /** Cash = B */
    public static final String PAYMENTRULE_Cash = X_Ref__Payment_Rule.CASH.getValue();
    /** Credit Card = K */
    public static final String PAYMENTRULE_CreditCard = X_Ref__Payment_Rule.CREDIT_CARD.getValue();
    /** Direct Deposit = T */
    public static final String PAYMENTRULE_DirectDeposit = X_Ref__Payment_Rule.DIRECT_DEPOSIT.getValue();
    /** Check = S */
    public static final String PAYMENTRULE_Check = X_Ref__Payment_Rule.CHECK.getValue();
    /** On Credit = P */
    public static final String PAYMENTRULE_OnCredit = X_Ref__Payment_Rule.ON_CREDIT.getValue();
    /** Direct Debit = D */
    public static final String PAYMENTRULE_DirectDebit = X_Ref__Payment_Rule.DIRECT_DEBIT.getValue();
    /** Set Payment Method.
    @param PaymentRule How you pay the invoice */
    public void setPaymentRule (String PaymentRule)
    {
        if (!X_Ref__Payment_Rule.isValid(PaymentRule))
        throw new IllegalArgumentException ("PaymentRule Invalid value - " + PaymentRule + " - Reference_ID=195 - B - K - T - S - P - D");
        set_Value ("PaymentRule", PaymentRule);
        
    }
    
    /** Get Payment Method.
    @return How you pay the invoice */
    public String getPaymentRule() 
    {
        return (String)get_Value("PaymentRule");
        
    }
    
    /** Set Percentage.
    @param Percentage Percent of the entire amount */
    public void setPercentage (java.math.BigDecimal Percentage)
    {
        set_Value ("Percentage", Percentage);
        
    }
    
    /** Get Percentage.
    @return Percent of the entire amount */
    public java.math.BigDecimal getPercentage() 
    {
        return get_ValueAsBigDecimal("Percentage");
        
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
    
    /** Set Process Now.
    @param Processing Process Now */
    public void setProcessing (boolean Processing)
    {
        set_Value ("Processing", Boolean.valueOf(Processing));
        
    }
    
    /** Get Process Now.
    @return Process Now */
    public boolean isProcessing() 
    {
        return get_ValueAsBoolean("Processing");
        
    }
    
    /** Set Total Amount.
    @param TotalAmt Total Amount */
    public void setTotalAmt (java.math.BigDecimal TotalAmt)
    {
        set_Value ("TotalAmt", TotalAmt);
        
    }
    
    /** Get Total Amount.
    @return Total Amount */
    public java.math.BigDecimal getTotalAmt() 
    {
        return get_ValueAsBigDecimal("TotalAmt");
        
    }
    
    /** Set Total Down Payment.
    @param TotalDPAmount Total down payment (include tax) */
    public void setTotalDPAmount (java.math.BigDecimal TotalDPAmount)
    {
        if (TotalDPAmount == null) throw new IllegalArgumentException ("TotalDPAmount is mandatory.");
        set_Value ("TotalDPAmount", TotalDPAmount);
        
    }
    
    /** Get Total Down Payment.
    @return Total down payment (include tax) */
    public java.math.BigDecimal getTotalDPAmount() 
    {
        return get_ValueAsBigDecimal("TotalDPAmount");
        
    }
    
    /** Set Down Payment.
    @param XX_DownPayment_ID Down payment of an order */
    public void setXX_DownPayment_ID (int XX_DownPayment_ID)
    {
        if (XX_DownPayment_ID < 1) throw new IllegalArgumentException ("XX_DownPayment_ID is mandatory.");
        set_ValueNoCheck ("XX_DownPayment_ID", Integer.valueOf(XX_DownPayment_ID));
        
    }
    
    /** Get Down Payment.
    @return Down payment of an order */
    public int getXX_DownPayment_ID() 
    {
        return get_ValueAsInt("XX_DownPayment_ID");
        
    }
    
    
}
