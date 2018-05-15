/**
 * 
 */
package rsge.form.web;

import java.util.ArrayList;
import java.util.HashMap;

import org.compiere.common.QueryVO;
import org.compiere.intf.ComponentImplIntf;
import org.compiere.intf.WindowImplIntf;
import org.compiere.layout.Box;
import org.compiere.util.CLogger;
import org.compiere.util.Ctx;
import org.compiere.util.Env;
import org.compiere.vos.ChangeVO;
import org.compiere.vos.ResponseVO;
import org.compiere.vos.UWindowID;
import org.compiere.vos.WindowCtx;
import org.compiere.vos.WindowVO.ClientWindowType;

/**
 * @author bang
 *
 */
public class CheckPurchaseRequisition implements WindowImplIntf{
	private CLogger s_log = CLogger.getCLogger(CheckPurchaseRequisition.class);

	private int windowNO;
	private Ctx serverCtx;
	private WindowCtx windowCtx; 
	private UWindowID uid;

	
	public CheckPurchaseRequisition(int windowNO, Ctx serverCtx, WindowCtx windowCtx,
			UWindowID uid) {
		// TODO Auto-generated constructor stub
		this.windowNO = windowNO;
		this.serverCtx = serverCtx;
		this.windowCtx = windowCtx;
		this.uid = uid;

		s_log.fine("Form Receipt Control ==>> Language:"
				+ Env.getLanguage(this.serverCtx) + " windowNO:"
				+ this.windowNO + " AD_Form_ID:" + this.uid.getAD_Form_ID());
	}
	
	@Override
	public ArrayList<ComponentImplIntf> getComponents() {
		// TODO Auto-generated method stub
		ArrayList<ComponentImplIntf> arrayList = new ArrayList<ComponentImplIntf>();
		arrayList.add(new CheckPurchaseRequisition_Header(serverCtx, windowCtx));
		arrayList.add(new CheckPurchaseRequisition_Table(serverCtx));
		return arrayList;
	}

	@Override
	public ChangeVO processCallback(String sender) {
		// TODO Auto-generated method stub
		ChangeVO cVO = new ChangeVO();
		cVO.queryComponents = new HashMap<Integer, QueryVO>();
		QueryVO queryVO = new QueryVO();
		if (sender.equals("btnSearch")) {
			System.out.println("Button Search Clicked!");
			cVO.queryComponents.put(1, queryVO);
		}
		cVO.showResults(true);

		return cVO;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validateResponse(ResponseVO responseVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Box getLayout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientWindowType getClientWindowType() {
		// TODO Auto-generated method stub
		return null;
	}

}
