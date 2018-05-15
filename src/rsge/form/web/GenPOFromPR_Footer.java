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
import org.compiere.vos.ComponentVO.ComponentType;
import org.compiere.vos.FieldVO;
import org.compiere.vos.ListBoxVO;
import org.compiere.vos.WindowCtx;

/**
 * @author bang
 * 
 */
public class GenPOFromPR_Footer implements ComponentImplIntf {
	private Ctx serverCtx;
	int AD_Client_ID = 0;
	int AD_Org_ID = 0;

	/**
	 * 
	 */
	public GenPOFromPR_Footer(Ctx serverCtx) {
		// TODO Auto-generated constructor stub
		this.serverCtx = serverCtx;
	}

	@Override
	public ComponentVO getComponentVO() {
		// TODO Auto-generated method stub
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AD_Client_ID = serverCtx.getAD_Client_ID();
		ComponentVO cvo = new ComponentVO();
		cvo.componentType = ComponentType.SEARCH;
		cvo.tableName = "";
		cvo.name = "";
		cvo.numColumns = 2;
		cvo.fieldVOs = new ArrayList<FieldVO>();

		// panel vendor
		FieldVO vendorName = new FieldVO("C_BPartner_ID", Msg.translate(
				Env.getCtx(), "Vendor"), DisplayTypeConstants.Search,
				false);
		vendorName.tableName = "C_BPartner";
		cvo.fieldVOs.add(vendorName);

		// Field PriceList
		FieldVO fVO_M_PriceList_ID = new FieldVO(
				"M_PriceList_ID", "Price List",
				DisplayTypeConstants.TableDir, false);
		fVO_M_PriceList_ID.isImpactsValue = true;
		ArrayList<NamePair> categoryName = new ArrayList<NamePair>();
		sql = "SELECT M_PriceList_ID, Name FROM M_PriceList " +
				"WHERE AD_Client_ID = "+AD_Client_ID+" AND IsSOPriceList = 'N' AND IsActive = 'Y'  ORDER BY Name ASC ";

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
		fVO_M_PriceList_ID.listBoxVO = new ListBoxVO(categoryName, null);
		cvo.fieldVOs.add(fVO_M_PriceList_ID);
		
		// Field Date Ordered
		FieldVO fVO_DateOrdered = new FieldVO("DateOrdered", "Date Ordered",
				DisplayTypeConstants.Date, false);
		fVO_DateOrdered.IsSameLine = false;
		cvo.fieldVOs.add(fVO_DateOrdered);

		// Field Date Promised
		FieldVO fVO_DatePromised = new FieldVO("DatePromised", "DatePromised",
				DisplayTypeConstants.Date, false);
		fVO_DatePromised.IsSameLine = true;
		cvo.fieldVOs.add(fVO_DatePromised);
		//Field Button Search
				FieldVO fVO_BtnProcess = new FieldVO("btnProcess", "Process", DisplayTypeConstants.Button, false);
				cvo.fieldVOs.add(fVO_BtnProcess);
		return cvo;
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
