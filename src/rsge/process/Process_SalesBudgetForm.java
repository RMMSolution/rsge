/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MSalesBudgetForm;
import rsge.model.MSummaryReturnBudget;
import rsge.model.MSummarySalesBudget;

/**
 * @author bang
 *
 */
public class Process_SalesBudgetForm extends SvrProcess {
	private MSalesBudgetForm form = null;
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		form = new MSalesBudgetForm(getCtx(), getRecord_ID(), get_Trx());
		
		DB.executeUpdate(get_Trx(), "DELETE FROM XX_SummarySalesBudget WHERE XX_SalesBudgetForm_ID = ?", form.getXX_SalesBudgetForm_ID());
		DB.executeUpdate(get_Trx(), "DELETE FROM XX_SummaryReturnBudget WHERE XX_SalesBudgetForm_ID = ?", form.getXX_SalesBudgetForm_ID());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT M_Product_ID, COALESCE(SUM(QtyPeriod1),0)," +
				"COALESCE(SUM(QtyPeriod2),0)," +
				"COALESCE(SUM(QtyPeriod3),0), " +
				"COALESCE(SUM(QtyPeriod4),0)," +
				"COALESCE(SUM(QtyPeriod5),0)," +
				"COALESCE(SUM(QtyPeriod6),0)," +
				"COALESCE(SUM(QtyPeriod7),0)," +
				"COALESCE(SUM(QtyPeriod8),0)," +
				"COALESCE(SUM(QtyPeriod9),0)," +
				"COALESCE(SUM(QtyPeriod10),0)," +
				"COALESCE(SUM(QtyPeriod11),0)," +
				"COALESCE(SUM(QtyPeriod12),0) " +
				"FROM XX_ProductBudgetForm " +
				"WHERE XX_CustomerBudgetForm_ID IN " +
				"(SELECT XX_CustomerBudgetForm_ID FROM XX_CustomerBudgetForm WHERE XX_SalesBudgetForm_ID = ?) " +
				"GROUP BY M_Product_ID";
		
		PreparedStatement ps = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try {
			ps.setInt(1, form.getXX_SalesBudgetForm_ID());
			rs = ps.executeQuery();
			while(rs.next()){
				MSummarySalesBudget budget = new MSummarySalesBudget(getCtx(), 0, get_Trx());
				budget.setAD_Client_ID(form.getAD_Client_ID());
				budget.setAD_Org_ID(form.getAD_Org_ID());
				budget.setM_Product_ID(rs.getInt(1));
				budget.setQtyPeriod1(rs.getBigDecimal(2));
				budget.setQtyPeriod2(rs.getBigDecimal(3));
				budget.setQtyPeriod3(rs.getBigDecimal(4));
				budget.setQtyPeriod4(rs.getBigDecimal(5));
				budget.setQtyPeriod5(rs.getBigDecimal(6));
				budget.setQtyPeriod6(rs.getBigDecimal(7));
				budget.setQtyPeriod7(rs.getBigDecimal(8));
				budget.setQtyPeriod8(rs.getBigDecimal(9));
				budget.setQtyPeriod9(rs.getBigDecimal(10));
				budget.setQtyPeriod10(rs.getBigDecimal(11));
				budget.setQtyPeriod11(rs.getBigDecimal(12));
				budget.setQtyPeriod12(rs.getBigDecimal(13));
				budget.setXX_SalesBudgetForm_ID(form.getXX_SalesBudgetForm_ID());
				budget.save();
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		String sql1 = "SELECT M_Product_ID, COALESCE(SUM(QtyPeriod1),0)," +
				"COALESCE(SUM(QtyPeriod2),0)," +
				"COALESCE(SUM(QtyPeriod3),0), " +
				"COALESCE(SUM(QtyPeriod4),0)," +
				"COALESCE(SUM(QtyPeriod5),0)," +
				"COALESCE(SUM(QtyPeriod6),0)," +
				"COALESCE(SUM(QtyPeriod7),0)," +
				"COALESCE(SUM(QtyPeriod8),0)," +
				"COALESCE(SUM(QtyPeriod9),0)," +
				"COALESCE(SUM(QtyPeriod10),0)," +
				"COALESCE(SUM(QtyPeriod11),0)," +
				"COALESCE(SUM(QtyPeriod12),0) " +
				"FROM XX_ReturnBudgetForm " +
				"WHERE XX_CustomerBudgetForm_ID IN " +
				"(SELECT XX_CustomerBudgetForm_ID FROM XX_CustomerBudgetForm WHERE XX_SalesBudgetForm_ID = ?) " +
				"GROUP BY M_Product_ID";
		
		PreparedStatement ps1 = DB.prepareStatement(sql1, get_Trx());
		ResultSet rs1 = null;
		try {
			ps1.setInt(1, form.getXX_SalesBudgetForm_ID());
			rs1 = ps1.executeQuery();
			while(rs1.next()){
				MSummaryReturnBudget budget = new MSummaryReturnBudget(getCtx(), 0, get_Trx());
				budget.setAD_Client_ID(form.getAD_Client_ID());
				budget.setAD_Org_ID(form.getAD_Org_ID());
				budget.setM_Product_ID(rs.getInt(1));
				budget.setQtyPeriod1(rs.getBigDecimal(2));
				budget.setQtyPeriod2(rs.getBigDecimal(3));
				budget.setQtyPeriod3(rs.getBigDecimal(4));
				budget.setQtyPeriod4(rs.getBigDecimal(5));
				budget.setQtyPeriod5(rs.getBigDecimal(6));
				budget.setQtyPeriod6(rs.getBigDecimal(7));
				budget.setQtyPeriod7(rs.getBigDecimal(8));
				budget.setQtyPeriod8(rs.getBigDecimal(9));
				budget.setQtyPeriod9(rs.getBigDecimal(10));
				budget.setQtyPeriod10(rs.getBigDecimal(11));
				budget.setQtyPeriod11(rs.getBigDecimal(12));
				budget.setQtyPeriod12(rs.getBigDecimal(13));
				budget.setXX_SalesBudgetForm_ID(form.getXX_SalesBudgetForm_ID());
				budget.save();
			}
			rs1.close();
			ps1.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "Complete";
	}

}
