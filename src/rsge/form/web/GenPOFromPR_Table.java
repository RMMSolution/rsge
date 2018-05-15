/**
 * 
 */
package rsge.form.web;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.compiere.common.QueryVO;
import org.compiere.common.TableModel;
import org.compiere.common.constants.DisplayTypeConstants;
import org.compiere.intf.ComponentImplIntf;
import org.compiere.intf.WindowImplIntf;
import org.compiere.util.CLogger;
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
public class GenPOFromPR_Table implements ComponentImplIntf {
	private CLogger s_log = CLogger.getCLogger(GenPOFromPR_Table.class);
	int AD_Client_ID = 0;
	int AD_Org_ID = 0;

	/**
	 * 
	 */
	public GenPOFromPR_Table(Ctx serverCtx) {
		// TODO Auto-generated constructor stub
		AD_Client_ID = serverCtx.getAD_Client_ID();
		AD_Org_ID = serverCtx.getAD_Org_ID();
	}

	@Override
	public ComponentVO getComponentVO() {
		// TODO Auto-generated method stub
		ComponentVO componentVO = new ComponentVO();
		componentVO.componentType = ComponentType.TABLE_MULTI;
		componentVO.name = "";
		componentVO.tableName = "";
		componentVO.fieldVOs = new ArrayList<FieldVO>();

		// Column M_Requisition_ID
		FieldVO fVO_Requisition_ID = new FieldVO("M_Requisition_ID", "ID",
				DisplayTypeConstants.ID, false);
		fVO_Requisition_ID.IsReadOnly = true;
		fVO_Requisition_ID.IsDisplayed = false;
		componentVO.fieldVOs.add(fVO_Requisition_ID);

		// Column M_RequisitionLine_ID
		FieldVO fVO_M_RequisitionLine_ID = new FieldVO("M_RequisitionLine_ID",
				"Requisition Line", DisplayTypeConstants.ID, false);
		fVO_M_RequisitionLine_ID.IsReadOnly = true;
		fVO_M_RequisitionLine_ID.IsDisplayed = false;
		componentVO.fieldVOs.add(fVO_M_RequisitionLine_ID);

		// Column OrgName
		FieldVO fVO_OrgName = new FieldVO("OrgName", "Organization",
				DisplayTypeConstants.String, false);
		fVO_OrgName.IsReadOnly = true;
		componentVO.fieldVOs.add(fVO_OrgName);

		// Column Date Doc
		FieldVO fVO_DateDoc = new FieldVO("DateDoc", "Document Date",
				DisplayTypeConstants.String, false);
		fVO_DateDoc.IsReadOnly = true;
		componentVO.fieldVOs.add(fVO_DateDoc);

		// Column Description
		FieldVO fVO_Description = new FieldVO("Description", "Description",
				DisplayTypeConstants.String, false);
		fVO_Description.IsReadOnly = true;
		componentVO.fieldVOs.add(fVO_Description);

		// Column Date Expected
		FieldVO fVO_DateExpected = new FieldVO("DateExpeted", "Date Expected",
				DisplayTypeConstants.String, false);
		fVO_DateExpected.IsReadOnly = true;
		componentVO.fieldVOs.add(fVO_DateExpected);

		// Column Quantity
		FieldVO fVO_Qty = new FieldVO("Quantity", "Qty",
				DisplayTypeConstants.Quantity, false);
		fVO_Qty.IsReadOnly = true;
		componentVO.fieldVOs.add(fVO_Qty);

		return componentVO;
	}

	@Override
	public TableModel getQueryResults(QueryVO queryVO,
			WindowImplIntf windowImpl, WindowCtx windowCtx, FieldVO fieldVO,
			int startRow, int rowCount, boolean countOnly, boolean asc,
			int sortCol) {
		// TODO Auto-generated method stub
		TableModel result = new TableModel();
		int row = 0;
		int row3 = 0;

		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Date dt = null;
		Date dt1 = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");

		StringBuffer sql = new StringBuffer(
				"SELECT a.M_Requisition_ID, a.M_RequisitionLine_ID, " +
				"(SELECT OrgName FROM XX_ClientOrg WHERE Org_ID = a.AD_Org_ID), " +
				"b.DateDoc, " +
				"COALESCE((SELECT Name FROM M_Product WHERE M_Product_ID = a.M_Product_ID),(SELECT Name FROM C_Charge " +
				"WHERE C_Charge_ID = a.C_Charge_ID)) As Description, " +
				"b.DateRequired , a.Qty " +
				"FROM M_RequisitionLine a " +
				"INNER JOIN M_Requisition b ON (a.M_Requisition_ID = b.M_Requisition_ID) " +
				"WHERE a.AD_Client_ID = "+AD_Client_ID+" AND a.C_OrderLine_ID IS NULL ");

		if (windowCtx.get("AD_Org_ID") != null
				&& windowCtx.getAsInt("AD_Org_ID") > 0) {
			sql.append(" AND a.AD_Org_ID =    "
					+ windowCtx.getAsInt("AD_Org_ID"));
		}

		if (windowCtx.get("M_Product_Category_ID") != null
				&& windowCtx.getAsInt("M_Product_Category_ID") > 0) {
			sql.append(" AND a.M_Product_ID IN " +
				"(SELECT M_Product_ID FROM M_Product " +
				"WHERE M_Product_Category_ID = "+windowCtx.getAsInt("M_Product_Category_ID")+" " +
						"AND AD_Client_ID = "+AD_Client_ID+")");
		}

		if (windowCtx.get("C_Charge_ID") != null
				&& windowCtx.getAsInt("C_Charge_ID") > 0) {
			sql.append(" AND a.C_Charge_ID =   "
					+ windowCtx.getAsInt("C_Charge_ID"));
		}
		

		if (windowCtx.get("M_Product_ID") != null
				&& windowCtx.getAsInt("M_Product_ID") > 0) {
			sql.append(" AND a.M_Product_ID =   "
					+ windowCtx.getAsInt("M_Product_ID"));
		}
		
		if (windowCtx.get("Suplier_ID") != null
				&& windowCtx.getAsInt("Suplier_ID") > 0) {
			sql.append(" AND a.M_Product_ID IN " +
				"(SELECT M_Product_ID FROM M_Product_PO WHERE AD_Client_ID = "+AD_Client_ID+" AND C_BPartner_ID = "+windowCtx.getAsInt("Suplier_ID")+")   ");
		}

		
		if (windowCtx.get("StartDate") != null
				&& windowCtx.getAsLong("StartDate") > 0 && windowCtx.get("EndDate") != null
				&& windowCtx.getAsLong("EndDate") > 0) {
			sql.append(" AND a.M_Requisition_ID IN " +
				"(SELECT M_Requisition_ID FROM M_Requisition WHERE DateRequired BETWEEN '"+new Timestamp(windowCtx.getAsLong("StartDate"))+"' " +
						"AND '"+new Timestamp(windowCtx.getAsLong("EndDate"))+"')  ");
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
					try {
						dt = dateFormat.parse(rs.getString(4));
						dt1 = dateFormat.parse(rs.getString(6));
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					result.addRow(
							row,
							new String[] { rs.getString(1), rs.getString(2),
									rs.getString(3),
									df.format(dt),
									rs.getString(5), df.format(dt1),
									rs.getString(7) });
					++row;
				}
				row3 = row3 + row;
				startRow = row3;
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
