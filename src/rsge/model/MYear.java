/**
 * 
 */
package rsge.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;

import org.compiere.model.MPeriod;
import org.compiere.util.Ctx;
import org.compiere.util.Trx;

/**
 * @author Fanny
 *
 */
public class MYear extends org.compiere.model.MYear {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_Year_ID id
	 *	@param trx transaction
	 */
	public MYear (Ctx ctx, int C_Year_ID, Trx trx)
	{
		super (ctx, C_Year_ID, trx);
		if (C_Year_ID == 0)
		{
		//	setC_Calendar_ID (0);
		//	setYear (null);
			setProcessing (false);	// N
		}		
	}	//	MYear

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trx transaction
	 */
	public MYear (Ctx ctx, ResultSet rs, Trx trx)
	{
		super(ctx, rs, trx);
	}	//	MYear

    public static boolean createYearPeriod(MYear[] list, Timestamp startDate, String calendarType)
    {
		Timestamp pStartDate = startDate;

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);		
    	
    	// Get Number of period per year
    	int periods = 0;
    	// Week - 52
    	if(calendarType.equalsIgnoreCase("WK"))
    		periods = 52;
    	// Bi-Week - 26
    	else if(calendarType.equalsIgnoreCase("BW"))
    		periods = 26;
    	// Semi Month - 24
    	else if(calendarType.equalsIgnoreCase("SM"))
    		periods = 24;
    	// Month - 12
    	else if(calendarType.equalsIgnoreCase("MO"))
    		periods = 12;
    	// Bi-Month - 6
    	else if(calendarType.equalsIgnoreCase("BM"))
    		periods = 6;
    	// Quarter - 4
    	else if(calendarType.equalsIgnoreCase("QT"))
    		periods = 4;
    	// Semi Year - 2
    	else if(calendarType.equalsIgnoreCase("SY"))
    		periods = 2;
    	// Year - 1
    	else if(calendarType.equalsIgnoreCase("YR"))
    		periods = 1;    	
    	
    	// Create Periods
    	for(int i = 0; i<list.length; i++)
    	{
    		MYear year = list[i];
    		
    		if(startCal.get(Calendar.DAY_OF_MONTH)==1 && startCal.get(Calendar.MONTH)==0 
    				&& calendarType.equals("MO"))
    		{
    			year.createStdPeriods(null);
    		}
    		else
    		{
    			// Create custom periods
        		for(int p = 0; p < periods; p++)
        		{    			
        			int periodNo = p+1;
        			Calendar nextPeriod = Calendar.getInstance();
        			nextPeriod.setTime(createPeriod(year, periodNo, pStartDate, calendarType));
        			pStartDate = new Timestamp(nextPeriod.getTimeInMillis());
        		}

    		}
    	}
    	
