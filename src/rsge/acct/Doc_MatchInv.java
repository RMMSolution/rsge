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
import java.util.logging.Level;

import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.*;
import org.compiere.util.*;

import rsge.model.MGeneralSetup;
import rsge.model.MOrgInfo;

/**
 *  Post MatchInv Documents.
 *  <pre>
 *  Table:              M_MatchInv (472)
 *  Document Types:     MXI
 *  </pre>
 *  Update Costing Records
 *  @author Jorg Janke
 *  @version  $Id: Doc_MatchInv.java 9696 2011-03-30 06:42:02Z ssharma $
 */
public class Doc_MatchInv extends Doc
{
	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trx p_trx
	 */
	public Doc_MatchInv (MAcctSchema[] ass, ResultSet rs, Trx trx)
	{
		super(ass, MMatchInv.class, rs, MDocBaseType.DOCBASETYPE_MatchInvoice, trx);
	}   //  Doc_MatchInv

	/** Invoice Line			*/
	private MInvoiceLine	m_invoiceLine = null;
	/** Material Receipt		*/
	private MInOutLine		m_receiptLine = null;
	/** Consigned Transfer Line */
	private MConsignedTransferLine m_consignedTransferLine = null;
	
	private ProductCost		m_pc = null;
	
	/**
	 *  Load Specific Document Details
	 *  @return error message or null
	 */
	@Override
	public String loadDocumentDetails()
	{
		setC_Currency_ID (Doc.NO_CURRENCY);
		MMatchInv matchInv = (MMatchInv)getPO();
		setDateDoc(matchInv.getDateTrx());
		setQty (matchInv.getQty());
		//	Invoice Info
		int C_InvoiceLine_ID = matchInv.getC_InvoiceLine_ID();
		m_invoiceLine = new MInvoiceLine (getCtx(), C_InvoiceLine_ID, null);
		//		BP for NotInvoicedReceipts
		int C_BPartner_ID = m_invoiceLine.getParent().getC_BPartner_ID(); 
		setC_BPartner_ID(C_BPartner_ID);
		//
		int M_InOutLine_ID = matchInv.getM_InOutLine_ID();
		m_receiptLine = new MInOutLine (getCtx(), M_InOutLine_ID, null);		
		//
		int M_ConsignedTransferLine_ID = matchInv.getM_ConsignedTransferLine_ID();
		if(M_ConsignedTransferLine_ID != 0)
			m_consignedTransferLine = new MConsignedTransferLine(getCtx(), M_ConsignedTransferLine_ID, getTrx());
		//
		m_pc = new ProductCost (Env.getCtx(), 
			getM_Product_ID(), matchInv.getM_AttributeSetInstance_ID(), null);
		m_pc.setQty(getQty());
		
		return null;
	}   //  loadDocumentDetails


