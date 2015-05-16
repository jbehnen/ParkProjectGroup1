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
	public void testIsDateInPastShouldReturnTrueIfYesterday() {
		GregorianCalendar yesterday = today;
		yesterday.add(Calendar.DATE, -1);
		assertTrue("Yesterday is in the past", RulesHelp.isDateInPast(yesterday));
	}
	
	@Test
	public void testIsDateTooFarInFutureShouldReturnFalseIfOnLastLegalDay() {
		GregorianCalendar futureDate = today;
		futureDate.add(Calendar.DATE, Config.MAX_DAYS_IN_FUTURE);
		boolean check = RulesHelp.isDateTooFarInFuture(futureDate);
		assertFalse("Job is within allowed time frame", check);
	}
	
	@Test
	public void testIsDateTooFarInFutureShouldReturnTrueIfAfterLastLegalDay() {
		GregorianCalendar futureDate = today;
		futureDate.add(Calendar.DATE, Config.MAX_DAYS_IN_FUTURE + 1);
		boolean check = RulesHelp.isDateTooFarInFuture(futureDate);
		assertTrue("Job is after allowed time frame", check);
	}
	
	@Test
	public void testIsDateInRangeShouldReturnFalseIfDateBeforeFirstDate() {
		GregorianCalendar testDate = new GregorianCalendar(2015, 2, 7);
		assertFalse("Date before first date", 
				RulesHelp.isDateInRange(testDate, firstDate, lastDate));
	}
	
	@Test
	public void testIsDateInRangeShouldReturnTrueIfDateIsFirstDate() {
		GregorianCalendar testDate = new GregorianCalendar(2015, 2, 8);
		assertTrue("Date is first date", 
				RulesHelp.isDateInRange(testDate, firstDate, lastDate));
	}
	
	@Test
	public void testIsDateInRangeShouldReturnTrueIfDateBetweenFirstAndLastDate() {
		GregorianCalendar testDate = new GregorianCalendar(2015, 2, 14);
		assertTrue("Date is between first and last date", 
				RulesHelp.isDateInRange(testDate, firstDate, lastDate));
	}
	
	@Test
	public void testIsDateInRangeShouldReturnTrueIfDateIsLastDate() {
		GregorianCalendar testDate = new GregorianCalendar(2015, 2, 17);
		assertTrue("Date is last date", 
				RulesHelp.isDateInRange(testDate, firstDate, lastDate));
	}
	
	@Test
	public void testIsDateInRangeShouldReturnFalseIfDateAfterLastDate() {
		GregorianCalendar testDate = new GregorianCalendar(2015, 2, 18);
		assertFalse("Date after last date", 
				RulesHelp.isDateInRange(testDate, firstDate, lastDate));
	}

}
