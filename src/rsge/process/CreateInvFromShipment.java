/**
 * 
 */
package rsge.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MBPartner;
import org.compiere.model.MConversionType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MUOMConversion;
import org.compiere.model.X_C_Invoice;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Trx;

/**
 * @author bang
 *
 */
public class CreateInvFromShipment extends SvrProcess {
	private MInOut inOut = null;
	private int mM_InOut_ID;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter param : params) {
			String name = param.getParameterName();
			if (name.equalsIgnoreCase("M_InOut_ID")) {
				mM_InOut_ID = param.getParameterAsInt();
			}else
				System.out.println(name + " unknown");
		}
		inOut = new MInOut(getCtx(), mM_InOut_ID, get_Trx());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		if(getCtx().getAD_Org_ID()==0)
			return "Don't use Organization * ";
		// TODO Auto-generated method stub
		MInvoice inv = new MInvoice(getCtx(), 0, get_Trx());
		inv.setAD_Org_ID(getCtx().getAD_Org_ID());
		inv.setAD_Client_ID(getAD_Client_ID());
		inv.setDateInvoiced(inOut.getDateAcct());
		inv.setC_Order_ID(inOut.getC_Order_ID());
		inv.setDateOrdered(inOut.getDateOrdered());
		inv.setDateAcct(inOut.getDateAcct());
		inv.setIsSOTrx(true);

		// // Set Target Document Type and Document Type
		String sql = "SELECT C_DocType_ID FROM C_DocType "
				+ "WHERE DocBaseType IN ('ARI') AND C_DocType.IsSOTrx='Y' "
				+ "AND AD_Client_ID = ? ";

		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try {
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				inv.setC_DocTypeTarget_ID(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		MBPartner mbPartner = new MBPartner(getCtx(),
				inOut.getC_BPartner_ID(), get_Trx());
		inv.setC_BPartner_ID(mbPartner.getC_BPartner_ID());
		inv.setC_BPartner_Location_ID(getBPLocID(mbPartner.getC_BPartner_ID(),
				get_Trx()));
		inv.setDescription("Shipment : "+inOut.getDocumentNo());
		if(mbPartner.getM_PriceList_ID()==0)
			return "Price List Not Found";
		inv.setM_PriceList_ID(mbPartner.getM_PriceList_ID());
		inv.setC_Currency_ID(303);
		
		
		if(mbPartner.getC_PaymentTerm_ID()==0)
			return "Payment Term Not Found";
		 inv.setC_PaymentTerm_ID(mbPartner.getC_PaymentTerm_ID());

		inv.setC_ConversionType_ID(MConversionType
				.getDefault(getAD_Client_ID()));
		if (inv.save()) {
			int taxID = getdefault(inv.getAD_Client_ID(), get_Trx());
			// Create Lines
			sql = "SELECT * FROM M_InOutLine WHERE M_InOut_ID = ? ";  
			
			PreparedStatement statement = DB.prepareStatement(sql, get_Trx());
			ResultSet resultSet2 = null;
			try {
				statement.setInt(1, inOut.getM_InOut_ID());
				resultSet2 = statement.executeQuery();
				while (resultSet2.next()) {
					MInOutLine line = new MInOutLine(getCtx(), resultSet2, get_Trx());
						MInvoiceLine il = new MInvoiceLine(inv.getCtx(), 0,
								inv.get_Trx());
						MOrderLine line2 = new MOrderLine(getCtx(), line.getC_OrderLine_ID(), get_Trx());
						// il.setClientOrg(il);
						il.setAD_Client_ID(inv.getAD_Client_ID());
						il.setAD_Org_ID(inv.getAD_Org_ID());
						il.setM_InOutLine_ID(line.getM_InOutLine_ID());
						il.setC_OrderLine_ID(line2.getC_OrderLine_ID());
						il.setC_Invoice_ID(inv.getC_Invoice_ID());
						il.setM_Product_ID(line.getM_Product_ID());
						BigDecimal movementQty = MUOMConversion.convertProductFrom (getCtx(), 
								line.getM_Product_ID(), line.getC_UOM_ID(), line.getQtyEntered());
						il.setQty(movementQty);
						MProduct mProduct = new MProduct(getCtx(), line.getM_Product_ID(), get_Trx());
						il.setC_UOM_ID(mProduct.getC_UOM_ID());
						il.setQtyInvoiced(movementQty);
						il.setQtyEntered(movementQty);
						il.setPrice(line2.getPriceList());
						il.setPriceActual(line2.getPriceActual());
						il.setPriceEntered(line2.getPriceEntered());
						il.setPriceList(line2.getPriceList());
						il.setC_Tax_ID(taxID);
						il.save();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				DB.closeResultSet(resultSet2);
				DB.closeStatement(statement);
			}
			DocumentEngine.processIt(inv, X_C_Invoice.DOCACTION_Prepare);
			inv.save();
		}
		return "No of Invoice Generated : " + inv.getDocumentNo();
	}
		public static int getBPLocID(int C_BPartner_ID, Trx trx) {
			int bpLocID = 0;
			String sql = "SELECT C_BPartner_Location_ID FROM C_BPartner_Location "
					+ "WHERE IsBillTo = 'Y' AND IsActive = 'Y' "
					+ "AND C_BPartner_ID = ? ";

			PreparedStatement pstmt = DB.prepareStatement(sql, trx);
			ResultSet rs = null;
			try {
				pstmt.setInt(1, C_BPartner_ID);
				rs = pstmt.executeQuery();
				if (rs.next())
					bpLocID = rs.getInt(1);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DB.closeResultSet(rs);
				DB.closeStatement(pstmt);
			}
			return bpLocID;
		}
		
		
	    public static int getdefault(int AD_Client_ID, Trx trx)
	    {
	    	int taxID = 0;
	    	String sql = "SELECT C_Tax_ID FROM C_Tax WHERE AD_Client_ID = ? AND IsDefault = 'Y' ";
	    	PreparedStatement pstmt = DB.prepareStatement(sql, trx);
	    	ResultSet rs = null;
	    	try{
	    		pstmt.setInt(1, AD_Client_ID);
	    		rs = pstmt.executeQuery();
	    		if(rs.next())
	    			taxID = rs.getInt(1);
	    		rs.close();
	    		pstmt.close();
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    	
	    	return taxID;
	    }
}
