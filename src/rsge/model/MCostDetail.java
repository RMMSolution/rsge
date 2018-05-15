package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.util.CLogger;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

public class MCostDetail extends org.compiere.model.MCostDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MCostDetail(Ctx ctx, int M_CostDetail_ID, Trx trx) {
		super(ctx, M_CostDetail_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MCostDetail(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 	New Constructor
	 *	@param as accounting schema
	 *	@param AD_Org_ID org
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param M_CostElement_ID optional cost element for Freight
	 *	@param Amt amt
	 *	@param Qty qty
	 *	@param Description optional description
	 *	@param trx transaction
	 */
	public MCostDetail (MAcctSchema as, int AD_Org_ID, 
		int M_Product_ID, int M_AttributeSetInstance_ID,
		int M_CostElement_ID, BigDecimal Amt, BigDecimal Qty,
		String Description, Trx trx)
	{
		this (as.getCtx(), 0, trx);
		setClientOrg(as.getAD_Client_ID(), AD_Org_ID);
		setC_AcctSchema_ID (as.getC_AcctSchema_ID());
		setM_Product_ID (M_Product_ID);
		setM_AttributeSetInstance_ID (M_AttributeSetInstance_ID);
		//
		setM_CostElement_ID(M_CostElement_ID);
		//
		setAmt (Amt);
		setQty (Qty);
		setDescription(Description);
	}	//	MCostDetail

	
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
    
	/**	Logger	*/
	private static CLogger 	s_log = CLogger.getCLogger (MCostDetail.class);
    
    public static boolean createAdvanceDisbursementRealization (MAcctSchema as, int AD_Org_ID, 
    		int M_Product_ID, int M_AttributeSetInstance_ID,
    		int XX_DisRealizationLineProd_ID, int M_CostElement_ID, 
    		BigDecimal Amt, BigDecimal Qty,
    		String Description, boolean IsSOTrx, Trx trx)
    {
		//	Delete Unprocessed zero Differences
		String sql = "DELETE FROM M_CostDetail "
			+ "WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0"
			+ " AND XX_DisRealizationLineProd_ID= ?"
			+ " AND M_AttributeSetInstance_ID= ? "
			+ " AND C_AcctSchema_ID = ? ";
		Object[] params = new Object[] {XX_DisRealizationLineProd_ID,M_AttributeSetInstance_ID,as.getC_AcctSchema_ID()};
		int no = DB.executeUpdate(trx, sql,params);
		if (no != 0)
			s_log.config("Deleted #" + no);
		MCostDetail cd = get (as, "XX_DisRealizationLineProd_ID=? AND M_AttributeSetInstance_ID=?", 
			XX_DisRealizationLineProd_ID, M_AttributeSetInstance_ID, trx);
		//
		if (cd == null)		//	createNew
		{
			cd = new MCostDetail (as, AD_Org_ID, 
				M_Product_ID, M_AttributeSetInstance_ID, 
				M_CostElement_ID, 
				Amt, Qty, Description, trx);
			cd.setXX_DisRealizationLineProd_ID(XX_DisRealizationLineProd_ID);
			cd.setIsSOTrx(IsSOTrx);
		}
		else
		{
			cd.setDeltaAmt(cd.getAmt().subtract(Amt));
			cd.setDeltaQty(cd.getQty().subtract(Qty));
			if (cd.isDelta())
				cd.setProcessed(false);
			else
				return true;	//	nothing to do
		}
		boolean ok = cd.save();
		if (ok && !cd.isProcessed())
		{
			MClient client = MClient.get(as.getCtx(), as.getAD_Client_ID());
			if (client.isCostImmediate())
				cd.process();
		}
		s_log.config("(" + ok + ") " + cd);
		return ok;
	}
    
	/**************************************************************************
	 * 	Get Cost Detail
	 *	@param ctx context
	 *	@param whereClause where clause
	 *	@param ID 1st parameter
	 *	@param M_AttributeSetInstance_ID ASI
	 *	@param trx p_trx
	 *	@return cost detail
	 */
	private static MCostDetail get (MAcctSchema as, String whereClause, 
		int ID, int M_AttributeSetInstance_ID, Trx trx)
	{
		String sql = "SELECT * FROM M_CostDetail WHERE " + whereClause;
		sql+= " AND C_AcctSchema_ID = ? AND Processed = 'N' ";
		MCostDetail retValue = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trx);
			pstmt.setInt (1, ID);
			pstmt.setInt (2, M_AttributeSetInstance_ID);
			pstmt.setInt(3, as.getC_AcctSchema_ID());
			rs = pstmt.executeQuery ();
			if (rs.next ())
				retValue = new MCostDetail (as.getCtx(), rs, trx);
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql + " - " + ID, e);
		}
		finally
		{
			DB.closeStatement(pstmt);
			DB.closeResultSet(rs);
		}
		return retValue;
	}	//	get

}
