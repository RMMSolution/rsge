package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_BudgetTransaction;

public class MBudgetTransaction extends X_XX_BudgetTransaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MBudgetTransaction(Ctx ctx, int XX_BudgetTransaction_ID, Trx trx) {
		super(ctx, XX_BudgetTransaction_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MBudgetTransaction(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}	
	
	public static int get(Ctx ctx,  int AD_Org_ID, 
			Timestamp budgetDate, int accountID, int A_Asset_ID, int C_Activity_ID, int C_BPartner_ID, int C_Campaign_ID, 
			int C_LocFrom_ID, int C_LocTo_ID, 
			int C_Project_ID, int C_ProjectPhase_ID, int C_ProjectTask_ID, int C_SalesRegion_ID, 			
			int M_Product_ID, int User1_ID, int User2_ID,  int UserElement1_ID, int UserElement2_ID, 
			int C_SubAcct_ID, Trx trx)
	{
		int ID = 0;
		StringBuilder sql = new StringBuilder("SELECT XX_BudgetTransaction_ID "
				+ "FROM XX_BudgetTransaction "
				+ "WHERE AD_Org_ID = ? "
				+ "AND Account_ID = ? "
				+ "AND BudgetDate = ? ");
		
		if(A_Asset_ID!=0)
		{
			sql.append(" AND A_Asset_ID = ");
			sql.append(A_Asset_ID);
		}
		if(C_Activity_ID!=0)
		{
			sql.append(" AND C_Activity_ID = ");
			sql.append(C_Activity_ID);
		}
		if(C_BPartner_ID!=0)
		{
			sql.append(" AND C_BPartner_ID = ");
			sql.append(C_BPartner_ID);
		}
		if(C_Campaign_ID!=0)
		{
			sql.append(" AND C_Campaign_ID = ");
			sql.append(C_Campaign_ID);
		}
		if(C_LocFrom_ID!=0)
		{
			sql.append(" AND C_LocFrom_ID = ");
			sql.append(C_LocFrom_ID);
		}
		if(C_LocTo_ID!=0)
		{
			sql.append(" AND C_LocTo_ID = ");
			sql.append(C_LocTo_ID);
		}
		if(C_Project_ID!=0)
		{
			sql.append(" AND C_Project_ID = ");
			sql.append(C_Project_ID);
		}
		if(C_ProjectPhase_ID!=0)
		{
			sql.append(" AND C_ProjectPhase_ID = ");
			sql.append(C_ProjectPhase_ID);
		}
		if(C_ProjectTask_ID!=0)
		{
			sql.append(" AND C_ProjectTask_ID = ");
			sql.append(C_ProjectTask_ID);
		}
		if(C_SalesRegion_ID!=0)
		{
			sql.append(" AND C_SalesRegion_ID = ");
			sql.append(C_SalesRegion_ID);
		}
		if(M_Product_ID!=0)
		{
			sql.append(" AND M_Product_ID = ");
			sql.append(M_Product_ID);
		}
		if(User1_ID!=0)
		{
			sql.append(" AND User1_ID = ");
			sql.append(User1_ID);
		}
		if(User2_ID!=0)
		{
			sql.append(" AND User2_ID = ");
			sql.append(User2_ID);
		}
		if(UserElement1_ID!=0)
		{
			sql.append(" AND UserElement1_ID = ");
			sql.append(UserElement1_ID);
		}
		if(UserElement2_ID!=0)
		{
			sql.append(" AND UserElement2_ID = ");
			sql.append(UserElement2_ID);
		}
		if(C_SubAcct_ID!=0)
		{
			sql.append(" AND C_SubAcct_ID = ");
			sql.append(C_SubAcct_ID);
		}
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Org_ID);
			pstmt.setInt(2, accountID);
			pstmt.setTimestamp(3, budgetDate);
			rs = pstmt.executeQuery();
			if(rs.next())
				ID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

		if(ID==0)
		{
			MBudgetTransaction bt = new MBudgetTransaction(ctx, 0, trx);
			bt.setAD_Org_ID(AD_Org_ID);
			bt.setBudgetDate(budgetDate);
			bt.setAccount_ID(accountID);

			if(A_Asset_ID!=0)
				bt.setA_Asset_ID(A_Asset_ID);
			if(C_Activity_ID!=0)
				bt.setC_Activity_ID(C_Activity_ID);
			if(C_BPartner_ID!=0)
				bt.setC_BPartner_ID(C_BPartner_ID);
			if(C_Campaign_ID!=0)
				bt.setC_Campaign_ID(C_Campaign_ID);
			if(C_LocFrom_ID!=0)
				bt.setC_LocFrom_ID(C_LocFrom_ID);
			if(C_LocTo_ID!=0)
				bt.setC_LocTo_ID(C_LocTo_ID);
			if(C_Project_ID!=0)
				bt.setC_Project_ID(C_Project_ID);
			if(C_ProjectPhase_ID!=0)
				bt.setC_ProjectPhase_ID(C_ProjectPhase_ID);
			if(C_ProjectTask_ID!=0)
				bt.setC_ProjectTask_ID(C_ProjectTask_ID);
			if(C_SalesRegion_ID!=0)
				bt.setC_SalesRegion_ID(C_SalesRegion_ID);
			if(M_Product_ID!=0)
				bt.setM_Product_ID(M_Product_ID);
			if(User1_ID!=0)
				bt.setUser1_ID(User1_ID);
			if(User2_ID!=0)
				bt.setUser2_ID(User1_ID);
			if(UserElement1_ID!=0)
				bt.setUserElement1_ID(UserElement1_ID);
			if(UserElement2_ID!=0)
				bt.setUserElement2_ID(UserElement2_ID);
			if(C_SubAcct_ID!=0)
				bt.setC_SubAcct_ID(C_SubAcct_ID);
			

			if(bt.save())
				ID = bt.getXX_BudgetTransaction_ID();
		}
		return ID;
	}

}
