package config_files;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Holds all of the important constants used by the program.
 * 
 * @author Julia Behnen
 * @version 5/8/2015
 */
public class Config {
	
	/**
	 * File that holds information about all users registered in the system.
	 */
	public static final String USER_FILE = "src/config_files/users.txt";
	
	/**
	 * File that holds all upcoming jobs.
	 */
	public static final String JOB_SCHEDULE_FILE = "src/config_files/jobSchedule.txt";
	
	public static final String JOB_TEST_OUTPUT_FILE = "src/config_files/text.txt";
	
	/**
	 * Number of total dates in the job.
	 */
	public static final int MAX_JOB_DAYS = 2;
	
	/**
	 * Maximum pending jobs at any given time.
	 */
	public static final int MAX_TOTAL_JOBS = 30;
	
	/**
	 * Maximum days in the future that the job may be scheduled.
	 */
	public static final int MAX_DAYS_IN_FUTURE = 90;
	
	/**
	 * Maximum days in the future that the job may be scheduled.
	 */
	public static final int MAX_JOBS_PER_WEEK = 5;
	
	// For unit tests
	public static final String DEFAULT_EMAIL = "smith@aol.com";
	public static final String DEFAULT_FIRST_NAME = "John";
	public static final String DEFAULT_LAST_NAME = "Smith";
	
	/**
	 * Rounds down a date to the midnight that starts the day.
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
}
