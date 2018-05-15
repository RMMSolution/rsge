package rsge.report;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MElementValue;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MBudgetInfo;
import rsge.tools.BudgetCalculation;

public class GenerateBudgetRpt extends SvrProcess {
	
	private int						p_AD_Org_ID = 0;
	private Timestamp				p_DateReport = null;
	private int						p_C_Currency_ID = 0;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null)
				;
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = element.getParameterAsInt();
			else if (name.equals("DateReport"))
				p_DateReport = (Timestamp)element.getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
		MBudgetInfo bi = MBudgetInfo.get(getCtx(), getAD_Client_ID(), get_Trx());
		MAcctSchema as = new MAcctSchema(getCtx(), bi.getC_AcctSchema_ID(), get_Trx());
		p_C_Currency_ID = as.getC_Currency_ID();
	}

	@Override
	protected String doIt() throws Exception {
		String delete = "DELETE FROM T_BudgetReport WHERE AD_Client_ID = ? ";
		DB.executeUpdate(get_Trx(), delete, getAD_Client_ID());
		
		// TODO Auto-generated method stub
		Integer[] accountList = getBudgetAcctList();
		int no = 1;
		for(Integer accountID : accountList)
		{
			BudgetCalculation bc = new BudgetCalculation(getCtx(), p_AD_Org_ID, p_DateReport, p_DateReport, null, null, false, accountID.intValue(), 0, 0, 0, 0, 0, 0, 0, 0, false, get_Trx());
			MElementValue ev = new MElementValue(getCtx(), accountID.intValue(), get_Trx());
			StringBuilder desc = new StringBuilder(ev.getValue());
			desc.append(" " + ev.getName());
			if(createLines(no, accountID, desc.toString(), bc.getBudgetAmt(), bc.getPendingAmt(), bc.getReservedAmt(), bc.getCommitedAmt(), bc.getUnrealizedAmt(), bc.getRealizedAmt(), bc.getRemainsAmt()))
				no++;
		}
		
		return null;
	}
	
	private boolean createLines(int lineNo, int accountID, String desc, BigDecimal budgetAmt, BigDecimal pendingAmt, BigDecimal reservedAmt, BigDecimal commitedAmt, BigDecimal unrealizedAmt, BigDecimal realizedAmt, BigDecimal remainingAmt)
	{
		String insert = "INSERT INTO T_BudgetReport (AD_Client_ID, AD_Org_ID, Line, C_Currency_ID, "
				+ "Account_ID, DateReport, Description, "
				+ "BudgetAmt, PendingAmt, ReservedBudgetAmt, CommittedAmt, UnrealizedAmt, RealizedAmt, RemainingAmt) "
				+ "VALUES(?, ?, ?, ?, "
				+ "?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?) ";
		DB.executeUpdate(get_Trx(), insert, getAD_Client_ID(), p_AD_Org_ID, lineNo, p_C_Currency_ID, 
				accountID, p_DateReport, desc, 
				budgetAmt, pendingAmt, reservedAmt, commitedAmt, unrealizedAmt, realizedAmt, remainingAmt
				);
		return true;
	}
	
	private Integer[] getBudgetAcctList()
	{
		ArrayList<Integer> list = new ArrayList<>();
		String sql = "SELECT ev.C_ElementValue_ID FROM Fact_Acct fa "
				+ "INNER JOIN C_ElementValue ev ON fa.Account_ID = ev.C_ElementValue_ID "
				+ "WHERE fa.AD_Org_ID = ? "
				+ "AND ev.C_ElementValue_ID NOT IN (SELECT vc.Account_ID FROM C_ValidCombination vc INNER JOIN XX_BudgetInfo bi ON vc.C_ValidCombination_ID = bi.BudgetClearing_Acct ) "
				+ "AND fa.PostingType = 'B' "
				+ "GROUP BY ev.C_ElementValue_ID, ev.Value, fa.AD_Org_ID "
				+ "ORDER BY ev.Value ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, p_AD_Org_ID);
			rs = pstmt.executeQuery();
			while(rs.next())
				list.add(rs.getInt(1));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		Integer[] retValue = new Integer[list.size()];
		list.toArray(retValue);
		return retValue;
	}

}
