package park_model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//Serializable assistance from http://www.tutorialspoint.com/java/java_serialization.htm

/**
 * Provides the ability to read users from serialized files.
 * 
 * @author Julia Behnen
 * @version 5/24/2015
 */
public class UserList {
	
	/**
	 * Constructs a list of all users from the given file.
	 * 
	 * Preconditions: theFile != null.
	 * 
	 * @return A list of all users from the given file.
	 */
	@SuppressWarnings("unchecked")
	public static Collection<User> getAllUsers(String theFile) {
		Collection<User> users = new ArrayList<User>();
		try {
			FileInputStream fileIn = new FileInputStream(theFile);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			users = (List<User>) in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e1) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return users;
	}
	
}