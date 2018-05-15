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
/** Generated Model for XX_QuickReturnLineBatch
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_QuickReturnLineBatch extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_QuickReturnLineBatch_ID id
    @param trx transaction
    */
    public X_XX_QuickReturnLineBatch (Ctx ctx, int XX_QuickReturnLineBatch_ID, Trx trx)
    {
        super (ctx, XX_QuickReturnLineBatch_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_QuickReturnLineBatch_ID == 0)
        {
            setC_UOM_ID (0);
            setIsAttributeSetNotFound (false);	// N
            setM_Product_ID (0);	// @M_Product_ID@
            setQty (Env.ZERO);	// 0
            setQtyEntered (Env.ZERO);	// 0
            setXX_QuickReturnLine_ID (0);
            setXX_QuickReturnLineBatch_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_QuickReturnLineBatch (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27700073838789L;
    /** Last Updated Timestamp 2014-12-07 17:35:22.0 */
    public static final long updatedMS = 1417948522000L;
    /** AD_Table_ID=1000178 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_QuickReturnLineBatch");
        
    }
    ;
    
    /** TableName=XX_QuickReturnLineBatch */
    public static final String Table_Name="XX_QuickReturnLineBatch";
    
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
    
    /** Set Attribute Set not found.
    @param IsAttributeSetNotFound Attribute set not found in the system */
    public void setIsAttributeSetNotFound (boolean IsAttributeSetNotFound)
    {
        set_Value ("IsAttributeSetNotFound", Boolean.valueOf(IsAttributeSetNotFound));
        
    }
    
    /** Get Attribute Set not found.
    @return Attribute set not found in the system */
    public boolean isAttributeSetNotFound() 
    {
        return get_ValueAsBoolean("IsAttributeSetNotFound");
        
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
    
    /** Set Quantity.
    @param QtyEntered The Quantity Entered is based on the selected UoM */
    public void setQtyEntered (java.math.BigDecimal QtyEntered)
    {
        if (QtyEntered == null) throw new IllegalArgumentException ("QtyEntered is mandatory.");
        set_Value ("QtyEntered", QtyEntered);
        
    }
    
    /** Get Quantity.
    @return The Quantity Entered is based on the selected UoM */
    public java.math.BigDecimal getQtyEntered() 
    {
        return get_ValueAsBigDecimal("QtyEntered");
        
    }
    
    /** Set Return Line.
    @param XX_QuickReturnLine_ID Line of quick return */
    public void setXX_QuickReturnLine_ID (int XX_QuickReturnLine_ID)
    {
        if (XX_QuickReturnLine_ID < 1) throw new IllegalArgumentException ("XX_QuickReturnLine_ID is mandatory.");
        set_Value ("XX_QuickReturnLine_ID", Integer.valueOf(XX_QuickReturnLine_ID));
        
    }
    
    /** Get Return Line.
    @return Line of quick return */
    public int getXX_QuickReturnLine_ID() 
    {
        return get_ValueAsInt("XX_QuickReturnLine_ID");
        
    }
    
    /** Set Line Batch.
    @param XX_QuickReturnLineBatch_ID Batch of line */
    public void setXX_QuickReturnLineBatch_ID (int XX_QuickReturnLineBatch_ID)
    {
        if (XX_QuickReturnLineBatch_ID < 1) throw new IllegalArgumentException ("XX_QuickReturnLineBatch_ID is mandatory.");
        set_ValueNoCheck ("XX_QuickReturnLineBatch_ID", Integer.valueOf(XX_QuickReturnLineBatch_ID));
        
    }
    
    /** Get Line Batch.
    @return Batch of line */
    public int getXX_QuickReturnLineBatch_ID() 
    {
        return get_ValueAsInt("XX_QuickReturnLineBatch_ID");
        
    }
    
    
}
