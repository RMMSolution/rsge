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
import org.compiere.util.Trx;

import rsge.po.X_XX_DisRealizationLine;

/**
 * @author FANNY
 *
 */
public class MDisRealizationLine extends X_XX_DisRealizationLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /** Logger for class MDisRealizationLine */
    private static final org.compiere.util.CLogger log = org.compiere.util.CLogger.getCLogger(MDisRealizationLine.class);


	/**
	 * @param ctx
	 * @param XX_DisRealizationLine_ID
	 * @param trx
	 */
	public MDisRealizationLine(Ctx ctx, int XX_DisRealizationLine_ID, Trx trx) {
		super(ctx, XX_DisRealizationLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MDisRealizationLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		if(newRecord)
		{
			if(getXX_AdvanceDisbursementLine_ID()!=0)
			{
				MAdvanceDisbursementLine adl = new MAdvanceDisbursementLine(getCtx(), getXX_AdvanceDisbursementLine_ID(), get_Trx());
				setC_BPartner_ID(adl.getC_BPartner_ID());			
			}
			
		}
		
//		if(getInvoicedAmt().add(getRealizedAmt()).compareTo(getAmount())>0)
//			log.saveWarning("Warning", "Total realized amount is bigger than allocated amount");		
		return true;
	}
	
	
	@Override
	protected boolean afterDelete(boolean success) {
		// Update Header
		MDisRealizationLine.updateHeader(this);
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// Update Header
		MDisRealizationLine.updateHeader(this);
		return true;
	}

	public static MDisRealizationLine[] getLines(MDisbursementRealization dr)
	{
		ArrayList<MDisRealizationLine> lines = new ArrayList<>();
		String sql = "SELECT * " +
				"FROM XX_DisRealizationLine " +
				"WHERE XX_DisbursementRealization_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, dr.get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, dr.getXX_DisbursementRealization_ID());
			rs = pstmt.executeQuery();
			while(rs.next())
				lines.add(new MDisRealizationLine(dr.getCtx(), rs, dr.get_Trx()));
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeStatement(pstmt);
			DB.closeResultSet(rs);
		}
		
		MDisRealizationLine[] retValue = new MDisRealizationLine[lines.size()];
		lines.toArray(retValue);
		return retValue;
	}

	public static void updateHeader(MDisRealizationLine drl)
	{
		BigDecimal amt = BigDecimal.ZERO;
		BigDecimal ramt = BigDecimal.ZERO;
		BigDecimal diffAmt = BigDecimal.ZERO;
		
		String sql = "SELECT COALESCE(SUM(Amount),0) AS Amount, COALESCE(SUM(RealizedAmt+InvoicedAmt),0) AS RealizedAmt " +
				"FROM XX_DisRealizationLine " +
				"WHERE XX_DisbursementRealization_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, drl.get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, drl.getXX_DisbursementRealization_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				amt = rs.getBigDecimal(1);
				ramt = rs.getBigDecimal(2);
				diffAmt = amt.subtract(ramt);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			DB.closeStatement(pstmt);
			DB.closeResultSet(rs);
		}
		
		sql = "UPDATE XX_DisbursementRealization " +
				"SET TotalAmt = ?, RealizedAmt = ?, DifferenceAmt = ? " +
				"WHERE XX_DisbursementRealization_ID = ? ";
		
		DB.executeUpdate(drl.get_Trx(), sql, amt, ramt, diffAmt, drl.getXX_DisbursementRealization_ID());
	}
	
}
