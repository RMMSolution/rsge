/**
 * 
 */
package rsge.acct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCharge;
import org.compiere.model.MCurrency;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
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
		int acctType = 0;
		MCurrency currency = new MCurrency(getCtx(), dps.getC_Currency_ID(), getTrx());

		if(dps.isSOTrx())
		{
			System.out.println("START NOW");
			boolean hasDPInvoice = false;
			bpAcct = getAccount(Doc.ACCTTYPE_C_Receivable, as);
			if(dps.isManual())
			{
				System.out.println("OK IS MANUAL");
				// Get Down Payment
				MDownPaymentOrderAlloc[] dpLocList = getDownPaymentOrderAlloc();
				System.out.println("dpLocList size " + dpLocList);
				for(MDownPaymentOrderAlloc dploc : dpLocList)
				{
					System.out.println("OK NOW");
					MDownPayment dp = new MDownPayment(getCtx(), dploc.getXX_DownPayment_ID(), getTrx());
					MChargeAcct ca = MChargeAcct.get(dp.getC_Charge_ID(), as);					
					MAccount account = MAccount.get(getCtx(), ca.getCh_Revenue_Acct());
					System.out.println("DPLoc AllocatedAmt " + dploc.getAllocatedAmt());
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
						hasDPInvoice = true;
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
						MAccount account = null;
						if(chargeAcct==null)
						{
							System.out.println("Should be here");
//							if(oLine.getM_Product_ID()!=0)
//								acctType = DocBaseType.ACCTTYPE_ProductCustomerPrepayment;
//							else if(oLine.getC_Charge_ID()!=0)
//								acctType = DocBaseType.ACCTTYPE_ChargeCustomerPrepayment;
							int dpCharge = MDownPayment.getDownPaymentCharge(oLine.getC_Order_ID(), getTrx());
							System.out.println("Charge name : " + new MCharge(getCtx(), dpCharge, getTrx()).getName());
							chargeAcct = DocBaseType.getAccount(DocBaseType.ACCTTYPE_Charge, 0, dpCharge, as);
//							account = MAccount.get(getCtx(), DocBaseType.getValidCombination_ID(acctType, oLine.getM_Product_ID(), oLine.getC_Charge_ID(), as));						
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
						fact.createLine(null, account, dps.getC_Currency_ID(), allocatedAmt, null);
						n++;
					}
					
				}
//				if(!hasDPInvoice)
//					fact.createLine(null, bpAcct, dps.getC_Currency_ID(), null, dps.getInvoiceAmt());
				
			}
		}
		else
		{
			System.out.println("HERE");
			bpAcct = getAccount(Doc.ACCTTYPE_V_Liability, as);			
			System.out.println(dps.getInvoiceAmt());
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
//					if(oLine.getM_Product_ID()!=0)
//						acctType = DocBaseType.ACCTTYPE_ProductVendorPrepayment;
//					else if(oLine.getC_Charge_ID()!=0)
//						acctType = DocBaseType.ACCTTYPE_ChargeVendorPrepayment;
					MChargeAcct ca = MChargeAcct.get(MDownPayment.getDownPaymentCharge(oLine.getC_Order_ID(), getTrx()), as);					
					MAccount account = MAccount.get(getCtx(), ca.getCh_Expense_Acct());
					System.out.println("Account " + account.getCombination());
					if(n < size)
					{
						BigDecimal ratio = oLine.getLineNetAmt().divide(order.getTotalLines(), 4, RoundingMode.HALF_EVEN);
						allocatedAmt = (baseAmt.multiply(ratio)).setScale(currency.getStdPrecision(), RoundingMode.HALF_EVEN);
						distAmount = distAmount.subtract(allocatedAmt);
					}
					else
						allocatedAmt = distAmount;
					fact.createLine(null, account, dps.getC_Currency_ID(), null, allocatedAmt);
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


	 
	 

}
