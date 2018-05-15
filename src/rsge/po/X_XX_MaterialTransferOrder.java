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
/** Generated Model for XX_MaterialTransferOrder
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_MaterialTransferOrder extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_MaterialTransferOrder_ID id
    @param trx transaction
    */
    public X_XX_MaterialTransferOrder (Ctx ctx, int XX_MaterialTransferOrder_ID, Trx trx)
    {
        super (ctx, XX_MaterialTransferOrder_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_MaterialTransferOrder_ID == 0)
        {
            setIsClosed (false);	// N
            setM_RequisitionLine_ID (0);
            setXX_MaterialTransferOrder_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_MaterialTransferOrder (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27801736562645L;
    /** Last Updated Timestamp 2018-02-26 09:14:05.856 */
    public static final long updatedMS = 1519611245856L;
    /** AD_Table_ID=1000204 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_MaterialTransferOrder");
        
    }
    ;
    
    /** TableName=XX_MaterialTransferOrder */
    public static final String Table_Name="XX_MaterialTransferOrder";
    
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
    
    /** Set Date Expected.
    @param DateExpected Date on which the order is expected to be fulfilled */
    public void setDateExpected (Timestamp DateExpected)
    {
        set_Value ("DateExpected", DateExpected);
        
    }
    
    /** Get Date Expected.
    @return Date on which the order is expected to be fulfilled */
    public Timestamp getDateExpected() 
    {
        return (Timestamp)get_Value("DateExpected");
        
    }
    
    /** Set Difference.
    @param DifferenceQty Difference Quantity */
    public void setDifferenceQty (java.math.BigDecimal DifferenceQty)
    {
        set_Value ("DifferenceQty", DifferenceQty);
        
    }
    
    /** Get Difference.
    @return Difference Quantity */
    public java.math.BigDecimal getDifferenceQty() 
    {
        return get_ValueAsBigDecimal("DifferenceQty");
        
    }
    
    /** Set Closed Status.
    @param IsClosed The status is closed */
    public void setIsClosed (boolean IsClosed)
    {
        set_Value ("IsClosed", Boolean.valueOf(IsClosed));
        
    }
    
    /** Get Closed Status.
    @return The status is closed */
    public boolean isClosed() 
    {
        return get_ValueAsBoolean("IsClosed");
        
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
    
    /** Set Requisition Line.
    @param M_RequisitionLine_ID Material Requisition Line */
    public void setM_RequisitionLine_ID (int M_RequisitionLine_ID)
    {
        if (M_RequisitionLine_ID < 1) throw new IllegalArgumentException ("M_RequisitionLine_ID is mandatory.");
        set_Value ("M_RequisitionLine_ID", Integer.valueOf(M_RequisitionLine_ID));
        
    }
    
    /** Get Requisition Line.
    @return Material Requisition Line */
    public int getM_RequisitionLine_ID() 
    {
        return get_ValueAsInt("M_RequisitionLine_ID");
        
    }
    
    /** Set Quantity Delivered.
    @param QtyDelivered Quantity Delivered */
    public void setQtyDelivered (java.math.BigDecimal QtyDelivered)
    {
        set_Value ("QtyDelivered", QtyDelivered);
        
    }
    
    /** Get Quantity Delivered.
    @return Quantity Delivered */
    public java.math.BigDecimal getQtyDelivered() 
    {
        return get_ValueAsBigDecimal("QtyDelivered");
        
    }
    
    /** Set Expected Quantity.
    @param QtyExpected Quantity expected to be received into a locator */
    public void setQtyExpected (java.math.BigDecimal QtyExpected)
    {
        set_Value ("QtyExpected", QtyExpected);
        
    }
    
    /** Get Expected Quantity.
    @return Quantity expected to be received into a locator */
    public java.math.BigDecimal getQtyExpected() 
    {
        return get_ValueAsBigDecimal("QtyExpected");
        
    }
    
    /** Set Unrealized Qty.
    @param QtyUnrealized Unrealized quantity */
    public void setQtyUnrealized (java.math.BigDecimal QtyUnrealized)
    {
        set_Value ("QtyUnrealized", QtyUnrealized);
        
    }
    
    /** Get Unrealized Qty.
    @return Unrealized quantity */
    public java.math.BigDecimal getQtyUnrealized() 
    {
        return get_ValueAsBigDecimal("QtyUnrealized");
        
    }
    
    /** Set Material Transfer Order.
    @param XX_MaterialTransferOrder_ID Material Transfer Order */
    public void setXX_MaterialTransferOrder_ID (int XX_MaterialTransferOrder_ID)
    {
        if (XX_MaterialTransferOrder_ID < 1) throw new IllegalArgumentException ("XX_MaterialTransferOrder_ID is mandatory.");
        set_ValueNoCheck ("XX_MaterialTransferOrder_ID", Integer.valueOf(XX_MaterialTransferOrder_ID));
        
    }
    
    /** Get Material Transfer Order.
    @return Material Transfer Order */
    public int getXX_MaterialTransferOrder_ID() 
    {
        return get_ValueAsInt("XX_MaterialTransferOrder_ID");
        
    }
    
    
}
