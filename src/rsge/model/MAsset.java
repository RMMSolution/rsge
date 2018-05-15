/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.util.CLogger;
import org.compiere.util.Ctx;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

/**
 * @author Fanny
 *
 */
public class MAsset extends org.compiere.model.MAsset {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**************************************************************************
	 * 	Asset Constructor
	 *	@param ctx context
	 *	@param A_Asset_ID asset
	 *	@param trx transaction name 
	 */
	public MAsset (Ctx ctx, int A_Asset_ID, Trx trx)
	{
		super(ctx, A_Asset_ID, trx);
	}
	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *	@param trx transaction
	 */
	public MAsset (Ctx ctx, ResultSet rs, Trx trx)
	{
		super(ctx, rs, trx);
	}	//	MAsset
	
	/**
	 * @param shipment
	 * @param shipLine
	 * @param deliveryCount
	 */
	public MAsset(MInOut shipment, MInOutLine shipLine, int deliveryCount) {
		super(shipment, shipLine, deliveryCount);
		// TODO Auto-generated constructor stub
	}

	/**	Logger							*/
	private static CLogger	s_log = CLogger.getCLogger (MAsset.class);

	@Override
	protected boolean beforeSave(boolean newRecord) {
		if(super.beforeSave(newRecord))
		{
//			// Set M_Product_ID if Asset Group selected
//			if(getA_Asset_Group_ID() > 0)
//			{	
//				MAssetGroup ag = new MAssetGroup(getCtx(), getA_Asset_Group_ID(), get_Trx());
//				if(ag.getM_Product_ID()>0)
//				{
//					setM_Product_ID(ag.getM_Product_ID());
//				}
//				else
//				{
//					s_log.saveError("Save Error : ", Msg.getMsg(getCtx(), "NoProductInAssetGroup"));
//				}
//			}

		}
		return true;
	}


}
