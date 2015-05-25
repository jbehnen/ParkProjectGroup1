package park_model;

import java.util.Collections;
import java.util.List;

/**
 * An immutable park manager who is registered in the system.
 * 
 * @author Julia Behnen
 * @version 5/24/2015
 */
// Invariant: theEmail != null, theFirstName != null, theLastName != null, 
// myParks != null.
public class ParkManager extends User {
	
	private static final long serialVersionUID = 8909077605412708768L;
	/**
	 * A list of parks that the ParkManager manages.
	 */
	private List<String> myParks;
	
	/**
	 * Constructs a new Park Manager.
	 * 
	 * Preconditions: theEmail != null, theFirstName != null, theLastName != null, 
	 * and theParks != null.
	 * 
	 * @param theEmail The email address used to identify the ParkManager.
	 * @param theParks The parks that the ParkManager manages.
	 */
	public ParkManager(String theEmail, String theFirstName, String theLastName, 
			List<String> theParks) {
		super(theEmail, theFirstName, theLastName);
		this.myParks = Collections.unmodifiableList(theParks);
		assert theEmail != null;
		assert theFirstName != null;
		assert theLastName != null;
		assert myParks != null;
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
