package user_interface;

import park_model.User;
import park_model.VolunteerList;

public class AdminIO implements IO {
	
	private User myUser;
	private VolunteerList myVolunteerList;
	
	public AdminIO(User myUser) {
		this.myUser = myUser;
		myVolunteerList = new VolunteerList();
	}

	@Override
	public void mainMenu() {
		System.out.println("I'm in the Admin menu.");
		System.out.println("Admin " + myUser);
	}
}
