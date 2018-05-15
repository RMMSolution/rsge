/**
 * 
 */
package rsge.form.web;

import java.util.ArrayList;

import org.compiere.common.QueryVO;
import org.compiere.common.TableModel;
import org.compiere.common.constants.DisplayTypeConstants;
import org.compiere.intf.ComponentImplIntf;
import org.compiere.intf.WindowImplIntf;
import org.compiere.vos.ComponentVO;
import org.compiere.vos.FieldVO;
import org.compiere.vos.WindowCtx;
import org.compiere.vos.ComponentVO.ComponentType;

/**
 * @author bang
 *
 */
public class F_CreatePO_ButtonComplete implements ComponentImplIntf{

	public F_CreatePO_ButtonComplete() {
		// TODO Auto-generated constructor stub
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
		cVO.numColumns = 2;
		cVO.fieldVOs = new ArrayList<FieldVO>();

		//Field Button Complete
		FieldVO fVO_BtnCompleted = new FieldVO("bComplete", "Complete", DisplayTypeConstants.Button, false);
		cVO.fieldVOs.add(fVO_BtnCompleted);

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
