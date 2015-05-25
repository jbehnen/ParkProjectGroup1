package park_model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import config_files.Config;

/**
 * Tests the JobSchedule class.
 * 
 * @author Julia Behnen
 * @version 5/24/2015
 */
public class JobScheduleTest {
	
	private List<Job> jobListWith5JobsInFuture;
	private Job jobOnTodayAndTomorrow;
	private Job jobInPast;
	private Job jobOnYesterdayAndToday;

	@Before
	public void setUp() throws Exception {
		GregorianCalendar today = RulesHelp.getTodaysDate();
		GregorianCalendar yesterday = (GregorianCalendar) today.clone();
		yesterday.add(Calendar.DATE, -1);
		GregorianCalendar twoDaysAgo = (GregorianCalendar) today.clone();
		twoDaysAgo.add(Calendar.DATE, -2);
		jobOnTodayAndTomorrow = new Job("Default job", today, 2, 1, 1, 1, "Description");
		jobOnYesterdayAndToday = new Job("Overlaps boundary", yesterday, 2, 1, 1, 1,
				"On the edge");
		jobInPast = new Job("Past job", twoDaysAgo, 2, 1, 1, 1, "The past is gone");
		jobListWith5JobsInFuture = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			jobListWith5JobsInFuture.add(new Job(jobOnTodayAndTomorrow));
		}
	}
	
	@Test
	public void testGetAllJobsFromEmptyFileShouldReturnEmptyList() {
		List<Job> jobs = JobSchedule.getAllJobs(Config.EMPTY_TEXT_FILE);
		assertEquals(0, jobs.size());
	}
	
	@Test
	public void testSaveAllJobsShouldWriteAllJobsAndGetAllJobsShouldRetrieveThem() {
		JobSchedule.saveJobList(jobListWith5JobsInFuture, Config.JOB_TEST_OUTPUT_FILE);
		List<Job> retrievedJobs = JobSchedule.getAllJobs(Config.JOB_TEST_OUTPUT_FILE);
		assertEquals("All jobs that were written should have been retrieved", 5,
				retrievedJobs.size());
		assertTrue("Job data should be preserved", 
				retrievedJobs.contains(jobOnTodayAndTomorrow));
	}
	
	@Test
	public void testGetAllFutureJobsShouldReturnAllJobsFromFileIfAllInFuture() {
		JobSchedule.saveJobList(jobListWith5JobsInFuture, Config.JOB_TEST_OUTPUT_FILE);
		List<Job> retrievedJobs = JobSchedule.getAllJobs(Config.JOB_TEST_OUTPUT_FILE);
		assertEquals("getAllJobs retrieved all jobs that were written", 5,
				retrievedJobs.size());
		List<Job> retrievedFutureJobs = 
				JobSchedule.getAllFutureJobs(Config.JOB_TEST_OUTPUT_FILE);
		assertEquals("All jobs were in the future, so all were retrieved", 5,
				retrievedFutureJobs.size());
	}
	
	@Test
	public void testGetAllFutureJobsShouldReturnNoJobsFromFileIfNoneInFuture() {
		List<Job> allInPast = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			allInPast.add(jobInPast);
		}
		JobSchedule.saveJobList(allInPast, Config.JOB_TEST_OUTPUT_FILE);
		List<Job> retrievedJobs = JobSchedule.getAllJobs(Config.JOB_TEST_OUTPUT_FILE);
		assertEquals("getAllJobs retrieved all jobs that were written", 5,
				retrievedJobs.size());
		List<Job> retrievedFutureJobs = 
				JobSchedule.getAllFutureJobs(Config.JOB_TEST_OUTPUT_FILE);
		assertEquals("No jobs were in the future, so none were retrieved", 0,
				retrievedFutureJobs.size());
	}
	
	@Test
	public void testGetAllFutureJobsShouldReturnAllJobsMinus1FromFileIf1InPast() {
		List<Job> jobListWithOneJobInPast = new ArrayList<>(jobListWith5JobsInFuture);
		jobListWithOneJobInPast.add(jobInPast);
		JobSchedule.saveJobList(jobListWithOneJobInPast, Config.JOB_TEST_OUTPUT_FILE);
		List<Job> retrievedJobs = JobSchedule.getAllJobs(Config.JOB_TEST_OUTPUT_FILE);
		assertEquals("getAllJobs retrieved all jobs that were written", 6,
				retrievedJobs.size());
		List<Job> retrievedFutureJobs = 
				JobSchedule.getAllFutureJobs(Config.JOB_TEST_OUTPUT_FILE);
		assertEquals("All jobs but one were in the future, so all but one were retrieved",
				5, retrievedFutureJobs.size());
	}
	
	@Test
	public void testGetAllFutureJobsShouldReturnNoJobsFromFileIfAllStartInThePastButEndInPresent() {
		List<Job> allInPast = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			allInPast.add(jobOnYesterdayAndToday);
		}
		JobSchedule.saveJobList(allInPast, Config.JOB_TEST_OUTPUT_FILE);
		List<Job> retrievedJobs = JobSchedule.getAllJobs(Config.JOB_TEST_OUTPUT_FILE);
		assertEquals("getAllJobs retrieved all jobs that were written", 5,
				retrievedJobs.size());
		List<Job> retrievedFutureJobs = 
				JobSchedule.getAllFutureJobs(Config.JOB_TEST_OUTPUT_FILE);
		assertEquals("No jobs were in the future, so none were retrieved", 0,
				retrievedFutureJobs.size());
	}

}

