package park_model;

import java.util.List;

public class JobSchedule {
	
	/**
	 * Constructs a list of all jobs in the system from the back-end data.
	 */
	public JobSchedule() {
		
	}
	
	/**
	 * Adds a job to the list of jobs if it doesn't violate any business rules; returns true
	 * if job successfully added, false otherwise.
	 *
	 * @return  
	 * @param job
	 */
	public boolean addJob(Job job) {  // need to use the copy constructor or something
		return false;
		
	}
	
	
	/**
	 * Returns an unmodifiable list of future jobs that the given volunteer is signed up for. 
	 * 
	 * @param theVolunteer The volunteer being searched for.
	 * @return An unmodifiable list of future jobs that the given volunteer is signed up for. 
	 * 
	 * Delete the following note when you are done:
	 * See http://docs.oracle.com/javase/7/docs/api/java/util/Collections.html#unmodifiableList(java.util.List)
	 */
	public List<Volunteer> getUpcomingJobsByVolunteer(Volunteer theVolunteer) {
		return null;
		
	}
	
	/**
	 * Returns an unmodifiable list of jobs that the volunteer can sign up for, excluding all jobs that violate business rules.
	 * 
	 * @param theVolunteer
	 * @return
	 */
	public List<Volunteer> getJobsForSignUp(Volunteer theVolunteer) {
		return null;
		
	}
	
	/**
	 * Returns an unmodifiable list of future jobs scheduled at the given park.
	 * 
	 * @param thePark
	 * @return
	 */
	public List<Volunteer> getJobsByPark(String thePark) {
		return null;
		
	}
	
	/**
	 * Writes all list information to the back-end storage.
	 */
	public void saveList() {
		// don't do this yet?
	}
	
}
