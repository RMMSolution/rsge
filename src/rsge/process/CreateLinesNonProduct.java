/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import org.compiere.model.MOrderLine;

/**
 * Create Lines based on Purchase Order (for Non Product only)
 * @author FANNY
 *
 */
public class CreateLinesNonProduct extends SvrProcess {

	/**
	 * 
	 */
	public CreateLinesNonProduct() {
		// TODO Auto-generated constructor stub
	}
	
	/** Locator									*/
	private int						p_LocatorID = 0;
	/** Order ID								*/
	private int						p_OrderID = 0;
	/** Order Line List							*/
	private ArrayList<Integer>      orderLineList = null;
	/** Receipt (Object)						*/
	private MInOut 					inOut = null;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Prepare Data
		ProcessInfoParameter params[] = getParameter();
		for(ProcessInfoParameter param : params){
			String name = param.getParameterName();
			if(name.equals("M_Locator_ID"))
				p_LocatorID = param.getParameterAsInt();
			if(name.equals("C_Order_ID"))
				p_OrderID = param.getParameterAsInt();
		}
		
		inOut = new MInOut(getCtx(), getRecord_ID(), get_Trx());
		
		// Get Data	
		String sql = "SELECT C_OrderLine_ID " +
				"FROM C_OrderLine " +
				"WHERE QtyOrdered > QtyDelivered " +
				"AND C_Charge_ID > 0 " +
				"AND C_Order_ID = ? " +
				"ORDER BY Line ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		orderLineList = new ArrayList<Integer>();
		
		try{
			pstmt.setInt(1, p_OrderID);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				orderLineList.add(rs.getInt(1));
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// If List contains no data, cancel process
		if(orderLineList.size()==0)
		{
			return "";
		}
		
		for(int i = 0; i < orderLineList.size(); i++)
		{
			// Create Receipt Line
			MOrderLine line = new MOrderLine(getCtx(), orderLineList.get(i), get_Trx());
			if(!checkLines(line.getC_OrderLine_ID()))
			{
				MInOutLine iol = new MInOutLine(inOut);
				iol.setLine(getLineNo());
				iol.setM_Locator_ID(p_LocatorID);
				iol.setC_OrderLine_ID(line.getC_OrderLine_ID());
				iol.setC_Charge_ID(line.getC_Charge_ID());
				iol.setDescription(line.getDescription());
				iol.setQtyEntered(line.getQtyEntered().subtract(line.getQtyDelivered()));
				iol.setC_UOM_ID(line.getC_UOM_ID());
				iol.setC_Campaign_ID(line.getC_Campaign_ID());
				iol.setC_ProjectPhase_ID(line.getC_ProjectPhase_ID());
				iol.setC_ProjectTask_ID(line.getC_ProjectPhase_ID());
				iol.save();
			}
		}
		return "Process Complete";
	}
	
	private int getLineNo()
	{
		int lines = 0;
		String sql = "SELECT NVL(MAX(Line),0)+10 AS DefaultValue " +
				"FROM M_InOutLine " +
				"WHERE M_InOut_ID=? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, inOut.getM_InOut_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				lines = rs.getInt(1);
			}
			
			rs.close();			
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	/** 
	 * Check if order line already exists in this receipt
	 * @return
	 */
	private boolean checkLines(int C_OrderLine_ID)
	{		
		String sql = "SELECT 1 " +
				"FROM M_InOutLine " +
				"WHERE M_InOut_ID=? " +
				"AND C_OrderLine_ID = ? ";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		
		try{
			pstmt.setInt(1, inOut.getM_InOut_ID());
			pstmt.setInt(2, C_OrderLine_ID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				return true;
			}
			
			rs.close();			
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
