package park_model;

/**
 * An immutable system user; used for Admin and Volunteer.
 * 
 * @author Julia Behnen
 * @version 5/3/2015
 */

public class User {

	/**
	 * The user's email address.
	 */
	private String myEmail;
	
	private String myFirstName;

	private String myLastName;
	
	/**
	 * Constructs a User.
	 */
	public User(String myEmail, String myFirstName, String myLastName) {
		this.myEmail = myEmail;
		this.myFirstName = myFirstName;
		this.myLastName = myLastName;
	}
	
	/**
	 * Returns the user's email address.
	 * 
	 * @return The user's email address.
	 */
	public String getEmail() {
		return myEmail;
	}
	
	public String getFirstName() {
		return myFirstName;
	}
	
	public String getLastName() {
		return myLastName;
	}
	
	/**
	 * Returns a User derived from the string produced by a CSV line,
	 * format "{user id char},email,firstName,lastName"
	 * 
	 * @param theString
	 */
	public static User parseDelimitedString(String theString) {
		String[] userInfo = theString.split(",");
		return new User(userInfo[1], userInfo[2], userInfo[3]);
	}
	
	/**
	 * A delimited string that can be parsed by parseDelimitedString.
	 * 
	 * @return a delimited string that can be parsed by parseDelimitedString.
	 */
	public String toDelimitedStringVolunteer() {
		return "V," + myEmail + "," + myFirstName + "," + myLastName;
	}

	/**
	 * Generates a hash code based only on user email.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((myEmail == null) ? 0 : myEmail.hashCode());
		return result;
	}

	/**
	 * Tests for equality using only the user email.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (myEmail == null) {
			if (other.myEmail != null)
				return false;
		} else if (!myEmail.equals(other.myEmail))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [myEmail=" + myEmail + ", myFirstName=" + myFirstName
				+ ", myLastName=" + myLastName + "]";
	}
	
}
