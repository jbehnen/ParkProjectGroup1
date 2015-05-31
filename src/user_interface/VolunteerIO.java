package user_interface;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import park_model.Job;
import park_model.User;
import park_model.VolunteerAbilities;
import park_model.WorkCategory;
import config_files.Config;

/**
 * Running the menu for Volunteer This class is checking valid or invalid or
 * conflict date Viewing list of Job, Showing list of Job in the future Managing
 * option the volunteer take for Job
 * 
 * @author Le Bui and Shewan
 * @version 05/22/2014
 *
 */
public class VolunteerIO implements IO {

	private User myUser;
	private VolunteerAbilities myAbilities;

	public VolunteerIO(User myUser) {
		this.myUser = myUser;
		myAbilities = new VolunteerAbilities(Config.JOB_SCHEDULE_FILE);

	}

	@SuppressWarnings("resource")
	@Override
	public void mainMenu() {

		Scanner myScan = new Scanner(System.in);
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
			choice = myScan.nextInt();
			if (choice > 0 && choice < 4) {
				isValid = true;
				switch (choice) {
				case 1:
					viewMyJobs();
					break;
				case 2:
					// see Open jobs
					showAllFutureJobs();
					break;
				case 3:
					myAbilities.saveJobs(Config.JOB_SCHEDULE_FILE);
					System.exit(0);
					break;
				default:
					System.out.println("Invalid input -Exit");
				}
			} else {
				System.err.println("Please make a valid selection");
			}
		}
	}

	/**
	 * Displays the jobs that the volunteer has already signed up for.
	 */
	private void viewMyJobs() {

		if (getMyJobs().size() == 0) {
			System.out.println("Didn't sign up for a job yet.");
		}

		for (int i = 0; i < getMyJobs().size(); i++) {
			System.out.println(i + 1 + ".  " + getMyJobs().get(i).toString());

		}
		System.out.println("\n");
		mainMenu();

	}

	private List<Job> getMyJobs() {
		List<Job> myJobs = new ArrayList<Job>();
		for (int i = 0; i < myAbilities.getAllFutureJobs().size(); i++) {
			if (myAbilities.getAllFutureJobs().get(i).isSignedUp(myUser)) {
				myJobs.add(myAbilities.getAllFutureJobs().get(i));

			}

		}

		return myJobs;

	}

	/**
	 * This method is the helper method to display the jobs list.
	 *
	 */
	private void showAllFutureJobs() {

		for (int i = 0; i < myAbilities.getAllFutureJobs().size(); i++) {
			System.out.println(i + 1 + ".  "
					+ myAbilities.getAllFutureJobs().get(i).toString());

		}

		askForSignUp();// If the user wants to sign up.

	}

	@SuppressWarnings("resource")
	private void askForSignUp() {
		// Ask if user want to sign up.
		Scanner inner = new Scanner(System.in);

		String response = "";
		System.out.println(myUser.getFirstName()
				+ ", do you want to sign up? (y/n) ");
		response = inner.next();

		switch (response) {

		case "y":
			startSignUp();
			break;
		case "n":
			mainMenu();
			break;
		default:
			System.err.println("Invalid Option  y/n");
			showAllFutureJobs();
			break;

		}

	}

	/**
	 * Validate user job choice for selection.
	 */

	@SuppressWarnings("resource")
	private void startSignUp() {
		Scanner inner = new Scanner(System.in);
		inner.reset();

		int jobChoice = 0;
		System.out.println("For which job do you want sign up for?");
		jobChoice = inner.nextInt();
		--jobChoice;
		if (jobChoice >= 0 && jobChoice < myAbilities.getAllFutureJobs().size()) {
			validateAlreadySignedUp(jobChoice);

		} else {
			System.out.println("Invalid Option");
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
			System.err.println("Remark: " + myUser.getFirstName()
					+ ", you can't signup for same job multiple times.");
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
			System.err
					.println(myUser.getFirstName()
							+ ",you have already signed up for another job on that same day");
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
	@SuppressWarnings("resource")
	private void validateWorkCategory(Job jobForSignUp) {
		Scanner on = new Scanner(System.in);
		on.reset();
		int choice = 0;
		boolean fail = false;
		WorkCategory temp = null;

		/*
		 * if (jobForSignUp.isFull()) { System.out.println("Job is full");
		 * mainMenu(); }
		 */

		System.out.println(" Which work catgory? ");

		System.out.println(" 1. Light ");

		System.out.println(" 2. Medium ");

		System.out.println(" 3. Heavy ");

		System.out.println(" 4. Go Back to list of jobs ");

		System.out.println(" 5. Main Menu ");

		while (!fail) {

			choice = on.nextInt();
			if (choice > 0 && choice < 6) {
				fail = true;
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
					System.err
							.println("Error: incorrect choice taken at console input");
				}// End switch

			}

			else {
				System.err.println("Please make a valid selection");
			} 

		}

		if (!jobForSignUp.isOpen(temp)) {
			System.err.println("Sorry, it seems that catagory is full.");
			showAllFutureJobs();

		}
		jobForSignUp.signUp(myUser, temp);
		System.out.println("Signed up for the job.");
		System.out.println("\n");
		mainMenu();

	}

}
