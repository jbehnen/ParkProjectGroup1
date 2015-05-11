package model_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import park_model.User;
import park_model.VolunteerList;


/**
 * Tests the VolunteerList class.
 * 
 * @author Julia Behnen
 * @version 5/10/2015
 */
public class VolunteerListTest {
	private static final String LastName = "Smith";
	private VolunteerList myVolunteerList;
	@Before
	public void setUp() throws Exception {
		
		myVolunteerList = new VolunteerList("src/config_files/volunteerByLastNameTest.txt");

	}
	
	@Test
	public void testGetVolunteersByLastNameShouldReturnAllVolunteersWithThatLastName() {

		List<User> expectedList = new ArrayList<>();
		expectedList.add(new User("stewart@cc.com", "Jon", "Stewart"));
		assertEquals(expectedList, myVolunteerList.getVolunteersByLastName("Stewart"));
		expectedList.clear();
		expectedList.add(new User("king@selma.org", "Martin Luther", "King"));
		expectedList.add(new User("mcking@uw.edu", "Mary-Claire", "King"));
		assertEquals(expectedList, myVolunteerList.getVolunteersByLastName("King"));
	}

}

