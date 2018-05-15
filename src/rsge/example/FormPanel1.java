/**
 * 
 */
package rsge.example;

import java.awt.Component;
import java.security.KeyPair;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.compiere.common.QueryVO;
import org.compiere.common.TableModel;
import org.compiere.common.constants.DisplayTypeConstants;
import org.compiere.intf.ComponentImplIntf;
import org.compiere.intf.WindowImplIntf;
import org.compiere.util.DB;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.Trx;
import org.compiere.vos.ComponentVO;
import org.compiere.vos.FieldVO;
import org.compiere.vos.ListBoxVO;
import org.compiere.vos.WindowCtx;
import org.compiere.vos.ComponentVO.ComponentType;

/**
 * @author programmer2
 *
 */
public class FormPanel1 implements ComponentImplIntf {

	/**
	 * 
	 */
	public FormPanel1() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.intf.ComponentImplIntf#getComponentVO()
	 */
	@Override
	public ComponentVO getComponentVO() {
		ComponentVO componentVO = new ComponentVO();
		componentVO.componentType = ComponentType.SEARCH;
		componentVO.name = "Pencarian";
		componentVO.tableName = "User";
		componentVO.fieldVOs = new ArrayList<FieldVO>();
		
		//Field User
		FieldVO fVO_AD_User_ID = new FieldVO("AD_User_ID", "User ID", DisplayTypeConstants.ID, false);
		ArrayList<NamePair> lUser = new ArrayList<NamePair>();
		String sql = "SELECT AD_User_ID, Name FROM AD_User ORDER BY Name";
		PreparedStatement pstmt = DB.prepareStatement(sql, (Trx) null);
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				lUser.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fVO_AD_User_ID.listBoxVO = new ListBoxVO(lUser, null);
		componentVO.fieldVOs.add(fVO_AD_User_ID);
		
		//Field Description
		FieldVO fVO_Desc = new FieldVO("fName", "Name", DisplayTypeConstants.String, false);
		componentVO.fieldVOs.add(fVO_Desc);
		
		//Button Search
		FieldVO fVO_btnSearch = new FieldVO("fBtnSearch", "Search", DisplayTypeConstants.Button, false);
		componentVO.fieldVOs.add(fVO_btnSearch);
		
		//Button Print User
		FieldVO fVO_btnPrint = new FieldVO("fBtnPrint", "Print User", DisplayTypeConstants.Button, false);
		componentVO.fieldVOs.add(fVO_btnPrint);
		
		return componentVO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.intf.ComponentImplIntf#getQueryResults(org.compiere.common.QueryVO, org.compiere.intf.WindowImplIntf, org.compiere.vos.WindowCtx, org.compiere.vos.FieldVO, int, int, boolean, boolean, int)
	 */
	@Override
	public TableModel getQueryResults(QueryVO queryVO,
			WindowImplIntf windowImpl, WindowCtx windowCtx, FieldVO fieldVO,
			int startRow, int rowCount, boolean countOnly, boolean asc,
			int sortCol) {
		// TODO Auto-generated method stub
		return null;
	}

}
