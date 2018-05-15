/**
 * 
 */
package rsge.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MUOMConversion;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.model.MAdvanceDisbursement;
import rsge.model.MCostDetail;
import rsge.model.MDisRealizationLine;
import rsge.model.MDisRealizationLineInv;
import rsge.model.MDisRealizationLineProd;
import rsge.model.MDisbursementRealization;
import rsge.model.MGeneralSetup;
import rsge.model.MInvoice;
import rsge.model.MProduct;

/**
 * @author Fanny
 *
 */
public class Doc_DisbursementRealization extends Doc {

	/**
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trx
	 */
	public Doc_DisbursementRealization(MAcctSchema[] ass, ResultSet rs, Trx trx) {
		super(ass, MDisbursementRealization.class, rs, DocBaseType.DOCBASETYPE_DisbursementRealization, trx);
		// TODO Auto-generated constructor stub
	}
	
	private MDisbursementRealization 		adr = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	public String loadDocumentDetails() {
		adr = (MDisbursementRealization) getPO();
		// Set Date Doc 
		setDateDoc(adr.getDateDoc());		
		setDateAcct(adr.getDateDoc());		
		setC_Currency_ID(adr.getC_Currency_ID());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	/**
	 *  Create Facts (the accounting logic) for
	 *  XBP	
	 *  <pre>
	 *  XBP
	 *      Account			     DR
	 *      Budget Clearing	         CR
	 *
	 *  </pre>
	 *  @param as accounting schema
	 *  @return Fact
	 */	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		 ArrayList<Fact> facts = new ArrayList<Fact>();
		//  create Fact Header
		Fact fact = new Fact (this, as, Fact.POST_Actual);				
		DocLine_DisbursementRealization[] d_lines = loadLines(as);
		int size = d_lines.length;
		int paymentOrg = 0;
		for(int i=0; i<size; i++)
		{
			MDisRealizationLine dl = new MDisRealizationLine(getCtx(), d_lines[i].getP_LineID(), getTrx());
			if(dl.getInvoicedAmt().signum()>0)
			{
				// Check if line has Invoice			
				String sql = "SELECT * FROM XX_DisRealizationLineInv WHERE XX_DisRealizationLine_ID = ? ";
				PreparedStatement pstmt = DB.prepareStatement(sql, getTrx());
				ResultSet rs = null;
				try{
					pstmt.setInt(1, d_lines[i].getP_LineID());
					rs = pstmt.executeQuery();
					while(rs.next())
					{
						MDisRealizationLineInv l = new MDisRealizationLineInv(getCtx(), rs, getTrx());
						BigDecimal allocatedAmt = l.getAllocatedAmt();
					}
					rs.close();
					pstmt.close();
				}catch(Exception e)
				{
					e.printStackTrace();
				}				
			}
			if(dl.getRealizedAmt().signum()>0)
			{
				MDisRealizationLineProd[] prodList = MDisRealizationLineProd.get(dl);
				if(prodList.length==0)
				{
					BigDecimal amt = d_lines[i].getAmtSource();
					MAccount account = d_lines[i].getAccount();
					FactLine dr = fact.createLine(d_lines[i], account, getC_Currency_ID(), amt, null);
					if(dr==null)
						continue;
					dr.setAD_Org_ID(d_lines[i].getP_AD_Org_ID());
					dr.save();
					if(paymentOrg==0)
					{
						if(d_lines[i].getP_PaymentOrg_ID()!=0)
							paymentOrg= d_lines[i].getP_PaymentOrg_ID();
						else
							paymentOrg = d_lines[i].getP_AD_Org_ID();
					}					
				}
				else
				{
					for(MDisRealizationLineProd dp : prodList)
					{
						ProductCost pc = new ProductCost(getCtx(), dp.getM_Product_ID(), dp.getM_AttributeSetInstance_ID(), getTrx());
						FactLine dr = fact.createLine(null, pc.getAccount(ProductCost.ACCTTYPE_P_Asset, as), getC_Currency_ID(), dp.getTotalAmt(), null);
						dr.setAD_Org_ID(d_lines[i].getP_AD_Org_ID());
						dr.save();						
						
						// Update Cost
						BigDecimal qty = dp.getQty();
						MProduct product = new MProduct(getCtx(), dp.getM_Product_ID(), getTrx());
						if(product.getC_UOM_ID()!=dp.getC_UOM_ID())
							qty = MUOMConversion.convertProductFrom(getCtx(), dp.getM_Product_ID(), dp.getC_UOM_ID(), qty);
						MCostDetail.createAdvanceDisbursementRealization(as, d_lines[i].getP_AD_Org_ID(), dp.getM_Product_ID(), dp.getM_AttributeSetInstance_ID(), 
								dp.getXX_DisRealizationLineProd_ID(), 0, dp.getTotalAmt(), qty, "Advance Disbursement Realization #"+adr.getDocumentNo(), false, getTrx());
					}
				}
			}
			
			FactLine cr = fact.createLine(null, d_lines[i].getAdDisbursementAcct(), getC_Currency_ID(), null, dl.getAmount());				
			if(cr!=null)
			{
				cr.setAD_Org_ID(paymentOrg);
				cr.save();
			}
		}
		
		if(adr.getDifferenceAmt().signum()!=0)
		{	
			int chrg = MGeneralSetup.get(getCtx(), getAD_Client_ID(), getTrx()).getAdvDisbursementClrg_ID();
			if(adr.getDifferenceAmt().signum()>0)
				fact.createLine(null, DocBaseType.getAccount(DocBaseType.ACCTTYPE_Charge, 0, chrg, as), getC_Currency_ID(), adr.getDifferenceAmt(), null);
			else
				fact.createLine(null, DocBaseType.getAccount(DocBaseType.ACCTTYPE_Charge, 0, chrg, as), getC_Currency_ID(), null, adr.getDifferenceAmt().negate());
		}
		
		facts.add(fact);
		return facts;	
	}

