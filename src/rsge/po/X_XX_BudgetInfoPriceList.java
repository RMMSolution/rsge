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
/** Generated Model for XX_BudgetInfoPriceList
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_BudgetInfoPriceList extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_BudgetInfoPriceList_ID id
    @param trx transaction
    */
    public X_XX_BudgetInfoPriceList (Ctx ctx, int XX_BudgetInfoPriceList_ID, Trx trx)
    {
        super (ctx, XX_BudgetInfoPriceList_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_BudgetInfoPriceList_ID == 0)
        {
            setC_Currency_ID (0);
            setIsPriceListRestriction (false);	// N
            setM_PriceList_Version_ID (0);
            setXX_BudgetInfo_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_BudgetInfoPriceList (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27802216856046L;
    /** Last Updated Timestamp 2018-03-03 22:38:59.257 */
    public static final long updatedMS = 1520091539257L;
    /** AD_Table_ID=1000208 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_BudgetInfoPriceList");
        
    }
    ;
    
    /** TableName=XX_BudgetInfoPriceList */
    public static final String Table_Name="XX_BudgetInfoPriceList";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Currency.
    @param C_Currency_ID The Currency for this record */
    public void setC_Currency_ID (int C_Currency_ID)
    {
        if (C_Currency_ID < 1) throw new IllegalArgumentException ("C_Currency_ID is mandatory.");
        set_ValueNoCheck ("C_Currency_ID", Integer.valueOf(C_Currency_ID));
        
    }
    
    /** Get Currency.
    @return The Currency for this record */
    public int getC_Currency_ID() 
    {
        return get_ValueAsInt("C_Currency_ID");
        
    }
    
    /** Set Price List Restriction.
    @param IsPriceListRestriction If it is checked, altering price in Requisition is not possible */
    public void setIsPriceListRestriction (boolean IsPriceListRestriction)
    {
        set_Value ("IsPriceListRestriction", Boolean.valueOf(IsPriceListRestriction));
        
    }
    
    /** Get Price List Restriction.
    @return If it is checked, altering price in Requisition is not possible */
    public boolean isPriceListRestriction() 
    {
        return get_ValueAsBoolean("IsPriceListRestriction");
        
    }
    
    /** Set Price List Version.
    @param M_PriceList_Version_ID Identifies a unique instance of a Price List */
    public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
    {
        if (M_PriceList_Version_ID < 1) throw new IllegalArgumentException ("M_PriceList_Version_ID is mandatory.");
        set_ValueNoCheck ("M_PriceList_Version_ID", Integer.valueOf(M_PriceList_Version_ID));
        
    }
    
    /** Get Price List Version.
    @return Identifies a unique instance of a Price List */
    public int getM_PriceList_Version_ID() 
    {
        return get_ValueAsInt("M_PriceList_Version_ID");
        
    }
    
    /** Set Budget Info.
    @param XX_BudgetInfo_ID Budget Info */
    public void setXX_BudgetInfo_ID (int XX_BudgetInfo_ID)
    {
        if (XX_BudgetInfo_ID < 1) throw new IllegalArgumentException ("XX_BudgetInfo_ID is mandatory.");
        set_ValueNoCheck ("XX_BudgetInfo_ID", Integer.valueOf(XX_BudgetInfo_ID));
        
    }
    
    /** Get Budget Info.
    @return Budget Info */
    public int getXX_BudgetInfo_ID() 
    {
        return get_ValueAsInt("XX_BudgetInfo_ID");
        
    }
    
    
}
