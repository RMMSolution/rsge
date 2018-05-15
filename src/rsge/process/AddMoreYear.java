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
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import rsge.model.MYear;

/**
 * @author Fanny
 *
 */
public class AddMoreYear extends SvrProcess {

	private Timestamp				p_StartDate;
	private String					p_CalendarType;
	private int						p_NumberOfYear = 0;
	
	private MCalendar				calendar = null;

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
		
		// Check if calendar has already have year or not. If no, cancel process
		String sql = "SELECT C_Year_ID FROM C_Year WHERE C_Calendar_ID = " + getRecord_ID();		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_Trx());
		ResultSet rs = null;
		try{
			rs = pstmt.executeQuery();
			if(!rs.next())
			{
				return "Calendar has no year. Process cancel ";
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		// Check Year
		
		//Get Start date and Year
		int yearID = 0;
		sql = "SELECT p.EndDate, p.C_Year_ID FROM C_Period p INNER JOIN C_Year y ON (p.C_Year_ID = y.C_Year_ID) " +
				"WHERE y.C_Calendar_ID = ? " +
				"AND p.EndDate = (SELECT MAX(p2.EndDate) " +
				"FROM C_Period p2 " +
				"INNER JOIN C_Year y2 ON (p2.C_Year_ID = y2.C_Year_ID) " +
				"WHERE y2.C_Calendar_ID = y.C_Calendar_ID) ";
		
		pstmt = DB.prepareStatement(sql, get_Trx());
		rs = null;
		try{
			pstmt.setInt(1, calendar.getC_Calendar_ID());
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				Calendar cal = Calendar.getInstance();
				cal.setTime(rs.getTimestamp(1));
				
				// Add a day to end date
				cal.add(Calendar.DAY_OF_YEAR, 1);
				p_StartDate = new Timestamp(cal.getTimeInMillis());
				
				yearID = rs.getInt(2);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Check calendar type
		int periods = 0;
		sql = "SELECT COUNT(C_Period_ID) " +
				"FROM C_Period " +
				"WHERE C_Year_ID =" + yearID;
		
		pstmt = DB.prepareStatement(sql, get_Trx());
		rs = null;
		try{
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				periods = rs.getInt(1);
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

		if(periods == 52)
			p_CalendarType = "WK";
    	// Bi-Week - 26
    	else if(periods == 26)
    		p_CalendarType= "BW";
		// Semi Month - 24
    	else if(periods == 24)
    		p_CalendarType= "SM";
    	// Month - 12
    	else if(periods == 12)
    		p_CalendarType= "MO";
		// Bi Month - 6
    	else if(periods == 6)
    		p_CalendarType= "BM";
		// Quarter - 4
    	else if(periods == 4)
    		p_CalendarType= "QT";
    	// Semi Year - 2
    	else if(periods == 2)
    		p_CalendarType= "SY";
		// Year - 1
    	else if(periods == 1)
    		p_CalendarType= "YR";
    	else 
    		return "Calendar Type cannot be recognized. Process cancel";
		
		// Check cross year
		boolean isCrossYear = true;

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
		return "Process Complete";
	}

}
