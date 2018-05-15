/**
 * 
 */
package rsge.process;

import org.compiere.process.SvrProcess;

import rsge.model.MBudgetPlanning;
import rsge.po.X_XX_BudgetPlanning;

/**
 * @author FANNY R
 *
 */
public class ApproveBudgetPlan extends SvrProcess {

	/** Initialize Variable					*/
	private int 					XX_BudgetPlanning_ID = 0;

	
	/**
	 * 
	 */
	public ApproveBudgetPlan() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// Change Budget Plan Status to Available
		MBudgetPlanning bp = new MBudgetPlanning(getCtx(), XX_BudgetPlanning_ID, get_Trx());
		bp.setBudgetPlanStatus(X_XX_BudgetPlanning.BUDGETPLANSTATUS_Available);
		bp.save();
		
		return "";
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Get initial data
		XX_BudgetPlanning_ID = getRecord_ID();
	}

}
