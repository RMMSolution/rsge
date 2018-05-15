/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import rsge.model.MInvoiceTax;
import rsge.model.MPriceList;

import org.compiere.model.MTax;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

/**
 * @author Fanny
 *
 */
public class MInvoiceLine extends org.compiere.model.MInvoiceLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /** Logger for class MInvoiceLine */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MInvoiceLine.class);

	/**
	 * @param ctx
	 * @param C_InvoiceLine_ID
	 * @param trx
	 */
	public MInvoiceLine(Ctx ctx, int C_InvoiceLine_ID, Trx trx) {
		super(ctx, C_InvoiceLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MInvoiceLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public MInvoiceLine(MInvoice invoice, int p_X_I_Invoice_ID) {
		super(invoice, p_X_I_Invoice_ID);
		// TODO Auto-generated constructor stub
	}

	public MInvoiceLine(MInvoice invoice) {
		super(invoice);
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
    
    /** Set Line Discount.
    @param LineDiscountAmt Line Discount Amount */
    public void setLineDiscountAmt (java.math.BigDecimal LineDiscountAmt)
    {
        set_Value ("LineDiscountAmt", LineDiscountAmt);
        
    }
    
    /** Get Line Discount.
    @return Line Discount Amount */
    public java.math.BigDecimal getLineDiscountAmt() 
    {
        return get_ValueAsBigDecimal("LineDiscountAmt");
        
    }
    
    /** Set Program Discount.
    @param ProgramDiscountAmt Discount based on specific program */
    public void setProgramDiscountAmt (java.math.BigDecimal ProgramDiscountAmt)
    {
        set_Value ("ProgramDiscountAmt", ProgramDiscountAmt);
        
    }
    
    /** Get Program Discount.
    @return Discount based on specific program */
    public java.math.BigDecimal getProgramDiscountAmt() 
    {
        return get_ValueAsBigDecimal("ProgramDiscountAmt");
        
    }
    
    @Override
    protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
//		BigDecimal disc = BigDecimal.ZERO;
//		MInvoice invoice = new MInvoice(getCtx(), getC_Invoice_ID(), get_Trx());

//		if(invoice.isSOTrx() && !invoice.isReturnTrx())
//		{
//			if(getQtyInvoiced().signum()<=0)
//			{
//				setDiscountAmt(BigDecimal.ZERO);
//				setLineDiscountAmt(BigDecimal.ZERO);
//				setProgramDiscountAmt(BigDecimal.ZERO);
//			}
//			else if(is_ValueChanged("DiscountAmt") || is_ValueChanged("LineDiscountAmt") || is_ValueChanged("ProgramDiscountAmt"))
//			{				
//				// Get Standard Price
//				MPriceList pl = new MPriceList(getCtx(), invoice.getM_PriceList_ID(), get_Trx());			
//				
//				// Actual Price
//				BigDecimal totalDisc = getLineDiscountAmt().add(getDiscountAmt()).add(getProgramDiscountAmt());
//				BigDecimal unitDiscount = totalDisc.divide(getQtyInvoiced(), pl.getStandardPrecision(), RoundingMode.HALF_EVEN);
//				BigDecimal uomDiscount = getQtyInvoiced().divide(getQtyEntered(), 2, RoundingMode.HALF_EVEN);
//				setPriceActual(getPriceList().subtract(unitDiscount));				
//				setPriceEntered(getPriceActual().multiply(uomDiscount));					
//			}
//			else if(is_ValueChanged("PriceEntered") || is_ValueChanged("QtyEntered") || is_ValueChanged("Discount")
//					|| is_ValueChanged("PriceList"))
//			{
//				disc = getPriceList().subtract(getPriceActual());			
//				setLineDiscountAmt(disc.multiply(getQtyInvoiced()));
//				setDiscountAmt(BigDecimal.ZERO);
//				setProgramDiscountAmt(BigDecimal.ZERO);			
//			}			
//		}
		
		// Set AD_OrgTrx_ID based on source documents
		if(getC_OrderLine_ID()!=0)
		{
			MOrderLine ol = new MOrderLine(getCtx(), getC_OrderLine_ID(), get_Trx());
			setAD_OrgTrx_ID(ol.getAD_Org_ID());
		}
		else if(getM_InOutLine_ID()!=0)
		{
			MInOutLine iol = new MInOutLine(getCtx(), getM_InOutLine_ID(), get_Trx());
			MOrderLine ol = new MOrderLine(getCtx(), iol.getC_OrderLine_ID(), get_Trx());
			if(ol!=null)
				setAD_OrgTrx_ID(ol.getAD_Org_ID());
		}
			 
    	return super.beforeSave(newRecord);
    }
    
    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {
    	// TODO Auto-generated method stub
//    	if(super.afterSave(newRecord, success))
//    	{
//    		updateInvoiceDiscount(this);
//    	}
		if (!isProcessed () && !getCtx().isBatchMode())
		{
			if (!newRecord && is_ValueChanged("C_Tax_ID"))
			{
				//	Recalculate Tax for old Tax
				MInvoiceTax tax = MInvoiceTax.get(getCtx(), getC_Invoice_ID(), getC_Tax_ID(), getPrecision(), get_Trx(), getAD_Client_ID(), getAD_Org_ID(), isTaxIncluded());
				if (tax != null)
				{
					if (!tax.calculateTaxFromLines(null, null))
						return false;
					if (!tax.save(get_Trx()))
						return true;
				}
			}
			return updateHeaderTax();
		}

    	return true;
    }
    
	private static void updateInvoiceDiscount(MInvoiceLine line)
	{
		// Get Total Lines
		BigDecimal totalAmt = BigDecimal.ZERO;
		BigDecimal totalDisc = BigDecimal.ZERO;
		String sql = "SELECT SUM(PriceList*(LineNetAmt/(CASE WHEN PriceActual<1 THEN 1 ELSE PriceActual END))) AS TotalAmt, (COALESCE(SUM(LineDiscountAmt+DiscountAmt+ProgramDiscountAmt),0)) AS TotalDisc " +
				"FROM C_InvoiceLine WHERE C_Invoice_ID = " + line.getC_Invoice_ID();
		PreparedStatement pstmt = DB.prepareStatement(sql, line.get_Trx());
		ResultSet rs = null;
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				totalAmt = rs.getBigDecimal(1);
				totalDisc = rs.getBigDecimal(2);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		String update = "UPDATE C_Invoice SET TotalAmt = ?, DiscountAmt = ? WHERE C_Invoice_ID = ? ";
		DB.executeUpdate(line.get_Trx(), update, totalAmt, totalDisc, line.getC_Invoice_ID());

	}
	
	/**
	 *	Update Tax & Header
	 *	@return true if header updated with tax
	 */
	public boolean updateHeaderTax()
	{
		//	Recalculate Tax for this Tax
		BigDecimal serviceCharge = calculateServiceCharge(getCtx(), getC_Invoice_ID(), get_Trx());
		MInvoiceTax itax = MInvoiceTax.get (getCtx(), getC_Invoice_ID(), getC_Tax_ID(), getPrecision(), get_Trx(), getAD_Client_ID(), getAD_Org_ID(), isTaxIncluded());	//	current Tax
		if (itax != null)
		{

		if(!itax.calculateTaxFromLines(serviceCharge, new MInvoice(getCtx(), getC_Invoice_ID(), get_Trx()).getInvoiceDiscAmt()))
			return false;
		if (!itax.save(get_Trx()))
			return false;
		
			MTax tax = itax.getTax();
			if ( tax.isSummary())
			{
				MTax[] cTaxes = tax.getChildTaxes(false);
				for (MTax cTax : cTaxes)
				{   
					MInvoiceTax ichildtax = MInvoiceTax.get (getCtx(), getC_Invoice_ID(),cTax.getC_Tax_ID(), getPrecision(),
							get_Trx(), getAD_Client_ID(), getAD_Org_ID(), isTaxIncluded());	//	current Tax
					BigDecimal taxAmt= cTax.calculateTax(itax.getTaxBaseAmt(), isTaxIncluded(), getPrecision());
					ichildtax.setTaxBaseAmt(itax.getTaxBaseAmt());
					ichildtax.setTaxAmt(taxAmt);
					if (!ichildtax.save(get_Trx()))
						return false;
				}
				if (!itax.delete(true, get_Trx()))
					return false;
			}
		
		}
		//	Update Invoice Header
		String sql = "UPDATE C_Invoice i"
			+ " SET TotalLines="
				+ "(SELECT COALESCE(SUM(LineNetAmt),0) FROM C_InvoiceLine il WHERE i.C_Invoice_ID=il.C_Invoice_ID) "
			+ "WHERE C_Invoice_ID=?";
		int no1 = DB.executeUpdate(get_Trx(), sql,new Object[]{getC_Invoice_ID()});
		if (no1 != 1)
			log.warning("(1) #" + no1);

		if (isTaxIncluded())
			sql = "UPDATE C_Invoice i "
				+ "SET GrandTotal=TotalLines+? "
				+ "WHERE C_Invoice_ID=?";
		else
			sql = "UPDATE C_Invoice i "
				+ "SET GrandTotal=TotalLines+"
					+ "(SELECT COALESCE(SUM(TaxAmt),0)+? FROM C_InvoiceTax it WHERE i.C_Invoice_ID=it.C_Invoice_ID) "
					+ "WHERE C_Invoice_ID=?";
		int no2 = DB.executeUpdate(get_Trx(), sql, new Object[]{serviceCharge, getC_Invoice_ID()});
		if (no2 != 1)
			log.warning("(2) #" + no2);
//		m_parent = null;
		
		return (no1==1)&&(no2==1);
	}	//	updateHeaderTax

	public static BigDecimal calculateServiceCharge(Ctx ctx, int C_Invoice_ID, Trx trx)
	{
		BigDecimal serviceAmt = BigDecimal.ZERO;
		MInvoice invoice = new MInvoice(ctx, C_Invoice_ID, trx);
		if(!invoice.isSOTrx())
			return serviceAmt;

		MPriceList pl = new MPriceList(invoice.getCtx(), invoice.getM_PriceList_ID(), invoice.get_Trx());		
		if(!pl.isCalculateServiceCharge())
			return serviceAmt;
		if(pl.getServiceChargeRate().signum()==0)
			return serviceAmt;		
		BigDecimal serviceRate = pl.getServiceChargeRate().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);		
		
		if(serviceRate.signum()!=0)
		{
			String sql = "SELECT COALESCE(SUM(LineNetAmt),0) FROM C_InvoiceLine "
					+ "WHERE C_Invoice_ID = ? ";
			PreparedStatement pstmt = DB.prepareStatement(sql, trx);
			ResultSet rs = null;
			try{
				pstmt.setInt(1, C_Invoice_ID);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					BigDecimal totalLines = rs.getBigDecimal(1).subtract(invoice.getInvoiceDiscAmt());
					serviceAmt = (totalLines.multiply(serviceRate)).setScale(invoice.getPrecision(), RoundingMode.HALF_EVEN);
				}
				rs.close();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			updateServiceChargeAmt(C_Invoice_ID, serviceAmt, trx);
		}
		else
			updateServiceChargeAmt(C_Invoice_ID, serviceAmt, trx);		
		
		return serviceAmt;
	}
	
	private static boolean updateServiceChargeAmt(int C_Invoice_ID, BigDecimal serviceChargeAmt, Trx trx)
	{
		String update = "UPDATE C_Invoice SET ServiceChargeAmt = ? "
				+ "WHERE C_Invoice_ID = ? ";
		DB.executeUpdate(trx, update, serviceChargeAmt, C_Invoice_ID);
		return true;
	}
	
//	public static BigDecimal getServiceChargeAmt(Ctx ctx, int C_Invoice_ID, Trx trx)
//	{
//		BigDecimal serviceChargeAmt = BigDecimal.ZERO;
//		MInvoice inv = new MInvoice(ctx, C_Invoice_ID, trx);
//		MInvoiceServiceCharge isc = MInvoiceServiceCharge.get(inv, false);
//		if(isc!=null)
//			serviceChargeAmt = isc.getAmount();
//		return serviceChargeAmt;
//	}


}
