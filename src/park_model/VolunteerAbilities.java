package park_model;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Provides the methods that allow Volunteers to complete
 * their user stories and access the list of jobs.
 * 
 * @author Shewan, Lee Bui, and Julia
 * @version 5/24/2015
 */
// Invariant: myJobs != null.
public class VolunteerAbilities {
	
	List<Job> myJobs;
	
	/**
	 * Constructs the VolunteerAbilities and gets all of the jobs
	 * stored in the given file.
	 * 
	 * Preconditions: theFileName != null.
	 * 
	 * @param theFileName The name of the file that holds the
	 * jobs that the ParkManager should be able to 
	 * access.
	 */
	public VolunteerAbilities(String theFileName) {
		myJobs = JobSchedule.getAllFutureJobs(theFileName);
		assert myJobs != null;
	}
	
	/**
	 * Returns an unmodifiable list of all jobs in 
	 * the class - not a deep copy. 
	 * Stored jobs can be changed based on Volunteer input.
	 * @return an unmodifiable list of all jobs in 
	 * the class - not a deep copy.
	 */
	public List<Job> getAllFutureJobs() {			
		return Collections.unmodifiableList(myJobs);
	}
	
	/**
	 * Returns true if theVolunteer is already signed up for another job
	 * that overlaps theJob, false otherwise. 
	 * 
	 * Preconditions: theVolunteer != null, theJob != null, theJob.getFirstDate() != null,
	 * theJob.getLastDate() != null.
	 * 
	 * @return true if theVolunteer is already signed up for another job
	 * that overlaps theJob, false otherwise. 
	 */
	public boolean isSignedUpForConflictingJob(User theVolunteer, Job theJob) {
		GregorianCalendar firstDate = theJob.getFirstDate();
		GregorianCalendar lastDate = theJob.getLastDate();
		for (Job otherJob: myJobs) {
			if (otherJob.isSignedUp(theVolunteer) 
					&& otherJob.isJobInRange(firstDate, lastDate)) {
				assert myJobs != null;
				return true;
			}
		}
		assert myJobs != null;
		return false;
	}
	
	/**
	 * Adds a job to the list of jobs.
	 * 
	 * TESTING PURPOSES ONLY
	 * 
	 * Preconditions: theJob != null, !tooManyTotalJobs(),
	 * !tooManyJobsNearJobTime(theJob). 
	 * 
	 * @param theJob The job to be added.
	 */
	void addJob(Job theJob) {
		myJobs.add(new Job(theJob));
		assert myJobs != null;
	}
	
	/**
	 * Saves jobs to file.
	 * 
	 * Preconditions: fileName != null.
	 */
	public void saveJobs(String fileName) {
		JobSchedule.saveJobList(myJobs, fileName);
		assert myJobs != null;
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
	@Deprecated
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
	
	@Deprecated
	public boolean isFull(Job jobForSignUp){
		
		return !jobForSignUp.isOpen(WorkCategory.LIGHT)&&
				!jobForSignUp.isOpen(WorkCategory.MEDIUM) &&
						!jobForSignUp.isOpen(WorkCategory.HEAVY);
			
			
		}
	
}
