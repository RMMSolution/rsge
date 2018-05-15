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
/** Generated Model for XX_ExpenseTransfer
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_ExpenseTransfer extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_ExpenseTransfer_ID id
    @param trx transaction
    */
    public X_XX_ExpenseTransfer (Ctx ctx, int XX_ExpenseTransfer_ID, Trx trx)
    {
        super (ctx, XX_ExpenseTransfer_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_ExpenseTransfer_ID == 0)
        {
            setDateDoc (new Timestamp(System.currentTimeMillis()));
            setDocumentNo (null);
            setProcessed (false);	// N
            setXX_ExpenseTransfer_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_ExpenseTransfer (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27807311660084L;
    /** Last Updated Timestamp 2018-05-01 21:52:23.295 */
    public static final long updatedMS = 1525186343295L;
    /** AD_Table_ID=1000612 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_ExpenseTransfer");
        
    }
    ;
    
    /** TableName=XX_ExpenseTransfer */
    public static final String Table_Name="XX_ExpenseTransfer";
    
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
    
    /** Set Cash Journal.
    @param C_Cash_ID Cash Journal */
    public void setC_Cash_ID (int C_Cash_ID)
    {
        if (C_Cash_ID <= 0) set_Value ("C_Cash_ID", null);
        else
        set_Value ("C_Cash_ID", Integer.valueOf(C_Cash_ID));
        
    }
    
    /** Get Cash Journal.
    @return Cash Journal */
    public int getC_Cash_ID() 
    {
        return get_ValueAsInt("C_Cash_ID");
        
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
    
    /** Set XX_ExpenseTransfer_ID.
    @param XX_ExpenseTransfer_ID XX_ExpenseTransfer_ID */
    public void setXX_ExpenseTransfer_ID (int XX_ExpenseTransfer_ID)
    {
        if (XX_ExpenseTransfer_ID < 1) throw new IllegalArgumentException ("XX_ExpenseTransfer_ID is mandatory.");
        set_ValueNoCheck ("XX_ExpenseTransfer_ID", Integer.valueOf(XX_ExpenseTransfer_ID));
        
    }
    
    /** Get XX_ExpenseTransfer_ID.
    @return XX_ExpenseTransfer_ID */
    public int getXX_ExpenseTransfer_ID() 
    {
        return get_ValueAsInt("XX_ExpenseTransfer_ID");
        
    }
    
    
}
