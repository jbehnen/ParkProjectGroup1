/**
 * Job class
 * Creates a job
 * Author: LA Hamaker
 * Last Edit: 21 May 2015
 * 
 */
package park_model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import config_files.Config;

/**
 * Represents a Job.
 * 
 * @author Lisa
 * @version 5/31/2015
 */
//Invariant: myPark != null, startDate != null, 0 < numDays <= Config.MAX_JOB_DAYS,
// myVolunteers != null, numLightJobs >= 0, numMediumJobs >= 0, numHeavyJobs >= 0, 
// myDescription != null.
public class Job implements Serializable {

	private static final long serialVersionUID = 256786873250255803L;
	
	public final int LIGHT_JOBS;
	public final int MEDIUM_JOBS;
	public final int HEAVY_JOBS;
	
	// Class Variables
	private String myPark;
	private GregorianCalendar startDate;
	private int numDays;
	private List<User> myVolunteers;
	private int numLightJobs;
	private int numMediumJobs;
	private int numHeavyJobs;
	private String myDescription;

	/**
	 * Class Constructor Creates an instance of a Job.
	 * 
	 * @param thePark
	 *            the location of a job as a park
	 * @param theStartDate
	 *            the date(s) of a job
	 * @param theNumDays
	 *            the duration of the job in days
	 * @param theLightNum
	 *            the number of jobs in the work category Light that is
	 *            available.
	 * @param theMediumNum
	 *            the number of jobs in the work category Medium that is
	 *            available.
	 * @param theHeavyNum
	 *            the number of jobs in the work category Heavy that is
	 *            available.
	 * @param theDescription
	 *            the description of the job
	 */
	public Job(String thePark, GregorianCalendar theStartDate, int theNumDays,
			int theLightNum, int theMediumNum, int theHeavyNum,
			String theDescription) {

		myPark = thePark;
		startDate = theStartDate;
		numDays = theNumDays;
		LIGHT_JOBS = theLightNum;
		numLightJobs = 0;
		MEDIUM_JOBS = theMediumNum;
		numMediumJobs = 0;
		HEAVY_JOBS = theHeavyNum;
		numHeavyJobs = 0;
		myDescription = theDescription;
		myVolunteers = new ArrayList<User>();
		
		assert myPark != null;
		assert numDays > 0;
		assert numDays <= Config.MAX_JOB_DAYS;
		assert numLightJobs >= 0;
		assert numMediumJobs >= 0;
		assert numHeavyJobs >= 0;
		assert myDescription != null;
		assert myVolunteers != null;
	}


	/**
	 * Copy Constructor Creates a copy of a job passed in as a parameter.
	 * 
	 * @param theJob
	 *            the job to be copied.
	 */

	public Job(Job theJob) {
		this(theJob.getParkName(), ((GregorianCalendar) theJob.startDate),
				theJob.numDays, theJob.LIGHT_JOBS, theJob.MEDIUM_JOBS,
				theJob.HEAVY_JOBS, theJob.myDescription);
		myVolunteers = new ArrayList<>(theJob.myVolunteers);

		assert myPark != null;
		assert numDays > 0;
		assert numDays <= Config.MAX_JOB_DAYS;
		assert numLightJobs >= 0;
		assert numMediumJobs >= 0;
		assert numHeavyJobs >= 0;
		assert myDescription != null;
		assert myVolunteers != null;
	}

	/**
	 * Returns an unmodifiable list of the volunteers signed up for the job.
	 * 
	 * @return a list of volunteers
	 */
	public List<User> getVolunteers() {
		return Collections.unmodifiableList(myVolunteers);
	}

	/**
	 * Signs up the user for the job in the given work category. 
	 * 
	 * Preconditions: theVolunteer != null, !isSignedUp(theVolunteer),
	 * isOpen(theCategory).
	 */
	public void signUp(User theVolunteer, WorkCategory theCategory) {
		myVolunteers.add(theVolunteer);
		if (theCategory == WorkCategory.LIGHT) {
			numLightJobs++;
		} else if (theCategory == WorkCategory.MEDIUM) {
			numMediumJobs++;
		} else {
			numHeavyJobs++;
		}
		
		assert myPark != null;
		assert numDays > 0;
		assert numDays <= Config.MAX_JOB_DAYS;
		assert numLightJobs >= 0;
		assert numMediumJobs >= 0;
		assert numHeavyJobs >= 0;
		assert myDescription != null;
		assert myVolunteers != null;
	}

	/**
	 * Returns true if theVolunteer is signed up for the job, false otherwise.
	 * 
	 * Preconditions: theVolunteer != null.
	 * 
	 * @param theVolunteer
	 * @return boolean
	 */
	public boolean isSignedUp(User theVolunteer) {
		return myVolunteers.contains(theVolunteer);
	}

	/**
	 * Returns the name of the park the job is at.
	 * 
	 * @return the name of the park the job is at.
	 */
	public String getParkName() {
		return myPark;
	}

	/**
	 * Returns the number of open jobs in the work category.
	 * 
	 * @param theCategory The category which is being checked 
	 * for number of open jobs.
	 * @return the number of open jobs for the work category.
	 */
	public int getNumOpen(WorkCategory theCategory) {
		int wc = 0;

		if (theCategory == WorkCategory.LIGHT) {
			wc = LIGHT_JOBS - numLightJobs;
		} else if (theCategory == WorkCategory.MEDIUM) {
			wc = MEDIUM_JOBS - numMediumJobs;
		} else {
			wc = HEAVY_JOBS - numHeavyJobs;
		}
		
		assert myPark != null;
		assert numDays > 0;
		assert numDays <= Config.MAX_JOB_DAYS;
		assert numLightJobs >= 0;
		assert numMediumJobs >= 0;
		assert numHeavyJobs >= 0;
		assert myDescription != null;
		assert myVolunteers != null;
		
		return wc;
	}


