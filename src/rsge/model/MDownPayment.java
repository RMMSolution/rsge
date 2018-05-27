/**
 * 
 */
package rsge.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.compiere.api.UICallout;
import org.compiere.model.MBankAccount;
import org.compiere.model.MCurrency;
import org.compiere.model.MOrder;
import org.compiere.model.MTax;
import org.compiere.model.X_C_Invoice;
import org.compiere.model.X_C_Payment;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env.QueryParams;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_XX_DownPayment;
import rsge.po.X_XX_DownPaymentPlan;

/**
 * @author Fanny
 *
 */
public class MDownPayment extends X_XX_DownPayment implements DocAction{

    /** Logger for class MDownPayment */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MDownPayment.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_DownPayment_ID
	 * @param trx
	 */
	public MDownPayment(Ctx ctx, int XX_DownPayment_ID, Trx trx) {
		super(ctx, XX_DownPayment_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MDownPayment(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@UICallout public void setC_Order_ID(String oldC_Order_ID, 
			String newC_Order_ID, int windowNo) throws Exception
	{		
		if((newC_Order_ID == null) || (newC_Order_ID.length() == 0))
			return;

		Integer C_Order_ID = Integer.valueOf(newC_Order_ID);
		MOrder order = new MOrder(getCtx(), C_Order_ID, get_Trx());
		
		setC_BPartner_ID(order.getC_BPartner_ID());
		setIsSOTrx(order.isSOTrx());
		setC_Currency_ID(order.getC_Currency_ID());
		setTotalAmt(order.getTotalLines());
		setGrandTotal(order.getGrandTotal());

	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {		
		BigDecimal trxPayment = BigDecimal.ZERO;
		BigDecimal taxPayment = BigDecimal.ZERO;
		MCurrency currency = new MCurrency(getCtx(), getC_Currency_ID(), get_Trx());
		
		// Based on Percentage
		if(isDPPercentage())
		{
			// Reset DPAmount
			setDPAmount(BigDecimal.ZERO);
			BigDecimal multiplier = BigDecimal.ZERO;
			if(getPercentage().signum()>0)
				multiplier = getPercentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
			trxPayment = (getTotalAmt().multiply(multiplier)).setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
			taxPayment = ((getGrandTotal().subtract(getTotalAmt())).multiply(multiplier)).setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);				
		}
		else if(isDPFixedAmt())
		{
			// Reset Percentage
			setPercentage(BigDecimal.ZERO);
			
			// Get Tax Ratio
			System.out.println("OK NOW");
			BigDecimal taxRatio = BigDecimal.ZERO;
			System.out.println("Grand Total " + getGrandTotal() + ", TotalAmt " + getTotalAmt());
			if(getGrandTotal().compareTo(getTotalAmt())!=0)
			{
				System.out.println("HERE");
				taxRatio = getTotalAmt().divide(getGrandTotal(), 4, RoundingMode.HALF_EVEN);
				System.out.println("Tax Ratio "+ taxRatio);
				trxPayment = (getDPAmount().multiply(taxRatio)).setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
				System.out.println("Trx Payment " + trxPayment);
				taxPayment = getDPAmount().subtract(trxPayment);
				System.out.println("Tax Payment " + taxPayment);
			}
			else
			{
				System.out.println("Should be here");
				trxPayment = getDPAmount();
				taxPayment = BigDecimal.ZERO;
			}
		}		
		
		setDPTrxAmount(trxPayment);
		setDPTaxAmount(taxPayment);
		setTotalDPAmount(trxPayment.add(taxPayment));
		
		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public static BigDecimal calculateDPPercentage(MDownPayment dp, MOrder order)
	{
		BigDecimal dpAmt = BigDecimal.ZERO;
		BigDecimal percentage = dp.getPercentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
		dpAmt = order.getGrandTotal().multiply(percentage);		
		return dpAmt;
	}
	
	private MDownPaymentOrder[] getDownPaymentOrder()
	{
		ArrayList<MDownPaymentOrder> list = new ArrayList<MDownPaymentOrder>();
		String sql = "SELECT * FROM XX_DownPaymentOrder " +
				"WHERE XX_DownPayment_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_DownPayment_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				list.add(new MDownPaymentOrder(getCtx(), rs, get_Trx()));				
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		MDownPaymentOrder[] orderList = new MDownPaymentOrder[list.size()];
		list.toArray(orderList);
		return orderList;
	}
	
	@Override
	public boolean approveIt() {
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}	//	approveIt

	@Override
	public boolean closeIt() {
		log.info("closeIt - " + toString());
		return true;
	}	//	closeIt

	int cashBookID = 0;
	int cashID = 0;
	ArrayList<Integer> lines = null;
	
	@Override
	public String completeIt() {
		// Allocate down payment
		if(getTotalAmt().signum()!=0)
		{
			MDownPaymentOrder[] list = getDownPaymentOrder();
			for(MDownPaymentOrder dpo : list)
			{
				MOrder order = new MOrder(getCtx(), dpo.getC_Order_ID(), get_Trx());
				BigDecimal dpAmt = BigDecimal.ZERO;
				BigDecimal reservedAmt = BigDecimal.ZERO;
				if(isDPPercentage())
				{
					dpAmt = calculateDPPercentage(this, order);
				}
				else if(isDPFixedAmt())
				{
					BigDecimal ratio = dpo.getGrandTotal().divide(getGrandTotal(), 4, RoundingMode.HALF_EVEN);
					dpAmt = ratio.multiply(getTotalDPAmount());					
				}
				reservedAmt = dpAmt;
				
				// 
				if(dpo.getMaxAllocationPct().compareTo(BigDecimal.valueOf(100))<0)
					dpAmt = (dpAmt.multiply(dpo.getMaxAllocationPct())).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_EVEN);
				
				// Set Scale based on currency
				MCurrency currency = new MCurrency(getCtx(), getC_Currency_ID(), get_Trx());
				dpAmt = dpAmt.setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);				
				reservedAmt = reservedAmt.setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);				
				dpo.setDPAmount(dpAmt);
				dpo.save();
			}		
		}

		if(lines==null)
			lines = getLines();
		
		if(isGenerateDPInvoice())
			setDPInvoice_ID(generateDPInvoice());

		// Get Lines		
		if(lines.size()!=0)
		{
			for(int i=0; i<lines.size(); i++)
			{
				MDownPaymentPlan dpp = new MDownPaymentPlan(getCtx(), lines.get(i), get_Trx());
				// Create Payment
				if(!dpp.getPaymentRule().equals(X_XX_DownPaymentPlan.PAYMENTRULE_DownPayment))
				{
					StringBuilder description = new StringBuilder();
					if(dpp.getPaymentRule().equals(X_XX_DownPaymentPlan.PAYMENTRULE_Check))
						description.append("Check payment ");
					else
						description.append("Direct deposit payment ");
					description.append("for down payment #" + getDocumentNo());
					MPayment payment = createNonCashPayment(dpp, description.toString());
					dpp.setC_Payment_ID(payment.getC_Payment_ID());
					if(getDPInvoice_ID()!=0) // Allocate Invoice
						payment.setC_Invoice_ID(getDPInvoice_ID());
					else
						payment.setC_Charge_ID(getC_Charge_ID());
					payment.save();

					// Complete document
					DocumentEngine.processIt(payment, X_C_Payment.DOCACTION_Complete);
					payment.save();
				}
				else if(dpp.getPaymentRule().equals(X_XX_DownPaymentPlan.PAYMENTRULE_DownPayment))
				{
					MDownPayment[] dpList = MDownPaymentPlan.unAllocatedDownPayment(getCtx(), getC_BPartner_ID(), get_Trx());
					BigDecimal distAmt = dpp.getAllocatedAmt();
					for(MDownPayment odp : dpList)
					{
						BigDecimal availAmt = odp.getPayAmt().subtract(odp.getAllocatedAmt());
						MOtherDPAllocation oda = new MOtherDPAllocation(getCtx(), 0, get_Trx());
						oda.setClientOrg(dpp);
						oda.setXX_DownPaymentPlan_ID(dpp.getXX_DownPaymentPlan_ID());
						oda.setXX_DownPayment_ID(odp.getXX_DownPayment_ID());
						BigDecimal allocatedAmt = BigDecimal.ZERO;
						if(availAmt.compareTo(distAmt)<=0)
							allocatedAmt = availAmt;
						else
							allocatedAmt = distAmt;
						oda.setDPAmount(distAmt);
						if(oda.save())
							distAmt = distAmt.subtract(allocatedAmt);
						if(distAmt.signum()<=0)
							break;
					}
				
					
				}
				dpp.setProcessed(true);
				dpp.save();
			}
		}		
		
		// Update ReservedAmt
		updateReservedAmt(this);
		return DocActionConstants.STATUS_Completed;
	}
	
	public static boolean updateReservedAmt(MDownPayment dp)
	{
		BigDecimal reservedAmt = MDownPaymentOrder.getDownPaymentReserved(dp).add(MOtherDPAllocation.getDownPaymentReserved(dp));
		String update = "UPDATE XX_DownPayment " +
				"SET ReservedAmt = ? " +
				"WHERE XX_DownPayment_ID = ? ";
		DB.executeUpdate(dp.get_Trx(), update, reservedAmt, dp.getXX_DownPayment_ID());		
		return true;
	}
	
	public static boolean updateDownPayment(MDownPayment dp, BigDecimal dpAmount, BigDecimal totalLines, BigDecimal grandTotal)
	{
		BigDecimal dpAmt = BigDecimal.ZERO;
		BigDecimal dpTaxAmt = BigDecimal.ZERO;
		
		// Calculate DP Amount
		// Get Ratio
		MCurrency currency = new MCurrency(dp.getCtx(), dp.getC_Currency_ID(), dp.get_Trx());
		BigDecimal ratio = BigDecimal.ZERO;
		if(dp.isDPPercentage())
		{
			ratio = dp.getPercentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
			dpAmt = (ratio.multiply(totalLines)).setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
		}
		else if(dp.isDPFixedAmt())
		{
			// Convert to Percentage
			BigDecimal percentage = totalLines.divide(dp.getGrandTotal(), 2, RoundingMode.HALF_EVEN);
			dpAmt = percentage.multiply(dpAmount).setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
		}
		BigDecimal totalDPAmt = BigDecimal.ZERO;		
		if(dp.isDPPercentage())
			totalDPAmt = (ratio.multiply(grandTotal)).setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
		else if(dp.isDPFixedAmt())
			totalDPAmt = dp.getDPAmount();
		dpTaxAmt = totalDPAmt.subtract(dpAmt);
		
		String update = "UPDATE XX_DownPayment " +
				"SET TotalAmt = ?, GrandTotal = ?, DPTrxAmount = ?, DPTaxAmount = ?, TotalDPAmount = ? " +
				"WHERE XX_DownPayment_ID = ? ";
		DB.executeUpdate(dp.get_Trx(), update, totalLines, grandTotal, dpAmt, dpTaxAmt, totalDPAmt, dp.getXX_DownPayment_ID());

		return true;
	}	
	
	private MPayment createNonCashPayment(MDownPaymentPlan plan, String description)
	{ 
		int conversionType = 0;
		if(plan.getC_ConversionType_ID()>0)
			conversionType = plan.getC_ConversionType_ID();
		BigDecimal payAmt = plan.getAllocatedAmt();
		if(plan.getC_Currency_ID()!=getC_Currency_ID())
			payAmt = plan.getConvertedAmt();
		return createPayment(plan.getC_BankAccount_ID(), payAmt, conversionType, plan.getPaymentRule(), description, false);
	}

	private MPayment createPayment(int C_BankAccount_ID, BigDecimal allocatedAmt, int C_ConversionType_ID,
			String paymentRule, String description, boolean completeIt)
	{
		MPayment payment = new MPayment(getCtx(), 0, get_Trx());		
		payment.setIsReceipt(isSOTrx());
		payment.setC_BankAccount_ID(C_BankAccount_ID);
		
		int orgID = 0;
		MBankAccount ba = new MBankAccount(getCtx(), C_BankAccount_ID, get_Trx());
		if(ba.getAD_Org_ID()!=0)
			orgID = ba.getAD_Org_ID();
		else
			orgID = getAD_Org_ID();
		payment.setAD_Org_ID(orgID);
		payment.setDateTrx(getDateTrx());
		payment.setDateAcct(getDateTrx());
		payment.setC_BPartner_ID(getC_BPartner_ID());
		payment.setPayAmt(allocatedAmt);
		payment.setC_Currency_ID(ba.getC_Currency_ID());
		if(C_ConversionType_ID>0)
			payment.setC_ConversionType_ID(C_ConversionType_ID);
		
		// Set Tender Type		
		if(paymentRule.equals(X_XX_DownPaymentPlan.PAYMENTRULE_Check))
			payment.setTenderType(X_C_Payment.TENDERTYPE_Check);
		else if(paymentRule.equals(X_XX_DownPaymentPlan.PAYMENTRULE_DirectDeposit))
			payment.setTenderType(X_C_Payment.TENDERTYPE_DirectDeposit);

		if(description!=null)
			payment.setDescription(description);
		
		payment.setIsPrepayment(true);
		payment.save();
		return payment;
	}
	
	private int generateDPInvoice()
	{
		int invoiceID = 0;
		MInvoice inv = new MInvoice(getCtx(), 0, get_Trx());
		inv.setClientOrg(this);
		inv.setC_BPartner_ID(getC_BPartner_ID());
		inv.setC_Currency_ID(getC_Currency_ID());
		inv.setC_PaymentTerm_ID(getC_PaymentTerm_ID());
		inv.setDateAcct(getDateTrx());
		inv.setDateInvoiced(getDateTrx());
		inv.setIsReturnTrx(false);
		inv.setIsSOTrx(isSOTrx());
		inv.setM_PriceList_ID(getM_PriceList_ID());
		inv.setPaymentRule(getPaymentRule());
		inv.setPOReference(getDocumentNo());
		inv.setSalesRep_ID(getUpdatedBy());
		inv.setC_ConversionType_ID(getC_ConversionType_ID());
		inv.setDescription("Down Payment #" + getDocumentNo());
		int docTypeID = getDocType(isSOTrx());
		inv.setC_DocType_ID(docTypeID);		
		inv.setC_DocTypeTarget_ID(docTypeID);		
		if(inv.save())
			invoiceID = inv.getC_Invoice_ID();
		
		// Generate Line
		MInvoiceLine line = new MInvoiceLine(inv);
		line.setLine(10);
		line.setC_Charge_ID(getC_Charge_ID());
		BigDecimal priceAmt = priceAmt(getC_Tax_ID(), getPayAmt());
		// Calculate Price Amt based on tax
		line.setC_Tax_ID(getC_Tax_ID());
		line.setPrice(priceAmt);
		line.setQtyEntered(BigDecimal.ONE);
		if(line.save())
		{
			DocumentEngine.processIt(inv, X_C_Invoice.DOCACTION_Complete);
			inv.save();
		}
		return invoiceID;
	}
	
	private BigDecimal priceAmt(int C_Tax_ID, BigDecimal payAmt)
	{
		BigDecimal priceAmt = payAmt;
		MTax tax = new MTax(getCtx(), C_Tax_ID, get_Trx());
		if(tax.getRate().compareTo(BigDecimal.ZERO)!=0)
		{
			BigDecimal divider = BigDecimal.valueOf(100).add(tax.getRate());
			priceAmt = (priceAmt.divide(divider, 4, RoundingMode.HALF_EVEN)).multiply(BigDecimal.valueOf(100));
			MCurrency currency = new MCurrency(getCtx(), getC_Currency_ID(), get_Trx());
			priceAmt = priceAmt.setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);			
		}		
		return priceAmt;
	}
	
