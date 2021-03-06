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
package rsge.acct;

import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.*;
import org.compiere.util.*;

import rsge.acct.DocLine_Allocation;
import rsge.acct.Doc_Order;
import rsge.model.MAcctSchemaDefault;
import rsge.model.MAllocationLine;
import rsge.model.MAllocationHdr;

/**
 *  Post Allocation Documents.
 *  <pre>
 *  Table:              C_AllocationHdr
 *  Document Types:     CMA
 *  </pre>
 *  @author Jorg Janke
 *  @version  $Id: Doc_Allocation.java 8780 2010-05-19 23:08:57Z nnayak $
 */
public class Doc_Allocation extends Doc
{
	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trx p_trx
	 */
	public Doc_Allocation (MAcctSchema[] ass, ResultSet rs, Trx trx)
	{
		super (ass, MAllocationHdr.class, rs, MDocBaseType.DOCBASETYPE_PaymentAllocation, trx);
	}   //  Doc_Allocation

	/**	Tolerance G&L				*/
	private static final BigDecimal	TOLERANCE = new BigDecimal (0.02);
	/** Facts						*/
	private ArrayList<Fact>		m_facts = null; 


	/**
	 *  Load Specific Document Details
	 *  @return error message or null
	 */
	@Override
	public String loadDocumentDetails()
	{
		MAllocationHdr alloc = (MAllocationHdr)getPO();
		setDateDoc(alloc.getDateTrx());
		//	Contained Objects
		p_lines = loadLines(alloc);
		return null;
	}   //  loadDocumentDetails

	/**
	 *	Load Invoice Line
	 *	@param alloc header
	 *  @return DocLine Array
	 */
	private DocLine[] loadLines(MAllocationHdr alloc)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		MAllocationLine[] lines = alloc.getAllocationLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MAllocationLine line = lines[i];
			if (!lines[i].isActive())
				continue;
			DocLine_Allocation docLine = new DocLine_Allocation(line, this);
			
