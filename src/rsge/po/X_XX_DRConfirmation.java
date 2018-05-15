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
/** Generated Model for XX_DRConfirmation
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_DRConfirmation extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_DRConfirmation_ID id
    @param trx transaction
    */
    public X_XX_DRConfirmation (Ctx ctx, int XX_DRConfirmation_ID, Trx trx)
    {
        super (ctx, XX_DRConfirmation_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_DRConfirmation_ID == 0)
        {
            setAD_User_ID (0);
            setDateDoc (new Timestamp(System.currentTimeMillis()));	// @#Date@
            setDocumentNo (null);
            setDRConfirmationType (null);	// 1
            setIsApproved (false);	// N
            setProcessed (false);	// N
            setXX_DRConfirmation_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_DRConfirmation (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27801866365134L;
    /** Last Updated Timestamp 2018-02-27 21:17:28.345 */
    public static final long updatedMS = 1519741048345L;
    /** AD_Table_ID=1000144 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_DRConfirmation");
        
    }
    ;
    
    /** TableName=XX_DRConfirmation */
    public static final String Table_Name="XX_DRConfirmation";
    
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
    
    /** Return = 1 */
    public static final String DRCONFIRMATIONTYPE_Return = X_Ref__DR_Confirmation_Type.RETURN.getValue();
    /** Reimburse = 2 */
    public static final String DRCONFIRMATIONTYPE_Reimburse = X_Ref__DR_Confirmation_Type.REIMBURSE.getValue();
    /** Set Confirmation Type.
    @param DRConfirmationType Disbursement realization confirmation type */
    public void setDRConfirmationType (String DRConfirmationType)
    {
        if (DRConfirmationType == null) throw new IllegalArgumentException ("DRConfirmationType is mandatory");
        if (!X_Ref__DR_Confirmation_Type.isValid(DRConfirmationType))
        throw new IllegalArgumentException ("DRConfirmationType Invalid value - " + DRConfirmationType + " - Reference_ID=1000112 - 1 - 2");
        set_ValueNoCheck ("DRConfirmationType", DRConfirmationType);
        
    }
    
    /** Get Confirmation Type.
    @return Disbursement realization confirmation type */
    public String getDRConfirmationType() 
    {
        return (String)get_Value("DRConfirmationType");
        
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
    
    /** Set Returned Amount.
    @param ReturnAmt Amount returned */
    public void setReturnAmt (java.math.BigDecimal ReturnAmt)
    {
        set_Value ("ReturnAmt", ReturnAmt);
        
    }
    
    /** Get Returned Amount.
    @return Amount returned */
    public java.math.BigDecimal getReturnAmt() 
    {
        return get_ValueAsBigDecimal("ReturnAmt");
        
    }
    
    /** Set Disbursement Realization.
    @param XX_DisbursementRealization_ID Realization of advance disbursement */
    public void setXX_DisbursementRealization_ID (int XX_DisbursementRealization_ID)
    {
        if (XX_DisbursementRealization_ID <= 0) set_Value ("XX_DisbursementRealization_ID", null);
        else
        set_Value ("XX_DisbursementRealization_ID", Integer.valueOf(XX_DisbursementRealization_ID));
        
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
        if (XX_DRConfirmation_ID < 1) throw new IllegalArgumentException ("XX_DRConfirmation_ID is mandatory.");
        set_ValueNoCheck ("XX_DRConfirmation_ID", Integer.valueOf(XX_DRConfirmation_ID));
        
    }
    
    /** Get DR Confirmation.
    @return Confirmation of disbursement realization */
    public int getXX_DRConfirmation_ID() 
    {
        return get_ValueAsInt("XX_DRConfirmation_ID");
        
    }
    
    
}
