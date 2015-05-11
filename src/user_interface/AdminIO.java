/**
 * AdminIO.java
 * 
 * This class is searching for Volunteer by lastName
 */
package user_interface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import config_files.Config;
import park_model.User;
import park_model.VolunteerList;


/**
 * Runs the menu for administrators.
 * 
 * @author Julia and Lee
 * @version 5/10/2015
 */
public class AdminIO implements IO{
	private User myUser;
	private VolunteerList myVolunteerList;
	List<User>myVolList;
	
	public AdminIO(User myUser) {
		this.myUser = myUser;
		myVolunteerList = new VolunteerList(Config.USER_FILE);
	}
	/**
	 * This my control menu
	 */
	@Override
	public void mainMenu() {
		
		int i = 0;
		boolean valid = false;
		
 		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("1: Search For Volunteer. \n2: Quit");
		
		while(!valid){
			try{
				i = Integer.parseInt(console.readLine());
				
				switch(i){
				case 1:
					searchVol(console);
				case 2:	
					System.exit(0);
				}
			}catch(IOException e){
				System.err.println("Make your choice");
			}
		}
		
	}
	
	//This method shows off the name of volunteer have been searched
	private void searchVol(BufferedReader console){
		String name = null;
		while (name == null) {
			System.out.println("Please enter a last name to search with: ");
			try {
				name = console.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<User> volunteers = myVolunteerList.getVolunteersByLastName(name);
		for(int i = 0; i < volunteers.size(); i++){
			System.out.println(volunteers.get(i).toString());
		}
		mainMenu();
	}
	
}