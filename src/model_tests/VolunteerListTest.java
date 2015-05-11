package model_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import park_model.User;
import park_model.VolunteerList;


public class VolunteerListTest {
	private static final String LastName = "Smith";
	private VolunteerList myVolunteerList;
	@Before
	public void setUp() throws Exception {
		
		// this file has some duplicate last names, some non-duplicates.
		// It will be good for testing
		myVolunteerList = new VolunteerList("src/config_files/volunteerByLastNameTest.txt");

	}
	
	@Test
	public void testGetVolunteersByLastName() {
		
		List<User> expectedList = new ArrayList<>();
		expectedList.add(new User("stewart@cc.com", "Jon", "Stewart"));
		assertEquals(myVolunteerList.getVolunteersByLastName("Stewart"), expectedList);
		
		// checking the size of the list you get back is a good alternative,
		
		assertEquals(myVolunteerList.getVolunteersByLastName("Smith").size(), 0);
		myVolunteerList.addVolunteer(new User("smith@aol.com", "John" ,"Smith"));
		assertEquals(myVolunteerList.getVolunteersByLastName("Smith").size(), 1);
		assertEquals(myVolunteerList.getVolunteersByLastName("Jones").size(), 0);
	}

}

