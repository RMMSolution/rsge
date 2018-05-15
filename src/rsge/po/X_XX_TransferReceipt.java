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
/** Generated Model for XX_TransferReceipt
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_TransferReceipt extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_TransferReceipt_ID id
    @param trx transaction
    */
    public X_XX_TransferReceipt (Ctx ctx, int XX_TransferReceipt_ID, Trx trx)
    {
        super (ctx, XX_TransferReceipt_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_TransferReceipt_ID == 0)
        {
            setProcessed (false);	// N
            setXX_TransferReceipt_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_TransferReceipt (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27807308119548L;
    /** Last Updated Timestamp 2018-05-01 20:53:22.759 */
    public static final long updatedMS = 1525182802759L;
    /** AD_Table_ID=1000411 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_TransferReceipt");
        
    }
    ;
    
    /** TableName=XX_TransferReceipt */
    public static final String Table_Name="XX_TransferReceipt";
    
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
    
    /** Set Transaction Date.
    @param DateTrx Transaction Date */
    public void setDateTrx (Timestamp DateTrx)
    {
        set_Value ("DateTrx", DateTrx);
        
    }
    
    /** Get Transaction Date.
    @return Transaction Date */
    public Timestamp getDateTrx() 
    {
        return (Timestamp)get_Value("DateTrx");
        
    }
    
    /** Set Document No.
    @param DocumentNo Document sequence number of the document */
    public void setDocumentNo (String DocumentNo)
    {
        set_Value ("DocumentNo", DocumentNo);
        
    }
    
    /** Get Document No.
    @return Document sequence number of the document */
    public String getDocumentNo() 
    {
        return (String)get_Value("DocumentNo");
        
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
    
    /** Set Posted.
    @param Posted Posting status */
    public void setPosted (boolean Posted)
    {
        set_Value ("Posted", Boolean.valueOf(Posted));
        
    }
    
    /** Get Posted.
    @return Posting status */
    public boolean isPosted() 
    {
        return get_ValueAsBoolean("Posted");
        
    }
    
    /** Set Processed.
    @param Processed The document has been processed */
    public void setProcessed (boolean Processed)
    {
        set_Value ("Processed", Boolean.valueOf(Processed));
        
    }
    
    /** Get Processed.
    @return The document has been processed */
    public boolean isProcessed() 
    {
        return get_ValueAsBoolean("Processed");
        
    }
    
    /** Set Process Now.
    @param Processing Process Now */
    public void setProcessing (boolean Processing)
    {
        set_Value ("Processing", Boolean.valueOf(Processing));
        
    }
    
    /** Get Process Now.
    @return Process Now */
    public boolean isProcessing() 
    {
        return get_ValueAsBoolean("Processing");
        
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
    
    /** Set Transfer Receipt.
    @param XX_TransferReceipt_ID Transfer receipt */
    public void setXX_TransferReceipt_ID (int XX_TransferReceipt_ID)
    {
        if (XX_TransferReceipt_ID < 1) throw new IllegalArgumentException ("XX_TransferReceipt_ID is mandatory.");
        set_ValueNoCheck ("XX_TransferReceipt_ID", Integer.valueOf(XX_TransferReceipt_ID));
        
    }
    
    /** Get Transfer Receipt.
    @return Transfer receipt */
    public int getXX_TransferReceipt_ID() 
    {
        return get_ValueAsInt("XX_TransferReceipt_ID");
        
    }
    
    
}
