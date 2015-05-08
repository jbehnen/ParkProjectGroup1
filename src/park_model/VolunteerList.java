package park_model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VolunteerList {
	
	private List<User> list;
	private List<User> newList;
	
	/**
	 * Constructs a list of all volunteers in the system from the back-end data.
	 */
	public VolunteerList() {
		
		list = new ArrayList<>();
//		Volunteer theLastName = null;
//		list.add(theLastName);
	}
	
	/**
	 * A constructor that makes an empty list. Testing purposes only.
	 * 
	 * @param test A boolean indicating that this is a test.
	 */
	public VolunteerList(boolean test) {
		list = new ArrayList<>();
	}
	
	/**
	 *	Class Constructor.
	 * 	Creates an instance of Job.
	 * @param theLastName The last name being searched for.
	 * @return An unmodifiable list of volunteers with the last name theLastName. 
	 * 
	 */
	public List<User> getVolunteersByLastName(String theLastName) {   
		
		newList = new ArrayList<>();
		
		for(User myVolunteer : list)
		{
			if (myVolunteer.getLastName().equals(theLastName))
			{
				newList.add(myVolunteer);
			}
		}
		
		return Collections.unmodifiableList(newList);	
	}
	
	/**
	 * Adds volunteer to list. Testing purposes only.
	 */
	public void addVolunteer(User theVolunteer) {
		list.add(theVolunteer);
	}
	
	// Note from your friendly neighborhood Julia: I realized we don't actually
	// need this function, as the list of users will never change. 
	
//	/**
//	 * Writes all list information to the back-end storage.
//	 */
//	public void saveList() {
//		
//		//create a input file of volunteer list
//		Scanner input = null; 
//	    try
//	    { 
//	        input = new Scanner(new File("VolunteerList/volunteerList.txt")); 
//	          
//	        while (input.hasNextLine()) 
//	        { 
//	            String lastName = input.nextLine();
//	            System.out.println(lastName);   
//	        } 
//	    } 
//	    catch (FileNotFoundException ex) 
//	    { 
//	        System.out.println(ex.getMessage()); 
//	    } 
//	    finally
//	    { 
//	        if (input != null) 
//	        { 
//	            input.close(); 
//	        } 
//	    }
//	}
	
}
