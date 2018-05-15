/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.compiere.model.MProductPO;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.utils.GeneralEnhancementUtils;

/**
 * @author Fanny
 *
 */
public class MOrderLine extends org.compiere.model.MOrderLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param C_OrderLine_ID
	 * @param trx
	 */
	public MOrderLine(Ctx ctx, int C_OrderLine_ID, Trx trx) {
		super(ctx, C_OrderLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MOrderLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public MOrderLine (MOrder order)
	{
		this (order.getCtx(), 0, order.get_Trx());
		if (order.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setC_Order_ID (order.getC_Order_ID());	//	parent
		setOrder(order);
	}	//	MOrderLine
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_Trx());
		if(order.isReturnTrx())
		{			
			MOrderLine origLine = new MOrderLine(getCtx(), getOrig_OrderLine_ID(), get_Trx());
			if(origLine.getPriceActual().compareTo(getPriceActual())!=0)
			{
				setPriceActual(origLine.getPriceActual());
				setPriceEntered(origLine.getPriceEntered());
				setPriceCost(origLine.getPriceCost());
				setPriceList(origLine.getPriceList());
			}
		}			
		return super.beforeSave(newRecord);
	}
	
	public static boolean multiplyLineNo(Ctx ctx, int C_Order_ID, int multiplier, Trx trx)
	{
		if(multiplier==0)
			multiplier = 10;
		String sql = "SELECT * FROM C_OrderLine WHERE C_Order_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, C_Order_ID);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MOrderLine ol = new MOrderLine(ctx, rs, trx);				
				int lineNo = rs.getInt(2)*multiplier;
				ol.setLine(lineNo);
				ol.save();
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
		return true;
	}
	
	public static boolean rearrangeLineNo(int C_Order_ID, Trx trx)
	{
		int no = 1;
		String sql = "SELECT C_OrderLine_ID, Line FROM C_OrderLine WHERE C_Order_ID = ? ORDER BY Line ASC ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, C_Order_ID);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				String update = "UPDATE C_OrderLine SET Line = ? WHERE C_OrderLine_ID = ? ";
				DB.executeUpdate(trx, update, no, rs.getInt(1));
				no++;
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
		return true;
	}
	
	public static int updateLines(Ctx ctx, MRequisitionLine line, int C_BPartner_ID, int repID, Timestamp dateRequired, int C_Order_ID, Trx trx)
	{
		MOrderLine ol = null;
		BigDecimal orderedQty = BigDecimal.ZERO;
		
		MRequisition req = new MRequisition(ctx, line.getM_Requisition_ID(), trx);
		if(dateRequired==null)
			dateRequired = req.getDateRequired();
		// Try to get open order with same product
		StringBuilder sql = new StringBuilder("SELECT ol.* FROM C_OrderLine ol "
				+ "INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID "
				+ "WHERE o.IsSOTrx = 'N' AND o.IsReturnTrx = 'N' "
				+ "AND o.DocStatus IN ('DR') "
				+ "AND o.C_BPartner_ID = ? "
//				+ "AND o.DatePromised <= ? "
				+ "AND o.M_PriceList_ID = ? ");
		if(line.getM_Product_ID()!=0)
			sql.append("AND ol.M_Product_ID = ? ");
		if(line.getC_Charge_ID()!=0)
			sql.append("AND ol.C_Charge_ID = ? ");
		sql.append("ORDER BY ol.QtyOrdered ");
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			int index=1;
			pstmt.setInt(index++, C_BPartner_ID);
//			pstmt.setTimestamp(index++, dateRequired);
			pstmt.setInt(index++, req.getM_PriceList_ID());
			if(line.getM_Product_ID()!=0)
				pstmt.setInt(index++, line.getM_Product_ID());
			if(line.getC_Charge_ID()!=0)
				pstmt.setInt(index++, line.getC_Charge_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				ol = new MOrderLine(ctx, rs, trx);
				orderedQty = getCumulativeQty(ol.getC_OrderLine_ID(), trx);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}		

		if(ol==null)
		{
			MOrder order = null;
			if(C_Order_ID!=0)
				order = new MOrder(ctx, C_Order_ID, trx);
			else
				order =	MOrder.getPurchaseOrder(ctx, req.getAD_Org_ID(), C_BPartner_ID, dateRequired, req.getM_PriceList_ID(), repID, trx);
			ol = new MOrderLine(order);
			if(line.getM_Product_ID()!=0)
			{
				MProduct p = new MProduct(ctx, line.getM_Product_ID(), trx);
				ol.setM_Product_ID(line.getM_Product_ID());
				ol.setC_UOM_ID(p.getC_UOM_ID());
			}
			else if(line.getC_Charge_ID()!=0)
				ol.setC_Charge_ID(line.getC_Charge_ID());
			ol.setPrice(line.getPriceActual());
			ol.setPriceList(line.getPriceActual());
		}		
		orderedQty = orderedQty.add(line.getQty());		
		if(line.getM_Product_ID()!=0)
			orderedQty = getOrderedProductQty(ctx, line.getM_Product_ID(), orderedQty, C_BPartner_ID, trx);
		ol.setQty(orderedQty);
		ol.save();
		return ol.getC_OrderLine_ID();
	}

	private static BigDecimal getCumulativeQty(int C_OrderLine_ID, Trx trx)
	{
		BigDecimal qty = BigDecimal.ZERO;
		String sql = "SELECT COALESCE(SUM(Qty),0) AS Qty FROM M_RequisitionLine WHERE C_OrderLine_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			int index=1;
			pstmt.setInt(index++, C_OrderLine_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				qty = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
		return qty;
	}
	
	private static BigDecimal getOrderedProductQty(Ctx ctx, int M_Product_ID, BigDecimal orderedQty, int vendorID, Trx trx)
	{
		BigDecimal qty = BigDecimal.ZERO;
		MProductPO pp = MProductPO.getOfVendorProduct(ctx, vendorID, M_Product_ID, trx);
		if(pp==null)
			return orderedQty;
		if(pp.getOrder_Min()!=null)
		{
			if(pp.getOrder_Min().signum()!=0)
			{
				if(orderedQty.compareTo(pp.getOrder_Min())<0)				
					orderedQty = pp.getOrder_Min();		
			}
			else
				qty = orderedQty;
		}
		else
			qty = orderedQty;
		
		if(pp.getOrder_Pack()!=null)
		{
			if(pp.getOrder_Pack().signum()!=0)
			{
				if(pp.getOrder_Pack().signum()!=0 && orderedQty.compareTo(pp.getOrder_Pack())!=0)
				{
					if(orderedQty.compareTo(pp.getOrder_Pack())<=0)
						qty = pp.getOrder_Pack();
					else
					{				
						do{
							qty = qty.add(pp.getOrder_Pack());
							if(orderedQty.compareTo(pp.getOrder_Pack())>0)
								orderedQty = orderedQty.subtract(pp.getOrder_Pack());
							else
								orderedQty = orderedQty.subtract(orderedQty);
						}
						while(orderedQty.signum()!=0);
					}
				}			
			}
			else
				qty = orderedQty;
		}		
		else
			qty = orderedQty;

		return qty;
	}

	
    /** Set Sales Region.
    @param C_SalesRegion_ID Sales coverage region */
    public void setC_SalesRegion_ID (int C_SalesRegion_ID)
    {
        if (C_SalesRegion_ID <= 0) set_Value ("C_SalesRegion_ID", null);
        else
        set_Value ("C_SalesRegion_ID", Integer.valueOf(C_SalesRegion_ID));
        
    }
    
    /** Get Sales Region.
    @return Sales coverage region */
    public int getC_SalesRegion_ID() 
    {
        return get_ValueAsInt("C_SalesRegion_ID");
        
    }
    
    public static int createUpdateLineFromRequisition(MRequisitionLine rl, int vendorID, Timestamp dateRequired)
    {
    	MOrderLine ol = null;
    	StringBuilder sql = new StringBuilder("SELECT ol.* FROM C_OrderLine ol "
    			+ "INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID "
    			+ "WHERE o.IsSoTrx = 'N' AND o.IsReturnTrx = 'N' "
    			+ "AND o.DocStatus IN ('DR') "
    			+ "AND o.C_BPartner_ID = ? "
    			+ "AND o.DatePromised <= ? ");

    	
    	if(rl.getM_Product_ID()!=0)
    		sql.append("AND ol.M_Product_ID = ? ");
    	else if(rl.getC_Charge_ID()!=0)
    		sql.append("AND ol.C_Charge_ID = ? ");
    	
    	PreparedStatement pstmt = DB.prepareStatement(sql.toString(), rl.get_Trx());
    	ResultSet rs = null;
    	try{
    		int index = 1;
    		pstmt.setInt(index++, vendorID);
    		pstmt.setTimestamp(index++, dateRequired);
        	if(rl.getM_Product_ID()!=0)
        		pstmt.setInt(index++, rl.getM_Product_ID());
        	else if(rl.getC_Charge_ID()!=0)
        		pstmt.setInt(index++, rl.getC_Charge_ID());
    		rs = pstmt.executeQuery();
    		if(rs.next())
    			ol = new MOrderLine(rl.getCtx(), rs, rl.get_Trx());
    		rs.close();
    		pstmt.close();
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
    	if(ol==null)
    	{
    	}
    	return 0;
    }
    
	public boolean recordLineTransaction()
	{
		ArrayList<MPurchaseRequisitionLine> prlines = new ArrayList<>();
		BigDecimal totalQty = BigDecimal.ZERO;
		String sql = "SELECT prl.* FROM XX_PurchaseRequisitionLine prl "
				+ "INNER JOIN M_RequisitionLine rl ON prl.M_RequisitionLine_ID = rl.M_RequisitionLine_ID "
				+ "INNER JOIN C_OrderLine ol ON rl.C_OrderLine_ID = ol.C_OrderLine_ID "
				+ "WHERE ol.C_OrderLine_ID = ? ";
				
    	PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
    	ResultSet rs = null;
    	try{
    		int index = 1;
    		pstmt.setInt(index++, getC_OrderLine_ID());
    		rs = pstmt.executeQuery();
    		while(rs.next())
    		{
    			MPurchaseRequisitionLine prl = new MPurchaseRequisitionLine(getCtx(), rs, get_Trx());
    			totalQty = totalQty.add(prl.getQty());
    			prlines.add(prl);
    		}
    		rs.close();
    		pstmt.close();
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
    	MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_Trx());
    	for(MPurchaseRequisitionLine prl : prlines)
    	{
    		BigDecimal reservedAmt = prl.getQty().multiply(prl.getPriceActual());
    		MPurchaseRequisition pr = new MPurchaseRequisition(getCtx(), prl.getXX_PurchaseRequisition_ID(), get_Trx());
    		reservedAmt = GeneralEnhancementUtils.calculateBudgetAmt(getCtx(), reservedAmt, prl.getAD_Org_ID(), pr.getC_Currency_ID(), pr.getDateDoc(), get_Trx());
    		
    		BigDecimal unrealizedAmt = prl.getQty().multiply(getPriceActual());
    		unrealizedAmt = GeneralEnhancementUtils.calculateBudgetAmt(getCtx(), unrealizedAmt, order.getAD_Org_ID(), order.getC_Currency_ID(), order.getDateOrdered(), get_Trx());
    		MBudgetTransactionLine bLine = MBudgetTransactionLine.createLine(getCtx(), get_Table_ID(), getC_OrderLine_ID(), prl.get_Table_ID(), prl.getXX_PurchaseRequisitionLine_ID(), getAD_Org_ID(), pr.getDateDoc(), prl.getAccount_ID(), 0, getC_Activity_ID(), 0, pr.getC_Campaign_ID(), 0, 0, pr.getC_Project_ID(), 0, 0, 0, getM_Product_ID(), 0, 0, 0, 0, 0, get_Trx());
    		bLine.setReservedAmt(reservedAmt.negate());
    		bLine.setUnrealizedAmt(unrealizedAmt);
    		bLine.save();
    	}
		return true;
	}

    
}
