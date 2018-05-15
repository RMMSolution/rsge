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
/** Generated Model for XX_ProcCriteria
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_ProcCriteria extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_ProcCriteria_ID id
    @param trx transaction
    */
    public X_XX_ProcCriteria (Ctx ctx, int XX_ProcCriteria_ID, Trx trx)
    {
        super (ctx, XX_ProcCriteria_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_ProcCriteria_ID == 0)
        {
            setXX_ProcCriteria_ID (0);
            setXX_ProcCriteriaType_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_ProcCriteria (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27672952935789L;
    /** Last Updated Timestamp 2014-01-27 20:00:19.0 */
    public static final long updatedMS = 1390827619000L;
    /** AD_Table_ID=1000170 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_ProcCriteria");
        
    }
    ;
    
    /** TableName=XX_ProcCriteria */
    public static final String Table_Name="XX_ProcCriteria";
    
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
    
    /** Set Valuation Weight.
    @param ValuationWeight Weight of valuation. Max. 100 percent */
    public void setValuationWeight (java.math.BigDecimal ValuationWeight)
    {
        set_Value ("ValuationWeight", ValuationWeight);
        
    }
    
    /** Get Valuation Weight.
    @return Weight of valuation. Max. 100 percent */
    public java.math.BigDecimal getValuationWeight() 
    {
        return get_ValueAsBigDecimal("ValuationWeight");
        
    }
    
    /** Set Procurement Criteria.
    @param XX_ProcCriteria_ID Criteria of procurement */
    public void setXX_ProcCriteria_ID (int XX_ProcCriteria_ID)
    {
        if (XX_ProcCriteria_ID < 1) throw new IllegalArgumentException ("XX_ProcCriteria_ID is mandatory.");
        set_ValueNoCheck ("XX_ProcCriteria_ID", Integer.valueOf(XX_ProcCriteria_ID));
        
    }
    
    /** Get Procurement Criteria.
    @return Criteria of procurement */
    public int getXX_ProcCriteria_ID() 
    {
        return get_ValueAsInt("XX_ProcCriteria_ID");
        
    }
    
    /** Set Procurement Criteria Set.
    @param XX_ProcCriteriaSet_ID Set of procurement criteria */
    public void setXX_ProcCriteriaSet_ID (int XX_ProcCriteriaSet_ID)
    {
        if (XX_ProcCriteriaSet_ID <= 0) set_Value ("XX_ProcCriteriaSet_ID", null);
        else
        set_Value ("XX_ProcCriteriaSet_ID", Integer.valueOf(XX_ProcCriteriaSet_ID));
        
    }
    
    /** Get Procurement Criteria Set.
    @return Set of procurement criteria */
    public int getXX_ProcCriteriaSet_ID() 
    {
        return get_ValueAsInt("XX_ProcCriteriaSet_ID");
        
    }
    
    /** Set Procurement Criteria Type.
    @param XX_ProcCriteriaType_ID Type of procurement''s criteria */
    public void setXX_ProcCriteriaType_ID (int XX_ProcCriteriaType_ID)
    {
        if (XX_ProcCriteriaType_ID < 1) throw new IllegalArgumentException ("XX_ProcCriteriaType_ID is mandatory.");
        set_Value ("XX_ProcCriteriaType_ID", Integer.valueOf(XX_ProcCriteriaType_ID));
        
    }
    
    /** Get Procurement Criteria Type.
    @return Type of procurement''s criteria */
    public int getXX_ProcCriteriaType_ID() 
    {
        return get_ValueAsInt("XX_ProcCriteriaType_ID");
        
    }
    
    
}
