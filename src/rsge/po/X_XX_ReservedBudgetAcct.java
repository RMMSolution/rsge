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
/** Generated Model for XX_ReservedBudgetAcct
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_ReservedBudgetAcct extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_ReservedBudgetAcct_ID id
    @param trx transaction
    */
    public X_XX_ReservedBudgetAcct (Ctx ctx, int XX_ReservedBudgetAcct_ID, Trx trx)
    {
        super (ctx, XX_ReservedBudgetAcct_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_ReservedBudgetAcct_ID == 0)
        {
            setAccount_ID (0);
            setRemainingBudgetQty (Env.ZERO);	// 0
            setReservedQty (Env.ZERO);	// 0
            setXX_ReservedBudgetAcct_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_ReservedBudgetAcct (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27672974566789L;
    /** Last Updated Timestamp 2014-01-28 02:00:50.0 */
    public static final long updatedMS = 1390849250000L;
    /** AD_Table_ID=1000181 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_ReservedBudgetAcct");
        
    }
    ;
    
    /** TableName=XX_ReservedBudgetAcct */
    public static final String Table_Name="XX_ReservedBudgetAcct";
    
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
        if (RemainingBudgetQty == null) throw new IllegalArgumentException ("RemainingBudgetQty is mandatory.");
        set_Value ("RemainingBudgetQty", RemainingBudgetQty);
        
    }
    
    /** Get Remaining Qty.
    @return Remaning Quantity */
    public java.math.BigDecimal getRemainingBudgetQty() 
    {
        return get_ValueAsBigDecimal("RemainingBudgetQty");
        
    }
    
    /** Set Reserved Budget Amount.
    @param ReservedBudgetAmt Reserved budget amount */
    public void setReservedBudgetAmt (java.math.BigDecimal ReservedBudgetAmt)
    {
        set_Value ("ReservedBudgetAmt", ReservedBudgetAmt);
        
    }
    
    /** Get Reserved Budget Amount.
    @return Reserved budget amount */
    public java.math.BigDecimal getReservedBudgetAmt() 
    {
        return get_ValueAsBigDecimal("ReservedBudgetAmt");
        
    }
    
    /** Set Reserved Qty.
    @param ReservedQty Reserved quantity */
    public void setReservedQty (java.math.BigDecimal ReservedQty)
    {
        if (ReservedQty == null) throw new IllegalArgumentException ("ReservedQty is mandatory.");
        set_Value ("ReservedQty", ReservedQty);
        
    }
    
    /** Get Reserved Qty.
    @return Reserved quantity */
    public java.math.BigDecimal getReservedQty() 
    {
        return get_ValueAsBigDecimal("ReservedQty");
        
    }
    
    /** Set Reserved Budget.
    @param XX_ReservedBudget_ID Part of budget which is reserved for future used */
    public void setXX_ReservedBudget_ID (int XX_ReservedBudget_ID)
    {
        if (XX_ReservedBudget_ID <= 0) set_Value ("XX_ReservedBudget_ID", null);
        else
        set_Value ("XX_ReservedBudget_ID", Integer.valueOf(XX_ReservedBudget_ID));
        
    }
    
    /** Get Reserved Budget.
    @return Part of budget which is reserved for future used */
    public int getXX_ReservedBudget_ID() 
    {
        return get_ValueAsInt("XX_ReservedBudget_ID");
        
    }
    
    /** Set Budget Account.
    @param XX_ReservedBudgetAcct_ID Budget Account */
    public void setXX_ReservedBudgetAcct_ID (int XX_ReservedBudgetAcct_ID)
    {
        if (XX_ReservedBudgetAcct_ID < 1) throw new IllegalArgumentException ("XX_ReservedBudgetAcct_ID is mandatory.");
        set_ValueNoCheck ("XX_ReservedBudgetAcct_ID", Integer.valueOf(XX_ReservedBudgetAcct_ID));
        
    }
    
    /** Get Budget Account.
    @return Budget Account */
    public int getXX_ReservedBudgetAcct_ID() 
    {
        return get_ValueAsInt("XX_ReservedBudgetAcct_ID");
        
    }
    
    
}
