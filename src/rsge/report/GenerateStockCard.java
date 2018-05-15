package rsge.report;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MBPartner;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MLocator;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MProduct;
import org.compiere.model.MTransaction;
import org.compiere.model.MUOM;
import org.compiere.model.MWarehouse;
import org.compiere.model.X_M_Transaction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MOrg;

public class GenerateStockCard extends SvrProcess {

	private int				p_AD_Org_ID = 0;
	private int				p_M_Warehouse_ID = 0;
	private int				p_M_Product_Category_ID = 0;
	private int				p_M_Product_ID = 0;
	private Timestamp		p_StartDate = null;
	private Timestamp		p_EndDate = null;
	private String			p_ProductValue = null;
	private String			p_ProductName = null;
	
	private int				p_C_UOM_ID = 0;
	private String			p_UOM = null;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null)
				;
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = element.getParameterAsInt();
			else if (name.equals("M_Warehouse_ID"))
				p_M_Warehouse_ID = element.getParameterAsInt();
			else if (name.equals("M_Product_Category_ID"))
				p_M_Product_Category_ID = element.getParameterAsInt();
			else if (name.equals("M_Product_ID"))
			{
				p_M_Product_ID = element.getParameterAsInt();
				MProduct product = new MProduct(getCtx(), p_M_Product_ID, get_Trx());
				p_ProductValue = product.getValue();
				p_ProductName = product.getName();
				
				MUOM uom = new MUOM(getCtx(), product.getC_UOM_ID(), get_Trx());
				p_C_UOM_ID = product.getC_UOM_ID();
				p_UOM = uom.getX12DE355();
			}
			else if (name.equals("MovementDate"))
			{
				p_StartDate = (Timestamp)element.getParameter();
				p_EndDate = (Timestamp)element.getParameter_To();
			}
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		// Delete Records
		resetTable();
		
		// Get Initial Qty
		// Return
		BigDecimal balance = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(t.MovementQty),0) AS MovementQty " +
				"FROM M_Transaction t " +
				"INNER JOIN M_Locator loc ON (t.M_Locator_ID = loc.M_Locator_ID) " +
				"WHERE t.M_Product_ID = ? " +
				"AND t.MovementDate < ? " +
				"AND loc.M_Warehouse_ID = ? ");
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, p_M_Product_ID);
			pstmt.setTimestamp(2, p_StartDate);
			pstmt.setInt(3, p_M_Warehouse_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				insertRecord(p_StartDate, "Beginning Balance", null, BigDecimal.ZERO, BigDecimal.ZERO, rs.getBigDecimal(1));
				balance = rs.getBigDecimal(1);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// 
		sql = new StringBuilder("SELECT t.* " +
				"FROM M_Transaction t " +
				"INNER JOIN M_Locator loc ON (t.M_Locator_ID = loc.M_Locator_ID) " +
				"WHERE t.M_Product_ID = ? " +
				"AND t.MovementDate BETWEEN ? AND ? " +
				"AND loc.M_Warehouse_ID = ? " +
				"ORDER BY t.MovementDate ");
	
		pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		rs = null;
		try{
			pstmt.setInt(1, p_M_Product_ID);
			pstmt.setTimestamp(2, p_StartDate);
			pstmt.setTimestamp(3, p_EndDate);
			pstmt.setInt(4, p_M_Warehouse_ID);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MTransaction t = new MTransaction(getCtx(), rs, get_Trx());
				balance = balance.add(t.getMovementQty());				
				BigDecimal inQty = BigDecimal.ZERO;
				BigDecimal outQty = BigDecimal.ZERO;				
				String documentNo = null;
				if(t.getMovementQty().compareTo(BigDecimal.ZERO)<0)
					outQty = t.getMovementQty().negate();				
				else
					inQty = t.getMovementQty();
				
				StringBuilder desc = new StringBuilder();
				if(t.getMovementType().equals(X_M_Transaction.MOVEMENTTYPE_CustomerReturns))
				{
					desc.append("Return from ");
					MInOutLine iol = new MInOutLine(getCtx(), t.getM_InOutLine_ID(), get_Trx());
					MInOut io = new MInOut(getCtx(), iol.getM_InOut_ID(), get_Trx());
					MBPartner bp = new MBPartner(getCtx(), io.getC_BPartner_ID(), get_Trx());
					desc.append(bp.getName());
					documentNo = io.getDocumentNo();
				}
				else if(t.getMovementType().equals(X_M_Transaction.MOVEMENTTYPE_InventoryIn))
				{
					if(t.getM_InventoryLine_ID()!=0)
					{
						desc.append("Physical Inventory ");
						MInventoryLine il = new MInventoryLine(getCtx(), t.getM_InventoryLine_ID(), get_Trx());
						MInventory i = new MInventory(getCtx(), il.getM_Inventory_ID(), get_Trx());
						documentNo = i.getDocumentNo();						
					}
					else if(t.getM_InOutLine_ID()!=0)
					{
						desc.append("Receive from ");
						MInOutLine iol = new MInOutLine(getCtx(), t.getM_InOutLine_ID(), get_Trx());
						MInOut io = new MInOut(getCtx(), iol.getM_InOut_ID(), get_Trx());
						MBPartner bp = new MBPartner(getCtx(), io.getC_BPartner_ID(), get_Trx());
						desc.append(bp.getName());
						documentNo = io.getDocumentNo();						
					}
				}
				else if(t.getMovementType().equals(X_M_Transaction.MOVEMENTTYPE_InventoryOut))
				{
					if(t.getM_InventoryLine_ID()!=0)
					{
						MInventoryLine il = new MInventoryLine(getCtx(), t.getM_InventoryLine_ID(), get_Trx());
						MInventory i = new MInventory(getCtx(), il.getM_Inventory_ID(), get_Trx());
						if(il.isInternalUse())
						{
							desc.append("Internal Use ");
							MOrg org = new MOrg(getCtx(), il.getAD_Org_ID(), get_Trx());
							desc.append(org.getName());
						}
						else
							desc.append("Physical Inventory ");					
						documentNo = i.getDocumentNo();						
					}
					else if(t.getM_InOutLine_ID()!=0)
					{
						MInOutLine iol = new MInOutLine(getCtx(), t.getM_InOutLine_ID(), get_Trx());
						MInOut io = new MInOut(getCtx(), iol.getM_InOut_ID(), get_Trx());
						MBPartner bp = new MBPartner(getCtx(), io.getC_BPartner_ID(), get_Trx());
						desc.append(bp.getName());
						documentNo = io.getDocumentNo();
					}
				}
				else if(t.getMovementType().equals(X_M_Transaction.MOVEMENTTYPE_MovementFrom))
				{
					desc.append("Move to ");
					MMovementLine ml = new MMovementLine(getCtx(), t.getM_MovementLine_ID(), get_Trx());
					MMovement m = new MMovement(getCtx(), ml.getM_Movement_ID(), get_Trx());
					MLocator loc = new MLocator(getCtx(), ml.getM_LocatorTo_ID(), get_Trx());
					MWarehouse w = new MWarehouse(getCtx(), loc.getM_Warehouse_ID(), get_Trx());
					desc.append(w.getName());
					documentNo = m.getDocumentNo();
				}
				else if(t.getMovementType().equals(X_M_Transaction.MOVEMENTTYPE_MovementTo))
				{
					desc.append("Receive from ");
					MMovementLine ml = new MMovementLine(getCtx(), t.getM_MovementLine_ID(), get_Trx());
					MMovement m = new MMovement(getCtx(), ml.getM_Movement_ID(), get_Trx());
					MLocator loc = new MLocator(getCtx(), ml.getM_Locator_ID(), get_Trx());
					MWarehouse w = new MWarehouse(getCtx(), loc.getM_Warehouse_ID(), get_Trx());
					desc.append(w.getName());
					documentNo = m.getDocumentNo();
				}
//				else if(t.getMovementType().equals(X_M_Transaction.MOVEMENTTYPE_VendorReceipts))
//				{
//				}
				else if(t.getMovementType().equals(X_M_Transaction.MOVEMENTTYPE_VendorReturns))
				{
					desc.append("Return To ");
					MInOutLine iol = new MInOutLine(getCtx(), t.getM_InOutLine_ID(), get_Trx());
					MInOut io = new MInOut(getCtx(), iol.getM_InOut_ID(), get_Trx());
					MBPartner bp = new MBPartner(getCtx(), io.getC_BPartner_ID(), get_Trx());
					desc.append(bp.getName());
					documentNo = io.getDocumentNo();
				}

				insertRecord(t.getMovementDate(), desc.toString(), documentNo, inQty, outQty, balance);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "Stock card generated";
	}
	
	private void resetTable()
	{
		String delete = "DELETE T_StockCard WHERE AD_Client_ID = ? ";
		DB.executeUpdate(get_Trx(), delete, getAD_Client_ID());
	}
	
	private void insertRecord(Timestamp movementDate, String description, String documentNo, BigDecimal qtyIn, BigDecimal qtyOut, BigDecimal qtyBalance)
	{		
		String insert = "INSERT INTO T_StockCard (AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, " +
				"MovementDate, Description, DocumentNo, C_UOM_ID, UOM, M_Product_Category_ID, M_Product_ID, ProductValue, ProductName, M_Warehouse_ID, " +
				"QtyIn, QtyOut, QtyBalance) " +
				"VALUES(?, ?, SysDate, ?, SysDate, ?, 'Y', " +
				"?,?,?,?,?,?,?,?,?,?, " +
				"?,?,?) ";
		
		DB.executeUpdate(get_Trx(), insert, getAD_Client_ID(), p_AD_Org_ID, getAD_User_ID(), getAD_User_ID(), 
				movementDate, description, documentNo, p_C_UOM_ID, p_UOM, p_M_Product_Category_ID, p_M_Product_ID, p_ProductValue, p_ProductName, p_M_Warehouse_ID, 
				qtyIn, qtyOut, qtyBalance);
	}

}
