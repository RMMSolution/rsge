package rsge.acct;

import java.math.BigDecimal;

import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MConversionRate;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.ProductCost;
import org.compiere.model.X_M_Product;
import org.compiere.util.CLogger;

import rsge.model.MBudgetInfo;
import rsge.model.MChargeAcct;
import rsge.model.MInOutLine;
import rsge.model.MOrder;
import rsge.model.MOrderLine;
import rsge.model.MProductAcct;
import rsge.model.MProductCategoryAcct;
import rsge.model.MPurchaseRequisition;
import rsge.model.MPurchaseRequisitionLine;
import rsge.model.MTransferReceipt;
import rsge.model.MTransferReceiptLine;

public class DocLine_ReceiptTransfer extends DocLine {

	/**	Log	per Document				*/
	protected CLogger			log = CLogger.getCLogger(getClass());
	private int					p_AD_Org_ID = 0;
	private MAccount			Account = null;
	private BigDecimal			p_Amount = BigDecimal.ZERO;
	
	/**
	 * @param po
	 * @param doc
	 */
	public DocLine_ReceiptTransfer(MTransferReceiptLine line, Doc doc, MAcctSchema as) {
		
		super(line, doc);		
		setP_AD_Org_ID(line.getAD_Org_ID());
		setAccount(line, as);
		setAmount(line, as);
	}
	
	private boolean setAccount(MTransferReceiptLine line, MAcctSchema as)
	{
		MPurchaseRequisitionLine prl = new MPurchaseRequisitionLine(line.getCtx(), line.getXX_PurchaseRequisitionLine_ID(), line.get_Trx());
		int accountID = 0;
		if(prl.getAccount_ID()!=0)
			accountID = prl.getAccount_ID();
		else if(prl.getC_Charge_ID()!=0)
		{
			MChargeAcct ca = MChargeAcct.get(prl.getC_Charge_ID(), as);
			MAccount vc = MAccount.get(prl.getCtx(), ca.getCh_Expense_Acct());
			accountID = vc.getAccount_ID();
		}
		else if(prl.getM_Product_ID()!=0)
		{
			MProduct p = new MProduct(line.getCtx(), prl.getM_Product_ID(), line.get_Trx());
			MProductAcct pa = MProductAcct.get(p.getM_Product_ID(), as);
			MAccount vc = null;
			if(p.getProductType().equals(X_M_Product.PRODUCTTYPE_Item)
					|| p.getProductType().equals(X_M_Product.PRODUCTTYPE_Resource))
				vc = MAccount.get(line.getCtx(), pa.getP_Asset_Acct());
			else
				vc = MAccount.get(line.getCtx(), pa.getP_Expense_Acct());
			accountID = vc.getAccount_ID();
		}
		
		MPurchaseRequisition pr = new MPurchaseRequisition(line.getCtx(), prl.getXX_PurchaseRequisition_ID(), line.get_Trx());
		setAccount(MAccount.get(line.getCtx(), line.getAD_Client_ID(), line.getAD_Org_ID(), 
				as.getC_AcctSchema_ID(), accountID, 0, prl.getM_Product_ID(), 0, 0, 0, 0, 0, pr.getC_Project_ID(), pr.getC_Campaign_ID(), prl.getC_Activity_ID(), 0,0,0,0));
		return true;
	}
	
	private boolean setAmount(MTransferReceiptLine line, MAcctSchema as)
	{
		BigDecimal amount = BigDecimal.ZERO;
		MTransferReceipt tr = new MTransferReceipt(line.getCtx(), line.getXX_TransferReceipt_ID(), getTrx());
		MInOutLine iol = new MInOutLine(tr.getCtx(), tr.getM_InOutLine_ID(), getTrx());
		MPurchaseRequisitionLine prl = new MPurchaseRequisitionLine(line.getCtx(), line.getXX_PurchaseRequisitionLine_ID(), getTrx());
		MOrderLine ol = new MOrderLine(prl.getCtx(), prl.getC_OrderLine_ID(), getTrx());
		MOrder order = new MOrder(ol.getCtx(), ol.getC_Order_ID(), getTrx());
		
		MBudgetInfo bi = MBudgetInfo.get(line.getCtx(),line.getAD_Client_ID(), line.get_Trx());
		MPriceList pl = new MPriceList(order.getCtx(), order.getM_PriceList_ID(), order.get_Trx());
		BigDecimal costPerUnit = ol.getPriceActual();
		if(ol.getM_Product_ID()!=0)
		{
			MProduct p = new MProduct(ol.getCtx(), ol.getM_Product_ID(), ol.get_Trx());
			if(p.getProductType().equals(X_M_Product.PRODUCTTYPE_Item) 
					|| p.getProductType().equals(X_M_Product.PRODUCTTYPE_Resource)) 
			{
				ProductCost pc = new ProductCost(line.getCtx(), iol.getM_Product_ID(), iol.getM_AttributeSetInstance_ID(), line.get_Trx());
				pc.setQty(BigDecimal.ONE);									
				MProductCategoryAcct pca = MProductCategoryAcct.get(tr.getCtx(), tr.getM_Product_ID(), as.getC_AcctSchema_ID(), tr.get_Trx());
				String costingMethod = pca.getCostingMethod();
				if(costingMethod==null)
					costingMethod = as.getCostingMethod();
				BigDecimal unitCost = BigDecimal.ZERO;
				unitCost = pc.getProductCosts(as, line.getAD_Org_ID(), costingMethod, iol.getC_OrderLine_ID(), true);
				if(unitCost.signum()!=0)
					costPerUnit = unitCost;
			}
		}
		if(as.getC_Currency_ID()!=pl.getC_Currency_ID())
			costPerUnit = MConversionRate.convert(order.getCtx(), costPerUnit, order.getC_Currency_ID(), as.getC_Currency_ID(), order.getDateOrdered(), bi.getC_ConversionType_ID(), order.getAD_Client_ID(), order.getAD_Org_ID());							
		amount = costPerUnit.multiply(line.getQtyAllocated());
		setP_Amount(amount);
		return true;
	}

	public int getP_AD_Org_ID() {
		return p_AD_Org_ID;
	}

	public void setP_AD_Org_ID(int p_AD_Org_ID) {
		this.p_AD_Org_ID = p_AD_Org_ID;
	}

	public MAccount getAccount() {
		return Account;
	}

	public void setAccount(MAccount Account) {
		this.Account = Account;
	}

	public BigDecimal getP_Amount() {
		return p_Amount;
	}

	public void setP_Amount(BigDecimal p_Amount) {
		this.p_Amount = p_Amount;
	}

	

}
