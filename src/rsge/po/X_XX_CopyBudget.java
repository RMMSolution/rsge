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
/** Generated Model for XX_CopyBudget
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_CopyBudget extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_CopyBudget_ID id
    @param trx transaction
    */
    public X_XX_CopyBudget (Ctx ctx, int XX_CopyBudget_ID, Trx trx)
    {
        super (ctx, XX_CopyBudget_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_CopyBudget_ID == 0)
        {
            setC_AcctSchema_ID (0);
            setC_Period_ID (0);
            setC_Period_To_ID (0);
            setGL_Budget_ID (0);
            setProcessed (false);	// N
            setTargetBudget_ID (0);
            setXX_CopyBudget_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_CopyBudget (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27745274491789L;
    /** Last Updated Timestamp 2016-05-13 21:19:35.0 */
    public static final long updatedMS = 1463149175000L;
    /** AD_Table_ID=1000130 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_CopyBudget");
        
    }
    ;
    
    /** TableName=XX_CopyBudget */
    public static final String Table_Name="XX_CopyBudget";
    
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
        if (C_AcctSchema_ID < 1) throw new IllegalArgumentException ("C_AcctSchema_ID is mandatory.");
        set_Value ("C_AcctSchema_ID", Integer.valueOf(C_AcctSchema_ID));
        
    }
    
    /** Get Accounting Schema.
    @return Rules for accounting */
    public int getC_AcctSchema_ID() 
    {
        return get_ValueAsInt("C_AcctSchema_ID");
        
    }
    
    /** Set Period.
    @param C_Period_ID Period of the Calendar */
    public void setC_Period_ID (int C_Period_ID)
    {
        if (C_Period_ID < 1) throw new IllegalArgumentException ("C_Period_ID is mandatory.");
        set_Value ("C_Period_ID", Integer.valueOf(C_Period_ID));
        
    }
    
    /** Get Period.
    @return Period of the Calendar */
    public int getC_Period_ID() 
    {
        return get_ValueAsInt("C_Period_ID");
        
    }
    
    /** Set Period To.
    @param C_Period_To_ID Ending period of a range of periods */
    public void setC_Period_To_ID (int C_Period_To_ID)
    {
        if (C_Period_To_ID < 1) throw new IllegalArgumentException ("C_Period_To_ID is mandatory.");
        set_Value ("C_Period_To_ID", Integer.valueOf(C_Period_To_ID));
        
    }
    
    /** Get Period To.
    @return Ending period of a range of periods */
    public int getC_Period_To_ID() 
    {
        return get_ValueAsInt("C_Period_To_ID");
        
    }
    
    /** Set Document No.
    @param DocumentNo Document sequence number of the document */
    public void setDocumentNo (int DocumentNo)
    {
        set_Value ("DocumentNo", Integer.valueOf(DocumentNo));
        
    }
    
    /** Get Document No.
    @return Document sequence number of the document */
    public int getDocumentNo() 
    {
        return get_ValueAsInt("DocumentNo");
        
    }
    
    /** Set Budget.
    @param GL_Budget_ID General Ledger Budget */
    public void setGL_Budget_ID (int GL_Budget_ID)
    {
        if (GL_Budget_ID < 1) throw new IllegalArgumentException ("GL_Budget_ID is mandatory.");
        set_Value ("GL_Budget_ID", Integer.valueOf(GL_Budget_ID));
        
    }
    
    /** Get Budget.
    @return General Ledger Budget */
    public int getGL_Budget_ID() 
    {
        return get_ValueAsInt("GL_Budget_ID");
        
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
    
    /** Set Target Budget.
    @param TargetBudget_ID Target budget */
    public void setTargetBudget_ID (int TargetBudget_ID)
    {
        if (TargetBudget_ID < 1) throw new IllegalArgumentException ("TargetBudget_ID is mandatory.");
        set_Value ("TargetBudget_ID", Integer.valueOf(TargetBudget_ID));
        
    }
    
    /** Get Target Budget.
    @return Target budget */
    public int getTargetBudget_ID() 
    {
        return get_ValueAsInt("TargetBudget_ID");
        
    }
    
    /** Set Total Amount.
    @param TotalAmt Total Amount */
    public void setTotalAmt (java.math.BigDecimal TotalAmt)
    {
        set_Value ("TotalAmt", TotalAmt);
        
    }
    
    /** Get Total Amount.
    @return Total Amount */
    public java.math.BigDecimal getTotalAmt() 
    {
        return get_ValueAsBigDecimal("TotalAmt");
        
    }
    
    /** Set Budget Copy.
    @param XX_CopyBudget_ID Copy budget from one budget to another */
    public void setXX_CopyBudget_ID (int XX_CopyBudget_ID)
    {
        if (XX_CopyBudget_ID < 1) throw new IllegalArgumentException ("XX_CopyBudget_ID is mandatory.");
        set_ValueNoCheck ("XX_CopyBudget_ID", Integer.valueOf(XX_CopyBudget_ID));
        
    }
    
    /** Get Budget Copy.
    @return Copy budget from one budget to another */
    public int getXX_CopyBudget_ID() 
    {
        return get_ValueAsInt("XX_CopyBudget_ID");
        
    }
    
    
}
