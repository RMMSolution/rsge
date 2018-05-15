/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * @author Fanny
 *
 */
public class AcctSchemaCopyAcctRSGE extends SvrProcess {

	/**	Acct Schema					*/
	private int			p_C_AcctSchema_ID = 0;

	/** Use Product Return Acct		*/
	private String 		p_UseProductReturn = null;
	/**	Sales Return				*/
	private int			p_SalesReturnAcct = 0;
	/**	Returned Product Asset		*/
	private int			p_ReturnedProductAcct = 0;
	/** Returned Product COGS		*/
	private int			p_ReturnedCOGSAcct = 0;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null)
				;
			else if (name.equals("C_AcctSchema_ID"))
				p_C_AcctSchema_ID = element.getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}		
		
		// Get Account
		String sql = "SELECT IsUseReturnAccount, P_SalesReturn_Acct, P_AssetReturn_Acct, P_COGSReturn_Acct " +
				"FROM C_AcctSchema_Default WHERE C_AcctSchema_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs= null;
		
		try{
			pstmt.setInt(1, p_C_AcctSchema_ID);
			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				p_UseProductReturn = rs.getString("IsUseReturnAccount");
				p_SalesReturnAcct = rs.getInt("P_SalesReturn_Acct");
				p_ReturnedProductAcct = rs.getInt("P_AssetReturn_Acct");
				p_ReturnedCOGSAcct = rs.getInt("P_COGSReturn_Acct");
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
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		// Update Product Category
		StringBuilder update = new StringBuilder("UPDATE M_Product_Category_Acct " +
						"SET IsUseReturnAccount = ?, P_SalesReturn_Acct = ?");
		if(p_ReturnedProductAcct>0)
		{
			update.append(", P_AssetReturn_Acct = ");
			update.append(p_ReturnedProductAcct);
		}
		if(p_ReturnedCOGSAcct>0)
		{
			update.append(", P_COGSReturn_Acct = ");
			update.append(p_ReturnedCOGSAcct);
		}
		update.append(" WHERE C_AcctSchema_ID = ? ");
		DB.executeUpdate(get_Trx(), update.toString(), p_UseProductReturn, p_SalesReturnAcct, p_C_AcctSchema_ID);
		
		// Update Product 
		update = new StringBuilder("UPDATE M_Product_Acct " +
				"SET IsUseReturnAccount = ?, P_SalesReturn_Acct = ?");
		if(p_ReturnedProductAcct>0)
		{
			update.append(", P_AssetReturn_Acct = ");
			update.append(p_ReturnedProductAcct);
		}
		if(p_ReturnedCOGSAcct>0)
		{
			update.append(", P_COGSReturn_Acct = ");
			update.append(p_ReturnedCOGSAcct);
		}
		update.append("WHERE C_AcctSchema_ID = ? ");
		DB.executeUpdate(get_Trx(), update.toString(), p_UseProductReturn, p_SalesReturnAcct, p_C_AcctSchema_ID);
		return "Accounts Updated";
	}

}
