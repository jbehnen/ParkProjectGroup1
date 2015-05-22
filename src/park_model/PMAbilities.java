package park_model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import config_files.Config;

/**
 * Provides the methods that allow ParkManagers to complete
 * their user stories and access the list of jobs.
 * 
 * @author Julia Behnen
 * @version 5/16/2015
 */
public class PMAbilities {
	
	Collection<Job> myJobs;
	
	/**
	 * Constructs the PMAbilities and gets all of the jobs
	 * stored in the given file.
	 * 
	 * Preconditions: theFileName != null.
	 * 
	 * @param fileName The name of the file that holds the
	 * jobs that the ParkManager should be able to 
	 * access.
	 */
	public PMAbilities(String fileName) {
		myJobs = JobSchedule.getAllFutureJobs(fileName);
		assert myJobs != null;
	}
	
	/**
	 * Adds a job to the list of jobs.
	 * 
	 * Preconditions: theJob != null, !tooManyTotalJobs(),
	 * !tooManyJobsNearJobTime(theJob). 
	 * 
	 * @param theJob The job to be added.
	 */
	public void addJob(Job theJob) {
		myJobs.add(new Job(theJob));
		assert myJobs != null;
	}
	
	/**
	 * Returns the number of jobs in the list.
	 * 
	 * Testing purposes only.
	 * 
	 * @return The number of jobs in the list.
	 */
	public int getNumJobs() {
		return myJobs.size();
	}
	
	/**
	 * Returns the volunteers signed up for a given job.
	 * 
	 * Preconditions: theJob != null.
	 * 
	 * @return the volunteers signed up for a given job.
	 */
	public Collection<User> getVolunteersForJob(Job theJob) {
		return theJob.getVolunteers();
	}
	
	/**
	 * Gets all jobs scheduled at a given park; the interface 
	 * is responsible for making sure that PMs 
	 * can only get jobs for parks that they manage.
	 * 
	 * Preconditions: thePark != null.
	 * 
	 * @return all jobs scheduled at the park.
	 */
	public Collection<Job> getJobsAtPark(String thePark) {
		Collection<Job> jobsAtPark = new ArrayList<>();
		for (Job job: myJobs) {
			if (job.getParkName().equals(thePark)) {
				jobsAtPark.add(new Job(job));
			}
		}
		assert myJobs != null;
		return jobsAtPark;
	}
	
	/**
	 * Saves jobs to file.
	 * 
	 * Preconditions: fileName != null.
	 */
	public void saveJobs(String fileName) {
		JobSchedule.saveJobList(myJobs, fileName);
		assert myJobs != null;
	}
	
	/**
	 * Returns true if there are too many total jobs to
	 * add a new job, false otherwise.
	 * 
	 * @return true if there are too many total jobs to
	 * add a new job, false otherwise.
	 */
	public boolean tooManyTotalJobs() {
		return myJobs.size() >= Config.MAX_TOTAL_JOBS;
	}
	
	/**
	 * ****************** REPLACES tooManyJobsInWeek() ************************
	 * 
	 * Returns true if the immediate time frame surrounding the proposed job has
	 * already reached maximum jobs (Config.MAX_DENSE_JOBS), false otherwise. 
	 * 
	 * "Week" is += Config.IMMEDIATE_TIME_FRAME_DAYS days.
	 * 
	 * Precondition: theJob != null.
	 * 
	 * @return true if the immediate time frame surrounding the proposed job has
	 * already reached maximum jobs, false otherwise. 
	 */
	public boolean tooManyJobsNearJobTime(Job theJob) {
		GregorianCalendar weekStart = theJob.getFirstDate();
		weekStart.add(Calendar.DATE, -Config.IMMEDIATE_TIME_FRAME_DAYS);
		GregorianCalendar weekEnd = theJob.getLastDate();
		weekEnd.add(Calendar.DATE, Config.IMMEDIATE_TIME_FRAME_DAYS);
		int sameWeekJobs = 0;
		for (Job j: myJobs) {
			if (isJobInRange(j, weekStart, weekEnd)) {
				sameWeekJobs += 1;
			}
		}
		assert myJobs != null;
		return sameWeekJobs >= Config.MAX_DENSE_JOBS;
	}
	
	/**
	 * Returns true if part of the job falls within the inclusive 
	 * time range from theFirstDate to theEndDate, false otherwise.
	 * 
	 * Helper method for tooManyJobsNearJobTime.
	 * 
	 * Precondition: theJob != null, theFirstDate != null, theLastDate != null.
	 * 
	 * @param theJob The job being checked against the time range.
	 * @param theFirstDate The first day of the time range.
	 * @param theLastDate The last day of the time range.
	 * @return true if part of the job falls within the inclusive 
	 * time range from theFirstDate to theLastDate, false otherwise.
	 */
	private boolean isJobInRange(Job theJob, GregorianCalendar theFirstDate, GregorianCalendar theLastDate) {
		if (RulesHelp.isDateInRange(theJob.getFirstDate(), theFirstDate, theLastDate)) {
			return true;
		}
		if (RulesHelp.isDateInRange(theJob.getLastDate(), theFirstDate, theLastDate)) {
			return true;
		}
		return false;
	}
	
}
