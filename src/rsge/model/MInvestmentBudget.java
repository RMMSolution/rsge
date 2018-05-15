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

import org.compiere.process.DocAction;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.util.Env.QueryParams;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_XX_InvestmentBudget;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY R
 *
 */
public class MInvestmentBudget extends X_XX_InvestmentBudget implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_InvestmentBudget_ID
	 * @param trx
	 */
	public MInvestmentBudget(Ctx ctx, int XX_InvestmentBudget_ID, Trx trx) {
		super(ctx, XX_InvestmentBudget_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MInvestmentBudget(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public static int get(Ctx ctx, int AD_Org_ID, int XX_BudgetPlanning_ID, Timestamp dateDoc, Trx trx)
	{
		int ID = 0;
		String sql = "SELECT XX_InvestmentBudget_ID FROM XX_InvestmentBudget " +
				"WHERE AD_Org_ID = ? AND XX_BudgetPlanning_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Org_ID);
			pstmt.setInt(2, XX_BudgetPlanning_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				ID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(ID==0)
		{
			MInvestmentBudget ib = new MInvestmentBudget(ctx, 0, trx);
			ib.setAD_Org_ID(AD_Org_ID);
			MBudgetPlanning bp = new MBudgetPlanning(ctx, XX_BudgetPlanning_ID, trx);
			ib.setXX_BudgetPlanning_ID(XX_BudgetPlanning_ID);
			ib.setPlanningPeriod(bp.getPlanningPeriod());
			ib.setDateDoc(dateDoc);
			if(ib.save())
				ID = ib.getXX_InvestmentBudget_ID();
		}
		return ID;
	}
	
	public void setXX_BudgetPlanning_ID(String oldXX_BudgetPlanning_ID,
			String newXX_BudgetPlanning_ID, int windowNo) throws Exception
	{
		if ((newXX_BudgetPlanning_ID == null) || (newXX_BudgetPlanning_ID.length() == 0))
			return;
		Integer XX_BudgetPlanning_ID = Integer.valueOf(newXX_BudgetPlanning_ID);
		if(XX_BudgetPlanning_ID == 0)
			return;
	
		MBudgetPlanning bp = new MBudgetPlanning(getCtx(), XX_BudgetPlanning_ID, get_Trx());
		setPlanningPeriod(bp.getPlanningPeriod());
		return;
	}

/** Document Action Methods							*/	
	
	/** Error Message						*/
	private String					m_processMsg = null;
	
	/** C_Activity_ID - parameter			*/
	private int						p_C_Activity_ID = 0;
	/** C_Project_ID - parameter			*/
	private int						p_C_Project_ID = 0; 
	/** C_Campaign_ID - parameter			*/
	private int						p_C_Campaign_ID = 0;
	/** M_Product_ID - parameter			*/
	private int						p_M_Product_ID = 0;
	/** C_SalesRegion_ID - parameter			*/
	private int						p_C_SalesRegion_ID = 0;
	
	/** Investment Budget Line List 								*/
	private ArrayList<int[]>      			InvestmentBudgetLine = null; 
	/** Investment Budget Account Info List							*/
	private ArrayList<String[]>				InvestmentBudgetLineAccount = null;
	/** Investment Budget Line Amount List 							*/
	private ArrayList<BigDecimal[]>      	InvestmentBudgetLineAmt = null; 
	/** Investment Budget Line Qty List 							*/
	private ArrayList<BigDecimal[]>      	InvestmentBudgetQtyAmt = null; 
	
	
	/** Asset Depreciation List 									*/
	private ArrayList<int[]>    		  	LineDepreciation = null;   
	/** Investment Budget Depreciation Account Info List			*/
	private ArrayList<String[]>				LineDepreciationAccount = null;
	/** Asset List Amount										*/
	private ArrayList<BigDecimal[]>     	LineDepreciationAmt = null;   
	
	/** Investment Budget Line Element			*/
	private int[]					lineElement = null;
	/** Investment Budget Line Account Element	*/
	private String[]					lineAccountElement = null;
	/** Investment Budget Line Amount Element			*/
	private BigDecimal[]			lineAmtElement = null;
	/** Investment Budget Line Qty Element			*/
	private BigDecimal[]			lineQtyElement = null;
	/** Line Depreciation Element						*/
	private int[]					lineDeprElement = null;
	/** Investment Line Depreciation Account Element	*/
	private String[]					lineDeprAccountElement = null;
	/** Line Depreciation Amount Element	*/
	private BigDecimal[]			lineDeprAmtElement = null;
	
	/** Number of Period						*/
	private int						numberOfPeriod = 0;
	
	/** User Element 1 Name						*/
	private String					element1 = null;
	/** User Element 2 Name						*/
	private String					element2 = null;
	
	/** User Element 1 Exists in source Table	*/
	private boolean					element1Source = false;
	/** User Element 2 Exists in source Table	*/
	private boolean					element2Source = false;
	
	/** User Element 1 Exists in target Table	*/
	private boolean					element1Target = false;
	/** User Element 2 Exists in target Table	*/
	private boolean					element2Target = false;
	
	/** User Element 1 value					*/
	private Integer					elementValue1 = null;
	/** User Element 2 value					*/
	private Integer					elementValue2 = null;
	
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

		@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProcessMsg(String processMsg) {
		// TODO Auto-generated method stub
		
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
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		if(getC_Project_ID()>0)
			p_C_Project_ID = getC_Project_ID();
		return true;
	}

	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		String delete = null;
		
		delete = "DELETE FROM XX_InvLineItemDepreciation " +
				"WHERE XX_InvestmentBudgetLineItem_ID IN (" +
				"SELECT ibli.XX_InvestmentBudgetLineItem_ID FROM XX_InvestmentBudgetLineItem ibli " +
				"INNER JOIN XX_InvestmentBudgetLine ibl ON (ibli.XX_InvestmentBudgetLine_ID = ibl.XX_InvestmentBudgetLine_ID) " +
				"WHERE ibl.XX_InvestmenBudget_ID = ? )";
		DB.executeUpdate(get_Trx(), delete, getXX_InvestmentBudget_ID());

		
		delete = "DELETE FROM XX_InvLineDepreciation " +
				"WHERE XX_InvestmentBudgetLine_ID IN (" +
				"SELECT XX_InvestmentBudgetLine_ID FROM XX_InvestmentBudgetLine" +
				"WHERE XX_InvestmenBudget_ID = ? )";
		DB.executeUpdate(get_Trx(), delete, getXX_InvestmentBudget_ID());
		
		delete = "DELETE FROM XX_InvestmentBudgetLineItem " +
				"WHERE XX_InvestmentBudgetLine_ID IN (" +
				"SELECT XX_InvestmentBudgetLine_ID FROM XX_InvestmentBudgetLine" +
				"WHERE XX_InvestmenBudget_ID = ? )";
		DB.executeUpdate(get_Trx(), delete, getXX_InvestmentBudget_ID());


		delete = "DELETE FROM XX_InvestmentBudgetLine WHERE XX_InvestmenBudget_ID = ? ";
		DB.executeUpdate(get_Trx(), delete, getXX_InvestmentBudget_ID());
		return true;
	}
	
	@Override
	public String completeIt() {

		System.out.println("NOW HERE");
		// Prepare Basic Info
		InvestmentBudgetLine = new ArrayList<int[]>();
		InvestmentBudgetLineAccount = new ArrayList<String[]>();
		InvestmentBudgetLineAmt = new ArrayList<BigDecimal[]>();
		InvestmentBudgetQtyAmt = new ArrayList<BigDecimal[]>();
		
		MBudgetInfo info = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), get_Trx());
		
		// Get InvestmentBudgetList
		StringBuilder assetList = new StringBuilder();
		 
		assetList.append("SELECT ev.C_ElementValue_ID, ev.AccountSign, ev.AccountType, " +
				"ibl.Period1, ibl.Period2, ibl.Period3, ibl.Period4, ibl.Period5, ibl.Period6, ibl.Period7, ibl.Period8, " +
				"ibl.Period9, ibl.Period10, ibl.Period11, ibl.Period12, ibl.XX_InvestmentBudgetLine_ID, " +
				"ibl.QtyPeriod1, ibl.QtyPeriod2, ibl.QtyPeriod3, ibl.QtyPeriod4, ibl.QtyPeriod5, ibl.QtyPeriod6, ibl.QtyPeriod7, ibl.QtyPeriod8, " +
				"ibl.QtyPeriod9, ibl.QtyPeriod10, ibl.QtyPeriod11, ibl.QtyPeriod12 " +
				"FROM XX_InvestmentBudgetLine ibl " +
				"INNER JOIN A_Asset_Group ag ON (ibl.A_Asset_Group_ID = ag.A_Asset_Group_ID) " +
				"INNER JOIN M_Product_Acct p ON (ag.M_Product_ID = p.M_Product_ID AND p.C_AcctSchema_ID = ?) " +
				"INNER JOIN C_ValidCombination vc ON (p.P_Asset_Acct = vc.C_ValidCombination_ID) " +
				"INNER JOIN C_ElementValue ev ON (vc.Account_ID = ev.C_ElementValue_ID)  " +
				"WHERE ibl.XX_InvestmentBudget_ID = ? " +
				"AND (ibl.TotalAmt > 0 OR ibl.TotalQty > 0) ");
		
		PreparedStatement pstmt = DB.prepareStatement(assetList.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			System.out.println(assetList);
			System.out.println(info.getC_AcctSchema_ID());
			System.out.println(getXX_InvestmentBudget_ID());
			pstmt.setInt(1, info.getC_AcctSchema_ID());
			pstmt.setInt(2, getXX_InvestmentBudget_ID());
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				lineElement = new int[2]; // record Account info
				lineAccountElement = new String[2];
				lineAmtElement = new BigDecimal[12];
				lineQtyElement = new BigDecimal[12];
				
				lineElement[0] = rs.getInt("C_ElementValue_ID");
				lineElement[1] = rs.getInt("XX_InvestmentBudgetLine_ID");				
				InvestmentBudgetLine.add(lineElement);
				
				lineAccountElement[0] = rs.getString(2);
				lineAccountElement[1] = rs.getString(3);
				
				InvestmentBudgetLineAccount.add(lineAccountElement);
				
				lineAmtElement[0] = rs.getBigDecimal(4);
				lineAmtElement[1] = rs.getBigDecimal(5);
				lineAmtElement[2] = rs.getBigDecimal(6);
				lineAmtElement[3] = rs.getBigDecimal(7);
				lineAmtElement[4] = rs.getBigDecimal(8);
				lineAmtElement[5] = rs.getBigDecimal(9);
				lineAmtElement[6] = rs.getBigDecimal(10);
				lineAmtElement[7] = rs.getBigDecimal(11);
				lineAmtElement[8] = rs.getBigDecimal(12);
				lineAmtElement[9] = rs.getBigDecimal(13);
				lineAmtElement[10] = rs.getBigDecimal(14);
				lineAmtElement[11] = rs.getBigDecimal(15);

				lineQtyElement[0] = rs.getBigDecimal(17);
				lineQtyElement[1] = rs.getBigDecimal(18);
				lineQtyElement[2] = rs.getBigDecimal(19);
				lineQtyElement[3] = rs.getBigDecimal(20);
				lineQtyElement[4] = rs.getBigDecimal(21);
				lineQtyElement[5] = rs.getBigDecimal(22);
				lineQtyElement[6] = rs.getBigDecimal(23);
				lineQtyElement[7] = rs.getBigDecimal(24);
				lineQtyElement[8] = rs.getBigDecimal(25);
				lineQtyElement[9] = rs.getBigDecimal(26);
				lineQtyElement[10] = rs.getBigDecimal(27);
				lineQtyElement[11] = rs.getBigDecimal(28);

				InvestmentBudgetLineAmt.add(lineAmtElement);			
				InvestmentBudgetQtyAmt.add(lineQtyElement);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		// Get Investment Budget List Depreciation
		
		LineDepreciation = new ArrayList<int[]>();
		LineDepreciationAccount = new ArrayList<String[]>();
		LineDepreciationAmt = new ArrayList<BigDecimal[]>();
		
		StringBuilder assetDeprList = new StringBuilder();
		
		assetDeprList.append("SELECT ibl.BudgetAssetDepreciation_Acct, ev.AccountSign, ev.AccountType, " +
				"ild.Period1, ild.Period2, ild.Period3, ild.Period4, ild.Period5, ild.Period6, ild.Period7, ild.Period8, " +
				"ild.Period9, ild.Period10, ild.Period11, ild.Period12, ild.XX_InvLineDepreciation_ID " +
				"FROM XX_InvLineDepreciation ild " +
				"INNER JOIN XX_InvestmentBudgetLine ibl ON (ild.XX_InvestmentBudgetLine_ID = ibl.XX_InvestmentBudgetLine_ID) " +
				"INNER JOIN XX_InvestmentBudget ib ON (ibl.XX_InvestmentBudget_ID = ib.XX_InvestmentBudget_ID) " +
				"INNER JOIN C_ElementValue ev ON (ibl.BudgetAssetDepreciation_Acct = ev.C_ElementValue_ID) " +
				"WHERE ibl.IsDepreciated = 'Y' " +
				"AND ib.XX_InvestmentBudget_ID = ? ");
		
		pstmt = DB.prepareStatement(assetDeprList.toString(), get_Trx());
		rs = null;
		
		try{
			pstmt.setInt(1, getXX_InvestmentBudget_ID());
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				lineDeprElement = new int[2]; // record Account info
				lineDeprAccountElement = new String[2];
				lineDeprAmtElement = new BigDecimal[12];
				
				lineDeprElement[0] = rs.getInt(1);				
				lineDeprElement[1] = rs.getInt(16);
				
				LineDepreciation.add(lineDeprElement);
				
				lineDeprAccountElement[0] = rs.getString(2);
				lineDeprAccountElement[1] = rs.getString(3);
				
				LineDepreciationAccount.add(lineDeprAccountElement);
				
				lineDeprAmtElement[0] = rs.getBigDecimal(4);
				lineDeprAmtElement[1] = rs.getBigDecimal(5);
				lineDeprAmtElement[2] = rs.getBigDecimal(6);
				lineDeprAmtElement[3] = rs.getBigDecimal(7);
				lineDeprAmtElement[4] = rs.getBigDecimal(8);
				lineDeprAmtElement[5] = rs.getBigDecimal(9);
				lineDeprAmtElement[6] = rs.getBigDecimal(10);
				lineDeprAmtElement[7] = rs.getBigDecimal(11);
				lineDeprAmtElement[8] = rs.getBigDecimal(12);
				lineDeprAmtElement[9] = rs.getBigDecimal(13);
				lineDeprAmtElement[10] = rs.getBigDecimal(14);
				lineDeprAmtElement[11] = rs.getBigDecimal(15);
								
				LineDepreciationAmt.add(lineDeprAmtElement);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		// End - Prepare data
		
		//Set Number of Periods
		if(getPlanningPeriod().equals(PLANNINGPERIOD_Monthly))
			numberOfPeriod = 12;
		else if(getPlanningPeriod().equals(PLANNINGPERIOD_Quarterly))
			numberOfPeriod = 4;
		else if(getPlanningPeriod().equals(PLANNINGPERIOD_Semester))
			numberOfPeriod = 2;
		
		int XX_BudgetPlanningLine_ID = 0;
		
		// Process Investment Budget Line		
		for(int account = 0; account < InvestmentBudgetLine.size(); account++)
		{
			int a[] = InvestmentBudgetLine.get(account);
			BigDecimal b[] = InvestmentBudgetLineAmt.get(account);			
			String c[] = InvestmentBudgetLineAccount.get(account);
			BigDecimal d[] = InvestmentBudgetQtyAmt.get(account);
			
			// Check Account and create Budget Line
			XX_BudgetPlanningLine_ID = checkAccount(a[0]);
			
			//Update Budget Line Period
			for(int x = 0; x < numberOfPeriod; x++)
			{
				BigDecimal SourceAmt = b[x];
				BigDecimal sourceQty = d[x];

				int periodNo = x + 1;				
				int XX_BudgetPlanningLinePeriod_ID = getPlanningLinePeriod(XX_BudgetPlanningLine_ID, periodNo);
				MPlanningLineSrc pls = new MPlanningLineSrc(getCtx(), 0, get_Trx());
				pls.setAD_Org_ID(getAD_Org_ID());
				pls.setXX_BudgetPlanningLinePeriod_ID(XX_BudgetPlanningLinePeriod_ID);
				pls.setXX_InvestmentBudgetLine_ID(a[1]); 
				pls.setAmtSource(SourceAmt);
				
				// Set Amount Debit and Amount Credit
				// Case 1 - Based on natural sign of the account
				if(c[0].equals("N")) 
				{
					// Case 1.1 - If Account is Asset or Expense - Natural Sign is Debit
					if((c[1].equals("A") || c[1].equals("E"))) 
					{
						if(SourceAmt.signum() >= 0)
						{									
							pls.setAmtAcctDr(SourceAmt);
						}
						else if(c[0].equals("D") & SourceAmt.signum() < 0)
						{
							pls.setAmtAcctCr(SourceAmt.negate());
						}
					} // End Case 1.1
					
					// Case 1.2 - If Account is Liability, Revenue or Owner's Equity - Natural Sign is Credit
					else if( (c[1].equals("L")) || (c[1].equals("R")) || (c[1].equals("O")) )
					{
						if(SourceAmt.signum() >= 0)
						{
							pls.setAmtAcctCr(SourceAmt);
						}
						else if(SourceAmt.signum() < 0)
						{
							pls.setAmtAcctDr(SourceAmt.negate());
						}
					} // End Case 1.2
				}// End Case 1						
				
				// Case 2 - Account Sign Debit
				// Case 2.1 - Transaction Amount is positive
				else if(c[0].equals("D") & SourceAmt.signum() >= 0)
				{
					pls.setAmtAcctDr(SourceAmt);
				}
				// Case 2.2 - Transaction Amount is negative
				else if(c[0].equals("D") & SourceAmt.signum() < 0)
				{
					pls.setAmtAcctCr(SourceAmt.negate());							
				}
				// End Case 2
				
				// Case 3 - Account Sign Credit
				// Case 3.1 - Transaction Amount is positive
				else if(c[0].equals("C") & SourceAmt.signum() >= 0)
				{
					pls.setAmtAcctCr(SourceAmt);
				}
				// Case 3.2 - Transaction Amount is negative
				else if(c[0].equals("C") & SourceAmt.signum() < 0)
				{
					pls.setAmtAcctDr(SourceAmt.negate());
				}
				// End Case 3
				pls.setQty(sourceQty);
				pls.save();  // End create  Budget Planning Line Period
				System.out.println("Source : " + pls.getXX_PlanningLineSrc_ID());
			}			
		}
		// End Process Investment Budget Line
				
		// Process Investment Depreciation Budget Line		
		for(int deprAccount = 0; deprAccount < LineDepreciation.size(); deprAccount++)
		{
			int d[] = LineDepreciation.get(deprAccount);
			BigDecimal e[] = LineDepreciationAmt.get(deprAccount);
//			String f[] = LineDepreciationAccount.get(deprAccount);
			
			// Check Account and create Budget Line
			XX_BudgetPlanningLine_ID = checkAccount(d[0]);
			
			//Update Budget Line Period
			for(int x = 0; x < numberOfPeriod; x++)
			{
				BigDecimal SourceAmt = e[x];

				int periodNo = x + 1;				
				int XX_BudgetPlanningLinePeriod_ID = getPlanningLinePeriod(XX_BudgetPlanningLine_ID, periodNo);
				MPlanningLineSrc pls = new MPlanningLineSrc(getCtx(), 0, get_Trx());
				pls.setAD_Org_ID(getAD_Org_ID());
				pls.setXX_BudgetPlanningLinePeriod_ID(XX_BudgetPlanningLinePeriod_ID);
				pls.setXX_InvLineDepreciation_ID(d[1]); 
				pls.setAmtSource(SourceAmt);
				
				// Set Amount Debit and Amount Credit
				// Set Amount to Credit 
				pls.setAmtAcctCr(SourceAmt);
				pls.save();  // End create  Budget Planning Line Period
			}
		}// End Process Investment Budget Line Depreciation
		
		// 
		String update = "UPDATE XX_BudgetPlanningLinePeriod SET Processed = 'Y' " +
				"WHERE XX_BudgetPlanningLinePeriod_ID IN(" +
				"SELECT lp.XX_BudgetPlanningLinePeriod_ID FROM XX_BudgetPlanningLinePeriod lp " +
				"INNER JOIN XX_BudgetPlanningLine pl ON (lp.XX_BudgetPlanningLine_ID = pl.XX_BudgetPlanningLine_ID) " +
				"INNER JOIN XX_BudgetPlanning bp ON (pl.XX_BudgetPlanning_ID = bp.XX_BudgetPlanning_ID) " +
				"WHERE bp.Processed = 'Y' " +
				"AND lp.Processed = 'N' ) " +
				"AND AD_Client_ID = ? ";
		DB.executeUpdate(get_Trx(), update, getAD_Client_ID());
		
		return DocActionConstants.STATUS_Completed;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}	
	
	@Override
	public String prepareIt() {
				
		// Get InvestmentBudgetList
		StringBuilder assetList = new StringBuilder();
		
		boolean hasValidLine = true;
		assetList.append("SELECT COUNT(1) " +
				"FROM XX_InvestmentBudgetLine ibl " +
				"WHERE ibl.XX_InvestmentBudget_ID = ? " +
				"AND (ibl.TotalAmt > 0 OR ibl.TotalQty > 0) ");
		
		PreparedStatement pstmt = DB.prepareStatement(assetList.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_InvestmentBudget_ID());
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				if(rs.getInt(1)==0)
					hasValidLine = false;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		// Check if documents has valid line. If not, invalidate document
		if(!hasValidLine)
		{
			m_processMsg = Msg.getMsg(getCtx(), "NoLines");
			return DocActionConstants.STATUS_Invalid;
		}
		
		return DocActionConstants.STATUS_InProgress;
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/** 
	 * Check if account with selected segment is exists in Budget Planning Line. 
	 * @return XX_BudgetPlanningLine_ID
	 */
	private int checkAccount(int Account_ID)
	{
		int XX_BudgetPlanningLine_ID = 0;
		
		MBudgetInfo info = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), get_Trx());
		
		element1 = GeneralEnhancementUtils.getElementColumn(info.getC_AcctSchema_ID(), 1, get_Trx());
		element2 = GeneralEnhancementUtils.getElementColumn(info.getC_AcctSchema_ID(), 2, get_Trx());
		
		if(element1!=null)
		{
			element1Source = GeneralEnhancementUtils.checkColumn("XX_InvestmentBudget", element1, get_Trx());
			if(element1Source)
			{
				elementValue1 = GeneralEnhancementUtils.getElementValue(element1, "XX_InvestmentBudget", getXX_InvestmentBudget_ID(), get_Trx());
			}
			element1Target = GeneralEnhancementUtils.checkColumn("XX_BudgetPlanningLine", element1, get_Trx());
		}
		if(element2!=null)
		{
			element2Source = GeneralEnhancementUtils.checkColumn("XX_InvestmentBudget", element2, get_Trx());
			if(element2Source)
			{
				elementValue2 = GeneralEnhancementUtils.getElementValue(element2, "XX_InvestmentBudget", getXX_InvestmentBudget_ID(), get_Trx());
			}
			element2Target = GeneralEnhancementUtils.checkColumn("XX_BudgetPlanningLine", element2, get_Trx());
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT XX_BudgetPlanningLine_ID " +
				"FROM XX_BudgetPlanningLine " +
				"WHERE XX_BudgetPlanning_ID = ? " +
				"AND Account_ID = ? ");
		
		if(getC_Activity_ID()!=0)
		{
			sql.append("AND C_Activity_ID = " + p_C_Activity_ID + " ");
		}
		else 
		{
			sql.append("AND (C_Activity_ID = 0 OR C_Activity_ID IS NULL) ");
		}
		if(getC_Project_ID()!=0)
		{
			sql.append("AND C_Project_ID = " + p_C_Project_ID + " ");
		}
		else
		{
			sql.append("AND (C_Project_ID = 0 OR C_Project_ID IS NULL) ");
		}
		if(getC_Campaign_ID()!=0)
		{
			sql.append("AND C_Campaign_ID = " + p_C_Campaign_ID + " ");
		}
		else
		{
			sql.append("AND (C_Campaign_ID = 0 OR C_Campaign_ID IS NULL) ");
		}		
		
		sql.append("AND (M_Product_ID = 0 OR M_Product_ID IS NULL) ")
			.append("AND (C_SalesRegion_ID = 0 OR C_SalesRegion_ID IS NULL) ");		
		
		// For User Defined Element
		if(element1Target)
		{
			if(elementValue1 > 0)
			{
				sql.append("AND " + element1 + " = " + elementValue1 + " ");
			}
			else
			{
				sql.append("AND (" + element1 + "= 0 OR " + element1 + " IS NULL) ");
			}
		}
		if(element2Target)
		{
			if(elementValue2 > 0)
			{
				sql.append("AND " + element2 + " = " + elementValue2 + " ");
			}
			else
			{
				sql.append("AND (" + element2 + "= 0 OR " + element2 + " IS NULL) ");
			}
		}
		
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_BudgetPlanning_ID());
			pstmt.setInt(2, Account_ID);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				XX_BudgetPlanningLine_ID = rs.getInt(1);				
			}
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		if(XX_BudgetPlanningLine_ID == 0) // Create new one if not exists yet
		{
			MBudgetPlanningLine bpl = new MBudgetPlanningLine(getCtx(), 0, get_Trx());
			bpl.setAD_Org_ID(getAD_Org_ID());
			bpl.setXX_BudgetPlanning_ID(getXX_BudgetPlanning_ID());
			bpl.setAccount_ID(Account_ID);
			bpl.setIsGenerated(true);
			bpl.setC_Activity_ID(p_C_Activity_ID);
			bpl.setC_Project_ID(p_C_Project_ID);
			bpl.setC_Campaign_ID(p_C_Campaign_ID);
			bpl.setM_Product_ID(p_M_Product_ID);
			bpl.setC_SalesRegion_ID(p_C_SalesRegion_ID);
			bpl.setAmtAcctDr(Env.ZERO);
			bpl.setAmtAcctCr(Env.ZERO);
			
			if(element1Source && element1Target && elementValue1!=null)
			{
				bpl.setElement_X1(elementValue1);
			}
			if(element2Source && element2Target && elementValue2!=null)
			{
				bpl.setElement_X2(elementValue2);
			}
			
			if(bpl.save())
			{
				XX_BudgetPlanningLine_ID = bpl.getXX_BudgetPlanningLine_ID();
			}
			
			// Update XX_BudgetPlanningLine with User Element
			GeneralEnhancementUtils.updateElementValue("XX_BudgetPlanningLine", XX_BudgetPlanningLine_ID, element1Target, element1, elementValue1, element2Target, element2, elementValue2, get_Trx());
			updateGenerateStatus(XX_BudgetPlanningLine_ID);
		}
		return XX_BudgetPlanningLine_ID;
	} // End check budget form line	
	
	private int getPlanningLinePeriod(int XX_BudgetPlanningLine_ID, int PeriodNo)
	{
		int XX_BudgetPlanningLinePeriod_ID = 0;
		
		String sql = "SELECT XX_BudgetPlanningLinePeriod_ID " +
				"FROM XX_BudgetPlanningLinePeriod " +
				"WHERE XX_BudgetPlanningLine_ID = ? " +
				"AND PeriodNo = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, XX_BudgetPlanningLine_ID);
			pstmt.setInt(2, PeriodNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				XX_BudgetPlanningLinePeriod_ID = rs.getInt(1);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		return XX_BudgetPlanningLinePeriod_ID;
	}
	
	private void updateGenerateStatus(int XX_BudgetPlanningLine_ID)
	{
		MBudgetPlanningLine bpl = new MBudgetPlanningLine(getCtx(), XX_BudgetPlanningLine_ID, get_Trx());
		if(!bpl.isGenerated()) // Update Budget Planning Line's Generated -> Yes 
		{
			String sql = "UPDATE XX_BudgetPlanningLine " +
					"SET IsGenerated = 'Y' " +
					"WHERE XX_BudgetPlanningLine_ID = " + XX_BudgetPlanningLine_ID;
			
			PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
			
			try{
				pstmt.executeUpdate();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		}
	}
}
