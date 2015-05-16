package park_model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import config_files.Config;

public class PMAbilities {
	Collection<Job> myJobs;
	
	public PMAbilities(String fileName) {
		myJobs = JobSchedule.getAllFutureJobs(fileName);
	}
	
	public void addJob(Job theJob) {
		myJobs.add(theJob);
	}
	
	public Collection<User> getVolunteersForJob(Job theJob) {
		return theJob.getVolunteers();
	}
	
	public Collection<Job> getJobsAtPark(String thePark) {
		Collection<Job> jobsAtPark = new ArrayList<>();
		for (Job job: myJobs) {
			if (job.getParkName().equals(thePark)) {
				jobsAtPark.add(job);
			}
		}
		return jobsAtPark;
	}
	
	public boolean tooManyTotalJobs() {
		return myJobs.size() >= Config.MAX_TOTAL_JOBS;
	}
	
	/**
	 * ****************** REPLACES tooManyJobsInWeek() ************************
	 * 
	 * Returns true if the immediate time frame surrounding the proposed job has
	 * already reached maximum jobs, false otherwise. 
	 * 
	 * "Week" is += Config.IMMEDIATE_TIME_FRAME_DAYS days.
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
		return sameWeekJobs >= Config.MAX_DENSE_JOBS;
	}
	
	/**
	 * Returns true if part of the job falls within the inclusive 
	 * time range from theFirstDate to theEndDate, false otherwise.
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
