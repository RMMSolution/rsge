/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_GeneralSetup;

/**
 * @author FANNY R
 *
 */
public class MGeneralSetup extends X_XX_GeneralSetup {

    /** Logger for class MGeneralSetup */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MGeneralSetup.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_GeneralSetup_ID
	 * @param trx
	 */
	public MGeneralSetup(Ctx ctx, int XX_GeneralSetup_ID, Trx trx) {
		super(ctx, XX_GeneralSetup_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MGeneralSetup(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}	
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		System.out.println("START BEFORE SAVE");
		// Check record. If already exists, cancel the process
		if(newRecord)
		{
			boolean recordExists = false;
			
			String sql = "SELECT 1 " +
					"FROM XX_GeneralSetup " +
					"WHERE AD_Client_ID = ? ";
			
			PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
			ResultSet rs = null;
			
			try{
				pstmt.setInt(1, getAD_Client_ID());
				rs = pstmt.executeQuery();
				
				if(rs.next())
				{
					recordExists = true;
				}			
				
				rs.close();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			// Return if record already exists
			if(recordExists)
			{
				log.saveError("Record Exists", "RecordAlreadyExist");
				return false;
			}			
		}
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// Create Budget Info if new Record
		MBudgetInfo.createBudgetInfo(this);
		return true;
	}
	
	/**
	 * Get General setup based on Client ID
	 * @param ctx
	 * @param AD_Client_ID
	 * @param trx
	 * @return
	 */
	public static MGeneralSetup get(Ctx ctx, int AD_Client_ID, Trx trx)
	{
		MGeneralSetup setup = null;
		
		String sql = "SELECT * FROM XX_GeneralSetup " +
				"WHERE AD_Client_ID = " + AD_Client_ID;
		
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
				setup = new MGeneralSetup(ctx, rs, trx);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		return setup;
	}
	

}
