package de.test.suites;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

import de.test.HelloWorldRestRequestExecutor;
import de.test.HelloWorldSoapRequestExecutor;
import de.test.util.TestDataProvider;

/**
 * These system tests depend on a running TomCat on which the services to test are deployed.
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
	public void returnsHelloForName() throws Exception 
	{
		// arrange
		final String soapTestName = "SoapSystemTest";
		final String restTestName = "RestSystemTest";
		
		// act
		final String soapResult = HelloWorldSoapRequestExecutor.sendSayHelloWordRequest(soapTestName);
		final String restResult = HelloWorldRestRequestExecutor.sendSayHelloWordRequest(restTestName);
		
		// assert
		assertEquals("result", "Hello " + soapTestName + "!", soapResult);
		assertEquals("result", "Hello " + restTestName + "!", restResult);
	}
	
	/**
	 * This is a integration test on system level,
	 * because the HelloWorldServices uses the
	 * SmallTalkCommentService for the small talk functionality. 
	 */
	@Test
	public void returnsSmallTalkCommentForNameWithoutDate() throws Exception 
	{
		// arrange
		final String soapTestName = "SoapSystemTest";
		final String restTestName = "RestSystemTest";
		
		// act
		final String soapResult = HelloWorldSoapRequestExecutor.sendStartSmallTalkRequest(soapTestName, null);
		final String restResult = HelloWorldRestRequestExecutor.sendStartSmallTalkRequest(restTestName, null);
		
		// assert
		assertEquals("result", "Hi SoapSystemTest, how are you?" + System.getProperty("line.separator") + 
                               "Nice day, this " + TestDataProvider.getDayOfWeek() + ", isn't it?" + System.getProperty("line.separator") +
                               "SoapSystemTest, do you know a prominent person of your name?", soapResult);
		assertEquals("result", "Hi RestSystemTest, how are you?" + System.getProperty("line.separator") + 
                               "Nice day, this " + TestDataProvider.getDayOfWeek() + ", isn't it?" + System.getProperty("line.separator") +
                               "RestSystemTest, do you know a prominent person of your name?", restResult);
	}

	/**
	 * This is a integration test on system level,
	 * because the HelloWorldServices uses the
	 * SmallTalkCommentService for the small talk functionality. 
	 */
	@Test
	public void returnsSmallTalkCommentForNameWithDate() throws Exception 
	{
		// arrange
		final String soapTestName = "SoapSystemTest";
		final String restTestName = "RestSystemTest";
		final DateTime dt = new DateTime();
		final Date d1 = dt.withHourOfDay(7).toDate();   // set a morning time
		final Date d2 = dt.withHourOfDay(17).toDate();  // set a afternoon time
		
		// act
		final String soapResult = HelloWorldSoapRequestExecutor.sendStartSmallTalkRequest(soapTestName, d1);
		final String restResult = HelloWorldRestRequestExecutor.sendStartSmallTalkRequest(restTestName, d2);
		
		// assert
		assertEquals("result", "Hi SoapSystemTest, how are you?" + System.getProperty("line.separator")
                             + "Good morning, have a good time on this " + TestDataProvider.getDayOfWeek() + "!" + System.getProperty("line.separator")
                             + "SoapSystemTest, do you know a prominent person of your name?", soapResult);
		assertEquals("result", "Hi RestSystemTest, how are you?" + System.getProperty("line.separator")
                             + "Good afternoon, have a good time on this " + TestDataProvider.getDayOfWeek() + "!" + System.getProperty("line.separator")
                             + "RestSystemTest, do you know a prominent person of your name?", restResult);
	}
	
}
