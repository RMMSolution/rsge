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
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.model.MCashLine;
import rsge.model.MExpenseTransfer;

/**
 * @author Fanny
 *
 */
public class Doc_ExpenseTransfer extends Doc {

	/**
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trx
	 */
	public Doc_ExpenseTransfer(MAcctSchema[] ass, ResultSet rs, Trx trx) {
		super(ass, MExpenseTransfer.class, rs, DocBaseType.DOCBASETYPE_ExpenseTransfer, trx);
		// TODO Auto-generated constructor stub
	}
	
	private MExpenseTransfer 					etr = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	public String loadDocumentDetails() {
		etr = (MExpenseTransfer) getPO();
		// Set Date Doc (taken from Budget Plan Date)
		setDateDoc(etr.getDateDoc());		
		setDateAcct(etr.getDateAcct());
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
	 *  Create Facts (the accounting logic) for DPS
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
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		DocLine_ExpenseTransfer[] lines = getLines(as); 
		
		for(DocLine_ExpenseTransfer line : lines)
		{
			FactLine dr = fact.createLine(null, line.getAccount(), getC_Currency_ID(), line.getP_Amount(), null);
			dr.setAD_Org_ID(line.getP_AD_Org_ID());
			dr.save();
			
			FactLine cr = fact.createLine(null, line.getAccount(), getC_Currency_ID(), null, line.getP_Amount());
			cr.setAD_Org_ID(etr.getAD_Org_ID());
			cr.save();
		}		
		facts.add(fact);
		return facts;	
	}
	 
	private DocLine_ExpenseTransfer[] getLines(MAcctSchema as)
	{
		ArrayList<DocLine_ExpenseTransfer> list = new ArrayList<>();
		if(etr.getC_Cash_ID()!=0)
		{
			String sql = "SELECT * FROM C_CashLine "
	    			+ "WHERE TargetOrg_ID IS NOT NULL "
					+ "AND C_Cash_ID = ?";
			
			PreparedStatement pstmt = DB.prepareStatement(sql, getTrx());
			ResultSet rs = null;
			try{
				pstmt.setInt(1, etr.getC_Cash_ID());
				rs = pstmt.executeQuery();
				while(rs.next())				
				{
					DocLine_ExpenseTransfer etLine = new DocLine_ExpenseTransfer(new MCashLine(getCtx(), rs, getTrx()), as, this);
					list.add(etLine);
				}
				rs.close();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}		
			
		}
		DocLine_ExpenseTransfer[] retValue = new DocLine_ExpenseTransfer[list.size()];
		list.toArray(retValue);
		return retValue;
	}
	
}
