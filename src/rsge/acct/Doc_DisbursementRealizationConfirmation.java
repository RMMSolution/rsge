/**
 * 
 */
package rsge.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.model.MDRConfirmation;
import rsge.model.MGeneralSetup;

/**
 * @author Fanny
 *
 */
public class Doc_DisbursementRealizationConfirmation extends Doc {

	/**
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trx
	 */
	public Doc_DisbursementRealizationConfirmation(MAcctSchema[] ass, ResultSet rs, Trx trx) {
		super(ass, MDRConfirmation.class, rs, DocBaseType.DOCBASETYPE_DisbursementRealization, trx);
		// TODO Auto-generated constructor stub
	}
	
	private MDRConfirmation 		drc = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	public String loadDocumentDetails() {
		drc = (MDRConfirmation) getPO();
		// Set Date Doc 
		setDateDoc(drc.getDateDoc());		
		setDateAcct(drc.getDateDoc());		
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
		 if(drc.getDifferenceAmt().signum()==0)
			 return facts;
		//  create Fact Header
		Fact fact = new Fact (this, as, Fact.POST_Actual);		
		
		int chrg = MGeneralSetup.get(getCtx(), getAD_Client_ID(), getTrx()).getAdvDisbursementClrg_ID();
		if(drc.getDifferenceAmt().signum()>0)
		{
			fact.createLine(null, getGainLossAcct(false, as), getC_Currency_ID(), drc.getDifferenceAmt(), null);			
			fact.createLine(null, DocBaseType.getAccount(DocBaseType.ACCTTYPE_Charge, 0, chrg, as), getC_Currency_ID(), null, drc.getDifferenceAmt());			
		}
		else
		{
			fact.createLine(null, DocBaseType.getAccount(DocBaseType.ACCTTYPE_Charge, 0, chrg, as), getC_Currency_ID(), drc.getDifferenceAmt().negate(), null);
			fact.createLine(null, getGainLossAcct(true, as), getC_Currency_ID(), null, drc.getDifferenceAmt().negate());			
		}
		
		facts.add(fact);
		return facts;	
	}

	private MAccount getGainLossAcct(boolean IsGain, MAcctSchema as)
	{
		MAccount acct = null;
		int C_ValidCombination_ID = 0;
		String sql = " SELECT RealizedGain_Acct, RealizedLoss_Acct "
				+ " FROM C_AcctSchema_Default "
				+ " WHERE C_AcctSchema_ID=? AND IsActive = 'Y' ";
 
		PreparedStatement pstmt = DB.prepareStatement(sql, getTrx());
		ResultSet rs= null;
		try{
			pstmt.setInt(1, as.getC_AcctSchema_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				int x = 1;
				if(!IsGain)
					x = 2;
				C_ValidCombination_ID = rs.getInt(x);
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
