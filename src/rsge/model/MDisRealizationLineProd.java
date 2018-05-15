package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_DisRealizationLineProd;

public class MDisRealizationLineProd extends X_XX_DisRealizationLineProd {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MDisRealizationLineProd(Ctx ctx, int XX_DisRealizationLineProd_ID, Trx trx) {
		super(ctx, XX_DisRealizationLineProd_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MDisRealizationLineProd(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub		
		if(getPrice().signum()!=0 && getQty().signum()!=0)
			setTotalAmt(getPrice().multiply(getQty()));
		else
			setTotalAmt(BigDecimal.ZERO);
		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		BigDecimal totalAmt = BigDecimal.ZERO;
		String sql = "SELECT COALESCE(SUM(TotalAmt),0) AS TotalAmt "
				+ "FROM XX_DisRealizationLineProd "
				+ "WHERE XX_DisRealizationLine_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs= null;
		try{
			pstmt.setInt(1, getXX_DisRealizationLine_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				totalAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		String update = "UPDATE XX_DisRealizationLine "
				+ "SET RealizedAmt = ? WHERE XX_DisRealizationLine_ID = ? ";
		DB.executeUpdate(get_Trx(), update, totalAmt, getXX_DisRealizationLine_ID());
		MDisRealizationLine drl = new MDisRealizationLine(getCtx(), getXX_DisRealizationLine_ID(), get_Trx());
		MDisRealizationLine.updateHeader(drl);
		return true;
	}
	
	public static MDisRealizationLineProd[] get(MDisRealizationLine dl)
	{
		ArrayList<MDisRealizationLineProd> list = new ArrayList<>();
		String sql = "SELECT * "
				+ "FROM XX_DisRealizationLineProd "
				+ "WHERE XX_DisRealizationLine_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, dl.get_Trx());
		ResultSet rs= null;
		try{
			pstmt.setInt(1, dl.getXX_DisRealizationLine_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				list.add(new MDisRealizationLineProd(dl.getCtx(), rs, dl.get_Trx()));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

		MDisRealizationLineProd[] retValue = new MDisRealizationLineProd[list.size()];
		list.toArray(retValue);
		return retValue;
	}

}
