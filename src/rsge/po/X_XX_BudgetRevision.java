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
/** Generated Model for XX_BudgetRevision
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_BudgetRevision extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_BudgetRevision_ID id
    @param trx transaction
    */
    public X_XX_BudgetRevision (Ctx ctx, int XX_BudgetRevision_ID, Trx trx)
    {
        super (ctx, XX_BudgetRevision_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_BudgetRevision_ID == 0)
        {
            setBudgetRevisionType (null);	// C
            setDateDoc (new Timestamp(System.currentTimeMillis()));	// @#Date@
            setDateEffective (new Timestamp(System.currentTimeMillis()));	// @#Date@
            setDocumentNo (null);
            setExpenseToExpense (false);	// N
            setExpenseToInvestment (false);	// N
            setInvestmentToExpense (false);	// N
            setInvestmentToInvestment (false);	// N
            setIsActivity (false);	// N
            setIsApproved (false);	// N
            setIsBusinessPartner (false);	// N
            setIsCampaign (false);	// N
            setIsLocked (false);	// N
            setIsNotSummary (false);	// N
            setIsOrganization (false);	// N
            setIsPeriod (false);	// N
            setIsProduct (false);	// N
            setIsProject (false);	// N
            setIsSalesRegion (false);	// N
            setIsSameAccount (false);	// N
            setIsSOTrx (false);	// N
            setProcessed (false);	// N
            setXX_BudgetRevision_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_BudgetRevision (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27748525621789L;
    /** Last Updated Timestamp 2016-06-20 12:25:05.0 */
    public static final long updatedMS = 1466400305000L;
    /** AD_Table_ID=1000119 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_BudgetRevision");
        
    }
    ;
    
    /** TableName=XX_BudgetRevision */
    public static final String Table_Name="XX_BudgetRevision";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Inter-Organization.
    @param AD_OrgTo_ID Organization valid for intercompany documents */
    public void setAD_OrgTo_ID (int AD_OrgTo_ID)
    {
        if (AD_OrgTo_ID <= 0) set_Value ("AD_OrgTo_ID", null);
        else
        set_Value ("AD_OrgTo_ID", Integer.valueOf(AD_OrgTo_ID));
        
    }
    
    /** Get Inter-Organization.
    @return Organization valid for intercompany documents */
    public int getAD_OrgTo_ID() 
    {
        return get_ValueAsInt("AD_OrgTo_ID");
        
    }
    
    /** Set User/Contact.
    @param AD_User_ID User within the system - Internal or Business Partner Contact */
    public void setAD_User_ID (int AD_User_ID)
    {
        if (AD_User_ID <= 0) set_Value ("AD_User_ID", null);
        else
        set_Value ("AD_User_ID", Integer.valueOf(AD_User_ID));
        
    }
    
    /** Get User/Contact.
    @return User within the system - Internal or Business Partner Contact */
    public int getAD_User_ID() 
    {
        return get_ValueAsInt("AD_User_ID");
        
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
    
    /** Cut = C */
    public static final String BUDGETREVISIONTYPE_Cut = X_Ref__Budget_Revision_Type.CUT.getValue();
    /** Increase = I */
    public static final String BUDGETREVISIONTYPE_Increase = X_Ref__Budget_Revision_Type.INCREASE.getValue();
    /** Transfer = T */
    public static final String BUDGETREVISIONTYPE_Transfer = X_Ref__Budget_Revision_Type.TRANSFER.getValue();
    /** Set Revision Type.
    @param BudgetRevisionType Type of revision */
    public void setBudgetRevisionType (String BudgetRevisionType)
    {
        if (BudgetRevisionType == null) throw new IllegalArgumentException ("BudgetRevisionType is mandatory");
        if (!X_Ref__Budget_Revision_Type.isValid(BudgetRevisionType))
        throw new IllegalArgumentException ("BudgetRevisionType Invalid value - " + BudgetRevisionType + " - Reference_ID=1000104 - C - I - T");
        set_Value ("BudgetRevisionType", BudgetRevisionType);
        
    }
    
    /** Get Revision Type.
    @return Type of revision */
    public String getBudgetRevisionType() 
    {
        return (String)get_Value("BudgetRevisionType");
        
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
    
    /** Set Target Activity.
    @param C_Activity_To_ID Target Activity */
    public void setC_Activity_To_ID (int C_Activity_To_ID)
    {
        if (C_Activity_To_ID <= 0) set_Value ("C_Activity_To_ID", null);
        else
        set_Value ("C_Activity_To_ID", Integer.valueOf(C_Activity_To_ID));
        
    }
    
    /** Get Target Activity.
    @return Target Activity */
    public int getC_Activity_To_ID() 
    {
        return get_ValueAsInt("C_Activity_To_ID");
        
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
    
    /** Set To Business Partner.
    @param C_BPartner_To_ID To Business Partner */
    public void setC_BPartner_To_ID (int C_BPartner_To_ID)
    {
        if (C_BPartner_To_ID <= 0) set_Value ("C_BPartner_To_ID", null);
        else
        set_Value ("C_BPartner_To_ID", Integer.valueOf(C_BPartner_To_ID));
        
    }
    
    /** Get To Business Partner.
    @return To Business Partner */
    public int getC_BPartner_To_ID() 
    {
        return get_ValueAsInt("C_BPartner_To_ID");
        
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
    
    /** Set Target Campaign.
    @param C_Campaign_To_ID Target Campaign */
    public void setC_Campaign_To_ID (int C_Campaign_To_ID)
    {
        if (C_Campaign_To_ID <= 0) set_Value ("C_Campaign_To_ID", null);
        else
        set_Value ("C_Campaign_To_ID", Integer.valueOf(C_Campaign_To_ID));
        
    }
    
    /** Get Target Campaign.
    @return Target Campaign */
    public int getC_Campaign_To_ID() 
    {
        return get_ValueAsInt("C_Campaign_To_ID");
        
    }
    
    /** Set Current Period.
    @param C_Current_Period_ID Current Period */
    public void setC_Current_Period_ID (int C_Current_Period_ID)
    {
        if (C_Current_Period_ID <= 0) set_Value ("C_Current_Period_ID", null);
        else
        set_Value ("C_Current_Period_ID", Integer.valueOf(C_Current_Period_ID));
        
    }
    
    /** Get Current Period.
    @return Current Period */
    public int getC_Current_Period_ID() 
    {
        return get_ValueAsInt("C_Current_Period_ID");
        
    }
    
    /** Set Period From.
    @param C_Period_From_ID Starting period of a range of periods */
    public void setC_Period_From_ID (int C_Period_From_ID)
    {
        if (C_Period_From_ID <= 0) set_Value ("C_Period_From_ID", null);
        else
        set_Value ("C_Period_From_ID", Integer.valueOf(C_Period_From_ID));
        
    }
    
    /** Get Period From.
    @return Starting period of a range of periods */
    public int getC_Period_From_ID() 
    {
        return get_ValueAsInt("C_Period_From_ID");
        
    }
    
    /** Set Period To.
    @param C_Period_To_ID Ending period of a range of periods */
    public void setC_Period_To_ID (int C_Period_To_ID)
    {
        if (C_Period_To_ID <= 0) set_Value ("C_Period_To_ID", null);
        else
        set_Value ("C_Period_To_ID", Integer.valueOf(C_Period_To_ID));
        
    }
    
    /** Get Period To.
    @return Ending period of a range of periods */
    public int getC_Period_To_ID() 
    {
        return get_ValueAsInt("C_Period_To_ID");
        
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
    
    /** Set Target Project.
    @param C_Project_To_ID Target Project */
    public void setC_Project_To_ID (int C_Project_To_ID)
    {
        if (C_Project_To_ID <= 0) set_Value ("C_Project_To_ID", null);
        else
        set_Value ("C_Project_To_ID", Integer.valueOf(C_Project_To_ID));
        
    }
    
    /** Get Target Project.
    @return Target Project */
    public int getC_Project_To_ID() 
    {
        return get_ValueAsInt("C_Project_To_ID");
        
    }
    
    /** Set Sales Region.
    @param C_SalesRegion_ID Sales coverage region */
    public void setC_SalesRegion_ID (int C_SalesRegion_ID)
    {
        if (C_SalesRegion_ID <= 0) set_Value ("C_SalesRegion_ID", null);
        else
        set_Value ("C_SalesRegion_ID", Integer.valueOf(C_SalesRegion_ID));
        
    }
    
    /** Get Sales Region.
    @return Sales coverage region */
    public int getC_SalesRegion_ID() 
    {
        return get_ValueAsInt("C_SalesRegion_ID");
        
    }
    
    /** Set Target Sales Region.
    @param C_SalesRegion_To_ID Target Sales Region */
    public void setC_SalesRegion_To_ID (int C_SalesRegion_To_ID)
    {
        if (C_SalesRegion_To_ID <= 0) set_Value ("C_SalesRegion_To_ID", null);
        else
        set_Value ("C_SalesRegion_To_ID", Integer.valueOf(C_SalesRegion_To_ID));
        
    }
    
    /** Get Target Sales Region.
    @return Target Sales Region */
    public int getC_SalesRegion_To_ID() 
    {
        return get_ValueAsInt("C_SalesRegion_To_ID");
        
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
    
    /** Set Date Effective.
    @param DateEffective Effective date of selected transaction */
    public void setDateEffective (Timestamp DateEffective)
    {
        if (DateEffective == null) throw new IllegalArgumentException ("DateEffective is mandatory.");
        set_Value ("DateEffective", DateEffective);
        
    }
    
    /** Get Date Effective.
    @return Effective date of selected transaction */
    public Timestamp getDateEffective() 
    {
        return (Timestamp)get_Value("DateEffective");
        
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
    
    /** Set Expense to expense.
    @param ExpenseToExpense From expense account to expense account */
    public void setExpenseToExpense (boolean ExpenseToExpense)
    {
        set_Value ("ExpenseToExpense", Boolean.valueOf(ExpenseToExpense));
        
    }
    
    /** Get Expense to expense.
    @return From expense account to expense account */
    public boolean isExpenseToExpense() 
    {
        return get_ValueAsBoolean("ExpenseToExpense");
        
    }
    
    /** Set Expense to Investment.
    @param ExpenseToInvestment From expense account to investment account */
    public void setExpenseToInvestment (boolean ExpenseToInvestment)
    {
        set_Value ("ExpenseToInvestment", Boolean.valueOf(ExpenseToInvestment));
        
    }
    
    /** Get Expense to Investment.
    @return From expense account to investment account */
    public boolean isExpenseToInvestment() 
    {
        return get_ValueAsBoolean("ExpenseToInvestment");
        
    }
    
    /** Set Journal.
    @param GL_Journal_ID General Ledger Journal */
    public void setGL_Journal_ID (int GL_Journal_ID)
    {
        if (GL_Journal_ID <= 0) set_Value ("GL_Journal_ID", null);
        else
        set_Value ("GL_Journal_ID", Integer.valueOf(GL_Journal_ID));
        
    }
    
    /** Get Journal.
    @return General Ledger Journal */
    public int getGL_Journal_ID() 
    {
        return get_ValueAsInt("GL_Journal_ID");
        
    }
    
    /** Set Investment to Expense.
    @param InvestmentToExpense From investment account to expense''s account */
    public void setInvestmentToExpense (boolean InvestmentToExpense)
    {
        set_Value ("InvestmentToExpense", Boolean.valueOf(InvestmentToExpense));
        
    }
    
    /** Get Investment to Expense.
    @return From investment account to expense''s account */
    public boolean isInvestmentToExpense() 
    {
        return get_ValueAsBoolean("InvestmentToExpense");
        
    }
    
    /** Set Investment to Investment.
    @param InvestmentToInvestment From investment account to investment account */
    public void setInvestmentToInvestment (boolean InvestmentToInvestment)
    {
        set_Value ("InvestmentToInvestment", Boolean.valueOf(InvestmentToInvestment));
        
    }
    
    /** Get Investment to Investment.
    @return From investment account to investment account */
    public boolean isInvestmentToInvestment() 
    {
        return get_ValueAsBoolean("InvestmentToInvestment");
        
    }
    
    /** Set Activity.
    @param IsActivity Activity */
    public void setIsActivity (boolean IsActivity)
    {
        set_Value ("IsActivity", Boolean.valueOf(IsActivity));
        
    }
    
    /** Get Activity.
    @return Activity */
    public boolean isActivity() 
    {
        return get_ValueAsBoolean("IsActivity");
        
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
    
    /** Set Business Partner.
    @param IsBusinessPartner Business Partner */
    public void setIsBusinessPartner (boolean IsBusinessPartner)
    {
        set_Value ("IsBusinessPartner", Boolean.valueOf(IsBusinessPartner));
        
    }
    
    /** Get Business Partner.
    @return Business Partner */
    public boolean isBusinessPartner() 
    {
        return get_ValueAsBoolean("IsBusinessPartner");
        
    }
    
    /** Set Campaign.
    @param IsCampaign Campaign */
    public void setIsCampaign (boolean IsCampaign)
    {
        set_Value ("IsCampaign", Boolean.valueOf(IsCampaign));
        
    }
    
    /** Get Campaign.
    @return Campaign */
    public boolean isCampaign() 
    {
        return get_ValueAsBoolean("IsCampaign");
        
    }
    
    /** Set Lock.
    @param IsLocked Lock */
    public void setIsLocked (boolean IsLocked)
    {
        set_Value ("IsLocked", Boolean.valueOf(IsLocked));
        
    }
    
    /** Get Lock.
    @return Lock */
    public boolean isLocked() 
    {
        return get_ValueAsBoolean("IsLocked");
        
    }
    
    /** Set Not Summary.
    @param IsNotSummary Not summary product */
    public void setIsNotSummary (boolean IsNotSummary)
    {
        set_Value ("IsNotSummary", Boolean.valueOf(IsNotSummary));
        
    }
    
    /** Get Not Summary.
    @return Not summary product */
    public boolean isNotSummary() 
    {
        return get_ValueAsBoolean("IsNotSummary");
        
    }
    
    /** Set Organization.
    @param IsOrganization Organization */
    public void setIsOrganization (boolean IsOrganization)
    {
        set_Value ("IsOrganization", Boolean.valueOf(IsOrganization));
        
    }
    
    /** Get Organization.
    @return Organization */
    public boolean isOrganization() 
    {
        return get_ValueAsBoolean("IsOrganization");
        
    }
    
    /** Set Period.
    @param IsPeriod Period */
    public void setIsPeriod (boolean IsPeriod)
    {
        set_Value ("IsPeriod", Boolean.valueOf(IsPeriod));
        
    }
    
    /** Get Period.
    @return Period */
    public boolean isPeriod() 
    {
        return get_ValueAsBoolean("IsPeriod");
        
    }
    
    /** Set Product.
    @param IsProduct Product */
    public void setIsProduct (boolean IsProduct)
    {
        set_Value ("IsProduct", Boolean.valueOf(IsProduct));
        
    }
    
    /** Get Product.
    @return Product */
    public boolean isProduct() 
    {
        return get_ValueAsBoolean("IsProduct");
        
    }
    
    /** Set Project.
    @param IsProject Project */
    public void setIsProject (boolean IsProject)
    {
        set_Value ("IsProject", Boolean.valueOf(IsProject));
        
    }
    
    /** Get Project.
    @return Project */
    public boolean isProject() 
    {
        return get_ValueAsBoolean("IsProject");
        
    }
    
    /** Set Sales Region.
    @param IsSalesRegion Sales Region */
    public void setIsSalesRegion (boolean IsSalesRegion)
    {
        set_Value ("IsSalesRegion", Boolean.valueOf(IsSalesRegion));
        
    }
    
    /** Get Sales Region.
    @return Sales Region */
    public boolean isSalesRegion() 
    {
        return get_ValueAsBoolean("IsSalesRegion");
        
    }
    
    /** Set Same Account.
    @param IsSameAccount Budget will be transfered to same account in different segments */
    public void setIsSameAccount (boolean IsSameAccount)
    {
        set_Value ("IsSameAccount", Boolean.valueOf(IsSameAccount));
        
    }
    
    /** Get Same Account.
    @return Budget will be transfered to same account in different segments */
    public boolean isSameAccount() 
    {
        return get_ValueAsBoolean("IsSameAccount");
        
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
    
    /** Set Product.
    @param M_Product_ID Product, Service, Item */
    public void setM_Product_ID (int M_Product_ID)
    {
        if (M_Product_ID <= 0) set_Value ("M_Product_ID", null);
        else
        set_Value ("M_Product_ID", Integer.valueOf(M_Product_ID));
        
    }
    
    /** Get Product.
    @return Product, Service, Item */
    public int getM_Product_ID() 
    {
        return get_ValueAsInt("M_Product_ID");
        
    }
    
    /** Set To Product.
    @param M_Product_To_ID Product to be converted to (must have UOM Conversion defined to From Product) */
    public void setM_Product_To_ID (int M_Product_To_ID)
    {
        if (M_Product_To_ID <= 0) set_Value ("M_Product_To_ID", null);
        else
        set_Value ("M_Product_To_ID", Integer.valueOf(M_Product_To_ID));
        
    }
    
    /** Get To Product.
    @return Product to be converted to (must have UOM Conversion defined to From Product) */
    public int getM_Product_To_ID() 
    {
        return get_ValueAsInt("M_Product_To_ID");
        
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
    
    /** Set Related Budget Revision.
    @param TargetRevision_ID Related budget revision */
    public void setTargetRevision_ID (int TargetRevision_ID)
    {
        if (TargetRevision_ID <= 0) set_Value ("TargetRevision_ID", null);
        else
        set_Value ("TargetRevision_ID", Integer.valueOf(TargetRevision_ID));
        
    }
    
    /** Get Related Budget Revision.
    @return Related budget revision */
    public int getTargetRevision_ID() 
    {
        return get_ValueAsInt("TargetRevision_ID");
        
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
    
    /** Sales/Purchase = 1 */
    public static final String TRANSACTIONTYPE_SalesPurchase = X_Ref__Product_Trx_Type.SALES_PURCHASE.getValue();
    /** Return = 2 */
    public static final String TRANSACTIONTYPE_Return = X_Ref__Product_Trx_Type.RETURN.getValue();
    /** Discount = 3 */
    public static final String TRANSACTIONTYPE_Discount = X_Ref__Product_Trx_Type.DISCOUNT.getValue();
    /** Set Transaction Type.
    @param TransactionType Type of transaction */
    public void setTransactionType (String TransactionType)
    {
        if (!X_Ref__Product_Trx_Type.isValid(TransactionType))
        throw new IllegalArgumentException ("TransactionType Invalid value - " + TransactionType + " - Reference_ID=1000117 - 1 - 2 - 3");
        set_Value ("TransactionType", TransactionType);
        
    }
    
    /** Get Transaction Type.
    @return Type of transaction */
    public String getTransactionType() 
    {
        return (String)get_Value("TransactionType");
        
    }
    
    /** Set Budget Form Template.
    @param XX_BudgetFormTemplate_ID Template for budget form */
    public void setXX_BudgetFormTemplate_ID (int XX_BudgetFormTemplate_ID)
    {
        if (XX_BudgetFormTemplate_ID <= 0) set_Value ("XX_BudgetFormTemplate_ID", null);
        else
        set_Value ("XX_BudgetFormTemplate_ID", Integer.valueOf(XX_BudgetFormTemplate_ID));
        
    }
    
    /** Get Budget Form Template.
    @return Template for budget form */
    public int getXX_BudgetFormTemplate_ID() 
    {
        return get_ValueAsInt("XX_BudgetFormTemplate_ID");
        
    }
    
    /** Set Budget Plan.
    @param XX_BudgetPlanning_ID Budget plan of organization */
    public void setXX_BudgetPlanning_ID (int XX_BudgetPlanning_ID)
    {
        if (XX_BudgetPlanning_ID <= 0) set_Value ("XX_BudgetPlanning_ID", null);
        else
        set_Value ("XX_BudgetPlanning_ID", Integer.valueOf(XX_BudgetPlanning_ID));
        
    }
    
    /** Get Budget Plan.
    @return Budget plan of organization */
    public int getXX_BudgetPlanning_ID() 
    {
        return get_ValueAsInt("XX_BudgetPlanning_ID");
        
    }
    
    /** Set Budget Revision.
    @param XX_BudgetRevision_ID Revise amount of specific budget */
    public void setXX_BudgetRevision_ID (int XX_BudgetRevision_ID)
    {
        if (XX_BudgetRevision_ID < 1) throw new IllegalArgumentException ("XX_BudgetRevision_ID is mandatory.");
        set_ValueNoCheck ("XX_BudgetRevision_ID", Integer.valueOf(XX_BudgetRevision_ID));
        
    }
    
    /** Get Budget Revision.
    @return Revise amount of specific budget */
    public int getXX_BudgetRevision_ID() 
    {
        return get_ValueAsInt("XX_BudgetRevision_ID");
        
    }
    
    
}
