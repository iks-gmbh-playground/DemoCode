package de.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import de.test.api.HelloWorld;
import de.test.api.autogen.SayHelloRequest;
import de.test.api.autogen.SayHelloResponse;
import de.test.util.TestDataProvider;

/**
 * This integration test does NOT depend on a running Tomcat 
 * because the service methods tested here are called by class references (not by URI call)
 * and do not use the SmallTalkCommentSoapService.
 * 
 * @author Reik Oberrath
 */
public class SayHelloIntegrationTest {
	
	private HelloWorld helloWorldServiceSoapWrapper = new HelloWorldServiceSoapWrapper();
	private HelloWorld helloWorldServiceRestWrapper = new HelloWorldServiceRESTWrapper(); 
	private SayHelloRequest standardSayHelloRequest = TestDataProvider.createStandardSayHelloRequest();
	
	@Test
	public void returnsHelloForName() throws Exception {
		// arrange
		standardSayHelloRequest.setName( "SoapIntegrationstest" );
		
		// act
		final SayHelloResponse soapResult = helloWorldServiceSoapWrapper.sayHello( standardSayHelloRequest);
		standardSayHelloRequest.setName( "RestIntegrationstest" );
		final SayHelloResponse restResult = helloWorldServiceRestWrapper.sayHello( standardSayHelloRequest );
		
		// assert
		assertEquals("result", "Hello SoapIntegrationstest!", soapResult.getHelloText());
		assertEquals("result", "Hello RestIntegrationstest!", restResult.getHelloText());
	}

	@Test
	public void throwsExceptionForMissingNameInSoapSayHelloRequest() throws Exception {
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

	@Test
	public void throwsExceptionForMissingNameInRestSayHelloRequest() throws Exception {
		// arrange
		standardSayHelloRequest.setName( null );
		
		try {
			// act
			helloWorldServiceRestWrapper.sayHello( standardSayHelloRequest );
			fail("Expected exception was not thrown!");
		} catch (Exception e) {
			// assert
			assertEquals("Error message", 
					     "java.lang.IllegalArgumentException: Name is missing.", 
					     e.getMessage());
		}
	}
	
}
