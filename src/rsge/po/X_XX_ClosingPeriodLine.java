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
/** Generated Model for XX_ClosingPeriodLine
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_ClosingPeriodLine extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_ClosingPeriodLine_ID id
    @param trx transaction
    */
    public X_XX_ClosingPeriodLine (Ctx ctx, int XX_ClosingPeriodLine_ID, Trx trx)
    {
        super (ctx, XX_ClosingPeriodLine_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_ClosingPeriodLine_ID == 0)
        {
            setAccount_ID (0);
            setXX_ClosingPeriod_ID (0);
            setXX_ClosingPeriodLine_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_ClosingPeriodLine (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27770913033852L;
    /** Last Updated Timestamp 2017-03-06 15:08:37.063 */
    public static final long updatedMS = 1488787717063L;
    /** AD_Table_ID=1000128 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_ClosingPeriodLine");
        
    }
    ;
    
    /** TableName=XX_ClosingPeriodLine */
    public static final String Table_Name="XX_ClosingPeriodLine";
    
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
        if (Account_ID < 1) throw new IllegalArgumentException ("Account_ID is mandatory.");
        set_Value ("Account_ID", Integer.valueOf(Account_ID));
        
    }
    
    /** Get Account.
    @return Account used */
    public int getAccount_ID() 
    {
        return get_ValueAsInt("Account_ID");
        
    }
    
    /** Set Trx Organization.
    @param AD_OrgTrx_ID Performing or initiating organization */
    public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
    {
        if (AD_OrgTrx_ID <= 0) set_Value ("AD_OrgTrx_ID", null);
        else
        set_Value ("AD_OrgTrx_ID", Integer.valueOf(AD_OrgTrx_ID));
        
    }
    
    /** Get Trx Organization.
    @return Performing or initiating organization */
    public int getAD_OrgTrx_ID() 
    {
        return get_ValueAsInt("AD_OrgTrx_ID");
        
    }
    
    /** Set Accounted Credit.
    @param AmtAcctCr Accounted Credit Amount */
    public void setAmtAcctCr (java.math.BigDecimal AmtAcctCr)
    {
        set_Value ("AmtAcctCr", AmtAcctCr);
        
    }
    
    /** Get Accounted Credit.
    @return Accounted Credit Amount */
    public java.math.BigDecimal getAmtAcctCr() 
    {
        return get_ValueAsBigDecimal("AmtAcctCr");
        
    }
    
    /** Set Accounted Debit.
    @param AmtAcctDr Accounted Debit Amount */
    public void setAmtAcctDr (java.math.BigDecimal AmtAcctDr)
    {
        set_Value ("AmtAcctDr", AmtAcctDr);
        
    }
    
    /** Get Accounted Debit.
    @return Accounted Debit Amount */
    public java.math.BigDecimal getAmtAcctDr() 
    {
        return get_ValueAsBigDecimal("AmtAcctDr");
        
    }
    
    /** Set Closing Period.
    @param XX_ClosingPeriod_ID Closing period */
    public void setXX_ClosingPeriod_ID (int XX_ClosingPeriod_ID)
    {
        if (XX_ClosingPeriod_ID < 1) throw new IllegalArgumentException ("XX_ClosingPeriod_ID is mandatory.");
        set_Value ("XX_ClosingPeriod_ID", Integer.valueOf(XX_ClosingPeriod_ID));
        
    }
    
    /** Get Closing Period.
    @return Closing period */
    public int getXX_ClosingPeriod_ID() 
    {
        return get_ValueAsInt("XX_ClosingPeriod_ID");
        
    }
    
    /** Set Account.
    @param XX_ClosingPeriodLine_ID Account to be closed */
    public void setXX_ClosingPeriodLine_ID (int XX_ClosingPeriodLine_ID)
    {
        if (XX_ClosingPeriodLine_ID < 1) throw new IllegalArgumentException ("XX_ClosingPeriodLine_ID is mandatory.");
        set_ValueNoCheck ("XX_ClosingPeriodLine_ID", Integer.valueOf(XX_ClosingPeriodLine_ID));
        
    }
    
    /** Get Account.
    @return Account to be closed */
    public int getXX_ClosingPeriodLine_ID() 
    {
        return get_ValueAsInt("XX_ClosingPeriodLine_ID");
        
    }
    
    
}
