/**
 * 
 */
package rsge.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.api.UICallout;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClientInfo;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.X_M_Product;
import org.compiere.process.DocAction;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Env.QueryParams;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_I_BudgetForm;
import rsge.po.X_XX_BudgetForm;
import rsge.utils.BudgetUtils;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY
 *
 */
public class MBudgetForm extends X_XX_BudgetForm implements DocAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Logger for class MBudgetForm */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MBudgetForm.class);

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param XX_BudgetForm_ID
	 * @param trx
	 */
	public MBudgetForm(Ctx ctx, int XX_BudgetForm_ID, Trx trx) {
		super(ctx, XX_BudgetForm_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MBudgetForm(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}		
	
	public MBudgetForm(X_I_BudgetForm imp) {
		// TODO Auto-generated constructor stub
		this(imp.getCtx(), 0, imp.get_Trx());
		setC_Currency_ID(imp.getC_Currency_ID());

		if(imp.isProduct())
		{
			setIsProduct(imp.isProduct());
			setIsSOTrx(imp.isSOTrx());
			setTransactionType(imp.getTransactionType());
		}
		
		setC_Activity_ID(imp.getC_Activity_ID());
		setC_Campaign_ID(imp.getC_Campaign_ID());
		setC_Project_ID(imp.getC_Project_ID());		
	}

	/* (non-Javadoc)
	 * @see org.compiere.framework.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// Populate Planning Period and GL Budget from Budget Planning 
		MBudgetPlanning bplan = new MBudgetPlanning(getCtx(), getXX_BudgetPlanning_ID(), get_Trx());
		setPlanningPeriod(bplan.getPlanningPeriod());
		setGL_Budget_ID(bplan.getGL_Budget_ID());
		
		if(isProduct() && !isSOTrx() && !getTransactionType().equals(TRANSACTIONTYPE_SalesPurchase))
		{
			setTransactionType(TRANSACTIONTYPE_SalesPurchase);
		}

		return true;
	}

	/**
	 * 	Set XX_BudgetFormTemplate_ID - Callout
	 *  
	 *	@param oldXX_BudgetFormTemplate_ID old XX_BudgetFormTemplate_ID
	 *	@param newXX_BudgetFormTemplate_ID new XX_BudgetFormTemplate_ID
	 *	@param windowNo window no
	 */
	@UICallout public void setXX_BudgetFormTemplate_ID (String oldXX_BudgetFormTemplate_ID,
			String newXX_BudgetFormTemplate_ID, int windowNo) throws Exception
	{
		if(newXX_BudgetFormTemplate_ID == null || newXX_BudgetFormTemplate_ID.length() == 0)		
			return;
		int XX_BudgetFormTemplate_ID = Integer.valueOf(newXX_BudgetFormTemplate_ID);
		
		// Get data from Template
		String sql = "SELECT IsProduct, IsSoTrx, TransactionType, IsUsedProductTree " +
						"FROM XX_BudgetFormTemplate " +
						"WHERE XX_BudgetFormTemplate_ID = " + XX_BudgetFormTemplate_ID;
				
		PreparedStatement pstmt = DB.prepareStatement(sql, (Trx)null);
		ResultSet rs = null;
				
		try{
				rs = pstmt.executeQuery();
					
				if(rs.next())
				{
					setIsProduct("Y".equals(rs.getString(1)));
					setIsSOTrx("Y".equals(rs.getString(2)));
					setTransactionType(rs.getString(3));
					if(rs.getString(4).equalsIgnoreCase("Y"))
					{
						setIsUsedProductTree(true);					
					}
					else	
					{
						setIsUsedProductTree(false);					
					}
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
				finally
				{
					DB.closeResultSet(rs);
					DB.closeStatement(pstmt);
				}
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.framework.PO#afterSave(boolean, boolean)
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		String sql = "SELECT 1 FROM XX_BudgetFormLine " +
				"WHERE XX_BudgetForm_ID = " + getXX_BudgetForm_ID();
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			
			if(rs.next())
				return true; // Line found. Process end
			
		}catch (Exception e) {
			e.printStackTrace();
		}		

		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		// Line not found. Create line
		sql = "SELECT * " +
				"FROM XX_BudgetFormTemplateLine " +
				"WHERE XX_BudgetFormTemplate_ID = ? ";
		
		pstmt = DB.prepareStatement(sql, get_Trx());
		rs = null;
		
		try{
			pstmt.setInt(1, getXX_BudgetFormTemplate_ID());
			rs = pstmt.executeQuery();
			
			while(rs.next()) // Create line(s)
			{
				MBudgetFormTemplateLine tl = new MBudgetFormTemplateLine(getCtx(), rs, get_Trx());
				
				MBudgetFormLine bl = new MBudgetFormLine(getCtx(), 0, get_Trx());
				bl.setAD_Org_ID(getAD_Org_ID());
				bl.setXX_BudgetForm_ID(getXX_BudgetForm_ID());
				bl.setLine(tl.getLine());
				if(getPlanningPeriod().equals(PLANNINGPERIOD_Monthly))
				{
					bl.setIsShowMQ(true);
					bl.setIsShowM(true);
				}
				else if(getPlanningPeriod().equals(PLANNINGPERIOD_Quarterly))
				{
					bl.setIsShowMQ(true);
					bl.setIsShowM(false);
				}
				else
				{
					bl.setIsShowM(false);
					bl.setIsShowMQ(false);
				}				

				int accountID = 0;
				int productID = 0;

				if(!isProduct())
				{
					accountID = tl.getAccount_ID();
				}
				else
				{
					// Get Accounting Schema
					if(rs.getInt("M_Product_ID")>0)
						productID = rs.getInt("M_Product_ID");
					else
						productID = rs.getInt("M_Product_Summary_ID");
					
					MAcctSchema as = getOrgAcctSchema(getAD_Org_ID());
					MProduct p = new MProduct(getCtx(), productID, get_Trx());
					MProductAcct pa = MProductAcct.get(productID, as);
					MAccount account = null;
					
					if(isSOTrx())
					{
						if(getTransactionType().equals(TRANSACTIONTYPE_SalesPurchase))
						{
							account = new MAccount(getCtx(), pa.getP_Revenue_Acct(), get_Trx());
						}
						else if(getTransactionType().equals(TRANSACTIONTYPE_Discount))
						{
							account = new MAccount(getCtx(), pa.getP_TradeDiscountGrant_Acct(), get_Trx());							
						}
						else
						{
							if(pa.isUseReturnAccount())
								account = new MAccount(getCtx(), pa.getP_SalesReturn_Acct(), get_Trx());							
							else
								account = new MAccount(getCtx(), pa.getP_Revenue_Acct(), get_Trx());															
						}

					}
					else
					{
						if(p.getProductType().equals(X_M_Product.PRODUCTTYPE_Item))
							account = new MAccount(getCtx(), pa.getP_Asset_Acct(), get_Trx());							
						else 
							account = new MAccount(getCtx(), pa.getP_Expense_Acct(), get_Trx());							
					}
					accountID = account.getAccount_ID();
				}
				
				if(!isUsedProductTree())
				{
					bl.setM_Product_ID(productID);					
				}
				else
					bl.setM_Product_Summary_ID(productID);
				bl.setDescription(tl.getDescription());
				bl.setC_Project_ID(tl.getC_Project_ID());
				bl.setAccount_ID(accountID);
				bl.save();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}		

		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}				
		return true;
	}
	
	private MAcctSchema getOrgAcctSchema(int AD_Org_ID)
	{
		MAcctSchema as = null;
		String sql = "SELECT * FROM C_AcctSchema WHERE AD_OrgOnly_ID = " + AD_Org_ID;
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
				as = new MAcctSchema(getCtx(), rs, get_Trx());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		if(as==null)
		{
			MClientInfo ci = MClientInfo.get(getCtx(), getAD_Client_ID());
			as = new MAcctSchema(getCtx(), ci.getC_AcctSchema1_ID(), get_Trx());
		}
		return as;
	}
	
	/** Initialize Variable					*/
	private BigDecimal				conversionRate = Env.ZERO; 
	/** Accounting Schema Currency			*/
	private int 					C_Currency_Acct_ID = 0;
	
	/** Error Message						*/
	private String					m_processMsg = null;
	
	/** Number of Period					*/
	private int						numberOfPeriod = 0;
	
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
	private int					elementValue1 = 0;
	/** User Element 2 value					*/
	private int					elementValue2 = 0;
	
	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

 
	/**
	 * 	Invalidate Document
	 * 	@return true if success
	 */
	public boolean invalidateIt()
	{
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}	//	invalidateIt

	@Override
	public String prepareIt() {
		conversionRate = getConversionRate();		
		if(conversionRate == null)
		{
			return DocActionConstants.STATUS_Invalid;			
		}
		//End get currency				
		return DocActionConstants.STATUS_InProgress;
	}

	@Override
	public String completeIt() {
		// Setup
		MBudgetInfo info = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), get_Trx());
		conversionRate = getConversionRate();
		
		// Setup Parameter
		if(Integer.toString(getC_Activity_ID()).length()!=0)
		{
			p_C_Activity_ID = getC_Activity_ID();
		}		
		if(Integer.toString(getC_Project_ID()).length()!=0)
		{
			p_C_Project_ID = getC_Project_ID();
		}
		if(Integer.toString(getC_Campaign_ID()).length()!=0)
		{
			p_C_Campaign_ID = getC_Campaign_ID();
		}
		if(Integer.toString(getM_Product_ID()).length()!=0)
		{
			p_M_Product_ID = getM_Product_ID();
		}
		if(Integer.toString(getC_SalesRegion_ID()).length()!=0)
		{
			p_C_SalesRegion_ID = getC_SalesRegion_ID();
		}		

		//Set Number of Periods
		if(getPlanningPeriod().equals(PLANNINGPERIOD_Monthly))
			numberOfPeriod = 12;
		else if(getPlanningPeriod().equals(PLANNINGPERIOD_Quarterly))
			numberOfPeriod = 4;
		else if(getPlanningPeriod().equals(PLANNINGPERIOD_Semester))
			numberOfPeriod = 2;
		
		// Get User Element Column name (if exists)
		element1 = GeneralEnhancementUtils.getElementColumn(info.getC_AcctSchema_ID(), 1, get_Trx());
		element2 = GeneralEnhancementUtils.getElementColumn(info.getC_AcctSchema_ID(), 2, get_Trx());
		
		// Check if element exists in source table (XX_BudgetFormLine)
		if(element1!=null)
		{
			element1Source = GeneralEnhancementUtils.checkColumn("XX_BudgetFormLine", element1, get_Trx());
		}
		if(element2!=null)
		{
			element2Source = GeneralEnhancementUtils.checkColumn("XX_BudgetFormLine", element2, get_Trx());
		}
		
		// Check if element exists in target table (XX_BudgetPlanningLine)
		if(element1!=null)
		{
			element1Target = GeneralEnhancementUtils.checkColumn("XX_BudgetPlanningLine", element1, get_Trx());	
		}
		if(element2!=null)
		{
			element2Target = GeneralEnhancementUtils.checkColumn("XX_BudgetPlanningLine", element2, get_Trx());
		}
		
		// Check if account is exists in budget planning line. if not, create new one
		int XX_BudgetPlanningLine_ID = 0;
		int XX_BudgetPlanningLinePeriod_ID = 0;
		
		StringBuilder sql = new StringBuilder();
		// SELECT Statement for Product Based
		sql.append("SELECT bfl.Account_ID, ev.AccountSign, ev.AccountType, " +
				"bfl.Period1, bfl.Period2, bfl.Period3, bfl.Period4, bfl.Period5, " +
				"bfl.Period6, bfl.Period7, bfl.Period8, bfl.Period9, bfl.Period10, bfl.Period11, bfl.Period12, " +
				"bfl.QtyPeriod1, bfl.QtyPeriod2, bfl.QtyPeriod3, bfl.QtyPeriod4, bfl.QtyPeriod5, " +
				"bfl.QtyPeriod6, bfl.QtyPeriod7, bfl.QtyPeriod8, bfl.QtyPeriod9, bfl.QtyPeriod10, bfl.QtyPeriod11, bfl.QtyPeriod12, " +
				"bfl.XX_BudgetFormLine_ID ");		
		
		// Set Product column based on IsUsedProductTree
		if(!isUsedProductTree())
		{
			sql.append(", bfl.M_Product_ID"); 
		}
		else
		{
			sql.append(", bfl.M_Product_Summary_ID");
		}
		
		// If Source Table has both user element column
		if(element1Source && element2Source) 
		{
			sql.append(", bfl." + element1 + ", bfl." + element2); 
		}
		// If Source Table has user element column 1 only
		else if(element1Source && !element2Source) 
		{
			sql.append(", bfl." + element1 + ", null");
		}
		// If Source Table has user element column 2 only
		else if(element1Source && !element2Source) 
		{
			sql.append(", null, bfl." + element2);
		}
		// If Source Table does not have user element column 2
		else if(element1Source && !element2Source) 
		{
			sql.append(", null, null");
		}
		
		// Add FROM syntax
		sql.append(" FROM XX_BudgetFormLine bfl " +
				"INNER JOIN XX_BudgetForm bf ON (bfl.XX_BudgetForm_ID = bf.XX_BudgetForm_ID) ");
		
		sql.append("INNER JOIN C_ElementValue ev ON (bfl.Account_ID = ev.C_ElementValue_ID) " +
					"WHERE bfl.XX_BudgetForm_ID = ? ");			
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_BudgetForm_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				if(element1Source && element1Target)
				{
					elementValue1 = rs.getInt(element1);					
				}
				if(element2Source && element2Target)
				{
					elementValue2 = rs.getInt(element2);					
				}
				
				int productID = 0;
				String product = null;
				// Set Product column based on IsUsedProductTree
				if(!isUsedProductTree())
				{
					productID = rs.getInt("M_Product_ID");
					product = "M_Product_ID";
				}
				else
				{
					productID = rs.getInt("M_Product_Summary_ID"); 
					product = "M_Product_Summary_ID";
				}

				XX_BudgetPlanningLine_ID = checkAccount(rs.getInt("Account_ID"), productID, elementValue1, elementValue2); 
								

				if(!isProduct())
				 {
					//Update Budget Line Period
					for(int x=0; x < numberOfPeriod; x++)
					{
						int data = 4 + x;
						BigDecimal SourceAmt = rs.getBigDecimal(data);

						MCurrency currency = new MCurrency(getCtx(), C_Currency_Acct_ID, get_Trx());
						BigDecimal TrxAmt = (SourceAmt.multiply(conversionRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP));
						
						int periodNo = x + 1;
						XX_BudgetPlanningLinePeriod_ID = getPlanningLinePeriod(XX_BudgetPlanningLine_ID, periodNo);
						
						MPlanningLineSrc pls = new MPlanningLineSrc(getCtx(), 0, get_Trx());
						pls.setAD_Org_ID(getAD_Org_ID());
						pls.setXX_BudgetPlanningLinePeriod_ID(XX_BudgetPlanningLinePeriod_ID);
						pls.setXX_BudgetFormLine_ID(rs.getInt("XX_BudgetFormLine_ID"));
						pls.setAmtSource(SourceAmt);
						
						// Set Amount Debit and Amount Credit
						// Case 1 - Based on natural sign of the account
						if(rs.getString("AccountSign").equals("N")) 
						{
							// Case 1.1 - If Account is Asset or Expense - Natural Sign is Credit
							if((rs.getString("AccountType").equals("A") || rs.getString("AccountType").equals("E"))) 
							{
								if(TrxAmt.signum() >= 0)
								{									
									pls.setAmtAcctDr(TrxAmt);
								}
								else if(rs.getString("AccountSign").equals("D") & TrxAmt.signum() < 0)
								{
									pls.setAmtAcctCr(TrxAmt.negate());
								}
							} // End Case 1.1
							
							// Case 1.2 - If Account is Liability, Revenue or Owner's Equity - Natural Sign is Credit
							else if( (rs.getString("AccountType").equals("L")) || (rs.getString("AccountType").equals("R")) || (rs.getString("AccountType").equals("O")) )
							{
								if(TrxAmt.signum() >= 0)
								{
									pls.setAmtAcctCr(TrxAmt);
								}
								else if(TrxAmt.signum() < 0)
								{
									pls.setAmtAcctDr(TrxAmt.negate());
								}
							} // End Case 1.2
						}// End Case 1						
						
						// Case 2 - Account Sign Debit
						// Case 2.1 - Transaction Amount is positive
						else if(rs.getString("AccountSign").equals("D") & TrxAmt.signum() >= 0)
						{
							pls.setAmtAcctDr(TrxAmt);
						}
						// Case 2.2 - Transaction Amount is negative
						else if(rs.getString("AccountSign").equals("D") & TrxAmt.signum() < 0)
						{
							pls.setAmtAcctCr(TrxAmt.negate());							
						}
						// End Case 2
						
						// Case 3 - Account Sign Credit
						// Case 3.1 - Transaction Amount is positive
						else if(rs.getString("AccountSign").equals("C") & TrxAmt.signum() >= 0)
						{
							pls.setAmtAcctCr(TrxAmt);
						}
						// Case 3.2 - Transaction Amount is negative
						else if(rs.getString("AccountSign").equals("C") & TrxAmt.signum() < 0)
						{
							pls.setAmtAcctDr(TrxAmt.negate());
						}
						// End Case 3
						pls.save();  // End create  Budget Planning Line Period
					}
					
				 }
				 else if(isProduct())
				 {
					//Update Budget Line Period
					for(int x=0; x < numberOfPeriod; x++)
					{
						int data = 4 + x;
						int qty = 16 + x;
						BigDecimal SourceAmt = rs.getBigDecimal(data);		
						BigDecimal qtyAmt = rs.getBigDecimal(qty);
						MCurrency currency = new MCurrency(getCtx(), C_Currency_Acct_ID, get_Trx());						
						BigDecimal TrxAmt = (SourceAmt.multiply(conversionRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP));
						
						int periodNo = x + 1;
						p_M_Product_ID = rs.getInt(product);
						XX_BudgetPlanningLinePeriod_ID = getPlanningLinePeriod(XX_BudgetPlanningLine_ID, periodNo);
						
						MPlanningLineSrc pls = new MPlanningLineSrc(getCtx(), 0, get_Trx());
						pls.setAD_Org_ID(getAD_Org_ID());
						pls.setXX_BudgetPlanningLinePeriod_ID(XX_BudgetPlanningLinePeriod_ID);
						pls.setXX_BudgetFormLine_ID(rs.getInt("XX_BudgetFormLine_ID"));												
						pls.setAmtSource(SourceAmt);
						
						// Case 1 - Sales Transaction OR Purchasing Return or Purchasing Discount 
						if((isSOTrx() & getTransactionType().equals(TRANSACTIONTYPE_SalesPurchase))
								|| (!isSOTrx() & getTransactionType().equals(TRANSACTIONTYPE_Return)) 
								|| (!isSOTrx() & getTransactionType().equals(TRANSACTIONTYPE_Discount)))								
						{
							if(TrxAmt.signum() >= 0) // Case 1.1 - Transaction Amount is positive
							{
								pls.setAmtAcctCr(TrxAmt);
							}
							else // Case 1.2 -Transaction Amount is negative
							{
								pls.setAmtAcctDr(TrxAmt);
							}

							if(getTransactionType().equals(TRANSACTIONTYPE_SalesPurchase))
								pls.setQty(qtyAmt.negate());
						}
						// Case 2 - Purchase Transaction OR Sales Return or Sales Discount
						if((!isSOTrx() & getTransactionType().equals(TRANSACTIONTYPE_SalesPurchase))
								|| (isSOTrx() & getTransactionType().equals(TRANSACTIONTYPE_Return)) 
								|| (isSOTrx() & getTransactionType().equals(TRANSACTIONTYPE_Discount)))
						{
							if(TrxAmt.signum() >= 0) // Case 2.1 - Transaction Amount is positive
							{
								pls.setAmtAcctDr(TrxAmt);
							}
							else // Case 2.2 -Transaction Amount is negative
							{
								pls.setAmtAcctCr(TrxAmt);
							}
							if(getTransactionType().equals(TRANSACTIONTYPE_SalesPurchase))
								pls.setQty(qtyAmt);
						}						
						pls.save();
					}	
				} // End Product Based
				 
				//Mark Budget Planning Line as Generated
				if((element1Source&&element1Target) || (element2Source&&element2Target))
				{
					updateGenerateStatus(XX_BudgetPlanningLine_ID);					
				}

			}
		}catch (Exception e) {
			e.printStackTrace();			
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		String update = "UPDATE XX_BudgetPlanningLinePeriod SET Processed = 'Y' " +
				"WHERE XX_BudgetPlanningLinePeriod_ID IN(" +
				"SELECT lp.XX_BudgetPlanningLinePeriod_ID FROM XX_BudgetPlanningLinePeriod lp " +
				"INNER JOIN XX_BudgetPlanningLine pl ON (lp.XX_BudgetPlanningLine_ID = pl.XX_BudgetPlanningLine_ID) " +
				"INNER JOIN XX_BudgetPlanning bp ON (pl.XX_BudgetPlanning_ID = bp.XX_BudgetPlanning_ID) " +
				"WHERE bp.Processed = 'Y' " +
				"AND lp.Processed = 'N' ) " +
				"AND AD_Client_ID = ? ";
		DB.executeUpdate(get_Trx(), update, getAD_Client_ID());
		// End Get Data
		return DocActionConstants.STATUS_Completed;
	}	
		
	/**
	 * 	Set XX_BudgetPlanning_ID - Callout
	 *  
	 *	@param oldXX_BudgetPlanning_ID old XX_BudgetPlanning_ID
	 *	@param newXX_BudgetPlanning_ID new XX_BudgetPlanning_ID
	 *	@param windowNo window no
	 */
	@UICallout public void setXX_BudgetPlanning_ID(String oldXX_BudgetPlanning_ID,
			String newXX_BudgetPlanning_ID, int windowNo) throws Exception
	{
		if((newXX_BudgetPlanning_ID == null)||(newXX_BudgetPlanning_ID.length()==0))
			return;
		
		MBudgetPlanning bplan = new MBudgetPlanning(getCtx(), Integer.parseInt(newXX_BudgetPlanning_ID), get_Trx());
		setPlanningPeriod(bplan.getPlanningPeriod());
		setGL_Budget_ID(bplan.getGL_Budget_ID());
	}
	
	/** Document Action Methods							*/	
	
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

	/** 
	 * Check if account with selected segment is exists in Budget Planning Line. 
	 * @return XX_BudgetPlanningLine_ID
	 */
	private int checkAccount(int Account_ID, int M_Product_ID, Integer Element1Value, Integer Element2Value)
	{
		int XX_BudgetPlanningLine_ID = 0;
		
		StringBuilder sql = new StringBuilder("SELECT XX_BudgetPlanningLine_ID " +
				"FROM XX_BudgetPlanningLine " +
				"WHERE XX_BudgetPlanning_ID = ? " +
				"AND Account_ID = ? ");
		
		if(M_Product_ID > 0)
		{
			sql.append("AND M_Product_ID = " + M_Product_ID + " ");
		}
		else
		{
			sql.append("AND (M_Product_ID = 0 OR M_Product_ID IS NULL) ");
		}
		
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
		if(getM_Product_ID()!=0)
		{
			sql.append("AND M_Product_ID = " + p_M_Product_ID + " ");
		}
		else
		{
			sql.append("AND (M_Product_ID = 0 OR M_Product_ID IS NULL) ");
		}
		if(getC_SalesRegion_ID()!=0)
		{
			sql.append("AND C_SalesRegion_ID = " + p_C_SalesRegion_ID + " ");
		}
		else
		{
			sql.append("AND (C_SalesRegion_ID = 0 OR C_SalesRegion_ID IS NULL) ");
		}
		
		// For User Element
//		if(Element1Value > 0 && element1Target)
//		{
//			sql.append("AND " + element1 + " = " + Element1Value + " ");
//		}
//		else if(element1Target)
//		{
//			sql.append("AND (" + element1 + " = 0 OR " + element1 + " IS NULL) ");
//		}
//		if(Element2Value > 0 && element2Target)
//		{
//			sql.append("AND " + element2 + " = " + Element2Value + " ");
//		}
//		else if(element2Target)
//		{
//			sql.append("AND (" + element2 + " = 0 OR " + element2 + " IS NULL) ");
//		}
		
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_BudgetPlanning_ID());
			pstmt.setInt(2, Account_ID);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				XX_BudgetPlanningLine_ID = rs.getInt("XX_BudgetPlanningLine_ID");				
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
			
			bpl.setXX_BudgetPlanning_ID(getXX_BudgetPlanning_ID());
			bpl.setAD_Org_ID(getAD_Org_ID());
			bpl.setAccount_ID(Account_ID);
			if(M_Product_ID > 0)
			{
				bpl.setM_Product_ID(M_Product_ID);
			}
			bpl.setIsGenerated(true);
			bpl.setC_Activity_ID(p_C_Activity_ID);
			bpl.setC_Project_ID(p_C_Project_ID);
			bpl.setC_Campaign_ID(p_C_Campaign_ID);
			bpl.setM_Product_ID(M_Product_ID);
			bpl.setC_SalesRegion_ID(p_C_SalesRegion_ID);
			bpl.setAmtAcctDr(Env.ZERO);
			bpl.setAmtAcctCr(Env.ZERO);
			
			if(element1Source && element1Target && elementValue1 > 0)
			{
				bpl.setElement_X1(elementValue1);
			}
			if(element2Source && element2Target && elementValue2 > 0)
			{
				bpl.setElement_X2(elementValue2);
			}
			
			if(bpl.save())
			{
				XX_BudgetPlanningLine_ID = bpl.getXX_BudgetPlanningLine_ID();
			}		
			
			// Update XX_BudgetPlanningLine with User Element
			GeneralEnhancementUtils.updateElementValue("XX_BudgetPlanningLine", XX_BudgetPlanningLine_ID, element1Target, element1, elementValue1, element2Target, element2, elementValue2, get_Trx());
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
				XX_BudgetPlanningLinePeriod_ID = rs.getInt("XX_BudgetPlanningLinePeriod_ID");
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
			ResultSet rs = null;
			
			try{
				rs = pstmt.executeQuery();
				rs.close();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		}
	}
	
	/**
	 * Get Conversion Rate from Transaction Currency to Budget Currency
	 * @return
	 */
	private BigDecimal getConversionRate()
	{
		C_Currency_Acct_ID = BudgetUtils.getBudgetCurrencyID(getCtx(), getAD_Client_ID(), get_Trx());				
		if(C_Currency_Acct_ID != getC_Currency_ID())
		{
			return MConversionRate.getRate(getC_Currency_ID(), C_Currency_Acct_ID, 
					getDocumentDate(), BudgetUtils.getConversionTypeID(getAD_Client_ID(), get_Trx()), getAD_Client_ID(), getAD_Org_ID());					
		}
		return Env.ONE;
	}
	
	
}
