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
/** Generated Model for XX_InternalUseRequest
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_InternalUseRequest extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_InternalUseRequest_ID id
    @param trx transaction
    */
    public X_XX_InternalUseRequest (Ctx ctx, int XX_InternalUseRequest_ID, Trx trx)
    {
        super (ctx, XX_InternalUseRequest_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_InternalUseRequest_ID == 0)
        {
            setC_Charge_ID (0);
            setDateDoc (new Timestamp(System.currentTimeMillis()));	// @#Date@
            setDateExpected (new Timestamp(System.currentTimeMillis()));	// @#Date@
            setDocumentNo (null);
            setIsApproved (false);	// N
            setIsRequestSubmitted (false);	// N
            setProcessed (false);	// N
            setRequestBy_ID (0);	// @#AD_User_ID@
            setSubmitRequest (null);	// N
            setXX_InternalUseRequest_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_InternalUseRequest (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27695412901789L;
    /** Last Updated Timestamp 2014-10-14 18:53:05.0 */
    public static final long updatedMS = 1413287585000L;
    /** AD_Table_ID=1000149 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_InternalUseRequest");
        
    }
    ;
    
    /** TableName=XX_InternalUseRequest */
    public static final String Table_Name="XX_InternalUseRequest";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Trx Organization.
    @param AD_OrgTrx_ID Performing or initiating organization */
    public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
    {
        if (AD_OrgTrx_ID <= 0) set_Value ("AD_OrgTrx_ID", null);
        else
        set_Value ("AD_OrgTrx_ID", Integer.valueOf(AD_OrgTrx_ID));
        
    }
    
    /** Get Trx Organization.
    @return Performing or initiating organization */
    public int getAD_OrgTrx_ID() 
    {
        return get_ValueAsInt("AD_OrgTrx_ID");
        
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
    
    /** Set Charge.
    @param C_Charge_ID Additional document charges */
    public void setC_Charge_ID (int C_Charge_ID)
    {
        if (C_Charge_ID < 1) throw new IllegalArgumentException ("C_Charge_ID is mandatory.");
        set_Value ("C_Charge_ID", Integer.valueOf(C_Charge_ID));
        
    }
    
    /** Get Charge.
    @return Additional document charges */
    public int getC_Charge_ID() 
    {
        return get_ValueAsInt("C_Charge_ID");
        
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
    
    /** Set Date Delivered.
    @param DateDelivered Date when the product was delivered */
    public void setDateDelivered (Timestamp DateDelivered)
    {
        set_Value ("DateDelivered", DateDelivered);
        
    }
    
    /** Get Date Delivered.
    @return Date when the product was delivered */
    public Timestamp getDateDelivered() 
    {
        return (Timestamp)get_Value("DateDelivered");
        
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
    
    /** Set Date Expected.
    @param DateExpected Date on which the order is expected to be fulfilled */
    public void setDateExpected (Timestamp DateExpected)
    {
        if (DateExpected == null) throw new IllegalArgumentException ("DateExpected is mandatory.");
        set_Value ("DateExpected", DateExpected);
        
    }
    
    /** Get Date Expected.
    @return Date on which the order is expected to be fulfilled */
    public Timestamp getDateExpected() 
    {
        return (Timestamp)get_Value("DateExpected");
        
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
    
    /** Set Request Submitted.
    @param IsRequestSubmitted Request has already submitted */
    public void setIsRequestSubmitted (boolean IsRequestSubmitted)
    {
        set_Value ("IsRequestSubmitted", Boolean.valueOf(IsRequestSubmitted));
        
    }
    
    /** Get Request Submitted.
    @return Request has already submitted */
    public boolean isRequestSubmitted() 
    {
        return get_ValueAsBoolean("IsRequestSubmitted");
        
    }
    
    /** Set Phys.Inventory.
    @param M_Inventory_ID Parameters for a Physical Inventory */
    public void setM_Inventory_ID (int M_Inventory_ID)
    {
        if (M_Inventory_ID <= 0) set_Value ("M_Inventory_ID", null);
        else
        set_Value ("M_Inventory_ID", Integer.valueOf(M_Inventory_ID));
        
    }
    
    /** Get Phys.Inventory.
    @return Parameters for a Physical Inventory */
    public int getM_Inventory_ID() 
    {
        return get_ValueAsInt("M_Inventory_ID");
        
    }
    
    /** Set Warehouse.
    @param M_Warehouse_ID Storage Warehouse and Service Point */
    public void setM_Warehouse_ID (int M_Warehouse_ID)
    {
        if (M_Warehouse_ID <= 0) set_Value ("M_Warehouse_ID", null);
        else
        set_Value ("M_Warehouse_ID", Integer.valueOf(M_Warehouse_ID));
        
    }
    
    /** Get Warehouse.
    @return Storage Warehouse and Service Point */
    public int getM_Warehouse_ID() 
    {
        return get_ValueAsInt("M_Warehouse_ID");
        
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
    
    /** Set Process Request.
    @param ProcessRequest Process this request */
    public void setProcessRequest (String ProcessRequest)
    {
        set_Value ("ProcessRequest", ProcessRequest);
        
    }
    
    /** Get Process Request.
    @return Process this request */
    public String getProcessRequest() 
    {
        return (String)get_Value("ProcessRequest");
        
    }
    
    /** Set Request By.
    @param RequestBy_ID Indicates user who request this document */
    public void setRequestBy_ID (int RequestBy_ID)
    {
        if (RequestBy_ID < 1) throw new IllegalArgumentException ("RequestBy_ID is mandatory.");
        set_Value ("RequestBy_ID", Integer.valueOf(RequestBy_ID));
        
    }
    
    /** Get Request By.
    @return Indicates user who request this document */
    public int getRequestBy_ID() 
    {
        return get_ValueAsInt("RequestBy_ID");
        
    }
    
    /** Set Submit Request.
    @param SubmitRequest Submit request so document can be processed */
    public void setSubmitRequest (String SubmitRequest)
    {
        if (SubmitRequest == null) throw new IllegalArgumentException ("SubmitRequest is mandatory.");
        set_Value ("SubmitRequest", SubmitRequest);
        
    }
    
    /** Get Submit Request.
    @return Submit request so document can be processed */
    public String getSubmitRequest() 
    {
        return (String)get_Value("SubmitRequest");
        
    }
    
    /** Set Warehouse Rep.
    @param WH_Rep_ID Warehouse Representative */
    public void setWH_Rep_ID (int WH_Rep_ID)
    {
        if (WH_Rep_ID <= 0) set_Value ("WH_Rep_ID", null);
        else
        set_Value ("WH_Rep_ID", Integer.valueOf(WH_Rep_ID));
        
    }
    
    /** Get Warehouse Rep.
    @return Warehouse Representative */
    public int getWH_Rep_ID() 
    {
        return get_ValueAsInt("WH_Rep_ID");
        
    }
    
    /** Set Internal Use Request.
    @param XX_InternalUseRequest_ID Internal use request */
    public void setXX_InternalUseRequest_ID (int XX_InternalUseRequest_ID)
    {
        if (XX_InternalUseRequest_ID < 1) throw new IllegalArgumentException ("XX_InternalUseRequest_ID is mandatory.");
        set_ValueNoCheck ("XX_InternalUseRequest_ID", Integer.valueOf(XX_InternalUseRequest_ID));
        
    }
    
    /** Get Internal Use Request.
    @return Internal use request */
    public int getXX_InternalUseRequest_ID() 
    {
        return get_ValueAsInt("XX_InternalUseRequest_ID");
        
    }
    
    
}
