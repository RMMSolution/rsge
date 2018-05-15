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
/** Generated Model for XX_PurchaseRequisition
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_PurchaseRequisition extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_PurchaseRequisition_ID id
    @param trx transaction
    */
    public X_XX_PurchaseRequisition (Ctx ctx, int XX_PurchaseRequisition_ID, Trx trx)
    {
        super (ctx, XX_PurchaseRequisition_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_PurchaseRequisition_ID == 0)
        {
            setC_Currency_ID (0);
            setDateDoc (new Timestamp(System.currentTimeMillis()));
            setDateRequired (new Timestamp(System.currentTimeMillis()));
            setDocumentNo (null);
            setIsApproved (false);	// N
            setIsLineOverBudget (false);	// N
            setPriorityRule (null);	// 5
            setProcessed (false);	// N
            setXX_PurchaseRequisition_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_PurchaseRequisition (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27802427889522L;
    /** Last Updated Timestamp 2018-03-06 09:16:12.733 */
    public static final long updatedMS = 1520302572733L;
    /** AD_Table_ID=1000174 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_PurchaseRequisition");
        
    }
    ;
    
    /** TableName=XX_PurchaseRequisition */
    public static final String Table_Name="XX_PurchaseRequisition";
    
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
    
    /** Set Approver.
    @param Approval_ID Approver of document */
    public void setApproval_ID (int Approval_ID)
    {
        if (Approval_ID <= 0) set_Value ("Approval_ID", null);
        else
        set_Value ("Approval_ID", Integer.valueOf(Approval_ID));
        
    }
    
    /** Get Approver.
    @return Approver of document */
    public int getApproval_ID() 
    {
        return get_ValueAsInt("Approval_ID");
        
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
        if (C_Currency_ID < 1) throw new IllegalArgumentException ("C_Currency_ID is mandatory.");
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
    
    /** Set Date Required.
    @param DateRequired Date when required */
    public void setDateRequired (Timestamp DateRequired)
    {
        if (DateRequired == null) throw new IllegalArgumentException ("DateRequired is mandatory.");
        set_Value ("DateRequired", DateRequired);
        
    }
    
    /** Get Date Required.
    @return Date when required */
    public Timestamp getDateRequired() 
    {
        return (Timestamp)get_Value("DateRequired");
        
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
    
    /** Set Line Over Budget.
    @param IsLineOverBudget One of record''s line is over budget */
    public void setIsLineOverBudget (boolean IsLineOverBudget)
    {
        set_Value ("IsLineOverBudget", Boolean.valueOf(IsLineOverBudget));
        
    }
    
    /** Get Line Over Budget.
    @return One of record''s line is over budget */
    public boolean isLineOverBudget() 
    {
        return get_ValueAsBoolean("IsLineOverBudget");
        
    }
    
    /** Set Line Qty Over Budget.
    @param IsLineQtyOverBudget Line quantity over budget */
    public void setIsLineQtyOverBudget (boolean IsLineQtyOverBudget)
    {
        set_Value ("IsLineQtyOverBudget", Boolean.valueOf(IsLineQtyOverBudget));
        
    }
    
    /** Get Line Qty Over Budget.
    @return Line quantity over budget */
    public boolean isLineQtyOverBudget() 
    {
        return get_ValueAsBoolean("IsLineQtyOverBudget");
        
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
    
    /** Set Price List Version.
    @param M_PriceList_Version_ID Identifies a unique instance of a Price List */
    public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
    {
        if (M_PriceList_Version_ID <= 0) set_Value ("M_PriceList_Version_ID", null);
        else
        set_Value ("M_PriceList_Version_ID", Integer.valueOf(M_PriceList_Version_ID));
        
    }
    
    /** Get Price List Version.
    @return Identifies a unique instance of a Price List */
    public int getM_PriceList_Version_ID() 
    {
        return get_ValueAsInt("M_PriceList_Version_ID");
        
    }
    
    /** Set Requisition.
    @param M_Requisition_ID Material Requisition */
    public void setM_Requisition_ID (int M_Requisition_ID)
    {
        if (M_Requisition_ID <= 0) set_Value ("M_Requisition_ID", null);
        else
        set_Value ("M_Requisition_ID", Integer.valueOf(M_Requisition_ID));
        
    }
    
    /** Get Requisition.
    @return Material Requisition */
    public int getM_Requisition_ID() 
    {
        return get_ValueAsInt("M_Requisition_ID");
        
    }
    
    /** Set Message.
    @param Message EMail Message */
    public void setMessage (String Message)
    {
        set_Value ("Message", Message);
        
    }
    
    /** Get Message.
    @return EMail Message */
    public String getMessage() 
    {
        return (String)get_Value("Message");
        
    }
    
    /** Set Over Budget Amount.
    @param OverBudgetAmt Amount exceed budget allocated */
    public void setOverBudgetAmt (java.math.BigDecimal OverBudgetAmt)
    {
        set_Value ("OverBudgetAmt", OverBudgetAmt);
        
    }
    
    /** Get Over Budget Amount.
    @return Amount exceed budget allocated */
    public java.math.BigDecimal getOverBudgetAmt() 
    {
        return get_ValueAsBigDecimal("OverBudgetAmt");
        
    }
    
    /** Urgent = 1 */
    public static final String PRIORITYRULE_Urgent = X_Ref__PriorityRule.URGENT.getValue();
    /** High = 3 */
    public static final String PRIORITYRULE_High = X_Ref__PriorityRule.HIGH.getValue();
    /** Medium = 5 */
    public static final String PRIORITYRULE_Medium = X_Ref__PriorityRule.MEDIUM.getValue();
    /** Low = 7 */
    public static final String PRIORITYRULE_Low = X_Ref__PriorityRule.LOW.getValue();
    /** Minor = 9 */
    public static final String PRIORITYRULE_Minor = X_Ref__PriorityRule.MINOR.getValue();
    /** Set Priority.
    @param PriorityRule Priority of a document */
    public void setPriorityRule (String PriorityRule)
    {
        if (PriorityRule == null) throw new IllegalArgumentException ("PriorityRule is mandatory");
        if (!X_Ref__PriorityRule.isValid(PriorityRule))
        throw new IllegalArgumentException ("PriorityRule Invalid value - " + PriorityRule + " - Reference_ID=154 - 1 - 3 - 5 - 7 - 9");
        set_Value ("PriorityRule", PriorityRule);
        
    }
    
    /** Get Priority.
    @return Priority of a document */
    public String getPriorityRule() 
    {
        return (String)get_Value("PriorityRule");
        
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
    
    /** Set Request By.
    @param RequestBy_ID Indicates user who request this document */
    public void setRequestBy_ID (int RequestBy_ID)
    {
        if (RequestBy_ID <= 0) set_Value ("RequestBy_ID", null);
        else
        set_Value ("RequestBy_ID", Integer.valueOf(RequestBy_ID));
        
    }
    
    /** Get Request By.
    @return Indicates user who request this document */
    public int getRequestBy_ID() 
    {
        return get_ValueAsInt("RequestBy_ID");
        
    }
    
    /** Set Request by (Employee).
    @param RequestByEmp_ID This requisition is request by */
    public void setRequestByEmp_ID (int RequestByEmp_ID)
    {
        if (RequestByEmp_ID <= 0) set_Value ("RequestByEmp_ID", null);
        else
        set_Value ("RequestByEmp_ID", Integer.valueOf(RequestByEmp_ID));
        
    }
    
    /** Get Request by (Employee).
    @return This requisition is request by */
    public int getRequestByEmp_ID() 
    {
        return get_ValueAsInt("RequestByEmp_ID");
        
    }
    
    /** Set Summary.
    @param Summary Textual summary of this request */
    public void setSummary (String Summary)
    {
        set_Value ("Summary", Summary);
        
    }
    
    /** Get Summary.
    @return Textual summary of this request */
    public String getSummary() 
    {
        return (String)get_Value("Summary");
        
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
    
    /** Set Requisition.
    @param XX_PurchaseRequisition_ID User Requisition */
    public void setXX_PurchaseRequisition_ID (int XX_PurchaseRequisition_ID)
    {
        if (XX_PurchaseRequisition_ID < 1) throw new IllegalArgumentException ("XX_PurchaseRequisition_ID is mandatory.");
        set_ValueNoCheck ("XX_PurchaseRequisition_ID", Integer.valueOf(XX_PurchaseRequisition_ID));
        
    }
    
    /** Get Requisition.
    @return User Requisition */
    public int getXX_PurchaseRequisition_ID() 
    {
        return get_ValueAsInt("XX_PurchaseRequisition_ID");
        
    }
    
    
}
