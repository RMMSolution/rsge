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
/** Generated Model for I_WindowTab
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_I_WindowTab extends PO
{
    /** Standard Constructor
    @param ctx context
    @param I_WindowTab_ID id
    @param trx transaction
    */
    public X_I_WindowTab (Ctx ctx, int I_WindowTab_ID, Trx trx)
    {
        super (ctx, I_WindowTab_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (I_WindowTab_ID == 0)
        {
            setHasTree (false);	// N
            setIsAdvancedTab (false);	// N
            setIsDisplayed (true);	// Y
            setIsInsertRecord (false);	// N
            setIsReadOnly (false);	// N
            setIsSingleRow (false);	// N
            setIsSortTab (false);	// N
            setIsTranslationTab (false);	// N
            setI_WindowTab_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_I_WindowTab (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664614329789L;
    /** Last Updated Timestamp 2013-10-23 07:43:33.0 */
    public static final long updatedMS = 1382489013000L;
    /** AD_Table_ID=1000510 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("I_WindowTab");
        
    }
    ;
    
    /** TableName=I_WindowTab */
    public static final String Table_Name="I_WindowTab";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Column.
    @param AD_Column_ID Column in the table */
    public void setAD_Column_ID (int AD_Column_ID)
    {
        if (AD_Column_ID <= 0) set_Value ("AD_Column_ID", null);
        else
        set_Value ("AD_Column_ID", Integer.valueOf(AD_Column_ID));
        
    }
    
    /** Get Column.
    @return Column in the table */
    public int getAD_Column_ID() 
    {
        return get_ValueAsInt("AD_Column_ID");
        
    }
    
    /** Set Context Area.
    @param AD_CtxArea_ID Business Domain Area Terminology */
    public void setAD_CtxArea_ID (int AD_CtxArea_ID)
    {
        if (AD_CtxArea_ID <= 0) set_Value ("AD_CtxArea_ID", null);
        else
        set_Value ("AD_CtxArea_ID", Integer.valueOf(AD_CtxArea_ID));
        
    }
    
    /** Get Context Area.
    @return Business Domain Area Terminology */
    public int getAD_CtxArea_ID() 
    {
        return get_ValueAsInt("AD_CtxArea_ID");
        
    }
    
    /** Set Image.
    @param AD_Image_ID Image or Icon */
    public void setAD_Image_ID (int AD_Image_ID)
    {
        if (AD_Image_ID <= 0) set_Value ("AD_Image_ID", null);
        else
        set_Value ("AD_Image_ID", Integer.valueOf(AD_Image_ID));
        
    }
    
    /** Get Image.
    @return Image or Icon */
    public int getAD_Image_ID() 
    {
        return get_ValueAsInt("AD_Image_ID");
        
    }
    
    /** Set Process.
    @param AD_Process_ID Process or Report */
    public void setAD_Process_ID (int AD_Process_ID)
    {
        if (AD_Process_ID <= 0) set_Value ("AD_Process_ID", null);
        else
        set_Value ("AD_Process_ID", Integer.valueOf(AD_Process_ID));
        
    }
    
    /** Get Process.
    @return Process or Report */
    public int getAD_Process_ID() 
    {
        return get_ValueAsInt("AD_Process_ID");
        
    }
    
    /** Set Quick Info.
    @param AD_QuickInfo_ID Quick Info Widget */
    public void setAD_QuickInfo_ID (int AD_QuickInfo_ID)
    {
        if (AD_QuickInfo_ID <= 0) set_Value ("AD_QuickInfo_ID", null);
        else
        set_Value ("AD_QuickInfo_ID", Integer.valueOf(AD_QuickInfo_ID));
        
    }
    
    /** Get Quick Info.
    @return Quick Info Widget */
    public int getAD_QuickInfo_ID() 
    {
        return get_ValueAsInt("AD_QuickInfo_ID");
        
    }
    
    /** Set Tab.
    @param AD_Tab_ID Tab within a Window */
    public void setAD_Tab_ID (int AD_Tab_ID)
    {
        if (AD_Tab_ID <= 0) set_Value ("AD_Tab_ID", null);
        else
        set_Value ("AD_Tab_ID", Integer.valueOf(AD_Tab_ID));
        
    }
    
    /** Get Tab.
    @return Tab within a Window */
    public int getAD_Tab_ID() 
    {
        return get_ValueAsInt("AD_Tab_ID");
        
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
    
    /** Set Window.
    @param AD_Window_ID Data entry or display window */
    public void setAD_Window_ID (int AD_Window_ID)
    {
        if (AD_Window_ID <= 0) set_Value ("AD_Window_ID", null);
        else
        set_Value ("AD_Window_ID", Integer.valueOf(AD_Window_ID));
        
    }
    
    /** Get Window.
    @return Data entry or display window */
    public int getAD_Window_ID() 
    {
        return get_ValueAsInt("AD_Window_ID");
        
    }
    
    /** Set Commit Warning.
    @param CommitWarning Warning displayed when saving */
    public void setCommitWarning (String CommitWarning)
    {
        set_Value ("CommitWarning", CommitWarning);
        
    }
    
    /** Get Commit Warning.
    @return Warning displayed when saving */
    public String getCommitWarning() 
    {
        return (String)get_Value("CommitWarning");
        
    }
    
    /** Set Context Area Name.
    @param ContextAreaName Name of context area */
    public void setContextAreaName (String ContextAreaName)
    {
        set_Value ("ContextAreaName", ContextAreaName);
        
    }
    
    /** Get Context Area Name.
    @return Name of context area */
    public String getContextAreaName() 
    {
        return (String)get_Value("ContextAreaName");
        
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
    
    /** Set Display Logic.
    @param DisplayLogic If the Field is displayed, the result determines if the field is actually displayed */
    public void setDisplayLogic (String DisplayLogic)
    {
        set_Value ("DisplayLogic", DisplayLogic);
        
    }
    
    /** Get Display Logic.
    @return If the Field is displayed, the result determines if the field is actually displayed */
    public String getDisplayLogic() 
    {
        return (String)get_Value("DisplayLogic");
        
    }
    
    /** Set Has Tree.
    @param HasTree Window has Tree Graph */
    public void setHasTree (boolean HasTree)
    {
        set_Value ("HasTree", Boolean.valueOf(HasTree));
        
    }
    
    /** Get Has Tree.
    @return Window has Tree Graph */
    public boolean isHasTree() 
    {
        return get_ValueAsBoolean("HasTree");
        
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
    
    /** Set Image Name.
    @param ImageName Image name */
    public void setImageName (String ImageName)
    {
        set_Value ("ImageName", ImageName);
        
    }
    
    /** Get Image Name.
    @return Image name */
    public String getImageName() 
    {
        return (String)get_Value("ImageName");
        
    }
    
    /** Set Advanced Tab.
    @param IsAdvancedTab This Tab contains advanced Functionality */
    public void setIsAdvancedTab (boolean IsAdvancedTab)
    {
        set_Value ("IsAdvancedTab", Boolean.valueOf(IsAdvancedTab));
        
    }
    
    /** Get Advanced Tab.
    @return This Tab contains advanced Functionality */
    public boolean isAdvancedTab() 
    {
        return get_ValueAsBoolean("IsAdvancedTab");
        
    }
    
    /** Set Displayed.
    @param IsDisplayed Determines, if this field is displayed */
    public void setIsDisplayed (boolean IsDisplayed)
    {
        set_Value ("IsDisplayed", Boolean.valueOf(IsDisplayed));
        
    }
    
    /** Get Displayed.
    @return Determines, if this field is displayed */
    public boolean isDisplayed() 
    {
        return get_ValueAsBoolean("IsDisplayed");
        
    }
    
    /** Set Accounting Tab.
    @param IsInfoTab This Tab contains accounting information */
    public void setIsInfoTab (boolean IsInfoTab)
    {
        set_Value ("IsInfoTab", Boolean.valueOf(IsInfoTab));
        
    }
    
    /** Get Accounting Tab.
    @return This Tab contains accounting information */
    public boolean isInfoTab() 
    {
        return get_ValueAsBoolean("IsInfoTab");
        
    }
    
    /** Set Insert Record.
    @param IsInsertRecord The user can insert a new Record */
    public void setIsInsertRecord (boolean IsInsertRecord)
    {
        set_Value ("IsInsertRecord", Boolean.valueOf(IsInsertRecord));
        
    }
    
    /** Get Insert Record.
    @return The user can insert a new Record */
    public boolean isInsertRecord() 
    {
        return get_ValueAsBoolean("IsInsertRecord");
        
    }
    
    /** Set Read Only.
    @param IsReadOnly Field is read only */
    public void setIsReadOnly (boolean IsReadOnly)
    {
        set_Value ("IsReadOnly", Boolean.valueOf(IsReadOnly));
        
    }
    
    /** Get Read Only.
    @return Field is read only */
    public boolean isReadOnly() 
    {
        return get_ValueAsBoolean("IsReadOnly");
        
    }
    
    /** Set Single Row Layout.
    @param IsSingleRow Default for toggle between Single- and Multi-Row (Grid) Layouts */
    public void setIsSingleRow (boolean IsSingleRow)
    {
        set_Value ("IsSingleRow", Boolean.valueOf(IsSingleRow));
        
    }
    
    /** Get Single Row Layout.
    @return Default for toggle between Single- and Multi-Row (Grid) Layouts */
    public boolean isSingleRow() 
    {
        return get_ValueAsBoolean("IsSingleRow");
        
    }
    
    /** Set Order Tab.
    @param IsSortTab The Tab determines the Order */
    public void setIsSortTab (boolean IsSortTab)
    {
        set_Value ("IsSortTab", Boolean.valueOf(IsSortTab));
        
    }
    
    /** Get Order Tab.
    @return The Tab determines the Order */
    public boolean isSortTab() 
    {
        return get_ValueAsBoolean("IsSortTab");
        
    }
    
    /** Set TranslationTab.
    @param IsTranslationTab This Tab contains translation information */
    public void setIsTranslationTab (boolean IsTranslationTab)
    {
        set_Value ("IsTranslationTab", Boolean.valueOf(IsTranslationTab));
        
    }
    
    /** Get TranslationTab.
    @return This Tab contains translation information */
    public boolean isTranslationTab() 
    {
        return get_ValueAsBoolean("IsTranslationTab");
        
    }
    
    /** Set I_WindowTab_ID.
    @param I_WindowTab_ID I_WindowTab_ID */
    public void setI_WindowTab_ID (int I_WindowTab_ID)
    {
        if (I_WindowTab_ID < 1) throw new IllegalArgumentException ("I_WindowTab_ID is mandatory.");
        set_ValueNoCheck ("I_WindowTab_ID", Integer.valueOf(I_WindowTab_ID));
        
    }
    
    /** Get I_WindowTab_ID.
    @return I_WindowTab_ID */
    public int getI_WindowTab_ID() 
    {
        return get_ValueAsInt("I_WindowTab_ID");
        
    }
    
    /** Set Link Column Name.
    @param LinkColumnName Link Column Name */
    public void setLinkColumnName (String LinkColumnName)
    {
        set_Value ("LinkColumnName", LinkColumnName);
        
    }
    
    /** Get Link Column Name.
    @return Link Column Name */
    public String getLinkColumnName() 
    {
        return (String)get_Value("LinkColumnName");
        
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
    
    /** Set Process Key.
    @param ProcessValue Process search key */
    public void setProcessValue (String ProcessValue)
    {
        set_Value ("ProcessValue", ProcessValue);
        
    }
    
    /** Get Process Key.
    @return Process search key */
    public String getProcessValue() 
    {
        return (String)get_Value("ProcessValue");
        
    }
    
    /** Set Quick Info Name.
    @param QuickInfoName Quick Info Name */
    public void setQuickInfoName (String QuickInfoName)
    {
        set_Value ("QuickInfoName", QuickInfoName);
        
    }
    
    /** Get Quick Info Name.
    @return Quick Info Name */
    public String getQuickInfoName() 
    {
        return (String)get_Value("QuickInfoName");
        
    }
    
    /** Set Read Only Logic.
    @param ReadOnlyLogic Logic to determine if field is read only (applies only when field is read-write) */
    public void setReadOnlyLogic (String ReadOnlyLogic)
    {
        set_Value ("ReadOnlyLogic", ReadOnlyLogic);
        
    }
    
    /** Get Read Only Logic.
    @return Logic to determine if field is read only (applies only when field is read-write) */
    public String getReadOnlyLogic() 
    {
        return (String)get_Value("ReadOnlyLogic");
        
    }
    
    /** Set Referenced Tab.
    @param Referenced_Tab_ID Referenced Tab */
    public void setReferenced_Tab_ID (int Referenced_Tab_ID)
    {
        if (Referenced_Tab_ID <= 0) set_Value ("Referenced_Tab_ID", null);
        else
        set_Value ("Referenced_Tab_ID", Integer.valueOf(Referenced_Tab_ID));
        
    }
    
    /** Get Referenced Tab.
    @return Referenced Tab */
    public int getReferenced_Tab_ID() 
    {
        return get_ValueAsInt("Referenced_Tab_ID");
        
    }
    
    /** Set Reference Tab Name.
    @param ReferenceTabName Reference Tab Name */
    public void setReferenceTabName (String ReferenceTabName)
    {
        set_Value ("ReferenceTabName", ReferenceTabName);
        
    }
    
    /** Get Reference Tab Name.
    @return Reference Tab Name */
    public String getReferenceTabName() 
    {
        return (String)get_Value("ReferenceTabName");
        
    }
    
    /** Set Sequence.
    @param SeqNo Method of ordering elements;
     lowest number comes first */
    public void setSeqNo (int SeqNo)
    {
        set_Value ("SeqNo", Integer.valueOf(SeqNo));
        
    }
    
    /** Get Sequence.
    @return Method of ordering elements;
     lowest number comes first */
    public int getSeqNo() 
    {
        return get_ValueAsInt("SeqNo");
        
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
    
    /** Set Tab Level.
    @param TabLevel Hierarchical Tab Level (0 = top) */
    public void setTabLevel (int TabLevel)
    {
        set_Value ("TabLevel", Integer.valueOf(TabLevel));
        
    }
    
    /** Get Tab Level.
    @return Hierarchical Tab Level (0 = top) */
    public int getTabLevel() 
    {
        return get_ValueAsInt("TabLevel");
        
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
    
    /** Set Window Name.
    @param WindowName Name of window */
    public void setWindowName (String WindowName)
    {
        set_Value ("WindowName", WindowName);
        
    }
    
    /** Get Window Name.
    @return Name of window */
    public String getWindowName() 
    {
        return (String)get_Value("WindowName");
        
    }
    
    
}
