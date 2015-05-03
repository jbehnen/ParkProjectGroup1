package model_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import park_model.ParkManager;
import park_model.User;
import park_model.Volunteer;

public class UserTest {
	
	private static final String DEFAULT_EMAIL = "smith@aol.com";
	private static final String DEFAULT_LAST_NAME = "Smith";
	private User myUser;

	@Before
	public void setUp() throws Exception {
		myUser = new Volunteer(DEFAULT_EMAIL, DEFAULT_LAST_NAME);
	}

	@Test
	public void testVolunteerConstructor() {
		assertEquals("Email should be set", DEFAULT_EMAIL, myUser.getEmail());
	}
	
	@Test
	public void testUserEquals() {
		User otherUser = new Volunteer(DEFAULT_EMAIL, "Jones");
		assertEquals("Email is only parameter needed for equality", myUser, otherUser);
		otherUser = new Volunteer("jones@aol.com", DEFAULT_LAST_NAME);
		assertNotEquals("Last name does not guarantee equality", myUser, otherUser);
	}

}