	/**************************************************************************
	 *  Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
	 *  @return Zero (always balanced)
	 */
	@Override
	public BigDecimal getBalance()
	{
		return Env.ZERO;
	}   //  getBalance

	
	/**
	 *  Create Facts (the accounting logic) for
	 *  MXI.
	 * 	(single line)
	 *  <pre>
	 *      NotInvoicedReceipts     DR			(Receipt Org)
	 *      InventoryClearing               CR
	 *      InvoicePV               DR      CR  (difference)
	 *  Commitment
	 * 		Expense							CR
	 * 		Offset					DR
	 *  </pre>
	 *  @param as accounting schema
	 *  @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		ArrayList<Fact> facts = new ArrayList<Fact>();
		//  Nothing to do
		if (getM_Product_ID() == 0								//	no Product
			|| getQty().signum() == 0
			|| m_receiptLine.getMovementQty().signum() == 0)	//	Qty = 0
		{
			log.fine("No Product/Qty - M_Product_ID=" + getM_Product_ID()
				+ ",Qty=" + getQty() + ",InOutQty=" + m_receiptLine.getMovementQty());
			return facts;
		}
		MMatchInv matchInv = (MMatchInv)getPO();
		
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		Fact dummyFact = new Fact(this,as,Fact.POST_Actual);
		setC_Currency_ID (as.getC_Currency_ID());

		//  NotInvoicedReceipt      DR
		//  From Receipt
		FactLine dr = fact.createLine (null,
			getAccount(Doc.ACCTTYPE_NotInvoicedReceipts, as),
			as.getC_Currency_ID(), Env.ONE, null);			// updated below
		if (dr == null)
		{
			p_Error = "No Product Costs";
			return null;
		}
		dr.setQty(getQty());
		BigDecimal temp = dr.getAcctBalance();
		
		DocLine line = new DocLine(m_receiptLine,this);
		BigDecimal LineDrAmount = BigDecimal.ZERO;
		
		//if the Receipt match requirement is Invoice at client level, then matching invoice has to be posted before receipt
		if ( m_consignedTransferLine==null &&
				!MClientInfo.get(this.getCtx(),this.getAD_Client_ID()).getMatchRequirementR().equals(X_M_InOut.MATCHREQUIREMENTR_Invoice))
		{
			//	Set AmtAcctCr/Dr from Receipt (sets also Project)
			if (!dr.updateReverseLine (X_M_InOut.Table_ID, 		//	Amt updated
				m_receiptLine.getM_InOut_ID(), m_receiptLine.getM_InOutLine_ID(),
				getQty(), m_receiptLine.getMovementQty(), as.getC_Currency_ID()))
			{
				p_Error = "Mat.Receipt not posted yet";
				return null;
			}
		}
		else
		{
			if( m_receiptLine.getC_OrderLine_ID() == 0 ) // if no matching PO, then use Product cost
			{
				LineDrAmount = m_pc.getProductCosts(as, getAD_Org_ID(), as.getCostingMethod(), 0, false);
			}
			else //if Matching PO is there, use the price for PO
			{
				MOrderLine ol = new MOrderLine(this.getCtx(),m_receiptLine.getC_OrderLine_ID(),this.getTrx());
				BigDecimal price = ol.getLineNetAmt();
				MProduct product = line.getProduct();
				if (price == null || price.signum() == 0)
				{
					p_Error = "Resubmit - No Costs for " + product.getName();
					return null;
				}
				BigDecimal Qty = ol.getQtyOrdered();
				LineDrAmount = price.multiply(dr.getQty()).divide(Qty,10,BigDecimal.ROUND_HALF_UP);
			}
			dr.setAmtSource(as.getC_Currency_ID(), LineDrAmount , null);
		}
		dr.convert();
		dr.setM_Locator_ID(line.getM_Locator_ID());
		dr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   // from Loc
		dr.setLocationFromLocator(line.getM_Locator_ID(), false);  

		log.fine("CR - Amt(" + temp + "->" + dr.getAcctBalance() 
			+ ") - " + dr.toString());

		
		//  InventoryClearing               CR
		//  From Invoice
		MAccount expense = m_pc.getAccount(ProductCost.ACCTTYPE_P_InventoryClearing, as);
		if (m_pc.isService())
			expense = m_pc.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
		BigDecimal LineNetAmt = m_invoiceLine.getLineNetAmt();
		BigDecimal multiplier = getQty()
			.divide(m_invoiceLine.getQtyInvoiced(), 12, BigDecimal.ROUND_HALF_UP)
			.abs();
		if (multiplier.compareTo(Env.ONE) != 0)
			LineNetAmt = LineNetAmt.multiply(multiplier);
		if (m_pc.isService())
			LineNetAmt = dr.getAcctBalance();	//	book out exact receipt amt
		FactLine cr = null;
		if (as.isAccrual())
		{
			// The createLine parameter "createZero = true" will cause a zero accounting line
			// to be generated. This ensures that the fact will be balanced by IPV.
			cr = fact.createLine (null, expense,
				as.getC_Currency_ID(), null, (getQty().signum()<0)?LineNetAmt.negate():LineNetAmt, true);		//	updated below
			if (cr == null)
			{
				log.fine("Line Net Amt=0 - M_Product_ID=" + getM_Product_ID()
					+ ",Qty=" + getQty() + ",InOutQty=" + m_receiptLine.getMovementQty());
				facts.add(fact);
				dummyFact.dispose();
				return facts;
			}
			cr.setQty(getQty().negate());
			temp = cr.getAcctBalance();
			//	Set AmtAcctCr/Dr from Invoice (sets also Project)
			if (as.isAccrual() && !cr.updateReverseLine (X_C_Invoice.Table_ID, 		//	Amt updated
				m_invoiceLine.getC_Invoice_ID(), m_invoiceLine.getC_InvoiceLine_ID(), 
				getQty(), m_invoiceLine.getQtyInvoiced(), as.getC_Currency_ID()))
			{
				p_Error = "Invoice not posted yet";
				return null;
			}
			log.fine("DR - Amt(" + temp + "->" + cr.getAcctBalance() 
				+ ") - " + cr.toString());
		}
		else	//	Cash Acct
		{
			MInvoice invoice = m_invoiceLine.getParent();
			if (as.getC_Currency_ID() == invoice.getC_Currency_ID())
				LineNetAmt = MConversionRate.convert(getCtx(), LineNetAmt, 
					invoice.getC_Currency_ID(), as.getC_Currency_ID(),
					invoice.getDateAcct(), invoice.getC_ConversionType_ID(),
					invoice.getAD_Client_ID(), invoice.getAD_Org_ID());
			cr = fact.createLine (null, expense,
				as.getC_Currency_ID(), null,(getQty().signum()<0)?LineNetAmt.negate():LineNetAmt);
			cr.setQty(getQty().multiply(multiplier).negate());
		}
		cr.setC_Activity_ID(m_invoiceLine.getC_Activity_ID());
		cr.setC_Campaign_ID(m_invoiceLine.getC_Campaign_ID());
		cr.setC_Project_ID(m_invoiceLine.getC_Project_ID());
		cr.setC_UOM_ID(m_invoiceLine.getC_UOM_ID());
		cr.setUser1_ID(m_invoiceLine.getUser1_ID());
		cr.setUser2_ID(m_invoiceLine.getUser2_ID());

		//  Invoice Price Variance 	difference
		BigDecimal ipv = cr.getAcctBalance().add(dr.getAcctBalance()).negate();
		if (ipv.signum() != 0)
		{
			FactLine pv = fact.createLine(null,
				m_pc.getAccount(ProductCost.ACCTTYPE_P_IPV, as),
				as.getC_Currency_ID(), ipv);
			pv.setC_Activity_ID(m_invoiceLine.getC_Activity_ID());
			pv.setC_Campaign_ID(m_invoiceLine.getC_Campaign_ID());
			pv.setC_Project_ID(m_invoiceLine.getC_Project_ID());
			pv.setC_UOM_ID(m_invoiceLine.getC_UOM_ID());
			pv.setUser1_ID(m_invoiceLine.getUser1_ID());
			pv.setUser2_ID(m_invoiceLine.getUser2_ID());
		}
//		log.fine("IPV=" + ipv + "; Balance=" + fact.getSourceBalance());
		
		MInOut inOut = m_receiptLine.getParent(); 
		boolean isReturnTrx = inOut.isReturnTrx();
		
		//	Cost Detail Record - data from Expense/IncClearing (CR) record
		MCostDetail.createInvoice(as, getAD_Org_ID(), 
			getM_Product_ID(), matchInv.getM_AttributeSetInstance_ID(),
			m_invoiceLine.getC_InvoiceLine_ID(), 0,		//	No cost element
			cr.getAcctBalance().negate(), isReturnTrx? getQty().negate() : getQty(),		//	correcting
			getDescription(), getTrx());

		//  Update Costing
		updateProductInfo(as.getC_AcctSchema_ID(), 
			X_C_AcctSchema.COSTINGMETHOD_StandardCosting.equals(as.getCostingMethod()));
		//
		facts.add(fact);
		
		/** Commitment release										****/
		if (as.isAccrual() && as.isCreateCommitment())
		{
			fact = getCommitmentRelease(as, this, 
				getQty(), m_invoiceLine.getC_InvoiceLine_ID(),(getQty().signum()<0)?Env.ONE.negate():Env.ONE );
			if (fact == null)
				return null;
			facts.add(fact);
		}	//	Commitment
		
