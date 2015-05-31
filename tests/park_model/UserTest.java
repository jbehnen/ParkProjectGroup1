package park_model;

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
 * @version 5/24/2015
 */
public class UserTest {
	
	private User myUser, otherUser;

	@Before
	public void setUp() throws Exception {
		myUser = new User(Config.DEFAULT_EMAIL, Config.DEFAULT_FIRST_NAME, Config.DEFAULT_LAST_NAME);
		otherUser = new User(Config.DEFAULT_EMAIL, "Eddie", "Izzard");
	}

	@Test
	public void testUserConstructorShouldConstructAUser() {
		assertEquals("Email should be set", Config.DEFAULT_EMAIL, myUser.getEmail());
		assertEquals("First name should be set", Config.DEFAULT_FIRST_NAME, myUser.getFirstName());
		assertEquals("Last name should be set", Config.DEFAULT_LAST_NAME, myUser.getLastName());
	}
	
	@Test
	public void testHashCodeShouldBeEqualIfSameEmail() {
		assertEquals("Email the same so hash code the same", 
				myUser.hashCode(), otherUser.hashCode());
	}
	
	@Test
	public void testEqualsShouldBeTrueIfSameEmail() {
		assertEquals("Email is only parameter needed for equality", myUser, otherUser);
	}
	
	@Test
	public void testEqualsShouldNotBeTrueIfNotSameEmail() {
		User otherUser = new User("jones@aol.com", Config.DEFAULT_LAST_NAME, Config.DEFAULT_LAST_NAME);
		assertNotEquals("Same name does not guarantee equality", myUser, otherUser);
	}
	
	@Test
	public void testToStringShouldReturnExpectedString() {
		assertEquals("Should return correct string", 
				Config.DEFAULT_FIRST_NAME + " " + Config.DEFAULT_LAST_NAME 
				+ " " + Config.DEFAULT_EMAIL, myUser.toString());
	}

}