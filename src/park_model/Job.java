
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
	private List<DateAndTime> myDateAndTimes;
	private List<User> myVolunteers;
	private int numLightJobs;
	private int numMediumJobs;
	private int numHeavyJobs;

	/**
	 * Class Constructor
	 * Creates an instance of a Job.
	 * @param thePark		the location of a job as a park
	 * @param theDateAndTimes		the date(s) of a job
	 * @param theLightNum	the number of jobs in the work category Light that is available.
	 * @param theMediumNum	the number of jobs in the work category Medium that is available.
	 * @param theHeavyNum	the number of jobs in the work category Heavy that is available. 
	 */

	public Job(String thePark, List<DateAndTime> theDateAndTimes, int theLightNum, int theMediumNum, int theHeavyNum) {
		myPark = thePark;
		numLightJobs = theLightNum;
		numMediumJobs = theMediumNum;
		numHeavyJobs = theHeavyNum;
		myVolunteers = new ArrayList<User>();

		myDateAndTimes = new ArrayList<DateAndTime>();
		
		for(int i= 0; i< theDateAndTimes.size(); i++){
			myDateAndTimes.add(theDateAndTimes.get(i));
		}
		
		Collections.sort(myDateAndTimes);
	}
	
	/**
	 * Copy Constructor
	 * Creates a copy of a job passed in as a parameter.
	 * @param theJob	the job to be copied.
	 */
	
	public Job(Job theJob) {
		this(theJob.getParkName(), theJob.getDateAndTimes(), theJob.getNumLight(), theJob.getNumMedium(), theJob.getNumHeavy());
	}
	
	/**
	 * Returns an unmodifiable list of the date(s) during which this job takes place, sorted by date.
	 * @return unmodifiable list of dates for a job.
	 */
	public List<GregorianCalendar> getDates() {
		List<GregorianCalendar> list = new ArrayList<>();
		for (DateAndTime date: myDateAndTimes) {
			list.add((GregorianCalendar) date.getDate().clone());
		}
		return list; // doesn't even need to be unmodifiable 
	}
	
	/**
	 * Returns an unmodifiable list of the dates and times during which this job takes place,
	 * sorted by date.
	 * 
	 * @return An unmodifiable unmodifiable list of the dates and times during which
	 * this job takes place, sorted by date.
	 */
	public List<DateAndTime> getDateAndTimes() {
		return Collections.unmodifiableList(myDateAndTimes);
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
			User v = myVolunteers.get(count);
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
	
	public GregorianCalendar getFirstDate() {
		return getDates().get(0);
	}
	
	public GregorianCalendar getLastDate() {
		return getDates().get(myDateAndTimes.size() - 1);
	}
	
	public boolean isJobTooLong() {
		List<GregorianCalendar> dates = getDates();
		int numDates = dates.size();
		if (numDates > Config.MAX_JOB_DAYS) {
			return true;
		}
		return false;
	}
	
	public boolean isJobInPast() {
		GregorianCalendar today = Config.getTodaysDate();
		if (getDates().get(0).compareTo(today) < 0) {
			return true;
		}
		return false;
	}
	
	public boolean isJobTooFarInFuture() {
		GregorianCalendar futureBound = Config.getTodaysDate();
		futureBound.add(Calendar.DATE, Config.MAX_DAYS_IN_FUTURE);
		if (getDates().get(getDates().size() - 1).compareTo(futureBound) > 0) {
			return true;
		}
		return false;
	}
}
