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

import rsge.po.X_XX_CopyBudgetLine;

/**
 * @author bang
 *
 */
public class MCopyBudgetLine extends X_XX_CopyBudgetLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_CopyBudgetLine_ID
	 * @param trx
	 */
	public MCopyBudgetLine(Ctx ctx, int XX_CopyBudgetLine_ID, Trx trx) {
		super(ctx, XX_CopyBudgetLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MCopyBudgetLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		BigDecimal amt = BigDecimal.ZERO;
		String sql = "SELECT COALESCE(SUM(AmtAcctDr-AmtAcctCR),0) FROM XX_CopyBudgetLine " +
				"WHERE Account_ID NOT IN (SELECT C_ElementValue_ID FROM C_ElementValue WHERE C_ElementValue_ID IN " +
				"(SELECT Account_ID FROM C_ValidCombination WHERE C_ValidCombination_ID IN " +
				"(SELECT BudgetClearing_Acct FROM XX_BudgetInfo WHERE AD_Client_ID = "+getAD_Client_ID()+"))) AND XX_CopyBudget_ID = "+getXX_CopyBudget_ID();
		PreparedStatement ps = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if(rs.next())
				amt = rs.getBigDecimal(1);
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		DB.executeUpdate(get_Trx(), "UPDATE XX_CopyBudget SET TotalAmt = "+amt+" WHERE XX_CopyBudget_ID = "+getXX_CopyBudget_ID());
		return true;
	}

}
