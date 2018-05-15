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
/** Generated Model for XX_SubsidiaryPeriodBalance
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_SubsidiaryPeriodBalance extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_SubsidiaryPeriodBalance_ID id
    @param trx transaction
    */
    public X_XX_SubsidiaryPeriodBalance (Ctx ctx, int XX_SubsidiaryPeriodBalance_ID, Trx trx)
    {
        super (ctx, XX_SubsidiaryPeriodBalance_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_SubsidiaryPeriodBalance_ID == 0)
        {
            setC_Year_ID (0);
            setDateDoc (new Timestamp(System.currentTimeMillis()));
            setDocumentNo (null);
            setProcessed (false);	// N
            setXX_SubsidiaryPeriodBalance_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_SubsidiaryPeriodBalance (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27746234543789L;
    /** Last Updated Timestamp 2016-05-25 00:00:27.0 */
    public static final long updatedMS = 1464109227000L;
    /** AD_Table_ID=1000187 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_SubsidiaryPeriodBalance");
        
    }
    ;
    
    /** TableName=XX_SubsidiaryPeriodBalance */
    public static final String Table_Name="XX_SubsidiaryPeriodBalance";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
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
    
    /** Set Period.
    @param C_Period_ID Period of the Calendar */
    public void setC_Period_ID (int C_Period_ID)
    {
        if (C_Period_ID <= 0) set_Value ("C_Period_ID", null);
        else
        set_Value ("C_Period_ID", Integer.valueOf(C_Period_ID));
        
    }
    
    /** Get Period.
    @return Period of the Calendar */
    public int getC_Period_ID() 
    {
        return get_ValueAsInt("C_Period_ID");
        
    }
    
    /** Set Year.
    @param C_Year_ID Calendar Year */
    public void setC_Year_ID (int C_Year_ID)
    {
        if (C_Year_ID < 1) throw new IllegalArgumentException ("C_Year_ID is mandatory.");
        set_Value ("C_Year_ID", Integer.valueOf(C_Year_ID));
        
    }
    
    /** Get Year.
    @return Calendar Year */
    public int getC_Year_ID() 
    {
        return get_ValueAsInt("C_Year_ID");
        
    }
    
    /** Set Account Date.
    @param DateAcct General Ledger Date */
    public void setDateAcct (Timestamp DateAcct)
    {
        set_Value ("DateAcct", DateAcct);
        
    }
    
    /** Get Account Date.
    @return General Ledger Date */
    public Timestamp getDateAcct() 
    {
        return (Timestamp)get_Value("DateAcct");
        
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
    
    /** Set Description.
    @param Description Optional short description of the record */
    public void setDescription (java.math.BigDecimal Description)
    {
        set_Value ("Description", Description);
        
    }
    
    /** Get Description.
    @return Optional short description of the record */
    public java.math.BigDecimal getDescription() 
    {
        return get_ValueAsBigDecimal("Description");
        
    }
    
    /** Set Difference.
    @param DifferenceAmt Difference Amount */
    public void setDifferenceAmt (java.math.BigDecimal DifferenceAmt)
    {
        set_Value ("DifferenceAmt", DifferenceAmt);
        
    }
    
    /** Get Difference.
    @return Difference Amount */
    public java.math.BigDecimal getDifferenceAmt() 
    {
        return get_ValueAsBigDecimal("DifferenceAmt");
        
    }
    
    /** Set Document No.
    @param DocumentNo Document sequence number of the document */
    public void setDocumentNo (String DocumentNo)
    {
        if (DocumentNo == null) throw new IllegalArgumentException ("DocumentNo is mandatory.");
        set_Value ("DocumentNo", DocumentNo);
        
    }
    
    /** Get Document No.
    @return Document sequence number of the document */
    public String getDocumentNo() 
    {
        return (String)get_Value("DocumentNo");
        
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
    
    /** Set Total Credit.
    @param TotalCr Total Credit in document currency */
    public void setTotalCr (java.math.BigDecimal TotalCr)
    {
        set_Value ("TotalCr", TotalCr);
        
    }
    
    /** Get Total Credit.
    @return Total Credit in document currency */
    public java.math.BigDecimal getTotalCr() 
    {
        return get_ValueAsBigDecimal("TotalCr");
        
    }
    
    /** Set Total Debit.
    @param TotalDr Total debit in document currency */
    public void setTotalDr (java.math.BigDecimal TotalDr)
    {
        set_Value ("TotalDr", TotalDr);
        
    }
    
    /** Get Total Debit.
    @return Total debit in document currency */
    public java.math.BigDecimal getTotalDr() 
    {
        return get_ValueAsBigDecimal("TotalDr");
        
    }
    
    /** Set Period Balance.
    @param XX_SubsidiaryPeriodBalance_ID Subsidiary's period balance */
    public void setXX_SubsidiaryPeriodBalance_ID (int XX_SubsidiaryPeriodBalance_ID)
    {
        if (XX_SubsidiaryPeriodBalance_ID < 1) throw new IllegalArgumentException ("XX_SubsidiaryPeriodBalance_ID is mandatory.");
        set_ValueNoCheck ("XX_SubsidiaryPeriodBalance_ID", Integer.valueOf(XX_SubsidiaryPeriodBalance_ID));
        
    }
    
    /** Get Period Balance.
    @return Subsidiary's period balance */
    public int getXX_SubsidiaryPeriodBalance_ID() 
    {
        return get_ValueAsInt("XX_SubsidiaryPeriodBalance_ID");
        
    }
    
    
}
