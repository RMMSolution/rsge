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
/** Generated Model for XX_InvRealizedQty
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_InvRealizedQty extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_InvRealizedQty_ID id
    @param trx transaction
    */
    public X_XX_InvRealizedQty (Ctx ctx, int XX_InvRealizedQty_ID, Trx trx)
    {
        super (ctx, XX_InvRealizedQty_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_InvRealizedQty_ID == 0)
        {
            setIsVoid (false);	// N
            setM_InventoryLine_ID (0);
            setXX_CostDistributionInvLine_ID (0);
            setXX_InvRealizedQty_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_InvRealizedQty (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27720921697470L;
    /** Last Updated Timestamp 2015-08-06 00:39:40.681 */
    public static final long updatedMS = 1438796380681L;
    /** AD_Table_ID=1008122 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_InvRealizedQty");
        
    }
    ;
    
    /** TableName=XX_InvRealizedQty */
    public static final String Table_Name="XX_InvRealizedQty";
    
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
    
    /** Set Void.
    @param IsVoid Void */
    public void setIsVoid (boolean IsVoid)
    {
        set_Value ("IsVoid", Boolean.valueOf(IsVoid));
        
    }
    
    /** Get Void.
    @return Void */
    public boolean isVoid() 
    {
        return get_ValueAsBoolean("IsVoid");
        
    }
    
    /** Set Phys Inventory Line.
    @param M_InventoryLine_ID Unique line in an Inventory document */
    public void setM_InventoryLine_ID (int M_InventoryLine_ID)
    {
        if (M_InventoryLine_ID < 1) throw new IllegalArgumentException ("M_InventoryLine_ID is mandatory.");
        set_Value ("M_InventoryLine_ID", Integer.valueOf(M_InventoryLine_ID));
        
    }
    
    /** Get Phys Inventory Line.
    @return Unique line in an Inventory document */
    public int getM_InventoryLine_ID() 
    {
        return get_ValueAsInt("M_InventoryLine_ID");
        
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
    
    /** Set Cost Distributed Line.
    @param XX_CostDistributionInvLine_ID Cost Distributed Line */
    public void setXX_CostDistributionInvLine_ID (int XX_CostDistributionInvLine_ID)
    {
        if (XX_CostDistributionInvLine_ID < 1) throw new IllegalArgumentException ("XX_CostDistributionInvLine_ID is mandatory.");
        set_Value ("XX_CostDistributionInvLine_ID", Integer.valueOf(XX_CostDistributionInvLine_ID));
        
    }
    
    /** Get Cost Distributed Line.
    @return Cost Distributed Line */
    public int getXX_CostDistributionInvLine_ID() 
    {
        return get_ValueAsInt("XX_CostDistributionInvLine_ID");
        
    }
    
    /** Set Realized Quantity.
    @param XX_InvRealizedQty_ID Realized Quantity */
    public void setXX_InvRealizedQty_ID (int XX_InvRealizedQty_ID)
    {
        if (XX_InvRealizedQty_ID < 1) throw new IllegalArgumentException ("XX_InvRealizedQty_ID is mandatory.");
        set_ValueNoCheck ("XX_InvRealizedQty_ID", Integer.valueOf(XX_InvRealizedQty_ID));
        
    }
    
    /** Get Realized Quantity.
    @return Realized Quantity */
    public int getXX_InvRealizedQty_ID() 
    {
        return get_ValueAsInt("XX_InvRealizedQty_ID");
        
    }
    
    
}
