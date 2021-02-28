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
		boolean DateTimeValid;
	
		if (callbackDateandTime.isAfter(MaxDaysinFuture)) {
			System.out.println("Date is over 6 business days in the future");
			DateTimeValid = false;
		}
		else if (callbackDateandTime.isBefore(EarliestCallBackTime)) {
			DateTimeValid = false;
		}
		else if (isTimeValid(callbackDateandTime)) {
			DateTimeValid = true;
		} else {
			DateTimeValid = false;
		}
		return DateTimeValid;
	}

	private boolean isTimeValid(LocalDateTime callbackDateandTime) {
		boolean ValidTime;
		DayOfWeek dayToCheck = callbackDateandTime.getDayOfWeek();
		OpeningHours allowedHours = timeMatrix.get(dayToCheck);
		if (allowedHours == null) {
			ValidTime = false;
		} else if (isInWorkingHours(callbackDateandTime.toLocalTime(), allowedHours)) {
			ValidTime = true;
		} else {
			ValidTime = false;
		}
		return ValidTime;
	}

	private boolean isInWorkingHours(LocalTime callbackTime, OpeningHours allowedHours) {

		boolean InWorkingHours = false;
		if (callbackTime.equals(allowedHours.getOpeningTime()) || callbackTime.equals(allowedHours.getClosingTime())) {
			InWorkingHours = true;
		}
		if (callbackTime.isAfter(allowedHours.getOpeningTime()) && callbackTime.isBefore(allowedHours.closingTime)) {
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
