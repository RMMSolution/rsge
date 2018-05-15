/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

import rsge.po.X_XX_InternalUseRequestLine;

/**
 * @author FANNY
 *
 */
public class MInternalUseRequestLine extends X_XX_InternalUseRequestLine {

    /** Logger for class MInternalUseRequest */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MInternalUseRequest.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param XX_InternalUseRequestLine_ID
	 * @param trx
	 */
	public MInternalUseRequestLine(Ctx ctx, int XX_InternalUseRequestLine_ID,
			Trx trx) {
		super(ctx, XX_InternalUseRequestLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MInternalUseRequestLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		// if Quantity Required <= 0, cancel Save
		if(!(getQtyRequired().compareTo(Env.ZERO)==1))
		{
			log.saveError("Qty is 0 or less", Msg.getMsg(getCtx(), "Qty is greater than 0"));
			return false;			
		}
		
		// Check availability of quantity in locator if internal use qty is entered
		if(!isMultiLocator() && getQtyInternalUse().compareTo(Env.ZERO)==1)
		{
			int attributeSetInstance = 0;
			if(getM_AttributeSetInstance_ID()>0)
				attributeSetInstance = getM_AttributeSetInstance_ID();
			BigDecimal availableQty = checkAvailableQty(getM_Locator_ID(), getM_Product_ID(), attributeSetInstance, get_Trx());
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
	
	public ArrayList<Integer> getLocatorLines(MInternalUseRequestLine rline)
	{
		ArrayList<Integer> lines = new ArrayList<Integer>();
		
		String sql = "SELECT XX_RequestLineLocator_ID " +
				"FROM XX_RequestLineLocator " +
				"WHERE XX_InternalUseRequestLine_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, rline.getXX_InternalUseRequestLine_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				lines.add(rs.getInt(1));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeStatement(pstmt);
			DB.closeResultSet(rs);
		}
		return lines;
	}
	
	/**
	 * Check wheter specific locator has enough quantity
	 * 
	 * @return available quantity
	 */
	public static BigDecimal checkAvailableQty(int locatorID, int productID, int attributeSetInstanceID, Trx trx)
	{
		BigDecimal availableQty = Env.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(QtyOnHand-QtyReserved), 0) AS AvailableQty " +
				"FROM M_Storage_V " +
				"WHERE M_Locator_ID = ? " +
				"AND M_Product_ID = ? ");
		if(attributeSetInstanceID > 0)
		{
			sql.append("AND M_AttributeSetInstance_ID = " + attributeSetInstanceID + " ");			
		}
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
		ResultSet rs = null;
		
		try{			
			pstmt.setInt(1, locatorID);
			pstmt.setInt(2, productID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				availableQty = rs.getBigDecimal(1);
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return availableQty;
	}
}
