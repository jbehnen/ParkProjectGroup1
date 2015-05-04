package model_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import park_model.Job;
import park_model.Volunteer;
import park_model.WorkCategory;

public class JobTest {
	//Private fields to use in testing
	
	// The Jobs for testing
	private Job firstJob;
	private Job secondJob;
	private Job copySecondJob;
	
	//The Date List
	private List<Date> jobDates;
	
	//The Dates
	private Date begin1;
	private Date begin2;
	private Date end1;
	private Date end2;

	//Date is depricated and until another data type is chosen, the program will suppress the warning.
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		
		//Create 2 List of Dates
		jobDates = new ArrayList();
		
		begin1 = new Date(2015, 05, 04);
		end1 = new Date(2015, 05, 05);
		
		jobDates.add(begin1);
		jobDates.add(end1);
		
		//Create 2 jobs
		firstJob = new Job("Lincoln",jobDates, 2, 1, 0);
		secondJob = new Job("Tide", jobDates, 1, 4, 2);

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
		Volunteer v1 = new Volunteer("you@gmail.com", "Wise");
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
		
		Volunteer v2 = new Volunteer("you@gmail.com", "Wise");
		Volunteer v3 = new Volunteer("everyone@gmail.com", "Gandolf");
		
		//Light has 1 availability
		//Light jobs available = 1
		assertTrue(secondJob.signUp(v2, WorkCategory.LIGHT));
		//Light jobs available = 0
		assertFalse(secondJob.signUp(v3, WorkCategory.LIGHT));
	}

}
