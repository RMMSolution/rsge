package rsge.utils.customization;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.compiere.model.MBankStatement;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class UpdateBankAccountBalance extends SvrProcess {
	
	private int			p_BankAccount_ID = 0;
	private Timestamp	p_BaseDate = null;
	private BigDecimal	p_BeginningBalance = BigDecimal.ZERO;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter params[] = getParameter();
		for(ProcessInfoParameter param : params){
			String name = param.getParameterName();
			if(name.equals("C_BankAccount_ID"))
			{
				p_BankAccount_ID = param.getParameterAsInt();
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
		ArrayList<MBankStatement> bsList = getBankStatement();
		BigDecimal currentBalance = p_BeginningBalance;
		for(MBankStatement bs : bsList)
		{
			BigDecimal endingBalance = currentBalance.add(bs.getStatementDifference());
			System.out.println(" ");
			System.out.println("Bank Statement " + bs.getName());
			System.out.println("Statement date " + bs.getStatementDate());
			System.out.println("Beginning Balance " + currentBalance);
			System.out.println("Transaction Balance " + bs.getStatementDifference());
			System.out.println("Current balance " + endingBalance);
			bs.setEndingBalance(endingBalance);
			if(bs.save())
				currentBalance = endingBalance;
		}
		
		// Check Bank Account Balance
		String update = "UPDATE C_BankAccount SET CurrentBalance = ? WHERE C_BankAccount_ID = ? ";
		DB.executeUpdate(get_Trx(), update, currentBalance, p_BankAccount_ID);
		
		BigDecimal diff = currentBalance.subtract(p_BeginningBalance);
		return "Beginning balance : " + p_BeginningBalance + ", Ending balance : " + currentBalance + ", Difference : " + diff;
	}
	
	private ArrayList<MBankStatement> getBankStatement()
	{
		ArrayList<MBankStatement> bsList = new ArrayList<>();
		String sql = "SELECT * FROM C_BankStatement " +
				"WHERE DocStatus IN ('CO', 'CL') " +
				"AND C_BankAccount_ID = ? " +
				"AND StatementDate >= ? " +
				"ORDER BY StatementDate ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, p_BankAccount_ID);
			pstmt.setTimestamp(2, p_BaseDate);
			rs = pstmt.executeQuery();
			while(rs.next())
				bsList.add(new MBankStatement(getCtx(), rs, get_Trx()));
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return bsList;
	}

}
