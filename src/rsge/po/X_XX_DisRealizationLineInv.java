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
/** Generated Model for XX_DisRealizationLineInv
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_DisRealizationLineInv extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_DisRealizationLineInv_ID id
    @param trx transaction
    */
    public X_XX_DisRealizationLineInv (Ctx ctx, int XX_DisRealizationLineInv_ID, Trx trx)
    {
        super (ctx, XX_DisRealizationLineInv_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_DisRealizationLineInv_ID == 0)
        {
            setXX_DisRealizationLine_ID (0);
            setXX_DisRealizationLineInv_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_DisRealizationLineInv (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27801738258526L;
    /** Last Updated Timestamp 2018-02-26 09:42:21.737 */
    public static final long updatedMS = 1519612941737L;
    /** AD_Table_ID=1000137 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_DisRealizationLineInv");
        
    }
    ;
    
    /** TableName=XX_DisRealizationLineInv */
    public static final String Table_Name="XX_DisRealizationLineInv";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
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
    
    /** Set Allocation Line.
    @param C_AllocationLine_ID Allocation Line */
    public void setC_AllocationLine_ID (int C_AllocationLine_ID)
    {
        if (C_AllocationLine_ID <= 0) set_Value ("C_AllocationLine_ID", null);
        else
        set_Value ("C_AllocationLine_ID", Integer.valueOf(C_AllocationLine_ID));
        
    }
    
    /** Get Allocation Line.
    @return Allocation Line */
    public int getC_AllocationLine_ID() 
    {
        return get_ValueAsInt("C_AllocationLine_ID");
        
    }
    
    /** Set Business Partner.
    @param C_BPartner_ID Identifies a Business Partner */
    public void setC_BPartner_ID (int C_BPartner_ID)
    {
        if (C_BPartner_ID <= 0) set_Value ("C_BPartner_ID", null);
        else
        set_Value ("C_BPartner_ID", Integer.valueOf(C_BPartner_ID));
        
    }
    
    /** Get Business Partner.
    @return Identifies a Business Partner */
    public int getC_BPartner_ID() 
    {
        return get_ValueAsInt("C_BPartner_ID");
        
    }
    
    /** Set Currency.
    @param C_Currency_ID The Currency for this record */
    public void setC_Currency_ID (int C_Currency_ID)
    {
        if (C_Currency_ID <= 0) set_Value ("C_Currency_ID", null);
        else
        set_Value ("C_Currency_ID", Integer.valueOf(C_Currency_ID));
        
    }
    
    /** Get Currency.
    @return The Currency for this record */
    public int getC_Currency_ID() 
    {
        return get_ValueAsInt("C_Currency_ID");
        
    }
    
    /** Set Invoice.
    @param C_Invoice_ID Invoice Identifier */
    public void setC_Invoice_ID (int C_Invoice_ID)
    {
        if (C_Invoice_ID <= 0) set_Value ("C_Invoice_ID", null);
        else
        set_Value ("C_Invoice_ID", Integer.valueOf(C_Invoice_ID));
        
    }
    
    /** Get Invoice.
    @return Invoice Identifier */
    public int getC_Invoice_ID() 
    {
        return get_ValueAsInt("C_Invoice_ID");
        
    }
    
    /** Set Date Invoiced.
    @param DateInvoiced Date printed on Invoice */
    public void setDateInvoiced (Timestamp DateInvoiced)
    {
        set_Value ("DateInvoiced", DateInvoiced);
        
    }
    
    /** Get Date Invoiced.
    @return Date printed on Invoice */
    public Timestamp getDateInvoiced() 
    {
        return (Timestamp)get_Value("DateInvoiced");
        
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
    
    /** Set Invoiced Amount.
    @param InvoicedAmt The amount invoiced */
    public void setInvoicedAmt (java.math.BigDecimal InvoicedAmt)
    {
        set_Value ("InvoicedAmt", InvoicedAmt);
        
    }
    
    /** Get Invoiced Amount.
    @return The amount invoiced */
    public java.math.BigDecimal getInvoicedAmt() 
    {
        return get_ValueAsBigDecimal("InvoicedAmt");
        
    }
    
    /** Set Open Amount.
    @param OpenAmt Open item amount */
    public void setOpenAmt (java.math.BigDecimal OpenAmt)
    {
        set_Value ("OpenAmt", OpenAmt);
        
    }
    
    /** Get Open Amount.
    @return Open item amount */
    public java.math.BigDecimal getOpenAmt() 
    {
        return get_ValueAsBigDecimal("OpenAmt");
        
    }
    
    /** Set Realized Amount.
    @param RealizedAmt Realized amount */
    public void setRealizedAmt (java.math.BigDecimal RealizedAmt)
    {
        set_Value ("RealizedAmt", RealizedAmt);
        
    }
    
    /** Get Realized Amount.
    @return Realized amount */
    public java.math.BigDecimal getRealizedAmt() 
    {
        return get_ValueAsBigDecimal("RealizedAmt");
        
    }
    
    /** Set Line.
    @param XX_DisRealizationLine_ID Line of realization */
    public void setXX_DisRealizationLine_ID (int XX_DisRealizationLine_ID)
    {
        if (XX_DisRealizationLine_ID < 1) throw new IllegalArgumentException ("XX_DisRealizationLine_ID is mandatory.");
        set_Value ("XX_DisRealizationLine_ID", Integer.valueOf(XX_DisRealizationLine_ID));
        
    }
    
    /** Get Line.
    @return Line of realization */
    public int getXX_DisRealizationLine_ID() 
    {
        return get_ValueAsInt("XX_DisRealizationLine_ID");
        
    }
    
    /** Set XX_DisRealizationLineInv_ID.
    @param XX_DisRealizationLineInv_ID XX_DisRealizationLineInv_ID */
    public void setXX_DisRealizationLineInv_ID (int XX_DisRealizationLineInv_ID)
    {
        if (XX_DisRealizationLineInv_ID < 1) throw new IllegalArgumentException ("XX_DisRealizationLineInv_ID is mandatory.");
        set_ValueNoCheck ("XX_DisRealizationLineInv_ID", Integer.valueOf(XX_DisRealizationLineInv_ID));
        
    }
    
    /** Get XX_DisRealizationLineInv_ID.
    @return XX_DisRealizationLineInv_ID */
    public int getXX_DisRealizationLineInv_ID() 
    {
        return get_ValueAsInt("XX_DisRealizationLineInv_ID");
        
    }
    
    
}
