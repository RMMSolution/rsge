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
/** Generated Model for XX_NonItemAllocationLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_NonItemAllocationLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_NonItemAllocationLine_ID id
    @param trx transaction
    */
    public X_XX_NonItemAllocationLine (Ctx ctx, int XX_NonItemAllocationLine_ID, Trx trx)
    {
        super (ctx, XX_NonItemAllocationLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_NonItemAllocationLine_ID == 0)
        {
            setM_Product_ID (0);
            setXX_NonItemAllocation_ID (0);
            setXX_NonItemAllocationLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_NonItemAllocationLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27801702143843L;
    /** Last Updated Timestamp 2018-02-25 23:40:27.054 */
    public static final long updatedMS = 1519576827054L;
    /** AD_Table_ID=1000203 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_NonItemAllocationLine");
        
    }
    ;
    
    /** TableName=XX_NonItemAllocationLine */
    public static final String Table_Name="XX_NonItemAllocationLine";
    
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
    
    /** Set Allocated Amount.
    @param AllocatedAmt Amount allocated to this document */
    public void setAllocatedAmt (java.math.BigDecimal AllocatedAmt)
    {
        set_Value ("AllocatedAmt", AllocatedAmt);
        
    }
    
    /** Get Allocated Amount.
    @return Amount allocated to this document */
    public java.math.BigDecimal getAllocatedAmt() 
    {
        return get_ValueAsBigDecimal("AllocatedAmt");
        
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
    
    /** Set Requisition Line.
    @param M_RequisitionLine_ID Material Requisition Line */
    public void setM_RequisitionLine_ID (int M_RequisitionLine_ID)
    {
        if (M_RequisitionLine_ID <= 0) set_Value ("M_RequisitionLine_ID", null);
        else
        set_Value ("M_RequisitionLine_ID", Integer.valueOf(M_RequisitionLine_ID));
        
    }
    
    /** Get Requisition Line.
    @return Material Requisition Line */
    public int getM_RequisitionLine_ID() 
    {
        return get_ValueAsInt("M_RequisitionLine_ID");
        
    }
    
    /** Set Organization Key.
    @param OrgValue Key of the Organization */
    public void setOrgValue (String OrgValue)
    {
        set_Value ("OrgValue", OrgValue);
        
    }
    
    /** Get Organization Key.
    @return Key of the Organization */
    public String getOrgValue() 
    {
        return (String)get_Value("OrgValue");
        
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
    
    /** Set Non Item Allocation.
    @param XX_NonItemAllocation_ID Non Item Allocation */
    public void setXX_NonItemAllocation_ID (int XX_NonItemAllocation_ID)
    {
        if (XX_NonItemAllocation_ID < 1) throw new IllegalArgumentException ("XX_NonItemAllocation_ID is mandatory.");
        set_Value ("XX_NonItemAllocation_ID", Integer.valueOf(XX_NonItemAllocation_ID));
        
    }
    
    /** Get Non Item Allocation.
    @return Non Item Allocation */
    public int getXX_NonItemAllocation_ID() 
    {
        return get_ValueAsInt("XX_NonItemAllocation_ID");
        
    }
    
    /** Set Allocation Line.
    @param XX_NonItemAllocationLine_ID Line of Non Item Allocation */
    public void setXX_NonItemAllocationLine_ID (int XX_NonItemAllocationLine_ID)
    {
        if (XX_NonItemAllocationLine_ID < 1) throw new IllegalArgumentException ("XX_NonItemAllocationLine_ID is mandatory.");
        set_ValueNoCheck ("XX_NonItemAllocationLine_ID", Integer.valueOf(XX_NonItemAllocationLine_ID));
        
    }
    
    /** Get Allocation Line.
    @return Line of Non Item Allocation */
    public int getXX_NonItemAllocationLine_ID() 
    {
        return get_ValueAsInt("XX_NonItemAllocationLine_ID");
        
    }
    
    
}
