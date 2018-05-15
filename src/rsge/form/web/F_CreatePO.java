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
import org.compiere.model.MClientInfo;
import org.compiere.model.MProduct;
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
import org.compiere.model.MRequisitionLine;

/**
 * @author bang
 * 
 */
public class F_CreatePO implements WindowImplIntf {

	private CLogger s_log = CLogger.getCLogger(F_CreatePO.class);

	private int windowNO;
	private Ctx serverCtx;
	private WindowCtx windowCtx;
	private UWindowID uid;

	/**
	 * 
	 */
	public F_CreatePO(int windowNO, Ctx serverCtx, WindowCtx windowCtx,
			UWindowID uid) {
		this.windowNO = windowNO;
		this.serverCtx = serverCtx;
		this.windowCtx = windowCtx;
		this.uid = uid;

		s_log.fine("Create PO Language:" + Env.getLanguage(this.serverCtx)
				+ " windowNO:" + this.windowNO + " AD_Form_ID:"
				+ this.uid.getAD_Form_ID());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.intf.WindowImplIntf#getComponents()
	 */
	@Override
	public ArrayList<ComponentImplIntf> getComponents() {
		ArrayList<ComponentImplIntf> components = new ArrayList<ComponentImplIntf>();
		components.add(new F_CreatePO_Header(serverCtx, windowCtx));
		components.add(new F_CreatePO_ButtonSearch());
		components.add(new F_CreatePO_Table(serverCtx));
		components.add(new F_CreatePO_ButtonComplete());

		return components;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.intf.WindowImplIntf#processCallback(java.lang.String)
	 */
	@Override
	public ChangeVO processCallback(String sender) {
		ChangeVO changeVO = new ChangeVO();
		changeVO.queryComponents = new HashMap<Integer, QueryVO>();
		QueryVO queryVO = new QueryVO();
		int M_RequisitionLine_ID = 0;
		if (sender.equals("bComplete")) {
			ArrayList<?> selRow = windowCtx.getSelectedRows(2);
			if (selRow != null && selRow.size() > 0) {
				ArrayList<Integer> list = new ArrayList<Integer>();
				Integer[] reqs = null;
				Integer[] retValue = null;
				for (int i = 0; i < selRow.size(); i++) {
					Object[] selVal = windowCtx.getSelectedRows(2).get(i);
					M_RequisitionLine_ID = Integer.valueOf(selVal[0].toString());
					list.add(M_RequisitionLine_ID);
					retValue = new Integer[list.size ()];
					list.toArray (retValue);
				}
				reqs = retValue;
				
				
				Integer[] allVendor = getAllVendor(serverCtx, reqs, (Trx)null);
				if(allVendor.length>0){
					for(Integer alla:allVendor){
						MOrder order = order(serverCtx, alla.intValue(), serverCtx.getAD_Org_ID(), new Timestamp(System.currentTimeMillis()), 
								serverCtx.getAD_Client_ID(), getTotal(reqs, alla.intValue()), (Trx)null);
						MRequisitionLine[] lines = getOrderLines(reqs, alla.intValue());
						if(lines.length > 0){
							for(MRequisitionLine line:lines){
								orderLine(order, line);
							}
						}
					}
				}
				changeVO.addSuccess("Winner selected!");
				// refresh the table
				changeVO.queryComponents.put(2, queryVO);

			} else {
				changeVO.addWarning("No record selected!");
			}
		}

		changeVO.showResults(true);

		return changeVO;
	}
	
	private MRequisitionLine[] getOrderLines(Integer[] AD_Org_IDs, int Vendor)
	{
		ArrayList<MRequisitionLine> list = new ArrayList<MRequisitionLine>();
		String sql = "SELECT rl.* FROM M_RequisitionLine rl " +
				"INNER JOIN M_Product_PO ppo ON (rl.M_Product_ID = ppo.M_Product_ID) " +
				"WHERE rl.M_RequisitionLine_ID IN ("+setCommaLoop(AD_Org_IDs, false)+") AND ppo.C_BPartner_ID = " + Vendor;
		
		PreparedStatement pstmt = DB.prepareStatement(sql, (Trx) null);
		ResultSet rs= null;
		try{
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MRequisitionLine line = new MRequisitionLine(serverCtx, rs, (Trx)null);
				list.add(line);
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
		MRequisitionLine[] lines = new MRequisitionLine[list.size()];
		list.toArray(lines);
		return lines;
	}
	
	public static Integer[] getAllVendor(Ctx ctx, Integer[] AD_Org_IDs ,Trx trx){
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		String sql = "SELECT ppo.C_BPartner_ID FROM M_RequisitionLine rl " +
				"INNER JOIN M_Product_PO ppo ON (rl.M_Product_ID = ppo.M_Product_ID) " +
				"WHERE rl.M_RequisitionLine_ID IN ("+setCommaLoop(AD_Org_IDs, false)+") " +
				"GROUP BY ppo.C_BPartner_ID";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = DB.prepareStatement(sql, trx);
			rs = ps.executeQuery();
			
			while(rs.next())
				list.add(rs.getInt(1));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		
		Integer[] retValue = new Integer[list.size ()];
		list.toArray (retValue);
		return retValue;
		
	}
	
	public static String setCommaLoop(Integer[] arr , boolean isString){
		//The string builder used to construct the string
	    StringBuilder commaSepValueBuilder = new StringBuilder();
	 
	    if(arr.length > 0){
	    	//Looping through the list
		    for ( int i = 0; i< arr.length; i++){
		      //append the value into the builder
		    	if(isString)
		    		commaSepValueBuilder.append("'"+arr[i]+"'");
		    	else
		    		commaSepValueBuilder.append(arr[i]);
		       
		      //if the value is not the last element of the list
		      //then append the comma(,) as well
		      if ( i != arr.length-1){
		        commaSepValueBuilder.append(", ");
		      }
		    }
	    }

	    return commaSepValueBuilder.length() > 0 ? commaSepValueBuilder.toString() : "";
	}

	@SuppressWarnings("unused")
	private BigDecimal getRemainBudget(int C_RfQResponseLine_ID, Trx trx) {
		String sql = "SELECT rl.RemainingBudget FROM M_RequisitionLine rl "
				+ "INNER JOIN XX_ReleaseToPOLine rtl ON (rl.M_RequisitionLine_ID = rtl.M_RequisitionLine_ID) "
				+ "INNER JOIN C_RfQLine rfl ON (rtl.C_RfQLine_ID = rfl.C_RfQLine_ID) "
				+ "INNER JOIN C_RfQResponseLine rflr ON (rfl.C_RfQLine_ID = rflr.C_RfQLine_ID) "
				+ "WHERE C_RfQResponseLine_ID = " + C_RfQResponseLine_ID;

		System.out.println("sql Remaining : " + sql);
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getBigDecimal(1);

			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return BigDecimal.ZERO;
	}

	@SuppressWarnings("unused")
	private BigDecimal getBudgetCost(int C_RFQResponseLine_ID, Trx trx) {
		String sql = "SELECT COALESCE(SUM(Price),0) As BudgetAmt FROM C_RFQResponseLineQty WHERE C_RFQResponseLine_ID = "
				+ C_RFQResponseLine_ID;

		System.out.println("BudgetCost sql : " + sql);
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getBigDecimal(1);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return BigDecimal.ZERO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.intf.WindowImplIntf#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.compiere.intf.WindowImplIntf#validateResponse(org.compiere.vos.ResponseVO
	 * )
	 */
	@Override
	public void validateResponse(ResponseVO responseVO) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.intf.WindowImplIntf#getLayout()
	 */
	@Override
	public Box getLayout() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.intf.WindowImplIntf#getClientWindowType()
	 */
	@Override
	public ClientWindowType getClientWindowType() {
		return ClientWindowType.GENERIC_STACK;
	}
	
	public static MOrder order(Ctx ctx, int C_BPartner_ID, int AD_Org_ID,
			Timestamp dateTrx, int AD_Client_ID, 
			BigDecimal Total, Trx trx) {
		MOrder order = new MOrder(ctx, 0, trx);
		order.setAD_Client_ID(AD_Client_ID);
		order.setAD_Org_ID(AD_Org_ID);
		order.setC_BPartner_ID(C_BPartner_ID);
		order.setC_BPartner_Location_ID(partnerLocation(ctx, C_BPartner_ID,
				AD_Client_ID, trx));
		order.setC_DocTypeTarget_ID(docTypeTarget(ctx, AD_Client_ID, trx));
		order.setM_PriceList_ID(priceList(ctx, AD_Client_ID, trx));
		order.setM_Warehouse_ID(warehouse(ctx, AD_Org_ID, trx));
		order.setC_PaymentTerm_ID(paymentTerm(ctx, AD_Client_ID, trx));
		order.setSalesRep_ID(salesRep(ctx, AD_Client_ID, trx));
		order.setPriorityRule("5");
		order.setDeliveryViaRule("P");
		MClientInfo info = MClientInfo.get(ctx, AD_Client_ID);
		MAcctSchema as = new MAcctSchema(ctx, info.getC_AcctSchema1_ID(), trx);
		order.setC_Currency_ID(as.getC_Currency_ID());
		order.setDateAcct(dateTrx);
		order.setDateOrdered(dateTrx);
		order.setDatePromised(dateTrx);
		order.setTotalLines(Total);
		order.setGrandTotal(Total);
		order.setIsSOTrx(false);
		order.save();
		return order;
	}
	
	public static MOrderLine orderLine(MOrder order, MRequisitionLine poLine) {
		MOrderLine line = new MOrderLine(order);
		line.setAD_Client_ID(order.getAD_Client_ID());
		line.setAD_Org_ID(order.getAD_Org_ID());
		line.setM_Product_ID(poLine.getM_Product_ID());
		line.setLine(setLineNum(order.getCtx(), "C_Order", "C_OrderLine", "Line", order.getC_Order_ID(), order.get_Trx()));
		line.setQty(poLine.getQty());
		line.setQtyEntered(poLine.getQty());
		if(poLine.getM_Product_ID() > 0){
			if(setIsAsset(poLine.getM_Product_ID(), poLine.get_Trx())){
				line.set_Value("IsAssetAddition", true);
			}
		}
		MProduct product = new MProduct(poLine.getCtx(), poLine.getM_Product_ID(), poLine.get_Trx());
		line.setC_UOM_ID(product.getC_UOM_ID());
		line.setPriceList(poLine.getPriceActual());
		line.setPriceActual(poLine.getPriceActual());
		line.setPriceEntered(poLine.getPriceActual());
		line.setQtyOrdered(poLine.getQty());
		line.save();
		return line;
	}
	
	public static boolean setIsAsset(int M_Product_ID, Trx trx){
		String sql = "SELECT 1 AS IsAsset FROM A_Asset_Group ag " +
				"WHERE M_Product_ID = "+M_Product_ID+"";
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1) == 1){
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		return false;
	}
	
	public static int setLineNum(Ctx ctx, String TableHeader, String Tableline, String Kolom, int idHeader, Trx trx){
		String sql = "SELECT COALESCE(MAX("+Kolom+"),0)+1 AS DefaultValue FROM "+Tableline+" WHERE "+TableHeader+"_ID="+idHeader;
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	public static int paymentTerm(Ctx ctx, int AD_Client_ID, Trx trx) {
		String sql = "SELECT C_PaymentTerm_ID FROM C_PaymentTerm WHERE IsDefault = 'Y' AND AD_Client_ID = "
				+ AD_Client_ID + " ";
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		return 0;
	}

	public static int warehouse(Ctx ctx, int AD_Org_ID, Trx trx) {
		String sql = "SELECT M_Warehouse_ID FROM M_Warehouse WHERE AD_Org_ID = "
				+ AD_Org_ID + " ";
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		return 0;
	}

	public static int docTypeTarget(Ctx ctx, int AD_Client_ID, Trx trx) {
		String sql = "SELECT C_DocType_ID FROM C_DocType WHERE "
				+ "C_DocType.DocBaseType IN ('POO') "
				+ "AND C_DocType.IsSOTrx='N' AND C_DocType.IsReturnTrx='N' "
				+ "AND C_DocType.IsReleaseDocument='N' AND AD_Client_ID = "
				+ AD_Client_ID + "";
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		return 0;
	}

	public static int priceList(Ctx ctx, int AD_Client_ID, Trx trx) {
		String sql = "SELECT M_PriceList_ID FROM M_PriceList WHERE IsDefault = 'Y' "
				+ "AND IsSOPriceList = 'N' AND AD_Client_ID = "
				+ AD_Client_ID
				+ " ";
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		return 0;
	}

	public static int partnerLocation(Ctx ctx, int C_BPartner_ID,
			int AD_Client_ID, Trx trx) {
		String sql = "SELECT C_BPartner_Location_ID FROM C_BPartner_Location WHERE C_BPartner_ID = "
				+ C_BPartner_ID
				+ " "
				+ "AND AD_Client_ID = "
				+ AD_Client_ID
				+ " ";
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		return 0;
	}

	public static int salesRep(Ctx ctx, int AD_Client_ID, Trx trx) {
		String sql = "SELECT AD_User_ID FROM AD_User WHERE C_BPartner_ID IN (SELECT C_BPartner_ID FROM C_BPartner WHERE IsSalesRep = 'Y' AND AD_Client_ID = "
				+ AD_Client_ID + ") " + "ORDER BY AD_User_ID ASC";
		PreparedStatement ps = DB.prepareStatement(sql, trx);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
		return 0;
	}
	
	private BigDecimal getTotal(Integer[] AD_Org_IDs, int Vendor){
		String sql = "SELECT COALESCE(SUM(rl.LineNetAmt),0) FROM M_RequisitionLine rl " +
				"INNER JOIN M_Product_PO ppo ON (rl.M_Product_ID = ppo.M_Product_ID) " +
				"WHERE rl.M_RequisitionLine_ID IN ("+setCommaLoop(AD_Org_IDs, false)+") AND ppo.C_BPartner_ID = "+Vendor;
		PreparedStatement ps = DB.prepareStatement(sql, (Trx)null);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getBigDecimal(1);
			
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return BigDecimal.ZERO;
	}
}
