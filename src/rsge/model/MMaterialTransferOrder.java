package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MProduct;
import org.compiere.model.X_M_Inventory;
import org.compiere.model.X_M_Product;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_MaterialTransferOrder;
import rsge.model.MProductCategory;

public class MMaterialTransferOrder extends X_XX_MaterialTransferOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MMaterialTransferOrder.class);


	public MMaterialTransferOrder(Ctx ctx, int XX_MaterialTransferOrder_ID, Trx trx) {
		super(ctx, XX_MaterialTransferOrder_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MMaterialTransferOrder(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	public MMaterialTransferOrder(MRequisitionLine reqLine) {
		super(reqLine.getCtx(), 0, reqLine.get_Trx());
		setClientOrg(reqLine);
		setM_Product_ID(reqLine.getM_Product_ID());
		MProduct p = new MProduct(getCtx(), reqLine.getM_Product_ID(), get_Trx());
		setC_UOM_ID(p.getC_UOM_ID());
		setM_RequisitionLine_ID(reqLine.getM_RequisitionLine_ID());
		MRequisition requisition = new MRequisition(getCtx(), reqLine.getM_Requisition_ID(), get_Trx());
		setDateExpected(requisition.getDateRequired());
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		if(isClosed())
			setDifferenceQty(getQtyExpected().subtract(getQtyDelivered()));
		return true;
	}
	
	public static MMaterialTransferOrder get(MRequisitionLine reqLine)
	{
		MMaterialTransferOrder mto = null;
		String sql = "SELECT * FROM XX_MaterialTransferOrder "
				+ "WHERE M_RequisitionLine_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, reqLine.get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, reqLine.getM_RequisitionLine_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				mto = new MMaterialTransferOrder(reqLine.getCtx(), rs, reqLine.get_Trx());
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}		

		if(mto==null)
			mto = new MMaterialTransferOrder(reqLine);
		
		return mto;
	}
	
	public static boolean generateAutoExpense(MInOut io)
	{		
		MInventory inv = new MInventory(io.getCtx(), 0, io.get_Trx());
		inv.setClientOrg(io);
		inv.setC_Activity_ID(io.getC_Activity_ID());
		inv.setC_Campaign_ID(io.getC_Campaign_ID());
		inv.setC_Project_ID(io.getC_Project_ID());
		inv.setM_Warehouse_ID(io.getM_Warehouse_ID());
		inv.setMovementDate(io.getMovementDate());
		inv.setDescription("Auto Expense Receipt #" + io.getDocumentNo());
		inv.setC_DocType_ID(getDocType(io.getAD_Client_ID(), io.get_Trx()));
		if(inv.save())
		{
			if(inv.getMovementDate()!=io.getMovementDate())
			{
				inv.setMovementDate(io.getMovementDate());
				inv.save();
			}
		}

		MInOutLine[] lines = io.getLines();
		for(MInOutLine iol : lines)
		{
			MProduct p = new MProduct(io.getCtx(), iol.getM_Product_ID(), io.get_Trx());
			if(!p.getProductType().equals(X_M_Product.PRODUCTTYPE_Item))
				continue;
			MProductCategory pc = new MProductCategory(p.getCtx(), p.getM_Product_Category_ID(), p.get_Trx());
			if(!pc.isAutoExpensed())
				continue;
			
			BigDecimal receiptQty = iol.getMovementQty();
			String sql = "SELECT rl.M_RequisitionLine_ID, (mto.QtyExpected-mto.QtyDelivered) AS Qty FROM M_InOutLine iol "
					+ "INNER JOIN M_RequisitionLine rl ON iol.C_OrderLine_ID = rl.C_OrderLine_ID "
					+ "INNER JOIN XX_MaterialTransferOrder mto ON rl.M_RequisitionLine_ID = mto.M_RequisitionLine_ID "
					+ "WHERE mto.QtyDelivered < mto.QtyExpected "
					+ "AND iol.M_InOut_ID = ? ";
			
			PreparedStatement pstmt = DB.prepareStatement(sql, io.get_Trx());
			ResultSet rs = null;
			
			try{
				pstmt.setInt(1, io.getM_InOut_ID());
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					if(receiptQty.signum()<=0)
						break;
					MRequisitionLine rl = new MRequisitionLine(io.getCtx(), rs.getInt(1), io.get_Trx());
					MMaterialTransferOrder mto = MMaterialTransferOrder.get(rl);
					BigDecimal requiredQty = mto.getQtyExpected().subtract(mto.getQtyDelivered());
					if(requiredQty.compareTo(receiptQty)>0)
						requiredQty = receiptQty;
					
					MInventoryLine il = new MInventoryLine(inv.getCtx(), 0, inv.get_Trx());
					il.setClientOrg(inv);
					il.setM_Inventory_ID(inv.getM_Inventory_ID());
					int orgID = rl.getAD_Org_ID();
					MRequisition req = new MRequisition(rl.getCtx(), rl.getM_Requisition_ID(), rl.get_Trx());
					if(req.getAD_OrgTrx_ID()!=0)
						orgID = req.getAD_OrgTrx_ID();
					il.setAD_Org_ID(orgID);
					il.setAD_OrgTrx_ID(rl.getAD_Org_ID());
					il.setM_Product_ID(iol.getM_Product_ID());
					il.setQtyInternalUse(requiredQty);
					il.setIsInternalUse(true);					
					il.setC_UOM_ID(p.getC_UOM_ID());
					il.setM_Locator_ID(iol.getM_Locator_ID());
//					if(rl.getBudgetCharge_ID()!=0)
//						il.setC_Charge_ID(rl.getBudgetCharge_ID());
//					else
//						il.setC_Charge_ID(pc.getC_Charge_ID());
					if(il.save())
					{
						MMaterialTransferOrderLine mtl = new MMaterialTransferOrderLine(mto);
						mtl.setM_InOutLine_ID(iol.getM_InOut_ID());
						mtl.setMovementDate(inv.getMovementDate());
						mtl.setQty(requiredQty);
						mtl.save();							
						receiptQty = receiptQty.subtract(requiredQty);
					}
				}							
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}			
		}
	
		DocumentEngine.processIt(inv, X_M_Inventory.DOCACTION_Complete);
		inv.save();
		log.info("Internal Used #" + inv.getDocumentNo());
		return true;
	}
	
	private static int getDocType(int AD_Client_ID, Trx trx)
	{
		int docTypeID = 0;
		// Get MInoutLine data 
		String sql = "SELECT C_DocType_ID FROM C_DocType " +
				"WHERE AD_Client_ID = ? " +
				"AND DocBaseType = 'MMI' ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, AD_Client_ID);
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


}
