/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MElementValue;
import org.compiere.model.X_C_ElementValue;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_BudgetRevisionTargetAcct;

/**
 * @author FANNY R
 *
 */
public class MBudgetRevisionTargetAcct extends X_XX_BudgetRevisionTargetAcct {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_BudgetRevisionTargetAcct_ID
	 * @param trx
	 */
	public MBudgetRevisionTargetAcct(Ctx ctx,
			int XX_BudgetRevisionTargetAcct_ID, Trx trx) {
		super(ctx, XX_BudgetRevisionTargetAcct_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MBudgetRevisionTargetAcct(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		updateHeader();
		// Re-check Header Lock
		MBudgetRevision.checkHeaderLock(getCtx(), getXX_BudgetRevision_ID(), get_Trx());
		return true;
	}

	int XX_BudgetRevision_ID = 0;

	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// Set Budget Account Type
		if(getBudgetType(getAccount_ID()) == 1)
		{
			setIsInvestmentAccount(true);
		}
		else setIsInvestmentAccount(false);
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean beforeDelete() {		
		XX_BudgetRevision_ID = getXX_BudgetRevisionAccount_ID();		
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		updateHeader();
		// Re-check Header Lock
		MBudgetRevision.checkHeaderLock(getCtx(), getXX_BudgetRevision_ID(), get_Trx());
		return true;
	}
	
	/**
	 * Update Total Amount in Budget Revision
	 */
	private void updateHeader()
	{		
		StringBuilder sql = new StringBuilder("Update XX_BudgetRevision br " +
				"SET br.AllocatedAmt = (COALESCE((SELECT SUM(target.AppliedAmt) FROM XX_BudgetRevisionTargetAcct target " +
				"WHERE target.XX_BudgetRevision_ID = br.XX_BudgetRevision_ID),0)) " +
				"WHERE br.XX_BudgetRevision_ID = ? ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_BudgetRevision_ID());
			rs = pstmt.executeQuery();
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			sql = new StringBuilder("Update XX_BudgetRevision br " +
					"SET br.AllocatedAmt = 0 " +
					"WHERE br.XX_BudgetRevision_ID = ? ");			
			
			DB.executeUpdate(get_Trx(), sql.toString(), XX_BudgetRevision_ID);
		}
	}
	
	/**
	 * Return Budget Type based on account 
	 * 1 for Investment
	 * 2 for Expense
	 * 0 for Error
	 * @param Account_ID
	 * @return
	 */
	private int getBudgetType(int Account_ID)
	{
		int budgetType = 0;
		
		MElementValue ev = new MElementValue(getCtx(), Account_ID, get_Trx());
		if(ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Asset)
				|| ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Liability)
				|| ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_OwnerSEquity))
			budgetType = 1;
		else if(ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Expense)
				|| ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Revenue)
				|| ev.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Memo))
			budgetType = 2;		
		return budgetType;
	}

}
