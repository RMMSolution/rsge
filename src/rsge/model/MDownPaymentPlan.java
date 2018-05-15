/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.api.UICallout;
import org.compiere.model.MBankAccount;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.MOrder;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

import rsge.po.X_XX_DownPaymentPlan;

/**
 * @author Fanny
 *
 */
public class MDownPaymentPlan extends X_XX_DownPaymentPlan {

    /** Logger for class MDownPaymentPlan */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MDownPaymentPlan.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_DownPaymentPlan_ID
	 * @param trx
	 */
	public MDownPaymentPlan(Ctx ctx, int XX_DownPaymentPlan_ID, Trx trx) {
		super(ctx, XX_DownPaymentPlan_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MDownPaymentPlan(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@UICallout public void setC_Currency_ID(String oldC_Currency_ID, 
			String newC_Currency_ID, int windowNo) throws Exception
	{		
		if((newC_Currency_ID == null) || (newC_Currency_ID.length() == 0))
			return;

		MDownPayment dp = new MDownPayment(getCtx(), getXX_DownPayment_ID(), get_Trx());
		MOrder order = new MOrder(getCtx(), dp.getC_Order_ID(), get_Trx());
		if(getC_Currency_ID()!=order.getC_Currency_ID())
			setIsDifferentCurrency(true);
		else
			setIsDifferentCurrency(false);		
		// Set Conversion Amount
		updateRate(order, dp.getDateTrx());
	}
	
	@UICallout public void setC_ConversionType_ID(String oldC_ConversionType_ID, 
			String newC_ConversionType_ID, int windowNo) throws Exception
	{		
		if((newC_ConversionType_ID == null) || (newC_ConversionType_ID.length() == 0))
			return;

		MDownPayment dp = new MDownPayment(getCtx(), getXX_DownPayment_ID(), get_Trx());
		MOrder order = new MOrder(getCtx(), dp.getC_Order_ID(), get_Trx());
		// Set Conversion Amount
		updateRate(order, dp.getDateTrx());
	}
	
	private void updateRate(MOrder order, Timestamp dateTrx)
	{
		// Set Conversion Amount
		BigDecimal rate = MConversionRate.getRate(order.getC_Currency_ID(), getC_Currency_ID(), dateTrx, getC_ConversionType_ID(), getAD_Client_ID(), getAD_Org_ID());
		if(rate == null)
			rate = BigDecimal.ZERO;		
		setRate(rate);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MDownPayment dp = new MDownPayment(getCtx(), getXX_DownPayment_ID(), get_Trx());
		MOrder order = new MOrder(getCtx(), dp.getC_Order_ID(), get_Trx());
		if(is_ValueChanged("C_Currency_ID") || is_ValueChanged("C_ConversionType_ID"))
		{
			if(getC_Currency_ID()!=order.getC_Currency_ID())
				setIsDifferentCurrency(true);
			else
				setIsDifferentCurrency(false);		
			updateRate(order, dp.getDateTrx());
		}
		// Check if bank is in the same currency as Plan Currency. If not reset it
		if(getC_BankAccount_ID()!=0)
		{
			MBankAccount ba = new MBankAccount(getCtx(), getC_BankAccount_ID(), get_Trx());
			if(ba.getC_Currency_ID()!=getC_Currency_ID())
			{
				log.saveError("Error", Msg.getMsg(getCtx(), "Bank Account Currency is invalid"));
				return false;
			}
		}

		//		// Make sure allocated amount is not exceed total down payment
//		MDownPayment dp = new MDownPayment(getCtx(), getXX_DownPayment_ID(), get_Trx());
//		MOrder order = new MOrder(getCtx(), dp.getC_Order_ID(), get_Trx());
//		// Set Different Currency
//		if(getC_Currency_ID()!=order.getC_Currency_ID())
//			setIsDifferentCurrency(true);
//		else
//			setIsDifferentCurrency(false);			

		BigDecimal totalAllocation = BigDecimal.ZERO;
		BigDecimal allocationAmt = BigDecimal.ZERO;
		
		if(dp.isSeparateTaxPayment())
		{
			// Check Trx and Tax Payment
			totalAllocation = getAllocatedPayment(isTrxPayment(), isTaxPayment());
			if(isTrxPayment())
			{
				if(dp.getDPTrxAmount().compareTo(totalAllocation.add(getAllocatedAmt()))<0)
				{
					allocationAmt = dp.getDPTrxAmount().subtract(totalAllocation);
				}
			}
			else if(isTaxPayment())
			{
				if(dp.getDPTaxAmount().compareTo(totalAllocation.add(getAllocatedAmt()))<0)
				{
					allocationAmt = dp.getDPTaxAmount().subtract(totalAllocation);
				}
			}
			else if(!isTrxPayment() && !isTaxPayment())
			{
				log.saveError("Error", Msg.getMsg(getCtx(), "TrxPayment or Tax Payment must be select"));
				return false;
			}
		}
		else
		{
//			totalAllocation = getAllocatedPayment(true, true);
//			if(dp.getTotalDPAmount().compareTo(totalAllocation.add(getAllocatedAmt()))<0)
//			{
//				allocationAmt = dp.getTotalDPAmount().subtract(totalAllocation);
//			}
		}		
		
		if(allocationAmt.compareTo(BigDecimal.ZERO)>0)
			setAllocatedAmt(allocationAmt);		

		// Set Converted Amount
		BigDecimal convertedAmt = getAllocatedAmt();
		if(getRate().compareTo(BigDecimal.ZERO)>0)
		{
			MCurrency currency = new MCurrency(getCtx(), getC_Currency_ID(), get_Trx());
			convertedAmt = getAllocatedAmt().multiply(getRate()).setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);			
		}
		setConvertedAmt(convertedAmt);
		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		BigDecimal payAmt = BigDecimal.ZERO;
		String sql = "SELECT COALESCE(SUM(ConvertedAmt),0) " +
				"FROM XX_DownPaymentPlan " +
				"WHERE XX_DownPayment_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_DownPayment_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				payAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		String update = "UPDATE XX_DownPayment SET PayAmt = ? WHERE XX_DownPayment_ID = ? ";
		DB.executeUpdate(get_Trx(), update, payAmt, getXX_DownPayment_ID());

		return true;
	}
	
	private BigDecimal getAllocatedPayment(boolean isTrxPayment, boolean isTaxPayment)
	{
		BigDecimal payment =  BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(AllocatedAmt),0) " +
				"FROM XX_DownPaymentPlan " +
				"WHERE XX_DownPayment_ID = ? " +
				"AND XX_DownPaymentPlan_ID NOT IN (?) ");
		
		if(isTrxPayment && !isTaxPayment)
		{
			sql.append("AND IsTrxPayment = 'Y' ");
		}
		else if(!isTrxPayment && isTaxPayment)
		{
			sql.append("AND IsTaxPayment = 'Y' ");			
		}
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_DownPayment_ID());
			pstmt.setInt(2, getXX_DownPaymentPlan_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				payment = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Add payment to allocation amount
		return payment;
	}

}
