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
/** Generated Model for XX_BudgetPlanning
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_BudgetPlanning extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_BudgetPlanning_ID id
    @param trx transaction
    */
    public X_XX_BudgetPlanning (Ctx ctx, int XX_BudgetPlanning_ID, Trx trx)
    {
        super (ctx, XX_BudgetPlanning_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_BudgetPlanning_ID == 0)
        {
            setC_Period_From_ID (0);
            setDateDoc (new Timestamp(System.currentTimeMillis()));	// @#Date@
            setDocumentNo (null);
            setGL_Budget_ID (0);
            setIsApproved (false);	// N
            setName (null);
            setProcessed (false);	// N
            setXX_BudgetPlanning_ID (0);
            setXX_CorpBudgetPlan_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_BudgetPlanning (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27789146768013L;
    /** Last Updated Timestamp 2017-10-03 16:04:11.224 */
    public static final long updatedMS = 1507021451224L;
    /** AD_Table_ID=1000116 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_BudgetPlanning");
        
    }
    ;
    
    /** TableName=XX_BudgetPlanning */
    public static final String Table_Name="XX_BudgetPlanning";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Approved Budget Plan.
    @param ApprovedBudgetPlan Approved budget plan */
    public void setApprovedBudgetPlan (String ApprovedBudgetPlan)
    {
        set_Value ("ApprovedBudgetPlan", ApprovedBudgetPlan);
        
    }
    
    /** Get Approved Budget Plan.
    @return Approved budget plan */
    public String getApprovedBudgetPlan() 
    {
        return (String)get_Value("ApprovedBudgetPlan");
        
    }
    
    /** Available = AV */
    public static final String BUDGETPLANSTATUS_Available = X_Ref__Budget_Plan_Status.AVAILABLE.getValue();
    /** Draft = DR */
    public static final String BUDGETPLANSTATUS_Draft = X_Ref__Budget_Plan_Status.DRAFT.getValue();
    /** Set Budget Plan Status.
    @param BudgetPlanStatus Status of budget plan */
    public void setBudgetPlanStatus (String BudgetPlanStatus)
    {
        if (!X_Ref__Budget_Plan_Status.isValid(BudgetPlanStatus))
        throw new IllegalArgumentException ("BudgetPlanStatus Invalid value - " + BudgetPlanStatus + " - Reference_ID=1000103 - AV - DR");
        set_Value ("BudgetPlanStatus", BudgetPlanStatus);
        
    }
    
    /** Get Budget Plan Status.
    @return Status of budget plan */
    public String getBudgetPlanStatus() 
    {
        return (String)get_Value("BudgetPlanStatus");
        
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
    
    /** Set Period From.
    @param C_Period_From_ID Starting period of a range of periods */
    public void setC_Period_From_ID (int C_Period_From_ID)
    {
        if (C_Period_From_ID < 1) throw new IllegalArgumentException ("C_Period_From_ID is mandatory.");
        set_Value ("C_Period_From_ID", Integer.valueOf(C_Period_From_ID));
        
    }
    
    /** Get Period From.
    @return Starting period of a range of periods */
    public int getC_Period_From_ID() 
    {
        return get_ValueAsInt("C_Period_From_ID");
        
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
    
    /** Set Budget.
    @param GL_Budget_ID General Ledger Budget */
    public void setGL_Budget_ID (int GL_Budget_ID)
    {
        if (GL_Budget_ID < 1) throw new IllegalArgumentException ("GL_Budget_ID is mandatory.");
        set_Value ("GL_Budget_ID", Integer.valueOf(GL_Budget_ID));
        
    }
    
    /** Get Budget.
    @return General Ledger Budget */
    public int getGL_Budget_ID() 
    {
        return get_ValueAsInt("GL_Budget_ID");
        
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
    
    /** Set Name.
    @param Name Alphanumeric identifier of the entity */
    public void setName (String Name)
    {
        if (Name == null) throw new IllegalArgumentException ("Name is mandatory.");
        set_Value ("Name", Name);
        
    }
    
    /** Get Name.
    @return Alphanumeric identifier of the entity */
    public String getName() 
    {
        return (String)get_Value("Name");
        
    }
    
    /** Get Record ID/ColumnName
    @return ID/ColumnName pair */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
        
    }
    
    /** Annual = A */
    public static final String PLANNINGPERIOD_Annual = X_Ref__Planning_Period.ANNUAL.getValue();
    /** Monthly = M */
    public static final String PLANNINGPERIOD_Monthly = X_Ref__Planning_Period.MONTHLY.getValue();
    /** Quarterly = Q */
    public static final String PLANNINGPERIOD_Quarterly = X_Ref__Planning_Period.QUARTERLY.getValue();
    /** Semester = S */
    public static final String PLANNINGPERIOD_Semester = X_Ref__Planning_Period.SEMESTER.getValue();
    /** Set Planning Period.
    @param PlanningPeriod Planning period type (Monthly, Quarterly, Semester) */
    public void setPlanningPeriod (String PlanningPeriod)
    {
        if (!X_Ref__Planning_Period.isValid(PlanningPeriod))
        throw new IllegalArgumentException ("PlanningPeriod Invalid value - " + PlanningPeriod + " - Reference_ID=1000116 - A - M - Q - S");
        set_Value ("PlanningPeriod", PlanningPeriod);
        
    }
    
    /** Get Planning Period.
    @return Planning period type (Monthly, Quarterly, Semester) */
    public String getPlanningPeriod() 
    {
        return (String)get_Value("PlanningPeriod");
        
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
    
    /** Set Total Credit.
    @param TotalCr Total Credit in document currency */
    public void setTotalCr (java.math.BigDecimal TotalCr)
    {
        set_Value ("TotalCr", TotalCr);
        
    }
    
    /** Get Total Credit.
    @return Total Credit in document currency */
    public java.math.BigDecimal getTotalCr() 
    {
        return get_ValueAsBigDecimal("TotalCr");
        
    }
    
    /** Set Total Debit.
    @param TotalDr Total debit in document currency */
    public void setTotalDr (java.math.BigDecimal TotalDr)
    {
        set_Value ("TotalDr", TotalDr);
        
    }
    
    /** Get Total Debit.
    @return Total debit in document currency */
    public java.math.BigDecimal getTotalDr() 
    {
        return get_ValueAsBigDecimal("TotalDr");
        
    }
    
    /** Set Budget Plan.
    @param XX_BudgetPlanning_ID Budget plan of organization */
    public void setXX_BudgetPlanning_ID (int XX_BudgetPlanning_ID)
    {
        if (XX_BudgetPlanning_ID < 1) throw new IllegalArgumentException ("XX_BudgetPlanning_ID is mandatory.");
        set_ValueNoCheck ("XX_BudgetPlanning_ID", Integer.valueOf(XX_BudgetPlanning_ID));
        
    }
    
    /** Get Budget Plan.
    @return Budget plan of organization */
    public int getXX_BudgetPlanning_ID() 
    {
        return get_ValueAsInt("XX_BudgetPlanning_ID");
        
    }
    
    /** Set Corporate Budget Plan.
    @param XX_CorpBudgetPlan_ID Main template to create budget plan for organization */
    public void setXX_CorpBudgetPlan_ID (int XX_CorpBudgetPlan_ID)
    {
        if (XX_CorpBudgetPlan_ID < 1) throw new IllegalArgumentException ("XX_CorpBudgetPlan_ID is mandatory.");
        set_Value ("XX_CorpBudgetPlan_ID", Integer.valueOf(XX_CorpBudgetPlan_ID));
        
    }
    
    /** Get Corporate Budget Plan.
    @return Main template to create budget plan for organization */
    public int getXX_CorpBudgetPlan_ID() 
    {
        return get_ValueAsInt("XX_CorpBudgetPlan_ID");
        
    }
    
    
}
