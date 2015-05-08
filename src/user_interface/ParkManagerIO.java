package user_interface;

import park_model.JobSchedule;
import park_model.ParkManager;
import park_model.User;

public class ParkManagerIO implements IO {

	private ParkManager myUser;
	private JobSchedule myJobSchedule;
	
	public ParkManagerIO(ParkManager myUser) {
		this.myUser = myUser;
		myJobSchedule = new JobSchedule();
	}

	@Override
	public void mainMenu() {
		System.out.println("I'm in the Park Manager menu.");
		System.out.println("Park Manager " + myUser);
		for(String park: myUser.getParks()) {
			System.out.println(park);
		}
	}

}
