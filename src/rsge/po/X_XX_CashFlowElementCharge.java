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
/** Generated Model for XX_CashFlowElementCharge
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_CashFlowElementCharge extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_CashFlowElementCharge_ID id
    @param trx transaction
    */
    public X_XX_CashFlowElementCharge (Ctx ctx, int XX_CashFlowElementCharge_ID, Trx trx)
    {
        super (ctx, XX_CashFlowElementCharge_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_CashFlowElementCharge_ID == 0)
        {
            setC_Charge_ID (0);
            setXX_CashFlowElement_ID (0);
            setXX_CashFlowElementCharge_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_CashFlowElementCharge (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27757663232961L;
    /** Last Updated Timestamp 2016-10-04 06:38:36.172 */
    public static final long updatedMS = 1475537916172L;
    /** AD_Table_ID=1000124 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_CashFlowElementCharge");
        
    }
    ;
    
    /** TableName=XX_CashFlowElementCharge */
    public static final String Table_Name="XX_CashFlowElementCharge";
    
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
    
    /** Set Cash Flow Element.
    @param XX_CashFlowElement_ID Cash Flow Element */
    public void setXX_CashFlowElement_ID (int XX_CashFlowElement_ID)
    {
        if (XX_CashFlowElement_ID < 1) throw new IllegalArgumentException ("XX_CashFlowElement_ID is mandatory.");
        set_Value ("XX_CashFlowElement_ID", Integer.valueOf(XX_CashFlowElement_ID));
        
    }
    
    /** Get Cash Flow Element.
    @return Cash Flow Element */
    public int getXX_CashFlowElement_ID() 
    {
        return get_ValueAsInt("XX_CashFlowElement_ID");
        
    }
    
    /** Set Charge.
    @param XX_CashFlowElementCharge_ID Charge of Cash Flow Element */
    public void setXX_CashFlowElementCharge_ID (int XX_CashFlowElementCharge_ID)
    {
        if (XX_CashFlowElementCharge_ID < 1) throw new IllegalArgumentException ("XX_CashFlowElementCharge_ID is mandatory.");
        set_ValueNoCheck ("XX_CashFlowElementCharge_ID", Integer.valueOf(XX_CashFlowElementCharge_ID));
        
    }
    
    /** Get Charge.
    @return Charge of Cash Flow Element */
    public int getXX_CashFlowElementCharge_ID() 
    {
        return get_ValueAsInt("XX_CashFlowElementCharge_ID");
        
    }
    
    
}
