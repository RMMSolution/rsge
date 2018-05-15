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
/** Generated Model for XX_InvoiceServiceCharge
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_InvoiceServiceCharge extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_InvoiceServiceCharge_ID id
    @param trx transaction
    */
    public X_XX_InvoiceServiceCharge (Ctx ctx, int XX_InvoiceServiceCharge_ID, Trx trx)
    {
        super (ctx, XX_InvoiceServiceCharge_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_InvoiceServiceCharge_ID == 0)
        {
            setC_Invoice_ID (0);
            setXX_InvoiceServiceCharge_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_InvoiceServiceCharge (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27786184762934L;
    /** Last Updated Timestamp 2017-08-30 09:17:26.145 */
    public static final long updatedMS = 1504059446145L;
    /** AD_Table_ID=1000156 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_InvoiceServiceCharge");
        
    }
    ;
    
    /** TableName=XX_InvoiceServiceCharge */
    public static final String Table_Name="XX_InvoiceServiceCharge";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Amount.
    @param Amount Amount in a defined currency */
    public void setAmount (java.math.BigDecimal Amount)
    {
        set_Value ("Amount", Amount);
        
    }
    
    /** Get Amount.
    @return Amount in a defined currency */
    public java.math.BigDecimal getAmount() 
    {
        return get_ValueAsBigDecimal("Amount");
        
    }
    
    /** Set Invoice.
    @param C_Invoice_ID Invoice Identifier */
    public void setC_Invoice_ID (int C_Invoice_ID)
    {
        if (C_Invoice_ID < 1) throw new IllegalArgumentException ("C_Invoice_ID is mandatory.");
        set_Value ("C_Invoice_ID", Integer.valueOf(C_Invoice_ID));
        
    }
    
    /** Get Invoice.
    @return Invoice Identifier */
    public int getC_Invoice_ID() 
    {
        return get_ValueAsInt("C_Invoice_ID");
        
    }
    
    /** Set Rate.
    @param Rate Rate or Tax or Exchange */
    public void setRate (java.math.BigDecimal Rate)
    {
        set_Value ("Rate", Rate);
        
    }
    
    /** Get Rate.
    @return Rate or Tax or Exchange */
    public java.math.BigDecimal getRate() 
    {
        return get_ValueAsBigDecimal("Rate");
        
    }
    
    /** Set Invoice Service Charge.
    @param XX_InvoiceServiceCharge_ID Invoice Service Charge */
    public void setXX_InvoiceServiceCharge_ID (int XX_InvoiceServiceCharge_ID)
    {
        if (XX_InvoiceServiceCharge_ID < 1) throw new IllegalArgumentException ("XX_InvoiceServiceCharge_ID is mandatory.");
        set_ValueNoCheck ("XX_InvoiceServiceCharge_ID", Integer.valueOf(XX_InvoiceServiceCharge_ID));
        
    }
    
    /** Get Invoice Service Charge.
    @return Invoice Service Charge */
    public int getXX_InvoiceServiceCharge_ID() 
    {
        return get_ValueAsInt("XX_InvoiceServiceCharge_ID");
        
    }
    
    
}
