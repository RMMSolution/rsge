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
/** Generated Model for XX_GeneralSetup
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_GeneralSetup extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_GeneralSetup_ID id
    @param trx transaction
    */
    public X_XX_GeneralSetup (Ctx ctx, int XX_GeneralSetup_ID, Trx trx)
    {
        super (ctx, XX_GeneralSetup_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_GeneralSetup_ID == 0)
        {
            setIsAllowNonMatchReturn (false);	// N
            setIsClockOutMandatory (false);	// N
            setIsMaintainOrgFunction (false);	// N
            setName (null);
            setUseOrgIntercompanyAcct (false);
            setUsePOAsBatchID (false);	// N
            setXX_GeneralSetup_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_GeneralSetup (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27805464589054L;
    /** Last Updated Timestamp 2018-04-10 12:47:52.265 */
    public static final long updatedMS = 1523339272265L;
    /** AD_Table_ID=1000147 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_GeneralSetup");
        
    }
    ;
    
    /** TableName=XX_GeneralSetup */
    public static final String Table_Name="XX_GeneralSetup";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set AD Clearing Charge.
    @param AdvDisbursementClrg_ID Advance Disbursement Clearing Charge */
    public void setAdvDisbursementClrg_ID (int AdvDisbursementClrg_ID)
    {
        if (AdvDisbursementClrg_ID <= 0) set_Value ("AdvDisbursementClrg_ID", null);
        else
        set_Value ("AdvDisbursementClrg_ID", Integer.valueOf(AdvDisbursementClrg_ID));
        
    }
    
    /** Get AD Clearing Charge.
    @return Advance Disbursement Clearing Charge */
    public int getAdvDisbursementClrg_ID() 
    {
        return get_ValueAsInt("AdvDisbursementClrg_ID");
        
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
    
    /** Set Expensed Transit.
    @param ExpenseTransitCharge_ID Charge to use record expensed transit */
    public void setExpenseTransitCharge_ID (int ExpenseTransitCharge_ID)
    {
        if (ExpenseTransitCharge_ID <= 0) set_Value ("ExpenseTransitCharge_ID", null);
        else
        set_Value ("ExpenseTransitCharge_ID", Integer.valueOf(ExpenseTransitCharge_ID));
        
    }
    
    /** Get Expensed Transit.
    @return Charge to use record expensed transit */
    public int getExpenseTransitCharge_ID() 
    {
        return get_ValueAsInt("ExpenseTransitCharge_ID");
        
    }
    
    /** Set Allow Non Match Return.
    @param IsAllowNonMatchReturn System will allow any return with no matching attribute set instance */
    public void setIsAllowNonMatchReturn (boolean IsAllowNonMatchReturn)
    {
        set_Value ("IsAllowNonMatchReturn", Boolean.valueOf(IsAllowNonMatchReturn));
        
    }
    
    /** Get Allow Non Match Return.
    @return System will allow any return with no matching attribute set instance */
    public boolean isAllowNonMatchReturn() 
    {
        return get_ValueAsBoolean("IsAllowNonMatchReturn");
        
    }
    
    /** Set Clock Out Mandatory.
    @param IsClockOutMandatory Indicate clock out is mandatory */
    public void setIsClockOutMandatory (boolean IsClockOutMandatory)
    {
        set_Value ("IsClockOutMandatory", Boolean.valueOf(IsClockOutMandatory));
        
    }
    
    /** Get Clock Out Mandatory.
    @return Indicate clock out is mandatory */
    public boolean isClockOutMandatory() 
    {
        return get_ValueAsBoolean("IsClockOutMandatory");
        
    }
    
    /** Set Maintain Organization Function.
    @param IsMaintainOrgFunction When this checkbox is checked, some modules can only be used by specific organization */
    public void setIsMaintainOrgFunction (boolean IsMaintainOrgFunction)
    {
        set_Value ("IsMaintainOrgFunction", Boolean.valueOf(IsMaintainOrgFunction));
        
    }
    
    /** Get Maintain Organization Function.
    @return When this checkbox is checked, some modules can only be used by specific organization */
    public boolean isMaintainOrgFunction() 
    {
        return get_ValueAsBoolean("IsMaintainOrgFunction");
        
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
    
    /** Set Unmatch Return Charge.
    @param UnmatchReturnCharge_ID Unmatch return charge is use to record any product which its attribute set is not found in the system */
    public void setUnmatchReturnCharge_ID (int UnmatchReturnCharge_ID)
    {
        if (UnmatchReturnCharge_ID <= 0) set_Value ("UnmatchReturnCharge_ID", null);
        else
        set_Value ("UnmatchReturnCharge_ID", Integer.valueOf(UnmatchReturnCharge_ID));
        
    }
    
    /** Get Unmatch Return Charge.
    @return Unmatch return charge is use to record any product which its attribute set is not found in the system */
    public int getUnmatchReturnCharge_ID() 
    {
        return get_ValueAsInt("UnmatchReturnCharge_ID");
        
    }
    
    /** Set Use Organization.
    @param UseOrgIntercompanyAcct Use organization intercompany account to record intercompany transaction */
    public void setUseOrgIntercompanyAcct (boolean UseOrgIntercompanyAcct)
    {
        set_Value ("UseOrgIntercompanyAcct", Boolean.valueOf(UseOrgIntercompanyAcct));
        
    }
    
    /** Get Use Organization.
    @return Use organization intercompany account to record intercompany transaction */
    public boolean isUseOrgIntercompanyAcct() 
    {
        return get_ValueAsBoolean("UseOrgIntercompanyAcct");
        
    }
    
    /** Set Use PO As Batch ID.
    @param UsePOAsBatchID Use PO number and Product's Search Key as batch ID  */
    public void setUsePOAsBatchID (boolean UsePOAsBatchID)
    {
        set_Value ("UsePOAsBatchID", Boolean.valueOf(UsePOAsBatchID));
        
    }
    
    /** Get Use PO As Batch ID.
    @return Use PO number and Product's Search Key as batch ID  */
    public boolean isUsePOAsBatchID() 
    {
        return get_ValueAsBoolean("UsePOAsBatchID");
        
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
