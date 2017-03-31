package de.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.test.HelloWorldServiceSoapWrapper;
import de.test.api.HelloWorld;
import de.test.api.autogen.SayHelloRequest;
import de.test.api.autogen.SayHelloResponse;
import de.test.util.TestDataProvider;

/**
 * This integration test does NOT depend on a running Tomcat 
 * because the service methods tested here do not use
 * the SmallTalkCommentSoapService.
 * 
 * @author Reik Oberrath
 */
public class SayHelloIntegrationTest {
	
	private HelloWorld helloWorldServiceSoapWrapper = new HelloWorldServiceSoapWrapper();
	private SayHelloRequest standardSayHelloRequest;
	
	@Before
	public void setup() {
		standardSayHelloRequest = TestDataProvider.createStandardSayHelloRequest();
	}
	
	@Test
	public void returnsHelloForName() throws Exception {
		// arrange
		final String testName = "Integrationstest";
		standardSayHelloRequest.setName( testName );
		
		// act
		final SayHelloResponse result = helloWorldServiceSoapWrapper.sayHello( standardSayHelloRequest );
		
		// assert
		assertEquals("result", "Hello Integrationstest!", result.getHelloText());
	}

	@Test
	public void throwsExceptionForMissingNameInSayHelloRequest() throws Exception {
		// arrange
		standardSayHelloRequest.setName( null );
		
		try {
			// act
			helloWorldServiceSoapWrapper.sayHello( standardSayHelloRequest );
			fail("Expected exception was not thrown!");
		} catch (Exception e) {
			// assert
			assertEquals("Error message", 
					     "java.lang.IllegalArgumentException: Name is missing.", 
					     e.getMessage());
		}
	}

}
