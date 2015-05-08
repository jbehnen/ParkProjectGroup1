package model_tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import config_files.Config;
import park_model.DateAndTime;
import park_model.Job;
import park_model.JobSchedule;
import park_model.WorkCategory;

@SuppressWarnings("deprecation")
public class JobScheduleTest {

	private JobSchedule myJobSchedule;
	private GregorianCalendar today;
	private DateAndTime myLegalDAT;
	private List<DateAndTime> myDateList;
	private Job myDefaultJob;

	@Before
	public void setUp() throws Exception {
		myJobSchedule = new JobSchedule(true);
		myDateList = new ArrayList<>();
		today = Config.getTodaysDate();
		today.add(Calendar.DATE, 1);
		myLegalDAT = new DateAndTime(today.get(Calendar.YEAR),
				today.get(Calendar.MONTH), today.get(Calendar.DATE),
				"", "");
		myDateList.add(myLegalDAT);
		myDefaultJob = new Job("Park", myDateList, 1,
				1, 1);
	}

	@Test
	public void testAddJob() {
		assertEquals("Job list should start empty", 0,
				myJobSchedule.numberOfJobs());
		myJobSchedule.addJob(myDefaultJob);
		assertEquals("Job list should now have one job", 1,
				myJobSchedule.numberOfJobs());
	}

	@Test
	public void testTooManyExistingJobs() {
		boolean check;
		for (int i = 1; i <= Config.MAX_TOTAL_JOBS; i++) {
			check = myJobSchedule.tooManyExistingJobs();
			myJobSchedule.addJob(myDefaultJob);
			assertEquals("Not too many jobs", false, check);
		}
		assertEquals("We should now have maximum possible jobs",
				myJobSchedule.jobListSize(), Config.MAX_TOTAL_JOBS);
		check = myJobSchedule.tooManyExistingJobs();
		assertEquals("Too many jobs", true, check);
	}

	@Test
	public void testIsWeekFull() {
		GregorianCalendar date = Config.getTodaysDate();
		GregorianCalendar comparisonDate = Config.getTodaysDate();
		comparisonDate.add(Calendar.DATE, 2);
		List<DateAndTime> comparisonDateList = new ArrayList<>();
		comparisonDateList.add(new DateAndTime(comparisonDate, "", ""));
		Job comparisonJob = new Job("Park", comparisonDateList, 1, 1, 1);
		for (int i = 1; i <= 5; i++) {
			assertEquals("Week is not full", false,
					myJobSchedule.isWeekFull(comparisonJob));
			List<DateAndTime> jobDates = new ArrayList<>();
			jobDates.add(new DateAndTime((GregorianCalendar) date.clone(), "", ""));
			Job job = new Job("Park", jobDates, 1, 1, 1);
			myJobSchedule.addJob(job);
			date.add(Calendar.DATE, 1);
			
		}
		assertEquals("Week is full", true,
				myJobSchedule.isWeekFull(comparisonJob));
	}

	// A volunteer may not sign up for a work catagory on a job if the max
	// number of volunteers for that
	// work catagoey has aleady been reached
	
	@Test
	public void testNoVolunteerSignUpForSameDate() {

//		List<Date> jobDates = new ArrayList<>();
//		jobDates.add((Date) legalDate.clone());
//		Job job = new Job("Park", jobDates, 1, 1, 1);
//		Job job2 = new Job("Park", jobDates, 1, 1, 1);
//		// Two jobs with the same date
//		myJobSchedule.addJob(job);
//		myJobSchedule.addJob(job2);
//
//		Volunteer v1 = new Volunteer("you@gmail.com", "Wise");
//		job.signUp(v1, WorkCategory.HEAVY);
//
//		assertEquals("Volunteer can't sign up for two jobs same day", 0,
//				myJobSchedule.getJobsForSignUp(v1).size());

	}

	@Test
	public void testNoVolunteerSignUpWhenMaxReached() {

//		List<Date> jobDates = new ArrayList<>();
//		jobDates.add((Date) legalDate.clone());
//		Job job = new Job("Park", jobDates, 1, 1, 1);
//		myJobSchedule.addJob(job);
//
//		// Add volunteers for for the job in each categories
//		Volunteer v1 = new Volunteer("you@gmail.com", "Wise");
//		job.signUp(v1, WorkCategory.HEAVY);
//
//		Volunteer v2 = new Volunteer("you@gmail.com", "Wise");
//		job.signUp(v2, WorkCategory.MEDIUM);
//
//		Volunteer v3 = new Volunteer("everyone@gmail.com", "Gandolf");
//		job.signUp(v3, WorkCategory.LIGHT);
//
//		assertEquals(
//				"Volunteer can't sign up Since maximum for the job reached And no twice registration",
//				0, myJobSchedule.getJobsForSignUp(v1).size());

	}

	// We don't need this any more now that we have GregorianCalendar. 
	
//	public static class DateUtil {
//		public static Date addDays(Date date, int days) {
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(date);
//			cal.add(Calendar.DATE, days); // minus number would decrement the
//											// days
//			return cal.getTime();
//		}
//	}

	@Test
	public void testUpComingJobsByVolunteer() throws ParseException {

//		List<Date> jobDates = new ArrayList<>();
//		jobDates.add((Date) legalDate.clone());
//
//		List<Date> jobDates2 = new ArrayList<>();
//		jobDates2.add((Date) legalDate.clone());
//
//		Job job = new Job("Park", jobDates, 1, 1, 1);
//		myJobSchedule.addJob(job);
//
//		Job job2 = new Job("Park", jobDates, 1, 1, 1);
//		myJobSchedule.addJob(job2);
//
//		Volunteer v1 = new Volunteer("you@gmail.com", "Wise");
//		job.signUp(v1, WorkCategory.HEAVY);
//
//		job2.signUp(v1, WorkCategory.HEAVY);
//
//		assertEquals("Volunteer didn't register for future jobs", 0,
//				myJobSchedule.getJobsForSignUp(v1).size());

	}

	@Test
	public void testJobsByParkName() {
//		List<Date> jobDates = new ArrayList<>();
//		jobDates.add((Date) legalDate.clone());
//
//		Job job = new Job("Park", jobDates, 1, 1, 1);
//		myJobSchedule.addJob(job);
//
//		Job job2 = new Job("Park2", jobDates, 1, 1, 1);
//		myJobSchedule.addJob(job2);
//
//		assertEquals("Volunteer didn't register for future jobs", 1,
//				myJobSchedule.getJobsByPark("Park").size());
//		assertTrue("Volunteer didn't register for future jobs", myJobSchedule
//				.getJobsByPark("Park2").get(0).getParkName().equals("Park2"));

	}

}

