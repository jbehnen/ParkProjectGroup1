package park_model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
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
	private Job job1, job1Copy, job2, job3, job4, jobNoCapacity, jobOneLightSlot;
	private GregorianCalendar firstDate, lastDate, today;
	
	//Set up the data needed
	@Before
	public void setUp() throws Exception {
		volun1 = new User("Betty", "White", "white@gmail.com");
		volun2 = new User("Lucille", "Ball", "ball@gmail.com");
		today = RulesHelp.getTodaysDate();
		job1 = new Job("King", RulesHelp.getTodaysDate(), 1, 2, 3, 1, "Planting roses and eating ice cream.");
		job1Copy = new Job("King", RulesHelp.getTodaysDate(), 1, 2, 3, 1, "Planting roses and eating ice cream.");
		job2 = new Job("Rosa", RulesHelp.getTodaysDate(), 2, 2, 2, 2, "Building a trail and clearing brush.");
		job3 = new Job("King", RulesHelp.getTodaysDate(), 1, 1, 1, 1, "Planting trees");
		job4 = new Job("Rosa", RulesHelp.getTodaysDate(), 2, 0, 1, 4, "Clearing Debris"); 
		jobNoCapacity = new Job("King", RulesHelp.getTodaysDate(), 1, 0, 0, 0, "The most exclusive job");
		jobOneLightSlot = new Job("King", RulesHelp.getTodaysDate(), 1, 1, 0, 0, "The most exclusive job");
		
		firstDate = new GregorianCalendar(2015, 2, 8);
		lastDate = new GregorianCalendar(2015, 2, 17);
	}
	
	//Constructor Tests
	
	@Test
	public void testGeneralConstructorShouldConstructNewJob() {
		assertEquals("Correct park name", "King", job1.getParkName());
		assertEquals("First date should be today", today, job1.getFirstDate());
		assertEquals("One day long", 1, job1.getNumDays());
		assertEquals("2 open light jobs", 2, job1.getNumOpen(WorkCategory.LIGHT));
		assertEquals("3 open medium jobs", 3, job1.getNumOpen(WorkCategory.MEDIUM));
		assertEquals("1 open heavy jobs", 1, job1.getNumOpen(WorkCategory.HEAVY));
		assertEquals("Starts with no volunteers", new ArrayList<User>(), job1.getVolunteers());
	}
	
	@Test
	public void testCopyConstructorShouldConstructCopyOfJob() {
		Job copy = new Job(job1);
		assertEquals("Jobs have same values", copy, job1);
		assertNotSame("New job is a copy", copy, job1);
	}

	//SignUp Tests
	
	@Test
	public void testSignUpShouldAddVolunteerAndDecrementOpenJobsWhenListIsEmpty(){
		assertEquals("Initially two open light jobs", 2, job1.getNumOpen(WorkCategory.LIGHT));
		job1.signUp(volun1,  WorkCategory.LIGHT);
		assertTrue("The volunteer should be in the list.", job1.getVolunteers().contains(volun1));
		assertEquals("Number of open light jobs decremented", 1, job1.getNumOpen(WorkCategory.LIGHT));
	}
	
	@Test
	public void testSignUpShouldAddVolunteerAndDecrementOpenJobsWhenListNotEmpty(){
		job1.signUp(volun1, WorkCategory.LIGHT);
		assertEquals("There should be a volunteer in the list", 1, job1.getVolunteers().size());
		job1.signUp(volun2, WorkCategory.MEDIUM);
		assertTrue("The volunteer should be in the list.", job1.getVolunteers().contains(volun2));
		assertEquals("Number of open light jobs decremented", 2, job1.getNumOpen(WorkCategory.MEDIUM));
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
	
	//getLastDate Tests
	public void testGetLastDateShouldReturnFirstDateIfOneDayLong() {
		assertEquals(today, job1.getLastDate());
	}
	
	//getLastDate Tests
	public void testGetLastDateShouldReturnDayAfterFirstDateIfTwoDaysLong() {
		today.add(Calendar.DATE, 1);
		assertEquals(today, job2.getLastDate());
	}
	
	//isOpen Tests
	@Test
	public void testIsOpenShouldReturnFalseWhenNoLightJobsInitiallyAvailable(){
		assertFalse("Job is at its volunteer limit.",job4.isOpen(WorkCategory.LIGHT));
	}
	
	@Test
	public void testIsOpenShouldReturnFalseWhenAllLightJobsTaken() {
		assertTrue("Job starts with available light jobs", job3.isOpen(WorkCategory.LIGHT));
		job3.signUp(new User("email", "first", "last"), WorkCategory.LIGHT);
		assertFalse("Category is now full", job3.isOpen(WorkCategory.LIGHT));
	}
	
	@Test
	public void testIsOpenShouldReturnFalseWhenSomeLightJobsTaken() {
		job3.signUp(new User("email", "first", "last"), WorkCategory.LIGHT);
		assertTrue("Category is still open when partially full", job1.isOpen(WorkCategory.LIGHT));
	}

	@Test
	public void testIsOpenShouldReturnTrueWhenMediumJobsAreAvailable(){
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
	
	// isFull tests
	@Test
	public void testIsFullShouldReturnTrueIfMaxCapacityZeroForAllCategories() {
		assertTrue("Job with no capacity is full", jobNoCapacity.isFull());
	}
	
	@Test
	public void testIsFullShouldReturnFalseIfOneCategoryHasNonZeroCapacityWithNoSignUps() {
		assertFalse("Job with one open category is not full", jobOneLightSlot.isFull());
	}
	
	@Test
	public void testIsFullShouldReturnFalseIfOneCategoryHasNonZeroCapacityAndIsFull() {
		jobOneLightSlot.signUp(new User("email", "first", "last"), WorkCategory.LIGHT);
		assertTrue("Job with one full category is full", jobOneLightSlot.isFull());
	}
	
	@Test
	public void testIsFullShouldReturnFalseIfAllCategoriesHaveNonZeroCapacityWithNoSignUps() {
		assertFalse("Job with non-zero capacity and no sign-ups not full", job3.isFull());
	}
	
	@Test
	public void testIsFullShouldReturnFalseIfAllCategoriesHaveNonZeroCapacityWithOneCategoryFull() {
		job3.signUp(new User("email", "first", "last"), WorkCategory.LIGHT);
		assertFalse("Partially filled categories -> job not full", job3.isFull());
	}
	
	@Test
	public void testIsFullShouldReturnTrueIfAllCategoriesHaveNonZeroCapacityWithAllCategoriesFull() {
		job3.signUp(new User("email", "first", "last"), WorkCategory.LIGHT);
		job3.signUp(new User("email2", "first", "last"), WorkCategory.MEDIUM);
		job3.signUp(new User("email3", "first", "last"), WorkCategory.HEAVY);
		assertTrue("Job with no capacity is full", job3.isFull());
	}
	
	@Test
	public void testToStringShouldReturnExpectedString() {
		assertEquals("Correct toString", 
				"King 5/31/2015-5/31/2015, Planting roses and eating ice cream."
				+ " Light: 0/2, Medium: 0/3, Heavy: 0/1", job1.toString());
	}
	
	// Equals test
	@Test
	public void testEqualsShouldReturnTrueForIdenticalObjects() {
		assertEquals("Equivalent objects are equal", job1, job1Copy);
	}
	
	@Test
	public void testEqualsShouldReturnFalseForNonIdenticalObjects() {
		assertNotEquals("Non-equivalent objects are not equal", job1, job2);
	}
	
	// Hash code test
	@Test
	public void testHashCodeShouldReturnSameForEqualObjects() {
		assertEquals("Equal objects -> equal hash code", 
				job1.hashCode(), job1Copy.hashCode());
	}
}
