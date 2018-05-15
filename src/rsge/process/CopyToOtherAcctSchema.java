package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class CopyToOtherAcctSchema extends SvrProcess {

	private int				p_GL_Journal_ID = 0;
	private int				p_C_AcctSchema_ID = 0;
	private MJournal		orgJournal = null;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] params = getParameter();
		for(ProcessInfoParameter param : params){
			String name = param.getParameterName();			
			if(name.equalsIgnoreCase("GL_Journal_ID"))
				p_GL_Journal_ID = param.getParameterAsInt();
			else if(name.equalsIgnoreCase("C_AcctSchema_ID"))
				p_C_AcctSchema_ID = param.getParameterAsInt();
			else
				System.out.println("Param " + name + " not found...");
			}
		
		orgJournal = new MJournal(getCtx(), p_GL_Journal_ID, get_Trx());
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		MJournal newJournal = new MJournal(orgJournal);
		newJournal.setDateDoc(orgJournal.getDateDoc());
		newJournal.setDateAcct(orgJournal.getDateAcct());
		newJournal.setC_Period_ID(orgJournal.getC_Period_ID());
		newJournal.save();
		
		ArrayList<MJournalLine> journalLine = new ArrayList<MJournalLine>();
		String sql = "SELECT * FROM GL_JournalLine WHERE GL_Journal_ID = ? ";
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, p_GL_Journal_ID);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MJournalLine jl = new MJournalLine(getCtx(), rs, get_Trx());
				journalLine.add(jl);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		for(MJournalLine line : journalLine)
		{
			MJournalLine newLine = new MJournalLine(newJournal);
			MAcctSchema orgSchema = new MAcctSchema(getCtx(), newJournal.getC_AcctSchema_ID(), get_Trx());
			int accountID = 0;
			MAccount orgAccount = new MAccount(getCtx(), line.getC_ValidCombination_ID(), get_Trx());
			sql = "SELECT aem.TargetAccount_ID FROM XX_AccountElementMatch aem " +
					"INNER JOIN C_AcctSchema_Element ase ON (aem.C_Element_ID = ase.C_Element_ID) " +
					"WHERE aem.Account_ID = ? " +
					"AND ase.C_AcctSchema_ID = ? ";
			pstmt = DB.prepareStatement(sql, get_Trx());
			rs = null;
			try{
				pstmt.setInt(1, orgAccount.getAccount_ID());
				pstmt.setInt(2, orgAccount.getC_AcctSchema_ID());
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					accountID = rs.getInt(1);
				}
				rs.close();
				pstmt.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			MAccount account = MAccount.get(getCtx(), getAD_Client_ID(), newLine.getAD_Org_ID(), p_C_AcctSchema_ID, accountID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			newLine.setC_ValidCombination_ID(account);
			newLine.setAmtAcctCr(line.getAmtAcctCr());
			newLine.setAmtAcctDr(line.getAmtAcctDr());
			newLine.setAmtSourceCr(line.getAmtSourceCr());
			newLine.setAmtSourceDr(line.getAmtSourceDr());
			newLine.setC_ConversionType_ID(line.getC_ConversionType_ID());
			newLine.setC_Currency_ID(line.getC_Currency_ID());
			newLine.setC_UOM_ID(line.getC_UOM_ID());
			newLine.setCurrencyRate(line.getCurrencyRate());
			newLine.setIsGenerated(true);
			newLine.setLine(line.getLine());
			newLine.setDescription(account.getDescription());
			newLine.save();
		}
		return null;
	}

}
