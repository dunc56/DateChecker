package com.datecheck;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class DateCheckerService implements DateChecker {

	public static final long MAXDAYSINFUTURE = 7l;
	public static final long MAXHOURSAFTERNOW = 2l;
	public static final String MondayOpen = "09:00";
	public static final String MondayClose = "18:00";
	public static final String TuesdayOpen = "09:00";
	public static final String TuesdayClose = "18:00";
	public static final String WednesdayOpen = "09:00";
	public static final String WednesdayClose = "18:00";
	public static final String ThursdayOpen = "09:00";
	public static final String ThursdayClose = "20:00";
	public static final String FridayOpen = "09:00";
	public static final String FridayClose = "20:00";
	public static final String SaturdayOpen = "09:00";
	public static final String SaturdayClose = "12:30";
	private static final OpeningHours NO_WORKING_HOURS = null;
	
	public Map<DayOfWeek, OpeningHours> timeMatrix;

	public DateCheckerService() {

		generateTimeMap();

	}

	public boolean isCallBackDateValid(LocalDateTime callbackDateandTime, LocalDateTime currentDateTime) {
		boolean dateTimeValid;
		if (isWithinRules(currentDateTime, callbackDateandTime)) {
			dateTimeValid = true;
		} else {
			dateTimeValid = false;
		}
		return dateTimeValid;
	}

	private boolean isTimeValid(LocalDateTime callbackDateandTime) {
		boolean ValidTime;
		DayOfWeek dayToCheck = callbackDateandTime.getDayOfWeek();
		OpeningHours allowedHours = timeMatrix.get(dayToCheck);
		if (allowedHours == NO_WORKING_HOURS) {
			ValidTime = false;
		} else if (isInWorkingHours(callbackDateandTime.toLocalTime(), allowedHours)) {
			ValidTime = true;
		} else {
			ValidTime = false;
		}
		return ValidTime;
	}

	private boolean isWithinRules(LocalDateTime currentDateTime, LocalDateTime callbackDateandTime) {
		LocalDateTime MaxDaysinFuture = currentDateTime.plusDays(MAXDAYSINFUTURE);
		LocalDateTime EarliestCallBackTime = currentDateTime.plusHours(MAXHOURSAFTERNOW);
		return !callbackDateandTime.isAfter(MaxDaysinFuture) && !callbackDateandTime.isBefore(EarliestCallBackTime)
				&& isTimeValid(callbackDateandTime);
	}

	private boolean isInWorkingHours(LocalTime callbackTime, OpeningHours allowedHours) {
		boolean InWorkingHours = false;

		if (!callbackTime.isBefore(allowedHours.getOpeningTime()) && !callbackTime.isAfter(allowedHours.closingTime)) {
			InWorkingHours = true;
		}
		return InWorkingHours;
	}

	private void generateTimeMap() {
		timeMatrix = new HashMap<DayOfWeek, OpeningHours>();
		OpeningHours mondayHours = new OpeningHours(LocalTime.parse(MondayOpen), LocalTime.parse(MondayClose));
		OpeningHours tuesdayHours = new OpeningHours(LocalTime.parse(TuesdayOpen), LocalTime.parse(TuesdayClose));
		OpeningHours wednesdayHours = new OpeningHours(LocalTime.parse(WednesdayOpen), LocalTime.parse(WednesdayClose));
		OpeningHours thursdayHours = new OpeningHours(LocalTime.parse(ThursdayOpen), LocalTime.parse(ThursdayClose));
		OpeningHours fridayHours = new OpeningHours(LocalTime.parse(FridayOpen), LocalTime.parse(FridayClose));
		OpeningHours saturdayHours = new OpeningHours(LocalTime.parse(SaturdayOpen), LocalTime.parse(SaturdayClose));
		timeMatrix.put(DayOfWeek.MONDAY, mondayHours);
		timeMatrix.put(DayOfWeek.TUESDAY, tuesdayHours);
		timeMatrix.put(DayOfWeek.WEDNESDAY, wednesdayHours);
		timeMatrix.put(DayOfWeek.THURSDAY, thursdayHours);
		timeMatrix.put(DayOfWeek.FRIDAY, fridayHours);
		timeMatrix.put(DayOfWeek.SATURDAY, saturdayHours);
	}

	class OpeningHours {
	
		private LocalTime openingTime;
		private LocalTime closingTime;

		public OpeningHours(LocalTime openingTime, LocalTime closingTime) {
			super();
			this.openingTime = openingTime;
			this.closingTime = closingTime;
		}

		public LocalTime getOpeningTime() {
			return openingTime;
		}

		public void setOpeningTime(LocalTime openingTime) {
			this.openingTime = openingTime;
		}

		public LocalTime getClosingTime() {
			return closingTime;
		}

		public void setClosingTime(LocalTime closingTime) {
			this.closingTime = closingTime;
		}

	}

}
