/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_ReservedBudgetAcct;

/**
 * @author FANNY R
 *
 */
public class MReservedBudgetAcct extends X_XX_ReservedBudgetAcct {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_ReservedBudgetAcct_ID
	 * @param trx
	 */
	public MReservedBudgetAcct(Ctx ctx, int XX_ReservedBudgetAcct_ID, Trx trx) {
		super(ctx, XX_ReservedBudgetAcct_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MReservedBudgetAcct(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean afterDelete(boolean success) {
		// TODO Auto-generated method stub
		updateHeader();
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		updateHeader();
		return true;
	}
	
	/**
	 * Update XX_ReservedBudget Total Amount
	 */
	private void updateHeader()
	{
		String sql = "UPDATE XX_ReservedBudget rb " +
				"SET rb.TotalAmt = (SELECT SUM(rba.ReservedBudgetAmt) " +
				"FROM XX_ReservedBudgetAcct rba " +
				"WHERE rba.XX_ReservedBudget_ID = rb.XX_ReservedBudget_ID) " +
				"WHERE rb.XX_ReservedBudget_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_ReservedBudget_ID());
			
			rs = pstmt.executeQuery();
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