	private int getDocType(boolean isSOTrx)
	{
		int docTypeID = 0;
		StringBuilder sql = new StringBuilder("SELECT C_DocType_ID FROM C_DocType "
				+ "WHERE IsActive = 'Y' "
				+ "AND AD_Client_ID = ? ");
		if(isSOTrx)
			sql.append("AND DocBaseType = 'ARI' ");
		else
			sql.append("AND DocBaseType = 'API' ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				docTypeID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return docTypeID;
	}
	
	@Override
	public File createPDF() {
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	public File createPDF (File file)
	{
		return null;
	}
	
	@Override
	public BigDecimal getApprovalAmt() {		
		return BigDecimal.ZERO;
	}

	@Override
	public String getDocBaseType() {
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		return 0;
	}

	@Override
	public Timestamp getDocumentDate() {
		return super.getDateTrx();
	}

	@Override
	public String getDocumentInfo() {
		return "";
	}	//	getDocumentInfo

	@Override
	public QueryParams getLineOrgsQueryInfo() {
		return null;
	}

	/**	Process Message 			*/
	private String		m_processMsg = null;

	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		return sb.toString();
	}	//	getSummary

	@Override
	public boolean invalidateIt()
	{
		log.info(toString());
		setDocAction(DOCACTION_Prepare);		
		return true;
	}	//	invalidateIt

	@Override
	public String prepareIt() {
		lines = getLines();
		if(lines.size()==0)
		{
			log.saveError("NoLines", "No Payment Plan");
			return "@NoLines@";
		}
		if(getTotalDPAmount().compareTo(getPayAmt())!=0)
		{
			m_processMsg = Msg.getMsg(getCtx(), "Total down payment must have exact amount as Payment amount");
			return DocActionConstants.STATUS_Invalid;
		}
		
		return DocActionConstants.STATUS_InProgress;
	}	//	prepareIt
	
	@Override
	public boolean reActivateIt() {
		log.info("reActivateIt - " + toString());
		return DocumentEngine.processIt(this, DocActionConstants.ACTION_Reverse_Correct);
	}	//	reActivateIt

	@Override
	public boolean rejectIt() {
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}//	rejectIt

	@Override
	public boolean reverseAccrualIt() {
		log.info("reverseAccrualIt - " + toString());
		return false;
	}//	reverseAccrualIt

	@Override
	public boolean reverseCorrectIt() {
		log.info("reverseCorrectIt - " + toString());
		return false;
	}//	reverseCorrectionIt

	@Override
	public void setProcessMsg(String processMsg) {
		m_processMsg = processMsg;
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}//	unlockIt

	@Override
	public boolean voidIt() {
		log.info("voidIt - " + toString());
		
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return false;
	}//	voidIt

	private ArrayList<Integer> getLines()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		// Get Lines
		String sql = "SELECT XX_DownPaymentPlan_ID " +
				"FROM XX_DownPaymentPlan " +
				"WHERE XX_DownPayment_ID = ? " +
				"ORDER BY C_BankAccount_ID, C_Cash_ID ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_DownPayment_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				list.add(rs.getInt(1));
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public static int getDownPaymentCharge(int C_Order_ID, Trx trx)
	{
		int chargeID = 0;
		// Get Lines
		String sql = "SELECT dp.C_Charge_ID FROM XX_DownPayment dp "
				+ "INNER JOIN XX_DownPaymentOrder dpo ON dp.XX_DownPayment_ID = dpo.XX_DownPayment_ID "
				+ "WHERE dpo.C_Order_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, C_Order_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				chargeID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return chargeID;
	}

}
