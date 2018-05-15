/**
 * 
 */
package rsge.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.QueryUtil;

import rsge.model.MBudgetForm;
import rsge.model.MBudgetFormLine;
import rsge.po.X_I_BudgetForm;
import rsge.po.X_XX_BudgetForm;

/**
 * @author FANNY R
 *
 */
public class ImportOperationalBudget extends SvrProcess {
	
	/** Parameter							*/
	/** AD_Client_ID						*/
	private int						p_AD_Client_ID = 0;
	/** Date Processed						*/
	private Timestamp				p_DateProcessed = null;
	/** Budget Plan							*/
	private int						p_BudgetPlan_ID = 0;
	/** User ID								*/
	private int						p_User_ID = 0;
	/**	Delete old Imported					*/
	private boolean					p_DeleteOldImported = false;
	
	private static final String STD_CLIENT_CHECK = " AND AD_Client_ID=? " ;	

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) 
		{
			String name = element.getParameterName();
			if (element.getParameter() == null)
				;
			else if (name.equals("AD_Client_ID"))
				p_AD_Client_ID = ((BigDecimal)element.getParameter()).intValue();
			else if (name.equals("DateProcessed"))
				p_DateProcessed = (Timestamp) element.getParameter();
			else if (name.equals("XX_BudgetPlanning_ID"))
				p_BudgetPlan_ID = element.getParameterAsInt();
			else if (name.equals("AD_User_ID"))
				p_User_ID = element.getParameterAsInt();
			else if (name.equals("DeleteOldImported"))
				p_DeleteOldImported = "Y".equals(element.getParameter());
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

	}
	
	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		StringBuilder sql = new StringBuilder();
		int no = 0;
		
