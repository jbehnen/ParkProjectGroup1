package park_model;

import java.util.Date;
import java.util.List;

public class Job {
	
	public Job(/*...whatever parameters you need...*/) {
		
	}
	
	public Job(Job theJob) {
		// we need a copy constructor for JobSchedule
	}
	
	/**
	 * Returns an unmodifiable list of the date(s) during which this job takes place.
	 * 
	 * @return
	 * 
	 * Delete the following note when you are done:
	 * See http://docs.oracle.com/javase/7/docs/api/java/util/Collections.html#unmodifiableList(java.util.List)
	 */
	public List<Date> getDates() {
		return null;
		
	}
	
	/**
	 * Returns an unmodifiable list of the work categories that still have slots available for sign-up.
	 * 
	 * @return
	 */
	public List<WorkCategory> getAvailableWorkCategories() {
		return null;
	}
	
	/**
	 * Returns an unmodifiable list of the volunteers signed up for the job.
	 * 
	 * @return
	 */
	public List<Volunteer> getVolunteers() {
		return null;
	}
	
	/**
	 * Returns true if theVolunteer is signed up for the job, false otherwise.
	 * 
	 * @param theVolunteer
	 * @return
	 */
	public boolean isSignedUp(Volunteer theVolunteer) {
		return false;
	}
	
	/**
	 * Returns true if theVolunteer was successfully added, false if they were not added due to a violation
	 * of business rules.
	 * 
	 * @param theVolunteer
	 * @return
	 */
	public boolean signUp(Volunteer theVolunteer, WorkCategory theCategory) {
		return false;
	}
	
}
