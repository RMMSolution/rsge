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
import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.*;
import org.compiere.util.*;

import rsge.model.MGeneralSetup;
import rsge.model.MOrgInfo;

/**
 *  Post Invoice Documents.
 *  <pre>
 *  Table:              M_Movement (323)
 *  Document Types:     MMM
 *  </pre>
 *  @author Jorg Janke
 *  @version  $Id: Doc_Movement.java 9974 2011-12-02 07:21:55Z ragrawal $
 */
public class Doc_Movement extends Doc
{
	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trx p_trx
	 */
	public Doc_Movement (MAcctSchema[] ass, ResultSet rs, Trx trx)
	{
		super (ass, MMovement.class, rs, MDocBaseType.DOCBASETYPE_MaterialMovement, trx);
	}   //  Doc_Movement

	private DocLine_MovementLine[] m_Lines;
	/**
	 *  Load Document Details
	 *  @return error message or null
	 */
	@Override
	public String loadDocumentDetails()
	{
		setC_Currency_ID(NO_CURRENCY);
		MMovement move = (MMovement)getPO();
		setDateDoc (move.getMovementDate());
		setDateAcct(move.getMovementDate());
		//	Contained Objects
		m_Lines = loadLines(move);
		log.fine("Lines=" + m_Lines.length);
		return null;
	}   //  loadDocumentDetails

	/**
	 *	Load Invoice Line
	 *	@param move move
	 *  @return document lines (DocLine_Material)
	 */
	private DocLine_MovementLine[] loadLines(MMovement move)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		MMovementLine[] lines = move.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MMovementLine line = lines[i];
			DocLine_MovementLine docLine = new DocLine_MovementLine(line, this);
			docLine.setQty(line.getMovementQty(), false);
			//
			log.fine(docLine.toString());
			list.add (docLine);
		}

		//	Return Array
		DocLine_MovementLine[] dls = new DocLine_MovementLine[list.size()];
		list.toArray(dls);
		return dls;
	}	//	loadLines

	/**
	 *  Get Balance
	 *  @return balance (ZERO) - always balanced
	 */
	@Override
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for
	 *  MMM.
	 *  <pre>
	 *  Movement
	 *      Inventory       DR      CR
	 *      InventoryTo     DR      CR
	 *  </pre>
	 *  @param as account schema
	 *  @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID(as.getC_Currency_ID());

		//  Line pointers
		FactLine dr = null;
		FactLine cr = null;

		for (int i = 0; i < m_Lines.length; i++)
		{
			DocLine_MovementLine line = m_Lines[i];
			MMovementLine mLine = line.ml;
			BigDecimal costs = line.getProductCosts(as, line.getAD_Org_ID(), false);
			
			MProduct product = line.getProduct();
			if(mLine.isConsigned())
			{
				String whereClause = " AND M_Locator_ID = " + mLine.getM_Locator_ID();
				MBPConsignedLocator[] conLoc = MBPConsignedLocator.getBPLocators(getCtx(), whereClause, "", getTrx());
				if (conLoc.length !=1)
				{
					// Should not reach here
					log.saveError("Error", "Locator Associated with more than one Business Partner");
					return  null;
				}
				
				if(!conLoc[0].isSOTrx())
				{
					log.fine("Vendor Consigned Locator :- Product owned by the Vendor");
					ArrayList<Fact> facts = new ArrayList<Fact>();
					facts.add(fact);
					return facts;
				}
			}

			if(!product.isService()) {
				//  ** Inventory       DR      CR
				dr = fact.createLine(line,
					line.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
					as.getC_Currency_ID(), costs.negate());		//	from (-) CR
			}
			else {
			//  ** Expense       DR      CR
				dr = fact.createLine(line,
					line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
					as.getC_Currency_ID(), costs.negate());		//	from (-) CR
			}
			if (dr == null)
				continue;
			
			dr.setM_Locator_ID(line.getM_Locator_ID());
			dr.setQty(line.getQty().negate());	//	outgoing
			
			if(!product.isService()) {
				//  ** InventoryTo     DR      CR
				cr = fact.createLine(line,
					line.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
					as.getC_Currency_ID(), costs);			//	to (+) DR
			}
			else {
			//  ** InventoryTo     DR      CR
				cr = fact.createLine(line,
					line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
					as.getC_Currency_ID(), costs);			//	to (+) DR
			}
				
			if (cr == null)
				continue;
			cr.setM_Locator_ID(line.getM_LocatorTo_ID());
			cr.setQty(line.getQty());

			//	Only for between-org movements
			if (dr.getAD_Org_ID() != cr.getAD_Org_ID())
			{
				String costingLevel = as.getCostingLevel();
				MProductCategoryAcct pca = MProductCategoryAcct.get(getCtx(), 
					line.getProduct().getM_Product_Category_ID(), 
					as.getC_AcctSchema_ID(), getTrx());
				if (pca.getCostingLevel() != null)
					costingLevel = pca.getCostingLevel();
				if (!X_C_AcctSchema.COSTINGLEVEL_Organization.equals(costingLevel))
					continue;
				//
				String description = line.getDescription();
				if (description == null)
					description = "";
				//	Cost Detail From
				MCostDetail.createMovement(as, dr.getAD_Org_ID(), 	//	locator org
					line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
					line.get_ID(), 0,
					costs.negate(), line.getQty().negate(), true,
					description + "(|->)", getTrx());
				//	Cost Detail To
				MCostDetail.createMovement(as, cr.getAD_Org_ID(),	//	locator org 
					line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
					line.get_ID(), 0,
					costs, line.getQty(), false,
					description + "(|<-)", getTrx());
				
				MGeneralSetup setup = MGeneralSetup.get(getCtx(), as.getAD_Client_ID(), getTrx());
				if(setup.isUseOrgIntercompanyAcct())
				{
					MOrgInfo drInfo = MOrgInfo.get(getCtx(), dr.getAD_Org_ID(), getTrx());
					MOrgInfo crInfo = MOrgInfo.get(getCtx(), cr.getAD_Org_ID(), getTrx());
					if(drInfo.getParent_Org_ID()!=crInfo.getParent_Org_ID())
					{
						FactLine id = fact.createLine(null, DocBaseType.getIntercompanyAccount(cr.getAD_Org_ID(), DocBaseType.dueTo, as),
								as.getC_Currency_ID(), costs, null);
						id.setAD_Org_ID(dr.getAD_Org_ID());
						id.save();
						FactLine ic = fact.createLine(null, DocBaseType.getIntercompanyAccount(dr.getAD_Org_ID(), DocBaseType.dueFrom, as),
								as.getC_Currency_ID(), null, costs);
						ic.setAD_Org_ID(cr.getAD_Org_ID());
						ic.save();
					}
				}
			}
		}

		//
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}   //  createFact	
}   //  Doc_Movement
