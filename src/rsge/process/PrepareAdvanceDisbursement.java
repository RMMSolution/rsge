/**
 * 
 */
package rsge.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MCash;
import org.compiere.model.MCashBook;
import org.compiere.model.MCashLine;
import org.compiere.model.MClientInfo;
import org.compiere.model.MPayment;
import org.compiere.model.X_C_CashLine;
import org.compiere.model.X_C_Payment;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import rsge.model.MAdvanceDisbursement;
import rsge.model.MGeneralSetup;

/**
 * @author FANNY
 *
 */
public class PrepareAdvanceDisbursement extends SvrProcess {

	/** Advance Disbursement					*/
	private int						p_AdvanceDisbursementID = 0;
	/** Cash Book								*/
	private int						p_CashBookID = 0;
	private int p_C_BankAccount_ID = 0;
	
	/**
	 * 
	 */
	public PrepareAdvanceDisbursement() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		ProcessInfoParameter params[] = getParameter();
		for(ProcessInfoParameter param : params){
			String name = param.getParameterName();
			if(name.equals("XX_AdvanceDisbursement_ID"))
				p_AdvanceDisbursementID = param.getParameterAsInt();
			else if(name.equals("C_CashBook_ID"))
				p_CashBookID = param.getParameterAsInt();
			else if(name.equals("C_BankAccount_ID"))
				p_C_BankAccount_ID  = param.getParameterAsInt();
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		String DocumentNo = null;
		int adTransitChargeID = MGeneralSetup.get(getCtx(), getAD_Client_ID(), get_Trx()).getAdvDisbursementClrg_ID();
		if(p_CashBookID>0){
			// Prepare Data from Advance Disbursement
			MAdvanceDisbursement ad = new MAdvanceDisbursement(getCtx(), p_AdvanceDisbursementID, get_Trx());
			MCashBook cb = new MCashBook(getCtx(), p_CashBookID, get_Trx());
			
			// Create Cash Journal
			MCash cash = new MCash(cb, null);
			if(cb.getAD_Org_ID()>0)
				cash.setAD_Org_ID(cb.getAD_Org_ID());
			cash.setC_Activity_ID(ad.getC_Activity_ID());
			cash.setC_Campaign_ID(ad.getC_Campaign_ID());
			cash.setC_Project_ID(ad.getC_Project_ID());
			cash.setName("Payment for Advance Disbursement #" + ad.getDocumentNo());

			java.util.Date currentDate = new java.util.Date();
			Timestamp dateTrx = new Timestamp(currentDate.getTime());
			cash.setStatementDate(dateTrx);
			cash.setDateAcct(dateTrx);
			
			cash.save();
			
			// Create Cash Journal Line
			MCashLine cl = new MCashLine(cash);
			cl.setAD_Org_ID(ad.getAD_Org_ID());
			cl.setCashType(X_C_CashLine.CASHTYPE_Charge);
			String sql1 = "SELECT C_BPartner_ID FROM XX_AdvanceDisbursementLine " +
					"WHERE XX_AdvanceDisbursement_ID = ? ORDER BY C_BPartner_ID ASC LIMIT 1";
			PreparedStatement ps1 = DB.prepareStatement(sql1, get_Trx());
			ResultSet rs1 = null;
			try {
				ps1.setInt(1, ad.getXX_AdvanceDisbursement_ID());
				rs1 = ps1.executeQuery();
				if(rs1.next()) 
					cl.set_Value("C_BPartner_ID", rs1.getInt(1));
				rs1.close();
				ps1.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			cl.setC_Charge_ID(adTransitChargeID);
			// Set amount to minus (cash journal use minus sign for disbursement)
			cl.setAmount(ad.getTotalAmt().multiply(BigDecimal.valueOf(-1)));
			cl.set_Value("CashAmount", ad.getTotalAmt().multiply(BigDecimal.valueOf(-1)));
			cl.save();
			ad.setC_Cash_ID(cash.getC_Cash_ID());
			ad.save();
			DocumentNo = "Cash No : "+cash.getDocumentNo();
		}
		
		if(p_C_BankAccount_ID>0){
			String message = createPayment(p_C_BankAccount_ID, adTransitChargeID);
			if(message.equals(noEmployee))
				return Msg.getMsg(getCtx(), "No Employee found");
			DocumentNo = "Payment No : " + message;
		}				
		return DocumentNo;
	}
	
	private String noEmployee = "NE";
	
	private String createPayment(int C_BankAccount_ID, int adTransitChargeID) {		
		MAdvanceDisbursement ad = new MAdvanceDisbursement(getCtx(), p_AdvanceDisbursementID, get_Trx());
		if(ad.getEmployee_ID()==0)
			return noEmployee;
		MPayment payment = new MPayment(getCtx(), 0, get_Trx());
		payment.setAD_Client_ID(getAD_Client_ID());
		payment.setAD_Org_ID(ad.getAD_Org_ID());
		payment.setTenderType(X_C_Payment.TENDERTYPE_Check);
		payment.setPayAmt(ad.getTotalAmt());
		payment.setDateTrx(new Timestamp(System.currentTimeMillis()));
		payment.setDateAcct(new Timestamp(System.currentTimeMillis()));
		payment.setC_DocType_ID(docType());
		payment.setDescription("Payment for Advance Disbursement #" + ad.getDocumentNo());
		payment.setC_Charge_ID(adTransitChargeID);
		MClientInfo info = MClientInfo.get(getCtx(), getAD_Client_ID());
		MAcctSchema as = new MAcctSchema(getCtx(), info.getC_AcctSchema1_ID(),
				get_Trx());
		payment.setC_Currency_ID(as.getC_Currency_ID());
		payment.setC_BankAccount_ID(C_BankAccount_ID);
		payment.setC_BPartner_ID(ad.getEmployee_ID());
		String docNo = null;
		if (payment.save()) {
			ad.setC_Payment_ID(payment.getC_Payment_ID());
			ad.save();
			docNo =  payment.getDocumentNo();
		}
		return docNo;
	}

	private int docType() {
		String sql = "SELECT C_DocType_ID FROM C_DocType WHERE AD_Client_ID = "
				+ getAD_Client_ID() + " AND DocBaseType = 'APP'";
		PreparedStatement ps = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

}
