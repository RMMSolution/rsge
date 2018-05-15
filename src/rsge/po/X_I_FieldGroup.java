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
/** Generated Model for I_FieldGroup
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_I_FieldGroup extends PO
{
    /** Standard Constructor
    @param ctx context
    @param I_FieldGroup_ID id
    @param trx transaction
    */
    public X_I_FieldGroup (Ctx ctx, int I_FieldGroup_ID, Trx trx)
    {
        super (ctx, I_FieldGroup_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (I_FieldGroup_ID == 0)
        {
            setI_FieldGroup_ID (0);
            setProcessed (false);	// N
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_I_FieldGroup (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664527281789L;
    /** Last Updated Timestamp 2013-10-22 07:32:45.0 */
    public static final long updatedMS = 1382401965000L;
    /** AD_Table_ID=1000502 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("I_FieldGroup");
        
    }
    ;
    
    /** TableName=I_FieldGroup */
    public static final String Table_Name="I_FieldGroup";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Field Group.
    @param AD_FieldGroup_ID Logical grouping of fields */
    public void setAD_FieldGroup_ID (int AD_FieldGroup_ID)
    {
        if (AD_FieldGroup_ID <= 0) set_Value ("AD_FieldGroup_ID", null);
        else
        set_Value ("AD_FieldGroup_ID", Integer.valueOf(AD_FieldGroup_ID));
        
    }
    
    /** Get Field Group.
    @return Logical grouping of fields */
    public int getAD_FieldGroup_ID() 
    {
        return get_ValueAsInt("AD_FieldGroup_ID");
        
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
    
    /** Set I_FieldGroup_ID.
    @param I_FieldGroup_ID I_FieldGroup_ID */
    public void setI_FieldGroup_ID (int I_FieldGroup_ID)
    {
        if (I_FieldGroup_ID < 1) throw new IllegalArgumentException ("I_FieldGroup_ID is mandatory.");
        set_ValueNoCheck ("I_FieldGroup_ID", Integer.valueOf(I_FieldGroup_ID));
        
    }
    
    /** Get I_FieldGroup_ID.
    @return I_FieldGroup_ID */
    public int getI_FieldGroup_ID() 
    {
        return get_ValueAsInt("I_FieldGroup_ID");
        
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
