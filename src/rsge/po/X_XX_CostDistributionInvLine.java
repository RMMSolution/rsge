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
/** Generated Model for XX_CostDistributionInvLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_CostDistributionInvLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_CostDistributionInvLine_ID id
    @param trx transaction
    */
    public X_XX_CostDistributionInvLine (Ctx ctx, int XX_CostDistributionInvLine_ID, Trx trx)
    {
        super (ctx, XX_CostDistributionInvLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_CostDistributionInvLine_ID == 0)
        {
            setC_InvoiceLine_ID (0);
            setIsVoid (false);	// N
            setProcessed (false);	// N
            setXX_CostDistributionInvLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_CostDistributionInvLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27720920608876L;
    /** Last Updated Timestamp 2015-08-06 00:21:32.087 */
    public static final long updatedMS = 1438795292087L;
    /** AD_Table_ID=1004526 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_CostDistributionInvLine");
        
    }
    ;
    
    /** TableName=XX_CostDistributionInvLine */
    public static final String Table_Name="XX_CostDistributionInvLine";
    
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
    
    /** Set Invoice Line.
    @param C_InvoiceLine_ID Invoice Detail Line */
    public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
    {
        if (C_InvoiceLine_ID < 1) throw new IllegalArgumentException ("C_InvoiceLine_ID is mandatory.");
        set_Value ("C_InvoiceLine_ID", Integer.valueOf(C_InvoiceLine_ID));
        
    }
    
    /** Get Invoice Line.
    @return Invoice Detail Line */
    public int getC_InvoiceLine_ID() 
    {
        return get_ValueAsInt("C_InvoiceLine_ID");
        
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
    
    /** Set UOM.
    @param C_UOM_ID Unit of Measure */
    public void setC_UOM_ID (int C_UOM_ID)
    {
        if (C_UOM_ID <= 0) set_Value ("C_UOM_ID", null);
        else
        set_Value ("C_UOM_ID", Integer.valueOf(C_UOM_ID));
        
    }
    
    /** Get UOM.
    @return Unit of Measure */
    public int getC_UOM_ID() 
    {
        return get_ValueAsInt("C_UOM_ID");
        
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
    
    /** Set Void.
    @param IsVoid Void */
    public void setIsVoid (boolean IsVoid)
    {
        set_Value ("IsVoid", Boolean.valueOf(IsVoid));
        
    }
    
    /** Get Void.
    @return Void */
    public boolean isVoid() 
    {
        return get_ValueAsBoolean("IsVoid");
        
    }
    
    /** Set Line Amount.
    @param LineNetAmt Line Extended Amount (Quantity * Actual Price) without Freight and Charges */
    public void setLineNetAmt (java.math.BigDecimal LineNetAmt)
    {
        set_Value ("LineNetAmt", LineNetAmt);
        
    }
    
    /** Get Line Amount.
    @return Line Extended Amount (Quantity * Actual Price) without Freight and Charges */
    public java.math.BigDecimal getLineNetAmt() 
    {
        return get_ValueAsBigDecimal("LineNetAmt");
        
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
    
    /** Set Quantity.
    @param Qty Quantity */
    public void setQty (java.math.BigDecimal Qty)
    {
        set_Value ("Qty", Qty);
        
    }
    
    /** Get Quantity.
    @return Quantity */
    public java.math.BigDecimal getQty() 
    {
        return get_ValueAsBigDecimal("Qty");
        
    }
    
    /** Set Quantity Invoiced.
    @param QtyInvoiced Invoiced Quantity */
    public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced)
    {
        set_Value ("QtyInvoiced", QtyInvoiced);
        
    }
    
    /** Get Quantity Invoiced.
    @return Invoiced Quantity */
    public java.math.BigDecimal getQtyInvoiced() 
    {
        return get_ValueAsBigDecimal("QtyInvoiced");
        
    }
    
    /** Set Realized Qty.
    @param RealizedQty Realized Qty */
    public void setRealizedQty (java.math.BigDecimal RealizedQty)
    {
        set_Value ("RealizedQty", RealizedQty);
        
    }
    
    /** Get Realized Qty.
    @return Realized Qty */
    public java.math.BigDecimal getRealizedQty() 
    {
        return get_ValueAsBigDecimal("RealizedQty");
        
    }
    
    /** Set Cost Distribution - Invoice.
    @param XX_CostDistributionInv_ID Cost distribution of invoice */
    public void setXX_CostDistributionInv_ID (int XX_CostDistributionInv_ID)
    {
        if (XX_CostDistributionInv_ID <= 0) set_Value ("XX_CostDistributionInv_ID", null);
        else
        set_Value ("XX_CostDistributionInv_ID", Integer.valueOf(XX_CostDistributionInv_ID));
        
    }
    
    /** Get Cost Distribution - Invoice.
    @return Cost distribution of invoice */
    public int getXX_CostDistributionInv_ID() 
    {
        return get_ValueAsInt("XX_CostDistributionInv_ID");
        
    }
    
    /** Set Cost Distributed Line.
    @param XX_CostDistributionInvLine_ID Cost Distributed Line */
    public void setXX_CostDistributionInvLine_ID (int XX_CostDistributionInvLine_ID)
    {
        if (XX_CostDistributionInvLine_ID < 1) throw new IllegalArgumentException ("XX_CostDistributionInvLine_ID is mandatory.");
        set_ValueNoCheck ("XX_CostDistributionInvLine_ID", Integer.valueOf(XX_CostDistributionInvLine_ID));
        
    }
    
    /** Get Cost Distributed Line.
    @return Cost Distributed Line */
    public int getXX_CostDistributionInvLine_ID() 
    {
        return get_ValueAsInt("XX_CostDistributionInvLine_ID");
        
    }
    
    
}
