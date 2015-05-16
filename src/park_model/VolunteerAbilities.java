package park_model;

import java.util.Collection;

public class VolunteerAbilities {
	
	Collection<Job> myJobs;
	
	public VolunteerAbilities(String fileName) {
		myJobs = JobSchedule.getAllFutureJobs(fileName);
	}
	
	// example method
	
	public Collection<Job> getAllFutureJobs() {
		
		// method stub
		
		return null;
	}
	
}
