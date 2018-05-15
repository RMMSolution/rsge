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
/** Generated Model for I_ReportProcess
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_I_ReportProcess extends PO
{
    /** Standard Constructor
    @param ctx context
    @param I_ReportProcess_ID id
    @param trx transaction
    */
    public X_I_ReportProcess (Ctx ctx, int I_ReportProcess_ID, Trx trx)
    {
        super (ctx, I_ReportProcess_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (I_ReportProcess_ID == 0)
        {
            setI_ReportProcess_ID (0);
            setIsBetaFunctionality (false);	// N
            setIsCentrallyMaintained (false);	// N
            setIsDashboard (false);	// N
            setIsDirectPrint (false);	// N
            setIsEncrypted (false);	// N
            setIsMandatory (false);	// N
            setIsRange (false);	// N
            setIsReport (false);	// N
            setProcessed (false);	// N
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_I_ReportProcess (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664504743789L;
    /** Last Updated Timestamp 2013-10-22 01:17:07.0 */
    public static final long updatedMS = 1382379427000L;
    /** AD_Table_ID=1000504 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("I_ReportProcess");
        
    }
    ;
    
    /** TableName=I_ReportProcess */
    public static final String Table_Name="I_ReportProcess";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Organization = 1 */
    public static final String ACCESSLEVEL_Organization = X_Ref_AD_Table_Access_Levels.ORGANIZATION.getValue();
    /** Tenant only = 2 */
    public static final String ACCESSLEVEL_TenantOnly = X_Ref_AD_Table_Access_Levels.TENANT_ONLY.getValue();
    /** Tenant+Organization = 3 */
    public static final String ACCESSLEVEL_TenantPlusOrganization = X_Ref_AD_Table_Access_Levels.TENANT_PLUS_ORGANIZATION.getValue();
    /** System only = 4 */
    public static final String ACCESSLEVEL_SystemOnly = X_Ref_AD_Table_Access_Levels.SYSTEM_ONLY.getValue();
    /** System+Tenant = 6 */
    public static final String ACCESSLEVEL_SystemPlusTenant = X_Ref_AD_Table_Access_Levels.SYSTEM_PLUS_TENANT.getValue();
    /** All = 7 */
    public static final String ACCESSLEVEL_All = X_Ref_AD_Table_Access_Levels.ALL.getValue();
    /** Set Data Access Level.
    @param AccessLevel Access Level required */
    public void setAccessLevel (String AccessLevel)
    {
        if (!X_Ref_AD_Table_Access_Levels.isValid(AccessLevel))
        throw new IllegalArgumentException ("AccessLevel Invalid value - " + AccessLevel + " - Reference_ID=5 - 1 - 2 - 3 - 4 - 6 - 7");
        set_Value ("AccessLevel", AccessLevel);
        
    }
    
    /** Get Data Access Level.
    @return Access Level required */
    public String getAccessLevel() 
    {
        return (String)get_Value("AccessLevel");
        
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
    
    /** Set Print Format.
    @param AD_PrintFormat_ID Data Print Format */
    public void setAD_PrintFormat_ID (int AD_PrintFormat_ID)
    {
        if (AD_PrintFormat_ID <= 0) set_Value ("AD_PrintFormat_ID", null);
        else
        set_Value ("AD_PrintFormat_ID", Integer.valueOf(AD_PrintFormat_ID));
        
    }
    
    /** Get Print Format.
    @return Data Print Format */
    public int getAD_PrintFormat_ID() 
    {
        return get_ValueAsInt("AD_PrintFormat_ID");
        
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
    
    /** Set Process Parameter.
    @param AD_Process_Para_ID Process Parameter */
    public void setAD_Process_Para_ID (int AD_Process_Para_ID)
    {
        if (AD_Process_Para_ID <= 0) set_Value ("AD_Process_Para_ID", null);
        else
        set_Value ("AD_Process_Para_ID", Integer.valueOf(AD_Process_Para_ID));
        
    }
    
    /** Get Process Parameter.
    @return Process Parameter */
    public int getAD_Process_Para_ID() 
    {
        return get_ValueAsInt("AD_Process_Para_ID");
        
    }
    
    /** Get Record ID/ColumnName
    @return ID/ColumnName pair */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_Process_Para_ID()));
        
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
    
    /** Set Reference Key.
    @param AD_Reference_Value_ID Required to specify, if data type is Table or List */
    public void setAD_Reference_Value_ID (int AD_Reference_Value_ID)
    {
        if (AD_Reference_Value_ID <= 0) set_Value ("AD_Reference_Value_ID", null);
        else
        set_Value ("AD_Reference_Value_ID", Integer.valueOf(AD_Reference_Value_ID));
        
    }
    
    /** Get Reference Key.
    @return Required to specify, if data type is Table or List */
    public int getAD_Reference_Value_ID() 
    {
        return get_ValueAsInt("AD_Reference_Value_ID");
        
    }
    
    /** Set Report View.
    @param AD_ReportView_ID View used to generate this report */
    public void setAD_ReportView_ID (int AD_ReportView_ID)
    {
        if (AD_ReportView_ID <= 0) set_Value ("AD_ReportView_ID", null);
        else
        set_Value ("AD_ReportView_ID", Integer.valueOf(AD_ReportView_ID));
        
    }
    
    /** Get Report View.
    @return View used to generate this report */
    public int getAD_ReportView_ID() 
    {
        return get_ValueAsInt("AD_ReportView_ID");
        
    }
    
    /** Set Dynamic Validation.
    @param AD_Val_Rule_ID Dynamic Validation Rule */
    public void setAD_Val_Rule_ID (int AD_Val_Rule_ID)
    {
        if (AD_Val_Rule_ID <= 0) set_Value ("AD_Val_Rule_ID", null);
        else
        set_Value ("AD_Val_Rule_ID", Integer.valueOf(AD_Val_Rule_ID));
        
    }
    
    /** Get Dynamic Validation.
    @return Dynamic Validation Rule */
    public int getAD_Val_Rule_ID() 
    {
        return get_ValueAsInt("AD_Val_Rule_ID");
        
    }
    
    /** Set Workflow.
    @param AD_Workflow_ID Workflow or combination of tasks */
    public void setAD_Workflow_ID (int AD_Workflow_ID)
    {
        if (AD_Workflow_ID <= 0) set_Value ("AD_Workflow_ID", null);
        else
        set_Value ("AD_Workflow_ID", Integer.valueOf(AD_Workflow_ID));
        
    }
    
    /** Get Workflow.
    @return Workflow or combination of tasks */
    public int getAD_Workflow_ID() 
    {
        return get_ValueAsInt("AD_Workflow_ID");
        
    }
    
    /** Set Classname.
    @param Classname Java Classname */
    public void setClassname (String Classname)
    {
        set_Value ("Classname", Classname);
        
    }
    
    /** Get Classname.
    @return Java Classname */
    public String getClassname() 
    {
        return (String)get_Value("Classname");
        
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
    
    /** Set Default Logic.
    @param DefaultValue Default value hierarchy, separated by;
     */
    public void setDefaultValue (String DefaultValue)
    {
        set_Value ("DefaultValue", DefaultValue);
        
    }
    
    /** Get Default Logic.
    @return Default value hierarchy, separated by;
     */
    public String getDefaultValue() 
    {
        return (String)get_Value("DefaultValue");
        
    }
    
    /** Set Default Logic 2.
    @param DefaultValue2 Default value hierarchy, separated by;
     */
    public void setDefaultValue2 (String DefaultValue2)
    {
        set_Value ("DefaultValue2", DefaultValue2);
        
    }
    
    /** Get Default Logic 2.
    @return Default value hierarchy, separated by;
     */
    public String getDefaultValue2() 
    {
        return (String)get_Value("DefaultValue2");
        
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
    
    /** Set Element Name.
    @param ElementName Name of the Element */
    public void setElementName (String ElementName)
    {
        set_ValueNoCheck ("ElementName", ElementName);
        
    }
    
    /** Get Element Name.
    @return Name of the Element */
    public String getElementName() 
    {
        return (String)get_Value("ElementName");
        
    }
    
    /** Set Length.
    @param FieldLength Length of the column in the database */
    public void setFieldLength (int FieldLength)
    {
        set_Value ("FieldLength", Integer.valueOf(FieldLength));
        
    }
    
    /** Get Length.
    @return Length of the column in the database */
    public int getFieldLength() 
    {
        return get_ValueAsInt("FieldLength");
        
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
    
    /** Set I_ReportProcess_ID.
    @param I_ReportProcess_ID I_ReportProcess_ID */
    public void setI_ReportProcess_ID (int I_ReportProcess_ID)
    {
        if (I_ReportProcess_ID < 1) throw new IllegalArgumentException ("I_ReportProcess_ID is mandatory.");
        set_ValueNoCheck ("I_ReportProcess_ID", Integer.valueOf(I_ReportProcess_ID));
        
    }
    
    /** Get I_ReportProcess_ID.
    @return I_ReportProcess_ID */
    public int getI_ReportProcess_ID() 
    {
        return get_ValueAsInt("I_ReportProcess_ID");
        
    }
    
    /** Set Beta Functionality.
    @param IsBetaFunctionality This functionality is considered Beta */
    public void setIsBetaFunctionality (boolean IsBetaFunctionality)
    {
        set_Value ("IsBetaFunctionality", Boolean.valueOf(IsBetaFunctionality));
        
    }
    
    /** Get Beta Functionality.
    @return This functionality is considered Beta */
    public boolean isBetaFunctionality() 
    {
        return get_ValueAsBoolean("IsBetaFunctionality");
        
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
    
    /** Set Dashboard.
    @param IsDashboard Indicates if this report should be rendered as a dashboard */
    public void setIsDashboard (boolean IsDashboard)
    {
        set_Value ("IsDashboard", Boolean.valueOf(IsDashboard));
        
    }
    
    /** Get Dashboard.
    @return Indicates if this report should be rendered as a dashboard */
    public boolean isDashboard() 
    {
        return get_ValueAsBoolean("IsDashboard");
        
    }
    
    /** Set Direct print.
    @param IsDirectPrint Print without dialog */
    public void setIsDirectPrint (boolean IsDirectPrint)
    {
        set_Value ("IsDirectPrint", Boolean.valueOf(IsDirectPrint));
        
    }
    
    /** Get Direct print.
    @return Print without dialog */
    public boolean isDirectPrint() 
    {
        return get_ValueAsBoolean("IsDirectPrint");
        
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
    
    /** Set Mandatory.
    @param IsMandatory Data is required in this column */
    public void setIsMandatory (boolean IsMandatory)
    {
        set_Value ("IsMandatory", Boolean.valueOf(IsMandatory));
        
    }
    
    /** Get Mandatory.
    @return Data is required in this column */
    public boolean isMandatory() 
    {
        return get_ValueAsBoolean("IsMandatory");
        
    }
    
    /** Set Range.
    @param IsRange The parameter is a range of values */
    public void setIsRange (boolean IsRange)
    {
        set_Value ("IsRange", Boolean.valueOf(IsRange));
        
    }
    
    /** Get Range.
    @return The parameter is a range of values */
    public boolean isRange() 
    {
        return get_ValueAsBoolean("IsRange");
        
    }
    
    /** Set Report.
    @param IsReport Indicates a Report record */
    public void setIsReport (boolean IsReport)
    {
        set_Value ("IsReport", Boolean.valueOf(IsReport));
        
    }
    
    /** Get Report.
    @return Indicates a Report record */
    public boolean isReport() 
    {
        return get_ValueAsBoolean("IsReport");
        
    }
    
    /** Set Sequence.
    @param LoadSeq Sequence */
    public void setLoadSeq (int LoadSeq)
    {
        set_Value ("LoadSeq", Integer.valueOf(LoadSeq));
        
    }
    
    /** Get Sequence.
    @return Sequence */
    public int getLoadSeq() 
    {
        return get_ValueAsInt("LoadSeq");
        
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
    
    /** Set Parameter Description.
    @param ParameterDescription Description of parameter */
    public void setParameterDescription (String ParameterDescription)
    {
        set_Value ("ParameterDescription", ParameterDescription);
        
    }
    
    /** Get Parameter Description.
    @return Description of parameter */
    public String getParameterDescription() 
    {
        return (String)get_Value("ParameterDescription");
        
    }
    
    /** Set Parameter Comment.
    @param ParameterHelp Comment of parameter */
    public void setParameterHelp (String ParameterHelp)
    {
        set_Value ("ParameterHelp", ParameterHelp);
        
    }
    
    /** Get Parameter Comment.
    @return Comment of parameter */
    public String getParameterHelp() 
    {
        return (String)get_Value("ParameterHelp");
        
    }
    
    /** Set Parameter Name.
    @param ParameterName Parameter Name */
    public void setParameterName (String ParameterName)
    {
        set_Value ("ParameterName", ParameterName);
        
    }
    
    /** Get Parameter Name.
    @return Parameter Name */
    public String getParameterName() 
    {
        return (String)get_Value("ParameterName");
        
    }
    
    /** Set Print Format Name.
    @param PrintFormatName Name of print format */
    public void setPrintFormatName (String PrintFormatName)
    {
        set_Value ("PrintFormatName", PrintFormatName);
        
    }
    
    /** Get Print Format Name.
    @return Name of print format */
    public String getPrintFormatName() 
    {
        return (String)get_Value("PrintFormatName");
        
    }
    
    /** Set Procedure.
    @param ProcedureName Name of the Database Procedure */
    public void setProcedureName (String ProcedureName)
    {
        set_Value ("ProcedureName", ProcedureName);
        
    }
    
    /** Get Procedure.
    @return Name of the Database Procedure */
    public String getProcedureName() 
    {
        return (String)get_Value("ProcedureName");
        
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
    
    /** Set Reference Key Name.
    @param RefKeyName Reference key name */
    public void setRefKeyName (String RefKeyName)
    {
        set_Value ("RefKeyName", RefKeyName);
        
    }
    
    /** Get Reference Key Name.
    @return Reference key name */
    public String getRefKeyName() 
    {
        return (String)get_Value("RefKeyName");
        
    }
    
    /** Set Report View Name.
    @param ReportViewName Name of report view */
    public void setReportViewName (String ReportViewName)
    {
        set_Value ("ReportViewName", ReportViewName);
        
    }
    
    /** Get Report View Name.
    @return Name of report view */
    public String getReportViewName() 
    {
        return (String)get_Value("ReportViewName");
        
    }
    
    /** Set Validation Name.
    @param ValidationName Validation name */
    public void setValidationName (String ValidationName)
    {
        set_Value ("ValidationName", ValidationName);
        
    }
    
    /** Get Validation Name.
    @return Validation name */
    public String getValidationName() 
    {
        return (String)get_Value("ValidationName");
        
    }
    
    /** Set Search Key.
    @param Value Search key for the record in the format required - must be unique */
    public void setValue (String Value)
    {
        set_Value ("Value", Value);
        
    }
    
    /** Get Search Key.
    @return Search key for the record in the format required - must be unique */
    public String getValue() 
    {
        return (String)get_Value("Value");
        
    }
    
    /** Set Max. Value.
    @param ValueMax Maximum Value for a field */
    public void setValueMax (String ValueMax)
    {
        set_Value ("ValueMax", ValueMax);
        
    }
    
    /** Get Max. Value.
    @return Maximum Value for a field */
    public String getValueMax() 
    {
        return (String)get_Value("ValueMax");
        
    }
    
    /** Set Min. Value.
    @param ValueMin Minimum Value for a field */
    public void setValueMin (String ValueMin)
    {
        set_Value ("ValueMin", ValueMin);
        
    }
    
    /** Get Min. Value.
    @return Minimum Value for a field */
    public String getValueMin() 
    {
        return (String)get_Value("ValueMin");
        
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
    
    /** Set Workflow Name.
    @param WorkflowName Name of workflow */
    public void setWorkflowName (String WorkflowName)
    {
        set_Value ("WorkflowName", WorkflowName);
        
    }
    
    /** Get Workflow Name.
    @return Name of workflow */
    public String getWorkflowName() 
    {
        return (String)get_Value("WorkflowName");
        
    }
    
    /** Set Workflow Key.
    @param WorkflowValue Key of the Workflow to start */
    public void setWorkflowValue (String WorkflowValue)
    {
        set_Value ("WorkflowValue", WorkflowValue);
        
    }
    
    /** Get Workflow Key.
    @return Key of the Workflow to start */
    public String getWorkflowValue() 
    {
        return (String)get_Value("WorkflowValue");
        
    }
    
    
}
