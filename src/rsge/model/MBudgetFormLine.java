/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.api.UICallout;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.callout.CalloutBudget;
import rsge.po.X_I_BudgetForm;
import rsge.po.X_XX_BudgetForm;
import rsge.po.X_XX_BudgetFormLine;

/**
 * @author FANNY
 *
 */
public class MBudgetFormLine extends X_XX_BudgetFormLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param XX_BudgetFormLine_ID
	 * @param trx
	 */
	public MBudgetFormLine(Ctx ctx, int XX_BudgetFormLine_ID, Trx trx) {
		super(ctx, XX_BudgetFormLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MBudgetFormLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public MBudgetFormLine(X_I_BudgetForm imp) {
		this(imp.getCtx(), 0, imp.get_Trx());
		
		setAccount_ID(imp.getAccount_ID());
		setM_Product_ID(imp.getM_Product_ID());		
	}
	
	@UICallout public void setM_Product_ID (String oldM_Product_ID,
			String newM_Product_ID, int windowNo) throws Exception
	{		
		if(newM_Product_ID == null || newM_Product_ID.length() == 0)		
			return;

		BigDecimal price = CalloutBudget.getProductPrice(getM_Product_ID(), get_Trx());
		setPrice(price);
	}

	
	/**
	 * 	Set QtyPeriod 1 - Callout
	 *  
	 *	@param oldQtyPeriod1 old QtyPeriod1
	 *	@param newQtyPeriod1 new QtyPeriod1
	 *	@param windowNo window no
	 */
	@UICallout public void setQtyPeriod1 (String oldQtyPeriod1,
			String newQtyPeriod1, int windowNo) throws Exception
	{		
		calculateQty("QtyPeriod1");
	}
	
	/**
	 * 	Set QtyPeriod 2 - Callout
	 *  
	 *	@param oldQtyPeriod2 old QtyPeriod2
	 *	@param newQtyPeriod2 new QtyPeriod2
	 *	@param windowNo window no
	 */
	@UICallout public void setQtyPeriod2 (String oldQtyPeriod1,
			String newQtyPeriod2, int windowNo) throws Exception
	{		
		calculateQty("QtyPeriod2");
	}
	
	/**
	 * 	Set QtyPeriod 3 - Callout
	 *  
	 *	@param oldQtyPeriod3 old QtyPeriod 3
	 *	@param newQtyPeriod3 new QtyPeriod 3
	 *	@param windowNo window no
	 */
	@UICallout public void setQtyPeriod3 (String oldQtyPeriod1,
			String newQtyPeriod3, int windowNo) throws Exception
	{			
		calculateQty("QtyPeriod3");
	}
	
	/**
	 * 	Set QtyPeriod 4 - Callout
	 *  
	 *	@param oldQtyPeriod4 old QtyPeriod 4
	 *	@param newQtyPeriod4 new QtyPeriod 4
	 *	@param windowNo window no
	 */
	@UICallout public void setQtyPeriod4 (String oldQtyPeriod4,
			String newQtyPeriod4, int windowNo) throws Exception
	{		
		calculateQty("QtyPeriod4");
	}
	
	/**
	 * 	Set QtyPeriod 5 - Callout
	 *  
	 *	@param oldQtyPeriod5 old QtyPeriod 5
	 *	@param newQtyPeriod5 new QtyPeriod 5
	 *	@param windowNo window no
	 */
	@UICallout public void setQtyPeriod5 (String oldQtyPeriod5,
			String newQtyPeriod5, int windowNo) throws Exception
	{		
		calculateQty("QtyPeriod5");
	}
	
	/**
	 * 	Set QtyPeriod 6 - Callout
	 *  
	 *	@param oldQtyPeriod6 old QtyPeriod 6
	 *	@param newQtyPeriod6 new QtyPeriod 6
	 *	@param windowNo window no
	 */
	@UICallout public void setQtyPeriod6 (String oldQtyPeriod6,
			String newQtyPeriod6, int windowNo) throws Exception
	{		
		calculateQty("QtyPeriod6");
	}
	
	/**
	 * 	Set QtyPeriod 7 - Callout
	 *  
	 *	@param oldQtyPeriod7 old QtyPeriod 7
	 *	@param newQtyPeriod7 new QtyPeriod 7
	 *	@param windowNo window no
	 */
	@UICallout public void setQtyPeriod7 (String oldQtyPeriod7,
			String newQtyPeriod7, int windowNo) throws Exception
	{		
		calculateQty("QtyPeriod7");
	}
	
	/**
	 * 	Set QtyPeriod 8 - Callout
	 *  
	 *	@param oldQtyPeriod4 old QtyPeriod 8
	 *	@param newQtyPeriod4 new QtyPeriod 8
	 *	@param windowNo window no
	 */
	@UICallout public void setQtyPeriod8 (String oldQtyPeriod8,
			String newQtyPeriod8, int windowNo) throws Exception
	{		
		calculateQty("QtyPeriod8");
	}
	
	/**
	 * 	Set QtyPeriod 9 - Callout
	 *  
	 *	@param oldQtyPeriod9 old QtyPeriod 9
	 *	@param newQtyPeriod9 new QtyPeriod 9
	 *	@param windowNo window no
	 */
	@UICallout public void setQtyPeriod9 (String oldQtyPeriod9,
			String newQtyPeriod9, int windowNo) throws Exception
	{		
		calculateQty("QtyPeriod9");
	}
	
	/**
	 * 	Set QtyPeriod 10 - Callout
	 *  
	 *	@param oldQtyPeriod10 old QtyPeriod 10
	 *	@param newQtyPeriod10 new QtyPeriod 10
	 *	@param windowNo window no
	 */
	@UICallout public void setQtyPeriod10 (String oldQtyPeriod10,
			String newQtyPeriod10, int windowNo) throws Exception
	{		
		calculateQty("QtyPeriod10");
	}
	
	/**
	 * 	Set QtyPeriod 11 - Callout
	 *  
	 *	@param oldQtyPeriod11 old QtyPeriod 11
	 *	@param newQtyPeriod11 new QtyPeriod 11
	 *	@param windowNo window no
	 */
	@UICallout public void setQtyPeriod11 (String oldQtyPeriod11,
			String newQtyPeriod11, int windowNo) throws Exception
	{		
		calculateQty("QtyPeriod11");
	}
	
	/**
	 * 	Set QtyPeriod 12 - Callout
	 *  
	 *	@param oldQtyPeriod12 old QtyPeriod 12
	 *	@param newQtyPeriod12 new QtyPeriod 12
	 *	@param windowNo window no
	 */
	@UICallout public void setQtyPeriod12 (String oldQtyPeriod12,
			String newQtyPeriod12, int windowNo) throws Exception
	{		
		calculateQty("QtyPeriod12");
	}
	
	@UICallout public void setPrice (String oldPrice,
			String newPrice, int windowNo) throws Exception
	{		
		MBudgetForm bf = new MBudgetForm(getCtx(), getXX_BudgetForm_ID(), get_Trx());
		if(!bf.isProduct()
				|| (bf.isProduct() && bf.isUsedProductTree()))
			return;
		
		BigDecimal price = getPrice();
		
		BigDecimal period1 = getQtyPeriod1();
		BigDecimal period1Amt = period1.multiply(price);
		setPeriod1(period1Amt);
		
		BigDecimal period2 = getQtyPeriod2();
		BigDecimal period2Amt = period2.multiply(price);
		setPeriod2(period2Amt);
		
		BigDecimal period3 = getQtyPeriod3();
		BigDecimal period3Amt = period3.multiply(price);
		setPeriod3(period3Amt);
		
		BigDecimal period4 = getQtyPeriod4();
		BigDecimal period4Amt = period4.multiply(price);
		setPeriod4(period4Amt);
		
		BigDecimal period5 = getQtyPeriod5();
		BigDecimal period5Amt = period5.multiply(price);
		setPeriod5(period5Amt);
		
		BigDecimal period6 = getQtyPeriod6();
		BigDecimal period6Amt = period6.multiply(price);
		setPeriod5(period6Amt);
		
		BigDecimal period7 = getQtyPeriod7();
		BigDecimal period7Amt = period7.multiply(price);
		setPeriod7(period7Amt);
		
		BigDecimal period8 = getQtyPeriod8();
		BigDecimal period8Amt = period8.multiply(price);
		setPeriod8(period8Amt);
		
		BigDecimal period9 = getQtyPeriod9();
		BigDecimal period9Amt = period9.multiply(price);
		setPeriod9(period9Amt);
		
		BigDecimal period10 = getQtyPeriod10();
		BigDecimal period10Amt = period10.multiply(price);
		setPeriod10(period10Amt);
		
		BigDecimal period11 = getQtyPeriod11();
		BigDecimal period11Amt = period11.multiply(price);
		setPeriod11(period11Amt);
		
		BigDecimal period12 = getQtyPeriod12();
		BigDecimal period12Amt = period12.multiply(price);
		setPeriod12(period12Amt);
	}

	private void calculateQty (String qtyPeriod)
	{	
		BigDecimal periodAmt = BigDecimal.ZERO;
		BigDecimal price = getPrice();
		
		if(qtyPeriod.equals("QtyPeriod1"))
		{
			periodAmt = getQtyPeriod1().multiply(price);
			setPeriod1(periodAmt);
		}
		else if(qtyPeriod.equals("QtyPeriod2"))
		{
			periodAmt = getQtyPeriod2().multiply(price);
			setPeriod2(periodAmt);
		}
		else if(qtyPeriod.equals("QtyPeriod3"))
		{
			periodAmt = getQtyPeriod3().multiply(price);
			setPeriod3(periodAmt);
		}
		else if(qtyPeriod.equals("QtyPeriod4"))
		{
			periodAmt = getQtyPeriod4().multiply(price);
			setPeriod4(periodAmt);
		}
		else if(qtyPeriod.equals("QtyPeriod5"))
		{
			periodAmt = getQtyPeriod5().multiply(price);
			setPeriod5(periodAmt);
		}
		else if(qtyPeriod.equals("QtyPeriod6"))
		{
			periodAmt = getQtyPeriod6().multiply(price);
			setPeriod6(periodAmt);
		}
		else if(qtyPeriod.equals("QtyPeriod7"))
		{
			periodAmt = getQtyPeriod7().multiply(price);
			setPeriod7(periodAmt);
		}
		else if(qtyPeriod.equals("QtyPeriod8"))
		{
			periodAmt = getQtyPeriod8().multiply(price);
			setPeriod8(periodAmt);
		}
		else if(qtyPeriod.equals("QtyPeriod9"))
		{
			periodAmt = getQtyPeriod9().multiply(price);
			setPeriod9(periodAmt);
		}
		else if(qtyPeriod.equals("QtyPeriod10"))
		{
			periodAmt = getQtyPeriod10().multiply(price);
			setPeriod10(periodAmt);
		}
		else if(qtyPeriod.equals("QtyPeriod11"))
		{
			periodAmt = getQtyPeriod11().multiply(price);
			setPeriod11(periodAmt);
		}
		else if(qtyPeriod.equals("QtyPeriod12"))
		{
			periodAmt = getQtyPeriod12().multiply(price);
			setPeriod12(periodAmt);
		}
	}	

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		// Set Line
		if(newRecord)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+1 FROM XX_BudgetFormLine " +
					"WHERE XX_BudgetForm_ID = ? ";
			PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
			ResultSet rs = null;
			try{
				pstmt.setInt(1, getXX_BudgetForm_ID());
				rs = pstmt.executeQuery();
				if(rs.next())
					setLine(rs.getInt(1));
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		// UPDATE Account ID if it's product
		MBudgetForm bf = new MBudgetForm(getCtx(), getXX_BudgetForm_ID(), get_Trx());
		if(bf.isProduct())
		{
			MBudgetInfo bi = MBudgetInfo.get(getCtx(), getAD_Client_ID(), get_Trx());
			MAcctSchema as = new MAcctSchema(getCtx(), bi.getC_AcctSchema_ID(), get_Trx());
			MProductAcct pa = MProductAcct.get(getM_Product_ID(), as);
			MAccount account = null;
			if(bf.getTransactionType().equals(X_XX_BudgetForm.TRANSACTIONTYPE_SalesPurchase))
			{
				if(bf.isSOTrx())
				{
					account = new MAccount(getCtx(), pa.getP_Revenue_Acct(), get_Trx());
				}
				else
				{
					account = new MAccount(getCtx(), pa.getP_Asset_Acct(), get_Trx());
				}
			}
			else if(bf.getTransactionType().equals(X_XX_BudgetForm.TRANSACTIONTYPE_Discount))
			{
				account = new MAccount(getCtx(), pa.getP_TradeDiscountGrant_Acct(), get_Trx());				
			}
			setAccount_ID(account.getAccount_ID());
		}
		updateTotal();
		return true;
	}
	/* (non-Javadoc)
	 * @see org.compiere.framework.PO#afterSave(boolean, boolean)
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {		
		//Update Parent Total Amount
			updateParentTotalAmt();			
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.framework.PO#afterDelete(boolean)
	 */
	@Override
	protected boolean afterDelete(boolean success) {
		//Update Parent Total Amount
		updateParentTotalAmt();
		return true;

	}
	
	public boolean updateTotal()
	{
		MBudgetForm bf = new MBudgetForm(getCtx(), getXX_BudgetForm_ID(), get_Trx());		
		BigDecimal totalQty = getQtyPeriod1().add(getQtyPeriod2()).add(getQtyPeriod3()).add(getQtyPeriod4());
		BigDecimal totalAmt = getPeriod1().add(getPeriod2()).add(getPeriod3()).add(getPeriod4());

		if(bf.getPlanningPeriod().equals(X_XX_BudgetForm.PLANNINGPERIOD_Semester)
				|| bf.getPlanningPeriod().equals(X_XX_BudgetForm.PLANNINGPERIOD_Monthly))
		{
			totalQty = totalQty.add(getQtyPeriod5()).add(getQtyPeriod6());
			totalAmt = totalAmt.add(getPeriod5()).add(getPeriod6());
		}
		if(bf.getPlanningPeriod().equals(X_XX_BudgetForm.PLANNINGPERIOD_Monthly))
		{
			totalQty = totalQty.add(getQtyPeriod7()).add(getQtyPeriod8()).add(getQtyPeriod9()).add(getQtyPeriod10()).add(getQtyPeriod11()).add(getQtyPeriod12());
			totalAmt = totalAmt.add(getPeriod7()).add(getPeriod8()).add(getPeriod9()).add(getPeriod10()).
					add(getPeriod11()).add(getPeriod12());
		}
		setTotalQty(totalQty);
		setTotalAmt(totalAmt);
		return true;
	}

	/**
	 * Update Total Amount of Budget Form 
	 */
	private void updateParentTotalAmt()
	{		
		// Update Parent
		StringBuilder sql = new StringBuilder("UPDATE XX_BudgetForm b " +
			"SET b.TotalAmt = ");
		
		sql.append("(SELECT COALESCE(SUM(bl.TotalAmt),0) FROM XX_BudgetFormLine bl WHERE bl.XX_BudgetForm_ID = b.XX_BudgetForm_ID) "); 
		
		
		sql.append("WHERE b.XX_BudgetForm_ID = ? "); //1
	
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(),get_Trx());
			try{
					pstmt.setInt(1, getXX_BudgetForm_ID());
					pstmt.executeUpdate();
			}catch (Exception e) {
				e.printStackTrace();
			}			
		
		finally{
			DB.closeStatement(pstmt);
		}

	}
}
