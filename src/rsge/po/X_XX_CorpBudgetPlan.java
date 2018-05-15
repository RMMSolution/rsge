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
/** Generated Model for XX_CorpBudgetPlan
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_CorpBudgetPlan extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_CorpBudgetPlan_ID id
    @param trx transaction
    */
    public X_XX_CorpBudgetPlan (Ctx ctx, int XX_CorpBudgetPlan_ID, Trx trx)
    {
        super (ctx, XX_CorpBudgetPlan_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_CorpBudgetPlan_ID == 0)
        {
            setC_From_Period_ID (0);
            setGL_Budget_ID (0);
            setPlanningPeriod (null);
            setProcessed (false);	// N
            setXX_CorpBudgetPlan_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_CorpBudgetPlan (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27789654438418L;
    /** Last Updated Timestamp 2017-10-09 13:05:21.629 */
    public static final long updatedMS = 1507529121629L;
    /** AD_Table_ID=1000132 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_CorpBudgetPlan");
        
    }
    ;
    
    /** TableName=XX_CorpBudgetPlan */
    public static final String Table_Name="XX_CorpBudgetPlan";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Approved = A */
    public static final String BUDGETSTATUS_Approved = X_Ref_GL_Budget_Status.APPROVED.getValue();
    /** Draft = D */
    public static final String BUDGETSTATUS_Draft = X_Ref_GL_Budget_Status.DRAFT.getValue();
    /** Set Budget Status.
    @param BudgetStatus Indicates the current status of this budget */
    public void setBudgetStatus (String BudgetStatus)
    {
        if (!X_Ref_GL_Budget_Status.isValid(BudgetStatus))
        throw new IllegalArgumentException ("BudgetStatus Invalid value - " + BudgetStatus + " - Reference_ID=178 - A - D");
        set_Value ("BudgetStatus", BudgetStatus);
        
    }
    
    /** Get Budget Status.
    @return Indicates the current status of this budget */
    public String getBudgetStatus() 
    {
        return (String)get_Value("BudgetStatus");
        
    }
    
    /** Set From Period.
    @param C_From_Period_ID Starting period of a range of periods */
    public void setC_From_Period_ID (int C_From_Period_ID)
    {
        if (C_From_Period_ID < 1) throw new IllegalArgumentException ("C_From_Period_ID is mandatory.");
        set_Value ("C_From_Period_ID", Integer.valueOf(C_From_Period_ID));
        
    }
    
    /** Get From Period.
    @return Starting period of a range of periods */
    public int getC_From_Period_ID() 
    {
        return get_ValueAsInt("C_From_Period_ID");
        
    }
    
    /** Set Create Budget Plan.
    @param CreateBudgetPlan Create budget plan for organization */
    public void setCreateBudgetPlan (String CreateBudgetPlan)
    {
        set_Value ("CreateBudgetPlan", CreateBudgetPlan);
        
    }
    
    /** Get Create Budget Plan.
    @return Create budget plan for organization */
    public String getCreateBudgetPlan() 
    {
        return (String)get_Value("CreateBudgetPlan");
        
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
    
    /** Set Budget.
    @param GL_Budget_ID General Ledger Budget */
    public void setGL_Budget_ID (int GL_Budget_ID)
    {
        if (GL_Budget_ID < 1) throw new IllegalArgumentException ("GL_Budget_ID is mandatory.");
        set_Value ("GL_Budget_ID", Integer.valueOf(GL_Budget_ID));
        
    }
    
    /** Get Budget.
    @return General Ledger Budget */
    public int getGL_Budget_ID() 
    {
        return get_ValueAsInt("GL_Budget_ID");
        
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
    
    /** Annual = A */
    public static final String PLANNINGPERIOD_Annual = X_Ref__Planning_Period.ANNUAL.getValue();
    /** Monthly = M */
    public static final String PLANNINGPERIOD_Monthly = X_Ref__Planning_Period.MONTHLY.getValue();
    /** Quarterly = Q */
    public static final String PLANNINGPERIOD_Quarterly = X_Ref__Planning_Period.QUARTERLY.getValue();
    /** Semester = S */
    public static final String PLANNINGPERIOD_Semester = X_Ref__Planning_Period.SEMESTER.getValue();
    /** Set Planning Period.
    @param PlanningPeriod Planning period type (Monthly, Quarterly, Semester) */
    public void setPlanningPeriod (String PlanningPeriod)
    {
        if (PlanningPeriod == null) throw new IllegalArgumentException ("PlanningPeriod is mandatory");
        if (!X_Ref__Planning_Period.isValid(PlanningPeriod))
        throw new IllegalArgumentException ("PlanningPeriod Invalid value - " + PlanningPeriod + " - Reference_ID=1000116 - A - M - Q - S");
        set_Value ("PlanningPeriod", PlanningPeriod);
        
    }
    
    /** Get Planning Period.
    @return Planning period type (Monthly, Quarterly, Semester) */
    public String getPlanningPeriod() 
    {
        return (String)get_Value("PlanningPeriod");
        
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
    
    /** Set Corporate Budget Plan.
    @param XX_CorpBudgetPlan_ID Main template to create budget plan for organization */
    public void setXX_CorpBudgetPlan_ID (int XX_CorpBudgetPlan_ID)
    {
        if (XX_CorpBudgetPlan_ID < 1) throw new IllegalArgumentException ("XX_CorpBudgetPlan_ID is mandatory.");
        set_ValueNoCheck ("XX_CorpBudgetPlan_ID", Integer.valueOf(XX_CorpBudgetPlan_ID));
        
    }
    
    /** Get Corporate Budget Plan.
    @return Main template to create budget plan for organization */
    public int getXX_CorpBudgetPlan_ID() 
    {
        return get_ValueAsInt("XX_CorpBudgetPlan_ID");
        
    }
    
    
}
