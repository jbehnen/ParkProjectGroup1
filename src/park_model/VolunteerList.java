package park_model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VolunteerList {
	
	private List<User> list;
	private List<User> newList;
	
	/**
	 * Constructs a list of all volunteers in the system from the back-end data.
	 * @throws IOException 
	 */
	public VolunteerList(String inputFile) {
		list = new ArrayList<User>();
		String line;
		InputStream is = this.getClass().getResourceAsStream(inputFile);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(is));
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

	
}