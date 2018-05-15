package rsge.model;

import java.sql.ResultSet;

import org.compiere.model.MAllocationHdr;
import org.compiere.model.X_C_AllocationLine;
import org.compiere.util.Ctx;
import org.compiere.util.Trx;

public class MAllocationLine extends X_C_AllocationLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MAllocationLine(Ctx ctx, int C_AllocationLine_ID, Trx trx) {
		super(ctx, C_AllocationLine_ID, trx);
		// TODO Auto-generated constructor stub
	}

	public MAllocationLine(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 	Parent Constructor
	 *	@param parent parent
	 */
	public MAllocationLine (MAllocationHdr parent)
	{
		this (parent.getCtx(), 0, parent.get_Trx());
		setClientOrg(parent);
		setC_AllocationHdr_ID(parent.getC_AllocationHdr_ID());
		set_Trx(parent.get_Trx());
	}	//	MAllocationLine

	
    /** Set Disbursement Realization Line Invoice.
    @param XX_DisRealizationLineInv_ID Disbursement Realization Line Invoice */
    public void setXX_DisRealizationLineInv_ID (int XX_DisRealizationLineInv_ID)
    {
        if (XX_DisRealizationLineInv_ID <= 0) set_Value ("XX_DisRealizationLineInv_ID", null);
        else 
        set_Value ("XX_DisRealizationLineInv_ID", Integer.valueOf(XX_DisRealizationLineInv_ID));
        
    }
    
    /** Get Disbursement Realization Line Invoice.
    @return Disbursement Realization Line Invoice */
    public int getXX_DisRealizationLineInv_ID() 
    {
        return get_ValueAsInt("XX_DisRealizationLineInv_ID");
        
    }
}
