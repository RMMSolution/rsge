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
/** Generated Model for XX_IntercompanyOffset
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_IntercompanyOffset extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_IntercompanyOffset_ID id
    @param trx transaction
    */
    public X_XX_IntercompanyOffset (Ctx ctx, int XX_IntercompanyOffset_ID, Trx trx)
    {
        super (ctx, XX_IntercompanyOffset_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_IntercompanyOffset_ID == 0)
        {
            setDateDoc (new Timestamp(System.currentTimeMillis()));	// @#Date@
            setProcessed (false);	// N
            setRecord_ID (0);
            setXX_IntercompanyOffset_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_IntercompanyOffset (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27672974504789L;
    /** Last Updated Timestamp 2014-01-28 01:59:48.0 */
    public static final long updatedMS = 1390849188000L;
    /** AD_Table_ID=1000237 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_IntercompanyOffset");
        
    }
    ;
    
    /** TableName=XX_IntercompanyOffset */
    public static final String Table_Name="XX_IntercompanyOffset";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Table.
    @param AD_Table_ID Database Table information */
    public void setAD_Table_ID (int AD_Table_ID)
    {
        if (AD_Table_ID <= 0) set_Value ("AD_Table_ID", null);
        else
        set_Value ("AD_Table_ID", Integer.valueOf(AD_Table_ID));
        
    }
    
    /** Get Table.
    @return Database Table information */
    public int getAD_Table_ID() 
    {
        return get_ValueAsInt("AD_Table_ID");
        
    }
    
    /** Set Accounting Schema.
    @param C_AcctSchema_ID Rules for accounting */
    public void setC_AcctSchema_ID (int C_AcctSchema_ID)
    {
        if (C_AcctSchema_ID <= 0) set_Value ("C_AcctSchema_ID", null);
        else
        set_Value ("C_AcctSchema_ID", Integer.valueOf(C_AcctSchema_ID));
        
    }
    
    /** Get Accounting Schema.
    @return Rules for accounting */
    public int getC_AcctSchema_ID() 
    {
        return get_ValueAsInt("C_AcctSchema_ID");
        
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
    
    /** Set Document Date.
    @param DateDoc Date of the Document */
    public void setDateDoc (Timestamp DateDoc)
    {
        if (DateDoc == null) throw new IllegalArgumentException ("DateDoc is mandatory.");
        set_Value ("DateDoc", DateDoc);
        
    }
    
    /** Get Document Date.
    @return Date of the Document */
    public Timestamp getDateDoc() 
    {
        return (Timestamp)get_Value("DateDoc");
        
    }
    
    /** Set Budget.
    @param GL_Budget_ID General Ledger Budget */
    public void setGL_Budget_ID (int GL_Budget_ID)
    {
        if (GL_Budget_ID <= 0) set_Value ("GL_Budget_ID", null);
        else
        set_Value ("GL_Budget_ID", Integer.valueOf(GL_Budget_ID));
        
    }
    
    /** Get Budget.
    @return General Ledger Budget */
    public int getGL_Budget_ID() 
    {
        return get_ValueAsInt("GL_Budget_ID");
        
    }
    
    /** Set Journal.
    @param GL_Journal_ID General Ledger Journal */
    public void setGL_Journal_ID (int GL_Journal_ID)
    {
        if (GL_Journal_ID <= 0) set_Value ("GL_Journal_ID", null);
        else
        set_Value ("GL_Journal_ID", Integer.valueOf(GL_Journal_ID));
        
    }
    
    /** Get Journal.
    @return General Ledger Journal */
    public int getGL_Journal_ID() 
    {
        return get_ValueAsInt("GL_Journal_ID");
        
    }
    
    /** Actual = A */
    public static final String POSTINGTYPE_Actual = X_Ref__Posting_Type.ACTUAL.getValue();
    /** Budget = B */
    public static final String POSTINGTYPE_Budget = X_Ref__Posting_Type.BUDGET.getValue();
    /** Commitment = E */
    public static final String POSTINGTYPE_Commitment = X_Ref__Posting_Type.COMMITMENT.getValue();
    /** Fiscal Correction = F */
    public static final String POSTINGTYPE_FiscalCorrection = X_Ref__Posting_Type.FISCAL_CORRECTION.getValue();
    /** Reservation = R */
    public static final String POSTINGTYPE_Reservation = X_Ref__Posting_Type.RESERVATION.getValue();
    /** Statistical = S */
    public static final String POSTINGTYPE_Statistical = X_Ref__Posting_Type.STATISTICAL.getValue();
    /** Set PostingType.
    @param PostingType The type of posted amount for the transaction */
    public void setPostingType (String PostingType)
    {
        if (!X_Ref__Posting_Type.isValid(PostingType))
        throw new IllegalArgumentException ("PostingType Invalid value - " + PostingType + " - Reference_ID=125 - A - B - E - F - R - S");
        set_Value ("PostingType", PostingType);
        
    }
    
    /** Get PostingType.
    @return The type of posted amount for the transaction */
    public String getPostingType() 
    {
        return (String)get_Value("PostingType");
        
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
    
    /** Set Record ID.
    @param Record_ID Direct internal record ID */
    public void setRecord_ID (int Record_ID)
    {
        if (Record_ID < 0) throw new IllegalArgumentException ("Record_ID is mandatory.");
        set_Value ("Record_ID", Integer.valueOf(Record_ID));
        
    }
    
    /** Get Record ID.
    @return Direct internal record ID */
    public int getRecord_ID() 
    {
        return get_ValueAsInt("Record_ID");
        
    }
    
    /** Set Intercompany Offset.
    @param XX_IntercompanyOffset_ID Intercompany Offset */
    public void setXX_IntercompanyOffset_ID (int XX_IntercompanyOffset_ID)
    {
        if (XX_IntercompanyOffset_ID < 1) throw new IllegalArgumentException ("XX_IntercompanyOffset_ID is mandatory.");
        set_ValueNoCheck ("XX_IntercompanyOffset_ID", Integer.valueOf(XX_IntercompanyOffset_ID));
        
    }
    
    /** Get Intercompany Offset.
    @return Intercompany Offset */
    public int getXX_IntercompanyOffset_ID() 
    {
        return get_ValueAsInt("XX_IntercompanyOffset_ID");
        
    }
    
    
}
