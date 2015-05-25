package park_model;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import config_files.Config;

/**
 * Tests the UserList class.
 * 
 * @author Julia Behnen
 * @version 5/24/2015
 */
public class UserListTest {

	@Test
	public void testGetAllUsersFromEmptyFileShouldReturnEmptyList() {
		Collection<User> users = UserList.getAllUsers(Config.EMPTY_TEXT_FILE);
		assertEquals(0, users.size());
	}
	
	@Test
	public void testGetAllUsersFromTestFileShouldReturnPopulatedList() {
		Collection<User> users = 
				UserList.getAllUsers("src/config_files/userListTest.txt");
		assertEquals("All users loaded", 5, users.size());
		assertTrue("User data is correct", 
				users.contains(new User("smith@aol.com", "John", "Smith")));
	}

}
