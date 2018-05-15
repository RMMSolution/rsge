package rsge.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.compiere.model.MMovementLine;
import org.compiere.model.MWarehouse;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env.QueryParams;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_XX_MaterialRequest;
import rsge.model.MMovement;



public class MMaterialRequest extends X_XX_MaterialRequest implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /** Logger for class MMaterialRequest */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MMaterialRequest.class);

	public MMaterialRequest(Ctx ctx, int XX_MaterialRequest_ID, Trx trx) {
		super(ctx, XX_MaterialRequest_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MMaterialRequest(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
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

	int cashBookID = 0;
	int cashID = 0;

	MMaterialRequestLine[] lines = null;
	
	@Override
	public String completeIt() {
		lines = getLines();
		int length = lines.length;
		
		// Get Designated locator
		MOrgInfo org = MOrgInfo.get(getCtx(), getAD_Org_ID(), get_Trx());
		MWarehouse wh = new MWarehouse(getCtx(), org.getM_Warehouse_ID(), get_Trx());
		for(int i = 0; i<length; i++)
		{
			MMaterialRequestLine line = lines[i];
			// Generate 
				String sql = "SELECT s.QtyOnHand, s.M_Locator_ID, loc.M_Warehouse_ID, s.M_AttributeSetInstance_ID FROM M_Storage_V s " +
				"INNER JOIN M_Locator loc ON (s.M_Locator_ID = loc.M_Locator_ID) " +
				"WHERE s.M_Product_ID = ? " +
				"AND s.QtyOnHand>0 " +
				"AND loc.IsAvailableForAllocation = 'Y' "
				+ "AND loc.AD_Org_ID != ? ";
			
			PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
			ResultSet rs = null;
			try{
				int M_Warehouse_ID = 0;
				MMovement movement = null;
				int lineNo = 0;
				pstmt.setInt(1, line.getM_Product_ID());
				pstmt.setInt(2, getAD_Org_ID());
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					if(M_Warehouse_ID != rs.getInt("M_Warehouse_ID"))
					{
						M_Warehouse_ID=rs.getInt("M_Warehouse_ID");
						movement = getMovement(M_Warehouse_ID);
						lineNo = 10;
					}
					MMovementLine ml = new MMovementLine(movement);
					ml.setM_Locator_ID(rs.getInt("M_Locator_ID"));
					ml.setM_LocatorTo_ID(wh.getM_ReservationLocator_ID());
					ml.setConfirmedQty(BigDecimal.ZERO);
					ml.setMovementQty(BigDecimal.ZERO);
					ml.setLine(lineNo);
					lineNo = lineNo+10;
					ml.setM_Product_ID(line.getM_Product_ID());					
					ml.setTargetQty(line.getQty());					
					if(ml.save())
					{
						line.setM_MovementLine_ID(ml.getM_MovementLine_ID());
						line.save();
					}
				}
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}

		}
		return DocActionConstants.STATUS_Completed;
	}
	
	public MMovement getMovement(int M_Warehouse_ID)
	{
		MMovement retValue = null;
		retValue = new MMovement(getCtx(), 0, get_Trx());
		MWarehouse wh = new MWarehouse(getCtx(), M_Warehouse_ID, get_Trx());
		retValue.setClientOrg(wh);
		retValue.setDescription("Material Request # " + getDocumentNo());
		String sql = "SELECT C_DocType_ID FROM C_DocType " +
				"WHERE AD_Client_ID = ? " +
				"AND DocBaseType = 'MMM' ";
		
		int docTypeID = 0;
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				docTypeID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}		

		retValue.setC_DocType_ID(docTypeID);
		retValue.setDateReceived(getDocumentDate());
		retValue.setMovementDate(getDocumentDate());
		MOrg org = new MOrg(getCtx(), getAD_Org_ID(), get_Trx());
		retValue.setDescription("Material Request #" + getDocumentNo() + " - " + org.getName());
		retValue.save();		
		return retValue;
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
		lines = getLines();
		if(lines==null)
		{
			m_processMsg = Msg.getMsg(getCtx(), "No Lines");
			return DOCSTATUS_Invalid;
		}
		// Check Organization warehouse
		MOrg org = new MOrg(getCtx(), getAD_Org_ID(), get_Trx());
		if(org.getM_Warehouse_ID()==0)
		{			
			m_processMsg = "No warehouse specificied for organization " + org.getName();
			return DOCSTATUS_Invalid;			
		}
		System.out.println("Prepare It");
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

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public MMaterialRequestLine[] getLines()
	{
		ArrayList<MMaterialRequestLine> list = null;
		String sql = "SELECT * FROM XX_MaterialRequestLine " +
				"WHERE XX_MaterialRequest_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_MaterialRequest_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				if(list==null)
					list = new ArrayList<MMaterialRequestLine>();
				
				MMaterialRequestLine line = new MMaterialRequestLine(getCtx(), rs, get_Trx());
				list.add(line);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		MMaterialRequestLine[] retValue = null;
		if(list!=null)
		{
			retValue = new MMaterialRequestLine[list.size()];
			list.toArray(retValue);
		}
		return retValue;
	}
	
	public static boolean MovementConfirmation(MMovementLine[] lines, MMovement movement)
	{		
		for(MMovementLine line : lines)
		{
			String sql = "SELECT * FROM XX_MaterialRequestLine "
					+ "WHERE M_MovementLine_ID = ? ";
			PreparedStatement pstmt = DB.prepareStatement(sql, movement.get_Trx());
			ResultSet rs = null;
			try{
				pstmt.setInt(1, line.getM_MovementLine_ID());
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					MMaterialRequestLine mrl = new MMaterialRequestLine(movement.getCtx(), rs, movement.get_Trx());
					mrl.setConfirmedQty(line.getMovementQty());
					mrl.save();
				}
				rs.close();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public static boolean MovementCancelation(MMovementLine[] lines, MMovement movement)
	{		
		for(MMovementLine line : lines)
		{
			String sql = "SELECT XX_MaterialRequestLine_ID FROM XX_MaterialRequestLine "
					+ "WHERE M_MovementLine_ID = ? ";
			PreparedStatement pstmt = DB.prepareStatement(sql, movement.get_Trx());
			ResultSet rs = null;
			try{
				pstmt.setInt(1, line.getM_MovementLine_ID());
				rs = pstmt.executeQuery();
				if(rs.next())
				{				
					String update = "UPDATE XX_MaterialRequestLine "
							+ "SET M_MovementLine_ID = NULL "
							+ "WHERE XX_MaterialRequestLine_ID = ? ";
					DB.executeUpdate(movement.get_Trx(), update, rs.getInt(1));
				}
				rs.close();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}


}
