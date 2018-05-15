package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import rsge.model.MAllocationLine;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

public class MAllocationHdr extends org.compiere.model.MAllocationHdr {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Logger for class MAllocationHdr */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MAllocationHdr.class);

	public MAllocationHdr(Ctx ctx, int C_AllocationHdr_ID, Trx trx) {
		super(ctx, C_AllocationHdr_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MAllocationHdr(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	/**	Lines						*/
	private MAllocationLine[]	m_lines = null;
	
	/**
	 * 	Get Lines
	 *	@param requery if true requery
	 *	@return lines
	 */
	public MAllocationLine[] getAllocationLines (boolean requery)
	{
		if ((m_lines != null) && (m_lines.length != 0) && !requery)
			return m_lines;
		//
		String sql = "SELECT * FROM C_AllocationLine WHERE C_AllocationHdr_ID=?";
		ArrayList<MAllocationLine> list = new ArrayList<MAllocationLine>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_Trx());
			pstmt.setInt (1, getC_AllocationHdr_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MAllocationLine line = new MAllocationLine(getCtx(), rs, get_Trx());
				list.add (line);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		//
		m_lines = new MAllocationLine[list.size ()];
		list.toArray (m_lines);
		return m_lines;
	}	//	getLines


}
