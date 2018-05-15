package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_OrgProdCatAccess;

public class MOrgProdCatAccess extends X_XX_OrgProdCatAccess {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7626780702690624753L;

	public MOrgProdCatAccess(Ctx ctx, int XX_OrgProdCatAccess_ID, Trx trx) {
		super(ctx, XX_OrgProdCatAccess_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MOrgProdCatAccess(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public static void getProductAccess(MPurchaseRequisitionLine line)
	{
		ArrayList<Integer> prodCatList = new ArrayList<>();
		String sql = "SELECT M_Product_Category_ID FROM M_Product_Category "
				+ "WHERE M_Product_Category_ID IN "
				+ "(SELECT M_Product_Category_ID FROM XX_OrgProdCatAccess "
				+ "WHERE AD_Org_ID = ? )";
		PreparedStatement pstmt = DB.prepareStatement(sql, line.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, line.getAD_Org_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				prodCatList.add(rs.getInt(1));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		if(prodCatList.size()==0)
		{
			sql = "SELECT M_Product_Category_ID FROM M_Product_Category "
					+ "WHERE AD_Client_ID = ? ";
			pstmt = DB.prepareStatement(sql, line.get_Trx());
			rs = null;
			try{
				pstmt.setInt(1, line.getAD_Client_ID());
				rs = pstmt.executeQuery();
				while(rs.next())
					prodCatList.add(rs.getInt(1));
				rs.close();
				pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}			
		}
		
		if(prodCatList.size()!=0)
		{
			for(Integer i : prodCatList)
			{
				String insert = "INSERT INTO T_RequisitionLineOption "
						+ "(XX_PurchaseRequisitionLine_ID, M_Product_Category_ID) "
						+ "VALUES(?, ?) ";
				DB.executeUpdate(line.get_Trx(), insert, line.getXX_PurchaseRequisitionLine_ID(), i.intValue());
			}
		}
	}

}
