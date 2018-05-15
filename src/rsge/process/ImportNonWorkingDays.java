/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * Import Non Working Days
 * @author Fanny
 *
 */
public class ImportNonWorkingDays extends SvrProcess {

	private boolean					m_deleteOldImported = false;
	private static final String 	STD_CLIENT_CHECK = " AND AD_Client_ID=? " ;	
	/**	Client to be imported to		*/
	private int						m_AD_Client_ID = getAD_Client_ID();


	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {				

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		String sql;
		int no = 0;
		
		//	****	Prepare	****
		//	Delete Old Imported
		if (m_deleteOldImported)
		{
			sql =  "DELETE FROM I_NonWorkingDays "
				  + "WHERE I_IsImported='Y'"
				  + STD_CLIENT_CHECK;
			no = DB.executeUpdate(get_Trx(), sql, new Object[] {m_AD_Client_ID});
			log.fine("Delete Old Imported =" + no);
		}
		
		sql = "SELECT * " +
				"FROM I_NonWorkingDays " +
				"WHERE (I_IsImported<>'Y' OR I_IsImported IS NULL) " +
				STD_CLIENT_CHECK;
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		
		
		
		return null;
	}

}
