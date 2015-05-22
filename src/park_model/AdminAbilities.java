package park_model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Provides the methods that allow Administrators to complete
 * their user stories and access the list of jobs.
 * 
 * @author Julia Behnen
 * @version 5/19/2015
 */
public class AdminAbilities {
	
	Collection<User> myVolunteers;
	
	/**
	 * Constructs the AdminAbilities and gets all of the volunteers
	 * stored in the given file.
	 * 
	 * Preconditions: fileName != null.
	 * 
	 * @param fileName The name of the file that the list of all
	 * volunteers is stored in and loaded from.
	 */
	public AdminAbilities(String fileName) {
		myVolunteers = UserList.getAllUsers(fileName);
		assert myVolunteers != null;
	}
	
	/**
	 * Returns an unmodifiable list of volunteers with the last name theLastName. 
	 * 
	 * Precondition: theLastName != null.
	 * 
	 * @return An unmodifiable list of volunteers with the last name theLastName. 
	 */
	public Collection<User> getVolunteersByLastName(String theLastName) {   
		
		Collection<User> lastNameList = new ArrayList<User>();
		
		for(User myVolunteer : myVolunteers) {
			if (myVolunteer.getLastName().equals(theLastName)) {
				lastNameList.add(myVolunteer);
			}
		}
		assert myVolunteers != null;
		return Collections.unmodifiableCollection(lastNameList);	
	}
	
	/**
	 * Adds a volunteer to the collection of volunteers.
	 * 
	 * Precondition: theVolunteer != null.
	 * 
	 * TESTING PURPOSES ONLY. Needs to be repackaged and
	 * have the access modifier changed.
	 */
	public void addVolunteer(User theVolunteer) {
		myVolunteers.add(theVolunteer);
		assert myVolunteers != null;
	}
}
