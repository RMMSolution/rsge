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
/** Generated Model for XX_TenantBranchAcct
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.7 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_TenantBranchAcct extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_TenantBranchAcct_ID id
    @param trx transaction
    */
    public X_XX_TenantBranchAcct (Ctx ctx, int XX_TenantBranchAcct_ID, Trx trx)
    {
        super (ctx, XX_TenantBranchAcct_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_TenantBranchAcct_ID == 0)
        {
            setC_AcctSchema_ID (0);
            setIntercompanyDueFrom_Acct (0);
            setIntercompanyDueTo_Acct (0);
            setXX_TenantBranch_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_TenantBranchAcct (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27778110138092L;
    /** Last Updated Timestamp 2017-05-28 22:20:21.303 */
    public static final long updatedMS = 1495984821303L;
    /** AD_Table_ID=1014495 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_TenantBranchAcct");
        
    }
    ;
    
    /** TableName=XX_TenantBranchAcct */
    public static final String Table_Name="XX_TenantBranchAcct";
    
    /**
     *  Get AD Table ID.
     *  @return AD_Table_ID
     */
    @Override public int get_Table_ID()
    {
        return Table_ID;
        
    }
    /** Set Accounting Schema.
    @param C_AcctSchema_ID Rules for accounting */
    public void setC_AcctSchema_ID (int C_AcctSchema_ID)
    {
        if (C_AcctSchema_ID < 1) throw new IllegalArgumentException ("C_AcctSchema_ID is mandatory.");
        set_ValueNoCheck ("C_AcctSchema_ID", Integer.valueOf(C_AcctSchema_ID));
        
    }
    
    /** Get Accounting Schema.
    @return Rules for accounting */
    public int getC_AcctSchema_ID() 
    {
        return get_ValueAsInt("C_AcctSchema_ID");
        
    }
    
    /** Set Intercompany Due From Acct.
    @param IntercompanyDueFrom_Acct Intercompany Due From / Receivables Account */
    public void setIntercompanyDueFrom_Acct (int IntercompanyDueFrom_Acct)
    {
        set_Value ("IntercompanyDueFrom_Acct", Integer.valueOf(IntercompanyDueFrom_Acct));
        
    }
    
    /** Get Intercompany Due From Acct.
    @return Intercompany Due From / Receivables Account */
    public int getIntercompanyDueFrom_Acct() 
    {
        return get_ValueAsInt("IntercompanyDueFrom_Acct");
        
    }
    
    /** Set Intercompany Due To Acct.
    @param IntercompanyDueTo_Acct Intercompany Due To / Payable Account */
    public void setIntercompanyDueTo_Acct (int IntercompanyDueTo_Acct)
    {
        set_Value ("IntercompanyDueTo_Acct", Integer.valueOf(IntercompanyDueTo_Acct));
        
    }
    
    /** Get Intercompany Due To Acct.
    @return Intercompany Due To / Payable Account */
    public int getIntercompanyDueTo_Acct() 
    {
        return get_ValueAsInt("IntercompanyDueTo_Acct");
        
    }
    
    /** Set Branch.
    @param XX_TenantBranch_ID Branch or representative office of company */
    public void setXX_TenantBranch_ID (int XX_TenantBranch_ID)
    {
        if (XX_TenantBranch_ID < 1) throw new IllegalArgumentException ("XX_TenantBranch_ID is mandatory.");
        set_ValueNoCheck ("XX_TenantBranch_ID", Integer.valueOf(XX_TenantBranch_ID));
        
    }
    
    /** Get Branch.
    @return Branch or representative office of company */
    public int getXX_TenantBranch_ID() 
    {
        return get_ValueAsInt("XX_TenantBranch_ID");
        
    }
    
    
}
