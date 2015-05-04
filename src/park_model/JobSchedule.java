package park_model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class JobSchedule {
	
	/**
	 * The maximum number of jobs that can be scheduled at any given time.
	 */
	private static final int MAX_JOBS = 30;
	
	/**
	 * The maximum job length in days.
	 */
	private static final int MAX_JOB_DAYS = 2;
	
	/**
	 * 
	 */
	private static final int MAX_JOBS_PER_WEEK = 5;
	
	/**
	 * The list of all jobs scheduled in the future.
	 */
	private List<Job> myJobList;
	
	/**
	 * The list of all jobs scheduled in the future for a specific volunteer.
	 */
	private List<Job> myJobListByVolunteer;
	
	/**
	 * The list of all jobs scheduled in the future for a specific volunteer.
	 */
	private List<Job> myJobListByPark;
	
	/**
	 * The list of all jobs scheduled in the future for a specific volunteer.
	 */
	private List<Job> myJobListForSignup;
	
	/**
	 * Constructs a list of all jobs in the system from the back-end data.
	 */
	public JobSchedule() {
		// This constructor should also get rid of any jobs that are in the past.
		myJobList = new ArrayList<>(); // Gets input from file
		myJobListByVolunteer = new ArrayList<Job>();
		myJobListByPark = new ArrayList<Job>();
		myJobListForSignup= new ArrayList<Job>();
	    
	}
	
	/**
	 * Constructor used to create an empty JobSchedule for testing purposes.
	 * 
	 * @param test Either boolean value; simply used to indicate that a 
	 * test JobSchedule should be constructed.
	 */
	public JobSchedule(boolean test) {
		//myJobList = new ArrayList<>();
	}
	
	/**
	 * Adds a job to the list of jobs if it doesn't violate any business rules; returns true
	 * if the job is successfully added, false otherwise.
	 *
	 * @return true if the job is successfully added, false otherwise
	 * @param theJob
	 */
	public boolean addJob(Job theJob) {  // need to use the copy constructor or something
		// check if too many jobs already scheduled
		if (jobListSize() > MAX_JOBS) { 
			return false;
		}
		
		//check if job is more than the max number of days
		List<Date> dates = theJob.getDates();
		int numDates = dates.size();
		if (numDates > MAX_JOB_DAYS) {
			return false;
		}
		
		GregorianCalendar firstDate, lastDate;
		firstDate = new GregorianCalendar();
		firstDate.setTime(dates.get(0));
		firstDate = roundDown(firstDate);
		
		// check if job is out of acceptable time range
		if (!isInTimeRange(firstDate)) {
			return false;
		}
		
		// check if too many jobs within the week
		lastDate = new GregorianCalendar();
		
		if (numDates > 1) {
			lastDate.setTime(dates.get(numDates - 1));
			lastDate = roundDown(lastDate);
		} else {
			lastDate = (GregorianCalendar) firstDate.clone();
		}
		
		firstDate.add(Calendar.DATE, 3);
		lastDate.add(Calendar.DATE, 4); // adds 4 days worth of millis: 3 to move forward 3
											// days, 1 to move time to very end of the last day
		int sameWeekJobs = 0;
		for (Job j: myJobList) {
			if (isJobInRange(j, firstDate, lastDate)) {
				sameWeekJobs += 1;
			}
		}
		if (sameWeekJobs >= MAX_JOBS_PER_WEEK) {
			return false;
		}
		myJobList.add(new Job(theJob));
		return true;
	}
	
	private GregorianCalendar roundDown(GregorianCalendar theDate) {
		theDate.set(Calendar.HOUR, 0);
		theDate.set(Calendar.MINUTE, 0);
		theDate.set(Calendar.SECOND, 0);
		theDate.set(Calendar.MILLISECOND, 0);
		return theDate;
	}
	
	/**
	 * Returns true if the given date is neither in the past nor more than 90 days in the
	 * future, false otherwise.
	 * 
	 * @param theDate The date being checked.
	 * @return true if the given date is neither in the past nor more than 90 days in the
	 * future, false otherwise.
	 */
	private boolean isInTimeRange(GregorianCalendar theDate) {
		GregorianCalendar comparisonDate = new GregorianCalendar();
		comparisonDate = roundDown(comparisonDate);
		if (theDate.compareTo(comparisonDate) < 0) {
			return false;
		}
		comparisonDate.add(Calendar.DATE, 90);
		if (theDate.compareTo(comparisonDate) > 0) {
			return false;
		}
		return true;
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
		for (Date date: theJob.getDates()) {
			GregorianCalendar thisDate = new GregorianCalendar();
			thisDate.setTime(date);
			if (thisDate.compareTo(theFirstDate) > 0 && thisDate.compareTo(theEndDate) < 0) {
				return true;
			}
		}
		return true;
	}

	/**
	 * Returns true if the first day (Job start date) is in the future.
	 * 
	 * @param theStartDate
	 *            - job start date
	 * @return -true in the future
	 */
	private boolean isJobInFuture(Date theStartDate) {
		// get current date time with Date()
		Date currentDate = new Date();
        //Start date is after the current date
		if (theStartDate.compareTo(currentDate) > 0) {

			return true;
		}
		return false;

	}	
	
	/**
	 * Returns the number of scheduled jobs.
	 * 
	 * @return The number of scheduled jobs. 
	 */
	public int jobListSize() {
		return myJobList.size();
	}
	
	/**
	 * Returns an unmodifiable list of future jobs that the given volunteer is signed up for. 
	 * 
	 * @param theVolunteer The volunteer being searched for.
	 * @return An unmodifiable list of future jobs that the given volunteer is signed up for. 
	 * 	
	 */
	public List<Job> getUpcomingJobsByVolunteer(Volunteer theVolunteer) {

		// check if my job list is empty
		if (jobListSize() == 0) {
			System.out.print("Your job list is empty. ");
			return null;
		}
		for (Job myJob : myJobList) {
			if (myJob.isSignedUp(theVolunteer)) {
				// if true for that job check if job's start day is in the future				
				if (isJobInFuture(myJob.getDates().get(0))) {
					myJobListByVolunteer.add(myJob);

				}

			}

		}	
		return Collections.unmodifiableList(myJobListByVolunteer);
	}
	
	/**
	 * Returns an unmodifiable list of jobs that the volunteer can sign up for, excluding all jobs that violate business rules.
	 * 
	 * @param theVolunteer
	 * @return
	 */
	public List<Job> getJobsForSignUp(Volunteer theVolunteer) {
		// A volunteer may not sign up for a work catagory on a job if the max
		// number of volunteers for that
		// work catagoey has aleady been reached
		for (Job myJob : myJobList) {
			// Get jobs that are open
			if (myJob.getNumOpenJobs() > 0) {
				// Volunteer shouldn't sign up for the same job he already signed up
				if (!myJob.isSignedUp(theVolunteer)) {
					// check if the volunteer didn't sign up for other job on  the same date
					for (Job nextJob : getUpcomingJobsByVolunteer(theVolunteer)) {
						// Check if the not same date
						if (nextJob.getDates().get(0)
								.compareTo(myJob.getDates().get(0)) != 0) {

							myJobListForSignup.add(myJob);

						}
						// System.out.print("You hava a job on this date");

					}

				}
				// System.out.print("You already signed up for this job");

			}
			// System.out.print("Is Full");

		}

		// volunteer can't sign up for two jobs on the same day
		return Collections.unmodifiableList(myJobListForSignup);

	}
	
	/**
	 * Returns an unmodifiable list of future jobs scheduled at the given park.
	 * 
	 * @param thePark
	 * @return
	 */
	public List<Job> getJobsByPark(String thePark) {
		// check if my job list is empty
		if (jobListSize() == 0) {
			System.out.print("Your job list is empty. ");
			return null;
		}

		// For each job in the list, call the ask isSignedUp(Volunteer
		// theVolunteer)
		for (Job myJob : myJobList) {
			if (myJob.getParkName().equals(thePark)) {

				myJobListByPark.add(myJob);

			}

		}

		return Collections.unmodifiableList(myJobListByPark);

	}
	
	/**
	 * Writes all list information to the back-end storage.
	 */
	public void saveList() {
		// don't do this yet?
	}
	
	
}

