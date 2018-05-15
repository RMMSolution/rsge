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
/** Generated Model for T_RequisitionLineOption
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_T_RequisitionLineOption extends PO
{
    /** Standard Constructor
    @param ctx context
    @param T_RequisitionLineOption_ID id
    @param trx transaction
    */
    public X_T_RequisitionLineOption (Ctx ctx, int T_RequisitionLineOption_ID, Trx trx)
    {
        super (ctx, T_RequisitionLineOption_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (T_RequisitionLineOption_ID == 0)
        {
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_T_RequisitionLineOption (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27802272382364L;
    /** Last Updated Timestamp 2018-03-04 14:04:25.575 */
    public static final long updatedMS = 1520147065575L;
    /** AD_Table_ID=1000209 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("T_RequisitionLineOption");
        
    }
    ;
    
    /** TableName=T_RequisitionLineOption */
    public static final String Table_Name="T_RequisitionLineOption";
    
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
        if (C_Charge_ID <= 0) set_Value ("C_Charge_ID", null);
        else
        set_Value ("C_Charge_ID", Integer.valueOf(C_Charge_ID));
        
    }
    
    /** Get Charge.
    @return Additional document charges */
    public int getC_Charge_ID() 
    {
        return get_ValueAsInt("C_Charge_ID");
        
    }
    
    /** Set Product Category.
    @param M_Product_Category_ID Category of a Product */
    public void setM_Product_Category_ID (int M_Product_Category_ID)
    {
        if (M_Product_Category_ID <= 0) set_Value ("M_Product_Category_ID", null);
        else
        set_Value ("M_Product_Category_ID", Integer.valueOf(M_Product_Category_ID));
        
    }
    
    /** Get Product Category.
    @return Category of a Product */
    public int getM_Product_Category_ID() 
    {
        return get_ValueAsInt("M_Product_Category_ID");
        
    }
    
    /** Set Requisition Line.
    @param XX_PurchaseRequisitionLine_ID Requisition Line */
    public void setXX_PurchaseRequisitionLine_ID (int XX_PurchaseRequisitionLine_ID)
    {
        if (XX_PurchaseRequisitionLine_ID <= 0) set_Value ("XX_PurchaseRequisitionLine_ID", null);
        else
        set_Value ("XX_PurchaseRequisitionLine_ID", Integer.valueOf(XX_PurchaseRequisitionLine_ID));
        
    }
    
    /** Get Requisition Line.
    @return Requisition Line */
    public int getXX_PurchaseRequisitionLine_ID() 
    {
        return get_ValueAsInt("XX_PurchaseRequisitionLine_ID");
        
    }
    
    
}
