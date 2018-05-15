/**
 * 
 */
package rsge.wmsrule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.common.CompiereStateException;
import org.compiere.intf.WMSRuleIntf;
import org.compiere.model.MLocator;
import org.compiere.util.CLogger;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

/**
 * Pick Available Product Based on First Expired First Out
 * @author FANNY
 *
 */
public class PickAvailableFEFO implements WMSRuleIntf{

	private static final CLogger s_log = CLogger.getCLogger(PickAvailableFEFO.class);

	@Override
	public MLocator[] getValidLocators(Ctx ctx, int M_Warehouse_ID,
			int M_Zone_ID, int M_Product_ID, int C_OrderLine_ID, Trx trx) {
		
		if (M_Warehouse_ID == 0 || M_Product_ID == 0)
			return null;
		
		String sql = "SELECT * ";
		
		sql += "FROM M_Locator l ";
		
		// Define FEFO Rule
		sql += "INNER JOIN M_Storage_V s ON (l.M_Locator_ID = s.M_Locator_ID) " +
				"LEFT OUTER JOIN M_AttributeSetInstance asi ON (s.M_AttributeSetInstance_ID = asi.M_AttributeSetInstance_ID) ";
		// End Define FEFO Rule
		
		sql += "LEFT OUTER JOIN M_ZoneLocator zl ON (zl.M_Locator_ID=l.M_Locator_ID) "
				+ "LEFT OUTER JOIN M_Zone z ON (z.M_Zone_ID=zl.M_Zone_ID AND z.IsStatic='Y') ";
		
		sql += "WHERE l.M_Warehouse_ID=? " 
				+ "AND l.IsAvailableForAllocation='Y' "
				+ "AND l.IsActive='Y' AND l.IsConsigned = 'N' ";
					
		sql += "AND s.M_Product_ID = ?  " +
				"AND s.QtyOnHand > (s.QtyDedicated + s.QtyAllocated) ";
		
		if(M_Zone_ID != 0)
		{
			sql += "AND l.M_Locator_ID IN (SELECT M_Locator_ID FROM M_ZoneLocator zl " +
								" WHERE zl.M_Zone_ID=? ) ";
		}

		sql += " ORDER BY asi.GuaranteeDate, ";
		
		if (M_Zone_ID == 0)
			sql += "z.PickingSeqNo, ";
		
		sql += " l.PickingSeqNo ";

		PreparedStatement pstmt = null;
		MLocator[] locators = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trx);
			int index = 1;
			pstmt.setInt(index++, M_Warehouse_ID);
			pstmt.setInt(index++, M_Product_ID);
			if (M_Zone_ID != 0)
				pstmt.setInt(index++, M_Zone_ID);
			locators = getLocators(ctx, pstmt, trx);
		}
		catch (SQLException e) {
			s_log.log(Level.SEVERE, sql, e);
			throw new CompiereStateException(Msg.translate(ctx, "SQLException"));
		}
		finally {
			DB.closeStatement(pstmt);
		}
		
		return locators;
	}
	
	/**
	 * Get Locators 
	 * @param pstmt
	 * @return locators
	 */
	public static MLocator[] getLocators(Ctx ctx, PreparedStatement pstmt, Trx trx)
	{
		ArrayList<MLocator> list = new ArrayList<MLocator>();
		ResultSet rs = null;
		try	{
			rs = pstmt.executeQuery ();
			while (rs.next ()) {
				MLocator locator = new MLocator (ctx, rs, trx);
				list.add(locator);
			}	//	for all orders
		}
		catch (SQLException e) {
			s_log.log(Level.SEVERE, "", e);
			throw new CompiereStateException(Msg.translate(ctx, "SQLException"));
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		
		MLocator[] retValue = new MLocator[list.size()];
		list.toArray(retValue);
		return retValue;
	}


}
