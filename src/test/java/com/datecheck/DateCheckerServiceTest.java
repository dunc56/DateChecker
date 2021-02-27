package com.datecheck;



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.Before;
import org.junit.Test;


public class DateCheckerServiceTest {
	//current date of 9:10am 2Jan 2021 -which is a Monday
	private static final LocalDateTime CURRENT_DATE_TIME = LocalDateTime.of(2021, 1, 2, 9, 10, 0);
	private static final LocalDateTime OVER_SEVEN_DAYS = CURRENT_DATE_TIME.plusDays(DateCheckerService.MAXDAYSINFUTURE).plusMinutes(1);
	private static final LocalDateTime LESS_THAN_TWO_HOURS = CURRENT_DATE_TIME.plusHours(DateCheckerService.MAXHOURSAFTERNOW).minusMinutes(1);
	private static final LocalDateTime NEXT_SUNDAY = CURRENT_DATE_TIME.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
	private static final LocalDateTime WITHIN_WORKING_HOURS_SATURDAY = CURRENT_DATE_TIME.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).withHour(11).withMinute(29);
	private static final LocalDateTime BEFORE_WORKING_HOURS_SATURDAY = CURRENT_DATE_TIME.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).withHour(6);
	private static final LocalDateTime AFTER_WORKING_HOURS_SATURDAY = CURRENT_DATE_TIME.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).withHour(12).withMinute(31);
	private static final LocalDateTime WITHIN_WORKING_HOURS_WEDNESDAY = CURRENT_DATE_TIME.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY)).withHour(12).withMinute(30);
	private static final LocalDateTime NINE_AM_EXACTLY_WITHIN_WORKING_HOURS_WEDNESDAY = CURRENT_DATE_TIME.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY)).withHour(9).withMinute(0).withSecond(0).withNano(0);
	private static final LocalDateTime AFTER_WORKING_HOURS_WEDNESDAY = CURRENT_DATE_TIME.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY)).withHour(18).withMinute(1);


	private DateCheckerService dateCheckerService;
	
	@Before
	public void setup() {
		dateCheckerService  = new DateCheckerService();
	}
	
	@Test
	public void givenCheckerService_whenCallBackIsMoreThanSevenDays_thenTheDateIsInvalid() {
		assertFalse(dateCheckerService.isCallBackDateValid(OVER_SEVEN_DAYS, CURRENT_DATE_TIME));
	}
	
	@Test
	public void givenCheckerService_whenCallBackIsLessThanTwoHours_thenTheDateIsInvalid() {
		assertFalse(dateCheckerService.isCallBackDateValid(LESS_THAN_TWO_HOURS, CURRENT_DATE_TIME));
	}
	
	@Test
	public void givenCheckerService_whenCallBackIsOnSunday_thenTheDateIsInvalid() {
		assertFalse(dateCheckerService.isCallBackDateValid(NEXT_SUNDAY, CURRENT_DATE_TIME));
	}

	@Test
	public void givenCheckerService_whenCallBackIsWithinWorkingHoursOnSaturday_thenTheDateIsValid() {
		assertTrue(dateCheckerService.isCallBackDateValid(WITHIN_WORKING_HOURS_SATURDAY, CURRENT_DATE_TIME));
	}
	
	@Test
	public void givenCheckerService_whenCallBackIsBeforeWorkingHoursOnSaturday_thenTheDateIsInValid() {
		assertFalse(dateCheckerService.isCallBackDateValid(BEFORE_WORKING_HOURS_SATURDAY, CURRENT_DATE_TIME));
	}
	
	@Test
	public void givenCheckerService_whenCallBackIsAfterWorkingHoursOnSaturday_thenTheDateIsInValid() {
		assertFalse(dateCheckerService.isCallBackDateValid(AFTER_WORKING_HOURS_SATURDAY, CURRENT_DATE_TIME));
	}
	
	@Test
	public void givenCheckerService_whenCallBackIsWithinWorkingHoursOnWednesday_thenTheDateIsValid() {
		assertTrue(dateCheckerService.isCallBackDateValid(WITHIN_WORKING_HOURS_WEDNESDAY, CURRENT_DATE_TIME));
	}
	
	@Test
	public void givenCheckerService_whenCallBackIsNineAmExactlyWithinWorkingHoursOnWednesday_thenTheDateIsValid() {
		assertTrue(dateCheckerService.isCallBackDateValid(NINE_AM_EXACTLY_WITHIN_WORKING_HOURS_WEDNESDAY, CURRENT_DATE_TIME));
	}
	
	@Test
	public void givenCheckerService_whenCallBackIsAfterWorkingHoursOnWednesday_thenTheDateIsInValid() {
		assertFalse(dateCheckerService.isCallBackDateValid(AFTER_WORKING_HOURS_WEDNESDAY, CURRENT_DATE_TIME));
	}

	
}
