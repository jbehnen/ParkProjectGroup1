package model_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import park_model.ParkManager;

public class ParkManagerTest {
	
	private static final String DEFAULT_EMAIL = "smith@aol.com";
	private static final String DEFAULT_FIRST_NAME = "Bob";
	private static final String DEFAULT_LAST_NAME = "Smith";
	
	private ParkManager myPM;
	private List<String> myParks;

	@Before
	public void setUp() throws Exception {
		myParks = new ArrayList<String>();
		myParks.add("Park");
		myPM = new ParkManager(DEFAULT_EMAIL, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, myParks);
	}

	@Test
	public void testParkManagerConstructor() {
		assertEquals("Email should be the same", DEFAULT_EMAIL, myPM.getEmail());
		assertEquals("Park list contents should be the same", myParks, myPM.getParks());
		assertNotSame("List should be a copy", myParks, myPM.getParks());
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testGetParksShouldReturnUnmodifiableList() {
		myPM.getParks().add("Failure Park");
	}

}
