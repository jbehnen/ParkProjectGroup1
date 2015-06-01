package park_model;

import java.util.ArrayList;
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
	 * Returns all jobs that the user is signed up for.
	 * 
	 * Precondition: theUser != null.
	 * 
	 * @return all jobs that the user is signed up for.
	 */
	public List<Job> getMyJobs(User theUser) {
		List<Job> signedUpJobs = new ArrayList<Job>();
		for (Job job: myJobs) {
			if (job.isSignedUp(theUser)) {
				signedUpJobs.add(new Job(job));
			}
		}
		assert myJobs != null;
		return signedUpJobs;
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
	

	
}
