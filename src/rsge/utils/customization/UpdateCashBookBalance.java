package rsge.utils.customization;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MCash;

public class UpdateCashBookBalance extends SvrProcess {
	
	private int			p_CashBook_ID = 0;
	private Timestamp	p_BaseDate = null;
	private BigDecimal	p_BeginningBalance = BigDecimal.ZERO;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter params[] = getParameter();
		for(ProcessInfoParameter param : params){
			String name = param.getParameterName();
			if(name.equals("C_CashBook_ID"))
			{
				p_CashBook_ID = param.getParameterAsInt();
			}			
			if(name.equals("StartDate"))
			{
				p_BaseDate = (Timestamp)param.getParameter();
			}			
			if(name.equals("BeginningBalance"))
			{
				p_BeginningBalance = (BigDecimal)param.getParameter();
			}			
		}		
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		ArrayList<MCash> cList = getCashJournal();
		BigDecimal currentBalance = p_BeginningBalance;
		for(MCash c : cList)
		{
			BigDecimal endingBalance = currentBalance.add(c.getStatementDifference());
			System.out.println(" ");
			System.out.println("Cash Journal " + c.getDocumentNo());
			System.out.println("Statement date " + c.getStatementDate());
			System.out.println("Beginning Balance " + currentBalance);
			System.out.println("Transaction Balance " + c.getStatementDifference());
			System.out.println("Current balance " + endingBalance);
			c.setEndingBalance(endingBalance);
			if(c.save())
				currentBalance = endingBalance;
		}
		
		// Check Bank Account Balance
		String update = "UPDATE C_CashBook SET CashBookSetAmt = ? WHERE C_CashBook_ID = ? ";
		DB.executeUpdate(get_Trx(), update, currentBalance, p_CashBook_ID);
		
		BigDecimal diff = currentBalance.subtract(p_BeginningBalance);
		return "Beginning balance : " + p_BeginningBalance + ", Ending balance : " + currentBalance + ", Difference : " + diff;
	}
	
	private ArrayList<MCash> getCashJournal()
	{
		ArrayList<MCash> cList = new ArrayList<>();
		String sql = "SELECT * FROM C_Cash " +
				"WHERE DocStatus IN ('CO', 'CL') " +
				"AND C_CashBook_ID = ? " +
				"AND StatementDate >= ? " +
				"ORDER BY StatementDate ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, p_CashBook_ID);
			pstmt.setTimestamp(2, p_BaseDate);
			rs = pstmt.executeQuery();
			while(rs.next())
				cList.add(new MCash(getCtx(), rs, get_Trx()));
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return cList;
	}

}
