package user_interface;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import park_model.Job;
import park_model.JobSchedule;
import park_model.User;
import park_model.VolunteerAbilities;
import park_model.WorkCategory;
import config_files.Config;
/**
 * Running the menu for Volunteer
 * This class is checking valid or invalid or conflict date
 * Viewing list of Job, Showing list of Job in the future
 * Managing option the volunteer take for Job
 * @author Le Bui and Shewan
 * @version 05/22/2014
 *
 */
public class VolunteerIO implements IO {

	private User myUser;
	private VolunteerAbilities myAbilities;
	private int size;
	private List<Job> display;


	public VolunteerIO(User myUser) {
		this.myUser = myUser;
		myAbilities = new VolunteerAbilities(Config.JOB_SCHEDULE_FILE);
		//display = new ArrayList<Job>();
		//display = (List<Job>) myAbilities.getAllFutureJobs();
		//size = display.size();
	}

	@SuppressWarnings("resource")
	@Override
	public void mainMenu() {

		Scanner myScan = new Scanner(System.in);
		boolean isValid = false;
		int choice = 0;

		System.out.println(String.format("%40s",
				"Hi Volunteer " + myUser.getFirstName()));
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
					System.err
							.println("Error: incorrect choice taken at console input");
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
			System.out.println("Sorry, you didn't signup for any job yet.");
		} else {
			System.out.println("Here is the list you signed up for.");
			for (int i = 1; i <= getMyJobs().size(); i++) {
				System.out.println(i + ". " + getMyJobs().get(i).toString());
			}

		}
	}

	/**
	 * Gets the jobs that i am singed up for.
	 *
	 * @return - List of jobs that user signed up for
	 */
	private List<Job> getMyJobs() {
		// Temp list to hold and display user's job info
		List<Job> myJobsList = new ArrayList<Job>();
		for (Job theJob : display) {
			if (theJob.isSignedUp(myUser)) {
				myJobsList.add(theJob);
			}

		}

		return myJobsList;

	}

	/**
     * This method is the helper method to display the jobs list.
     *
     */
	private void showAllFutureJobs() {
		if (myAbilities.getAllFutureJobs().size() == 0) {
			System.out.println("Am sorry, no future jobs to display.");
			mainMenu();
		} else {
			System.out.println("Jobs in the future.");
			for (int i = 1; i <= myAbilities.getAllFutureJobs().size(); i++) {
				System.out.println(i + ". " + ((List<Job>) myAbilities.getAllFutureJobs()).get(i).toString());
			}

		}

		askForSignUp();// If the user wants to sign up.

	}

	@SuppressWarnings("resource")
	private void askForSignUp() {
		//Ask if user want to sign up.
		Scanner inner = new Scanner(System.in);
		String response;
		System.out.println(myUser.getFirstName() +", do you want to sign up? (y/n) ");
		response = inner.next();

		if (!response.toLowerCase().contains("y")
				|| !response.toLowerCase().contains("n")) {
			System.out.println("Invalid Option");
			showAllFutureJobs();

		} else if (response.toLowerCase().contains("n")) {
			mainMenu();

		}else{
			startSignUp();
		}

	}

	/**
	 *Validate user job choice for selection.
	 */

	@SuppressWarnings("resource")
	private void startSignUp() {
		Scanner inner = new Scanner(System.in);
		int jobChoice;
		System.out.println("For which job do you want sign up for?");
		jobChoice = inner.nextInt();

		if (jobChoice > 0 && jobChoice < size) {
			validateAlreadySignedUp(jobChoice);

		} else {
			System.out.println("Invalid Option");
			showAllFutureJobs();

		}

	}
    /**
     * Checks if the job can be signed up by the user
     * Exit if user has already signed up for the job
     * @param response - job index user want to sign up for.
     */
	private void validateAlreadySignedUp(int jobChoice) {
		//if(display.get(response).isSignedUp(myUser)){
		if(((List<Job>) myAbilities.getAllFutureJobs()).get(jobChoice).isSignedUp(myUser)){
			System.out.println(myUser.getFirstName() + ", you have already signed up for this job");
			showAllFutureJobs();
			}
		else{
			validateDateConflict(((List<Job>) myAbilities.getAllFutureJobs()).get(jobChoice));
			//validateConflict(display.get(response));
		}


	}

	/**
	 * Validates if the two jobs are in the same day.
	 *
	 * @param jobForSignUp
	 *            - job user wants to sign up for
	 */
	private void validateDateConflict(Job jobForSignUp) {
		for (Job signedUpJob : getMyJobs()) {
			if (myAbilities.checkJobsOnSameDay(jobForSignUp, signedUpJob)) {
				System.out.println(myUser.getFirstName()
						+ ",you have already signed up for a job on same day");
			}

		}
		// No other job on the same day.
		validateWorkCategory(jobForSignUp);
	}

    /**
     * Validates if the user chooses open work category.
     * @param signedUpJob - job whose work category to be check for
     */
	@SuppressWarnings("resource")
	private void validateWorkCategory(Job jobForSignUp) {
		Scanner on = new Scanner(System.in);
		int choice = 0;
		boolean fail = false;
		WorkCategory temp = null;
		if (myAbilities.isFull(jobForSignUp)) {
			System.out.println("Job is full");
			mainMenu();
		}

		System.out
				.println(" Here are the avaialbe lists of work catgory for that job");
		// int light = jobForSignUp.getNumOpen(WorkCategory.LIGHT);
		System.out.println(" 1. Light  "
				+ jobForSignUp.getNumOpen(WorkCategory.LIGHT));
		// int medium = jobForSignUp.getNumOpen(WorkCategory.MEDIUM);
		System.out.println(" 2. Medium "
				+ jobForSignUp.getNumOpen(WorkCategory.MEDIUM));
		// int heavy = jobForSignUp.getNumOpen(WorkCategory.HEAVY);
		System.out.println(" 3. Heavy  "
				+ jobForSignUp.getNumOpen(WorkCategory.HEAVY));

		System.out.println(" Which one you like to sign up for? 4 to go back");
		choice = on.nextInt();

		while (!fail) {

			choice = on.nextInt();
			if (choice > 0 && choice < 5) {
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
				default:
					System.err
							.println("Error: incorrect choice taken at console input");
				}
			} else {
				System.err.println("Please make a valid selection");
			}

		}

		if (!jobForSignUp.isOpen(temp)) {
			System.out.print("That catagory is full");
			showAllFutureJobs();

		}
		jobForSignUp.signUp(myUser, temp);
		System.out.print("Signed up for the job");

	}

}

