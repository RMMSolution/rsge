/**
 * 
 */
package rsge.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
import org.compiere.util.DB;
import org.compiere.util.Trx;

import rsge.model.MBudgetInfo;
import rsge.model.MBudgetRevision;
import rsge.model.MBudgetRevisionAccount;
import rsge.model.MBudgetRevisionTargetAcct;
import rsge.po.X_XX_BudgetRevision;
import rsge.utils.GeneralEnhancementUtils;

/**
 * @author Fanny
 *
 */
public class Doc_BudgetRevision extends Doc {

	/**
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trx
	 */
	public Doc_BudgetRevision(MAcctSchema[] ass, ResultSet rs, Trx trx) {
		super(ass, MBudgetRevision.class, rs, DocBaseType.DOCBASETYPE_BudgetRevision, trx);
		// TODO Auto-generated constructor stub
	}
	
	private MBudgetRevision					budgetRevision = null;
	private int								m_C_AcctSchema_ID = 0;
	private int								m_GL_Budget_ID = 0;
	private Timestamp						dateAcct = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	public String loadDocumentDetails() {
		budgetRevision = (MBudgetRevision)getPO();
		setDateDoc(budgetRevision.getDateDoc());
		
		dateAcct = budgetRevision.getDateEffective();
		if(budgetRevision.isPeriod())
		{
			MPeriod period = new MPeriod(getCtx(), budgetRevision.getC_Period_From_ID(), getTrx());
			dateAcct = period.getStartDate();
		}
		setDateAcct(dateAcct);
		
		// Get Budget Accounting Schema
		MBudgetInfo bi = GeneralEnhancementUtils.getBudgetInfo(getCtx(), getAD_Client_ID(), getTrx());
		X_GL_BudgetControl bc = new X_GL_BudgetControl(getCtx(), bi.getGL_BudgetControl_ID(), getTrx());
		m_C_AcctSchema_ID = bi.getC_AcctSchema_ID();
		m_GL_Budget_ID = bc.getGL_Budget_ID();
		
		MAcctSchema acctSchema = new MAcctSchema(getCtx(), m_C_AcctSchema_ID, getTrx());
		setC_Currency_ID(acctSchema.getC_Currency_ID());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		// TODO Auto-generated method stub
		return BigDecimal.ZERO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
		 ArrayList<Fact> facts = new ArrayList<Fact>();
		//	Other Acct Schema
		if (as.getC_AcctSchema_ID() != m_C_AcctSchema_ID)
			return facts;

		//  create Fact Header
		Fact fact = new Fact (this, as, Fact.POST_Budget);						
		BigDecimal budgetClrgAmt = BigDecimal.ZERO;
		
		for(MBudgetRevisionAccount bra : loadAccount())
		{
			// Get Account ID
			int m_Account_ID = bra.getAccount_ID();
			String m_AccountSign = null;
			
			DocLine dl = new DocLine(bra, this);
			// Check account natural sign			
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

			// Budget Revision Increase
			// Account +
			FactLine source = null;
			if(budgetRevision.getBudgetRevisionType().equals(X_XX_BudgetRevision.BUDGETREVISIONTYPE_Increase))
			{
				// If Account sign is DR
				if(m_AccountSign.equals(X_C_ElementValue.ACCOUNTSIGN_Debit))
				{
					// Account 				Dr
					source = fact.createLine(dl, DocBaseType.getBudgetAcct(m_Account_ID, as), as.getC_Currency_ID(), bra.getAppliedAmt(), null);
					// Update Budget Clrg
					budgetClrgAmt = budgetClrgAmt.add(bra.getAppliedAmt());					
				}
				// Account Sign is CR
				else
				{
					// Account 				Cr
					source = fact.createLine(dl, DocBaseType.getBudgetAcct(m_Account_ID, as), as.getC_Currency_ID(), null, bra.getAppliedAmt());					
					// Update Budget Clrg
					budgetClrgAmt = budgetClrgAmt.subtract(bra.getAppliedAmt());
				}
				if(bra.isProduct())
				{
					source.setQty(bra.getRevisedQty().subtract(bra.getRemainingBudgetQty()));
				}
			}
			// Budget Revision Cut and Transfer
			else
			{
				// If Account sign is DR
				// Set amount to CR
				if(m_AccountSign.equals(X_C_ElementValue.ACCOUNTSIGN_Debit))
				{
					// Account 				Cr
					source = fact.createLine(dl, DocBaseType.getBudgetAcct(m_Account_ID, as), as.getC_Currency_ID(), null, bra.getAppliedAmt());
					// Update Budget Clrg
					budgetClrgAmt = budgetClrgAmt.subtract(bra.getAppliedAmt());					
				}
				// Account Sign is CR
				// Set amount to DR
				else
				{
					// Account 				Dr
					source = fact.createLine(dl, DocBaseType.getBudgetAcct(m_Account_ID, as), as.getC_Currency_ID(), bra.getAppliedAmt(), null);
					// Update Budget Clrg
					budgetClrgAmt = budgetClrgAmt.add(bra.getAppliedAmt());					
				}
				
				if(bra.isProduct())
				{
					source.setQty(bra.getRemainingBudgetQty().subtract(bra.getRevisedQty()));
				}

			}
			source.setGL_Budget_ID(m_GL_Budget_ID);									
//			if(budgetRevision.getC_Activity_ID()!=0)
//				source.setC_Activity_ID(budgetRevision.getC_Activity_ID());
//			if(budgetRevision.getC_BPartner_ID()!=0)
//				source.setC_BPartner_ID(budgetRevision.getC_BPartner_ID());
//			if(budgetRevision.getC_Campaign_ID()!=0)
//				source.setC_Campaign_ID(budgetRevision.getC_Campaign_ID());
//			if(budgetRevision.getM_Product_ID()!=0)
//				source.setM_Product_ID(budgetRevision.getM_Product_ID());
//			if(budgetRevision.getC_Project_ID()!=0)
//				source.setC_Project_ID(budgetRevision.getC_Project_ID());
//			if(budgetRevision.getC_SalesRegion_ID()!=0)
//				source.setC_SalesRegion_ID(budgetRevision.getC_SalesRegion_ID());
		}

		//*************************** TARGET ACCOUNT **************************************************************************//
		// Create Budget Clearing
		if(budgetRevision.getBudgetRevisionType().equals(X_XX_BudgetRevision.BUDGETREVISIONTYPE_Increase)
				|| budgetRevision.getBudgetRevisionType().equals(X_XX_BudgetRevision.BUDGETREVISIONTYPE_Cut)
				|| (budgetRevision.getBudgetRevisionType().equals(X_XX_BudgetRevision.BUDGETREVISIONTYPE_Transfer) && budgetRevision.isPeriod()) 
				|| (budgetRevision.getBudgetRevisionType().equals(X_XX_BudgetRevision.BUDGETREVISIONTYPE_Transfer) && budgetRevision.isOrganization()))
		{
			FactLine budget = null;
			FactLine targetBudget = null;
			if(budgetClrgAmt.compareTo(BigDecimal.ZERO)>0)
			{
				budget = fact.createLine(null, DocBaseType.getAccount(DocBaseType.ACCTTYPE_BudgetClearing, 0, 0, as), as.getC_Currency_ID(), null, budgetClrgAmt);	
				if(budgetRevision.isOrganization())
				{
					targetBudget = fact.createLine(null, DocBaseType.getAccount(DocBaseType.ACCTTYPE_BudgetClearing, 0, 0, as), as.getC_Currency_ID(), budgetClrgAmt, null);							
				}

			}
			else if(budgetClrgAmt.compareTo(BigDecimal.ZERO)<0)
			{
				budget = fact.createLine(null, DocBaseType.getAccount(DocBaseType.ACCTTYPE_BudgetClearing, 0, 0, as), as.getC_Currency_ID(), budgetClrgAmt.negate(), null);	
				if(budgetRevision.isOrganization())
				{
					targetBudget = fact.createLine(null, DocBaseType.getAccount(DocBaseType.ACCTTYPE_BudgetClearing, 0, 0, as), as.getC_Currency_ID(), null, budgetClrgAmt.negate());							
				}
			}
			budget.setGL_Budget_ID(m_GL_Budget_ID);	
			
			if(budgetRevision.isOrganization())
			{
				targetBudget.setGL_Budget_ID(m_GL_Budget_ID);
				targetBudget.setAD_Org_ID(budgetRevision.getAD_OrgTo_ID());
				targetBudget.setAD_OrgTrx_ID(budgetRevision.getAD_OrgTo_ID());
				
				// Set Target Account
				for(MBudgetRevisionTargetAcct brta : loadTargetAccount())
				{
					// Get Account ID
					int m_Account_ID = brta.getAccount_ID();
//					DocLine dl = new DocLine(brta, this);

					FactLine targetAcct = null;
					if(budgetClrgAmt.compareTo(BigDecimal.ZERO)>0)
					{
						targetAcct = fact.createLine(null, DocBaseType.getBudgetAcct(m_Account_ID, as), as.getC_Currency_ID(), null, brta.getAppliedAmt());
					}
					else if(budgetClrgAmt.compareTo(BigDecimal.ZERO)<0)
					{
						targetAcct = fact.createLine(null, DocBaseType.getBudgetAcct(m_Account_ID, as), as.getC_Currency_ID(), brta.getAppliedAmt(), null);						
					}				
					targetAcct.setGL_Budget_ID(m_GL_Budget_ID);
					targetAcct.setAD_Org_ID(budgetRevision.getAD_OrgTo_ID());
					targetAcct.setAD_OrgTrx_ID(budgetRevision.getAD_OrgTo_ID());
					targetAcct.setC_Activity_ID(budgetRevision.getC_Activity_To_ID());
					targetAcct.setC_BPartner_ID(budgetRevision.getC_BPartner_To_ID());
					targetAcct.setC_Campaign_ID(budgetRevision.getC_Campaign_To_ID());
					targetAcct.setM_Product_ID(budgetRevision.getM_Product_To_ID());
					targetAcct.setC_Project_ID(budgetRevision.getC_Project_To_ID());
					targetAcct.setC_SalesRegion_ID(budgetRevision.getC_SalesRegion_To_ID());
				}
			} // End if Is Organization
			
			if(budgetRevision.isPeriod())
			{
				// Set Date Acct
				MPeriod periodTo = new MPeriod(getCtx(), budgetRevision.getC_Period_To_ID(), getTrx());
				dateAcct = periodTo.getStartDate();
				setDateAcct(dateAcct);
				
				for(MBudgetRevisionAccount bra : loadAccount())
				{
					// Get Account ID
					int m_Account_ID = bra.getAccount_ID();
					String m_AccountSign = null;
					
					DocLine dl = new DocLine(bra, this);
					// Check account natural sign			
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
					// If Account sign is DR
					// Set amount to DR
					FactLine target = null;
					if(m_AccountSign.equals(X_C_ElementValue.ACCOUNTSIGN_Debit))
					{
						// Account 				Dr
						target = fact.createLine(dl, DocBaseType.getBudgetAcct(m_Account_ID, as), as.getC_Currency_ID(), bra.getAppliedAmt(), null);
					}
					// Account Sign is CR
					// Set amount to CR
					else
					{
						// Account 				Cr
						target = fact.createLine(dl, DocBaseType.getBudgetAcct(m_Account_ID, as), as.getC_Currency_ID(), null, bra.getAppliedAmt());
					}
					target.setGL_Budget_ID(m_GL_Budget_ID);
					target.setC_Period_ID(periodTo.getC_Period_ID());
				}
				
				// Create Budget Clearing
				if(budgetClrgAmt.compareTo(BigDecimal.ZERO)>0)
				{
					budget = fact.createLine(null, DocBaseType.getAccount(DocBaseType.ACCTTYPE_BudgetClearing, 0, 0, as), as.getC_Currency_ID(), budgetClrgAmt, null);	
				}
				else if(budgetClrgAmt.compareTo(BigDecimal.ZERO)<0)
				{					
					budget = fact.createLine(null, DocBaseType.getAccount(DocBaseType.ACCTTYPE_BudgetClearing, 0, 0, as), as.getC_Currency_ID(), null, budgetClrgAmt.negate());	
				}
				budget.setGL_Budget_ID(m_GL_Budget_ID);
				budget.setC_Period_ID(periodTo.getC_Period_ID());
			}

		}
		// Budget Revision Transfer between accounts (Create target account)		
		else
		{
			// Get Target Account
			for(MBudgetRevisionTargetAcct brta : loadTargetAccount())
			{
				// Get Account ID
				int m_Account_ID = brta.getAccount_ID();
				String m_AccountSign = null;
				
//				DocLine dl = new DocLine(brta, this);
				// Check account natural sign			
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
				
				// If Account sign is DR
				FactLine targetAcct = null;
				if(m_AccountSign.equals(X_C_ElementValue.ACCOUNTSIGN_Debit))
				{
					// Account 				Dr
					targetAcct = fact.createLine(null, DocBaseType.getBudgetAcct(m_Account_ID, as), as.getC_Currency_ID(), brta.getAppliedAmt(), null);
				}
				// Account Sign is CR
				else
				{
					// Account 				Cr
					targetAcct = fact.createLine(null, DocBaseType.getBudgetAcct(m_Account_ID, as), as.getC_Currency_ID(), null, brta.getAppliedAmt());
				}				
				targetAcct.setGL_Budget_ID(m_GL_Budget_ID);				

//				if(budgetRevision.isActivity())
					targetAcct.setC_Activity_ID(budgetRevision.getC_Activity_To_ID());
//				if(budgetRevision.isBusinessPartner())
					targetAcct.setC_BPartner_ID(budgetRevision.getC_BPartner_To_ID());
//				if(budgetRevision.isCampaign())
					targetAcct.setC_Campaign_ID(budgetRevision.getC_Campaign_To_ID());
//				if(budgetRevision.isProduct())
					targetAcct.setM_Product_ID(budgetRevision.getM_Product_To_ID());
//				if(budgetRevision.isProject())
					targetAcct.setC_Project_ID(budgetRevision.getC_Project_To_ID());
//				if(budgetRevision.isSalesRegion())
					targetAcct.setC_SalesRegion_ID(budgetRevision.getC_SalesRegion_To_ID());
			}
		}
		
		facts.add(fact);
		return facts;
	}
	
