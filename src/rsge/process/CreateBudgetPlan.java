/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.compiere.model.MOrg;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import rsge.model.MBudgetPlanning;
import rsge.model.MCorpBudgetPlan;
import rsge.po.X_XX_BudgetPlanning;

/**
 * @author Fanny
 *
 */
public class CreateBudgetPlan extends SvrProcess {

	private Timestamp		p_Date = null;
	private int				p_AD_Org_ID = 0;
	
	private MOrg[]			orgList = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null)
				;
			else if(name.equals("DateDoc")){
				p_Date = (Timestamp)element.getParameter();
			}
			else if(name.equals("AD_Org_ID")){
				p_AD_Org_ID=element.getParameterAsInt();
			}					
		}
		
		if(p_AD_Org_ID!=0)
		{
			orgList = new MOrg[1];
			orgList[0] = new MOrg(getCtx(), p_AD_Org_ID, get_Trx());
		}
		else
		{
			ArrayList<MOrg> list = new ArrayList<MOrg>();
			String sql = "SELECT o.* FROM AD_Org o " +
					"WHERE o.AD_Org_ID NOT IN " +
					"(SELECT AD_Org_ID FROM XX_BudgetPlanning WHERE XX_CorpBudgetPlan_ID = ?) " +
					"AND o.AD_Client_ID = ? " +
					"AND o.IsSummary ='N' ";
			
			PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
			ResultSet rs = null;
			
			try{
				pstmt.setInt(1, getRecord_ID());
				pstmt.setInt(2, getAD_Client_ID());
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					MOrg org = new MOrg(getCtx(), rs, get_Trx());
					list.add(org);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DB.closeResultSet(rs);
				DB.closeStatement(pstmt);
			}
			
			orgList = new MOrg[list.size()];
			list.toArray(orgList);
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// Check date
		MCorpBudgetPlan cbp = new MCorpBudgetPlan(getCtx(), getRecord_ID(), get_Trx());
		MPeriod checkPeriod = new MPeriod(getCtx(), cbp.getC_From_Period_ID(), get_Trx());
		if(p_Date.after(checkPeriod.getStartDate()))
		{
		 	return Msg.getMsg(getCtx(), "InvalidBudgetPlanPeriod");			
		}			

		int record = 0;
		for(MOrg org : orgList)
		{
			MBudgetPlanning bp = new MBudgetPlanning(getCtx(), 0, get_Trx());
			
			StringBuilder desc = new StringBuilder(cbp.getName());
			desc.append(" - ");
			desc.append(org.getName());
			
			bp.setAD_Org_ID(org.getAD_Org_ID());
			bp.setDateDoc(p_Date);
			bp.setXX_CorpBudgetPlan_ID(getRecord_ID());
			bp.setBudgetPlanStatus(X_XX_BudgetPlanning.BUDGETPLANSTATUS_Available);
			bp.setPlanningPeriod(cbp.getPlanningPeriod());
			bp.setGL_Budget_ID(cbp.getGL_Budget_ID());
			bp.setC_Period_From_ID(cbp.getC_From_Period_ID());
			bp.setName(desc.toString());
			bp.setDocStatus(X_XX_BudgetPlanning.DOCSTATUS_InProgress);
			bp.setDocAction(X_XX_BudgetPlanning.DOCACTION_Complete);
			if(bp.save())
				record = record + 1;
		}
		return "Record(s) created : " + record;
	}

}
