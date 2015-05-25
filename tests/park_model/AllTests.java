package park_model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ JobScheduleTest.class, JobTest.class, 
	ParkManagerTest.class, UserTest.class,
	AdminAbilitiesTest.class, PMAbilitiesTest.class,
	VolunteerAbilitiesTest.class, RulesHelpTest.class,
	UserListTest.class})
public class AllTests {

} 