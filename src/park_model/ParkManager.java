package park_model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A park manager who is registered in the system.
 * 
 * @author Julia Behnen
 * @version 5/3/2015
 */
public class ParkManager extends User {

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
	public ParkManager(String theEmail, List<String> theParks) {
		super(theEmail);
		myParks = new ArrayList<String>(theParks);
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