		dummyFact.dispose();
		
		MGeneralSetup setup = MGeneralSetup.get(getCtx(), as.getAD_Client_ID(), getTrx());
		if(setup.isUseOrgIntercompanyAcct())
		{
			BigDecimal cost = dr.getAmtAcctDr();
			MOrgInfo drInfo = MOrgInfo.get(getCtx(), dr.getAD_Org_ID(), getTrx());
			MOrgInfo crInfo = MOrgInfo.get(getCtx(), cr.getAD_Org_ID(), getTrx());
			if(drInfo.getParent_Org_ID()!=crInfo.getParent_Org_ID())
			{
				FactLine id = fact.createLine(null, DocBaseType.getIntercompanyAccount(cr.getAD_Org_ID(), DocBaseType.dueTo, as),
						as.getC_Currency_ID(), null, cost);
				id.setAD_Org_ID(dr.getAD_Org_ID());
				id.save();
				FactLine ic = fact.createLine(null, DocBaseType.getIntercompanyAccount(dr.getAD_Org_ID(), DocBaseType.dueFrom, as),
						as.getC_Currency_ID(), cost, null);
				ic.setAD_Org_ID(cr.getAD_Org_ID());
				ic.save();
			}
		}

		return facts;
	}   //  createFact

	/** Error Message			*/
	protected static String			p_Error = null;
	
	/**
	 * 	Get Commitment Release.
	 * 	Called from MatchInv for accrual and Allocation for Cash Based
	 *	@param as accounting schema
	 *	@param doc doc
	 *	@param Qty qty invoiced/matched
	 *	@param C_InvoiceLine_ID line
	 *	@param multiplier 1 for accrual
	 *	@return Fact
	 */
	protected static Fact getCommitmentRelease(MAcctSchema as, Doc doc, 
		BigDecimal Qty, int C_InvoiceLine_ID, BigDecimal multiplier)
	{
		Fact fact = new Fact(doc, as, Fact.POST_Commitment);
		DocLine[] commitments = getCommitments(as.getCtx(), doc, Qty, 
				C_InvoiceLine_ID);
		
		BigDecimal total = Env.ZERO;
		int C_Currency_ID = -1;
		for (int i = 0; i < commitments.length; i++)
		{
			DocLine line = commitments[i];
			if (C_Currency_ID == -1)
				C_Currency_ID = line.getC_Currency_ID();
			else if (C_Currency_ID != line.getC_Currency_ID())
			{
				p_Error = "Different Currencies of Order Lines";
				s_log.log(Level.SEVERE, p_Error);
				return null;
			}
			BigDecimal cost = line.getAmtSource().multiply(multiplier);
			total = total.add(cost);

			//	Account
			MAccount expense = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
			fact.createLine (line, expense,
				C_Currency_ID, null, cost);
		}
		//	Offset
		MAccount offset = doc.getAccount(ACCTTYPE_CommitmentOffset, as);
		if (offset == null)
		{
			p_Error = "@NotFound@ @CommitmentOffset_Acct@";
			s_log.log(Level.SEVERE, p_Error);
			return null;
		}
		fact.createLine (null, offset,
			C_Currency_ID, total, null);
		return fact;
	}	//	getCommitmentRelease
	
	/**
	 * 	Get Commitments
	 * 	@param doc document
	 * 	@param maxQty Qty invoiced/matched
	 * 	@param C_InvoiceLine_ID invoice line
	 *	@return commitments (order lines)
	 */
	protected static DocLine[] getCommitments(Ctx ctx, Doc doc, BigDecimal maxQty, int C_InvoiceLine_ID)
	{
		int precision = -1;
		//
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		String sql = "SELECT * FROM C_OrderLine ol "
			+ "WHERE EXISTS "
				+ "(SELECT * FROM C_InvoiceLine il "
				+ "WHERE il.C_OrderLine_ID=ol.C_OrderLine_ID"
				+ " AND il.C_InvoiceLine_ID=?)"
			+ " OR EXISTS "
				+ "(SELECT * FROM M_MatchPO po "
				+ "WHERE po.C_OrderLine_ID=ol.C_OrderLine_ID"
				+ " AND po.C_InvoiceLine_ID=?)";
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		try
		{
			pstmt = DB.prepareStatement(sql, (Trx) null);
			pstmt.setInt (1, C_InvoiceLine_ID);
			pstmt.setInt (2, C_InvoiceLine_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				if (maxQty.signum() == 0)
					continue;
				MOrderLine line = new MOrderLine (ctx, rs, null);
				int StdPrecision=line.getPrecision();
				DocLine docLine = new DocLine (line, doc);
				//	Currency
				if (precision == -1)
				{
					doc.setC_Currency_ID(docLine.getC_Currency_ID());
					precision = MCurrency.getStdPrecision(ctx, docLine.getC_Currency_ID());
				}
				//	Qty
				BigDecimal Qty = line.getQtyOrdered().max(maxQty);
				docLine.setQty(Qty, false);
				//
				BigDecimal PriceActual = line.getPriceActual();
				BigDecimal PriceCost = line.getPriceCost();
				BigDecimal LineNetAmt = null;
				if (PriceCost != null && PriceCost.signum() != 0)
					LineNetAmt = Qty.multiply(PriceCost);
				else if (Qty.equals(maxQty))
					LineNetAmt = line.getLineNetAmt();
				else
					LineNetAmt = Qty.multiply(PriceActual);
				maxQty = maxQty.subtract(Qty);
				
				docLine.setAmount (LineNetAmt);	//	DR
				BigDecimal PriceList = line.getPriceList();
				int C_Tax_ID = docLine.getC_Tax_ID();
				//	Correct included Tax
				if (C_Tax_ID != 0 && line.getParent().isTaxIncluded())
				{
					MTax tax = MTax.get(ctx, C_Tax_ID);
					if (!tax.isZeroTax())
					{
						BigDecimal LineNetAmtTax = tax.calculateTax(LineNetAmt, true, precision);
						s_log.fine("LineNetAmt=" + LineNetAmt + " - Tax=" + LineNetAmtTax);
						LineNetAmt = LineNetAmt.subtract(LineNetAmtTax);
						BigDecimal PriceListTax = tax.calculateTax(PriceList, true, precision);
						PriceList = PriceList.subtract(PriceListTax);
					}
				}	//	correct included Tax
				
				docLine.setAmount (LineNetAmt, PriceList, Qty,StdPrecision);
				list.add(docLine);
			}
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql, e);
		}
		finally
		{
			DB.closeStatement(pstmt);
			DB.closeResultSet(rs);
		}
		//	Return Array
		DocLine[] dl = new DocLine[list.size()];
		list.toArray(dl);
		return dl;
	}	//	getCommitments



	/**
	 *  Update Product Info (old).
	 *  - Costing (CostStandardCumQty, CostStandardCumAmt, CostAverageCumQty, CostAverageCumAmt)
	 *  @param C_AcctSchema_ID accounting schema
	 *  @param standardCosting true if std costing
	 *  @return true if updated
	 *  @deprecated old costing
	 */
	@Deprecated
	private boolean updateProductInfo (int C_AcctSchema_ID, boolean standardCosting)
	{
		log.fine("M_MatchInv_ID=" + get_ID());

		// Modified the query because of issues in EDB with the earlier query with the bind variables
		StringBuffer sql = new StringBuffer (
			"UPDATE M_Product_Costing pc "
		    + " SET CostStandardCumQty = (SELECT pc.CostStandardCumQty + m.Qty "
  									  + " FROM M_MatchInv m"
									  + " INNER JOIN C_InvoiceLine il ON (m.C_InvoiceLine_ID=il.C_InvoiceLine_ID)"
									  + " INNER JOIN C_Invoice i ON (il.C_Invoice_ID=i.C_Invoice_ID),"
									  + " C_AcctSchema a "
									  + " WHERE pc.C_AcctSchema_ID=a.C_AcctSchema_ID"
									  + " AND pc.M_Product_ID=m.M_Product_ID"
									  + " AND m.M_MatchInv_ID= ? ), "
			    + " CostStandardCumAmt = (SELECT pc.CostStandardCumAmt + "
			                               + " currencyConvert(il.PriceActual,i.C_Currency_ID,a.C_Currency_ID,"
			                                               + " i.DateInvoiced,i.C_ConversionType_ID, "
			                                               + " i.AD_Client_ID,i.AD_Org_ID)*m.Qty "
  									  + " FROM M_MatchInv m"
									  + " INNER JOIN C_InvoiceLine il ON (m.C_InvoiceLine_ID=il.C_InvoiceLine_ID)"
									  + " INNER JOIN C_Invoice i ON (il.C_Invoice_ID=i.C_Invoice_ID),"
									  + " C_AcctSchema a "
									  + " WHERE pc.C_AcctSchema_ID=a.C_AcctSchema_ID"
									  + " AND pc.M_Product_ID=m.M_Product_ID"
									  + " AND m.M_MatchInv_ID= ? ), "
               + " CostAverageCumQty =   (SELECT pc.CostAverageCumQty + m.Qty "
  									  + " FROM M_MatchInv m"
									  + " INNER JOIN C_InvoiceLine il ON (m.C_InvoiceLine_ID=il.C_InvoiceLine_ID)"
									  + " INNER JOIN C_Invoice i ON (il.C_Invoice_ID=i.C_Invoice_ID),"
									  + " C_AcctSchema a "
									  + " WHERE pc.C_AcctSchema_ID=a.C_AcctSchema_ID"
									  + " AND pc.M_Product_ID=m.M_Product_ID"
									  + " AND m.M_MatchInv_ID= ? ), "
			   + " CostAverageCumAmt =   (SELECT pc.CostAverageCumAmt + " 
			                              + " currencyConvert(il.PriceActual,i.C_Currency_ID,a.C_Currency_ID,"
			                                              + " i.DateInvoiced,i.C_ConversionType_ID, "
			                                              + " i.AD_Client_ID,i.AD_Org_ID)*m.Qty "
  									  + " FROM M_MatchInv m"
									  + " INNER JOIN C_InvoiceLine il ON (m.C_InvoiceLine_ID=il.C_InvoiceLine_ID)"
									  + " INNER JOIN C_Invoice i ON (il.C_Invoice_ID=i.C_Invoice_ID),"
									  + " C_AcctSchema a "
									  + " WHERE pc.C_AcctSchema_ID=a.C_AcctSchema_ID"
									  + " AND pc.M_Product_ID=m.M_Product_ID"
									  + " AND m.M_MatchInv_ID= ? ) "
			+ " WHERE pc.C_AcctSchema_ID= ? "
			+ " AND EXISTS (SELECT * FROM M_MatchInv m "
				        + " WHERE pc.M_Product_ID=m.M_Product_ID"
				        + " AND m.M_MatchInv_ID= ?)"); 
		
		Object[] params = new Object[]{get_ID(),get_ID(),get_ID(),get_ID(),C_AcctSchema_ID,get_ID()};
		int no = DB.executeUpdate(getTrx(), sql.toString(),params);
		log.fine("M_Product_Costing - Qty/Amt Updated #=" + no);

		//  Update Average Cost
		sql = new StringBuffer (
			  " UPDATE M_Product_Costing "
			+ " SET CostAverage = CostAverageCumAmt/DECODE(CostAverageCumQty, 0,1, CostAverageCumQty) "
			+ " WHERE C_AcctSchema_ID= ? "
			+ " AND M_Product_ID= ? ");
		params = new Object[]{C_AcctSchema_ID,getM_Product_ID()};
		no = DB.executeUpdate(getTrx(), sql.toString(),params);
		log.fine("M_Product_Costing - AvgCost Updated #=" + no);
		

		//  Update Current Cost
		if (!standardCosting)
		{
			sql = new StringBuffer (
				  " UPDATE M_Product_Costing "
				+ " SET CurrentCostPrice = CostAverage "
				+ " WHERE C_AcctSchema_ID= ? "
				+ " AND M_Product_ID= ? ");
			params = new Object[]{C_AcctSchema_ID,getM_Product_ID()};
			no = DB.executeUpdate(getTrx(), sql.toString(),params);
			log.fine("M_Product_Costing - CurrentCost Updated=" + no);
		}
		return true;
	}   //  updateProductInfo

}   //  Doc_MatchInv
