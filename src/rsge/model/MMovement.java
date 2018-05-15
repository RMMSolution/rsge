package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.model.MMovementLine;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

public class MMovement extends org.compiere.model.MMovement {

	/**
	 * 
	 */
    /** Logger for class MMovement */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MMovement.class);
	private static final long serialVersionUID = 1L;

	public MMovement(Ctx ctx, int M_Movement_ID, Trx trx) {
		super(ctx, M_Movement_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MMovement(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String completeIt() {

		MMovementLine[] lines = getLines();
		// Confirm Material request (if any)
		MMaterialRequest.MovementConfirmation(lines, this);
		return super.completeIt();
	}
	
	@Override
	public boolean voidIt() {
		// Cancel Material Request Confirmation
		MMovementLine[] lines = getLines();
		MMaterialRequest.MovementCancelation(lines, this);
		return super.voidIt();
	}
	
	/**
	 * 	Get Lines
	 *	@param requery requery
	 *	@return array of lines
	 */
	public MMovementLine[] getLines ()
	{
		//
		ArrayList<MMovementLine> list = new ArrayList<MMovementLine>();
		String sql = "SELECT * FROM M_MovementLine WHERE M_Movement_ID=? ORDER BY Line";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_Trx());
			pstmt.setInt (1, getM_Movement_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				list.add (new MMovementLine (getCtx(), rs, get_Trx()));
			}
		} catch (Exception e)
		{
			log.log(Level.SEVERE, "getLines", e);
		}
		finally
		{
			DB.closeStatement(pstmt);
			DB.closeResultSet(rs);
		}
		MMovementLine[] m_lines = new MMovementLine[list.size ()];
		list.toArray (m_lines);
		return m_lines;
	}	//	getLines
	
	public static int get(MInOut io)
	{
		int movementID = 0;
		MMovement movement = new MMovement(io.getCtx(), 0, io.get_Trx());
		movement.setClientOrg(io);
		movement.setC_Activity_ID(io.getC_Activity_ID());
		movement.setC_Campaign_ID(io.getC_Campaign_ID());
		movement.setC_Project_ID(io.getC_Project_ID());
		movement.setC_DocType_ID(getDocType(io.getAD_Client_ID(), io.get_Trx()));
		movement.setDateReceived(io.getMovementDate());
		movement.setMovementDate(io.getMovementDate());
		movement.setDescription("Transfer from Receipt #" + io.getDocumentNo());
		movement.setDocumentNo(io.getDocumentNo());
		if(movement.save())
			movementID = movement.getM_Movement_ID(); 
		return movementID;
	}
	
	private static int getDocType(int AD_Client_ID, Trx trx)
	{
		//
		int docTypeID = 0;
		String sql = "SELECT C_DocType_ID FROM C_DocType WHERE AD_Client_ID = ? AND DocBaseType = 'MMM' ";
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
