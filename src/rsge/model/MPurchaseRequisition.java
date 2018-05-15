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
import java.util.logging.Level;

import org.compiere.model.MCurrency;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.X_M_Requisition;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env.QueryParams;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

import rsge.po.X_XX_PurchaseRequisition;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author bang
 * 
 */
public class MPurchaseRequisition extends X_XX_PurchaseRequisition implements
		DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final org.compiere.util.CLogger log = org.compiere.util.CLogger
			.getCLogger(MPurchaseRequisition.class);

	/**
	 * @param ctx
	 * @param XX_PurchaseRequisition_ID
	 * @param trx
	 */
	public MPurchaseRequisition(Ctx ctx, int XX_PurchaseRequisition_ID, Trx trx) {
		super(ctx, XX_PurchaseRequisition_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MPurchaseRequisition(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		String errorMsg = null;		
		if(is_ValueChanged("C_Currency_ID"))
		{
			int priceListID = getPriceList();
			if(priceListID==0)
			{
				MCurrency currency = new MCurrency(getCtx(), getC_Currency_ID(), get_Trx());
				errorMsg = "No purchase price list found for " + currency.getISO_Code();
				log.saveError("Error", Msg.getMsg(getCtx(), errorMsg));
				return false;
			}
			setM_PriceList_ID(priceListID);
		}		
		
		if(getDateRequired().before(getDateDoc()))
		{
			log.saveError("Error", "Date Required cannot be before Document Date");
			return false;
		}
		return true;
	}
	
	private int getPriceList()
	{
		int priceListID = 0;
		// Get From Budget Info Price List Version
		String sql = "SELECT M_PriceList_Version_ID FROM XX_BudgetInfoPriceList "
				+ "WHERE AD_Client_ID = ? "
				+ "AND C_Currency_ID = ? ";
		PreparedStatement ps = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try {
			int index = 1;
			ps.setInt(index++, getAD_Client_ID());
			ps.setInt(index++, getC_Currency_ID());
			rs = ps.executeQuery();
			if (rs.next())
			{
				MPriceListVersion plv = new MPriceListVersion(getCtx(), rs.getInt(1), get_Trx());
				priceListID = plv.getM_PriceList_ID();
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if(priceListID==0)
		{
			// Get From Budget Info Price List Version
			sql = "SELECT M_PriceList_ID FROM M_PriceList "
					+ "WHERE IsSOPriceList = 'N' "
					+ "AND IsActive = 'Y' "
					+ "AND AD_Client_ID = ? "
					+ "AND C_Currency_ID = ? ";
			ps = DB.prepareStatement(sql, get_Trx());
			rs = null;
			try {
				int index = 1;
				ps.setInt(index++, getAD_Client_ID());
				ps.setInt(index++, getC_Currency_ID());
				rs = ps.executeQuery();
				if (rs.next())
					priceListID = rs.getInt(1);
				rs.close();
				ps.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}			
		}
		
		return priceListID;
	}
	
	public static String valueIn(int lineID, Trx trx) {
		String value = null;
		String sql = "SELECT Value FROM XX_DocSequenceLine WHERE XX_DocSequenceLine_ID = "
				+ lineID;
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next())
				value = rs.getString(1);
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}

	private MPurchaseRequisitionLine[] m_lines = null;

	public MPurchaseRequisitionLine[] getLines(boolean requery) {
		if ((m_lines != null) && !requery)
			return m_lines;

		ArrayList<MPurchaseRequisitionLine> list = new ArrayList<MPurchaseRequisitionLine>();
		// String sql =
		// "SELECT * FROM M_RequisitionLine WHERE M_Requisition_ID=? ORDER BY Line";
		String sql = "SELECT * FROM XX_PurchaseRequisitionLine WHERE XX_PurchaseRequisition_ID=? ORDER BY Line";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, get_Trx());
			// pstmt.setInt (1, getM_Requisition_ID());
			pstmt.setInt(1, getXX_PurchaseRequisition_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MPurchaseRequisitionLine(getCtx(), rs, get_Trx()));
		} catch (Exception e) {
			log.log(Level.SEVERE, "getLines", e);
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		m_lines = new MPurchaseRequisitionLine[list.size()];
		list.toArray(m_lines);
		return m_lines;
	} // getLines

	public static int CurrentNext(int lineID, Trx trx) {
		int value = 0;
		String sql = "SELECT CurrentNext FROM XX_DocSequenceLine WHERE XX_DocSequenceLine_ID = "
				+ lineID;
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next())
				value = rs.getInt(1);
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		MPurchaseRequisitionLine[] line = null;
		line = getOrderLines();

		if (line.length == 0) {
			m_processMsg = "No Line Found";
			return DocActionConstants.STATUS_Invalid;
		}

		if(getAmount().signum()==0){
			m_processMsg = "Amount is 0";
			return DocActionConstants.STATUS_Invalid;
		}
		return DocActionConstants.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}

	public void setSummary(String Summary) {
		set_Value("Summary", Summary);

	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		if(GeneralEnhancementUtils.budgetStart(getCtx(), getDateDoc(), get_Trx()))
		{
			// Record line transaction
			MPurchaseRequisitionLine[] lines = getLines(true);
			for(MPurchaseRequisitionLine line : lines)
				line.recordLineTransaction();					
		}
		
		resetOption();
		return DocActionConstants.STATUS_Completed;
	}
	
	private void resetOption()
	{
		String delete = "DELETE T_RequisitionLineOption "
				+ "WHERE XX_PurchaseRequisitionLine_ID IN ("
				+ "SELECT XX_PurchaseRequisitionLine_ID FROM XX_PurchaseRequisitionLine "
				+ "WHERE XX_PurchaseRequisition_ID = ? "
				+ ") ";
		DB.executeUpdate(get_Trx(), delete, getXX_PurchaseRequisition_ID());
	}
	
	public static int getRequisition(Ctx ctx, int AD_Org_ID, int requestBy, Timestamp dateDoc, Timestamp dateRequired, int C_Currency_ID, Trx trx)
	{
		int headerID = 0;
		String sql = "SELECT r.M_Requisition_ID FROM M_Requisition r "
				+ "INNER JOIN M_PriceList pl ON r.M_PriceList_ID = pl.M_PriceList_ID "
				+ "WHERE pl.IsSOPriceList = 'N' "
				+ "AND r.DocStatus IN ('DR') "
				+ "AND r.AD_Org_ID = ? "
				+ "AND r.DateRequired = ? "
				+ "AND r.AD_User_ID = ? "
				+ "AND pl.C_Currency_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Org_ID);
			pstmt.setTimestamp(2, dateRequired);
			pstmt.setInt(3, requestBy);
			pstmt.setInt(4, C_Currency_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				headerID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(headerID==0)
		{
			int priceListID = getPriceList(ctx.getAD_Client_ID(), C_Currency_ID, trx);
			MRequisition req = new MRequisition(ctx, 0, trx);
			MOrgInfo oi = MOrgInfo.get(ctx, AD_Org_ID, trx);
			req.setAD_Org_ID(AD_Org_ID);
			req.setM_Warehouse_ID(oi.getM_Warehouse_ID());
			req.setAD_User_ID(requestBy);
			req.setDateDoc(dateDoc);
			req.setDateRequired(dateRequired);
			req.setM_PriceList_ID(priceListID);
			req.setPriorityRule(X_M_Requisition.PRIORITYRULE_Medium);
			req.setC_DocType_ID(getRequisitionDocType(ctx.getAD_Client_ID(), trx));
			if(req.save())
				headerID = req.getM_Requisition_ID();			
		}
		
		return headerID;
	}
	
	private static int getRequisitionDocType(int AD_Client_ID, Trx trx)
	{
		int docTypeID = 0;
		String sql = "SELECT C_DocType_ID FROM C_DocType "
				+ "WHERE DocBaseType = 'POR' "
				+ "AND AD_Client_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Client_ID );
			rs = pstmt.executeQuery();
			if(rs.next())
				docTypeID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return docTypeID;
	}
	
	private static int getPriceList(int AD_Client_ID, int C_Currency_ID, Trx trx)
	{
		int priceListID = 0;
		String sql = "SELECT M_PriceList_ID FROM M_PriceList "
				+ "WHERE IsSOPriceList = 'N' "
				+ "AND AD_Client_ID = ? "
				+ "AND C_Currency_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setInt(2, C_Currency_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				priceListID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return priceListID;
	}


	/**
	 * Set Direct To Invoice.
	 * 
	 * @param IsDirectToInvoice
	 *            This requisition will automatically generate invoice
	 */
	public void setIsDirectToInvoice(boolean IsDirectToInvoice) {
		set_Value("IsDirectToInvoice", Boolean.valueOf(IsDirectToInvoice));

	}

	/**
	 * Get Direct To Invoice.
	 * 
	 * @return This requisition will automatically generate invoice
	 */
	public boolean isDirectToInvoice() {
		return get_ValueAsBoolean("IsDirectToInvoice");

	}


	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		log.info("voidIt - " + toString());

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		log.info("closeIt - " + toString());
		return true;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		return DocumentEngine.processIt(this, DocActionConstants.ACTION_Void);
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		log.info("reverseAccrualIt - " + toString());
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		log.info("reActivateIt - " + toString());
		return DocumentEngine.processIt(this,
				DocActionConstants.ACTION_Reverse_Correct);
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		try {
			File temp = File.createTempFile(get_TableName() + get_ID() + "_",
					".pdf");
			return createPDF(temp);
		} catch (Exception e) {
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}

	public File createPDF(File file) {
		return null;
	}

	String m_processMsg;

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return BigDecimal.ZERO;
	}

	@Override
	public void setProcessMsg(String processMsg) {
		// TODO Auto-generated method stub
		m_processMsg = processMsg;
	}

	@Override
	public Timestamp getDocumentDate() {
		// TODO Auto-generated method stub
		return super.getDateDoc();
	}

	@Override
	public String getDocBaseType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryParams getLineOrgsQueryInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	private MPurchaseRequisitionLine[] getOrderLines() {
		ArrayList<MPurchaseRequisitionLine> list = new ArrayList<MPurchaseRequisitionLine>();
		String sql = "SELECT * FROM XX_PurchaseRequisitionLine "
				+ "WHERE XX_PurchaseRequisition_ID = ? ";

		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try {
			pstmt.setInt(1, getXX_PurchaseRequisition_ID());
			rs = pstmt.executeQuery();
			while (rs.next()) 
				list.add(new MPurchaseRequisitionLine(getCtx(), rs, get_Trx()));
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		MPurchaseRequisitionLine[] lines = new MPurchaseRequisitionLine[list
				.size()];
		list.toArray(lines);
		return lines;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static BigDecimal getBudgetPendingAmt(int AD_Org_ID, int Account_ID, Timestamp startDate, Timestamp endDate, Integer C_Activity_ID, Integer C_Campaign_ID, Integer C_Project_ID, Integer M_Product_ID, Trx trx)
	{
		BigDecimal pendingAmt = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(rl.ConvertedAmt),0) AS PendingAmt "
				+ " FROM XX_PurchaseRequisitionLine rl "
				+ " INNER JOIN XX_PurchaseRequisition r ON rl.XX_PurchaseRequisition_ID = r.XX_PurchaseRequisition_ID "
				+ " WHERE r.DocStatus IN ('IP', 'AP') ");
		if(AD_Org_ID!=0)
			sql.append(" AND r.AD_Org_ID = ? ");
		sql.append(" AND rl.Account_ID = ? "
				+ " AND r.DateDoc BETWEEN ? AND ? "
				+ " AND rl.C_OrderLine_ID IS NULL ");
		
		if(C_Activity_ID != 0)
		{
			sql.append("AND rl.C_Activity_ID = ");
			sql.append(C_Activity_ID);
			sql.append(" ");
		}
		else
			sql.append("AND (rl.C_Activity_ID = 0 OR rl.C_Activity_ID IS NULL) ");
		
		if(C_Campaign_ID!=0)
		{
			sql.append("AND r.C_Campaign_ID = ");
			sql.append(C_Campaign_ID);
			sql.append(" ");
		}
		else
			sql.append("AND (r.C_Campaign_ID = 0 OR r.C_Campaign_ID IS NULL) ");
		
		if(C_Project_ID!=0)
		{
			sql.append("AND r.C_Project_ID = ");
			sql.append(C_Project_ID);
			sql.append(" ");
		}
		else
			sql.append("AND (r.C_Project_ID = 0 OR r.C_Project_ID IS NULL) ");

		if(M_Product_ID!=0)
		{
			sql.append("AND rl.M_Product_ID = ");
			sql.append(M_Product_ID);
			sql.append(" ");
		}
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
        ResultSet rs = null;
	    try{
	    	int index = 1;
	    	if(AD_Org_ID!=0)
	    		pstmt.setInt(index++, AD_Org_ID);
	    	pstmt.setInt(index++, Account_ID);
	    	pstmt.setTimestamp(index++, startDate);
	    	pstmt.setTimestamp(index++, endDate);
	    	
	    	rs = pstmt.executeQuery();
	    	if(rs.next())
	    		pendingAmt = rs.getBigDecimal("PendingAmt");
	    	rs.close();
	    	pstmt.close();
	    }catch (Exception e) {
	    	e.printStackTrace();
		}	    			

		return pendingAmt;
	}
}
