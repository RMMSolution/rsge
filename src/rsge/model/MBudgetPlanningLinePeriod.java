/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.ResultSet;

import org.compiere.api.UICallout;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.po.X_XX_BudgetPlanningLinePeriod;

/**
 * @author FANNY
 *
 */
public class MBudgetPlanningLinePeriod extends X_XX_BudgetPlanningLinePeriod {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Logger for class MBudgetPlanningLinePeriod */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MBudgetPlanningLinePeriod.class);

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param XX_BudgetPlanningLinePeriod_ID
	 * @param trx
	 */
	public MBudgetPlanningLinePeriod(Ctx ctx,
			int XX_BudgetPlanningLinePeriod_ID, Trx trx) {
		super(ctx, XX_BudgetPlanningLinePeriod_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MBudgetPlanningLinePeriod(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.compiere.framework.PO#afterSave(boolean, boolean)
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// Update Planning Line Amount (AmtAcctDr, AmtAcctCr)
		String update = "UPDATE XX_BudgetPlanningLine bpl " +
				"SET (bpl.AmtAcctDr, bpl.AmtAcctCr, bpl.Qty) = " +
				"(SELECT SUM(bplp.TotalDr), SUM(bplp.TotalCr), SUM(bplp.Qty) " +
				"FROM XX_BudgetPlanningLinePeriod bplp " +
				"WHERE bplp.XX_BudgetPlanningLine_ID = bpl.XX_BudgetPlanningLine_ID) " +
				"WHERE bpl.XX_BudgetPlanningLine_ID = ? "; // 1
		int no = DB.executeUpdate(get_Trx(), update, getXX_BudgetPlanningLine_ID());
		
		update = "UPDATE XX_BudgetPlanning bp " +
				"SET bp.TotalDr = " +
				"(SELECT SUM(bpl.AmtAcctDr) " +
				"FROM XX_BudgetPlanningLine bpl " +
				"WHERE bpl.XX_BudgetPlanning_ID = bp.XX_BudgetPlanning_ID) " +
				", bp.TotalCr = " + 
				"(SELECT SUM(bpl.AmtAcctCr) " +
				"FROM XX_BudgetPlanningLine bpl " +
				"WHERE bpl.XX_BudgetPlanning_ID = bp.XX_BudgetPlanning_ID) " +
				"WHERE bp.XX_BudgetPlanning_ID = ? "; 
		MBudgetPlanningLine bpl = new MBudgetPlanningLine(getCtx(), getXX_BudgetPlanningLine_ID(), get_Trx());
		no = DB.executeUpdate(get_Trx(), update, bpl.getXX_BudgetPlanning_ID());
		if (no != 1)
			log.warning("(1) #" + no);
		
		return true;
	}
	
	/**
	 * 	Set AmtRevDr - Callout
	 *  
	 *	@param oldAmtRevDr old AmtRevDr
	 *	@param newAmtRevDr new AmtRevDr
	 *	@param windowNo window no
	 */
	@UICallout public void setRevisedQty (String oldRevisedQty,
			String newRevisedQty, int windowNo) throws Exception
	{		
		BigDecimal revQty = getRevisedQty();
		if(revQty == Env.ZERO || revQty == null)
			return;			
		setQty(revQty);			
	}


	/**
	 * 	Set AmtRevDr - Callout
	 *  
	 *	@param oldAmtRevDr old AmtRevDr
	 *	@param newAmtRevDr new AmtRevDr
	 *	@param windowNo window no
	 */
	@UICallout public void setAmtRevDr (String oldAmtRevDr,
			String newAmtRevDr, int windowNo) throws Exception
	{		
		BigDecimal AmtRevDr = getAmtRevDr();
		if(AmtRevDr == Env.ZERO || AmtRevDr == null)
			return;			
		setTotalDr(getAmtAcctDr().add(AmtRevDr));			
	}
	
	/**
	 * 	Set AmtRevCr - Callout
	 *  
	 *	@param oldAmtRevCr old AmtRevCr
	 *	@param newAmtRevCr new AmtRevCr
	 *	@param windowNo window no
	 */
	@UICallout public void setAmtRevCr (String oldAmtRevCr,
			String newAmtRevCr, int windowNo) throws Exception
	{		
		BigDecimal AmtRevCr = getAmtRevCr();
		if(AmtRevCr == Env.ZERO || AmtRevCr == null)
			return;			
		setTotalDr(getAmtAcctCr().add(AmtRevCr));			
	}

}
