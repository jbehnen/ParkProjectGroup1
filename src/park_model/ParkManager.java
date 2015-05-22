package park_model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An immutable park manager who is registered in the system.
 * 
 * @author Julia Behnen
 * @version 5/3/2015
 */

public class ParkManager extends User {
<<<<<<< Updated upstream
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8909077605412708768L;
	
=======

>>>>>>> Stashed changes
	/**
	 * A list of parks that the ParkManager manages.
	 */
	private List<String> myParks;
	
	/**
	 * Constructs a new Park Manager.
	 * 
	 * @param theEmail The email address used to identify the ParkManager.
	 * @param theParks The parks that the ParkManager manages.
	 */
	public ParkManager(String myEmail, String myFirstName, String myLastName, List<String> myParks) {
		super(myEmail, myFirstName, myLastName);
		this.myParks = new ArrayList<String>(myParks);
	}
	
	/**
	 * Returns the parks managed by the ParkManager.
	 * 
	 * @return The parks managed by the ParkManager.
	 */
	public List<String> getParks() {
		return Collections.unmodifiableList(myParks);
	}

}
