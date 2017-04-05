package de.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import de.test.api.HelloWorld;
import de.test.api.autogen.SmallTalkCommentRequest;
import de.test.api.autogen.SmallTalkCommentResponse;
import de.test.api.utils.XMLGregorianCalendarUtil;
import de.test.smalltalkcomment.SmallTalkCommentTestHelper;
import de.test.util.TestDataProvider;

/**
 * This integration test does NOT depend on a running Tomcat 
 * because the service methods of HelloWorldServices are
 * called by class reference and the URI call of the SmallTalkCommentSoapService
 * is replaced by class reference call. This replacement is possible by
 * an injection of SmallTalkCommentTestHelper into the HelloWorldServiceImpl !
 * 
 * @author Reik Oberrath
 */
public class SmallTalkCommentIntegrationVariant2Test {
	
	private HelloWorld helloWorldServiceSoapWrapper;
	private HelloWorld helloWorldServiceRestWrapper;
	private SmallTalkCommentRequest standardSmallTalkCommentRequest;
	private boolean firstTime = true;
	
	@Before
	public void setup() 
	{
		if (firstTime) {
			firstTime = false;
			helloWorldServiceSoapWrapper = createHelloWorldServiceSoapWrapper();
			helloWorldServiceRestWrapper = createHelloWorldServiceRestWrapper();
		}
		standardSmallTalkCommentRequest = TestDataProvider.createStandardSmallTalkCommentRequest();
	}

	/**
	 * Creates the fake to short cut the webservice call and address its 
	 * business logic directly.
	 */
	private HelloWorld createHelloWorldServiceSoapWrapper() 
	{
		HelloWorldServiceSoapWrapper toReturn = new HelloWorldServiceSoapWrapper();
		
		/* This injection of the TestWrapper is possible, because the variable
		 * smallTalkCommentHelper in HelloWorldService is package friendly!!! */
		toReturn.helloWorldService.smallTalkCommentHelper = new SmallTalkCommentTestHelper();
		
		return toReturn;
	}
	
	/**
	 * Creates the fake to short cut the webservice call and address its 
	 * business logic directly.
	 */
	private HelloWorld createHelloWorldServiceRestWrapper() 
	{
		HelloWorldServiceRESTWrapper toReturn = new HelloWorldServiceRESTWrapper();
		
		/* This injection of the TestWrapper is possible, because the variable
		 * smallTalkCommentHelper in HelloWorldService is package friendly!!! */
		toReturn.helloWorldService.smallTalkCommentHelper = new SmallTalkCommentTestHelper();
		
		return toReturn;
	}
	

	@Test
	public void returnsSmallTalkCommentOnlyForNameFromSoapService() throws Exception {
		// arrange
		final String testName = "Integrationstest";
		standardSmallTalkCommentRequest.setName( testName );
		standardSmallTalkCommentRequest.setDate(null);
		
		// act
		final SmallTalkCommentResponse result = helloWorldServiceSoapWrapper.startSmallTalk( standardSmallTalkCommentRequest );
		
		// assert
		assertEquals("result", "Hi Integrationstest, how are you?" + System.getProperty("line.separator")
				               + "Nice day, this " + TestDataProvider.getDayOfWeek() + ", isn't it?" + System.getProperty("line.separator")
				               + "Integrationstest, do you know a prominent person of your name?", result.getSmallTalkComment());
	}

	@Test
	public void returnsSmallTalkCommentForNameAndDateFromRestService() throws Exception {
		// arrange
		final String testName = "Integrationstest";
		standardSmallTalkCommentRequest.setName( testName );
		final DateTime dt = new DateTime();
		final Date d = dt.withHourOfDay(7).toDate();  // set a morning time
		standardSmallTalkCommentRequest.setDate( XMLGregorianCalendarUtil.toGregorianCaldendar(d));
		
		// act
		final SmallTalkCommentResponse result = helloWorldServiceRestWrapper.startSmallTalk( standardSmallTalkCommentRequest );
		
		// assert
		assertEquals("result", "Hi Integrationstest, how are you?" + System.getProperty("line.separator")
                               + "Good morning, have a good time on this " + TestDataProvider.getDayOfWeek() + "!" + System.getProperty("line.separator")
				               + "Integrationstest, do you know a prominent person of your name?", result.getSmallTalkComment());
	}

}
