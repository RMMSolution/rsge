package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.model.MPeriod;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MClosingPeriod;
import rsge.model.MClosingPeriodLine;

public class RunClosingPeriodCalculation extends SvrProcess {

	MClosingPeriod cp = null;
	Timestamp startdate = null;
	Timestamp enddate = null;
	
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		cp = new MClosingPeriod(getCtx(), getRecord_ID(), get_Trx());
		MPeriod mPeriod = new MPeriod(getCtx(), cp.getC_Period_ID(), get_Trx());
		startdate = mPeriod.getStartDate();
		enddate = mPeriod.getEndDate();
		DB.executeUpdate(get_Trx(), "DELETE FROM XX_ClosingPeriodLine WHERE XX_ClosingPeriod_ID = ? ",cp.getXX_ClosingPeriod_ID());
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT AD_Client_ID, AD_Org_ID, Account_ID, Value AS AccountCode, OrgValue, AccountType, Amount " +
				"FROM " +
				"(" +
				"SELECT fa.AD_Client_ID, fa.AD_Org_ID, fa.Account_ID, ev.Value, o.Value AS OrgValue, ev.AccountType, " +
				"COALESCE(SUM(CASE WHEN ev.AccountType = 'R' THEN fa.AmtAcctCr-fa.AmtAcctDr ELSE fa.AmtAcctDr-fa.AmtAcctCr END),0) AS Amount " +
				"FROM Fact_Acct fa " +
				"INNER JOIN C_ElementValue ev ON (fa.Account_ID = ev.C_ElementValue_ID) " +
				"INNER JOIN AD_Org o ON (fa.AD_Org_ID = o.AD_Org_ID) " +
				"WHERE ev.AccountType IN ('R', 'E') " +
				"AND fa.PostingType IN ('A') " +
				"AND fa.DateAcct BETWEEN ? AND ? " +
				"AND fa.AD_Client_ID = ?  " +
				"GROUP BY fa.AD_Client_ID, fa.AD_Org_ID, fa.Account_ID, ev.Value, o.Value, ev.AccountType " +
				") " +
				"WHERE Amount != 0 " +
				"ORDER BY AccountCode";
		PreparedStatement ps = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try {
			ps.setTimestamp(1, startdate);
			ps.setTimestamp(2, enddate);
			ps.setInt(3, getAD_Client_ID());
			rs = ps.executeQuery();
			while(rs.next()){
				MClosingPeriodLine line = new MClosingPeriodLine(getCtx(), 0, get_Trx());
				line.setAD_Client_ID(getAD_Client_ID());
				line.setAD_Org_ID(cp.getAD_Org_ID());
				line.setXX_ClosingPeriod_ID(cp.getXX_ClosingPeriod_ID());
				line.setAccount_ID(rs.getInt(3));
				if(rs.getString(6).equals("R")){
					if(rs.getBigDecimal(7).signum()>0){
						line.setAmtAcctDr(rs.getBigDecimal(7));
					}else{
						line.setAmtAcctCr(rs.getBigDecimal(7));
					}
				}else{
					if(rs.getBigDecimal(7).signum()>0){
						line.setAmtAcctCr(rs.getBigDecimal(7));
					}else{
						line.setAmtAcctDr(rs.getBigDecimal(7));
					}
				}
				line.setAD_OrgTrx_ID(rs.getInt(2));
				line.save();
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "Process Complete";
	}

}
