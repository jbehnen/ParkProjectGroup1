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

//	@Test
//	public void test() {
//		assertEquals("List of Volunteer Last Name should be set", LastName,
//				myVolunteerList.getVolunteersByLastName(LastName));
//	}
	
	@Test
	public void testGetVolunteersByLastName() {
		// Hello! I recommend that you look at the text file I referenced and 
		// just call getVolunteersByLastName using that as the expected data,
		// because it's what's being loaded into the list during setUp. 
		// We should really get rid of the addVolunteer method anyway,
		// since the list really shouldn't be modified. It's an artifact 
		// of the time before we could read test files.
		// For example:
		List<User> expectedList = new ArrayList<>();
		expectedList.add(new User("stewart@cc.com", "Jon", "Stewart"));
		assertEquals(myVolunteerList.getVolunteersByLastName("Stewart"), expectedList);
		// checking the size of the list you get back is a good alternative, and easier
		// to set up
		
//		assertEquals(myVolunteerList.getVolunteersByLastName("Smith").size(), 0);
//		myVolunteerList.addVolunteer(new User("smith@aol.com", "John" ,"Smith"));
//		assertEquals(myVolunteerList.getVolunteersByLastName("Smith").size(), 1);
//		assertEquals(myVolunteerList.getVolunteersByLastName("Jones").size(), 0);
	}

}
