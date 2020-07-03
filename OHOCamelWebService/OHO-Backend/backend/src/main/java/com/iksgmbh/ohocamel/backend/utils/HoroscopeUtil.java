package com.iksgmbh.ohocamel.backend.utils;

import com.iksgmbh.oho.backend.HoroscopeRequestData;

public class HoroscopeUtil 
{
	public static String getPersonType(HoroscopeRequestData requestData) 
	{
		int yearPerson = DateUtil.getYearFromDateString(requestData.getBirthday());
		int age = DateUtil.getYearNow() - yearPerson;
		String gender = requestData.getGender();

		if ("f".equals(gender)) {
			if (age < 14) return "girl";
			if (age < 25) return "lass";
			return "woman";
		} 
		
		if ("m".equals(gender)) {
			if (age < 14) return "boy";
			if (age < 25) return "lad";
			return "man";
		} 
		
		if (age < 13) return "child";
		if (age < 20) return "teenager";
		return "person";
		
	}
}
