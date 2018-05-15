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
/** Generated Model for XX_ReturnBatchRelatedDoc
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_ReturnBatchRelatedDoc extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_ReturnBatchRelatedDoc_ID id
    @param trx transaction
    */
    public X_XX_ReturnBatchRelatedDoc (Ctx ctx, int XX_ReturnBatchRelatedDoc_ID, Trx trx)
    {
        super (ctx, XX_ReturnBatchRelatedDoc_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_ReturnBatchRelatedDoc_ID == 0)
        {
            setC_UOM_ID (0);	// @C_UOM_ID@
            setQty (Env.ZERO);	// 0
            setQtyEntered (Env.ZERO);	// 0
            setXX_QuickReturnLineBatch_ID (0);
            setXX_ReturnBatchRelatedDoc_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_ReturnBatchRelatedDoc (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27687414291789L;
    /** Last Updated Timestamp 2014-07-14 05:02:55.0 */
    public static final long updatedMS = 1405288975000L;
    /** AD_Table_ID=1000182 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_ReturnBatchRelatedDoc");
        
    }
    ;
    
    /** TableName=XX_ReturnBatchRelatedDoc */
    public static final String Table_Name="XX_ReturnBatchRelatedDoc";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Order Line.
    @param C_OrderLine_ID Order Line */
    public void setC_OrderLine_ID (int C_OrderLine_ID)
    {
        if (C_OrderLine_ID <= 0) set_Value ("C_OrderLine_ID", null);
        else
        set_Value ("C_OrderLine_ID", Integer.valueOf(C_OrderLine_ID));
        
    }
    
    /** Get Order Line.
    @return Order Line */
    public int getC_OrderLine_ID() 
    {
        return get_ValueAsInt("C_OrderLine_ID");
        
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
    
    /** Set Orig Shipment Line.
    @param Orig_InOutLine_ID Original shipment line of the RMA */
    public void setOrig_InOutLine_ID (int Orig_InOutLine_ID)
    {
        if (Orig_InOutLine_ID <= 0) set_Value ("Orig_InOutLine_ID", null);
        else
        set_Value ("Orig_InOutLine_ID", Integer.valueOf(Orig_InOutLine_ID));
        
    }
    
    /** Get Orig Shipment Line.
    @return Original shipment line of the RMA */
    public int getOrig_InOutLine_ID() 
    {
        return get_ValueAsInt("Orig_InOutLine_ID");
        
    }
    
    /** Set Orig Sales Order Line.
    @param Orig_OrderLine_ID Original Sales Order Line for Return Material Authorization */
    public void setOrig_OrderLine_ID (int Orig_OrderLine_ID)
    {
        if (Orig_OrderLine_ID <= 0) set_Value ("Orig_OrderLine_ID", null);
        else
        set_Value ("Orig_OrderLine_ID", Integer.valueOf(Orig_OrderLine_ID));
        
    }
    
    /** Get Orig Sales Order Line.
    @return Original Sales Order Line for Return Material Authorization */
    public int getOrig_OrderLine_ID() 
    {
        return get_ValueAsInt("Orig_OrderLine_ID");
        
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
    
    /** Set Line Batch.
    @param XX_QuickReturnLineBatch_ID Batch of line */
    public void setXX_QuickReturnLineBatch_ID (int XX_QuickReturnLineBatch_ID)
    {
        if (XX_QuickReturnLineBatch_ID < 1) throw new IllegalArgumentException ("XX_QuickReturnLineBatch_ID is mandatory.");
        set_Value ("XX_QuickReturnLineBatch_ID", Integer.valueOf(XX_QuickReturnLineBatch_ID));
        
    }
    
    /** Get Line Batch.
    @return Batch of line */
    public int getXX_QuickReturnLineBatch_ID() 
    {
        return get_ValueAsInt("XX_QuickReturnLineBatch_ID");
        
    }
    
    /** Set Related Document.
    @param XX_ReturnBatchRelatedDoc_ID Document related to quick return */
    public void setXX_ReturnBatchRelatedDoc_ID (int XX_ReturnBatchRelatedDoc_ID)
    {
        if (XX_ReturnBatchRelatedDoc_ID < 1) throw new IllegalArgumentException ("XX_ReturnBatchRelatedDoc_ID is mandatory.");
        set_ValueNoCheck ("XX_ReturnBatchRelatedDoc_ID", Integer.valueOf(XX_ReturnBatchRelatedDoc_ID));
        
    }
    
    /** Get Related Document.
    @return Document related to quick return */
    public int getXX_ReturnBatchRelatedDoc_ID() 
    {
        return get_ValueAsInt("XX_ReturnBatchRelatedDoc_ID");
        
    }
    
    
}
