/**
 * 
 */
package rsge.example;

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
import org.compiere.vos.FieldVO;
import org.compiere.vos.WindowCtx;
import org.compiere.vos.ComponentVO.ComponentType;

/**
 * @author programmer2
 *
 */
public class FormTable2 implements ComponentImplIntf {

	private Ctx serverCtx;
	
	/**
	 * 
	 */
	public FormTable2(Ctx serverCtx) {
		this.serverCtx = serverCtx;
	}

	/* (non-Javadoc)
	 * @see org.compiere.intf.ComponentImplIntf#getComponentVO()
	 */
	@Override
	public ComponentVO getComponentVO() {
		ComponentVO cVO = new ComponentVO();
		cVO.componentType = ComponentType.TABLE_MULTI;
		cVO.name = "Tabel 2";
		cVO.tableName = "Order";
		cVO.fieldVOs = new ArrayList<FieldVO>();
		
		//Column ID Order
		FieldVO fVO_C_Order_ID = new FieldVO("fOrder", "ID Order", DisplayTypeConstants.ID, false);
		fVO_C_Order_ID.IsReadOnly = true;
		cVO.fieldVOs.add(fVO_C_Order_ID);
		
		//Column Grand Total
		FieldVO fVO_GrandTotal = new FieldVO("fGrandTotal", "Grand Total", DisplayTypeConstants.Number, false);
		fVO_GrandTotal.IsReadOnly = true;
		cVO.fieldVOs.add(fVO_GrandTotal);
		
		//Column Total Lines
		FieldVO fVO_TotalLines = new FieldVO("fTotalLines", "Total Lines", DisplayTypeConstants.Number, false);
		fVO_TotalLines.IsReadOnly = true;
		cVO.fieldVOs.add(fVO_TotalLines);
		
		return cVO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.intf.ComponentImplIntf#getQueryResults(org.compiere.common.QueryVO, org.compiere.intf.WindowImplIntf, org.compiere.vos.WindowCtx, org.compiere.vos.FieldVO, int, int, boolean, boolean, int)
	 */
	@Override
	public TableModel getQueryResults(QueryVO queryVO,
			WindowImplIntf windowImpl, WindowCtx windowCtx, FieldVO fieldVO,
			int startRow, int rowCount, boolean countOnly, boolean asc,
			int sortCol) {

		TableModel result = new TableModel();
		ArrayList slctRow2 = windowCtx.getSelectedRows(1);
		Object[] slctVal2 = null;
		int AD_User_ID = 0;
		
		for (int i = 0; i < slctRow2.size(); i++) {
			slctVal2 = windowCtx.getSelectedRows(1).get(i);
			if (slctVal2.length > 0) {
				AD_User_ID = Integer.valueOf(slctVal2[0].toString());
			}
		}
		
		String sql = "SELECT C_Order_ID, GrandTotal, TotalLines" +
					 " FROM C_Order WHERE AD_User_ID = " + AD_User_ID;
		
		if (countOnly) {
			try {
				PreparedStatement pstmt = DB.prepareStatement("SELECT COUNT(*) FROM (" + sql + ")", (Trx) null);
				/*pstmt.setInt(1, serverCtx.getAD_Client_ID());
				pstmt.setInt(2, serverCtx.getAD_Org_ID());*/
				ResultSet rs = pstmt.executeQuery();
				int row = 0;
				while (rs.next()) {
					result.addRow(row, new String[] {rs.getString(1)});
					++row;
				}
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				PreparedStatement pstmt = DB.prepareStatement(sql, (Trx) null);
				/*pstmt.setInt(1, serverCtx.getAD_Client_ID());
				pstmt.setInt(2, serverCtx.getAD_Org_ID());*/
				ResultSet rs = pstmt.executeQuery();
				int row = 0;
				while (rs.next() && row < startRow + rowCount) {
					if (row >= startRow)
						result.addRow(row, new String[] {rs.getString(1), rs.getString(2), rs.getString(3)});
					++row;
				}
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