	private DocLine_DisbursementRealization[] loadLines (MAcctSchema as)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		//
		MDisRealizationLine[] lines = MDisbursementRealization.getLines(adr);
		for (int i = 0; i < lines.length; i++)
		{
			MDisRealizationLine line = lines[i];
			if(line.getRealizedAmt().signum()==0 && line.getInvoicedAmt().signum()==0)
				continue;
			DocLine_DisbursementRealization docLine = new DocLine_DisbursementRealization(line, this);
			docLine.setAccount(DocBaseType.getAccount(DocBaseType.ACCTTYPE_Charge, 0, line.getC_Charge_ID(), as));			
			docLine.setAdDisbursementAcct(DocBaseType.getAccount(DocBaseType.ACCTTYPE_Charge, 0, docLine.getAdDisbursementCharge_ID(), as));			
			log.fine(docLine.toString());
			list.add(docLine);
			}			
			//	Convert to Array
			DocLine_DisbursementRealization[] dls = new DocLine_DisbursementRealization[list.size()];
			list.toArray(dls);

			//	Return Array
			return dls;
		}	//	loadLines
	
	private MAccount getLiabilityAcct(int C_BPartner_ID, MAcctSchema as)
	{
		MAccount acct = null;
		int C_ValidCombination_ID = 0;
		String sql = " SELECT COALESCE(a.V_Liability_Acct, b.V_Liability_Acct) "
				+ " FROM C_BP_Vendor_Acct a "
				+ " INNER JOIN C_AcctSchema_Default b ON (a.C_AcctSchema_ID = b.C_AcctSchema_ID)"
				+ " WHERE a.C_BPartner_ID=? AND a.C_AcctSchema_ID=? AND a.IsActive = 'Y' ";
 
		PreparedStatement pstmt = DB.prepareStatement(sql, getTrx());
		ResultSet rs= null;
		try{
			pstmt.setInt(1, C_BPartner_ID);
			pstmt.setInt(2, as.getC_AcctSchema_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				C_ValidCombination_ID = rs.getInt(1);
				acct = MAccount.get(getCtx(), C_ValidCombination_ID);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return acct;
	}


}