//		****	Prepare	****

		//	Delete Old Imported
		if (p_DeleteOldImported)
		{
			sql.append("DELETE FROM I_BudgetForm "
				  + "WHERE I_IsImported='Y' ");
			sql.append(STD_CLIENT_CHECK);
			no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
			log.fine("Delete Old Impored =" + no);
		}
		
		//	Set IsActive, Created/Updated
		sql = new StringBuilder("UPDATE I_BudgetForm "
			+ "SET IsActive = COALESCE (IsActive, 'Y'),"
			+ " Created = COALESCE (Created, SysDate),"
			+ " CreatedBy = COALESCE (CreatedBy, 0),"
			+ " Updated = COALESCE (Updated, SysDate),"
			+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
			+ " I_ErrorMsg = NULL,"
			+ " I_IsImported = 'N' "
			+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL ");
		no = DB.executeUpdate(get_Trx(),sql.toString());
		log.info ("Reset=" + no);
		
		//	Error Doc Org
		String ts = DB.isPostgreSQL()?"COALESCE(I_ErrorMsg,'')":"I_ErrorMsg";  //java bug, it could not be used directly
		
		//	Set Organization
		sql = new StringBuilder("UPDATE I_BudgetForm bf "
			+ "SET bf.AD_Org_ID=(SELECT o.AD_Org_ID FROM AD_Org o "
			+ "WHERE bf.OrgValue = o.Value) "
			+ "WHERE AD_Org_ID IS NULL AND OrgValue IS NOT NULL "
			+ "AND I_IsImported<>'Y' ");
		sql.append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
		log.fine("Set AD_Org_ID from Organization Value = " + no);
		// Error Organization
		sql = new StringBuilder("UPDATE I_BudgetForm bf "
			+ "SET I_IsImported='E', I_ErrorMsg="+ts +"||'ERR=Invalid Organization, '"
			+ "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0)"
			+ " AND I_IsImported<>'Y' ");
		sql.append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
		if (no != 0)
			log.warning ("Invalid Organization=" + no);
		
		//	Set Currency
		sql = new StringBuilder("UPDATE I_BudgetForm bf "
			+ "SET bf.C_Currency_ID=(SELECT c.C_Currency_ID FROM C_Currency c "
			+ "WHERE bf.ISO_Code = c.ISO_Code) "
			+ "WHERE C_Currency_ID IS NULL AND ISO_Code IS NOT NULL "
			+ "AND I_IsImported<>'Y' ");
		sql.append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
		log.fine("Set C_Currency_ID from ISO Code = " + no);
		
		sql = new StringBuilder("UPDATE I_BudgetForm bf " 
			+ "SET C_Currency_ID=(SELECT a.C_Currency_ID FROM C_AcctSchema a " +
					"INNER JOIN XX_BudgetInfo bi ON (a.C_AcctSchema_ID = bi.C_AcctSchema_ID) " +
					"WHERE bf.AD_Client_ID=bi.AD_Client_ID) "
			+ "WHERE C_Currency_ID IS NULL AND ISO_Code IS NULL "
			+ "AND I_IsImported<>'Y' ");
		sql.append(STD_CLIENT_CHECK);		
		no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
		log.fine("Set C_Currency_ID from default currency");
		
		// Error Currency
		sql = new StringBuilder("UPDATE I_BudgetForm bf "
			+ "SET I_IsImported='E', I_ErrorMsg="+ts +"||'ERR=Invalid Currency, '"
			+ "WHERE (C_Currency_ID IS NULL OR C_Currency_ID=0)"
			+ " AND I_IsImported<>'Y' ");
		sql.append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
		if (no != 0)
			log.warning ("Invalid Currency=" + no);
		
		//	Set Activity
		sql = new StringBuilder("UPDATE I_BudgetForm bf "
			+ "SET bf.C_Activity_ID=(SELECT a.C_Activity_ID FROM C_Activity a "
			+ "WHERE bf.ActivityValue = a.Value) "
			+ "WHERE C_Activity_ID IS NULL AND ActivityValue IS NOT NULL "
			+ "AND I_IsImported<>'Y' ");
		sql.append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
		log.fine("Set C_Activity_ID from Activity Value = " + no);
		
		//	Set Campaign
		sql = new StringBuilder("UPDATE I_BudgetForm bf "
			+ "SET bf.C_Campaign_ID=(SELECT c.C_Campaign_ID FROM C_Campaign c "
			+ "WHERE bf.CampaignValue = c.Value) "
			+ "WHERE C_Campaign_ID IS NULL AND CampaignValue IS NOT NULL "
			+ "AND I_IsImported<>'Y' ");
		sql.append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
		log.fine("Set C_Campaign_ID from Campaign Value = " + no);
		
		//	Set Project
		sql = new StringBuilder("UPDATE I_BudgetForm bf "
			+ "SET bf.C_Project_ID=(SELECT p.C_Project_ID FROM C_Project p "
			+ "WHERE bf.ProjectValue = p.Value) "
			+ "WHERE C_Project_ID IS NULL AND ProjectValue IS NOT NULL "
			+ "AND I_IsImported<>'Y' ");
		sql.append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
		log.fine("Set C_Project_ID from Project Value = " + no);

		//	Set Account if Product is not Y
		sql = new StringBuilder("UPDATE I_BudgetForm bf "
			+ "SET bf.Account_ID=(SELECT ev.C_ElementValue_ID FROM C_ElementValue ev "
			+ "WHERE bf.AccountValue = ev.Value) "
			+ "WHERE Account_ID IS NULL AND AccountValue IS NOT NULL "
			+ "AND IsProduct<>'Y' "
			+ "AND I_IsImported<>'Y' ");
		sql.append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
		log.fine("Set Account_ID from Account Value = " + no);		
		// Error Account
		sql = new StringBuilder("UPDATE I_BudgetForm bf "
			+ "SET I_IsImported='E', I_ErrorMsg="+ts +"||'ERR=Invalid Account, '"
			+ "WHERE (Account_ID IS NULL OR Account_ID=0) "
			+ "AND I_IsImported<>'Y' ");
		sql.append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
		if (no != 0)
			log.warning ("Invalid Account=" + no);
		
		//	Set Product if Product is Y
		sql = new StringBuilder("UPDATE I_BudgetForm bf "
			+ "SET bf.M_Product_ID=(SELECT pc.M_Product_ID FROM M_Product pc "
			+ "WHERE bf.Product_Value = pc.Value) "
			+ "WHERE M_Product_ID IS NULL AND Product_Value IS NOT NULL "
			+ "AND IsProduct='Y' "
			+ "AND I_IsImported<>'Y' ");
		sql.append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
		log.fine("Set M_Product_ID from Product Value = " + no);		
		// Error Product
		sql = new StringBuilder("UPDATE I_BudgetForm bf "
			+ "SET I_IsImported='E', I_ErrorMsg="+ts +"||'ERR=Invalid Product, '"
			+ "WHERE (M_Product_ID IS NULL OR M_Product_ID=0)"
			+ " AND I_IsImported<>'Y' ");
		sql.append(STD_CLIENT_CHECK);
		no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
		if (no != 0)
			log.warning ("Invalid Product =" + no);
		
		/***********************************************************************************/
		
		// Update Total Amount		
		// Update Period Price * QtyPeriod (for Product only)
		sql = new StringBuilder("UPDATE I_BudgetForm bf "
				+ "SET Period1=(Price*QtyPeriod1), Period2=(Price*QtyPeriod2), Period3=(Price*QtyPeriod3), Period4=(Price*QtyPeriod4), " +
						"Period5=(Price*QtyPeriod5), Period6=(Price*QtyPeriod6), Period7=(Price*QtyPeriod7), Period8=(Price*QtyPeriod8), " +
						"Period9=(Price*QtyPeriod9), Period10=(Price*QtyPeriod10), Period11=(Price*QtyPeriod11), Period12=(Price*QtyPeriod12) "
						+"WHERE IsProduct='Y' "
						+"AND I_IsImported<>'Y' " );
			sql.append(STD_CLIENT_CHECK);
			no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
			log.fine("Set TotalAmt = " + no);

		sql = new StringBuilder("UPDATE I_BudgetForm bf "
				+ "SET bf.TotalAmt=(Period1 + Period2 + Period3 + Period4 " +
						"+ Period5 + Period6 + Period7 + Period8" +
						"+ Period9 + Period10 + Period11 + Period12) "
				+ "WHERE I_IsImported<>'Y' " );
			sql.append(STD_CLIENT_CHECK);
			no = DB.executeUpdate(get_Trx(),sql.toString(),p_AD_Client_ID);
			log.fine("Set TotalAmt = " + no);
		
			//	Count Errors
			int errors = QueryUtil.getSQLValue(get_Trx(), 
				"SELECT COUNT(*) FROM I_BudgetForm WHERE I_IsImported NOT IN ('Y','N') " + STD_CLIENT_CHECK,p_AD_Client_ID);

			commit();	
		
		/*******************************************************************************/
		
		int noProcessed = 0;
		int noLines = 0;
		
		// Create new Budget Form
		sql = new StringBuilder("SELECT * " +
				"FROM I_BudgetForm " +
				"WHERE I_IsImported<>'Y' AND AD_Client_ID=? " +
				"AND TotalAmt > 0 " +
				"ORDER BY I_BudgetForm_ID ");
			
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
		
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			
			int oldCurrency_ID = 0;
			boolean oldIsProduct = false;
			boolean oldIsSoTrx = false;
			String oldTrxType = null;
			int oldActivity_ID = 0;
			int oldCampaign_ID = 0;
			int oldProject_ID = 0;
			
			int lineNo = 0;			
			
			int XX_BudgetForm_ID = 0;
			int XX_BudgetFormLine_ID = 0;
			
			MBudgetForm form = null;
			MBudgetFormLine line = null;
			
			String planningPeriod = null;
			
			while(rs.next())
			{				
				X_I_BudgetForm imp = new X_I_BudgetForm(getCtx(), rs, get_Trx());
				
				// Create new Budget Form
				if(form==null
					|| oldCurrency_ID != imp.getC_Currency_ID() || oldIsProduct != imp.isProduct() || oldIsSoTrx != imp.isSOTrx()
					|| oldTrxType != imp.getTransactionType() || oldActivity_ID != imp.getC_Activity_ID() || oldCampaign_ID != imp.getC_Campaign_ID()
					|| oldProject_ID != imp.getC_Project_ID())
				{
					oldCurrency_ID = imp.getC_Currency_ID();
					oldIsProduct = imp.isProduct();
					oldIsSoTrx = imp.isSOTrx();
					oldTrxType = imp.getTransactionType();
					oldActivity_ID = imp.getC_Activity_ID();
					oldCampaign_ID = imp.getC_Campaign_ID();
					oldProject_ID = imp.getC_Project_ID();		
					
					lineNo = 0;
					noProcessed ++;
					
					form = new MBudgetForm(imp);
					form.setDateDoc(p_DateProcessed);
					form.setAD_User_ID(p_User_ID);
					form.setXX_BudgetPlanning_ID(p_BudgetPlan_ID);
					
					// Populate GL Budget and Planning Period
					String a = "SELECT GL_Budget_ID, PlanningPeriod " +
							"FROM XX_BudgetPlanning " +
							"WHERE XX_BudgetPlanning_ID = ? ";
					
					PreparedStatement pstmt1 = DB.prepareStatement(a, get_Trx());
					ResultSet rs1 = null;
					
					try{
						pstmt1.setInt(1, p_BudgetPlan_ID);
						rs1 = pstmt1.executeQuery();
						
						if(rs1.next())
						{
							form.setGL_Budget_ID(rs1.getInt(1));
							planningPeriod = (rs1.getString(2));
							form.setPlanningPeriod(planningPeriod);
						}
						rs1.close();
						pstmt1.close();
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					if(form.save())
					{
						XX_BudgetForm_ID = form.getXX_BudgetForm_ID();
					}
				} // End Create new Budget Form
				
				// Create Budget Form Line
				line = new MBudgetFormLine(imp); 
				line.setXX_BudgetForm_ID(XX_BudgetForm_ID);
				
				lineNo = lineNo + 10;				
				line.setLine(lineNo);
				
				line.setPeriod1(imp.getPeriod1());
				line.setPeriod2(imp.getPeriod2());
				line.setPeriod3(imp.getPeriod3());
				line.setPeriod4(imp.getPeriod4());
				line.setPeriod5(imp.getPeriod5());
				line.setPeriod6(imp.getPeriod6());
				line.setPeriod7(imp.getPeriod7());
				line.setPeriod8(imp.getPeriod8());
				line.setPeriod9(imp.getPeriod9());
				line.setPeriod10(imp.getPeriod10());
				line.setPeriod11(imp.getPeriod11());
				line.setPeriod12(imp.getPeriod12());		
				
				//Set Total Amt
				BigDecimal totalAmt = BigDecimal.ZERO;
				
				totalAmt = imp.getPeriod1().add(imp.getPeriod2());
				
				if(planningPeriod.equals(X_XX_BudgetForm.PLANNINGPERIOD_Quarterly))
				{
					totalAmt = totalAmt.add(imp.getPeriod3().add(imp.getPeriod4()));
				}
				else if(planningPeriod.equals(X_XX_BudgetForm.PLANNINGPERIOD_Monthly))
				{
					totalAmt = totalAmt.add(imp.getPeriod3()).add(imp.getPeriod4()).add(imp.getPeriod5()).add(imp.getPeriod6())
					.add(imp.getPeriod7()).add(imp.getPeriod8()).add(imp.getPeriod9()).add(imp.getPeriod10()).add(imp.getPeriod11()).add(imp.getPeriod12());
				}				
				
				line.setTotalAmt(totalAmt);
				noLines ++;
				
				if(line.save())
				{
					XX_BudgetFormLine_ID = line.getXX_BudgetFormLine_ID();
				}
				
				imp.setXX_BudgetForm_ID(XX_BudgetForm_ID);
				imp.setXX_BudgetFormLine_ID(XX_BudgetFormLine_ID);
				imp.setI_IsImported ("Y");
				imp.setI_ErrorMsg (null);
				imp.setProcessed(true);
				imp.setProcessing(true);
				imp.save();				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeStatement(pstmt);
			DB.closeResultSet(rs);
		}
		return processMsg(noProcessed, noLines);
	}	
	
	/**
	 * 	Process Fail
	 *	@param imp import
	 *	@param errorMsg error message
	 *	@return false
	 */
	private boolean processFail (X_I_BudgetForm imp, String errorMsg)
	{
		imp.setI_IsImported("N");
		imp.setI_ErrorMsg(errorMsg);
		imp.save();
		
		return false;
	}	//	processFail
	
	private String processMsg(int noProcessed, int noLines)
	{
		StringBuilder message = new StringBuilder();
		message.append(Msg.getMsg(getCtx(), "CreatedDocument")).append(noProcessed).append("\n ");
		message.append(Msg.getMsg(getCtx(), "CreatedLines")).append(noLines);
		
		return message.toString();
	}

}
