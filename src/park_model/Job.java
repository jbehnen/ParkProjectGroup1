/**
 * Job class
 * Creates a job
 * Author: L. Hamaker
 * Last Edit: 10 May 2015
 * 
 */
package park_model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import config_files.Config;

public class Job {
	
	//Class Variables
	private String myPark;
	private List<GregorianCalendar> myDates;
	private List<User> myVolunteers;
	private int numLightJobs;
	private int numMediumJobs;
	private int numHeavyJobs;
	private String myDescription;

	/**
	 * Class Constructor
	 * Creates an instance of a Job.
	 * @param thePark		the location of a job as a park
	 * @param theDates		the date(s) of a job
	 * @param theLightNum	the number of jobs in the work category Light that is available.
	 * @param theMediumNum	the number of jobs in the work category Medium that is available.
	 * @param theHeavyNum	the number of jobs in the work category Heavy that is available. 
	 */
	public Job(String thePark, List<GregorianCalendar> theDates, int theLightNum, int theMediumNum, 
			int theHeavyNum, String theDescription) {
		myPark = thePark;
		numLightJobs = theLightNum;
		numMediumJobs = theMediumNum;
		numHeavyJobs = theHeavyNum;
		myVolunteers = new ArrayList();
		myDescription = theDescription;
		myDates = new ArrayList<GregorianCalendar>();
		
		for(int i= 0; i< theDates.size(); i++){
			myDates.add(theDates.get(i));
		}
	}
	
	/**
	 * Copy Constructor
	 * Creates a copy of a job passed in as a parameter.
	 * @param theJob	the job to be copied.
	 */
	
	public Job(Job theJob) {
		this(theJob.getParkName(), theJob.getDates(), theJob.getNumLight(),
				theJob.getNumMedium(), theJob.getNumHeavy(), theJob.myDescription);
	}
	
	
	
	/**
	 * Constructs a new Job.
	 * 
	 * @param theNumDays The total number of consecutive days that the job spans.
	 */
	public Job(String thePark, int theYear, int theMonth, int theDate, int theNumDays,
			int theLightNum, int theMediumNum, int theHeavyNum, String theDescription,
			List<User> theVolunteers) {
		myPark = thePark;
		numLightJobs = theLightNum;
		numMediumJobs = theMediumNum;
		numHeavyJobs = theHeavyNum;
		myVolunteers = theVolunteers; // okay because volunteer is immutable, and we
										// actually want to modify this list
		myDescription = theDescription;
		GregorianCalendar startDate = new GregorianCalendar(theYear, theMonth, theDate);
		startDate.get(Calendar.MILLISECOND); // reset fields after assignment
		myDates = new ArrayList<>();
		for (int i = 0; i < theNumDays; i++) {
			myDates.add((GregorianCalendar) startDate.clone());
			startDate.add(Calendar.DATE, 1);
		}
	}
	
	/**
	 * Constructs a new 1Job.
	 * 
	 * Primarily useful for unit testing.
	 * 
	 * @param theNumDays The total number of consecutive days that the job spans.
	 */
	public Job(String thePark, GregorianCalendar theStartDate, int theNumDays,
			int theLightNum, int theMediumNum, int theHeavyNum, String theDescription) {
		
		this(thePark, theStartDate.get(Calendar.YEAR), theStartDate.get(Calendar.MONTH), 
				theStartDate.get(Calendar.DATE), theNumDays, theLightNum, theMediumNum,
				theHeavyNum, theDescription, new ArrayList<>());
	}
	
	/**
	 * Returns an unmodifiable list of the date(s) during which this job takes place.
	 * @return unmodifiable list of dates for a job.
	 */
	public List<GregorianCalendar> getDates() {
		return Collections.unmodifiableList(myDates);
	}
	
	/**
	 * Returns an unmodifiable list of the work categories that still have slots available for sign-up.
	 * @return a list of Work Categories still available. 
	*/ 
	 
