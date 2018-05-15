package rsge.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.po.X_XX_DownPaymentSettlementLn;

public class MDownPaymentSettlementLn extends X_XX_DownPaymentSettlementLn {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MDownPaymentSettlementLn(Ctx ctx, int XX_DownPaymentSettlementLn_ID, Trx trx) {
		super(ctx, XX_DownPaymentSettlementLn_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MDownPaymentSettlementLn(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public MDownPaymentSettlementLn(MDownPaymentSettlement dps) {
		super(dps.getCtx(), 0, dps.get_Trx());
		setClientOrg(dps);
		setXX_DownPaymentSettlement_ID(dps.getXX_DownPaymentSettlement_ID());
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		String sql = "SELECT COALESCE(Amount) FROM XX_DownPaymentSettlementLn "
				+ "WHERE XX_DownPaymentSettlement_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getXX_DownPaymentSettlement_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				MDownPaymentSettlement dps = new MDownPaymentSettlement(getCtx(), getXX_DownPaymentSettlement_ID(), get_Trx());
				dps.setInvoiceAmt(rs.getBigDecimal(1));
				dps.save();
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
