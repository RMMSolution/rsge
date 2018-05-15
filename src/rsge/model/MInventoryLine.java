package rsge.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.model.MInventory;
import org.compiere.model.X_M_InventoryLine;
import org.compiere.util.CLogger;
import org.compiere.util.Ctx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

public class MInventoryLine extends org.compiere.model.MInventoryLine {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**	Logger				*/
	private static CLogger	s_log	= CLogger.getCLogger (MInventoryLine.class);

	/**
	 * 	Get Inventory Line with parameters
	 *	@param inventory inventory
	 *	@param M_Locator_ID locator
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@return line or null
	 */
	public static MInventoryLine get (MInventory inventory, 
		int M_Locator_ID, int M_Product_ID, int M_AttributeSetInstance_ID)
	{
		MInventoryLine retValue = null;
		String sql = "SELECT * FROM M_InventoryLine "
			+ "WHERE M_Inventory_ID=? AND M_Locator_ID=?"
			+ " AND M_Product_ID=? AND M_AttributeSetInstance_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, inventory.get_Trx());
			pstmt.setInt (1, inventory.getM_Inventory_ID());
			pstmt.setInt(2, M_Locator_ID);
			pstmt.setInt(3, M_Product_ID);
			pstmt.setInt(4, M_AttributeSetInstance_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
				retValue = new MInventoryLine (inventory.getCtx(), rs, inventory.get_Trx());
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql, e);
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeStatement(pstmt);
		}
		return retValue;
	}	//	get

	/**************************************************************************
	 * 	Default Constructor
	 *	@param ctx context
	 *	@param M_InventoryLine_ID line
	 *	@param trx transaction
	 */
	public MInventoryLine (Ctx ctx, int M_InventoryLine_ID, Trx trx)
	{
		super (ctx, M_InventoryLine_ID, trx);
		if (M_InventoryLine_ID == 0)
		{
		//	setM_Inventory_ID (0);			//	Parent
		//	setM_InventoryLine_ID (0);		//	PK
		//	setM_Locator_ID (0);			//	FK
			setLine(0);
		//	setM_Product_ID (0);			//	FK
			setM_AttributeSetInstance_ID(0);	//	FK
			setInventoryType (INVENTORYTYPE_InventoryDifference);
			setQtyBook (Env.ZERO);
			setQtyCount (Env.ZERO);
			setProcessed(false);
		}
	}	//	MInventoryLine

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trx transaction
	 */
	public MInventoryLine (Ctx ctx, ResultSet rs, Trx trx)
	{
		super(ctx, rs, trx);
	}	//	MInventoryLine

	/**
	 * 	Detail Constructor.
	 * 	Locator/Product/AttributeSetInstance must be unique
	 *	@param inventory parent
	 *	@param M_Locator_ID locator
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID instance
	 *	@param QtyBook book value
	 *	@param QtyCount count value
	 */
	public MInventoryLine (MInventory inventory, 
		int M_Locator_ID, int M_Product_ID, int M_AttributeSetInstance_ID,
		BigDecimal QtyBook, BigDecimal QtyCount)
	{
		this (inventory.getCtx(), 0, inventory.get_Trx());
		if (inventory.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setM_Inventory_ID (inventory.getM_Inventory_ID());		//	Parent
		setClientOrg (inventory.getAD_Client_ID(), inventory.getAD_Org_ID());
		setM_Locator_ID (M_Locator_ID);		//	FK
		setM_Product_ID (M_Product_ID);		//	FK
		setM_AttributeSetInstance_ID (M_AttributeSetInstance_ID);
		//
		if (QtyBook != null)
			setQtyBook (QtyBook);
		if (QtyCount != null && QtyCount.signum() != 0)
			setQtyCount (QtyCount);
	}	//	MInventoryLine
	
    public void setC_UOM_ID (int C_UOM_ID){
        set_Value ("C_UOM_ID", Integer.valueOf(C_UOM_ID));
    }
    
    public int getC_UOM_ID() {
        return get_ValueAsInt("C_UOM_ID");
    }
    
	public static boolean generateInternalUsedLine(MTransferReceiptLine trLine, MInOutLine iol, int inventoryID, int lineNo)
	{
		MInventoryLine ml = new MInventoryLine(trLine.getCtx(), 0, trLine.get_Trx());
		ml.setC_Activity_ID(ml.getC_Activity_ID());
		ml.setC_Charge_ID(MGeneralSetup.get(trLine.getCtx(), trLine.getAD_Client_ID(), trLine.get_Trx()).getExpenseTransitCharge_ID());
		ml.setC_UOM_ID(new MTransferReceipt(trLine.getCtx(), trLine.getXX_TransferReceipt_ID(), trLine.get_Trx()).getC_UOM_ID());
		ml.setClientOrg(iol);
		ml.setInventoryType(X_M_InventoryLine.INVENTORYTYPE_ChargeAccount);
		ml.setIsInternalUse(true);
		ml.setLine(lineNo);
		ml.setM_Inventory_ID(inventoryID);
		ml.setM_Locator_ID(iol.getM_Locator_ID());
		
		ml.setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());
		ml.setM_Product_ID(iol.getM_Product_ID());
		ml.setQtyInternalUse(trLine.getQtyAllocated());
		ml.save();
		return true;
	}	



}
