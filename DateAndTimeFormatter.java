package com.headsup.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateAndTimeFormatter {

	private final String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec"};
	private final String SUNDAY = "Sun";
	private final String MONDAY = "Mon";
	private final String TUESDAY = "Tue";
	private final String WEDNESDAY = "Wed";
	private final String THURSDAY = "Thu";
	private final String FRIDAY = "Fri";
	private final String SATURDAY = "Sat";
	private final String AM = "am";
	private final String PM = "pm";	
	private final String DATE_FORMAT_PATTERN_TO_DISPLAY = "EEE MMM dd hh:mm a";
	private final String DATE_FORMAT_PATTERN_TO_SAVE = "MM/dd/yyyy hh:mm a";

	/**
	 * This function returns date in DayOfWeek Month DayOfMonth format. For ex :- Thu Sep 9
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return formattedDate
	 */
	public String getDateInDayMonthDateFormat(int year, int monthOfYear, int dayOfMonth){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, monthOfYear, dayOfMonth);

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)))
		.append(" ")
		.append(months[calendar.get(Calendar.MONTH)])
		.append(" ")
		.append(calendar.get(Calendar.DAY_OF_MONTH));

		return stringBuffer.toString();
	}


	private String getDayOfWeek(int calendarDayOfWeek) {
		String dayOfWeek = null;
		switch (calendarDayOfWeek) {

		case Calendar.SUNDAY:
			dayOfWeek = SUNDAY;
			break;

		case Calendar.MONDAY:
			dayOfWeek = MONDAY;
			break;

		case Calendar.TUESDAY:
			dayOfWeek = TUESDAY;
			break;

		case Calendar.WEDNESDAY:
			dayOfWeek = WEDNESDAY;
			break;

		case Calendar.THURSDAY:
			dayOfWeek = THURSDAY;
			break;

		case Calendar.FRIDAY:
			dayOfWeek = FRIDAY;
			break;

		case Calendar.SATURDAY:
			dayOfWeek = SATURDAY;
			break;
		}
		return dayOfWeek;
	}


	/**
	 * This function returns time in HH:MM am/pm format. For ex :- 8:00 am
	 * @param hourOfDay
	 * @param minute
	 * @return formattedTime
	 */
	public String getDateInHHMMFormat(int hourOfDay, int minute){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		StringBuffer stringBuffer = new StringBuffer();
		int hours = calendar.get(Calendar.HOUR);
		if(hours == 0){
			stringBuffer.append(12);
		}else if (hours < 10) {
			stringBuffer.append(0);
			stringBuffer.append(hours);
		}else {
			stringBuffer.append(hours);
		}
		stringBuffer.append(":");
		int minutes = calendar.get(Calendar.MINUTE);
		if (minutes < 10) {
			stringBuffer.append(0);
		}
		stringBuffer.append(minutes);
		stringBuffer.append(" ");
		stringBuffer.append(getAmPmFromHour(calendar.get(Calendar.AM_PM)));
		return stringBuffer.toString().toLowerCase();
	}

	private String getAmPmFromHour(int amPm) {
		if (amPm == Calendar.AM) {
			return AM;
		} else {
			return PM;
		}
	}

	/**
	 * This function returns date in MM/dd/yyyy h:mm a format
	 * @param hourOfDay
	 * @param minute
	 * @return formattedTime
	 */
	public String getDateinStringFormat(long longdatefromDB) {
		String strDate = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN_TO_DISPLAY,
				Locale.ENGLISH);
		Date date = new Date(longdatefromDB);
		try {
			strDate = dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}

	/**
	 * This function returns date in MM/dd/yyyy hh:mm a. For ex :- 03/27/2014 08:33 PM
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @param hourOfday
	 * @param min
	 * @return formattedDate
	 */
	public String getDateFormatToDisplay(int year, int monthOfYear, int dayOfMonth, int hourOfday, int min ){
		String formattedDate = "";
		@SuppressWarnings("deprecation")
		Date date = new Date((year-1900), monthOfYear,dayOfMonth,hourOfday,min);
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN_TO_SAVE);
		try {
			formattedDate = dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogUtils.LOGI("DateTimeFormatter", "Formatted Date : " + formattedDate);
		return formattedDate;
	}

	public static String getLongDate(String strDate) {
		DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat df3 = new SimpleDateFormat("EEE MMM dd");

		Date date = null;
		System.out.println("Trying 1st Format");
		try {
			date = df1.parse(strDate);
		} catch (Exception ex1) {
			ex1.printStackTrace();
			System.out.println("Trying 2nd Format");
			try {
				date = df2.parse(strDate);
			} catch (Exception ex2) {
				ex2.printStackTrace();
				System.out.println("Trying 3rd Format");
				try {
					date = df3.parse(strDate);
				} catch (Exception ex3) {
					ex3.printStackTrace();
				}
			}
		}

		return String.valueOf(date.getTime());
	}

	public static String getFullDate(String dateTimestamp) {
		System.out.println("Long date is " + dateTimestamp);
		DateFormat df = new SimpleDateFormat("EEE MMM dd");
		return df.format(new Date(Long.valueOf(dateTimestamp)));
	}

	public static String getTimeInHHMM(String dateTimestamp) {
		System.out.println("Long date is " + dateTimestamp);
		DateFormat df = new SimpleDateFormat("hh:mm a");
		return df.format(new Date(Long.valueOf(dateTimestamp)));
	}

	public static long getTimeInMillis(String dateTimestamp) {
		System.out.println("Long date is " + dateTimestamp);
		DateFormat df = new SimpleDateFormat("EEE MMM dd hh:mm a");
		Date date = null;
		try {
			date = df.parse(dateTimestamp);
		} catch (ParseException exception) {
			exception.printStackTrace();
		}
		return date.getTime();
	}

	@SuppressWarnings("deprecation")
	public static String getTime(String timeInHHMMSS){
		String TIME_FORMAT_TO_DISPLAY = "hh:mm a";
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_TO_DISPLAY);
		Date date = new Date();
		try {
			String[] timeValues = timeInHHMMSS.split(":");
			date.setHours(Integer.parseInt(timeValues[0]));
			date.setMinutes(Integer.parseInt(timeValues[1]));
			date.setSeconds(Integer.parseInt(timeValues[2]));
			return sdf.format(date);
		} catch (Exception e) {
			return timeInHHMMSS;
		}

	}
	
	public static String getFullDateForInjury(String dateTimestamp) {
		System.out.println("Long date is " + dateTimestamp);
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		try {
			Date date = df1.parse(dateTimestamp);
			DateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
			return df.format(new Date(Long.valueOf(date.getTime())));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateTimestamp;
		
	}
	
	/**
	 * Function to check if the current date is a future date or not.
	 * @param year 
	 * @param month
	 * @param day
	 * @return true is given date is of future else false.
	 */
	public boolean isAFutureData(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH);
		int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		if (year > currentYear) {
			return true;
		}else if (year == currentYear) {
			if (month > currentMonth) {
				return true;
			}else if (month == currentMonth) {
				if (day > currentDay) {
					return true;
				}
			}
		}
		return false;
	}
}
