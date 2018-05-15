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
/** Generated Model for XX_BudgetFormTemplateLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_BudgetFormTemplateLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_BudgetFormTemplateLine_ID id
    @param trx transaction
    */
    public X_XX_BudgetFormTemplateLine (Ctx ctx, int XX_BudgetFormTemplateLine_ID, Trx trx)
    {
        super (ctx, XX_BudgetFormTemplateLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_BudgetFormTemplateLine_ID == 0)
        {
            setIsCustomForm (false);	// N
            setLine (0);	// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM XX_BudgetFormTemplateLine WHERE XX_BudgetFormTemplate_ID=@XX_BudgetFormTemplate_ID@
            setXX_BudgetFormTemplateLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_BudgetFormTemplateLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27794853750329L;
    /** Last Updated Timestamp 2017-12-08 17:20:33.54 */
    public static final long updatedMS = 1512728433540L;
    /** AD_Table_ID=1000114 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_BudgetFormTemplateLine");
        
    }
    ;
    
    /** TableName=XX_BudgetFormTemplateLine */
    public static final String Table_Name="XX_BudgetFormTemplateLine";
    
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
    
    /** Set Custom Form.
    @param IsCustomForm Use customized form */
    public void setIsCustomForm (boolean IsCustomForm)
    {
        set_Value ("IsCustomForm", Boolean.valueOf(IsCustomForm));
        
    }
    
    /** Get Custom Form.
    @return Use customized form */
    public boolean isCustomForm() 
    {
        return get_ValueAsBoolean("IsCustomForm");
        
    }
    
    /** Set Line No.
    @param Line Unique line for this document */
    public void setLine (int Line)
    {
        set_Value ("Line", Integer.valueOf(Line));
        
    }
    
    /** Get Line No.
    @return Unique line for this document */
    public int getLine() 
    {
        return get_ValueAsInt("Line");
        
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
    
    /** Set Parent Product.
    @param M_Product_Summary_ID Product, Service, Item */
    public void setM_Product_Summary_ID (int M_Product_Summary_ID)
    {
        if (M_Product_Summary_ID <= 0) set_Value ("M_Product_Summary_ID", null);
        else
        set_Value ("M_Product_Summary_ID", Integer.valueOf(M_Product_Summary_ID));
        
    }
    
    /** Get Parent Product.
    @return Product, Service, Item */
    public int getM_Product_Summary_ID() 
    {
        return get_ValueAsInt("M_Product_Summary_ID");
        
    }
    
    /** Set Budget Form Template.
    @param XX_BudgetFormTemplate_ID Template for budget form */
    public void setXX_BudgetFormTemplate_ID (int XX_BudgetFormTemplate_ID)
    {
        if (XX_BudgetFormTemplate_ID <= 0) set_Value ("XX_BudgetFormTemplate_ID", null);
        else
        set_Value ("XX_BudgetFormTemplate_ID", Integer.valueOf(XX_BudgetFormTemplate_ID));
        
    }
    
    /** Get Budget Form Template.
    @return Template for budget form */
    public int getXX_BudgetFormTemplate_ID() 
    {
        return get_ValueAsInt("XX_BudgetFormTemplate_ID");
        
    }
    
    /** Set Template Line.
    @param XX_BudgetFormTemplateLine_ID Template line for budget form */
    public void setXX_BudgetFormTemplateLine_ID (int XX_BudgetFormTemplateLine_ID)
    {
        if (XX_BudgetFormTemplateLine_ID < 1) throw new IllegalArgumentException ("XX_BudgetFormTemplateLine_ID is mandatory.");
        set_ValueNoCheck ("XX_BudgetFormTemplateLine_ID", Integer.valueOf(XX_BudgetFormTemplateLine_ID));
        
    }
    
    /** Get Template Line.
    @return Template line for budget form */
    public int getXX_BudgetFormTemplateLine_ID() 
    {
        return get_ValueAsInt("XX_BudgetFormTemplateLine_ID");
        
    }
    
    
}
