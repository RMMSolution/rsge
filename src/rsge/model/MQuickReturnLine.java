package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_QuickReturnLine;

public class MQuickReturnLine extends X_XX_QuickReturnLine {
    /** Logger for class MQuickReturnLine */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MQuickReturnLine.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MQuickReturnLine(Ctx ctx, int XX_QuickReturnLine_ID, Trx trx) {
		super(ctx, XX_QuickReturnLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MQuickReturnLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(newRecord)
		{
			MProduct product = new MProduct(getCtx(), getM_Product_ID(), get_Trx());
			setC_UOM_ID(product.getC_UOM_ID());
		}
		return true;
	}
	
	public MQuickReturnLineBatch[] getLines()
	{
		// Get Batch List
		ArrayList<MQuickReturnLineBatch> lines = new ArrayList<MQuickReturnLineBatch>();
		String sql = "SELECT * FROM XX_QuickReturnLineBatch " +
				"WHERE XX_QuickReturnLine_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_QuickReturn_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MQuickReturnLineBatch line = new MQuickReturnLineBatch(getCtx(), rs, get_Trx());
				lines.add(line);
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
		
		MQuickReturnLineBatch[] retValue = new MQuickReturnLineBatch[lines.size()];
		lines.toArray(retValue);
		return retValue;
	}
}
