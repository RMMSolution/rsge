/**
 * 
 */
package rsge.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MFactAcct;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.model.MInOut;
import rsge.model.MInOutLine;
import rsge.model.MNonItemAllocation;
import rsge.model.MNonItemAllocationLine;

/**
 * @author Fanny
 *
 */
public class Doc_NonItemAllocation extends Doc {

	/**
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trx
	 */
	public Doc_NonItemAllocation(MAcctSchema[] ass, ResultSet rs, Trx trx) {
		super(ass, MNonItemAllocation.class, rs, DocBaseType.DOCBASETYPE_NonItemAllocation, trx);
		// TODO Auto-generated constructor stub
	}
	
	private MNonItemAllocation		 		nia = null;
	
	// Account Detail
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	public String loadDocumentDetails() {
		nia = (MNonItemAllocation) getPO();
		setDateDoc(nia.getDateDoc());
		
		setDateAcct(nia.getDateAcct());
		
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
	 *  NIA	
	 *  <pre>
	 *
	 *  </pre>
	 *  @param as accounting schema
	 *  @return Fact
	 */	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
		 
		 ArrayList<Fact> facts = new ArrayList<Fact>();

		//  create Fact Header
		Fact fact = new Fact (this, as, Fact.POST_Actual);		
		// Source Fact
		MInOutLine iol = new MInOutLine(getCtx(), nia.getM_InOutLine_ID(), getTrx());
		MInOut io = new MInOut(getCtx(), iol.getM_InOut_ID(), getTrx());
		if(!io.isPosted())
		{
			p_Error = "Receipt has not been posted yet";
			log.log(Level.WARNING, p_Error);
			return null;
		}
		MFactAcct sourceFact = getSourceFact(iol.getM_InOut_ID(), iol.getM_InOutLine_ID());
		MNonItemAllocationLine[] lines = MNonItemAllocation.getLines(getCtx(), nia.getXX_NonItemAllocation_ID(), getTrx());
		for(MNonItemAllocationLine line : lines)
		{
			FactLine dr = fact.createLine(null, getSourceAccount(sourceFact), getC_Currency_ID(), line.getAllocatedAmt(), null);
			dr.setAD_Org_ID(line.getAD_OrgTrx_ID());
			dr.setAD_OrgTrx_ID(line.getAD_OrgTrx_ID());
			dr.setC_Activity_ID(line.getC_Activity_ID());
			dr.setC_Campaign_ID(line.getC_Campaign_ID());
			dr.setC_Project_ID(line.getC_Project_ID());
			dr.save();
		}
		// Set to Credit
		fact.createLine(null, getSourceAccount(sourceFact), getC_Currency_ID(), null, sourceFact.getAmtAcctDr());
		facts.add(fact);
		return facts;	
	}
	 
	 private MAccount getSourceAccount(MFactAcct source)
	 {
		 return MAccount.get(getCtx(), getAD_Client_ID(), source.getAD_Org_ID(), source.getC_AcctSchema_ID(), source.getAccount_ID(), source.getC_SubAcct_ID(), source.getM_Product_ID(), source.getC_BPartner_ID(), source.getAD_OrgTrx_ID(), source.getC_LocFrom_ID(), source.getC_LocTo_ID(), source.getC_SalesRegion_ID(), source.getC_Project_ID(), source.getC_Campaign_ID(),  source.getC_Activity_ID(), source.getUser1_ID(),  source.getUser2_ID(),  source.getUserElement1_ID(), source.getUserElement2_ID());
	 }
	 
	 private MFactAcct getSourceFact(int recordID, int lineID)
	 {
		 MFactAcct fa = null;
		 String sql = "SELECT * FROM Fact_Acct "
		 		+ "WHERE AD_Table_ID = 319 "
		 		+ "AND Record_ID = ? "
		 		+ "AND Line_ID = ? ";
		 PreparedStatement pstmt = DB.prepareStatement(sql, getTrx());
		 ResultSet rs = null;
		 try{
			 pstmt.setInt(1, recordID);
			 pstmt.setInt(2, lineID);
			 rs = pstmt.executeQuery();
			 if(rs.next())
				 fa = new MFactAcct(getCtx(), rs, getTrx());
			 rs.close();
			 pstmt.close();
		 }catch (Exception e) {
			e.printStackTrace();
		}
		 
		 return fa;
	 }

}
