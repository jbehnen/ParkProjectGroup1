package model_tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import config_files.Config;
import park_model.Job;
import park_model.RulesHelp;

/**
 * Tests the RulesHelp class.
 * 
 * @author Julia Behnen
 * @version 5/16/2015
 */
public class RulesHelpTest {
	
	private GregorianCalendar today;
	private GregorianCalendar firstDate;
	private GregorianCalendar lastDate;

	@Before
	public void setUp() throws Exception {
		today = RulesHelp.getTodaysDate();
		firstDate = new GregorianCalendar(2015, 2, 8);
		lastDate = new GregorianCalendar(2015, 2, 17);
	}
	
	@Test
	public void getTodaysDateShouldReturnMidnightOfThisMorning() {
		GregorianCalendar comparisionToday = new GregorianCalendar();
		assertEquals("Year is correct", comparisionToday.get(Calendar.YEAR),
				today.get(Calendar.YEAR));
		assertEquals("Month is correct", comparisionToday.get(Calendar.MONTH),
				today.get(Calendar.MONTH));
		assertEquals("Date is correct", comparisionToday.get(Calendar.DATE),
				today.get(Calendar.DATE));
		assertEquals("Hour is 0", 0, today.get(Calendar.HOUR_OF_DAY));
		assertEquals("Minute is 0", 0, today.get(Calendar.MINUTE));
		assertEquals("Second is 0", 0, today.get(Calendar.SECOND));
		assertEquals("Millisecond is 0", 0, today.get(Calendar.MILLISECOND));
	}

	@Test
	public void testIsDateInPastShouldReturnFalseIfToday() {
		assertFalse("Today is not in the past", RulesHelp.isDateInPast(today));
	}
	
	@Test
	public void testIsDateInPastShouldReturnFalseIfTomorrow() {
		GregorianCalendar tomorrow = today;
		tomorrow.add(Calendar.DATE, 1);
		assertFalse("Tomorrow is not in the past", RulesHelp.isDateInPast(tomorrow));
	}
	
	@Test
	public void testIsDateInPastShouldReturnFalseIfTenDaysInFuture() {
		GregorianCalendar tenDaysInFuture = today;
		tenDaysInFuture.add(Calendar.DATE, 10);
		assertFalse("Tomorrow is not in the past", RulesHelp.isDateInPast(tenDaysInFuture));
	}
	
	@Test
	public void testIsDateInPastShouldReturnTrueIfYesterday() {
		GregorianCalendar yesterday = today;
		yesterday.add(Calendar.DATE, -1);
		assertTrue("Yesterday is in the past", RulesHelp.isDateInPast(yesterday));
	}
	
	@Test
	public void testIsDateInPastShouldReturnTrueIfTenDaysAgo() {
		GregorianCalendar tenDaysAgo = today;
		tenDaysAgo.add(Calendar.DATE, -10);
		assertTrue("Yesterday is in the past", RulesHelp.isDateInPast(tenDaysAgo));
	}
	
	@Test
	public void testIsDateTooFarInFutureShouldReturnFalseIfOnLastAllowedDay() {
		GregorianCalendar lastAllowedDay = today;
		lastAllowedDay.add(Calendar.DATE, Config.MAX_DAYS_IN_FUTURE);
		boolean check = RulesHelp.isDateTooFarInFuture(lastAllowedDay);
		assertFalse("Job is within allowed time frame", check);
	}
	
	@Test
	public void testIsDateTooFarInFutureShouldReturnTrueIfAfterLastLegalDay() {
		GregorianCalendar dayAfterLastAllowedDay = today;
		dayAfterLastAllowedDay.add(Calendar.DATE, Config.MAX_DAYS_IN_FUTURE + 1);
		boolean check = RulesHelp.isDateTooFarInFuture(dayAfterLastAllowedDay);
		assertTrue("Job is after allowed time frame", check);
	}
	
