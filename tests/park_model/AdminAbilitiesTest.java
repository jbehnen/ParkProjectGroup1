package park_model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import park_model.AdminAbilities;
import park_model.User;


/**
 * Tests the VolunteerList class.
 * 
 * @author Julia Behnen
 * @version 5/10/2015
 */
public class AdminAbilitiesTest {
	
	private User stewart;
	private User martin;
	private User maryClaire;
	private AdminAbilities myAdminAbilities;
	private Collection<User> expectedCollection;
	
	@Before
	public void setUp() throws Exception {
		stewart = new User("stewart@cc.com", "Jon", "Stewart");
		martin = new User("king@selma.org", "Martin Luther", "King");
		maryClaire = new User("mcking@uw.edu", "Mary-Claire", "King");
		// Construct an admin abilities class with no stored volunteers
		myAdminAbilities = new AdminAbilities("config_files/empty.txt");
		expectedCollection = new ArrayList<>();
	}
	
	@Test
	public void testGetVolunteersByLastNameShouldNoVolunteersIfNoMatch() {
		assertEquals("Should return an empty list if no matches", 0, 
				(myAdminAbilities.getVolunteersByLastName("Brenner").size()));
	}
	
	@Test
	public void testGetVolunteersByLastNameShouldReturnOneVolunteerWithThatLastName() {
		expectedCollection.add(stewart);
		myAdminAbilities.addVolunteer(stewart);
		assertEquals("Should return a single volunteer if one match", 1, 
				(myAdminAbilities.getVolunteersByLastName("Stewart").size()));
		assertTrue("The returned entry should be a match to the existing volunteer", 
				expectedCollection.containsAll(myAdminAbilities.getVolunteersByLastName("Stewart")));
	}
	
	@Test
	public void testGetVolunteersByLastNameShouldReturnMultipleVolunteersWithThatLastName() {
		expectedCollection.add(martin);
		expectedCollection.add(maryClaire);
		myAdminAbilities.addVolunteer(martin);
		myAdminAbilities.addVolunteer(maryClaire);
		assertEquals("Should return two volunteers if multiple matches", 2, 
				(myAdminAbilities.getVolunteersByLastName("King").size()));
		assertTrue("The returned volunteers should match the existing volunteers", 
				expectedCollection.containsAll(myAdminAbilities.getVolunteersByLastName("King")));
	}
	
	@Test
	public void testGetVolunteersByLastNameShouldOnlyReturnVolunteersWithThatLastName() {
		expectedCollection.add(martin);
		expectedCollection.add(maryClaire);
		expectedCollection.add(stewart);
		myAdminAbilities.addVolunteer(martin);
		myAdminAbilities.addVolunteer(maryClaire);
		myAdminAbilities.addVolunteer(stewart);
		assertEquals("Should return only the two volunteers that match", 2, 
				(myAdminAbilities.getVolunteersByLastName("King").size()));
	}

}

