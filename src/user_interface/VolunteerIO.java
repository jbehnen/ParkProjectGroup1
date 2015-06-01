package user_interface;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import park_model.Job;
import park_model.User;
import park_model.VolunteerAbilities;
import park_model.WorkCategory;
import config_files.Config;

/**
 * Runs the menu for volunteers.
 * 
 * @author Le Bui and Shewan
 * @version 05/22/2014
 *
 */
public class VolunteerIO implements IO {

	private User myUser;
	private VolunteerAbilities myAbilities;
	private BufferedReader myConsole;

	public VolunteerIO(User myUser) {
		this.myUser = myUser;
		myAbilities = new VolunteerAbilities(Config.JOB_SCHEDULE_FILE);
		myConsole = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public void mainMenu() {

		boolean isValid = false;
		int choice = 0;

		System.out.println(String.format("%40s", "Logged in as volunteer "
				+ myUser.getFirstName()));
		System.out.println(" What do you want to do today?");
		// Want to see upcoming jobs i can sign for
		System.out.println("  1. View my jobs");
		// i want to see job i am signed up for
		System.out.println("  2. Future Jobs ");
		System.out.println("  3. Exit");
		while (!isValid) {
			try {
				choice = Integer.parseInt(myConsole.readLine());
				System.out.println();
				if (choice > 0 && choice < 4) {
					isValid = true;
					switch (choice) {
					case 1:
						viewMyJobs();
						break;
					case 2:
						showAllFutureJobs();
						break;
					case 3:
						System.out.println("Have a great day!");
						myAbilities.saveJobs(Config.JOB_SCHEDULE_FILE);
						myConsole.close();
						break;
					default:
						System.out.println("Invalid input -Exit");
					}
				} else {
					System.out.println("Please make a valid selection");
				}
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Please make a valid selection");
			}
		}
	}

	/**
	 * Displays the jobs that the volunteer has already signed up for.
	 */
	private void viewMyJobs() {
		List<Job> myJobs = myAbilities.getMyJobs(myUser);
		if (myJobs.isEmpty()) {
			System.out.println("You are not signed up for any jobs.");
		} else {
			System.out.println("\nYou are signed up for: \n");
			for (int i = 0; i < myJobs.size(); i++) {
				System.out.println(i + 1 + ".  " + myJobs.get(i).toString());
	
			}
		}
		System.out.println();
		mainMenu();

	}

	/**
	 * This method is the helper method to display the jobs list.
	 *
	 */
	private void showAllFutureJobs() {
		System.out.println("All jobs scheduled in the future: \n");
		for (int i = 0; i < myAbilities.getAllFutureJobs().size(); i++) {
			System.out.println(i + 1 + ".  "
					+ myAbilities.getAllFutureJobs().get(i).toString());

		}
		System.out.println();
		askForSignUp();// If the user wants to sign up.

	}

	private void askForSignUp() {
		// Ask if user want to sign up.

		String response = "";
		System.out.println(myUser.getFirstName()
				+ ", do you want to sign up? (y/n) ");
		try {
			response = myConsole.readLine();
		} catch (IOException e) {
		}
		System.out.println();
		switch (response) {

		case "y":
			startSignUp();
			break;
		case "n":
			mainMenu();
			break;
		default:
			System.out.println("Invalid Option  y/n");
			showAllFutureJobs();
			break;

		}

	}

	/**
	 * Validate user job choice for selection.
	 */
	private void startSignUp() {
		int jobChoice = 0;
		System.out.println("Which job do you want sign up for?");
		try {
			jobChoice = Integer.parseInt(myConsole.readLine());
			--jobChoice;
			if (jobChoice >= 0 && jobChoice < myAbilities.getAllFutureJobs().size()) {
				validateAlreadySignedUp(jobChoice);
			} else {
				System.out.println("Please enter a valid job number.\n");
				showAllFutureJobs();
			}
		} catch (NumberFormatException | IOException e) {
			System.out.println("Please enter a valid job number.\n");
			showAllFutureJobs();
		}
		

	}

	/**
	 * Checks if the job can be signed up by the user Exit if user has already
	 * signed up for the job
	 * 
	 * @param response
	 *            - job index user want to sign up for.
	 */
	private void validateAlreadySignedUp(int jobChoice) {

		if (((List<Job>) myAbilities.getAllFutureJobs()).get(jobChoice)
				.isSignedUp(myUser)) {
			System.out.println(myUser.getFirstName()
					+ ", you can't sign up for same job multiple times.\n");
			showAllFutureJobs();

		} else {

			validateDateConflict(((List<Job>) myAbilities.getAllFutureJobs())
					.get(jobChoice));

		}

	}

	/**
	 * Validates if the two jobs are in the same day.
	 *
	 * @param jobForSignUp
	 *            - job user wants to sign up for
	 */
	private void validateDateConflict(Job jobForSignUp) {
		if (myAbilities.isSignedUpForConflictingJob(myUser, jobForSignUp)) {
			System.out.println(myUser.getFirstName() 
					+ ", you have already signed up for another job on that same day\n");
			showAllFutureJobs();

		}

		// No other job on the same day.
		validateWorkCategory(jobForSignUp);
	}

	/**
	 * Validates if the user chooses open work category.
	 * 
	 * @param signedUpJob
	 *            - job whose work category to be check for
	 */
	private void validateWorkCategory(Job jobForSignUp) {
		int choice = 0;
		boolean fail = true;
		WorkCategory temp = null;

		if (jobForSignUp.isFull()) { 
			System.out.println("Job is full");
			mainMenu(); 
		}

		System.out.println(" Which work catgory? ");

		System.out.println(" 1. Light ");

		System.out.println(" 2. Medium ");

		System.out.println(" 3. Heavy ");

		System.out.println(" 4. Go Back to list of jobs ");

		System.out.println(" 5. Main Menu ");

		while (fail) {

			try {
				choice = Integer.parseInt(myConsole.readLine());
			
				if (choice > 0 && choice < 6) {
					fail = false;
					switch (choice) {
					case 1:
						temp = WorkCategory.LIGHT;
						break;
					case 2:
						temp = WorkCategory.MEDIUM;
						break;
					case 3:
						temp = WorkCategory.HEAVY;
						break;
					case 4:
						showAllFutureJobs();
						break;
					case 5:
						mainMenu();
						break;
					default:
						System.out.println("Error: incorrect choice taken at console input");
					}// End switch
				}
				else {
					System.out.println("Please make a valid selection.");
				} 
			} catch (NumberFormatException | IOException e) {
				System.out.println("Please make a valid selection.");
			}

		}

		if (!jobForSignUp.isOpen(temp)) {
			System.out.println("Sorry, it seems that catagory is full.");
			showAllFutureJobs();

		}
		jobForSignUp.signUp(myUser, temp);
		System.out.println("Signed up for the job.");
		System.out.println("\n");
		mainMenu();

	}

}
