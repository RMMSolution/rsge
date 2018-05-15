/**
 * 
 */
package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

import rsge.po.X_XX_SummaryReturnBudget;

/**
 * @author bang
 *
 */
public class MSummaryReturnBudget extends X_XX_SummaryReturnBudget {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final org.compiere.util.CLogger log = org.compiere.util.CLogger
			.getCLogger(MSummaryReturnBudget.class);
	/**
	 * @param ctx
	 * @param XX_SummaryReturnBudget_ID
	 * @param trx
	 */
	public MSummaryReturnBudget(Ctx ctx, int XX_SummaryReturnBudget_ID, Trx trx) {
		super(ctx, XX_SummaryReturnBudget_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MSummaryReturnBudget(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		BigDecimal price = BigDecimal.ZERO;
		price = get(getM_Product_ID());
		if(price.signum()==0){
			MProduct mProduct = new MProduct(getCtx(), getM_Product_ID(), get_Trx());
			log.saveError("Error", Msg.getMsg(getCtx(), "No Price for = "+mProduct.getName()));
			return false;
		}
		
		setPeriod1(getQtyPeriod1().multiply(price));
		setPeriod2(getQtyPeriod2().multiply(price));
		setPeriod3(getQtyPeriod3().multiply(price));
		setPeriod4(getQtyPeriod4().multiply(price));
		setPeriod5(getQtyPeriod5().multiply(price));
		setPeriod6(getQtyPeriod6().multiply(price));
		setPeriod7(getQtyPeriod7().multiply(price));
		setPeriod8(getQtyPeriod8().multiply(price));
		setPeriod9(getQtyPeriod9().multiply(price));
		setPeriod10(getQtyPeriod10().multiply(price));
		setPeriod11(getQtyPeriod11().multiply(price));
		setPeriod12(getQtyPeriod12().multiply(price));
		
		setTotalAmt(getPeriod1().add(getPeriod2().add(getPeriod3().add(getPeriod4().add(getPeriod5().add(getPeriod6().
				add(getPeriod7().add(getPeriod8().add(getPeriod9().add(getPeriod10().add(getPeriod11().add(getPeriod12()))))))))))));
		setTotalQty(getQtyPeriod1().add(getQtyPeriod2().add(getQtyPeriod3().add(getQtyPeriod4().add(getQtyPeriod5().add(getQtyPeriod6().add(getQtyPeriod7().add(getQtyPeriod8().add(getQtyPeriod9().add(getQtyPeriod10().add(getQtyPeriod11().add(getQtyPeriod12()))))))))))));
		return true;
	}
	
	private BigDecimal get(int M_Product_ID){
	BigDecimal amt = BigDecimal.ZERO;
	String sql = "SELECT COALESCE(SUM(PriceList),0) FROM M_ProductPrice " +
			"WHERE M_Product_ID = ? AND M_PriceList_Version_ID IN (" +
			"SELECT M_PriceList_Version_ID FROM XX_BudgetInfo " +
			"WHERE AD_Client_ID = ?)";
	PreparedStatement ps = DB.prepareStatement(sql, get_Trx());
	ResultSet rs = null;
	try {
		ps.setInt(1, M_Product_ID);
		ps.setInt(2, getAD_Client_ID());
		rs = ps.executeQuery();
		if(rs.next())
			amt = rs.getBigDecimal(1);
		rs.close();
		ps.close();
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return amt;
	}

}
