package park_model;

import java.util.Collection;

public class VolunteerAbilities {
	
	Collection<Job> myJobs;
	
	public VolunteerAbilities(String fileName) {
		myJobs = JobSchedule.getAllFutureJobs(fileName);
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
	
	
	public boolean isFull(Job jobForSignUp){
		
		return !jobForSignUp.isOpen(WorkCategory.LIGHT)&&
				!jobForSignUp.isOpen(WorkCategory.MEDIUM) &&
						!jobForSignUp.isOpen(WorkCategory.HEAVY);
			
			
		}
		
		
		
		
		
	
	
	
	/**
	 * 
	 * @param fileName
	 */
	
	public void saveJobs(String fileName) {
		JobSchedule.saveJobList(myJobs, fileName);
	}
	
	
	 
	
}
