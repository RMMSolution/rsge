/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MBPartnerLocation;
import org.compiere.model.X_M_Product;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_ExcludeAmountOnly;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author Fanny
 *
 */
public class MExcludeAmountOnly extends X_XX_ExcludeAmountOnly {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_ExcludeAmountOnly_ID
	 * @param trx
	 */
	public MExcludeAmountOnly(Ctx ctx, int XX_ExcludeAmountOnly_ID, Trx trx) {
		super(ctx, XX_ExcludeAmountOnly_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MExcludeAmountOnly(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// 
		if(!isOrganization() && getAD_Org_ID()>0)
			setAD_Org_ID(0);
		if(!isActivity() && getC_Activity_ID()>0)
			setC_Activity_ID(0);
		if(!isCampaign() && getC_Campaign_ID()>0)
			setC_Campaign_ID(0);
		if(!isProject() && getC_Project_ID()>0)
			setC_Project_ID(0);
		if(!isBusinessPartner() && getC_BPartner_ID()>0)
			setC_BPartner_ID(0);
		if(!isSalesRegion() && getC_SalesRegion_ID()>0)
			setC_SalesRegion_ID(0);
		if(!isProduct() && getM_Product_ID()>0)
			setM_Product_ID(0);

		return true;
	}
	
	/**
	 * Check Budget Qty
	 * 
	 * @param ctx
	 * @param AD_Client_ID
	 * @param AD_Org_ID
	 * @param M_Product_ID
	 * @param C_Activity_ID
	 * @param C_BPartner_ID
	 * @param C_BPartner_Location_ID
	 * @param C_Campaign_ID
	 * @param C_Project_ID
	 * @param trx
	 * @return true if quantity is also checked
	 */
	public static boolean checkBudgetQty(Ctx ctx, int AD_Client_ID, int AD_Org_ID, int M_Product_ID, 
			int C_Activity_ID, int C_BPartner_ID, int C_BPartner_Location_ID, int C_Campaign_ID, int C_Project_ID, Trx trx)
	{
		MBudgetInfo bi = GeneralEnhancementUtils.getBudgetInfo(ctx, AD_Client_ID, trx);
		if(!bi.isCheckBudgetQty())
			return false;
		
		if(M_Product_ID==0)
			return false;
		MProduct product = new MProduct(ctx, M_Product_ID, trx);
		if(!product.getProductType().equals(X_M_Product.PRODUCTTYPE_Item))
			return false;
		
		boolean checkQty = true;				
		StringBuilder sql = new StringBuilder("SELECT 1 FROM XX_ExcludeAmountOnly " +
				"WHERE AD_Org_ID = ?");
		
		sql.append(" AND M_Product_ID = ");
		sql.append(M_Product_ID);			

		if(AD_Org_ID > 0)
		{
			sql.append(" AND AD_Org_ID = ");
			sql.append(AD_Org_ID);			
		}
		if(C_Activity_ID > 0)
		{
			sql.append(" AND C_Activity_ID = ");
			sql.append(C_Activity_ID);			
		}
		if(C_BPartner_ID > 0)
		{
			sql.append(" AND C_BPartner_ID = ");
			sql.append(C_BPartner_ID);			
		}
		if(C_Campaign_ID > 0)
		{
			sql.append(" AND C_Campaign_ID = ");
			sql.append(C_Campaign_ID);			
		}
		if(C_Project_ID > 0)
		{
			sql.append(" AND C_Project_ID = ");
			sql.append(C_Project_ID);
		}
		if(C_BPartner_Location_ID > 0)
		{
			MBPartnerLocation bploc = new MBPartnerLocation(ctx, C_BPartner_Location_ID, trx);
			sql.append(" AND C_SalesRegion_ID = ");
			sql.append(bploc.getC_SalesRegion_ID());			
		}
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, AD_Org_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				checkQty = false;
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
		return checkQty;
	}

}
