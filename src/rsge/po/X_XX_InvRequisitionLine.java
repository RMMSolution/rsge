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
/** Generated Model for XX_InvRequisitionLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_InvRequisitionLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_InvRequisitionLine_ID id
    @param trx transaction
    */
    public X_XX_InvRequisitionLine (Ctx ctx, int XX_InvRequisitionLine_ID, Trx trx)
    {
        super (ctx, XX_InvRequisitionLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_InvRequisitionLine_ID == 0)
        {
            setC_Charge_ID (0);	// @C_Charge_ID@
            setIsOverBudget (false);	// N
            setLine (0);	// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM XX_InvRequisitionLine WHERE XX_InvRequisition_ID=@XX_InvRequisition_ID@
            setM_Product_ID (0);
            setProductCost (Env.ZERO);	// 0
            setQtyRequired (Env.ZERO);	// 1
            setXX_InvRequisitionLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_InvRequisitionLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664551454789L;
    /** Last Updated Timestamp 2013-10-22 14:15:38.0 */
    public static final long updatedMS = 1382426138000L;
    /** AD_Table_ID=1000158 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_InvRequisitionLine");
        
    }
    ;
    
    /** TableName=XX_InvRequisitionLine */
    public static final String Table_Name="XX_InvRequisitionLine";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Charge.
    @param C_Charge_ID Additional document charges */
    public void setC_Charge_ID (int C_Charge_ID)
    {
        if (C_Charge_ID < 1) throw new IllegalArgumentException ("C_Charge_ID is mandatory.");
        set_Value ("C_Charge_ID", Integer.valueOf(C_Charge_ID));
        
    }
    
    /** Get Charge.
    @return Additional document charges */
    public int getC_Charge_ID() 
    {
        return get_ValueAsInt("C_Charge_ID");
        
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
    
    /** Set Cost.
    @param Cost Cost information */
    public void setCost (java.math.BigDecimal Cost)
    {
        set_Value ("Cost", Cost);
        
    }
    
    /** Get Cost.
    @return Cost information */
    public java.math.BigDecimal getCost() 
    {
        return get_ValueAsBigDecimal("Cost");
        
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
    
    /** Set Cost (Est).
    @param EstCost Estimated cost */
    public void setEstCost (java.math.BigDecimal EstCost)
    {
        set_Value ("EstCost", EstCost);
        
    }
    
    /** Get Cost (Est).
    @return Estimated cost */
    public java.math.BigDecimal getEstCost() 
    {
        return get_ValueAsBigDecimal("EstCost");
        
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
    
    /** Set Product.
    @param M_Product_ID Product, Service, Item */
    public void setM_Product_ID (int M_Product_ID)
    {
        if (M_Product_ID < 1) throw new IllegalArgumentException ("M_Product_ID is mandatory.");
        set_Value ("M_Product_ID", Integer.valueOf(M_Product_ID));
        
    }
    
    /** Get Product.
    @return Product, Service, Item */
    public int getM_Product_ID() 
    {
        return get_ValueAsInt("M_Product_ID");
        
    }
    
    /** Set Product Cost.
    @param ProductCost Cost of product */
    public void setProductCost (java.math.BigDecimal ProductCost)
    {
        if (ProductCost == null) throw new IllegalArgumentException ("ProductCost is mandatory.");
        set_Value ("ProductCost", ProductCost);
        
    }
    
    /** Get Product Cost.
    @return Cost of product */
    public java.math.BigDecimal getProductCost() 
    {
        return get_ValueAsBigDecimal("ProductCost");
        
    }
    
    /** Set Qty Received.
    @param QtyReceived Qty Received */
    public void setQtyReceived (java.math.BigDecimal QtyReceived)
    {
        set_Value ("QtyReceived", QtyReceived);
        
    }
    
    /** Get Qty Received.
    @return Qty Received */
    public java.math.BigDecimal getQtyReceived() 
    {
        return get_ValueAsBigDecimal("QtyReceived");
        
    }
    
    /** Set Required Quantity.
    @param QtyRequired Quantity required for an activity */
    public void setQtyRequired (java.math.BigDecimal QtyRequired)
    {
        if (QtyRequired == null) throw new IllegalArgumentException ("QtyRequired is mandatory.");
        set_Value ("QtyRequired", QtyRequired);
        
    }
    
    /** Get Required Quantity.
    @return Quantity required for an activity */
    public java.math.BigDecimal getQtyRequired() 
    {
        return get_ValueAsBigDecimal("QtyRequired");
        
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
    
    /** Set Requisition.
    @param XX_InvRequisition_ID Inventory Requisition */
    public void setXX_InvRequisition_ID (int XX_InvRequisition_ID)
    {
        if (XX_InvRequisition_ID <= 0) set_Value ("XX_InvRequisition_ID", null);
        else
        set_Value ("XX_InvRequisition_ID", Integer.valueOf(XX_InvRequisition_ID));
        
    }
    
    /** Get Requisition.
    @return Inventory Requisition */
    public int getXX_InvRequisition_ID() 
    {
        return get_ValueAsInt("XX_InvRequisition_ID");
        
    }
    
    /** Set Line.
    @param XX_InvRequisitionLine_ID Line */
    public void setXX_InvRequisitionLine_ID (int XX_InvRequisitionLine_ID)
    {
        if (XX_InvRequisitionLine_ID < 1) throw new IllegalArgumentException ("XX_InvRequisitionLine_ID is mandatory.");
        set_ValueNoCheck ("XX_InvRequisitionLine_ID", Integer.valueOf(XX_InvRequisitionLine_ID));
        
    }
    
    /** Get Line.
    @return Line */
    public int getXX_InvRequisitionLine_ID() 
    {
        return get_ValueAsInt("XX_InvRequisitionLine_ID");
        
    }
    
    
}
