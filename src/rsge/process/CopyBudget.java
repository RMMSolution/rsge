/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MFactAcct;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MCopyBudget;
import rsge.model.MCopyBudgetLine;

/**
 * @author bang
 * 
 */
public class CopyBudget extends SvrProcess {
	MCopyBudget budget = null;
	MFactAcct[] accts = null;
	MAcctSchema as = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		budget = new MCopyBudget(getCtx(), getRecord_ID(), get_Trx());
		accts = getOrderLines(budget.getC_AcctSchema_ID(),
				budget.getC_Period_ID(), budget.getGL_Budget_ID());
		as = new MAcctSchema(getCtx(), budget.getC_AcctSchema_ID(), get_Trx());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		int acct = 0;
		acct = accts.length;
		if(acct == 0)
			return "No Data Found";
		else{
			budget.setProcessed(true);
			budget.save();
		}
		for(int a = 0; a<acct;a++){
			MFactAcct factAcct = accts[a];
			MCopyBudgetLine budgetLine = new MCopyBudgetLine(getCtx(), 0, get_Trx());
			budgetLine.setAD_Client_ID(getAD_Client_ID());
			budgetLine.setAD_Org_ID(budget.getAD_Org_ID());
			budgetLine.setFact_Acct_ID(factAcct.getFact_Acct_ID());
			budgetLine.setAccount_ID(factAcct.getAccount_ID());
			budgetLine.setAmtAcctCr(factAcct.getAmtAcctCr());
			budgetLine.setXX_CopyBudget_ID(budget.getXX_CopyBudget_ID());
			budgetLine.setAmtAcctDr(factAcct.getAmtAcctDr());
			budgetLine.save();
			
			MFactAcct mFactAcct = new MFactAcct(getCtx(), 0, get_Trx());
			mFactAcct.setAD_Client_ID(factAcct.getAD_Client_ID());
			mFactAcct.setAD_Org_ID(factAcct.getAD_Org_ID());
			mFactAcct.setUserElement2_ID(factAcct.getUserElement2_ID());
			mFactAcct.setUserElement1_ID(factAcct.getUserElement1_ID());
			mFactAcct.setUser2_ID(factAcct.getUser2_ID());
			mFactAcct.setUser1_ID(factAcct.getUser1_ID());
			mFactAcct.setRecord_ID(factAcct.getRecord_ID());
			mFactAcct.setQty(factAcct.getQty());
			mFactAcct.setPostingType(factAcct.getPostingType());
			mFactAcct.setM_Product_ID(factAcct.getM_Product_ID());
			mFactAcct.setM_Locator_ID(factAcct.getM_Locator_ID());
			mFactAcct.setLine_ID(factAcct.getLine_ID());
			mFactAcct.setGL_Category_ID(factAcct.getGL_Category_ID());
			mFactAcct.setGL_Budget_ID(budget.getTargetBudget_ID());
			mFactAcct.setDateTrx(factAcct.getDateAcct());
			mFactAcct.setDateAcct(factAcct.getDateAcct());
			mFactAcct.setC_UOM_ID(factAcct.getC_UOM_ID());
			mFactAcct.setC_Tax_ID(factAcct.getC_Tax_ID());
			mFactAcct.setC_SubAcct_ID(factAcct.getC_SubAcct_ID());
			mFactAcct.setC_SalesRegion_ID(factAcct.getC_SalesRegion_ID());
			mFactAcct.setC_ProjectTask_ID(factAcct.getC_ProjectTask_ID());
			mFactAcct.setC_ProjectPhase_ID(factAcct.getC_ProjectPhase_ID());
			mFactAcct.setC_Project_ID(factAcct.getC_Project_ID());
			mFactAcct.setC_Period_ID(budget.getC_Period_To_ID());
			mFactAcct.setC_LocTo_ID(factAcct.getC_LocTo_ID());
			mFactAcct.setC_LocFrom_ID(factAcct.getC_LocFrom_ID());
			mFactAcct.setC_Currency_ID(factAcct.getC_Currency_ID());
			mFactAcct.setC_Campaign_ID(factAcct.getC_Campaign_ID());
			mFactAcct.setC_BPartner_ID(factAcct.getC_BPartner_ID());
			mFactAcct.setC_Activity_ID(factAcct.getC_Activity_ID());
			mFactAcct.setC_AcctSchema_ID(factAcct.getC_AcctSchema_ID());
			mFactAcct.setAmtSourceDr(factAcct.getAmtSourceDr());
			mFactAcct.setAmtSourceCr(factAcct.getAmtSourceCr());
			mFactAcct.setAmtAcctDr(factAcct.getAmtAcctDr());
			mFactAcct.setAmtAcctCr(factAcct.getAmtAcctCr());
			mFactAcct.setAD_OrgTrx_ID(factAcct.getAD_OrgTrx_ID());
			mFactAcct.setAD_Table_ID(factAcct.getAD_Table_ID());
			mFactAcct.setAccount_ID(factAcct.getAccount_ID());
			mFactAcct.setA_Asset_ID(factAcct.getA_Asset_ID());
			mFactAcct.save();
		}
		return "Complete";
	}

	private MFactAcct[] getOrderLines(int C_AcctSchema_ID, int C_Period_ID,
			int GL_Budget_ID) {
		ArrayList<MFactAcct> list = new ArrayList<MFactAcct>();
		String sql = "SELECT * FROM Fact_Acct WHERE PostingType = 'B' AND AD_Client_ID = "
				+ getAD_Client_ID()
				+ " "
				+ "AND GL_Budget_ID = "
				+ GL_Budget_ID
				+ " AND C_Period_ID = "
				+ C_Period_ID
				+ " AND C_AcctSchema_ID = "
				+ C_AcctSchema_ID
				+ " "
				+ "AND Fact_Acct_ID NOT IN (SELECT Fact_Acct_ID FROM XX_CopyBudgetLine WHERE XX_CopyBudget_ID = "
				+ getRecord_ID() + ") ";

		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MFactAcct line = new MFactAcct(getCtx(), rs, get_Trx());
				list.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		MFactAcct[] lines = new MFactAcct[list.size()];
		list.toArray(lines);
		return lines;
	}

}
