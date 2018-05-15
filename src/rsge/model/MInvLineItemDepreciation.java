/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_InvLineItemDepreciation;

/**
 * @author FANNY R
 *
 */
public class MInvLineItemDepreciation extends X_XX_InvLineItemDepreciation {

	/** Logger for class MInvLineItemDepreciation */
	private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MInvLineItemDepreciation.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_InvLineItemDepreciation_ID
	 * @param trx
	 */
	public MInvLineItemDepreciation(Ctx ctx, int XX_InvLineItemDepreciation_ID,
			Trx trx) {
		super(ctx, XX_InvLineItemDepreciation_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MInvLineItemDepreciation(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	protected boolean afterDelete(boolean success) {
		// Update Asset Group Depreciation
		updateAssetGroupDepreciation();
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// Update Asset Group Depreciation
		updateAssetGroupDepreciation();
		return true;
	}

	private void updateAssetGroupDepreciation()
	{
		MInvestmentBudgetLineItem ibli = new MInvestmentBudgetLineItem(getCtx(), getXX_InvestmentBudgetLineItem_ID(), get_Trx());
		
		// Get Source
		BigDecimal period1 = BigDecimal.ZERO;
		BigDecimal period2 = BigDecimal.ZERO;
		BigDecimal period3 = BigDecimal.ZERO;
		BigDecimal period4 = BigDecimal.ZERO;
		BigDecimal period5 = BigDecimal.ZERO;
		BigDecimal period6 = BigDecimal.ZERO;
		BigDecimal period7 = BigDecimal.ZERO;
		BigDecimal period8 = BigDecimal.ZERO;
		BigDecimal period9 = BigDecimal.ZERO;
		BigDecimal period10 = BigDecimal.ZERO;
		BigDecimal period11 = BigDecimal.ZERO;
		BigDecimal period12 = BigDecimal.ZERO;
		
		String sql = "SELECT COALESCE(SUM(ilid.Period1), 0), COALESCE(SUM(ilid.Period2), 0), COALESCE(SUM(ilid.Period3), 0), COALESCE(SUM(ilid.Period4), 0), " +
				"COALESCE(SUM(ilid.Period5), 0), COALESCE(SUM(ilid.Period6), 0), COALESCE(SUM(ilid.Period7), 0), COALESCE(SUM(ilid.Period8), 0), " +
				"COALESCE(SUM(ilid.Period9), 0), COALESCE(SUM(ilid.Period10), 0), COALESCE(SUM(ilid.Period11), 0), COALESCE(SUM(ilid.Period12), 0) " +
				"FROM XX_InvLineItemDepreciation ilid " +
				"WHERE ilid.XX_InvestmentBudgetLineItem_ID IN " +
				"(SELECT ibli.XX_InvestmentBudgetLineItem_ID " +
				"FROM XX_InvestmentBudgetLineItem ibli " +
				"WHERE ibli.XX_InvestmentBudgetLine_ID = ?) ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, ibli.getXX_InvestmentBudgetLine_ID());
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				period1 = rs.getBigDecimal(1);
				period2 = rs.getBigDecimal(2);
				period3 = rs.getBigDecimal(3);
				period4 = rs.getBigDecimal(4);
				period5 = rs.getBigDecimal(5);
				period6 = rs.getBigDecimal(6);
				period7 = rs.getBigDecimal(7);
				period8 = rs.getBigDecimal(8);
				period9 = rs.getBigDecimal(9);
				period10 = rs.getBigDecimal(10);
				period11 = rs.getBigDecimal(11);
				period12 = rs.getBigDecimal(12);				
			}			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		MInvestmentBudgetLine ibl = new MInvestmentBudgetLine(getCtx(), ibli.getXX_InvestmentBudgetLine_ID(), get_Trx());
		
		int XX_InvestmentBudgetLine_ID = ibl.getXX_InvestmentBudgetLine_ID();
		
		// Update Asset Group Depreciation
		String update = "UPDATE XX_InvLineDepreciation " +
				"SET Period1 = ?, Period2 = ?, Period3 = ?, Period4 = ?, Period5 = ?, Period6 = ?, Period7 = ?, " +
				"Period8 = ?, Period9 = ?, Period10 = ?, Period11 = ?, Period12 = ? " +
				"WHERE XX_InvestmentBudgetLine_ID = ? ";
		
		int no = DB.executeUpdate(get_Trx(), update, new Object[]{period1, period2, period3, period4, period5, period6, period7, period8, 
			period9, period10, period11, period12, XX_InvestmentBudgetLine_ID});
		if (no != 1)
			log.warning("(1) #" + no);
	}	

}
