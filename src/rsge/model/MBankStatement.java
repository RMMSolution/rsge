package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

public class MBankStatement extends org.compiere.model.MBankStatement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MBankStatement(Ctx ctx, int C_BankStatement_ID, Trx trx) {
		super(ctx, C_BankStatement_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MBankStatement(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
    /** Set Document No.
    @param DocumentNo Document sequence number of the document */
    public void setDocumentNo (String DocumentNo)
    {
        set_ValueNoCheck ("DocumentNo", DocumentNo);
        
    }
    
    /** Get Document No.
    @return Document sequence number of the document */
    public String getDocumentNo() 
    {
        return (String)get_Value("DocumentNo");
        
    }

}
