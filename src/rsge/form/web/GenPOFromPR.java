/**
 * 
 */
package rsge.form.web;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.compiere.common.QueryVO;
import org.compiere.intf.ComponentImplIntf;
import org.compiere.intf.WindowImplIntf;
import org.compiere.layout.Box;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBPartner;
import org.compiere.model.MClientInfo;
import org.compiere.model.MProduct;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.X_C_Order;
import org.compiere.util.CLogger;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.compiere.vos.ChangeVO;
import org.compiere.vos.ResponseVO;
import org.compiere.vos.UWindowID;
import org.compiere.vos.WindowCtx;
import org.compiere.vos.WindowVO.ClientWindowType;

import rsge.model.MOrder;
import rsge.model.MOrderLine;

/**
 * @author bang
 *
 */
public class GenPOFromPR implements WindowImplIntf {
	private CLogger s_log = CLogger.getCLogger(GenPOFromPR.class);

	private int windowNO;
	private Ctx serverCtx;
	private WindowCtx windowCtx;
	private UWindowID uid;
	
	public GenPOFromPR(int windowNO, Ctx serverCtx,
			WindowCtx windowCtx, UWindowID uid) {
		this.windowNO = windowNO;
		this.serverCtx = serverCtx;
		this.windowCtx = windowCtx;
		this.uid = uid;

		s_log.fine("Language:" + Env.getLanguage(this.serverCtx) + " windowNO:"
				+ this.windowNO + " AD_Form_ID:" + this.uid.getAD_Form_ID());
	}

	@Override
	public ArrayList<ComponentImplIntf> getComponents() {
		// TODO Auto-generated method stub
		ArrayList<ComponentImplIntf> arrayList = new ArrayList<ComponentImplIntf>();
		arrayList.add(new GenPOFromPR_Header(serverCtx, windowCtx));
		arrayList.add(new GenPOFromPR_Table(serverCtx));
		arrayList.add(new GenPOFromPR_Footer(serverCtx));
		return arrayList;
	}

