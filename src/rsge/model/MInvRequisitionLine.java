/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.compiere.api.UICallout;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MProduct;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.po.X_XX_InvRequisitionLine;
import rsge.utils.BudgetUtils;
import rsge.utils.CheckBudget;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY
 *
 */
public class MInvRequisitionLine extends X_XX_InvRequisitionLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_InvRequisitionLine_ID
	 * @param trx
	 */
	public MInvRequisitionLine(Ctx ctx, int XX_InvRequisitionLine_ID, Trx trx) {
		super(ctx, XX_InvRequisitionLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MInvRequisitionLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		BigDecimal remainingBudget = checkRemainingBudget();
		if(remainingBudget.compareTo(((BigDecimal.ONE).negate())) != 0) // if account has budget
		{
			remainingBudget = remainingBudget.subtract(getLinesAmount());			
			setRemainingBudget(remainingBudget);
			
			if(getEstCost().compareTo(remainingBudget) == 1)
			{
				setIsOverBudget(true);
			}
			else
			{
				setIsOverBudget(false);
			}					
		}
		else // Account has no budget
		{
			setRemainingBudget(Env.ZERO);
			setIsOverBudget(false);
		}

		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		updateHeader();
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		updateHeader();
		return true;
	}

	@UICallout public void setQtyRequired (String oldQty,
			String newQty, int windowNo) throws Exception
	{
		updateEstCost();		
	}

	
	@UICallout public void setM_Product_ID (String oldM_Product_ID,
			String newM_Product_ID, int windowNo) throws Exception
	{
		Integer productID = getM_Product_ID();
		if(productID == null || getM_Product_ID()==0)
			return;	
		
		MInvRequisition cr = new MInvRequisition(getCtx(), getXX_InvRequisition_ID(), get_Trx());

		// Set UOM 
		MProduct product = new MProduct(getCtx(), getM_Product_ID(), get_Trx());
		setC_UOM_ID(product.getC_UOM_ID());
		
		// Get Product Cost
		MBudgetInfo bi = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), get_Trx());
		MAcctSchema as = new MAcctSchema(getCtx(), bi.getC_AcctSchema_ID(), get_Trx());
		
		BigDecimal productCost = MCost.getCurrentCost(product, 0, as, cr.getAD_Org_ID(), null, Env.ONE, 0, true, (Trx)null);
		setProductCost(productCost);
		
		return;
	}
	
	private void updateEstCost()
	{
		setEstCost(getProductCost().multiply(getQtyRequired()));
	}

	/**
	 * Check Remaining Budget of an account
	 * @return Remaining Budget
	 * Return -1 to indicate the account is not budgeted
	 */
	private BigDecimal checkRemainingBudget()
	{
		MInvRequisition r = new MInvRequisition(getCtx(), getXX_InvRequisition_ID(), get_Trx());
		int Account_ID = 0;

		int acctSchemaID = BudgetUtils.getBudgetAcctSchemaID(getCtx(), getAD_Client_ID(), get_Trx());
		String sql = "SELECT vc.Account_ID " +
				"FROM C_Charge_Acct ca " +
				"INNER JOIN C_ValidCombination vc ON (ca.Ch_Expense_Acct = vc.C_ValidCombination_ID) " +
				"WHERE ca.C_Charge_ID = ? " +
				"AND ca.C_AcctSchema_ID = ? ";
			
			PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
			ResultSet rs = null;
			
			try{
				pstmt.setInt(1, getC_Charge_ID());
				pstmt.setInt(2, acctSchemaID);
				
				rs = pstmt.executeQuery();
				
				if(rs.next())
				{
					Account_ID = rs.getInt(1);
				}
				
				rs.close();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}			
			
			int element1Value = 0;
			String element1 = null;
			element1 = GeneralEnhancementUtils.getElementColumn(acctSchemaID, 1, get_Trx());
			
			if((element1 != null) && (GeneralEnhancementUtils.checkColumn("XX_InvRequisitionLine", element1, get_Trx())))
			{
				element1Value = GeneralEnhancementUtils.getElementValue(element1, "XX_InvRequisitionLine", getXX_InvRequisitionLine_ID(), get_Trx());
			}
			else if((element1 != null) && (GeneralEnhancementUtils.checkColumn("XX_InvRequisition", element1, get_Trx())))
			{
				element1Value = GeneralEnhancementUtils.getElementValue(element1, "XX_InvRequisition", getXX_InvRequisition_ID(), get_Trx());
			}
			
			int element2Value = 0;
			String element2 = null;
			element2 = GeneralEnhancementUtils.getElementColumn(acctSchemaID, 2, get_Trx());
			
			if((element2 != null) && (GeneralEnhancementUtils.checkColumn("XX_InvRequisitionLine", element2, get_Trx())))
			{
				element2Value = GeneralEnhancementUtils.getElementValue(element2, "XX_InvRequisitionLine", getXX_InvRequisitionLine_ID(), get_Trx());
			}
			else if((element2 != null) && (GeneralEnhancementUtils.checkColumn("XX_InvRequisition", element2, get_Trx())))

			{
				element2Value = GeneralEnhancementUtils.getElementValue(element2, "XX_InvRequisition", getXX_InvRequisition_ID(), get_Trx());
			}		
			
		CheckBudget check = new CheckBudget(getCtx(), false, getAD_Org_ID(), Account_ID, r.getDateExpected(), false,BudgetUtils.getBudgetCalendar(getCtx(), getAD_Client_ID(), get_Trx()),
				r.getC_Activity_ID(), 0, r.getC_Campaign_ID(), r.getC_Project_ID(), 0, element1Value, element2Value, get_Trx());

		BigDecimal remainingBudget = check.getRemainingBudget();
		if(check.getAccountNotBudgeted())
		{
			remainingBudget = BigDecimal.ONE.negate();  
		}

		return remainingBudget;
	}

	private void updateHeader()
	{
		BigDecimal totalAmt = Env.ZERO;
		BigDecimal totalEstAmt = Env.ZERO;
		String sql = "SELECT COALESCE(SUM(Cost), 0) AS TotalAmount, COALESCE(SUM(EstCost), 0) AS TotalEstAmt " +
				"FROM XX_InvRequisitionLine " +
				"WHERE XX_InvRequisition_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try
		{
			pstmt.setInt(1, getXX_InvRequisition_ID());			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				totalAmt = rs.getBigDecimal(1);
				totalEstAmt = rs.getBigDecimal(2);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}

		sql = "UPDATE XX_InvRequisition " +
				"SET TotalAmt = ?, EstTotalAmt = ? " +
				"WHERE XX_InvRequisition_ID = ? ";
		
		pstmt = DB.prepareStatement(sql, get_Trx());
		try{
			pstmt.setBigDecimal(1, totalAmt);
			pstmt.setBigDecimal(2, totalEstAmt);
			pstmt.setInt(3, getXX_InvRequisition_ID());
			pstmt.executeUpdate();			
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
	}

	private void updateParentBudgetStatus(boolean overBudget, boolean isLineOverBudget)
	{
		String sql = "UPDATE XX_InvRequisition " +
				"SET IsOverBudget = ? " +
				"WHERE XX_InvRequisition_ID = ? ";
		
		String para1 = null;
		
		if(overBudget)
			para1 = "Y";		
		else para1 = "N";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		
		try{
			pstmt.setString(1, para1);
			pstmt.setInt(2, getXX_InvRequisition_ID());
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeStatement(pstmt);
		}
			
	}

	private BigDecimal getLinesAmount()
	{
		BigDecimal linesAmt = BigDecimal.ZERO;		
		int C_Charge_ID = getC_Charge_ID();
		String sql = "SELECT COALESCE(SUM(EstCost), 0) " +
				"FROM XX_InvRequisitionLine rl " +
				"WHERE C_Charge_ID = ? " +
				"AND XX_InvRequisitionLine_ID NOT IN (?) " +
				"AND XX_InvRequisition_ID IN (?) ";
				
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, C_Charge_ID);
			pstmt.setInt(2, getXX_InvRequisitionLine_ID());
			pstmt.setInt(3, getXX_InvRequisition_ID());
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				linesAmt = rs.getBigDecimal(1);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return linesAmt;
	}
}
