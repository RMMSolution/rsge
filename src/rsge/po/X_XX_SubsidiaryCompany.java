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
/** Generated Model for XX_SubsidiaryCompany
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_SubsidiaryCompany extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_SubsidiaryCompany_ID id
    @param trx transaction
    */
    public X_XX_SubsidiaryCompany (Ctx ctx, int XX_SubsidiaryCompany_ID, Trx trx)
    {
        super (ctx, XX_SubsidiaryCompany_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_SubsidiaryCompany_ID == 0)
        {
            setSubsidiaryType (null);	// 1
            setXX_SystemClient_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_SubsidiaryCompany (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27777053644366L;
    /** Last Updated Timestamp 2017-05-16 16:52:07.577 */
    public static final long updatedMS = 1494928327577L;
    /** AD_Table_ID=1000186 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_SubsidiaryCompany");
        
    }
    ;
    
    /** TableName=XX_SubsidiaryCompany */
    public static final String Table_Name="XX_SubsidiaryCompany";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Ownership (%).
    @param OwnershipShare Ownership share in subsidiary company */
    public void setOwnershipShare (java.math.BigDecimal OwnershipShare)
    {
        set_ValueNoCheck ("OwnershipShare", OwnershipShare);
        
    }
    
    /** Get Ownership (%).
    @return Ownership share in subsidiary company */
    public java.math.BigDecimal getOwnershipShare() 
    {
        return get_ValueAsBigDecimal("OwnershipShare");
        
    }
    
    /** Child Company = 1 */
    public static final String SUBSIDIARYTYPE_ChildCompany = X_Ref__Subsidiary_Type.CHILD_COMPANY.getValue();
    /** Branch = 2 */
    public static final String SUBSIDIARYTYPE_Branch = X_Ref__Subsidiary_Type.BRANCH.getValue();
    /** Set Type.
    @param SubsidiaryType Type of subsidiary. Company or Branch */
    public void setSubsidiaryType (String SubsidiaryType)
    {
        if (SubsidiaryType == null) throw new IllegalArgumentException ("SubsidiaryType is mandatory");
        if (!X_Ref__Subsidiary_Type.isValid(SubsidiaryType))
        throw new IllegalArgumentException ("SubsidiaryType Invalid value - " + SubsidiaryType + " - Reference_ID=1000118 - 1 - 2");
        set_Value ("SubsidiaryType", SubsidiaryType);
        
    }
    
    /** Get Type.
    @return Type of subsidiary. Company or Branch */
    public String getSubsidiaryType() 
    {
        return (String)get_Value("SubsidiaryType");
        
    }
    
    /** Set System's Client.
    @param XX_SystemClient_ID System's Client */
    public void setXX_SystemClient_ID (int XX_SystemClient_ID)
    {
        if (XX_SystemClient_ID < 1) throw new IllegalArgumentException ("XX_SystemClient_ID is mandatory.");
        set_ValueNoCheck ("XX_SystemClient_ID", Integer.valueOf(XX_SystemClient_ID));
        
    }
    
    /** Get System's Client.
    @return System's Client */
    public int getXX_SystemClient_ID() 
    {
        return get_ValueAsInt("XX_SystemClient_ID");
        
    }
    
    
}
