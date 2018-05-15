/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import rsge.po.X_XX_RequestLineLocator;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;


/**
 * @author FANNY
 *
 */
public class MRequestLineLocator extends X_XX_RequestLineLocator {

    /** Logger for class MRequestLineLocator */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MRequestLineLocator.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_RequestLineLocator_ID
	 * @param trx
	 */
	public MRequestLineLocator(Ctx ctx, int XX_RequestLineLocator_ID, Trx trx) {
		super(ctx, XX_RequestLineLocator_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MRequestLineLocator(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {

		// Check availability of quantity in locator if internal use qty is entered
		if(getQtyInternalUse().compareTo(Env.ZERO)==1)
		{
			int attributeSetInstance = 0;
			if(getM_AttributeSetInstance_ID()>0)
				attributeSetInstance = getM_AttributeSetInstance_ID();
			MInternalUseRequestLine rl = new MInternalUseRequestLine(getCtx(), getXX_InternalUseRequestLine_ID(), get_Trx());
			BigDecimal availableQty = MInternalUseRequestLine.checkAvailableQty(getM_Locator_ID(), rl.getM_Product_ID(), attributeSetInstance, get_Trx());
			if(getQtyInternalUse().compareTo(availableQty) == 1)
			{
				log.saveWarning("Qty insufficient", Msg.getMsg(getCtx(), "QtyInsufficient"));
				setQtyInternalUse(availableQty);				
			}
		}
		
		if(getQtyInternalUse().compareTo(getQtyRequired())==1)
		{
			log.saveError("Internal Use Qty is bigger than Required Qty", Msg.getMsg(getCtx(), "QtyEnteredExceedRequiredQty"));
			return false;
		}
		return true;
	}
	
	

	@Override
	protected boolean afterDelete(boolean success) {
		// Update Header
		updateHeader();
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// Update Header
		updateHeader();
		return true;
	}

	/**
	 * Set XX_InternalUseRequestLine_ID
	 * @param oldXX_InternalUseRequestLine_ID
	 * @param newXX_InternalUseRequestLine_ID
	 * @param windowNo
	 * @throws Exception
	 */
	public void setXX_InternalUseRequestLine_ID(String oldXX_InternalUseRequestLine_ID,
			String newXX_InternalUseRequestLine_ID, int windowNo) throws Exception
	{
		if ((newXX_InternalUseRequestLine_ID == null) || (newXX_InternalUseRequestLine_ID.length() == 0))
			return;
		Integer XX_InternalUseRequestLine_ID = Integer.valueOf(newXX_InternalUseRequestLine_ID);

		// Get Quantity required from header
		MInternalUseRequestLine rl = new MInternalUseRequestLine(getCtx(), getXX_InternalUseRequestLine_ID(), get_Trx());
		BigDecimal initialQtyRequired = rl.getQtyRequired();
		
		// Set Quantity Required
		String sql = "SELECT COALESCE(SUM(QtyInternalUse), 0) " +
				"FROM XX_RequestLineLocator " +
				"WHERE XX_InternalUseRequestLine_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, XX_InternalUseRequestLine_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				setQtyRequired(initialQtyRequired.subtract(rs.getBigDecimal(1)));
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void updateHeader()
	{
		BigDecimal qtyInternalUse = Env.ZERO;
		
		String sql = "SELECT COALESCE(SUM(QtyInternalUse),0) " +
				"FROM XX_RequestLineLocator " +
				"WHERE XX_InternalUseRequestLine_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, getXX_InternalUseRequestLine_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				qtyInternalUse = rs.getBigDecimal(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
		}
		
		// Update Header
		sql = "UPDATE XX_InternalUseRequestLine " +
				"SET QtyInternalUse = ? " +
				"WHERE XX_InternalUseRequestLine_ID = ? ";
		
		pstmt = DB.prepareStatement(sql, get_Trx());
		
		try{
			pstmt.setBigDecimal(1, qtyInternalUse);
			pstmt.setInt(2, getXX_InternalUseRequestLine_ID());
			pstmt.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeStatement(pstmt);
		}
	}

}
