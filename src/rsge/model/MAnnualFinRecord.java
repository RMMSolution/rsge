package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MAcctSchema;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_AnnualFinRecord;

public class MAnnualFinRecord extends X_XX_AnnualFinRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MAnnualFinRecord(Ctx ctx, int XX_AnnualFinRecord_ID, Trx trx) {
		super(ctx, XX_AnnualFinRecord_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MAnnualFinRecord(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public static MAnnualFinRecord get(Ctx ctx, int C_AcctSchema_ID, int C_Year_ID, String year, int AD_Org_ID, Trx trx)
	{
		MAnnualFinRecord retValue = null;
		
		if(C_Year_ID==0)
		{
			if(year==null)
				return retValue;
			String query = "SELECT y.C_Year_ID FROM C_Year y " +
					"INNER JOIN AD_ClientInfo ci ON (y.C_Calendar_ID=ci.C_Calendar_ID) " +
					"WHERE ci.AD_Client_ID = ? " +
					"AND y.FiscalYear = ?";
			PreparedStatement pstmt1 = DB.prepareStatement(query, trx);
			ResultSet rs1 = null;
			try{
				pstmt1.setInt(1, ctx.getAD_Client_ID());
				pstmt1.setString(2, year);
				rs1 = pstmt1.executeQuery();
				if(rs1.next())
					C_Year_ID = rs1.getInt(1);
				rs1.close();
				pstmt1.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		StringBuilder sql = new StringBuilder("SELECT * FROM XX_AnnualFinRecord " +
				"WHERE C_AcctSchema_ID = ? AND C_Year_ID = ?");
		if(AD_Org_ID!=0)
		{
			sql.append(" AND AD_Org_ID = ");
			sql.append(AD_Org_ID);
		}
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, C_AcctSchema_ID);
			pstmt.setInt(2, C_Year_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				retValue = new MAnnualFinRecord(ctx, rs, trx);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(retValue==null)
		{
			retValue = new MAnnualFinRecord(ctx, 0, trx);
			retValue.setC_Year_ID(C_Year_ID);
			retValue.setC_AcctSchema_ID(C_AcctSchema_ID);
			if(AD_Org_ID!=0)
				retValue.setAD_Org_ID(AD_Org_ID);
			MAcctSchema asi = new MAcctSchema(ctx, C_AcctSchema_ID, trx);
			retValue.setC_Currency_ID(asi.getC_Currency_ID());
			retValue.setCurrentAssetAmt(BigDecimal.ZERO);
			retValue.setCurrentLiabilityAmt(BigDecimal.ZERO);
			retValue.setEATAmt(BigDecimal.ZERO);
			retValue.setEBITAmt(BigDecimal.ZERO);
			retValue.setEBITDAAmt(BigDecimal.ZERO);
			retValue.setTotalAssetAmt(BigDecimal.ZERO);
			retValue.setTotalCOGMAmt(BigDecimal.ZERO);
			retValue.setTotalCOGSAmt(BigDecimal.ZERO);
			retValue.setTotalEquityAmt(BigDecimal.ZERO);
			retValue.setTotalExpenseAmt(BigDecimal.ZERO);
			retValue.setTotalLiabilityAmt(BigDecimal.ZERO);
			retValue.setTotalRevenueAmt(BigDecimal.ZERO);
			retValue.setGrossProfitAmt(BigDecimal.ZERO);
			retValue.setOperatingProfitAmt(BigDecimal.ZERO);
			retValue.save();
		}
		return retValue;
	}

}
