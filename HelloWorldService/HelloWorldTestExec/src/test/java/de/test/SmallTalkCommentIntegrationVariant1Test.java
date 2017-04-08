package de.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

import de.test.api.HelloWorld;
import de.test.api.autogen.SmallTalkCommentRequest;
import de.test.api.autogen.SmallTalkCommentResponse;
import de.test.api.utils.XMLGregorianCalendarUtil;
import de.test.util.TestDataProvider;

/**
 * This integration test depends on a running Tomcat where the
 * current version of the SmallTalkCommentSoapService is deployed !
 * 
 * @author Reik Oberrath
 */
public class SmallTalkCommentIntegrationVariant1Test {
	
	private HelloWorld helloWorldServiceSoapWrapper = new HelloWorldServiceSoapWrapper();
	private HelloWorld helloWorldServiceRestWrapper = new HelloWorldServiceRESTWrapper(); 
	private SmallTalkCommentRequest standardSmallTalkCommentRequest = TestDataProvider.createStandardSmallTalkCommentRequest();
		
	@Test
	public void returnsSmallTalkCommentOnlyForName() throws Exception {
		// arrange
		standardSmallTalkCommentRequest.setName( "SoapIntegrationstest" );
		standardSmallTalkCommentRequest.setDate(null);
		
		// act
		final SmallTalkCommentResponse soapResult = helloWorldServiceSoapWrapper.startSmallTalk( standardSmallTalkCommentRequest );
		standardSmallTalkCommentRequest.setName( "RestIntegrationstest" );
		final SmallTalkCommentResponse restResult = helloWorldServiceRestWrapper.startSmallTalk( standardSmallTalkCommentRequest );
		
		// assert
		assertEquals("result", "Hi SoapIntegrationstest, how are you?" + System.getProperty("line.separator")
				               + "Nice day, this " + TestDataProvider.getDayOfWeek() + ", isn't it?" + System.getProperty("line.separator")
				               + "SoapIntegrationstest, do you know a prominent person of your name?", soapResult.getSmallTalkComment());
		assertEquals("result", "Hi RestIntegrationstest, how are you?" + System.getProperty("line.separator")
                               + "Nice day, this " + TestDataProvider.getDayOfWeek() + ", isn't it?" + System.getProperty("line.separator")
				               + "RestIntegrationstest, do you know a prominent person of your name?", restResult.getSmallTalkComment());
	}

	@Test
	public void returnsSmallTalkCommentWithNameAndDate() throws Exception 
	{
		// arrange
		standardSmallTalkCommentRequest.setName( "SoapIntegrationstest" );
		final DateTime dt = new DateTime();
		final Date d1 = dt.withHourOfDay(7).toDate();   // set a morning time
		final Date d2 = dt.withHourOfDay(17).toDate();  // set a afternoon time
		standardSmallTalkCommentRequest.setDate( XMLGregorianCalendarUtil.toGregorianCaldendar(d1) );
		
		// act
		final SmallTalkCommentResponse soapResult = helloWorldServiceSoapWrapper.startSmallTalk( standardSmallTalkCommentRequest );
		standardSmallTalkCommentRequest.setName( "RestIntegrationstest" );
		standardSmallTalkCommentRequest.setDate( XMLGregorianCalendarUtil.toGregorianCaldendar(d2) );
		final SmallTalkCommentResponse restResult = helloWorldServiceRestWrapper.startSmallTalk( standardSmallTalkCommentRequest );
		
		// assert
		assertEquals("result", "Hi SoapIntegrationstest, how are you?" + System.getProperty("line.separator")
                               + "Good morning, have a good time on this " + TestDataProvider.getDayOfWeek() + "!" + System.getProperty("line.separator")
				               + "SoapIntegrationstest, do you know a prominent person of your name?", soapResult.getSmallTalkComment());
		assertEquals("result", "Hi RestIntegrationstest, how are you?" + System.getProperty("line.separator")
                               + "Good afternoon, have a good time on this " + TestDataProvider.getDayOfWeek() + "!" + System.getProperty("line.separator")
				               + "RestIntegrationstest, do you know a prominent person of your name?", restResult.getSmallTalkComment());
	}
	
}
