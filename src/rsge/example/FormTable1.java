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
public class FormTable1 implements ComponentImplIntf {

	private Ctx serverCtx;
	/**
	 * 
	 */
	public FormTable1(Ctx serverCtx) {
		this.serverCtx = serverCtx;
	}

	/* (non-Javadoc)
	 * @see org.compiere.intf.ComponentImplIntf#getComponentVO()
	 */
	@Override
	public ComponentVO getComponentVO() {
		ComponentVO cVO = new ComponentVO();
		cVO.componentType = ComponentType.TABLE_SINGLE;
		cVO.name = "Table 1";
		cVO.tableName = "User";
		cVO.fieldVOs = new ArrayList<FieldVO>();
		
		//Column User ID
		FieldVO fVO_AD_User_ID = new FieldVO("AD_User_ID", "User ID", DisplayTypeConstants.ID, false);
		fVO_AD_User_ID.IsReadOnly = true;
		cVO.fieldVOs.add(fVO_AD_User_ID);
		
		//Column Name
		FieldVO fVO_Name = new FieldVO("fName", "Name", DisplayTypeConstants.String, false);
		fVO_Name.IsReadOnly = true;
		cVO.fieldVOs.add(fVO_Name);
		
		//Column Description
		FieldVO fVO_Desc = new FieldVO("fDesc", "Description", DisplayTypeConstants.String, false);
		fVO_Desc.IsReadOnly = true;
		cVO.fieldVOs.add(fVO_Desc);
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
		String sql = "SELECT AD_User_ID, Name, Description FROM AD_User WHERE AD_Client_ID = ? AND AD_Org_ID = ?";
		if(windowCtx.get("AD_User_ID") != null) 
			sql += " AND AD_User_ID = " + windowCtx.getAsInt("AD_User_ID");
		if(windowCtx.get("fName") != null) 
			sql += " AND Name LIKE '%" + windowCtx.get("fName") +"%'";
		sql += " ORDER BY Name";
		
		if (countOnly) {
			try {
				PreparedStatement pstmt = DB.prepareStatement("SELECT COUNT(*) FROM (" + sql + ")", (Trx) null);
				pstmt.setInt(1, serverCtx.getAD_Client_ID());
				pstmt.setInt(2, serverCtx.getAD_Org_ID());
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
				pstmt.setInt(1, serverCtx.getAD_Client_ID());
				pstmt.setInt(2, serverCtx.getAD_Org_ID());
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
