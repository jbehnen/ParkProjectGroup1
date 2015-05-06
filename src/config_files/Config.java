package config_files;

public class Config {
	
	public static final String USER_FILE = "users.txt";
	
	public static final String JOB_SCHEDULE_FILE = "jobSchedule.txt";
	
	/**
	 * Number of total dates in the job.
	 */
	public static final int MAX_JOB_DAYS = 2;
	
	/**
	 * Maxiumum number of days that a job can span.
	 */
	public static final int MAX_JOB_LENGTH = 2;
	
	/**
	 * Maximum pending jobs at any given time.
	 */
	public static final int MAX_TOTAL_JOBS = 30;
	
	/**
	 * Minimum number of days in the future that the job may be scheduled.
	 */
	public static final int MIN_DAYS_IN_FUTURE = 0;
	
	/**
	 * Maximum days in the future that the job may be scheduled.
	 */
	public static final int MAX_DAYS_IN_FUTURE = 90;
	
	/**
	 * Maximum days in the future that the job may be scheduled.
	 */
	public static final int MAX_JOBS_PER_WEEK = 5;
}
