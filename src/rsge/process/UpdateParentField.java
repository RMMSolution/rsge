/**
 * 
 */
package rsge.process;

import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import rsge.utils.GeneralEnhancementUtils;

/**
 * @author Fanny
 *
 */
public class UpdateParentField extends SvrProcess {

	private String			p_MasterData = null;
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null && element.getParameter_To() == null)
				;
			else if (name.equals("MasterData"))
				p_MasterData = (String) element.getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}


	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		int n = GeneralEnhancementUtils.updateParent(getCtx(), getAD_Client_ID(), p_MasterData, get_Trx());
		StringBuilder processMsg = new StringBuilder("Processed record : ");
		processMsg.append(n);
		return processMsg.toString();
	}

}
