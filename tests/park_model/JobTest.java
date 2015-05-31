package park_model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import park_model.Job;
import park_model.RulesHelp;
import park_model.User;
import park_model.WorkCategory;

public class JobTest {
	
	//assertEquals("Message", expected, actual)
	//assertTrue(methodCall)
	//assertFalse(methodCall)
	
	private User volun1, volun2;
	private Job job1, job2, job3, job4;
	private GregorianCalendar firstDate, lastDate;
	
	//Set up the data needed
	@Before
	public void setUp() throws Exception {
		volun1 = new User("Betty", "White", "white@gmail.com");
		volun2 = new User("Lucille", "Ball", "ball@gmail.com");
		job1 = new Job("King", RulesHelp.getTodaysDate(), 1, 2, 3, 1, "Planting roses and eating ice cream.");
		job2 = new Job("Rosa", RulesHelp.getTodaysDate(), 2, 2, 2, 2, "Building a trail and clearing brush.");
		job3 = new Job("King", RulesHelp.getTodaysDate(), 1, 1, 1, 1, "Planting trees");
		job4 = new Job("Rosa", RulesHelp.getTodaysDate(), 2, 0, 1, 4, "Clearing Debris"); 
		
		firstDate = new GregorianCalendar(2015, 2, 8);
		lastDate = new GregorianCalendar(2015, 2, 17);
	}

	//SignUp Tests
	
	@Test
	public void testSignUpWhenListIsEmpty(){
		job1.signUp(volun1,  WorkCategory.LIGHT);
		assertTrue("The volunteer should be in the list.", job1.getVolunteers().contains(volun1));
	}
	
	@Test
	public void testSignUpWhenListNotEmpty(){
		job1.signUp(volun1, WorkCategory.LIGHT);
		assertEquals("There should be a volunteer in the list", 1, job1.getVolunteers().size());
		job1.signUp(volun2, WorkCategory.MEDIUM);
		assertTrue("The volunteer should be in the list.", job1.getVolunteers().contains(volun2));
	}
	
	
	//Test isSignedUp Tests
	@Test
	public void testIsSignedUpWhenSignedUp(){
		job1.signUp(volun1, WorkCategory.LIGHT);
		assertTrue("The volunteer is already signed up", job1.isSignedUp(volun1));
	}
	
	@Test
	public void testIsSignedUpWhenNotSignedUpListEmpty(){
		assertFalse("The volunteer is not already signed up.", job2.isSignedUp(volun2));
	}
	
	@Test
	public void testIsSignedUpWhenNotSignedUpListNotEmpty(){
		job2.signUp(volun1, WorkCategory.MEDIUM);
		assertFalse("The volunteer is not in the list.", job2.isSignedUp(volun2));
	}
	
	//Test the getNumOpen Tests
	@Test
	public void testgetNumOpenLIGHTOne() {
		assertEquals(job3.getNumOpen(WorkCategory.LIGHT), 1);
	}
	
	@Test
	public void testgetNumOpenMEDIUMOne() {
		assertEquals(job3.getNumOpen(WorkCategory.MEDIUM), 1);
	}


	@Test
	public void testgetNumOpenHEAVYOne() {
		assertEquals(job3.getNumOpen(WorkCategory.HEAVY), 1);
	}
	
	@Test
	public void testgetNumOpenHeavyAfterBeingDecremented() {
		assertEquals(job3.getNumOpen(WorkCategory.HEAVY), 1);
		job3.signUp(volun1,WorkCategory.HEAVY);
		assertEquals(job3.getNumOpen(WorkCategory.HEAVY), 0);
	}
	
	//isOpen Tests

	public void testIsOpenWhenNoLightJobsAvailable(){
		assertFalse("Job is at its volunteer limit.",job4.isOpen(WorkCategory.LIGHT));
	}

	public void testIsOpenWhenMediumJobsAreAvailable(){
		assertTrue("Job has medium work positions available.", job4.isOpen(WorkCategory.MEDIUM));
	}
	
	//isJobInRange Tests
	@Test
	public void testIsJobInRangeShouldReturnFalseIfJobJustBeforeRange() {
		GregorianCalendar twoDaysBeforeRange = new GregorianCalendar(2015, 2, 6);
		Job jobBeforeRange = new Job("King Park", 
				twoDaysBeforeRange, 2, 1, 1, 1, "Description");
		assertFalse("Job not in range", 
				jobBeforeRange.isJobInRange(firstDate, lastDate));
	}
	
	@Test
	public void testIsJobInRangeShouldReturnTrueIfJobOverlapsStartOfRange() {
		GregorianCalendar dayBeforeRange = new GregorianCalendar(2015, 2, 7);
		Job jobOverlappingStartOfRange = new Job("King Park", 
				dayBeforeRange, 2, 1, 1, 1, "Description");
		assertTrue("Job overlaps start of range range", 
				jobOverlappingStartOfRange.isJobInRange(firstDate, lastDate));
	}
	
	@Test
	public void testIsJobInRangeShouldReturnTrueIfJobWithinRange() {
		GregorianCalendar dayInRange = new GregorianCalendar(2015, 2, 10);
		Job jobWithinRange = new Job("King Park", 
				dayInRange, 2, 1, 1, 1, "Description");
		assertTrue("Job overlaps start of range range", 
				jobWithinRange.isJobInRange(firstDate, lastDate));
	}
	
	@Test
	public void testIsJobInRangeShouldReturnTrueIfJobOverlapsEndOfRange() {
		GregorianCalendar dayAtEndOfRange = new GregorianCalendar(2015, 2, 17);
		Job jobOverlappingEndOfRange = new Job("King Park", 
				dayAtEndOfRange, 2, 1, 1, 1, "Description");
		assertTrue("Job overlaps start of range range", 
				jobOverlappingEndOfRange.isJobInRange(firstDate, lastDate));
	}
	
	@Test
	public void testIsJobInRangeShouldReturnFalseIfJobJustAfterRange() {
		GregorianCalendar dayAfterRange = new GregorianCalendar(2015, 2, 18);
		Job jobAfterRange = new Job("King Park", 
				dayAfterRange, 2, 1, 1, 1, "Description");
		assertFalse("Job not in range", 
				jobAfterRange.isJobInRange(firstDate, lastDate));
	}
}
