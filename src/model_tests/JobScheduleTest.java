package model_tests;

/**
 * @author shewan 
 * @author Julia
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import park_model.Job;
import park_model.JobSchedule;
import park_model.User;
import park_model.WorkCategory;
import config_files.Config;

public class JobScheduleTest {

	/**
	 * A JobSchedule initialized with 0 jobs.
	 */
	private JobSchedule myJobSchedule;
	private GregorianCalendar legalDate;
	private List<GregorianCalendar> myOneDateList;
	
	/**
	 * A job that is scheduled on today only.
	 */
	private Job myDefaultJob;

	@Before
	public void setUp() throws Exception {
		myJobSchedule = new JobSchedule(true);
		myOneDateList = new ArrayList<GregorianCalendar>();
		legalDate = Config.getTodaysDate();
		legalDate.add(Calendar.DATE, 1);
		myOneDateList.add(legalDate);
		myDefaultJob = new Job("Park", Config.getTodaysDate(), 1, 1,
				1, 1, "description");
	}
	
	@Test
	public void test() {
		assertTrue(true);
	}
	
//	// Expires in August due to hard-coded time values
//	@SuppressWarnings("resource")
//	@Test
//	public void JobScheduleConstructorAndSaveFileShouldPreserveJobInfo() {
//		JobSchedule jobSchedule = 
//				new JobSchedule("jobScheduleAllJobsInFuture.txt");
//		assertEquals("All jobs should be in list", 
//				3, jobSchedule.numberOfJobs());
//		jobSchedule.saveList("tempJobSchedule.txt");
//		jobSchedule = 
//				new JobSchedule("jobScheduleAllJobsInFuture.txt");
//		assertEquals("All jobs should be in list", 
//				3, jobSchedule.numberOfJobs());
//		jobSchedule.saveList("tempJobSchedule.txt");
//		jobSchedule = 
//				new JobSchedule("jobScheduleAllJobsInFuture.txt");
//		assertEquals("Exact original jobs should be in list after file overwrite", 
//				3, jobSchedule.numberOfJobs());
//		// http://jdevelopment.nl/java-7-oneliner-read-file-string/
//		String original = null;
//		try {
//			original = new Scanner(new File("src/config_files/jobScheduleAllJobsInFuture.txt")).useDelimiter("\\Z").next();
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		String copy = null;
//		try {
//			copy = new Scanner(new File("src/config_files/tempJobSchedule.txt")).useDelimiter("\\Z").next();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		assertEquals("Original file and copy should be the same", original, copy);
//	}
	
