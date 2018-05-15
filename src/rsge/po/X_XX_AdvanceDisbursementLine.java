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
/** Generated Model for XX_AdvanceDisbursementLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_AdvanceDisbursementLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_AdvanceDisbursementLine_ID id
    @param trx transaction
    */
    public X_XX_AdvanceDisbursementLine (Ctx ctx, int XX_AdvanceDisbursementLine_ID, Trx trx)
    {
        super (ctx, XX_AdvanceDisbursementLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_AdvanceDisbursementLine_ID == 0)
        {
            setAmount (Env.ZERO);	// 0
            setIsOverBudget (false);	// N
            setLine (0);	// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM XX_AdvanceDisbursementLine WHERE XX_AdvanceDisbursement_ID=@XX_AdvanceDisbursement_ID@
            setXX_AdvanceDisbursementLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_AdvanceDisbursementLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27801854059139L;
    /** Last Updated Timestamp 2018-02-27 17:52:22.35 */
    public static final long updatedMS = 1519728742350L;
    /** AD_Table_ID=1000108 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_AdvanceDisbursementLine");
        
    }
    ;
    
    /** TableName=XX_AdvanceDisbursementLine */
    public static final String Table_Name="XX_AdvanceDisbursementLine";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set AD Charge.
    @param AdvDisbursementCharge_ID Charge used in advance disbursement */
    public void setAdvDisbursementCharge_ID (int AdvDisbursementCharge_ID)
    {
        if (AdvDisbursementCharge_ID <= 0) set_Value ("AdvDisbursementCharge_ID", null);
        else
        set_Value ("AdvDisbursementCharge_ID", Integer.valueOf(AdvDisbursementCharge_ID));
        
    }
    
    /** Get AD Charge.
    @return Charge used in advance disbursement */
    public int getAdvDisbursementCharge_ID() 
    {
        return get_ValueAsInt("AdvDisbursementCharge_ID");
        
    }
    
    /** Set Amount.
    @param Amount Amount in a defined currency */
    public void setAmount (java.math.BigDecimal Amount)
    {
        if (Amount == null) throw new IllegalArgumentException ("Amount is mandatory.");
        set_Value ("Amount", Amount);
        
    }
    
    /** Get Amount.
    @return Amount in a defined currency */
    public java.math.BigDecimal getAmount() 
    {
        return get_ValueAsBigDecimal("Amount");
        
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
        if (C_Charge_ID <= 0) set_Value ("C_Charge_ID", null);
        else
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
    
    /** Set Over Budget.
    @param IsOverBudget This record is over budget */
    public void setIsOverBudget (boolean IsOverBudget)
    {
        set_Value ("IsOverBudget", Boolean.valueOf(IsOverBudget));
        
    }
    
    /** Get Over Budget.
    @return This record is over budget */
    public boolean isOverBudget() 
    {
        return get_ValueAsBoolean("IsOverBudget");
        
    }
    
    /** Set Line No.
    @param Line Unique line for this document */
    public void setLine (int Line)
    {
        set_Value ("Line", Integer.valueOf(Line));
        
    }
    
    /** Get Line No.
    @return Unique line for this document */
    public int getLine() 
    {
        return get_ValueAsInt("Line");
        
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
    
    /** Set Remaining Budget.
    @param RemainingBudget Remaining budget amount for selected account */
    public void setRemainingBudget (java.math.BigDecimal RemainingBudget)
    {
        set_Value ("RemainingBudget", RemainingBudget);
        
    }
    
    /** Get Remaining Budget.
    @return Remaining budget amount for selected account */
    public java.math.BigDecimal getRemainingBudget() 
    {
        return get_ValueAsBigDecimal("RemainingBudget");
        
    }
    
    /** Set Advance Disbursement.
    @param XX_AdvanceDisbursement_ID Advance disbursement */
    public void setXX_AdvanceDisbursement_ID (int XX_AdvanceDisbursement_ID)
    {
        if (XX_AdvanceDisbursement_ID <= 0) set_Value ("XX_AdvanceDisbursement_ID", null);
        else
        set_Value ("XX_AdvanceDisbursement_ID", Integer.valueOf(XX_AdvanceDisbursement_ID));
        
    }
    
    /** Get Advance Disbursement.
    @return Advance disbursement */
    public int getXX_AdvanceDisbursement_ID() 
    {
        return get_ValueAsInt("XX_AdvanceDisbursement_ID");
        
    }
    
    /** Set Disbursement Line.
    @param XX_AdvanceDisbursementLine_ID Disbursement Line */
    public void setXX_AdvanceDisbursementLine_ID (int XX_AdvanceDisbursementLine_ID)
    {
        if (XX_AdvanceDisbursementLine_ID < 1) throw new IllegalArgumentException ("XX_AdvanceDisbursementLine_ID is mandatory.");
        set_ValueNoCheck ("XX_AdvanceDisbursementLine_ID", Integer.valueOf(XX_AdvanceDisbursementLine_ID));
        
    }
    
    /** Get Disbursement Line.
    @return Disbursement Line */
    public int getXX_AdvanceDisbursementLine_ID() 
    {
        return get_ValueAsInt("XX_AdvanceDisbursementLine_ID");
        
    }
    
    /** Set Disbursement Type.
    @param XX_DisbursementType_ID Type of disbursement */
    public void setXX_DisbursementType_ID (int XX_DisbursementType_ID)
    {
        if (XX_DisbursementType_ID <= 0) set_Value ("XX_DisbursementType_ID", null);
        else
        set_Value ("XX_DisbursementType_ID", Integer.valueOf(XX_DisbursementType_ID));
        
    }
    
    /** Get Disbursement Type.
    @return Type of disbursement */
    public int getXX_DisbursementType_ID() 
    {
        return get_ValueAsInt("XX_DisbursementType_ID");
        
    }
    
    
}
