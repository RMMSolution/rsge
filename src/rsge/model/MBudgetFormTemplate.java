/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_Ref__Product_Trx_Type;
import rsge.po.X_XX_BudgetFormTemplate;

/**
 * @author FANNY
 *
 */
public class MBudgetFormTemplate extends X_XX_BudgetFormTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param XX_BudgetFormTemplate_ID
	 * @param trx
	 */
	public MBudgetFormTemplate(Ctx ctx, int XX_BudgetFormTemplate_ID, Trx trx) {
		super(ctx, XX_BudgetFormTemplate_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MBudgetFormTemplate(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		if(isProduct() && !isSOTrx() && !getTransactionType().equals(X_Ref__Product_Trx_Type.SALES_PURCHASE))
		{
			setTransactionType(TRANSACTIONTYPE_SalesPurchase);
		}
		return true;
	}
	
	public static MBudgetFormTemplate get(Ctx ctx, int AD_Org_ID, Trx trx)
	{
		MBudgetFormTemplate retValue = null;
		String sql = "SELECT * FROM XX_BudgetFormTemplate " +
				"WHERE AD_Org_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Org_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
				retValue = new MBudgetFormTemplate(ctx, rs, trx);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		if(retValue==null)
		{
			retValue = new MBudgetFormTemplate(ctx, 0, trx);
			retValue.setAD_Org_ID(AD_Org_ID);
			retValue.setIsProduct(false);
			retValue.setIsValid(true);
			MOrg org = new MOrg(ctx, AD_Org_ID, trx);
			retValue.setName(org.getName());
			retValue.save();
		}
		return retValue;
	}

}
