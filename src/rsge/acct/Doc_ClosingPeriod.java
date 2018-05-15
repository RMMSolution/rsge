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
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.model.MClosingPeriod;
import rsge.model.MClosingPeriodLine;

/**
 * @author Fanny
 *
 */
public class Doc_ClosingPeriod extends Doc {

	/**
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trx
	 */
	public Doc_ClosingPeriod(MAcctSchema[] ass, ResultSet rs, Trx trx) {
		super(ass, MClosingPeriod.class, rs, DocBaseType.DOCBASETYPE_ClosingPeriod, trx);
		// TODO Auto-generated constructor stub
	}
	
	private MClosingPeriod 					closingPeriod = null;
	private int								m_C_AcctSchema_ID = 0;
	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	public String loadDocumentDetails() {
		closingPeriod = (MClosingPeriod) getPO();
		// Set Date Doc (taken from Budget Plan Date)
		setDateDoc(closingPeriod.getDateDoc());		
		setDateAcct(closingPeriod.getDateAcct());
		m_C_AcctSchema_ID = closingPeriod.getC_AcctSchema_ID();
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
		//	Other Acct Schema
		if (as.getC_AcctSchema_ID() != m_C_AcctSchema_ID)
			return facts;

		//  create Fact Header
		Fact fact = new Fact (this, as,  "S");
		setC_Currency_ID(as.getC_Currency_ID());

		String sql = "SELECT * FROM XX_ClosingPeriodLine " +
				"WHERE XX_ClosingPeriod_ID = ? ";
		PreparedStatement ps = DB.prepareStatement(sql, getTrx());
		ResultSet rs = null;
		try {
			ps.setInt(1, closingPeriod.getXX_ClosingPeriod_ID());
			rs = ps.executeQuery();
			while(rs.next()){
				MClosingPeriodLine line = new MClosingPeriodLine(getCtx(), rs, getTrx());
				if(line.getAmtAcctDr().signum()!=0){
					FactLine Dr = fact.createLine(null, DocBaseType.getBudgetAcct(line.getAccount_ID(), as), as.getC_Currency_ID(), 
							line.getAmtAcctDr(), null);
					Dr.setAD_Org_ID(line.getAD_OrgTrx_ID());
				}
				
				if(line.getAmtAcctCr().signum()!=0){
					FactLine Cr = fact.createLine(null, DocBaseType.getBudgetAcct(line.getAccount_ID(), as), as.getC_Currency_ID(), null, line.getAmtAcctCr());
					Cr.setAD_Org_ID(line.getAD_OrgTrx_ID());
				}
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		String sql1 = "SELECT IncomeSummary_Acct, RetainedEarning_Acct " +
				"FROM C_AcctSchema_GL WHERE AD_Client_ID = ?";
		PreparedStatement ps1 = DB.prepareStatement(sql1, getTrx());
		ResultSet rs1 = null;
		try {
			ps1.setInt(1, getAD_Client_ID());
			rs1 = ps1.executeQuery();
			if(rs1.next()){
				BigDecimal cr = BigDecimal.ZERO;
				BigDecimal dr = BigDecimal.ZERO;
				
//				if(closingPeriod.getTotalCr().signum() < 0)
//					cr = closingPeriod.getTotalCr().multiply(new BigDecimal(-1));
//				else
					cr = closingPeriod.getTotalCr();
				
//				if(closingPeriod.getTotalDr().signum() < 0)
//					dr = closingPeriod.getTotalDr().multiply(new BigDecimal(-1));
//				else
					dr = closingPeriod.getTotalDr();
				
				if(closingPeriod.getTotalCr().compareTo(closingPeriod.getTotalDr()) > 0){
					BigDecimal md = BigDecimal.ZERO;
					md = cr.subtract(dr);
					MAccount mAccount = new MAccount(getCtx(), rs1.getInt(1), getTrx());
					FactLine Dr = fact.createLine(null, DocBaseType.getBudgetAcct(mAccount.getAccount_ID(), as), as.getC_Currency_ID(), 
							md, null);
					Dr.setAD_Org_ID(closingPeriod.getAD_Org_ID());
				}else if(closingPeriod.getTotalDr().compareTo(closingPeriod.getTotalCr()) > 0){
					BigDecimal md = BigDecimal.ZERO;
					md = dr.subtract(cr);
					MAccount mAccount = new MAccount(getCtx(), rs1.getInt(1), getTrx());
					FactLine Cr = fact.createLine(null, DocBaseType.getBudgetAcct(mAccount.getAccount_ID(), as), as.getC_Currency_ID(), 
							null, md);
					Cr.setAD_Org_ID(closingPeriod.getAD_Org_ID());
				}
			}
			rs1.close();
			ps1.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		facts.add(fact);
		return facts;	
	}
	 
	 

}
