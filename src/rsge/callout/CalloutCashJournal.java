/**
 * 
 */
package rsge.callout;

import java.math.BigDecimal;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Ctx;

/**
 * @author FANNY
 *
 */
public class CalloutCashJournal extends CalloutEngine {

	/**************************************************************************
	 * Standard Constructor
	 * 
	 */
	public CalloutCashJournal() {
		// TODO Auto-generated constructor stub
	}
	
	public String setCashType (Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String type = (String) value;
		if (type == null || type.length() == 0)
		return "";
		
		mTab.setValue("CashType", type);		
		return "";
	}

}
