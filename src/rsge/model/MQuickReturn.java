/**
 * 
 */
package rsge.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MBPartner;
import org.compiere.model.MClientInfo;
import org.compiere.model.X_M_InOut;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env.QueryParams;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_XX_QuickReturn;

/**
 * @author Fanny
 *
 */
public class MQuickReturn extends X_XX_QuickReturn implements DocAction{
    /** Logger for class MQuickReturn */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MQuickReturn.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_QuickReturn_ID
	 * @param trx
	 */
	public MQuickReturn(Ctx ctx, int XX_QuickReturn_ID, Trx trx) {
		super(ctx, XX_QuickReturn_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MQuickReturn(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// Check whether selected business partner has specific return policy. If not, check return policy default
		if(!checkReturnPolicy(getCustomer_ID()))
			log.saveError("Error", Msg.getMsg(getCtx(), "ReturnPolicyExceeded"));

		return true;
	}

	/**
	 * Check Return policy of business partner or tenant's default
	 * @param C_BPartner_ID
	 * @return
	 */
	private boolean checkReturnPolicy(int C_BPartner_ID)
	{
		boolean hasPolicy = false;
		String check = "SELECT COALESCE(bp.M_ReturnPolicy_ID,rp.M_ReturnPolicy_ID) AS M_ReturnPolicy_ID " +
				"FROM C_BPartner bp INNER JOIN M_ReturnPolicy rp ON (bp.AD_Client_ID = rp.AD_Client_ID) " +
				"WHERE bp.C_BPartner_ID = ? AND rp.IsDefault = 'Y' ";
		PreparedStatement pstmt = DB.prepareStatement(check, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, C_BPartner_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				hasPolicy = true;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		return hasPolicy;
	}
	
	@Override
	public boolean approveIt() {
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}	//	approveIt

	@Override
	public boolean closeIt() {
		log.info("closeIt - " + toString());
		return true;
	}	//	closeIt

	@Override
	public String completeIt() {
		MQuickReturn quickReturn = new MQuickReturn(getCtx(), getXX_QuickReturn_ID(), get_Trx());
		MOrder mOrder = createHeaderOrder(getCtx(), quickReturn, get_Trx());
		if(mOrder != null){
			MInOut mInOut = creaInOut(getCtx(), quickReturn, mOrder, get_Trx());
			MQuickReturnLine[] lines = getLines();
			
			if(lines.length > 0){
				for(MQuickReturnLine line:lines){
					MQuickReturnLine quickReturnLine = new MQuickReturnLine(getCtx(), line.getXX_QuickReturnLine_ID(), get_Trx());
					MQuickReturnLineBatch[] batchs = getLinesBatchs(line.getXX_QuickReturnLine_ID());
					if(batchs.length > 0){
						for(MQuickReturnLineBatch batch:batchs){
							MQuickReturnLineBatch returnLineBatch = new MQuickReturnLineBatch(getCtx(), batch.getXX_QuickReturnLineBatch_ID(), get_Trx());
							MReturnBatchRelatedDoc[] docs = getretuDRelatedDocs(batch.getXX_QuickReturnLineBatch_ID());
							if(docs.length > 0){
								for(MReturnBatchRelatedDoc doc:docs){
									MOrderLine line2 = new MOrderLine(getCtx(), doc.getC_OrderLine_ID(), get_Trx());
									MOrder mOrder2 = new MOrder(getCtx(), line2.getC_Order_ID(), get_Trx());
									MInOutLine line3 = new MInOutLine(getCtx(), doc.getM_InOutLine_ID(), get_Trx());
									MInOut mInOut2 = new MInOut(getCtx(), line3.getM_InOut_ID(), get_Trx());
									mOrder.setOrig_Order_ID(mOrder2.getC_Order_ID());
									mOrder.setOrig_InOut_ID(mInOut2.getM_InOut_ID());
									mOrder.save();
									mInOut.save();
									MReturnBatchRelatedDoc doc2 = new MReturnBatchRelatedDoc(getCtx(), doc.getXX_ReturnBatchRelatedDoc_ID(), get_Trx());
									MOrderLine mOrderLine = creaOrderLine(getCtx(), mOrder, quickReturnLine, returnLineBatch, doc2, get_Trx());
									if(mOrderLine != null && mInOut != null){
										creaInOutLine(getCtx(), getAD_OrgTrx_ID(), mInOut, mOrderLine, quickReturn, quickReturnLine, returnLineBatch, doc2, get_Trx());
									}
								}
							}
						}
					}
				}
			}
		}
		
//		MQuickReturnLine[] lines = getLines();
//		
//		for(MQuickReturnLine line : lines)
//		{
//			// Get Original Order
//			StringBuilder sql = new StringBuilder("SELECT ol.C_OrderLine_ID, iol.M_InOutLine_ID, iol.M_AttributeSetInstance_ID, (ol.QtyDelivered-ol.QtyReturned) AS Qty " +
//					"FROM M_InOutLine iol " +
//					"INNER JOIN M_InOut io ON (io.M_InOut_ID=iol.M_InOut_ID) " +
//					"INNER JOIN C_OrderLine ol ON (iol.C_OrderLine_ID = ol.C_OrderLine_ID) " +
//					"INNER JOIN C_Order o ON (ol.C_Order_ID = o.C_Order_ID) " +
//					"WHERE io.IsSOTrx = 'Y' AND io.IsReturnTrx = 'N' " +
//					"AND io.DocStatus IN ('CO', 'CL') " +
//					"AND io.MovementDate < ? AND (ol.QtyDelivered-ol.QtyReturned)>0 " +
//					"AND iol.M_Product_ID = ? " +
//					"ORDER BY o.DateOrdered, iol.M_AttributeSetInstance_ID DESC " );
//
//			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
//			ResultSet rs = null;
//			try{
//				pstmt.setTimestamp(1, getDateDoc());
//				pstmt.setInt(2, line.getM_Product_ID());
//
//				pstmt.close();
//				rs.close();
//			}catch(Exception e)
//			{
//				e.printStackTrace();
//			}			
//
//			for(MQuickReturnLineBatch batch : line.getLines())
//			{
//
//			}
//
//		}
		
		return DocActionConstants.STATUS_Completed;
	}

	@Override
	public File createPDF() {
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	public File createPDF (File file)
	{
		return null;
	}
	
	@Override
	public BigDecimal getApprovalAmt() {		
		return BigDecimal.ZERO;
	}

	@Override
	public int getC_Currency_ID() {
		return 0;
	}

	@Override
	public String getDocBaseType() {
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		return getCreatedBy();
	}

	@Override
	public Timestamp getDocumentDate() {
		return getDateDoc();
	}

	@Override
	public String getDocumentInfo() {
		return "";
	}	//	getDocumentInfo

	@Override
	public QueryParams getLineOrgsQueryInfo() {
		return null;
	}

	String m_processMsg;

	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		return sb.toString();
	}	//	getSummary

	@Override
	public boolean invalidateIt() {
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}	//	invalidateIt

	ArrayList<MQuickReturnLineBatch> batchList = null;
	
	@Override
	public String prepareIt() {
		if(getAD_OrgTrx_ID()==0)
			setAD_OrgTrx_ID(getAD_Org_ID());
		return DocActionConstants.STATUS_InProgress;
	}	//	prepareIt
	
	@Override
	public boolean reActivateIt() {
		log.info("reActivateIt - " + toString());
		return DocumentEngine.processIt(this, DocActionConstants.ACTION_Reverse_Correct);
	}	//	reActivateIt

	@Override
	public boolean rejectIt() {
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}//	rejectIt

	@Override
	public boolean reverseAccrualIt() {
		log.info("reverseAccrualIt - " + toString());
		return false;
	}//	reverseAccrualIt

	@Override
	public boolean reverseCorrectIt() {
		log.info("reverseCorrectIt - " + toString());
		return false;
	}//	reverseCorrectionIt

	@Override
	public void setProcessMsg(String processMsg) {
		m_processMsg = processMsg;
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}//	unlockIt

	@Override
	public boolean voidIt() {
		log.info("voidIt - " + toString());
		
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return false;
	}//	voidIt

	private MQuickReturnLine[] getLines()
	{
		// Get Batch List
		ArrayList<MQuickReturnLine> lines = new ArrayList<MQuickReturnLine>();
		String sql = "SELECT * FROM XX_QuickReturnLine " +
				"WHERE XX_QuickReturn_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_QuickReturn_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MQuickReturnLine line = new MQuickReturnLine(getCtx(), rs, get_Trx());
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
		
		MQuickReturnLine[] retValue = new MQuickReturnLine[lines.size()];
		lines.toArray(retValue);
		return retValue;
	}
	
	private MQuickReturnLineBatch[] getLinesBatchs(int XX_QuickReturnLine_ID)
	{
		// Get Batch List
		ArrayList<MQuickReturnLineBatch> lines = new ArrayList<MQuickReturnLineBatch>();
		String sql = "SELECT * FROM XX_QuickReturnLineBatch " +
				"WHERE XX_QuickReturnLine_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, XX_QuickReturnLine_ID);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MQuickReturnLineBatch line = new MQuickReturnLineBatch(getCtx(), rs, get_Trx());
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
		
		MQuickReturnLineBatch[] retValue = new MQuickReturnLineBatch[lines.size()];
		lines.toArray(retValue);
		return retValue;
	}
	
	
	private MReturnBatchRelatedDoc[] getretuDRelatedDocs(int XX_QuickReturnLineBatch_ID)
	{
		// Get Batch List
		ArrayList<MReturnBatchRelatedDoc> lines = new ArrayList<MReturnBatchRelatedDoc>();
		String sql = "SELECT * FROM XX_ReturnBatchRelatedDoc " +
				"WHERE XX_QuickReturnLineBatch_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, XX_QuickReturnLineBatch_ID);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MReturnBatchRelatedDoc line = new MReturnBatchRelatedDoc(getCtx(), rs, get_Trx());
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
		
		MReturnBatchRelatedDoc[] retValue = new MReturnBatchRelatedDoc[lines.size()];
		lines.toArray(retValue);
		return retValue;
	}
	
	public static MOrder createHeaderOrder(Ctx ctx, MQuickReturn quickReturn, Trx trx){
		MOrder order = new MOrder(ctx, 0, trx);
		order.setAD_Client_ID(ctx.getAD_Client_ID());
		order.setAD_Org_ID(quickReturn.getAD_Org_ID());
		order.setIsSOTrx(true);
		order.setIsReturnTrx(true);
		order.setC_DocTypeTarget_ID(docTypeRMA(ctx, true, trx));
		order.setDateOrdered(new Timestamp(System.currentTimeMillis()));
		MBPartner partner = new MBPartner(ctx, quickReturn.getCustomer_ID(), trx);
		order.setC_BPartner_ID(quickReturn.getCustomer_ID());
		order.setC_BPartner_Location_ID(quickReturn.getC_BPartner_Location_ID());
		order.setM_Warehouse_ID(quickReturn.getM_Warehouse_ID());
		order.setM_ReturnPolicy_ID(quickReturn.getM_ReturnPolicy_ID());
		order.setM_PriceList_ID(partner.getM_PriceList_ID());
		MClientInfo info = MClientInfo.get(ctx, ctx.getAD_Client_ID());
		MAcctSchema as = new MAcctSchema(ctx, info.getC_AcctSchema1_ID(), trx);
		order.setC_Currency_ID(as.getC_Currency_ID());
		order.setC_ConversionType_ID(conversionType(ctx, trx));
		order.setC_DocType_ID(docTypeRMA(ctx, true, trx));
		order.setSalesRep_ID(salesRep_ID(trx));
		order.save();
		return order;
	}
	
	public static MOrderLine creaOrderLine(Ctx ctx, MOrder order, MQuickReturnLine quickline, MQuickReturnLineBatch batch, MReturnBatchRelatedDoc relatedDoc, Trx trx){
		MOrderLine line = new MOrderLine(ctx, 0, trx);
		line.setAD_Client_ID(ctx.getAD_Client_ID());
		line.setAD_Org_ID(order.getAD_Org_ID());
		line.setC_BPartner_ID(order.getC_BPartner_ID());
		line.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
		line.setLine(lineNo(ctx, "C_OrderLine", "C_Order", "Line", order.getC_Order_ID(), trx));
		line.setOrig_OrderLine_ID(relatedDoc.getC_OrderLine_ID());
		line.setOrig_InOutLine_ID(relatedDoc.getM_InOutLine_ID());
		line.setC_Order_ID(order.getC_Order_ID());
		line.setM_Warehouse_ID(order.getM_Warehouse_ID());
		line.setM_Product_ID(quickline.getM_Product_ID());
		line.setM_AttributeSetInstance_ID(batch.getM_AttributeSetInstance_ID());
		line.setQtyEntered(relatedDoc.getQtyEntered());
		line.setC_UOM_ID(quickline.getC_UOM_ID());
		line.setQtyOrdered(relatedDoc.getQty());
		line.save();
		return line;
	}
	
	public static int docTypeRMA(Ctx ctx, boolean customerRMA, Trx trx){
		String sql = "SELECT C_DocType_ID FROM C_DocType WHERE IsReturnTrx = 'Y' AND AD_Client_ID = "+ctx.getAD_Client_ID()+" AND IsSOTrx = 'Y' ";
		if(customerRMA)
			sql+=" AND DocBaseType = 'SOO'";
		else
			sql+=" AND DocBaseType = 'MMS' AND IsConsigned = 'N'";
		PreparedStatement ps = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int conversionType(Ctx ctx, Trx trx){
		String sql = "SELECT C_ConversionType_ID FROM C_ConversionType WHERE AD_Client_ID = "+ctx.getAD_Client_ID();
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
	
	public static int lineNo(Ctx ctx, String Table_line, String Table_header, String coloumnLine, int ID, Trx trx){
		String sql = "SELECT COALESCE(MAX("+coloumnLine+"),0)+10 AS DefaultValue FROM "+Table_line+" WHERE "+Table_header+"_ID="+ID;
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	public static MInOut creaInOut(Ctx ctx, MQuickReturn quickReturn, MOrder order, Trx trx){
		MInOut inOut = new  MInOut(ctx, 0, trx);
		inOut.setAD_Client_ID(ctx.getAD_Client_ID());
		inOut.setAD_Org_ID(quickReturn.getAD_Org_ID());
		inOut.setC_Order_ID(order.getC_Order_ID());
		inOut.setDateOrdered(new Timestamp(System.currentTimeMillis()));
		inOut.setM_Warehouse_ID(order.getM_Warehouse_ID());
		inOut.setC_DocType_ID(docTypeRMA(ctx, false, trx));
		inOut.setMovementDate(new Timestamp(System.currentTimeMillis()));
		inOut.setDateAcct(new Timestamp(System.currentTimeMillis()));
		inOut.setC_BPartner_ID(quickReturn.getCustomer_ID());
		inOut.setC_BPartner_Location_ID(quickReturn.getC_BPartner_Location_ID());
		inOut.setMovementType(X_M_InOut.MOVEMENTTYPE_CustomerReturns);
		inOut.save();
		return inOut;
	}
	
	public static MInOutLine creaInOutLine(Ctx ctx , int AD_OrgTrx_ID, MInOut inOut, MOrderLine orderLine, MQuickReturn quickReturn, MQuickReturnLine quickline, MQuickReturnLineBatch batch, MReturnBatchRelatedDoc relatedDoc,  Trx trx){
		MInOutLine line = new MInOutLine(ctx, 0, trx);
		line.setAD_Client_ID(ctx.getAD_Client_ID());
		line.setAD_Org_ID(inOut.getAD_Org_ID());
		line.setAD_OrgTrx_ID(AD_OrgTrx_ID);
		line.setM_InOut_ID(inOut.getM_InOut_ID());
		line.setLine(lineNo(ctx, "M_InOutLine", "M_InOut", "Line", inOut.getM_InOut_ID(), trx));
		line.setM_Product_ID(quickline.getM_Product_ID());
		line.setM_AttributeSetInstance_ID(batch.getM_AttributeSetInstance_ID());
		line.setM_Warehouse_ID(quickReturn.getM_Warehouse_ID());
		line.setM_Locator_ID(quickReturn.getM_Locator_ID());
		line.setC_UOM_ID(quickline.getC_UOM_ID());
		line.setQtyEntered(relatedDoc.getQtyEntered());
		line.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		line.save();
		return line;
	}
	
	public static int salesRep_ID(Trx trx){
		String sql = "SELECT AD_User_ID FROM AD_User WHERE EXISTS (SELECT * FROM C_BPartner bp WHERE " +
				"AD_User.C_BPartner_ID=bp.C_BPartner_ID AND bp.IsSalesRep='Y')";
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
