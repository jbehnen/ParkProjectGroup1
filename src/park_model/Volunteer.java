package park_model;

/**
 * A volunteer who is registered in the system.
 * 
 * @author Julia Behnen
 * @version 5/3/2015
 */
public class Volunteer extends User {

	/**
	 * The volunteer's last name.
	 */
	private String myLastName;
	
	/**
	 * Constructs a Volunteer.
	 * 
	 * @param theEmail The volunteer's email.
	 * @param theLastName The volunteer's last name.
	 */
	public Volunteer(String theEmail, String theLastName) {
		super(theEmail);
		myLastName = theLastName;
	}
	
	/**
	 * Copy constructor for Volunteer.
	 * 
	 * @param theVolunteer The Volunteer object to be copied.
	 */
	public Volunteer(Volunteer theVolunteer) {
		super(theVolunteer.getEmail());
		myLastName = theVolunteer.getLastName();
	}
	
	/**
	 * Returns the volunteer's last name.
	 * 
	 * @return The volunteer's last name.
	 */
	public String getLastName() {
		return myLastName;
	}

}
