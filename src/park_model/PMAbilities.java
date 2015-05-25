package park_model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import config_files.Config;

/**
 * Provides the methods that allow ParkManagers to complete
 * their user stories and access the list of jobs.
 * 
 * @author Julia Behnen
 * @version 5/16/2015
 */
public class PMAbilities {
	
	List<Job> myJobs;
	
	/**
	 * Constructs the PMAbilities and gets all of the jobs
	 * stored in the given file.
	 * 
	 * Preconditions: theFileName != null.
	 * 
	 * @param theFileName The name of the file that holds the
	 * jobs that the ParkManager should be able to 
	 * access.
	 */
	public PMAbilities(String theFileName) {
		myJobs = JobSchedule.getAllFutureJobs(theFileName);
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
	int getNumJobs() {
		return myJobs.size();
	}
	
	/**
	 * Returns the volunteers signed up for a given job.
	 * 
	 * Preconditions: theJob != null.
	 * 
	 * @return the volunteers signed up for a given job.
	 */
	public List<User> getVolunteersForJob(Job theJob) {
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
	public List<Job> getJobsAtPark(String thePark) {
		List<Job> jobsAtPark = new ArrayList<>();
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
	public void saveJobs(String theFileName) {
		JobSchedule.saveJobList(myJobs, theFileName);
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
		for (Job job: myJobs) {
			if (job.isJobInRange(weekStart, weekEnd)) {
				sameWeekJobs += 1;
			}
		}
		assert myJobs != null;
		return sameWeekJobs >= Config.MAX_DENSE_JOBS;
	}
	
}
