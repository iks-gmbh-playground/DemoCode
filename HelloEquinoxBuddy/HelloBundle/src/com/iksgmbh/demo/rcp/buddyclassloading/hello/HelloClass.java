package com.iksgmbh.demo.rcp.buddyclassloading.hello;

import com.iksgmbh.demo.rcp.buddyclassloading.BuddyClass;

public class HelloClass 
{
	public static String getHello() {
		return "Hello";
	}
	
	public static String getBuddy() {
		return BuddyClass.getBuddy();
	}
	
}
