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

import park_model.Job;
import park_model.JobSchedule;
import park_model.Volunteer;
import park_model.WorkCategory;

@SuppressWarnings("deprecation")
public class JobScheduleTest {

	private JobSchedule myJobSchedule;
	private GregorianCalendar legalDate;
	private List<GregorianCalendar> myOneDateList;

	@Before
	public void setUp() throws Exception {
		myJobSchedule = new JobSchedule(true);
		myOneDateList = new ArrayList<DateAndTime>();
		legalDate = new GregorianCalendar();
		legalDate.add(Calendar.DATE, 1);
		myOneDateList.add(legalDate);
	}

	@Test
	public void testAddJobShouldWorkWithEmptyList() {
		assertEquals("Job list should start empty", 0,
				myJobSchedule.numberOfJobs());
		boolean check = myJobSchedule.addJob(new Job("Park", myOneDateList, 1,
				1, 1));
		assertEquals("Job list should now have one job", 1,
				myJobSchedule.numberOfJobs());
		assertEquals("Method should return true", true, check);
	}

	@Test
	public void testAddJobShouldNotWorkIfMaxJobsScheduled() {
		boolean check;
		for (int i = 1; i <= 30; i++) {
			List<GregorianCalendar> jobDates = new ArrayList<>();
			jobDates.add((Date) legalDate.clone());
			Job job = new Job("Park", jobDates, 1, 1, 1);
			check = myJobSchedule.addJob(job);
			legalDate.setDate(legalDate.getDate() + 2);
			assertEquals("Jobs should be successful so far", i,
					myJobSchedule.numberOfJobs());
			assertEquals("Method should be returning true", true, check);
		}

		List<Date> jobDates = new ArrayList<>();
		jobDates.add((Date) legalDate.clone());
		Job job = new Job("Park", jobDates, 1, 1, 1);
		check = myJobSchedule.addJob(job);
		assertEquals("Jobs number should not have increased", 30,
				myJobSchedule.numberOfJobs());
		assertEquals("Method should be returning false", false, check);
	}

	@Test
	public void testAddJobShouldWorkIfJobIsMaxDaysLong() {
		List<Date> jobDates = new ArrayList<Date>();
		for (int i = 1; i <= 2; i++) {
			jobDates.add(legalDate);
		}
		Job job = new Job("Park", jobDates, 1, 1, 1);
		boolean check = myJobSchedule.addJob(job);
		assertEquals("Job should be successful", 1,
				myJobSchedule.numberOfJobs());
		assertEquals("Method should be returning true", true, check);
	}

	@Test
	public void testAddJobShouldntWorkIfJobIsMoreThanMaxDaysLong() {
		List<Date> jobDates = new ArrayList<Date>();
		for (int i = 1; i <= 3; i++) {
			jobDates.add(legalDate);
		}
		Job job = new Job("Park", jobDates, 1, 1, 1);
		boolean check = myJobSchedule.addJob(job);
		assertEquals("Job should not be successful", 0,
				myJobSchedule.numberOfJobs());
		assertEquals("Method should be returning false", false, check);
	}

	@Test
	public void testAddJobShouldNotAddJobFromPast() {
		Date pastDate = new Date();
		pastDate.setDate(pastDate.getDate() - 1);
		List<Date> jobDates = new ArrayList<>();
		jobDates.add(pastDate);
		jobDates.add(legalDate);
		boolean check = myJobSchedule
				.addJob(new Job("Park", jobDates, 1, 1, 1));
		assertEquals("Job should not have been added", 0,
				myJobSchedule.numberOfJobs());
		assertEquals("Method should return false", false, check);
	}

	@Test
	public void testAddJobShouldNotAddJobTooFarInFuture() {
		Date futureDate = new Date();
		futureDate.setDate(futureDate.getDate() + 91);
		List<Date> jobDates = new ArrayList<>();
		jobDates.add(legalDate);
		jobDates.add(futureDate);
		boolean check = myJobSchedule
				.addJob(new Job("Park", jobDates, 1, 1, 1));
		assertEquals("Job should not have been added", 0,
				myJobSchedule.numberOfJobs());
		assertEquals("Method should return false", false, check);
	}

