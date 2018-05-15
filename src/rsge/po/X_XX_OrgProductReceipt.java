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
/** Generated Model for XX_OrgProductReceipt
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_OrgProductReceipt extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_OrgProductReceipt_ID id
    @param trx transaction
    */
    public X_XX_OrgProductReceipt (Ctx ctx, int XX_OrgProductReceipt_ID, Trx trx)
    {
        super (ctx, XX_OrgProductReceipt_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_OrgProductReceipt_ID == 0)
        {
            setM_Product_ID (0);
            setXX_OrgProductReceipt_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_OrgProductReceipt (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27782988881858L;
    /** Last Updated Timestamp 2017-07-24 09:32:45.069 */
    public static final long updatedMS = 1500863565069L;
    /** AD_Table_ID=1006474 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_OrgProductReceipt");
        
    }
    ;
    
    /** TableName=XX_OrgProductReceipt */
    public static final String Table_Name="XX_OrgProductReceipt";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
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
    
    /** Set Locator.
    @param M_Locator_ID Warehouse Locator */
    public void setM_Locator_ID (int M_Locator_ID)
    {
        if (M_Locator_ID <= 0) set_Value ("M_Locator_ID", null);
        else
        set_Value ("M_Locator_ID", Integer.valueOf(M_Locator_ID));
        
    }
    
    /** Get Locator.
    @return Warehouse Locator */
    public int getM_Locator_ID() 
    {
        return get_ValueAsInt("M_Locator_ID");
        
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
    
    /** Set Warehouse.
    @param M_Warehouse_ID Storage Warehouse and Service Point */
    public void setM_Warehouse_ID (int M_Warehouse_ID)
    {
        if (M_Warehouse_ID <= 0) set_Value ("M_Warehouse_ID", null);
        else
        set_Value ("M_Warehouse_ID", Integer.valueOf(M_Warehouse_ID));
        
    }
    
    /** Get Warehouse.
    @return Storage Warehouse and Service Point */
    public int getM_Warehouse_ID() 
    {
        return get_ValueAsInt("M_Warehouse_ID");
        
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
    
    /** Set Quantity Remaining.
    @param QtyRemaining Quantity Remaining */
    public void setQtyRemaining (java.math.BigDecimal QtyRemaining)
    {
        set_Value ("QtyRemaining", QtyRemaining);
        
    }
    
    /** Get Quantity Remaining.
    @return Quantity Remaining */
    public java.math.BigDecimal getQtyRemaining() 
    {
        return get_ValueAsBigDecimal("QtyRemaining");
        
    }
    
    /** Set XX_OrgProductReceipt_ID.
    @param XX_OrgProductReceipt_ID XX_OrgProductReceipt_ID */
    public void setXX_OrgProductReceipt_ID (int XX_OrgProductReceipt_ID)
    {
        if (XX_OrgProductReceipt_ID < 1) throw new IllegalArgumentException ("XX_OrgProductReceipt_ID is mandatory.");
        set_ValueNoCheck ("XX_OrgProductReceipt_ID", Integer.valueOf(XX_OrgProductReceipt_ID));
        
    }
    
    /** Get XX_OrgProductReceipt_ID.
    @return XX_OrgProductReceipt_ID */
    public int getXX_OrgProductReceipt_ID() 
    {
        return get_ValueAsInt("XX_OrgProductReceipt_ID");
        
    }
    
    
}
