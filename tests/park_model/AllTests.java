package park_model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Runs the tests for all classes in the park_model package.
 * 
 * @author Julia Behnen
 * @version 5/24/2015
 */
@RunWith(Suite.class)
@SuiteClasses({ JobScheduleTest.class, JobTest.class, 
	ParkManagerTest.class, UserTest.class,
	AdminAbilitiesTest.class, PMAbilitiesTest.class,
	VolunteerAbilitiesTest.class, RulesHelpTest.class,
	UserListTest.class})
public class AllTests {

} 