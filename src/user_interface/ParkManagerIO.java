/**
 * ParkManagerIO.java 
 * 
 * A class that creates jobs, adds them to the job schedule, 
 * provides a viewable list of upcoming jobs and provides a
 * viewable list for volunteers signed up for a specific job
 * 
 * Author: L. Hamaker lahama9@uw.edu
 * Date of last edit: 10 May 2015
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

	private ParkManager myUser;
	private PMAbilities abilities;
	
	public ParkManagerIO(ParkManager myUser) {
		this.myUser = myUser;
		abilities = new PMAbilities(Config.JOB_SCHEDULE_FILE_FOR_STATIC);
	}

	@Override
	public void mainMenu() {
		int i = 0;
		boolean validChoice = false;
		
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("\nPlease type the number of the action you want to perform.");
		System.out.println("1:Create a new job. \n2:View upcoming jobs. \n3:View the volunteers for a job. \n4:Quit program. ");
		
		//IO try/catch was adapted from code found at http://stackoverflow.com/questions/4644415/java-how-to-get-input-from-system-console
		while(!validChoice){
			try{
				i = Integer.parseInt(console.readLine());
				if(i>0 && i<5){
					validChoice = true; 
					switch(i){
					case 1:
						addJob(console);
						break;
					case 2:
						viewJobs(console);
						break;
					case 3:
						viewVol(console);
						break;
					case 4:
						abilities.saveJobs(Config.JOB_TEST_OUTPUT_FILE_FOR_STATIC);
						System.exit(0);
					default:
						System.err.println("Error: incorrect choice taken at console input");
					}
				}else{
					System.err.println("Please make a valid selection");
				}
			} catch(NumberFormatException | IOException nfe){
				System.err.println("Please make a valid selection");
			}
		}
	}
	
	//Method that views volunteers for a specific job
	private void viewVol(BufferedReader con){
		
		boolean validJob = false;
		int index;
		List<Job> jobs= getJobs(con);
		
		//Display the jobs
		if(jobs.size() >0){
			System.out.println("Which job do you want to see?");
			for(int i = 0; i < jobs.size(); i++) {
				System.out.println(i + ": " + jobs.get(i).toString());
			}
			
			//Validate the selection
			while(!validJob){
				try{
					index = Integer.parseInt(con.readLine());
					if(index >= 0 && index < jobs.size()){
						validJob = true;
						//Get the list of Volunteers for the selected job and display. 
						List<User> vols = jobs.get(index).getVolunteers();
						if(vols.size() >0){
							for(User v : vols){
								System.out.println(v.toString());
							}
						}else{
							System.out.println("There are no volunteers schedules for this job.");
						}
						
					}else{
						System.err.println("please make a valid selection.");;
					}
				}catch(NumberFormatException | IOException nfe){
					System.err.println("Please make a valid selection");
				}
			}
		}else{
			System.out.println("There are no jobs scheduled for this park.");
		}
		mainMenu();
	}
	
	//Method that views jobs for a specific park
	private void viewJobs(BufferedReader con){
		List<Job> jobs= getJobs(con);
		for(int i = 0; i < jobs.size(); i++) {
			System.out.println(jobs.get(i).toString());
		}
		mainMenu();
	}
	

	private List<Job> getJobs(BufferedReader c){
		String thePark;
		int ind;
		boolean validPark = false;
		List<Job> jobList = new ArrayList<Job>();
		
		System.out.println("Please choose a park.");
		for(int i = 0; i<myUser.getParks().size(); i++) {
			System.out.println((i) + ": " + myUser.getParks().get(i));
		}
		while(!validPark){
			try{
				ind = Integer.parseInt(c.readLine());
				if(ind >= 0 && ind < myUser.getParks().size()){
					validPark = true;
					thePark = myUser.getParks().get(ind);
					jobList = (List<Job>)(abilities.getJobsAtPark(thePark));
				}else{
					System.err.println("please make a valid selection.");;
				}
			}catch(NumberFormatException | IOException nfe){
				System.err.println("Please make a valid selection");
			}
		}
		return jobList;
	}
	
	private void addJob(BufferedReader console2) {
		String thePark = "";
		int temp = 0;
		int month = 0;
		int day = 0;
		int year = 0;
		int numDays = 0;
		int numLightJobs = 0;
		int numMediumJobs = 0;
		int numHeavyJobs = 0;
		boolean validPick = false;
		boolean validDate = false;
		int index = -1;
		
		StringBuilder theDescription = new StringBuilder();
		
		//Ask for a park and get park
		System.out.println("Please choose a park.");
		for(int i = 0; i<myUser.getParks().size(); i++) {
			System.out.println((i) + ": " + myUser.getParks().get(i));
		}
		while(!validPick){
			try{
				index = Integer.parseInt(console2.readLine());
				if(index >= 0 && index < myUser.getParks().size()){
					validPick = true;
					thePark = myUser.getParks().get(index);
					index = -1;
				}else{
					System.err.println("please make a valid selection.");;
				}
			}catch(NumberFormatException | IOException nfe){
				System.err.println("Please make a valid seletion");
			}
		}
		validPick = false;
		
		//////////Ask for the job name and read in a job name
		System.out.println("What is the job name?");
		try {
			theDescription.append(console2.readLine() + " ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(theDescription.toString());

		///////////////////////////DATE///////////////////////////////////////
		//////////Get the month
		System.out.println("Enter the month (number):");
		while(!validDate){
			try{
				temp = Integer.parseInt(console2.readLine());
				if(temp > 0 && temp < 13){
					validDate = true;
					month = temp - 1; 
				}else{
					System.err.println("Please make a valid selection (1-12).");
				}
			}catch(NumberFormatException | IOException nfe){
				System.err.println("Please make a valid seletion");
			}
		}
		
		validDate = false;
		
		///////Get the Day
		System.out.println("Enter the starting day:");
		while(!validDate){
			try{
				temp = Integer.parseInt(console2.readLine());
				if(temp > 0 && temp < 32){
					validDate = true;
					day = temp;
				}else{
					System.err.println("Please make a valid selection (1-31).");
				}
			}catch(NumberFormatException | IOException nfe){
				System.err.println("Must be a number.");
			}
		}
		validDate = false;
		
		////////Get the year
		System.out.println("Enter the year (yyyy):");
		while(!validDate){
			try{
				temp = Integer.parseInt(console2.readLine());
				if(temp > 0){
					validDate = true;
					year = temp;
				}else{
					System.err.println("Please make a valid selection (1-31).");
				}
			}catch(NumberFormatException | IOException nfe){
				System.err.println("Must be a number.");
			}
		}
		validDate = false;

		///////////Ask if it is a two day job
		System.out.println("How long does this job last? (in number of days)");
		while(!validDate){
			try{
				numDays = Integer.parseInt(console2.readLine());
				validDate = true;
			}catch(NumberFormatException | IOException nfe){
				System.err.println("Must be a number.");
			}
		}

		///////////////////////////TIME///////////////////////////////////////
		System.out.println("What are the hours for this job?");
		try {
			theDescription.append(console2.readLine() + " ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		///////////////////////////Description////////////////////////////////
		//Ask for the description
		System.out.println("Please enter any notes for this job.");
		try {
			theDescription.append(console2.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		///////////////////////Work Category/////////////////////////////////
		//Ask for the num of light jobs
		System.out.println("How many volunteers do you need for light work?");
		
		while(!validPick){
			try{
				index = Integer.parseInt(console2.readLine());
				if(index >= 0){
					validPick = true;
					numLightJobs = index;
				}else{
					System.err.println("please make a valid selection.");
				}
			}catch(NumberFormatException | IOException nfe){
				System.err.println("Please make a valid seletion");
			}
		}
		validPick = false;
		
		//ask for the num of med jobs
		System.out.println("How many volunteers do you need for medium work?");
		while(!validPick){
			try{
				index = Integer.parseInt(console2.readLine());
				if(index >= 0){
					validPick = true;
					numMediumJobs = index;
				}else{
					System.err.println("please make a valid selection.");
				}
			}catch(NumberFormatException | IOException nfe){
				System.err.println("Please make a valid seletion");
			}
		}
		validPick = false;
		
		//Ask for the num of heavy jobs
		System.out.println("How many volunteers do you need for heavy work?");
		while(!validPick){
			try{
				index = Integer.parseInt(console2.readLine());
				if(index >= 0){
					validPick = true;
					numHeavyJobs = index;
				}else{
					System.err.println("please make a valid selection.");
				}
			}catch(NumberFormatException | IOException nfe){
				System.err.println("Please make a valid seletion");
			}
		}
		
		////////////////CREATE USER LIST///////////////////////////
		List<User> newList = new ArrayList<User>();
		
		////////////////////CREATE JOB////////////////////////////
		GregorianCalendar startDate = new GregorianCalendar(year, month, day);
		Job thisJob = new Job(thePark, startDate, numDays,numLightJobs, numMediumJobs, numHeavyJobs, theDescription.toString());

		//If it passed, then add to jobSchedule
		if(testsPassed(thisJob)){
			abilities.addJob(thisJob);
			System.out.println("Your job is now on the schedule");
		}else{
			System.out.println("Your job was not added");
		}
		mainMenu();
	}

	//////////////////////////////////////////////////////////
	///////////////VALIDATION CHECK OF JOBS///////////////////
	//If the job passes the 5 tests, then TRUE is returned. 
	private boolean testsPassed(Job j){
		int count = 0;
		
		//If it returns true, the date is in the past and the job cannot be added to the schedule
		if(RulesHelp.isDateInPast(j.getFirstDate())){
			System.out.println("The starting date is in the past.");
			count++;
		}
		
		//If the starting date is too far in the future, the job cannot be added to the schedule. 
		if(RulesHelp.isDateTooFarInFuture(j.getFirstDate())){
			System.out.println("The date is too far in the future.");
			count++;
		}
		
		///If it returns false, the job is the right amount of time and can be added to the schedule.
		///If it returns true, it is not and cannot be added to the schedule
		if(j.getNumDays() > Config.MAX_JOB_DAYS){
			System.out.println("The job's duration is too long.");
			count++;
		}
		
		///If it returns false, the week is not too full and the job can be scheduled.
		///If it returns true, the week is too full and the job cannot be scheduled. 
		if(abilities.tooManyTotalJobs()){
			System.out.println("The week's schedule is too full.");
			count++;
		}
		
		///If it returns false, the maximum number of jobs have not been reached and the job can be added. 
		///If it returns true, the maximum number of jobs have been reached and the job cannot be added to the system. 
		if(abilities.tooManyJobsNearJobTime(j)){
			System.out.println("There are too many jobs in the system.");
			count++;
		}
	
		return (count == 0);
	}
}