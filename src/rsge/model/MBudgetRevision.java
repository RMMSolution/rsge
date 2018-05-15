/**
 * 
 */
package rsge.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.compiere.api.UICallout;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MPeriod;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.util.Env.QueryParams;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_XX_BudgetRevision;
import rsge.utils.BudgetUtils;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY R
 *
 */
public class MBudgetRevision extends X_XX_BudgetRevision implements DocAction{

    /** Logger for class MBudgetRevision */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MBudgetRevision.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_BudgetRevision_ID
	 * @param trx
	 */
	public MBudgetRevision(Ctx ctx, int XX_BudgetRevision_ID, Trx trx) {
		super(ctx, XX_BudgetRevision_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MBudgetRevision(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}	
	
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// Check whether Current Period is fill in or not.
		if(getC_Current_Period_ID() == 0 || Integer.toString(getC_Current_Period_ID()).length()==0)
		{
			log.saveError("Error", Msg.getMsg(getCtx(), "Current Period Empty"));			
			return false;
		}
		
		// Check Period
		if(isPeriod() && !checkPeriod())
		{
			log.saveError("Error", Msg.getMsg(getCtx(), "PeriodError"));
			return false;
		}
		
		// If Product is entered, is Sales Transaction and not a summary, make sure Revision type is not Transfer
		if(getM_Product_ID()>0 && isNotSummary() && getBudgetRevisionType().equals(BUDGETREVISIONTYPE_Transfer))
		{
			log.saveError("Error", Msg.getMsg(getCtx(), "Budget product cannot be transfer"));
			return false;
		}			
		
		// Set Percentage to percentage allowed if Percentage is bigger than percentage allowed by Source Reserve - for Budget Transfer only
		if(getBudgetRevisionType().equals(BUDGETREVISIONTYPE_Transfer))
		{
			MBudgetInfo bi = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), get_Trx());
			BigDecimal percentageAllowed = BigDecimal.valueOf(100).subtract(bi.getSourceReserve());
			if(getPercentage().compareTo(percentageAllowed)>1)
			{
				setPercentage(percentageAllowed);
				log.saveWarning("Error", Msg.getMsg(getCtx(), "Exceed allowed by Source Reserve"));
			}
		}
		
		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// Update Account Lines
		for(MBudgetRevisionAccount bra : getAccountLines())
		{
			bra.setC_Activity_ID(getC_Activity_ID());
			bra.setC_BPartner_ID(getC_BPartner_ID());
			bra.setC_Campaign_ID(getC_Campaign_ID());
			bra.setC_Project_ID(getC_Project_ID());
			bra.setM_Product_ID(getM_Product_ID());
			bra.setC_SalesRegion_ID(getC_SalesRegion_ID());	
			bra.save();
		}
		// Update Target Account Lines
		for(MBudgetRevisionTargetAcct brta : getTargetAccountLines())
		{
			brta.setC_Activity_ID(getC_Activity_ID());
			brta.setC_BPartner_ID(getC_BPartner_ID());
			brta.setC_Campaign_ID(getC_Campaign_ID());
			brta.setC_Project_ID(getC_Project_ID());
			brta.setM_Product_ID(getM_Product_ID());
			brta.setC_SalesRegion_ID(getC_SalesRegion_ID());	
			brta.save();
		}
		
		if(getXX_BudgetFormTemplate_ID()>0){
			MBudgetFormTemplateLine[] lines = null;
			lines = getBudgetFormTemplateLine();
			if(lines.length>0){
				for(MBudgetFormTemplateLine line:lines){
					MBudgetRevisionAccount account = new MBudgetRevisionAccount(getCtx(), 0, get_Trx());
					account.setAD_Client_ID(line.getAD_Client_ID());
					account.setAD_Org_ID(line.getAD_Org_ID());
					account.setXX_BudgetRevision_ID(getXX_BudgetRevision_ID());
					account.setAccount_ID(line.getAccount_ID());
					account.save();
				}
			}
		}

