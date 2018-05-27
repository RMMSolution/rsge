/**
 * 
 */
package rsge.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import rsge.model.MOtherDPAllocation;
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
		BigDecimal invAmt = inv.getGrandTotal();
		if(orderList.size()>0)
		{
			for(int orderID : orderList)
			{
				// Get Other Down Payment Allocation
				ArrayList<MOtherDPAllocation> odaList = new ArrayList<>();
				sql = "SELECT oda.* FROM XX_OtherDPAllocation oda "
						+ "WHERE oda.DPAmount > dpo.AllocatedAmt "
						+ "AND oda.XX_DownPayment_ID IN ("
						+ "SELECT dp.XX_DownPayment_ID FROM XX_DownPayment dp "
						+ "INNER JOIN XX_DownPaymentOrder dpo ON dp.XX_DownPayment_ID = dpo.XX_DownPayment_ID "
						+ "WHERE dpo.C_Order_ID = ? "
						+ ")";
								
				pstmt = DB.prepareStatement(sql, trx);
				rs = null;
				
				try{
					pstmt.setInt(1, orderID);
					rs = pstmt.executeQuery();						
					while(rs.next())			
						odaList.add(new MOtherDPAllocation(ctx, rs, trx));
					rs.close();
					pstmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}	
				
				for(MOtherDPAllocation oda : odaList)
				{
					BigDecimal allocAmt = BigDecimal.ZERO;
					BigDecimal remainsAmt = oda.getDPAmount().subtract(oda.getAllocatedAmt());
					if(remainsAmt.compareTo(invAmt)>0)
						allocAmt = invAmt;
					else
						allocAmt = remainsAmt;
					
					// Allocate
					oda.setAllocatedAmt(oda.getAllocatedAmt().add(allocAmt));
					if(oda.save())
					{
						// Create Down Payment Settlement
						MDownPaymentSettlement dps = new MDownPaymentSettlement(ctx, 0, trx);
						dps.setClientOrg(inv);
						dps.setC_Invoice_ID(inv.getC_Invoice_ID());
						dps.setDateDoc(inv.getDateAcct());
						dps.setDateAcct(inv.getDateAcct());
						dps.setIsManual(false);
						dps.save();
						
						MDownPaymentSettlementLn dpsl = new MDownPaymentSettlementLn(dps);
						dpsl.setXX_DownPayment_ID(oda.getXX_DownPayment_ID());
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
					invAmt = invAmt.subtract(allocAmt);
					if(invAmt.signum()==0)
						break;
				}
				
				if(invAmt.signum()==0)
					break;
				ArrayList<MDownPaymentOrder> dpoList = new ArrayList<>();
				// Get Order List
				sql = "SELECT dpo.* FROM XX_DownPaymentOrder dpo "
						+ "INNER JOIN XX_DownPayment dp ON dpo.XX_DownPayment_ID = dp.XX_DownPayment_ID "
						+ "WHERE dpo.C_Order_ID = ? "
						+ "AND dpo.DPAmount > dpo.AllocatedAmt "
						+ "ORDER BY dp.DateTrx ";
								
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
					BigDecimal maxRatio = BigDecimal.ONE;
					if(dp.isDPPercentage())
						maxRatio = dp.getPercentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
					else
						maxRatio = dp.getDPAmount().divide(dp.getGrandTotal(), 2, RoundingMode.HALF_EVEN);
					BigDecimal allocationBase = invAmt;
					BigDecimal maxInvAllocation = inv.getGrandTotal().multiply(maxRatio);
					if(maxInvAllocation.compareTo(invAmt)<=0)
						allocationBase = maxInvAllocation;
					
					BigDecimal maxAllocation = dpo.getDPAmount().subtract(dpo.getAllocatedAmt());
					BigDecimal allocAmt = BigDecimal.ZERO;
					
					if(allocationBase.compareTo(maxAllocation)>0)
						allocAmt = maxAllocation;
					else
						allocAmt = allocationBase;
					invAmt = invAmt.subtract(allocAmt);
					
					MDownPaymentOrderAlloc dpAlloc = MDownPaymentOrderAlloc.get(dpo, C_Invoice_ID);
					dpAlloc.setAllocatedAmt(allocAmt);					
					if(dpAlloc.save())
					{
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
//							else
//								al.setAmount(allocAmt.negate());
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
					
					if(invAmt.signum()==0)
						break;
					
				}
			}		
			
		}
		return dpAmt;
	}
	
}
