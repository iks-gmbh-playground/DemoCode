package de.test.suites;

import static org.junit.Assert.*;

import org.junit.Test;

import de.test.HelloWorldSoapRequestExecutor;
import de.test.util.TestDataProvider;

/**
 * These system tests depend on TomCat on which the 
 * services to test are deployed.
 * One test methods represend a unit, another a integration test. 
 * 
 * @author Reik Oberrath
 */
public class SystemLevelTests {

	/**
	 * This is a unit test on system level,
	 * because no other service is needed
	 * for the hello functionality. 
	 */
	@Test
	public void returnsHelloForName() throws Exception {
		// arrange
		final String testName = "SystemTest";
		
		// act
		final String result = HelloWorldSoapRequestExecutor.sendSayHelloWordRequest(testName);
		
		// assert
		assertEquals("result", "Hello SystemTest!", result);
	}
	
	/**
	 * This is a integration test on system level,
	 * because the HelloWorldServices uses the
	 * SmallTalkCommentService for the small talk functionality. 
	 */
	@Test
	public void returnsSmallTalkCommentForNameWithoutDate() throws Exception {
		// arrange
		final String testName = "SystemTest";
		
		// act
		final String result = HelloWorldSoapRequestExecutor.sendStartSmallTalkRequest(testName, null);
		
		// assert
		assertEquals("result", "Hi SystemTest, how are you?" + System.getProperty("line.separator") + 
                               "Nice day, this " + TestDataProvider.getDayOfWeek() + ", isn't it?" + System.getProperty("line.separator") +
                               "SystemTest, do you know a prominent person of your name?", result);
	}
	
}
