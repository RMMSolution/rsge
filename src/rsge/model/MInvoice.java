/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.compiere.model.MCurrency;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoiceBatch;
import org.compiere.model.MInvoiceBatchLine;
import org.compiere.model.MOrder;
import org.compiere.model.X_I_Invoice;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

import rsge.utils.AmtInWords_IN;
import rsge.utils.SettleInvoiceDownPayment;
import rsge.model.MInvoiceLine;

/**
 * @author Fanny
 *
 */
public class MInvoice extends org.compiere.model.MInvoice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
    /** Logger for class MInvoice */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MInvoice.class);

	
    public MInvoice(Ctx ctx, int C_Invoice_ID, Trx trx) {
		super(ctx, C_Invoice_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MInvoice(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	public MInvoice(MInOut ship, Timestamp invoiceDate) {
		super(ship, invoiceDate);
		// TODO Auto-generated constructor stub
	}

	public MInvoice(MInvoiceBatch batch, MInvoiceBatchLine line) {
		super(batch, line);
		// TODO Auto-generated constructor stub
	}

	public MInvoice(MOrder order, int C_DocTypeTarget_ID, Timestamp invoiceDate) {
		super(order, C_DocTypeTarget_ID, invoiceDate);
		// TODO Auto-generated constructor stub
	}

	public MInvoice(X_I_Invoice imp) {
		super(imp);
		// TODO Auto-generated constructor stub
	}

    /** Set Discount Amount.
    @param DiscountAmt Calculated amount of discount */
    public void setDiscountAmt (java.math.BigDecimal DiscountAmt)
    {
        set_Value ("DiscountAmt", DiscountAmt);
        
    }
    
    /** Get Discount Amount.
    @return Calculated amount of discount */
    public java.math.BigDecimal getDiscountAmt() 
    {
        return get_ValueAsBigDecimal("DiscountAmt");
        
    }
    
    /** Set Print Header.
    @param IsPrintHeader Print header description instead line  */
    public void setIsPrintHeader (boolean IsPrintHeader)
    {
        set_Value ("IsPrintHeader", Boolean.valueOf(IsPrintHeader));
        
    }
    
    /** Get Print Header.
    @return Print header description instead line  */
    public boolean isPrintHeader() 
    {
        return get_ValueAsBoolean("IsPrintHeader");
        
    }
    
    /** Set Invoice Discount.
    @param InvoiceDiscAmt Additional discount in Invoice level */
    public void setInvoiceDiscAmt (java.math.BigDecimal InvoiceDiscAmt)
    {
        set_Value ("InvoiceDiscAmt", InvoiceDiscAmt);
        
    }
    
    /** Get Invoice Discount.
    @return Additional discount in Invoice level */
    public java.math.BigDecimal getInvoiceDiscAmt() 
    {
        return get_ValueAsBigDecimal("InvoiceDiscAmt");
        
    }
    
    /** Set Service Charge.
    @param ServiceChargeAmt Service Charge */
    public void setServiceChargeAmt (java.math.BigDecimal ServiceChargeAmt)
    {
        set_Value ("ServiceChargeAmt", ServiceChargeAmt);
        
    }
    
    /** Get Service Charge.
    @return Service Charge */
    public java.math.BigDecimal getServiceChargeAmt() 
    {
        return get_ValueAsBigDecimal("ServiceChargeAmt");
        
    }
    
    /** Set Total Amount.
    @param TotalAmt Total Amount */
    public void setTotalAmt (java.math.BigDecimal TotalAmt)
    {
        set_Value ("TotalAmt", TotalAmt);
        
    }
    
    /** Get Total Amount.
    @return Total Amount */
    public java.math.BigDecimal getTotalAmt() 
    {
        return get_ValueAsBigDecimal("TotalAmt");
        
    }
    
    /** Set DP Amount.
    @param DPAmount DP Amount */
    public void setDPAmount (java.math.BigDecimal DPAmount)
    {
        set_Value ("DPAmount", DPAmount);
        
    }
    
    /** Get DP Amount.
    @return DP Amount */
    public java.math.BigDecimal getDPAmount() 
    {
        return get_ValueAsBigDecimal("DPAmount");
        
    }
    
    /** Set DP Amount.
    @param RemainingAmt DP Amount */
    public void setRemainingAmt (java.math.BigDecimal RemainingAmt)
    {
        set_Value ("RemainingAmt", RemainingAmt);
        
    }
    
    /** Get DP Amount.
    @return DP Amount */
    public java.math.BigDecimal getRemainingAmt() 
    {
        return get_ValueAsBigDecimal("RemainingAmt");
        
    }

    /** Set RSGESayInWordInd.
    @param RSGESayInWordInd Optional short RSGESayInWordInd of the record */
    public void setRSGESayInWordInd (String RSGESayInWordID)
    {
        set_Value ("RSGESayInWordID", RSGESayInWordID);
        
    }
    
    /** Get RSGESayInWordInd.
    @return Optional short RSGESayInWordID of the record */
    public String getRSGESayInWordID() 
    {
        return (String)get_Value("RSGESayInWordID");
        
    }
    
    @Override
    protected boolean beforeSave(boolean newRecord) {
    	// TODO Auto-generated method stub
       	if(documentExists(newRecord))
       	{
			m_processMsg = "Invoice with this number already exists";
			log.saveError("Error", m_processMsg);
       		return false;
       	}
       	
    	
       	return super.beforeSave(newRecord);
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {
    	// TODO Auto-generated method stub    	
    	if(is_ValueChanged("InvoiceDiscAmt"))
    	{
    		BigDecimal serviceCharge = MInvoiceLine.calculateServiceCharge(getCtx(), getC_Invoice_ID(), get_Trx());
    		recalculateTax(serviceCharge);
    		// Update Grand Total
    		String update = "UPDATE C_Invoice "
    				+ "SET GrandTotal = ? WHERE C_Invoice_ID = ? ";
    		DB.executeUpdate(get_Trx(), update, getGrandTotalAmt(serviceCharge, getInvoiceDiscAmt()), getC_Invoice_ID());
    	}
    	if(isReturnTrx())
    		updateLinePrice();
    	return super.afterSave(newRecord, success);
    }
    
	/**	Process Message 			*/
	private String		m_processMsg = null;
    
    @Override
    public String prepareIt() {
    	// Check if same document already exists 
    	if(documentExists(false))
    	{
			m_processMsg = "Invoice with this number already exists";
			return DocActionConstants.STATUS_Invalid;
    	}
    	// Fix Compiere bug where price of product line is based on Price List. It should be based on returned Invoice
    	if(isReturnTrx())
    		updateLinePrice();
    	
    	if(super.prepareIt().equals(DocActionConstants.STATUS_InProgress))
    	{
    		// Recalculate 
    		recalculateTax(null);
    		// Set Grand Total Amount
    		setGrandTotal(getGrandTotalAmt(null, null));    		
    	}
		return DocActionConstants.STATUS_InProgress;
    }
    
    private boolean documentExists(boolean isNewRecord)
    {
    	boolean isExists = false;
		StringBuilder sql = new StringBuilder("SELECT 1 FROM C_Invoice " +
				"WHERE DocumentNo = ? " +
				"AND C_BPartner_ID = ? " +
				"AND IsSOTrx = ? " +
				"AND IsReturnTrx = ? ");
		if(!isNewRecord)
			sql.append("AND C_Invoice_ID != ? ");
		
    	PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
    	ResultSet rs = null;
    	try{
    		int index = 1;
    		pstmt.setString(index++, getDocumentNo());
    		pstmt.setInt(index++, getC_BPartner_ID());
    		pstmt.setBoolean(index++, isSOTrx());
    		pstmt.setBoolean(index++, isReturnTrx());
    		if(!isNewRecord)
    			pstmt.setInt(index++, getC_Invoice_ID());
    		rs = pstmt.executeQuery();    		
    		if(rs.next())
    			isExists = true;
    		rs.close();
    		pstmt.close();
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return isExists;
    }

    @Override
    public String completeIt() {
    	// Check Down Payment
//    	if(super.completeIt().equals(DocActionConstants.STATUS_Completed))
//    	{
//    		 Update Down Payment (if exists)    		
    		BigDecimal dpAmt = SettleInvoiceDownPayment.settleDownPayment(getCtx(), getC_Invoice_ID(), get_Trx());
    		BigDecimal remains = getGrandTotal().subtract(dpAmt);
    		setRemainingAmt(remains);
    		setDPAmount(dpAmt);
    		if(remains.signum()==0)
    			setIsPaid(true);
//    	}
//    	
//    	if(isSOTrx() && getRemainingAmt().signum()!=0)
//    	{
//    		MCurrency currency = new MCurrency(getCtx(), getC_Currency_ID(), get_Trx());
//    		String sayInWord = AmtInWords_IN.sayNumber(getRemainingAmt().doubleValue(), currency.getDescription());
//    		if(sayInWord!=null)
//    			setRSGESayInWordInd(sayInWord);
//    	}
//    	
//    	// Calculate Budget 
//    	if(!isSOTrx() && !isReturnTrx())
//    		updateBudgetTrx(this);
    	
    	return DocActionConstants.STATUS_Completed;
    }
    
    public static boolean updateBudgetTrx(MInvoice invoice)
    {
    	String sql = "SELECT il.C_InvoiceLine_ID, prl.XX_PurchaseRequisitionLine_ID, ol.C_OrderLine_ID FROM C_InvoiceLine il "
    			+ "INNER JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID "
    			+ "INNER JOIN M_RequisitionLine rl ON ol.C_OrderLine_ID = rl.C_OrderLine_ID "
    			+ "INNER JOIN XX_PurchaseRequisitionLine prl ON rl.M_RequisitionLine_ID = prl.M_RequisitionLine_ID "
    			+ "WHERE il.C_Charge_ID IS NOT NULL "
    			+ "AND il.C_InvoiceLine_ID NOT IN "
    			+ "(SELECT Record_ID FROM XX_BudgetTransactionLine WHERE AD_Table_ID = 318) "
    			+ "AND C_Invoice_ID = ? ";
    	PreparedStatement pstmt = DB.prepareStatement(sql, invoice.get_Trx());
    	ResultSet rs = null;
    	try{
    		pstmt.setInt(1, invoice.getC_Invoice_ID());
    		rs = pstmt.executeQuery();
    		while(rs.next())
    		{
    			MInvoiceLine il = new MInvoiceLine(invoice.getCtx(), rs.getInt(1), invoice.get_Trx());
    			MPurchaseRequisitionLine prl = new MPurchaseRequisitionLine(invoice.getCtx(), rs.getInt(2), invoice.get_Trx());
    			MPurchaseRequisition pr = new MPurchaseRequisition(invoice.getCtx(), prl.getXX_PurchaseRequisition_ID(), invoice.get_Trx());
    			MOrderLine ol = new MOrderLine(invoice.getCtx(), rs.getInt(3), invoice.get_Trx());
    			MBudgetTransactionLine btl = MBudgetTransactionLine.createLine(invoice.getCtx(), 318, il.getC_InvoiceLine_ID(), prl.get_Table_ID(), prl.getXX_PurchaseRequisitionLine_ID(), prl.getAD_Org_ID(), invoice.getDateInvoiced(), prl.getAccount_ID(), 0, prl.getC_Activity_ID(), 0, pr.getC_Campaign_ID(), 0, 0, pr.getC_Project_ID(), 0, 0, 0, prl.getM_Product_ID(), 0, 0, 0, 0, 0, invoice.get_Trx());
    			btl.setUnrealizedAmt((ol.getPriceActual().multiply(il.getQtyInvoiced())).negate());
    			btl.setRealizedAmt(il.getPriceActual().multiply(il.getQtyInvoiced()));
    			btl.save();
    		}
    		rs.close();
    		pstmt.close();
    	}catch (Exception e) {
    		e.printStackTrace();
		}    	
    	return true;
    }    
    
    public BigDecimal getGrandTotalAmt(BigDecimal serviceChargeAmt, BigDecimal invoiceDiscAmt)
    {
    	if(serviceChargeAmt==null)
    		serviceChargeAmt = getServiceChargeAmt();
    	if(invoiceDiscAmt==null)
    		invoiceDiscAmt = getInvoiceDiscAmt();
    	BigDecimal grandTotal = (getTotalLines().add(serviceChargeAmt).subtract(invoiceDiscAmt));
    	String sql = "SELECT COALESCE(SUM(TaxAmt),0) FROM C_InvoiceTax WHERE C_Invoice_ID = ? ";
    	PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
    	ResultSet rs = null;
    	try{
    		pstmt.setInt(1, getC_Invoice_ID());
    		rs = pstmt.executeQuery();
    		if(rs.next())
	    		grandTotal = grandTotal.add(rs.getBigDecimal(1));
    		rs.close();
    		pstmt.close();
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	return grandTotal;
    }
    
	private boolean updateLinePrice()
	{
		String sql = "SELECT il.C_InvoiceLine_ID, il.PriceEntered as PE1, il.PriceActual AS PA1, il.PriceList AS PL1, oil.PriceEntered AS PE2, oil.PriceActual AS PA2, oil.PriceList AS PL2 " +
				"FROM C_InvoiceLine il " +
				"INNER JOIN C_OrderLine ol ON (il.C_OrderLine_ID = ol.C_OrderLine_ID) " +
				"INNER JOIN C_InvoiceLine oil ON (ol.Orig_OrderLine_ID = oil.C_OrderLine_ID) " +
				"WHERE il.C_Invoice_ID = ? " +
				"AND il.PriceEntered <> oil.PriceEntered ";
		
    	PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
    	ResultSet rs = null;
    	try{
    		pstmt.setInt(1, getC_Invoice_ID());
    		rs = pstmt.executeQuery();    		
    		while(rs.next())
    		{
    			MInvoiceLine il = new MInvoiceLine(getCtx(), rs.getInt(1), get_Trx());
    			il.setPriceEntered(rs.getBigDecimal("PE2"));
    			il.setPriceList(rs.getBigDecimal("PL2"));
    			il.setPriceActual(rs.getBigDecimal("PA2"));
    			il.save();
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


		return true;
	}

    
    /**
     * Distribute Invoice Discount Level to Line 
     * @return
     */
    public boolean recalculateTax(BigDecimal serviceCharge)
    {
    	if(serviceCharge==null)
    		serviceCharge = getServiceChargeAmt();
    	String sql = "SELECT * FROM C_InvoiceTax WHERE C_Invoice_ID = ? ";
    	PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
    	ResultSet rs = null;
    	try{
    		pstmt.setInt(1, getC_Invoice_ID());
    		rs = pstmt.executeQuery();
    		while(rs.next())
    		{
    			MInvoiceTax tax = new MInvoiceTax(getCtx(), rs, get_Trx());
    			if (!tax.calculateTaxFromLines(serviceCharge, getInvoiceDiscAmt()))
    				return false;
    			if (!tax.save(get_Trx()))
    				return true;    			
    		}
    		rs.close();
    		pstmt.close();
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    	return true;
    }
    
    /** 
     * Distribute Cost if :
     * - Invoice Line is created from Order Line which also has cost distribution
     * - Invoice Line is not Product (Item)
     *  
     */
    public static void distributeInvoiceCost(MInvoice invoice)
    {
    	ArrayList<MInvoiceLine> iList = new ArrayList<MInvoiceLine>();
    	
    	String sql = "SELECT il.* FROM C_InvoiceLine il " +
    			"WHERE il.C_Invoice_ID = ? " +
    			"AND il.C_OrderLine_ID IN (SELECT cdo.C_OrderLine_ID FROM XX_CostDistributionOrder cdo) ";

    	PreparedStatement pstmt = DB.prepareStatement(sql, invoice.get_Trx());
    	ResultSet rs = null;
    	try{
    		pstmt.setInt(1, invoice.getC_Invoice_ID());
    		rs = pstmt.executeQuery();    		
    		while(rs.next())
    		{
    			MInvoiceLine il = new MInvoiceLine(invoice.getCtx(), rs, invoice.get_Trx());
    			iList.add(il);
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
    	
    	if(iList.size()==0)
    		return;    	

    }
    
	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg

    
}