	public List<WorkCategory> getAvailableWorkCategories() {
		List<WorkCategory> wcList = new ArrayList<WorkCategory>();
		
		//Checks each Work Category for availability.
		if(numLightJobs >0){
			wcList.add(WorkCategory.LIGHT);
		}
		if(numMediumJobs > 0){
			wcList.listIterator().add(WorkCategory.MEDIUM);
		}
		if(numHeavyJobs > 0){
			wcList.add(WorkCategory.HEAVY);
		}

		return Collections.unmodifiableList(wcList);
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
	 * Returns true if theVolunteer was successfully added, false if they were not added due to a violation
	 * of business rules.
	 * 
	 * @param theVolunteer
	 * @return boolean value
	 */
	public boolean signUp(User theVolunteer, WorkCategory theCategory) {
		
		boolean signUp = true;
		signUp = !isSignedUp(theVolunteer); //This has to be reversed as if they are not signed up (false) then they can be signed up (true). 

		//Checks to see if their work category is available.
		if(signUp){
			switch (theCategory){
			case LIGHT:
				if(numLightJobs <= 0){
					signUp = false;
				} else{
					numLightJobs--;
				}
				break;
			case MEDIUM:
				if(numMediumJobs <= 0){
					signUp = false;
				} else{
					numMediumJobs--;
				}
				break;
			case HEAVY:
				if(numHeavyJobs <= 0){
					signUp = false;
				}else{
					numHeavyJobs--;
				}
				break;
			}
		}
		//Add volunteer to Job list if volunteer meets the constraints.
		if(signUp){
			myVolunteers.add(theVolunteer);
		}
		return signUp;
	}
	
	/**
	 * isSignedUp
	 * Returns true if theVolunteer is signed up for the job, false otherwise.
	 * @param theVolunteer
	 * @return boolean 
	 */
	public boolean isSignedUp(User theVolunteer) {
		boolean isSignedUp = false;
		int count = 0;

		//Searches the Volunteer List for the volunteer attempting to sign up for this job.
		while(!(isSignedUp) && (count < myVolunteers.size())){
			User v = (User) myVolunteers.get(count);
			if(v.getEmail().equals(theVolunteer.getEmail())){
				isSignedUp = true;
			}else{
				isSignedUp = false;
			}
			count++;
		}

		//If in the list, returns true.
		//If not in the list, returns false.
		return isSignedUp;
	}
	
	/**
	 * getNumOpenJobs
	 * Returns the number of open jobs
	 * 
	 */
	public int getNumOpenJobs(){
		return (numLightJobs + numMediumJobs + numHeavyJobs);
	}
	
	/**
	 * getParkName
	 * Returns the name of the Park
	 */
	public String getParkName(){
		return myPark;
	}
	
	
	//These are methods added for testing, but since all three return int values, 
	//they are safe and may be helpful to other classes.
	/**
	 * getNumLight
	 * Returns the number of light jobs available
	 */
	public int getNumLight(){
		return numLightJobs;
	}
	/**
	 * getNumMedium
	 * Returns the number of medium jobs available
	 */
	public int getNumMedium(){
		return numMediumJobs;
	}
	/**
	 * getNumHeavy
	 * Returns the number of heavy jobs available
	 */
	public int getNumHeavy(){
		return numHeavyJobs;
	}
	
	
	
	// ADD JOB FUNCTIONALITY
	
	/**
	 * Returns the first date of the job.
	 * 
	 * @return The first date of the job.
	 */
	public GregorianCalendar getFirstDate() {
		return (GregorianCalendar) getDates().get(0).clone();
	}
	
	/**
	 * Returns the last date of the job.
	 * 
	 * @return The last date of the job.
	 */
	public GregorianCalendar getLastDate() {
		return (GregorianCalendar) getDates().get(myDates.size() - 1).clone();
	}
	
	/**
	 * Returns true if the job is longer than the maximum
	 * allowed days, false otherwise.
	 * 
	 * @return True if the job is longer than the maximum
	 * allowed days, false otherwise.
	 */
	public boolean isJobTooLong() {
		List<GregorianCalendar> dates = getDates();
		int numDates = dates.size();
		if (numDates > Config.MAX_JOB_DAYS) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if the first day of the job is an 
	 * earlier date than today, false otherwise.
	 * 
	 * @return true if the first day of the job is an 
	 * earlier date than today, false otherwise.
	 */
	public boolean isJobInPast() {
		GregorianCalendar today = Config.getTodaysDate();
		if (getFirstDate().compareTo(today) < 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if the last day of the job is more
	 * than the maximum allowed number of days in the
	 * future, false otherwise.
	 * 
	 * @return true if the last day of the job is more
	 * than the maximum allowed number of days in the
	 * future, false otherwise.
	 */
	public boolean isJobTooFarInFuture() {
		GregorianCalendar futureBound = Config.getTodaysDate();
		futureBound.add(Calendar.DATE, Config.MAX_DAYS_IN_FUTURE);
		if (getLastDate().compareTo(futureBound) > 0) {
			return true;
		}
		return false;
	}
	
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
		for (User vol: myVolunteers) {
			string.append("DELIM" + vol.toDelimitedStringVolunteer());
		}
		return string.toString();
	}

	// If you don't already know, you can auto-generate hashCode, equals,
	// and toString by right clicking somewhere in the code window, clicking
	// "Source", and then "Generate [...]" whatever you need! That's
	// where these came from. A test needed "equals".
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((myDates == null) ? 0 : myDates.hashCode());
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (myDates == null) {
			if (other.myDates != null)
				return false;
		} else if (!myDates.equals(other.myDates))
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

	public String getDescription(){
		return myDescription;
	}
	
	@Override
	public String toString() {
		return myPark + " " + dateString(getFirstDate()) + "-" 
				+ dateString(getLastDate())  + ", "
				+ myDescription + " "
				+ "Light: " + numLightJobs + ", "
				+ "Medium: " + numMediumJobs + ", "
				+ "Heavy: " + numHeavyJobs;
	}

	public String dateString(GregorianCalendar theDate) {
		return theDate.get(Calendar.MONTH) + "/" + theDate.get(Calendar.DATE) 
				+ "/" + theDate.get(Calendar.YEAR);
	}
	
}