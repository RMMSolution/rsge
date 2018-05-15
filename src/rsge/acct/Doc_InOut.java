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
import org.compiere.common.CompiereStateException;
import org.compiere.model.*;
import org.compiere.util.*;

/**
 *  Post Shipment/Receipt Documents.
 *  <pre>
 *  Table:              M_InOut (319)
 *  Document Types:     MMS, MMR
 *  </pre>
 *  @author Jorg Janke
 *  @version  $Id: Doc_InOut.java 9940 2011-11-17 07:24:41Z ragrawal $
 *  Revised by Fanny R 2014
 *  To accomodate costing if product receipt exceeds PO/Invoice
 */
public class Doc_InOut extends Doc
{
	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trx p_trx
	 */
	public Doc_InOut (MAcctSchema[] ass, ResultSet rs, Trx trx)
	{
		super (ass, MInOut.class, rs, null, trx);
	}   //  DocInOut

	DocLine[] p_lines = null;
	
	/**
	 *  Load Document Details
	 *  @return error message or null
	 */
	@Override
	public String loadDocumentDetails()
	{
		setC_Currency_ID(NO_CURRENCY);
		MInOut inout = (MInOut)getPO();
		setDateDoc (inout.getMovementDate());
		//
		m_MatchRequirementR = X_AD_ClientInfo.MATCHREQUIREMENTR_None;
		if (!inout.isSOTrx())
		{
			m_MatchRequirementR = MClientInfo.get (getCtx(), inout.getAD_Client_ID())
				.getMatchRequirementR();
			String mr = inout.getMatchRequirementR();
			if (mr == null)
				inout.setMatchRequirementR(m_MatchRequirementR);
			else
				m_MatchRequirementR = mr;
		}
		
		//	Contained Objects
		p_lines = loadLines(inout);
		
		log.fine("Lines=" + p_lines.length);
		if (m_matchProblem == null || m_matchProblem.length() == 0)
			return null;
		return m_matchProblem.substring(1).trim();
	}   //  loadDocumentDetails

	/** Match Requirement		*/
	private String	m_MatchRequirementR = null;
	/** Match Problem Info	*/
	private String	m_matchProblem = "";
	
	/**
	 *	Load Invoice Line
	 *	@param inout shipment/receipt
	 *  @return DocLine Array
	 */
	private DocLine[] loadLines(MInOut inout)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		MInOutLine[] lines = inout.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MInOutLine line = lines[i];
			if (line.isDescription() 
				|| line.getM_Product_ID() == 0
				|| line.getMovementQty().signum() == 0)
			{
				log.finer("Ignored: " + line);
				continue;
			}
			//	PO Matching
			if (m_MatchRequirementR.equals (X_M_InOut.MATCHREQUIREMENTR_PurchaseOrder)
				|| m_MatchRequirementR.equals (X_M_InOut.MATCHREQUIREMENTR_PurchaseOrderAndInvoice))
			{
				BigDecimal poDiff = line.getMatchPODifference();
				if (poDiff.signum() != 0)
					m_matchProblem += "; Line=" + line.getLine() 
						+ " PO Match diff=" + poDiff;
				else if (!line.isMatchPOPosted())
					m_matchProblem += "; PO Match not posted for Line=" + line.getLine();
			}
			//	Inv Matching
			else if (m_MatchRequirementR.equals (X_M_InOut.MATCHREQUIREMENTR_Invoice)
				|| m_MatchRequirementR.equals (X_M_InOut.MATCHREQUIREMENTR_PurchaseOrderAndInvoice))
			{
				BigDecimal invDiff = line.getMatchInvDifference();
				if (invDiff.signum() != 0)
					m_matchProblem += "; Line=" + line.getLine() 
						+ " PO Match diff=" + invDiff;
				else if (!line.isMatchInvPosted())
					m_matchProblem += "; Inv Match not posted for Line=" + line.getLine();
			}
			
