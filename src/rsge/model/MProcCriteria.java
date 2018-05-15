/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_ProcCriteria;

/**
 * @author FANNY R
 *
 */
public class MProcCriteria extends X_XX_ProcCriteria {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_ProcCriteria_ID
	 * @param trx
	 */
	public MProcCriteria(Ctx ctx, int XX_ProcCriteria_ID, Trx trx) {
		super(ctx, XX_ProcCriteria_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MProcCriteria(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		// Check if criteria weight of same Procurement criteria is not exceed 100
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(ValuationWeight),0) " +
				"FROM XX_ProcCriteria " +
				"WHERE XX_ProcCriteriaSet_ID = ? ");
		if(!newRecord)			
		{
			sql.append("AND XX_ProcCriteria_ID NOT IN (?) ");
		}
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_ProcCriteriaSet_ID());
			if(!newRecord)
			{
				pstmt.setInt(2, getXX_ProcCriteria_ID());
			}
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				BigDecimal totalValuation = BigDecimal.ZERO;
				if(rs.getBigDecimal(1).compareTo(BigDecimal.ZERO)==1)
				{
					totalValuation = rs.getBigDecimal(1);
				}
				
				if((getValuationWeight().add(totalValuation)).compareTo(BigDecimal.valueOf(100))==1)
				{
					setValuationWeight(BigDecimal.valueOf(100).subtract(totalValuation));
				}
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// Update Is Valid in Criteria Set as No
		String sql = "UPDATE XX_ProcCriteriaSet " +
				"SET IsValid = 'N' " +
				"WHERE XX_ProcCriteriaSet_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_ProcCriteriaSet_ID());
			rs = pstmt.executeQuery();			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
				
		return true;
	}
	
	

}
