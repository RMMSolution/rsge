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
package rsge.report;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

import org.compiere.framework.*;
import org.compiere.model.*;

import rsge.po.X_Ref__Financial_Summary_Type;
import rsge.report.MReportSource;
import org.compiere.util.*;


/**
 *	Report Line Model
 *
 *  @author Jorg Janke
 *  @version $Id: MReportLine.java,v 1.3 2006/08/03 22:16:52 jjanke Exp $
 */
public class MReportLine extends X_PA_ReportLine
{
    /** Logger for class MReportLine */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MReportLine.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 	Constructor
	 * 	@param ctx context
	 * 	@param PA_ReportLine_ID id
	 * 	@param trx transaction
	 */
	public MReportLine (Ctx ctx, int PA_ReportLine_ID, Trx trx)
	{
		super (ctx, PA_ReportLine_ID, trx);
		if (PA_ReportLine_ID == 0)
		{
			setSeqNo (0);
		//	setIsSummary (false);		//	not active in DD 
			setIsPrinted (false);
		}
		else
			loadSources();
	}	//	MReportLine

	/**
	 * 	Constructor
	 * 	@param ctx context
	 * 	@param rs ResultSet to load from
	 * 	@param trx transaction
	 */
	public MReportLine (Ctx ctx, ResultSet rs, Trx trx)
	{
		super(ctx, rs, trx);
		loadSources();
	}	//	MReportLine

	/**	Containt Sources				*/
	private MReportSource[]		m_sources = null;
	/** Cache result					*/
	private String				m_whereClause = null;
	
    /** Current Asset = CA */
    public static final String FINANCIALSUMMARYTYPE_CurrentAsset = X_Ref__Financial_Summary_Type.CURRENT_ASSET.getValue();
    /** Current Liability = CL */
    public static final String FINANCIALSUMMARYTYPE_CurrentLiability = X_Ref__Financial_Summary_Type.CURRENT_LIABILITY.getValue();
    /** COGM = CM */
    public static final String FINANCIALSUMMARYTYPE_COGM = X_Ref__Financial_Summary_Type.COGM.getValue();
    /** COGS = CS */
    public static final String FINANCIALSUMMARYTYPE_COGS = X_Ref__Financial_Summary_Type.COGS.getValue();
    /** EAT = EA */
    public static final String FINANCIALSUMMARYTYPE_EAT = X_Ref__Financial_Summary_Type.EAT.getValue();
    /** EBIT = EB */
    public static final String FINANCIALSUMMARYTYPE_EBIT = X_Ref__Financial_Summary_Type.EBIT.getValue();
    /** EBITDA = ED */
    public static final String FINANCIALSUMMARYTYPE_EBITDA = X_Ref__Financial_Summary_Type.EBITDA.getValue();
    /** Gross Profit = GP */
    public static final String FINANCIALSUMMARYTYPE_GrossProfit = X_Ref__Financial_Summary_Type.GROSS_PROFIT.getValue();
    /** Operating Profit = OP */
    public static final String FINANCIALSUMMARYTYPE_OperatingProfit = X_Ref__Financial_Summary_Type.OPERATING_PROFIT.getValue();
    /** Total Asset = TA */
    public static final String FINANCIALSUMMARYTYPE_TotalAsset = X_Ref__Financial_Summary_Type.TOTAL_ASSET.getValue();
    /** Total Expense = TE */
    public static final String FINANCIALSUMMARYTYPE_TotalExpense = X_Ref__Financial_Summary_Type.TOTAL_EXPENSE.getValue();
    /** Total Liability = TL */
    public static final String FINANCIALSUMMARYTYPE_TotalLiability = X_Ref__Financial_Summary_Type.TOTAL_LIABILITY.getValue();
    /** Total Equity = TO */
    public static final String FINANCIALSUMMARYTYPE_TotalEquity = X_Ref__Financial_Summary_Type.TOTAL_EQUITY.getValue();
    /** Total Revenue = TR */
    public static final String FINANCIALSUMMARYTYPE_TotalRevenue = X_Ref__Financial_Summary_Type.TOTAL_REVENUE.getValue();
    /** Set Summary Type.
    @param FinancialSummaryType Financial summary type */
    public void setFinancialSummaryType (String FinancialSummaryType)
    {
        if (!X_Ref__Financial_Summary_Type.isValid(FinancialSummaryType))
        throw new IllegalArgumentException ("FinancialSummaryType Invalid value - " + FinancialSummaryType + " - Reference_ID=1002624 - CA - CL - CM - CS - EA - EB - ED - TA - TE - TL - TO - TR");
        set_Value ("FinancialSummaryType", FinancialSummaryType);
        
    }
    
    /** Get Summary Type.
    @return Financial summary type */
    public String getFinancialSummaryType() 
    {
        return (String)get_Value("FinancialSummaryType");
        
    }
    
    /** Set Negative value.
    @param IsNegativeValue This line will show negative value */
    public void setIsNegativeValue (boolean IsNegativeValue)
    {
        set_Value ("IsNegativeValue", Boolean.valueOf(IsNegativeValue));
        
    }
    
