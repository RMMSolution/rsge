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
import java.util.Map;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MConversionRate;
import org.compiere.model.MPriceList;
import org.compiere.model.MProductPO;
import org.compiere.model.MProject;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.X_I_Order;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

import rsge.model.MOrderLine;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author Fanny
 *
 */
public class MOrder extends org.compiere.model.MOrder {

    /** Logger for class MOrder */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MOrder.class);
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MOrder(Ctx ctx, int C_Order_ID, Trx trx) {
		super(ctx, C_Order_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MOrder(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	public MOrder(MProject project, boolean IsSOTrx, String DocSubTypeSO) {
		super(project, IsSOTrx, DocSubTypeSO);
		// TODO Auto-generated constructor stub
	}

	public MOrder(X_I_Order imp) {
		super(imp);
		// TODO Auto-generated constructor stub
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
    
    /** Set Discount Amount.
    @param DiscountAmt Calculated amount of discount */
    public void setDiscountAmt (java.math.BigDecimal DiscountAmt)
    {
        set_Value ("DiscountAmt", DiscountAmt);
        
    }
    
    /** Get Discount Amount.
    @return Calculated amount of discount */
    public java.math.BigDecimal getDiscountAmt() 
    {
        return get_ValueAsBigDecimal("DiscountAmt");
        
    }
    
    String errorMsg = null;    
    @Override
    protected boolean beforeSave(boolean newRecord) {
    	// Check String
    	if(getDocumentNo()!=null)
    		setDocumentNo(getDocumentNo().trim());
    	    	
    	return super.beforeSave(newRecord);
    }
    
    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {
    	// TODO Auto-generated method stub
    	return super.afterSave(newRecord, success);
    }
    
    @Override
    public String completeIt() {    
    	if(super.completeIt().equals(DocActionConstants.STATUS_Completed) && !isSOTrx() && !isReturnTrx())
    		MOrder.updateLines(this);
    	return DocActionConstants.STATUS_Completed;
    }
    
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		if(super.voidIt())
			voidRealization();
		return true;
	}
	
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		if(super.reverseCorrectIt())
		{
			voidRealization();
		}
		return true;
	}
	
	private void voidRealization()
	{
		ArrayList<MOrderLine> ol = new ArrayList<MOrderLine>();
		String sql = "SELECT * FROM C_OrderLine " +
				"WHERE C_Order_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getC_Order_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MOrderLine line = new MOrderLine(getCtx(), rs, get_Trx());
				ol.add(line);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static MOrder getPurchaseOrder(Ctx ctx, int AD_Org_ID, int vendorID, Timestamp datePromised, int priceListID, int repID, Trx trx)
	{
		MOrder order = null;
		String sql = "SELECT * FROM C_Order "
				+ "WHERE IsSOTrx = 'N' AND IsReturnTrx = 'N' "
				+ "AND DocStatus IN ('DR') "
				+ "AND C_BPartner_ID = ? "
				+ "AND DatePromised <= ? "
				+ "AND M_PriceList_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, vendorID);
			pstmt.setTimestamp(2, datePromised);
			pstmt.setInt(3, priceListID);
			rs = pstmt.executeQuery();
			if(rs.next())
				order = new MOrder(ctx, rs, trx);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(order==null)
		{
			order = new MOrder(ctx, 0, trx);
			order.setAD_Client_ID(ctx.getAD_Client_ID());
			order.setAD_Org_ID(AD_Org_ID);
			order.setC_DocTypeTarget_ID(getDocType(ctx.getAD_Client_ID(), trx));
			order.setC_DocType_ID(getDocType(ctx.getAD_Client_ID(), trx));
			order.setIsSOTrx(false);
			order.setIsReturnTrx(false);
			order.setC_BPartner_ID(vendorID);
			order.setC_BPartner_Location_ID(getBPLocation(vendorID, trx));
			order.setBill_Location_ID(getBPLocation(vendorID, trx));
			order.setM_PriceList_ID(priceListID);
			order.setSalesRep_ID(repID);
			order.save();
		}
		return order;
	}
	
	private static int getDocType(int AD_Client_ID, Trx trx)
	{
		int docTypeID = 0;
		String sql = "SELECT C_DocType_ID FROM C_DocType "
				+ "WHERE DocBaseType = 'POO' "
				+ "AND IsSOTrx = 'N' "
				+ "AND IsReturnTrx = 'N' "
				+ "AND IsReleaseDocument = 'N' "
				+ "AND AD_Client_ID = ? ";
		
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

	
	private static int getBPLocation(int C_BPartner_ID, Trx trx)
	{
		int bpLocationID = 0;
		String sql = "SELECT C_BPartner_Location_ID FROM C_BPartner_Location "
				+ "WHERE IsActive = 'Y' "
				+ "AND IsBillTo = 'Y' "
				+ "AND C_BPartner_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, C_BPartner_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				bpLocationID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return bpLocationID;
	}


	public static boolean updateLines(MOrder order)
	{
		ArrayList<MOrderLine> lines = null;
		String sql = "SELECT * FROM C_OrderLine WHERE C_Order_ID = " + order.getC_Order_ID();
		PreparedStatement pstmt = DB.prepareStatement(sql, order.get_Trx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				if(lines==null)
					lines = new ArrayList<MOrderLine>();
				MOrderLine line = new MOrderLine(order.getCtx(), rs, order.get_Trx());
				lines.add(line);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		boolean budgetStart =  GeneralEnhancementUtils.budgetStart(order.getCtx(), order.getDateOrdered(), order.get_Trx());

		for(MOrderLine line : lines)
		{
			// Update Purchasing Product
			MProductPO pp = MProductPO.getOfVendorProduct(order.getCtx(), order.getC_BPartner_ID(), line.getM_Product_ID(), order.get_Trx());
			if(pp!=null)
				pp.setPriceLastPO(line.getPriceActual());
			
			// Update Purchase Requisition
			Map<MPurchaseRequisitionLine[], BigDecimal> results = MPurchaseRequisitionLine.get(line);
			for(Map.Entry<MPurchaseRequisitionLine[], BigDecimal> entry : results.entrySet())
			{
				MPurchaseRequisitionLine[] prlines = entry.getKey();
				BigDecimal totalQty = line.getQtyEntered();
				BigDecimal ratio = BigDecimal.ZERO;
				if(line.getQtyEntered().compareTo(entry.getValue())>0)
					totalQty = entry.getValue();
				else
					ratio = totalQty.divide(entry.getValue(), 2, RoundingMode.HALF_EVEN);
				for(MPurchaseRequisitionLine prline : prlines)
				{
					BigDecimal lineQty = MUOMConversion.convertProductTo(line.getCtx(), line.getM_Product_ID(), line.getC_UOM_ID(), prline.getQty());
					if(ratio.signum()!=0)
					{
						MUOM uom = new MUOM(line.getCtx(), line.getC_UOM_ID(), line.get_Trx());
						lineQty = (lineQty.multiply(ratio)).setScale(uom.getStdPrecision(), RoundingMode.HALF_EVEN);						
					}
					BigDecimal qtyOrdered = lineQty;
					BigDecimal prPrice = prline.getPriceActual();
					BigDecimal poPrice = line.getPriceActual();
					if(line.getC_UOM_ID()!=prline.getC_UOM_ID())
						qtyOrdered = MUOMConversion.convertProductFrom(line.getCtx(), line.getM_Product_ID(), prline.getC_UOM_ID(), lineQty);					
					prline.setQtyOrdered(qtyOrdered);
					prline.setC_OrderLine_ID(line.getC_OrderLine_ID());
					if(budgetStart)
					{
						MBudgetInfo bi = MBudgetInfo.get(line.getCtx(),line.getAD_Client_ID(), line.get_Trx());
						MAcctSchema as = new MAcctSchema(line.getCtx(), bi.getC_AcctSchema_ID(), line.get_Trx());
						MPriceList pl = new MPriceList(line.getCtx(), order.getM_PriceList_ID(), line.get_Trx());

						MPurchaseRequisition pr = new MPurchaseRequisition(line.getCtx(), prline.getXX_PurchaseRequisition_ID(), line.get_Trx());
						if(as.getC_Currency_ID()!=pl.getC_Currency_ID())
						{
							prPrice = MConversionRate.convert(pr.getCtx(), prPrice, pr.getC_Currency_ID(), as.getC_Currency_ID(), pr.getDateDoc(), bi.getC_ConversionType_ID(), pr.getAD_Client_ID(), pr.getAD_Org_ID());
							poPrice = MConversionRate.convert(line.getCtx(), poPrice, order.getC_Currency_ID(), as.getC_Currency_ID(), order.getDateOrdered(), bi.getC_ConversionType_ID(), order.getAD_Client_ID(), order.getAD_Org_ID());
						}
						
						BigDecimal unrealizedAmt = lineQty.multiply(poPrice); 
						prline.setUnrealizedAmt(unrealizedAmt);

						MOrg org = new MOrg(pr.getCtx(), prline.getAD_Org_ID(), pr.get_Trx());
						System.out.println("PR Organization " + org.getName());
						MBudgetTransactionLine bline = MBudgetTransactionLine.createLine(line.getCtx(), line.get_Table_ID(), line.getC_OrderLine_ID(), 
								prline.get_Table_ID(), prline.getXX_PurchaseRequisitionLine_ID(), prline.getAD_Org_ID(), pr.getDateDoc(), prline.getAccount_ID(), 
								0, prline.getC_Activity_ID(), 0, pr.getC_Campaign_ID(), 0, 0, pr.getC_Project_ID(), 0, 0, 0, prline.getM_Product_ID(), 0, 0, 0, 0, 0, line.get_Trx());
						bline.setReservedAmt((prPrice.multiply(prline.getQtyOrdered())).negate());
						bline.setUnrealizedAmt(unrealizedAmt);
						bline.save();
					}				
					prline.save();
				}
			}			
		}
		return true;
	}		
}
