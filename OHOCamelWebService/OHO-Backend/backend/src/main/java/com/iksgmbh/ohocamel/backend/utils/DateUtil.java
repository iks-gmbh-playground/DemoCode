package com.iksgmbh.ohocamel.backend.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class DateUtil 
{
	private static final DateFormat DATE_FORMAT_DE = new SimpleDateFormat("dd.MM.yyyy");
	private static final DateFormat DATE_FORMAT_EN = new SimpleDateFormat("yyyy.MM.dd");
			
	public static String todayAsString() {
		return DATE_FORMAT_EN.format(new Date());
	}
	
	public static boolean isToday(String aDay) {
		boolean ok = todayAsString().equals(aDay);
		if (! ok) ok = DATE_FORMAT_DE.format(new Date()).equals(aDay);
		return ok;
	}
	
	public static int getAge(String birthday) {
		return getYearNow() - getYearFromDateString(birthday);
	}

	public static int getYearFromDateString(String dateAsString) 
	{
		if (isDateFormatEnglish(dateAsString)) 
		{
			int pos = dateAsString.indexOf(".");
			String year = dateAsString.substring(0, pos);
			return Integer.valueOf(year);
		}
		
		int pos = dateAsString.lastIndexOf(".");
		String year = dateAsString.substring(pos+1);
		return Integer.valueOf(year);
	}

	public static int getYearNow() {
		return new GregorianCalendar().get(Calendar.YEAR);
	}

	public static boolean isDateValid(String dateAsString) 
	{
		List<Character> chars = dateAsString.chars().mapToObj(e->(char)e).collect(Collectors.toList());
		Optional<Character> match = chars.stream()
				            .filter(c -> ! Character.isDigit(c))
		                    .filter(c -> c != '.')
		                    .findAny();
		if (match.isPresent()) return false;
		
		try {
			DATE_FORMAT_DE.parse(dateAsString);
			return true;
		} catch (ParseException e) {
			// do nothing;
		}
		
		try {
			DATE_FORMAT_EN.parse(dateAsString);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	public static boolean isDateFormatEnglish(String dateAsString) 
	{
		try {
			DATE_FORMAT_DE.parse(dateAsString);
			return false;
		} catch (ParseException e) {
			// do nothing;
		}
		
		try {
			DATE_FORMAT_EN.parse(dateAsString);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
}
