/**
 * 
 */
package rsge.form.web;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.compiere.common.QueryVO;
import org.compiere.common.TableModel;
import org.compiere.common.constants.DisplayTypeConstants;
import org.compiere.intf.ComponentImplIntf;
import org.compiere.intf.WindowImplIntf;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.NamePair;
import org.compiere.util.Trx;
import org.compiere.vos.ComponentVO;
import org.compiere.vos.FieldVO;
import org.compiere.vos.ListBoxVO;
import org.compiere.vos.WindowCtx;
import org.compiere.vos.ComponentVO.ComponentType;

/**
 * @author bang
 * 
 */
public class GenPOFromPR_Header implements ComponentImplIntf {
	private Ctx serverCtx;
	private int AD_Client_ID = 0;
	private int AD_Org_ID = 0;
	private WindowCtx windowCtx = null;

	/**
	 * 
	 */
	public GenPOFromPR_Header(Ctx serverCtx, WindowCtx windowCtx) {
		// TODO Auto-generated constructor stub
		this.serverCtx = serverCtx;
		this.windowCtx = windowCtx;
	}

	@Override
	public ComponentVO getComponentVO() {
		// TODO Auto-generated method stub
		ComponentVO componentVO = new ComponentVO();
		componentVO.componentType = ComponentType.SEARCH;
		componentVO.name = "";
		componentVO.tableName = "";
		componentVO.numColumns = 2;
		componentVO.fieldVOs = new ArrayList<FieldVO>();

		AD_Client_ID = serverCtx.getAD_Client_ID();
		AD_Org_ID = serverCtx.getAD_Org_ID();

		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Field Organization
		FieldVO fVO_AD_Org = new FieldVO("AD_Org_ID", "Organization",
				DisplayTypeConstants.TableDir, false);
		fVO_AD_Org.isImpactsValue = true;
		ArrayList<NamePair> orgName = new ArrayList<NamePair>();
		sql = "SELECT Org_ID,OrgName FROM XX_ClientOrg WHERE IsActive = 'Y' "
				+ "AND AD_Client_ID = " + AD_Client_ID
				+ " ORDER BY OrgName ASC ";

		ps = DB.prepareStatement(sql, (Trx) null);
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				orgName.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fVO_AD_Org.listBoxVO = new ListBoxVO(orgName, null);
		componentVO.fieldVOs.add(fVO_AD_Org);

		// Field Product Category
		FieldVO fVO_M_Product_Category_ID = new FieldVO(
				"M_Product_Category_ID", "Product Category",
				DisplayTypeConstants.TableDir, false);
		fVO_M_Product_Category_ID.isImpactsValue = true;
		ArrayList<NamePair> categoryName = new ArrayList<NamePair>();
		sql = "SELECT M_Product_Category_ID, Name FROM M_Product_Category "
				+ "WHERE IsActive = 'Y' AND AD_Client_ID = " + AD_Client_ID
				+ " ORDER BY Name ASC ";

		ps = DB.prepareStatement(sql, (Trx) null);
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				categoryName
						.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fVO_M_Product_Category_ID.listBoxVO = new ListBoxVO(categoryName, null);
		componentVO.fieldVOs.add(fVO_M_Product_Category_ID);

		// Field Charge
		FieldVO fVO_C_Charge_ID = new FieldVO("C_Charge_ID", "Charge",
				DisplayTypeConstants.TableDir, false);
		fVO_C_Charge_ID.isImpactsValue = true;
		ArrayList<NamePair> chargeName = new ArrayList<NamePair>();
		sql = "SELECT C_Charge_ID, Name FROM C_Charge "
				+ "WHERE IsActive = 'Y' AND AD_Client_ID = " + AD_Client_ID
				+ " ORDER BY Name ASC ";

		ps = DB.prepareStatement(sql, (Trx) null);
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				chargeName.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fVO_C_Charge_ID.listBoxVO = new ListBoxVO(chargeName, null);
		componentVO.fieldVOs.add(fVO_C_Charge_ID);

//		// Field Product
//		FieldVO fVO_M_Product_ID = new FieldVO("M_Product_ID", "Product",
//				DisplayTypeConstants.TableDir, false);
//		ArrayList<NamePair> product = new ArrayList<NamePair>();
//		sql = "SELECT M_Product_ID, Name FROM M_Product "
//				+ "WHERE IsActive = 'Y' " + "AND AD_Client_ID = "
//				+ AD_Client_ID;
//
//		ps = DB.prepareStatement(sql, (Trx) null);
//		try {
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				product.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
//			}
//			rs.close();
//			ps.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		fVO_M_Product_ID.listBoxVO = new ListBoxVO(product, null);
//		componentVO.fieldVOs.add(fVO_M_Product_ID);
		
		FieldVO fVO_M_Product_ID = new FieldVO("M_Product_ID", Msg.translate(
				Env.getCtx(), "Product"), DisplayTypeConstants.Search,
				false);
		fVO_M_Product_ID.tableName = "M_Product";
		componentVO.fieldVOs.add(fVO_M_Product_ID);

		// Field Supplier
		FieldVO fVO_Suplier_ID = new FieldVO("Suplier_ID", "Suplier",
				DisplayTypeConstants.TableDir, false);
		ArrayList<NamePair> suplier = new ArrayList<NamePair>();
		sql = "SELECT C_BPartner_ID, Name FROM C_BPartner "
				+ "WHERE IsActive = 'Y' "
				+ "AND C_BPartner_ID IN "
				+ "(SELECT C_BPartner_ID FROM M_Product_PO WHERE AD_Client_ID = "
				+ AD_Client_ID + ") " + " AND AD_Client_ID = " + AD_Client_ID;

		ps = DB.prepareStatement(sql, (Trx) null);
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				suplier.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fVO_Suplier_ID.listBoxVO = new ListBoxVO(suplier, null);
		componentVO.fieldVOs.add(fVO_Suplier_ID);


		// Field Date Expected
		FieldVO fVO_DateExpected = new FieldVO("StartDate", "Date Expected",
				DisplayTypeConstants.Date, false);
		fVO_DateExpected.IsSameLine = false;
		componentVO.fieldVOs.add(fVO_DateExpected);

		// Field Date Expected
		FieldVO fVO_EndDateExpected = new FieldVO("EndDate", "-",
				DisplayTypeConstants.Date, false);
		fVO_EndDateExpected.IsSameLine = true;
		componentVO.fieldVOs.add(fVO_EndDateExpected);

		// Field Button Search
		FieldVO fVO_BtnSearch = new FieldVO("btnSearch", "Search",
				DisplayTypeConstants.Button, false);
		fVO_BtnSearch.IsSameLine = false;
		componentVO.fieldVOs.add(fVO_BtnSearch);

		return componentVO;
	}

	@Override
	public TableModel getQueryResults(QueryVO queryVO,
			WindowImplIntf windowImpl, WindowCtx windowCtx, FieldVO fieldVO,
			int startRow, int rowCount, boolean countOnly, boolean asc,
			int sortCol) {
		// TODO Auto-generated method stub
		return null;
	}
}
