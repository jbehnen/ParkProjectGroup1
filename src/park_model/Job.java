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

public class Job implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 256786873250255803L;
	
	public final int LIGHT_JOBS;
	public final int MEDIUM_JOBS;
	public final int HEAVY_JOBS;
	
	// Class Variables
	private String myPark;
	private GregorianCalendar startDate;
	private int numDays;
	private List<User> myVolunteers; // Use a Set
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
	 * Returns true if theVolunteer was successfully added, false if they were
	 * not added due to a violation of business rules.
	 * 
	 * @param theVolunteer
	 * @return boolean value
	 */

	/**
	 * signUp method
	 * 
	 * Signs up the user if the work category is still available. 
	 * @param theVolunteer
	 * @param theCategory
	 */
	
	/*Precondition: caller must check to see if Volunteer is already signed up*/
	/*Precondition: caller must check to see if the Work Category is still available*/
	public void signUp(User theVolunteer, WorkCategory theCategory) {
				myVolunteers.add(theVolunteer);
				if (theCategory == WorkCategory.LIGHT) {
					numLightJobs++;
				} else if (theCategory == WorkCategory.MEDIUM) {
					numMediumJobs++;
				} else {
					numHeavyJobs++;
				}
	}

	/**
	 * Returns true if theVolunteer is signed up for the job, false otherwise.
	 * 
	 * @param theVolunteer
	 * @return boolean
	 */
	public boolean isSignedUp(User theVolunteer) {
		return myVolunteers.contains(theVolunteer);
	}

	/**
	 * getParkName Returns the name of the Park
	 */
	public String getParkName() {
		return myPark;
	}

	/**
	 * getNumOpen method
	 * 
	 * @param theCategory
	 * @return int the number of open jobs for a given category
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
		return wc;
	}


	/**
	 * isOpen method Returns true if the job category is open.
	 * 
	 * @param theCategory
	 * @return
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
	 * getFirstDate method Returns the first date of the job.
	 * 
	 * @return Gregorian Calendar the first date of the job
	 */
	public GregorianCalendar getFirstDate() {
		return (GregorianCalendar) startDate.clone();
	}

	/**
	 * Returns the last date of the job.
	 * 
	 * @return The last date of the job.
	 */
	public GregorianCalendar getLastDate() {
		GregorianCalendar temp = (GregorianCalendar) startDate.clone();
		temp.add(Calendar.DAY_OF_MONTH, numDays-1);
		return temp;
	}

	/**
	 * getNumDays method Returns the length of a job
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
	 * dateString method converts the GregorianCalendar date to a string: month,
	 * day, year
	 * 
	 * @param theDate
	 * @return String
	 */
	private String dateString(GregorianCalendar theDate) {
		return (theDate.get(Calendar.MONTH) + 1) + "/" + theDate.get(Calendar.DATE)
				+ "/" + theDate.get(Calendar.YEAR);
	}

	/**
	 * toString method
	 * 
	 * @return a String representation of the Job object
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

}