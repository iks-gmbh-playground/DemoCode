package com.iksgmbh.demo.rcp.buddyclassloading;

import java.lang.reflect.Method;

public class BuddyClass 
{
	public static String getHello() 
	{
		try {
			// Note:
			// The buddy class loading mechanism acts only during runtime.
			// At compile time the classes cannot be resolved by the IDE.
			// That's why they must be addressed via reflection!
			final Class<?> helloClass = Class.forName("com.iksgmbh.demo.rcp.buddyclassloading.hello.HelloClass");
			final Method getHelloMethod = helloClass.getMethod("getHello");
			final Object helloClassInstance = helloClass.newInstance();
			return (String) getHelloMethod.invoke(helloClassInstance);
		} catch (Exception e) {
			final String errorMessage = "An error occurred accessing the method getHello in the HelloClass via reflection: " 
		                                + System.getProperty("line.separator")
					                    + e.getMessage();
			System.err.println(errorMessage);
			return errorMessage;
		}
	}
	
	public static String getBuddy() {
		return "Buddy";
	}
}
