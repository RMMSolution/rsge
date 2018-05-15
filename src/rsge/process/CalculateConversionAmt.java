/**
 * 
 */
package rsge.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.common.constants.EnvConstants;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import rsge.model.MPayment;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author FANNY
 *
 */
public class CalculateConversionAmt extends SvrProcess {

	/** Initialize Variable							*/
	
	/** AD_Client ID								*/
	private int 					AD_Client_ID = 0;
	private int 					AD_Org_ID = 0;
	
	/** Conversion Type								*/
	private int 					C_ConversionType_ID = 0;
	
	/** Invoice										*/
	private int						C_Invoice_ID = 0;
	/** Open Invoice Amount							*/
	private BigDecimal				InvoiceOpenAmt = BigDecimal.ZERO;
	/** Invoice Currency							*/
	private int						C_Currency_Invoice_ID = 0;
	
	/** Transaction Currency						*/
	private int						C_Currency_ID = 0;
	
	/** Pay Amount									*/
	private BigDecimal				payAmt = BigDecimal.ZERO;
	/** Discount Amount								*/
	private BigDecimal				discountAmt = BigDecimal.ZERO;
	/** Write-Off Amount							*/
	private BigDecimal				writeOffAmt = BigDecimal.ZERO;
	/** Over Under Amount							*/
	private BigDecimal				overUnderAmt = BigDecimal.ZERO;
	
	/** Conversion Rate								*/
	private BigDecimal				conversionAmt = null;
	
	/** Transaction Date							*/
	private Timestamp				trxDate = null;
	
	/**************************************************************************
	 * Standard Constructor
	 * 
	 */
	public CalculateConversionAmt() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Get Default Parameter
		ProcessInfoParameter params[] = getParameter();
		for(ProcessInfoParameter param:params)
		{
			String name = param.getParameterName();			
			if(name.equals("C_ConversionType_ID"))
				C_ConversionType_ID = param.getParameterAsInt();			
			else if(name.equals("Rate"))
				conversionAmt = (BigDecimal) param.getParameter();			
		}		
		
		// Get default value from current transaction		

		MPayment payment = new MPayment(getCtx(), getRecord_ID(), get_Trx());

		AD_Client_ID = getAD_Client_ID();
		AD_Org_ID = payment.getAD_Org_ID();
		C_Invoice_ID = payment.getC_Invoice_ID();
		trxDate = payment.getDateTrx();
		C_Currency_ID = payment.getC_Currency_ID(); 

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		MPayment payment = new MPayment(getCtx(), getRecord_ID(), get_Trx());
		
		// Return if Conversion Type is not Bank Rate
		if(checkConversionType(C_ConversionType_ID) == false)
			return null;
		// Return if no conversion amount is entered
		if(conversionAmt.compareTo(BigDecimal.ZERO)==0)
			return null;
		
		int C_InvoicePaySchedule_ID = 0;
		if ((getCtx().getContextAsInt(EnvConstants.WINDOW_INFO, EnvConstants.TAB_INFO, "C_Invoice_ID") == C_Invoice_ID)
			&& (getCtx().getContextAsInt(EnvConstants.WINDOW_INFO, EnvConstants.TAB_INFO, "C_InvoicePaySchedule_ID") != 0))
			C_InvoicePaySchedule_ID = getCtx().getContextAsInt(EnvConstants.WINDOW_INFO, EnvConstants.TAB_INFO, "C_InvoicePaySchedule_ID");
		
		Timestamp ts = payment.getDateTrx();
		if (ts == null)
			ts = new Timestamp(System.currentTimeMillis());
		
		String sql = "SELECT C_BPartner_ID,C_Currency_ID,"		//	1..2
			+ " invoiceOpen(C_Invoice_ID, ?),"					//	3		#1
			+ " invoiceDiscount(C_Invoice_ID,?,?), IsSOTrx "	//	4..5	#2/3
			+ "FROM C_Invoice WHERE C_Invoice_ID=?";			//			#4
		
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		
		try
		{
			pstmt = DB.prepareStatement(sql, get_Trx());
			pstmt.setInt(1, C_InvoicePaySchedule_ID);
			pstmt.setTimestamp(2, ts);
			pstmt.setInt(3, C_InvoicePaySchedule_ID);
			pstmt.setInt(4, C_Invoice_ID);
			 rs = pstmt.executeQuery();
			if (rs.next())
			{
				C_Currency_Invoice_ID= rs.getInt(2);
				InvoiceOpenAmt = rs.getBigDecimal(3);		//	Set Invoice Open Amount
				if (InvoiceOpenAmt == null)
					InvoiceOpenAmt = Env.ZERO;
				discountAmt = rs.getBigDecimal(4);
				if (discountAmt == null)
					discountAmt = Env.ZERO;

				MInvoice invoice = new MInvoice(getCtx(), C_Invoice_ID, null);
				MDocType docType = MDocType.get(getCtx(), invoice.getC_DocType_ID());
				if (docType.isReturnTrx())
				{
					// Adjust discount amount for credit memos. Invoice Open Amt is already adjusted.
					discountAmt = discountAmt.negate();
				}
			}
			}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		MCurrency currency = new MCurrency(getCtx(), C_Currency_ID, get_Trx());
		
