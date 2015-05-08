package user_interface;

import park_model.JobSchedule;
import park_model.User;

public class VolunteerIO implements IO {

	private User myUser;
	private JobSchedule myJobSchedule;
	
	public VolunteerIO(User myUser) {
		this.myUser = myUser;
		myJobSchedule = new JobSchedule();
	}

	@Override
	public void mainMenu() {
		System.out.println("I'm in the Volunteer menu.");
		System.out.println("Volunteer " + myUser);
	}

}
