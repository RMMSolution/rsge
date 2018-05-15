/**
 * 
 */
package rsge.callout;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Ctx;
import org.compiere.util.Trx;

import rsge.model.MAdvanceDisbursement;
import rsge.model.MDisbursementRealization;
import rsge.po.X_XX_DRConfirmation;

/**
 * Callout for Advance Disbursement's module
 * @author FANNY
 *
 */
public class CalloutAdvanceDisbursement extends CalloutEngine {

	/**
	 * Set Business Partner based on Advance Disbursement
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setAdvanceDisbursement(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value == null)
			return "";
		
		Integer AdvanceDisbursement_ID = (Integer) value;
		
		MAdvanceDisbursement ad = new MAdvanceDisbursement(ctx, AdvanceDisbursement_ID, (Trx)null);
		mTab.setValue("AD_Org_ID", ad.getAD_Org_ID());
		mTab.setValue("C_Currency_ID", ad.getC_Currency_ID());
		mTab.setValue("C_Activity_ID", ad.getC_Activity_ID());
		mTab.setValue("C_Campaign_ID", ad.getC_Campaign_ID());
		mTab.setValue("C_Project_ID", ad.getC_Project_ID());
		
		return "";
	}
	
	/**
	 * Set Disbursement Realization info
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setDisbursementRealization(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value == null)
			return "";
		
		Integer DR_ID = (Integer) value;
		
		MDisbursementRealization dr = new MDisbursementRealization(ctx, DR_ID, (Trx)null);
		mTab.setValue("C_Currency_ID", dr.getC_Currency_ID());
		
		// For Return Transaction
		if(dr.getTotalAmt().compareTo(dr.getRealizedAmt())== 1)
		{
			mTab.setValue("DRConfirmationType", X_XX_DRConfirmation.DRCONFIRMATIONTYPE_Return);
			mTab.setValue("Amount", dr.getTotalAmt().subtract(dr.getRealizedAmt()));
		}
		else // For Reimburse Transaction
		{
			mTab.setValue("DRConfirmationType", X_XX_DRConfirmation.DRCONFIRMATIONTYPE_Reimburse);
			mTab.setValue("Amount", dr.getRealizedAmt().subtract(dr.getTotalAmt()));
		}			
		
		return "";
	}
}
