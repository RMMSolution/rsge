/**
 * 
 */
package rsge.acct;

import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.framework.PO;
import org.compiere.model.MMovementLine;

/**
 * @author Fanny
 *
 */
public class DocLine_MovementLine extends DocLine {

	
	protected MMovementLine ml = null;
	
	/**
	 * @param po
	 * @param doc
	 */
	public DocLine_MovementLine(PO po, Doc doc) {
		super(po, doc);		
		ml = (MMovementLine) po;
	}
	
	public MMovementLine getMovementLine()
	{
		return ml;
	}

}
