/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import rsge.utils.CalculateFirstYearDepreciation;

import org.compiere.api.UICallout;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_InvestmentBudget;
import rsge.po.X_XX_InvestmentBudgetLineItem;
import rsge.utils.BudgetUtils;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY R
 *
 */
public class MInvestmentBudgetLineItem extends X_XX_InvestmentBudgetLineItem {
	
	/** Investment Budget Line Item Depreciation	*/
	private int					XX_InvLineItemDepreciation_ID = 0;
	
	/** Logger for class MInvestmentBudgetLineItem */
	private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MInvestmentBudgetLineItem.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_InvestmentBudgetLineItem_ID
	 * @param trx
	 */
	public MInvestmentBudgetLineItem(Ctx ctx,
			int XX_InvestmentBudgetLineItem_ID, Trx trx) {
		super(ctx, XX_InvestmentBudgetLineItem_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MInvestmentBudgetLineItem(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@UICallout 
	/**
	 * Set Total Amt
	 */
	public void setTotalAmt(String oldTotalAmt,
			String newTotalAmt, int windowNo) throws Exception
	{
		if ((newTotalAmt == null) || (newTotalAmt.length() == 0))
			return;
		BigDecimal TotalAmt = new BigDecimal(newTotalAmt);
		if (TotalAmt.compareTo(BigDecimal.ZERO)==0)
			return;
		
		// Get Budget info currency
		int budgetCurrency_ID = BudgetUtils.getBudgetCurrencyID(getCtx(), getAD_Client_ID(), get_Trx());
		
		if(budgetCurrency_ID != getC_Currency_ID()) // Convert Total Amount to Budget Currency
		{
			MInvestmentBudgetLine ibl = new MInvestmentBudgetLine(getCtx(), getXX_InvestmentBudgetLine_ID(), get_Trx());
			MInvestmentBudget ib = new MInvestmentBudget(getCtx(), ibl.getXX_InvestmentBudget_ID(), get_Trx());
			BigDecimal total = MConversionRate.convert(getCtx(), TotalAmt, getC_Currency_ID(), budgetCurrency_ID, ib.getDateDoc(), BudgetUtils.getConversionTypeID(getAD_Client_ID(), get_Trx()), getAD_Client_ID(), getAD_Org_ID());
			
			MCurrency currency = new MCurrency(getCtx(), budgetCurrency_ID, get_Trx());
			total = total.setScale(currency.getStdPrecision(), RoundingMode.HALF_UP);
			setConvertedAmt(total);
		}
		else // Set Converted Amount = TotalAmt
		{
			setConvertedAmt(getTotalAmt());
		}
	}
	
	/**
	 * Set Price Entered
	 * @param oldPriceEntered
	 * @param newPriceEntered
	 * @param windowNo
	 * @throws Exception
	 */
	public void setPriceEntered(String oldPriceEntered,
			String newPriceEntered, int windowNo) throws Exception
	{
		if ((newPriceEntered == null) || (newPriceEntered.length() == 0))
			return;
		BigDecimal PriceEntered = new BigDecimal(newPriceEntered);
		if (PriceEntered.compareTo(BigDecimal.ZERO)==0)
			return;
		
		if (getQty()==0)
			return;
		
		setTotalAmt(GeneralEnhancementUtils.calculateTotalAmt(getCtx(), PriceEntered, getQty(), getC_Currency_ID(), get_Trx()));
	}
	
	public void setQty(String oldQty,
			String newQty, int windowNo) throws Exception
	{
		if ((newQty == null) || (newQty.length() == 0))
			return;
		Integer Qty = Integer.valueOf(newQty);
		if (getQty()==0)
			return;
		
		if (getPriceEntered().compareTo(BigDecimal.ZERO)==0)
			return;
		
		setTotalAmt(GeneralEnhancementUtils.calculateTotalAmt(getCtx(), getPriceEntered(), Qty, getC_Currency_ID(), get_Trx()));
	}
	
	@Override
	protected boolean afterDelete(boolean success) {
		// TODO Auto-generated method stub
		return updateParent();
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		// Calculate Depreciation if Asset Group is Depreciated
		MInvestmentBudgetLine ibl = new MInvestmentBudgetLine(getCtx(), getXX_InvestmentBudgetLine_ID(), get_Trx());
		
		if(ibl.isDepreciated())
		{
			boolean recordExists = false;
			
			String sql = "SELECT XX_InvLineItemDepreciation_ID " +
					"FROM XX_InvLineItemDepreciation " +
					"WHERE XX_InvestmentBudgetLineItem_ID = ? ";
			
			PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
			ResultSet rs = null;
			
			try{
				pstmt.setInt(1, getXX_InvestmentBudgetLineItem_ID());
				
				rs = pstmt.executeQuery();
				
				if(rs.next())
				{
					recordExists = true;
					XX_InvLineItemDepreciation_ID = rs.getInt(1);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				DB.closeResultSet(rs);
				DB.closeStatement(pstmt);
			}			
			calculateDepreciation(getConvertedAmt(), recordExists, XX_InvLineItemDepreciation_ID);
		} // End Calculate Depreciation
		
		
		return updateParent();
	}

	/**
	 * Update Investment Budget Line
	 */
	private boolean updateParent()
	{
		// Update Total Amount
		boolean updateAmt = updateParentAmt();
		if(!updateAmt)
			log.warning("Cannot update parent's amount");
		
		// Update Quantity
		boolean updateQty = updateParentQty();
		if(!updateQty)
			log.warning("Cannot update parent's quantity");
		
		return true;
	}
	
	private boolean updateParentAmt()
	{
		// Get Sum Data
		BigDecimal period1 = BigDecimal.ZERO;
		BigDecimal period2 = BigDecimal.ZERO;
		BigDecimal period3 = BigDecimal.ZERO;
		BigDecimal period4 = BigDecimal.ZERO;
		BigDecimal period5 = BigDecimal.ZERO;
		BigDecimal period6 = BigDecimal.ZERO;
		BigDecimal period7 = BigDecimal.ZERO;
		BigDecimal period8 = BigDecimal.ZERO;
		BigDecimal period9 = BigDecimal.ZERO;
		BigDecimal period10 = BigDecimal.ZERO;
		BigDecimal period11 = BigDecimal.ZERO;
		BigDecimal period12 = BigDecimal.ZERO;
		
		BigDecimal totalAmt = BigDecimal.ZERO;
		
		StringBuilder getData = new StringBuilder();
		getData.append("SELECT COALESCE(SUM(ibli1.ConvertedAmt),0) AS Period1, COALESCE(SUM(ibli2.ConvertedAmt),0) AS Period2, COALESCE(SUM(ibli3.ConvertedAmt),0) AS Period3, COALESCE(SUM(ibli4.ConvertedAmt),0) AS Period4, " +
				"COALESCE(SUM(ibli5.ConvertedAmt),0) AS Period5, COALESCE(SUM(ibli6.ConvertedAmt),0) AS Period6, COALESCE(SUM(ibli7.ConvertedAmt),0) AS Period7, COALESCE(SUM(ibli8.ConvertedAmt),0) AS Period8, " +
				"COALESCE(SUM(ibli9.ConvertedAmt),0) AS Period9, COALESCE(SUM(ibli10.ConvertedAmt),0) AS Period10, COALESCE(SUM(ibli11.ConvertedAmt),0) AS Period11, COALESCE(SUM(ibli12.ConvertedAmt),0) AS Period12 " +
				"FROM XX_InvestmentBudgetLineItem ibli " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli1 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli1.XX_InvestmentBudgetLineItem_ID AND ibli1.Period1 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli2 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli2.XX_InvestmentBudgetLineItem_ID AND ibli2.Period2 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli3 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli3.XX_InvestmentBudgetLineItem_ID AND ibli3.Period3 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli4 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli4.XX_InvestmentBudgetLineItem_ID AND ibli4.Period4 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli5 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli5.XX_InvestmentBudgetLineItem_ID AND ibli5.Period5 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli6 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli6.XX_InvestmentBudgetLineItem_ID AND ibli6.Period6 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli7 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli7.XX_InvestmentBudgetLineItem_ID AND ibli7.Period7 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli8 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli8.XX_InvestmentBudgetLineItem_ID AND ibli8.Period8 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli9 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli9.XX_InvestmentBudgetLineItem_ID AND ibli9.Period9 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli10 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli10.XX_InvestmentBudgetLineItem_ID AND ibli10.Period10 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli11 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli11.XX_InvestmentBudgetLineItem_ID AND ibli11.Period11 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli12 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli12.XX_InvestmentBudgetLineItem_ID AND ibli12.Period12 = 'Y') " +
				"WHERE ibli.XX_InvestmentBudgetLine_ID = ? ");
		
		PreparedStatement pstmt = DB.prepareStatement(getData.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_InvestmentBudgetLine_ID());
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				period1 = rs.getBigDecimal(1);
				period2 = rs.getBigDecimal(2);
				period3 = rs.getBigDecimal(3);
				period4 = rs.getBigDecimal(4);
				period5 = rs.getBigDecimal(5);
				period6 = rs.getBigDecimal(6);
				period7 = rs.getBigDecimal(7);
				period8 = rs.getBigDecimal(8);
				period9 = rs.getBigDecimal(9);
				period10 = rs.getBigDecimal(10);
				period11 = rs.getBigDecimal(11);
				period12 = rs.getBigDecimal(12);
				
				totalAmt = period1.add(period2).add(period3).add(period4).add(period5).add(period6).add(period7)
				.add(period8).add(period9).add(period10).add(period11).add(period12);
			}
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		// Update Parent's Total Amount
		StringBuilder sql = new StringBuilder();
		
		sql.append("UPDATE XX_InvestmentBudgetLine " +
				"SET Period1 = ?, Period2 = ?, Period3 = ?, Period4 = ?, Period5 = ?, Period6 = ?, Period7 = ?, " +
				"Period8 = ?, Period9 = ?, Period10 = ?, Period11 = ?, Period12 = ?, TotalAmt = ? " +
				"WHERE XX_InvestmentBudgetLine_ID = ? ");
		
		int no = DB.executeUpdate(get_Trx(), sql.toString(), new Object[]{period1, period2, period3, period4, period5, period6, period7, period8, 
			period9, period10, period11, period12, totalAmt, getXX_InvestmentBudgetLine_ID()});
		if (no != 1)
			log.warning("(1) #" + no);
		
		return no == 1;
		
	}
	private boolean updateParentQty()
	{
		// Get Sum Data
		BigDecimal period1 = BigDecimal.ZERO;
		BigDecimal period2 = BigDecimal.ZERO;
		BigDecimal period3 = BigDecimal.ZERO;
		BigDecimal period4 = BigDecimal.ZERO;
		BigDecimal period5 = BigDecimal.ZERO;
		BigDecimal period6 = BigDecimal.ZERO;
		BigDecimal period7 = BigDecimal.ZERO;
		BigDecimal period8 = BigDecimal.ZERO;
		BigDecimal period9 = BigDecimal.ZERO;
		BigDecimal period10 = BigDecimal.ZERO;
		BigDecimal period11 = BigDecimal.ZERO;
		BigDecimal period12 = BigDecimal.ZERO;
		
		BigDecimal totalAmt = BigDecimal.ZERO;
		
		StringBuilder getData = new StringBuilder();
		getData.append("SELECT COALESCE(SUM(ibli1.Qty),0) AS Period1, COALESCE(SUM(ibli2.Qty),0) AS Period2, COALESCE(SUM(ibli3.Qty),0) AS Period3, COALESCE(SUM(ibli4.Qty),0) AS Period4, " +
				"COALESCE(SUM(ibli5.Qty),0) AS Period5, COALESCE(SUM(ibli6.Qty),0) AS Period6, COALESCE(SUM(ibli7.Qty),0) AS Period7, COALESCE(SUM(ibli8.Qty),0) AS Period8, " +
				"COALESCE(SUM(ibli9.Qty),0) AS Period9, COALESCE(SUM(ibli10.Qty),0) AS Period10, COALESCE(SUM(ibli11.Qty),0) AS Period11, COALESCE(SUM(ibli12.Qty),0) AS Period12 " +
				"FROM XX_InvestmentBudgetLineItem ibli " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli1 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli1.XX_InvestmentBudgetLineItem_ID AND ibli1.Period1 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli2 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli2.XX_InvestmentBudgetLineItem_ID AND ibli2.Period2 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli3 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli3.XX_InvestmentBudgetLineItem_ID AND ibli3.Period3 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli4 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli4.XX_InvestmentBudgetLineItem_ID AND ibli4.Period4 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli5 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli5.XX_InvestmentBudgetLineItem_ID AND ibli5.Period5 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli6 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli6.XX_InvestmentBudgetLineItem_ID AND ibli6.Period6 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli7 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli7.XX_InvestmentBudgetLineItem_ID AND ibli7.Period7 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli8 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli8.XX_InvestmentBudgetLineItem_ID AND ibli8.Period8 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli9 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli9.XX_InvestmentBudgetLineItem_ID AND ibli9.Period9 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli10 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli10.XX_InvestmentBudgetLineItem_ID AND ibli10.Period10 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli11 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli11.XX_InvestmentBudgetLineItem_ID AND ibli11.Period11 = 'Y') " +
				"LEFT OUTER JOIN XX_InvestmentBudgetLineItem ibli12 ON (ibli.XX_InvestmentBudgetLineItem_ID = ibli12.XX_InvestmentBudgetLineItem_ID AND ibli12.Period12 = 'Y') " +
				"WHERE ibli.XX_InvestmentBudgetLine_ID = ? ");
		
		PreparedStatement pstmt = DB.prepareStatement(getData.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_InvestmentBudgetLine_ID());
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				period1 = rs.getBigDecimal(1);
				period2 = rs.getBigDecimal(2);
				period3 = rs.getBigDecimal(3);
				period4 = rs.getBigDecimal(4);
				period5 = rs.getBigDecimal(5);
				period6 = rs.getBigDecimal(6);
				period7 = rs.getBigDecimal(7);
				period8 = rs.getBigDecimal(8);
				period9 = rs.getBigDecimal(9);
				period10 = rs.getBigDecimal(10);
				period11 = rs.getBigDecimal(11);
				period12 = rs.getBigDecimal(12);
				
				totalAmt = period1.add(period2).add(period3).add(period4).add(period5).add(period6).add(period7)
				.add(period8).add(period9).add(period10).add(period11).add(period12);
			}
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE XX_InvestmentBudgetLine " +
				"SET QtyPeriod1 = ?, QtyPeriod2 = ?, QtyPeriod3 = ?, QtyPeriod4 = ?, QtyPeriod5 = ?, QtyPeriod6 = ?, QtyPeriod7 = ?, " +
				"QtyPeriod8 = ?, QtyPeriod9 = ?, QtyPeriod10 = ?, QtyPeriod11 = ?, QtyPeriod12 = ?, TotalQty = ? " +				
				"WHERE XX_InvestmentBudgetLine_ID = ? ");
		
