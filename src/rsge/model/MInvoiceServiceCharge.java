package rsge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MCurrency;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.po.X_XX_InvoiceServiceCharge;

public class MInvoiceServiceCharge extends X_XX_InvoiceServiceCharge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MInvoiceServiceCharge(Ctx ctx, int XX_InvoiceServiceCharge_ID, Trx trx) {
		super(ctx, XX_InvoiceServiceCharge_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MInvoiceServiceCharge(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	public MInvoiceServiceCharge(MInvoice invoice) {
		super(invoice.getCtx(), 0, invoice.get_Trx());
		setClientOrg(invoice);
		setC_Invoice_ID(invoice.getC_Invoice_ID());
		// TODO Auto-generated constructor stub
	}

	
	
	private static MInvoiceServiceCharge get(MInvoice invoice, boolean createNew)
	{
		MInvoiceServiceCharge retValue = null;
		
		String sql = "SELECT * FROM XX_InvoiceServiceCharge "
				+ "WHERE C_Invoice_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, invoice.get_Trx());
		ResultSet rs= null;
		try{
			pstmt.setInt(1, invoice.getC_Invoice_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				retValue = new MInvoiceServiceCharge(invoice.getCtx(), rs, invoice.get_Trx());
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(retValue==null && createNew)
			retValue = new MInvoiceServiceCharge(invoice);		
		return retValue;
	}

}
