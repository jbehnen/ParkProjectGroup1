package park_model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import user_interface.StartUp;
import config_files.Config;

public class UserList {
	
	/**
	 * Constructs a list of all users from a given file.
	 * @throws IOException 
	 */
	public static Collection<User> getAllUsers(String inputFile) {
		Collection<User> users = new ArrayList<User>();
		String line;
		InputStream is = StartUp.class.getClassLoader().getResourceAsStream(inputFile);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(is));
		try {
			while((line = fileReader.readLine()) != null) {
				if (line.charAt(0) == 'V') {
					users.add(User.parseDelimitedString(line));
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
		return users;
	}
	
}