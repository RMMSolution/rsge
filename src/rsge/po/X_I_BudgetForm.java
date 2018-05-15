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
/** Generated Model for I_BudgetForm
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_I_BudgetForm extends PO
{
    /** Standard Constructor
    @param ctx context
    @param I_BudgetForm_ID id
    @param trx transaction
    */
    public X_I_BudgetForm (Ctx ctx, int I_BudgetForm_ID, Trx trx)
    {
        super (ctx, I_BudgetForm_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (I_BudgetForm_ID == 0)
        {
            setI_BudgetForm_ID (0);
            setI_IsImported (null);	// N
            setIsProduct (false);	// N
            setIsSOTrx (false);	// N
            setProcessed (false);	// N
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_I_BudgetForm (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27691103032789L;
    /** Last Updated Timestamp 2014-08-25 21:41:56.0 */
    public static final long updatedMS = 1408977716000L;
    /** AD_Table_ID=1000100 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("I_BudgetForm");
        
    }
    ;
    
    /** TableName=I_BudgetForm */
    public static final String Table_Name="I_BudgetForm";
    
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
    
    /** Set Account Key.
    @param AccountValue Key of Account Element */
    public void setAccountValue (String AccountValue)
    {
        set_Value ("AccountValue", AccountValue);
        
    }
    
    /** Get Account Key.
    @return Key of Account Element */
    public String getAccountValue() 
    {
        return (String)get_Value("AccountValue");
        
    }
    
    /** Set Activity Key.
    @param ActivityValue Search Key for an Activity */
    public void setActivityValue (String ActivityValue)
    {
        set_Value ("ActivityValue", ActivityValue);
        
    }
    
    /** Get Activity Key.
    @return Search Key for an Activity */
    public String getActivityValue() 
    {
        return (String)get_Value("ActivityValue");
        
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
    
    /** Set Currency.
    @param C_Currency_ID The Currency for this record */
    public void setC_Currency_ID (int C_Currency_ID)
    {
        if (C_Currency_ID <= 0) set_Value ("C_Currency_ID", null);
        else
        set_Value ("C_Currency_ID", Integer.valueOf(C_Currency_ID));
        
    }
    
    /** Get Currency.
    @return The Currency for this record */
    public int getC_Currency_ID() 
    {
        return get_ValueAsInt("C_Currency_ID");
        
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
    
    /** Set Campaign Key.
    @param CampaignValue Search key for a Campaign */
    public void setCampaignValue (String CampaignValue)
    {
        set_Value ("CampaignValue", CampaignValue);
        
    }
    
    /** Get Campaign Key.
    @return Search key for a Campaign */
    public String getCampaignValue() 
    {
        return (String)get_Value("CampaignValue");
        
    }
    
    /** Set Import Operational Budget.
    @param I_BudgetForm_ID Import Operational Budget */
    public void setI_BudgetForm_ID (int I_BudgetForm_ID)
    {
        if (I_BudgetForm_ID < 1) throw new IllegalArgumentException ("I_BudgetForm_ID is mandatory.");
        set_ValueNoCheck ("I_BudgetForm_ID", Integer.valueOf(I_BudgetForm_ID));
        
    }
    
    /** Get Import Operational Budget.
    @return Import Operational Budget */
    public int getI_BudgetForm_ID() 
    {
        return get_ValueAsInt("I_BudgetForm_ID");
        
    }
    
    /** Set Import Error Message.
    @param I_ErrorMsg Messages generated from import process */
    public void setI_ErrorMsg (String I_ErrorMsg)
    {
        set_Value ("I_ErrorMsg", I_ErrorMsg);
        
    }
    
    /** Get Import Error Message.
    @return Messages generated from import process */
    public String getI_ErrorMsg() 
    {
        return (String)get_Value("I_ErrorMsg");
        
    }
    
    /** Error = E */
    public static final String I_ISIMPORTED_Error = X_Ref__IsImported.ERROR.getValue();
    /** No = N */
    public static final String I_ISIMPORTED_No = X_Ref__IsImported.NO.getValue();
    /** Yes = Y */
    public static final String I_ISIMPORTED_Yes = X_Ref__IsImported.YES.getValue();
    /** Set Imported.
    @param I_IsImported Has this import been processed? */
    public void setI_IsImported (String I_IsImported)
    {
        if (I_IsImported == null) throw new IllegalArgumentException ("I_IsImported is mandatory");
        if (!X_Ref__IsImported.isValid(I_IsImported))
        throw new IllegalArgumentException ("I_IsImported Invalid value - " + I_IsImported + " - Reference_ID=420 - E - N - Y");
        set_Value ("I_IsImported", I_IsImported);
        
    }
    
    /** Get Imported.
    @return Has this import been processed? */
    public String getI_IsImported() 
    {
        return (String)get_Value("I_IsImported");
        
    }
    
    /** Set ISO Currency Code.
    @param ISO_Code Three letter ISO 4217 Code of the Currency */
    public void setISO_Code (String ISO_Code)
    {
        set_Value ("ISO_Code", ISO_Code);
        
    }
    
    /** Get ISO Currency Code.
    @return Three letter ISO 4217 Code of the Currency */
    public String getISO_Code() 
    {
        return (String)get_Value("ISO_Code");
        
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
    
    /** Set Organization Key.
    @param OrgValue Key of the Organization */
    public void setOrgValue (String OrgValue)
    {
        set_Value ("OrgValue", OrgValue);
        
    }
    
    /** Get Organization Key.
    @return Key of the Organization */
    public String getOrgValue() 
    {
        return (String)get_Value("OrgValue");
        
    }
    
    /** Set 1st Period.
    @param Period1 First period budget amount */
    public void setPeriod1 (java.math.BigDecimal Period1)
    {
        set_Value ("Period1", Period1);
        
    }
    
    /** Get 1st Period.
    @return First period budget amount */
    public java.math.BigDecimal getPeriod1() 
    {
        return get_ValueAsBigDecimal("Period1");
        
    }
    
    /** Set 10th Period.
    @param Period10 Tenth period budget amount */
    public void setPeriod10 (java.math.BigDecimal Period10)
    {
        set_Value ("Period10", Period10);
        
    }
    
    /** Get 10th Period.
    @return Tenth period budget amount */
    public java.math.BigDecimal getPeriod10() 
    {
        return get_ValueAsBigDecimal("Period10");
        
    }
    
    /** Set 11th Period.
    @param Period11 Eleventh period budget amount */
    public void setPeriod11 (java.math.BigDecimal Period11)
    {
        set_Value ("Period11", Period11);
        
    }
    
    /** Get 11th Period.
    @return Eleventh period budget amount */
    public java.math.BigDecimal getPeriod11() 
    {
        return get_ValueAsBigDecimal("Period11");
        
    }
    
    /** Set 12th Period.
    @param Period12 Twelveth period budget amount */
    public void setPeriod12 (java.math.BigDecimal Period12)
    {
        set_Value ("Period12", Period12);
        
    }
    
    /** Get 12th Period.
    @return Twelveth period budget amount */
    public java.math.BigDecimal getPeriod12() 
    {
        return get_ValueAsBigDecimal("Period12");
        
    }
    
    /** Set 2nd Period.
    @param Period2 Second period budget amount */
    public void setPeriod2 (java.math.BigDecimal Period2)
    {
        set_Value ("Period2", Period2);
        
    }
    
    /** Get 2nd Period.
    @return Second period budget amount */
    public java.math.BigDecimal getPeriod2() 
    {
        return get_ValueAsBigDecimal("Period2");
        
    }
    
    /** Set 3rd Period.
    @param Period3 Third period budget amount */
    public void setPeriod3 (java.math.BigDecimal Period3)
    {
        set_Value ("Period3", Period3);
        
    }
    
    /** Get 3rd Period.
    @return Third period budget amount */
    public java.math.BigDecimal getPeriod3() 
    {
        return get_ValueAsBigDecimal("Period3");
        
    }
    
    /** Set 4th Period.
    @param Period4 Fourth period budget amount */
    public void setPeriod4 (java.math.BigDecimal Period4)
    {
        set_Value ("Period4", Period4);
        
    }
    
    /** Get 4th Period.
    @return Fourth period budget amount */
    public java.math.BigDecimal getPeriod4() 
    {
        return get_ValueAsBigDecimal("Period4");
        
    }
    
    /** Set 5th Period.
    @param Period5 Fifth period budget amount */
    public void setPeriod5 (java.math.BigDecimal Period5)
    {
        set_Value ("Period5", Period5);
        
    }
    
    /** Get 5th Period.
    @return Fifth period budget amount */
    public java.math.BigDecimal getPeriod5() 
    {
        return get_ValueAsBigDecimal("Period5");
        
    }
    
    /** Set 6th Period.
    @param Period6 Sixth period budget amount */
    public void setPeriod6 (java.math.BigDecimal Period6)
    {
        set_Value ("Period6", Period6);
        
    }
    
    /** Get 6th Period.
    @return Sixth period budget amount */
    public java.math.BigDecimal getPeriod6() 
    {
        return get_ValueAsBigDecimal("Period6");
        
    }
    
    /** Set 7th Period.
    @param Period7 Seventh period budget amount */
    public void setPeriod7 (java.math.BigDecimal Period7)
    {
        set_Value ("Period7", Period7);
        
    }
    
    /** Get 7th Period.
    @return Seventh period budget amount */
    public java.math.BigDecimal getPeriod7() 
    {
        return get_ValueAsBigDecimal("Period7");
        
    }
    
    /** Set 8th Period.
    @param Period8 Eighth period budget amount */
    public void setPeriod8 (java.math.BigDecimal Period8)
    {
        set_Value ("Period8", Period8);
        
    }
    
    /** Get 8th Period.
    @return Eighth period budget amount */
    public java.math.BigDecimal getPeriod8() 
    {
        return get_ValueAsBigDecimal("Period8");
        
    }
    
    /** Set 9th Period.
    @param Period9 Ninth period budget amount */
    public void setPeriod9 (java.math.BigDecimal Period9)
    {
        set_Value ("Period9", Period9);
        
    }
    
    /** Get 9th Period.
    @return Ninth period budget amount */
    public java.math.BigDecimal getPeriod9() 
    {
        return get_ValueAsBigDecimal("Period9");
        
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
    
    /** Set Product Key.
    @param ProductValue Key of the Product */
    public void setProductValue (String ProductValue)
    {
        set_Value ("ProductValue", ProductValue);
        
    }
    
    /** Get Product Key.
    @return Key of the Product */
    public String getProductValue() 
    {
        return (String)get_Value("ProductValue");
        
    }
    
    /** Set Project Key.
    @param ProjectValue Key of the Project */
    public void setProjectValue (String ProjectValue)
    {
        set_Value ("ProjectValue", ProjectValue);
        
    }
    
    /** Get Project Key.
    @return Key of the Project */
    public String getProjectValue() 
    {
        return (String)get_Value("ProjectValue");
        
    }
    
    /** Set Qty Period 1.
    @param QtyPeriod1 Qty in first period */
    public void setQtyPeriod1 (java.math.BigDecimal QtyPeriod1)
    {
        set_Value ("QtyPeriod1", QtyPeriod1);
        
    }
    
    /** Get Qty Period 1.
    @return Qty in first period */
    public java.math.BigDecimal getQtyPeriod1() 
    {
        return get_ValueAsBigDecimal("QtyPeriod1");
        
    }
    
    /** Set Qty Period 10.
    @param QtyPeriod10 Qty in tenth period */
    public void setQtyPeriod10 (java.math.BigDecimal QtyPeriod10)
    {
        set_Value ("QtyPeriod10", QtyPeriod10);
        
    }
    
    /** Get Qty Period 10.
    @return Qty in tenth period */
    public java.math.BigDecimal getQtyPeriod10() 
    {
        return get_ValueAsBigDecimal("QtyPeriod10");
        
    }
    
    /** Set Qty Period 11.
    @param QtyPeriod11 Qty in eleventh period */
    public void setQtyPeriod11 (java.math.BigDecimal QtyPeriod11)
    {
        set_Value ("QtyPeriod11", QtyPeriod11);
        
    }
    
    /** Get Qty Period 11.
    @return Qty in eleventh period */
    public java.math.BigDecimal getQtyPeriod11() 
    {
        return get_ValueAsBigDecimal("QtyPeriod11");
        
    }
    
    /** Set Qty Period 12.
    @param QtyPeriod12 Qty in twelveth period */
    public void setQtyPeriod12 (java.math.BigDecimal QtyPeriod12)
    {
        set_Value ("QtyPeriod12", QtyPeriod12);
        
    }
    
    /** Get Qty Period 12.
    @return Qty in twelveth period */
    public java.math.BigDecimal getQtyPeriod12() 
    {
        return get_ValueAsBigDecimal("QtyPeriod12");
        
    }
    
    /** Set Qty Period 2.
    @param QtyPeriod2 Qty in second period */
    public void setQtyPeriod2 (java.math.BigDecimal QtyPeriod2)
    {
        set_Value ("QtyPeriod2", QtyPeriod2);
        
    }
    
    /** Get Qty Period 2.
    @return Qty in second period */
    public java.math.BigDecimal getQtyPeriod2() 
    {
        return get_ValueAsBigDecimal("QtyPeriod2");
        
    }
    
    /** Set Qty Period 3.
    @param QtyPeriod3 Qty in third period */
    public void setQtyPeriod3 (java.math.BigDecimal QtyPeriod3)
    {
        set_Value ("QtyPeriod3", QtyPeriod3);
        
    }
    
    /** Get Qty Period 3.
    @return Qty in third period */
    public java.math.BigDecimal getQtyPeriod3() 
    {
        return get_ValueAsBigDecimal("QtyPeriod3");
        
    }
    
    /** Set Qty Period 4.
    @param QtyPeriod4 Qty in forth period */
    public void setQtyPeriod4 (java.math.BigDecimal QtyPeriod4)
    {
        set_Value ("QtyPeriod4", QtyPeriod4);
        
    }
    
    /** Get Qty Period 4.
    @return Qty in forth period */
    public java.math.BigDecimal getQtyPeriod4() 
    {
        return get_ValueAsBigDecimal("QtyPeriod4");
        
    }
    
    /** Set Qty Period 5.
    @param QtyPeriod5 Qty in fifth period */
    public void setQtyPeriod5 (java.math.BigDecimal QtyPeriod5)
    {
        set_Value ("QtyPeriod5", QtyPeriod5);
        
    }
    
    /** Get Qty Period 5.
    @return Qty in fifth period */
    public java.math.BigDecimal getQtyPeriod5() 
    {
        return get_ValueAsBigDecimal("QtyPeriod5");
        
    }
    
    /** Set Qty Period 6.
    @param QtyPeriod6 Qty in sixth period */
    public void setQtyPeriod6 (java.math.BigDecimal QtyPeriod6)
    {
        set_Value ("QtyPeriod6", QtyPeriod6);
        
    }
    
    /** Get Qty Period 6.
    @return Qty in sixth period */
    public java.math.BigDecimal getQtyPeriod6() 
    {
        return get_ValueAsBigDecimal("QtyPeriod6");
        
    }
    
    /** Set Qty Period 7.
    @param QtyPeriod7 Qty in seventh period */
    public void setQtyPeriod7 (java.math.BigDecimal QtyPeriod7)
    {
        set_Value ("QtyPeriod7", QtyPeriod7);
        
    }
    
    /** Get Qty Period 7.
    @return Qty in seventh period */
    public java.math.BigDecimal getQtyPeriod7() 
    {
        return get_ValueAsBigDecimal("QtyPeriod7");
        
    }
    
    /** Set Qty Period 8.
    @param QtyPeriod8 Qty in eight period */
    public void setQtyPeriod8 (java.math.BigDecimal QtyPeriod8)
    {
        set_Value ("QtyPeriod8", QtyPeriod8);
        
    }
    
    /** Get Qty Period 8.
    @return Qty in eight period */
    public java.math.BigDecimal getQtyPeriod8() 
    {
        return get_ValueAsBigDecimal("QtyPeriod8");
        
    }
    
    /** Set Qty Period 9.
    @param QtyPeriod9 Qty in ninth period */
    public void setQtyPeriod9 (java.math.BigDecimal QtyPeriod9)
    {
        set_Value ("QtyPeriod9", QtyPeriod9);
        
    }
    
    /** Get Qty Period 9.
    @return Qty in ninth period */
    public java.math.BigDecimal getQtyPeriod9() 
    {
        return get_ValueAsBigDecimal("QtyPeriod9");
        
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
    
    /** Set Budget Form.
    @param XX_BudgetForm_ID Form use to propose budget */
    public void setXX_BudgetForm_ID (int XX_BudgetForm_ID)
    {
        if (XX_BudgetForm_ID <= 0) set_Value ("XX_BudgetForm_ID", null);
        else
        set_Value ("XX_BudgetForm_ID", Integer.valueOf(XX_BudgetForm_ID));
        
    }
    
    /** Get Budget Form.
    @return Form use to propose budget */
    public int getXX_BudgetForm_ID() 
    {
        return get_ValueAsInt("XX_BudgetForm_ID");
        
    }
    
    /** Set Budget Form Line.
    @param XX_BudgetFormLine_ID Form Line */
    public void setXX_BudgetFormLine_ID (int XX_BudgetFormLine_ID)
    {
        if (XX_BudgetFormLine_ID <= 0) set_Value ("XX_BudgetFormLine_ID", null);
        else
        set_Value ("XX_BudgetFormLine_ID", Integer.valueOf(XX_BudgetFormLine_ID));
        
    }
    
    /** Get Budget Form Line.
    @return Form Line */
    public int getXX_BudgetFormLine_ID() 
    {
        return get_ValueAsInt("XX_BudgetFormLine_ID");
        
    }
    
    
}
