package rsge.acct;

import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.model.MAccount;
import org.compiere.model.MConversionType;
import org.compiere.util.CLogger;

import rsge.model.MAdvanceDisbursement;
import rsge.model.MAdvanceDisbursementLine;
import rsge.model.MCash;
import rsge.model.MDisRealizationLine;
import rsge.model.MDisbursementRealization;

public class DocLine_DisbursementRealization extends DocLine {

	/**	Log	per Document				*/
	protected CLogger			log = CLogger.getCLogger(getClass());
	private int					p_AD_Org_ID = 0;
	private int 				p_PaymentOrg_ID = 0;
	private int					p_LineID = 0;
	private int					adDisbursementCharge_ID = 0;
	

	private MAccount			adDisbursementAcct = null;
	
	/**
	 * @param po
	 * @param doc
	 */
	public DocLine_DisbursementRealization(MDisRealizationLine line, Doc doc) {
		
		super(line, doc);
		MDisRealizationLine drl = new MDisRealizationLine(line.getCtx(), line.getXX_DisRealizationLine_ID(), line.get_Trx());
		MDisbursementRealization dr = new MDisbursementRealization(drl.getCtx(), drl.getXX_DisbursementRealization_ID(), drl.get_Trx());
		MAdvanceDisbursementLine adl = new MAdvanceDisbursementLine(drl.getCtx(), drl.getXX_AdvanceDisbursementLine_ID(), drl.get_Trx());
		MAdvanceDisbursement ad = new MAdvanceDisbursement(adl.getCtx(), adl.getXX_AdvanceDisbursement_ID(), adl.get_Trx());
		
		p_LineID = drl.getXX_DisRealizationLine_ID();
		setDateAcct(dr.getDateDoc());
		setC_ConversionType_ID(MConversionType.getDefault(line.getAD_Client_ID()));
		setAmount(line.getRealizedAmt());
		setC_BPartner_ID(drl.getC_BPartner_ID());
		setAdDisbursementCharge_ID(adl.getAdvDisbursementCharge_ID());
		if(adl.getAD_Org_ID()!=0)
			setP_AD_Org_ID(adl.getAD_Org_ID());
		else if(drl.getAD_Org_ID()!=0)
			setP_AD_Org_ID(drl.getAD_Org_ID());
		if(ad.getC_Cash_ID()!=0)
		{
			MCash cash = new MCash(ad.getCtx(), ad.getC_Cash_ID(), ad.get_Trx());
			setP_PaymentOrg_ID(cash.getAD_Org_ID());
		}
	}

	public int getP_AD_Org_ID() {
		return p_AD_Org_ID;
	}

	public void setP_AD_Org_ID(int p_AD_Org_ID) {
		this.p_AD_Org_ID = p_AD_Org_ID;
	}

	public int getP_PaymentOrg_ID() {
		return p_PaymentOrg_ID;
	}

	public void setP_PaymentOrg_ID(int p_PaymentOrg_ID) {
		this.p_PaymentOrg_ID = p_PaymentOrg_ID;
	}

	public int getP_LineID() {
		return p_LineID;
	}

	public void setP_LineID(int p_LineID) {
		this.p_LineID = p_LineID;
	}
	
	public int getAdDisbursementCharge_ID() {
		return adDisbursementCharge_ID;
	}

	public void setAdDisbursementCharge_ID(int adDisbursementCharge_ID) {
		this.adDisbursementCharge_ID = adDisbursementCharge_ID;
	}

	public MAccount getAdDisbursementAcct() {
		return adDisbursementAcct;
	}

	public void setAdDisbursementAcct(MAccount adDisbursementAcct) {
		this.adDisbursementAcct = adDisbursementAcct;
	}



}
