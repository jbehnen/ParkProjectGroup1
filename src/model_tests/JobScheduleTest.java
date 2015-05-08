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
import park_model.Job;
import park_model.JobSchedule;
import park_model.User;
import park_model.WorkCategory;

@SuppressWarnings("deprecation")
public class JobScheduleTest {

	private JobSchedule myJobSchedule;
	private GregorianCalendar legalDate;
	private List<GregorianCalendar> myOneDateList;

	@Before
	public void setUp() throws Exception {
		myJobSchedule = new JobSchedule(true);
		myOneDateList = new ArrayList<GregorianCalendar>();
		legalDate = Config.getTodaysDate();
		legalDate.add(Calendar.DATE, 1);
		myOneDateList.add(legalDate);
	}

	
	
	
	

	// A volunteer may not sign up for a work catagory on a job if the max
	// number of volunteers for that
	// work catagoey has aleady been reached
	@Test
	public void testNoVolunteerSignUpForSameDate() {

		List<GregorianCalendar> jobDates = new ArrayList<>();
		jobDates.add((GregorianCalendar) legalDate.clone());
		Job job = new Job("Park", jobDates, 1, 1, 1);
		Job job2 = new Job("Park", jobDates, 1, 1, 1);
		// Two jobs with the same date
		myJobSchedule.addJob(job);
		myJobSchedule.addJob(job2);

		User v1 = new User("you@gmail.com", "", "Wise");
		job.signUp(v1, WorkCategory.HEAVY);

		assertEquals("Volunteer can't sign up for two jobs same day", 0,
				myJobSchedule.getJobsForSignUp(v1).size());

	}

	@Test
	public void testNoVolunteerSignUpWhenMaxReached() {

		List<GregorianCalendar> jobDates = new ArrayList<>();
		jobDates.add((GregorianCalendar) legalDate.clone());
		Job job = new Job("Park", jobDates, 1, 1, 1);
		myJobSchedule.addJob(job);

		// Add volunteers for for the job in each categories
		User v1 = new User("you@gmail.com", "", "Wise");
		job.signUp(v1, WorkCategory.HEAVY);

		User v2 = new User("you@gmail.com", "", "Wise");
		job.signUp(v2, WorkCategory.MEDIUM);

		User v3 = new User("everyone@gmail.com", "", "Gandolf");
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

		List<GregorianCalendar> jobDates = new ArrayList<>();
		jobDates.add((GregorianCalendar) legalDate.clone());

		List<GregorianCalendar> jobDates2 = new ArrayList<>();
		jobDates2.add((GregorianCalendar) legalDate.clone());

		Job job = new Job("Park", jobDates, 1, 1, 1);
		myJobSchedule.addJob(job);

		Job job2 = new Job("Park", jobDates, 1, 1, 1);
		myJobSchedule.addJob(job2);

		User v1 = new User("you@gmail.com", "", "Wise");
		job.signUp(v1, WorkCategory.HEAVY);

		job2.signUp(v1, WorkCategory.HEAVY);

		assertEquals("Volunteer didn't register for future jobs", 0,
				myJobSchedule.getJobsForSignUp(v1).size());

	}

	@Test
	public void testJobsByParkName() {
		List<GregorianCalendar> jobDates = new ArrayList<>();
		jobDates.add((GregorianCalendar) legalDate.clone());

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

