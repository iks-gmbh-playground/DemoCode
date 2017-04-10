package com.iksgmbh.demo.hoo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.iksgmbh.demo.hoo.control.api.HOOHoroscopeRequest;
import com.iksgmbh.demo.hoo.control.api.HOOHoroscopeResponse;
import com.iksgmbh.demo.hoo.control.api.HOOOrderRequest;
import com.iksgmbh.demo.hoo.control.api.HOOOrderResponse;
import com.iksgmbh.demo.hoo.control.api.HOOPaymentRequest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Tests four typical uses cases of the HOO micro service system.
 * 
 * @author Reik Oberrath
 */

public class HOOSystemTest 
{
	private Client jerseyClient = Client.create( new DefaultClientConfig() );
		
    @Test
    public void createsSuccessfullHOOOrderResponse()
    {
        // arrange
    	final long timestamp = new Date().getTime();
    	final HOOOrderRequest request = new HOOOrderRequest();
    	request.setCustomerName("aCustomer");
        request.setBirthdayOfTargetPerson("1.1.1999");
        request.setHoroscopeType("JOB");

        // act
        final HOOOrderResponse result = send(request);

        // assert
        assertTrue("unexpected order number", result.getOrderNumber() >= timestamp );
        assertEquals("result", "Please pay 9.99 Euro.", result.getBill());
        assertEquals("result", "Horoscope for order " + result.getOrderNumber() 
                                                      + " is not yet available. Please pay your bill.", 
        		               result.getStatusInfo());
    }
    
    
	@Test
	public void doesNotMakeJobHoroscopeAvailableIfOrderIsNotPaid() 
	{
		// arrange
		final HOOOrderRequest request1 = createHOOOrderRequest("UnkownCustomer", "JOB", "10.10.1990");
		
		// act 
		final HOOOrderResponse response1 = send(request1);
		final HOOHoroscopeRequest request2 = createHOOHoroscopeRequest(response1.getOrderNumber());
		final HOOHoroscopeResponse response2 = fetchHoroscope(request2);
		
		// assert
		assertEquals("bill", "Please pay 9.99 Euro.", response1.getBill());
		assertEquals("statusInfo", "Horoscope for order " + response1.getOrderNumber()  + " is not yet available. Please pay your bill.", response1.getStatusInfo());
		assertEquals("type", "", response2.getHoroscopeText());
		assertEquals("statusInfo", "Order "  + response1.getOrderNumber() + " is not yet paid.", response2.getStatusInfo());
		}
	
	
	@Test
	public void makesJobHoroscopeAvailableIfOrderHasBeenSetToPaid() 
	{
		// arrange
		final HOOOrderRequest request1 = createHOOOrderRequest("UnkownCustomer", "JOB", "10.10.1990");
		
		// act 
		final HOOOrderResponse response1 = send(request1);
		setPaidStatus(response1.getOrderNumber(), true);
		final HOOHoroscopeRequest request3 = createHOOHoroscopeRequest(response1.getOrderNumber());
		final HOOHoroscopeResponse response2 = fetchHoroscope(request3);
		
		// assert
		assertEquals("bill", "Please pay 9.99 Euro.", response1.getBill());
		assertEquals("statusInfo", "Horoscope for order " + response1.getOrderNumber()  + " is not yet available. Please pay your bill.", response1.getStatusInfo());
		assertEquals("type", "Divide and rule!", response2.getHoroscopeText());
		assertEquals("statusInfo", "Order "  + response1.getOrderNumber() + " is paid.", response2.getStatusInfo());
	}
	
	@Test
	public void makesJobHoroscopeImmediatelyAvailableForPrepaidCustomer() 
	{
		// arrange
		final HOOOrderRequest request1 = createHOOOrderRequest("Prepaid", "JOB", "10.10.1990");
		
		// act 
		final HOOOrderResponse response1 = send(request1);
		final HOOHoroscopeRequest request2 = createHOOHoroscopeRequest(response1.getOrderNumber());
		final HOOHoroscopeResponse response2 = fetchHoroscope(request2);
		
		// assert
		assertEquals("bill", "", response1.getBill());
		assertEquals("statusInfo", "Order " + response1.getOrderNumber() + " is paid. The horoscope is available.", response1.getStatusInfo());
		assertEquals("type", "Divide and rule!", response2.getHoroscopeText());
		assertEquals("statusInfo", "Order "  + response1.getOrderNumber() + " is paid.", response2.getStatusInfo());
	}
	
