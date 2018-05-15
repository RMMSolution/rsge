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
import org.compiere.util.KeyNamePair;
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
public class CheckPurchaseRequisition_Header implements ComponentImplIntf{
	private Ctx serverCtx;
	private int AD_Client_ID = 0;
	private int AD_Org_ID = 0;
	private WindowCtx windowCtx = null;

	/**
	 * 
	 */
	public CheckPurchaseRequisition_Header(Ctx serverCtx, WindowCtx windowCtx) {
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
		componentVO.numColumns = 1;
		componentVO.fieldVOs = new ArrayList<FieldVO>();

		AD_Client_ID = serverCtx.getAD_Client_ID();
		AD_Org_ID = serverCtx.getAD_Org_ID();

		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Field Organization
		FieldVO fVO_XX_PurchaseRequisition_ID = new FieldVO("XX_PurchaseRequisition_ID", "Purchase Requisition",
				DisplayTypeConstants.TableDir, false);
		ArrayList<NamePair> orgName = new ArrayList<NamePair>();
		sql = "SELECT XX_PurchaseRequisition_ID, DocumentNo FROM XX_PurchaseRequisition " +
				"WHERE DocStatus IN ('CO','CL') AND AD_Client_ID = ? " +
				"AND AD_Org_ID = ? " +
				"ORDER BY DocumentNo ASC ";

		ps = DB.prepareStatement(sql, (Trx) null);
		try {
			ps.setInt(1, AD_Client_ID);
			ps.setInt(2, AD_Org_ID);
			rs = ps.executeQuery();
			while (rs.next()) {
				orgName.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fVO_XX_PurchaseRequisition_ID.listBoxVO = new ListBoxVO(orgName, null);
		componentVO.fieldVOs.add(fVO_XX_PurchaseRequisition_ID);


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
