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

public class Job implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4056472263186921581L;

	// Class Variables
	private String myPark;
	private GregorianCalendar startDate;
	private List<GregorianCalendar> myDates;
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
		numLightJobs = theLightNum;
		numMediumJobs = theMediumNum;
		numHeavyJobs = theHeavyNum;
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
				theJob.numDays, theJob.numLightJobs, theJob.numMediumJobs,
				theJob.numHeavyJobs, theJob.myDescription);
		myVolunteers = theJob.getVolunteers();

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
					numLightJobs--;
				} else if (theCategory == WorkCategory.MEDIUM) {
					numMediumJobs--;
				} else {
					numHeavyJobs--;
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
			wc = numLightJobs;
		} else if (theCategory == WorkCategory.MEDIUM) {
			wc = numMediumJobs;
		} else {
			wc = numHeavyJobs;
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
		return (getNumOpen(theCategory)) > 0;
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
	 * hashCode method returns a hashcode representation of the object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result
				+ ((myDescription == null) ? 0 : myDescription.hashCode());
		result = prime * result + ((myPark == null) ? 0 : myPark.hashCode());
		result = prime * result
				+ ((myVolunteers == null) ? 0 : myVolunteers.hashCode());
		result = prime * result + numHeavyJobs;
		result = prime * result + numLightJobs;
		result = prime * result + numMediumJobs;
		return result;
	}

	/**
	 * equals method compares two objects returns true if the objects are the
	 * same, false otherwise.
	 * 
	 * @param Object
	 *            obj
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
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
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
		if (myVolunteers == null) {
			if (other.myVolunteers != null)
				return false;
		} else if (!myVolunteers.equals(other.myVolunteers))
			return false;
		if (numHeavyJobs != other.numHeavyJobs)
			return false;
		if (numLightJobs != other.numLightJobs)
			return false;
		if (numMediumJobs != other.numMediumJobs)
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
		return theDate.get(Calendar.MONTH) + "/" + theDate.get(Calendar.DATE)
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
		numLightJobs = theLightNum;
		numMediumJobs = theMediumNum;
		numHeavyJobs = theHeavyNum;
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
		numLightJobs = theLightNum;
		numMediumJobs = theMediumNum;
		numHeavyJobs = theHeavyNum;
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
			return true;
		}
		return false;
	}

	@Deprecated
	public List<GregorianCalendar> getDates() {
		return Collections.unmodifiableList(myDates);
	}

}