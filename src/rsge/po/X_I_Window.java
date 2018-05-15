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
/** Generated Model for I_Window
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_I_Window extends PO
{
    /** Standard Constructor
    @param ctx context
    @param I_Window_ID id
    @param trx transaction
    */
    public X_I_Window (Ctx ctx, int I_Window_ID, Trx trx)
    {
        super (ctx, I_Window_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (I_Window_ID == 0)
        {
            setI_IsImported (null);	// N
            setIsBetaFunctionality (false);	// N
            setIsCustomDefault (false);	// N
            setI_Window_ID (0);
            setProcessed (false);	// N
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_I_Window (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664584272789L;
    /** Last Updated Timestamp 2013-10-22 23:22:36.0 */
    public static final long updatedMS = 1382458956000L;
    /** AD_Table_ID=1000508 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("I_Window");
        
    }
    ;
    
    /** TableName=I_Window */
    public static final String Table_Name="I_Window";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set System Color.
    @param AD_Color_ID Color for backgrounds or indicators */
    public void setAD_Color_ID (int AD_Color_ID)
    {
        if (AD_Color_ID <= 0) set_Value ("AD_Color_ID", null);
        else
        set_Value ("AD_Color_ID", Integer.valueOf(AD_Color_ID));
        
    }
    
    /** Get System Color.
    @return Color for backgrounds or indicators */
    public int getAD_Color_ID() 
    {
        return get_ValueAsInt("AD_Color_ID");
        
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
        if (I_IsImported == null) throw new IllegalArgumentException ("I_IsImported is mandatory");
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
    
    /** Set Customization Default.
    @param IsCustomDefault Default Customization */
    public void setIsCustomDefault (boolean IsCustomDefault)
    {
        set_Value ("IsCustomDefault", Boolean.valueOf(IsCustomDefault));
        
    }
    
    /** Get Customization Default.
    @return Default Customization */
    public boolean isCustomDefault() 
    {
        return get_ValueAsBoolean("IsCustomDefault");
        
    }
    
    /** Set I_Window_ID.
    @param I_Window_ID I_Window_ID */
    public void setI_Window_ID (int I_Window_ID)
    {
        if (I_Window_ID < 1) throw new IllegalArgumentException ("I_Window_ID is mandatory.");
        set_ValueNoCheck ("I_Window_ID", Integer.valueOf(I_Window_ID));
        
    }
    
    /** Get I_Window_ID.
    @return I_Window_ID */
    public int getI_Window_ID() 
    {
        return get_ValueAsInt("I_Window_ID");
        
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
    
    /** Set System Color Name.
    @param SystemColorName System color name */
    public void setSystemColorName (String SystemColorName)
    {
        set_Value ("SystemColorName", SystemColorName);
        
    }
    
    /** Get System Color Name.
    @return System color name */
    public String getSystemColorName() 
    {
        return (String)get_Value("SystemColorName");
        
    }
    
    /** Maintain = M */
    public static final String WINDOWTYPE_Maintain = X_Ref_AD_Window_Types.MAINTAIN.getValue();
    /** Query Only = Q */
    public static final String WINDOWTYPE_QueryOnly = X_Ref_AD_Window_Types.QUERY_ONLY.getValue();
    /** Single Record = S */
    public static final String WINDOWTYPE_SingleRecord = X_Ref_AD_Window_Types.SINGLE_RECORD.getValue();
    /** Transaction = T */
    public static final String WINDOWTYPE_Transaction = X_Ref_AD_Window_Types.TRANSACTION.getValue();
    /** Set WindowType.
    @param WindowType Type or classification of a Window */
    public void setWindowType (String WindowType)
    {
        if (!X_Ref_AD_Window_Types.isValid(WindowType))
        throw new IllegalArgumentException ("WindowType Invalid value - " + WindowType + " - Reference_ID=108 - M - Q - S - T");
        set_Value ("WindowType", WindowType);
        
    }
    
    /** Get WindowType.
    @return Type or classification of a Window */
    public String getWindowType() 
    {
        return (String)get_Value("WindowType");
        
    }
    
    /** Set Window Height.
    @param WinHeight Window Height */
    public void setWinHeight (int WinHeight)
    {
        set_Value ("WinHeight", Integer.valueOf(WinHeight));
        
    }
    
    /** Get Window Height.
    @return Window Height */
    public int getWinHeight() 
    {
        return get_ValueAsInt("WinHeight");
        
    }
    
    /** Set Window Width.
    @param WinWidth Window Width */
    public void setWinWidth (int WinWidth)
    {
        set_Value ("WinWidth", Integer.valueOf(WinWidth));
        
    }
    
    /** Get Window Width.
    @return Window Width */
    public int getWinWidth() 
    {
        return get_ValueAsInt("WinWidth");
        
    }
    
    
}
