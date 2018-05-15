/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MBPartner;
import org.compiere.model.MCharge;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPO;
import org.compiere.model.MUser;
import org.compiere.model.X_M_Requisition;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CompiereSystemException;
import org.compiere.util.CompiereUserException;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import rsge.model.MPurchaseRequisition;
import rsge.model.MPurchaseRequisitionLine;
import rsge.model.MRequisition;
import rsge.model.MRequisitionLine;

/**
 * @author Fanny
 * 
 */
public class GenPOFromRequisition extends SvrProcess {

	/** Org */
	private int p_AD_Org_ID = 0;
	/** Warehouse */
	private int p_M_Warehouse_ID = 0;
	/** Doc Date From */
	private Timestamp p_DateDoc_From;
	/** Doc Date To */
	private Timestamp p_DateDoc_To;
	/** Doc Date From */
	private Timestamp p_DateRequired_From;
	/** Doc Date To */
	private Timestamp p_DateRequired_To;
	/** Priority */
	private String p_PriorityRule = null;
	/** User */
	private int p_AD_User_ID = 0;
	/** Product */
	private int p_M_Product_ID = 0;
	/** Requisition */
	private int p_M_Requisition_ID = 0;
	/** Vendor */
	private int p_Vendor_ID = 0;

	/** Consolidate */
	private boolean p_ConsolidateDocument = false;

	/** Order */
	private MOrder m_order = null;
	/** Order Line */
	private MOrderLine m_orderLine = null;

	private Timestamp m_dateRequired = null;
	
	private int docOrgID = 0;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null)
				;
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = element.getParameterAsInt();
			else if (name.equals("M_Warehouse_ID"))
				p_M_Warehouse_ID = element.getParameterAsInt();
			else if (name.equals("DateDoc")) {
				p_DateDoc_From = (Timestamp) element.getParameter();
				p_DateDoc_To = (Timestamp) element.getParameter_To();
			} else if (name.equals("DateRequired")) {
				p_DateRequired_From = (Timestamp) element.getParameter();
				p_DateRequired_To = (Timestamp) element.getParameter_To();
			} else if (name.equals("PriorityRule"))
				p_PriorityRule = (String) element.getParameter();
			else if (name.equals("AD_User_ID"))
				p_AD_User_ID = element.getParameterAsInt();
			else if (name.equals("M_Product_ID"))
				p_M_Product_ID = element.getParameterAsInt();
			else if (name.equals("M_Requisition_ID"))
