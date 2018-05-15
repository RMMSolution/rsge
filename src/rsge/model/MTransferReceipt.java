package rsge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClientInfo;
import org.compiere.model.MConversionRate;
import org.compiere.model.MPriceList;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.ProductCost;
import org.compiere.model.X_M_Inventory;
import org.compiere.model.X_M_Movement;
import org.compiere.model.X_M_Product;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_TransferReceipt;

public class MTransferReceipt extends X_XX_TransferReceipt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MTransferReceipt(Ctx ctx, int XX_TransferReceipt_ID, Trx trx) {
		super(ctx, XX_TransferReceipt_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MTransferReceipt(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// Set UOM
		if(getM_Product_ID()!=0)
			setC_UOM_ID(new MProduct(getCtx(), getM_Product_ID(), get_Trx()).getC_UOM_ID());
		return true;
	}
	
	public static boolean generateAutroTransfer(MInOut io)
	{
		ArrayList<MInOutLine> iolList = new ArrayList<>();
		String sql = "SELECT * FROM M_InOutLine "
				+ "WHERE M_InOut_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, io.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, io.getM_InOut_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				iolList.add(new MInOutLine(io.getCtx(), rs, io.get_Trx()));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(iolList.size()!=0)
		{
			for(MInOutLine iol : iolList)
			{
				boolean isProductItem = false;
				boolean autoTransfer = false;
				int prodCatID = 0;
				if(iol.getC_Charge_ID()!=0)
					autoTransfer = true;
				else if(iol.getM_Product_ID()!=0)
				{
					MProduct product = new MProduct(iol.getCtx(), iol.getM_Product_ID(), iol.get_Trx());
					if(product.getProductType().equals(X_M_Product.PRODUCTTYPE_Service) 
							|| product.getProductType().equals(X_M_Product.PRODUCTTYPE_ExpenseType))
						autoTransfer = true;
					else
					{
						MProductCategory pc = new MProductCategory(iol.getCtx(), product.getM_Product_Category_ID(), iol.get_Trx());
						autoTransfer = pc.isAutoTransfer();
						isProductItem = true;
						prodCatID = product.getM_Product_Category_ID();
					}
				}
				
				if(autoTransfer)
				{
					// Get PR data
					Map<ArrayList<MPurchaseRequisitionLine>, BigDecimal> values = MPurchaseRequisitionLine.getPRLines(iol);
					ArrayList<MPurchaseRequisitionLine> prList = null;
					BigDecimal totalRequestQty = BigDecimal.ZERO;
					for(Map.Entry<ArrayList<MPurchaseRequisitionLine>, BigDecimal> entry : values.entrySet())
					{
						prList = entry.getKey();
						totalRequestQty = entry.getValue();
					}
					
					ArrayList<MTransferReceiptLine> trReceiptLines = new ArrayList<>();
					MTransferReceipt tr = null;
					if(prList.size()!=0)
					{
						// Convert Recept Line qty to product UOM qty
						BigDecimal receiptQty = iol.getMovementQty();
						// Generate Tranfer Receipt
						for(MPurchaseRequisitionLine prLine : prList)
						{
							if(isProductItem)
								if(MAutoTransferException.isExist(prLine.getCtx(), prodCatID, prLine.getAD_Org_ID(), prLine.get_Trx()))
									continue;
							if(tr==null)
								tr = MTransferReceipt.get(iol);
							
							MTransferReceiptLine trl = new MTransferReceiptLine(iol.getCtx(), 0, iol.get_Trx());
							trl.setAD_Org_ID(prLine.getAD_Org_ID());
							BigDecimal lineQty = prLine.getQty();
							MProduct p = new MProduct(prLine.getCtx(), prLine.getM_Product_ID(), prLine.get_Trx());
							if(p.getProductType().equals(X_M_Product.PRODUCTTYPE_Item) || p.getProductType().equals(X_M_Product.PRODUCTTYPE_Resource))
							{
								if(p.getC_UOM_ID()!=prLine.getC_UOM_ID())
									lineQty = MUOMConversion.convertProductTo(prLine.getCtx(), prLine.getM_Product_ID(), prLine.getC_UOM_ID(), lineQty);
							}
							
							if(totalRequestQty.compareTo(receiptQty)>0)
							{
								MUOM uom = new MUOM(prLine.getCtx(), p.getC_UOM_ID(), prLine.get_Trx());
								lineQty = (lineQty.divide(totalRequestQty, uom.getStdPrecision(), RoundingMode.HALF_EVEN));
							}
							trl.setQtyAllocated(lineQty);
							trl.setXX_PurchaseRequisitionLine_ID(prLine.getXX_PurchaseRequisitionLine_ID());
							trl.setXX_TransferReceipt_ID(tr.getXX_TransferReceipt_ID());
							if(trl.save())
								trReceiptLines.add(trl);
						}
					}
					
					if(tr!=null)
					{
						tr.setProcessed(true);
						tr.save();
					}
					
					// Generate Inventory Move
					if(trReceiptLines.size()!=0)
					{
						// Generate Inventory Move header
						int movementID = 0;
						int inventoryID = 0;
						int movementlineNo = 1;
						int invlineNo = 1;
						for(MTransferReceiptLine trl : trReceiptLines)
						{
							MPurchaseRequisitionLine prline = new MPurchaseRequisitionLine(trl.getCtx(), trl.getXX_PurchaseRequisitionLine_ID(), trl.get_Trx());
							
							if(tr.getM_Product_ID()!=0)
							{
								MProduct p = new MProduct(tr.getCtx(), tr.getM_Product_ID(), tr.get_Trx());
								if(p.getProductType().equals(X_M_Product.PRODUCTTYPE_Item) 
										|| p.getProductType().equals(X_M_Product.PRODUCTTYPE_Resource)) 
								{									
									// Check if it's inventory move or internal used
									if(IsInventoryCheck(prline)) // Generate Inventory Move
									{
										int sourceLocID = iol.getM_Locator_ID();
										int targetLocID = getTargetLocator(trl.getAD_Org_ID(), tr.get_Trx());
										if(sourceLocID==targetLocID)
											continue;
										
										if(movementID==0)
											movementID = MMovement.get(io);
										if(!MMovementLine.generateMovementLine(trl, iol.getM_Product_ID(), iol.getM_AttributeSetInstance_ID(), movementID, sourceLocID, targetLocID, movementlineNo))
											continue;									
										movementlineNo++;										
									}
									else // Generate Internal Used
									{
										if(inventoryID==0)
											inventoryID = MInventory.get(iol);
										if(!MInventoryLine.generateInternalUsedLine(trl, iol, inventoryID, invlineNo))
											continue;
										invlineNo++;
									}
								}
							}							
														
							// Update Budget Transaction
							MOrderLine orderLine = new MOrderLine(iol.getCtx(), iol.getC_OrderLine_ID(), iol.get_Trx());
							MOrder order = new MOrder(iol.getCtx(), orderLine.getC_Order_ID(), iol.get_Trx());
							prline.setQtyDelivered(prline.getQtyDelivered().add(trl.getQtyAllocated()));
							MPurchaseRequisition pr = new MPurchaseRequisition(trl.getCtx(), prline.getXX_PurchaseRequisition_ID(), trl.get_Trx());
							MBudgetTransactionLine bline = MBudgetTransactionLine.createLine(prline.getCtx(), iol.get_Table_ID(), iol.getM_InOutLine_ID(), 
									prline.get_Table_ID(), prline.getXX_PurchaseRequisitionLine_ID(), trl.getAD_Org_ID(), tr.getDateTrx(), prline.getAccount_ID(), 
									0, prline.getC_Activity_ID(), 0, pr.getC_Campaign_ID(), 0, 0, pr.getC_Project_ID(), 0, 0, 0, tr.getM_Product_ID(), 0, 0, 0, 0, 0, trl.get_Trx());
							
							MBudgetInfo bi = MBudgetInfo.get(trl.getCtx(),trl.getAD_Client_ID(), trl.get_Trx());
							MAcctSchema as = new MAcctSchema(bi.getCtx(), bi.getC_AcctSchema_ID(), bi.get_Trx());
							MPriceList pl = new MPriceList(order.getCtx(), order.getM_PriceList_ID(), order.get_Trx());
							BigDecimal costPerUnit = orderLine.getPriceActual();
							if(tr.getM_Product_ID()!=0)
							{
								MProduct p = new MProduct(tr.getCtx(), tr.getM_Product_ID(), tr.get_Trx());
								if(p.getProductType().equals(X_M_Product.PRODUCTTYPE_Item) 
										|| p.getProductType().equals(X_M_Product.PRODUCTTYPE_Resource)) 
								{
									ProductCost pc = new ProductCost(prline.getCtx(), tr.getM_Product_ID(), iol.getM_AttributeSetInstance_ID(), prline.get_Trx());
									pc.setQty(BigDecimal.ONE);									
									MProductCategoryAcct pca = MProductCategoryAcct.get(tr.getCtx(), tr.getM_Product_ID(), as.getC_AcctSchema_ID(), tr.get_Trx());
									String costingMethod = pca.getCostingMethod();
									if(costingMethod==null)
										costingMethod = as.getCostingMethod();
									BigDecimal unitCost = BigDecimal.ZERO;
									unitCost = pc.getProductCosts(as, trl.getAD_Org_ID(), costingMethod, iol.getC_OrderLine_ID(), true);
									if(unitCost.signum()!=0)
										costPerUnit = unitCost;
								}
							}
							if(as.getC_Currency_ID()!=pl.getC_Currency_ID())
								costPerUnit = MConversionRate.convert(order.getCtx(), costPerUnit, order.getC_Currency_ID(), as.getC_Currency_ID(), order.getDateOrdered(), bi.getC_ConversionType_ID(), order.getAD_Client_ID(), order.getAD_Org_ID());							
							BigDecimal realizedAmt = trl.getQtyAllocated().multiply(costPerUnit);						
							bline.setUnrealizedAmt(realizedAmt.negate());
							bline.setRealizedAmt(realizedAmt);
							bline.save();
						}
						
						if(movementID!=0)
						{
							MMovement movement = new MMovement(iol.getCtx(), movementID, iol.get_Trx());
							DocumentEngine.processIt(movement, X_M_Movement.DOCACTION_Complete);
							movement.save();							
						}
						if(inventoryID!=0)
						{
							MInventory inv = new MInventory(iol.getCtx(), inventoryID, iol.get_Trx());
							DocumentEngine.processIt(inv, X_M_Inventory.DOCACTION_Complete);
							inv.save();
						}
					}
					
				}
				
			}
		}
		return true;
	}
	
	public static MTransferReceipt get(MInOutLine iol)
	{
		MTransferReceipt tr = null;
		String sql = "SELECT * FROM XX_TransferReceipt "
				+ "WHERE M_InOutLine_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, iol.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, iol.getM_InOutLine_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				tr = new MTransferReceipt(iol.getCtx(), rs, iol.get_Trx());
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(tr==null)
		{
			MInOut io = new MInOut(iol.getCtx(), iol.getM_InOut_ID(), iol.get_Trx());
			tr = new MTransferReceipt(iol.getCtx(), 0, iol.get_Trx());
			tr.setClientOrg(iol);
			tr.setM_InOutLine_ID(iol.getM_InOutLine_ID());
			tr.setM_Product_ID(iol.getM_Product_ID());
			tr.setDateTrx(io.getMovementDate());
			tr.save();
		}
		return tr;
	}
	
	public static int getTargetLocator(int AD_Org_ID, Trx trx)
	{
		int locatorID = 0;
		String sql = "SELECT COALESCE(w.M_RcvLocator_ID, w.M_ReservationLocator_ID) AS M_Locator_ID "
				+ "FROM M_Warehouse w "
				+ "INNER JOIN AD_OrgInfo oi ON w.M_Warehouse_ID = oi.M_Warehouse_ID "
				+ "AND oi.AD_Org_ID = ? ";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trx);
			pstmt.setInt (1, AD_Org_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
				locatorID = rs.getInt(1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeStatement(pstmt);
			DB.closeResultSet(rs);
		}
		
		return locatorID;
	}
	
	public static boolean IsInventoryCheck(MPurchaseRequisitionLine prline)
	{
		if(prline.getAccount_ID()==0)
			return true;
		MAcctSchema as = new MAcctSchema(prline.getCtx(), MClientInfo.get(prline.getCtx(), prline.getAD_Client_ID()).getC_AcctSchema1_ID(), prline.get_Trx());
		MProductAcct pa = MProductAcct.get(prline.getM_Product_ID(), as);
		MAccount vc = new MAccount(prline.getCtx(), pa.getP_Asset_Acct(), prline.get_Trx());
		if(vc.getAccount_ID()==prline.getAccount_ID())
			return true;
		else
			return false;
	}


}
