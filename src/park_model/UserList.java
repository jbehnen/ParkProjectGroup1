package park_model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserList {
	
	/**
	 * Constructs a list of all users from a given file.
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
}