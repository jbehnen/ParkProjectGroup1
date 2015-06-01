/**
 * ParkManagerIO.java 
 * 
 * A class that creates jobs, adds them to the job schedule, 
 * provides a viewable list of upcoming jobs and provides a
 * viewable list for volunteers signed up for a specific job
 * 
 * Author: L. Hamaker lahama9@uw.edu
 * Created: 9 May 2015
 * Date of last edit: 31 May 2015
 * 
 */
package user_interface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import config_files.Config;
import park_model.Job;
import park_model.PMAbilities;
import park_model.ParkManager;
import park_model.RulesHelp;
import park_model.User;

public class ParkManagerIO implements IO {

	//Class variables
	private ParkManager myUser;
	private PMAbilities myAbilities;
	private BufferedReader myConsole;

	/**
	 * Class constructor
	 * @param myUser 
	 * 		a Park Manager
	 */
	public ParkManagerIO(ParkManager myUser) {
		this.myUser = myUser;
		myAbilities = new PMAbilities(Config.JOB_SCHEDULE_FILE);
		myConsole = new BufferedReader(new InputStreamReader(System.in));
	}

	/**
	 * Main Menu()
	 *
	 */
	@Override
	public void mainMenu() {
		int i = 0;
		boolean validChoice = false;

		System.out.println("\n********Welcome to the Park Manager page********");
		System.out.println("\nPlease type the number of the action you want to perform.");
		System.out.println("\n1: Create a new job. \n2: View upcoming jobs. \n3: View the volunteers for a job. \n4: Quit program");

		// IO try/catch was adapted from code found at
		// http://stackoverflow.com/questions/4644415/java-how-to-get-input-from-system-console
		while (!validChoice) {
			try {
				i = Integer.parseInt(myConsole.readLine());
				if (i > 0 && i < 5) {
					validChoice = true;
					switch (i) {
					case 1:
						addJob();
						break;
					case 2:
						viewJobs();
						break;
					case 3:
						viewVol();
						break;
					case 4:
						System.out.println("Have a great day!");
						myAbilities.saveJobs(Config.JOB_SCHEDULE_FILE);
						myConsole.close();
						break;
					default:
						System.out.println("outor: incorrect choice taken at console input");
					}
				} else {
					System.out.println("Please make a valid selection");
				}
			} catch (NumberFormatException | IOException nfe) {
				System.out.println("Please make a valid selection");
			}
		}
	}


	// Private method that views volunteers for a specific job
	private void viewVol() {

		boolean validJob = false;
		boolean tooFull = false;
		int index;
		System.out.println("********View the Volunteers********");
		List<Job> jobs = getJobs();
		// Display the jobs
		if (jobs.size() > 0) {
			System.out.println("Which job do you want displayed?\n");
			for (int i = 0; i < jobs.size(); i++) {
				System.out.println(i + ": " + jobs.get(i).toString());
			}

			// Validate the selection
			while (!validJob && !tooFull) {
				try {
					index = Integer.parseInt(myConsole.readLine());
					if (index >= 0 && index < jobs.size()) {
						validJob = true;
						// Get the list of Volunteers for the selected job and
						// display.
						List<User> vols = jobs.get(index).getVolunteers();
						if (vols.size() > 0) {
							for (User v : vols) {
								System.out.println(v.toString());
							}
						} else {
							System.out.println("No volunteers are scheduled for this job.\n");
						}
					} else {
						System.out.println("please make a valid selection.");
					}
				} catch (NumberFormatException | IOException nfe) {
					System.out.println("Please make a valid selection");
				}
			}
		} else {
			System.out.println("No Jobs are scheduled for this park.");
		}
		mainMenu();
	}


	//Private method that displays jobs for a specific park.
	private void viewJobs() {
		System.out.println("********View Jobs********");
		List<Job> jobs = getJobs();
		for (int i = 0; i < jobs.size(); i++) {
			System.out.println(jobs.get(i).toString());
		}
		mainMenu();
	}

