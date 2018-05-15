package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;

import rsge.po.X_XX_BudgetTransactionLine;

public class MBudgetTransactionLine extends X_XX_BudgetTransactionLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MBudgetTransactionLine(Ctx ctx, int XX_BudgetTransactionLine_ID, Trx trx) {
		super(ctx, XX_BudgetTransactionLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MBudgetTransactionLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		updateHeader();
		return true;
	}
	
	public static MBudgetTransactionLine get(Ctx ctx, int AD_Table_ID, int record_ID, Trx trx)
	{
		MBudgetTransactionLine line = null;
		String sql = "SELECT * FROM XX_BudgetTransactionLine "
				+ "WHERE AD_Table_ID = ? AND Record_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Table_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				line = new MBudgetTransactionLine(ctx, rs, trx);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}
	
	public static MBudgetTransactionLine createLine(Ctx ctx, int AD_Table_ID, int recordID, int SourceTable_ID, int sourceRecordID, 
			int AD_Org_ID, Timestamp budgetDate, int accountID, Trx trx)
	{
		return MBudgetTransactionLine.createLine(ctx, AD_Table_ID, recordID, SourceTable_ID, sourceRecordID, AD_Org_ID, budgetDate, accountID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, trx);
	}
	
	public static MBudgetTransactionLine createLine(Ctx ctx, int AD_Table_ID, int recordID, int SourceTable_ID, int sourceRecordID, 
			int AD_Org_ID, Timestamp budgetDate, int accountID, int A_Asset_ID, int C_Activity_ID, int C_BPartner_ID, int C_Campaign_ID, 
			int C_LocFrom_ID, int C_LocTo_ID, 
			int C_Project_ID, int C_ProjectPhase_ID, int C_ProjectTask_ID, int C_SalesRegion_ID, 			
			int M_Product_ID, int User1_ID, int User2_ID,  int UserElement1_ID, int UserElement2_ID, 
			int C_SubAcct_ID, Trx trx)
	{
		budgetDate = TimeUtil.getDay(budgetDate);
		MBudgetTransactionLine line = new MBudgetTransactionLine(ctx, 0, trx);
		line.setXX_BudgetTransaction_ID(MBudgetTransaction.get(ctx, AD_Org_ID, budgetDate, accountID, A_Asset_ID, C_Activity_ID, C_BPartner_ID, C_Campaign_ID, C_LocFrom_ID, C_LocTo_ID, C_Project_ID, C_ProjectPhase_ID, C_ProjectTask_ID, C_SalesRegion_ID, M_Product_ID, User1_ID, User2_ID, UserElement1_ID, UserElement2_ID, C_SubAcct_ID, trx));
		line.setAD_Org_ID(AD_Org_ID);
		line.setAD_Table_ID(AD_Table_ID);
		line.setRecord_ID(recordID);
		if(SourceTable_ID!=0)
			line.setSourceTable_ID(SourceTable_ID);
		if(sourceRecordID!=0)
			line.setSourceRecord_ID(sourceRecordID);
		line.save();
		return line;
	}
	
	@Override
	protected boolean afterDelete(boolean success) {
		updateHeader();
		return true;
	}
	
	private void updateHeader()
	{
		BigDecimal reservedAmt = BigDecimal.ZERO;
		BigDecimal unrealizedAmt = BigDecimal.ZERO;
		BigDecimal realizedAmt = BigDecimal.ZERO;
		
		String sql = "SELECT COALESCE(SUM(ReservedAmt),0) AS ReservedAmt, "
				+ "COALESCE(SUM(UnrealizedAmt),0) AS UnrealizedAmt, "
				+ "COALESCE(SUM(RealizedAmt),0) AS RealizedAmt "
				+ "FROM XX_BudgetTransactionLine "
				+ "WHERE XX_BudgetTransaction_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_BudgetTransaction_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				reservedAmt = rs.getBigDecimal(1);
				unrealizedAmt = rs.getBigDecimal(2);
				realizedAmt = rs.getBigDecimal(3);
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		String update = "UPDATE XX_BudgetTransaction "
				+ "SET ReservedAmt = ?, UnrealizedAmt = ?, RealizedAmt = ? "
				+ "WHERE XX_BudgetTransaction_ID = ? ";
		DB.executeUpdate(get_Trx(), update, reservedAmt, unrealizedAmt, realizedAmt, getXX_BudgetTransaction_ID());
		
	}

}
