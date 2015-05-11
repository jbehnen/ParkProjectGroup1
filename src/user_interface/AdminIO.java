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
import java.util.List;

import config_files.Config;
import park_model.User;
import park_model.VolunteerList;


public class AdminIO implements IO{
	private User myUser;
	private VolunteerList myVolunteerList;
	private List<User> myVolList;
	public List<User> getVolunteerByLastName(String lastName){
		List<User> searchResult = new ArrayList<>();
		for(User vol : myVolList){
			
			if(lastName == vol.getLastName()){
				searchResult.add(vol);
			}
			//Want to ask if there are no lastName in List, should It return an original list or keep searching
//			if(lastName != vol.getLastName()){ 
//				return myVolList;
//			}
			//Want to ask if there more than 1 volunteer have same lastName in List, what should it return???
		}
		return searchResult;
	}
	
	
	public AdminIO(User myUser) {
		this.myUser = myUser;
		myVolunteerList = new VolunteerList(Config.USER_FILE);
	}

	@Override
	public void mainMenu() {
		System.out.println("I'm in the Admin menu.");
		System.out.println("Admin " + myUser);
		System.out.println("1: Search For Volunteer. \n2: Quit");
		
		int c = 1;
		
 		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		
		while(c != 0){
			try{
				int i = Integer.parseInt(console.readLine());
				if(i > 0){
					
					switch(i){
					case 1:
						myVolList.add(myUser);
						break;
					case 2:
						System.exit(0);
					default:
						System.err.println("Error!!!");
					}
				}else{
					System.err.println("You can only take 1 or 2");
				}
			} catch(IOException e){
				System.err.println("You have to take right choice");
			}
		}
		
		
	}
	
	
}