//	// Expires in August due to hard-coded time values
//	@Test
//	public void JobScheduleConstructorShouldRemovePastJobs() {
//		JobSchedule jobSchedule = 
//				new JobSchedule("jobScheduleOneJobInPast.txt");
//		assertEquals("One of the four original jobs should be eliminated", 
//				3, jobSchedule.numberOfJobs());
//	}
//
//	@Test
//	public void testAddJobShouldAddAJob() {
//		assertEquals("Job list should start empty", 0,
//				myJobSchedule.numberOfJobs());
//		myJobSchedule.addJob(myDefaultJob);
//		assertEquals("Job list should now have one job", 1,
//				myJobSchedule.numberOfJobs());
//	}
//
//	@Test
//	public void testTooManyExistingJobsShouldReturnFalseUntilJobsGreaterThanMax() {
//		boolean check;
//		for (int i = 1; i <= Config.MAX_TOTAL_JOBS; i++) {
//			check = myJobSchedule.tooManyExistingJobs();
//			myJobSchedule.addJob(new Job(myDefaultJob));
//			assertEquals("Not too many jobs", false, check);
//		}
//		assertEquals("We should now have maximum possible jobs",
//				myJobSchedule.numberOfJobs(), Config.MAX_TOTAL_JOBS);
//		check = myJobSchedule.tooManyExistingJobs();
//		assertEquals("Too many jobs", true, check);
//	}
//
//	@Test
//	public void testIsWeekFullShouldReturnFalseUntilMoreJobsThanAllowedPerWeek() {
//		GregorianCalendar date = Config.getTodaysDate();
//		GregorianCalendar comparisonDate = Config.getTodaysDate();
//		comparisonDate.add(Calendar.DATE, 2);
//		Job comparisonJob = new Job("Park", comparisonDate, 1, 1, 1, 1, "");
//		for (int i = 1; i <= Config.MAX_DENSE_JOBS; i++) {
//			assertEquals("Week is not full", false,
//					myJobSchedule.isWeekFull(comparisonJob));
//			Job job = new Job("Park", (GregorianCalendar) date.clone(),
//					1, 1, 1, 1, "");
//			myJobSchedule.addJob(job);
//			date.add(Calendar.DATE, 1);
//			
//		}
//		assertEquals("Week is full", true,
//				myJobSchedule.isWeekFull(comparisonJob));
//	}
//
//	// A volunteer may not sign up for a work catagory on a job if the max
//	// number of volunteers for that
//	// work catagoey has aleady been reached
//	@Test
//	public void testNoVolunteerSignUpForSameDate() {
//
//		List<GregorianCalendar> jobDates = new ArrayList<>();
//		jobDates.add((GregorianCalendar) legalDate.clone());
//		Job job = new Job("Park", jobDates, 1, 1, 1, "");
//		Job job2 = new Job("Park", jobDates, 1, 1, 1, "");
//		// Two jobs with the same date
//		myJobSchedule.addJob(job);
//		myJobSchedule.addJob(job2);
//
//		User v1 = new User("you@gmail.com", "", "Wise");
//		job.signUp(v1, WorkCategory.HEAVY);
//
//		assertEquals("Volunteer can't sign up for two jobs same day", 0,
//				myJobSchedule.getJobsForSignUp(v1).size());
//
//	}
//
//	@Test
//	public void testNoVolunteerSignUpWhenMaxReached() {
//
//		List<GregorianCalendar> jobDates = new ArrayList<>();
//		jobDates.add((GregorianCalendar) legalDate.clone());
//		Job job = new Job("Park", jobDates, 1, 1, 1, "");
//		myJobSchedule.addJob(job);
//
//		// Add volunteers for for the job in each categories
//		User v1 = new User("you@gmail.com", "", "Wise");
//		job.signUp(v1, WorkCategory.HEAVY);
//
//		User v2 = new User("you@gmail.com", "", "Wise");
//		job.signUp(v2, WorkCategory.MEDIUM);
//
//		User v3 = new User("everyone@gmail.com", "", "Gandolf");
//		job.signUp(v3, WorkCategory.LIGHT);
//
//		assertEquals(
//				"Volunteer can't sign up Since maximum for the job reached And no twice registration",
//				0, myJobSchedule.getJobsForSignUp(v1).size());
//
//	}
//
//	public static class DateUtil {
//		public static Date addDays(Date date, int days) {
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(date);
//			cal.add(Calendar.DATE, days); // minus number would decrement the
//											// days
//			return cal.getTime();
//		}
//	}
//
//	@Test
//	public void testUpComingJobsByVolunteer() throws ParseException {
//
//		List<GregorianCalendar> jobDates = new ArrayList<>();
//		jobDates.add((GregorianCalendar) legalDate.clone());
//
//		List<GregorianCalendar> jobDates2 = new ArrayList<>();
//		jobDates2.add((GregorianCalendar) legalDate.clone());
//
//		Job job = new Job("Park", jobDates, 1, 1, 1, "");
//		myJobSchedule.addJob(job);
//
//		Job job2 = new Job("Park", jobDates, 1, 1, 1, "");
//		myJobSchedule.addJob(job2);
//
//		User v1 = new User("you@gmail.com", "", "Wise");
//		job.signUp(v1, WorkCategory.HEAVY);
//
//		job2.signUp(v1, WorkCategory.HEAVY);
//
//		assertEquals("Volunteer didn't register for future jobs", 0,
//				myJobSchedule.getJobsForSignUp(v1).size());
//
//	}
//
//	@Test
//	public void testJobsByParkName() {
//		List<GregorianCalendar> jobDates = new ArrayList<>();
//		jobDates.add((GregorianCalendar) legalDate.clone());
//
//		Job job = new Job("Park", jobDates, 1, 1, 1, "");
//		myJobSchedule.addJob(job);
//
//		Job job2 = new Job("Park2", jobDates, 1, 1, 1, "");
//		myJobSchedule.addJob(job2);
//
//		assertEquals("Volunteer didn't register for future jobs", 1,
//				myJobSchedule.getJobsByPark("Park").size());
//		assertTrue("Volunteer didn't register for future jobs", myJobSchedule
//				.getJobsByPark("Park2").get(0).getParkName().equals("Park2"));
//
//	}

}

