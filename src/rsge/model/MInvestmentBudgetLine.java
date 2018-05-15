/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import rsge.model.MAssetGroup;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_InvestmentBudgetLine;

/**
 * @author FANNY R
 *
 */
public class MInvestmentBudgetLine extends X_XX_InvestmentBudgetLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_InvestmentBudgetLine_ID
	 * @param trx
	 */
	public MInvestmentBudgetLine(Ctx ctx, int XX_InvestmentBudgetLine_ID,
			Trx trx) {
		super(ctx, XX_InvestmentBudgetLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MInvestmentBudgetLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// Check Asset Group Depreciation. If not exists, create a new one
		boolean recordExist = false;
		
		String sql = "SELECT XX_InvLineDepreciation_ID " +
				"FROM XX_InvLineDepreciation " +
				"WHERE XX_InvestmentBudgetLine_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_InvestmentBudgetLine_ID());
			rs = pstmt.executeQuery();
			
			if(rs.next())
				recordExist = true;			
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		if(!recordExist)
		{
			MInvLineDepreciation ild = new MInvLineDepreciation(getCtx(), 0, get_Trx());
			ild.setXX_InvestmentBudgetLine_ID(getXX_InvestmentBudgetLine_ID());
			ild.setAD_Org_ID(getAD_Org_ID());
			ild.save();
		}
		
		return true;
	}
	
	/**
	 * Set Asset Group callout
	 * @param oldA_Asset_Group_ID
	 * @param newA_Asset_Group_ID
	 * @param windowNo
	 * @throws Exception
	 */
	public void setA_Asset_Group_ID(String oldA_Asset_Group_ID,
			String newA_Asset_Group_ID, int windowNo) throws Exception
	{
		if ((newA_Asset_Group_ID == null) || (newA_Asset_Group_ID.length() == 0))
			return;
		Integer A_Asset_Group_ID = Integer.valueOf(newA_Asset_Group_ID);
		if(A_Asset_Group_ID == 0)
			return;
		
		MAssetGroup ag = new MAssetGroup(getCtx(), A_Asset_Group_ID, get_Trx());
		setIsDepreciated(ag.isDepreciated());
		
		// Populate Account Information
		
		setBudgetAssetDepreciation_Acct(ag.getBudgetAssetDepreciation_Acct());		
		return;
	}

}
