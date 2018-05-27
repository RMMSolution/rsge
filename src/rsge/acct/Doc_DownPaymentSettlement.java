/**
 * 
 */
package rsge.acct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCurrency;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MTax;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.model.MChargeAcct;
import rsge.model.MDownPayment;
import rsge.model.MDownPaymentOrderAlloc;
import rsge.model.MDownPaymentSettlement;
import rsge.model.MDownPaymentSettlementLn;

/**
 * @author Fanny
 *
 */
public class Doc_DownPaymentSettlement extends Doc {

	/**
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trx
	 */
	public Doc_DownPaymentSettlement(MAcctSchema[] ass, ResultSet rs, Trx trx) {
		super(ass, MDownPaymentSettlement.class, rs, DocBaseType.DOCBASETYPE_DownPaymentSettlement, trx);
		// TODO Auto-generated constructor stub
	}
	
	private MDownPaymentSettlement 					dps = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	public String loadDocumentDetails() {
		dps = (MDownPaymentSettlement) getPO();
		// Set Date Doc (taken from Budget Plan Date)
		setDateDoc(dps.getDateDoc());		
		setDateAcct(dps.getDateAcct());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	/**
	 *  Create Facts (the accounting logic) for DPS
	 *  <pre>
	 *  XBP
	 *      Account			     DR
	 *      Budget Clearing	         CR
	 *
	 *  </pre>
	 *  @param as accounting schema
	 *  @return Fact
	 */	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		ArrayList<Fact> facts = new ArrayList<Fact>();
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		MDownPaymentSettlementLn[] lines = getLines(); 
		MAccount bpAcct = null;
		MCurrency currency = new MCurrency(getCtx(), dps.getC_Currency_ID(), getTrx());

		if(dps.isSOTrx())
		{
			bpAcct = getAccount(Doc.ACCTTYPE_C_Receivable, as);
			if(dps.isManual())
			{
				// Get Down Payment
				MDownPaymentOrderAlloc[] dpLocList = getDownPaymentOrderAlloc();
				for(MDownPaymentOrderAlloc dploc : dpLocList)
				{
					MDownPayment dp = new MDownPayment(getCtx(), dploc.getXX_DownPayment_ID(), getTrx());
					MChargeAcct ca = MChargeAcct.get(dp.getC_Charge_ID(), as);					
					MAccount account = MAccount.get(getCtx(), ca.getCh_Revenue_Acct());
					fact.createLine(null, account, dps.getC_Currency_ID(), dploc.getAllocatedAmt(), null);					
				}
			}
			else
			{
				for(MDownPaymentSettlementLn line : lines)
				{
					MOrder order = new MOrder(getCtx(), line.getC_Order_ID(), getTrx());
					MOrderLine[] oLines = getOrderLines(order);
					BigDecimal distAmount = line.getAmount();						
					if(distAmount.signum()<0)
						distAmount = distAmount.negate();
					BigDecimal baseAmt = distAmount;

					int size = oLines.length;
					int n = 1;
					
					MAccount chargeAcct = null;
					if(line.getC_Invoice_ID()!=0)
					{
						// Get Charge
						int chargeID = 0;
						String sql = "SELECT il.C_Charge_ID FROM C_InvoiceLine il "
								+ "INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID "
								+ "WHERE i.C_Invoice_ID = ? ";
						PreparedStatement pstmt = DB.prepareStatement(sql, getTrx());
						ResultSet rs = null;
						try{
							pstmt.setInt(1, line.getC_Invoice_ID());
							rs = pstmt.executeQuery();
							if(rs.next())				
								chargeID = rs.getInt(1);
							rs.close();
							pstmt.close();
						}catch (Exception e) {
							e.printStackTrace();
						}							
						chargeAcct = DocBaseType.getAccount(DocBaseType.ACCTTYPE_Charge, 0, chargeID, as);

					}
					
					for(MOrderLine oLine : oLines)
					{
						BigDecimal allocatedAmt = BigDecimal.ZERO;
						BigDecimal taxAmt = BigDecimal.ZERO;
						
						MAccount account = null;
						if(chargeAcct==null)
						{
							int dpCharge = MDownPayment.getDownPaymentCharge(oLine.getC_Order_ID(), getTrx());
							chargeAcct = DocBaseType.getAccount(DocBaseType.ACCTTYPE_Charge, 0, dpCharge, as);
						}

						account = chargeAcct;
						
						if(n < size)
						{
							BigDecimal ratio = oLine.getLineNetAmt().divide(order.getTotalLines(), 4, RoundingMode.HALF_EVEN);
							allocatedAmt = (baseAmt.multiply(ratio)).setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
							distAmount = distAmount.subtract(allocatedAmt);
						}
						else
							allocatedAmt = distAmount;
						
						BigDecimal trxPlusTax = BigDecimal.valueOf(100);
						BigDecimal taxRate = BigDecimal.ZERO;
						if(oLine.getC_Tax_ID()!=0)
						{
							MTax t = new MTax(getCtx(), oLine.getC_Tax_ID(), getTrx());
							taxRate = t.getRate();
							if(t.getRate().signum()!=0)
								trxPlusTax = trxPlusTax.add(taxRate);
						}
						
						if(taxRate.signum()!=0)
						{
							BigDecimal multiplier = allocatedAmt.divide(trxPlusTax, 4, RoundingMode.HALF_EVEN);
							allocatedAmt = multiplier.multiply(BigDecimal.valueOf(100));
							allocatedAmt = allocatedAmt.setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
							taxAmt = multiplier.multiply(taxRate);
							taxAmt = taxAmt.setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
						}
						fact.createLine(null, account, dps.getC_Currency_ID(), allocatedAmt, null);
						if(taxAmt.signum()!=0)
							fact.createLine(null, getTaxAccount(oLine.getC_Tax_ID(), as), dps.getC_Currency_ID(), taxAmt, null);

						n++;
					}
					
				}
			}
		}
		else
		{
			bpAcct = getAccount(Doc.ACCTTYPE_V_Liability, as);			
			fact.createLine(null, bpAcct, dps.getC_Currency_ID(), dps.getInvoiceAmt(), null);			
			for(MDownPaymentSettlementLn line : lines)
			{
				MOrder order = new MOrder(getCtx(), line.getC_Order_ID(), getTrx());
				MOrderLine[] oLines = getOrderLines(order);
				BigDecimal distAmount = line.getAmount();						
				if(distAmount.signum()<0)
					distAmount = distAmount.negate();
				BigDecimal baseAmt = distAmount;

				int size = oLines.length;
				int n = 1;
				for(MOrderLine oLine : oLines)
				{
					BigDecimal allocatedAmt = BigDecimal.ZERO;
					BigDecimal taxAmt = BigDecimal.ZERO;
					BigDecimal taxRatio = BigDecimal.ZERO;
					if(oLine.getC_Tax_ID()!=0)
					{
						MTax t = new MTax(getCtx(), oLine.getC_Tax_ID(), getTrx());
						if(t.getRate().signum()!=0)
							taxRatio = t.getRate().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
					}
					
					MChargeAcct ca = MChargeAcct.get(MDownPayment.getDownPaymentCharge(oLine.getC_Order_ID(), getTrx()), as);					
					MAccount account = MAccount.get(getCtx(), ca.getCh_Expense_Acct());
					if(n < size)
					{
						BigDecimal ratio = oLine.getLineNetAmt().divide(order.getTotalLines(), 4, RoundingMode.HALF_EVEN);
						allocatedAmt = (baseAmt.multiply(ratio)).setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
						distAmount = distAmount.subtract(allocatedAmt);
					}
					else
						allocatedAmt = distAmount;
					if(taxRatio.signum()!=0)
					{
						taxAmt = allocatedAmt.multiply(taxRatio);
						taxAmt = taxAmt.setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
						allocatedAmt = allocatedAmt.subtract(taxAmt);
					}
					fact.createLine(null, account, dps.getC_Currency_ID(), null, allocatedAmt);
					if(taxAmt.signum()!=0)
						fact.createLine(null, getTaxAccount(oLine.getC_Tax_ID(), as), dps.getC_Currency_ID(), null, taxAmt);

					n++;
				}
				
			}
		}
		
		facts.add(fact);
		return facts;	
	}
	 