//			else if (name.equals("XX_PurchaseRequisition_ID"))
				p_M_Requisition_ID = element.getParameterAsInt();
			else if (name.equals("Vendor_ID"))
				p_Vendor_ID = element.getParameterAsInt();
			else if (name.equals("ConsolidateDocument"))
				p_ConsolidateDocument = "Y".equals(element.getParameter());
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
		docOrgID = getCtx().getAD_Org_ID();
	} // prepare

	
	
	/**
	 * Process
	 * 
	 * @return info
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		if(docOrgID==0)
			return "No organization found. Please select organization in login screen";
		
		if (p_M_Requisition_ID != 0) {
			log.info("M_Requisition_ID=" + p_M_Requisition_ID);
			MRequisition req = new MRequisition(getCtx(), p_M_Requisition_ID,
					get_TrxName());
//			MPurchaseRequisition req = new MPurchaseRequisition(getCtx(), p_M_Requisition_ID,
//					get_TrxName());
			m_dateRequired = req.getDateRequired();
			if (!X_M_Requisition.DOCSTATUS_Completed.equals(req.getDocStatus()))
				throw new CompiereUserException("@DocStatus@ = "
						+ req.getDocStatus());
			MRequisitionLine[] lines = req.getLines(true);
//			MPurchaseRequisitionLine[] lines = req.getLines(true);
			for (MRequisitionLine element : lines) {
//			for (MPurchaseRequisitionLine element : lines) {
				if (element.getC_OrderLine_ID() == 0)
					process(element);
			}
			closeOrder();
			return "";
		} // single Requisition

		//
		log.info("AD_Org_ID=" + p_AD_Org_ID + ", M_Warehouse_ID="
				+ p_M_Warehouse_ID + ", DateDoc=" + p_DateDoc_From + "/"
				+ p_DateDoc_To + ", DateRequired=" + p_DateRequired_From + "/"
				+ p_DateRequired_To + ", PriorityRule=" + p_PriorityRule
				+ ", M_Product_ID=" + p_M_Product_ID + ", ConsolidateDocument"
				+ p_ConsolidateDocument);

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM M_RequisitionLine rl ")
				.append("WHERE rl.C_OrderLine_ID IS NULL");
		if (p_M_Product_ID != 0)
			sql.append(" AND M_Product_ID=?");
		// Requisition Header
		sql.append(
				" AND EXISTS (SELECT * FROM M_Requisition r WHERE rl.M_Requisition_ID=r.M_Requisition_ID")
				.append(" AND r.DocStatus='CO'");
		if (p_AD_Org_ID != 0)
			sql.append(" AND r.AD_Org_ID=?");
		if (p_M_Warehouse_ID != 0)
			sql.append(" AND r.M_Warehouse_ID=?");
		//
		if ((p_DateDoc_From != null) && (p_DateDoc_To != null))
			sql.append(" AND r.DateDoc BETWEEN ? AND ?");
		else if (p_DateDoc_From != null)
			sql.append(" AND r.DateDoc >= ?");
		else if (p_DateDoc_To != null)
			sql.append(" AND r.DateDoc <= ?");
		//
		if ((p_DateRequired_From != null) && (p_DateRequired_To != null))
			sql.append(" AND r.DateRequired BETWEEN ? AND ?");
		else if (p_DateRequired_From != null)
			sql.append(" AND r.DateRequired >= ?");
		else if (p_DateRequired_To != null)
			sql.append(" AND r.DateRequired <= ?");
		//
		if (p_PriorityRule != null)
			sql.append(" AND r.PriorityRule >= ?");

		//
		sql.append(") ORDER BY ");
		if (!p_ConsolidateDocument)
			sql.append("M_Requisition_ID, ");
		sql.append("M_Product_ID, C_Charge_ID, M_AttributeSetInstance_ID");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			int index = 1;
			if (p_M_Product_ID != 0) {
				pstmt.setInt(index++, p_M_Product_ID);
			}
			if (p_AD_Org_ID != 0) {
				pstmt.setInt(index++, p_AD_Org_ID);
			}
			if (p_M_Warehouse_ID != 0)
				pstmt.setInt(index++, p_M_Warehouse_ID);
			if ((p_DateDoc_From != null) && (p_DateDoc_To != null)) {
				pstmt.setTimestamp(index++, p_DateDoc_From);
				pstmt.setTimestamp(index++, p_DateDoc_To);
			} else if (p_DateDoc_From != null)
				pstmt.setTimestamp(index++, p_DateDoc_From);
			else if (p_DateDoc_To != null)
				pstmt.setTimestamp(index++, p_DateDoc_To);
			if ((p_DateRequired_From != null) && (p_DateRequired_To != null)) {
				pstmt.setTimestamp(index++, p_DateRequired_From);
				pstmt.setTimestamp(index++, p_DateRequired_To);
			} else if (p_DateRequired_From != null)
				pstmt.setTimestamp(index++, p_DateRequired_From);
			else if (p_DateRequired_To != null)
				pstmt.setTimestamp(index++, p_DateRequired_To);
			if (p_PriorityRule != null)
				pstmt.setString(index++, p_PriorityRule);
			rs = pstmt.executeQuery();
			int noLine = 0;
			while (rs.next()) {
				noLine = noLine + 1;
				MRequisitionLine rLine = new MRequisitionLine(getCtx(), rs,
						get_TrxName());
				MRequisition req = new MRequisition(getCtx(),
						rLine.getM_Requisition_ID(), get_TrxName());
//				MPurchaseRequisitionLine rLine = new MPurchaseRequisitionLine(getCtx(), rs,
//						get_TrxName());
//				MPurchaseRequisition req = new MPurchaseRequisition(getCtx(),
//						rLine.getXX_PurchaseRequisition_ID(), get_TrxName());
				m_dateRequired = req.getDateRequired();
				process(rLine);
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, sql.toString(), e);
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		closeOrder();
		return "Process Complete";
	} // doit

	private int m_M_Requisition_ID = 0;
	private int m_M_Product_ID = 0;
	private int m_M_AttributeSetInstance_ID = 0;
	/** BPartner */
	private MBPartner m_bpartner = null;

	/**
	 * Process Line
	 * 
	 * @param rLine
	 *            request line
	 * @throws Exception
	 */
	private void process(MRequisitionLine rLine) throws Exception {
//	private void process(MPurchaseRequisitionLine rLine) throws Exception {
		if ((rLine.getM_Product_ID() == 0) && (rLine.getC_Charge_ID() == 0)) {
			log.warning("Ignored Line" + rLine.getLine() + " "
					+ rLine.getDescription() + " - " + rLine.getLineNetAmt());
			return;
		}

		if (!p_ConsolidateDocument
				&& (rLine.getM_Requisition_ID() != m_M_Requisition_ID))
//				&& (rLine.getXX_PurchaseRequisition_ID() != m_M_Requisition_ID))
			closeOrder();
		if ((m_orderLine == null)
				|| (rLine.getM_Product_ID() != m_M_Product_ID)
				|| (rLine.getM_AttributeSetInstance_ID() != m_M_AttributeSetInstance_ID)
				|| (rLine.getC_Charge_ID() != 0)) // single line per charge
			newLine(rLine);

		// Update Order Line
		m_orderLine.setQty(m_orderLine.getQtyOrdered().add(rLine.getQty()));
		// Update Requisition Line
		rLine.setC_OrderLine_ID(m_orderLine.getC_OrderLine_ID());
		if (!rLine.save())
			throw new CompiereSystemException("Cannot update Request Line");
	} // process

	/**
	 * Create new Order
	 * 
	 * @param rLine
	 *            request line
	 * @param C_BPartner_ID
	 *            b.partner
	 * @throws Exception
	 */
	private void newOrder(MRequisitionLine rLine, int C_BPartner_ID)
