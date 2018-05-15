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
import org.compiere.util.Trx;
import org.compiere.vos.ComponentVO;
import org.compiere.vos.ComponentVO.ComponentType;
import org.compiere.vos.FieldVO;
import org.compiere.vos.WindowCtx;

/**
 * @author bang
 *
 */
public class F_CreatePO_Table implements ComponentImplIntf{

	private Ctx serverCtx = null;

	/**
	 * 
	 */
	public F_CreatePO_Table(Ctx serverCtx) {
		this.serverCtx = serverCtx;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.intf.ComponentImplIntf#getComponentVO()
	 */
	@Override
	public ComponentVO getComponentVO() {
		ComponentVO cVO = new ComponentVO();
		cVO.componentType = ComponentType.TABLE_MULTI;
		cVO.name = "";
		cVO.tableName = "";
		cVO.numColumns = 2;
		cVO.fieldVOs = new ArrayList<FieldVO>();

		// 01. Column M_RequisitionLine_ID
		FieldVO fVO_M_RequisitionLine_ID = new FieldVO("cM_RequisitionLine_ID",
				"ID", DisplayTypeConstants.ID, false);
		fVO_M_RequisitionLine_ID.IsReadOnly = true;
		fVO_M_RequisitionLine_ID.IsDisplayed = false;
		cVO.fieldVOs.add(fVO_M_RequisitionLine_ID);

		// 02. Column Product
		FieldVO fVO_ProductName = new FieldVO("cProductName", "Product",
				DisplayTypeConstants.String, false);
		fVO_ProductName.IsReadOnly = true;
		fVO_ProductName.IsDisplayed = false;
		cVO.fieldVOs.add(fVO_ProductName);

		// 03. Column Charge
		FieldVO fVO_ChargeName = new FieldVO("cChargeName", "Charge",
				DisplayTypeConstants.String, false);
		fVO_ChargeName.IsReadOnly = true;
		fVO_ChargeName.IsDisplayed = true;
		cVO.fieldVOs.add(fVO_ChargeName);

		// 04. Column Qty
		FieldVO fVO_Qty = new FieldVO("cQty", "Qty",
				DisplayTypeConstants.Amount, false);
		fVO_Qty.IsReadOnly = true;
		fVO_Qty.IsDisplayed = true;
		cVO.fieldVOs.add(fVO_Qty);

		// 05. Column Price
		FieldVO fVO_PriceActual = new FieldVO("cPriceActual", "Price",
				DisplayTypeConstants.Amount, false);
		fVO_PriceActual.IsReadOnly = true;
		cVO.fieldVOs.add(fVO_PriceActual);

		// 06. Column Total
		FieldVO fVO_LineNetAmt = new FieldVO("cLineNetAmt", "Total",
				DisplayTypeConstants.Amount, false);
		fVO_LineNetAmt.IsReadOnly = true;
		fVO_LineNetAmt.IsDisplayed = true;
		cVO.fieldVOs.add(fVO_LineNetAmt);


		return cVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.compiere.intf.ComponentImplIntf#getQueryResults(org.compiere.common
	 * .QueryVO, org.compiere.intf.WindowImplIntf, org.compiere.vos.WindowCtx,
	 * org.compiere.vos.FieldVO, int, int, boolean, boolean, int)
	 */
	@Override
	public TableModel getQueryResults(QueryVO queryVO,
			WindowImplIntf windowImpl, WindowCtx windowCtx, FieldVO fieldVO,
			int startRow, int rowCount, boolean countOnly, boolean asc,
			int sortCol) {

		TableModel result = new TableModel();
		int row = 0;
		int row2 = 0;
		StringBuffer sql = new StringBuffer(
				"SELECT a.M_RequsitionLine_ID, (SELECT Name FROM M_Product WHERE M_Product_ID = a.M_Product_ID) As ProductName, " +
				"(SELECT Name FROM C_Charge WHERE C_Charge_ID = a.C_Charge_ID) As ChargeName, a.Qty, a.PriceActual, a.LineNetAmt " +
				"FROM M_RequisitionLine WHERE AD_Client_ID = "+serverCtx.getAD_Client_ID()+" ");
		if (windowCtx.get("fRequisition") != null && windowCtx.getAsInt("fRequisition") > 0) {
			sql.append(" AND a.M_Requisition_ID  = "
					+ windowCtx.getAsInt("fRFQ"));
		} else {
			// for refreshing the table only when no RFQ selected
			sql.append(" a.M_Requisition_ID IN " +
					"(SELECT M_Requisition_ID FROM M_Requisition " +
				"WHERE AD_Client_ID = "+serverCtx.getAD_Client_ID()+" AND DocStatus IN ('CO','CL') AND M_Requisition_ID  NOT IN " +
				"(SELECT M_Requisition_ID FROM M_RequisitionLine WHERE C_OrderLine_ID IS NOT NULL " +
				"AND AD_Client_ID = "+serverCtx.getAD_Client_ID()+"))");
		}
		if (countOnly) {
			try {
				PreparedStatement pstmt = DB.prepareStatement(
						"SELECT COUNT(*) FROM (" + sql.toString() + ")",
						(Trx) null);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					result.addRow(row, new String[] { rs.getString(1) });
				}
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				row = 0;
				sql.append(" LIMIT 20 OFFSET " + startRow);

				PreparedStatement pstmt = DB.prepareStatement(sql.toString(),
						(Trx) null);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					result.addRow(row,
							new String[] { rs.getString(1), 
							rs.getString(2),
							rs.getString(3), 
							rs.getString(4),
							rs.getString(5) });
					++row;
				}
				row2 = row2 + row;
				startRow = row2;
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
