package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_DisRealizationLineInv;

public class MDisRealizationLineInv extends X_XX_DisRealizationLineInv {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MDisRealizationLineInv(Ctx ctx, int XX_DisRealizationLineInv_ID,
			Trx trx) {
		super(ctx, XX_DisRealizationLineInv_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MDisRealizationLineInv(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
    @Override
    protected boolean beforeSave(boolean newRecord) {
    	// TODO Auto-generated method stub
		MDisRealizationLine drl = new MDisRealizationLine(getCtx(), getXX_DisRealizationLine_ID(), get_Trx());
    	String sql = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	if(getC_Invoice_ID()!=0)
    	{
    		MInvoice inv = new MInvoice(getCtx(), getC_Invoice_ID(), get_Trx());
    		setC_Currency_ID(inv.getC_Currency_ID());
    		setDateInvoiced(inv.getDateInvoiced());
    		setInvoicedAmt(inv.getGrandTotal());
    		
    		MDisbursementRealization dr = new MDisbursementRealization(getCtx(), drl.getXX_DisbursementRealization_ID(), get_Trx());
    		
    		sql = "SELECT invoiceOpenAsOfDate(C_Invoice_ID,0,?)  AS OpenAmt FROM C_Invoice " +
    				"WHERE C_Invoice_ID = ? ";
    		pstmt = DB.prepareStatement(sql, get_Trx());
    		rs = null;
    		try{
    			pstmt.setTimestamp(1, dr.getDateDoc());
    			pstmt.setInt(2, getC_Invoice_ID());
    			rs = pstmt.executeQuery();
    			if(rs.next())
    				setOpenAmt(rs.getBigDecimal(1));
    			rs.close();
    			pstmt.close();
    		}catch(Exception e)
    		{
    			e.printStackTrace();
    		}    		
    	}		
    	return true;
    }
    
    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {
    	// TODO Auto-generated method stub
    	updateHeader();
    	return true;
    }
    
    @Override
    protected boolean afterDelete(boolean success) {
    	// TODO Auto-generated method stub
    	updateHeader();
    	return true;
    }

    private void updateHeader()
    {
    	BigDecimal allocatedAmt = BigDecimal.ZERO;
		String sql = "SELECT COALESCE(SUM(AllocatedAmt),0) AS AllocatedAmt FROM XX_DisRealizationLineInv " +
				"WHERE XX_DisRealizationLine_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_DisRealizationLine_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				allocatedAmt = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
    	
		String update = "UPDATE XX_DisRealizationLine " +
				"SET InvoicedAmt = ? " +
				"WHERE XX_DisRealizationLine_ID = ? ";
		DB.executeUpdate(get_Trx(), update, allocatedAmt, getXX_DisRealizationLine_ID());
    }

}
