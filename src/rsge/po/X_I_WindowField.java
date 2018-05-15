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
/** Generated Model for I_WindowField
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_I_WindowField extends PO
{
    /** Standard Constructor
    @param ctx context
    @param I_WindowField_ID id
    @param trx transaction
    */
    public X_I_WindowField (Ctx ctx, int I_WindowField_ID, Trx trx)
    {
        super (ctx, I_WindowField_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (I_WindowField_ID == 0)
        {
            setIsCentrallyMaintained (false);	// N
            setIsCopy (false);	// N
            setIsDefaultFocus (false);	// N
            setIsDisplayed (false);	// N
            setIsEncrypted (false);	// N
            setIsFieldOnly (false);	// N
            setIsHeading (false);	// N
            setIsReadOnly (false);	// N
            setIsSameLine (false);	// N
            setI_WindowField_ID (0);
            setProcessed (false);	// N
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_I_WindowField (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664703350789L;
    /** Last Updated Timestamp 2013-10-24 08:27:14.0 */
    public static final long updatedMS = 1382578034000L;
    /** AD_Table_ID=1000509 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("I_WindowField");
        
    }
    ;
    
    /** TableName=I_WindowField */
    public static final String Table_Name="I_WindowField";
    
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
    
    /** Set Field.
    @param AD_Field_ID Field on a tab in a window */
    public void setAD_Field_ID (int AD_Field_ID)
    {
        if (AD_Field_ID <= 0) set_Value ("AD_Field_ID", null);
        else
        set_Value ("AD_Field_ID", Integer.valueOf(AD_Field_ID));
        
    }
    
    /** Get Field.
    @return Field on a tab in a window */
    public int getAD_Field_ID() 
    {
        return get_ValueAsInt("AD_Field_ID");
        
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
    
    /** Set Display Length.
    @param DisplayLength Length of the display in characters */
    public void setDisplayLength (int DisplayLength)
    {
        set_Value ("DisplayLength", Integer.valueOf(DisplayLength));
        
    }
    
    /** Get Display Length.
    @return Length of the display in characters */
    public int getDisplayLength() 
    {
        return get_ValueAsInt("DisplayLength");
        
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
    
    /** Set Field Group.
    @param FieldGroup Field Group */
    public void setFieldGroup (String FieldGroup)
    {
        set_Value ("FieldGroup", FieldGroup);
        
    }
    
    /** Get Field Group.
    @return Field Group */
    public String getFieldGroup() 
    {
        return (String)get_Value("FieldGroup");
        
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
    
    /** Set Centrally maintained.
    @param IsCentrallyMaintained Information maintained in System Element table */
    public void setIsCentrallyMaintained (boolean IsCentrallyMaintained)
    {
        set_Value ("IsCentrallyMaintained", Boolean.valueOf(IsCentrallyMaintained));
        
    }
    
    /** Get Centrally maintained.
    @return Information maintained in System Element table */
    public boolean isCentrallyMaintained() 
    {
        return get_ValueAsBoolean("IsCentrallyMaintained");
        
    }
    
    /** Set Copy.
    @param IsCopy Copy contents of this field using the Copy Record function. */
    public void setIsCopy (boolean IsCopy)
    {
        set_Value ("IsCopy", Boolean.valueOf(IsCopy));
        
    }
    
    /** Get Copy.
    @return Copy contents of this field using the Copy Record function. */
    public boolean isCopy() 
    {
        return get_ValueAsBoolean("IsCopy");
        
    }
    
    /** Set Default Focus.
    @param IsDefaultFocus Field received the default focus */
    public void setIsDefaultFocus (boolean IsDefaultFocus)
    {
        set_Value ("IsDefaultFocus", Boolean.valueOf(IsDefaultFocus));
        
    }
    
    /** Get Default Focus.
    @return Field received the default focus */
    public boolean isDefaultFocus() 
    {
        return get_ValueAsBoolean("IsDefaultFocus");
        
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
    
    /** Set Encrypted.
    @param IsEncrypted Display or Storage is encrypted */
    public void setIsEncrypted (boolean IsEncrypted)
    {
        set_Value ("IsEncrypted", Boolean.valueOf(IsEncrypted));
        
    }
    
    /** Get Encrypted.
    @return Display or Storage is encrypted */
    public boolean isEncrypted() 
    {
        return get_ValueAsBoolean("IsEncrypted");
        
    }
    
    /** Set Field Only.
    @param IsFieldOnly Label is not displayed */
    public void setIsFieldOnly (boolean IsFieldOnly)
    {
        set_Value ("IsFieldOnly", Boolean.valueOf(IsFieldOnly));
        
    }
    
    /** Get Field Only.
    @return Label is not displayed */
    public boolean isFieldOnly() 
    {
        return get_ValueAsBoolean("IsFieldOnly");
        
    }
    
    /** Set Heading only.
    @param IsHeading Field without Column - Only label is displayed */
    public void setIsHeading (boolean IsHeading)
    {
        set_Value ("IsHeading", Boolean.valueOf(IsHeading));
        
    }
    
    /** Get Heading only.
    @return Field without Column - Only label is displayed */
    public boolean isHeading() 
    {
        return get_ValueAsBoolean("IsHeading");
        
    }
    
    /** Set Mandatory UI.
    @param IsMandatoryUI Data entry is required for data entry in the field */
    public void setIsMandatoryUI (boolean IsMandatoryUI)
    {
        set_Value ("IsMandatoryUI", Boolean.valueOf(IsMandatoryUI));
        
    }
    
    /** Get Mandatory UI.
    @return Data entry is required for data entry in the field */
    public boolean isMandatoryUI() 
    {
        return get_ValueAsBoolean("IsMandatoryUI");
        
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
    
    /** Set Same Line.
    @param IsSameLine Displayed on same line as previous field */
    public void setIsSameLine (boolean IsSameLine)
    {
        set_Value ("IsSameLine", Boolean.valueOf(IsSameLine));
        
    }
    
    /** Get Same Line.
    @return Displayed on same line as previous field */
    public boolean isSameLine() 
    {
        return get_ValueAsBoolean("IsSameLine");
        
    }
    
    /** Set I_WindowField_ID.
    @param I_WindowField_ID I_WindowField_ID */
    public void setI_WindowField_ID (int I_WindowField_ID)
    {
        if (I_WindowField_ID < 1) throw new IllegalArgumentException ("I_WindowField_ID is mandatory.");
        set_ValueNoCheck ("I_WindowField_ID", Integer.valueOf(I_WindowField_ID));
        
    }
    
    /** Get I_WindowField_ID.
    @return I_WindowField_ID */
    public int getI_WindowField_ID() 
    {
        return get_ValueAsInt("I_WindowField_ID");
        
    }
    
    /** Set Multi-Row Sequence.
    @param MRSeqNo Method of ordering fields in Multi-Row (Grid) View;
     lowest number comes first */
    public void setMRSeqNo (int MRSeqNo)
    {
        set_Value ("MRSeqNo", Integer.valueOf(MRSeqNo));
        
    }
    
    /** Get Multi-Row Sequence.
    @return Method of ordering fields in Multi-Row (Grid) View;
     lowest number comes first */
    public int getMRSeqNo() 
    {
        return get_ValueAsInt("MRSeqNo");
        
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
    
    /** Obscure Digits but last 4 = 904 */
    public static final String OBSCURETYPE_ObscureDigitsButLast4 = X_Ref_AD_Field_ObscureType.OBSCURE_DIGITS_BUT_LAST4.getValue();
    /** Obscure Digits but first/last 4 = 944 */
    public static final String OBSCURETYPE_ObscureDigitsButFirstLast4 = X_Ref_AD_Field_ObscureType.OBSCURE_DIGITS_BUT_FIRST_LAST4.getValue();
    /** Obscure AlphaNumeric but last 4 = A04 */
    public static final String OBSCURETYPE_ObscureAlphaNumericButLast4 = X_Ref_AD_Field_ObscureType.OBSCURE_ALPHA_NUMERIC_BUT_LAST4.getValue();
    /** Obscure AlphaNumeric but first/last 4 = A44 */
    public static final String OBSCURETYPE_ObscureAlphaNumericButFirstLast4 = X_Ref_AD_Field_ObscureType.OBSCURE_ALPHA_NUMERIC_BUT_FIRST_LAST4.getValue();
    /** Set Obscure.
    @param ObscureType Type of obscuring the data (limiting the display) */
    public void setObscureType (String ObscureType)
    {
        if (!X_Ref_AD_Field_ObscureType.isValid(ObscureType))
        throw new IllegalArgumentException ("ObscureType Invalid value - " + ObscureType + " - Reference_ID=291 - 904 - 944 - A04 - A44");
        set_Value ("ObscureType", ObscureType);
        
    }
    
    /** Get Obscure.
    @return Type of obscuring the data (limiting the display) */
    public String getObscureType() 
    {
        return (String)get_Value("ObscureType");
        
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
    
    /** Set Reference Name.
    @param ReferenceName Reference Name */
    public void setReferenceName (String ReferenceName)
    {
        set_Value ("ReferenceName", ReferenceName);
        
    }
    
    /** Get Reference Name.
    @return Reference Name */
    public String getReferenceName() 
    {
        return (String)get_Value("ReferenceName");
        
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
    
    /** Set Record Sort No.
    @param SortNo Determines in what order the records are displayed */
    public void setSortNo (int SortNo)
    {
        set_Value ("SortNo", Integer.valueOf(SortNo));
        
    }
    
    /** Get Record Sort No.
    @return Determines in what order the records are displayed */
    public int getSortNo() 
    {
        return get_ValueAsInt("SortNo");
        
    }
    
    /** Set Tab Name.
    @param TabName Tab name */
    public void setTabName (String TabName)
    {
        set_Value ("TabName", TabName);
        
    }
    
    /** Get Tab Name.
    @return Tab name */
    public String getTabName() 
    {
        return (String)get_Value("TabName");
        
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
