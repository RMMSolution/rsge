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
/** Generated Model for XX_RequestLineLocator
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_RequestLineLocator extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_RequestLineLocator_ID id
    @param trx transaction
    */
    public X_XX_RequestLineLocator (Ctx ctx, int XX_RequestLineLocator_ID, Trx trx)
    {
        super (ctx, XX_RequestLineLocator_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_RequestLineLocator_ID == 0)
        {
            setM_Locator_ID (0);
            setQtyInternalUse (Env.ZERO);	// 0
            setQtyRequired (Env.ZERO);	// @QtyRequired@
            setXX_RequestLineLocator_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_RequestLineLocator (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27695387245789L;
    /** Last Updated Timestamp 2014-10-14 11:45:29.0 */
    public static final long updatedMS = 1413261929000L;
    /** AD_Table_ID=1000179 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_RequestLineLocator");
        
    }
    ;
    
    /** TableName=XX_RequestLineLocator */
    public static final String Table_Name="XX_RequestLineLocator";
    
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
    
    /** Set Request Line.
    @param XX_InternalUseRequestLine_ID Request Line */
    public void setXX_InternalUseRequestLine_ID (int XX_InternalUseRequestLine_ID)
    {
        if (XX_InternalUseRequestLine_ID <= 0) set_Value ("XX_InternalUseRequestLine_ID", null);
        else
        set_Value ("XX_InternalUseRequestLine_ID", Integer.valueOf(XX_InternalUseRequestLine_ID));
        
    }
    
    /** Get Request Line.
    @return Request Line */
    public int getXX_InternalUseRequestLine_ID() 
    {
        return get_ValueAsInt("XX_InternalUseRequestLine_ID");
        
    }
    
    /** Set Line Locator.
    @param XX_RequestLineLocator_ID Line Locator */
    public void setXX_RequestLineLocator_ID (int XX_RequestLineLocator_ID)
    {
        if (XX_RequestLineLocator_ID < 1) throw new IllegalArgumentException ("XX_RequestLineLocator_ID is mandatory.");
        set_ValueNoCheck ("XX_RequestLineLocator_ID", Integer.valueOf(XX_RequestLineLocator_ID));
        
    }
    
    /** Get Line Locator.
    @return Line Locator */
    public int getXX_RequestLineLocator_ID() 
    {
        return get_ValueAsInt("XX_RequestLineLocator_ID");
        
    }
    
    
}
