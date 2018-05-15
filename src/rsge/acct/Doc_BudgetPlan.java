/**
 * 
 */
package rsge.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MElementValue;
import org.compiere.model.MPeriod;
import org.compiere.model.X_C_ElementValue;
import org.compiere.model.X_GL_BudgetControl;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.model.MBudgetInfo;
import rsge.model.MBudgetPlanning;
import rsge.model.MBudgetPlanningLine;
import rsge.model.MBudgetPlanningLinePeriod;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author Fanny
 *
 */
public class Doc_BudgetPlan extends Doc {

	/**
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trx
	 */
	public Doc_BudgetPlan(MAcctSchema[] ass, ResultSet rs, Trx trx) {
		super(ass, MBudgetPlanningLinePeriod.class, rs, DocBaseType.DOCBASETYPE_BudgetPlan, trx);
		// TODO Auto-generated constructor stub
	}
	
	private MBudgetPlanningLinePeriod 		planPeriod = null;
	private int								m_C_AcctSchema_ID = 0;
	private int								m_GL_Budget_ID = 0;
	
	// Account Detail
	private int								m_Account_ID = 0;						
	private String							m_AccountSign = null;
	
	// Amount
	private BigDecimal						m_Amount = BigDecimal.ZERO;
	private BigDecimal						m_Qty = BigDecimal.ZERO;
	
	private MBudgetPlanningLine				bpl = null;
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	public String loadDocumentDetails() {
		planPeriod = (MBudgetPlanningLinePeriod) getPO();
		// Set Date Doc (taken from Budget Plan Date)
		bpl = new MBudgetPlanningLine(getCtx(), planPeriod.getXX_BudgetPlanningLine_ID(), getTrx());
		MBudgetPlanning bp = new MBudgetPlanning(getCtx(), bpl.getXX_BudgetPlanning_ID(), getTrx());		
		setDateDoc(bp.getDateDoc());
		
		// Set Date Acct (taken from Period start date)
		MPeriod period = new MPeriod(getCtx(), planPeriod.getC_Period_ID(), getTrx());
		setDateAcct(period.getStartDate());
		
		// Get Budget Accounting Schema
		MBudgetInfo bi = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), getTrx());
		X_GL_BudgetControl bc = new X_GL_BudgetControl(getCtx(), bi.getGL_BudgetControl_ID(), getTrx());
		m_C_AcctSchema_ID = bi.getC_AcctSchema_ID();
		m_GL_Budget_ID = bc.getGL_Budget_ID();
				
		// Get Account detail
//		if(bpl.getM_Product_ID()!=0)
			m_Account_ID = bpl.getAccount_ID();
//		else
//		{
//			
//		}

		
		MElementValue account = new MElementValue(getCtx(), m_Account_ID, getTrx());
		if(account.getAccountSign().equals(X_C_ElementValue.ACCOUNTSIGN_Natural))
		{
			if(account.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Asset)
					|| account.getAccountType().equals(X_C_ElementValue.ACCOUNTTYPE_Expense))
			{
				m_AccountSign = X_C_ElementValue.ACCOUNTSIGN_Debit;
			}
			else 
			{
				m_AccountSign = X_C_ElementValue.ACCOUNTSIGN_Credit;
			}
		}
		else 
		{
			m_AccountSign = account.getAccountSign();
		}
		
		if(m_AccountSign.equals(X_C_ElementValue.ACCOUNTSIGN_Debit))
		{
			BigDecimal amt = planPeriod.getTotalDr().subtract(planPeriod.getTotalCr());
			if(amt.compareTo(BigDecimal.ZERO)>0)
				m_Amount = amt;
			else
			{
				m_Amount = amt.negate();
				m_AccountSign = X_C_ElementValue.ACCOUNTSIGN_Credit;
			}
		}
		else
		{
			BigDecimal amt = planPeriod.getTotalCr().subtract(planPeriod.getTotalDr());
			if(amt.compareTo(BigDecimal.ZERO)>0)
				m_Amount = amt;
			else
			{
				m_Amount = amt.negate();
				m_AccountSign = X_C_ElementValue.ACCOUNTSIGN_Debit;
			}
		}
		m_Qty = planPeriod.getQty();
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	/**
	 *  Create Facts (the accounting logic) for
	 *  XBP	
	 *  <pre>
	 *  XBP
	 *      Account			     DR
	 *      Budget Clearing	         CR
	 *
	 *  </pre>
	 *  @param as accounting schema
	 *  @return Fact
	 */	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		 ArrayList<Fact> facts = new ArrayList<Fact>();
		//	Other Acct Schema
		if (as.getC_AcctSchema_ID() != m_C_AcctSchema_ID)
			return facts;

		//  create Fact Header
		Fact fact = new Fact (this, as, Fact.POST_Budget);				
		FactLine budgetClrg = null;
		// Set Doc Line
		DocLine line = new DocLine(bpl, this);
		
		if(m_Amount.compareTo(BigDecimal.ZERO)!=0)
		{
			if(m_AccountSign.equalsIgnoreCase("D"))
			{
				// Account 				Dr
				FactLine Dr = fact.createLine(line, DocBaseType.getBudgetAcct(m_Account_ID, as), as.getC_Currency_ID(), m_Amount, null);
				Dr.setGL_Budget_ID(m_GL_Budget_ID);				
				Dr.setQty(m_Qty);
				
				// Budget Clearing		Cr
				budgetClrg = fact.createLine(null, DocBaseType.getAccount(DocBaseType.ACCTTYPE_BudgetClearing, 0, 0, as), as.getC_Currency_ID(), null, m_Amount);			
				budgetClrg.setGL_Budget_ID(m_GL_Budget_ID);
			}
			else
			{
				// Budget Clearing		D
				budgetClrg = fact.createLine(null, DocBaseType.getAccount(DocBaseType.ACCTTYPE_BudgetClearing, 0, 0, as), as.getC_Currency_ID(), m_Amount, null);
				budgetClrg.setGL_Budget_ID(m_GL_Budget_ID);
				
				// Account 				Cr
				FactLine Cr = fact.createLine(line, DocBaseType.getBudgetAcct(m_Account_ID, as), as.getC_Currency_ID(), null, m_Amount);
				Cr.setGL_Budget_ID(m_GL_Budget_ID);
				Cr.setQty(m_Qty.negate());
			}
		}
		facts.add(fact);
		return facts;	
	}

}
