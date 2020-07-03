package com.iksgmbh.ohocamel.backend.utils;

public class Random 
{
	public static String oneOf(String... elements) 
	{
		int randomInt = new java.util.Random().nextInt(elements.length);
		return elements[randomInt];
	}
	
	public static int oneOf(int... elements) 
	{
		int randomInt = new java.util.Random().nextInt(elements.length);
		return elements[randomInt];
	}	
}
