package park_model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import config_files.Config;

/**
 * Provides helper methods for business rules that are not directly
 * tied to any given class.
 * 
 * @author Julia Behnen
 * @version 5/16/2015
 */
public class RulesHelp {
	
	/**
	 * Gets today's date set to the midnight that started the day.
	 * 
	 * @param theDate The date to be rounded down.
	 * @return a date set to the midnight that starts the theDate.
	 */
	public static GregorianCalendar getTodaysDate() {
		GregorianCalendar date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		date.get(Calendar.MILLISECOND); //reset milliseconds
		return date;
	}
	
	/**
	 * Returns true if the date is in the past (before today), false otherwise.
	 * 
	 * @param theDate The date being checked to see if it is in the past.
	 * @return True if the date is in the past (before today), false otherwise.
	 */
	public static boolean isDateInPast(GregorianCalendar theDate) {
		GregorianCalendar today = RulesHelp.getTodaysDate();
		return (theDate.compareTo(today) < 0);
	}
	
	/**
	 * Returns true if the date is more
	 * than the maximum allowed number of days in the
	 * future, false otherwise.
	 * 
	 * @param theDate The date being checked to see if it is
	 * too far in the future.
	 * 
	 * @return true if the date is more
	 * than the maximum allowed number of days in the
	 * future, false otherwise.
	 */
	public static boolean isDateTooFarInFuture(GregorianCalendar theDate) {
		GregorianCalendar futureBound = RulesHelp.getTodaysDate();
		futureBound.add(Calendar.DATE, Config.MAX_DAYS_IN_FUTURE);
		return (theDate.compareTo(futureBound) > 0);
	}
	
	/**
	 * Returns true if the date falls within the inclusive 
	 * time range from theFirstDate to theEndDate, false otherwise.
	 * 
	 * @param theDate The date being checked against the time range.
	 * @param theFirstDate The first day of the time range.
	 * @param theEndDate The last day of the time range.
	 * @return true if part of the job falls within the inclusive 
	 * time range from theFirstDate to theEndDate, false otherwise.
	 */
	public static boolean isDateInRange(GregorianCalendar theDate, 
			GregorianCalendar theFirstDate, GregorianCalendar theEndDate) {
		return (theDate.compareTo(theFirstDate) >= 0 
				&& theDate.compareTo(theEndDate) <= 0);
	}
	
	/**
	 * Returns true if part of the job falls within the inclusive 
	 * time range from theFirstDate to theEndDate, false otherwise.
	 * 
	 * Precondition: theJob != null, theFirstDate != null, theLastDate != null.
	 * 
	 * @param theJob The job being checked against the time range.
	 * @param theFirstDate The first day of the time range.
	 * @param theLastDate The last day of the time range.
	 * @return true if part of the job falls within the inclusive 
	 * time range from theFirstDate to theLastDate, false otherwise.
	 */
	public static boolean isJobInRange(Job theJob, GregorianCalendar theFirstDate,
			GregorianCalendar theLastDate) {
		if (RulesHelp.isDateInRange(theJob.getFirstDate(), theFirstDate, theLastDate)) {
			return true;
		}
		if (RulesHelp.isDateInRange(theJob.getLastDate(), theFirstDate, theLastDate)) {
			return true;
		}
		return false;
	}
	
}
