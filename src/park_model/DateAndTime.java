package park_model;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * An immutable class that describes the date, start time, and end time of an event, 
 * with the date set to the morning midnight of that date.
 * 
 * @author Julia Behnen
 * @version 5/6/2015
 *
 */
public class DateAndTime implements Comparable<DateAndTime> {
	
	private final GregorianCalendar myDate;
	private final String myStartTime;
	private final String myEndTime;
	
	public DateAndTime(int theYear, int theMonth, int theDate, String myStartTime, String myEndTime) {
		myDate = new GregorianCalendar(theYear, theMonth, theDate);
		myDate.get(Calendar.MILLISECOND); // reset fields after assignment
		this.myStartTime = myStartTime;
		this.myEndTime = myEndTime;
	}
	
	public DateAndTime(GregorianCalendar theDate, String myStartTime, String myEndTime) {
		this(theDate.get(Calendar.YEAR), theDate.get(Calendar.MONTH), theDate.get(Calendar.DATE),
				myStartTime, myEndTime);
	}
	
	public DateAndTime(DateAndTime theJobDate) {
		this(theJobDate.myDate.get(Calendar.YEAR), theJobDate.myDate.get(Calendar.MONTH), 
				theJobDate.myDate.get(Calendar.DATE), theJobDate.myStartTime, 
				theJobDate.myEndTime);
	}
	
	public GregorianCalendar getDate() {
		return (GregorianCalendar) myDate.clone();
	}
	
	public String getStartTime() {
		return myStartTime;
	}
	
	public String getEndTime() {
		return myEndTime;
	}

	@Override
	public int compareTo(DateAndTime o) {
		return this.myDate.compareTo(o.myDate);
	}
	
	
}
