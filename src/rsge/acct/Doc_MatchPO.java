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
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.*;
import org.compiere.util.*;

/**
 *  Post MatchPO Documents.
 *  <pre>
 *  Table:              C_MatchPO (473)
 *  Document Types:     MXP
 *  </pre>
 *  @author Jorg Janke
 *  @version  $Id: Doc_MatchPO.java 10634 2013-01-17 14:10:08Z ragrawal $
 */
public class Doc_MatchPO extends Doc
{
	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trx p_trx
	 */
	public Doc_MatchPO (MAcctSchema[] ass, ResultSet rs, Trx trx)
	{
		super(ass, MMatchPO.class, rs, MDocBaseType.DOCBASETYPE_MatchPO, trx);
	}   //  Doc_MatchPO

	private int         m_C_OrderLine_ID = 0;
	private MOrderLine	m_oLine = null;
	//
	private int         m_M_InOutLine_ID = 0;
	private ProductCost m_pc;
	private int			m_M_AttributeSetInstance_ID = 0;
	private MMatchPO matchPO = null;

	/**
	 *  Load Specific Document Details
	 *  @return error message or null
	 */
	@Override
	public String loadDocumentDetails()
	{
		setC_Currency_ID (Doc.NO_CURRENCY);
		matchPO = (MMatchPO)getPO();
		setDateDoc(matchPO.getDateTrx());
		//
		m_M_AttributeSetInstance_ID = matchPO.getM_AttributeSetInstance_ID();
		setQty (matchPO.getQty());
		//
		m_C_OrderLine_ID = matchPO.getC_OrderLine_ID();
		m_oLine = new MOrderLine (getCtx(), m_C_OrderLine_ID, getTrx());
		//
		m_M_InOutLine_ID = matchPO.getM_InOutLine_ID();
	//	m_C_InvoiceLine_ID = matchPO.getC_InvoiceLine_ID();
		//
		m_pc = new ProductCost (Env.getCtx(), 
			getM_Product_ID(), m_M_AttributeSetInstance_ID, getTrx());
		m_pc.setQty(getQty());
		return null;
	}   //  loadDocumentDetails

	
	/**************************************************************************
	 *  Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
	 *  @return Zero - always balanced
	 */
	@Override
	public BigDecimal getBalance()
	{
		return Env.ZERO;
	}   //  getBalance

	
	/**
	 *  Create Facts (the accounting logic) for
	 *  MXP.
	 *  <pre>
	 *      Product PPV     <difference>
	 *      PPV_Offset                  <difference>
	 *  </pre>
	 *  @param as accounting schema
	 *  @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		ArrayList<Fact> facts = new ArrayList<Fact>();
		//
		if (getM_Product_ID() == 0		//  Nothing to do if no Product
			|| getQty().signum() == 0
			|| m_M_InOutLine_ID == 0)	//  No posting if not matched to Shipment
		{
			log.fine("No Product/Qty - M_Product_ID=" + getM_Product_ID()
				+ ",Qty=" + getQty());
			return facts;
		}

		// Check that associated Receipt has been posted
		MInOut inout = (new MInOutLine(getCtx(),m_M_InOutLine_ID,getTrx())).getParent();
		if(!inout.isConsigned())
		{		
			if(!inout.isPosted())
			{
				p_Error = "Associated Receipt not posted";
				return null;
			}
		}
		else
		{
			MConsignedTransfer conTrf = (new MConsignedTransferLine(getCtx(),matchPO.getM_ConsignedTransferLine_ID(),getTrx())).getParent();
			if(!conTrf.isPosted())
			{
				p_Error = "Associated Consigned Transaction not posted";
				return null;
			}
		}
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID(as.getC_Currency_ID());
		
		//	Purchase Order Line
		BigDecimal poCost = m_oLine.getPriceCost();
		if (poCost == null || poCost.signum() == 0)
			poCost = m_oLine.getPriceActual();
		BigDecimal costQty = getQty();
		BigDecimal deltaQty = m_oLine.getQtyDelivered().subtract(m_oLine.getQtyOrdered());
		if(deltaQty.signum()>0)
		{
			costQty = costQty.subtract(deltaQty);
		}
		poCost = poCost.multiply(costQty);		//	Delivered so far
		//	Different currency
		if (m_oLine.getC_Currency_ID() != as.getC_Currency_ID())
		{
			MOrder order = m_oLine.getParent();
			BigDecimal rate = MConversionRate.getRate(
				order.getC_Currency_ID(), as.getC_Currency_ID(),
				order.getDateAcct(), order.getC_ConversionType_ID(),
				m_oLine.getAD_Client_ID(), m_oLine.getAD_Org_ID());
			if (rate == null)
			{
				p_Error = "Purchase Order not convertible - " + as.getName();
				return null;
			}
			poCost = poCost.multiply(rate);
			if (poCost.scale() > as.getCostingPrecision())
				poCost = poCost.setScale(as.getCostingPrecision(), BigDecimal.ROUND_HALF_UP);
		}

		MOrder order = m_oLine.getParent();
		boolean isReturnTrx = order.isReturnTrx();
		log.fine("Temp");

		//	Current Costs
		String costingMethod = as.getCostingMethod();
		MProduct product = MProduct.get(getCtx(), getM_Product_ID());
		MProductCategoryAcct pca = MProductCategoryAcct.get(getCtx(), 
			product.getM_Product_Category_ID(), as.getC_AcctSchema_ID(), getTrx());
		if (pca.getCostingMethod() != null)
			costingMethod = pca.getCostingMethod();
		
		//	Difference
		BigDecimal difference = Env.ZERO;

		BigDecimal costs = m_pc.getProductCosts(as, getAD_Org_ID(), 
				costingMethod, m_C_OrderLine_ID, false);	//	non-zero costs
		
		//
		if(deltaQty.signum()>0)
		{
			// 
			// Get Product Cost by costs : qty
			MCurrency curr = new MCurrency(getCtx(), as.getC_Currency_ID(), getTrx());
			BigDecimal productCost = costs.divide(getQty(), curr.getCostingPrecision(), RoundingMode.HALF_EVEN);			
			BigDecimal rcptQty = getQty();			
			costs = productCost.multiply(rcptQty.subtract(deltaQty));
		}

		//	No Costs yet - no PPV
		if (inout.isConsigned() && (costs == null || costs.signum() == 0))
		{
			p_Error = "Resubmit - No Costs for " + product.getName();
			log.log(Level.SEVERE, p_Error);
			return null;
		}

		//	Difference
		difference = poCost.subtract(costs);
		
		//	Nothing to post
		if (difference.signum() == 0)
		{
			log.log(Level.FINE, "No Cost Difference for M_Product_ID=" + getM_Product_ID());
			facts.add(fact);
			MCostDetail.createOrder(as, m_oLine.getAD_Org_ID(), 
					getM_Product_ID(), m_M_AttributeSetInstance_ID,
					m_C_OrderLine_ID, 0,		//	no cost element
					isReturnTrx? poCost.negate() : poCost, isReturnTrx? getQty().negate(): getQty(),			//	Delivered
					m_oLine.getDescription(), getTrx());
			
			if(updateActualDelivery())
				log.log(Level.FINE, "Updated Actual Delivery time for the product");
			else
				log.log(Level.WARNING, "Unable to update actual delivery time for the product");
			return facts;
		}

		FactLine cr = null;
		difference=difference.negate();
		//  Product PPV
		/** 
		 * Revision by Fanny R
		 * If difference is negative, use different sign instead.
		 * Example : -100 Cr -? 100 Dr
		 */
		if(as.getCostingMethod().equals(X_M_Cost.COSTINGMETHOD_StandardCosting))
		{
			if(difference.signum()<0)
				cr= fact.createLine (null, m_pc.getAccount(ProductCost.ACCTTYPE_P_PPV, as),
						as.getC_Currency_ID(), difference.negate(), null);
			else
				cr= fact.createLine (null, m_pc.getAccount(ProductCost.ACCTTYPE_P_PPV, as),
					as.getC_Currency_ID(), null, difference);
		}
		else //update the product asset with the difference
		{
			if(product.isService())
			{
				if(difference.signum()<0)
					cr= fact.createLine (null, m_pc.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
							as.getC_Currency_ID(), difference.negate(), null);
				else
					cr= fact.createLine (null, m_pc.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
						as.getC_Currency_ID(), null, difference);
			}
			else 
			{
				if(!m_oLine.isDropShip()){
					if(difference.signum()<0)
						cr= fact.createLine (null, m_pc.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
								as.getC_Currency_ID(), difference.negate(), null);
					else
						cr= fact.createLine (null, m_pc.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
								as.getC_Currency_ID(), null, difference);
				}
				else{
					if(difference.signum()<0)
						cr= fact.createLine (null, m_pc.getAccount(ProductCost.ACCTTYPE_L_Asset, as),
								as.getC_Currency_ID(), difference.negate(), null);
					else
						cr= fact.createLine (null, m_pc.getAccount(ProductCost.ACCTTYPE_L_Asset, as),
							as.getC_Currency_ID(), null, difference);
				}
			}
				
		}
		if (cr != null)
		{
			cr.setQty(getQty());
			cr.setC_BPartner_ID(m_oLine.getC_BPartner_ID());
			cr.setC_Activity_ID(m_oLine.getC_Activity_ID());
			cr.setC_Campaign_ID(m_oLine.getC_Campaign_ID());
			cr.setC_Project_ID(m_oLine.getC_Project_ID());
			cr.setC_UOM_ID(m_oLine.getC_UOM_ID());
			cr.setUser1_ID(m_oLine.getUser1_ID());
			cr.setUser2_ID(m_oLine.getUser2_ID());
		}
		FactLine dr = null;
		//  PPV Offset
		if(as.getCostingMethod().equals(X_M_Cost.COSTINGMETHOD_StandardCosting))
		{
			if(difference.signum()<0)
				dr= fact.createLine (null, getAccount(Doc.ACCTTYPE_PPVOffset, as), 
						as.getC_Currency_ID(), null, difference.negate());
			else	
				dr= fact.createLine (null, getAccount(Doc.ACCTTYPE_PPVOffset, as), 
					as.getC_Currency_ID(), difference, null);
		}
		else //update the product asset with the difference
		{
			setC_BPartner_ID(m_oLine.getParent().getC_BPartner_ID());
			if(difference.signum()<0)
				dr= fact.createLine (null,getAccount(Doc.ACCTTYPE_NotInvoicedReceipts, as), 
					as.getC_Currency_ID(), null, difference.negate());
			else
				dr= fact.createLine (null,getAccount(Doc.ACCTTYPE_NotInvoicedReceipts, as), 
					as.getC_Currency_ID(), difference, null);
		}			
		if (dr != null)
		{
			dr.setQty(getQty().negate());
			dr.setC_BPartner_ID(m_oLine.getC_BPartner_ID());
			dr.setC_Activity_ID(m_oLine.getC_Activity_ID());
			dr.setC_Campaign_ID(m_oLine.getC_Campaign_ID());
			dr.setC_Project_ID(m_oLine.getC_Project_ID());
			dr.setC_UOM_ID(m_oLine.getC_UOM_ID());
			dr.setUser1_ID(m_oLine.getUser1_ID());
			dr.setUser2_ID(m_oLine.getUser2_ID());
		}
		//
		facts.add(fact);
		
