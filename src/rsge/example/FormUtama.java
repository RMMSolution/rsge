/**
 * 
 */
package rsge.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import org.compiere.common.QueryRestrictionVO;
import org.compiere.common.QueryVO;
import org.compiere.common.constants.DisplayTypeConstants;
import org.compiere.intf.ComponentImplIntf;
import org.compiere.intf.WindowImplIntf;
import org.compiere.layout.Box;
import org.compiere.util.CLogger;
import org.compiere.util.Ctx;
import org.compiere.vos.ChangeVO;
import org.compiere.vos.ResponseVO;
import org.compiere.vos.UWindowID;
import org.compiere.vos.WindowCtx;
import org.compiere.vos.WindowVO.ClientWindowType;

/**
 * @author programmer2
 *
 */
public class FormUtama implements WindowImplIntf {
	
	private CLogger s_log = CLogger.getCLogger(FormUtama.class);
	
	private int windowNO;
	private Ctx serverCtx;
	private WindowCtx windowCtx;
	private UWindowID uid;
	
	/**
	 * 
	 */
	public FormUtama(int windowNO, Ctx serverCtx, WindowCtx windowCtx, UWindowID uid) {
		this.windowNO = windowNO;
		this.serverCtx = serverCtx;
		this.windowCtx = windowCtx;
		this.uid = uid;
	}

	/* (non-Javadoc)
	 * @see org.compiere.intf.WindowImplIntf#getClientWindowType()
	 */
	@Override
	public ClientWindowType getClientWindowType() {
		return ClientWindowType.GENERIC_STACK;
	}

	/* (non-Javadoc)
	 * @see org.compiere.intf.WindowImplIntf#getComponents()
	 */
	@Override
	public ArrayList<ComponentImplIntf> getComponents() {
		ArrayList<ComponentImplIntf> componentArrayList = new ArrayList<ComponentImplIntf>();
		componentArrayList.add(new FormPanel1());
		componentArrayList.add(new FormTable1(serverCtx));
		componentArrayList.add(new FormPanel2());
		componentArrayList.add(new FormTable2(serverCtx));
		return componentArrayList;
	}

	/* (non-Javadoc)
	 * @see org.compiere.intf.WindowImplIntf#getLayout()
	 */
	@Override
	public Box getLayout() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.intf.WindowImplIntf#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.intf.WindowImplIntf#processCallback(java.lang.String)
	 */
	@Override
	public ChangeVO processCallback(String sender) {
		Object[] slctVal = null;
		Object[] slctVal1 = null;
		ArrayList slctRow = null;
		ArrayList slctRow1 = null;
		String res = "";
		String res1 = "";
		ChangeVO changeVO = new ChangeVO();
		changeVO.queryComponents = new HashMap<Integer, QueryVO>();
		QueryVO queryVO = new QueryVO();
		
		if(sender.equals("fBtnSearch")) {	
			changeVO.queryComponents.put(1, queryVO);
		}
		
		if (sender.equals("fBtnOrder")) {
			changeVO.queryComponents.put(3, queryVO);
		}
		
		if(sender.equals("fBtnPrint")) {
			slctRow = windowCtx.getSelectedRows(1);
			for (int i = 0; i < slctRow.size(); i++) {
				slctVal = windowCtx.getSelectedRows(1).get(i);
				s_log.log(Level.SEVERE, "User ID : " + slctVal[0].toString() + " ; Name : " + slctVal[1].toString());
				res += "User ID : " + slctVal[0].toString() + " ; Name : " + slctVal[1].toString();
			}
			changeVO.addSuccess(res);
			changeVO.showResults(true);
		}
		
		if(sender.equals("fBtnPrintOrder")) {
			slctRow1 = windowCtx.getSelectedRows(3);
			for (int i = 0; i < slctRow1.size(); i++) {
				slctVal1 = windowCtx.getSelectedRows(3).get(i);
				s_log.log(Level.SEVERE, "Order ID : " + slctVal1[0].toString() + " ; Grand Total : "
						+ slctVal1[1].toString() + " ; Total Lines : " + slctVal1[2].toString());
				res1 += "Order ID : " + slctVal1[0].toString() + " ; Grand Total : "
						+ slctVal1[1].toString() + " ; Total Lines : " + slctVal1[2].toString();
			}
			changeVO.addSuccess(res1);
			changeVO.showResults(true);
		}
		
		return changeVO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.intf.WindowImplIntf#validateResponse(org.compiere.vos.ResponseVO)
	 */
	@Override
	public void validateResponse(ResponseVO responseVO) {
		// TODO Auto-generated method stub

	}

}
