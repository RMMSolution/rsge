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
/** Generated Model for I_SystemElement
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_I_SystemElement extends PO
{
    /** Standard Constructor
    @param ctx context
    @param I_SystemElement_ID id
    @param trx transaction
    */
    public X_I_SystemElement (Ctx ctx, int I_SystemElement_ID, Trx trx)
    {
        super (ctx, I_SystemElement_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (I_SystemElement_ID == 0)
        {
            setI_IsImported (false);	// N
            setI_SystemElement_ID (0);
            setProcessed (false);	// N
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_I_SystemElement (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664286052789L;
    /** Last Updated Timestamp 2013-10-19 12:32:16.0 */
    public static final long updatedMS = 1382160736000L;
    /** AD_Table_ID=1000505 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("I_SystemElement");
        
    }
    ;
    
    /** TableName=I_SystemElement */
    public static final String Table_Name="I_SystemElement";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set System Element.
    @param AD_Element_ID System Element enables the central maintenance of column description and help. */
    public void setAD_Element_ID (int AD_Element_ID)
    {
        if (AD_Element_ID <= 0) set_Value ("AD_Element_ID", null);
        else
        set_Value ("AD_Element_ID", Integer.valueOf(AD_Element_ID));
        
    }
    
    /** Get System Element.
    @return System Element enables the central maintenance of column description and help. */
    public int getAD_Element_ID() 
    {
        return get_ValueAsInt("AD_Element_ID");
        
    }
    
    /** Set DB Column Name.
    @param ColumnName Name of the column in the database */
    public void setColumnName (String ColumnName)
    {
        set_Value ("ColumnName", ColumnName);
        
    }
    
    /** Get DB Column Name.
    @return Name of the column in the database */
    public String getColumnName() 
    {
        return (String)get_Value("ColumnName");
        
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
    
    /** Set Entity Type.
    @param EntityType Dictionary Entity Type;
     Determines ownership and synchronization */
    public void setEntityType (String EntityType)
    {
        set_Value ("EntityType", EntityType);
        
    }
    
    /** Get Entity Type.
    @return Dictionary Entity Type;
     Determines ownership and synchronization */
    public String getEntityType() 
    {
        return (String)get_Value("EntityType");
        
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
    
    /** Set I_SystemElement_ID.
    @param I_SystemElement_ID I_SystemElement_ID */
    public void setI_SystemElement_ID (int I_SystemElement_ID)
    {
        if (I_SystemElement_ID < 1) throw new IllegalArgumentException ("I_SystemElement_ID is mandatory.");
        set_ValueNoCheck ("I_SystemElement_ID", Integer.valueOf(I_SystemElement_ID));
        
    }
    
    /** Get I_SystemElement_ID.
    @return I_SystemElement_ID */
    public int getI_SystemElement_ID() 
    {
        return get_ValueAsInt("I_SystemElement_ID");
        
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
    
    /** Set Print Text.
    @param PrintName The label text to be printed on a document or correspondence. */
    public void setPrintName (String PrintName)
    {
        set_Value ("PrintName", PrintName);
        
    }
    
    /** Get Print Text.
    @return The label text to be printed on a document or correspondence. */
    public String getPrintName() 
    {
        return (String)get_Value("PrintName");
        
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
