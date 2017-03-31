package de.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.test.HelloWorldServiceSoapWrapper;
import de.test.api.HelloWorld;
import de.test.api.autogen.SmallTalkCommentRequest;
import de.test.api.autogen.SmallTalkCommentResponse;
import de.test.util.TestDataProvider;

/**
 * This integration test depends on a running Tomcat where 
 * the current version of the SmallTalkCommentSoapService 
 * is deployed !
 * 
 * @author Reik Oberrath
 */
public class SmallTalkCommentIntegrationVariant1Test {
	
	private HelloWorld helloWorldService = new HelloWorldServiceSoapWrapper();
	private SmallTalkCommentRequest standardSmallTalkCommentRequest;
	
	@Before
	public void setup() {
		standardSmallTalkCommentRequest = TestDataProvider.createStandardSmallTalkCommentRequest();
	}
	
	@Test
	public void returnsSmallTalkCommentForNameWithoutDate() throws Exception {
		// arrange
		final String testName = "Integrationstest";
		standardSmallTalkCommentRequest.setName( testName );
		standardSmallTalkCommentRequest.setDate(null);
		
		// act
		final SmallTalkCommentResponse result = helloWorldService.startSmallTalk( standardSmallTalkCommentRequest );
		
		// assert
		assertEquals("result", "Hi Integrationstest, how are you?" + System.getProperty("line.separator")
				        + "Nice day, this " + TestDataProvider.getDayOfWeek() + ", isn't it?" + System.getProperty("line.separator")
				        + "Integrationstest, do you know a prominent person of your name?", result.getSmallTalkComment());
	}

}
