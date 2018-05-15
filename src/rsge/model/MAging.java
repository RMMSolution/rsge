/******************************************************************************
 * Product: Compiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 3600 Bridge Parkway #102, Redwood City, CA 94065, USA      *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package rsge.model;

import java.math.*;
import java.sql.*;

import org.compiere.model.X_T_Aging;
import org.compiere.util.*;

/**
 *	Aging Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MAging.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MAging extends X_T_Aging
{
    /** Logger for class MAging */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MAging.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MAging (Ctx ctx, int T_Aging_ID, Trx trx)
	{
		super (ctx, T_Aging_ID, trx);
		if (T_Aging_ID == 0)
		{
		//	setAD_PInstance_ID (0);
		//	setC_BP_Group_ID (0);
		//	setC_BPartner_ID (0);
		//	setC_Currency_ID (0);
			//
			setDueAmt (Env.ZERO);
			setDue0 (Env.ZERO);
			setDue0_7 (Env.ZERO);
			setDue0_30 (Env.ZERO);
			setDue1_7 (Env.ZERO);
			setDue31_60 (Env.ZERO);
			setDue31_Plus (Env.ZERO);
			setDue61_90 (Env.ZERO);
			setDue91_120 (Env.ZERO);
			setDue61_Plus (Env.ZERO);
			setDue8_30 (Env.ZERO);
			setDue91_Plus (Env.ZERO);
			setDue121_Plus(Env.ZERO);
			//
			setPastDueAmt (Env.ZERO);
			setPastDue1_7 (Env.ZERO);
			setPastDue1_30 (Env.ZERO);
			setPastDue31_60 (Env.ZERO);
			setPastDue31_Plus (Env.ZERO);
			setPastDue61_90 (Env.ZERO);
			setPastDue61_Plus (Env.ZERO);
			setPastDue8_30 (Env.ZERO);
			setPastDue91_Plus (Env.ZERO);
			setPastDue91_120 (Env.ZERO);
			setPastDue121_Plus(Env.ZERO);
			//
			setOpenAmt(Env.ZERO);
			setInvoicedAmt(Env.ZERO);
			//
			setIsListInvoices (false);
			setIsSOTrx (false);
		//	setDueDate (new Timestamp(System.currentTimeMillis()));
		//	setStatementDate (new Timestamp(System.currentTimeMillis()));
		}
	}	//	T_Aging

	/**
	 * 	Full Constructor
	 *	@param ctx context
	 *	@param AD_PInstance_ID instance
	 *	@param StatementDate statement date
	 *	@param C_BPartner_ID bpartner
	 *	@param C_Currency_ID currency
	 *	@param C_Invoice_ID invoice
	 *	@param C_InvoicePaySchedule_ID invoice schedule
	 *	@param C_BP_Group_ID group
	 *	@param DueDate due date
	 *	@param IsSOTrx SO Trx
	 *	@param trx transaction
	 */
	public MAging (Ctx ctx, int AD_PInstance_ID, Timestamp StatementDate, 
		int C_BPartner_ID, int C_Currency_ID, 
		int C_Invoice_ID, int C_InvoicePaySchedule_ID,
		int C_BP_Group_ID, Timestamp DueDate, boolean IsSOTrx, Trx trx)
	{
		this (ctx, 0, trx);
		setAD_PInstance_ID (AD_PInstance_ID);
		setStatementDate(StatementDate);
		//
		setC_BPartner_ID (C_BPartner_ID);
		setC_Currency_ID (C_Currency_ID);
		setC_BP_Group_ID (C_BP_Group_ID);
		setIsSOTrx (IsSOTrx);

		//	Optional
	//	setC_Invoice_ID (C_Invoice_ID);		// may be zero
		set_ValueNoCheck ("C_Invoice_ID", Integer.valueOf(C_Invoice_ID));
	//	setC_InvoicePaySchedule_ID(C_InvoicePaySchedule_ID);	//	may be zero
		set_ValueNoCheck ("C_InvoicePaySchedule_ID", Integer.valueOf(C_InvoicePaySchedule_ID));
		setIsListInvoices(C_Invoice_ID != 0);
		//
		setDueDate(DueDate);		//	only sensible if List invoices
	}	//	MAging

	
	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trx transaction
	 */
	public MAging (Ctx ctx, ResultSet rs, Trx trx)
	{
		super(ctx, rs, trx);
	}	//	MAging

	/** Number of items 		*/
	private int		m_noItems = 0;
	/** Sum of Due Days			*/
	private int		m_daysDueSum = 0;
	
	/**
	 * 	Add Amount to Buckets
	 *	@param DueDate due date 
	 *	@param daysDue positive due - negative not due
	 *	@param invoicedAmt invoiced amount
	 *	@param openAmt open amount
	 */
	public void add (Timestamp DueDate, int daysDue, BigDecimal invoicedAmt, BigDecimal openAmt)
	{
		if (invoicedAmt == null)
			invoicedAmt = Env.ZERO;
		setInvoicedAmt(getInvoicedAmt().add(invoicedAmt));
		if (openAmt == null)
			openAmt = Env.ZERO;
		setOpenAmt(getOpenAmt().add(openAmt));
		//	Days Due
		m_noItems++;
		m_daysDueSum += daysDue;
		setDaysDue(m_daysDueSum/m_noItems);
		//	Due Date
		if (getDueDate().after(DueDate))
			setDueDate(DueDate);		//	earliest
		//
		BigDecimal amt = openAmt;
		//	Not due - negative
		if (daysDue <= 0)
		{
			setDueAmt (getDueAmt().add(amt));
			if (daysDue == 0)
				setDue0 (getDue0().add(amt));
				
			if (daysDue >= -7)
				setDue0_7 (getDue0_7().add(amt));
				
			if (daysDue >= -30)
				setDue0_30 (getDue0_30().add(amt));
				
			if (daysDue <= -1 && daysDue >= -7)
				setDue1_7 (getDue1_7().add(amt));
				
			if (daysDue <= -8 && daysDue >= -30)
				setDue8_30 (getDue8_30().add(amt));
				
			if (daysDue <= -31 && daysDue >= -60)
				setDue31_60 (getDue31_60().add(amt));
				
			if (daysDue <= -31)
				setDue31_Plus (getDue31_Plus().add(amt));
				
			if (daysDue <= -61 && daysDue >= -90)
				setDue61_90 (getDue61_90().add(amt));
				
			if (daysDue <= -91 && daysDue >= -120)
				setDue91_120 (getDue91_120().add(amt));
			
			if (daysDue <= -91 && daysDue >= -365)
				setDue91_365 (getDue91_365().add(amt));

			if (daysDue <= -61)
				setDue61_Plus (getDue61_Plus().add(amt));
				
			if (daysDue <= -91)
				setDue91_Plus (getDue91_Plus().add(amt));

			if (daysDue <= -121)
				setDue121_Plus (getDue121_Plus().add(amt));

		}
		else	//	Due = positive (> 1)
		{
			setPastDueAmt (getPastDueAmt().add(amt));
			if (daysDue <= 7)
				setPastDue1_7 (getPastDue1_7().add(amt));
				
			if (daysDue <= 30)
				setPastDue1_30 (getPastDue1_30().add(amt));
				
			if (daysDue >= 8 && daysDue <= 30)
				setPastDue8_30 (getPastDue8_30().add(amt));
			
			if (daysDue >= 31 && daysDue <= 60)
				setPastDue31_60 (getPastDue31_60().add(amt));
				
			if (daysDue >= 31)
				setPastDue31_Plus (getPastDue31_Plus().add(amt));
			
			if (daysDue >= 61 && daysDue <= 90)
				setPastDue61_90 (getPastDue61_90().add(amt));
			
			if (daysDue >= 91 && daysDue <= 120)
				setPastDue91_120 (getPastDue91_120().add(amt));
				
			if (daysDue >= 61)
				setPastDue61_Plus (getPastDue61_Plus().add(amt));
				
			if (daysDue >= 91)
				setPastDue91_Plus (getPastDue91_Plus().add(amt));

			if (daysDue >= 121)
				setPastDue121_Plus (getPastDue121_Plus().add(amt));

		}
	}	//	add

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MAging[");
		sb.append("AD_PInstance_ID=").append(getAD_PInstance_ID())
			.append(",C_BPartner_ID=").append(getC_BPartner_ID())
			.append(",C_Currency_ID=").append(getC_Currency_ID())
			.append(",C_Invoice_ID=").append(getC_Invoice_ID());
		sb.append("]");
		return sb.toString();
	} //	toString
	
	
    /** Set Due 91-120.
    @param Due91_120 Due 91-120 */
    public void setDue91_120 (java.math.BigDecimal Due91_120)
    {
        if (Due91_120 == null) throw new IllegalArgumentException ("Due91_120 is mandatory.");
        set_Value ("Due91_120", Due91_120);
        
    }
    
    /** Get Due 91-120.
    @return Due 91-120 */
    public java.math.BigDecimal getDue91_120() 
    {
        return get_ValueAsBigDecimal("Due91_120");
        
    }

	
	/** Set Past Due 91-120.
    @param PastDue91_120 Past Due 91-120 */
    public void setPastDue91_120 (java.math.BigDecimal PastDue91_120)
    {
        if (PastDue91_120 == null) throw new IllegalArgumentException ("PastDue91_120 is mandatory.");
        set_Value ("PastDue91_120", PastDue91_120);
        
    }
    
    /** Get Past Due 91-120.
    @return Past Due 91-120 */
    public java.math.BigDecimal getPastDue91_120() 
    {
        return get_ValueAsBigDecimal("PastDue91_120");
        
    }
    
	/** Set Past Due 91-365.
    @param PastDue91_365 Past Due 91-365 */
    public void setPastDue91_365 (java.math.BigDecimal PastDue91_365)
    {
        if (PastDue91_365 == null) throw new IllegalArgumentException ("PastDue91_365 is mandatory.");
        set_Value ("PastDue91_365", PastDue91_365);
        
    }
    
    /** Get Past Due 91-365.
    @return Past Due 91-365 */
    public java.math.BigDecimal getPastDue91_365() 
    {
        return get_ValueAsBigDecimal("PastDue91_365");
        
    }

	/** Set Due 91-365.
    @param Due91_365 Due 91-365 */
    public void setDue91_365 (java.math.BigDecimal Due91_365)
    {
        if (Due91_365 == null) throw new IllegalArgumentException ("Due91_365 is mandatory.");
        set_Value ("Due91_365", Due91_365);
        
    }
    
    /** Get Due 91-365.
    @return Due 91-365 */
    public java.math.BigDecimal getDue91_365() 
    {
        return get_ValueAsBigDecimal("Due91_365");
        
    }

	
    /** Set Due > 121.
    @param Due121_Plus Due > 121 */
    public void setDue121_Plus (java.math.BigDecimal Due121_Plus)
    {
        if (Due121_Plus == null) throw new IllegalArgumentException ("Due121_Plus is mandatory.");
        set_Value ("Due121_Plus", Due121_Plus);
        
    }
    
    /** Get Due > 121.
    @return Due > 121 */
    public java.math.BigDecimal getDue121_Plus() 
    {
        return get_ValueAsBigDecimal("Due121_Plus");
        
    }

    /** Set Past Due > 121.
    @param PastDue121_Plus Past Due > 121 */
    public void setPastDue121_Plus (java.math.BigDecimal PastDue121_Plus)
    {
        if (PastDue121_Plus == null) throw new IllegalArgumentException ("PastDue121_Plus is mandatory.");
        set_Value ("PastDue121_Plus", PastDue121_Plus);
        
    }
    
    /** Get Past Due > 121.
    @return Past Due > 121 */
    public java.math.BigDecimal getPastDue121_Plus() 
    {
        return get_ValueAsBigDecimal("PastDue121_Plus");
        
    }


}	//	MAging
