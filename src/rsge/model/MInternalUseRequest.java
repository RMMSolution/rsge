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

import rsge.po.X_XX_InternalUseRequest;

import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MWarehouse;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env.QueryParams;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

/**
 * @author programmer2
 *
 */
public class MInternalUseRequest extends X_XX_InternalUseRequest implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Logger for class MAssetAddition */
	private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MInternalUseRequest.class);

	
	/**
	 * @param ctx
	 * @param XX_InternalUseRequest_ID
	 * @param trx
	 */
	public MInternalUseRequest(Ctx ctx, int XX_InternalUseRequest_ID, Trx trx) {
		super(ctx, XX_InternalUseRequest_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MInternalUseRequest(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub		
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// If Warehouse is null, set Warehouse from Organization warehouse
		if(getM_Warehouse_ID()==0)
		{
			MWarehouse[] whList = MWarehouse.getForOrg(getCtx(), getAD_Org_ID());
			if(whList==null)
			{
				log.saveError("Error", Msg.getMsg(getCtx(), "NoWarehouseOrgFound"));
				return false;
			}
		}
		return super.beforeSave(newRecord);
	}

	public ArrayList<Integer> getLines()
	{
		ArrayList<Integer> lines = new ArrayList<Integer>();
		String sql = "SELECT XX_InternalUseRequestLine_ID " +
				"FROM XX_InternalUseRequestLine " +
				"WHERE XX_InternalUseRequest_ID =? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_InternalUseRequest_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				lines.add(rs.getInt(1));
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}		
		return lines;
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
		generateInternalUsed();
		return DocActionConstants.STATUS_Completed;
	}
	
	private void generateInternalUsed()
	{
		// Create Internal Use
		int invID = 0;
		MInventory inv = new MInventory(getCtx(), 0, get_Trx());
		inv.setAD_Org_ID(getAD_Org_ID());
		inv.setMovementDate(getUpdated());
		inv.setDescription("Request #" + getDocumentNo());
		inv.setM_Warehouse_ID(getM_Warehouse_ID());	
		
		// Set Doc Type
		String sql = "SELECT C_DocType_ID " +
				"FROM C_DocType " +
				"WHERE DocBaseType = 'MMI' " +
				"AND AD_Client_ID = ? " +
				"ORDER BY C_DocType_ID ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				inv.setC_DocType_ID(rs.getInt(1));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		// Set Reference
		inv.setC_Activity_ID(getC_Activity_ID());
		inv.setC_Campaign_ID(getC_Campaign_ID());
		inv.setC_Project_ID(getC_Project_ID());
		
		if(inv.save(get_Trx()))
		{
			invID = inv.getM_Inventory_ID();
		}
		
		// Create Inventory Line
		ArrayList<Integer> lines = getLines();
		
		for(int i = 0; i<lines.size(); i++)
		{
			MInternalUseRequestLine rline = new MInternalUseRequestLine(getCtx(), lines.get(i), get_Trx());
			if(!rline.isMultiLocator()) // Single Locator
			{
				MInventoryLine line = new MInventoryLine(getCtx(), 0, get_Trx());
				line.setAD_Org_ID(getAD_OrgTrx_ID());
				line.setM_Inventory_ID(invID);
				line.setLine(rline.getLine());
				line.setM_Product_ID(rline.getM_Product_ID());
				line.setM_Locator_ID(rline.getM_Locator_ID());
				line.setM_AttributeSetInstance_ID(rline.getM_AttributeSetInstance_ID());
				line.setQtyInternalUse(rline.getQtyInternalUse());
				line.setC_Charge_ID(rline.getC_Charge_ID());
				line.setIsInternalUse(true);
				line.save(get_Trx());				
			}
			else // Multi Locator
			{
				ArrayList<Integer> locatorLine = rline.getLocatorLines(rline);
				for(int x = 0; x<locatorLine.size(); x++)
				{
					MRequestLineLocator	lineLocator = new MRequestLineLocator(getCtx(), locatorLine.get(x), get_Trx());
					MInventoryLine line = new MInventoryLine(getCtx(), 0, get_Trx());
					line.setM_Inventory_ID(invID);
					line.setLine(rline.getLine());
					line.setM_Product_ID(rline.getM_Product_ID());
					line.setM_Locator_ID(lineLocator.getM_Locator_ID());
					line.setM_AttributeSetInstance_ID(lineLocator.getM_AttributeSetInstance_ID());
					line.setQtyInternalUse(lineLocator.getQtyInternalUse());
					line.setC_Charge_ID(rline.getC_Charge_ID());
					line.setIsInternalUse(true);
					line.save(get_Trx());									
				}
			}			
		}
		
		DocumentEngine.processIt(inv, DocActionConstants.STATUS_Completed);
		inv.save(get_Trx());
		
		// Update Internal Use
		setDateDelivered(getUpdated());
		setM_Inventory_ID(invID);
		setProcessed(true);
		save();
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
		return 0;
	}

	@Override
	public Timestamp getDocumentDate() {
		return super.getDateDoc();
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

	@Override
	public String prepareIt() {
		
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

}
