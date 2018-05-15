/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;

import org.compiere.model.MClient;
import org.compiere.util.Ctx;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

/**
 * @author Fanny
 *
 */
public class MCalendar extends org.compiere.model.MCalendar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_Calendar_ID id
	 *	@param trx transaction
	 */
	public MCalendar (Ctx ctx, int C_Calendar_ID, Trx trx)
	{
		super(ctx, C_Calendar_ID, trx);
	}	//	MCalendar

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trx transaction
	 */
	public MCalendar (Ctx ctx, ResultSet rs, Trx trx)
	{
		super(ctx, rs, trx);
	}	//	MCalendar

	/**
	 * 	Parent Constructor
	 *	@param client parent
	 */
	public MCalendar (MClient client)
	{
		super(client.getCtx(), 0, client.get_Trx());
		setClientOrg(client);
		setName(client.getName() + " " + Msg.translate(client.getCtx(), "C_Calendar_ID"));
	}	//	MCalendar
	
    /** Set Add More Year and Period.
    @param AddMoreYear Add more year and period to existing calendar */
    public void setAddMoreYear (String AddMoreYear)
    {
        set_Value ("AddMoreYear", AddMoreYear);
        
    }
    
    /** Get Add More Year and Period.
    @return Add more year and period to existing calendar */
    public String getAddMoreYear() 
    {
        return (String)get_Value("AddMoreYear");
        
    }
    
    /** Set Generate Year and Period.
    @param GenerateYearAndPeriod Generate year and period for this calendar */
    public void setGenerateYearAndPeriod (String GenerateYearAndPeriod)
    {
        set_Value ("GenerateYearAndPeriod", GenerateYearAndPeriod);
        
    }
    
    /** Get Generate Year and Period.
    @return Generate year and period for this calendar */
    public String getGenerateYearAndPeriod() 
    {
        return (String)get_Value("GenerateYearAndPeriod");
        
    }


}
