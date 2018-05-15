/**
 * 
 */
package rsge.callout;

import java.math.BigDecimal;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.util.Ctx;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.model.MBudgetInfo;
import rsge.model.MInvRequisition;
import org.compiere.model.MProduct;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author FANNY
 *
 */
public class CalloutInvRequisition extends CalloutEngine {

	/**
	 * Set Amount based on Qty * Product Cost
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */	
	public String setAmt(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		BigDecimal qty = (BigDecimal) mTab.getValue("QtyRequired");
		BigDecimal cost = (BigDecimal) mTab.getValue("ProductCost");
		BigDecimal totalAmt = qty.multiply(cost);
		mTab.setValue("EstCost", totalAmt);
		return "";
	}
	
	/**
	 * Set Product Cost and UOM based on selected product
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */	
	public String setM_Product_ID(Ctx ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer M_Product_ID = (Integer)value;
		if(M_Product_ID == null || M_Product_ID.intValue() == 0)
			return "";	
		
		int headerId = (Integer) mTab.getValue("XX_InvRequisition_ID");
		MInvRequisition ir = new MInvRequisition(ctx, headerId, (Trx)null);

		// Set UOM 
		MProduct product = new MProduct(ctx, M_Product_ID, (Trx)null);
		mTab.setValue("C_UOM_ID", product.getC_UOM_ID());
		
		// Get Product Cost
		MBudgetInfo bi = GeneralEnhancementUtils.getBudgetInfo(ctx, ctx.getAD_Client_ID(), (Trx)null);
		MAcctSchema as = new MAcctSchema(ctx, bi.getC_AcctSchema_ID(), (Trx)null);
		
		BigDecimal productCost = MCost.getCurrentCost(product, 0, as, ir.getAD_Org_ID(), null, Env.ONE, 0, true, (Trx)null);
		mTab.setValue("ProductCost", productCost);
		
		return "";
	}


}
