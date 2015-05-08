package model_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import park_model.User;

public class UserTest {
	
	private static final String DEFAULT_EMAIL = "smith@aol.com";
	private static final String DEFAULT_FIRST_NAME = "Bob";
	private static final String DEFAULT_LAST_NAME = "Smith";
	private User myUser;

	@Before
	public void setUp() throws Exception {
		myUser = new User(DEFAULT_EMAIL, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME);
	}

	@Test
	public void testVolunteerConstructor() {
		assertEquals("Email should be set", DEFAULT_EMAIL, myUser.getEmail());
	}
	
	@Test
	public void testUserEquals() {
		User otherUser = new User(DEFAULT_EMAIL, "Georgia", "Jones");
		assertEquals("Email is only parameter needed for equality", myUser, otherUser);
		otherUser = new User("jones@aol.com", DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME);
		assertNotEquals("Name does not guarantee equality", myUser, otherUser);
	}

}