	//Private helper method that returns a list of jobs.
	private List<Job> getJobs() {
		String thePark;
		int ind;
		boolean validPark = false;
		List<Job> jobList = new ArrayList<Job>();

		System.out.println("Please choose a park.");
		for (int i = 0; i < myUser.getParks().size(); i++) {
			System.out.println((i) + ": " + myUser.getParks().get(i));
		}
		while (!validPark) {
			try {
				ind = Integer.parseInt(myConsole.readLine());
				if (ind >= 0 && ind < myUser.getParks().size()) {
					validPark = true;
					thePark = myUser.getParks().get(ind);
					jobList = (List<Job>) (myAbilities.getJobsAtPark(thePark));
				} else {
					System.out.println("please make a valid selection.");
					;
				}
			} catch (NumberFormatException | IOException nfe) {
				System.out.println("Please make a valid selection");
			}
		}
		return jobList;
	}

	//Private method that adds a valid Job to a park.
	private void addJob() {
		String thePark = "";
		int numDays = 0;
		int numLightJobs = 0;
		int numMediumJobs = 0;
		int numHeavyJobs = 0;
		GregorianCalendar startDate = null;
		boolean validInput = false;
		boolean validDate = false;
		boolean valueValid = false;
		int index = -1;
		
		StringBuilder theDescription = new StringBuilder();
		System.out.println("********Add a Job********");
		
		//********************Business Rule********************//
		//Answers the question: Are there too many jobs in the schedule//
		if(myAbilities.tooManyTotalJobs()){
			System.out.println("The number of jobs for this system is at capacity. You will not be able to add a job at this time and "
					+ "will be redirected to the main menu.");
			mainMenu();
		}else{
			System.out.println("Please choose a park.");
			for(int i = 0; i<myUser.getParks().size(); i++) {
				System.out.println((i) + ": " + myUser.getParks().get(i));
			}
			while(!validInput){
				try{
					index = Integer.parseInt(myConsole.readLine());
					if(index >= 0 && index < myUser.getParks().size()){
						validInput = true;
						thePark = myUser.getParks().get(index);
						index = -1;
					}else{
						System.out.println("please make a valid selection.");;
					}
				}catch(NumberFormatException | IOException nfe){
					System.out.println("Please make a valid seletion");
				}
			}
			validInput = false; //reset input boolean
			
			//Ask for the job name and read in a job name
			System.out.println("What is the job name?");
			try {
				theDescription.append(myConsole.readLine() + " ");
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			//Ask the User for the month, day and year 
			startDate = enterDate();
			numDays = enterNumDays();
				
			while(!validDate){	
				//***********************Business Rule Check***********************//
				//Send the date and length of job to helper methods for validation//
				if(passesDateRules(startDate, numDays)){
					validDate = true;
				}else{
					while(!valueValid){	//Check for valid input
						System.out.println("What would you like to do next?\n");
						System.out.println("1) Enter information again\n2) Return to the main menu\n3) Quit\n");
							try{
								int h = Integer.parseInt(myConsole.readLine());
								if(h >0 && h <4){
									valueValid = true; 
									switch(h){
									case 1:
										validDate = false;
										startDate = enterDate();
										numDays = enterNumDays();
										break;
									case 2:
										mainMenu();
										break;
									case 3:
										myAbilities.saveJobs(Config.JOB_SCHEDULE_FILE);
										System.exit(0);
									default:
										System.out.println("outor: incorrect choice taken in console input");
									}
								}else{
									System.out.println("Please make a valid selection");
								}
							} catch(NumberFormatException | IOException nfe){
								System.out.println("Please make a valid selection");
							}
						}
					valueValid = false;//resetting boolean for input validation
					}
				}
			}
			

			//Asks and captures the times for a job
			System.out.println("What are the hours for this job?");
			try {
				theDescription.append(myConsole.readLine() + " ");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Ask for the description
			System.out.println("Please enter any notes for this job.");
			try {
				theDescription.append(myConsole.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Ask for the number of light jobs
			System.out.println("How many volunteers do you need for light work?");
			
			while(!validInput){
				try{
					index = Integer.parseInt(myConsole.readLine());
					if(index >= 0){
						validInput = true;
						numLightJobs = index;
					}else{
						System.out.println("please make a valid selection.");
					}
				}catch(NumberFormatException | IOException nfe){
					System.out.println("Please make a valid seletion");
				}
			}
			validInput = false;
			
			//ask for the number of med jobs
			System.out.println("How many volunteers do you need for medium work?");
			while(!validInput){
				try{
					index = Integer.parseInt(myConsole.readLine());
					if(index >= 0){
						validInput = true;
						numMediumJobs = index;
					}else{
						System.out.println("please make a valid selection.");
					}
				}catch(NumberFormatException | IOException nfe){
					System.out.println("Please make a valid seletion");
				}
			}
			validInput = false;
			
			//Ask for the number of heavy jobs
			System.out.println("How many volunteers do you need for heavy work?");
			while(!validInput){
				try{
					index = Integer.parseInt(myConsole.readLine());
					if(index >= 0){
						validInput = true;
						numHeavyJobs = index;
					}else{
						System.out.println("please make a valid selection.");
					}
				}catch(NumberFormatException | IOException nfe){
					System.out.println("Please make a valid seletion");
				}
			}
			
			//Creates a job with validated information that has passed the business rules. 
			Job thisJob = new Job(thePark, startDate, numDays,numLightJobs, numMediumJobs, numHeavyJobs, theDescription.toString());
	
			//Adds the job to the schedule
			myAbilities.addJob(thisJob);
			System.out.println("\n\nYour job is now on the schedule\n\n");
			mainMenu();
	}

	//***********************Business Rule Check***********************//
	//Private method to check dates and length of job
	//Returns true if the business rules are followed
	//Returns false if the business rules are violated
	private boolean passesDateRules(GregorianCalendar gc, int days){
		int violations = 0;
		
		if(RulesHelp.isDateInPast(gc)){
			violations++;
			System.out.println("The date is in the past.");
		}
		
		if(RulesHelp.isDateTooFarInFuture(gc)){
			violations++;
			System.out.println("The date is too far in the future.");
		}
		
		if(days > Config.MAX_JOB_DAYS){
			violations++;
			System.out.println("The job's duration is too long.");
		}
		
		if(days <= 0) {
			violations++;
			System.out.println("The job must be at least one day long.");
		}
		
		if(myAbilities.tooManyJobsNearJobTime(gc, days)){
			violations++;
			System.out.println("There are too many jobs scheduled for that week.");
		}

		return (violations == 0);
	}
	
		
	//Private method that asks for and capture date information for a job.
	private GregorianCalendar enterDate(){
		GregorianCalendar theDate;
		int month = 0;
		int day = 0;
		int year = 0;
		int temp = 0;
		boolean validIn = false;

		//MONTH
		System.out.println("Enter the month (number):");
		while(!validIn){
			try{
				temp = Integer.parseInt(myConsole.readLine());
				if(temp > 0 && temp < 13){
					validIn = true;
					month = temp - 1; 
				}else{
					System.out.println("Please make a valid selection (1-12).");
				}
			}catch(NumberFormatException | IOException nfe){
				System.out.println("Please make a valid seletion");
			}
		}
		validIn = false; //reset input boolean
		
		//DAY
		System.out.println("Enter the starting day:");
		while(!validIn){
			try{
				temp = Integer.parseInt(myConsole.readLine());
				if(temp > 0 && temp < 32){
					validIn = true;
					day = temp;
				}else{
					System.out.println("Please make a valid selection (1-31).");
				}
			}catch(NumberFormatException | IOException nfe){
				System.out.println("Must be a number.");
			}
		}
			validIn = false;//reset input boolean
			
			//YEAR
			System.out.println("Enter the year (yyyy):");
			while(!validIn){
				try{
					temp = Integer.parseInt(myConsole.readLine());
					if(temp > 0){
						validIn = true;
						year = temp;
					}else{
						System.out.println("Please make a valid selection (1-31).");
					}
				}catch(NumberFormatException | IOException nfe){
					System.out.println("Must be a number.");
				}
			}
			
			//Formats the input and sends back the date.
			theDate = new GregorianCalendar(year, month, day);
			return theDate;
	}

	//Private method that asks for and captures the length of a job in days. 
	private int enterNumDays(){
		boolean validNum = false;
		int numDays = 0;

			System.out.println("How long does this job last? (in number of days)");
			while(!validNum){
				try{
					numDays = Integer.parseInt(myConsole.readLine());
					validNum = true;
				}catch(NumberFormatException | IOException nfe){
					System.out.println("Must be a number.");
				}
			}
			return numDays;	
	}
}
//End Class