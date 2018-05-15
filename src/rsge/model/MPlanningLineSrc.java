/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_PlanningLineSrc;

/**
 * @author FANNY
 *
 */
public class MPlanningLineSrc extends X_XX_PlanningLineSrc {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Logger for class MPlanningLineSrc */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MPlanningLineSrc.class);
    
	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param XX_PlanningLineSrc_ID
	 * @param trx
	 */
	public MPlanningLineSrc(Ctx ctx, int XX_PlanningLineSrc_ID, Trx trx) {
		super(ctx, XX_PlanningLineSrc_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MPlanningLineSrc(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.compiere.framework.PO#afterSave(boolean, boolean)
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		updatePlanningLineAmt();
		return true;
	}
	
	@Override
	protected boolean afterDelete(boolean success) {
		// TODO Auto-generated method stub
		updatePlanningLineAmt();
		return true;
	}

	/**
	 * Update Planning Line Amount
	 */
	private void updatePlanningLineAmt()
	{
		// Update Planning Line Period Amount
//		String update = "UPDATE XX_BudgetPlanningLinePeriod bplp " +
//				"SET (bplp.AmtAcctDr, bplp.AmtAcctCr, bplp.Qty, bplp.Processed) = " +
//				"(SELECT SUM(pls.AmtAcctDr), SUM(pls.AmtAcctCr), SUM(pls.Qty), 'N' " +
//				"FROM XX_PlanningLineSrc pls " +
//				"WHERE pls.XX_BudgetPlanningLinePeriod_ID = bplp.XX_BudgetPlanningLinePeriod_ID) " +
//				"WHERE bplp.XX_BudgetPlanningLinePeriod_ID = ? "; // 1
		String update = null;
		int no = 0;
		String sql1 = "SELECT SUM(pls.AmtAcctDr), SUM(pls.AmtAcctCr), SUM(pls.Qty), 'N' " +
				"FROM XX_PlanningLineSrc pls " +
				"WHERE pls.XX_BudgetPlanningLinePeriod_ID = ?";
		PreparedStatement ps1 = DB.prepareStatement(sql1, get_Trx());
		ResultSet rs1 = null;
		try {
			ps1.setInt(1, getXX_BudgetPlanningLinePeriod_ID());
			rs1 = ps1.executeQuery();
			if(rs1.next()){
				update = "UPDATE XX_BudgetPlanningLinePeriod bplp " +
						"SET bplp.AmtAcctDr = "+rs1.getBigDecimal(1)+", bplp.AmtAcctCr = "+rs1.getBigDecimal(2)+", " +
								"bplp.Qty = "+rs1.getBigDecimal(3)+", bplp.Processed = '"+rs1.getString(4)+"' " +
										"WHERE bplp.XX_BudgetPlanningLinePeriod_ID = ? "; // 1
				
				no = DB.executeUpdate(get_Trx(), update, getXX_BudgetPlanningLinePeriod_ID());
				if (no != 1)
					log.warning("(1) #" + no);
			}
			
			rs1.close();
			ps1.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		// Update Planning Line Period Amount (TotalDr, TotalCr)
		update = "UPDATE XX_BudgetPlanningLinePeriod bplp " +
				"SET bplp.TotalDr = (bplp.AmtAcctDr + bplp.AmtRevDr), " +
				"bplp.TotalCr = (bplp.AmtAcctCr + bplp.AmtRevCr) " +
				"WHERE bplp.XX_BudgetPlanningLinePeriod_ID = ? "; // 1
				
		no = DB.executeUpdate(get_Trx(), update, getXX_BudgetPlanningLinePeriod_ID());
		if (no != 1)
			log.warning("(2) #" + no);
		
		// Update Planning Line Amount (AmtAcctDr, AmtAcctCr)
		StringBuilder updateLine = new StringBuilder("UPDATE XX_BudgetPlanningLine bpl " +
				"SET (bpl.AmtAcctDr, bpl.AmtAcctCr, bpl.Qty) = " +
				"(SELECT SUM(bplp.TotalDr), SUM(bplp.TotalCr), SUM(bplp.Qty) " +
				"FROM XX_BudgetPlanningLinePeriod bplp " +
				"WHERE bplp.XX_BudgetPlanningLine_ID = bpl.XX_BudgetPlanningLine_ID) ");
		int para2 = 0;
		if(getXX_InvestmentBudgetLine_ID()!=0)
		{
			MInvestmentBudgetLine bi = new MInvestmentBudgetLine(getCtx(), getXX_InvestmentBudgetLine_ID(), get_Trx());
			MAssetGroup ag = new MAssetGroup(getCtx(), bi.getA_Asset_Group_ID(), get_Trx());
//			if(ag.getM_Product_ID()!=0)
//			{
//				para2 = ag.getM_Product_ID();
//				updateLine.append(", M_Product_ID = ? ");
//			}
		}
				
		updateLine.append("WHERE bpl.XX_BudgetPlanningLine_ID = ? "); // 1
		
		MBudgetPlanningLinePeriod bpl = new MBudgetPlanningLinePeriod(getCtx(), getXX_BudgetPlanningLinePeriod_ID(), get_Trx());
		if(para2==0)
			no = DB.executeUpdate(get_Trx(), updateLine.toString(), bpl.getXX_BudgetPlanningLine_ID());
		else 
			no = DB.executeUpdate(get_Trx(), updateLine.toString(), para2, bpl.getXX_BudgetPlanningLine_ID());

		if (no != 1)
			log.warning("(3) #" + no);
		
	}

}
