/**
 * 
 */
package rsge.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MAcctSchema;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.model.MBudgetInfo;

/**
 * Utility for Budget Management
 * @author FANNY
 *
 */
public class BudgetUtils {

	/**
	 * Return Budget Accounting Schema
	 * @param ctx
	 * @param AD_Client_ID
	 * @param trx
	 * @return
	 */
	public static int getBudgetAcctSchemaID(Ctx ctx, int AD_Client_ID, Trx trx)
	{
		MBudgetInfo info = GeneralEnhancementUtils.getBudgetInfo(ctx, AD_Client_ID, trx);		
		return info.getC_AcctSchema_ID();
	}
	
	/**
	 * Return Budget Calendar
	 * @param ctx
	 * @param AD_Client_ID
	 * @param trx
	 * @return
	 */
	public static int getBudgetCalendar(Ctx ctx, int AD_Client_ID, Trx trx)
	{		
		MBudgetInfo info = GeneralEnhancementUtils.getBudgetInfo(ctx, AD_Client_ID, trx);
		return info.getBudgetCalendar_ID();
	}

	
	/**
	 * Return Budget Clearing Account
	 * @param ctx
	 * @param AD_Client_ID
	 * @param trx
	 * @return
	 */
	public static int getBudgetClearingAcct(Ctx ctx, int AD_Client_ID, Trx trx)
	{		
		MBudgetInfo info = GeneralEnhancementUtils.getBudgetInfo(ctx, AD_Client_ID, trx);
		return info.getBudgetClearing_Acct();
	}

	
	/**
	 * Return Budget Currency ID
	 * @param ctx
	 * @param AD_Client_ID
	 * @param trx
	 * @return
	 */
	public static int getBudgetCurrencyID(Ctx ctx, int AD_Client_ID, Trx trx)
	{		
		MBudgetInfo info = GeneralEnhancementUtils.getBudgetInfo(ctx, AD_Client_ID, trx);
		MAcctSchema as = new MAcctSchema(ctx, info.getC_AcctSchema_ID(), trx);
		return as.getC_Currency_ID();
	}
	
	/**
	 * Return Budget Conversion Type
	 * @param AD_Client_ID
	 * @param trx
	 * @return
	 */
	public static int getConversionTypeID(int AD_Client_ID, Trx trx)
	{
		int ID = 0;
		// Get C_ConversionType_ID (for document creation only)
		String sql = "SELECT C_ConversionType_ID " +
				"FROM XX_BudgetInfo " +
				"WHERE AD_Client_ID = " + AD_Client_ID;
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				ID = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		} // End Get C_ConversionType_ID

		return ID;
	}


}
