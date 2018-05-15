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
public class CheckPurchaseRequisition_Table implements ComponentImplIntf {

	int AD_Client_ID = 0;
	int AD_Org_ID = 0;

	/**
	 * 
	 */
	public CheckPurchaseRequisition_Table(Ctx serverCtx) {
		// TODO Auto-generated constructor stub
		AD_Client_ID = serverCtx.getAD_Client_ID();
		AD_Org_ID = serverCtx.getAD_Org_ID();
	}

	@Override
	public ComponentVO getComponentVO() {
		// TODO Auto-generated method stub
		ComponentVO componentVO = new ComponentVO();
		componentVO.componentType = ComponentType.TABLE_SINGLE;
		componentVO.name = "";
		componentVO.tableName = "";
		componentVO.fieldVOs = new ArrayList<FieldVO>();

		// Column ReceiptQC
		FieldVO fVO_XX_PurchaseRequisition_ID = new FieldVO(
				"XX_PurchaseRequisition_ID", "Purchase Requisition",
				DisplayTypeConstants.ID, false);
		fVO_XX_PurchaseRequisition_ID.IsReadOnly = true;
		fVO_XX_PurchaseRequisition_ID.IsDisplayed = false;
		componentVO.fieldVOs.add(fVO_XX_PurchaseRequisition_ID);

		// Column ProductName
		FieldVO fVO_DocumentNo = new FieldVO("DocumentNo", "Document No",
				DisplayTypeConstants.String, false);
		fVO_DocumentNo.IsReadOnly = true;
		componentVO.fieldVOs.add(fVO_DocumentNo);

		// Column ProductName
		FieldVO fVO_PONo = new FieldVO("PONo", "PO No",
				DisplayTypeConstants.String, false);
		fVO_PONo.IsReadOnly = true;
		componentVO.fieldVOs.add(fVO_PONo);

		// Column ProductName
		FieldVO fVO_ReceiptNo = new FieldVO("ReceiptNo", "Receipt No",
				DisplayTypeConstants.String, false);
		fVO_ReceiptNo.IsReadOnly = true;
		componentVO.fieldVOs.add(fVO_ReceiptNo);

		// Column ProductName
		FieldVO fVO_InvoiceNo = new FieldVO("ReceiptNo", "Receipt No",
				DisplayTypeConstants.String, false);
		fVO_InvoiceNo.IsReadOnly = true;
		componentVO.fieldVOs.add(fVO_InvoiceNo);


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

		StringBuffer sql = new StringBuffer(
				"SELECT a.XX_PurchaseRequisition_ID, a.DocumentNo,"
						+ "(SELECT DocumentNo FROM C_Order "
						+ "WHERE C_Order_ID IN (SELECT C_Order_ID FROM C_OrderLine "
						+ "WHERE C_OrderLine_ID IN "
						+ "(SELECT C_OrderLine_ID FROM XX_PurchaseRequisitionLine "
						+ "WHERE XX_PurchaseRequisition_ID = a.XX_PurchaseRequisition_ID "
						+ "GROUP BY C_OrderLine_ID )) "
						+ "GROUP BY DocumentNo "
						+ "ORDER BY DocumentNo LIMIT 1 ) As PONo, "
						+ "(SELECT DocumentNo FROM M_InOut "
						+ "WHERE C_Order_ID IN "
						+ "(SELECT C_Order_ID FROM C_OrderLine "
						+ "WHERE C_OrderLine_ID IN "
						+ "(SELECT C_OrderLine_ID FROM XX_PurchaseRequisitionLine "
						+ "WHERE XX_PurchaseRequisition_ID = a.XX_PurchaseRequisition_ID "
						+ "GROUP BY C_OrderLine_ID )) "
						+ "GROUP BY DocumentNo "
						+ "ORDER BY DocumentNo LIMIT 1) As ReceiptNo, "
						+ "(SELECT DocumentNo FROM "
						+ "(SELECT DocumentNo FROM C_Invoice "
						+ "WHERE C_Invoice_ID IN "
						+ "(SELECT C_Invoice_ID FROM C_InvoiceLine "
						+ "WHERE M_InOutLine_ID IN "
						+ "(SELECT M_InOutLine_ID FROM M_InOutLine "
						+ "WHERE M_InOut_ID IN "
						+ "((SELECT M_InOut_ID FROM M_InOut "
						+ "WHERE C_Order_ID IN "
						+ "(SELECT C_Order_ID FROM C_OrderLine "
						+ "WHERE C_OrderLine_ID IN "
						+ "(SELECT C_OrderLine_ID FROM XX_PurchaseRequisitionLine "
						+ "WHERE XX_PurchaseRequisition_ID = a.XX_PurchaseRequisition_ID "
						+ "GROUP BY C_OrderLine_ID )) "
						+ "GROUP BY M_InOut_ID "
						+ "ORDER BY M_InOut_ID LIMIT 1)))) "
						+ "UNION "
						+ "SELECT DocumentNo FROM C_Invoice "
						+ "WHERE C_Order_ID IN "
						+ "(SELECT C_Order_ID FROM C_OrderLine "
						+ "WHERE C_OrderLine_ID IN "
						+ "(SELECT C_OrderLine_ID FROM XX_PurchaseRequisitionLine "
						+ "WHERE XX_PurchaseRequisition_ID = a.XX_PurchaseRequisition_ID "
						+ "GROUP BY C_OrderLine_ID )) "
						+ ") GROUP BY DocumentNo "
						+ "ORDER BY DocumentNo ASC LIMIT 1) As InvoiceNo, "
						+ "(SELECT Description FROM C_Order WHERE C_Order_ID IN "
						+ "(SELECT DocumentNo FROM C_Order "
						+ "WHERE C_Order_ID IN (SELECT C_Order_ID FROM C_OrderLine "
						+ "WHERE C_OrderLine_ID IN "
						+ "(SELECT C_OrderLine_ID FROM XX_PurchaseRequisitionLine  "
						+ "WHERE XX_PurchaseRequisition_ID = a.XX_PurchaseRequisition_ID "
						+ "GROUP BY C_OrderLine_ID )) "
						+ "GROUP BY DocumentNo "
						+ "ORDER BY DocumentNo LIMIT 1 )) As DescriptionPO, "
						+ "(SELECT Description FROM M_InOut "
						+ "WHERE M_InOut_ID IN "
						+ "((SELECT M_InOut_ID FROM M_InOut "
						+ "WHERE C_Order_ID IN "
						+ "(SELECT C_Order_ID FROM C_OrderLine "
						+ "WHERE C_OrderLine_ID IN "
						+ "(SELECT C_OrderLine_ID FROM XX_PurchaseRequisitionLine "
						+ "WHERE XX_PurchaseRequisition_ID = a.XX_PurchaseRequisition_ID "
						+ "GROUP BY C_OrderLine_ID )) GROUP BY M_InOut_ID "
						+ "ORDER BY M_InOut_ID LIMIT 1))) As DescriptionReceipt, "
						+ "(SELECT Description FROM "
						+ "(SELECT Description FROM C_Invoice "
						+ "WHERE C_Invoice_ID IN "
						+ "(SELECT C_Invoice_ID FROM C_InvoiceLine "
						+ "WHERE M_InOutLine_ID IN "
						+ "(SELECT M_InOutLine_ID FROM M_InOutLine "
						+ "WHERE M_InOut_ID IN "
						+ "((SELECT M_InOut_ID FROM M_InOut "
						+ "WHERE C_Order_ID IN "
						+ "(SELECT C_Order_ID FROM C_OrderLine "
						+ "WHERE C_OrderLine_ID IN "
						+ "(SELECT C_OrderLine_ID FROM XX_PurchaseRequisitionLine "
						+ "WHERE XX_PurchaseRequisition_ID = a.XX_PurchaseRequisition_ID "
						+ "GROUP BY C_OrderLine_ID )) GROUP BY M_InOut_ID "
						+ "ORDER BY M_InOut_ID LIMIT 1)))) "
						+ "UNION "
						+ "SELECT Description FROM C_Invoice "
						+ "WHERE C_Order_ID IN "
						+ "(SELECT C_Order_ID FROM C_OrderLine "
						+ "WHERE C_OrderLine_ID IN "
						+ "(SELECT C_OrderLine_ID FROM XX_PurchaseRequisitionLine "
						+ "WHERE XX_PurchaseRequisition_ID = a.XX_PurchaseRequisition_ID "
						+ "GROUP BY C_OrderLine_ID ))) "
						+ "GROUP BY Description "
						+ "ORDER BY Description ASC LIMIT 1) As DescriptionInv  "
						+ "FROM XX_PurchaseRequisition a  "
						+ "WHERE DocStatus IN ('CO','CL') AND AD_Client_ID = "
						+ AD_Client_ID);

		if (windowCtx.get("XX_PurchaseRequisition_ID") != null
				&& windowCtx.getAsInt("XX_PurchaseRequisition_ID") > 0) {
			sql.append("  AND a.XX_PurchaseRequisition_ID = "
					+ windowCtx.getAsInt("XX_PurchaseRequisition_ID") + " ");
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
				PreparedStatement pstmt1 = DB.prepareStatement(sql.toString(),
						(Trx) null);
				ResultSet rs1 = pstmt1.executeQuery();
				while (rs1.next()) {

					result.addRow(row,
							new String[] { rs1.getString(1), rs1.getString(2),
									rs1.getString(3)+" "+rs1.getString(6), 
									rs1.getString(4)+" "+rs1.getString(7), 
									rs1.getString(5)+" "+rs1.getString(8)});
					++row;
				}
				row3 = row3 + row;
				startRow = row3;
				rs1.close();
				pstmt1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
