/**
 * 
 */
package rsge.acct;

import java.math.BigDecimal;

import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import rsge.model.MAllocationLine;
import org.compiere.util.QueryUtil;

/**
 *	Allocation Line
 *	
 *  @author Jorg Janke
 *  @version $Id: DocLine_Allocation.java 8780 2010-05-19 23:08:57Z nnayak $
 */
public class DocLine_Allocation extends DocLine
{
	/** Conversion Type				*/
	private int					m_C_ConversionType_ID = -1;
	/** Parent					*/
	private Doc					m_doc = null;
	
	
	/**
	 * 	DocLine_Allocation
	 *	@param line allocation line
	 *	@param doc header
	 */
	public DocLine_Allocation (MAllocationLine line, Doc doc)
	{
		super (line, doc);
		// Default Compiere 
		m_C_Payment_ID = line.getC_Payment_ID();
		m_C_CashLine_ID = line.getC_CashLine_ID();
		m_C_Invoice_ID = line.getC_Invoice_ID();
		m_C_Order_ID = line.getC_Order_ID();
		//
		setAmount(line.getAmount());
		m_DiscountAmt = line.getDiscountAmt();
		m_WriteOffAmt = line.getWriteOffAmt();
		m_OverUnderAmt = line.getOverUnderAmt();
		
		// RSGE
		setM_XX_DisRealizationLineInv_ID(line.getXX_DisRealizationLineInv_ID());
	}	//	DocLine_Allocation

	private int 		m_C_Invoice_ID;
	private int 		m_C_Payment_ID;
	private int 		m_C_CashLine_ID;
	private int 		m_C_Order_ID;
	private BigDecimal	m_DiscountAmt; 
	private BigDecimal	m_WriteOffAmt; 
	private BigDecimal	m_OverUnderAmt; 
	
	private int			m_XX_DisRealizationLineInv_ID = 0;
	
	/**
	 * 	Get Invoice C_Currency_ID
	 *	@return 0 if no invoice -1 if not found
	 */
	public int getInvoiceC_Currency_ID()
	{
		if (m_C_Invoice_ID == 0)
			return 0;
		String sql = "SELECT C_Currency_ID "
			+ "FROM C_Invoice "
			+ "WHERE C_Invoice_ID=?";
		return  QueryUtil.getSQLValue(getTrx(), sql, m_C_Invoice_ID);
	}	//	getInvoiceC_Currency_ID

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("DocLine_Allocation[");
		sb.append(get_ID())
			.append(",Amt=").append(getAmtSource())
			.append(",Discount=").append(getDiscountAmt())
			.append(",WriteOff=").append(getWriteOffAmt())
			.append(",OverUnderAmt=").append(getOverUnderAmt())
			.append(" - C_Payment_ID=").append(m_C_Payment_ID)
			.append(",C_CashLine_ID=").append(m_C_CashLine_ID)
			.append(",C_Invoice_ID=").append(m_C_Invoice_ID)
			.append("]");
		return sb.toString ();
	}	//	toString
	
	
	/**
	 * @return Returns the c_Order_ID.
	 */
	public int getC_Order_ID ()
	{
		return m_C_Order_ID;
	}
	/**
	 * @return Returns the discountAmt.
	 */
	public BigDecimal getDiscountAmt ()
	{
		return m_DiscountAmt;
	}
	/**
	 * @return Returns the overUnderAmt.
	 */
	public BigDecimal getOverUnderAmt ()
	{
		return m_OverUnderAmt;
	}
	/**
	 * @return Returns the writeOffAmt.
	 */
	public BigDecimal getWriteOffAmt ()
	{
		return m_WriteOffAmt;
	}
	/**
	 * @return Returns the c_CashLine_ID.
	 */
	public int getC_CashLine_ID ()
	{
		return m_C_CashLine_ID;
	}
	/**
	 * @return Returns the c_Invoice_ID.
	 */
	public int getC_Invoice_ID ()
	{
		return m_C_Invoice_ID;
	}
	/**
	 * @return Returns the c_Payment_ID.
	 */
	public int getC_Payment_ID ()
	{
		return m_C_Payment_ID;
	}
	
	/**
	 *  Get Conversion Type
	 *  @return C_ConversionType_ID
	 */
	public int getC_ConversionType_ID ()
	{
		if (m_C_ConversionType_ID == -1)
		{
			int index = p_po.get_ColumnIndex("C_ConversionType_ID");
			if (index != -1)
			{
				Integer ii = (Integer)p_po.get_Value(index);
				if (ii != null)
					m_C_ConversionType_ID = ii.intValue();
			}
			if (m_C_ConversionType_ID <= 0)
				m_C_ConversionType_ID = m_doc.getC_ConversionType_ID();
		}
		return m_C_ConversionType_ID;
	}   //  getC_ConversionType_ID

	/**
	 * 	Set C_ConversionType_ID
	 *	@param C_ConversionType_ID id
	 */
	protected void setC_ConversionType_ID(int C_ConversionType_ID)
	{
		m_C_ConversionType_ID = C_ConversionType_ID;
	}	//	setC_ConversionType_ID

	public int getM_XX_DisRealizationLineInv_ID() {
		return m_XX_DisRealizationLineInv_ID;
	}

	public void setM_XX_DisRealizationLineInv_ID(
			int m_XX_DisRealizationLineInv_ID) {
		this.m_XX_DisRealizationLineInv_ID = m_XX_DisRealizationLineInv_ID;
	}
	
}	//	DocLine_Allocation

