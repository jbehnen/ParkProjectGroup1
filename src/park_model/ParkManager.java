package park_model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkManager extends User {

	private List<String> myParks;
	
	protected ParkManager(String theEmail, List<String> theParks) {
		super(theEmail);
		myParks = new ArrayList<String>(theParks);
	}
	
	public List<String> getParks() {
		return Collections.unmodifiableList(myParks);
	}

}
