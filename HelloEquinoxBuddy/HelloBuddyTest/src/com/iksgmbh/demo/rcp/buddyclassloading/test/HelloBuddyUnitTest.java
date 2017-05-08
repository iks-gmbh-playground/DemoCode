package com.iksgmbh.demo.rcp.buddyclassloading.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.iksgmbh.demo.rcp.buddyclassloading.BuddyClass;
import com.iksgmbh.demo.rcp.buddyclassloading.hello.HelloClass;

public class HelloBuddyUnitTest 
{
	@Test
	public void getGelloBuddyStringFromHelloBundle() {
		final String result = HelloClass.getHello() + " " + HelloClass.getBuddy() + "!";
		assertEquals("result", "Hello Buddy!", result);
	}
	
	@Test
	public void getGelloBuddyStringFromBuddyBundle() {
		final String result = BuddyClass.getHello() + " " + BuddyClass.getBuddy() + "!";
		assertEquals("result", "Hello Buddy!", result);
	}
	
}
