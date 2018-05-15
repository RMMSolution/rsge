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
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.model.MAdvanceDisbursement;
import rsge.model.MAdvanceDisbursementLine;
import rsge.model.MBudgetInfo;
import rsge.model.MDisRealizationLine;
import rsge.model.MDisRealizationLineInv;
import rsge.model.MGeneralSetup;
import rsge.model.MInvoice;

/**
 * @author Fanny
 *
 */
public class Doc_AdvanceDisbursement extends Doc {

	/**
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trx
	 */
	public Doc_AdvanceDisbursement(MAcctSchema[] ass, ResultSet rs, Trx trx) {
		super(ass, MAdvanceDisbursement.class, rs, DocBaseType.DOCBASETYPE_AdvanceDisbursement, trx);
		// TODO Auto-generated constructor stub
	}
	
	private MAdvanceDisbursement 		ad = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	public String loadDocumentDetails() {
		ad = (MAdvanceDisbursement) getPO();
		// Set Date Doc 
		setDateDoc(ad.getDateDoc());		
		setDateAcct(ad.getDateDoc());				
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
	 *      Ad Disbursememt Charge			     DR
	 *      		Disbursement Transit		         CR
	 *
	 *  </pre>
	 *  @param as accounting schema
	 *  @return Fact
	 */	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		 ArrayList<Fact> facts = new ArrayList<Fact>();
		//  create Fact Header
		Fact fact = new Fact (this, as, Fact.POST_Actual);				
		DocLine_AdvanceDisbursementLine[] d_lines = loadLines(as);
		int size = d_lines.length;
		for(int i=0; i<size; i++)
		{
			MAdvanceDisbursementLine dl = new MAdvanceDisbursementLine(getCtx(), d_lines[i].getP_LineID(), getTrx());
			MAccount account = d_lines[i].getAccount();
			fact.createLine(d_lines[i], account, ad.getC_Currency_ID(), dl.getAmount(), null);
		}
		int chrg = MGeneralSetup.get(getCtx(), getAD_Client_ID(), getTrx()).getAdvDisbursementClrg_ID();
		fact.createLine(null, DocBaseType.getAccount(DocBaseType.ACCTTYPE_Charge, 0, chrg, as), ad.getC_Currency_ID(), null, ad.getTotalAmt());				
		facts.add(fact);
		return facts;	
	}

	private DocLine_AdvanceDisbursementLine[] loadLines (MAcctSchema as)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		//
		MAdvanceDisbursementLine[] lines = MAdvanceDisbursementLine.getLines(ad);
		for (int i = 0; i < lines.length; i++)
		{
			MAdvanceDisbursementLine line = lines[i];
			DocLine_AdvanceDisbursementLine docLine = new DocLine_AdvanceDisbursementLine(line, this);
			docLine.setAccount(DocBaseType.getAccount(DocBaseType.ACCTTYPE_Charge, 0, line.getAdvDisbursementCharge_ID(), as));			
			log.fine(docLine.toString());
			list.add(docLine);
			}			
			//	Convert to Array
			DocLine_AdvanceDisbursementLine[] dls = new DocLine_AdvanceDisbursementLine[list.size()];
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
