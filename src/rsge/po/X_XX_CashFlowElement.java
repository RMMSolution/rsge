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
/** Generated Model for XX_CashFlowElement
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_CashFlowElement extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_CashFlowElement_ID id
    @param trx transaction
    */
    public X_XX_CashFlowElement (Ctx ctx, int XX_CashFlowElement_ID, Trx trx)
    {
        super (ctx, XX_CashFlowElement_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_CashFlowElement_ID == 0)
        {
            setCashFlowActivities (null);	// 1
            setDescription (null);
            setIsOperatingActivitiesCharge (false);	// N
            setIsOtherOperatingExpense (false);	// N
            setIsPaidToVendor (false);	// N
            setIsReceipt (false);	// N
            setIsReceiptFromCustomer (false);	// N
            setXX_CashFlowElement_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_CashFlowElement (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27757663305685L;
    /** Last Updated Timestamp 2016-10-04 06:39:48.896 */
    public static final long updatedMS = 1475537988896L;
    /** AD_Table_ID=1000123 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_CashFlowElement");
        
    }
    ;
    
    /** TableName=XX_CashFlowElement */
    public static final String Table_Name="XX_CashFlowElement";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Business Partner Group.
    @param C_BP_Group_ID Business Partner Group */
    public void setC_BP_Group_ID (int C_BP_Group_ID)
    {
        if (C_BP_Group_ID <= 0) set_Value ("C_BP_Group_ID", null);
        else
        set_Value ("C_BP_Group_ID", Integer.valueOf(C_BP_Group_ID));
        
    }
    
    /** Get Business Partner Group.
    @return Business Partner Group */
    public int getC_BP_Group_ID() 
    {
        return get_ValueAsInt("C_BP_Group_ID");
        
    }
    
    /** Operating Activities = 1 */
    public static final String CASHFLOWACTIVITIES_OperatingActivities = X_Ref__Cash_Flow_Activities.OPERATING_ACTIVITIES.getValue();
    /** Investing Activities = 2 */
    public static final String CASHFLOWACTIVITIES_InvestingActivities = X_Ref__Cash_Flow_Activities.INVESTING_ACTIVITIES.getValue();
    /** Financing Activities = 3 */
    public static final String CASHFLOWACTIVITIES_FinancingActivities = X_Ref__Cash_Flow_Activities.FINANCING_ACTIVITIES.getValue();
    /** Set Cash Flow Activities.
    @param CashFlowActivities Cash Flow Activities */
    public void setCashFlowActivities (String CashFlowActivities)
    {
        if (CashFlowActivities == null) throw new IllegalArgumentException ("CashFlowActivities is mandatory");
        if (!X_Ref__Cash_Flow_Activities.isValid(CashFlowActivities))
        throw new IllegalArgumentException ("CashFlowActivities Invalid value - " + CashFlowActivities + " - Reference_ID=1000107 - 1 - 2 - 3");
        set_Value ("CashFlowActivities", CashFlowActivities);
        
    }
    
    /** Get Cash Flow Activities.
    @return Cash Flow Activities */
    public String getCashFlowActivities() 
    {
        return (String)get_Value("CashFlowActivities");
        
    }
    
    /** Set Description.
    @param Description Optional short description of the record */
    public void setDescription (String Description)
    {
        if (Description == null) throw new IllegalArgumentException ("Description is mandatory.");
        set_Value ("Description", Description);
        
    }
    
    /** Get Description.
    @return Optional short description of the record */
    public String getDescription() 
    {
        return (String)get_Value("Description");
        
    }
    
    /** Set Operating Charge.
    @param IsOperatingActivitiesCharge Charge of operating activities */
    public void setIsOperatingActivitiesCharge (boolean IsOperatingActivitiesCharge)
    {
        set_Value ("IsOperatingActivitiesCharge", Boolean.valueOf(IsOperatingActivitiesCharge));
        
    }
    
    /** Get Operating Charge.
    @return Charge of operating activities */
    public boolean isOperatingActivitiesCharge() 
    {
        return get_ValueAsBoolean("IsOperatingActivitiesCharge");
        
    }
    
    /** Set Other Operating Expense.
    @param IsOtherOperatingExpense Other Operating Expense */
    public void setIsOtherOperatingExpense (boolean IsOtherOperatingExpense)
    {
        set_Value ("IsOtherOperatingExpense", Boolean.valueOf(IsOtherOperatingExpense));
        
    }
    
    /** Get Other Operating Expense.
    @return Other Operating Expense */
    public boolean isOtherOperatingExpense() 
    {
        return get_ValueAsBoolean("IsOtherOperatingExpense");
        
    }
    
    /** Set Paid to Business Partner.
    @param IsPaidToVendor Paid to Business Partner */
    public void setIsPaidToVendor (boolean IsPaidToVendor)
    {
        set_Value ("IsPaidToVendor", Boolean.valueOf(IsPaidToVendor));
        
    }
    
    /** Get Paid to Business Partner.
    @return Paid to Business Partner */
    public boolean isPaidToVendor() 
    {
        return get_ValueAsBoolean("IsPaidToVendor");
        
    }
    
    /** Set Receipt.
    @param IsReceipt This is a sales transaction (receipt) */
    public void setIsReceipt (boolean IsReceipt)
    {
        set_Value ("IsReceipt", Boolean.valueOf(IsReceipt));
        
    }
    
    /** Get Receipt.
    @return This is a sales transaction (receipt) */
    public boolean isReceipt() 
    {
        return get_ValueAsBoolean("IsReceipt");
        
    }
    
    /** Set Receipt from Business Partner.
    @param IsReceiptFromCustomer Receipt from Business Partner */
    public void setIsReceiptFromCustomer (boolean IsReceiptFromCustomer)
    {
        set_Value ("IsReceiptFromCustomer", Boolean.valueOf(IsReceiptFromCustomer));
        
    }
    
    /** Get Receipt from Business Partner.
    @return Receipt from Business Partner */
    public boolean isReceiptFromCustomer() 
    {
        return get_ValueAsBoolean("IsReceiptFromCustomer");
        
    }
    
    /** Set Sequence.
    @param SeqNo Method of ordering elements;
     lowest number comes first */
    public void setSeqNo (int SeqNo)
    {
        set_Value ("SeqNo", Integer.valueOf(SeqNo));
        
    }
    
    /** Get Sequence.
    @return Method of ordering elements;
     lowest number comes first */
    public int getSeqNo() 
    {
        return get_ValueAsInt("SeqNo");
        
    }
    
    /** Set Vendor Group.
    @param VendorGroup_ID Vendor group */
    public void setVendorGroup_ID (int VendorGroup_ID)
    {
        if (VendorGroup_ID <= 0) set_Value ("VendorGroup_ID", null);
        else
        set_Value ("VendorGroup_ID", Integer.valueOf(VendorGroup_ID));
        
    }
    
    /** Get Vendor Group.
    @return Vendor group */
    public int getVendorGroup_ID() 
    {
        return get_ValueAsInt("VendorGroup_ID");
        
    }
    
    /** Set Cash Flow Element.
    @param XX_CashFlowElement_ID Cash Flow Element */
    public void setXX_CashFlowElement_ID (int XX_CashFlowElement_ID)
    {
        if (XX_CashFlowElement_ID < 1) throw new IllegalArgumentException ("XX_CashFlowElement_ID is mandatory.");
        set_ValueNoCheck ("XX_CashFlowElement_ID", Integer.valueOf(XX_CashFlowElement_ID));
        
    }
    
    /** Get Cash Flow Element.
    @return Cash Flow Element */
    public int getXX_CashFlowElement_ID() 
    {
        return get_ValueAsInt("XX_CashFlowElement_ID");
        
    }
    
    
}
