package rsge.acct;

import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.model.MConversionType;
import org.compiere.util.CLogger;

import rsge.model.MAdvanceDisbursement;
import rsge.model.MAdvanceDisbursementLine;
import rsge.model.MCash;
import rsge.model.MDisRealizationLine;
import rsge.model.MDisbursementRealization;

public class DocLine_AdvanceDisbursementLine extends DocLine {

	/**	Log	per Document				*/
	protected CLogger			log = CLogger.getCLogger(getClass());
	private int					p_AD_Org_ID = 0;
	private int 				p_PaymentOrg_ID = 0;
	private int					p_LineID = 0;
	
	/**
	 * @param po
	 * @param doc
	 */
	public DocLine_AdvanceDisbursementLine(MAdvanceDisbursementLine line, Doc doc) {
		
		super(line, doc);
		p_LineID = line.getXX_AdvanceDisbursementLine_ID();
		setAmount(line.getAmount());
		setP_AD_Org_ID(line.getAD_Org_ID());
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

}
