package com.iksgmbh.demo.hoo.order.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateStringUtil 
{
	public static int calculateAgeFromYearOfDate(String birthdayOfTargetPerson) 
	{
		if (birthdayOfTargetPerson == null) 
			throw new RuntimeException("No birthday of target person provided.");
		
		
		String[] splitResult = birthdayOfTargetPerson.split("\\.");
		if (splitResult.length != 3)
			throw new RuntimeException("Unparsable birthday of target person: " + birthdayOfTargetPerson);
		
		int yearOfBirth = 0;
		String yearOfBirthAsString = splitResult[2];
		
		try {
			yearOfBirth = Integer.valueOf(yearOfBirthAsString);
		} catch (Exception e) {
			throw new RuntimeException("'" + yearOfBirthAsString + "' is no valid year of birth.");
		}

		int currentYear = new GregorianCalendar().get(Calendar.YEAR);
		int toReturn = currentYear - yearOfBirth;
		if (toReturn < 0) 
			throw new RuntimeException("'" + yearOfBirthAsString + "' is a invalid future date.");
		
		if (toReturn >  130) 
			throw new RuntimeException("Target person too old (" + toReturn + "). No human does live that long.");
		
		return toReturn;
	}
}
