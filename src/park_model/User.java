package park_model;

public abstract class User {
	private String myEmail;
	private VolunteerList myVolunteerList;
	private JobSchedule myJobSchedule;
	
	protected User(String theEmail) {
		myEmail = theEmail;
	}
	
	public void logIn() {
		myVolunteerList = new VolunteerList();
		myJobSchedule = new JobSchedule();
	}
	
	public void logOut() {
		myVolunteerList.saveList();
		myJobSchedule.saveList();
	}
	
	public String getEmail() {
		return myEmail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((myEmail == null) ? 0 : myEmail.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
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
