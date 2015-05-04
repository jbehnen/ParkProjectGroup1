package park_model;

public abstract class User {
	
	/**
	 * The user's email address.
	 */
	private String myEmail;
	
	/**
	 * The list of all volunteers in the system.
	 */
	private VolunteerList myVolunteerList;
	
	/**
	 * The list of all upcoming jobs in the system.
	 */
	private JobSchedule myJobSchedule;
	
	/**
	 * Constructs a User.
	 * 
	 * @param theEmail The user's email address.
	 */
	protected User(String theEmail) {
		myEmail = theEmail;
	}
	
	/**
	 * Logs the user in and initializes the job schedule
	 * and volunteer list data.
	 */
	public void logIn() {
		myVolunteerList = new VolunteerList();
		myJobSchedule = new JobSchedule();
	}
	
	/**
	 * Logs the user out and saves the job schedule
	 * and volunteer list data to the system.
	 */
	public void logOut() {
		myVolunteerList.saveList();
		myJobSchedule.saveList();
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
	
}
