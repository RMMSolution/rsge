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
/** Generated Model for I_Table
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_I_Table extends PO
{
    /** Standard Constructor
    @param ctx context
    @param I_Table_ID id
    @param trx transaction
    */
    public X_I_Table (Ctx ctx, int I_Table_ID, Trx trx)
    {
        super (ctx, I_Table_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (I_Table_ID == 0)
        {
            setI_IsImported (false);	// N
            setIsDeleteable (true);	// Y
            setIsHighVolume (true);	// Y
            setIsOtherExtension (false);	// N
            setIsReportingTable (false);	// N
            setIsView (false);	// N
            setI_Table_ID (0);
            setProcessed (false);	// N
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_I_Table (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664363173789L;
    /** Last Updated Timestamp 2013-10-20 09:57:37.0 */
    public static final long updatedMS = 1382237857000L;
    /** AD_Table_ID=1000506 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("I_Table");
        
    }
    ;
    
    /** TableName=I_Table */
    public static final String Table_Name="I_Table";
    
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
    
    /** All = A */
    public static final String CHANGELOGLEVEL_All = X_Ref_Change_Log_Level.ALL.getValue();
    /** None = N */
    public static final String CHANGELOGLEVEL_None = X_Ref_Change_Log_Level.NONE.getValue();
    /** Updates and deletes = U */
    public static final String CHANGELOGLEVEL_UpdatesAndDeletes = X_Ref_Change_Log_Level.UPDATES_AND_DELETES.getValue();
    /** Set Set Change Log.
    @param ChangeLogLevel Set what changes, if any, to log */
    public void setChangeLogLevel (String ChangeLogLevel)
    {
        if (!X_Ref_Change_Log_Level.isValid(ChangeLogLevel))
        throw new IllegalArgumentException ("ChangeLogLevel Invalid value - " + ChangeLogLevel + " - Reference_ID=534 - A - N - U");
        set_Value ("ChangeLogLevel", ChangeLogLevel);
        
    }
    
    /** Get Set Change Log.
    @return Set what changes, if any, to log */
    public String getChangeLogLevel() 
    {
        return (String)get_Value("ChangeLogLevel");
        
    }
    
    /** Set Table Description.
    @param DescriptionOfTable Description of table */
    public void setDescriptionOfTable (String DescriptionOfTable)
    {
        set_Value ("DescriptionOfTable", DescriptionOfTable);
        
    }
    
    /** Get Table Description.
    @return Description of table */
    public String getDescriptionOfTable() 
    {
        return (String)get_Value("DescriptionOfTable");
        
    }
    
    /** Set Table Comment.
    @param HelpOfTable Comment of table */
    public void setHelpOfTable (String HelpOfTable)
    {
        set_Value ("HelpOfTable", HelpOfTable);
        
    }
    
    /** Get Table Comment.
    @return Comment of table */
    public String getHelpOfTable() 
    {
        return (String)get_Value("HelpOfTable");
        
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
    
    /** Set Records deletable.
    @param IsDeleteable Indicates if records can be deleted from the database */
    public void setIsDeleteable (boolean IsDeleteable)
    {
        set_Value ("IsDeleteable", Boolean.valueOf(IsDeleteable));
        
    }
    
    /** Get Records deletable.
    @return Indicates if records can be deleted from the database */
    public boolean isDeleteable() 
    {
        return get_ValueAsBoolean("IsDeleteable");
        
    }
    
    /** Set High Volume.
    @param IsHighVolume Use Search instead of Pick list */
    public void setIsHighVolume (boolean IsHighVolume)
    {
        set_Value ("IsHighVolume", Boolean.valueOf(IsHighVolume));
        
    }
    
    /** Get High Volume.
    @return Use Search instead of Pick list */
    public boolean isHighVolume() 
    {
        return get_ValueAsBoolean("IsHighVolume");
        
    }
    
    /** Set Other Extension.
    @param IsOtherExtension This table is property of other extension */
    public void setIsOtherExtension (boolean IsOtherExtension)
    {
        set_Value ("IsOtherExtension", Boolean.valueOf(IsOtherExtension));
        
    }
    
    /** Get Other Extension.
    @return This table is property of other extension */
    public boolean isOtherExtension() 
    {
        return get_ValueAsBoolean("IsOtherExtension");
        
    }
    
    /** Set Reporting Table.
    @param IsReportingTable This is a reporting table */
    public void setIsReportingTable (boolean IsReportingTable)
    {
        set_Value ("IsReportingTable", Boolean.valueOf(IsReportingTable));
        
    }
    
    /** Get Reporting Table.
    @return This is a reporting table */
    public boolean isReportingTable() 
    {
        return get_ValueAsBoolean("IsReportingTable");
        
    }
    
    /** Set View.
    @param IsView This is a view */
    public void setIsView (boolean IsView)
    {
        set_Value ("IsView", Boolean.valueOf(IsView));
        
    }
    
    /** Get View.
    @return This is a view */
    public boolean isView() 
    {
        return get_ValueAsBoolean("IsView");
        
    }
    
    /** Set I_Table_ID.
    @param I_Table_ID I_Table_ID */
    public void setI_Table_ID (int I_Table_ID)
    {
        if (I_Table_ID < 1) throw new IllegalArgumentException ("I_Table_ID is mandatory.");
        set_ValueNoCheck ("I_Table_ID", Integer.valueOf(I_Table_ID));
        
    }
    
    /** Get I_Table_ID.
    @return I_Table_ID */
    public int getI_Table_ID() 
    {
        return get_ValueAsInt("I_Table_ID");
        
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
    
    /** Set Table Name.
    @param NameOfTable Name of table */
    public void setNameOfTable (String NameOfTable)
    {
        set_Value ("NameOfTable", NameOfTable);
        
    }
    
    /** Get Table Name.
    @return Name of table */
    public String getNameOfTable() 
    {
        return (String)get_Value("NameOfTable");
        
    }
    
    /** Set PO Window.
    @param PO_Window_ID Purchase Order Window */
    public void setPO_Window_ID (int PO_Window_ID)
    {
        if (PO_Window_ID <= 0) set_Value ("PO_Window_ID", null);
        else
        set_Value ("PO_Window_ID", Integer.valueOf(PO_Window_ID));
        
    }
    
    /** Get PO Window.
    @return Purchase Order Window */
    public int getPO_Window_ID() 
    {
        return get_ValueAsInt("PO_Window_ID");
        
    }
    
    /** Set PO Window Name.
    @param POWindowName Name of PO Window */
    public void setPOWindowName (String POWindowName)
    {
        set_Value ("POWindowName", POWindowName);
        
    }
    
    /** Get PO Window Name.
    @return Name of PO Window */
    public String getPOWindowName() 
    {
        return (String)get_Value("POWindowName");
        
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
    
    /** Set Referenced Table.
    @param Referenced_Table_ID Referenced Table */
    public void setReferenced_Table_ID (int Referenced_Table_ID)
    {
        if (Referenced_Table_ID <= 0) set_Value ("Referenced_Table_ID", null);
        else
        set_Value ("Referenced_Table_ID", Integer.valueOf(Referenced_Table_ID));
        
    }
    
    /** Get Referenced Table.
    @return Referenced Table */
    public int getReferenced_Table_ID() 
    {
        return get_ValueAsInt("Referenced_Table_ID");
        
    }
    
    /** Set Referenced Table Name.
    @param ReferencedTableName Name of referenced table */
    public void setReferencedTableName (String ReferencedTableName)
    {
        set_Value ("ReferencedTableName", ReferencedTableName);
        
    }
    
    /** Get Referenced Table Name.
    @return Name of referenced table */
    public String getReferencedTableName() 
    {
        return (String)get_Value("ReferencedTableName");
        
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
    
    /** Mandatory Organization = M */
    public static final String TABLETRXTYPE_MandatoryOrganization = X_Ref_AD_Table_Transaction.MANDATORY_ORGANIZATION.getValue();
    /** No Organization = N */
    public static final String TABLETRXTYPE_NoOrganization = X_Ref_AD_Table_Transaction.NO_ORGANIZATION.getValue();
    /** Optional Organization = O */
    public static final String TABLETRXTYPE_OptionalOrganization = X_Ref_AD_Table_Transaction.OPTIONAL_ORGANIZATION.getValue();
    /** Set Transaction Type.
    @param TableTrxType Table Transaction Type */
    public void setTableTrxType (String TableTrxType)
    {
        if (!X_Ref_AD_Table_Transaction.isValid(TableTrxType))
        throw new IllegalArgumentException ("TableTrxType Invalid value - " + TableTrxType + " - Reference_ID=493 - M - N - O");
        set_Value ("TableTrxType", TableTrxType);
        
    }
    
    /** Get Transaction Type.
    @return Table Transaction Type */
    public String getTableTrxType() 
    {
        return (String)get_Value("TableTrxType");
        
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
