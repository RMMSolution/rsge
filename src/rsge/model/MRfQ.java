/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

/**
 * @author FANNY R
 *
 */
public class MRfQ extends org.compiere.model.MRfQ {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param C_RfQ_ID
	 * @param trx
	 */
	public MRfQ(Ctx ctx, int C_RfQ_ID, Trx trx) {
		super(ctx, C_RfQ_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MRfQ(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

    /** Set Use Criteria Set.
    @param UseCriteriaSet Use criteria set which defined before */
    public void setUseCriteriaSet (boolean UseCriteriaSet)
    {
        set_Value ("UseCriteriaSet", Boolean.valueOf(UseCriteriaSet));
        
    }
    
    /** Get Use Criteria Set.
    @return Use criteria set which defined before */
    public boolean isUseCriteriaSet() 
    {
        return get_ValueAsBoolean("UseCriteriaSet");
        
    }
    
    /** Set Procurement Criteria Set.
    @param XX_ProcCriteriaSet_ID Set of procurement criteria */
    public void setXX_ProcCriteriaSet_ID (int XX_ProcCriteriaSet_ID)
    {
        if (XX_ProcCriteriaSet_ID <= 0) set_Value ("XX_ProcCriteriaSet_ID", null);
        else
        set_Value ("XX_ProcCriteriaSet_ID", Integer.valueOf(XX_ProcCriteriaSet_ID));
        
    }
    
    /** Get Procurement Criteria Set.
    @return Set of procurement criteria */
    public int getXX_ProcCriteriaSet_ID() 
    {
        return get_ValueAsInt("XX_ProcCriteriaSet_ID");
        
    }
    
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		// Get Criteria Set data		
		if(isUseCriteriaSet())
		{
			String sql = "SELECT pc.XX_ProcCriteriaType_ID, ValuationWeight " +
					"FROM XX_ProcCriteria pc " +
					"WHERE pc.XX_ProcCriteriaSet_ID = ? " +
					"AND NOT EXISTS (SELECT * FROM XX_RfQCriteria rc " +
					"WHERE rc.XX_ProcCriteriaType_ID = pc.XX_ProcCriteriaType_ID " +
					"AND rc.C_RfQ_ID = ?) ";
			
			PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
			ResultSet rs = null;
			
			try{
				pstmt.setInt(1, getXX_ProcCriteriaSet_ID());
				pstmt.setInt(2, getC_RfQ_ID());
				rs = pstmt.executeQuery();
				
				while(rs.next())
				{
					MRfqCriteria rc = new MRfqCriteria(getCtx(), 0, get_Trx());
					rc.setAD_Org_ID(getAD_Org_ID());
					rc.setC_RfQ_ID(getC_RfQ_ID());
					rc.setXX_ProcCriteriaType_ID(rs.getInt(1));
					rc.setValuationWeight(rs.getBigDecimal(2));
					rc.save();
				}
				
				rs.close();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();				
			}
		}
		return true;
	}


}
