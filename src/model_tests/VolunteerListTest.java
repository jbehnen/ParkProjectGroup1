package model_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import park_model.User;
import park_model.VolunteerList;


public class VolunteerListTest {
	private static final String LastName = "Smith";
	private VolunteerList myVolunteerList;
	@Before
	public void setUp() throws Exception {
		myVolunteerList = new VolunteerList(true);
//		myVolunteerList.getVolunteersByLastName("Smith");
	}

//	@Test
//	public void test() {
//		assertEquals("List of Volunteer Last Name should be set", LastName,
//				myVolunteerList.getVolunteersByLastName(LastName));
//	}
	
	@Test
	public void testGetVolunteersByLastName() {
		assertEquals(myVolunteerList.getVolunteersByLastName("Smith").size(), 0);
		myVolunteerList.addVolunteer(new User("smith@aol.com", "John" ,"Smith"));
		assertEquals(myVolunteerList.getVolunteersByLastName("Smith").size(), 1);
		assertEquals(myVolunteerList.getVolunteersByLastName("Jones").size(), 0);
	}

}
