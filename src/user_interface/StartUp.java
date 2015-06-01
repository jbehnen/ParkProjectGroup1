package user_interface;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

import park_model.ParkManager;
import park_model.User;
import park_model.UserList;
import config_files.Config;

/**
 * Starts up the program, lets the user log in,
 * and directs the user to their proper menu.
 * 
 * @author Julia Behnen
 * @version 5/8/2015
 */
public class StartUp {
	
	public static void main(String args[]) throws IOException {	
		String email = null;
		
		System.out.println("Please enter your email: ");
		Scanner in = new Scanner(System.in); 
		email = in.next();
		
		tryToSignIn(email, Config.VOLUNTEER_FILE, 'V', in);
		tryToSignIn(email, Config.ADMIN_FILE, 'A', in);
		tryToSignIn(email, Config.PARK_MANAGER_FILE, 'P', in);
		
		in.close();
		System.out.println("Invalid login.");
	}
	
	/**
	 * Reads in a list of a specific type of user, checks to see if the email
	 * belongs to one of those users, and if so, signs that user in and
	 * sends them to the correct menu.
	 * 
	 * @param theUserType The char that identifies the type of the user that was read in.
	 */
	private static void tryToSignIn(String theEmail, String theFileName,
			char theUserType, Scanner theScanner) {
		User foundUser = null;
		Collection<User> users = UserList.getAllUsers(theFileName);
		for (User user: users) {
			if (theEmail.equals(user.getEmail())) {
				foundUser = user;
			}
		}
		if (foundUser != null) {
			goToMenu(foundUser, theUserType);
			theScanner.close();
			System.exit(0);
		}
	}
	
	/**
	 * Logs in the user to the menu indicated by the theUserType; if the user type
	 * is incorrect, exits the system.
	 * 
	 * @param theUserType The char that identifies the type of the user that was read in.
	 */
	private static void goToMenu(User theUser, char theUserType) {
		
		IO menu = null;
		switch(theUserType) {
			case('A'): 
				menu = new AdminIO(theUser);
				break;
			case('V'): 
				menu = new VolunteerIO(theUser);
				break;
			case('P'):
				menu = new ParkManagerIO((ParkManager) theUser);
				break;
			default:
				System.exit(0);
		}
		menu.mainMenu();
	}
	
}