//	private void newOrder(MPurchaseRequisitionLine rLine, int C_BPartner_ID)
			throws Exception {
		if (m_order != null)
			closeOrder();
		// BPartner
		if ((m_bpartner == null)
				|| (C_BPartner_ID != m_bpartner.getC_BPartner_ID()))
			m_bpartner = new MBPartner(getCtx(), C_BPartner_ID, null);

		// Order
		m_order = new MOrder(getCtx(), 0, get_TrxName());
		m_order.setAD_Org_ID(docOrgID);
		MRequisition requisition = new MRequisition(getCtx(),
				rLine.getM_Requisition_ID(), get_Trx());
		m_order.setAD_OrgTrx_ID(requisition.getAD_Org_ID());
		m_order.setIsSOTrx(false);
		m_order.setC_DocTypeTarget_ID();
		m_order.setBPartner(m_bpartner);
		m_order.setDescription(requisition.getDescription());
		m_order.setSalesRep_ID(p_AD_User_ID);
		if (!p_ConsolidateDocument)
			m_order.setDatePromised(m_dateRequired);

		// default po document type
		if (!p_ConsolidateDocument)
			m_order.setDescription(Msg.getElement(getCtx(), "M_Requisition_ID")
					+ ": " + rLine.getParent().getDocumentNo() + " " +
							"\n User Request : "+new MUser(getCtx(), requisition.getCreatedBy(), get_Trx()).getName());

		// Prepare Save
		m_M_Requisition_ID = rLine.getM_Requisition_ID();
		if (!m_order.save())
			throw new CompiereSystemException("Cannot save Order");
	} // newOrder

	/**
	 * Close Order
	 * 
	 * @throws Exception
	 */
	private void closeOrder() throws Exception {
		closeOrderLine();
		if (m_order != null) {
			// Set the Date Promised to the greatest of all the PO lines
			// if consolidate Document flag is true
			if (p_ConsolidateDocument) {
				Timestamp datePromised = null;
				MOrderLine[] lines = m_order.getLines();
				for (MOrderLine element : lines) {
					if (datePromised == null)
						datePromised = element.getDatePromised();
					else if (datePromised.before(element.getDatePromised()))
						datePromised = element.getDatePromised();
				}
				m_order.setDatePromised(datePromised);
				if (!m_order.save())
					throw new CompiereSystemException("Cannot save Order");
			}
			m_order.load(get_TrxName());
			addLog(0, null, m_order.getGrandTotal(), m_order.getDocumentNo());
		}
		m_order = null;
	} // closeOrder

	private void closeOrderLine() throws CompiereSystemException {
		if (m_orderLine != null) {
			if (!m_orderLine.save() || !m_orderLine.updateHeaderTax())
				throw new CompiereSystemException("Cannot update Order Line");
		}
		m_orderLine = null;
	}

	/**
	 * New Order Line (different Product)
	 * 
	 * @param rLine
	 *            request line
	 * @throws Exception
	 */
	private void newLine(MRequisitionLine rLine) throws Exception {
//	private void newLine(MPurchaseRequisitionLine rLine) throws Exception {
		closeOrderLine();
		MProduct product = null;

		// Get Business Partner from Line level
		int C_BPartner_ID = 0;
		if (C_BPartner_ID == 0) {
			MRequisition r = new MRequisition(getCtx(),
					rLine.getM_Requisition_ID(), get_Trx());
//			MPurchaseRequisition r = new MPurchaseRequisition(getCtx(),
//					rLine.getXX_PurchaseRequisition_ID(), get_Trx());
			if (C_BPartner_ID == 0 && p_Vendor_ID != 0)
				C_BPartner_ID = p_Vendor_ID;
		}
		if (C_BPartner_ID != 0) {
			if (rLine.getM_Product_ID() != 0)
				product = MProduct.get(getCtx(), rLine.getM_Product_ID());
		} else if (rLine.getC_Charge_ID() != 0) {
			MCharge charge = MCharge.get(getCtx(), rLine.getC_Charge_ID());
			C_BPartner_ID = charge.getC_BPartner_ID();
			if (C_BPartner_ID == 0)
				throw new CompiereUserException("No Vendor for Charge "
						+ charge.getName());
		} else {
			// Find Vendor from Produt
			product = MProduct.get(getCtx(), rLine.getM_Product_ID());
			MProductPO[] ppos = MProductPO.getOfProduct(getCtx(),
					product.getM_Product_ID(), null);
			for (MProductPO element : ppos) {
				if (element.isCurrentVendor()
						&& (element.getC_BPartner_ID() != 0)) {
					C_BPartner_ID = element.getC_BPartner_ID();
					break;
				}
			}
			if ((C_BPartner_ID == 0) && (ppos.length > 0))
				C_BPartner_ID = ppos[0].getC_BPartner_ID();
			if (C_BPartner_ID == 0)
				throw new CompiereUserException("No Vendor for "
						+ product.getName());
		}

		// New Order - Different Vendor
		if ((m_order == null) || (m_order.getC_BPartner_ID() != C_BPartner_ID))
			newOrder(rLine,  C_BPartner_ID);

		// No Order Line
		m_orderLine = new MOrderLine(m_order);
		m_orderLine.setAD_OrgTrx_ID(rLine.getAD_Org_ID());
		m_orderLine.setDescription(rLine.getDescription());
		if (product != null) {
			m_orderLine.setProduct(product);
			m_orderLine.setM_AttributeSetInstance_ID(rLine
					.getM_AttributeSetInstance_ID());
			m_orderLine.setPriceActual(rLine.getPriceActual());
			m_orderLine.setPriceEntered(rLine.getPriceActual());
		} else {
			m_orderLine.setC_Charge_ID(rLine.getC_Charge_ID());
			m_orderLine.setPriceActual(rLine.getPriceActual());
			m_orderLine.setPriceEntered(rLine.getPriceActual());
		}
		MRequisition req = new MRequisition(getCtx(),
				rLine.getM_Requisition_ID(), get_Trx());
//		MPurchaseRequisition req = new MPurchaseRequisition(getCtx(),
//				rLine.getXX_PurchaseRequisition_ID(), get_Trx());
		m_orderLine.setDatePromised(m_dateRequired);

		// Prepare Save
		m_M_Product_ID = rLine.getM_Product_ID();
		m_M_AttributeSetInstance_ID = rLine.getM_AttributeSetInstance_ID();
		if (!m_orderLine.save() || !m_orderLine.updateHeaderTax())
			throw new CompiereSystemException("Cannot save Order Line");
	} // newLine

} // RequisitionPOCreate