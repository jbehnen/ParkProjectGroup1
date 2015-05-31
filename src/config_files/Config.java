package config_files;

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
	public static final String USER_FILE_FOR_STATIC = "config_files/users.txt";
	
	public static final String VOLUNTEER_FILE = "src/config_files/volunteers.txt";
	
	public static final String ADMIN_FILE = "src/config_files/administrators.txt";
	
	public static final String PARK_MANAGER_FILE = "src/config_files/park_managers.txt";
	
	/**
	 * File that holds all upcoming jobs.
	 */
	public static final String JOB_SCHEDULE_FILE = "src/config_files/jobSchedule.txt";
	
	public static final String EMPTY_TEXT_FILE = "src/config_files/empty.txt";

	public static final String JOB_TEST_OUTPUT_FILE = "src/config_files/test.txt";
	
	public static final String MAX_JOBS_FILE = "src/config_files/30jobs.txt";
	
	public static final String MAX_JOBS_PER_WEEK_FILE = "src/config_files/WeekFull7-15-2015.txt";
	
	public static final String JOB_IN_PAST_FILE = "src/config_files/5Jobs1InPast.txt";
	
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
	 * Maximum jobs that can be scheduled within an immediate time frame.
	 */
	public static final int MAX_DENSE_JOBS = 5;
	
	/**
	 * The number of days on either side of a given day in which
	 * there can be only MAX_DENSE_JOBS.
	 */
	public static final int IMMEDIATE_TIME_FRAME_DAYS = 3;
	
	// For unit tests
	public static final String DEFAULT_EMAIL = "smith@aol.com";
	public static final String DEFAULT_FIRST_NAME = "John";
	public static final String DEFAULT_LAST_NAME = "Smith";
	
}