    /** Get Negative value.
    @return This line will show negative value */
    public boolean isNegativeValue() 
    {
        return get_ValueAsBoolean("IsNegativeValue");
        
    }

	/**
	 * 	Load contained Sources
	 */
	private void loadSources()
	{
		ArrayList<MReportSource> list = new ArrayList<MReportSource>();
		String sql = "SELECT * FROM PA_ReportSource WHERE PA_ReportLine_ID=? AND IsActive='Y'";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_Trx());
			pstmt.setInt(1, getPA_ReportLine_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MReportSource (getCtx(), rs, null));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, null, e);
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close ();
			}
			catch (Exception e)
			{}
			pstmt = null;
		}
		//
		m_sources = new MReportSource[list.size()];
		list.toArray(m_sources);
		log.finest("ID=" + getPA_ReportLine_ID()
			+ " - Size=" + list.size());
	}	//	loadSources

	/**
	 * 	Get Sources
	 * 	@return sources
	 */
	public MReportSource[] getSources()
	{
		if (LINETYPE_SegmentValue.equals(getLineType()))
			return m_sources;
		return new MReportSource[0];
	}	//	getSources

	/**
	 * 	List Info to System.out
	 */
	public void list()
	{
		if (m_sources == null)
			return;
		for (MReportSource element : m_sources)
			System.out.println("  - " + element.toString());
	}	//	list


	/**
	 * 	Get Source Column Name
	 * 	@return Source ColumnName
	 */
	public String getSourceColumnName()
	{
		String ColumnName = null;
		for (MReportSource element : m_sources) {
			String col = MAcctSchemaElement.getColumnName (element.getElementType());
			if (ColumnName == null || ColumnName.length() == 0)
				ColumnName = col;
			else if (!ColumnName.equals(col))
			{
				log.config("More than one: " + ColumnName + " - " + col);
				return null;
			}
		}
		return ColumnName;
	}	//	getColumnName

	/**
	 *  Get Value Query for Segment Type
	 * 	@return Query for first source element or null
	 */
	public String getSourceValueQuery()
	{
		if (m_sources != null && m_sources.length > 0)
			return MAcctSchemaElement.getValueQuery(m_sources[0].getElementType());
		return null;
	}	//


	/**
	 * 	Get SQL Select Clause.
	 * 	@param withSum with SUM() function
	 * 	@return select clause - AmtAcctCR+AmtAcctDR/etc or "null" if not defined
	 */
	public String getSelectClause (boolean withSum)
	{
		String at = getAmountType().substring(0,1);	//	first letter
		StringBuffer sb = new StringBuffer();
		if (withSum)
			sb.append("SUM(");
		if (AmountType_Balance.equals(at))
		//	sb.append("AmtAcctDr-AmtAcctCr");
			sb.append("acctBalance(Account_ID,AmtAcctDr,AmtAcctCr)");
		else if (AmountType_CR.equals(at))
			sb.append("AmtAcctCr");
		else if (AmountType_DR.equals(at))
			sb.append("AmtAcctDr");
		else if (AmountType_Qty.equals(at))
			sb.append("Qty");
		else
		{
			log.log(Level.SEVERE, "AmountType=" + getAmountType () + ", at=" + at);
			return "NULL";
		}
		if (withSum)
			sb.append(")");
		return sb.toString();
	}	//	getSelectClause

	/**
	 * 	Is it Period ?
	 * 	@return true if Period Amount Type
	 */
	public boolean isPeriod()
	{
		String at = getAmountType();
		if (at == null)
			return false;
		return AMOUNTTYPE_PeriodBalance.equals(at)
			|| AMOUNTTYPE_PeriodCreditOnly.equals(at)
			|| AMOUNTTYPE_PeriodDebitOnly.equals(at)
			|| AMOUNTTYPE_PeriodQuantity.equals(at);
	}	//	isPeriod

	/**
	 * 	Is it Year ?
	 * 	@return true if Year Amount Type
	 */
	public boolean isYear()
	{
		String at = getAmountType();
		if (at == null)
			return false;
		return AMOUNTTYPE_YearBalance.equals(at)
			|| AMOUNTTYPE_YearCreditOnly.equals(at)
			|| AMOUNTTYPE_YearDebitOnly.equals(at)
			|| AMOUNTTYPE_YearQuantity.equals(at);
	}	//	isYear

	/**
	 * 	Is it Total ?
	 * 	@return true if Year Amount Type
	 */
	public boolean isTotal()
	{
		String at = getAmountType();
		if (at == null)
			return false;
		return AMOUNTTYPE_TotalBalance.equals(at)
			|| AMOUNTTYPE_TotalCreditOnly.equals(at)
			|| AMOUNTTYPE_TotalDebitOnly.equals(at)
			|| AMOUNTTYPE_TotalQuantity.equals(at);
	}	//	isTotal

	/**
	 * 	Get SQL where clause (sources, posting type)
	 * 	@param PA_Hierarchy_ID hierarchy
	 * 	@return where clause
	 */
	public String getWhereClause(int PA_Hierarchy_ID)
	{
		if (m_sources == null)
			return "";
		if (m_whereClause == null)
		{
			//	Only one
			if (m_sources.length == 0)
				m_whereClause = "";
			else if (m_sources.length == 1)
				m_whereClause = m_sources[0].getWhereClause(PA_Hierarchy_ID);
			else
			{
				//	Multiple
				StringBuffer sb = new StringBuffer ("(");
				for (int i = 0; i < m_sources.length; i++)
				{
					if (i > 0)
					{	// Edit by Fanny
						if(m_sources[i].getAndOr().equalsIgnoreCase(MReportSource.ANDOR_Or))
							sb.append (" OR ");
						else
							sb.append (" AND ");
						//
					}
					sb.append (m_sources[i].getWhereClause(PA_Hierarchy_ID));
				}
				sb.append (")");
				m_whereClause = sb.toString ();
			}
			//	Posting Type
			String PostingType = getPostingType();
			if (PostingType != null && PostingType.length() > 0)
			{
				if (m_whereClause.length() > 0)
					m_whereClause += " AND ";
				m_whereClause += "PostingType='" + PostingType + "'";
			}
			log.fine(m_whereClause);
		}
		return m_whereClause;
	}	//	getWhereClause

	/**
	 * 	Has Posting Type
	 *	@return true if posting
	 */
	public boolean isPostingType()
	{
		String PostingType = getPostingType();
		return (PostingType != null && PostingType.length() > 0);
	}	//	isPostingType

	/**
	 * 	String Representation
	 * 	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MReportLine[")
			.append(get_ID()).append("-").append(getName()).append("-").append(getDescription())
			.append(", SeqNo=").append(getSeqNo()).append(",AmountType=").append(getAmountType())
			.append(",LineType=").append(getLineType());
		if (isLineTypeCalculation())
			sb.append("-Calculation=").append(getCalculationType())
				.append(":").append(getOper_1_ID()).append(":").append(getOper_2_ID());
		else	//	SegmentValue
			sb.append("-SegmentValue-PostingType=").append(getPostingType())
				.append(",AmountType=").append(getAmountType());
		sb.append ("]");
		return sb.toString ();
	}	//	toString

	//	First Letter
	static final String		AmountType_Balance = "B";
	static final String		AmountType_CR = "C";
	static final String		AmountType_DR = "D";
	static final String		AmountType_Qty = "Q";
	//	Second Letter
	static final String		AmountType_Period = "P";
	static final String		AmountType_Year = "Y";
	static final String		AmountType_Total = "T";

	/**
	 * 	Line Type Calculation
	 *	@return true if calculation
	 */
	public boolean isLineTypeCalculation()
	{
		return LINETYPE_Calculation.equals(getLineType());
	}
	/**
	 * 	Line Type Segment Value
	 *	@return true if segment value
	 */
	public boolean isLineTypeSegmentValue()
	{
		return LINETYPE_SegmentValue.equals(getLineType());
	}
	
	/**
	 * 	Calculation Type Range
	 *	@return true if range
	 */
	public boolean isCalculationTypeRange()
	{
		return CALCULATIONTYPE_AddRangeOp1ToOp2.equals(getCalculationType());
	}
	/**
	 * 	Calculation Type Add
	 *	@return true if add
	 */
	public boolean isCalculationTypeAdd()
	{
		return CALCULATIONTYPE_AddOp1PlusOp2.equals(getCalculationType());
	}
	/**
	 * 	Calculation Type Subtract
	 *	@return true if subtract
	 */
	public boolean isCalculationTypeSubtract()
	{
		return CALCULATIONTYPE_SubtractOp1_Op2.equals(getCalculationType());
	}
	/**
	 * 	Calculation Type Percent
	 *	@return true if percent
	 */
	public boolean isCalculationTypePercent()
	{
		return CALCULATIONTYPE_PercentageOp1OfOp2.equals(getCalculationType());
	}
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (LINETYPE_SegmentValue.equals(getLineType()))
		{
			if (getCalculationType() != null)
				setCalculationType(null);
			if (getOper_1_ID() != 0)
				setOper_1_ID(0);
			if (getOper_2_ID() != 0)
				setOper_2_ID(0);
		}
		return true;
	}	//	beforeSave
	

	/**************************************************************************
	 * 	Copy
	 * 	@param ctx context
	 * 	@param AD_Client_ID parent
	 * 	@param AD_Org_ID parent
	 * 	@param PA_ReportLineSet_ID parent
	 * 	@param source copy source
	 * 	@param trx transaction
	 * 	@return Report Line
	 */
	public static MReportLine copy (Ctx ctx, int AD_Client_ID, int AD_Org_ID, 
		int PA_ReportLineSet_ID, MReportLine source, Trx trx)
	{
		MReportLine retValue = new MReportLine (ctx, 0, trx);
		PO.copyValues(source, retValue, AD_Client_ID, AD_Org_ID);
		//
		retValue.setPA_ReportLineSet_ID(PA_ReportLineSet_ID);
		retValue.setOper_1_ID(0);
		retValue.setOper_2_ID(0);
		return retValue;
	}	//	copy


}	//	MReportLine
