package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import rsge.model.MRequisitionLine;

public class MRequisition extends org.compiere.model.MRequisition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /** Logger for class MRequisition */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MRequisition.class);


	public MRequisition(Ctx ctx, int M_Requisition_ID, Trx trx) {
		super(ctx, M_Requisition_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MRequisition(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
    /** Set Organisasi Transaksi.
    @param AD_OrgTrx_ID Organisasi yang melakukan */
    public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
    {
        if (AD_OrgTrx_ID <= 0) set_Value ("AD_OrgTrx_ID", null);
        else
        set_Value ("AD_OrgTrx_ID", Integer.valueOf(AD_OrgTrx_ID));
        
    }
    
    /** Get Organisasi Transaksi.
    @return Organisasi yang melakukan */
    public int getAD_OrgTrx_ID() 
    {
        return get_ValueAsInt("AD_OrgTrx_ID");
        
    }


	/** Lines						*/
	private MRequisitionLine[]		m_lines = null;

	/**
	 * 	Get Lines
	 * 	@param requery requery
	 *	@return array of lines
	 */
	public MRequisitionLine[] getLines(boolean requery)
	{
		if ((m_lines != null) && !requery)
			return m_lines;

		ArrayList<MRequisitionLine> list = new ArrayList<MRequisitionLine>();
		String sql = "SELECT * FROM M_RequisitionLine WHERE M_Requisition_ID=? ORDER BY Line";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_Trx());
			pstmt.setInt (1, getM_Requisition_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MRequisitionLine (getCtx(), rs, get_Trx()));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "getLines", e);
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		m_lines = new MRequisitionLine[list.size ()];
		list.toArray (m_lines);
		return m_lines;
	}	//	getLines
	
    /** Set Campaign.
    @param C_Campaign_ID Marketing Campaign */
    public void setC_Campaign_ID (int C_Campaign_ID)
    {
        if (C_Campaign_ID <= 0) set_Value ("C_Campaign_ID", null);
        else
        set_Value ("C_Campaign_ID", Integer.valueOf(C_Campaign_ID));
        
    }
    
    /** Get Campaign.
    @return Marketing Campaign */
    public int getC_Campaign_ID() 
    {
        return get_ValueAsInt("C_Campaign_ID");
        
    }
	
    /** Set Project.
    @param C_Project_ID Financial Project */
    public void setC_Project_ID (int C_Project_ID)
    {
        if (C_Project_ID <= 0) set_Value ("C_Project_ID", null);
        else
        set_Value ("C_Project_ID", Integer.valueOf(C_Project_ID));
        
    }
    
    /** Get Project.
    @return Financial Project */
    public int getC_Project_ID() 
    {
        return get_ValueAsInt("C_Project_ID");
        
    }

	public static BigDecimal getBudgetPendingAmt(int AD_Org_ID, int Account_ID, Timestamp startDate, Timestamp endDate, Integer C_Activity_ID, Integer C_Campaign_ID, Integer C_Project_ID, Integer M_Product_ID, Trx trx)
	{
		BigDecimal pendingAmt = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder("SELECT COALESCE(SUM(rl.ConvertedAmt),0) AS PendingAmt "
				+ " FROM M_RequisitionLine rl "
				+ " INNER JOIN M_Requisition r ON rl.M_Requisition_ID = r.M_Requisition_ID "
				+ " WHERE r.DocStatus IN ('IP', 'AP') "
				+ " AND r.AD_OrgTrx_ID = ? "
				+ " AND rl.Account_ID = ? "
				+ " AND r.DateDoc BETWEEN ? AND ? "
				+ " AND rl.C_OrderLine_ID IS NULL "
				+ " AND r.M_Requisition_ID NOT IN (SELECT M_Requisition_ID FROM XX_PurchaseRequisition) ");
		
		if(C_Activity_ID != null)
		{
			sql.append("AND rl.C_Activity_ID = ");
			sql.append(C_Activity_ID);
			sql.append(" ");
		}
		else
			sql.append("AND (rl.C_Activity_ID = 0 OR rl.C_Activity_ID IS NULL) ");
		
		if(C_Campaign_ID!=null)
		{
			sql.append("AND r.C_Campaign_ID = ");
			sql.append(C_Campaign_ID);
			sql.append(" ");
		}
		else
			sql.append("AND (r.C_Campaign_ID = 0 OR r.C_Campaign_ID IS NULL) ");
		
		if(C_Project_ID!=null)
		{
			sql.append("AND r.C_Project_ID = ");
			sql.append(C_Project_ID);
			sql.append(" ");
		}
		else
			sql.append("AND (r.C_Project_ID = 0 OR r.C_Project_ID IS NULL) ");

		if(M_Product_ID!=null)
		{
			sql.append("AND rl.M_Product_ID = ");
			sql.append(M_Product_ID);
			sql.append(" ");
		}
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trx);
        ResultSet rs = null;
	    try{
	    	int index = 1;	    	
	    	pstmt.setInt(index++, AD_Org_ID);
	    	pstmt.setInt(index++, Account_ID);
	    	pstmt.setTimestamp(index++, startDate);
	    	pstmt.setTimestamp(index++, endDate);
	    	
	    	rs = pstmt.executeQuery();
	    	if(rs.next())
	    		pendingAmt = rs.getBigDecimal("PendingAmt");
	    	rs.close();
	    	pstmt.close();
	    }catch (Exception e) {
	    	e.printStackTrace();
		}	    			

		return pendingAmt;
	}
	
	public static MRequisitionLine[] getLines(MRequisition requisition)
	{
		ArrayList<MRequisitionLine> lines = new ArrayList<>();
		String sql = "SELECT * FROM M_RequisitionLine WHERE M_Requisition_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, requisition.get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, requisition.getM_Requisition_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				lines.add(new MRequisitionLine(requisition.getCtx(), rs, requisition.get_Trx()));
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		MRequisitionLine[] retValue = new MRequisitionLine[lines.size()];
		lines.toArray(retValue);
		return retValue;
	}

}
