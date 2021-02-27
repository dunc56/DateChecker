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

	public Map<DayOfWeek, OpeningHours> timeMatrix;

	public DateCheckerService() {

		generateTimeMap();

	}

	public boolean isCallBackDateValid(LocalDateTime callbackDateandTime, LocalDateTime currentDateTime) {

		LocalDateTime MaxDaysinFuture = currentDateTime.plusDays(MAXDAYSINFUTURE);
		LocalDateTime EarliestCallBackTime = currentDateTime.plusHours(MAXHOURSAFTERNOW);
		boolean isDateTimeValid;
		// assumption that 6 working days in future counts day 1 as tomorrow
		// so any date over 7 days can be discarded as every week will have a non
		// working Sunday
		if (callbackDateandTime.isAfter(MaxDaysinFuture)) {
			System.out.println("Date is over 6 business days in the future");
			isDateTimeValid = false;
		}
		// check less than 2 hours
		else if (callbackDateandTime.isBefore(EarliestCallBackTime)) {
			isDateTimeValid = false;
		}
		else if (isTimeValid(callbackDateandTime)) {
			isDateTimeValid = true;
		} else {
			// out of hours
			isDateTimeValid = false;
		}
		return isDateTimeValid;
	}

	private boolean isTimeValid(LocalDateTime callbackDateandTime) {
		boolean isValidTime;
		DayOfWeek dayToCheck = callbackDateandTime.getDayOfWeek();
		OpeningHours allowedHours = timeMatrix.get(dayToCheck);

		// no hours for the given day - so call centre closed
		if (allowedHours == null) {
			isValidTime = false;
		} else if (isInWorkingHours(callbackDateandTime.toLocalTime(), allowedHours)) {
			isValidTime = true;
		} else {
			isValidTime = false;
		}
		return isValidTime;
	}

	private boolean isInWorkingHours(LocalTime callbackTime, OpeningHours allowedHours) {

		boolean isInWorkingHours = false;
		// need to check if exact match is allowed ?
		if (callbackTime.equals(allowedHours.getOpeningTime()) || callbackTime.equals(allowedHours.getClosingTime())) {
			isInWorkingHours = true;
		}

		if (callbackTime.isAfter(allowedHours.getOpeningTime()) && callbackTime.isBefore(allowedHours.closingTime)) {
			isInWorkingHours = true;
		}

		return isInWorkingHours;
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
