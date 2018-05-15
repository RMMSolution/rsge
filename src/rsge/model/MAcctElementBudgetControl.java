package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_AcctElementBudgetControl;

public class MAcctElementBudgetControl extends X_XX_AcctElementBudgetControl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MAcctElementBudgetControl.class);


	public MAcctElementBudgetControl(Ctx ctx, int XX_AcctElementBudgetControl_ID, Trx trx) {
		super(ctx, XX_AcctElementBudgetControl_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MAcctElementBudgetControl(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		if(!getElementType().equals(ELEMENTTYPE_Campaign)
				&& !getElementType().equals(ELEMENTTYPE_Project))
		{
			log.saveError("Error", "Only Campaign or Project allowed");
			return false;
		}
		return true;
	}
	
	public static MAcctElementBudgetControl get(Ctx ctx, int XX_BudgetInfo_ID, String elementType, Trx trx)
	{
		MAcctElementBudgetControl bc = null;
		String sql = "SELECT * FROM XX_AcctElementBudgetControl "
				+ "WHERE XX_BudgetInfo_ID = ? AND ElementType = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, XX_BudgetInfo_ID);
			pstmt.setString(2, elementType);
			rs = pstmt.executeQuery();
			if(rs.next())
				bc = new MAcctElementBudgetControl(ctx, rs, trx);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bc;
	}

}
