package rsge.model;

import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.Trx;

public class MCashBook extends org.compiere.model.MCashBook {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MCashBook(Ctx ctx, int C_CashBook_ID, Trx trx) {
		super(ctx, C_CashBook_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MCashBook(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
    public void setCashBookSetAmt (java.math.BigDecimal CashBookSetAmt)
    {
        set_Value ("CashBookSetAmt", CashBookSetAmt);
        
    }
    
    public java.math.BigDecimal getCashBookSetAmt() 
    {
        return get_ValueAsBigDecimal("CashBookSetAmt");
        
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
