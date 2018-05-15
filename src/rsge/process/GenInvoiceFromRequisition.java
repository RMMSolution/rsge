/**
 * 
 */
package rsge.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MBPartner;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MUser;
import org.compiere.model.X_M_Requisition;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CompiereSystemException;
import org.compiere.util.CompiereUserException;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import rsge.model.MInvoice;
import rsge.model.MOrder;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;

/**
 * @author Fanny
 *
 */
public class GenInvoiceFromRequisition extends SvrProcess {

	/** Document No 		*/
	private String		p_DocumentNo = null;
	/**	Doc Date From		*/
	private Timestamp	p_DateDoc;
	/** Requisition			*/
	private int 		p_M_Requisition_ID = 0;
	/** Vendor				*/
	private int 		p_C_BPartner_ID = 0;
	/** Order				*/
	private MOrder		m_order = null;
	/** Order Line			*/
	private MOrderLine	m_orderLine = null;
	
	/** Invoice				*/
	private MInvoice	m_invoice = null;
	/** Invoice Line		*/
	private MInvoiceLine m_invoiceLine = null;

	private Timestamp   m_dateRequired = null;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null)
				;
//			else if (name.equals("M_Warehouse_ID"))
//				p_M_Warehouse_ID = element.getParameterAsInt();
			else if (name.equals("Vendor_ID"))
				p_C_BPartner_ID = element.getParameterAsInt();
			else if(name.equals("DocumentNo"))
				p_DocumentNo = (String) element.getParameter();
			else if (name.equals("DateDoc"))
			{
				p_DateDoc = (Timestamp)element.getParameter();
			}
			else if (name.equals("M_Requisition_ID"))
				p_M_Requisition_ID = element.getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}	//	prepare

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// Generate PO
		log.info("M_Requisition_ID=" + p_M_Requisition_ID);
		MRequisition req = new MRequisition(getCtx(), p_M_Requisition_ID, get_TrxName());
		m_dateRequired = req.getDateRequired();
		if (!X_M_Requisition.DOCSTATUS_Completed.equals(req.getDocStatus()))
			throw new CompiereUserException("@DocStatus@ = " + req.getDocStatus());
		MRequisitionLine[] lines = req.getLines(true);
		for (MRequisitionLine element : lines) {
			if (element.getC_OrderLine_ID() == 0)
				process (element);
		}
		// Process PO
		DocumentEngine.processIt(m_order, "CO");
		m_order.save();
		// Direct Update Document Status to Complete - Compiere Bug
		String update = "UPDATE C_Order SET DocAction = 'CL', DocStatus = 'CO', Processing = 'N', Processed = 'Y' " +
				"WHERE C_Order_ID = ? ";
		DB.executeUpdate(get_Trx(), update, m_order.getC_Order_ID());
		
		// Generate Invoice from PO
		// Generate Invoice header
		
		int docTypeID = getDocType();
		m_invoice = new MInvoice(m_order, docTypeID, p_DateDoc);
		m_invoice.setDocumentNo(p_DocumentNo);
		m_invoice.setC_Order_ID(m_order.getC_Order_ID());
		m_invoice.save();
		// Generate invoice line
		MOrderLine[] olines = m_order.getLines();
		for(MOrderLine line : olines)
		{
			m_invoiceLine = new MInvoiceLine(m_invoice);
			m_invoiceLine.setOrderLine(line);
			BigDecimal qty = line.getQtyOrdered();
			if(qty.compareTo(BigDecimal.ZERO)==0)
			{
				qty = getQty(line.getC_OrderLine_ID());
			}
			m_invoiceLine.setQty(qty);
			m_invoiceLine.save();
			closeInvoiceLine();
		}
		
		DocumentEngine.processIt(m_invoice, "PR");
		m_invoice.save();
		closeInvoice();
		return null;
	}	
	
	private BigDecimal getQty(int C_OrderLine_ID)
	{
		BigDecimal qty = BigDecimal.ZERO;
		String sql = "SELECT Qty FROM M_RequisitionLine " +
				"WHERE C_OrderLine_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, C_OrderLine_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				qty = rs.getBigDecimal(1);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}		
		return qty;
	}
	
	private int getDocType()
	{
		int ID = 0;
		String sql = "SELECT C_DocType_ID FROM C_DocType " +
				"WHERE AD_Client_ID = ? " +
				"AND IsReturnTrx = 'N' AND IsSOTrx = 'N' AND DocBaseType = 'API' ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				ID = rs.getInt(1);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}		
		return ID;
	}
	
	private int 		m_M_Requisition_ID = 0;
	private int 		m_M_Product_ID = 0;
	private int			m_M_AttributeSetInstance_ID = 0;
	/** BPartner				*/
	private MBPartner	m_bpartner = null;

	/**
	 * 	Process Line
	 *	@param rLine request line
	 * 	@throws Exception
	 */
	private void process (MRequisitionLine rLine) throws Exception
	{
		if ((rLine.getM_Product_ID() == 0) && (rLine.getC_Charge_ID() == 0))
		{
			log.warning("Ignored Line" + rLine.getLine()
				+ " " + rLine.getDescription()
				+ " - " + rLine.getLineNetAmt());
			return;
		}

		if (rLine.getM_Requisition_ID() != m_M_Requisition_ID)
			closeOrder();
		if ((m_orderLine == null)
			|| (rLine.getM_Product_ID() != m_M_Product_ID)
			|| (rLine.getM_AttributeSetInstance_ID() != m_M_AttributeSetInstance_ID)
			|| (rLine.getC_Charge_ID() != 0))		//	single line per charge
			newLine(rLine);

		//	Update Order Line
		m_orderLine.setQty(m_orderLine.getQtyOrdered().add(rLine.getQty()));
		//	Update Requisition Line
		rLine.setC_OrderLine_ID(m_orderLine.getC_OrderLine_ID());
		if (!rLine.save())
			throw new CompiereSystemException("Cannot update Request Line");
	}	//	process
	
	/**
	 * 	Create new Order
	 *	@param rLine request line
	 *	@param C_BPartner_ID b.partner
	 * 	@throws Exception
	 */
	private void newOrder(MRequisitionLine rLine, int C_BPartner_ID) throws Exception
	{
		if (m_order != null)
			closeOrder();
		//	BPartner
		if ((m_bpartner == null) || (C_BPartner_ID != m_bpartner.getC_BPartner_ID()))
			m_bpartner = new MBPartner (getCtx(), C_BPartner_ID, null);

		MUser u = new MUser(getCtx(), getAD_User_ID(), get_Trx());
		if(u.getName().equalsIgnoreCase("SuperUser"))
		{
			throw new CompiereSystemException("Cannot use SuperUser");
		}
		//	Order
		m_order = new MOrder(getCtx(), 0, get_TrxName());
		m_order.setIsSOTrx(false);
		m_order.setC_DocTypeTarget_ID();
		m_order.setBPartner(m_bpartner);
		m_order.setDatePromised(m_dateRequired);
		//	default po document type
		m_order.setDescription(Msg.getElement(getCtx(), "M_Requisition_ID")
				+ ": " + rLine.getParent().getDocumentNo());

		//	Prepare Save
		m_M_Requisition_ID = rLine.getM_Requisition_ID();
		if (!m_order.save())
			throw new CompiereSystemException("Cannot save Order");
	}	//	newOrder

	/**
	 * 	New Order Line (different Product)
	 *	@param rLine request line
	 * 	@throws Exception
	 */
	private void newLine(MRequisitionLine rLine) throws Exception
	{
		closeOrderLine();
		MProduct product = null;

		//	Get Business Partner from Line level
		int C_BPartner_ID = p_C_BPartner_ID;
		if(rLine.getM_Product_ID()!=0)
			product = MProduct.get(getCtx(), rLine.getM_Product_ID());

		//	New Order - Different Vendor
		if ((m_order == null)
			|| (m_order.getC_BPartner_ID() != C_BPartner_ID))
			newOrder(rLine, C_BPartner_ID);


		//	No Order Line
		m_orderLine = new MOrderLine(m_order);	
		if (product != null)
		{
			m_orderLine.setProduct(product);
			m_orderLine.setM_AttributeSetInstance_ID(rLine.getM_AttributeSetInstance_ID());
			m_orderLine.setPriceActual(rLine.getPriceActual());
			m_orderLine.setPriceEntered(rLine.getPriceActual());
		}
		else
		{
			m_orderLine.setC_Charge_ID(rLine.getC_Charge_ID());
			m_orderLine.setPriceActual(rLine.getPriceActual());
			m_orderLine.setPriceEntered(rLine.getPriceActual());
		}
		m_orderLine.setAD_Org_ID(rLine.getAD_Org_ID());
		m_orderLine.setDatePromised(m_dateRequired);

		//	Prepare Save
		m_M_Product_ID = rLine.getM_Product_ID();
		m_M_AttributeSetInstance_ID = rLine.getM_AttributeSetInstance_ID();
		if (!m_orderLine.save() || !m_orderLine.updateHeaderTax())
			throw new CompiereSystemException("Cannot save Order Line");
	}	//	newLine
	
	/**
	 * 	Close Order
	 * 	@throws Exception
	 */
	private void closeOrder() throws Exception
	{
		closeOrderLine();
		if (m_order != null)
		{
			m_order.load(get_TrxName());
			addLog(0, null, m_order.getGrandTotal(), m_order.getDocumentNo());
		}
		m_order = null;
	}	//	closeOrder
	
	/**
	 * 	Close Invoice
	 * 	@throws Exception
	 */
	private void closeInvoice() throws Exception
	{
		closeOrderLine();
		if (m_invoice != null)
		{
			m_invoice.load(get_TrxName());
			addLog(0, null, m_invoice.getGrandTotal(), m_invoice.getDocumentNo());
		}
		m_order = null;
	}	//	closeOrder


	private void closeOrderLine() throws CompiereSystemException {
		if (m_orderLine != null)
		{
			if (!m_orderLine.save() || !m_orderLine.updateHeaderTax())
				throw new CompiereSystemException("Cannot update Order Line");
		}
		m_orderLine = null;
	}
	
	private void closeInvoiceLine() throws CompiereSystemException {
		if (m_invoiceLine != null)
		{
			if (!m_invoiceLine.save() || !m_invoiceLine.updateHeaderTax())
				throw new CompiereSystemException("Cannot update Invoice Line");
		}
		m_invoiceLine = null;
	}

}
