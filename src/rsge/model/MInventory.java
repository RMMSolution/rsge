/**
 * 
 */
package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.compiere.vos.DocActionConstants;

/**
 * @author Fanny
 *
 */
public class MInventory extends org.compiere.model.MInventory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param M_Inventory_ID
	 * @param trx
	 */
	public MInventory(Ctx ctx, int M_Inventory_ID, Trx trx) {
		super(ctx, M_Inventory_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MInventory(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
		if(super.completeIt().equals(DocActionConstants.STATUS_Completed))
		{
//			MInvRealizedQty.doRealization(this);
		}
		return DocActionConstants.STATUS_Completed;
	}
	
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		if(super.voidIt())
		{
//			voidRealization();
		}
		return true;
	}
	
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		if(super.reverseCorrectIt())
		{
//			voidRealization();
		}
		return true;
	}
	
	public static int get(MInOutLine iol)
	{
		int id = 0;
		MInOut io = new MInOut(iol.getCtx(), iol.getM_InOut_ID(), iol.get_Trx());
		MInventory inv = new MInventory(iol.getCtx(), 0, iol.get_Trx());
		inv.setClientOrg(iol);
		inv.setC_DocType_ID(getDocType(iol.getAD_Client_ID(), iol.get_Trx()));
		inv.setM_Warehouse_ID(io.getM_Warehouse_ID());
		inv.setMovementDate(io.getMovementDate());
		inv.setC_Activity_ID(iol.getC_Activity_ID());
		inv.setC_Campaign_ID(iol.getC_Campaign_ID());
		inv.setC_Project_ID(iol.getC_Project_ID());
		String desc = null;
		inv.setDescription(desc);
		if(inv.save())
			id = inv.getM_Inventory_ID();
		return id;
	}
	
	private static int getDocType(int AD_Client_ID, Trx trx)
	{
		//
		int docTypeID = 0;
		String sql = "SELECT C_DocType_ID FROM C_DocType WHERE AD_Client_ID = ? AND DocBaseType = 'MMI' ";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trx);
			pstmt.setInt (1, AD_Client_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
				docTypeID = rs.getInt(1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeStatement(pstmt);
			DB.closeResultSet(rs);
		}
		return docTypeID;
	}	//	Doc Type of Inventory Move

	
}
