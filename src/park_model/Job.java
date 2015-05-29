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

<<<<<<< Updated upstream
	/**
	 * 
	 */
	private static final long serialVersionUID = 256786873250255803L;
	
	public final int LIGHT_JOBS;
	public final int MEDIUM_JOBS;
	public final int HEAVY_JOBS;
	
=======
	private static final long serialVersionUID = -4056472263186921581L; 

>>>>>>> Stashed changes
	// Class Variables
	private String myPark;
	private GregorianCalendar startDate;
	private int numDays;
	private List<User> myVolunteers; // Use a Set
	private int numLightJobs;
	private int numMediumJobs;
	private int numHeavyJobs;
	private String myDescription;
	
	public final int LIGHT_JOBS;
	public final int MEDIUM_JOBS;
	public final int HEAVY_JOBS;

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
<<<<<<< Updated upstream
		}

=======
	}
>>>>>>> Stashed changes

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
	 * signUp method
	 * 
	 * Signs up the user if the work category is still available. 
	 * @param theVolunteer
	 * @param theCategory
	 */
	/*Precondition: caller must check to see if Volunteer is already signed up*/
	/*Precondition: caller must check to see if the Work Category is still available*/
	public void signUp(User theVolunteer, WorkCategory theCategory) {
<<<<<<< Updated upstream
				myVolunteers.add(theVolunteer);
				if (theCategory == WorkCategory.LIGHT) {
					numLightJobs++;
				} else if (theCategory == WorkCategory.MEDIUM) {
					numMediumJobs++;
				} else {
					numHeavyJobs++;
				}
=======
		myVolunteers.add(theVolunteer);
		if (theCategory == WorkCategory.LIGHT) {
			numLightJobs++;
		} else if (theCategory == WorkCategory.MEDIUM) {
			numMediumJobs++;
		} else {
			numHeavyJobs++;
		}
>>>>>>> Stashed changes
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
				+ "Light: " + numLightJobs + ", " + "Medium: " + numMediumJobs
				+ ", " + "Heavy: " + numHeavyJobs;
	}
<<<<<<< Updated upstream
	
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
=======

	
	///////////////////////DEPRECATED METHODS/////////////////////
	
	@Deprecated
	public int getNumLight() {
		return numLightJobs;
	}

	@Deprecated
	public int getNumMedium() {
		return numMediumJobs;
	}

	@Deprecated
	public int getNumHeavy() {
		return numHeavyJobs;
	}

	@Deprecated
	public int getNumOpenJobs() {
		return (numLightJobs + numMediumJobs + numHeavyJobs);
	}

	@Deprecated
	public String getDescription() {
		return myDescription;
	}

	@Deprecated
	public static Job parseDelimitedString(String theString) {
		String[] jobInfo = theString.split("DELIM");
		List<User> volunteers = new ArrayList<>();
		for (int i = 9; i < jobInfo.length; i++) {
			volunteers.add(User.parseDelimitedString(jobInfo[i]));
		}
		return new Job(jobInfo[0], Integer.parseInt(jobInfo[1]),
				Integer.parseInt(jobInfo[2]), Integer.parseInt(jobInfo[3]),
				Integer.parseInt(jobInfo[4]), Integer.parseInt(jobInfo[5]),
				Integer.parseInt(jobInfo[6]), Integer.parseInt(jobInfo[7]),
				jobInfo[8], volunteers);
	}

	@Deprecated
	public String createDelimitedString() {
		StringBuilder string = new StringBuilder();
		string.append(myPark);
		string.append("DELIM" + getFirstDate().get(Calendar.YEAR));
		string.append("DELIM" + getFirstDate().get(Calendar.MONTH));
		string.append("DELIM" + getFirstDate().get(Calendar.DATE));
		string.append("DELIM" + myDates.size());
		string.append("DELIM" + numLightJobs);
		string.append("DELIM" + numMediumJobs);
		string.append("DELIM" + numHeavyJobs);
		string.append("DELIM" + myDescription);
		for (User vol : myVolunteers) {
			string.append("DELIM" + vol.toDelimitedStringVolunteer());
		}
		return string.toString();
	}

	@Deprecated
	// moved to RulesHelp
	public boolean isJobInPast() {
		GregorianCalendar today = Config.getTodaysDate();
		if (getFirstDate().compareTo(today) < 0) {
			return true;
		}
		return false;
	}

	@Deprecated
	// moved to RulesHelp
	public boolean isJobTooFarInFuture() {
		GregorianCalendar futureBound = Config.getTodaysDate();
		futureBound.add(Calendar.DATE, Config.MAX_DAYS_IN_FUTURE);
		if (getLastDate().compareTo(futureBound) > 0) {
			return true;
		}
		return false;
	}

	@Deprecated
	public Job(String thePark, List<GregorianCalendar> theDates,
			int theLightNum, int theMediumNum, int theHeavyNum,
			String theDescription) {
		myPark = thePark;
		numLightJobs = 0;
		LIGHT_JOBS = theLightNum;
		numMediumJobs = 0;
		MEDIUM_JOBS = theMediumNum;
		numHeavyJobs = 0;
		HEAVY_JOBS = theHeavyNum;
		myVolunteers = new ArrayList<User>();
		myDescription = theDescription;
		myDates = new ArrayList<GregorianCalendar>();

		for (int i = 0; i < theDates.size(); i++) {
			myDates.add(theDates.get(i));
		}
		assert myVolunteers != null;
	}

	@Deprecated
	public Job(String thePark, int theYear, int theMonth, int theDate,
			int theNumDays, int theLightNum, int theMediumNum, int theHeavyNum,
			String theDescription, List<User> theVolunteers) {
		myPark = thePark;
		numLightJobs = 0;
		LIGHT_JOBS = theLightNum;
		numMediumJobs = 0;
		MEDIUM_JOBS = theMediumNum;
		numHeavyJobs = 0;
		HEAVY_JOBS = theHeavyNum;
		myVolunteers = theVolunteers; // okay because volunteer is immutable,
										// and we
										// actually want to modify this list
		myDescription = theDescription;
		GregorianCalendar startDate = new GregorianCalendar(theYear, theMonth,
				theDate);
		startDate.get(Calendar.MILLISECOND); // reset fields after assignment
		myDates = new ArrayList<>();
		for (int i = 0; i < theNumDays; i++) {
			myDates.add((GregorianCalendar) startDate.clone());
			startDate.add(Calendar.DATE, 1);
		}
		assert myVolunteers != null;
	}

	@Deprecated
	public List<WorkCategory> getAvailableWorkCategories() {
		List<WorkCategory> wcList = new ArrayList<WorkCategory>();

		// Checks each Work Category for availability.
		if (numLightJobs > 0) {
			wcList.add(WorkCategory.LIGHT);
		}
		if (numMediumJobs > 0) {
			wcList.listIterator().add(WorkCategory.MEDIUM);
		}
		if (numHeavyJobs > 0) {
			wcList.add(WorkCategory.HEAVY);
		}

		return Collections.unmodifiableList(wcList);
	}

	@Deprecated
	// this can now happen in the ParkManagerIO
	public boolean isJobTooLong() {
		List<GregorianCalendar> dates = getDates();
		int numDates = dates.size();
		if (numDates > Config.MAX_JOB_DAYS) {
>>>>>>> Stashed changes
			return true;
		}
		return false;
	}

}