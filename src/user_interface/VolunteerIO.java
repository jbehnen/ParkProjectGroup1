package user_interface;

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
		myAbilities = new VolunteerAbilities(Config.JOB_SCHEDULE_FILE_FOR_STATIC);
		display = new ArrayList<Job>();
		display = (List<Job>) myAbilities.getAllFutureJobs();
		size = display.size();
	}

	@SuppressWarnings("resource")
	@Override
	public void mainMenu() {
		
			System.out.println(String.format("%40s","Hi Volunteer " + myUser.getFirstName()));		
			Scanner myScan = new Scanner(System.in);
			int choice = 0;
			do {
				System.out.println(" What do you want to do today?");
				// Want to see upcoming jobs i can sign for
				System.out.println("  1. View my jobs");
				// i want to see job i am signed up for
				System.out.println("  2. Future Jobs ");
				System.out.println("  3. Exit");
				choice = myScan.nextInt();
			} while (invalidate(choice));
			manageVolunteerOption(choice); 
	}
	
  /**
   * Validates user choice
   * @param choice
   * @return
   */
   private boolean invalidate(int choice) {
	   boolean fail = false;   
	   if (choice != 1 || choice != 2 || choice != 3) {
			fail = true;
		} 
		return fail;
		
	}

/**
    * Handles users action.
    * @param nextInt - users input.
    */
	private void manageVolunteerOption(int nextInt) {
		switch (nextInt) {
		case 1:
			viewMyJobs();
			break;
		case 2:
			// see Open jobs
			showAllFutureJobs();
			// Inner option call to sign up?
			break;
		case 3:
			myAbilities.saveJobs(Config.JOB_SCHEDULE_FILE_FOR_STATIC);
            System.exit(0);
			break;
		case 10:

		default:
			System.out.print("");
		}

	}
	/**
	 * Displays the jobs that the volunteer has already signed up for.
	 */
    private void viewMyJobs() {
    			if (size == 0) {
			System.out.println("Am sorry, no future jobs to display.");
			mainMenu();
		} else {
			getMyJobs();
		}

		if (size!=0 && getMyJobs().size() == 0) {
			System.out.println("Am sorry, you didn't signup for any job yet.");

		} else {
			System.out.println("Here is the list you signed up for.");
			for (int i = 1; i <= getMyJobs().size(); i++) {

				System.out.println(i + ". " + getMyJobs().get(i).toString());
			}

		}
	}
    /**
     * Gets the jobs that i am singed up for.
     * @return - List of jobs that user signed up for
     */
	private List<Job> getMyJobs() {
		//Temp list to hold and display user's job info
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
     * @pre
     */
	private void showAllFutureJobs() {
		
		if (size == 0) {
			System.out.println("Am sorry, no future jobs to display.");
			mainMenu();
		} else {
			for (int i = 1; i <= display.size(); i++) {
				System.out.println(i + ". " + display.get(i).toString());
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
			startSigninUp();
		}

	}
	
	/**
	 * Construct handles the siging up process.
	 */
	
	@SuppressWarnings("resource")
	private void startSigninUp() {
		Scanner inner = new Scanner(System.in);
		int response;
		System.out.println("For which job do you want sign up for?");
		response = inner.nextInt();
		
		if(response <0 || response > size){
			System.out.println("Invalid Option");
			showAllFutureJobs();			
		}else{
			
			validateSignUp( response);
		}
		
		
	}
    /**
     * Checks if the job can be signed up by the user
     * Exit if user has already signed up for the job
     * @param response - job index user want to sign up for.
     */
	private void validateSignUp(int response) {
		if(display.get(response).isSignedUp(myUser)){
			System.out.println(myUser.getFirstName() + ", you have already signed up for this job");
			showAllFutureJobs();
			}
		else{
			validateConflict(display.get(response));
		}
		
		
	}

	/**
	 * Validates if the two jobs are in the same day.
	 * 
	 * @param jobForSignUp
	 *            - job user wants to sign up for
	 */
	private void validateConflict(Job jobForSignUp) {
		for (Job signedUpJob : getMyJobs()) {

			if (myAbilities.checkJobsOnSameDay(jobForSignUp, signedUpJob)) {
				System.out.println(myUser.getFirstName()
						+ ",you have already signed up for a job on same day");

			} else {
				validateWorkCatagorie(jobForSignUp);
			}

		}

	}
    /**
     * Validates if the user chooses open work category.
     * @param signedUpJob - job whose work category to be check for
     */
	@SuppressWarnings("resource")
	private void validateWorkCatagorie(Job jobForSignUp) {
		Scanner on = new Scanner(System.in);
		int choice = 0;
		boolean fail = false;
		
		
		
		if(myAbilities.isFull(jobForSignUp)){
			System.out.println("Job is full");
			mainMenu();
		}
		
		do {

			System.out
					.println(" Here are the avaialbe lists of work catgory for that job");
			int light = jobForSignUp.getNumOpen(WorkCategory.LIGHT);
			System.out.println(" 1. Light  " + light);
			int medium = jobForSignUp.getNumOpen(WorkCategory.MEDIUM);
			System.out.println(" 2. Medium " + medium);
			int heavy = jobForSignUp.getNumOpen(WorkCategory.HEAVY);
			System.out.println(" 3. Heavy  " + heavy);

			System.out
					.println(" Which one you like to sign up for?  or enter 10 to exit");
			choice = on.nextInt();

			if (choice != 1 || choice != 2 || choice != 3 || choice !=10) {
				fail = true;
			} else {
				switch (choice) {

				case 1:
					if (!jobForSignUp.isOpen(WorkCategory.LIGHT)) {
						System.out.print("That catagory is full");
						showAllFutureJobs();
						fail = false;
					}
					jobForSignUp.signUp(myUser, WorkCategory.LIGHT);
					System.out.print("Signed up for the job");
					break;
				case 2:
					if (!jobForSignUp.isOpen(WorkCategory.MEDIUM)) {
						System.out.print("That catagory is full");
						showAllFutureJobs();
						fail = false;
					}
					jobForSignUp.signUp(myUser, WorkCategory.MEDIUM);
					System.out.print("Signed up for the job");
					break;
				case 3:
					if (!jobForSignUp.isOpen(WorkCategory.HEAVY)) {
						System.out.print("That catagory is full");
						showAllFutureJobs();
						fail = false;
					}
					jobForSignUp.signUp(myUser, WorkCategory.HEAVY);
					System.out.print("Signed up for the job");
					break;

				case 10:
					mainMenu();
					break;

				}

				fail = false;

			}

		} while (fail);

	}

	/*if(jobForSignUp.isOpen(WorkCategory.LIGHT)){
	int light = jobForSignUp.getNumOpen(WorkCategory.LIGHT);
	System.out.println(" 1. Light  " + light);
	}
if(jobForSignUp.isOpen(WorkCategory.MEDIUM)){
	int medium = jobForSignUp.getNumOpen(WorkCategory.MEDIUM);
	System.out.println(" 2. Medium " + medium);
}

if(jobForSignUp.isOpen(WorkCategory.HEAVY)){
	int heavy = jobForSignUp.getNumOpen(WorkCategory.HEAVY);
	System.out.println(" 3. Heavy  " + heavy);
}*/	
}
