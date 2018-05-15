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
/** Generated Model for XX_ARAPOffset
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_ARAPOffset extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_ARAPOffset_ID id
    @param trx transaction
    */
    public X_XX_ARAPOffset (Ctx ctx, int XX_ARAPOffset_ID, Trx trx)
    {
        super (ctx, XX_ARAPOffset_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_ARAPOffset_ID == 0)
        {
            setDifferenceAmt (Env.ZERO);	// 0
            setDocumentNo (null);
            setIsApproved (false);	// N
            setProcessed (false);	// N
            setTotalAP (Env.ZERO);	// 0
            setTotalAR (Env.ZERO);	// 0
            setXX_ARAPOffset_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_ARAPOffset (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664551445789L;
    /** Last Updated Timestamp 2013-10-22 14:15:29.0 */
    public static final long updatedMS = 1382426129000L;
    /** AD_Table_ID=1000512 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_ARAPOffset");
        
    }
    ;
    
    /** TableName=XX_ARAPOffset */
    public static final String Table_Name="XX_ARAPOffset";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set AP Settlement.
    @param APSettlementCharge_ID Account Payable''s Settlement Charge Account */
    public void setAPSettlementCharge_ID (int APSettlementCharge_ID)
    {
        if (APSettlementCharge_ID <= 0) set_Value ("APSettlementCharge_ID", null);
        else
        set_Value ("APSettlementCharge_ID", Integer.valueOf(APSettlementCharge_ID));
        
    }
    
    /** Get AP Settlement.
    @return Account Payable''s Settlement Charge Account */
    public int getAPSettlementCharge_ID() 
    {
        return get_ValueAsInt("APSettlementCharge_ID");
        
    }
    
    /** Set AR Settlement.
    @param ARSettlementCharge_ID Account Receivable''s Settlement Charge Account */
    public void setARSettlementCharge_ID (int ARSettlementCharge_ID)
    {
        if (ARSettlementCharge_ID <= 0) set_Value ("ARSettlementCharge_ID", null);
        else
        set_Value ("ARSettlementCharge_ID", Integer.valueOf(ARSettlementCharge_ID));
        
    }
    
    /** Get AR Settlement.
    @return Account Receivable''s Settlement Charge Account */
    public int getARSettlementCharge_ID() 
    {
        return get_ValueAsInt("ARSettlementCharge_ID");
        
    }
    
    /** Set Business Partner.
    @param C_BPartner_ID Identifies a Business Partner */
    public void setC_BPartner_ID (int C_BPartner_ID)
    {
        if (C_BPartner_ID <= 0) set_Value ("C_BPartner_ID", null);
        else
        set_Value ("C_BPartner_ID", Integer.valueOf(C_BPartner_ID));
        
    }
    
    /** Get Business Partner.
    @return Identifies a Business Partner */
    public int getC_BPartner_ID() 
    {
        return get_ValueAsInt("C_BPartner_ID");
        
    }
    
    /** Set Currency.
    @param C_Currency_ID The Currency for this record */
    public void setC_Currency_ID (int C_Currency_ID)
    {
        if (C_Currency_ID <= 0) set_ValueNoCheck ("C_Currency_ID", null);
        else
        set_ValueNoCheck ("C_Currency_ID", Integer.valueOf(C_Currency_ID));
        
    }
    
    /** Get Currency.
    @return The Currency for this record */
    public int getC_Currency_ID() 
    {
        return get_ValueAsInt("C_Currency_ID");
        
    }
    
    /** Set Document Date.
    @param DateDoc Date of the Document */
    public void setDateDoc (Timestamp DateDoc)
    {
        set_Value ("DateDoc", DateDoc);
        
    }
    
    /** Get Document Date.
    @return Date of the Document */
    public Timestamp getDateDoc() 
    {
        return (Timestamp)get_Value("DateDoc");
        
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
    
    /** Set Difference.
    @param DifferenceAmt Difference Amount */
    public void setDifferenceAmt (java.math.BigDecimal DifferenceAmt)
    {
        if (DifferenceAmt == null) throw new IllegalArgumentException ("DifferenceAmt is mandatory.");
        set_Value ("DifferenceAmt", DifferenceAmt);
        
    }
    
    /** Get Difference.
    @return Difference Amount */
    public java.math.BigDecimal getDifferenceAmt() 
    {
        return get_ValueAsBigDecimal("DifferenceAmt");
        
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
    /** Unlock = XL */
    public static final String DOCACTION_Unlock = X_Ref__Document_Action.UNLOCK.getValue();
    /** Wait Complete = WC */
    public static final String DOCACTION_WaitComplete = X_Ref__Document_Action.WAIT_COMPLETE.getValue();
    /** Prepare = PR */
    public static final String DOCACTION_Prepare = X_Ref__Document_Action.PREPARE.getValue();
    /** Set Document Action.
    @param DocAction The targeted status of the document */
    public void setDocAction (String DocAction)
    {
        if (!X_Ref__Document_Action.isValid(DocAction))
        throw new IllegalArgumentException ("DocAction Invalid value - " + DocAction + " - Reference_ID=135 - CO - AP - RJ - PO - VO - CL - RC - RA - IN - RE - -- - XL - WC - PR");
        set_Value ("DocAction", DocAction);
        
    }
    
    /** Get Document Action.
    @return The targeted status of the document */
    public String getDocAction() 
    {
        return (String)get_Value("DocAction");
        
    }
    
    /** Unknown = ?? */
    public static final String DOCSTATUS_Unknown = X_Ref__Document_Status.UNKNOWN.getValue();
    /** Approved = AP */
    public static final String DOCSTATUS_Approved = X_Ref__Document_Status.APPROVED.getValue();
    /** Closed = CL */
    public static final String DOCSTATUS_Closed = X_Ref__Document_Status.CLOSED.getValue();
    /** Completed = CO */
    public static final String DOCSTATUS_Completed = X_Ref__Document_Status.COMPLETED.getValue();
    /** Drafted = DR */
    public static final String DOCSTATUS_Drafted = X_Ref__Document_Status.DRAFTED.getValue();
    /** Invalid = IN */
    public static final String DOCSTATUS_Invalid = X_Ref__Document_Status.INVALID.getValue();
    /** In Progress = IP */
    public static final String DOCSTATUS_InProgress = X_Ref__Document_Status.IN_PROGRESS.getValue();
    /** Not Approved = NA */
    public static final String DOCSTATUS_NotApproved = X_Ref__Document_Status.NOT_APPROVED.getValue();
    /** Reversed = RE */
    public static final String DOCSTATUS_Reversed = X_Ref__Document_Status.REVERSED.getValue();
    /** Voided = VO */
    public static final String DOCSTATUS_Voided = X_Ref__Document_Status.VOIDED.getValue();
    /** Waiting Confirmation = WC */
    public static final String DOCSTATUS_WaitingConfirmation = X_Ref__Document_Status.WAITING_CONFIRMATION.getValue();
    /** Waiting Payment = WP */
    public static final String DOCSTATUS_WaitingPayment = X_Ref__Document_Status.WAITING_PAYMENT.getValue();
    /** Set Document Status.
    @param DocStatus The current status of the document */
    public void setDocStatus (String DocStatus)
    {
        if (!X_Ref__Document_Status.isValid(DocStatus))
        throw new IllegalArgumentException ("DocStatus Invalid value - " + DocStatus + " - Reference_ID=131 - ?? - AP - CL - CO - DR - IN - IP - NA - RE - VO - WC - WP");
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
    
    /** Set Total AP Amount.
    @param TotalAP Total Account Payable Amount */
    public void setTotalAP (java.math.BigDecimal TotalAP)
    {
        if (TotalAP == null) throw new IllegalArgumentException ("TotalAP is mandatory.");
        set_Value ("TotalAP", TotalAP);
        
    }
    
    /** Get Total AP Amount.
    @return Total Account Payable Amount */
    public java.math.BigDecimal getTotalAP() 
    {
        return get_ValueAsBigDecimal("TotalAP");
        
    }
    
    /** Set Total AR Amount.
    @param TotalAR Total amount of account receivable */
    public void setTotalAR (java.math.BigDecimal TotalAR)
    {
        if (TotalAR == null) throw new IllegalArgumentException ("TotalAR is mandatory.");
        set_Value ("TotalAR", TotalAR);
        
    }
    
    /** Get Total AR Amount.
    @return Total amount of account receivable */
    public java.math.BigDecimal getTotalAR() 
    {
        return get_ValueAsBigDecimal("TotalAR");
        
    }
    
    /** Set AR/AP Offset.
    @param XX_ARAPOffset_ID Account Receivable - Account Payable Offset */
    public void setXX_ARAPOffset_ID (int XX_ARAPOffset_ID)
    {
        if (XX_ARAPOffset_ID < 1) throw new IllegalArgumentException ("XX_ARAPOffset_ID is mandatory.");
        set_ValueNoCheck ("XX_ARAPOffset_ID", Integer.valueOf(XX_ARAPOffset_ID));
        
    }
    
    /** Get AR/AP Offset.
    @return Account Receivable - Account Payable Offset */
    public int getXX_ARAPOffset_ID() 
    {
        return get_ValueAsInt("XX_ARAPOffset_ID");
        
    }
    
    
}