	private MDownPaymentSettlementLn[] getLines()
	{
		ArrayList<MDownPaymentSettlementLn> dpsLines = new ArrayList<>();
		String sql = "SELECT * FROM XX_DownPaymentSettlementLn "
				+ "WHERE XX_DownPaymentSettlement_ID = ?";
		PreparedStatement pstmt = DB.prepareStatement(sql, getTrx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, dps.getXX_DownPaymentSettlement_ID());
			rs = pstmt.executeQuery();
			while(rs.next())				
				dpsLines.add(new MDownPaymentSettlementLn(getCtx(), rs, getTrx()));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}		
		MDownPaymentSettlementLn[] retValue = new MDownPaymentSettlementLn[dpsLines.size()];
		dpsLines.toArray(retValue);
		return retValue;
	}
	
	private MOrderLine[] getOrderLines(MOrder order)
	{
		ArrayList<MOrderLine> lines = new ArrayList<>();
		String sql = "SELECT * FROM C_OrderLine WHERE C_Order_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, order.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, order.getC_Order_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				lines.add(new MOrderLine(order.getCtx(), rs, order.get_Trx()));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		MOrderLine[] retValue = new MOrderLine[lines.size()];
		lines.toArray(retValue);
		return retValue;
	}
	
	private MDownPaymentOrderAlloc[] getDownPaymentOrderAlloc()
	{
		ArrayList<MDownPaymentOrderAlloc> lines = new ArrayList<>();
		String sql = "SELECT * FROM XX_DownPaymentOrderAlloc "
				+ "WHERE XX_DownPayment_ID IS NOT NULL "
				+ "AND XX_DownPaymentSettlement_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, (Trx)null);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, dps.getXX_DownPaymentSettlement_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				lines.add(new MDownPaymentOrderAlloc(getCtx(), rs, (Trx)null));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		MDownPaymentOrderAlloc[] retValue = new MDownPaymentOrderAlloc[lines.size()];
		lines.toArray(retValue);
		return retValue;
	}

	/**
	 *	Get Account
	 *  @param AcctType see ACCTTYPE_*
	 *  @param as account schema
	 *  @return Account
	 */
	private MAccount getTaxAccount (int C_Tax_ID, MAcctSchema as)
	{		
		String sql = "SELECT T_Due_Acct, T_Liability_Acct, T_Credit_Acct, T_Receivables_Acct "
			+ "FROM C_Tax_Acct WHERE C_Tax_ID=? AND C_AcctSchema_ID=?";
		int validCombination_ID = 0;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try
		{
			pstmt = DB.prepareStatement(sql,as.get_Trx());
			pstmt.setInt(1, C_Tax_ID);
			pstmt.setInt(2, as.getC_AcctSchema_ID());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				if(isSOTrx())
					validCombination_ID = rs.getInt(2);    //  1..5
				else
					validCombination_ID = rs.getInt(4);    //  1..5
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

		if (validCombination_ID == 0)
			return null;
		return MAccount.get(as.getCtx(), validCombination_ID);
	}   //  getAccount


	 
	 

}
