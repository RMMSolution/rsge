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
/** Generated Model for XX_PeriodAccountBalance
 *  @author Jorg Janke (generated) 
 *  @version Release 3.8.10 - $Id: GenerateModel.java 10534 2012-10-05 09:30:50Z ragrawal $ */
public class X_XX_PeriodAccountBalance extends PO
{
    /** Standard Constructor
    @param ctx context
    @param XX_PeriodAccountBalance_ID id
    @param trx transaction
    */
    public X_XX_PeriodAccountBalance (Ctx ctx, int XX_PeriodAccountBalance_ID, Trx trx)
    {
        super (ctx, XX_PeriodAccountBalance_ID, trx);
        
        /* The following are the mandatory fields for this object.
        
        if (XX_PeriodAccountBalance_ID == 0)
        {
            setProcessed (false);	// N
            setXX_PeriodAccountBalance_ID (0);
            setXX_SubsidiaryPeriodBalance_ID (0);
            
        }
        */
        
    }
    /** Load Constructor 
    @param ctx context
    @param rs result set 
    @param trx transaction
    */
    public X_XX_PeriodAccountBalance (Ctx ctx, ResultSet rs, Trx trx)
    {
        super (ctx, rs, trx);
        
    }
    /** Serial Version No */
    private static final long serialVersionUID = 27746234507789L;
    /** Last Updated Timestamp 2016-05-24 23:59:51.0 */
    public static final long updatedMS = 1464109191000L;
    /** AD_Table_ID=1000168 */
    public static final int Table_ID;
    
    static
    {
        Table_ID = get_Table_ID("XX_PeriodAccountBalance");
        
    }
    ;
    
    /** TableName=XX_PeriodAccountBalance */
    public static final String Table_Name="XX_PeriodAccountBalance";
    
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
    
    /** Set XX_PeriodAccountBalance_ID.
    @param XX_PeriodAccountBalance_ID XX_PeriodAccountBalance_ID */
    public void setXX_PeriodAccountBalance_ID (int XX_PeriodAccountBalance_ID)
    {
        if (XX_PeriodAccountBalance_ID < 1) throw new IllegalArgumentException ("XX_PeriodAccountBalance_ID is mandatory.");
        set_ValueNoCheck ("XX_PeriodAccountBalance_ID", Integer.valueOf(XX_PeriodAccountBalance_ID));
        
    }
    
    /** Get XX_PeriodAccountBalance_ID.
    @return XX_PeriodAccountBalance_ID */
    public int getXX_PeriodAccountBalance_ID() 
    {
        return get_ValueAsInt("XX_PeriodAccountBalance_ID");
        
    }
    
    /** Set Period Balance.
    @param XX_SubsidiaryPeriodBalance_ID Subsidiary's period balance */
    public void setXX_SubsidiaryPeriodBalance_ID (int XX_SubsidiaryPeriodBalance_ID)
    {
        if (XX_SubsidiaryPeriodBalance_ID < 1) throw new IllegalArgumentException ("XX_SubsidiaryPeriodBalance_ID is mandatory.");
        set_Value ("XX_SubsidiaryPeriodBalance_ID", Integer.valueOf(XX_SubsidiaryPeriodBalance_ID));
        
    }
    
    /** Get Period Balance.
    @return Subsidiary's period balance */
    public int getXX_SubsidiaryPeriodBalance_ID() 
    {
        return get_ValueAsInt("XX_SubsidiaryPeriodBalance_ID");
        
    }
    
    
}
