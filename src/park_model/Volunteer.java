package park_model;

public class Volunteer extends User {

	private String myLastName;
	
	public Volunteer(String theEmail, String theLastName) {
		super(theEmail);
		myLastName = theLastName;
	}
	
	/**
	 * Copy constructor for Volunteer.
	 * 
	 * @param theVolunteer
	 */
	public Volunteer(Volunteer theVolunteer) {
		super(theVolunteer.getEmail());
		myLastName = theVolunteer.getLastName();
	}
	
	public String getLastName() {
		return myLastName;
	}

}
