package park_model;

import java.io.Serializable;
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

public class DateAndTime implements Comparable<DateAndTime>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6756996974899115732L;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((myDate == null) ? 0 : myDate.hashCode());
		result = prime * result
				+ ((myEndTime == null) ? 0 : myEndTime.hashCode());
		result = prime * result
				+ ((myStartTime == null) ? 0 : myStartTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DateAndTime other = (DateAndTime) obj;
		if (myDate == null) {
			if (other.myDate != null)
				return false;
		} else if (!myDate.equals(other.myDate))
			return false;
		if (myEndTime == null) {
			if (other.myEndTime != null)
				return false;
		} else if (!myEndTime.equals(other.myEndTime))
			return false;
		if (myStartTime == null) {
			if (other.myStartTime != null)
				return false;
		} else if (!myStartTime.equals(other.myStartTime))
			return false;
		return true;
	}
	
}