	@Test
	public void testAddJobShouldNotAddJobIfWeekTooFull() {
		boolean check;
		for (int i = 1; i <= 5; i++) {
			List<Date> jobDates = new ArrayList<>();
			jobDates.add((Date) legalDate.clone());
			Job job = new Job("Park", jobDates, 1, 1, 1);
			check = myJobSchedule.addJob(job);
			assertEquals("Jobs should be successful so far", i,
					myJobSchedule.numberOfJobs());
			assertEquals("Method should be returning true", true, check);
		}
		List<Date> jobDates = new ArrayList<>();
		jobDates.add(legalDate);
		Job job = new Job("Park", jobDates, 1, 1, 1);
		check = myJobSchedule.addJob(job);
		assertEquals("Job should not have added", 5,
				myJobSchedule.numberOfJobs());
		assertEquals("Method should be returning false", false, check);
	}

	// A volunteer may not sign up for a work catagory on a job if the max
	// number of volunteers for that
	// work catagoey has aleady been reached
	@Test
	public void testNoVolunteerSignUpForSameDate() {

		List<Date> jobDates = new ArrayList<>();
		jobDates.add((Date) legalDate.clone());
		Job job = new Job("Park", jobDates, 1, 1, 1);
		Job job2 = new Job("Park", jobDates, 1, 1, 1);
		// Two jobs with the same date
		myJobSchedule.addJob(job);
		myJobSchedule.addJob(job2);

		Volunteer v1 = new Volunteer("you@gmail.com", "Wise");
		job.signUp(v1, WorkCategory.HEAVY);

		assertEquals("Volunteer can't sign up for two jobs same day", 0,
				myJobSchedule.getJobsForSignUp(v1).size());

	}

	@Test
	public void testNoVolunteerSignUpWhenMaxReached() {

		List<Date> jobDates = new ArrayList<>();
		jobDates.add((Date) legalDate.clone());
		Job job = new Job("Park", jobDates, 1, 1, 1);
		myJobSchedule.addJob(job);

		// Add volunteers for for the job in each categories
		Volunteer v1 = new Volunteer("you@gmail.com", "Wise");
		job.signUp(v1, WorkCategory.HEAVY);

		Volunteer v2 = new Volunteer("you@gmail.com", "Wise");
		job.signUp(v2, WorkCategory.MEDIUM);

		Volunteer v3 = new Volunteer("everyone@gmail.com", "Gandolf");
		job.signUp(v3, WorkCategory.LIGHT);

		assertEquals(
				"Volunteer can't sign up Since maximum for the job reached And no twice registration",
				0, myJobSchedule.getJobsForSignUp(v1).size());

	}

	public static class DateUtil {
		public static Date addDays(Date date, int days) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, days); // minus number would decrement the
											// days
			return cal.getTime();
		}
	}

	@Test
	public void testUpComingJobsByVolunteer() throws ParseException {

		List<Date> jobDates = new ArrayList<>();
		jobDates.add((Date) legalDate.clone());

		List<Date> jobDates2 = new ArrayList<>();
		jobDates2.add((Date) legalDate.clone());

		Job job = new Job("Park", jobDates, 1, 1, 1);
		myJobSchedule.addJob(job);

		Job job2 = new Job("Park", jobDates, 1, 1, 1);
		myJobSchedule.addJob(job2);

		Volunteer v1 = new Volunteer("you@gmail.com", "Wise");
		job.signUp(v1, WorkCategory.HEAVY);

		job2.signUp(v1, WorkCategory.HEAVY);

		assertEquals("Volunteer didn't register for future jobs", 0,
				myJobSchedule.getJobsForSignUp(v1).size());

	}

	@Test
	public void testJobsByParkName() {
		List<Date> jobDates = new ArrayList<>();
		jobDates.add((Date) legalDate.clone());

		Job job = new Job("Park", jobDates, 1, 1, 1);
		myJobSchedule.addJob(job);

		Job job2 = new Job("Park2", jobDates, 1, 1, 1);
		myJobSchedule.addJob(job2);

		assertEquals("Volunteer didn't register for future jobs", 1,
				myJobSchedule.getJobsByPark("Park").size());
		assertTrue("Volunteer didn't register for future jobs", myJobSchedule
				.getJobsByPark("Park2").get(0).getParkName().equals("Park2"));

	}

}

