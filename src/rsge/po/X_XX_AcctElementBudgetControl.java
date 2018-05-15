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
/** Generated Model for XX_AcctElementBudgetControl
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_AcctElementBudgetControl extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_AcctElementBudgetControl_ID id
    @param trx transaction
    */
    public X_XX_AcctElementBudgetControl (Ctx ctx, int XX_AcctElementBudgetControl_ID, Trx trx)
    {
        super (ctx, XX_AcctElementBudgetControl_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_AcctElementBudgetControl_ID == 0)
        {
            setAllYear (true);	// Y
            setAnyOrg (true);	// Y
            setElementType (null);
            setGL_BudgetControl_ID (0);
            setXX_BudgetInfo_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_AcctElementBudgetControl (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27807266047847L;
    /** Last Updated Timestamp 2018-05-01 09:12:11.058 */
    public static final long updatedMS = 1525140731058L;
    /** AD_Table_ID=1000106 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_AcctElementBudgetControl");
        
    }
    ;
    
    /** TableName=XX_AcctElementBudgetControl */
    public static final String Table_Name="XX_AcctElementBudgetControl";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set All Year.
    @param AllYear Match any year */
    public void setAllYear (boolean AllYear)
    {
        set_Value ("AllYear", Boolean.valueOf(AllYear));
        
    }
    
    /** Get All Year.
    @return Match any year */
    public boolean isAllYear() 
    {
        return get_ValueAsBoolean("AllYear");
        
    }
    
    /** Set Any Organization.
    @param AnyOrg Match any value of the Organization segment */
    public void setAnyOrg (boolean AnyOrg)
    {
        set_Value ("AnyOrg", Boolean.valueOf(AnyOrg));
        
    }
    
    /** Get Any Organization.
    @return Match any value of the Organization segment */
    public boolean isAnyOrg() 
    {
        return get_ValueAsBoolean("AnyOrg");
        
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
    
    /** Organization = OO */
    public static final String ELEMENTTYPE_Organization = X_Ref_C_AcctSchema_ElementType.ORGANIZATION.getValue();
    /** Account = AC */
    public static final String ELEMENTTYPE_Account = X_Ref_C_AcctSchema_ElementType.ACCOUNT.getValue();
    /** Product = PR */
    public static final String ELEMENTTYPE_Product = X_Ref_C_AcctSchema_ElementType.PRODUCT.getValue();
    /** BPartner = BP */
    public static final String ELEMENTTYPE_BPartner = X_Ref_C_AcctSchema_ElementType.B_PARTNER.getValue();
    /** Org Trx = OT */
    public static final String ELEMENTTYPE_OrgTrx = X_Ref_C_AcctSchema_ElementType.ORG_TRX.getValue();
    /** Location From = LF */
    public static final String ELEMENTTYPE_LocationFrom = X_Ref_C_AcctSchema_ElementType.LOCATION_FROM.getValue();
    /** Location To = LT */
    public static final String ELEMENTTYPE_LocationTo = X_Ref_C_AcctSchema_ElementType.LOCATION_TO.getValue();
    /** Sales Region = SR */
    public static final String ELEMENTTYPE_SalesRegion = X_Ref_C_AcctSchema_ElementType.SALES_REGION.getValue();
    /** Project = PJ */
    public static final String ELEMENTTYPE_Project = X_Ref_C_AcctSchema_ElementType.PROJECT.getValue();
    /** Campaign = MC */
    public static final String ELEMENTTYPE_Campaign = X_Ref_C_AcctSchema_ElementType.CAMPAIGN.getValue();
    /** User List 1 = U1 */
    public static final String ELEMENTTYPE_UserList1 = X_Ref_C_AcctSchema_ElementType.USER_LIST1.getValue();
    /** User List 2 = U2 */
    public static final String ELEMENTTYPE_UserList2 = X_Ref_C_AcctSchema_ElementType.USER_LIST2.getValue();
    /** Activity = AY */
    public static final String ELEMENTTYPE_Activity = X_Ref_C_AcctSchema_ElementType.ACTIVITY.getValue();
    /** Sub Account = SA */
    public static final String ELEMENTTYPE_SubAccount = X_Ref_C_AcctSchema_ElementType.SUB_ACCOUNT.getValue();
    /** User Element 1 = X1 */
    public static final String ELEMENTTYPE_UserElement1 = X_Ref_C_AcctSchema_ElementType.USER_ELEMENT1.getValue();
    /** User Element 2 = X2 */
    public static final String ELEMENTTYPE_UserElement2 = X_Ref_C_AcctSchema_ElementType.USER_ELEMENT2.getValue();
    /** Set Type.
    @param ElementType Element Type (account or user defined) */
    public void setElementType (String ElementType)
    {
        if (ElementType == null) throw new IllegalArgumentException ("ElementType is mandatory");
        if (!X_Ref_C_AcctSchema_ElementType.isValid(ElementType))
        throw new IllegalArgumentException ("ElementType Invalid value - " + ElementType + " - Reference_ID=181 - OO - AC - PR - BP - OT - LF - LT - SR - PJ - MC - U1 - U2 - AY - SA - X1 - X2");
        set_ValueNoCheck ("ElementType", ElementType);
        
    }
    
    /** Get Type.
    @return Element Type (account or user defined) */
    public String getElementType() 
    {
        return (String)get_Value("ElementType");
        
    }
    
    /** Set Budget Control.
    @param GL_BudgetControl_ID Budget Control */
    public void setGL_BudgetControl_ID (int GL_BudgetControl_ID)
    {
        if (GL_BudgetControl_ID < 1) throw new IllegalArgumentException ("GL_BudgetControl_ID is mandatory.");
        set_Value ("GL_BudgetControl_ID", Integer.valueOf(GL_BudgetControl_ID));
        
    }
    
    /** Get Budget Control.
    @return Budget Control */
    public int getGL_BudgetControl_ID() 
    {
        return get_ValueAsInt("GL_BudgetControl_ID");
        
    }
    
    /** Set Budget Info.
    @param XX_BudgetInfo_ID Budget Info */
    public void setXX_BudgetInfo_ID (int XX_BudgetInfo_ID)
    {
        if (XX_BudgetInfo_ID < 1) throw new IllegalArgumentException ("XX_BudgetInfo_ID is mandatory.");
        set_ValueNoCheck ("XX_BudgetInfo_ID", Integer.valueOf(XX_BudgetInfo_ID));
        
    }
    
    /** Get Budget Info.
    @return Budget Info */
    public int getXX_BudgetInfo_ID() 
    {
        return get_ValueAsInt("XX_BudgetInfo_ID");
        
    }
    
    
}
