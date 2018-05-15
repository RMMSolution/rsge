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
/** Generated Model for XX_DisbursementRealization
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_DisbursementRealization extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_DisbursementRealization_ID id
    @param trx transaction
    */
    public X_XX_DisbursementRealization (Ctx ctx, int XX_DisbursementRealization_ID, Trx trx)
    {
        super (ctx, XX_DisbursementRealization_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_DisbursementRealization_ID == 0)
        {
            setAD_User_ID (0);	// @AD_User_ID@
            setDateDoc (new Timestamp(System.currentTimeMillis()));	// @#Date@
            setDifferenceAmt (Env.ZERO);	// 0
            setDocAction (null);	// CO
            setDocumentNo (null);
            setIsApproved (false);	// N
            setIsSettled (false);	// N
            setProcessed (false);	// N
            setXX_AdvanceDisbursement_ID (0);
            setXX_DisbursementRealization_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_DisbursementRealization (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27801789653829L;
    /** Last Updated Timestamp 2018-02-26 23:58:57.04 */
    public static final long updatedMS = 1519664337040L;
    /** AD_Table_ID=1000134 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_DisbursementRealization");
        
    }
    ;
    
    /** TableName=XX_DisbursementRealization */
    public static final String Table_Name="XX_DisbursementRealization";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set User/Contact.
    @param AD_User_ID User within the system - Internal or Business Partner Contact */
    public void setAD_User_ID (int AD_User_ID)
    {
        if (AD_User_ID < 1) throw new IllegalArgumentException ("AD_User_ID is mandatory.");
        set_ValueNoCheck ("AD_User_ID", Integer.valueOf(AD_User_ID));
        
    }
    
    /** Get User/Contact.
    @return User within the system - Internal or Business Partner Contact */
    public int getAD_User_ID() 
    {
        return get_ValueAsInt("AD_User_ID");
        
    }
    
    /** Set Activity.
    @param C_Activity_ID Business Activity */
    public void setC_Activity_ID (int C_Activity_ID)
    {
        if (C_Activity_ID <= 0) set_Value ("C_Activity_ID", null);
        else
        set_Value ("C_Activity_ID", Integer.valueOf(C_Activity_ID));
        
    }
    
    /** Get Activity.
    @return Business Activity */
    public int getC_Activity_ID() 
    {
        return get_ValueAsInt("C_Activity_ID");
        
    }
    
    /** Set Allocation Line.
    @param C_AllocationLine_ID Allocation Line */
    public void setC_AllocationLine_ID (int C_AllocationLine_ID)
    {
        if (C_AllocationLine_ID <= 0) set_Value ("C_AllocationLine_ID", null);
        else
        set_Value ("C_AllocationLine_ID", Integer.valueOf(C_AllocationLine_ID));
        
    }
    
    /** Get Allocation Line.
    @return Allocation Line */
    public int getC_AllocationLine_ID() 
    {
        return get_ValueAsInt("C_AllocationLine_ID");
        
    }
    
    /** Set Campaign.
    @param C_Campaign_ID Marketing Campaign */
    public void setC_Campaign_ID (int C_Campaign_ID)
    {
        if (C_Campaign_ID <= 0) set_Value ("C_Campaign_ID", null);
        else
        set_Value ("C_Campaign_ID", Integer.valueOf(C_Campaign_ID));
        
    }
    
    /** Get Campaign.
    @return Marketing Campaign */
    public int getC_Campaign_ID() 
    {
        return get_ValueAsInt("C_Campaign_ID");
        
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
    
    /** Set Project.
    @param C_Project_ID Financial Project */
    public void setC_Project_ID (int C_Project_ID)
    {
        if (C_Project_ID <= 0) set_Value ("C_Project_ID", null);
        else
        set_Value ("C_Project_ID", Integer.valueOf(C_Project_ID));
        
    }
    
    /** Get Project.
    @return Financial Project */
    public int getC_Project_ID() 
    {
        return get_ValueAsInt("C_Project_ID");
        
    }
    
    /** Set Document Date.
    @param DateDoc Date of the Document */
    public void setDateDoc (Timestamp DateDoc)
    {
        if (DateDoc == null) throw new IllegalArgumentException ("DateDoc is mandatory.");
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
        if (DocAction == null) throw new IllegalArgumentException ("DocAction is mandatory");
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
    
    /** Set Journal Batch.
    @param GL_JournalBatch_ID General Ledger Journal Batch */
    public void setGL_JournalBatch_ID (int GL_JournalBatch_ID)
    {
        if (GL_JournalBatch_ID <= 0) set_Value ("GL_JournalBatch_ID", null);
        else
        set_Value ("GL_JournalBatch_ID", Integer.valueOf(GL_JournalBatch_ID));
        
    }
    
    /** Get Journal Batch.
    @return General Ledger Journal Batch */
    public int getGL_JournalBatch_ID() 
    {
        return get_ValueAsInt("GL_JournalBatch_ID");
        
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
    
    /** Set Settled.
    @param IsSettled This disbursement realization is settled */
    public void setIsSettled (boolean IsSettled)
    {
        set_Value ("IsSettled", Boolean.valueOf(IsSettled));
        
    }
    
    /** Get Settled.
    @return This disbursement realization is settled */
    public boolean isSettled() 
    {
        return get_ValueAsBoolean("IsSettled");
        
    }
    
    /** Set Posted.
    @param Posted Posting status */
    public void setPosted (boolean Posted)
    {
        set_Value ("Posted", Boolean.valueOf(Posted));
        
    }
    
    /** Get Posted.
    @return Posting status */
    public boolean isPosted() 
    {
        return get_ValueAsBoolean("Posted");
        
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
    
    /** Set Realized Amount.
    @param RealizedAmt Realized amount */
    public void setRealizedAmt (java.math.BigDecimal RealizedAmt)
    {
        set_Value ("RealizedAmt", RealizedAmt);
        
    }
    
    /** Get Realized Amount.
    @return Realized amount */
    public java.math.BigDecimal getRealizedAmt() 
    {
        return get_ValueAsBigDecimal("RealizedAmt");
        
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
    
    /** Set Advance Disbursement.
    @param XX_AdvanceDisbursement_ID Advance disbursement */
    public void setXX_AdvanceDisbursement_ID (int XX_AdvanceDisbursement_ID)
    {
        if (XX_AdvanceDisbursement_ID < 1) throw new IllegalArgumentException ("XX_AdvanceDisbursement_ID is mandatory.");
        set_ValueNoCheck ("XX_AdvanceDisbursement_ID", Integer.valueOf(XX_AdvanceDisbursement_ID));
        
    }
    
    /** Get Advance Disbursement.
    @return Advance disbursement */
    public int getXX_AdvanceDisbursement_ID() 
    {
        return get_ValueAsInt("XX_AdvanceDisbursement_ID");
        
    }
    
    /** Set Disbursement Realization.
    @param XX_DisbursementRealization_ID Realization of advance disbursement */
    public void setXX_DisbursementRealization_ID (int XX_DisbursementRealization_ID)
    {
        if (XX_DisbursementRealization_ID < 1) throw new IllegalArgumentException ("XX_DisbursementRealization_ID is mandatory.");
        set_ValueNoCheck ("XX_DisbursementRealization_ID", Integer.valueOf(XX_DisbursementRealization_ID));
        
    }
    
    /** Get Disbursement Realization.
    @return Realization of advance disbursement */
    public int getXX_DisbursementRealization_ID() 
    {
        return get_ValueAsInt("XX_DisbursementRealization_ID");
        
    }
    
    /** Set DR Confirmation.
    @param XX_DRConfirmation_ID Confirmation of disbursement realization */
    public void setXX_DRConfirmation_ID (int XX_DRConfirmation_ID)
    {
        if (XX_DRConfirmation_ID <= 0) set_Value ("XX_DRConfirmation_ID", null);
        else
        set_Value ("XX_DRConfirmation_ID", Integer.valueOf(XX_DRConfirmation_ID));
        
    }
    
    /** Get DR Confirmation.
    @return Confirmation of disbursement realization */
    public int getXX_DRConfirmation_ID() 
    {
        return get_ValueAsInt("XX_DRConfirmation_ID");
        
    }
    
    
}