		//	Create PO Cost Detail Record
		
		/**
		 * Revision by Fanny R
		 * if receipt line has more quantity, add qty with the difference between quantity and actual quantity
		 */		
		MCostDetail.createOrder(as, m_oLine.getAD_Org_ID(), 
				getM_Product_ID(), m_M_AttributeSetInstance_ID,
				m_C_OrderLine_ID, 0,		//	no cost element
				isReturnTrx? poCost.negate() : poCost, isReturnTrx? getQty().negate(): getQty(),			//	Delivered
				m_oLine.getDescription(), getTrx());
		
		// Update Actual Delivery time
		if(updateActualDelivery())
			log.log(Level.FINE, "Updated Actual Delivery time for the product");
		else
			log.log(Level.WARNING, "Unable to update actual delivery time for the product");
		
		return facts;
	}   //  createFact
	
	private boolean updateActualDelivery()
	{
		if(m_oLine == null || m_M_InOutLine_ID ==0)
			return true;
		else
		{
			Timestamp dateOrdered = m_oLine.getDateOrdered();
			Timestamp dateDelivered = m_oLine.getDateDelivered();
			if(dateDelivered == null)
			{
				MInOut inout = (new MInOutLine(getCtx(),m_M_InOutLine_ID,getTrx())).getParent();
				dateDelivered = inout.getMovementDate();
			}
			int deliveryTime = TimeUtil.getDaysBetween(dateOrdered, dateDelivered);
			MProductPO pPO= MProductPO.getOfVendorProduct(getCtx(), m_oLine.getC_BPartner_ID(), 
					m_oLine.getM_Product_ID(), getTrx()); 
			if (pPO != null){
				pPO.setDeliveryTime_Actual(deliveryTime);
			    return pPO.save(getTrx());
			}
			else
				return true;
		}
	}

}   //  Doc_MatchPO