	@Override
	public ChangeVO processCallback(String sender) {
		// TODO Auto-generated method stub
		ChangeVO changeVO = new ChangeVO();
		changeVO.queryComponents = new HashMap<Integer, QueryVO>();
		QueryVO queryVO = new QueryVO();
		ArrayList selRow = null;
		Object[] selVal = null;

		int C_BPartner_ID = 0;
		int AD_Org_ID = 0;
		int M_Requisition_ID = 0;
		if (sender.equals("btnSearch")) {
			changeVO.queryComponents.put(1, queryVO);
		} else if (sender.equals("btnProcess")) {
			
			C_BPartner_ID = windowCtx
					.getAsInt("C_BPartner_ID");
			
			if(serverCtx.getAD_Org_ID()==0){
				changeVO.addWarning("No Organization!");
				return changeVO;
			}
			
			AD_Org_ID = serverCtx.getAD_Org_ID();
			M_Requisition_ID = windowCtx.getAsInt("M_Requisition_ID");
			
			MOrder mOrder = new MOrder(serverCtx, 0, (Trx)null);
			mOrder.setAD_Client_ID(serverCtx.getAD_Client_ID());
			mOrder.setAD_Org_ID(AD_Org_ID);
			mOrder.setAD_User_ID(serverCtx.getAD_User_ID());
			mOrder.setBill_BPartner_ID(C_BPartner_ID);
			mOrder.setBill_User_ID(serverCtx.getAD_User_ID());
			mOrder.setC_BPartner_ID(C_BPartner_ID);
			mOrder.setC_BPartner_Location_ID(Location(C_BPartner_ID));
			MClientInfo info = MClientInfo.get(serverCtx, serverCtx.getAD_Client_ID());
			MAcctSchema as = new MAcctSchema(serverCtx, info.getC_AcctSchema1_ID(), (Trx)null);
			mOrder.setC_Currency_ID(as.getC_Currency_ID());
			mOrder.setC_DocType_ID(docTypeID());
			mOrder.setC_DocTypeTarget_ID(docTypeID());
			MBPartner partner = new MBPartner(serverCtx, C_BPartner_ID, (Trx)null);
			if(partner.getPO_PaymentTerm_ID() > 0)
				mOrder.setC_PaymentTerm_ID(partner.getPO_PaymentTerm_ID());
			else{
				changeVO.addWarning("No Payment Term in Vendor!");
				return changeVO;
			}
				
			mOrder.setDateAcct(new Timestamp(System.currentTimeMillis()));
			mOrder.setDateOrdered(new Timestamp(System.currentTimeMillis()));
			mOrder.setDatePromised(new Timestamp(System.currentTimeMillis()));
			mOrder.setDeliveryRule(X_C_Order.DELIVERYRULE_Manual);
			mOrder.setDeliveryViaRule(X_C_Order.DELIVERYVIARULE_Delivery);
			mOrder.setInvoiceRule(X_C_Order.INVOICERULE_AfterDelivery);
			mOrder.setIsSOTrx(false);
			mOrder.setSalesRep_ID(serverCtx.getAD_User_ID());
			mOrder.setPriorityRule(X_C_Order.PRIORITYRULE_Medium);
			mOrder.setPaymentRule(X_C_Order.PAYMENTRULE_Cash);
			if(mOrder.save()){
				selRow = windowCtx.getSelectedRows(1);
				if (selRow != null && selRow.size() > 0) {
					for (int i = 0; i < selRow.size(); i++) {
						selVal = windowCtx.getSelectedRows(1).get(i);
						int M_RequisitionLine_ID = 0;
						M_RequisitionLine_ID = Integer.valueOf(selVal[1].toString());
						MRequisitionLine requisitionLine = new MRequisitionLine(serverCtx, M_RequisitionLine_ID, (Trx)null);
						int ProductID = 0;
						ProductID = requisitionLine.getM_Product_ID();
						int C_Charge_ID = 0;
						C_Charge_ID = requisitionLine.getC_Charge_ID();
						int C_UOM_ID = 0;
						if(ProductID>0){
							MProduct mProduct = new MProduct(serverCtx, ProductID, (Trx)null);
							C_UOM_ID = mProduct.getC_UOM_ID();
						}else
						C_UOM_ID = 100; 
						BigDecimal PriceActual = BigDecimal.ZERO;
						PriceActual = requisitionLine.getPriceActual();
						BigDecimal Quantity = BigDecimal.ZERO;
						Quantity = new BigDecimal(selVal[6].toString());
						BigDecimal LineNetAmt = BigDecimal.ZERO;
						LineNetAmt = PriceActual.multiply(Quantity);
						
						MOrderLine line = new MOrderLine(serverCtx, 0, (Trx)null);
						line.setAD_Client_ID(serverCtx.getAD_Client_ID());
						line.setAD_Org_ID(AD_Org_ID);
						line.setLine(i+1);
						line.setC_Order_ID(mOrder.getC_Order_ID());
						line.setQtyOrdered(Quantity);
						line.setQtyEntered(Quantity);
						line.setQty(Quantity);
						line.setPriceList(PriceActual);
						line.setPriceEntered(PriceActual);
						line.setPriceActual(PriceActual);
						line.setPrice(PriceActual);
						line.setM_Product_ID(ProductID);
						line.setLineNetAmt(LineNetAmt);
						line.setDateOrdered(new Timestamp(System.currentTimeMillis()));
						line.setDateInvoiced(new Timestamp(System.currentTimeMillis()));
						line.setDatePromised(new Timestamp(System.currentTimeMillis()));
						line.setC_UOM_ID(C_UOM_ID);
						line.setC_Charge_ID(C_Charge_ID);
						if(line.save()){
							MRequisitionLine line2 = new MRequisitionLine(serverCtx, M_RequisitionLine_ID, (Trx)null);
							line2.setC_OrderLine_ID(line.getC_OrderLine_ID());
							line2.save();
						}
					}
					changeVO.addSuccess("Sukses PO : "+mOrder.getDocumentNo());
					changeVO.showResults(true);
					changeVO.updateWindowVO = true;
				} else {
					changeVO.addWarning("No record selected!");
				}
			}
			
		} 

		changeVO.showResults(true);
		return changeVO;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validateResponse(ResponseVO responseVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Box getLayout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientWindowType getClientWindowType() {
		// TODO Auto-generated method stub
		return null;
	}
	

	private int Location(int C_BPartner_ID){
		int loc = 0;
		String sql = "SELECT C_BPartner_Location_ID FROM C_BPartner_Location WHERE C_BPartner_ID = "+C_BPartner_ID;
		PreparedStatement ps = DB.prepareStatement(sql, (Trx)null);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if(rs.next())
				loc = rs.getInt(1);
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return loc;
	}

	private int docTypeID(){
		int type = 0;
		String sql = "SELECT C_DocType_ID, Name FROM C_DocType WHERE AD_Client_ID = "+serverCtx.getAD_Client_ID()+" " +
				"AND DocBaseType = 'POO' AND IsReleaseDocument = 'N' AND IsReturnTrx = 'N'";
		PreparedStatement ps = DB.prepareStatement(sql, (Trx)null);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if(rs.next())
				type = rs.getInt(1);
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return type;
	}

}
