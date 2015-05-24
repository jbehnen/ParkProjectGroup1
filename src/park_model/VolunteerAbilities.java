package park_model;

import java.util.Collection;
/**
 * This class allow Volunteer to complete
 * their user stories and business rules
 * @author Le Bui & Shewan
 * @version 05/22/2015
 */
public class VolunteerAbilities {
	
	Collection<Job> myJobs;
	/**
	 * This Construct get all the future job 
	 * and stored in given file
	 * @param fileName the name of the file that list of
	 * all future job is stored and loaded from
	 */
	public VolunteerAbilities(String fileName) {
		myJobs = JobSchedule.getAllFutureJobs(fileName);
		assert myJobs != null : "my list of job is null";
	}
	
	
	/**
	 * Returns the list of all future jobs.
	 * @return - list of future jobs.
	 */
	public Collection<Job> getAllFutureJobs() {			
		return myJobs; // must be deep copy
	}
	
	/**
	 * A volunteer can't sign up for two jobs in the same day.
	 * 
	 * @param A
	 *            -= First Job
	 * @param B
	 *            -= Second job
	 * @return if these two jobs do have same day.
	 */
	public boolean checkJobsOnSameDay(Job A, Job B) {
		boolean sameDay = false;

		if (A.getFirstDate().compareTo(B.getFirstDate()) == 0) {
			// the two jobs do have same start day.
			System.out.print("Those two jobs do have same start date");
			sameDay = true;
		} else if (A.getLastDate().compareTo(B.getLastDate()) == 0) {
			// the two jobs do have same start day.
			System.out.print("Those two jobs do have same end date");
			sameDay = true;
		} else {
			//Can omit this line and the above two lines.
			sameDay = false;
		}

		return sameDay;
	}
	
	/**
	 * This construct checks for level difficult
	 * of job sign up for
	 * @param jobForSignUp - it checks for oppening work category
	 * about level difficult of job
	 * @return level difficult of job
	 */
	public boolean isFull(Job jobForSignUp){
		
		return !jobForSignUp.isOpen(WorkCategory.LIGHT)&&
				!jobForSignUp.isOpen(WorkCategory.MEDIUM) &&
						!jobForSignUp.isOpen(WorkCategory.HEAVY);
			
			
		}
	/**
	 * Construct is saving list of Job
	 * @param fileName - the nam of this file is saved
	 * and loaded the list of Job
	 */
	
	public void saveJobs(String fileName) {
		JobSchedule.saveJobList(myJobs, fileName);
	}
}