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
/** Generated Model for XX_ExcludeAmountOnly
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_ExcludeAmountOnly extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_ExcludeAmountOnly_ID id
    @param trx transaction
    */
    public X_XX_ExcludeAmountOnly (Ctx ctx, int XX_ExcludeAmountOnly_ID, Trx trx)
    {
        super (ctx, XX_ExcludeAmountOnly_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_ExcludeAmountOnly_ID == 0)
        {
            setIsActivity (false);	// N
            setIsBusinessPartner (false);	// N
            setIsCampaign (false);	// N
            setIsOrganization (false);	// N
            setIsProduct (false);	// N
            setIsProject (false);	// N
            setIsSalesRegion (false);	// N
            setXX_BudgetInfo_ID (0);
            setXX_ExcludeAmountOnly_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_ExcludeAmountOnly (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27689904662789L;
    /** Last Updated Timestamp 2014-08-12 00:49:06.0 */
    public static final long updatedMS = 1407779346000L;
    /** AD_Table_ID=1000146 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_ExcludeAmountOnly");
        
    }
    ;
    
    /** TableName=XX_ExcludeAmountOnly */
    public static final String Table_Name="XX_ExcludeAmountOnly";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Activity.
    @param C_Activity_ID Business Activity */
    public void setC_Activity_ID (int C_Activity_ID)
    {
        if (C_Activity_ID <= 0) set_Value ("C_Activity_ID", null);
        else
        set_Value ("C_Activity_ID", Integer.valueOf(C_Activity_ID));
        
    }
    
    /** Get Activity.
    @return Business Activity */
    public int getC_Activity_ID() 
    {
        return get_ValueAsInt("C_Activity_ID");
        
    }
    
    /** Set Business Partner.
    @param C_BPartner_ID Identifies a Business Partner */
    public void setC_BPartner_ID (int C_BPartner_ID)
    {
        if (C_BPartner_ID <= 0) set_Value ("C_BPartner_ID", null);
        else
        set_Value ("C_BPartner_ID", Integer.valueOf(C_BPartner_ID));
        
    }
    
    /** Get Business Partner.
    @return Identifies a Business Partner */
    public int getC_BPartner_ID() 
    {
        return get_ValueAsInt("C_BPartner_ID");
        
    }
    
    /** Set Campaign.
    @param C_Campaign_ID Marketing Campaign */
    public void setC_Campaign_ID (int C_Campaign_ID)
    {
        if (C_Campaign_ID <= 0) set_Value ("C_Campaign_ID", null);
        else
        set_Value ("C_Campaign_ID", Integer.valueOf(C_Campaign_ID));
        
    }
    
    /** Get Campaign.
    @return Marketing Campaign */
    public int getC_Campaign_ID() 
    {
        return get_ValueAsInt("C_Campaign_ID");
        
    }
    
    /** Set Project.
    @param C_Project_ID Financial Project */
    public void setC_Project_ID (int C_Project_ID)
    {
        if (C_Project_ID <= 0) set_Value ("C_Project_ID", null);
        else
        set_Value ("C_Project_ID", Integer.valueOf(C_Project_ID));
        
    }
    
    /** Get Project.
    @return Financial Project */
    public int getC_Project_ID() 
    {
        return get_ValueAsInt("C_Project_ID");
        
    }
    
    /** Set Sales Region.
    @param C_SalesRegion_ID Sales coverage region */
    public void setC_SalesRegion_ID (int C_SalesRegion_ID)
    {
        if (C_SalesRegion_ID <= 0) set_Value ("C_SalesRegion_ID", null);
        else
        set_Value ("C_SalesRegion_ID", Integer.valueOf(C_SalesRegion_ID));
        
    }
    
    /** Get Sales Region.
    @return Sales coverage region */
    public int getC_SalesRegion_ID() 
    {
        return get_ValueAsInt("C_SalesRegion_ID");
        
    }
    
    /** Set Activity.
    @param IsActivity Activity */
    public void setIsActivity (boolean IsActivity)
    {
        set_Value ("IsActivity", Boolean.valueOf(IsActivity));
        
    }
    
    /** Get Activity.
    @return Activity */
    public boolean isActivity() 
    {
        return get_ValueAsBoolean("IsActivity");
        
    }
    
    /** Set Business Partner.
    @param IsBusinessPartner Business Partner */
    public void setIsBusinessPartner (boolean IsBusinessPartner)
    {
        set_Value ("IsBusinessPartner", Boolean.valueOf(IsBusinessPartner));
        
    }
    
    /** Get Business Partner.
    @return Business Partner */
    public boolean isBusinessPartner() 
    {
        return get_ValueAsBoolean("IsBusinessPartner");
        
    }
    
    /** Set Campaign.
    @param IsCampaign Campaign */
    public void setIsCampaign (boolean IsCampaign)
    {
        set_Value ("IsCampaign", Boolean.valueOf(IsCampaign));
        
    }
    
    /** Get Campaign.
    @return Campaign */
    public boolean isCampaign() 
    {
        return get_ValueAsBoolean("IsCampaign");
        
    }
    
    /** Set Organization.
    @param IsOrganization Organization */
    public void setIsOrganization (boolean IsOrganization)
    {
        set_Value ("IsOrganization", Boolean.valueOf(IsOrganization));
        
    }
    
    /** Get Organization.
    @return Organization */
    public boolean isOrganization() 
    {
        return get_ValueAsBoolean("IsOrganization");
        
    }
    
    /** Set Product.
    @param IsProduct Product */
    public void setIsProduct (boolean IsProduct)
    {
        set_Value ("IsProduct", Boolean.valueOf(IsProduct));
        
    }
    
    /** Get Product.
    @return Product */
    public boolean isProduct() 
    {
        return get_ValueAsBoolean("IsProduct");
        
    }
    
    /** Set Project.
    @param IsProject Project */
    public void setIsProject (boolean IsProject)
    {
        set_Value ("IsProject", Boolean.valueOf(IsProject));
        
    }
    
    /** Get Project.
    @return Project */
    public boolean isProject() 
    {
        return get_ValueAsBoolean("IsProject");
        
    }
    
    /** Set Sales Region.
    @param IsSalesRegion Sales Region */
    public void setIsSalesRegion (boolean IsSalesRegion)
    {
        set_Value ("IsSalesRegion", Boolean.valueOf(IsSalesRegion));
        
    }
    
    /** Get Sales Region.
    @return Sales Region */
    public boolean isSalesRegion() 
    {
        return get_ValueAsBoolean("IsSalesRegion");
        
    }
    
    /** Set Product.
    @param M_Product_ID Product, Service, Item */
    public void setM_Product_ID (int M_Product_ID)
    {
        if (M_Product_ID <= 0) set_Value ("M_Product_ID", null);
        else
        set_Value ("M_Product_ID", Integer.valueOf(M_Product_ID));
        
    }
    
    /** Get Product.
    @return Product, Service, Item */
    public int getM_Product_ID() 
    {
        return get_ValueAsInt("M_Product_ID");
        
    }
    
    /** Set Budget Info.
    @param XX_BudgetInfo_ID Budget Info */
    public void setXX_BudgetInfo_ID (int XX_BudgetInfo_ID)
    {
        if (XX_BudgetInfo_ID < 1) throw new IllegalArgumentException ("XX_BudgetInfo_ID is mandatory.");
        set_Value ("XX_BudgetInfo_ID", Integer.valueOf(XX_BudgetInfo_ID));
        
    }
    
    /** Get Budget Info.
    @return Budget Info */
    public int getXX_BudgetInfo_ID() 
    {
        return get_ValueAsInt("XX_BudgetInfo_ID");
        
    }
    
    /** Set XX_ExcludeAmountOnly_ID.
    @param XX_ExcludeAmountOnly_ID XX_ExcludeAmountOnly_ID */
    public void setXX_ExcludeAmountOnly_ID (int XX_ExcludeAmountOnly_ID)
    {
        if (XX_ExcludeAmountOnly_ID < 1) throw new IllegalArgumentException ("XX_ExcludeAmountOnly_ID is mandatory.");
        set_ValueNoCheck ("XX_ExcludeAmountOnly_ID", Integer.valueOf(XX_ExcludeAmountOnly_ID));
        
    }
    
    /** Get XX_ExcludeAmountOnly_ID.
    @return XX_ExcludeAmountOnly_ID */
    public int getXX_ExcludeAmountOnly_ID() 
    {
        return get_ValueAsInt("XX_ExcludeAmountOnly_ID");
        
    }
    
    
}
