package rsge.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MMovementLine;
import org.compiere.model.MWarehouse;
import org.compiere.model.X_M_Movement;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MInOut;
import rsge.model.MInOutLine;
import rsge.model.MMaterialTransferOrder;
import rsge.model.MMaterialTransferOrderLine;
import rsge.model.MMovement;
import rsge.model.MOrg;
import rsge.model.MOrgInfo;
import rsge.model.MRequisitionLine;

public class GenerateInventoryMoveFromReceipt extends SvrProcess {

	private MInOut					p_MInOut = null;
	private Timestamp				p_MovementDate = null;
	private Map<Integer, Integer> 	movementList = new HashMap<Integer, Integer>();

	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] params = getParameter();
		for(ProcessInfoParameter param : params){
			String name = param.getParameterName();			
			if(name.equalsIgnoreCase("AD_Org_ID"))
				param.getParameterAsInt();
			else if(name.equalsIgnoreCase("M_Warehouse_ID"))
				param.getParameterAsInt();
			else if(name.equalsIgnoreCase("M_InOut_ID"))
				p_MInOut = new MInOut(getCtx(), param.getParameterAsInt(), get_Trx());
			else if(name.equalsIgnoreCase("MovementDate"))
				p_MovementDate = (Timestamp)param.getParameter();
			else
				System.out.println("Param " + name + " not found...");
			}

	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		String errorMsg = null;
		
		ArrayList<MInOutLine> iolList = new ArrayList<>();
		String sql = "SELECT * FROM M_InOutLine "
				+ "WHERE M_InOutLine_ID IN ("
				+ "SELECT io.M_InOutLine_ID FROM M_InOutLine io "
				+ "INNER JOIN M_RequisitionLine rl ON io.C_OrderLine_ID = rl.C_OrderLine_ID "
				+ "INNER JOIN XX_MaterialTransferOrder mto ON (rl.M_RequisitionLine_ID = mto.M_RequisitionLine_ID) "
				+ "WHERE mto.QtyExpected-mto.QtyDelivered > 0 "
				+ "AND mto.IsClosed != 'Y' ) "
				+ "AND M_InOut_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, p_MInOut.getM_InOut_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				iolList.add(new MInOutLine(getCtx(), rs, get_Trx()));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(iolList.size()!=0)
		{
			for(MInOutLine iol : iolList)
			{
				BigDecimal qty = iol.getMovementQty();
				sql = "SELECT mto.* FROM M_InOutLine io "
						+ "INNER JOIN M_RequisitionLine rl ON io.C_OrderLine_ID = rl.C_OrderLine_ID "
						+ "INNER JOIN XX_MaterialTransferOrder mto ON (rl.M_RequisitionLine_ID = mto.M_RequisitionLine_ID) "
						+ "WHERE mto.QtyExpected-mto.QtyDelivered > 0 "
						+ "AND mto.IsClosed != 'Y' "
						+ "AND io.M_InOutLine_ID = ? "
						+ "ORDER BY mto.AD_Org_ID ";
				
				pstmt = DB.prepareStatement(sql, get_Trx());
				rs = null;
				try{
					pstmt.setInt(1, iol.getM_InOutLine_ID());
					rs = pstmt.executeQuery();
					while(rs.next()){
						if(qty.signum()==0)
							break;
						MMaterialTransferOrder mto = new MMaterialTransferOrder(getCtx(), rs, get_Trx());
						MMovement movement = getMovementHeader(mto.getAD_Org_ID());
						if(movement==null)
						{
							MOrg org = new MOrg(getCtx(), mto.getAD_Org_ID(), get_Trx());
							errorMsg = "No designated warehouse for " + org.getName() + ". Please check warehouse setup in Organization > Info";
							break;
						}						
						
						// Generate Line
						BigDecimal lineQty = mto.getQtyExpected().subtract(mto.getQtyDelivered());
						if(lineQty.compareTo(qty)>0)
							lineQty = qty;
						MOrgInfo oi = new MOrgInfo(getCtx(), mto.getAD_Org_ID(), get_Trx());
						MWarehouse wh = new MWarehouse(getCtx(), oi.getM_Warehouse_ID(), get_Trx());
						MMovementLine ml = new MMovementLine(movement);
						MRequisitionLine rl = new MRequisitionLine(getCtx(), mto.getM_RequisitionLine_ID(), get_Trx());
						ml.setM_Locator_ID(iol.getM_Locator_ID());
						ml.setM_LocatorTo_ID(wh.getM_ReservationLocator_ID());
						ml.setM_Product_ID(rs.getInt("M_Product_ID"));
//						ml.setC_Activity_ID(rl.getC_Activity_ID());
						ml.setTargetQty(lineQty);
						ml.setMovementQty(lineQty);
						if(ml.save())
						{
							MMaterialTransferOrderLine mtl = new MMaterialTransferOrderLine(mto);
							mtl.setMovementDate(movement.getMovementDate());
							mtl.setM_MovementLine_ID(ml.getM_MovementLine_ID());
							mtl.setQty(lineQty);
							if(mtl.save())
								qty = qty.subtract(lineQty);
						}
					}
					rs.close();
					pstmt.close();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}					
				if(errorMsg!=null)
					break;
			}
			
			// Process MInOut
			if(movementList!=null)
			{
				for(Map.Entry<Integer, Integer> entry : movementList.entrySet())
				{
					MMovement movement = new MMovement(getCtx(), entry.getValue(), get_Trx());
					DocumentEngine.processIt(movement, X_M_Movement.DOCACTION_Complete);
					if(movement.save())
						Doc.postImmediate(MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID()), 323, movement.getM_Movement_ID(), true, get_Trx());
				}
			}
			
		}
		if(errorMsg!=null)
			return errorMsg;
		return null;
	}
	
	private MMovement getMovementHeader(int targetOrg)
	{
		MMovement movement = null;
		int movementID = 0;
		for(Map.Entry<Integer, Integer> entry : movementList.entrySet())
		{
			if(entry.getKey()==targetOrg)
				movementID = entry.getValue();
		}
		
		if(movementID!=0)
			movement = new MMovement(getCtx(), movementID, get_Trx());
		else
		{
			MOrgInfo oi = MOrgInfo.get(getCtx(), targetOrg, get_Trx());
			if(oi.getM_Warehouse_ID()==0)
				return movement;

			movement = new MMovement(getCtx(), 0, get_Trx());
			movement.setClientOrg(p_MInOut);
			String sql2 = "SELECT C_DocType_ID FROM C_DocType " +
					"WHERE AD_Client_ID = ? " +
					"AND DocBaseType = 'MMM' ";
			
			int docTypeID = 0;
			PreparedStatement pstmt2 = DB.prepareStatement(sql2, get_Trx());
			ResultSet rs2 = null;
			try{
				pstmt2.setInt(1, getAD_Client_ID());
				rs2 = pstmt2.executeQuery();
				if(rs2.next())
					docTypeID = rs2.getInt(1);
				rs2.close();
				pstmt2.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}		

			movement.setC_DocType_ID(docTypeID);
			movement.setDateReceived(p_MovementDate);
			movement.setMovementDate(p_MovementDate);
			MOrg org = new MOrg(getCtx(), oi.getAD_Org_ID(), get_Trx());
			movement.setDescription("Material Transfer from Receipt " + p_MInOut.getDocumentNo() + " - To " + org.getName());
			if(movement.save())
				movementList.put(targetOrg, movement.getM_Movement_ID());			
		}		
		return movement;
	}

}
