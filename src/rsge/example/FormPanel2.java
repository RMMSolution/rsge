/**
 * 
 */
package rsge.example;

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
 * @author programmer2
 *
 */
public class FormPanel2 implements ComponentImplIntf {

	/**
	 * 
	 */
	public FormPanel2() {
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
		
		//Button Display Order
		FieldVO fVO_btnOrder = new FieldVO("fBtnOrder", "Display Order", DisplayTypeConstants.Button, false);
		componentVO.fieldVOs.add(fVO_btnOrder);
		
		//Button Print Order
		FieldVO fVO_btnPrintOrder = new FieldVO("fBtnPrintOrder", "Print Order", DisplayTypeConstants.Button, false);
		componentVO.fieldVOs.add(fVO_btnPrintOrder);
		
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