		InvoiceOpenAmt = InvoiceOpenAmt.multiply(conversionAmt)
				.setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		discountAmt = discountAmt.multiply(conversionAmt)
				.setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);		
		
		conversionAmt = conversionAmt.setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		payAmt = InvoiceOpenAmt.subtract(discountAmt).subtract(writeOffAmt).subtract(overUnderAmt);
		
		updateConversionRate();
		
		// Update Payment
		payment.setPayAmt(payAmt);
		payment.setDiscountAmt(discountAmt);
		payment.setOverUnderAmt(overUnderAmt);
		payment.setWriteOffAmt(writeOffAmt);
		payment.setC_ConversionType_ID(C_ConversionType_ID);
		payment.setRate(conversionAmt);
		payment.save();
		
		return null;
	}
	
	/**
	 * Update multiply rate of conversion rate with new conversion rate
	 * Create new multiply rate if record not exists yet
	 */
	private void updateConversionRate()
	{
		// Check if selected conversion rate is already exists. 
		// If yes, update multiply rate
		// If not, create new conversion rate
	
		int C_Conversion_Rate_ID = 0;
		BigDecimal MultiplyRate = null;
		
		String sql = "SELECT C_Conversion_Rate_ID, MultiplyRate " +
				"FROM C_Conversion_Rate " +
				"WHERE C_Currency_ID= ? "  + // 1
				"AND C_Currency_To_ID= ? " + // 2
				"AND C_ConversionType_ID = ? " + // 3
				"AND (ValidFrom IS NULL OR (ValidFrom IS NOT NULL AND ? >= ValidFrom)) " + // 4
				"AND (ValidTo IS NULL OR (ValidTo IS NOT NULL AND ? <= ValidTo)) " + // 5
				"AND AD_Client_ID IN (0,?) " + // 6
				"AND AD_Org_ID IN (0,?) AND IsActive='Y' " + // 7
				"ORDER BY AD_Client_ID DESC, AD_Org_ID DESC, ValidFrom DESC ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, C_Currency_Invoice_ID);
			pstmt.setInt(2, C_Currency_ID);
			pstmt.setInt(3, C_ConversionType_ID);
			pstmt.setTimestamp(4, trxDate);
			pstmt.setTimestamp(5, trxDate);
			pstmt.setInt(6, AD_Client_ID);
			pstmt.setInt(7, AD_Org_ID);
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				C_Conversion_Rate_ID = rs.getInt(1);
				MultiplyRate = rs.getBigDecimal(2);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		// Create or Update Conversion Rate
		if(C_Conversion_Rate_ID == 0) // Conversion rate not found. Create new one
		{
			MConversionRate cr = new MConversionRate(getCtx(), 0, get_Trx());
			cr.setC_Currency_ID(C_Currency_Invoice_ID);
			cr.setC_Currency_To_ID(C_Currency_ID);
			cr.setC_ConversionType_ID(C_ConversionType_ID);
			cr.setMultiplyRate(conversionAmt);
			cr.setValidFrom(trxDate);
			cr.save();
		}
		else if (MultiplyRate.compareTo(conversionAmt)!=0)
		{
			MConversionRate cr = new MConversionRate(getCtx(), C_Conversion_Rate_ID, get_Trx());
			cr.setMultiplyRate(conversionAmt);
			cr.save();
		}		

	}
	
	/**
	 * Use to check if the conversion type is Bank Rate or not
	 * 
	 * @param C_ConversionType_ID
	 * @return BankRate (boolean)
	 */
	private boolean checkConversionType(int C_ConversionType_ID)
	{
		boolean BankRate = false;
		
		String sql = "SELECT BankRate " +
				"FROM C_ConversionType " +
				"WHERE C_ConversionType_ID = " + C_ConversionType_ID;
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				if(rs.getString(1).equals("Y"))
					BankRate = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		return BankRate;
	}

}
