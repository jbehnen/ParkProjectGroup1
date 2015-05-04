package model_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import park_model.Volunteer;

public class VolunteerTest {

	private static final String DEFAULT_EMAIL = "smith@aol.com";
	private static final String DEFAULT_LAST_NAME = "Smith";
	private Volunteer myVol;

	@Before
	public void setUp() throws Exception {
		myVol = new Volunteer("smith@aol.com", "Smith");
	}

	@Test
	public void testVolunteerConstructor() {
		assertEquals("Email should be set", DEFAULT_EMAIL, myVol.getEmail());
		assertEquals("Last name should be set", DEFAULT_LAST_NAME, myVol.getLastName());
	}
	
	@Test
	public void testVolunteerCopyConstructor() {
		Volunteer copyVol = new Volunteer(myVol);
		assertEquals("Email should be same", myVol.getEmail(), copyVol.getEmail());
		assertEquals("Last name should be set", myVol.getLastName(),
				copyVol.getLastName());
		assertNotSame("Copy should be distinct object", myVol, copyVol);
	}

}
