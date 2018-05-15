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
/** Generated Model for XX_MaterialTransferOrderLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_MaterialTransferOrderLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_MaterialTransferOrderLine_ID id
    @param trx transaction
    */
    public X_XX_MaterialTransferOrderLine (Ctx ctx, int XX_MaterialTransferOrderLine_ID, Trx trx)
    {
        super (ctx, XX_MaterialTransferOrderLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_MaterialTransferOrderLine_ID == 0)
        {
            setXX_MaterialTransferOrder_ID (0);
            setXX_MaterialTransferOrderLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_MaterialTransferOrderLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27801736844105L;
    /** Last Updated Timestamp 2018-02-26 09:18:47.316 */
    public static final long updatedMS = 1519611527316L;
    /** AD_Table_ID=1000205 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_MaterialTransferOrderLine");
        
    }
    ;
    
    /** TableName=XX_MaterialTransferOrderLine */
    public static final String Table_Name="XX_MaterialTransferOrderLine";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Shipment/Receipt Line.
    @param M_InOutLine_ID Line on Shipment or Receipt document */
    public void setM_InOutLine_ID (int M_InOutLine_ID)
    {
        if (M_InOutLine_ID <= 0) set_Value ("M_InOutLine_ID", null);
        else
        set_Value ("M_InOutLine_ID", Integer.valueOf(M_InOutLine_ID));
        
    }
    
    /** Get Shipment/Receipt Line.
    @return Line on Shipment or Receipt document */
    public int getM_InOutLine_ID() 
    {
        return get_ValueAsInt("M_InOutLine_ID");
        
    }
    
    /** Set Phys Inventory Line.
    @param M_InventoryLine_ID Unique line in an Inventory document */
    public void setM_InventoryLine_ID (int M_InventoryLine_ID)
    {
        if (M_InventoryLine_ID <= 0) set_Value ("M_InventoryLine_ID", null);
        else
        set_Value ("M_InventoryLine_ID", Integer.valueOf(M_InventoryLine_ID));
        
    }
    
    /** Get Phys Inventory Line.
    @return Unique line in an Inventory document */
    public int getM_InventoryLine_ID() 
    {
        return get_ValueAsInt("M_InventoryLine_ID");
        
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
    
    /** Set Movement Date.
    @param MovementDate Date a product was moved in or out of inventory */
    public void setMovementDate (Timestamp MovementDate)
    {
        set_Value ("MovementDate", MovementDate);
        
    }
    
    /** Get Movement Date.
    @return Date a product was moved in or out of inventory */
    public Timestamp getMovementDate() 
    {
        return (Timestamp)get_Value("MovementDate");
        
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
    
    /** Set Material Transfer Order.
    @param XX_MaterialTransferOrder_ID Material Transfer Order */
    public void setXX_MaterialTransferOrder_ID (int XX_MaterialTransferOrder_ID)
    {
        if (XX_MaterialTransferOrder_ID < 1) throw new IllegalArgumentException ("XX_MaterialTransferOrder_ID is mandatory.");
        set_Value ("XX_MaterialTransferOrder_ID", Integer.valueOf(XX_MaterialTransferOrder_ID));
        
    }
    
    /** Get Material Transfer Order.
    @return Material Transfer Order */
    public int getXX_MaterialTransferOrder_ID() 
    {
        return get_ValueAsInt("XX_MaterialTransferOrder_ID");
        
    }
    
    /** Set Transfer Order Line.
    @param XX_MaterialTransferOrderLine_ID Transfer Order Line */
    public void setXX_MaterialTransferOrderLine_ID (int XX_MaterialTransferOrderLine_ID)
    {
        if (XX_MaterialTransferOrderLine_ID < 1) throw new IllegalArgumentException ("XX_MaterialTransferOrderLine_ID is mandatory.");
        set_ValueNoCheck ("XX_MaterialTransferOrderLine_ID", Integer.valueOf(XX_MaterialTransferOrderLine_ID));
        
    }
    
    /** Get Transfer Order Line.
    @return Transfer Order Line */
    public int getXX_MaterialTransferOrderLine_ID() 
    {
        return get_ValueAsInt("XX_MaterialTransferOrderLine_ID");
        
    }
    
    
}
