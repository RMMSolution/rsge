/**
 * 
 */
package rsge.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.compiere.model.MCurrency;
import org.compiere.model.MInvoice;
import org.compiere.model.X_C_AllocationHdr;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.model.MAllocationHdr;
import rsge.model.MAllocationLine;
import rsge.model.MDownPayment;
import rsge.model.MDownPaymentOrder;
import rsge.model.MDownPaymentOrderAlloc;
import rsge.model.MDownPaymentSettlement;
import rsge.model.MDownPaymentSettlementLn;
import rsge.po.X_XX_DownPaymentSettlement;

/**
 * @author Fanny
 *
 */
public class SettleInvoiceDownPayment {
	
	/**
	 * Settle Down Payment of invoice (if any)
	 * @param ctx
	 * @param C_Invoice_ID
	 * @param trx
	 * @return amount if any
	 */
	public static BigDecimal settleDownPayment(Ctx ctx, int C_Invoice_ID, Trx trx)
	{	
		ArrayList<Integer> orderList = new ArrayList<Integer>();
		// Get Order by lines first
		String sql = "SELECT ol.C_Order_ID " +
				"FROM C_InvoiceLine il " +
				"INNER JOIN C_OrderLine ol ON (il.C_OrderLine_ID = ol.C_OrderLine_ID) " +
				"WHERE il.C_Invoice_ID = ? " +
				"GROUP BY ol.C_Order_ID ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, C_Invoice_ID);
			rs = pstmt.executeQuery();	
			int orgID = 0;
			while(rs.next())			
			{
				if(rs.getInt(1)!=orgID)
				{
					orderList.add(rs.getInt(1));
					orgID = rs.getInt(1);
				}
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		BigDecimal dpAmt = BigDecimal.ZERO;
		MInvoice inv = new MInvoice(ctx, C_Invoice_ID, trx);
		MCurrency curr = new MCurrency(inv.getCtx(), inv.getC_Currency_ID(), inv.get_Trx());
		BigDecimal invAmt = inv.getGrandTotal();
		if(orderList.size()>0)
		{
			for(int orderID : orderList)
			{
				ArrayList<MDownPaymentOrder> dpoList = new ArrayList<>();
				// Get Order List
				sql = "SELECT * FROM XX_DownPaymentOrder "
						+ "WHERE C_Order_ID = ? "
						+ "AND DPAmount > AllocatedAmt ";
				
				pstmt = DB.prepareStatement(sql, trx);
				rs = null;
				
				try{
					pstmt.setInt(1, orderID);
					rs = pstmt.executeQuery();						
					while(rs.next())			
						dpoList.add(new MDownPaymentOrder(ctx, rs, trx));
					rs.close();
					pstmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}	
				
				for(MDownPaymentOrder dpo : dpoList)
				{
					MDownPayment dp = new MDownPayment(ctx, dpo.getXX_DownPayment_ID(), trx);
					BigDecimal dpPercentage = getDPPercentage(dp);
					BigDecimal dpoRatio = dpo.getDPAmount().divide(dp.getTotalDPAmount(), 4, RoundingMode.HALF_EVEN);
					BigDecimal invAllocAmt = (invAmt.multiply(dpPercentage)).multiply(dpoRatio);
					invAllocAmt = invAllocAmt.setScale(curr.getStdPrecision(), RoundingMode.HALF_EVEN);
					
					MDownPaymentOrderAlloc dpAlloc = MDownPaymentOrderAlloc.get(dpo, C_Invoice_ID);
					BigDecimal allocAmt = dpo.getDPAmount().subtract(dpo.getAllocatedAmt());
					if(allocAmt.compareTo(invAllocAmt)>0)
						allocAmt = invAllocAmt;
					dpAlloc.setAllocatedAmt(allocAmt);					
					if(dpAlloc.save())
					{
//						if(dp.isGenerateDPInvoice())
//						{
							// Generate Allocation
							MAllocationHdr ah = new MAllocationHdr(ctx, 0, trx);
							ah.setClientOrg(inv);
							ah.setDateAcct(inv.getDateAcct());
							ah.setDateTrx(inv.getDateInvoiced());
							ah.setC_Currency_ID(inv.getC_Currency_ID());
							ah.save();
							
							// Generate Allocation Line
							MAllocationLine al = new MAllocationLine(ah);
							al.setDateTrx(inv.getDateInvoiced());
							al.setC_Order_ID(dpo.getC_Order_ID());
							al.setC_Invoice_ID(C_Invoice_ID);
							al.setC_BPartner_ID(inv.getC_BPartner_ID());
							if(inv.isSOTrx())
								al.setAmount(allocAmt);
							else
								al.setAmount(allocAmt.negate());
							if(al.save())
							{
								DocumentEngine.processIt(ah, X_C_AllocationHdr.DOCACTION_Complete);
								ah.save();
							}
							
							// Create Down Payment Settlement
							MDownPaymentSettlement dps = new MDownPaymentSettlement(ctx, 0, trx);
							dps.setClientOrg(inv);
							dps.setC_Invoice_ID(inv.getC_Invoice_ID());
							dps.setDateDoc(inv.getDateAcct());
							dps.setDateAcct(inv.getDateAcct());
							dps.setIsManual(false);
							dps.save();
							
							MDownPaymentSettlementLn dpsl = new MDownPaymentSettlementLn(dps);
							dpsl.setC_Invoice_ID(dp.getDPInvoice_ID());
							dpsl.setC_Order_ID(dpo.getC_Order_ID());
							dpsl.setAmount(allocAmt);
							if(dpsl.save())
							{
								dps.setProcessed(true);
								dps.setDocAction(X_XX_DownPaymentSettlement.DOCACTION_Close);
								dps.setDocStatus(X_XX_DownPaymentSettlement.DOCACTION_Complete);
								dps.setIsApproved(true);
								dps.save();
								dpAmt = dpAmt.add(allocAmt);
							}
						}
//					}
					
				}
			}
		}
		return dpAmt;
	}
	
	private static BigDecimal getDPPercentage(MDownPayment dp)
	{
		BigDecimal percentage = BigDecimal.ZERO;
		if(dp.isDPPercentage())
			percentage = dp.getPercentage().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_EVEN);
		else
			percentage = dp.getDPAmount().divide(dp.getGrandTotal(), 4, RoundingMode.HALF_EVEN);
		return percentage;
	}

}
