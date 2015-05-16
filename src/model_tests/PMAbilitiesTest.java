package model_tests;

import static org.junit.Assert.*;

import java.awt.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import park_model.Job;
import park_model.PMAbilities;
import park_model.RulesHelp;
import park_model.User;

public class PMAbilitiesTest {
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testAddJobShouldAddAJob() {
		PMAbilities abilities = new PMAbilities("config_files/empty.txt");
		assertEquals("Starts with no jobs", 0, abilities.getNumJobs());
		Job testJob = new Job("Park Name", RulesHelp.getTodaysDate(), 1,
				2, 3, 4, "Description");
		abilities.addJob(testJob);
		assertEquals("First job added", 1, abilities.getNumJobs());
		abilities.addJob(testJob);
		assertEquals("Second job added", 2, abilities.getNumJobs());
	}

}
