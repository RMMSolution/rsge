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
/** Generated Model for T_StockCard
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_T_StockCard extends PO
{
    /** Standard Constructor
    @param ctx context
    @param T_StockCard_ID id
    @param trx transaction
    */
    public X_T_StockCard (Ctx ctx, int T_StockCard_ID, Trx trx)
    {
        super (ctx, T_StockCard_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (T_StockCard_ID == 0)
        {
            setM_Product_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_T_StockCard (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27785343791523L;
    /** Last Updated Timestamp 2017-08-20 15:41:14.734 */
    public static final long updatedMS = 1503218474734L;
    /** AD_Table_ID=1000104 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("T_StockCard");
        
    }
    ;
    
    /** TableName=T_StockCard */
    public static final String Table_Name="T_StockCard";
    
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
    
    /** Set Cost.
    @param Cost Cost information */
    public void setCost (java.math.BigDecimal Cost)
    {
        set_Value ("Cost", Cost);
        
    }
    
    /** Get Cost.
    @return Cost information */
    public java.math.BigDecimal getCost() 
    {
        return get_ValueAsBigDecimal("Cost");
        
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
    
    /** Set Product Category.
    @param M_Product_Category_ID Category of a Product */
    public void setM_Product_Category_ID (int M_Product_Category_ID)
    {
        if (M_Product_Category_ID <= 0) set_ValueNoCheck ("M_Product_Category_ID", null);
        else
        set_ValueNoCheck ("M_Product_Category_ID", Integer.valueOf(M_Product_Category_ID));
        
    }
    
    /** Get Product Category.
    @return Category of a Product */
    public int getM_Product_Category_ID() 
    {
        return get_ValueAsInt("M_Product_Category_ID");
        
    }
    
    /** Set Product.
    @param M_Product_ID Product, Service, Item */
    public void setM_Product_ID (int M_Product_ID)
    {
        if (M_Product_ID < 1) throw new IllegalArgumentException ("M_Product_ID is mandatory.");
        set_ValueNoCheck ("M_Product_ID", Integer.valueOf(M_Product_ID));
        
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
    
    /** Set Product Name.
    @param ProductName Name of the Product */
    public void setProductName (String ProductName)
    {
        set_Value ("ProductName", ProductName);
        
    }
    
    /** Get Product Name.
    @return Name of the Product */
    public String getProductName() 
    {
        return (String)get_Value("ProductName");
        
    }
    
    /** Set Product Key.
    @param ProductValue Key of the Product */
    public void setProductValue (String ProductValue)
    {
        set_Value ("ProductValue", ProductValue);
        
    }
    
    /** Get Product Key.
    @return Key of the Product */
    public String getProductValue() 
    {
        return (String)get_Value("ProductValue");
        
    }
    
    /** Set Balance.
    @param QtyBalance Quantity balance */
    public void setQtyBalance (java.math.BigDecimal QtyBalance)
    {
        set_Value ("QtyBalance", QtyBalance);
        
    }
    
    /** Get Balance.
    @return Quantity balance */
    public java.math.BigDecimal getQtyBalance() 
    {
        return get_ValueAsBigDecimal("QtyBalance");
        
    }
    
    /** Set In.
    @param QtyIn Quantity in */
    public void setQtyIn (java.math.BigDecimal QtyIn)
    {
        set_Value ("QtyIn", QtyIn);
        
    }
    
    /** Get In.
    @return Quantity in */
    public java.math.BigDecimal getQtyIn() 
    {
        return get_ValueAsBigDecimal("QtyIn");
        
    }
    
    /** Set Out.
    @param QtyOut Quantity out */
    public void setQtyOut (java.math.BigDecimal QtyOut)
    {
        set_Value ("QtyOut", QtyOut);
        
    }
    
    /** Get Out.
    @return Quantity out */
    public java.math.BigDecimal getQtyOut() 
    {
        return get_ValueAsBigDecimal("QtyOut");
        
    }
    
    /** Set UOM.
    @param UOM UOM */
    public void setUOM (String UOM)
    {
        set_Value ("UOM", UOM);
        
    }
    
    /** Get UOM.
    @return UOM */
    public String getUOM() 
    {
        return (String)get_Value("UOM");
        
    }
    
    
}
