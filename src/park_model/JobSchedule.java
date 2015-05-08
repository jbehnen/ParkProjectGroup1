package park_model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import config_files.Config;

public class JobSchedule {
	
	/**
	 * The list of all jobs scheduled in the future.
	 */
	private Collection<Job> myJobs;
	
	/**
	 * Constructs a list of all jobs in the system from the back-end data.
	 */
	public JobSchedule() {
		// This constructor should also get rid of any jobs that are in the past.
		myJobs = new ArrayList<>(); // Will eventually get input from file
	}
	
	/**
	 * Constructor used to create an empty JobSchedule for testing purposes.
	 * 
	 * @param test Either boolean value; simply used to indicate that a 
	 * test JobSchedule should be constructed.
	 */
	public JobSchedule(boolean test) {
		myJobs = new ArrayList<>();
	}
	
	/**
	 * Adds a job to the list of jobs if it doesn't violate any business rules; returns true
	 * if the job is successfully added, false otherwise.
	 * 
	 * @param theJob The job to be added.
	 */
	public void addJob(Job theJob) {  
		myJobs.add(new Job(theJob));
	}
	
	/**
	 * Checks if the system will allow a new job
	 * to be scheduled based on system capacity.
	 * 
	 * @return true if there is space in the system to
	 * schedule a new job, false otherwise.
	 */
	public boolean tooManyExistingJobs() {
		if (myJobs.size() >= Config.MAX_TOTAL_JOBS) { 
			return true;
		}
		return false;
	}
	
	/**
	 * @param theJob
	 * @return
	 */
	public boolean isWeekFull(Job theJob) {
		GregorianCalendar weekStart = theJob.getFirstDate();
		weekStart.add(Calendar.DATE, -3);
		GregorianCalendar weekEnd = theJob.getLastDate();
		weekEnd.add(Calendar.DATE, 3);
		int sameWeekJobs = 0;
		for (Job j: myJobs) {
			if (isJobInRange(j, weekStart, weekEnd)) {
				sameWeekJobs += 1;
			}
		}
		if (sameWeekJobs >= Config.MAX_JOBS_PER_WEEK) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if part of the job falls within the inclusive 
	 * time range from theFirstDate to theEndDate, false otherwise.
	 * 
	 * @param theJob The job being checked against the time range.
	 * @param theFirstDate The first day of the time range.
	 * @param theEndDate The last day of the time range.
	 * @return true if part of the job falls within the inclusive 
	 * time range from theFirstDate to theEndDate, false otherwise.
	 */
	private boolean isJobInRange(Job theJob, GregorianCalendar theFirstDate, GregorianCalendar theEndDate) {
		for (GregorianCalendar thisDate: theJob.getDates()) {
			if (thisDate.compareTo(theFirstDate) > 0 && thisDate.compareTo(theEndDate) < 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the number of scheduled jobs.
	 * 
	 * @return The number of scheduled jobs. 
	 */
	public int numberOfJobs() {
		return myJobs.size();
	}
	
	/**
	 * Returns the number of scheduled jobs.
	 * 
	 * @return The number of scheduled jobs. 
	 */
	public int jobListSize() {
		return myJobs.size();
	}
	
	/**
	 * Returns an unmodifiable list of future jobs that the given volunteer is signed up for. 
	 * 
	 * @param theVolunteer The volunteer being searched for.
	 * @return An unmodifiable list of future jobs that the given volunteer is signed up for. 
	 * 	
	 */
	public List<Job> getUpcomingJobsByVolunteer(User theVolunteer) {
		
		List<Job> jobList = new ArrayList<Job>();

		// check if my job list is empty
		if (jobListSize() == 0) {
			System.out.print("Your job list is empty. ");
			return null;
		}
		for (Job myJob : myJobs) {
			if (myJob.isSignedUp(theVolunteer)) {
				// if true for that job check if job's start day is in the future				
				if (!myJob.isJobInPast()) {
					jobList.add(myJob);

				}

			}

		}	
		return Collections.unmodifiableList(jobList);
	}
	
	/**
	 * Returns an unmodifiable list of jobs that the volunteer can sign up for, excluding all jobs that violate business rules.
	 * 
	 * @param theVolunteer
	 * @return
	 */
	public List<Job> getJobsForSignUp(User theVolunteer) {
		// A volunteer may not sign up for a work catagory on a job if the max
		// number of volunteers for that
		// work catagoey has aleady been reached
		
		List<Job> jobList = new ArrayList<Job>();
		
		for (Job myJob : myJobs) {
			// Get jobs that are open
			if (myJob.getNumOpenJobs() > 0) {
				// Volunteer shouldn't sign up for the same job he already signed up
				if (!myJob.isSignedUp(theVolunteer)) {
					// check if the volunteer didn't sign up for other job on  the same date
					for (Job nextJob : getUpcomingJobsByVolunteer(theVolunteer)) {
						// Check if the not same date
						if (nextJob.getDates().get(0)
								.compareTo(myJob.getDates().get(0)) != 0) {

							jobList.add(myJob);

						}
						// System.out.print("You hava a job on this date");

					}

				}
				// System.out.print("You already signed up for this job");

			}
			// System.out.print("Is Full");

		}

		// volunteer can't sign up for two jobs on the same day
		return Collections.unmodifiableList(jobList);

	}
	
	/**
	 * Returns an unmodifiable list of future jobs scheduled at the given park.
	 * 
	 * @param thePark
	 * @return
	 */
	public List<Job> getJobsByPark(String thePark) {
		
		List<Job> jobList = new ArrayList<Job>();
		// check if my job list is empty
		if (jobListSize() == 0) {
			System.out.print("Your job list is empty. ");
			return null;
		}

		// For each job in the list, call the ask isSignedUp(Volunteer
		// theVolunteer)
		for (Job myJob : myJobs) {
			if (myJob.getParkName().equals(thePark)) {

				jobList.add(myJob);

			}

		}

		return Collections.unmodifiableList(jobList);

	}
	
	/**
	 * Writes all list information to the back-end storage.
	 */
	public void saveList() {
		// don't do this yet?
	}
	
	
}

