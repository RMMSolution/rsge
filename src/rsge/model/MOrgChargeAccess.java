package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_OrgChargeAccess;

public class MOrgChargeAccess extends X_XX_OrgChargeAccess {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MOrgChargeAccess(Ctx ctx, int XX_OrgChargeAccess_ID, Trx trx) {
		super(ctx, XX_OrgChargeAccess_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MOrgChargeAccess(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public static void getChargeAccess(MPurchaseRequisitionLine line)
	{
		ArrayList<Integer> chargeList = new ArrayList<>();
		String sql = "SELECT C_Charge_ID FROM C_Charge "
				+ "WHERE C_Charge_ID IN "
				+ "(SELECT C_Charge_ID FROM XX_OrgChargeAccess "
				+ "WHERE AD_Org_ID = ? )";
		PreparedStatement pstmt = DB.prepareStatement(sql, line.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, line.getAD_Org_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				chargeList.add(rs.getInt(1));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		if(chargeList.size()==0)
		{
			sql = "SELECT C_Charge_ID FROM C_Charge "
					+ "WHERE AD_Client_ID = ? ";
			pstmt = DB.prepareStatement(sql, line.get_Trx());
			rs = null;
			try{
				pstmt.setInt(1, line.getAD_Client_ID());
				rs = pstmt.executeQuery();
				while(rs.next())
					chargeList.add(rs.getInt(1));
				rs.close();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}			
		}
		
		if(chargeList.size()!=0)
		{
			for(Integer i : chargeList)
			{
				String insert = "INSERT INTO T_RequisitionLineOption "
						+ "(XX_PurchaseRequisitionLine_ID, C_Charge_ID) "
						+ "VALUES(?, ?) ";
				DB.executeUpdate(line.get_Trx(), insert, line.getXX_PurchaseRequisitionLine_ID(), i.intValue());
			}
		}
	}


}