	@Test
	public void createsOverviewOfAllOrders() 
	{
		// arrange
		final HOOOrderRequest request1 = createHOOOrderRequest("UnkownCustomer", "JOB", "10.10.1990");
		final HOOOrderResponse response1 = send(request1);
		setPaidStatus(response1.getOrderNumber(), true);
		final HOOOrderRequest request3 = createHOOOrderRequest("UnkownCustomer", "LOVE", "10.10.1931");
		send(request3);
		final HOOOrderRequest request4 = createHOOOrderRequest("Prepaid", "LOVE", "10.10.1991");
		send(request4);
		final HOOOrderRequest request5 = createHOOOrderRequest("Prepaid", "MISC", "10.10.1991");
		HOOOrderResponse response6 = send(request5);
		final HOOHoroscopeRequest request7 = createHOOHoroscopeRequest(response6.getOrderNumber());
		fetchHoroscope(request7);
		
		// act 
    	final String result = jerseyClient.resource("http://localhost:18080/HOOServiceControl/allOrders")
                                          .accept(MediaType.TEXT_PLAIN).get(String.class);
		
		// assert
		System.out.println(result);
		assertTrue("unexpected overview", result.contains("JOB                 yes/no           UnkownCustomer"));
		assertTrue("unexpected overview", result.contains("LOVE                no/no            UnkownCustomer"));
		assertTrue("unexpected overview", result.contains("LOVE                yes/no           Prepaid"));
		assertTrue("unexpected overview", result.contains("MISC                yes/yes          Prepaid"));
		assertFalse("unexpected overview", result.contains("no/yes"));
		
    	System.out.println(result);
    }
	
    
	private void setPaidStatus(long orderNumber, boolean paid) 
	{
		final HOOPaymentRequest paymentRequest = new HOOPaymentRequest();
		
		paymentRequest.setOrderNumber(orderNumber);
		paymentRequest.setPaid(paid);
		
		jerseyClient.resource("http://localhost:18080/HOOServiceControl/payment")
                .type(MediaType.APPLICATION_XML)
                .put(paymentRequest);
	}


	private HOOHoroscopeRequest createHOOHoroscopeRequest(long orderNumber) 
	{
		final HOOHoroscopeRequest toReturn = new HOOHoroscopeRequest();
		
		toReturn.setOrderNumber(orderNumber);
		
		return toReturn;
	}

	private HOOOrderRequest createHOOOrderRequest(String aCustomerName, 
			                                      String HoroscopeType, 
			                                      String aBirthDay) 
	{
		final HOOOrderRequest toReturn = new HOOOrderRequest();
		
		toReturn.setCustomerName(aCustomerName);
		toReturn.setBirthdayOfTargetPerson(aBirthDay);
		toReturn.setHoroscopeType(HoroscopeType);
		
		return toReturn;
	}

	private HOOHoroscopeResponse fetchHoroscope(HOOHoroscopeRequest request) 
	{
    	return jerseyClient.resource("http://localhost:18080/HOOServiceControl/horoscope")
                                      .type(MediaType.APPLICATION_XML)
                                      .accept(MediaType.APPLICATION_XML).put(HOOHoroscopeResponse.class, 
                                    		                                 request);
	}
	

	private HOOOrderResponse send(HOOOrderRequest request) 
	{
    	return jerseyClient.resource("http://localhost:18080/HOOServiceControl/newOrder")
                                      .type(MediaType.APPLICATION_XML)
                                      .accept(MediaType.APPLICATION_XML).post(HOOOrderResponse.class, 
                                    		                                  request);
	}
	
}
