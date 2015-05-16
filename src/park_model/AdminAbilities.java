package park_model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AdminAbilities {
	
	Collection<User> myVolunteerList;
	
	public AdminAbilities(String fileName) {
		myVolunteerList = UserList.getAllUsers(fileName);
	}
	
	/**
	 *	Class Constructor.
	 * 	Creates an instance of Job.
	 * @param theLastName The last name being searched for.
	 * @return An unmodifiable list of volunteers with the last name theLastName. 
	 * 
	 */
	public Collection<User> getVolunteersByLastName(String theLastName) {   
		
		Collection<User> lastNameList = new ArrayList<User>();
		
		for(User myVolunteer : myVolunteerList)
		{
			if (myVolunteer.getLastName().equals(theLastName))
			{
				lastNameList.add(myVolunteer);
			}
		}
		
		return Collections.unmodifiableCollection(lastNameList);	
	}
}