			//	Get Payment Conversion Rate
			if (line.getC_Payment_ID() != 0)
			{
				MPayment payment = new MPayment (getCtx(), line.getC_Payment_ID(), getTrx());
				int C_ConversionType_ID = payment.getC_ConversionType_ID();
				docLine.setC_ConversionType_ID(C_ConversionType_ID);
			}
			//
			log.fine(docLine.toString());
			list.add (docLine);
		}

		//	Return Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}	//	loadLines

	
	/**************************************************************************
	 *  Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
	 *  @return positive amount, if total invoice is bigger than lines
	 */
	@Override
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for
	 *  CMA.
	 *  <pre>
	 *  AR_Invoice_Payment
	 *      UnAllocatedCash DR
	 *      or C_Prepayment
	 *      DiscountExp     DR
	 *      WriteOff        DR
	 *      Receivables             CR
	 *  AR_Invoice_Cash
	 *      CashTransfer    DR
	 *      DiscountExp     DR
	 *      WriteOff        DR
	 *      Receivables             CR
	 * 
	 *  AP_Invoice_Payment
	 *      Liability       DR
	 *      DiscountRev             CR
	 *      WriteOff                CR
	 *      PaymentSelect           CR
	 *      or V_Prepayment
	 *  AP_Invoice_Cash
	 *      Liability       DR
	 *      DiscountRev             CR
	 *      WriteOff                CR
	 *      CashTransfer            CR
	 *  CashBankTransfer
	 *      -
	 *  ==============================
	 *  Realized Gain & Loss
	 * 		AR/AP			DR		CR
	 * 		Realized G/L	DR		CR
	 * 
	 *
	 *  </pre>
	 *  Tax needs to be corrected for discount & write-off;
	 *  Currency gain & loss is realized here.
	 *  @param as accounting schema
	 *  @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{ 
		m_facts = new ArrayList<Fact>();
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);


		for (int i = 0; i < p_lines.length; i++)
		{
			DocLine_Allocation line = (DocLine_Allocation)p_lines[i];
			setC_BPartner_ID(line.getC_BPartner_ID());
			
			if(line.getM_XX_DisRealizationLineInv_ID()!=0)
				return m_facts;
			
			//  CashBankTransfer - all references null and Discount/WriteOff = 0
			if (line.getC_Payment_ID() != 0 
				&& line.getC_Invoice_ID() == 0 && line.getC_Order_ID() == 0
				&& line.getC_CashLine_ID() == 0 && line.getC_BPartner_ID() == 0
				&& Env.ZERO.compareTo(line.getDiscountAmt()) == 0
				&& Env.ZERO.compareTo(line.getWriteOffAmt()) == 0)
				continue;
		
			//	Receivables/Liability Amt
			BigDecimal allocationSource = line.getAmtSource()
				.add(line.getDiscountAmt())
				.add(line.getWriteOffAmt());
			BigDecimal allocationAccounted = Env.ZERO;	// AR/AP balance corrected

			FactLine fl = null;
			MAccount bpAcct = null;		//	Liability/Receivables
			//
			MPayment payment = null;
			if (line.getC_Payment_ID() != 0)
				payment = new MPayment (getCtx(), line.getC_Payment_ID(), getTrx());
			MInvoice invoice = null;
			if (line.getC_Invoice_ID() != 0)
				invoice = new MInvoice (getCtx(), line.getC_Invoice_ID(), null);
			
			//	No Invoice
			if (invoice == null)
			{
				//	Payment Only
				if (line.getC_Invoice_ID() == 0 && line.getC_Payment_ID() != 0)
				{
					if(line.getAmtSource().signum()>=0)
					{
						fl = fact.createLine (line, getPaymentAcct(as, line.getC_Payment_ID()),
								getC_Currency_ID(), line.getAmtSource(), null);						
					}
					else
					{
						fl = fact.createLine (line, getPaymentAcct(as, line.getC_Payment_ID()),
								getC_Currency_ID(), null, line.getAmtSource().negate());						

					}
					if (fl != null && payment != null)
						fl.setAD_Org_ID(payment.getAD_Org_ID());
					
					if(line.getC_Order_ID()!=0)
					{
						BigDecimal distAmount = line.getAmtSource();						
						if(distAmount.signum()<0)
							distAmount = distAmount.negate();
						BigDecimal baseAmt = distAmount;
						// Get Order TotalAmt
						MOrder order = new MOrder(getCtx(), line.getC_Order_ID(), getTrx());
						MCurrency currency = new MCurrency(getCtx(), getC_Currency_ID(), getTrx());
						BigDecimal totalLines = order.getTotalLines();
						MOrderLine[] oLines = getOrderLines(order);
						int size = oLines.length;
						for(int n = 0; n<size; n++)
						{
							MOrderLine oLine = oLines[n];							
							if(oLine.getLineNetAmt().signum()==0)
								continue;
							BigDecimal allocatedAmt = BigDecimal.ZERO;
							if(n+1 < size)
							{
								BigDecimal ratio = oLine.getLineNetAmt().divide(totalLines, 4, RoundingMode.HALF_EVEN);
								allocatedAmt = (baseAmt.multiply(ratio)).setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
								distAmount = distAmount.subtract(allocatedAmt);
							}
							else
								allocatedAmt = distAmount;
							
							int acctType = 0;
							if(order.isSOTrx())
							{
								if(oLine.getM_Product_ID()!=0)
									acctType = DocBaseType.ACCTTYPE_ProductCustomerPrepayment;
								else if(oLine.getC_Charge_ID()!=0)
									acctType = DocBaseType.ACCTTYPE_ChargeCustomerPrepayment;
							}
							else
							{
								if(oLine.getM_Product_ID()!=0)
									acctType = DocBaseType.ACCTTYPE_ProductVendorPrepayment;
								else if(oLine.getC_Charge_ID()!=0)
									acctType = DocBaseType.ACCTTYPE_ChargeVendorPrepayment;								
							}
							
							int validCombination = DocBaseType.getValidCombination_ID(acctType, oLine.getM_Product_ID(), oLine.getC_Charge_ID(), as);
							MAccount account = MAccount.get(getCtx(), validCombination);
							if(line.getAmtSource().signum()>=0)
							{
								fl = fact.createLine (line, account, getC_Currency_ID(), null, allocatedAmt);						
							}
							else
							{
								fl = fact.createLine (line, account,getC_Currency_ID(), allocatedAmt, null);						

							}							
							
						}
						
					}
				}
				else
				{
					p_Error = "Cannot determine SO/PO";
					log.log(Level.SEVERE, p_Error);
					return null;
				}
			}
			//	Sales Invoice	
			else if (invoice.isSOTrx())
			{
				//	Payment/Cash	DR
				if (line.getC_Payment_ID() != 0)
				{
					fl = fact.createLine (line, getPaymentAcct(as, line.getC_Payment_ID()),
						getC_Currency_ID(), line.getAmtSource(), null);
					if (fl != null && payment != null)
						fl.setAD_Org_ID(payment.getAD_Org_ID());
				}
				else if (line.getC_CashLine_ID() != 0)
				{
					fl = fact.createLine (line, getCashAcct(as, line.getC_CashLine_ID()),
						getC_Currency_ID(), line.getAmtSource(), null);
					MCashLine cashLine = new MCashLine (getCtx(), line.getC_CashLine_ID(), getTrx());
					if (fl != null && cashLine.get_ID() != 0)
						fl.setAD_Org_ID(cashLine.getAD_Org_ID());
				}
				//	Discount		DR
				if (Env.ZERO.compareTo(line.getDiscountAmt()) != 0)
				{
					fl = fact.createLine (line, getAccount(Doc.ACCTTYPE_DiscountExp, as),
						getC_Currency_ID(), line.getDiscountAmt(), null);
					if (fl != null && payment != null)
						fl.setAD_Org_ID(payment.getAD_Org_ID());
				}
				//	Write off		DR
				if (Env.ZERO.compareTo(line.getWriteOffAmt()) != 0)
				{
					fl = fact.createLine (line, getAccount(Doc.ACCTTYPE_WriteOff, as),
						getC_Currency_ID(), line.getWriteOffAmt(), null);
					if (fl != null && payment != null)
						fl.setAD_Org_ID(payment.getAD_Org_ID());
				}
						
				//	AR Invoice Amount	CR
				if (as.isAccrual())
				{
					bpAcct = getAccount(Doc.ACCTTYPE_C_Receivable, as);
					// Modified by Fanny R
					// Use Return Account 
					if(invoice.isReturnTrx())
					{
						MAcctSchemaDefault asDefault = MAcctSchemaDefault.get(getCtx(), as.getC_AcctSchema_ID());
						if(asDefault.isUseReturnAccount())
							bpAcct = DocBaseType.getAccount(DocBaseType.ACCTTYPE_UnallocatedReturn, 0, 0, as);
					}
					//
					if(allocationSource.signum()<0)
					{
						fl = fact.createLine (line, bpAcct,
								getC_Currency_ID(), allocationSource.negate(), null);		//	payment currency
					}
					else						
					{
						fl = fact.createLine (line, bpAcct,
									getC_Currency_ID(), null, allocationSource);		//	payment currency 
					}
					if (fl != null)
						allocationAccounted = fl.getAcctBalance();
					// 
					if (fl != null && invoice != null)
						fl.setAD_Org_ID(invoice.getAD_Org_ID());
				}
				else	//	Cash Based
				{
					allocationAccounted = createCashBasedAcct (as, fact, 
						invoice, allocationSource);
				}
			}
			//	Purchase Invoice
			else
			{
				allocationSource = allocationSource.negate();	//	allocation is negative
				//	AP Invoice Amount	DR
				if (as.isAccrual())
				{
					bpAcct = getAccount(Doc.ACCTTYPE_V_Liability, as);
					fl = fact.createLine (line, bpAcct,
						getC_Currency_ID(), allocationSource, null);		//	payment currency
					if (fl != null)
						allocationAccounted = fl.getAcctBalance();
					if (fl != null && invoice != null)
						fl.setAD_Org_ID(invoice.getAD_Org_ID());
				}
				else	//	Cash Based
				{
					allocationAccounted = createCashBasedAcct (as, fact, 
						invoice, allocationSource);
				}
							
				//	Discount		CR
				if (Env.ZERO.compareTo(line.getDiscountAmt()) != 0)
				{
					fl = fact.createLine (line, getAccount(Doc.ACCTTYPE_DiscountRev, as),
						getC_Currency_ID(), null, line.getDiscountAmt().negate());
					if (fl != null && payment != null)
						fl.setAD_Org_ID(payment.getAD_Org_ID());
				}
				//	Write off		CR
				if (Env.ZERO.compareTo(line.getWriteOffAmt()) != 0)
				{
					fl = fact.createLine (line, getAccount(Doc.ACCTTYPE_WriteOff, as),
						getC_Currency_ID(), null, line.getWriteOffAmt().negate());
					if (fl != null && payment != null)
						fl.setAD_Org_ID(payment.getAD_Org_ID());
				}
				//	Payment/Cash	CR
				if (line.getC_Payment_ID() != 0)
				{
					fl = fact.createLine (line, getPaymentAcct(as, line.getC_Payment_ID()),
						getC_Currency_ID(), null, line.getAmtSource().negate());
					if (fl != null && payment != null)
						fl.setAD_Org_ID(payment.getAD_Org_ID());
				}
				else if (line.getC_CashLine_ID() != 0)
				{
					fl = fact.createLine (line, getCashAcct(as, line.getC_CashLine_ID()),
						getC_Currency_ID(), null, line.getAmtSource().negate());
					MCashLine cashLine = new MCashLine (getCtx(), line.getC_CashLine_ID(), getTrx());
					if (fl != null && cashLine.get_ID() != 0)
						fl.setAD_Org_ID(cashLine.getAD_Org_ID());
				}
			}
			
			//	VAT Tax Correction
			if (invoice != null && as.isTaxCorrection())
			{
				BigDecimal taxCorrectionAmt = Env.ZERO;
				if (as.isTaxCorrectionDiscount())
					taxCorrectionAmt = line.getDiscountAmt();
				if (as.isTaxCorrectionWriteOff())
					taxCorrectionAmt = taxCorrectionAmt.add(line.getWriteOffAmt());
				//
				if (taxCorrectionAmt.signum() != 0)
				{
					if (!createTaxCorrection(as, fact, line, 
						getAccount(invoice.isSOTrx() ? Doc.ACCTTYPE_DiscountExp : Doc.ACCTTYPE_DiscountRev, as), 
						getAccount(Doc.ACCTTYPE_WriteOff, as)))
					{
						p_Error = "Cannot create Tax correction";
						return null;
					}
				}
			}
				
			//	Realized Gain & Loss
			if (invoice != null
				&& (getC_Currency_ID() != as.getC_Currency_ID()			//	payment allocation in foreign currency
					|| getC_Currency_ID() != line.getInvoiceC_Currency_ID()))	//	allocation <> invoice currency
			{
				p_Error = createRealizedGainLoss (as, fact, bpAcct, invoice, 
					allocationSource, allocationAccounted);
				if (p_Error != null)
					return null;
			}
			
		}	//	for all lines
		
		//	reset line info
		setC_BPartner_ID(0);
		//
		m_facts.add(fact);
		return m_facts;
	}   //  createFact
	
	private MOrderLine[] getOrderLines(MOrder order)
	{
		ArrayList<MOrderLine> lines = new ArrayList<>();
		String sql = "SELECT * FROM C_OrderLine WHERE C_Order_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, order.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, order.getC_Order_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				lines.add(new MOrderLine(order.getCtx(), rs, order.get_Trx()));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		MOrderLine[] retValue = new MOrderLine[lines.size()];
		lines.toArray(retValue);
		return retValue;
	}
		


	/**
	 * 	Create Cash Based Acct
	 * 	@param as accounting schema
	 *	@param fact fact
	 *	@param invoice invoice
	 *	@param allocationSource allocation amount (incl discount, writeoff)
	 *	@return Accounted Amt
	 */
	private BigDecimal createCashBasedAcct (MAcctSchema as, Fact fact, MInvoice invoice, 
		BigDecimal allocationSource)
	{
		BigDecimal allocationAccounted = Env.ZERO;
		//	Multiplier
		double percent = invoice.getGrandTotal().doubleValue() / allocationSource.doubleValue();
		if (percent > 0.99 && percent < 1.01)
			percent = 1.0;
		log.config("Multiplier=" + percent + " - GrandTotal=" + invoice.getGrandTotal()
			+ " - Allocation Source=" + allocationSource);
		
		//	Get Invoice Postings
		Doc_Invoice docInvoice = (Doc_Invoice)Doc.get(new MAcctSchema[]{as}, 
			X_C_Invoice.Table_ID, invoice.getC_Invoice_ID(), getTrx());
		docInvoice.loadDocumentDetails();
		allocationAccounted = docInvoice.createFactCash(as, fact, new BigDecimal(percent));
		log.config("Allocation Accounted=" + allocationAccounted);
		
		//	Cash Based Commitment Release 
		if (as.isCreateCommitment() && !invoice.isSOTrx())
		{
			MInvoiceLine[] lines = invoice.getLines();
			for (int i = 0; i < lines.length; i++)
			{
				Fact factC = Doc_Order.getCommitmentRelease(getCtx(), as, this, 
					lines[i].getQtyInvoiced(), lines[i].getC_InvoiceLine_ID(), new BigDecimal(percent));
				if (factC == null)
					return null;
				m_facts.add(factC);
			}
		}	//	Commitment
		
		return allocationAccounted;
	}	//	createCashBasedAcct

	
	/**
	 * 	Get Payment (Unallocated Payment or Payment Selection) Acct of Bank Account
	 *	@param as accounting schema
	 *	@param C_Payment_ID payment
	 *	@return acct
	 */
	private MAccount getPaymentAcct (MAcctSchema as, int C_Payment_ID)
	{
		setC_BankAccount_ID(0);
		//	Doc.ACCTTYPE_UnallocatedCash (AR) or C_Prepayment 
		//	or Doc.ACCTTYPE_PaymentSelect (AP) or V_Prepayment
		int accountType = Doc.ACCTTYPE_UnallocatedCash;
		//
		String sql = "SELECT p.C_BankAccount_ID, d.DocBaseType, p.IsReceipt, p.IsPrepayment "
			+ "FROM C_Payment p INNER JOIN C_DocType d ON (p.C_DocType_ID=d.C_DocType_ID) "
			+ "WHERE C_Payment_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, getTrx());
			pstmt.setInt (1, C_Payment_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				setC_BankAccount_ID(rs.getInt(1));
				if (MDocBaseType.DOCBASETYPE_APPayment.equals(rs.getString(2)))
					accountType = Doc.ACCTTYPE_PaymentSelect;
				//	Prepayment
				if ("Y".equals(rs.getString(4)))		//	Prepayment
				{
					if ("Y".equals(rs.getString(3)))	//	Receipt
						accountType = Doc.ACCTTYPE_C_Prepayment;
					else
						accountType = Doc.ACCTTYPE_V_Prepayment;
				}
			}
		} 
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		//
		if (getC_BankAccount_ID() <= 0)
		{
			log.log(Level.SEVERE, "NONE for C_Payment_ID=" + C_Payment_ID);
			return null;
		}
		return getAccount (accountType, as);
	}	//	getPaymentAcct
	
	/**
	 * 	Get Cash (Transfer) Acct of CashBook
	 *	@param as accounting schema
	 *	@param C_CashLine_ID
	 *	@return acct
	 */
	private MAccount getCashAcct (MAcctSchema as, int C_CashLine_ID)
	{
		String sql = "SELECT c.C_CashBook_ID "
			+ "FROM C_Cash c, C_CashLine cl "
			+ "WHERE c.C_Cash_ID=cl.C_Cash_ID AND cl.C_CashLine_ID=?";

		setC_CashBook_ID(QueryUtil.getSQLValue(getTrx(), sql, C_CashLine_ID));
		if (getC_CashBook_ID() <= 0)
		{
			log.log(Level.SEVERE, "NONE for C_CashLine_ID=" + C_CashLine_ID);
			return null;
		}
		return getAccount(Doc.ACCTTYPE_CashTransfer, as);
	}	//	getCashAcct
	

	/**************************************************************************
	 * 	Create Realized Gain & Loss.
	 * 	Compares the Accounted Amount of the Invoice to the
	 * 	Accounted Amount of the Allocation
	 *	@param as accounting schema
	 *	@param fact fact
	 *	@param acct account
	 *	@param invoice invoice
	 *	@param allocationSource source amt
	 *	@param allocationAccounted acct amt
	 *	@return Error Message or null if OK
	 */
	private String createRealizedGainLoss (MAcctSchema as, Fact fact, MAccount acct,
		MInvoice invoice, BigDecimal allocationSource, BigDecimal allocationAccounted)
	{
		BigDecimal invoiceSource = null;
		BigDecimal invoiceAccounted = null;
		//
		String sql = "SELECT " 
			+ (invoice.isSOTrx() 
				? "SUM(AmtSourceDr), SUM(AmtAcctDr)"	//	so 
				: "SUM(AmtSourceCr), SUM(AmtAcctCr)")	//	po
			+ " FROM Fact_Acct "
			+ "WHERE AD_Table_ID=318 AND Record_ID=?"	//	Invoice
			+ " AND C_AcctSchema_ID=?"
			+ " AND PostingType='A'";
		
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try
		{
			pstmt = DB.prepareStatement(sql, getTrx());
			pstmt.setInt(1, invoice.getC_Invoice_ID());
			pstmt.setInt(2, as.getC_AcctSchema_ID());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				invoiceSource = rs.getBigDecimal(1);
				invoiceAccounted = rs.getBigDecimal(2);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		// 	Requires that Invoice is Posted
		if (invoiceSource == null || invoiceAccounted == null)
			return "Gain/Loss - Invoice not posted yet";
		//
		String description = "Invoice=(" + invoice.getC_Currency_ID() + ")" + invoiceSource + "/" + invoiceAccounted
			+ " - Allocation=(" + getC_Currency_ID() + ")" + allocationSource + "/" + allocationAccounted;
		log.fine(description);
		//	Allocation not Invoice Currency
		if (getC_Currency_ID() != invoice.getC_Currency_ID())
		{
			BigDecimal allocationSourceNew = MConversionRate.convert(getCtx(), 
				allocationSource, getC_Currency_ID(), 
				invoice.getC_Currency_ID(), getDateAcct(), 
				invoice.getC_ConversionType_ID(), invoice.getAD_Client_ID(), invoice.getAD_Org_ID());
			if (allocationSourceNew == null)
				return "Gain/Loss - No Conversion from Allocation->Invoice";
			String d2 = "Allocation=(" + getC_Currency_ID() + ")" + allocationSource 
				+ "->(" + invoice.getC_Currency_ID() + ")" + allocationSourceNew;
			log.fine(d2);
			description += " - " + d2;
			allocationSource = allocationSourceNew;
		}

		BigDecimal acctDifference = null;	//	gain is negative
		//	Full Payment in currency
		if (allocationSource.compareTo(invoiceSource) == 0)
		{
			acctDifference = invoiceAccounted.subtract(allocationAccounted);	//	gain is negative
			String d2 = "(full) = " + acctDifference;
			log.fine(d2);
			description += " - " + d2;
		}
		else	//	partial or MC
		{
			//	percent of total payment
			double multiplier = allocationSource.doubleValue() / invoiceSource.doubleValue();
			//	Reduce Orig Invoice Accounted
			invoiceAccounted = invoiceAccounted.multiply(new BigDecimal(multiplier));
			//	Difference based on percentage of Orig Invoice
			acctDifference = invoiceAccounted.subtract(allocationAccounted);	//	gain is negative
			//	ignore Tolerance
			if (acctDifference.abs().compareTo(TOLERANCE) < 0)
				acctDifference = Env.ZERO;
			//	Round
			int precision = as.getStdPrecision();
			if (acctDifference.scale() > precision)
				acctDifference = acctDifference.setScale(precision, BigDecimal.ROUND_HALF_UP);
			String d2 = "(partial) = " + acctDifference + " - Multiplier=" + multiplier;
			log.fine(d2);
			description += " - " + d2;
		}
		
		if (acctDifference.signum() == 0)
		{
			log.fine("No Difference");
			return null;
		}
		
		MAccount gain = MAccount.get (as.getCtx(), as.getAcctSchemaDefault().getRealizedGain_Acct());
		MAccount loss = MAccount.get (as.getCtx(), as.getAcctSchemaDefault().getRealizedLoss_Acct());
		//
		if (invoice.isSOTrx())
		{
			FactLine fl = fact.createLine (null, loss, gain, 
				as.getC_Currency_ID(), acctDifference);
			fl.setDescription(description);
			fact.createLine (null, acct, 
				as.getC_Currency_ID(), acctDifference.negate());
			fl.setDescription(description);
		}
		else
		{
			fact.createLine (null, acct,
				as.getC_Currency_ID(), acctDifference);
			fact.createLine (null, loss, gain, 
				as.getC_Currency_ID(), acctDifference.negate());
		}
		return null;
	}	//	createRealizedGainLoss


	/**************************************************************************
	 * 	Create Tax Correction.
	 * 	Requirement: Adjust the tax amount, if you did not receive the full
	 * 	amount of the invoice (payment discount, write-off).
	 * 	Applies to many countries with VAT.
	 * 	Example:
	 * 		Invoice:	Net $100 + Tax1 $15 + Tax2 $5 = Total $120
	 * 		Payment:	$115 (i.e. $5 underpayment)
	 * 		Tax Adjustment = Tax1 = 0.63 (15/120*5) Tax2 = 0.21 (5/120/5) 
	 * 
	 * 	@param as accounting schema
	 * 	@param fact fact
	 * 	@param line Allocation line
	 *	@param DiscountAccount discount acct
	 *	@param WriteOffAccoint write off acct
	 *	@return true if created
	 */
	private boolean createTaxCorrection (MAcctSchema as, Fact fact, 
		DocLine_Allocation line,
		MAccount DiscountAccount, MAccount WriteOffAccoint)
	{
		log.info (line.toString());
		BigDecimal discount = Env.ZERO;
		if (as.isTaxCorrectionDiscount())
			discount = line.getDiscountAmt();
		BigDecimal writeOff = Env.ZERO;
		if (as.isTaxCorrectionWriteOff())
			writeOff = line.getWriteOffAmt();
		
		Doc_AllocationTax tax = new Doc_AllocationTax (
			DiscountAccount, discount, 	WriteOffAccoint, writeOff);
		
		//	Get Source Amounts with account
		String sql = "SELECT * "
			+ "FROM Fact_Acct "
			+ "WHERE AD_Table_ID=318 AND Record_ID=?"	//	Invoice
			+ " AND C_AcctSchema_ID=?"
			+ " AND Line_ID IS NULL";	//	header lines like tax or total
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, getTrx());
			pstmt.setInt(1, line.getC_Invoice_ID());
			pstmt.setInt(2, as.getC_AcctSchema_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				tax.addInvoiceFact (new MFactAcct(getCtx(), rs, fact.get_TrxName()));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		//	Invoice Not posted
		if (tax.getLineCount() == 0)
		{
			log.warning ("Invoice not posted yet - " + line);
			return false;
		}
		//	size = 1 if no tax
		if (tax.getLineCount() < 2)
			return true;
		return tax.createEntries (as, fact, line);
	}	//	createTaxCorrection

}   //  Doc_Allocation

