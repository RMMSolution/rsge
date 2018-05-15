/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

import org.compiere.model.MInOutLine;

/**
 * @author Fanny
 *
 */
public class MInOut extends org.compiere.model.MInOut {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MInOut(Ctx ctx, int M_InOut_ID, Trx trx) {
		super(ctx, M_InOut_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MInOut(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	public MInOut(org.compiere.model.MInOut original, int C_DocTypeShipment_ID,
			Timestamp movementDate) {
		super(original, C_DocTypeShipment_ID, movementDate);
		// TODO Auto-generated constructor stub
	}

	public MInOut(MInvoice invoice, int C_DocTypeShipment_ID,
			Timestamp movementDate, int M_Warehouse_ID) {
		super(invoice, C_DocTypeShipment_ID, movementDate, M_Warehouse_ID);
		// TODO Auto-generated constructor stub
	}

	public MInOut(MOrder order, int C_DocTypeShipment_ID, Timestamp movementDate) {
		super(order, C_DocTypeShipment_ID, movementDate);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		return super.beforeSave(newRecord);
	}
	
	@Override
	public String prepareIt() {
		// 
		setAttributeSet();
		return super.prepareIt();
	}
	
	String m_processMsg = null;
	
	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
//		if(super.completeIt().equals(DocActionConstants.STATUS_Completed));
//		{	
			if(!isSOTrx() && !isReturnTrx() && checkAutoExpense())
				MTransferReceipt.generateAutroTransfer(this);
//				MMaterialTransferOrder.generateAutoExpense(this);
			
//			// Generate Non Item Allocation
//			generateNonItemAllocation(getCtx(), getM_InOut_ID(), get_Trx());
//		}
		return DocActionConstants.STATUS_Completed;
	}
	
	public static int generateNonItemAllocation(Ctx ctx, int M_InOut_ID, Trx trx)
	{
		int no = 0;
		StringBuilder sql = new StringBuilder("SELECT iol.M_InOutLine_ID, iol.AD_Org_ID, o.C_Currency_ID, ol.C_OrderLine_ID, "
				+ "io.MovementDate, iol.M_Product_ID, r.AD_OrgTrx_ID, r.C_Project_ID, r.C_Campaign_ID, rl.C_Activity_ID, ol.QtyOrdered, rl.Qty, ol.C_UOM_ID, "
				+ "iol.MovementQty, iol.M_Product_ID, ol.PriceActual, rl.M_RequisitionLine_ID "
				+ "FROM M_InOutLine iol "
				+ "INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID "
				+ "INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID "
				+ "INNER JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID "
				+ "INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID "
				+ "INNER JOIN M_RequisitionLine rl ON iol.C_OrderLine_ID = rl.C_OrderLine_ID "
				+ "INNER JOIN M_Requisition r ON rl.M_Requisition_ID = r.M_Requisition_ID "
				+ "WHERE p.ProductType NOT IN ('I') "
				+ "AND iol.AD_Org_ID != rl.AD_Org_ID "
				+ "AND NOT EXISTS (SELECT * FROM XX_NonItemAllocationLine il INNER JOIN XX_NonItemAllocation ia ON il.XX_NonItemAllocation_ID = ia.XX_NonItemAllocation_ID "
				+ "WHERE ia.M_InOutLine_ID = iol.M_InOutLine_ID AND il.M_RequisitionLine_ID = rl.M_RequisitionLine_ID) "
				+ "AND iol.AD_Client_ID = ? ");
		if(M_InOut_ID!=0)
			sql.append("AND io.M_InOut_ID = ? ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		
		int inOutLineID = 0;
		MNonItemAllocation ia = null;
		try{
			int index = 1;
			pstmt.setInt(index++, ctx.getAD_Client_ID());
			if(M_InOut_ID!=0)
				pstmt.setInt(index++, M_InOut_ID);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				if(rs.getInt("M_InOutLine_ID")!=inOutLineID)
				{
					ia = new MNonItemAllocation(ctx, 0, trx);
					ia.setAD_Org_ID(rs.getInt("AD_Org_ID"));
					ia.setC_Currency_ID(rs.getInt("C_Currency_ID"));
					ia.setC_OrderLine_ID(rs.getInt("C_OrderLine_ID"));
					ia.setDateAcct(rs.getTimestamp("MovementDate"));
					ia.setDateDoc(rs.getTimestamp("MovementDate"));
					ia.setM_InOutLine_ID(rs.getInt("M_InOutLine_ID"));
					ia.setM_Product_ID(rs.getInt("M_Product_ID"));
					ia.setProcessed(true);
					if(ia.save())
					{
						inOutLineID = rs.getInt("M_InOutLine_ID");
						no++;
					}
				}
				
				MNonItemAllocationLine line = new MNonItemAllocationLine(ia);
				line.setAD_OrgTrx_ID(rs.getInt("AD_OrgTrx_ID"));
				line.setC_Campaign_ID(rs.getInt("C_Campaign_ID"));
				line.setC_Project_ID(rs.getInt("C_Project_ID"));
				line.setC_Activity_ID(rs.getInt("C_Activity_ID"));
				line.setM_Product_ID(rs.getInt("M_Product_ID"));
				line.setC_UOM_ID(rs.getInt("C_UOM_ID"));
				line.setM_RequisitionLine_ID(rs.getInt("M_RequisitionLine_ID"));
				
				MUOM uom = new MUOM(ctx, rs.getInt("C_UOM_ID"), trx);
				BigDecimal qtyOrdered = rs.getBigDecimal("QtyOrdered");
				BigDecimal requestQty = rs.getBigDecimal("Qty");
				BigDecimal ratio = requestQty.divide(qtyOrdered, 4, RoundingMode.HALF_EVEN);
				BigDecimal allocatedQty = (rs.getBigDecimal("MovementQty").multiply(ratio)).setScale(uom.getStdPrecision(), RoundingMode.HALF_EVEN);
				line.setQty(allocatedQty);
				
				BigDecimal amount = rs.getBigDecimal("PriceActual").multiply(allocatedQty);
				line.setAllocatedAmt(amount);
				line.save();				
			}
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(ia!=null)
		{
			ia.setProcessed(true);
			ia.save();
		}
		
		return no;
	}
	
	
	private boolean checkAutoExpense()
	{
		boolean isAutoExpense = false;
		String sql = "SELECT 1 "
				+ "FROM M_InOutLine iol "
				+ "INNER JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID "
				+ "INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID "
				+ "INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID "
				+ "WHERE pc.IsAutoTransfer = 'Y' "
				+ "AND iol.M_InOut_ID = ? ";


		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getM_InOut_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				isAutoExpense = true;			
			pstmt.close();
			rs.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return isAutoExpense;
	}	
	
	private void setAttributeSet()
	{
		ArrayList<Integer> lines = new ArrayList<Integer>();
		// Get MInoutLine data 
		String sql = "SELECT M_InOutLine_ID " +
				"FROM M_InOutLine WHERE M_Product_ID != 0 " +
				"AND (M_AttributeSetInstance_ID IS NULL OR M_AttributeSetInstance_ID = 0) " +
				"AND M_InOut_ID =" + getM_InOut_ID();
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
				lines.add(rs.getInt(1));			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		if(lines.size()==0)
			return;
		
		for(int i = 0; i<lines.size(); i++)
		{
			MInOutLine line = new MInOutLine(getCtx(), lines.get(i), get_Trx());
			if(line.getM_AttributeSetInstance_ID()==0)
			{
				// Check product attribute set
				MProduct product = new MProduct(getCtx(), line.getM_Product_ID(), get_Trx());
				if(product.getM_AttributeSet_ID()!=0)
				{
					MAttributeSet as = new MAttributeSet(getCtx(), product.getM_AttributeSet_ID(), get_Trx());
					// Check setup - Whether use PO as Batch ID is set true or not
					MGeneralSetup setup = MGeneralSetup.get(getCtx(), getAD_Client_ID(), get_Trx());
					if(setup.isUsePOAsBatchID() && as.isLot())
					{
						// Create Batch ID
						StringBuilder batchID = new StringBuilder();
						MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_Trx());					
						batchID.append(order.getDocumentNo());
						batchID.append("-");
						batchID.append(product.getValue());
						
						// Create Batch
						MAttributeSetInstance asi = new MAttributeSetInstance(getCtx(), 0, as.getM_AttributeSet_ID(), get_Trx());
						asi.setLot(batchID.toString());
						asi.setAD_Client_ID(getAD_Client_ID());
						asi.setAD_Org_ID(0);
						
						// Set Guarantee Date
						if(as.isGuaranteeDate() && as.getGuaranteeDays()>0)
						{
							MInOut io = new MInOut(getCtx(), getM_InOut_ID(), get_Trx());
							Calendar cal = Calendar.getInstance();
							cal.setTime(io.getMovementDate());
							cal.add(Calendar.DAY_OF_YEAR, as.getGuaranteeDays());
							asi.setGuaranteeDate(new Timestamp(cal.getTimeInMillis()));
						}
						// Set Description
						StringBuilder desc = new StringBuilder("<<");
						desc.append(batchID);
						desc.append(">>");
						asi.setDescription(desc.toString());
						asi.save();
						line.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());		
						line.save();
					}			
				}
			}		

		}
	}

}
