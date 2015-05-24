package model_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import park_model.Job;
import park_model.RulesHelp;
import park_model.VolunteerAbilities;
import park_model.WorkCategory;

public class VolunteerAbilitiesTest {

	private Job myFirstJob;
	private Job mySecondJob;
	private VolunteerAbilities myVolunteerAbilities;
	private Collection<Job> expectedCollection;
	@Before
	public void setUp() throws Exception {
		myFirstJob = new Job("King", RulesHelp.getTodaysDate(), 
				1, 2, 3, 4, "Description");
		mySecondJob = new Job("Rosa", RulesHelp.getTodaysDate(), 
				1, 2, 3, 4, "Description");
		myVolunteerAbilities = new VolunteerAbilities("config_files/empty.txt");
		expectedCollection   = new ArrayList<>();
	}
	
	@Test
	public void testGetAJobInFuture() {
		assertEquals("There are no Job at first", 0, myVolunteerAbilities.getAllFutureJobs());
		myVolunteerAbilities.getAllFutureJobs();
		assertEquals("Added to emptylist", 1, myVolunteerAbilities.getAllFutureJobs());
		myVolunteerAbilities.getAllFutureJobs();
		assertEquals("Added a future job to list", 2, myVolunteerAbilities.getAllFutureJobs());
		
	}
	
	//Testing 2 job at same start and end day
	@Test
	public void testTwoJobSignUpOnSameDay() {
		assertFalse("Two job signup for same start day", myFirstJob.getFirstDate().equals(mySecondJob.getFirstDate()));
		assertFalse("Two job signup for same end day", myFirstJob.getLastDate().equals(mySecondJob.getLastDate()));
	}
	
	//Testing job sign up for future job is full
	@Test
	public void testJobSignUpForFutureIsFull(){
		assertFalse("Job you sign up for light level that is full", myFirstJob.isOpen(WorkCategory.LIGHT));
		assertFalse("Job you sign up for medium level that is full", myFirstJob.isOpen(WorkCategory.MEDIUM));
		assertFalse("Job you sign up for heavy level that is full", myFirstJob.isOpen(WorkCategory.HEAVY));	
	}
	
	@Test
	public void testSaveListOfJob(){
		assertEquals("Save list of Job is null", 0, expectedCollection.isEmpty());
		assertEquals("Save list of Job ", 1, myVolunteerAbilities.getAllFutureJobs());

	}
}
