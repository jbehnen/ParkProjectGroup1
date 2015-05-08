package model_tests;

import static org.junit.Assert.*;

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
import park_model.User;
import park_model.WorkCategory;

public class JobTest {
	//Private fields to use in testing
	
	// The Jobs for testing
	private Job firstJob;
	private Job secondJob;
	private Job copySecondJob;
	private DateAndTime myLegalDAT;
	
	//The Date List
	private List<DateAndTime> jobDates;
	
	//The Dates
	private DateAndTime begin1;
	private DateAndTime begin2;
	private DateAndTime end1;
	private DateAndTime end2;

	//Date is depricated and until another data type is chosen, the program will suppress the warning.
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		
		//Create 2 List of Dates
		jobDates = new ArrayList<>();
		
		begin1 = new DateAndTime(2015, 05, 04, "800", "1600");
		end1 = new DateAndTime(2015, 05, 05, "800", "1600");
		
		jobDates.add(begin1);
		jobDates.add(end1);
		
		//Create 2 jobs
		firstJob = new Job("Lincoln",jobDates, 2, 1, 0);
		secondJob = new Job("Tide", jobDates, 1, 4, 2);
		
		GregorianCalendar today = Config.getTodaysDate();
		today.add(Calendar.DATE, 1);
		myLegalDAT = new DateAndTime(today.get(Calendar.YEAR),
				today.get(Calendar.MONTH), today.get(Calendar.DATE),
				"", "");

	}

	@Test
	public void testJobConstructor() {
		//Check Park
		assertEquals("Parks should be the same", "Lincoln", firstJob.getParkName());
		//Check instantiation of empty volunteer list
		assertEquals("Volunteer sign up list should be empty", firstJob.getVolunteers().size(), 0);
	}
	
	@Test
	public void testGetAvailableWorkCategory(){
		//Create list of available work categories
		List<WorkCategory> myCategories = new ArrayList();
		//Adding the work categorys to List
		myCategories.add(WorkCategory.MEDIUM);
		myCategories.add(WorkCategory.LIGHT);
		//Test 
		assertEquals("A list of available work categories should be returned.", firstJob.getAvailableWorkCategories(), myCategories);
	}
	
	@Test
	public void testSignUp(){
		User v1 = new User("you@gmail.com", "Bob", "Wise");
		//Tests if the volunteer is already signed up for the job
		assertFalse(firstJob.isSignedUp(v1));
		//Tests to see if the volunteer can sign up for the job under a category that has 0 available jobs
		assertFalse(firstJob.signUp(v1, WorkCategory.HEAVY));
		//Tests if the volunteer signed up
		assertTrue(firstJob.signUp(v1, WorkCategory.LIGHT));
		//Tests to see if the volunteer is listed for that job
		assertTrue(firstJob.isSignedUp(v1));
		//Tests to see if volunteer can sign up again
		assertFalse(firstJob.signUp(v1, WorkCategory.LIGHT));
		//Tests to see if volunteer can sign up again with a different work category
		assertFalse(firstJob.signUp(v1, WorkCategory.MEDIUM));
	}
	
	@Test
	public void testCategoryDecrement(){
		
		User v2 = new User("you@gmail.com", "Bob", "Wise");
		User v3 = new User("everyone@gmail.com", "Grey", "Gandolf");
		
		//Light has 1 availability
		//Light jobs available = 1
		assertTrue(secondJob.signUp(v2, WorkCategory.LIGHT));
		//Light jobs available = 0
		assertFalse(secondJob.signUp(v3, WorkCategory.LIGHT));
	}
	
	@Test
	public void testIsJobTooLongShouldReturnFalseIfJobIsMaxDaysLong() {
		List<DateAndTime> dateList = new ArrayList<>();
		for (int i = 0; i < Config.MAX_JOB_DAYS; i++) {
			dateList.add(myLegalDAT);
		}
		Job job = new Job("Park", dateList, 1, 1, 1);
		boolean check = job.isJobTooLong();
		assertEquals("Job is maximum acceptable length", false, check);
	}

	@Test
	public void testIsJobTooLongShouldReturnTrueIfJobMoreThanMaxDaysLong() {
		List<DateAndTime> dateList = new ArrayList<>();
		for (int i = 0; i <= Config.MAX_JOB_DAYS; i++) {
			dateList.add(myLegalDAT);
		}
		Job job = new Job("Park", dateList, 1, 1, 1);
		boolean check = job.isJobTooLong();
		assertEquals("Job too long", true, check);
	}

	@Test
	public void testIsJobInPast() {
		List<DateAndTime> dateList = new ArrayList<>();
		DateAndTime rightNow = new DateAndTime(new GregorianCalendar(), "", "");
		dateList.add(rightNow);
		Job job = new Job("Rosa", dateList, 1, 1, 1);
		boolean check = job.isJobInPast();
		assertFalse("Job not in past", check);
		GregorianCalendar yesterdaysDate = new GregorianCalendar();
		yesterdaysDate.add(Calendar.DATE, -1);
		DateAndTime yesterday = new DateAndTime(yesterdaysDate, "", "");
		dateList.set(0, yesterday);
		job = new Job("Rosa", dateList, 1, 1, 1); 
		check = job.isJobInPast();
		assertTrue("Job in past", check);
	}

}
