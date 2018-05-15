/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MUOMConversion;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

import rsge.po.X_XX_QuickReturnLineBatch;

/**
 * @author Fanny
 *
 */
public class MQuickReturnLineBatch extends X_XX_QuickReturnLineBatch {
    /** Logger for class MQuickReturnLineBatch */
//    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MQuickReturnLineBatch.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_QuickReturnLineBatch_ID
	 * @param trx
	 */
	public MQuickReturnLineBatch(Ctx ctx, int XX_QuickReturnLineBatch_ID,
			Trx trx) {
		super(ctx, XX_QuickReturnLineBatch_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MQuickReturnLineBatch(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MQuickReturnLineBatch(MQuickReturnLine line) {
		super(line.getCtx(), 0, line.get_Trx());
		setXX_QuickReturnLine_ID(line.getXX_QuickReturnLine_ID());
		setAD_Org_ID(line.getAD_Org_ID());
		setM_Product_ID(line.getM_Product_ID());
		// TODO Auto-generated constructor stub
	}

	
	public static String insertProductBatch(Ctx ctx, MQuickReturnLine line, String batch, BigDecimal Qty, int C_UOM_ID, Timestamp guaranteeDate, Trx trx)
	{
		boolean createNewBatch = false;
		
		// Get M_AttributeSetInstance_ID based on batch
		int M_AttributeSetInstance_ID = 0;
		Timestamp Guaranteedate = null;
		String sql = "SELECT M_AttributeSetInstance_ID, GuaranteeDate FROM M_AttributeSetInstance WHERE Lot = ? AND AD_Client_ID = ? AND GuaranteeDate = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, line.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setString(1, batch);
			pstmt.setInt(2, line.getAD_Client_ID());
			pstmt.setTimestamp(3, guaranteeDate);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				M_AttributeSetInstance_ID = rs.getInt(1);
				Guaranteedate = rs.getTimestamp(2);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		// If no attribute set instance found, create new one
		MProduct product = new MProduct(line.getCtx(), line.getM_Product_ID(), line.get_Trx());
		if(M_AttributeSetInstance_ID==0 && product.getM_AttributeSet_ID()>0)
		{
			MAttributeSetInstance asi = new MAttributeSetInstance(line.getCtx(), 0, line.get_Trx());
			asi.setLot(batch);			
			asi.setM_AttributeSet_ID(product.getM_AttributeSet_ID());
			StringBuilder desc = new StringBuilder("<<");
			desc.append(batch);
			desc.append(">>");
			if(guaranteeDate!=null)
				asi.setGuaranteeDate(guaranteeDate);				
			asi.setDescription(desc.toString());
			if(asi.save())
			{
				M_AttributeSetInstance_ID = asi.getM_AttributeSetInstance_ID();
				createNewBatch = true;
			}
		}
		
		// if found, check batch product
		if(M_AttributeSetInstance_ID>0 && !createNewBatch)
		{
			int batchProductID = 0;
			sql = "SELECT M_Product_ID FROM M_Storage_V WHERE M_AttributeSetInstance_ID = ? AND AD_Client_ID = ? ";
			pstmt = DB.prepareStatement(sql, line.get_Trx());
			rs = null;
			
			try{
				pstmt.setString(1, batch);
				pstmt.setInt(2, line.getAD_Client_ID());
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					batchProductID = rs.getInt(1);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			if(batchProductID>0 && line.getM_Product_ID()!=batchProductID)
				return Msg.getMsg(line.getCtx(), "Batch Product <> Product");			
		}
		
		// Create/Update record
		MQuickReturnLineBatch lineBatch = null;
		sql = "SELECT * FROM XX_QuickReturnLineBatch WHERE XX_QuickReturnLine_ID = ? AND M_Product_ID = ? AND M_AttributeSetInstance_ID = ? ";
		pstmt = DB.prepareStatement(sql, line.get_Trx());
		rs = null;
		
		try{
			pstmt.setInt(1, line.getXX_QuickReturnLine_ID());
			pstmt.setInt(2, line.getM_Product_ID());
			pstmt.setInt(3, M_AttributeSetInstance_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				lineBatch = new MQuickReturnLineBatch(line.getCtx(), rs, line.get_Trx());
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
		
		if(lineBatch==null)
		{
			lineBatch = new MQuickReturnLineBatch(line);
			lineBatch.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		}
		lineBatch.setQtyEntered(lineBatch.getQty().add(Qty));
		lineBatch.setC_UOM_ID(C_UOM_ID);
		if(Guaranteedate == null)
			Guaranteedate = guaranteeDate;
		lineBatch.setGuaranteeDate(Guaranteedate);
		lineBatch.setBatchID(batch);
		if(lineBatch.save()){
			MQuickReturn quickReturn = new MQuickReturn(ctx, line.getXX_QuickReturn_ID(), trx);
			MQuickReturnLineBatch batch2 = new MQuickReturnLineBatch(ctx, lineBatch.getXX_QuickReturnLineBatch_ID(), trx);
			int OrderLine = 0;
			OrderLine = checkSO(line.getCtx(), quickReturn, line, batch2,  line.get_Trx());
			if(OrderLine > 0){
				MOrderLine mOrderLine = new MOrderLine(ctx, OrderLine, trx);
				MReturnBatchRelatedDoc.createRelatedDoc(ctx, batch2, OrderLine, 
						mOrderLine.getQtyDelivered(), mOrderLine.getQtyOrdered(), mOrderLine.getQtyReturned(), trx);
			}
		}
		return null;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// Convert Quantity Entered to Product Qty
		BigDecimal productQty = MUOMConversion.convertProductTo(getCtx(), getM_Product_ID(), getC_UOM_ID(), getQtyEntered());
		setQty(productQty);				
		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		updateHeaderQty();
		return true;
	}
	
	@Override
	protected boolean afterDelete(boolean success) {
		updateHeaderQty();
		return true;
	}
	
	private void updateHeaderQty()
	{
		BigDecimal qty = BigDecimal.ZERO;
		String sql = "SELECT COALESCE(SUM(Qty),0) FROM XX_QuickReturnLineBatch " +
				"WHERE XX_QuickReturnLine_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_QuickReturnLine_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				qty = rs.getBigDecimal(1);
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

		String update = "UPDATE XX_QuickReturnLine SET Qty = ? WHERE XX_QuickReturnLine_ID = ?";
		DB.executeUpdate(get_Trx(), update, qty, getXX_QuickReturnLine_ID());
	}
	
	
	 /** Set BatchID.
    @param BatchID Optional short BatchID of the record */
    public void setBatchID (String BatchID)
    {
        set_Value ("BatchID", BatchID);
        
    }
    
    /** Get BatchID.
    @return Optional short BatchID of the record */
    public String getBatchID() 
    {
        return (String)get_Value("BatchID");
        
    }
    
    /** Set Guarantee Date.
    @param GuaranteeDate Date of the Document */
    public void setGuaranteeDate (Timestamp GuaranteeDate)
    {
        set_Value ("GuaranteeDate", GuaranteeDate);
        
    }
    
    /** Get Guarantee Date.
    @return Date of the Document */
    public Timestamp getGuaranteeDate() 
    {
        return (Timestamp)get_Value("GuaranteeDate");
        
    }
    
    public static int checkSO(Ctx ctx, MQuickReturn quickReturn, MQuickReturnLine returnLine, MQuickReturnLineBatch batch, Trx trx){
    	String sql = "SELECT ol.C_OrderLine_ID FROM C_OrderLine ol " +
    			"INNER JOIN C_Order o ON (o.C_Order_ID = ol.C_Order_ID) " +
    			"INNER JOIN M_InOutLine iol ON (ol.C_OrderLine_ID = iol.C_OrderLine_ID)  " +
    			" WHERE o.DocStatus IN ('CO','CL') " +
    			"AND ol.M_Product_ID = "+returnLine.getM_Product_ID()+" AND o.C_BPartner_ID = "+quickReturn.getCustomer_ID()+" " +
    					"AND iol.M_AttributeSetInstance_ID = "+batch.getM_AttributeSetInstance_ID();
    	PreparedStatement ps = DB.prepareStatement(sql, trx);
    	ResultSet rs = null;
    	try {
    		rs = ps.executeQuery();
    		if(rs.next())
    			return rs.getInt(1);
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return 0;
    }
}
