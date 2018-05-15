/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.model.X_C_JobCategory;
import org.compiere.util.Ctx;
import org.compiere.util.Trx;

/**
 * @author Fanny
 *
 */
public class MJobCategory extends X_C_JobCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param C_JobCategory_ID
	 * @param trx
	 */
	public MJobCategory(Ctx ctx, int C_JobCategory_ID, Trx trx) {
		super(ctx, C_JobCategory_ID, trx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trx
	 */
	public MJobCategory(Ctx ctx, ResultSet rs, Trx trx) {
		super(ctx, rs, trx);
		// TODO Auto-generated constructor stub
	}
	
    /** Set Calculate Attendance.
    @param IsCalculateAttendance Calculate attendance for employee in this category */
    public void setIsCalculateAttendance (boolean IsCalculateAttendance)
    {
        set_Value ("IsCalculateAttendance", Boolean.valueOf(IsCalculateAttendance));
        
    }
    
    /** Get Calculate Attendance.
    @return Calculate attendance for employee in this category */
    public boolean isCalculateAttendance() 
    {
        return get_ValueAsBoolean("IsCalculateAttendance");
        
    }


}
