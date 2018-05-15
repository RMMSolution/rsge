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
/** Generated Model for XX_PaymentAutoAllocation
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_PaymentAutoAllocation extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_PaymentAutoAllocation_ID id
    @param trx transaction
    */
    public X_XX_PaymentAutoAllocation (Ctx ctx, int XX_PaymentAutoAllocation_ID, Trx trx)
    {
        super (ctx, XX_PaymentAutoAllocation_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_PaymentAutoAllocation_ID == 0)
        {
            setTenderType (null);
            setXX_GeneralSetup_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_PaymentAutoAllocation (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27757712096142L;
    /** Last Updated Timestamp 2016-10-04 20:12:59.353 */
    public static final long updatedMS = 1475586779353L;
    /** AD_Table_ID=1000166 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_PaymentAutoAllocation");
        
    }
    ;
    
    /** TableName=XX_PaymentAutoAllocation */
    public static final String Table_Name="XX_PaymentAutoAllocation";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
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
    
    /** Direct Deposit = A */
    public static final String TENDERTYPE_DirectDeposit = X_Ref_C_Payment_Tender_Type.DIRECT_DEPOSIT.getValue();
    /** Credit Card = C */
    public static final String TENDERTYPE_CreditCard = X_Ref_C_Payment_Tender_Type.CREDIT_CARD.getValue();
    /** Direct Debit = D */
    public static final String TENDERTYPE_DirectDebit = X_Ref_C_Payment_Tender_Type.DIRECT_DEBIT.getValue();
    /** Check = K */
    public static final String TENDERTYPE_Check = X_Ref_C_Payment_Tender_Type.CHECK.getValue();
    /** Set Tender type.
    @param TenderType Method of Payment */
    public void setTenderType (String TenderType)
    {
        if (TenderType == null) throw new IllegalArgumentException ("TenderType is mandatory");
        if (!X_Ref_C_Payment_Tender_Type.isValid(TenderType))
        throw new IllegalArgumentException ("TenderType Invalid value - " + TenderType + " - Reference_ID=214 - A - C - D - K");
        set_ValueNoCheck ("TenderType", TenderType);
        
    }
    
    /** Get Tender type.
    @return Method of Payment */
    public String getTenderType() 
    {
        return (String)get_Value("TenderType");
        
    }
    
    /** Set General Setup.
    @param XX_GeneralSetup_ID Basic setup for general enhancement extension */
    public void setXX_GeneralSetup_ID (int XX_GeneralSetup_ID)
    {
        if (XX_GeneralSetup_ID < 1) throw new IllegalArgumentException ("XX_GeneralSetup_ID is mandatory.");
        set_ValueNoCheck ("XX_GeneralSetup_ID", Integer.valueOf(XX_GeneralSetup_ID));
        
    }
    
    /** Get General Setup.
    @return Basic setup for general enhancement extension */
    public int getXX_GeneralSetup_ID() 
    {
        return get_ValueAsInt("XX_GeneralSetup_ID");
        
    }
    
    
}
