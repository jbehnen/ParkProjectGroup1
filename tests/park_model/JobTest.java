package park_model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	
	//Set up the data needed
	@Before
	public void setUp() throws Exception {
		volun1 = new User("Betty", "White", "white@gmail.com");
		volun2 = new User("Lucille", "Ball", "ball@gmail.com");
		job1 = new Job("King", RulesHelp.getTodaysDate(), 1, 2, 3, 1, "Planting roses and eating ice cream.");
		job2 = new Job("Rosa", RulesHelp.getTodaysDate(), 2, 2, 2, 2, "Building a trail and clearing brush.");
		job3 = new Job("King", RulesHelp.getTodaysDate(), 1, 1, 1, 1, "Planting trees");
		job4 = new Job("Rosa", RulesHelp.getTodaysDate(), 2, 0, 1, 4, "Clearin Debris"); 
	}

	////////////////Test signUp method////////////////
	//Case 1: Tests new signUp
	@Test
	public void testSignUpWhenListIsEmpty(){
		assertEquals("There should be no volunteers in the new list", 0, job1.getVolunteers().size());
		job1.signUp(volun1,  WorkCategory.LIGHT);
		assertEquals("There should be a volunteer in the list", 1, job1.getVolunteers().size());
	}
	
	//Case 2: Tests when already in list
	@Test
	public void testSignUpWhenListNotEmpty(){
		job1.signUp(volun1, WorkCategory.LIGHT);
		assertEquals("There should be a volunteer in the list", 1, job1.getVolunteers().size());
		job1.signUp(volun2, WorkCategory.MEDIUM);
		assertEquals("There should be two volunteers in the list", 2, job1.getVolunteers().size());
	}
	
	//////////////Test isSignedUp method/////////
	//Tests isSignedUp when volunteer is in the list
	@Test
	public void testisSignedUpWhenSignedUp(){
		job1.signUp(volun1, WorkCategory.LIGHT);
		assertTrue("True should be returned as the volunteer is already signed up. ", job1.isSignedUp(volun1));
	}
	
	//Tests isSignedUp when volunteer is not in the list
	@Test
	public void testIsSignedUpWhenNotSignedUpListEmpty(){
		job2.isSignedUp(volun2);
	}
	
	//Tests isSignedUp when volunteer is not in the list and the list is not empty
	@Test
	public void testIsSignedUpWhenNotSignedUpListNotEmpty(){
		job2.signUp(volun1, WorkCategory.MEDIUM);
		job2.isSignedUp(volun2);
	}
	
	///////////////Test the getNumOpen method ////////////////
	//Tests when the WorkCategory is LIGHT
	@Test
	public void testgetNumOpenLIGHT() {
		assertEquals(job3.getNumOpen(WorkCategory.LIGHT), 1);
	}
	
	//Tests when the Work Category is MEDIUM 
	@Test
	public void testgetNumOpenMEDIUM() {
		assertEquals(job3.getNumOpen(WorkCategory.MEDIUM), 1);
	}

	//Tests when the Work Category is HEAVY 
	@Test
	public void testgetNumOpenHEAVY() {
		assertEquals(job3.getNumOpen(WorkCategory.HEAVY), 1);
	}
	
	//Tests after a volunteer has been added and 1 is decremented from the job category
	@Test
	public void testgetNumOpenDecremented() {
		assertEquals(job3.getNumOpen(WorkCategory.HEAVY), 1);
		job3.signUp(volun1,WorkCategory.HEAVY);
		assertEquals(job3.getNumOpen(WorkCategory.HEAVY), 0);
	}
	
	/////////////Test isOpen//////////////////
	//Tests isOpen when the total for the WorkCategory is 0
	//Should return false
	public void testIsOpenWhenZero(){
		assertFalse(job4.isOpen(WorkCategory.LIGHT));
	}
	
	//Should return true
	public void testIsOpenWhenGreaterThanZero(){
		assertFalse(job4.isOpen(WorkCategory.MEDIUM));
	}
}
