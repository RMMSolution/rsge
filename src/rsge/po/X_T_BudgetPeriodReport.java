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
/** Generated Model for T_BudgetPeriodReport
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_T_BudgetPeriodReport extends PO
{
    /** Standard Constructor
    @param ctx context
    @param T_BudgetPeriodReport_ID id
    @param trx transaction
    */
    public X_T_BudgetPeriodReport (Ctx ctx, int T_BudgetPeriodReport_ID, Trx trx)
    {
        super (ctx, T_BudgetPeriodReport_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (T_BudgetPeriodReport_ID == 0)
        {
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_T_BudgetPeriodReport (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27803571254993L;
    /** Last Updated Timestamp 2018-03-19 14:52:18.204 */
    public static final long updatedMS = 1521445938204L;
    /** AD_Table_ID=1000310 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("T_BudgetPeriodReport");
        
    }
    ;
    
    /** TableName=T_BudgetPeriodReport */
    public static final String Table_Name="T_BudgetPeriodReport";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Account.
    @param Account_ID Account used */
    public void setAccount_ID (int Account_ID)
    {
        if (Account_ID <= 0) set_Value ("Account_ID", null);
        else
        set_Value ("Account_ID", Integer.valueOf(Account_ID));
        
    }
    
    /** Get Account.
    @return Account used */
    public int getAccount_ID() 
    {
        return get_ValueAsInt("Account_ID");
        
    }
    
    /** Set Budget Amount.
    @param BudgetAmt Budget amount for this entity */
    public void setBudgetAmt (java.math.BigDecimal BudgetAmt)
    {
        set_Value ("BudgetAmt", BudgetAmt);
        
    }
    
    /** Get Budget Amount.
    @return Budget amount for this entity */
    public java.math.BigDecimal getBudgetAmt() 
    {
        return get_ValueAsBigDecimal("BudgetAmt");
        
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
    
    /** Set Project Phase.
    @param C_ProjectPhase_ID Phase of a Project */
    public void setC_ProjectPhase_ID (int C_ProjectPhase_ID)
    {
        if (C_ProjectPhase_ID <= 0) set_Value ("C_ProjectPhase_ID", null);
        else
        set_Value ("C_ProjectPhase_ID", Integer.valueOf(C_ProjectPhase_ID));
        
    }
    
    /** Get Project Phase.
    @return Phase of a Project */
    public int getC_ProjectPhase_ID() 
    {
        return get_ValueAsInt("C_ProjectPhase_ID");
        
    }
    
    /** Set Project Task.
    @param C_ProjectTask_ID Actual Project Task in a Phase */
    public void setC_ProjectTask_ID (int C_ProjectTask_ID)
    {
        if (C_ProjectTask_ID <= 0) set_Value ("C_ProjectTask_ID", null);
        else
        set_Value ("C_ProjectTask_ID", Integer.valueOf(C_ProjectTask_ID));
        
    }
    
    /** Get Project Task.
    @return Actual Project Task in a Phase */
    public int getC_ProjectTask_ID() 
    {
        return get_ValueAsInt("C_ProjectTask_ID");
        
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
    
    /** Set Report Date.
    @param DateReport Expense/Time Report Date */
    public void setDateReport (Timestamp DateReport)
    {
        set_Value ("DateReport", DateReport);
        
    }
    
    /** Get Report Date.
    @return Expense/Time Report Date */
    public Timestamp getDateReport() 
    {
        return (Timestamp)get_Value("DateReport");
        
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
    
    /** Set Ratio.
    @param Ratio Relative Ratio for Distributions */
    public void setRatio (java.math.BigDecimal Ratio)
    {
        set_Value ("Ratio", Ratio);
        
    }
    
    /** Get Ratio.
    @return Relative Ratio for Distributions */
    public java.math.BigDecimal getRatio() 
    {
        return get_ValueAsBigDecimal("Ratio");
        
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
    
    /** Set Realized budget ratio.
    @param RealizedBudgetRatio Ratio in percentage between realized and budget amount */
    public void setRealizedBudgetRatio (java.math.BigDecimal RealizedBudgetRatio)
    {
        set_Value ("RealizedBudgetRatio", RealizedBudgetRatio);
        
    }
    
    /** Get Realized budget ratio.
    @return Ratio in percentage between realized and budget amount */
    public java.math.BigDecimal getRealizedBudgetRatio() 
    {
        return get_ValueAsBigDecimal("RealizedBudgetRatio");
        
    }
    
    /** Set Remaining Amt.
    @param RemainingAmt Remaining Amount */
    public void setRemainingAmt (java.math.BigDecimal RemainingAmt)
    {
        set_Value ("RemainingAmt", RemainingAmt);
        
    }
    
    /** Get Remaining Amt.
    @return Remaining Amount */
    public java.math.BigDecimal getRemainingAmt() 
    {
        return get_ValueAsBigDecimal("RemainingAmt");
        
    }
    
    /** Set Reserved Amount.
    @param ReservedAmt Amount which already reserved by purchase requisition */
    public void setReservedAmt (java.math.BigDecimal ReservedAmt)
    {
        set_Value ("ReservedAmt", ReservedAmt);
        
    }
    
    /** Get Reserved Amount.
    @return Amount which already reserved by purchase requisition */
    public java.math.BigDecimal getReservedAmt() 
    {
        return get_ValueAsBigDecimal("ReservedAmt");
        
    }
    
    /** Set Reserved Budget Ratio.
    @param ReservedBudgetRatio Ratio in percentage between reserved and budget amount */
    public void setReservedBudgetRatio (java.math.BigDecimal ReservedBudgetRatio)
    {
        set_Value ("ReservedBudgetRatio", ReservedBudgetRatio);
        
    }
    
    /** Get Reserved Budget Ratio.
    @return Ratio in percentage between reserved and budget amount */
    public java.math.BigDecimal getReservedBudgetRatio() 
    {
        return get_ValueAsBigDecimal("ReservedBudgetRatio");
        
    }
    
    /** Set Unrealized amount.
    @param UnrealizedAmt Amount which has not been realized yet */
    public void setUnrealizedAmt (java.math.BigDecimal UnrealizedAmt)
    {
        set_Value ("UnrealizedAmt", UnrealizedAmt);
        
    }
    
    /** Get Unrealized amount.
    @return Amount which has not been realized yet */
    public java.math.BigDecimal getUnrealizedAmt() 
    {
        return get_ValueAsBigDecimal("UnrealizedAmt");
        
    }
    
    /** Set Unrealized budget ratio.
    @param UnrealizedBudgetRatio Ratio in percentage between unrealized and budget amount */
    public void setUnrealizedBudgetRatio (java.math.BigDecimal UnrealizedBudgetRatio)
    {
        set_Value ("UnrealizedBudgetRatio", UnrealizedBudgetRatio);
        
    }
    
    /** Get Unrealized budget ratio.
    @return Ratio in percentage between unrealized and budget amount */
    public java.math.BigDecimal getUnrealizedBudgetRatio() 
    {
        return get_ValueAsBigDecimal("UnrealizedBudgetRatio");
        
    }
    
    
}
