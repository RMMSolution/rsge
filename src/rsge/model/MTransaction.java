package rsge.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

public class MTransaction extends org.compiere.model.MTransaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MTransaction(Ctx ctx, int M_Transaction_ID, Trx trx) {
		super(ctx, M_Transaction_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MTransaction(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param ctx Ctx
	 * @param AD_Org_ID Organization
	 * @param MovementType MovementType 
	 * @param M_Locator_ID Locator
	 * @param M_Product_ID Product
	 * @param M_AttributeSetInstance_ID Attribute Set Instance
	 * @param MovementQty Qty
	 * @param MovementDate Date 
	 * @param trx Trx
	 * @param isConsigned Is this a consigned Trx
	 */
	public MTransaction (Ctx ctx, int AD_Org_ID, 
		String MovementType, 
		int M_Locator_ID, int M_Product_ID, int M_AttributeSetInstance_ID, 
		BigDecimal MovementQty, Timestamp MovementDate, Trx trx, boolean isConsigned)
	{
		super(ctx, 0, trx);
		setAD_Org_ID(AD_Org_ID);
		setMovementType (MovementType);
		if (M_Locator_ID == 0)
			throw new IllegalArgumentException("No Locator");
		setM_Locator_ID (M_Locator_ID);
		if (M_Product_ID == 0)
			throw new IllegalArgumentException("No Product");
		setM_Product_ID (M_Product_ID);
		setM_AttributeSetInstance_ID (M_AttributeSetInstance_ID);
		//
		if (MovementQty != null)		//	Can be 0
			setMovementQty (MovementQty);
		if (MovementDate == null)
			setMovementDate (new Timestamp(System.currentTimeMillis()));
		else
			setMovementDate(MovementDate);
		setIsConsigned(isConsigned);
	}

	
    /** Set Disbursement Realization Line Product.
    @param XX_DisRealizationLineProd_ID Disbursement realization line product */
    public void setXX_DisRealizationLineProd_ID (int XX_DisRealizationLineProd_ID)
    {
        if (XX_DisRealizationLineProd_ID <= 0) set_Value ("XX_DisRealizationLineProd_ID", null);
        else
        set_Value ("XX_DisRealizationLineProd_ID", Integer.valueOf(XX_DisRealizationLineProd_ID));
        
    }
    
    /** Get Disbursement Realization Line Product.
    @return Disbursement realization line product */
    public int getXX_DisRealizationLineProd_ID() 
    {
        return get_ValueAsInt("XX_DisRealizationLineProd_ID");
        
    }


}
