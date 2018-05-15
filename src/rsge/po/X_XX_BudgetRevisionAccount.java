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
/** Generated Model for XX_BudgetRevisionAccount
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_BudgetRevisionAccount extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_BudgetRevisionAccount_ID id
    @param trx transaction
    */
    public X_XX_BudgetRevisionAccount (Ctx ctx, int XX_BudgetRevisionAccount_ID, Trx trx)
    {
        super (ctx, XX_BudgetRevisionAccount_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_BudgetRevisionAccount_ID == 0)
        {
            setAccount_ID (0);
            setIsInvestmentAccount (false);	// N
            setIsProduct (false);	// N
            setXX_BudgetRevisionAccount_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_BudgetRevisionAccount (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27791659467635L;
    /** Last Updated Timestamp 2017-11-01 18:02:30.846 */
    public static final long updatedMS = 1509534150846L;
    /** AD_Table_ID=1000120 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_BudgetRevisionAccount");
        
    }
    ;
    
    /** TableName=XX_BudgetRevisionAccount */
    public static final String Table_Name="XX_BudgetRevisionAccount";
    
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
        if (Account_ID < 1) throw new IllegalArgumentException ("Account_ID is mandatory.");
        set_Value ("Account_ID", Integer.valueOf(Account_ID));
        
    }
    
    /** Get Account.
    @return Account used */
    public int getAccount_ID() 
    {
        return get_ValueAsInt("Account_ID");
        
    }
    
    /** Set Applied Amount.
    @param AppliedAmt Amount applied to this document */
    public void setAppliedAmt (java.math.BigDecimal AppliedAmt)
    {
        set_Value ("AppliedAmt", AppliedAmt);
        
    }
    
    /** Get Applied Amount.
    @return Amount applied to this document */
    public java.math.BigDecimal getAppliedAmt() 
    {
        return get_ValueAsBigDecimal("AppliedAmt");
        
    }
    
    /** Set Applied Qty.
    @param AppliedQty Applied quantity */
    public void setAppliedQty (java.math.BigDecimal AppliedQty)
    {
        set_Value ("AppliedQty", AppliedQty);
        
    }
    
    /** Get Applied Qty.
    @return Applied quantity */
    public java.math.BigDecimal getAppliedQty() 
    {
        return get_ValueAsBigDecimal("AppliedQty");
        
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
    
    /** Set Investment Account.
    @param IsInvestmentAccount This account is investment account */
    public void setIsInvestmentAccount (boolean IsInvestmentAccount)
    {
        set_Value ("IsInvestmentAccount", Boolean.valueOf(IsInvestmentAccount));
        
    }
    
    /** Get Investment Account.
    @return This account is investment account */
    public boolean isInvestmentAccount() 
    {
        return get_ValueAsBoolean("IsInvestmentAccount");
        
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
    
    /** Set Price.
    @param Price Price */
    public void setPrice (java.math.BigDecimal Price)
    {
        set_Value ("Price", Price);
        
    }
    
    /** Get Price.
    @return Price */
    public java.math.BigDecimal getPrice() 
    {
        return get_ValueAsBigDecimal("Price");
        
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
    
    /** Set Remaining Qty.
    @param RemainingBudgetQty Remaning Quantity */
    public void setRemainingBudgetQty (java.math.BigDecimal RemainingBudgetQty)
    {
        set_Value ("RemainingBudgetQty", RemainingBudgetQty);
        
    }
    
    /** Get Remaining Qty.
    @return Remaning Quantity */
    public java.math.BigDecimal getRemainingBudgetQty() 
    {
        return get_ValueAsBigDecimal("RemainingBudgetQty");
        
    }
    
    /** Set Revised Amount.
    @param RevisedAmt Revised amount */
    public void setRevisedAmt (java.math.BigDecimal RevisedAmt)
    {
        set_Value ("RevisedAmt", RevisedAmt);
        
    }
    
    /** Get Revised Amount.
    @return Revised amount */
    public java.math.BigDecimal getRevisedAmt() 
    {
        return get_ValueAsBigDecimal("RevisedAmt");
        
    }
    
    /** Set Revised Qty.
    @param RevisedQty Revised quantity */
    public void setRevisedQty (java.math.BigDecimal RevisedQty)
    {
        set_Value ("RevisedQty", RevisedQty);
        
    }
    
    /** Get Revised Qty.
    @return Revised quantity */
    public java.math.BigDecimal getRevisedQty() 
    {
        return get_ValueAsBigDecimal("RevisedQty");
        
    }
    
    /** Set Budget Revision.
    @param XX_BudgetRevision_ID Revise amount of specific budget */
    public void setXX_BudgetRevision_ID (int XX_BudgetRevision_ID)
    {
        if (XX_BudgetRevision_ID <= 0) set_Value ("XX_BudgetRevision_ID", null);
        else
        set_Value ("XX_BudgetRevision_ID", Integer.valueOf(XX_BudgetRevision_ID));
        
    }
    
    /** Get Budget Revision.
    @return Revise amount of specific budget */
    public int getXX_BudgetRevision_ID() 
    {
        return get_ValueAsInt("XX_BudgetRevision_ID");
        
    }
    
    /** Set Account.
    @param XX_BudgetRevisionAccount_ID Budget Revision Account */
    public void setXX_BudgetRevisionAccount_ID (int XX_BudgetRevisionAccount_ID)
    {
        if (XX_BudgetRevisionAccount_ID < 1) throw new IllegalArgumentException ("XX_BudgetRevisionAccount_ID is mandatory.");
        set_ValueNoCheck ("XX_BudgetRevisionAccount_ID", Integer.valueOf(XX_BudgetRevisionAccount_ID));
        
    }
    
    /** Get Account.
    @return Budget Revision Account */
    public int getXX_BudgetRevisionAccount_ID() 
    {
        return get_ValueAsInt("XX_BudgetRevisionAccount_ID");
        
    }
    
    
}
