package park_model;

import static org.junit.Assert.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import park_model.Job;
import park_model.PMAbilities;
import park_model.RulesHelp;
import config_files.Config;

public class PMAbilitiesTest {
	
	PMAbilities abilities;
	Job testJob;
	
	@Before
	public void setUp() throws Exception {
		abilities = new PMAbilities(Config.EMPTY_JOB_SCHEDULE);
		testJob = new Job("Rosa Park", RulesHelp.getTodaysDate(), 1,
				2, 3, 4, "Description");
	}
	
	@Test
	public void testConstructorShouldConstructEmptyJobListFromEmptyFile() {
		assertEquals("Starts with no jobs", 0, abilities.getNumJobs());
	}

	@Test
	public void testAddJobShouldAddAJob() {
		assertEquals("Starts with no jobs", 0, abilities.getNumJobs());
		Job testJob = new Job("Park Name", RulesHelp.getTodaysDate(), 1,
				2, 3, 4, "Description");
		abilities.addJob(testJob);
		assertEquals("Added to empty list", 1, abilities.getNumJobs());
		abilities.addJob(testJob);
		assertEquals("Added to list with existing job", 2, abilities.getNumJobs());
	}
	
	// getVolunteersForJob(Job theJob) does not need to be tested because it is
	// simply calling a method from Job
	
	@Test
	public void testGetJobsAtParkShouldReturnAllJobsAtAPark() {
		assertEquals("No jobs at Rosa Park", 0, 
				abilities.getJobsAtPark("Rosa Park").size());
		abilities.addJob(testJob);
		assertEquals("One job at Rosa Park", 1, 
				abilities.getJobsAtPark("Rosa Park").size());
		abilities.addJob(testJob);
		assertEquals("Two jobs at Rosa Park", 2, 
				abilities.getJobsAtPark("Rosa Park").size());
		abilities.addJob(new Job("King Park", RulesHelp.getTodaysDate(), 1,
				2, 3, 4, "Description"));
		assertEquals("King Park job does not affect Rosa Park job list",
				2, abilities.getJobsAtPark("Rosa Park").size());
	}
	
	// saveJobs(String fileName) does not need to be tested because
	// it simply calls a method from another class
	
	@Test
	public void testTooManyTotalJobsShouldReturnFalseUntilJobsGreaterThanMax() {
		assertFalse("0 jobs is not too many", abilities.tooManyTotalJobs());
		for (int i = 1; i < Config.MAX_TOTAL_JOBS; i++) {
			abilities.addJob(new Job(testJob));
		}
		assertFalse("Not too many jobs if one less than max", 
				abilities.tooManyTotalJobs());
		abilities.addJob(new Job(testJob));
		assertEquals("We should now have maximum possible jobs",
				abilities.getNumJobs(), Config.MAX_TOTAL_JOBS);
		assertTrue("Too many jobs if max jobs", 
				abilities.tooManyTotalJobs());
		abilities.addJob(testJob);
		assertTrue("Too many jobs if more than max jobs", 
				abilities.tooManyTotalJobs());
	}
	
	@Test
	public void testIsWeekFullShouldTrackJobsInMiddleOfRange() {
		Job newJob = new Job("Park", RulesHelp.getTodaysDate(), 2, 1, 1, 1, "");
		Job comparisionJob = new Job(newJob);
		assertFalse("Week is not full with 0 jobs",
				abilities.tooManyJobsNearJobTime(newJob));
		for (int i = 1; i < Config.MAX_DENSE_JOBS; i++) {
			abilities.addJob(comparisionJob);
		}
		assertFalse("Week is one less than full", 
				abilities.tooManyJobsNearJobTime(newJob));
		abilities.addJob(comparisionJob);
		assertTrue("Week is full", 
				abilities.tooManyJobsNearJobTime(newJob));
	}
	
