package de.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.test.api.HelloWorld;
import de.test.api.autogen.SmallTalkCommentRequest;
import de.test.api.autogen.SmallTalkCommentResponse;
import de.test.smalltalkcomment.SmallTalkCommentTestHelper;
import de.test.util.TestDataProvider;

/**
 * This integration test does NOT depend on a running Tomcat 
 * because the implementation of the SmallTalkCommentSoapService
 * is referenced from the source code and injected in the  
 * is HelloWorldServiceSoapWrapper !
 * 
 * @author Reik Oberrath
 */
public class SmallTalkCommentIntegrationVariant2Test {
	
	private HelloWorld helloWorldServiceSoapWrapper;
	private SmallTalkCommentRequest standardSmallTalkCommentRequest;
	private boolean firstTime = true;
	
	@Before
	public void setup() 
	{
		if (firstTime) {
			firstTime = false;
			helloWorldServiceSoapWrapper = createHelloWorldServiceSoapWrapper();
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

	@Test
	public void returnsSmallTalkCommentForNameWithoutDate() throws Exception {
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


}
