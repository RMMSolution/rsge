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
/** Generated Model for I_Reference
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_I_Reference extends PO
{
    /** Standard Constructor
    @param ctx context
    @param I_Reference_ID id
    @param trx transaction
    */
    public X_I_Reference (Ctx ctx, int I_Reference_ID, Trx trx)
    {
        super (ctx, I_Reference_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (I_Reference_ID == 0)
        {
            setI_Reference_ID (0);
            setIsDisplayIdentifiers (false);	// N
            setIsValueDisplayed (false);	// N
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_I_Reference (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664503069789L;
    /** Last Updated Timestamp 2013-10-22 00:49:13.0 */
    public static final long updatedMS = 1382377753000L;
    /** AD_Table_ID=1000503 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("I_Reference");
        
    }
    ;
    
    /** TableName=I_Reference */
    public static final String Table_Name="I_Reference";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Reference.
    @param AD_Reference_ID System Reference and Validation */
    public void setAD_Reference_ID (int AD_Reference_ID)
    {
        if (AD_Reference_ID <= 0) set_Value ("AD_Reference_ID", null);
        else
        set_Value ("AD_Reference_ID", Integer.valueOf(AD_Reference_ID));
        
    }
    
    /** Get Reference.
    @return System Reference and Validation */
    public int getAD_Reference_ID() 
    {
        return get_ValueAsInt("AD_Reference_ID");
        
    }
    
    /** Set Reference List.
    @param AD_Ref_List_ID Reference List based on Table */
    public void setAD_Ref_List_ID (int AD_Ref_List_ID)
    {
        if (AD_Ref_List_ID <= 0) set_Value ("AD_Ref_List_ID", null);
        else
        set_Value ("AD_Ref_List_ID", Integer.valueOf(AD_Ref_List_ID));
        
    }
    
    /** Get Reference List.
    @return Reference List based on Table */
    public int getAD_Ref_List_ID() 
    {
        return get_ValueAsInt("AD_Ref_List_ID");
        
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
    
    /** Set Display column.
    @param Column_Display_ID Column that will display */
    public void setColumn_Display_ID (int Column_Display_ID)
    {
        if (Column_Display_ID <= 0) set_Value ("Column_Display_ID", null);
        else
        set_Value ("Column_Display_ID", Integer.valueOf(Column_Display_ID));
        
    }
    
    /** Get Display column.
    @return Column that will display */
    public int getColumn_Display_ID() 
    {
        return get_ValueAsInt("Column_Display_ID");
        
    }
    
    /** Set Key column.
    @param Column_Key_ID Unique identifier of a record */
    public void setColumn_Key_ID (int Column_Key_ID)
    {
        if (Column_Key_ID <= 0) set_Value ("Column_Key_ID", null);
        else
        set_Value ("Column_Key_ID", Integer.valueOf(Column_Key_ID));
        
    }
    
    /** Get Key column.
    @return Unique identifier of a record */
    public int getColumn_Key_ID() 
    {
        return get_ValueAsInt("Column_Key_ID");
        
    }
    
    /** Set Key Column Name.
    @param ColumnKeyName Key column name */
    public void setColumnKeyName (String ColumnKeyName)
    {
        set_Value ("ColumnKeyName", ColumnKeyName);
        
    }
    
    /** Get Key Column Name.
    @return Key column name */
    public String getColumnKeyName() 
    {
        return (String)get_Value("ColumnKeyName");
        
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
    
    /** Set Display DB Column Name.
    @param DisplayColumnName Display DB column name */
    public void setDisplayColumnName (String DisplayColumnName)
    {
        set_Value ("DisplayColumnName", DisplayColumnName);
        
    }
    
    /** Get Display DB Column Name.
    @return Display DB column name */
    public String getDisplayColumnName() 
    {
        return (String)get_Value("DisplayColumnName");
        
    }
    
    /** Set Comment.
    @param Help Comment, Help or Hint */
    public void setHelp (String Help)
    {
        set_Value ("Help", Help);
        
    }
    
    /** Get Comment.
    @return Comment, Help or Hint */
    public String getHelp() 
    {
        return (String)get_Value("Help");
        
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
    
    /** Error = E */
    public static final String I_ISIMPORTED_Error = X_Ref__IsImported.ERROR.getValue();
    /** No = N */
    public static final String I_ISIMPORTED_No = X_Ref__IsImported.NO.getValue();
    /** Yes = Y */
    public static final String I_ISIMPORTED_Yes = X_Ref__IsImported.YES.getValue();
    /** Set Imported.
    @param I_IsImported Has this import been processed? */
    public void setI_IsImported (String I_IsImported)
    {
        if (!X_Ref__IsImported.isValid(I_IsImported))
        throw new IllegalArgumentException ("I_IsImported Invalid value - " + I_IsImported + " - Reference_ID=420 - E - N - Y");
        set_Value ("I_IsImported", I_IsImported);
        
    }
    
    /** Get Imported.
    @return Has this import been processed? */
    public String getI_IsImported() 
    {
        return (String)get_Value("I_IsImported");
        
    }
    
    /** Set I_Reference_ID.
    @param I_Reference_ID I_Reference_ID */
    public void setI_Reference_ID (int I_Reference_ID)
    {
        if (I_Reference_ID < 1) throw new IllegalArgumentException ("I_Reference_ID is mandatory.");
        set_ValueNoCheck ("I_Reference_ID", Integer.valueOf(I_Reference_ID));
        
    }
    
    /** Get I_Reference_ID.
    @return I_Reference_ID */
    public int getI_Reference_ID() 
    {
        return get_ValueAsInt("I_Reference_ID");
        
    }
    
    /** Set Display Identifiers.
    @param IsDisplayIdentifiers Display Identifiers */
    public void setIsDisplayIdentifiers (boolean IsDisplayIdentifiers)
    {
        set_Value ("IsDisplayIdentifiers", Boolean.valueOf(IsDisplayIdentifiers));
        
    }
    
    /** Get Display Identifiers.
    @return Display Identifiers */
    public boolean isDisplayIdentifiers() 
    {
        return get_ValueAsBoolean("IsDisplayIdentifiers");
        
    }
    
    /** Set Display Value.
    @param IsValueDisplayed Displays Value column with the Display column */
    public void setIsValueDisplayed (boolean IsValueDisplayed)
    {
        set_Value ("IsValueDisplayed", Boolean.valueOf(IsValueDisplayed));
        
    }
    
    /** Get Display Value.
    @return Displays Value column with the Display column */
    public boolean isValueDisplayed() 
    {
        return get_ValueAsBoolean("IsValueDisplayed");
        
    }
    
    /** Set List Description.
    @param ListDescription Description of list validation */
    public void setListDescription (String ListDescription)
    {
        set_Value ("ListDescription", ListDescription);
        
    }
    
    /** Get List Description.
    @return Description of list validation */
    public String getListDescription() 
    {
        return (String)get_Value("ListDescription");
        
    }
    
    /** Set List Name.
    @param ListName List Validation Name */
    public void setListName (String ListName)
    {
        set_Value ("ListName", ListName);
        
    }
    
    /** Get List Name.
    @return List Validation Name */
    public String getListName() 
    {
        return (String)get_Value("ListName");
        
    }
    
    /** Set List Value.
    @param ListValue Value of list validation */
    public void setListValue (String ListValue)
    {
        set_Value ("ListValue", ListValue);
        
    }
    
    /** Get List Value.
    @return Value of list validation */
    public String getListValue() 
    {
        return (String)get_Value("ListValue");
        
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
    
    /** Set Sql ORDER BY.
    @param OrderByClause Fully qualified ORDER BY clause */
    public void setOrderByClause (String OrderByClause)
    {
        set_Value ("OrderByClause", OrderByClause);
        
    }
    
    /** Get Sql ORDER BY.
    @return Fully qualified ORDER BY clause */
    public String getOrderByClause() 
    {
        return (String)get_Value("OrderByClause");
        
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
    
    /** Set DB Table Name.
    @param TableName Name of the table in the database */
    public void setTableName (String TableName)
    {
        set_Value ("TableName", TableName);
        
    }
    
    /** Get DB Table Name.
    @return Name of the table in the database */
    public String getTableName() 
    {
        return (String)get_Value("TableName");
        
    }
    
    /** DataType = D */
    public static final String VALIDATIONTYPE_DataType = X_Ref_AD_Reference_Validation_Types.DATA_TYPE.getValue();
    /** List Validation = L */
    public static final String VALIDATIONTYPE_ListValidation = X_Ref_AD_Reference_Validation_Types.LIST_VALIDATION.getValue();
    /** Table Validation = T */
    public static final String VALIDATIONTYPE_TableValidation = X_Ref_AD_Reference_Validation_Types.TABLE_VALIDATION.getValue();
    /** Set Validation type.
    @param ValidationType Different method of validating data */
    public void setValidationType (String ValidationType)
    {
        if (!X_Ref_AD_Reference_Validation_Types.isValid(ValidationType))
        throw new IllegalArgumentException ("ValidationType Invalid value - " + ValidationType + " - Reference_ID=2 - D - L - T");
        set_Value ("ValidationType", ValidationType);
        
    }
    
    /** Get Validation type.
    @return Different method of validating data */
    public String getValidationType() 
    {
        return (String)get_Value("ValidationType");
        
    }
    
    /** Set Valid from.
    @param ValidFrom Valid from including this date (first day) */
    public void setValidFrom (Timestamp ValidFrom)
    {
        set_Value ("ValidFrom", ValidFrom);
        
    }
    
    /** Get Valid from.
    @return Valid from including this date (first day) */
    public Timestamp getValidFrom() 
    {
        return (Timestamp)get_Value("ValidFrom");
        
    }
    
    /** Set Valid to.
    @param ValidTo Valid to including this date (last day) */
    public void setValidTo (Timestamp ValidTo)
    {
        set_Value ("ValidTo", ValidTo);
        
    }
    
    /** Get Valid to.
    @return Valid to including this date (last day) */
    public Timestamp getValidTo() 
    {
        return (Timestamp)get_Value("ValidTo");
        
    }
    
    /** Set Value Format.
    @param VFormat Format of the value;
     Can contain fixed format elements, Variables: "_lLoOaAcCa09" */
    public void setVFormat (String VFormat)
    {
        set_Value ("VFormat", VFormat);
        
    }
    
    /** Get Value Format.
    @return Format of the value;
     Can contain fixed format elements, Variables: "_lLoOaAcCa09" */
    public String getVFormat() 
    {
        return (String)get_Value("VFormat");
        
    }
    
    /** Set Sql WHERE.
    @param WhereClause Fully qualified SQL WHERE clause */
    public void setWhereClause (String WhereClause)
    {
        set_Value ("WhereClause", WhereClause);
        
    }
    
    /** Get Sql WHERE.
    @return Fully qualified SQL WHERE clause */
    public String getWhereClause() 
    {
        return (String)get_Value("WhereClause");
        
    }
    
    
}
