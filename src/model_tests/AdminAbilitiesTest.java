package model_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import park_model.AdminAbilities;
import park_model.User;
import park_model.UserList;


/**
 * Tests the VolunteerList class.
 * 
 * @author Julia Behnen
 * @version 5/10/2015
 */
public class AdminAbilitiesTest {
	
	private AdminAbilities myAdminAbilities;
	
	@Before
	public void setUp() throws Exception {
		
		myAdminAbilities = new AdminAbilities("config_files/volunteerByLastNameTest.txt");

	}
	
	@Test
	public void testGetVolunteersByLastNameShouldNoVolunteersIfNoMatch() {
		assertEquals("Should return an empty list if no matches", 0, 
				(myAdminAbilities.getVolunteersByLastName("Brenner").size()));
	}
	
	@Test
	public void testGetVolunteersByLastNameShouldReturnOneVolunteersWithThatLastName() {
		Collection<User> expectedCollection = new ArrayList<>();
		expectedCollection.add(new User("stewart@cc.com", "Jon", "Stewart"));
		assertEquals("Should return a single volunteer if one match", 
				expectedCollection.size(), 
				(myAdminAbilities.getVolunteersByLastName("Stewart").size()));
		assertTrue("The returned entry should be a match to the existing volunteer", 
				expectedCollection.containsAll(myAdminAbilities.getVolunteersByLastName("Stewart")));
	}
	
	@Test
	public void testGetVolunteersByLastNameShouldReturnMultipleVolunteersWithThatLastName() {
		Collection<User> expectedCollection = new ArrayList<>();
		expectedCollection.add(new User("king@selma.org", "Martin Luther", "King"));
		expectedCollection.add(new User("mcking@uw.edu", "Mary-Claire", "King"));
		assertEquals("Should return multiple volunteer if multiple matches", 
				expectedCollection.size(), 
				(myAdminAbilities.getVolunteersByLastName("King").size()));
		assertTrue("The returned volunteers should match the existing volunteers", 
				expectedCollection.containsAll(myAdminAbilities.getVolunteersByLastName("King")));
	}

}