	private MBudgetRevisionAccount[] loadAccount()
	{
		ArrayList<MBudgetRevisionAccount> line = new ArrayList<MBudgetRevisionAccount>();
		String sql = "SELECT * " +
				"FROM XX_BudgetRevisionAccount " +
				"WHERE XX_BudgetRevision_ID = " + budgetRevision.getXX_BudgetRevision_ID();
		
		PreparedStatement pstmt = DB.prepareStatement(sql, getTrx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MBudgetRevisionAccount bra = new MBudgetRevisionAccount(getCtx(), rs, getTrx());
				line.add(bra);
			}
			
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		MBudgetRevisionAccount[] lines = new MBudgetRevisionAccount[line.size()];
		line.toArray(lines);
		return lines;
	}
	
	private MBudgetRevisionTargetAcct[] loadTargetAccount()
	{
		ArrayList<MBudgetRevisionTargetAcct> line = new ArrayList<MBudgetRevisionTargetAcct>();
		String sql = "SELECT * " +
				"FROM XX_BudgetRevisionTargetAcct " +
				"WHERE XX_BudgetRevision_ID = " + budgetRevision.getXX_BudgetRevision_ID();
		
		PreparedStatement pstmt = DB.prepareStatement(sql, getTrx());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MBudgetRevisionTargetAcct brta = new MBudgetRevisionTargetAcct(getCtx(), rs, getTrx());
				line.add(brta);
			}
			
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		MBudgetRevisionTargetAcct[] lines = new MBudgetRevisionTargetAcct[line.size()];
		line.toArray(lines);
		return lines;
	}

}
