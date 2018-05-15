/**
 * 
 */
package rsge.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import org.compiere.model.MCalendar;
import rsge.model.MYear;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * @author Fanny
 *
 */
public class GenerateYearAndPeriod extends SvrProcess {

	private Timestamp				p_StartDate;
	private String					p_CalendarType;
	private int						p_NumberOfYear = 0;
	
	private MCalendar				calendar = null;
	private boolean					isCrossYear = true;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Payroll Parameter
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter element : para) {
			String name = element.getParameterName();
			if (element.getParameter() == null && element.getParameter_To() == null)
				;
			else if (name.equals("StartDate"))
				p_StartDate = (Timestamp) element.getParameter();
			else if (name.equals("CalendarType"))
				p_CalendarType = (String) element.getParameter();
			else if (name.equals("NumberOfYear"))
				p_NumberOfYear = element.getParameterAsInt();			
		}
		
		calendar = new MCalendar(getCtx(), getRecord_ID(), get_Trx());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		// Check if calendar has already have year or not. If yes, cancel process
		String sql = "SELECT C_Year_ID FROM C_Year WHERE C_Calendar_ID = " + getRecord_ID();		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				return "Calendar already has year. Process cancel ";
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
		// Check Year
		
		// Check if calendar will be cross year or not
		// If start date is not January 1, it means the calendar is cross year		
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(p_StartDate);
		if(startCal.get(Calendar.DAY_OF_MONTH) == 1 && startCal.get(Calendar.MONTH)==0)
			isCrossYear = false;				
		
		// Create year
		int firstYear = startCal.get(Calendar.YEAR);		
		ArrayList<MYear> yearList = new ArrayList<MYear>();
		
		for(int i = 0; i<p_NumberOfYear; i++)
		{			
			StringBuilder fiscalYear = new StringBuilder(Integer.toString(firstYear));
			if(isCrossYear)
			{
				fiscalYear.append("/");
				fiscalYear.append(Integer.toString(firstYear+1));
			}			

			MYear year = new MYear(getCtx(), 0, get_Trx());
			year.setClientOrg(calendar);
			year.setC_Calendar_ID(calendar.getC_Calendar_ID());
			year.setFiscalYear(fiscalYear.toString());
			if(year.save())
				yearList.add(year);			
			firstYear++;
		}
		
		MYear[] list = new MYear[yearList.size()];
		yearList.toArray(list);
		if(!MYear.createYearPeriod(list, p_StartDate, p_CalendarType))
			return "Error :";
		return "Year and Period created successfully";
	}

}
