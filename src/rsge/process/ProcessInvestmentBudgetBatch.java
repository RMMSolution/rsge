package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.process.DocumentEngine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.vos.DocActionConstants;

import rsge.model.MInvestmentBudget;

public class ProcessInvestmentBudgetBatch extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		ArrayList<MInvestmentBudget> ibList = null;
		String sql = "SELECT * FROM XX_InvestmentBudget " +
				"WHERE Processed = 'N' " +
				"AND DocStatus IN ('DR') " +
				"AND AD_Client_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				if(ibList==null)
					ibList = new ArrayList<MInvestmentBudget>();
				ibList.add(new MInvestmentBudget(getCtx(), rs, get_Trx()));
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		int listSize = ibList.size();
		int processed = 0;
		int error = 0;
		for(int i = 0; i<listSize; i++)
		{
			MInvestmentBudget ib = ibList.get(i);
			if(DocumentEngine.processIt(ib, DocActionConstants.ACTION_Complete))
				processed = processed+1;
			else
				error = error+1;
			ib.save();
		}
		return "Processed : " + processed + " - Not Processed : " + error;
	}

}
