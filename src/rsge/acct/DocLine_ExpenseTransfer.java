package rsge.acct;

import java.math.BigDecimal;

import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.util.CLogger;

import rsge.model.MCashLine;
import rsge.model.MChargeAcct;
import rsge.model.MClientOrg;
import rsge.model.MExpenseTransfer;

public class DocLine_ExpenseTransfer extends DocLine {

	/**	Log	per Document				*/
	protected CLogger			log = CLogger.getCLogger(getClass());
	private int					p_AD_Org_ID = 0;
	private BigDecimal			p_Amount = BigDecimal.ZERO;
	
	/**
	 * @param po
	 * @param doc
	 */
	public DocLine_ExpenseTransfer(MCashLine line, MAcctSchema as, Doc doc) {
		
		super(line, doc);
		if(line.getC_CashLine_ID()!=0)
		{
			System.out.println("OK HERE");
			MCashLine cl = new MCashLine(line.getCtx(), line.getC_CashLine_ID(), line.get_Trx());
			setP_AD_Org_ID(MClientOrg.get(cl.getTargetOrg_ID(), getTrx()));
			setP_Amount(cl.getCashAmount());			
			MChargeAcct ca = MChargeAcct.get(getC_Charge_ID(), as);
			setAccount(new MAccount(line.getCtx(), ca.getCh_Expense_Acct(), line.get_Trx()));
		}
	}

	public int getP_AD_Org_ID() {
		return p_AD_Org_ID;
	}

	public void setP_AD_Org_ID(int p_AD_Org_ID) {
		this.p_AD_Org_ID = p_AD_Org_ID;
	}

	public BigDecimal getP_Amount() {
		return p_Amount;
	}

	public void setP_Amount(BigDecimal p_Amount) {
		this.p_Amount = p_Amount;
	}
}
