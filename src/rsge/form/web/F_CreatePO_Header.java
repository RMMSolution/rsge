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
public class F_CreatePO_Header implements ComponentImplIntf{

	private Ctx serverCtx;
//	private WindowCtx windowCtx = null;
	
	/**
	 * 
	 */
	public F_CreatePO_Header(Ctx serverCtx, WindowCtx windowCtx) {
		this.serverCtx = serverCtx;
//		this.windowCtx = windowCtx;
	}

	/* (non-Javadoc)
	 * @see org.compiere.intf.ComponentImplIntf#getComponentVO()
	 */
	@Override
	public ComponentVO getComponentVO() {
		ComponentVO cVO = new ComponentVO();
		cVO.componentType = ComponentType.SEARCH;
		cVO.name = "";
		cVO.tableName = "";
		cVO.numColumns = 3;
		cVO.fieldVOs = new ArrayList<FieldVO>();

		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//Field Requisition
		FieldVO fVO_Req = new FieldVO("fRequisition", "Requisition", DisplayTypeConstants.TableDir, false);
		ArrayList<NamePair> lReq = new ArrayList<NamePair>();
		fVO_Req.isImpactsValue = true;
		sql = "SELECT M_Requisition_ID, DocumentNo FROM M_Requisition " +
				"WHERE AD_Client_ID = "+serverCtx.getAD_Client_ID()+" AND DocStatus IN ('CO','CL') AND M_Requisition_ID  NOT IN " +
				"(SELECT M_Requisition_ID FROM M_RequisitionLine WHERE C_OrderLine_ID IS NOT NULL " +
				"AND AD_Client_ID = "+serverCtx.getAD_Client_ID()+")";	
		ps = DB.prepareStatement(sql, (Trx) null);
		try {
			ps.setInt(1, serverCtx.getAD_Client_ID());
			rs = ps.executeQuery();
			while (rs.next()) {
				lReq.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fVO_Req.listBoxVO = new ListBoxVO(lReq, null);
		cVO.fieldVOs.add(fVO_Req);
		
		// RFQ
		FieldVO fVO_RFQ = new FieldVO("fRFQ", "RFQ", DisplayTypeConstants.TableDir, false);
//		fVO_RFQ.isImpactsValue = true;
		cVO.fieldVOs.add(fVO_RFQ);
		
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
		// TODO Auto-generated method stub
		return null;
	}

}