/**
 * 	Allocation Document Tax Handing
 *	
 *  @author Jorg Janke
 *  @version $Id: Doc_Allocation.java 8780 2010-05-19 23:08:57Z nnayak $
 */
class Doc_AllocationTax
{
	/**
	 * 	Allocation Tax Adjustment
	 *	@param DiscountAccount discount acct
	 *	@param DiscountAmt discount amt
	 *	@param WriteOffAccount write off acct
	 *	@param WriteOffAmt write off amt
	 */
	public Doc_AllocationTax (MAccount DiscountAccount, BigDecimal DiscountAmt,
		MAccount WriteOffAccount, BigDecimal WriteOffAmt)
	{
		m_DiscountAccount = DiscountAccount;
		m_DiscountAmt = DiscountAmt;
		m_WriteOffAccount = WriteOffAccount;
		m_WriteOffAmt = WriteOffAmt;
	}	//	Doc_AllocationTax

	private CLogger				log = CLogger.getCLogger(getClass());

	private MAccount			m_DiscountAccount;
	private BigDecimal 			m_DiscountAmt;
	private MAccount			m_WriteOffAccount;
	private BigDecimal 			m_WriteOffAmt;

	private ArrayList<MFactAcct>	m_facts  = new ArrayList<MFactAcct>();
	private int					m_totalIndex = 0;

