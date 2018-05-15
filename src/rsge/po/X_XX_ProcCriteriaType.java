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
/** Generated Model for XX_ProcCriteriaType
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_ProcCriteriaType extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_ProcCriteriaType_ID id
    @param trx transaction
    */
    public X_XX_ProcCriteriaType (Ctx ctx, int XX_ProcCriteriaType_ID, Trx trx)
    {
        super (ctx, XX_ProcCriteriaType_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_ProcCriteriaType_ID == 0)
        {
            setIsAdministration (false);	// N
            setIsPriceRelated (false);	// N
            setName (null);
            setValue (null);
            setXX_ProcCriteriaType_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_ProcCriteriaType (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27672946833789L;
    /** Last Updated Timestamp 2014-01-27 18:18:37.0 */
    public static final long updatedMS = 1390821517000L;
    /** AD_Table_ID=1000172 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_ProcCriteriaType");
        
    }
    ;
    
    /** TableName=XX_ProcCriteriaType */
    public static final String Table_Name="XX_ProcCriteriaType";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
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
    
    /** Set Administration Criteria.
    @param IsAdministration The criteria is relates to administration requirement */
    public void setIsAdministration (boolean IsAdministration)
    {
        set_Value ("IsAdministration", Boolean.valueOf(IsAdministration));
        
    }
    
    /** Get Administration Criteria.
    @return The criteria is relates to administration requirement */
    public boolean isAdministration() 
    {
        return get_ValueAsBoolean("IsAdministration");
        
    }
    
    /** Set Price Criteria.
    @param IsPriceRelated The criteria is relates to price */
    public void setIsPriceRelated (boolean IsPriceRelated)
    {
        set_Value ("IsPriceRelated", Boolean.valueOf(IsPriceRelated));
        
    }
    
    /** Get Price Criteria.
    @return The criteria is relates to price */
    public boolean isPriceRelated() 
    {
        return get_ValueAsBoolean("IsPriceRelated");
        
    }
    
    /** Set Name.
    @param Name Alphanumeric identifier of the entity */
    public void setName (String Name)
    {
        if (Name == null) throw new IllegalArgumentException ("Name is mandatory.");
        set_Value ("Name", Name);
        
    }
    
    /** Get Name.
    @return Alphanumeric identifier of the entity */
    public String getName() 
    {
        return (String)get_Value("Name");
        
    }
    
    /** Set Search Key.
    @param Value Search key for the record in the format required - must be unique */
    public void setValue (String Value)
    {
        if (Value == null) throw new IllegalArgumentException ("Value is mandatory.");
        set_Value ("Value", Value);
        
    }
    
    /** Get Search Key.
    @return Search key for the record in the format required - must be unique */
    public String getValue() 
    {
        return (String)get_Value("Value");
        
    }
    
    /** Set Procurement Criteria Type.
    @param XX_ProcCriteriaType_ID Type of procurement''s criteria */
    public void setXX_ProcCriteriaType_ID (int XX_ProcCriteriaType_ID)
    {
        if (XX_ProcCriteriaType_ID < 1) throw new IllegalArgumentException ("XX_ProcCriteriaType_ID is mandatory.");
        set_ValueNoCheck ("XX_ProcCriteriaType_ID", Integer.valueOf(XX_ProcCriteriaType_ID));
        
    }
    
    /** Get Procurement Criteria Type.
    @return Type of procurement''s criteria */
    public int getXX_ProcCriteriaType_ID() 
    {
        return get_ValueAsInt("XX_ProcCriteriaType_ID");
        
    }
    
    
}