			DocLine docLine = new DocLine (line, this);
			BigDecimal Qty = line.getMovementQty();
			docLine.setQty (Qty, getDocumentType().equals(MDocBaseType.DOCBASETYPE_MaterialDelivery));    //  sets Trx and Storage Qty
			//
			log.fine(docLine.toString());
			list.add (docLine);
		}

		//	Return Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}	//	loadLines

	/**
	 *  Get Balance
	 *  @return Zero (always balanced)
	 */
	@Override
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for
	 *  MMR
	 *  <pre>
	 *  Receipt
	 *      Inventory       DR
	 *      NotInvoicedReceipt      CR
	 *  </pre>
	 *  @param as accounting schema
	 *  @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID (as.getC_Currency_ID());

		//  Line pointers
		FactLine dr = null;
		FactLine cr = null;
		

		//  *** Sales - Shipment
		if (getDocumentType().equals(MDocBaseType.DOCBASETYPE_MaterialDelivery))
		{
			for (int i = 0; i < p_lines.length; i++)
			{
				DocLine line = p_lines[i];
				BigDecimal costs = line.getProductCosts(as, line.getAD_Org_ID(), true);
				if (costs == null)	//	zero costs OK
				{
					MProduct product = line.getProduct();
					if (product.isStocked())
					{
						p_Error = "No Costs for " + line.getProduct().getName();
						log.log(Level.WARNING, p_Error);
						return null;
					}
					else	//	ignore service
						continue;
				}
				
				if(((MInOut)getPO()).isConsigned())
				{
					cr = fact.createLine(line,
							line.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
							as.getC_Currency_ID(), costs.negate());
					if (cr == null)
						continue;
					cr.setM_Locator_ID(line.getM_Locator_ID());
					cr.setQty(line.getQty().negate());	//	outgoing

					dr = fact.createLine(line,
							line.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
							as.getC_Currency_ID(), costs);
					if(dr == null)
						continue;
					dr.setM_Locator_ID(line.getM_LocatorTo_ID());
					dr.setQty(line.getQty());
				}
				
				else if (!isReturnTrx())
				{
					//  CoGS            DR
					if(!line.isDropShip())
						dr = fact.createLine(line,
								line.getAccount(ProductCost.ACCTTYPE_P_Cogs, as),
								as.getC_Currency_ID(), costs, null);
					else
						dr = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_L_COGS, as), as.getC_Currency_ID(), costs, null);
					if (dr == null)
					{
						p_Error = "FactLine DR not created: " + line;
						log.log(Level.WARNING, p_Error);
						return null;
					}
					dr.setM_Locator_ID(line.getM_Locator_ID());
					dr.setLocationFromLocator(line.getM_Locator_ID(), true);    //  from Loc
					dr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  //  to Loc
					dr.setAD_Org_ID(line.getOrder_Org_ID());		//	Revenue X-Org
					dr.setQty(line.getQty().negate());
					
					//  Inventory               CR
					if(!line.isDropShip())
					cr = fact.createLine(line,
						line.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
						as.getC_Currency_ID(), null, costs);
					else
						cr = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_L_Asset, as), as.getC_Currency_ID(), null, costs);
					if (cr == null)
					{
						p_Error = "FactLine CR not created: " + line;
						log.log(Level.WARNING, p_Error);
						return null;
					}
					cr.setM_Locator_ID(line.getM_Locator_ID());
					cr.setLocationFromLocator(line.getM_Locator_ID(), true);    // from Loc
					cr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
				}
				else // Reverse accounting entries for returns
				{
					//				  CoGS            CR
					MAccount assets = line.getAccount(ProductCost.ACCTTYPE_P_Asset, as);
					MAccount cogs_assets = getAssetReturnCOGSAccount(line.getM_Product_ID(), assets, as);
					if(!line.isDropShip())
					cr = fact.createLine(line,
							cogs_assets,
							as.getC_Currency_ID(), null, costs);
					else
						cr = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_L_COGS,as), as.getC_Currency_ID(), null, costs);
					if (cr == null)
					{
						p_Error = "FactLine CR not created: " + line;
						log.log(Level.WARNING, p_Error);
						return null;
					}
					cr.setM_Locator_ID(line.getM_Locator_ID());
					cr.setLocationFromLocator(line.getM_Locator_ID(), true);    //  from Loc
					cr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  //  to Loc
					cr.setAD_Org_ID(line.getOrder_Org_ID());		//	Revenue X-Org
					
					//  Inventory               DR
					if(!line.isDropShip())
					{
						MAccount returned_assets = getAssetReturnAccount(line.getM_Product_ID(), assets, as);
						dr = fact.createLine(line,
							returned_assets,
							as.getC_Currency_ID(), costs, null);
					}
					else
						dr = fact.createLine(line, line.getAccount(ProductCost.ACCTTYPE_L_Asset, as), as.getC_Currency_ID(), costs, null);
					if (dr == null)
					{
						p_Error = "FactLine DR not created: " + line;
						log.log(Level.WARNING, p_Error);
						return null;
					}
					dr.setM_Locator_ID(line.getM_Locator_ID());
					dr.setLocationFromLocator(line.getM_Locator_ID(), true);    // from Loc
					dr.setLocationFromBPartner(getC_BPartner_Location_ID(), false);  // to Loc
					dr.setQty(line.getQty().negate());
				}
					//
				if (line.getM_Product_ID() != 0 && !((MInOut)getPO()).isConsigned() )
				{
					MCostDetail.createShipment(as, line.getAD_Org_ID(), 
						line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
						line.get_ID(), 0,
						costs, isReturnTrx()? line.getQty().negate() : line.getQty(),
						line.getDescription(), true, getTrx());
				}
			}	//	for all lines
			//updateProductInfo(as.getC_AcctSchema_ID());     //  only for SO!
		}	//	Shipment		
		//  *** Purchasing - Receipt
		if (getDocumentType().equals(MDocBaseType.DOCBASETYPE_MaterialReceipt))
		{
			if(((MInOut)getPO()).isConsigned())
			{
				ArrayList<Fact> facts = new ArrayList<Fact>();
				facts.add(fact);
				return facts;
			}

			for (int i = 0; i < p_lines.length; i++)
			{
				DocLine line = p_lines[i];
				BigDecimal costs = line.getProductCosts(as, line.getAD_Org_ID(), false);	//	non-zero costs
				MProduct product = line.getProduct();
				if(!isReturnTrx() && line.getC_OrderLine_ID()!=0 && !checkStdCostingMethod(product, as))
				{
					MOrderLine ol = new MOrderLine(getCtx(), line.getC_OrderLine_ID(), getTrx());
					BigDecimal productCost = ol.getPriceActual();
					if(ol.getQtyDelivered().compareTo(ol.getQtyOrdered())>0)
					{
						// 
						// Get Product Cost by costs : qty
						BigDecimal delta = ol.getQtyDelivered().subtract(ol.getQtyOrdered());
						BigDecimal rcptQty = line.getQty();						
						costs = productCost.multiply(rcptQty.subtract(delta));
					}
					else
						costs = productCost.multiply(line.getQty());
				}
				if (costs == null || costs.signum() == 0)
				{
					p_Error = "Resubmit - No Costs for " + product.getName();
					log.log(Level.WARNING, p_Error);
					return null;
				}
				//  Inventory/Asset			DR
				MAccount assets = line.getAccount(ProductCost.ACCTTYPE_P_Asset, as);
				if(line.isDropShip())
					assets = line.getAccount(ProductCost.ACCTTYPE_L_Asset,as);
				if (product.isService())
				{
					assets = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
				}
				if(isReturnTrx() && !product.isService())
				{
					assets = getAssetReturnAccount(product.getM_Product_ID(), assets, as);
				}
				
				if (!isReturnTrx())
				{
					//  Inventory/Asset			DR
					dr = fact.createLine(line, assets,
						as.getC_Currency_ID(), costs, null);
					if (dr == null)
					{
						p_Error = "DR not created: " + line;
						log.log(Level.WARNING, p_Error);
						return null;
					}
					dr.setM_Locator_ID(line.getM_Locator_ID());
					dr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   // from Loc
					dr.setLocationFromLocator(line.getM_Locator_ID(), false);   // to Loc
					//  NotInvoicedReceipt				CR
					cr = fact.createLine(line,
						getAccount(Doc.ACCTTYPE_NotInvoicedReceipts, as),
						as.getC_Currency_ID(), null, costs);
					if (cr == null)
					{
						p_Error = "CR not created: " + line;
						log.log(Level.WARNING, p_Error);
						return null;
					}
					cr.setM_Locator_ID(line.getM_Locator_ID());
					cr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   //  from Loc
					cr.setLocationFromLocator(line.getM_Locator_ID(), false);   //  to Loc
					cr.setQty(line.getQty().negate());
				}
				else // reverse accounting entries for returns
				{
					//  Inventory/Asset			CR
					cr = fact.createLine(line, assets,
							as.getC_Currency_ID(), null, costs);
					if (cr == null)
					{
						p_Error = "CR not created: " + line;
						log.log(Level.WARNING, p_Error);
						return null;
					}
					cr.setM_Locator_ID(line.getM_Locator_ID());
					cr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   // from Loc
					cr.setLocationFromLocator(line.getM_Locator_ID(), false);   // to Loc
					cr.setQty(line.getQty().negate());
					//  NotInvoicedReceipt				DR
					dr = fact.createLine(line,
						getAccount(Doc.ACCTTYPE_NotInvoicedReceipts, as),
						as.getC_Currency_ID(), costs, null);
					if (dr == null)
					{
						p_Error = "DR not created: " + line;
						log.log(Level.WARNING, p_Error);
						return null;
					}
					dr.setM_Locator_ID(line.getM_Locator_ID());
					dr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   //  from Loc
					dr.setLocationFromLocator(line.getM_Locator_ID(), false);   //  to Loc				
				}
			}

		}	//	Receipt
		
		//
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}   //  createFact

	private MAccount getAssetReturnAccount(int M_Product_ID, MAccount account, MAcctSchema as)
	{
		MAccount retValue = account;
		String sql = "SELECT COALESCE(COALESCE(a.P_AssetReturn_Acct, pca.P_AssetReturn_Acct), a.P_Asset_Acct) AS P_AssetReturn_Acct FROM M_Product_Acct a " +
				"INNER JOIN M_Product p ON (a.M_Product_ID = p.M_Product_ID) " +
				"INNER JOIN M_Product_Category_Acct pca ON (p.M_Product_Category_ID = pca.M_Product_Category_ID AND a.C_AcctSchema_ID = pca.C_AcctSchema_ID) " +
				"WHERE a.C_AcctSchema_ID = ? AND a.M_Product_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, account.get_Trx());
		ResultSet rs = null;
		try {
			pstmt.setInt(1, as.getC_AcctSchema_ID());
			pstmt.setInt(2, M_Product_ID);			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				int	validCombination_ID = rs.getInt(1);
				retValue = MAccount.get(getCtx(), validCombination_ID);
				System.out.println("Account " + retValue.getCombination());
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return retValue;
	}
	
	private MAccount getAssetReturnCOGSAccount(int M_Product_ID, MAccount account, MAcctSchema as)
	{
		MAccount retValue = account;
		String sql = "SELECT COALESCE(COALESCE(COALESCE(a.P_COGSReturn_Acct, pca.P_COGSReturn_Acct), a.P_COGS_Acct), pca.P_COGS_Acct) AS P_COGSReturn_Acct FROM M_Product_Acct a " +
				"INNER JOIN M_Product p ON (a.M_Product_ID = p.M_Product_ID) " +
				"INNER JOIN M_Product_Category_Acct pca ON (p.M_Product_Category_ID = pca.M_Product_Category_ID AND a.C_AcctSchema_ID = pca.C_AcctSchema_ID) " +
				"WHERE a.C_AcctSchema_ID = ? AND a.M_Product_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, account.get_Trx());
		ResultSet rs = null;
		try {
			pstmt.setInt(1, as.getC_AcctSchema_ID());
			pstmt.setInt(2, M_Product_ID);			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				int	validCombination_ID = rs.getInt(1);
				retValue = MAccount.get(getCtx(), validCombination_ID);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return retValue;
	}
	
	private static boolean checkStdCostingMethod(MProduct product, MAcctSchema as)
	{
		boolean costingMethodStandard = false;
		String costingMethod = null;
		MProductCategoryAcct pca = MProductCategoryAcct.get (product.getCtx(),
				product.getM_Product_Category_ID(), as.getC_AcctSchema_ID(), null);	

		if (pca == null)
			throw new CompiereStateException("Cannot find Acct for M_Product_Category_ID=" 
					+ product.getM_Product_Category_ID() 
					+ ", C_AcctSchema_ID=" + as.getC_AcctSchema_ID());
		costingMethod = pca.getCostingMethod();
		if (costingMethod == null)
		{
			costingMethod = as.getCostingMethod();
			if (costingMethod == null)
				throw new IllegalArgumentException("No Costing Method");
		}
		
		if(costingMethod!=null)
		{
			if(costingMethod.equals(X_C_AcctSchema.COSTINGMETHOD_StandardCosting))
				costingMethodStandard = true;
		}

		return costingMethodStandard;
	}


}   //  Doc_InOut
