package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_NonItemAllocationLine;

public class MNonItemAllocationLine extends X_XX_NonItemAllocationLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MNonItemAllocationLine(Ctx ctx, int XX_NonItemAllocationLine_ID, Trx trx) {
		super(ctx, XX_NonItemAllocationLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MNonItemAllocationLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public MNonItemAllocationLine(MNonItemAllocation header) {
		super(header.getCtx(), 0, header.get_Trx());
		setClientOrg(header);
		setXX_NonItemAllocation_ID(header.getXX_NonItemAllocation_ID());
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		MOrg org = new MOrg(getCtx(), getAD_OrgTrx_ID(), get_Trx());
		setOrgValue(org.getValue());
		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		updateHeader();
		return true;
	}
	
	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		updateHeader();
		return true;
	}
	
	private boolean updateHeader()
	{
		String sql = "SELECT COALESCE(SUM(AllocatedAmt),0) AS TotalAmt "
				+ "FROM XX_NonItemAllocationLine "
				+ "WHERE XX_NonItemAllocation_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_NonItemAllocation_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				MNonItemAllocation ia = new MNonItemAllocation(getCtx(), getXX_NonItemAllocation_ID(), get_Trx());
				ia.setAmount(rs.getBigDecimal(1));
				ia.save();
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