    	return true;
    }
    
    private static Timestamp createPeriod(MYear year, int periodNo, Timestamp startPeriod, String calendarType)
    {
    	Calendar cal = Calendar.getInstance();
		cal.setTime(startPeriod);
    	
		int initMonth = cal.get(Calendar.MONTH);    	
		String startMonth = getMonthName(initMonth);
		String startYear = Integer.toString(cal.get(Calendar.YEAR));
    	StringBuilder name = new StringBuilder();    	
    	// Week Base
    	if(calendarType.equalsIgnoreCase("WK") || calendarType.equalsIgnoreCase("BW"))
    	{
    		if(periodNo == 1)
    		{
        		// Set First day of week
        		// Get day of week
        		int diff = 0;
        		int day = cal.get(Calendar.DAY_OF_WEEK);
        		if(day==1)
        			diff = 6;
        		else if(day > 2)
        		{
        			diff = day - 2;
        		}
        		cal.add(Calendar.DAY_OF_YEAR, diff*-1);   
        		startPeriod = new Timestamp(cal.getTimeInMillis());
    		}
    		
    		// Get Next Period
    		// Week
    		if(calendarType.equalsIgnoreCase("WK"))
    		{
    			name.append("WEEK ");
    			cal.add(Calendar.WEEK_OF_YEAR, 1);    
    		}
    		// Bi Week
    		else
    		{
    			name.append("BI-WEEK ");
    			cal.add(Calendar.WEEK_OF_YEAR, 2);    
    		}
    		name.append(periodNo);
			name.append(" ");			
    	}
    	// Month, Bi Month, Quarter, Semi Year, Year
    	else if(calendarType.equalsIgnoreCase("SM") || calendarType.equalsIgnoreCase("MO") || calendarType.equalsIgnoreCase("BM") || calendarType.equalsIgnoreCase("QT")
    			|| calendarType.equalsIgnoreCase("SY") || calendarType.equalsIgnoreCase("YR"))
    	{	
    		// Set Start Date
    		if(periodNo == 1)
    		{
    			cal.set(Calendar.DAY_OF_MONTH, 1);
        		startPeriod = new Timestamp(cal.getTimeInMillis());
			}
    		
    		if(calendarType.equalsIgnoreCase("SM"))
    		{
    			// First semi month
    			if(cal.get(Calendar.DAY_OF_MONTH)==1)
    			{
    				cal.set(Calendar.DAY_OF_MONTH, 16);
    			}
    			// Second semi month
    			else
    			{
    				cal.set(Calendar.DAY_OF_MONTH, 1);
    				cal.add(Calendar.MONTH, 1);    				
    			}
    			name.append("SEMI MONTH ");
        		name.append(periodNo);
    			name.append(" ");
    		}
    		else if(calendarType.equalsIgnoreCase("MO"))
    		{
    			cal.add(Calendar.MONTH, 1);    
    		}
    		// Bi Month
    		else if(calendarType.equalsIgnoreCase("BM"))
    		{
    			cal.add(Calendar.MONTH, 2);    
    			name.append("BI MONTH ");
        		name.append(periodNo);
    			name.append(" ");
    		}
    		// Quarter
    		else if(calendarType.equalsIgnoreCase("QT"))
    		{
    			cal.add(Calendar.MONTH, 3);    
    			name.append("QUARTER ");
        		name.append(periodNo);
    			name.append(" ");
    		}
    		// Semi Year
    		else if(calendarType.equalsIgnoreCase("SY"))
    		{
    			cal.add(Calendar.MONTH, 6);        	
    			name.append("SEMI YEAR ");
        		name.append(periodNo);
    			name.append(" ");
    		}
    		else
    			cal.add(Calendar.YEAR, 1);
    	}
    	
    	name.append(startMonth);
    	name.append(" ");
    	name.append(startYear);
    	
    	// Get Next Period 
		Timestamp nextPeriod = new Timestamp(cal.getTimeInMillis());
		
		// Get End Period
		cal.add(Calendar.DAY_OF_YEAR, -1);    		
    	Timestamp endPeriod = null;    	
    	endPeriod = new Timestamp(cal.getTimeInMillis());    	
    	if(cal.get(Calendar.MONTH)!=initMonth)
    	{
    		name.append("/");
    		name.append(getMonthName(cal.get(Calendar.MONTH)));
    		name.append(" ");
    		name.append(cal.get(Calendar.YEAR));
    	}
    	// Create period
		MPeriod period = new MPeriod(year, periodNo, name.toString(), startPeriod, endPeriod);    
		period.save();		
		
    	return nextPeriod;
    }
    
    private static String getMonthName(int month)
    {
    	String monthName = null;
    	if(month == 0)
    		monthName = "JAN";
    	else if(month == 1)
    		monthName = "FEB";
    	else if(month == 2)
    		monthName = "MAR";
    	else if(month == 3)
    		monthName = "APR";
    	else if(month == 4)
    		monthName = "MAY";
    	else if(month == 5)
    		monthName = "JUN";
    	else if(month == 6)
    		monthName = "JUL";
    	else if(month == 7)
    		monthName = "AUG";
    	else if(month == 8)
    		monthName = "SEP";
    	else if(month == 9)
    		monthName = "OCT";
    	else if(month == 10)
    		monthName = "NOV";
    	else monthName = "DEC";
    		
    	return monthName;
    }

}
