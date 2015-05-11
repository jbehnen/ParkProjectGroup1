package user_interface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import park_model.ParkManager;
import park_model.User;
import config_files.Config;

// File reading assistance from https://www.daniweb.com/software-development/java/threads/17262/reading-in-a-csv-file-and-loading-the-data-into-an-array

/**
 * Starts up the program, lets the user log in,
 * and directs the user to their proper menu.
 * 
 * @author Julia Behnen
 * @version 5/8/2015
 */
public class StartUp {
	
	public static void main(String args[]) throws IOException {
		// File reading code adapted from 
		//https://www.daniweb.com/software-development/java/threads
		//17262/reading-in-a-csv-file-and-loading-the-data-into-an-array
		String line;
		String email = null;
		String userType = null;
		String[] split = null;
		User user;
		boolean loggedIn = false;
		File file = new File(Config.USER_FILE);
		
		BufferedReader fileReader = new BufferedReader(new FileReader(file));
		
		//http://stackoverflow.com/questions/4644415/java-how-to-get-input-from-system-console
		
		System.out.println("Please enter your email: ");
		Scanner in = new Scanner(System.in); 
		email = in.next();
//		in.close();
		
		// search Config.USER_FILE for user
		while((line = fileReader.readLine()) != null) {
			split = line.split(",");
			userType = split[0];
			if (email.equals(split[1])) {
				loggedIn = true;
				break;
			}
		}
		
		if(loggedIn) {
			IO menu = null;
			
			// go to correct menu page
			switch(userType) {
				case("A"): 
					user = new User(email, split[2], split[3]);
					menu = new AdminIO(user);
					menu.mainMenu();
					break;
				case("V"): 
					user = new User(email, split[2], split[3]);
					menu = new VolunteerIO(user);
					break;
				case("P"):
					ArrayList<String> parks = new ArrayList<>();
					for(int i = 4; i < split.length; i++) {
						parks.add(split[i]);
					}
					user = new ParkManager(email, split[2], split[3], parks);
					menu = new ParkManagerIO((ParkManager) user);
					break;
			}
			menu.mainMenu();
		} else {
			System.out.println("Invalid login.");
		}
	}
	
}