	@Test
	public void testIsWeekFullShouldTrackJobsThatOverlapStartOfRange() {
		// Jobs are two days long so that they overlap the boundary of the range
		Job newJob = new Job("Park", RulesHelp.getTodaysDate(), 2, 1, 1, 1, "");
		GregorianCalendar comparisonDate = RulesHelp.getTodaysDate();
		comparisonDate.add(Calendar.DATE, -(Config.IMMEDIATE_TIME_FRAME_DAYS + 1));
		Job comparisionJob = new Job("Park", comparisonDate, 2, 1, 1, 1, "");
		assertFalse("Week is not full with 0 jobs",
				abilities.tooManyJobsNearJobTime(newJob));
		for (int i = 1; i < Config.MAX_DENSE_JOBS; i++) {
			abilities.addJob(comparisionJob);
		}
		assertFalse("Week is one less than full",
				abilities.tooManyJobsNearJobTime(newJob));
		abilities.addJob(comparisionJob);
		assertTrue("Week is full",
				abilities.tooManyJobsNearJobTime(newJob));
	}
	
	@Test
	// Jobs are two days long so that they overlap the boundary of the range
	public void testIsWeekFullShouldTrackJobsThatOverlapEndOfRange() {
		Job newJob = new Job("Park", RulesHelp.getTodaysDate(), 2, 1, 1, 1, "");
		GregorianCalendar comparisonDate = RulesHelp.getTodaysDate();
		comparisonDate.add(Calendar.DATE, Config.IMMEDIATE_TIME_FRAME_DAYS + 1);
		Job comparisionJob = new Job("Park", comparisonDate, 2, 1, 1, 1, "");
		assertFalse("Week is not full with 0 jobs",
				abilities.tooManyJobsNearJobTime(newJob));
		for (int i = 1; i < Config.MAX_DENSE_JOBS; i++) {
			abilities.addJob(comparisionJob);
		}
		assertFalse("Week is one less than full",
				abilities.tooManyJobsNearJobTime(newJob));
		abilities.addJob(comparisionJob);
		assertTrue("Week is full",
				abilities.tooManyJobsNearJobTime(newJob));
	}
	
	@Test
	public void testIsWeekFullShouldNotTrackJobsBeforeRange() {
		Job newJob = new Job("Park", RulesHelp.getTodaysDate(), 2, 1, 1, 1, "");
		GregorianCalendar comparisonDate = RulesHelp.getTodaysDate();
		comparisonDate.add(Calendar.DATE, -(Config.IMMEDIATE_TIME_FRAME_DAYS + 2));
		Job comparisionJob = new Job("Park", comparisonDate, 2, 1, 1, 1, "");
		assertFalse("Week is not full with 0 jobs",
				abilities.tooManyJobsNearJobTime(newJob));
		for (int i = 1; i < Config.MAX_DENSE_JOBS; i++) {
			abilities.addJob(comparisionJob);
		}
		assertFalse("Week is still not full",
				abilities.tooManyJobsNearJobTime(newJob));
		abilities.addJob(comparisionJob);
		assertFalse("Week is still not full",
				abilities.tooManyJobsNearJobTime(newJob));
	}
	
	@Test
	public void testIsWeekFullShouldNotTrackJobsAfterRange() {
		Job newJob = new Job("Park", RulesHelp.getTodaysDate(), 2, 1, 1, 1, "");
		GregorianCalendar comparisonDate = RulesHelp.getTodaysDate();
		comparisonDate.add(Calendar.DATE, Config.IMMEDIATE_TIME_FRAME_DAYS + 2);
		Job comparisionJob = new Job("Park", comparisonDate, 2, 1, 1, 1, "");
		assertFalse("Week is not full with 0 jobs",
				abilities.tooManyJobsNearJobTime(newJob));
		for (int i = 1; i < Config.MAX_DENSE_JOBS; i++) {
			abilities.addJob(comparisionJob);
		}
		assertFalse("Week is still not full",
				abilities.tooManyJobsNearJobTime(newJob));
		abilities.addJob(comparisionJob);
		assertFalse("Week is still not full",
				abilities.tooManyJobsNearJobTime(newJob));
	}

}
