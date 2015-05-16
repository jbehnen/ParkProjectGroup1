package park_model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import config_files.Config;

public class JobSchedule {
	
	/**
	 * The list of all jobs scheduled in the future.
	 */
	@Deprecated
	private List<Job> myJobList;
	
	public static Collection<Job> getAllFutureJobs(String theFile) {
		Collection<Job> jobList = new ArrayList<>(); 
		String line;
		InputStream is = JobSchedule.class.getClassLoader().getResourceAsStream(theFile);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(is));
		try {

			while((line = fileReader.readLine()) != null) {
				Job job = Job.parseDelimitedString(line);
				if (!job.isJobInPast()) { // elimiates jobs from past
					jobList.add(job);
				}
			}

			fileReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobList;
	}
	
	public static void saveJobList(Collection<Job> theJobs, String theFile) {
		File file = new File(theFile);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), false);
			
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			for(Job job: theJobs) {
				bufferedWriter.write(job.createDelimitedString());
				bufferedWriter.newLine();
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructs a list of all jobs in the system from the back-end data.
	 */
	@Deprecated
	public JobSchedule(String jobFile) {
		myJobList = new ArrayList<>(); 
		String line;
		InputStream is = this.getClass().getResourceAsStream(jobFile);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(is));
		try {

			while((line = fileReader.readLine()) != null) {
				Job job = Job.parseDelimitedString(line);
				if (!job.isJobInPast()) { // elimiates jobs from past
					myJobList.add(job);
				}
			}

			fileReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes all list information to the back-end storage.
	 * 
	 * Adapted from http://www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/
	 */
	
	// park manager should call this with Config.JOB_SCHEDULE_FILE
	@Deprecated
	public void saveList(String theFile) {
		File file = new File(theFile);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), false);
			
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			for(Job job: myJobList) {
				bufferedWriter.write(job.createDelimitedString());
				bufferedWriter.newLine();
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor used to create an empty JobSchedule for testing purposes.
	 * 
	 * @param test Either boolean value; simply used to indicate that a 
	 * test JobSchedule should be constructed.
	 */
	@Deprecated
	public JobSchedule(boolean test) {
		myJobList = new ArrayList<>();
	}
	
	/**
	 * Adds a job to the list of jobs if it doesn't violate any business rules; returns true
	 * if the job is successfully added, false otherwise.
	 * 
	 * @param theJob The job to be added.
	 */
	@Deprecated
	public void addJob(Job theJob) {  
		myJobList.add(new Job(theJob));
	}
	
	/**
	 * Checks if the system will allow a new job
	 * to be scheduled based on system capacity.
	 * 
	 * @return true if there is space in the system to
	 * schedule a new job, false otherwise.
	 */
	@Deprecated
	public boolean tooManyExistingJobs() {
		if (myJobList.size() >= Config.MAX_TOTAL_JOBS) { 
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if the week surrounding the proposed job has
	 * already reached maximum jobs, false otherwise. 
	 * 
	 * "Week" is += 3 days.
	 * 
	 * @return true if the week surrounding the proposed job has
	 * already reached maximum jobs, false otherwise. 
	 */
	@Deprecated 
	public boolean isWeekFull(Job theJob) {
		GregorianCalendar weekStart = theJob.getFirstDate();
		weekStart.add(Calendar.DATE, -3);
		GregorianCalendar weekEnd = theJob.getLastDate();
		weekEnd.add(Calendar.DATE, 3);
		int sameWeekJobs = 0;
		for (Job j: myJobList) {
			if (isJobInRange(j, weekStart, weekEnd)) {
				sameWeekJobs += 1;
			}
		}
		if (sameWeekJobs >= Config.IMMEDIATE_TIME_FRAME_DAYS) {
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
	@Deprecated // alternate version - isDateInRange - in RulesHelp
	private boolean isJobInRange(Job theJob, GregorianCalendar theFirstDate, GregorianCalendar theEndDate) {
		for (GregorianCalendar thisDate: theJob.getDates()) {
			if (thisDate.compareTo(theFirstDate) >= 0 && thisDate.compareTo(theEndDate) <= 0) {
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
	@Deprecated
	public int numberOfJobs() {
		return myJobList.size();
	}

	/**
	 * Returns true if the first day (Job start date) is in the future.
	 * 
	 * @param theStartDate
	 *            - job start date
	 * @return -true in the future
	 */
	@Deprecated
	private boolean isJobInFuture(GregorianCalendar theStartDate) {
		// get current date time with Date()
		GregorianCalendar currentDate = new GregorianCalendar();
        //Start date is after the current date
		if (theStartDate.compareTo(currentDate) > 0) {

			return true;
		}
		return false;

	}	
	
	/**
	 * Returns an unmodifiable list of future jobs that the given volunteer is signed up for. 
	 * 
	 * @param theVolunteer The volunteer being searched for.
	 * @return An unmodifiable list of future jobs that the given volunteer is signed up for. 
	 * 	
	 */
	@Deprecated
	public List<Job> getUpcomingJobsByVolunteer(User theVolunteer) {
		
		Set<Job> jobSet = new HashSet<Job>();

		// check if my job list is empty
		if (numberOfJobs() == 0) {
			System.out.print("Your job list is empty. ");
			return null;
		}
		for (Job myJob : myJobList) {
			if (myJob.isSignedUp(theVolunteer)) {
				
				// FRIENDLY HELLO FROM JULIA
				// I TAKE CARE OF THIS WHEN I LOAD IN THE JOBS
				// ALL JOBS IN THE LIST ARE IN THE FUTURE
				// IT'S ALL GOOD. :) 
				
				// if true for that job check if job's start day is in the future				
				if (isJobInFuture(myJob.getDates().get(0))) {
					jobSet.add(myJob);

				}

			}

		}	
		
		List<Job> jobList = new ArrayList<Job>();
		for (Job job: jobSet) {
			jobList.add(job);
		}
		return Collections.unmodifiableList(jobList);
	}
	
	/**
	 * Returns an unmodifiable list of jobs that the volunteer can sign up for, excluding all jobs that violate business rules.
	 * 
	 * @param theVolunteer
	 * @return
	 */
	@Deprecated
	public List<Job> getJobsForSignUp(User theVolunteer) {
		// A volunteer may not sign up for a work catagory on a job if the max
		// number of volunteers for that
		// work catagoey has aleady been reached
		
		Set<Job> jobSet = new HashSet<Job>();
		
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

							jobSet.add(myJob);

						}
						// System.out.print("You hava a job on this date");

					}

				}
				// System.out.print("You already signed up for this job");

			}
			// System.out.print("Is Full");

		}

		// volunteer can't sign up for two jobs on the same day
		
		List<Job> jobList = new ArrayList<Job>();
		for (Job job: jobSet) {
			jobList.add(job);
		}
		
		return Collections.unmodifiableList(jobList);

	}
	
	/**
	 * Returns an unmodifiable list of future jobs scheduled at the given park.
	 * 
	 * @param thePark
	 * @return
	 */
	@Deprecated
	public List<Job> getJobsByPark(String thePark) {
		
		List<Job> jobList = new ArrayList<Job>();
		// check if my job list is empty
		if (numberOfJobs() == 0) {
			System.out.print("Your job list is empty. ");
			return null;
		}

		// For each job in the list, call the ask isSignedUp(Volunteer
		// theVolunteer)
		for (Job myJob : myJobList) {
			if (myJob.getParkName().equals(thePark)) {

				jobList.add(myJob);

			}

		}

		return Collections.unmodifiableList(jobList);

	}
	
	
}