	@Test
	public void testIsDateInRangeShouldReturnFalseIfDateBeforeFirstDate() {
		GregorianCalendar dayBeforeFirstDayInRange = new GregorianCalendar(2015, 2, 7);
		assertFalse("Date before first date", 
				RulesHelp.isDateInRange(dayBeforeFirstDayInRange, firstDate, lastDate));
	}
	
	@Test
	public void testIsDateInRangeShouldReturnTrueIfDateIsFirstDate() {
		GregorianCalendar firstDayInRange = (GregorianCalendar) firstDate.clone();
		assertTrue("Date is first date", 
				RulesHelp.isDateInRange(firstDayInRange, firstDate, lastDate));
	}
	
	@Test
	public void testIsDateInRangeShouldReturnTrueIfDateBetweenFirstAndLastDate() {
		GregorianCalendar dayInMiddleOfRange = new GregorianCalendar(2015, 2, 14);
		assertTrue("Date is between first and last date", 
				RulesHelp.isDateInRange(dayInMiddleOfRange, firstDate, lastDate));
	}
	
	@Test
	public void testIsDateInRangeShouldReturnTrueIfDateIsLastDate() {
		GregorianCalendar lastDayInRange = (GregorianCalendar) lastDate.clone();
		assertTrue("Date is last date", 
				RulesHelp.isDateInRange(lastDayInRange, firstDate, lastDate));
	}
	
	@Test
	public void testIsDateInRangeShouldReturnFalseIfDateAfterLastDate() {
		GregorianCalendar dayAfterLastDayInRange = new GregorianCalendar(2015, 2, 18);
		assertFalse("Date after last date", 
				RulesHelp.isDateInRange(dayAfterLastDayInRange, firstDate, lastDate));
	}
	
	@Test
	public void testIsJobInRangeShouldReturnFalseIfJobJustBeforeRange() {
		GregorianCalendar twoDaysBeforeRange = new GregorianCalendar(2015, 2, 6);
		Job jobBeforeRange = new Job("King Park", 
				twoDaysBeforeRange, 2, 1, 1, 1, "Description");
		assertFalse("Job not in range", 
				RulesHelp.isJobInRange(jobBeforeRange, firstDate, lastDate));
	}
	
	@Test
	public void testIsJobInRangeShouldReturnTrueIfJobOverlapsStartOfRange() {
		GregorianCalendar dayBeforeRange = new GregorianCalendar(2015, 2, 7);
		Job jobOverlappingStartOfRange = new Job("King Park", 
				dayBeforeRange, 2, 1, 1, 1, "Description");
		assertTrue("Job overlaps start of range range", 
				RulesHelp.isJobInRange(jobOverlappingStartOfRange, firstDate, lastDate));
	}
	
	@Test
	public void testIsJobInRangeShouldReturnTrueIfJobWithinRange() {
		GregorianCalendar dayInRange = new GregorianCalendar(2015, 2, 10);
		Job jobWithinRange = new Job("King Park", 
				dayInRange, 2, 1, 1, 1, "Description");
		assertTrue("Job overlaps start of range range", 
				RulesHelp.isJobInRange(jobWithinRange, firstDate, lastDate));
	}
	
	@Test
	public void testIsJobInRangeShouldReturnTrueIfJobOverlapsEndOfRange() {
		GregorianCalendar dayAtEndOfRange = new GregorianCalendar(2015, 2, 17);
		Job jobOverlappingEndOfRange = new Job("King Park", 
				dayAtEndOfRange, 2, 1, 1, 1, "Description");
		assertTrue("Job overlaps start of range range", 
				RulesHelp.isJobInRange(jobOverlappingEndOfRange, firstDate, lastDate));
	}
	
	@Test
	public void testIsJobInRangeShouldReturnFalseIfJobJustAfterRange() {
		GregorianCalendar dayAfterRange = new GregorianCalendar(2015, 2, 18);
		Job jobAfterRange = new Job("King Park", 
				dayAfterRange, 2, 1, 1, 1, "Description");
		assertFalse("Job not in range", 
				RulesHelp.isJobInRange(jobAfterRange, firstDate, lastDate));
	}

}
