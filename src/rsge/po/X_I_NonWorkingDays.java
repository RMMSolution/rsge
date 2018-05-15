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
/** Generated Model for I_NonWorkingDays
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_I_NonWorkingDays extends PO
{
    /** Standard Constructor
    @param ctx context
    @param I_NonWorkingDays_ID id
    @param trx transaction
    */
    public X_I_NonWorkingDays (Ctx ctx, int I_NonWorkingDays_ID, Trx trx)
    {
        super (ctx, I_NonWorkingDays_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (I_NonWorkingDays_ID == 0)
        {
            setI_IsImported (false);	// N
            setI_NonWorkingDays_ID (0);
            setProcessed (false);	// N
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_I_NonWorkingDays (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27671241659789L;
    /** Last Updated Timestamp 2014-01-08 00:39:03.0 */
    public static final long updatedMS = 1389116343000L;
    /** AD_Table_ID=1002603 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("I_NonWorkingDays");
        
    }
    ;
    
    /** TableName=I_NonWorkingDays */
    public static final String Table_Name="I_NonWorkingDays";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Date.
    @param Date1 Date when business is not conducted */
    public void setDate1 (Timestamp Date1)
    {
        set_Value ("Date1", Date1);
        
    }
    
    /** Get Date.
    @return Date when business is not conducted */
    public Timestamp getDate1() 
    {
        return (Timestamp)get_Value("Date1");
        
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
    
    /** Set Import Error Message.
    @param I_ErrorMsg Messages generated from import process */
    public void setI_ErrorMsg (String I_ErrorMsg)
    {
        set_Value ("I_ErrorMsg", I_ErrorMsg);
        
    }
    
    /** Get Import Error Message.
    @return Messages generated from import process */
    public String getI_ErrorMsg() 
    {
        return (String)get_Value("I_ErrorMsg");
        
    }
    
    /** Set Imported.
    @param I_IsImported Has this import been processed? */
    public void setI_IsImported (boolean I_IsImported)
    {
        set_Value ("I_IsImported", Boolean.valueOf(I_IsImported));
        
    }
    
    /** Get Imported.
    @return Has this import been processed? */
    public boolean isI_IsImported() 
    {
        return get_ValueAsBoolean("I_IsImported");
        
    }
    
    /** Set I_NonWorkingDays_ID.
    @param I_NonWorkingDays_ID I_NonWorkingDays_ID */
    public void setI_NonWorkingDays_ID (int I_NonWorkingDays_ID)
    {
        if (I_NonWorkingDays_ID < 1) throw new IllegalArgumentException ("I_NonWorkingDays_ID is mandatory.");
        set_ValueNoCheck ("I_NonWorkingDays_ID", Integer.valueOf(I_NonWorkingDays_ID));
        
    }
    
    /** Get I_NonWorkingDays_ID.
    @return I_NonWorkingDays_ID */
    public int getI_NonWorkingDays_ID() 
    {
        return get_ValueAsInt("I_NonWorkingDays_ID");
        
    }
    
    /** Set Name.
    @param Name Alphanumeric identifier of the entity */
    public void setName (String Name)
    {
        set_Value ("Name", Name);
        
    }
    
    /** Get Name.
    @return Alphanumeric identifier of the entity */
    public String getName() 
    {
        return (String)get_Value("Name");
        
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
    
    
}
