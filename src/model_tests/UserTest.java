package model_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import park_model.User;
import config_files.Config;

/**
 * Tests the User class.
 * 
 * @author Julia Behnen
 * @version 5/10/2015
 */
public class UserTest {
	
	private User myUser;

	@Before
	public void setUp() throws Exception {
		myUser = new User(Config.DEFAULT_EMAIL, Config.DEFAULT_FIRST_NAME, Config.DEFAULT_LAST_NAME);
	}

	@Test
	public void testVolunteerConstructor() {
		assertEquals("Email should be set", Config.DEFAULT_EMAIL, myUser.getEmail());
	}
	
	@Test
	public void testUserEquals() {
		User otherUser = new User(Config.DEFAULT_EMAIL, "Eddie", "Izzard");
		assertEquals("Email is only parameter needed for equality", myUser, otherUser);
		otherUser = new User("jones@aol.com", Config.DEFAULT_LAST_NAME, Config.DEFAULT_LAST_NAME);
		assertNotEquals("Last name does not guarantee equality", myUser, otherUser);
	}
	
	@Test
	public void toDelimitedStringVolunteerShouldProduceDataThatCanBeParsed() {
		User user = new User(Config.DEFAULT_EMAIL, Config.DEFAULT_FIRST_NAME,
				Config.DEFAULT_LAST_NAME);
		String string = user.toDelimitedStringVolunteer();
		User sameUser = User.parseDelimitedString(string);
		assertEquals("User info transferred to/from string", user, sameUser);
	}

}