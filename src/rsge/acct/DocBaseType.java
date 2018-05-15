/**
 * 
 */
package rsge.acct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.print.Doc;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Trx;

/**
 * @author Fanny
 *
 */
public class DocBaseType {
	/**	Log	per Document				*/
	protected static CLogger			log = CLogger.getCLogger(Doc.class);

	/** Budget Plan = RDI */
	public static final String DOCBASETYPE_CostDistributionInvoice = "RDI";
	/** Advance Disbursement = AAD */
	public static final String DOCBASETYPE_AdvanceDisbursement = "AAD";
	/** Advance Disbursement Realization = ADR */
	public static final String DOCBASETYPE_DisbursementRealization = "ADR";
	/** Disbursement Realization Confirmation = DRC */
	public static final String DOCBASETYPE_DisbursementRealizationConfirmation = "DRC";
	/** Budget Plan = RPE */
	public static final String DOCBASETYPE_ClosingPeriod = "XCP";
	/** Budget Plan = XBP */
	public static final String DOCBASETYPE_BudgetPlan = "XBP";
	/** Budget Plan = XBR */
	public static final String DOCBASETYPE_BudgetRevision = "XBR";
	/** Down Payment Settlement = RDS */
	public static final String DOCBASETYPE_DownPaymentSettlement = "RDS";
	/** Non Item Allocation = NIA */
	public static final String DOCBASETYPE_NonItemAllocation = "NIA";
	/** Receipt **/
	public static final String DOCBASETYPE_ReceiptTransfer = "XRT";
	/** Expense Transfer **/
	public static final String DOCBASETYPE_ExpenseTransfer = "ETR";
	
	/**
	 *	Get the account for Accounting Schema
	 *  @param AcctType see ACCTTYPE_*
	 *  @param as accounting schema
	 *  @return Account
	 */
	public static MAccount getAccount (int AcctType, int M_Product_ID, int C_Charge_ID, MAcctSchema as)
	{
		int C_ValidCombination_ID = getValidCombination_ID(AcctType, M_Product_ID, C_Charge_ID, as);
		if (C_ValidCombination_ID == 0)
			return null;
		//	Return Account
		MAccount acct = MAccount.get (as.getCtx(), C_ValidCombination_ID);
		return acct;
	}	//	getAccount
	
	/**
	 *	Get the account for Accounting Schema
	 *  @param AcctType see ACCTTYPE_*
	 *  @param as accounting schema
	 *  @return Account
	 */
	public static MAccount getIntercompanyAccount (int targetOrg_ID, int IntercompanyType, MAcctSchema as)
	{
		int C_ValidCombination_ID = getIntercompanyValidCombination_ID(targetOrg_ID, IntercompanyType, as);
		if (C_ValidCombination_ID == 0)
			return null;
		//	Return Account
		MAccount acct = MAccount.get (as.getCtx(), C_ValidCombination_ID);
		return acct;
	}	//	getAccount
	
	public static int 					dueFrom = 0;
	public static int					dueTo = 1;
	