	/**
	 * Returns true if theCategory is open, false otherwise.
	 * 
	 * @return true if theCategory is open, false otherwise.
	 */
	public boolean isOpen(WorkCategory theCategory) {
		if (theCategory == WorkCategory.LIGHT) {
			return numLightJobs < LIGHT_JOBS;
		} else if (theCategory == WorkCategory.MEDIUM) {
			return numMediumJobs < MEDIUM_JOBS;
		} else {
			return numHeavyJobs < HEAVY_JOBS;
		}
	}

	/**
	 * Returns the first date of the job.
	 * 
	 * @return the first date of the job
	 */
	public GregorianCalendar getFirstDate() {
		return (GregorianCalendar) startDate.clone();
	}

	/**
	 * Returns the last date of the job.
	 * 
	 * @return the last date of the job.
	 */
	public GregorianCalendar getLastDate() {
		GregorianCalendar temp = (GregorianCalendar) startDate.clone();
		temp.add(Calendar.DAY_OF_MONTH, numDays-1);
		
		assert myPark != null;
		assert numDays > 0;
		assert numDays <= Config.MAX_JOB_DAYS;
		assert numLightJobs >= 0;
		assert numMediumJobs >= 0;
		assert numHeavyJobs >= 0;
		assert myDescription != null;
		assert myVolunteers != null;
		
		return temp;
	}

	/**
	 * Returns the length of the job in days.
	 * 
	 * @return the length of the job in days.
	 */
	public int getNumDays() {
		return numDays;
	}

	/**
	 * @see java.lang.Object#hashCode(java.lang.Object)
	 * 
	 * Uses all fields except myVolunteers.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + HEAVY_JOBS;
		result = prime * result + LIGHT_JOBS;
		result = prime * result + MEDIUM_JOBS;
		result = prime * result
				+ ((myDescription == null) ? 0 : myDescription.hashCode());
		result = prime * result + ((myPark == null) ? 0 : myPark.hashCode());
		result = prime * result + numDays;
		result = prime * result + numHeavyJobs;
		result = prime * result + numLightJobs;
		result = prime * result + numMediumJobs;
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		
		assert myPark != null;
		assert numDays > 0;
		assert numDays <= Config.MAX_JOB_DAYS;
		assert numLightJobs >= 0;
		assert numMediumJobs >= 0;
		assert numHeavyJobs >= 0;
		assert myDescription != null;
		assert myVolunteers != null;
		
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * Uses all fields except myVolunteers.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (HEAVY_JOBS != other.HEAVY_JOBS)
			return false;
		if (LIGHT_JOBS != other.LIGHT_JOBS)
			return false;
		if (MEDIUM_JOBS != other.MEDIUM_JOBS)
			return false;
		if (myDescription == null) {
			if (other.myDescription != null)
				return false;
		} else if (!myDescription.equals(other.myDescription))
			return false;
		if (myPark == null) {
			if (other.myPark != null)
				return false;
		} else if (!myPark.equals(other.myPark))
			return false;
		if (numDays != other.numDays)
			return false;
		if (numHeavyJobs != other.numHeavyJobs)
			return false;
		if (numLightJobs != other.numLightJobs)
			return false;
		if (numMediumJobs != other.numMediumJobs)
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	/**
	 * Returns a string that shows a GregorianCalendar as month/date/year.
	 * 
	 * Precondition: theDate != null.
	 * 
	 * @return a string that shows a GregorianCalendar as month/date/year.
	 */
	private String dateString(GregorianCalendar theDate) {
		return (theDate.get(Calendar.MONTH) + 1) + "/" + theDate.get(Calendar.DATE)
				+ "/" + theDate.get(Calendar.YEAR);
	}

	/**
	 * Returns a String representation of the Job object.
	 * 
	 * @return a String representation of the Job object.
	 */
	@Override
	public String toString() {
		return myPark + " " + dateString(getFirstDate()) + "-"
				+ dateString(getLastDate()) + ", " + myDescription + " "
				+ "Light: " + numLightJobs + "/" + LIGHT_JOBS + ", " + "Medium: " + numMediumJobs + 
				"/" + MEDIUM_JOBS + ", " + "Heavy: " + numHeavyJobs + "/" + HEAVY_JOBS;
	}
	
	/**
	 * Returns true if part of the job falls within the inclusive 
	 * time range from theFirstDate to theEndDate, false otherwise.
	 * 
	 * Precondition: theFirstDate != null, theLastDate != null.
	 * 
	 * @param theFirstDate The first day of the time range.
	 * @param theLastDate The last day of the time range.
	 * @return true if part of the job falls within the inclusive 
	 * time range from theFirstDate to theLastDate, false otherwise.
	 */
	public boolean isJobInRange(GregorianCalendar theFirstDate,
			GregorianCalendar theLastDate) {
		if (RulesHelp.isDateInRange(getFirstDate(), theFirstDate, theLastDate)) {
			return true;
		}
		if (RulesHelp.isDateInRange(getLastDate(), theFirstDate, theLastDate)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if no work categories are open, false otherwise. 
	 * 
	 * @return true if no work categories are open, false otherwise. 
	 */
	public boolean isFull() {
		if (isOpen(WorkCategory.LIGHT) || isOpen(WorkCategory.MEDIUM) || isOpen(WorkCategory.HEAVY)) {
			return false;
		}
		return true;
	}

}