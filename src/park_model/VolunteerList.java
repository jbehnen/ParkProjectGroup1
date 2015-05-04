package park_model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VolunteerList {
	
	private List<Volunteer> list;
	private String myLastName;
	
	/**
	 * Constructs a list of all volunteers in the system from the back-end data.
	 */
	public VolunteerList() {
		
		List<Volunteer> list = new ArrayList<Volunteer>();
		Volunteer theLastName = null;
		list.add(theLastName);
		
	}
	
	/**
	 *	Class Constructor.
	 * 	Creates an instance of Job.
	 * @param theLastName The last name being searched for.
	 * @return An unmodifiable list of volunteers with the last name theLastName. 
	 * 
	 */
	public List<Volunteer> getVolunteersByLastName(String theLastName) {   
		myLastName = theLastName;
		
		return Collections.unmodifiableList(list);	
	}
	
	/**
	 * Writes all list information to the back-end storage.
	 */
	public void saveList() {
		
		//create a input file of volunteer list
		Scanner input = null; 
	    try
	    { 
	        input = new Scanner(new File("VolunteerList/volunteerList.txt")); 
	          
	        while (input.hasNextLine()) 
	        { 
	            String lastName = input.nextLine();
	            System.out.println(lastName);   
	        } 
	    } 
	    catch (FileNotFoundException ex) 
	    { 
	        System.out.println(ex.getMessage()); 
	    } 
	    finally
	    { 
	        if (input != null) 
	        { 
	            input.close(); 
	        } 
	    }
	}
	
}
