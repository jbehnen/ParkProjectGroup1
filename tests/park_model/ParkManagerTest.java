package park_model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import park_model.ParkManager;
import config_files.Config;

public class ParkManagerTest {
	
	private ParkManager myPM;
	private List<String> myParks;

	@Before
	public void setUp() throws Exception {
		myParks = new ArrayList<String>();
		myParks.add("Park");
		myPM = new ParkManager(Config.DEFAULT_EMAIL, Config.DEFAULT_FIRST_NAME, 
				Config.DEFAULT_LAST_NAME, myParks);
	}

	@Test
	public void testParkManagerConstructorShouldContainACopyOfTheParks() {
		assertEquals("Email should be the same", Config.DEFAULT_EMAIL, myPM.getEmail());
		assertEquals("Park list contents should be the same", myParks, myPM.getParks());
		assertNotSame("List should be a copy", myParks, myPM.getParks());
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testGetParksShouldReturnUnmodifiableList() {
		myPM.getParks().add("Failure Park");
	}

}