	/**
	 * 	Add Invoice Fact Line
	 *	@param fact fact line
	 */
	public void addInvoiceFact (MFactAcct fact)
	{
		m_facts.add(fact);
	}	//	addInvoiceLine
		
	/**
	 * 	Get Line Count
	 *	@return number of lines
	 */
	public int getLineCount()
	{
		return m_facts.size();
	}	//	getLineCount
		
	/**
	 * 	Create Accounting Entries
	 *	@param as account schema
	 *	@param fact fact to add lines
	 *	@param line line
	 *	@return true if created
	 */
	public boolean createEntries (MAcctSchema as, Fact fact, DocLine line)
	{
		//	get total index (the Receivables/Liabilities line)
		BigDecimal total = Env.ZERO;
		for (int i = 0; i < m_facts.size(); i++)
		{
			MFactAcct factAcct = m_facts.get(i);
			if (factAcct.getAmtSourceDr().compareTo(total) > 0)
			{
				total = factAcct.getAmtSourceDr();
				m_totalIndex = i; 
			}
			if (factAcct.getAmtSourceCr().compareTo(total) > 0)
			{
				total = factAcct.getAmtSourceCr();
				m_totalIndex = i; 
			}
		}
		
		MFactAcct factAcct = m_facts.get(m_totalIndex);
		log.info ("Total Invoice = " + total + " - " +  factAcct);
		int precision = as.getStdPrecision();
		for (int i = 0; i < m_facts.size(); i++)
		{
			//	No Tax Line
			if (i == m_totalIndex)
				continue;
			
			factAcct = m_facts.get(i);
			log.info (i + ": " + factAcct);
			
			//	Create Tax Account
			MAccount taxAcct = factAcct.getMAccount();
			if (taxAcct == null || taxAcct.get_ID() == 0)
			{
				log.severe ("Tax Account not found/created");
				return false;
			}
			
			
			//	Discount Amount	
			if (m_DiscountAmt.signum() != 0)
			{
				//	Original Tax is DR - need to correct it CR
				if (Env.ZERO.compareTo(factAcct.getAmtSourceDr()) != 0)
				{
					BigDecimal amount = calcAmount(factAcct.getAmtSourceDr(), 
						total, m_DiscountAmt, precision);
					if (amount.signum() != 0)
					{
						fact.createLine (line, m_DiscountAccount,
							factAcct.getC_Currency_ID(), amount, null);
						fact.createLine (line, taxAcct,
							factAcct.getC_Currency_ID(), null, amount);
					}
				}
				//	Original Tax is CR - need to correct it DR
				else
				{
					BigDecimal amount = calcAmount(factAcct.getAmtSourceCr(), 
						total, m_DiscountAmt, precision);
					if (amount.signum() != 0)
					{
						fact.createLine (line, taxAcct,
							factAcct.getC_Currency_ID(), amount, null);
						fact.createLine (line, m_DiscountAccount,
							factAcct.getC_Currency_ID(), null, amount);
					}
				}
			}	//	Discount
			
			//	WriteOff Amount	
			if (m_WriteOffAmt.signum() != 0)
			{
				//	Original Tax is DR - need to correct it CR
				if (Env.ZERO.compareTo(factAcct.getAmtSourceDr()) != 0)
				{
					BigDecimal amount = calcAmount(factAcct.getAmtSourceDr(), 
						total, m_WriteOffAmt, precision);
					if (amount.signum() != 0)
					{
						fact.createLine (line, m_WriteOffAccount,
							factAcct.getC_Currency_ID(), amount, null);
						fact.createLine (line, taxAcct,
							factAcct.getC_Currency_ID(), null, amount);
					}
				}
				//	Original Tax is CR - need to correct it DR
				else
				{
					BigDecimal amount = calcAmount(factAcct.getAmtSourceCr(), 
						total, m_WriteOffAmt, precision);
					if (amount.signum() != 0)
					{
						fact.createLine (line, taxAcct,
							factAcct.getC_Currency_ID(), amount, null);
						fact.createLine (line, m_WriteOffAccount,
							factAcct.getC_Currency_ID(), null, amount);
					}
				}
			}	//	WriteOff
			
		}	//	for all lines
		return true;
	}	//	createEntries
	
	/**
	 * 	Calc Amount tax / (total-tax) * amt
	 *	@param tax tax
	 *	@param total total
	 *	@param amt reduction amt
	 *	@param precision precision
	 *	@return tax / total * amt
	 */
	private BigDecimal calcAmount (BigDecimal tax, BigDecimal total, BigDecimal amt, int precision)
	{
		log.fine("Amt=" + amt + " - Total=" + total + ", Tax=" + tax);
		if (tax.signum() == 0 
			|| total.signum() == 0
			|| amt.signum() == 0)
			return Env.ZERO;
		//
		BigDecimal devisor = total.subtract(tax); 
		BigDecimal multiplier = tax.divide(devisor, 10, BigDecimal.ROUND_HALF_UP); 
		BigDecimal c = multiplier.add(Env.ONE);
		BigDecimal retValue = multiplier.multiply(amt);
		retValue = retValue.divide(c,10, BigDecimal.ROUND_HALF_UP);
		if (retValue.scale() > precision)
			retValue = retValue.setScale(precision, BigDecimal.ROUND_HALF_UP);
		log.fine(retValue + " (Mult=" + multiplier + "(Prec=" + precision + ")");
		return retValue;
	}	//	calcAmount
	
}	//	Doc_AllocationTax
