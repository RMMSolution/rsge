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
/** Generated Model for XX_DisRealizationLineProd
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_DisRealizationLineProd extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_DisRealizationLineProd_ID id
    @param trx transaction
    */
    public X_XX_DisRealizationLineProd (Ctx ctx, int XX_DisRealizationLineProd_ID, Trx trx)
    {
        super (ctx, XX_DisRealizationLineProd_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_DisRealizationLineProd_ID == 0)
        {
            setC_UOM_ID (0);
            setM_Locator_ID (0);
            setM_Product_ID (0);
            setM_Warehouse_ID (0);
            setPrice (Env.ZERO);	// 0
            setQty (Env.ZERO);	// 0
            setXX_DisRealizationLine_ID (0);
            setXX_DisRealizationLineProd_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_DisRealizationLineProd (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27801904305666L;
    /** Last Updated Timestamp 2018-02-28 07:49:48.877 */
    public static final long updatedMS = 1519778988877L;
    /** AD_Table_ID=1000207 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_DisRealizationLineProd");
        
    }
    ;
    
    /** TableName=XX_DisRealizationLineProd */
    public static final String Table_Name="XX_DisRealizationLineProd";
    
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
    
    /** Set Attribute Set Instance.
    @param M_AttributeSetInstance_ID Product Attribute Set Instance */
    public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
    {
        if (M_AttributeSetInstance_ID <= 0) set_Value ("M_AttributeSetInstance_ID", null);
        else
        set_Value ("M_AttributeSetInstance_ID", Integer.valueOf(M_AttributeSetInstance_ID));
        
    }
    
    /** Get Attribute Set Instance.
    @return Product Attribute Set Instance */
    public int getM_AttributeSetInstance_ID() 
    {
        return get_ValueAsInt("M_AttributeSetInstance_ID");
        
    }
    
    /** Set Locator.
    @param M_Locator_ID Warehouse Locator */
    public void setM_Locator_ID (int M_Locator_ID)
    {
        if (M_Locator_ID < 1) throw new IllegalArgumentException ("M_Locator_ID is mandatory.");
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
        if (M_Warehouse_ID < 1) throw new IllegalArgumentException ("M_Warehouse_ID is mandatory.");
        set_Value ("M_Warehouse_ID", Integer.valueOf(M_Warehouse_ID));
        
    }
    
    /** Get Warehouse.
    @return Storage Warehouse and Service Point */
    public int getM_Warehouse_ID() 
    {
        return get_ValueAsInt("M_Warehouse_ID");
        
    }
    
    /** Set Price.
    @param Price Price */
    public void setPrice (java.math.BigDecimal Price)
    {
        if (Price == null) throw new IllegalArgumentException ("Price is mandatory.");
        set_Value ("Price", Price);
        
    }
    
    /** Get Price.
    @return Price */
    public java.math.BigDecimal getPrice() 
    {
        return get_ValueAsBigDecimal("Price");
        
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
    
    /** Set Line.
    @param XX_DisRealizationLine_ID Line of realization */
    public void setXX_DisRealizationLine_ID (int XX_DisRealizationLine_ID)
    {
        if (XX_DisRealizationLine_ID < 1) throw new IllegalArgumentException ("XX_DisRealizationLine_ID is mandatory.");
        set_Value ("XX_DisRealizationLine_ID", Integer.valueOf(XX_DisRealizationLine_ID));
        
    }
    
    /** Get Line.
    @return Line of realization */
    public int getXX_DisRealizationLine_ID() 
    {
        return get_ValueAsInt("XX_DisRealizationLine_ID");
        
    }
    
    /** Set Disbursement Realization Line Product.
    @param XX_DisRealizationLineProd_ID Disbursement realization line product */
    public void setXX_DisRealizationLineProd_ID (int XX_DisRealizationLineProd_ID)
    {
        if (XX_DisRealizationLineProd_ID < 1) throw new IllegalArgumentException ("XX_DisRealizationLineProd_ID is mandatory.");
        set_ValueNoCheck ("XX_DisRealizationLineProd_ID", Integer.valueOf(XX_DisRealizationLineProd_ID));
        
    }
    
    /** Get Disbursement Realization Line Product.
    @return Disbursement realization line product */
    public int getXX_DisRealizationLineProd_ID() 
    {
        return get_ValueAsInt("XX_DisRealizationLineProd_ID");
        
    }
    
    
}
