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
/** Generated Model for XX_InternalUseRequestLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_InternalUseRequestLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_InternalUseRequestLine_ID id
    @param trx transaction
    */
    public X_XX_InternalUseRequestLine (Ctx ctx, int XX_InternalUseRequestLine_ID, Trx trx)
    {
        super (ctx, XX_InternalUseRequestLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_InternalUseRequestLine_ID == 0)
        {
            setC_Charge_ID (0);	// @C_Charge_ID@
            setIsMultiLocator (false);	// N
            setLine (0);	// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM XX_InternalUseRequestLine WHERE XX_InternalUseRequest_ID=@XX_InternalUseRequest_ID@
            setM_Product_ID (0);
            setQtyInternalUse (Env.ZERO);	// 0
            setQtyRequired (Env.ZERO);	// 0
            setXX_InternalUseRequestLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_InternalUseRequestLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27695386754789L;
    /** Last Updated Timestamp 2014-10-14 11:37:18.0 */
    public static final long updatedMS = 1413261438000L;
    /** AD_Table_ID=1000150 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_InternalUseRequestLine");
        
    }
    ;
    
    /** TableName=XX_InternalUseRequestLine */
    public static final String Table_Name="XX_InternalUseRequestLine";
    
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
    
    /** Set Multi Locator.
    @param IsMultiLocator Product source is from multi locator */
    public void setIsMultiLocator (boolean IsMultiLocator)
    {
        set_Value ("IsMultiLocator", Boolean.valueOf(IsMultiLocator));
        
    }
    
    /** Get Multi Locator.
    @return Product source is from multi locator */
    public boolean isMultiLocator() 
    {
        return get_ValueAsBoolean("IsMultiLocator");
        
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
    
    /** Set Internal Use Qty.
    @param QtyInternalUse Internal Use Quantity removed from Inventory */
    public void setQtyInternalUse (java.math.BigDecimal QtyInternalUse)
    {
        if (QtyInternalUse == null) throw new IllegalArgumentException ("QtyInternalUse is mandatory.");
        set_Value ("QtyInternalUse", QtyInternalUse);
        
    }
    
    /** Get Internal Use Qty.
    @return Internal Use Quantity removed from Inventory */
    public java.math.BigDecimal getQtyInternalUse() 
    {
        return get_ValueAsBigDecimal("QtyInternalUse");
        
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
    
    /** Set Internal Use Request.
    @param XX_InternalUseRequest_ID Internal use request */
    public void setXX_InternalUseRequest_ID (int XX_InternalUseRequest_ID)
    {
        if (XX_InternalUseRequest_ID <= 0) set_Value ("XX_InternalUseRequest_ID", null);
        else
        set_Value ("XX_InternalUseRequest_ID", Integer.valueOf(XX_InternalUseRequest_ID));
        
    }
    
    /** Get Internal Use Request.
    @return Internal use request */
    public int getXX_InternalUseRequest_ID() 
    {
        return get_ValueAsInt("XX_InternalUseRequest_ID");
        
    }
    
    /** Set Request Line.
    @param XX_InternalUseRequestLine_ID Request Line */
    public void setXX_InternalUseRequestLine_ID (int XX_InternalUseRequestLine_ID)
    {
        if (XX_InternalUseRequestLine_ID < 1) throw new IllegalArgumentException ("XX_InternalUseRequestLine_ID is mandatory.");
        set_ValueNoCheck ("XX_InternalUseRequestLine_ID", Integer.valueOf(XX_InternalUseRequestLine_ID));
        
    }
    
    /** Get Request Line.
    @return Request Line */
    public int getXX_InternalUseRequestLine_ID() 
    {
        return get_ValueAsInt("XX_InternalUseRequestLine_ID");
        
    }
    
    
}
