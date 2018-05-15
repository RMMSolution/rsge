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
/** Generated Model for XX_DisRealizationLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_DisRealizationLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_DisRealizationLine_ID id
    @param trx transaction
    */
    public X_XX_DisRealizationLine (Ctx ctx, int XX_DisRealizationLine_ID, Trx trx)
    {
        super (ctx, XX_DisRealizationLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_DisRealizationLine_ID == 0)
        {
            setIsGenerated (false);	// N
            setLine (0);	// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM XX_DisRealizationLine WHERE XX_DisbursementRealization_ID=@XX_DisbursementRealization_ID@
            setXX_DisRealizationLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_DisRealizationLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27796064309150L;
    /** Last Updated Timestamp 2017-12-22 17:36:32.361 */
    public static final long updatedMS = 1513938992361L;
    /** AD_Table_ID=1000136 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_DisRealizationLine");
        
    }
    ;
    
    /** TableName=XX_DisRealizationLine */
    public static final String Table_Name="XX_DisRealizationLine";
    
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
    
    /** Set Comments.
    @param Comments Comments or additional information */
    public void setComments (String Comments)
    {
        set_Value ("Comments", Comments);
        
    }
    
    /** Get Comments.
    @return Comments or additional information */
    public String getComments() 
    {
        return (String)get_Value("Comments");
        
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
    
    /** Set Generated.
    @param IsGenerated This Line is generated */
    public void setIsGenerated (boolean IsGenerated)
    {
        set_Value ("IsGenerated", Boolean.valueOf(IsGenerated));
        
    }
    
    /** Get Generated.
    @return This Line is generated */
    public boolean isGenerated() 
    {
        return get_ValueAsBoolean("IsGenerated");
        
    }
    
    /** Set Line No.
    @param Line Unique line for this document */
    public void setLine (int Line)
    {
        set_Value ("Line", Integer.valueOf(Line));
        
    }
    
    /** Get Line No.
    @return Unique line for this document */
    public int getLine() 
    {
        return get_ValueAsInt("Line");
        
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
    
    /** Set Disbursement Line.
    @param XX_AdvanceDisbursementLine_ID Disbursement Line */
    public void setXX_AdvanceDisbursementLine_ID (int XX_AdvanceDisbursementLine_ID)
    {
        if (XX_AdvanceDisbursementLine_ID <= 0) set_Value ("XX_AdvanceDisbursementLine_ID", null);
        else
        set_Value ("XX_AdvanceDisbursementLine_ID", Integer.valueOf(XX_AdvanceDisbursementLine_ID));
        
    }
    
    /** Get Disbursement Line.
    @return Disbursement Line */
    public int getXX_AdvanceDisbursementLine_ID() 
    {
        return get_ValueAsInt("XX_AdvanceDisbursementLine_ID");
        
    }
    
    /** Set Disbursement Realization.
    @param XX_DisbursementRealization_ID Realization of advance disbursement */
    public void setXX_DisbursementRealization_ID (int XX_DisbursementRealization_ID)
    {
        if (XX_DisbursementRealization_ID <= 0) set_Value ("XX_DisbursementRealization_ID", null);
        else
        set_Value ("XX_DisbursementRealization_ID", Integer.valueOf(XX_DisbursementRealization_ID));
        
    }
    
    /** Get Disbursement Realization.
    @return Realization of advance disbursement */
    public int getXX_DisbursementRealization_ID() 
    {
        return get_ValueAsInt("XX_DisbursementRealization_ID");
        
    }
    
    /** Set Disbursement Type.
    @param XX_DisbursementType_ID Type of disbursement */
    public void setXX_DisbursementType_ID (int XX_DisbursementType_ID)
    {
        if (XX_DisbursementType_ID <= 0) set_Value ("XX_DisbursementType_ID", null);
        else
        set_Value ("XX_DisbursementType_ID", Integer.valueOf(XX_DisbursementType_ID));
        
    }
    
    /** Get Disbursement Type.
    @return Type of disbursement */
    public int getXX_DisbursementType_ID() 
    {
        return get_ValueAsInt("XX_DisbursementType_ID");
        
    }
    
    /** Set Line.
    @param XX_DisRealizationLine_ID Line of realization */
    public void setXX_DisRealizationLine_ID (int XX_DisRealizationLine_ID)
    {
        if (XX_DisRealizationLine_ID < 1) throw new IllegalArgumentException ("XX_DisRealizationLine_ID is mandatory.");
        set_ValueNoCheck ("XX_DisRealizationLine_ID", Integer.valueOf(XX_DisRealizationLine_ID));
        
    }
    
    /** Get Line.
    @return Line of realization */
    public int getXX_DisRealizationLine_ID() 
    {
        return get_ValueAsInt("XX_DisRealizationLine_ID");
        
    }
    
    
}