		int no = DB.executeUpdate(get_Trx(), sql.toString(), new Object[]{period1, period2, period3, period4, period5, period6, period7, period8, 
			period9, period10, period11, period12, totalAmt, getXX_InvestmentBudgetLine_ID()});
		if (no != 1)
			log.warning("(2) #" + no);
		return no == 1;
	}	
	
	private void calculateDepreciation(BigDecimal assetCost, boolean recordExists, int XX_InvLineItemDepreciation_ID)
	{
		MInvestmentBudgetLine ibl = new MInvestmentBudgetLine(getCtx(), getXX_InvestmentBudgetLine_ID(), get_Trx());
		MAssetGroup assetGroup = new MAssetGroup(getCtx(), ibl.getA_Asset_Group_ID(), get_Trx());
		
		CalculateFirstYearDepreciation depreciation = new CalculateFirstYearDepreciation(getCtx(), assetCost, assetGroup.getBudgetDepreciationMethod(), 
				assetGroup.getBudgetAssetLifeYear(), assetGroup.getBudgetAssetLifeMonth(), get_Trx());
		
		BigDecimal depreciationAmt = depreciation.getDepreciationAmount();
		
		MCurrency currency = new MCurrency(getCtx(), BudgetUtils.getBudgetCurrencyID(getCtx(), getAD_Client_ID(), get_Trx()), get_Trx());
		
		int numberOfPeriod = 0;
		
		MInvestmentBudget ib = new MInvestmentBudget(getCtx(), ibl.getXX_InvestmentBudget_ID(), get_Trx());
		if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly))
			{
				numberOfPeriod = 12;
			}
		else if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Quarterly))
			{
				numberOfPeriod = 4;
			}
		else if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Semester))
			{
				numberOfPeriod = 2;
			}

		depreciationAmt = depreciationAmt.divide(BigDecimal.valueOf(numberOfPeriod), currency.getStdPrecision(), RoundingMode.HALF_EVEN);
		
		// Create/Update Investment Budget Line Item Depreciation
		int recordID = 0;
		
		if(recordExists)
		{
			recordID = XX_InvLineItemDepreciation_ID; 
		}

		MInvLineItemDepreciation ilid = new MInvLineItemDepreciation(getCtx(), recordID, get_Trx());
		ilid.setXX_InvestmentBudgetLineItem_ID(getXX_InvestmentBudgetLineItem_ID());
		ilid.setAD_Org_ID(getAD_Org_ID());
		
		//Reset PeriodAmt
		ilid.setPeriod1(BigDecimal.ZERO);
		ilid.setPeriod2(BigDecimal.ZERO);
		ilid.setPeriod3(BigDecimal.ZERO);
		ilid.setPeriod4(BigDecimal.ZERO);
		ilid.setPeriod5(BigDecimal.ZERO);
		ilid.setPeriod6(BigDecimal.ZERO);
		ilid.setPeriod7(BigDecimal.ZERO);
		ilid.setPeriod8(BigDecimal.ZERO);
		ilid.setPeriod9(BigDecimal.ZERO);
		ilid.setPeriod10(BigDecimal.ZERO);
		ilid.setPeriod11(BigDecimal.ZERO);
		ilid.setPeriod12(BigDecimal.ZERO);
		
		if(isPeriod1())
		{
			ilid.setPeriod1(depreciationAmt);
			ilid.setPeriod2(depreciationAmt);
			
			if((ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Quarterly)) 
					|| (ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly)) )
			{
				ilid.setPeriod3(depreciationAmt);
				ilid.setPeriod4(depreciationAmt);
			}
			if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly))
			{
				ilid.setPeriod5(depreciationAmt);
				ilid.setPeriod6(depreciationAmt);
				ilid.setPeriod7(depreciationAmt);
				ilid.setPeriod8(depreciationAmt);
				ilid.setPeriod9(depreciationAmt);
				ilid.setPeriod10(depreciationAmt);
				ilid.setPeriod11(depreciationAmt);
				ilid.setPeriod12(depreciationAmt);
			}
		}
		else if(isPeriod2())
		{
			ilid.setPeriod2(depreciationAmt);
			
			if((ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Quarterly)) 
					|| (ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly)) )
			{
				ilid.setPeriod3(depreciationAmt);
				ilid.setPeriod4(depreciationAmt);
			}
			if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly))
			{
				ilid.setPeriod5(depreciationAmt);
				ilid.setPeriod6(depreciationAmt);
				ilid.setPeriod7(depreciationAmt);
				ilid.setPeriod8(depreciationAmt);
				ilid.setPeriod9(depreciationAmt);
				ilid.setPeriod10(depreciationAmt);
				ilid.setPeriod11(depreciationAmt);
				ilid.setPeriod12(depreciationAmt);
			}
		}
		else if(isPeriod3())
		{
			if((ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Quarterly)) 
					|| (ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly)) )
			{
				ilid.setPeriod3(depreciationAmt);
				ilid.setPeriod4(depreciationAmt);
			}
			if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly))
			{
				ilid.setPeriod5(depreciationAmt);
				ilid.setPeriod6(depreciationAmt);
				ilid.setPeriod7(depreciationAmt);
				ilid.setPeriod8(depreciationAmt);
				ilid.setPeriod9(depreciationAmt);
				ilid.setPeriod10(depreciationAmt);
				ilid.setPeriod11(depreciationAmt);
				ilid.setPeriod12(depreciationAmt);
			}
		}
		else if(isPeriod4())
		{
			if((ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Quarterly)) 
					|| (ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly)) )
			{
				ilid.setPeriod4(depreciationAmt);
			}
			if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly))
			{
				ilid.setPeriod5(depreciationAmt);
				ilid.setPeriod6(depreciationAmt);
				ilid.setPeriod7(depreciationAmt);
				ilid.setPeriod8(depreciationAmt);
				ilid.setPeriod9(depreciationAmt);
				ilid.setPeriod10(depreciationAmt);
				ilid.setPeriod11(depreciationAmt);
				ilid.setPeriod12(depreciationAmt);
			}
		}
		else if(isPeriod5())
		{
			if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly))
			{
				ilid.setPeriod5(depreciationAmt);
				ilid.setPeriod6(depreciationAmt);
				ilid.setPeriod7(depreciationAmt);
				ilid.setPeriod8(depreciationAmt);
				ilid.setPeriod9(depreciationAmt);
				ilid.setPeriod10(depreciationAmt);
				ilid.setPeriod11(depreciationAmt);
				ilid.setPeriod12(depreciationAmt);
			}
		}
		else if(isPeriod6())
		{
			if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly))
			{
				ilid.setPeriod6(depreciationAmt);
				ilid.setPeriod7(depreciationAmt);
				ilid.setPeriod8(depreciationAmt);
				ilid.setPeriod9(depreciationAmt);
				ilid.setPeriod10(depreciationAmt);
				ilid.setPeriod11(depreciationAmt);
				ilid.setPeriod12(depreciationAmt);
			}
		}
		else if(isPeriod7())
		{
			if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly))
			{
				ilid.setPeriod7(depreciationAmt);
				ilid.setPeriod8(depreciationAmt);
				ilid.setPeriod9(depreciationAmt);
				ilid.setPeriod10(depreciationAmt);
				ilid.setPeriod11(depreciationAmt);
				ilid.setPeriod12(depreciationAmt);
			}
		}
		else if(isPeriod8())
		{
			if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly))
			{
				ilid.setPeriod8(depreciationAmt);
				ilid.setPeriod9(depreciationAmt);
				ilid.setPeriod10(depreciationAmt);
				ilid.setPeriod11(depreciationAmt);
				ilid.setPeriod12(depreciationAmt);
			}
		}
		else if(isPeriod9())
		{
			if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly))
			{
				ilid.setPeriod9(depreciationAmt);
				ilid.setPeriod10(depreciationAmt);
				ilid.setPeriod11(depreciationAmt);
				ilid.setPeriod12(depreciationAmt);
			}
		}
		else if(isPeriod10())
		{
			if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly))
			{
				ilid.setPeriod10(depreciationAmt);
				ilid.setPeriod11(depreciationAmt);
				ilid.setPeriod12(depreciationAmt);
			}
		}
		else if(isPeriod11())
		{
			if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly))
			{
				ilid.setPeriod11(depreciationAmt);
				ilid.setPeriod12(depreciationAmt);
			}
		}
		else if(isPeriod12())
		{
			if(ib.getPlanningPeriod().equals(X_XX_InvestmentBudget.PLANNINGPERIOD_Monthly))
			{
				ilid.setPeriod12(depreciationAmt);
			}
		}
		
		if(ilid.save())
		{
			XX_InvLineItemDepreciation_ID = ilid.getXX_InvLineItemDepreciation_ID();
		}
		
	}
	
}