		return true;
	}
	
	private MBudgetRevisionAccount[] getAccountLines()
	{
		ArrayList<MBudgetRevisionAccount> list = new ArrayList<MBudgetRevisionAccount>();
		String sql = "SELECT * FROM XX_BudgetRevisionAccount " +
				"WHERE XX_BudgetRevision_ID = " + getXX_BudgetRevision_ID();
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MBudgetRevisionAccount bra = new MBudgetRevisionAccount(getCtx(), rs, get_Trx());
				list.add(bra);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		MBudgetRevisionAccount[] accounts = new MBudgetRevisionAccount[list.size()];
		list.toArray(accounts);
		return accounts;
	}
	
	private MBudgetRevisionTargetAcct[] getTargetAccountLines()
	{
		ArrayList<MBudgetRevisionTargetAcct> list = new ArrayList<MBudgetRevisionTargetAcct>();
		String sql = "SELECT * FROM XX_BudgetRevisionTargetAcct " +
				"WHERE XX_BudgetRevision_ID = " + getXX_BudgetRevision_ID();
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MBudgetRevisionTargetAcct brta = new MBudgetRevisionTargetAcct(getCtx(), rs, get_Trx());
				list.add(brta);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		MBudgetRevisionTargetAcct[] accounts = new MBudgetRevisionTargetAcct[list.size()];
		list.toArray(accounts);
		return accounts;
	}
	
	private MBudgetFormTemplateLine[] getBudgetFormTemplateLine()
	{
		ArrayList<MBudgetFormTemplateLine> list = new ArrayList<MBudgetFormTemplateLine>();
		String sql = "SELECT * FROM XX_BudgetFormTemplateLine " +
				"WHERE XX_BudgetFormTemplate_ID = " + getXX_BudgetFormTemplate_ID();
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MBudgetFormTemplateLine brta = new MBudgetFormTemplateLine(getCtx(), rs, get_Trx());
				list.add(brta);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		MBudgetFormTemplateLine[] accounts = new MBudgetFormTemplateLine[list.size()];
		list.toArray(accounts);
		return accounts;
	}
	
	/** Doc Action Variable									*/
	/** Process Message										*/
	private String								m_processMsg = null;
	
	@Override
	public boolean unlockIt() {
		log.info(toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	} // invalidateIt

	@Override
	public String prepareIt() {
		
		String sourceBudgetType = null;
		String targetBudgetType = null;
		
		int accountList = 0; // Counter for account list
		StringBuilder sql = new StringBuilder("SELECT IsInvestmentAccount " +
				"FROM XX_BudgetRevisionAccount " +
				"WHERE XX_BudgetRevision_ID = ? " +
				"AND AppliedAmt " +
				"> 0 ");

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_BudgetRevision_ID());
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				accountList = accountList + 1;
				if(rs.getString(1).equalsIgnoreCase("Y")) // Investment Budget Account
				{
					if(sourceBudgetType == null || sourceBudgetType.equalsIgnoreCase("I"))
					{
						sourceBudgetType = "I";
					}
					else if (sourceBudgetType.equalsIgnoreCase("E"))
					{
						sourceBudgetType = "M"; 
					}
				}
				else // Expense Budget Account
				{
					if(sourceBudgetType == null || sourceBudgetType.equalsIgnoreCase("E"))
					{
						sourceBudgetType = "E";
					}
					else if (sourceBudgetType.equalsIgnoreCase("I"))
					{
						sourceBudgetType = "M"; 
					}
				}
			}			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		
		if(accountList == 0)
		{
			m_processMsg = Msg.getMsg(getCtx(), "NoLines");
			return DocActionConstants.STATUS_Invalid;
		}
		
		if(getBudgetRevisionType().equals(BUDGETREVISIONTYPE_Transfer) && !isPeriod())
		{
			// If Total Amount <> Allocated Amount for Budget Revision Transfer
			if(getTotalAmt().compareTo(getAllocatedAmt())!=0)
			{
				m_processMsg = Msg.getMsg(getCtx(), "TotalAmt <> AllocatedAmt");				
				return DocActionConstants.STATUS_Invalid;
			}

			int accountTarget = 0; // counter for account target
			sql = new StringBuilder("SELECT IsInvestmentAccount " +
					"FROM XX_BudgetRevisionTargetAcct " +
					"WHERE XX_BudgetRevision_ID = ? " +
					"AND AppliedAmt > 0 ");

			pstmt = DB.prepareStatement(sql.toString(), get_Trx());
			rs = null;
			
			try{
				pstmt.setInt(1, getXX_BudgetRevision_ID());
				rs = pstmt.executeQuery();
								
				while(rs.next())
				{
					accountTarget = accountTarget + 1;
					if(rs.getString(1).equalsIgnoreCase("Y")) // Investment Budget Account
					{
						if(targetBudgetType == null || targetBudgetType.equalsIgnoreCase("I"))
						{
							targetBudgetType = "I";
						}
						else if (targetBudgetType.equalsIgnoreCase("E"))
						{
							targetBudgetType = "M"; 
						}
					}
					else // Expense Budget Account
					{
						if(targetBudgetType == null || targetBudgetType.equalsIgnoreCase("E"))
						{
							targetBudgetType = "E";
						}
						else if (targetBudgetType.equalsIgnoreCase("I"))
						{
							targetBudgetType = "M"; 
						}
					}
				}			
				rs.close();
				pstmt.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
			
			if(accountTarget == 0)
			{
				m_processMsg = Msg.getMsg(getCtx(), "NoLines");
				return DocActionConstants.STATUS_Invalid;
			}
		}
		
		// Set Revision Type
		if(getBudgetRevisionType().equals(BUDGETREVISIONTYPE_Cut)
				|| getBudgetRevisionType().equals(BUDGETREVISIONTYPE_Increase)
				|| (getBudgetRevisionType().equals(BUDGETREVISIONTYPE_Transfer) && isPeriod()))
		{
			if(sourceBudgetType.equalsIgnoreCase("E")) // all lines expense account
			{
				setExpenseToExpense(true);
			}
			else if(sourceBudgetType.equalsIgnoreCase("I")) // all lines investment account
			{
				setInvestmentToInvestment(true);
			}
			else if(sourceBudgetType.equalsIgnoreCase("M")) // expense and investment account
			{
				setExpenseToExpense(true);
				setInvestmentToInvestment(true);
			}
		}
		else if(getBudgetRevisionType().equals(BUDGETREVISIONTYPE_Transfer) && !isPeriod())
		{
			if(sourceBudgetType.equalsIgnoreCase("E") && targetBudgetType.equalsIgnoreCase("E")) // all source and target lines expense account
			{
				setExpenseToExpense(true);
			}
			else if(sourceBudgetType.equalsIgnoreCase("E") && targetBudgetType.equalsIgnoreCase("I")) // all source lines Expense and target lines investment account
			{
				setExpenseToInvestment(true);
			}
			else if(sourceBudgetType.equalsIgnoreCase("E") && targetBudgetType.equalsIgnoreCase("M")) // all source lines Expense and target lines expense and investment account
			{
				setExpenseToInvestment(true);
				setExpenseToExpense(true);
			}
			else if(sourceBudgetType.equalsIgnoreCase("I") && targetBudgetType.equalsIgnoreCase("I")) // all source and target lines investment account
			{
				setInvestmentToInvestment(true);
			}
			else if(sourceBudgetType.equalsIgnoreCase("I") && targetBudgetType.equalsIgnoreCase("E")) // all source lines Investment and target lines expense account
			{
				setInvestmentToExpense(true);
			}
			else if(sourceBudgetType.equalsIgnoreCase("I") && targetBudgetType.equalsIgnoreCase("M")) // all source lines Investment and target lines expense and investment account
			{
				setInvestmentToInvestment(true);
				setInvestmentToExpense(true);
			}
			
			else if(sourceBudgetType.equalsIgnoreCase("M") && targetBudgetType.equalsIgnoreCase("I")) // all source multi and target lines investment account
			{
				setExpenseToInvestment(true);
				setInvestmentToInvestment(true);
			}
			else if(sourceBudgetType.equalsIgnoreCase("M") && targetBudgetType.equalsIgnoreCase("E")) // all source lines multi and target lines expense account
			{
				setExpenseToExpense(true);
				setInvestmentToExpense(true);
			}
			else if(sourceBudgetType.equalsIgnoreCase("M") && targetBudgetType.equalsIgnoreCase("M")) // all source lines multi and target lines expense and investment account
			{
				setExpenseToExpense(true);
				setExpenseToInvestment(true);
				setInvestmentToInvestment(true);
				setInvestmentToExpense(true);
			}
		}		
		
		return DocActionConstants.STATUS_InProgress;
	} // prepareIt

	@Override
	public boolean approveIt() {
		log.info(toString());
		setIsApproved(true);
		return true;
	}	//	approveIt


	/**
	 * 	Reject Approval
	 * 	@return true if success
	 */
	public boolean rejectIt()
	{
		log.info(toString());
		setIsApproved(false);
		return true;
	}	//	rejectIt

	@Override
	public String completeIt() {		
		return DocActionConstants.STATUS_Completed;
	}

	/**
	 * 	Void Document.
	 * 	Same as Close.
	 * 	@return true if success
	 */
	public boolean voidIt()
	{
		log.info(toString());
		setDocAction(DOCACTION_None);
		return false;
	}	//	voidIt

	/**
	 * 	Close Document.
	 * 	Cancel not delivered Quantities
	 * 	@return true if success
	 */
	public boolean closeIt()
	{
		log.info(toString());
		setDocAction(DOCACTION_None);
		return true;
	}	//	closeIt

	/**
	 * 	Reverse Correction
	 * 	@return true if success
	 */
	public boolean reverseCorrectIt()
	{
		log.info(toString());
		return false;
	}	//	reverseCorrectionIt

	/**
	 * 	Reverse Accrual - none
	 * 	@return true if success
	 */
	public boolean reverseAccrualIt()
	{
		log.info(toString());
		return false;
	}	//	reverseAccrualIt

	/**
	 * 	Re-activate
	 * 	@return true if success
	 */
	public boolean reActivateIt()
	{
		log.info(toString());
		setProcessed(false);
		return DocumentEngine.processIt(this, DocActionConstants.ACTION_Reverse_Correct);
	}	//	reActivateIt

	@Override
	public String getSummary() {
		StringBuffer sb = new StringBuffer();
		sb.append("Budget Revision #");
		sb.append(getDocumentNo());
		//	: Grand Total = 123.00 (#1)
		sb.append(": ").
		append(Msg.translate(getCtx(),"GrandTotal")).append("=").append(getTotalAmt());
		//	 - Description
		if ((getDescription() != null) && (getDescription().length() > 0))
			sb.append(" - ").append(getDescription());
		return sb.toString();
	} // getSummary

	@Override
	public String getDocumentInfo() {
		// Document info
		String DocumentInfo = "Budget Revision #" + getDocumentNo();		 
		return DocumentInfo;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return getAD_User_ID();
	}

	@Override
	public int getC_Currency_ID() {		
		MAcctSchema schema = new MAcctSchema(getCtx(), getBudgetAcctSchema(), get_Trx());		
		return schema.getC_Currency_ID();
	}

	@Override
	public BigDecimal getApprovalAmt() {		
		return getTotalAmt();
	}

	@Override
	public void setProcessMsg(String processMsg) {
		// TODO Auto-generated method stub
		m_processMsg = processMsg;	
	}

	@Override
	public Timestamp getDocumentDate() {
		// TODO Auto-generated method stub
		return getDateDoc();
	}

	@Override
	public String getDocBaseType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryParams getLineOrgsQueryInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Get Acct Schema of Budget
	 * @return C_AcctSchema_ID
	 */
	private int getBudgetAcctSchema()
	{
		return BudgetUtils.getBudgetAcctSchemaID(getCtx(), getAD_Client_ID(), get_Trx());
	}
	
	/**
	 * Set Current Period with period of Date Effective
	 * @param oldDateEffective
	 * @param newDateEffective
	 * @param windowNo
	 * @throws Exception
	 */
	@UICallout public void setDateEffective(String oldDateEffective,
			String newDateEffective, int windowNo) throws Exception
	{		
		if((newDateEffective == null) || (newDateEffective.length() == 0))
			return;
		
		Integer currentPeriod = null;
		StringBuilder sql = new StringBuilder("SELECT p.C_Period_ID " +
				"FROM C_Period p " +
				"INNER JOIN C_Year y On (p.C_Year_ID = y.C_Year_ID) " +
				"INNER JOIN XX_BudgetInfo bi ON (y.C_Calendar_ID = bi.BudgetCalendar_ID) " +
				"WHERE bi.AD_Client_ID = ? " +
				"AND ? BETWEEN p.StartDate AND p.EndDate ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getAD_Client_ID());
			pstmt.setTimestamp(2, getDateEffective());
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				currentPeriod = rs.getInt(1);
			}			
			rs.close();
			pstmt.close();			
		}catch (Exception e) {
			e.printStackTrace();
		}			
		setC_Current_Period_ID(currentPeriod);
	}
	
	@UICallout public void setM_Product_ID(String oldM_Product_ID, String newM_Product_ID, int windowNo) throws Exception
	{		
		if((newM_Product_ID == null) || (newM_Product_ID.length() == 0))
			return;
		
		// Set Is Product
		MProduct product = new MProduct(getCtx(), getM_Product_ID(), (Trx)null);
		if(!product.isSummary())
			setIsNotSummary(true);
		else
			setIsNotSummary(false);

		// Set Transaction Type
		setTransactionType(TRANSACTIONTYPE_SalesPurchase);
	}
	
	/**
	 * Check From Period. If future transfer is not allowed, then from period cannot be current period
	 * @return
	 */
	private boolean checkPeriod()
	{
		MPeriod periodFrom = new MPeriod(getCtx(), getC_Period_From_ID(), get_Trx());
		MPeriod periodTo = new MPeriod(getCtx(), getC_Period_To_ID(), get_Trx());
		
		// Test 1 - Period From or Period To must not be in the past 
		if(periodFrom.getEndDate().before(getDateEffective()) || periodTo.getEndDate().before(getDateEffective()))
		{
			return false;
		}
		// End Test 1

		// Test 2 - Period From must be different from Period To
		if(periodFrom.getC_Period_ID() == periodTo.getC_Period_ID())
		{
			return false;
		}
		// End Test 2
		
		// Test 3 - if Future Transfer is not allowed, From Period value cannot be the same as Current Period
		MBudgetInfo info = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), get_Trx());		
		boolean IsAllowFutureTransfer = info.isAllowFutureTransfer();		
		if(!IsAllowFutureTransfer && (getC_Current_Period_ID() == getC_Period_From_ID()) )
		{
			return false;		
		}
		// End Test 3
		
		// Test 4 - if Future Period Allowed is bigger than 0, Period From sequence cannot exceed sequence of current period + future period
		int futurePeriodAllowed = info.getFuturePeriodTransfer();
		
		if(futurePeriodAllowed > 0)
		{
			int maxPeriodSeq = GeneralEnhancementUtils.getMaxPeriodYear(getC_Current_Period_ID(), get_Trx());			
			int maxAllowedPeriod = 0;
			MPeriod currentPeriod = new MPeriod(getCtx(), getC_Current_Period_ID(), get_Trx());
			if(currentPeriod.getPeriodNo()+futurePeriodAllowed > maxPeriodSeq)
			{
				maxAllowedPeriod = maxPeriodSeq;
			}
			else
			{
				maxAllowedPeriod = currentPeriod.getPeriodNo() + futurePeriodAllowed;
			}
			
			// Check Period From Period No. If bigger than max allowed period, cancel save
			if(getC_Current_Period_ID() != getC_Period_From_ID())
			{
				MPeriod fromPeriod = new MPeriod(getCtx(), getC_Period_From_ID(), get_Trx());
				if(fromPeriod.getC_Year_ID() != currentPeriod.getC_Year_ID())
				{
					return false;
				}
				else if(fromPeriod.getPeriodNo() > maxAllowedPeriod)
				{
					return false;
				}
			}
		}		
		return true;
	}
	
	public static void checkHeaderLock(Ctx ctx, int XX_BudgetRevision_ID, Trx trx)
	{
		boolean lock = false;
		// Check Account
		String sql = "SELECT * FROM XX_BudgetRevisionAccount WHERE XX_BudgetRevision_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, XX_BudgetRevision_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				lock = true;
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		// Check target Account
		if(!lock)
		{
			sql = "SELECT * FROM XX_BudgetRevisionTargetAcct WHERE XX_BudgetRevision_ID = ? ";
			pstmt = DB.prepareStatement(sql, trx);
			rs = null;
			try{
				pstmt.setInt(1, XX_BudgetRevision_ID);
				rs = pstmt.executeQuery();
				if(rs.next())
					lock = true;
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}	
		}
		
		String headerLock = "N";
		if(lock)
			headerLock = "Y";
		
		String update = "UPDATE XX_BudgetRevision SET IsLocked = ? WHERE XX_BudgetRevision_ID = ? ";
		DB.executeUpdate(trx, update, headerLock, XX_BudgetRevision_ID);
	}
	
	public static BigDecimal getPendingAmt(int AD_Org_ID, int accountID, Timestamp startDate, Timestamp endDate, int C_Activity_ID, int C_Campaign_ID, int C_Project_ID, Trx trx)
	{
		BigDecimal pendingAmt = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(bra.AppliedAmt),0) AS AppliedAmt FROM XX_BudgetRevisionAccount bra "
				+ "INNER JOIN XX_BudgetRevision br ON bra.XX_BudgetRevision_ID = br.XX_BudgetRevision_ID "
				+ "WHERE br.DocStatus IN ('IP', 'AP') "
				+ "AND br.AD_Org_ID = ? "
				+ "AND bra.Account_ID = ? "
				+ "AND br.DateEffective BETWEEN ? AND ? "
				+ "AND br.BudgetRevisionType = 'T' ");
		if(C_Activity_ID!=0)
			sql.append("AND br.C_Activity_ID = ? ");
		else
			sql.append("AND (br.C_Activity_ID = 0 OR br.C_Activity_ID IS NULL) ");
		if(C_Campaign_ID!=0)
			sql.append("AND br.C_Campaign_ID = ? ");
		else
			sql.append("AND (br.C_Campaign_ID = 0 OR br.C_Campaign_ID IS NULL) ");
		if(C_Project_ID!=0)
			sql.append("AND br.C_Project_ID = ? ");
		else
			sql.append("AND (br.C_Project_ID = 0 OR br.C_Project_ID IS NULL) ");

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		
		try{
			int index = 1;
			pstmt.setInt(index++, AD_Org_ID);
			pstmt.setInt(index++, accountID);
			pstmt.setTimestamp(index++, startDate);
			pstmt.setTimestamp(index++, endDate);
			
			if(C_Activity_ID!=0)
				pstmt.setInt(index++, C_Activity_ID);
			if(C_Campaign_ID!=0)
				pstmt.setInt(index++, C_Campaign_ID);
			if(C_Project_ID!=0)
				pstmt.setInt(index++, C_Project_ID);

			rs = pstmt.executeQuery();
			
			if(rs.next())
				pendingAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return pendingAmt;
	}

}
