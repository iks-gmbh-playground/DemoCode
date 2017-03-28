package de.test.util;

public class CommentStringUtils {

	public static String appendStrings(String... stringArray) 
	{
		if (stringArray == null) return null;
		if (stringArray.length == 0) return null;
		
		String toReturn = "";
		for (String s : stringArray) {
			if (s != null)
				toReturn += s;
		}
		
		return toReturn;
	}

}
