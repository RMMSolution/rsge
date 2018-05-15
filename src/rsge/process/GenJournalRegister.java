/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.compiere.model.MAllocationHdr;
import org.compiere.model.MBankStatement;
import org.compiere.model.MConsignedTransfer;
import org.compiere.model.MElementValue;
import org.compiere.model.MFactAcct;
import org.compiere.model.MJournal;
import org.compiere.model.MMatchInv;
import org.compiere.model.MMatchPO;
import org.compiere.model.MMovement;
import org.compiere.model.MRequisition;
import org.compiere.model.X_M_Production;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MCash;
import rsge.model.MInOut;
import rsge.model.MInventory;
import rsge.model.MInvoice;
import rsge.model.MPayment;

/**
 * @author Fanny
 *
 */
public class GenJournalRegister extends SvrProcess {

	private int						p_C_AcctSchema_ID = 0;
	private int						p_AD_Org_ID = 0;
	private Timestamp				p_DateStart = null;
	private Timestamp				p_DateEnd = null;
	
	private ArrayList<MFactAcct> 	factList = new ArrayList<MFactAcct>();
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// DELETE Table based on Client 
		String delete = "DELETE FROM T_GLJournalRegister WHERE AD_Client_ID = ? ";
		DB.executeUpdate(get_Trx(), delete, getAD_Client_ID());	
		
		// Get Parameter
		ProcessInfoParameter params[] = getParameter();
		for(ProcessInfoParameter param : params){
			String name = param.getParameterName();
			if(name.equals("C_AcctSchema_ID"))
				p_C_AcctSchema_ID = param.getParameterAsInt();
			else if(name.equals("AD_Org_ID"))
				p_AD_Org_ID = param.getParameterAsInt();
			else if(name.equals("DateAcct"))
			{
				p_DateStart = (Timestamp)param.getParameter();
				p_DateEnd = (Timestamp)param.getParameter_To();
			}
		}		
		
		if(p_DateEnd==null)
		{
			Calendar cal = Calendar.getInstance();
			p_DateEnd = new Timestamp(cal.getTimeInMillis());
		}
		StringBuilder sql = new StringBuilder("SELECT * FROM Fact_Acct " +
				"WHERE DateAcct BETWEEN ? AND ? " +
				"AND C_AcctSchema_ID = ? AND PostingType = 'A' ");
		if(p_AD_Org_ID>0)
		{
			sql.append("AND AD_Org_ID = ");
			sql.append(p_AD_Org_ID);
			sql.append(" ");
		}
		else
		{
			sql.append("AND AD_Client_ID = ");
			sql.append(getAD_Client_ID());
			sql.append(" ");
		}	
		
		PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_Trx());
		ResultSet rs = null;
		try{
			System.out.println(sql);
			System.out.println(p_DateStart);
			System.out.println(p_DateEnd);
			System.out.println(p_C_AcctSchema_ID);
			pstmt.setTimestamp(1, p_DateStart);
			pstmt.setTimestamp(2, p_DateEnd);
			pstmt.setInt(3, p_C_AcctSchema_ID);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MFactAcct fa = new MFactAcct(getCtx(), rs, get_Trx());
				factList.add(fa);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// 
		for(MFactAcct fa : factList)
		{
			String moduleName = null;
			String documentNo = null;
			
			Map<String, String> values = new HashMap<String, String>();
			values = getDocumentInfo(fa.getAD_Table_ID(), fa.getRecord_ID());
			
			for(Map.Entry<String, String> entry : values.entrySet())
			{
				moduleName = entry.getKey();
				documentNo = entry.getValue();
			}
			
			Integer orgID = null;
			String accountName = null;
			MElementValue ev = new MElementValue(getCtx(), fa.getAccount_ID(), get_Trx());
			accountName = ev.getDescription();
			
			if(p_AD_Org_ID>0)
				orgID = p_AD_Org_ID;
			String update = "INSERT INTO T_GLJournalRegister (AD_Client_ID, AD_Org_ID, " +
					"C_AcctSchema_ID, Account_ID, AccountName, Description, DateAcct, DocumentType, DocumentNo, AmtAcctDr, AmtAcctCr, Record_ID) " +
					"VALUES (?, ?, " +
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			DB.executeUpdate(get_Trx(), update, getAD_Client_ID(), orgID, 
					p_C_AcctSchema_ID, fa.getAccount_ID(), accountName, fa.getDescription(), fa.getDateAcct(), moduleName, documentNo, fa.getAmtAcctDr(), fa.getAmtAcctCr(), fa.getRecord_ID());
		}
		return "Generate Complete";
	}

	
	private Map<String, String> getDocumentInfo(int tableID, int recordID)
	{
		Map<String, String> retValue = new HashMap<String, String>();
		String moduleName = null;
		String documentNo = null;
		
		if(tableID == 224)
		{			
			MJournal journal = new MJournal(getCtx(), recordID, get_Trx());
			documentNo = journal.getDocumentNo();
			moduleName = "GL Journal";			
		}
		else if(tableID == 318)
		{			
			MInvoice invoice = new MInvoice(getCtx(), recordID, get_Trx());
			documentNo = invoice.getDocumentNo();
			if(invoice.isSOTrx() && !invoice.isReturnTrx())
			{
				moduleName = "AR Invoice";
			}
			else if(invoice.isSOTrx() && invoice.isReturnTrx())
			{
				moduleName = "AR Credit Memo";
			}
			if(!invoice.isSOTrx() && !invoice.isReturnTrx())
			{
				moduleName = "AP Invoice";
			}
			else if(!invoice.isSOTrx() && invoice.isReturnTrx())
			{
				moduleName = "AP Credit Memo";
			}
		}
		else if(tableID == 319)
		{			
			MInOut io = new MInOut(getCtx(), recordID, get_Trx());
			documentNo = io.getDocumentNo();
			if(io.isSOTrx() && !io.isReturnTrx())
			{
				moduleName = "Shipment";
			}
			else if(io.isSOTrx() && io.isReturnTrx())
			{
				moduleName = "Customer Return";
			}
			if(!io.isSOTrx() && !io.isReturnTrx())
			{
				moduleName = "Receipt";
			}
			else if(!io.isSOTrx() && io.isReturnTrx())
			{
				moduleName = "Return to Vendor";
			}
		}
		else if(tableID == 321)
		{			
			MInventory io = new MInventory(getCtx(), recordID, get_Trx());
			documentNo = io.getDocumentNo();
			moduleName = "Internal Use/Physical Inventory";
		}
		else if(tableID == 323)
		{			
			MMovement doc = new MMovement(getCtx(), recordID, get_Trx());
			documentNo = doc.getDocumentNo();
			moduleName = "Inventory Move";			
		}
		else if(tableID == 325)
		{			
			X_M_Production doc = new X_M_Production(getCtx(), recordID, get_Trx());
			documentNo = doc.getName();
			moduleName = "Production";			
		}
		else if(tableID == 335)
		{			
			MPayment doc = new MPayment(getCtx(), recordID, get_Trx());
			documentNo = doc.getDocumentNo();
			if(doc.isReceipt())
				moduleName = "Payment Receipt";
			else
				moduleName = "Payment";
		}
		else if(tableID == 392)
		{			
			MBankStatement doc = new MBankStatement(getCtx(), recordID, get_Trx());
			documentNo = doc.getDocumentNo();
			moduleName = "Bank Statement";			
		}
		else if(tableID == 407)
		{			
			MCash doc = new MCash(getCtx(), recordID, get_Trx());
			documentNo = doc.getDocumentNo();
			moduleName = "Cash Journal";			
		}
		else if(tableID == 472)
		{			
			MMatchInv doc = new MMatchInv(getCtx(), recordID, get_Trx());
			documentNo = doc.getDocumentNo();
			moduleName = "Matched Invoice";			
		}
		else if(tableID == 473)
		{			
			MMatchPO doc = new MMatchPO(getCtx(), recordID, get_Trx());
			documentNo = doc.getDocumentNo();
			moduleName = "Matched PO";			
		}
		else if(tableID == 702)
		{			
			MRequisition doc = new MRequisition(getCtx(), recordID, get_Trx());
			documentNo = doc.getDocumentNo();
			moduleName = "Requisition";			
		}
		else if(tableID == 735)
		{			
			MAllocationHdr doc = new MAllocationHdr(getCtx(), recordID, get_Trx());
			documentNo = doc.getDocumentNo();
			moduleName = "Allocation";			
		}
		else if(tableID == 2179)
		{			
			MConsignedTransfer doc = new MConsignedTransfer(getCtx(), recordID, get_Trx());
			documentNo = doc.getDocumentNo();
			moduleName = "Consignment Transfer";			
		}
		else
		{
			String tableName = null;
			String sql = "SELECT COALESCE(w.Name, t.Name) AS ModuleName, t.TableName FROM AD_Table t " +
					"LEFT OUTER JOIN AD_Window w ON (t.AD_Window_ID = w.AD_Window_ID) " +
					"WHERE t.AD_Table_ID = ? ";
			
			PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
			ResultSet rs = null;
			try{
				pstmt.setInt(1, tableID);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					moduleName = rs.getString(1);
					tableName = rs.getString(2);
				}
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}		
			
			StringBuilder sql2 = new StringBuilder("SELECT COALESCE(DocumentNo, ' ') AS DocumentNo FROM ");
			sql2.append(tableName);
			sql2.append(" WHERE ");
			sql2.append(tableName+"_ID = ? ");
			
			pstmt = DB.prepareStatement(sql2.toString(), get_Trx());
			rs = null;
			try{
				pstmt.setInt(1, recordID);
				rs = pstmt.executeQuery();
				if(rs.next())
					documentNo = rs.getString(1);
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}		
			
		}		
		retValue.put(moduleName, documentNo);
		return retValue;
	}
}
