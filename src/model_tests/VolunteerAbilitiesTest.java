package model_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import park_model.Job;
import park_model.RulesHelp;
import park_model.User;
import park_model.VolunteerAbilities;
import park_model.WorkCategory;

public class VolunteerAbilitiesTest {

	/**
	 * Contains no jobs
	 */
	VolunteerAbilities abilitiesWithNoJobs;
	
	/**
	 * Contains one job: testJobUser1, in which user1 is signed up.
	 */
	VolunteerAbilities abilitiesTestJobUser1;
	
	/**
	 * A job set on today's date with a single user - user1 - signed up.
	 */
	Job testJobUser1;
	
	/**
	 * A job that overlaps with testJobUser1.
	 */
	Job overlappingJob;
	
	/**
	 * A job that does not overlap with testJobUser1.
	 */
	Job nonOverlappingJob;
	
	User user1;
	User user2;
	
	@Before
	public void setUp() throws Exception {
		abilitiesWithNoJobs = new VolunteerAbilities("config_files/empty.txt");
		abilitiesTestJobUser1 = new VolunteerAbilities("config_files/empty.txt");
		testJobUser1 = new Job("Rosa Park", RulesHelp.getTodaysDate(), 2,
				2, 3, 4, "Description");
		user1 = new User("user1@aol.com", "user1", "test");
		user2 = new User("user2@aol.com", "user2", "test");
		testJobUser1.signUp(user1, WorkCategory.LIGHT);
		abilitiesTestJobUser1.addJob(testJobUser1);
		
		GregorianCalendar tomorrow = RulesHelp.getTodaysDate();
		tomorrow.add(Calendar.DATE, 1);
		overlappingJob = new Job("King Park", tomorrow, 
				2, 2, 3, 4, "Overlapping Job");
		
		GregorianCalendar dayAfterTomorrow = RulesHelp.getTodaysDate();
		dayAfterTomorrow.add(Calendar.DATE, 2);
		nonOverlappingJob = new Job("King Park", dayAfterTomorrow, 
				2, 2, 3, 4, "Overlapping Job");
	}
	
	@Test
	public void testConstructorShouldConstructEmptyJobListFromEmptyFile() {
		assertEquals("Starts with no jobs", 0, abilitiesWithNoJobs.getAllFutureJobs().size());
	}
	
	@Test (expected=UnsupportedOperationException.class)
	public void testGetAllFutureJobsShouldReturnUnmodifiableList() {
		List<Job> jobs = abilitiesTestJobUser1.getAllFutureJobs();
		jobs.remove(0);
	}
	
	@Test
	public void testGetAllFutureJobsShouldLetOriginalJobsBeModifiedToAddVolunteers() {
		List<Job> jobs = abilitiesTestJobUser1.getAllFutureJobs();
		User newVol = new User("email", "first", "last");
		Job jobBeingAltered = jobs.get(0);
		assertFalse("newVol not initially signed up", 
				jobBeingAltered.isSignedUp(newVol));
		jobBeingAltered.signUp(newVol, WorkCategory.LIGHT);
		jobs = abilitiesTestJobUser1.getAllFutureJobs();
		assertTrue("newVol now signed up in original class list", 
				jobs.get(0).isSignedUp(newVol));
	}

	@Test
	public void testIsSignedUpForConflictingJobShouldBeFalseIfNoOtherJobs() {
		assertFalse("No conflict because no other jobs", 
				abilitiesWithNoJobs.isSignedUpForConflictingJob(user2, testJobUser1));
	}
	
	@Test
	public void testIsSignedUpForConflictingJobShouldBeTrueIfOverlappingJobThatVolunteerSignedUpFor() {
		assertTrue("Conflict because user1 signed up for an overlapping job", 
				abilitiesTestJobUser1.isSignedUpForConflictingJob(user1, overlappingJob));
	}
	
	@Test
	public void testIsSignedUpForConflictingJobShouldBeFalseIfOverlappingJobThatVolunteerNotSignedUpFor() {
		assertFalse("No conflict because user2 not signed up for an overlapping job", 
				abilitiesTestJobUser1.isSignedUpForConflictingJob(user2, overlappingJob));
	}
	
	@Test
	public void testIsSignedUpForConflictingJobShouldBeFalseIfNonOverlappingJobThatVolunteerSignedUpFor() {
		assertFalse("No conflict because user1 signed up for a non-overlapping job", 
				abilitiesTestJobUser1.isSignedUpForConflictingJob(user1, nonOverlappingJob));
	}
	
	@Test
	public void testIsSignedUpForConflictingJobShouldBeFalseIfNonOverlappingJobThatVolunteerNotSignedUpFor() {
		assertFalse("No conflict because user2 not signed up for a non-overlapping job", 
				abilitiesTestJobUser1.isSignedUpForConflictingJob(user2, nonOverlappingJob));
	}
	
	@Test
	public void testAddJobShouldAddAJob() {
		assertEquals("Starts with no jobs", 0, 
				abilitiesWithNoJobs.getAllFutureJobs().size());
		Job testJob = new Job("Park Name", RulesHelp.getTodaysDate(), 1,
				2, 3, 4, "Description");
		abilitiesWithNoJobs.addJob(testJob);
		assertEquals("Added to empty list", 1, 
				abilitiesWithNoJobs.getAllFutureJobs().size());
		assertEquals("Added job is equal to testJob", testJob, 
				abilitiesWithNoJobs.getAllFutureJobs().get(0));
		assertNotSame("Added job not the same as testJob", testJob, 
				abilitiesWithNoJobs.getAllFutureJobs().get(0));
		abilitiesWithNoJobs.addJob(testJob);
		assertEquals("Added to list with existing job", 2, 
				abilitiesWithNoJobs.getAllFutureJobs().size());
	}

}
