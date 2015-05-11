package user_interface;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import park_model.Job;
import park_model.JobSchedule;
import park_model.User;
import park_model.WorkCategory;
import config_files.Config;

public class VolunteerIO implements IO {

	private User myUser;
	private JobSchedule myJobSchedule;
	
	
	public VolunteerIO(User myUser) {
		this.myUser = myUser;
		myJobSchedule = new JobSchedule(Config.JOB_SCHEDULE_FILE);
	}

	@Override
	public void mainMenu() {
			System.out.println(String.format("%40s","Hi Volunteer " + myUser.getFirstName()));		
			Scanner myScan = new Scanner(System.in);

			int choice = 0;
			do {
				System.out.println(" What do you want to do today?");
				// Want to see upcoming jobs i can sign for
				System.out.println("  1. Look for future Jobs");
				// i want to see job i am signed up for
				System.out.println("  2. Sign up for jobs");
				System.out.println("  3. View my jobs");
				System.out.println("  10. Exit");

				choice = myScan.nextInt();

			} while (invalidate(0,choice,null));

			manageVolunteerOption(choice); 
	}
	


	private void manageVolunteerOption(int nextInt) {
		 boolean askForSignUp = false;     
		
		switch (nextInt) {
		case 1:
			// see Open jobs
			process(myJobSchedule.getJobsForSignUp(myUser),askForSignUp);
			//System.out.print(myJobSchedule.getJobsForSignUp(myUser));
			break;
		case 2:
			// see Open jobs
			askForSignUp = true;
			process(myJobSchedule.getUpcomingJobsByVolunteer(myUser),askForSignUp);
			// Inner option call to sign up?
			break; 
		case 3:
			listMyJobs();
			mainMenu();
			break;
		case 10:
			System.out.println("Thanks for visting us");
			myJobSchedule.saveList(Config.JOB_SCHEDULE_FILE);;
			System.exit(0);
		default:
			System.out.print("");
		}

	}

	private void listMyJobs() {
		for (Job job: myJobSchedule.getUpcomingJobsByVolunteer(myUser)) {
			System.out.println(job.toString());
		}
	}
	
	private void signup(User theUser, Job theJob) {
		Scanner myotherScanner = new Scanner(System.in);
		do{
		System.out.println(String.format("%50s"," This job currently has the following work need "));
		System.out.println(String.format("%50s"," 4. Heavy Load    "  + theJob.getNumHeavy()));
		System.out.println(String.format("%50s"," 5. Medium Load   " + theJob.getNumLight()));
		System.out.println(String.format("%50s"," 6. Light Load    " + theJob.getNumMedium()));
		
		System.out.println(String.format("%50s"," Which Load do you need ot sign up for? "));
		} while(invalidate(1,myotherScanner.nextInt(),theJob));
			
	}

	private void process(List<Job> list, boolean askForSignUp) {
		   GregorianCalendar gcalendar = new GregorianCalendar();
	      // Display current time and date information.
	      System.out.print("As of Date: ");
	      System.out.print(gcalendar.get(Calendar.MONTH));
	      System.out.print(" " + gcalendar.get(Calendar.DATE) + " ");
	      System.out.println(gcalendar.get(Calendar.YEAR));

		int count = 0;
		int size = list.size();

		if (size > 0) {
			System.out.println("Displaying now " + size + " record(s)");
			for (Job myJob : list) {
				System.out.println(String.format("%-20s", ++count));
				
				System.out.println(String.format("%-20s", "Park Name: ",
						"%-20s" + myJob.getParkName().toString()));
				
				
				System.out.println(String.format("%-20s", "Start Date: "
						+ myJob.getDates().get(0).get(Calendar.YEAR) + " "
						+ myJob.getDates().get(0).get(Calendar.MONTH) + " "
						+ myJob.getDates().get(0).get(Calendar.DATE)));

				System.out.println(String.format("%-20s",
						"Volunteers for a Light Duty: " + myJob.getNumLight()));
				
				System.out.println(String
						.format("%-20s", "Volunteers for a Medium Duty: "
								+ myJob.getNumMedium()));
				
				System.out.println(String.format("%-20s",
						"Volunteers for a Heavy Duty: " + myJob.getNumHeavy()));
				
				// System.out.println(String.format("%-20s","Description: " +
				// myJob.getDescribtion())); + myJob.getDates().get(0).DATE
				
				if(askForSignUp){
					Scanner yesNo = new Scanner(System.in);
					System.out.print("");				
					System.out.println(String.format("%40s",myUser.getFirstName() + " , do you want to sign - up for this job ? y/n"));
					if(yesNo.next().toLowerCase().contains("y")){
						signup(myUser,myJob);
					}
					
					
				}
				

			}

		} else {
			System.out.println("There are no jobs.");
		}
		
		
		//Call internal volunteer menu again
		mainMenu();

	}
	
	
	private boolean invalidate(int inner, int choice, Job theJob) {

		if(inner == 0){
			
			switch (choice) {
			case 1:
			case 2:
				return false;
			case 3:
				return false;
			case 10:
				return false;
			}		
			
		} else{
			
			switch (choice) {
			case 4:
				    theJob.signUp(myUser, WorkCategory.HEAVY);
				    System.out.println("Successfully signed up for heavy job");
				    break;
			case 5:
				theJob.signUp(myUser, WorkCategory.MEDIUM);
				 System.out.println("Successfully signed up for Medium job");
				 break;
			case 6:
				theJob.signUp(myUser, WorkCategory.LIGHT);
				 System.out.println("Successfully signed up for Light job");
				 break;
				default:
					System.out.println("Invalid");
								
			}
			
			System.out.println("Thanks for Volunteering");
			//Call internal volunteer menu again
			mainMenu();
			return false;
		}
		
		
		System.out.println("Invalid Option");
		return true;
	}
}
