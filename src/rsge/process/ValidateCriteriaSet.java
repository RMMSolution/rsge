/**
 * 
 */
package rsge.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import rsge.model.MProcCriteriaSet;

/**
 * @author FANNY R
 *
 */
public class ValidateCriteriaSet extends SvrProcess {

	/**
	 * 
	 */
	public ValidateCriteriaSet() {
		// TODO Auto-generated constructor stub
	}
	
	/** Line exists - 0 if not exists - 1 if yes	*/
	private int					exists = 0;
	/** Valuation Weight							*/
	private BigDecimal			valuationWeight	= BigDecimal.ZERO;	
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// Check lines
		if(exists==0)
			return Msg.getMsg(getCtx(), "NoLines");
		// Check Validation Weight
		if(valuationWeight.compareTo(BigDecimal.valueOf(100))== -1)
			return Msg.getMsg(getCtx(), "Validation Weight Less Than 100");
		// Set Valid as Yes
		MProcCriteriaSet pcs = new MProcCriteriaSet(getCtx(), getRecord_ID(), get_Trx());
		pcs.setIsValid(true);
		pcs.save();
		return "";
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		// Check Lines
		String sql = "SELECT 1 " +
				"FROM XX_ProcCriteria " +
				"WHERE XX_ProcCriteriaSet_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getRecord_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				exists = 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		sql = "SELECT SUM(ValuationWeight) " +
			"FROM XX_ProcCriteria " +
			"WHERE XX_ProcCriteriaSet_ID = ? ";
		
		pstmt = DB.prepareStatement(sql, get_Trx());
		rs = null;
		
		try{
			pstmt.setInt(1, getRecord_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				valuationWeight = rs.getBigDecimal(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		

	}

}
