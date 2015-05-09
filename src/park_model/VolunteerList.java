package park_model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config_files.Config;

public class VolunteerList {
	
	private List<User> list;
	private List<User> newList;
	
	/**
	 * Constructs a list of all volunteers in the system from the back-end data.
	 * @throws IOException 
	 */
	public VolunteerList(String inputFile) {
		list = new ArrayList<User>();
		File file = new File(inputFile);
		String line;
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while((line = fileReader.readLine()) != null) {
				if (line.charAt(0) == 'V') {
					list.add(User.parseDelimitedString(line));
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			fileReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * A constructor that makes an empty list. Testing purposes only.
	 * 
	 * @param test A boolean indicating that this is a test.
	 */
	public VolunteerList(boolean test) {
		list = new ArrayList<User>();
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

	
}