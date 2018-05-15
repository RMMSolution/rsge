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
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MProduct;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import rsge.model.MInOutLine;
import rsge.model.MOrder;
import rsge.model.MOrderLine;
import rsge.model.MProductAcct;
import rsge.model.MTransferReceipt;
import rsge.model.MTransferReceiptLine;

/**
 * @author Fanny
 *
 */
public class Doc_ReceiptTransfer extends Doc {

	/**
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trx
	 */
	public Doc_ReceiptTransfer(MAcctSchema[] ass, ResultSet rs, Trx trx) {
		super(ass, MTransferReceipt.class, rs, DocBaseType.DOCBASETYPE_ReceiptTransfer, trx);
		// TODO Auto-generated constructor stub
	}
	
	private MTransferReceipt 		tr = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	public String loadDocumentDetails() {
		tr = (MTransferReceipt) getPO();
		// Set Date Doc 
		setDateDoc(tr.getDateTrx());		
		setDateAcct(tr.getDateTrx());
		MInOutLine iol = new MInOutLine(getCtx(), tr.getM_InOutLine_ID(), getTrx());
		MOrderLine ol = new MOrderLine(getCtx(), iol.getC_OrderLine_ID(), getTrx());
		MOrder o = new MOrder(getCtx(), ol.getC_Order_ID(), getTrx());
		setC_Currency_ID(o.getC_Currency_ID());
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
	 *  XRT	
	 *  <pre>
	 *  XRT
	 *
	 *  </pre>
	 *  @param as accounting schema
	 *  @return Fact
	 */	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		 ArrayList<Fact> facts = new ArrayList<Fact>();
		//  create Fact Header
		Fact fact = new Fact (this, as, Fact.POST_Actual);				
		DocLine_ReceiptTransfer[] d_lines = loadLines(as);
		int size = d_lines.length;
		BigDecimal totalCr = BigDecimal.ZERO;
		for(int i=0; i<size; i++)
		{
			fact.createLine(d_lines[i], d_lines[i].getAccount(), as.getC_Currency_ID(), d_lines[i].getP_Amount(), null);
			totalCr = totalCr.add(d_lines[i].getP_Amount());
		}		
		MProduct p = new MProduct(tr.getCtx(), tr.getM_Product_ID(), tr.get_Trx());
		MProductAcct pa = MProductAcct.get(p.getM_Product_ID(), as);
		MAccount vc = null;
		if(p.getProductType().equals(X_M_Product.PRODUCTTYPE_Item)
				|| p.getProductType().equals(X_M_Product.PRODUCTTYPE_Resource))
			vc = MAccount.get(tr.getCtx(), pa.getP_Asset_Acct());
		else
			vc = MAccount.get(tr.getCtx(), pa.getP_Expense_Acct());

		fact.createLine(null, vc, as.getC_Currency_ID(), null, totalCr);				
		facts.add(fact);
		return facts;	
	}

	private DocLine_ReceiptTransfer[] loadLines (MAcctSchema as)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		//
		MTransferReceiptLine[] lines = MTransferReceiptLine.getLines(tr);
		for (int i = 0; i < lines.length; i++)
		{
			MTransferReceiptLine line = lines[i];
			DocLine_ReceiptTransfer docLine = new DocLine_ReceiptTransfer(line, this, as);
			log.fine(docLine.toString());
			list.add(docLine);
			}			
			//	Convert to Array
			DocLine_ReceiptTransfer[] drt = new DocLine_ReceiptTransfer[list.size()];
			list.toArray(drt);

			//	Return Array
			return drt;
		}	//	loadLines

}
