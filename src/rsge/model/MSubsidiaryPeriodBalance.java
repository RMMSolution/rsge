package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MClientInfo;
import org.compiere.model.MPeriod;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_SubsidiaryPeriodBalance;

public class MSubsidiaryPeriodBalance extends X_XX_SubsidiaryPeriodBalance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MSubsidiaryPeriodBalance(Ctx ctx, int XX_SubsidiaryPeriodBalance_ID,
			Trx trx) {
		super(ctx, XX_SubsidiaryPeriodBalance_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MSubsidiaryPeriodBalance(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeDelete() {
		// DELETE ALL LINES First
		String delete = "DELETE XX_PeriodAccountBalance WHERE XX_SubsidiaryPeriodBalance_ID = ? ";
		DB.executeUpdate(get_Trx(), delete, getXX_SubsidiaryPeriodBalance_ID());
		return true;
	}
	
	public static int get(Ctx ctx, int AD_Org_ID, int C_Period_ID, Trx trx)
	{
		int ID = 0;
		
		String sql = "SELECT XX_SubsidiaryPeriodBalance_ID FROM XX_SubsidiaryPeriodBalance " +
				"WHERE AD_Org_ID = ? AND C_Period_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Org_ID);
			pstmt.setInt(2, C_Period_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				ID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Then here");
		if(ID==0)
		{
			MSubsidiaryPeriodBalance spb = new MSubsidiaryPeriodBalance(ctx, 0, trx);
			spb.setAD_Org_ID(AD_Org_ID);
			spb.setC_Period_ID(C_Period_ID);
			MPeriod period = new MPeriod(ctx, C_Period_ID, trx);
			MClientInfo ci = MClientInfo.get(ctx, ctx.getAD_Client_ID(), trx);			
			MAcctSchema as = new MAcctSchema(ctx, ci.getC_AcctSchema1_ID(), trx);
			System.out.println("Accounting Schema " + as.getName());
			spb.setC_AcctSchema_ID(ci.getC_AcctSchema1_ID());
			spb.setC_Currency_ID(as.getC_Currency_ID());
			spb.setC_Year_ID(period.getC_Year_ID());
			spb.setDateDoc(period.getEndDate());
			spb.setDateAcct(period.getEndDate());
			if(spb.save())
				ID = spb.getXX_SubsidiaryPeriodBalance_ID();
		}
		return ID;
	}

}
