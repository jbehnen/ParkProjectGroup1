package model_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ JobScheduleTest.class, JobTest.class, 
	ParkManagerTest.class, UserTest.class,
	VolunteerListTest.class})
public class AllTests {

} 