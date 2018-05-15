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
/** Generated Model for I_MatchedAccountElement
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_I_MatchedAccountElement extends PO
{
    /** Standard Constructor
    @param ctx context
    @param I_MatchedAccountElement_ID id
    @param trx transaction
    */
    public X_I_MatchedAccountElement (Ctx ctx, int I_MatchedAccountElement_ID, Trx trx)
    {
        super (ctx, I_MatchedAccountElement_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (I_MatchedAccountElement_ID == 0)
        {
            setI_IsImported (false);	// N
            setI_MatchedAccountElement_ID (0);
            setProcessed (false);	// N
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_I_MatchedAccountElement (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27747472140789L;
    /** Last Updated Timestamp 2016-06-08 07:47:04.0 */
    public static final long updatedMS = 1465346824000L;
    /** AD_Table_ID=1000101 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("I_MatchedAccountElement");
        
    }
    ;
    
    /** TableName=I_MatchedAccountElement */
    public static final String Table_Name="I_MatchedAccountElement";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Account.
    @param Account_ID Account used */
    public void setAccount_ID (int Account_ID)
    {
        if (Account_ID <= 0) set_Value ("Account_ID", null);
        else
        set_Value ("Account_ID", Integer.valueOf(Account_ID));
        
    }
    
    /** Get Account.
    @return Account used */
    public int getAccount_ID() 
    {
        return get_ValueAsInt("Account_ID");
        
    }
    
    /** Set Account Key.
    @param AccountValue Key of Account Element */
    public void setAccountValue (String AccountValue)
    {
        set_Value ("AccountValue", AccountValue);
        
    }
    
    /** Get Account Key.
    @return Key of Account Element */
    public String getAccountValue() 
    {
        return (String)get_Value("AccountValue");
        
    }
    
    /** Set Account Key 2.
    @param AccountValue2 Key of second Account Element */
    public void setAccountValue2 (String AccountValue2)
    {
        set_Value ("AccountValue2", AccountValue2);
        
    }
    
    /** Get Account Key 2.
    @return Key of second Account Element */
    public String getAccountValue2() 
    {
        return (String)get_Value("AccountValue2");
        
    }
    
    /** Set Element.
    @param C_Element_ID Accounting Element */
    public void setC_Element_ID (int C_Element_ID)
    {
        if (C_Element_ID <= 0) set_Value ("C_Element_ID", null);
        else
        set_Value ("C_Element_ID", Integer.valueOf(C_Element_ID));
        
    }
    
    /** Get Element.
    @return Accounting Element */
    public int getC_Element_ID() 
    {
        return get_ValueAsInt("C_Element_ID");
        
    }
    
    /** Set Element Key.
    @param ElementValue Key of the element */
    public void setElementValue (String ElementValue)
    {
        set_Value ("ElementValue", ElementValue);
        
    }
    
    /** Get Element Key.
    @return Key of the element */
    public String getElementValue() 
    {
        return (String)get_Value("ElementValue");
        
    }
    
    /** Set Element Key 2.
    @param ElementValue2 Key of the second element */
    public void setElementValue2 (String ElementValue2)
    {
        set_Value ("ElementValue2", ElementValue2);
        
    }
    
    /** Get Element Key 2.
    @return Key of the second element */
    public String getElementValue2() 
    {
        return (String)get_Value("ElementValue2");
        
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
    
    /** Set I_MatchedAccountElement_ID.
    @param I_MatchedAccountElement_ID I_MatchedAccountElement_ID */
    public void setI_MatchedAccountElement_ID (int I_MatchedAccountElement_ID)
    {
        if (I_MatchedAccountElement_ID < 1) throw new IllegalArgumentException ("I_MatchedAccountElement_ID is mandatory.");
        set_ValueNoCheck ("I_MatchedAccountElement_ID", Integer.valueOf(I_MatchedAccountElement_ID));
        
    }
    
    /** Get I_MatchedAccountElement_ID.
    @return I_MatchedAccountElement_ID */
    public int getI_MatchedAccountElement_ID() 
    {
        return get_ValueAsInt("I_MatchedAccountElement_ID");
        
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
    
    /** Set Account 2.
    @param TargetAccount_ID Second account */
    public void setTargetAccount_ID (int TargetAccount_ID)
    {
        if (TargetAccount_ID <= 0) set_Value ("TargetAccount_ID", null);
        else
        set_Value ("TargetAccount_ID", Integer.valueOf(TargetAccount_ID));
        
    }
    
    /** Get Account 2.
    @return Second account */
    public int getTargetAccount_ID() 
    {
        return get_ValueAsInt("TargetAccount_ID");
        
    }
    
    /** Set Element 2.
    @param TargetElement_ID Second element */
    public void setTargetElement_ID (int TargetElement_ID)
    {
        if (TargetElement_ID <= 0) set_Value ("TargetElement_ID", null);
        else
        set_Value ("TargetElement_ID", Integer.valueOf(TargetElement_ID));
        
    }
    
    /** Get Element 2.
    @return Second element */
    public int getTargetElement_ID() 
    {
        return get_ValueAsInt("TargetElement_ID");
        
    }
    
    /** Set Matched Accounts.
    @param XX_AccountElementMatch_ID Matched Accounts */
    public void setXX_AccountElementMatch_ID (int XX_AccountElementMatch_ID)
    {
        if (XX_AccountElementMatch_ID <= 0) set_ValueNoCheck ("XX_AccountElementMatch_ID", null);
        else
        set_ValueNoCheck ("XX_AccountElementMatch_ID", Integer.valueOf(XX_AccountElementMatch_ID));
        
    }
    
    /** Get Matched Accounts.
    @return Matched Accounts */
    public int getXX_AccountElementMatch_ID() 
    {
        return get_ValueAsInt("XX_AccountElementMatch_ID");
        
    }
    
    
}
