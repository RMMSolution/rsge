package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_NonItemAllocation;

public class MNonItemAllocation extends X_XX_NonItemAllocation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MNonItemAllocation(Ctx ctx, int XX_NonItemAllocation_ID, Trx trx) {
		super(ctx, XX_NonItemAllocation_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MNonItemAllocation(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public static MNonItemAllocationLine[] getLines(Ctx ctx, int XX_NonItemAllocation_ID, Trx trx)
	{
		ArrayList<MNonItemAllocationLine> list = new ArrayList<>();
		String sql = "SELECT * "
				+ "FROM XX_NonItemAllocationLine "
				+ "WHERE XX_NonItemAllocation_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try{
			pstmt.setInt(1, XX_NonItemAllocation_ID);
			rs = pstmt.executeQuery();
			while(rs.next())
				list.add(new MNonItemAllocationLine(ctx, rs, trx));
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		MNonItemAllocationLine[] retValue = new MNonItemAllocationLine[list.size()];
		list.toArray(retValue);
		return retValue;
	}



}