	/**
	 *	Get the Valid Combination id for Accounting Schema
	 *  @param AcctType see ACCTTYPE_*
	 *  @param as accounting schema
	 *  @return C_ValidCombination_ID
	 */
	public static int getIntercompanyValidCombination_ID (int targetOrg_ID, int IntercompanyType, MAcctSchema as)
	{
		int para_1 = as.getC_AcctSchema_ID();
		int para_2 = as.getC_AcctSchema_ID();
		int para_3 = as.getC_AcctSchema_ID();
		
		String sql = "SELECT COALESCE(COALESCE(ia.IntercompanyDueFrom_Acct, pia.IntercompanyDueFrom_Acct), ag.IntercompanyDueFrom_Acct) AS InterCompanyDueFrom_Acct, " +
				"COALESCE(COALESCE(ia.IntercompanyDueTo_Acct, pia.IntercompanyDueTo_Acct), ag.IntercompanyDueTo_Acct) AS InterCompanyDueTo_Acct " +
				"FROM AD_OrgInfo oi " +
				"LEFT JOIN XX_IntercompanyAcct ia ON (oi.AD_Org_ID = ia.AD_Org_ID AND ia.C_AcctSchema_ID = ?) " +
				"LEFT OUTER JOIN AD_Org p ON (oi.Parent_Org_ID = p.AD_Org_ID) " +
				"LEFT OUTER JOIN XX_IntercompanyAcct pia ON (oi.Parent_Org_ID = pia.AD_Org_ID AND pia.C_AcctSchema_ID = ?) " +
				"LEFT OUTER JOIN C_AcctSchema_GL ag ON (oi.AD_Client_ID = ag.AD_Client_ID AND ag.C_AcctSchema_ID = ?) " +
				"WHERE oi.AD_Org_ID = ? ";
		
		//  Get Acct
		int Account_ID = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			pstmt = DB.prepareStatement(sql, (Trx)null);
			pstmt.setInt (1, para_1);
			pstmt.setInt (2, para_2);
			pstmt.setInt (3, para_3);
			pstmt.setInt (4, targetOrg_ID);

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				if(IntercompanyType == dueFrom)
				{
					Account_ID = rs.getInt(1);
				}
				else if(IntercompanyType == dueTo)
				{
					Account_ID = rs.getInt(2);					
				}
			}			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		//	No account
		if (Account_ID == 0)
		{
//			log.warning ("Account NOT found Type="
//				+ AcctType);
			return 0;
		}
		return Account_ID;
		
	}
	
	public static MAccount getBudgetAcct(int Account_ID, MAcctSchema as)
	{
		// Get first AD_Org_ID of Client
		int AD_Org_ID = 0;
		String sql = "SELECT AD_Org_ID FROM AD_Org " +
				"WHERE AD_Client_ID = ? " +
				"ORDER BY AD_Org_ID ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, as.get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, as.getAD_Client_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				AD_Org_ID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		MAccount acct = MAccount.get(as.getCtx(), as.getAD_Client_ID(), AD_Org_ID, as.getC_AcctSchema_ID(), Account_ID,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		
		return acct;
	}
	
	public static MAccount getConsolidationAcct(int AD_Org_ID, MAcctSchema as)
	{
		// Get first AD_Org_ID of Client
		int accountID = 0;
		String sql = "SELECT ConsolidationClrgAcct_ID FROM XX_Consolidation_Acct " +
				"WHERE AD_Client_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, as.get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, as.getAD_Client_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
				accountID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		MAccount acct = MAccount.get(as.getCtx(), as.getAD_Client_ID(), AD_Org_ID, as.getC_AcctSchema_ID(), accountID,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		
		return acct;
	}

	
	/** Account Type - Budget Plan		*/
	public static final int		ACCTTYPE_BudgetClearing					= 1;
	/** Account Type - Reserved Budget	*/
	public static final int		ACCTTYPE_ReservedBudget					= 2;

	/** Account Type - Sales Return (Product)	*/
	public static final int		ACCTTYPE_SalesReturn					= 3;
	/** Account Type - Unallocated Return (Product)	*/
	public static final int		ACCTTYPE_UnallocatedReturn				= 4;
	/** Account Type - Service Charge	*/
	public static final int		ACCTTYPE_ServiceCharge					= 5;
	/** Account Type - Invoice Discount	*/
	public static final int		ACCTTYPE_InvoiceDiscount				= 6;
	/** Account Type - Charge	*/
	public static final int		ACCTTYPE_Charge							= 7;
	
	/** Account Type - Product Customer Prepayment	*/
	public static final int		ACCTTYPE_ProductCustomerPrepayment		= 8;
	/** Account Type - Product Vendor Prepayment	*/
	public static final int		ACCTTYPE_ProductVendorPrepayment		= 9;

	/** Account Type - Charge Customer Prepayment	*/
	public static final int		ACCTTYPE_ChargeCustomerPrepayment		= 10;
	/** Account Type - Charge Vendor Prepayment	*/
	public static final int		ACCTTYPE_ChargeVendorPrepayment			= 11;


	/**
	 *	Get the Valid Combination id for Accounting Schema
	 *  @param AcctType see ACCTTYPE_*
	 *  @param as accounting schema
	 *  @return C_ValidCombination_ID
	 */
	public static int getValidCombination_ID (int AcctType, int M_Product_ID, int C_Charge_ID, MAcctSchema as)
	{
		int para_1 = 0;     //  first parameter (second is always AcctSchema, third is product)
		System.out.println("Account Type " + AcctType);
		String sql = null;
		/**	Account Type */
		if(AcctType == ACCTTYPE_BudgetClearing)
		{
			sql = "SELECT BudgetClearing_Acct " +
					"FROM XX_BudgetInfo " +
					"WHERE AD_Client_ID = ? AND C_AcctSchema_ID = ? AND IsActive = 'Y' ";						
		}
		else if(AcctType == ACCTTYPE_ReservedBudget)
		{
			sql = "SELECT ReservedBudget_Acct " +
					"FROM XX_BudgetInfo " +
					"WHERE AD_Client_ID = ? AND C_AcctSchema_ID = ? AND IsActive = 'Y' ";						
		}
		else if(AcctType == ACCTTYPE_SalesReturn || AcctType == ACCTTYPE_ProductCustomerPrepayment || AcctType == ACCTTYPE_ProductVendorPrepayment)
		{			
			sql = "SELECT P_SalesReturn_Acct, C_Prepayment_Acct, V_Prepayment_Acct " +
					"FROM M_Product_Acct " +
					"WHERE AD_Client_ID = ? AND C_AcctSchema_ID = ? AND M_Product_ID = ? AND IsActive = 'Y' ";						
		}
		else if(AcctType == ACCTTYPE_UnallocatedReturn)
		{			
			sql = "SELECT UnallocatedReturn_Acct " +
					"FROM C_AcctSchema_Default " +
					"WHERE AD_Client_ID = ? AND C_AcctSchema_ID = ? AND IsActive = 'Y' ";						
		}
		else if(AcctType == ACCTTYPE_ServiceCharge)
		{
			sql = " SELECT ServiceCharge_Acct "
					+ " FROM C_AcctSchema_Default "
					+ " WHERE AD_Client_ID=? AND C_AcctSchema_ID=? AND IsActive = 'Y' ";

		}
		else if(AcctType == ACCTTYPE_InvoiceDiscount)
		{
			sql = " SELECT P_TradeDiscountGrant_Acct "
					+ " FROM C_AcctSchema_Default "
					+ " WHERE AD_Client_ID=? AND C_AcctSchema_ID=? AND IsActive = 'Y' ";

		}
		else if(AcctType == ACCTTYPE_Charge || AcctType == ACCTTYPE_ChargeCustomerPrepayment || AcctType == ACCTTYPE_ChargeVendorPrepayment)
		{
			sql = " SELECT COALESCE(a.CH_Expense_Acct,b.CH_Expense_Acct), b.C_Prepayment_Acct, b.V_Prepayment_Acct "
					+ " FROM C_Charge_Acct a "
					+ " INNER JOIN C_AcctSchema_Default b ON (a.C_AcctSchema_ID = b.C_AcctSchema_ID)"
					+ " WHERE a.AD_Client_ID=? AND a.C_AcctSchema_ID=? AND a.C_Charge_ID=? AND a.IsActive = 'Y' ";

		}
		
		para_1 = as.getAD_Client_ID();
		
		//  Do we have sql & Parameter
		if ((sql == null) || (para_1 == 0))
		{
			log.warning ("No Parameter for AcctType=" + AcctType + " - SQL=" + sql);
			return 0;
		}

		//  Get Acct
		int Account_ID = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			System.out.println(sql);
			System.out.println(para_1);
			System.out.println(as.getC_AcctSchema_ID());
			pstmt = DB.prepareStatement(sql, (Trx)null);
			pstmt.setInt (1, para_1);
			pstmt.setInt (2, as.getC_AcctSchema_ID());
			if(AcctType == ACCTTYPE_SalesReturn || AcctType == ACCTTYPE_ProductCustomerPrepayment || AcctType == ACCTTYPE_ProductVendorPrepayment)
			{
				pstmt.setInt (3, M_Product_ID);			
				System.out.println(M_Product_ID);
			}
			if(AcctType == ACCTTYPE_Charge || AcctType == ACCTTYPE_ChargeCustomerPrepayment || AcctType == ACCTTYPE_ChargeVendorPrepayment)
				pstmt.setInt (3, C_Charge_ID);				
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				int id = 1;
				if(AcctType == ACCTTYPE_ProductCustomerPrepayment || AcctType == ACCTTYPE_ChargeCustomerPrepayment)
					id = 2;
				if(AcctType == ACCTTYPE_ProductVendorPrepayment || AcctType == ACCTTYPE_ChargeVendorPrepayment)
					id = 3;
				System.out.println("id " + id);
				Account_ID = rs.getInt(id);				
			}
			
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "AcctType=" + AcctType + " - SQL=" + sql, e);
			return 0;
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		//	No account
		if (Account_ID == 0)
		{
			log.warning ("Account NOT found Type="
				+ AcctType);
			return 0;
		}
		return Account_ID;
		
	}

}
