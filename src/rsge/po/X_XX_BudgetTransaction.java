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
/** Generated Model for XX_BudgetTransaction
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_BudgetTransaction extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_BudgetTransaction_ID id
    @param trx transaction
    */
    public X_XX_BudgetTransaction (Ctx ctx, int XX_BudgetTransaction_ID, Trx trx)
    {
        super (ctx, XX_BudgetTransaction_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_BudgetTransaction_ID == 0)
        {
            setXX_BudgetTransaction_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_BudgetTransaction (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27803686506988L;
    /** Last Updated Timestamp 2018-03-20 22:53:10.199 */
    public static final long updatedMS = 1521561190199L;
    /** AD_Table_ID=1000309 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_BudgetTransaction");
        
    }
    ;
    
    /** TableName=XX_BudgetTransaction */
    public static final String Table_Name="XX_BudgetTransaction";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Asset.
    @param A_Asset_ID Asset used internally or by customers */
    public void setA_Asset_ID (int A_Asset_ID)
    {
        if (A_Asset_ID <= 0) set_Value ("A_Asset_ID", null);
        else
        set_Value ("A_Asset_ID", Integer.valueOf(A_Asset_ID));
        
    }
    
    /** Get Asset.
    @return Asset used internally or by customers */
    public int getA_Asset_ID() 
    {
        return get_ValueAsInt("A_Asset_ID");
        
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
    
    /** Set Budget date.
    @param BudgetDate Indicate which date is used for budget calculation */
    public void setBudgetDate (Timestamp BudgetDate)
    {
        set_Value ("BudgetDate", BudgetDate);
        
    }
    
    /** Get Budget date.
    @return Indicate which date is used for budget calculation */
    public Timestamp getBudgetDate() 
    {
        return (Timestamp)get_Value("BudgetDate");
        
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
    
    /** Set Location From.
    @param C_LocFrom_ID Location that inventory was moved from */
    public void setC_LocFrom_ID (int C_LocFrom_ID)
    {
        if (C_LocFrom_ID <= 0) set_Value ("C_LocFrom_ID", null);
        else
        set_Value ("C_LocFrom_ID", Integer.valueOf(C_LocFrom_ID));
        
    }
    
    /** Get Location From.
    @return Location that inventory was moved from */
    public int getC_LocFrom_ID() 
    {
        return get_ValueAsInt("C_LocFrom_ID");
        
    }
    
    /** Set Location To.
    @param C_LocTo_ID Location that inventory was moved to */
    public void setC_LocTo_ID (int C_LocTo_ID)
    {
        if (C_LocTo_ID <= 0) set_Value ("C_LocTo_ID", null);
        else
        set_Value ("C_LocTo_ID", Integer.valueOf(C_LocTo_ID));
        
    }
    
    /** Get Location To.
    @return Location that inventory was moved to */
    public int getC_LocTo_ID() 
    {
        return get_ValueAsInt("C_LocTo_ID");
        
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
    
    /** Set Sub Account.
    @param C_SubAcct_ID Sub account for Element Value */
    public void setC_SubAcct_ID (int C_SubAcct_ID)
    {
        if (C_SubAcct_ID <= 0) set_Value ("C_SubAcct_ID", null);
        else
        set_Value ("C_SubAcct_ID", Integer.valueOf(C_SubAcct_ID));
        
    }
    
    /** Get Sub Account.
    @return Sub account for Element Value */
    public int getC_SubAcct_ID() 
    {
        return get_ValueAsInt("C_SubAcct_ID");
        
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
    
    /** Set User List 1.
    @param User1_ID User defined list element #1 */
    public void setUser1_ID (int User1_ID)
    {
        if (User1_ID <= 0) set_Value ("User1_ID", null);
        else
        set_Value ("User1_ID", Integer.valueOf(User1_ID));
        
    }
    
    /** Get User List 1.
    @return User defined list element #1 */
    public int getUser1_ID() 
    {
        return get_ValueAsInt("User1_ID");
        
    }
    
    /** Set User List 2.
    @param User2_ID User defined list element #2 */
    public void setUser2_ID (int User2_ID)
    {
        if (User2_ID <= 0) set_Value ("User2_ID", null);
        else
        set_Value ("User2_ID", Integer.valueOf(User2_ID));
        
    }
    
    /** Get User List 2.
    @return User defined list element #2 */
    public int getUser2_ID() 
    {
        return get_ValueAsInt("User2_ID");
        
    }
    
    /** Set User Element 1.
    @param UserElement1_ID User defined accounting Element */
    public void setUserElement1_ID (int UserElement1_ID)
    {
        if (UserElement1_ID <= 0) set_Value ("UserElement1_ID", null);
        else
        set_Value ("UserElement1_ID", Integer.valueOf(UserElement1_ID));
        
    }
    
    /** Get User Element 1.
    @return User defined accounting Element */
    public int getUserElement1_ID() 
    {
        return get_ValueAsInt("UserElement1_ID");
        
    }
    
    /** Set User Element 2.
    @param UserElement2_ID User defined accounting Element */
    public void setUserElement2_ID (int UserElement2_ID)
    {
        if (UserElement2_ID <= 0) set_Value ("UserElement2_ID", null);
        else
        set_Value ("UserElement2_ID", Integer.valueOf(UserElement2_ID));
        
    }
    
    /** Get User Element 2.
    @return User defined accounting Element */
    public int getUserElement2_ID() 
    {
        return get_ValueAsInt("UserElement2_ID");
        
    }
    
    /** Set Budget Transaction.
    @param XX_BudgetTransaction_ID Budget transaction */
    public void setXX_BudgetTransaction_ID (int XX_BudgetTransaction_ID)
    {
        if (XX_BudgetTransaction_ID < 1) throw new IllegalArgumentException ("XX_BudgetTransaction_ID is mandatory.");
        set_ValueNoCheck ("XX_BudgetTransaction_ID", Integer.valueOf(XX_BudgetTransaction_ID));
        
    }
    
    /** Get Budget Transaction.
    @return Budget transaction */
    public int getXX_BudgetTransaction_ID() 
    {
        return get_ValueAsInt("XX_BudgetTransaction_ID");
        
    }
    
    
}
