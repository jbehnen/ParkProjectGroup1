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
	
	private Job firstJob;
	private Job secondJob;
	private Job copySecondJob;
	private List<Date> jobDates;
	private Date begin1;
	private Date begin2;
	private Date end1;
	private Date end2;
	private List<Volunteer> jobVol;
	
	//Sets these things up before beginning tests
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
		
		//Set up a list of volunteers
		Volunteer v1 = new Volunteer("me@gmail.com", "Baggins");
		Volunteer v2 = new Volunteer("you@gmail.com", "Wise");
	}

	@Test
	public void testJobConstructor() {
		//Check Park
		assertEquals("Parks should be the same", "Lincoln", firstJob.getParkName());
		//Check instantiation of empty volunteer list
		assertEquals("Volunteer sign up list should be empty", firstJob.getVolunteers().size(), 0);
	}
	
	@Test
	public void testJobCopyConstructor(){
		copySecondJob = new Job(secondJob);
	}
	
	@Test
	public void testGetAvailableWorkCategory(){
		//Create list of available work categories
		List<WorkCategory> myCategories = new ArrayList();
		myCategories.add(WorkCategory.MEDIUM);
		myCategories.add(WorkCategory.LIGHT);
		assertEquals("A list of available work categories should be returned.", firstJob.getAvailableWorkCategories(), myCategories);
	}
	
	

	@Test
	public void testSignUp(){
		Volunteer v1 = new Volunteer("me@gmail.com", "Baggins");
		assertFalse(firstJob.isSignedUp(v1));
		assertTrue(firstJob.signUp(v1, WorkCategory.LIGHT));
		assertTrue(firstJob.isSignedUp(v1));
	}
	
	
	
	
	/*
	@Test
	public void testCategoryDecrement(){
		//Check the number of jobs being decremented. 
		
		
	}
	
	
	
	
	
	@Test
	public void testSignUp(){
		
		
		
		
		
		
	}*/
	
}
