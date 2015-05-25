package park_model;

import java.io.Serializable;

/**
 * An immutable system user; used for Admin and Volunteer.
 * 
 * @author Julia Behnen
 * @version 5/3/2015
 */
// Invariants: myEmail != null, myFirstName != null, myLastName != null.
public class User implements Serializable {

	private static final long serialVersionUID = 2978455524937410469L;

	private String myEmail;
	
	private String myFirstName;

	private String myLastName;
	
	/**
	 * Constructs a User.
	 * 
	 * Preconditions: theEmail != null, theFirstName != null, theLastName != null.
	 */
	public User(String myEmail, String myFirstName, String myLastName) {
		this.myEmail = myEmail;
		this.myFirstName = myFirstName;
		this.myLastName = myLastName;
		assert myEmail != null;
		assert myFirstName != null;
		assert myLastName != null;
	}
	
	/**
	 * Returns the user's email address.
	 * 
	 * @return The user's email address.
	 */
	public String getEmail() {
		return myEmail;
	}
	
	/**
	 * Return the user's first name.
	 * 
	 * @return The user's first name.
	 */
	public String getFirstName() {
		return myFirstName;
	}
	
	/**
	 * Return the user's last name.
	 * 
	 * @return The user's last name.
	 */
	public String getLastName() {
		return myLastName;
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
		return myFirstName + " " + myLastName + " " + myEmail;
	}
	
}
