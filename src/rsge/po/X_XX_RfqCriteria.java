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
/** Generated Model for XX_RfqCriteria
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_RfqCriteria extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_RfqCriteria_ID id
    @param trx transaction
    */
    public X_XX_RfqCriteria (Ctx ctx, int XX_RfqCriteria_ID, Trx trx)
    {
        super (ctx, XX_RfqCriteria_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_RfqCriteria_ID == 0)
        {
            setXX_ProcCriteriaType_ID (0);
            setXX_RfqCriteria_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_RfqCriteria (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27664482291789L;
    /** Last Updated Timestamp 2013-10-21 19:02:55.0 */
    public static final long updatedMS = 1382356975000L;
    /** AD_Table_ID=1000184 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_RfqCriteria");
        
    }
    ;
    
    /** TableName=XX_RfqCriteria */
    public static final String Table_Name="XX_RfqCriteria";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set RfQ.
    @param C_RfQ_ID Request for Quotation */
    public void setC_RfQ_ID (int C_RfQ_ID)
    {
        if (C_RfQ_ID <= 0) set_Value ("C_RfQ_ID", null);
        else
        set_Value ("C_RfQ_ID", Integer.valueOf(C_RfQ_ID));
        
    }
    
    /** Get RfQ.
    @return Request for Quotation */
    public int getC_RfQ_ID() 
    {
        return get_ValueAsInt("C_RfQ_ID");
        
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
    
    /** Set XX_RfqCriteria_ID.
    @param XX_RfqCriteria_ID XX_RfqCriteria_ID */
    public void setXX_RfqCriteria_ID (int XX_RfqCriteria_ID)
    {
        if (XX_RfqCriteria_ID < 1) throw new IllegalArgumentException ("XX_RfqCriteria_ID is mandatory.");
        set_ValueNoCheck ("XX_RfqCriteria_ID", Integer.valueOf(XX_RfqCriteria_ID));
        
    }
    
    /** Get XX_RfqCriteria_ID.
    @return XX_RfqCriteria_ID */
    public int getXX_RfqCriteria_ID() 
    {
        return get_ValueAsInt("XX_RfqCriteria_ID");
        
    }
    
    
}
