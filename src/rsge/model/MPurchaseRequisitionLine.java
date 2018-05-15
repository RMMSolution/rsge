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
import java.util.HashMap;
import java.util.Map;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.MPriceList;
import org.compiere.model.MProductPrice;
import org.compiere.model.MUOMConversion;
import org.compiere.model.X_M_Product;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.po.X_XX_PurchaseRequisition;
import rsge.po.X_XX_PurchaseRequisitionLine;
import rsge.tools.BudgetCalculation;
import rsge.utils.BudgetUtils;

/**
 * @author bang
 * 
 */
public class MPurchaseRequisitionLine extends X_XX_PurchaseRequisitionLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final org.compiere.util.CLogger log = org.compiere.util.CLogger
			.getCLogger(MPurchaseRequisitionLine.class);

	/**
	 * @param ctx
	 * @param XX_PurchaseRequisitionLine_ID
	 * @param trx
	 */
	public MPurchaseRequisitionLine(Ctx ctx, int XX_PurchaseRequisitionLine_ID,
			Trx trx) {
		super(ctx, XX_PurchaseRequisitionLine_ID, trx);
		// TODO Auto-generated constructor stub
	}
	private MPurchaseRequisition m_parent = null;
	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MPurchaseRequisitionLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public MPurchaseRequisition getParent()
	{
		if (m_parent == null)
			m_parent = new MPurchaseRequisition (getCtx(), getXX_PurchaseRequisition_ID(), get_Trx());
		return m_parent;
	}	//	getParent
	
	private void updateProductAndChargeOption()
	{
		resetOption();
		// Update Product Category
		MOrgProdCatAccess.getProductAccess(this);
		// Update Charge
		MOrgChargeAccess.getChargeAccess(this);
	}
	
	private void resetOption()
	{
		String delete = "DELETE T_RequisitionLineOption "
				+ "WHERE XX_PurchaseRequisitionLine_ID = ? ";
		DB.executeUpdate(get_Trx(), delete, getXX_PurchaseRequisitionLine_ID());
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		MPurchaseRequisition req = new MPurchaseRequisition(getCtx(), getXX_PurchaseRequisition_ID(), get_Trx());
		if(req.getDocStatus().equals(X_XX_PurchaseRequisition.DOCSTATUS_Drafted))
		{
			if(getM_Product_ID()!=0)
			{
				MProduct p = new MProduct(getCtx(), getM_Product_ID(), get_Trx());
				setC_UOM_ID(p.getC_UOM_ID());
			}
			
			if(is_ValueChanged("M_Product_ID") && getM_Product_ID()!=0)
			{			
				int priceListVersionID = getPriceListVersion(req.getM_PriceList_ID(), req.getDateDoc());
				MProductPrice pp = MProductPrice.get(getCtx(),
						priceListVersionID, getM_Product_ID(),
						get_Trx());
				
				if(pp!=null)
					setPriceActual(pp.getPriceStd());
				else
					setPriceActual(BigDecimal.ZERO);				
			}
			
			if(is_ValueChanged("PriceActual") || is_ValueChanged("Qty"))
				setLineNetAmt(getPriceActual().multiply(getQty()));
			
			
			// Budget check 
			if(req.getDocStatus().equals(X_XX_PurchaseRequisition.DOCSTATUS_Drafted))
			{
				MBudgetInfo bi = MBudgetInfo.get(getCtx(), getAD_Client_ID(), get_Trx());
				if(bi!=null)
				{
					if(bi.getStartDate()==null)
						return true;
					if(req.getDateRequired().before(bi.getStartDate()))
						return true;
					// Set Converted Amount
					MAcctSchema as = new MAcctSchema(getCtx(), bi.getC_AcctSchema_ID(), get_Trx());
					MPriceList pl = new MPriceList(getCtx(), req.getM_PriceList_ID(), get_Trx());
					if(as.getC_Currency_ID()!=pl.getC_Currency_ID())
					{
						BigDecimal convertedAmt = MConversionRate.convert(getCtx(), getLineNetAmt(), pl.getC_Currency_ID(), as.getC_Currency_ID(), req.getDateRequired(), bi.getC_ConversionType_ID(), getAD_Client_ID(), getAD_Org_ID());
						setConvertedAmt(convertedAmt);
					}
					else
						setConvertedAmt(getLineNetAmt());
					
					// Set Account
					if(getC_Charge_ID()!=0)
					{
						setBudgetCharge_ID(getC_Charge_ID());			
						MChargeAcct ca = MChargeAcct.get(getC_Charge_ID(), as);
						MAccount account = MAccount.get(getCtx(), ca.getCh_Expense_Acct());
						setAccount_ID(account.getAccount_ID());
					}
					else if(getC_Charge_ID()==0 && getBudgetCharge_ID()!=0)
					{
						MChargeAcct ca = MChargeAcct.get(getBudgetCharge_ID(), as);
						MAccount account = MAccount.get(getCtx(), ca.getCh_Expense_Acct());
						setAccount_ID(account.getAccount_ID());
					}
					else if(getM_Product_ID()!=0 && getBudgetCharge_ID()==0)
					{
						
						MProduct p = new MProduct(getCtx(), getM_Product_ID(), get_Trx());
						MProductAcct pa = MProductAcct.get(getM_Product_ID(), as);
						MAccount account = null;
						if(p.isItem())
							account = new MAccount(getCtx(), pa.getP_Asset_Acct(), get_Trx());
						else
							account = new MAccount(getCtx(), pa.getP_Expense_Acct(), get_Trx());
						setAccount_ID(account.getAccount_ID());
					}
					
					if(is_ValueChanged("LineNetAmt"))
						setConvertedAmt(calculateConvertedAmt(getLineNetAmt()));
					
					// Check budget if only header status is Draft
					BudgetCalculation bc = null;
					if(req.getDocStatus().equals(X_XX_PurchaseRequisition.DOCSTATUS_Drafted)
							|| req.getDocStatus().equals(X_XX_PurchaseRequisition.DOCSTATUS_NotApproved))
					{
						bc = checkLineBudget();
						if(bc.getBudgetAmt()==null) // No Budget defined
						{
							setRemainingBudget(BigDecimal.ZERO);
							setIsOverBudget(false);
							setOverBudgetAmt(BigDecimal.ZERO);
						}
						else
						{
							bc.calculateUsedAmt();
							BigDecimal remains = bc.getBudgetAmt().subtract(bc.getUsedAmt());
							System.out.println("Remains " + remains);
							setRemainingBudget(remains);
							
							// Check Over Budget
							if(getConvertedAmt().compareTo(remains)>0)
							{
								setIsOverBudget(true);
								setOverBudgetAmt((getConvertedAmt().subtract(remains)).negate());
							}
							else
							{
								setIsOverBudget(false);
								setOverBudgetAmt(BigDecimal.ZERO);
							}				
						}
					}
					
				}
				
			}
			
		}
		return true;
	}
	
	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		resetOption();
		return true;
	}
	
	public boolean recordLineTransaction()
	{
		MPurchaseRequisition pr = new MPurchaseRequisition(getCtx(), getXX_PurchaseRequisition_ID(), get_Trx());		
		MBudgetTransactionLine bLine = MBudgetTransactionLine.createLine(getCtx(), get_Table_ID(), getXX_PurchaseRequisitionLine_ID(), 0, 0, getAD_Org_ID(), pr.getDateDoc(), getAccount_ID(), 0, getC_Activity_ID(), 0, pr.getC_Campaign_ID(), 0, 0, pr.getC_Project_ID(), 0, 0, 0, getM_Product_ID(), 0, 0, 0, 0, 0, get_Trx());
		bLine.setReservedAmt(getConvertedAmt());
		bLine.save();
		return true;
	}
	
	public BudgetCalculation checkLineBudget()
	{
		MPurchaseRequisition pr = new MPurchaseRequisition(getCtx(), getXX_PurchaseRequisition_ID(), get_Trx());
		BudgetCalculation bc = new BudgetCalculation(getCtx(), getAD_Org_ID(), pr.getDateDoc(), pr.getDateDoc(), null, null, false, getAccount_ID(), 
				getC_Activity_ID(), pr.getC_Campaign_ID(), pr.getC_Project_ID(), 0, getM_Product_ID(), 0, 0, 0, false, 
				get_Trx());
		return bc;
	}
	
	public static BigDecimal getReservedAmt(int AD_Org_ID, int accountID, Timestamp startDate, Timestamp endDate, int C_Activity_ID, int C_Campaign_ID, int C_Project_ID, int M_Product_ID, Trx trx)
	{
		BigDecimal reservedAmt = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(rl.ConvertedAmt),0) AS ReservedAmt "
				+ " FROM M_RequisitionLine rl "
				+ " INNER JOIN M_Requisition r ON rl.M_Requisition_ID = r.M_Requisition_ID "
				+ " WHERE r.DocStatus IN ('CO', 'CL') "
				+ " AND r.AD_OrgTrx_ID = ? "
				+ " AND rl.Account_ID = ? "
				+ " AND r.DateDoc BETWEEN ? AND ? "
				+ " AND rl.C_OrderLine_ID IS NULL ");
		
		if(C_Activity_ID !=0)
		{
			sql.append("AND rl.C_Activity_ID = ");
			sql.append(C_Activity_ID);
			sql.append(" ");
		}
		else
			sql.append("AND (rl.C_Activity_ID = 0 OR rl.C_Activity_ID IS NULL) ");
		
		if(C_Campaign_ID !=0)
		{
			sql.append("AND r.C_Campaign_ID = ");
			sql.append(C_Campaign_ID);
			sql.append(" ");
		}
		else
			sql.append("AND (r.C_Campaign_ID = 0 OR r.C_Campaign_ID IS NULL) ");
		
		if(C_Project_ID !=0)
		{
			sql.append("AND r.C_Project_ID = ");
			sql.append(C_Campaign_ID);
			sql.append(" ");
		}
		else
			sql.append("AND (r.C_Project_ID = 0 OR r.C_Project_ID IS NULL) ");

		if(M_Product_ID !=0)
		{
			sql.append("AND rl.M_Product_ID = ");
			sql.append(M_Product_ID);
			sql.append(" ");
		}
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
        ResultSet rs = null;
	    try{
	    	int index = 1;
	    	pstmt.setInt(index++, AD_Org_ID);
	    	pstmt.setInt(index++, accountID);
	    	pstmt.setTimestamp(index++, startDate);
	    	pstmt.setTimestamp(index++, endDate);
	    	
	    	rs = pstmt.executeQuery();
	    	if(rs.next())
	    		reservedAmt = rs.getBigDecimal("ReservedAmt");
	    	rs.close();
	    	pstmt.close();
	    }catch (Exception e) {
	    	e.printStackTrace();
		}	    			

		return reservedAmt;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		if(newRecord)
			updateProductAndChargeOption();		
		updateHeader();
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		updateHeader();
		return true;
	}

	private void updateHeader() {
		log.fine("");
		BigDecimal totalLines = Env.ZERO;
		BigDecimal OverBudgetAmount = Env.ZERO;
		BigDecimal ConvertedAmount = Env.ZERO;

		// Check if there is any over budget line
		String checkOverBudget = "SELECT SUM(LineNetAmt) AS LineNetAmt, SUM(OverBudgetAmt) AS OverBudgetAmt, SUM(ConvertedAmt) AS ConvertedAmt "
				+ "FROM XX_PurchaseRequisitionLine "
				+ "WHERE XX_PurchaseRequisition_ID = ? ";

		PreparedStatement pstmt = DB.prepareStatement(checkOverBudget,get_Trx());
		ResultSet rs = null;

		try {
			pstmt.setInt(1, getXX_PurchaseRequisition_ID());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				totalLines = rs.getBigDecimal(1);
				OverBudgetAmount = rs.getBigDecimal(2);
				ConvertedAmount = rs.getBigDecimal(3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		// Check if there is any over budget line
		String isOverBudget = "N";
		String checkQtyOverBudget = "SELECT 1 "
				+ "FROM XX_PurchaseRequisitionLine "
				+ "WHERE IsOverBudget = 'Y' "
				+ "AND XX_PurchaseRequisition_ID = ? ";
		pstmt = DB.prepareStatement(checkQtyOverBudget, get_Trx());
		rs = null;

		try {
			pstmt.setInt(1, getXX_PurchaseRequisition_ID());
			rs = pstmt.executeQuery();
			if (rs.next()) 
				isOverBudget = "Y";			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(pstmt);
			DB.closeResultSet(rs);
		}
		
		String update = "UPDATE XX_PurchaseRequisition "
				+ "SET Amount = ?, ConvertedAmt = ?, OverBudgetAmt = ?, IsLineOverBudget = ? "
				+ "WHERE XX_PurchaseRequisition_ID = ? ";
		DB.executeUpdate(get_Trx(), update, totalLines, ConvertedAmount, OverBudgetAmount, isOverBudget, getXX_PurchaseRequisition_ID());
	} // updateHeader

	public static boolean checkBudgetInfoExisting(int AD_Client_ID, Trx trx) {
		boolean isExists = false;
		String sql = "SELECT 1 FROM XX_BudgetInfo WHERE AD_Client_ID = "
				+ AD_Client_ID;
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		try {
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				isExists = true;
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExists;
	}

	private BigDecimal calculateConvertedAmt(BigDecimal lineNetAmount) {
		
		MPurchaseRequisition r = new MPurchaseRequisition(getCtx(),
				getXX_PurchaseRequisition_ID(), get_Trx());
		MPriceList pl = new MPriceList(getCtx(), r.getM_PriceList_ID(),
				get_Trx());

		// Get Budget Info Currency
		MAcctSchema schema = new MAcctSchema(getCtx(),
				BudgetUtils.getBudgetAcctSchemaID(getCtx(), getAD_Client_ID(),
						get_Trx()), get_Trx());

		if (schema.getC_Currency_ID() != pl.getC_Currency_ID()) {
			BigDecimal convertedAmt = MConversionRate.convert(getCtx(),
					lineNetAmount, pl.getC_Currency_ID(), schema
							.getC_Currency_ID(), r.getDateDoc(), BudgetUtils
							.getConversionTypeID(getAD_Client_ID(), get_Trx()),
					getAD_Client_ID(), getAD_Org_ID());

			MCurrency currency = new MCurrency(getCtx(),
					schema.getC_Currency_ID(), get_Trx());
			convertedAmt = convertedAmt.setScale(currency.getStdPrecision(),
					RoundingMode.HALF_UP);

			return convertedAmt;
		} else
			return lineNetAmount;

	}

	public static int createRequisitionLine(MPurchaseRequisitionLine line, BigDecimal price, int AD_Org_ID, int requestBy, 
			Timestamp dateDoc, Timestamp dateRequired, int C_Currency_ID)
	{
		int mainOrgID = MOrg.getMainOrgID(line.getCtx(), AD_Org_ID, line.get_Trx());
		MRequisitionLine rl = null;
		
		StringBuilder sql = new StringBuilder("SELECT rl.* FROM M_RequisitionLine rl "
				+ "INNER JOIN M_Requisition r ON rl.M_Requisition_ID = r.M_Requisition_ID "
				+ "INNER JOIN M_PriceList pl ON r.M_PriceList_ID = pl.M_PriceList_ID "
				+ "WHERE pl.IsSOPriceList = 'N' "
				+ "AND r.DocStatus IN ('DR') "
				+ "AND r.AD_Org_ID = ? "
				+ "AND r.DateRequired = ? "
				+ "AND r.AD_User_ID = ? "
				+ "AND pl.C_Currency_ID = ? ");
		if(line.getM_Product_ID()!=0)
		{
			sql.append("AND rl.M_Product_ID = ");
			sql.append(line.getM_Product_ID());
		}
		else if(line.getC_Charge_ID()!=0)
		{
			sql.append("AND rl.C_Charge_ID = ");
			sql.append(line.getC_Charge_ID());
		}

		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), line.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Org_ID);
			pstmt.setTimestamp(2, dateRequired);
			pstmt.setInt(3, requestBy);
			pstmt.setInt(4, C_Currency_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				rl = new MRequisitionLine(line.getCtx(), rs, line.get_Trx());
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

		int requisitionID = 0;
		if(rl==null)
		{
			rl = new MRequisitionLine(line.getCtx(), 0, line.get_Trx());
			rl.setAD_Client_ID(line.getAD_Client_ID());
			rl.setAD_Org_ID(mainOrgID);
			rl.setM_Requisition_ID(MPurchaseRequisition.getRequisition(line.getCtx(), mainOrgID, requestBy, dateDoc, dateRequired, C_Currency_ID, line.get_Trx()));
			if(line.getM_Product_ID()!=0)
				rl.setM_Product_ID(line.getM_Product_ID());
			if(line.getC_Charge_ID()!=0)
				rl.setC_Charge_ID(line.getC_Charge_ID());
			rl.setQty(BigDecimal.ZERO);
			rl.setLine(1);			
		}
		BigDecimal qty = line.getQty();
		if(line.getM_Product_ID()!=0)
		{
			MProduct p = new MProduct(line.getCtx(), line.getM_Product_ID(), line.get_Trx());
			if(p.getC_UOM_ID()!=line.getC_UOM_ID())
				qty = MUOMConversion.convertProductTo(line.getCtx(), line.getM_Product_ID(), line.getC_UOM_ID(), line.getQty());
		}
		rl.setQty(rl.getQty().add(qty));		
		rl.setPriceActual(price);
		BigDecimal lineNetAmt = rl.getQty().multiply(price);
		rl.setLineNetAmt(lineNetAmt);
		if(rl.save())
		{
			String update = "UPDATE XX_PurchaseRequisitionLine "
					+ "SET M_RequisitionLine_ID = ? "
					+ "WHERE XX_PurchaseRequisitionLine_ID = ? ";
			DB.executeUpdate(line.get_Trx(), update, rl.getM_RequisitionLine_ID(), line.getXX_PurchaseRequisitionLine_ID());
			requisitionID = rl.getM_Requisition_ID();
		}		
		return requisitionID;
	}
	
	public static Map<MPurchaseRequisitionLine[], BigDecimal> get(MOrderLine oline)
	{
		ArrayList<MPurchaseRequisitionLine> lines = new ArrayList<>();
		BigDecimal totalQty = BigDecimal.ZERO;
		
		String sql = "SELECT prl.* FROM XX_PurchaseRequisitionLine prl "
				+ "INNER JOIN M_RequisitionLine rl ON prl.M_RequisitionLine_ID = rl.M_RequisitionLine_ID "
				+ "WHERE rl.C_OrderLine_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), oline.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, oline.getC_OrderLine_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				MPurchaseRequisitionLine line = new MPurchaseRequisitionLine(oline.getCtx(), rs, oline.get_Trx());
				if(line.getM_Product_ID()!=0)
				{
					BigDecimal productQty = line.getQty();
					MProduct p = new MProduct(line.getCtx(), line.getM_Product_ID(), line.get_Trx());
					if(line.getC_UOM_ID()!=p.getC_UOM_ID())
						productQty = MUOMConversion.convertProductTo(line.getCtx(), line.getM_Product_ID(), line.getC_UOM_ID(), productQty);
					totalQty = totalQty.add(productQty);
				}
				else
					totalQty = totalQty.add(line.getQty());
				lines.add(line);
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		MPurchaseRequisitionLine[] retLines = new MPurchaseRequisitionLine[lines.size()];
		lines.toArray(retLines);
		
		Map<MPurchaseRequisitionLine[], BigDecimal> retValue = new HashMap<>();
		retValue.put(retLines, totalQty);
		return retValue;
	}

	private int getPriceListVersion(int priceListID, Timestamp baseDate)
	{
		int versionID = 0;
		String sql = "SELECT plv.M_PriceList_Version_ID FROM M_PriceList_Version plv "
				+ "INNER JOIN M_PriceList pl ON plv.M_PriceList_ID = pl.M_PriceList_ID "
				+ "WHERE pl.M_PriceList_ID = ? "
				+ "AND plv.ValidFrom <= ? "
				+ "ORDER BY plv.ValidFrom DESC ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, priceListID);
			pstmt.setTimestamp(2, baseDate);
			rs = pstmt.executeQuery();
			if(rs.next())
				versionID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return versionID;
	}
	
	public static Map<ArrayList<MPurchaseRequisitionLine>, BigDecimal> getPRLines(MInOutLine iol)
	{
		BigDecimal totalQty = BigDecimal.ZERO;
		ArrayList<MPurchaseRequisitionLine> prLines = new ArrayList<>();
		String sql = "SELECT prl.* FROM XX_PurchaseRequisitionLine prl "
				+ "INNER JOIN M_RequisitionLine rl ON prl.M_RequisitionLine_ID = rl.M_RequisitionLine_ID "
				+ "INNER JOIN C_OrderLine ol ON rl.C_OrderLine_ID = ol.C_OrderLine_ID "
				+ "INNER JOIN M_InOutLine iol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID "
				+ "WHERE prl.Qty>prl.QtyDelivered "
				+ "AND iol.M_InOutLine_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, iol.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, iol.getM_InOutLine_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				MPurchaseRequisitionLine line = new MPurchaseRequisitionLine(iol.getCtx(), rs, iol.get_Trx());
				prLines.add(line);
				BigDecimal lineQty = line.getQty();
				if(line.getM_Product_ID()!=0)
				{
					MProduct p = new MProduct(line.getCtx(), line.getM_Product_ID(), line.get_Trx());
					if(p.getProductType().equals(X_M_Product.PRODUCTTYPE_Item) || p.getProductType().equals(X_M_Product.PRODUCTTYPE_Resource))
					{
						if(p.getC_UOM_ID()!=line.getC_UOM_ID())
						{
							lineQty = MUOMConversion.convertProductTo(line.getCtx(), line.getM_Product_ID(), line.getC_UOM_ID(), lineQty);
						}
					}
				}
				totalQty = totalQty.add(lineQty);
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<ArrayList<MPurchaseRequisitionLine>, BigDecimal> retValue = new HashMap<>();
		retValue.put(prLines, totalQty);
		return retValue;
	}

}
