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
/** Generated Model for XX_MaterialRequestLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_MaterialRequestLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_MaterialRequestLine_ID id
    @param trx transaction
    */
    public X_XX_MaterialRequestLine (Ctx ctx, int XX_MaterialRequestLine_ID, Trx trx)
    {
        super (ctx, XX_MaterialRequestLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_MaterialRequestLine_ID == 0)
        {
            setC_UOM_ID (0);
            setLine (0);	// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM XX_MaterialRequestLine WHERE XX_MaterialRequest_ID = @XX_MaterialRequest_ID@
            setM_Product_ID (0);
            setQty (Env.ZERO);	// 0
            setXX_MaterialRequest_ID (0);
            setXX_MaterialRequestLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_MaterialRequestLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27801738182069L;
    /** Last Updated Timestamp 2018-02-26 09:41:05.28 */
    public static final long updatedMS = 1519612865280L;
    /** AD_Table_ID=1000161 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_MaterialRequestLine");
        
    }
    ;
    
    /** TableName=XX_MaterialRequestLine */
    public static final String Table_Name="XX_MaterialRequestLine";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set UOM.
    @param C_UOM_ID Unit of Measure */
    public void setC_UOM_ID (int C_UOM_ID)
    {
        if (C_UOM_ID < 1) throw new IllegalArgumentException ("C_UOM_ID is mandatory.");
        set_Value ("C_UOM_ID", Integer.valueOf(C_UOM_ID));
        
    }
    
    /** Get UOM.
    @return Unit of Measure */
    public int getC_UOM_ID() 
    {
        return get_ValueAsInt("C_UOM_ID");
        
    }
    
    /** Set Confirmed Quantity.
    @param ConfirmedQty Confirmation of a received quantity */
    public void setConfirmedQty (java.math.BigDecimal ConfirmedQty)
    {
        set_Value ("ConfirmedQty", ConfirmedQty);
        
    }
    
    /** Get Confirmed Quantity.
    @return Confirmation of a received quantity */
    public java.math.BigDecimal getConfirmedQty() 
    {
        return get_ValueAsBigDecimal("ConfirmedQty");
        
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
    
    /** Set Phys.Inventory.
    @param M_Inventory_ID Parameters for a Physical Inventory */
    public void setM_Inventory_ID (int M_Inventory_ID)
    {
        if (M_Inventory_ID <= 0) set_Value ("M_Inventory_ID", null);
        else
        set_Value ("M_Inventory_ID", Integer.valueOf(M_Inventory_ID));
        
    }
    
    /** Get Phys.Inventory.
    @return Parameters for a Physical Inventory */
    public int getM_Inventory_ID() 
    {
        return get_ValueAsInt("M_Inventory_ID");
        
    }
    
    /** Set Move Line.
    @param M_MovementLine_ID Inventory Move document Line */
    public void setM_MovementLine_ID (int M_MovementLine_ID)
    {
        if (M_MovementLine_ID <= 0) set_Value ("M_MovementLine_ID", null);
        else
        set_Value ("M_MovementLine_ID", Integer.valueOf(M_MovementLine_ID));
        
    }
    
    /** Get Move Line.
    @return Inventory Move document Line */
    public int getM_MovementLine_ID() 
    {
        return get_ValueAsInt("M_MovementLine_ID");
        
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
    
    /** Set Quantity.
    @param Qty Quantity */
    public void setQty (java.math.BigDecimal Qty)
    {
        if (Qty == null) throw new IllegalArgumentException ("Qty is mandatory.");
        set_Value ("Qty", Qty);
        
    }
    
    /** Get Quantity.
    @return Quantity */
    public java.math.BigDecimal getQty() 
    {
        return get_ValueAsBigDecimal("Qty");
        
    }
    
    /** Set Material Request.
    @param XX_MaterialRequest_ID Material Request */
    public void setXX_MaterialRequest_ID (int XX_MaterialRequest_ID)
    {
        if (XX_MaterialRequest_ID < 1) throw new IllegalArgumentException ("XX_MaterialRequest_ID is mandatory.");
        set_Value ("XX_MaterialRequest_ID", Integer.valueOf(XX_MaterialRequest_ID));
        
    }
    
    /** Get Material Request.
    @return Material Request */
    public int getXX_MaterialRequest_ID() 
    {
        return get_ValueAsInt("XX_MaterialRequest_ID");
        
    }
    
    /** Set Request Line.
    @param XX_MaterialRequestLine_ID Request Line */
    public void setXX_MaterialRequestLine_ID (int XX_MaterialRequestLine_ID)
    {
        if (XX_MaterialRequestLine_ID < 1) throw new IllegalArgumentException ("XX_MaterialRequestLine_ID is mandatory.");
        set_ValueNoCheck ("XX_MaterialRequestLine_ID", Integer.valueOf(XX_MaterialRequestLine_ID));
        
    }
    
    /** Get Request Line.
    @return Request Line */
    public int getXX_MaterialRequestLine_ID() 
    {
        return get_ValueAsInt("XX_MaterialRequestLine_ID");
        
    }
    
    /** Set Requisition.
    @param XX_PurchaseRequisition_ID User Requisition */
    public void setXX_PurchaseRequisition_ID (int XX_PurchaseRequisition_ID)
    {
        if (XX_PurchaseRequisition_ID <= 0) set_Value ("XX_PurchaseRequisition_ID", null);
        else
        set_Value ("XX_PurchaseRequisition_ID", Integer.valueOf(XX_PurchaseRequisition_ID));
        
    }
    
    /** Get Requisition.
    @return User Requisition */
    public int getXX_PurchaseRequisition_ID() 
    {
        return get_ValueAsInt("XX_PurchaseRequisition_ID");
        
    }
    
    
}
