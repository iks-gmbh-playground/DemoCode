package com.iksgmbh.demo.hoo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.iksgmbh.demo.hoo.HOroscopeOnlineService;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeResponse;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderResponse;
import com.iksgmbh.demo.hoo.requestresponse.HOO_PaymentRequest;

/**
 * Tests four typical uses cases of the HOO classic monolith system.
 * 
 * @author Reik Oberrath
 */
public class HOOSystemTest {

	private HOroscopeOnlineService sut = new HOroscopeOnlineService(); 

    @Test
    public void createsSuccessfullHOOOrderResponse()
    {
        // arrange
    	final long timestamp = new Date().getTime();
    	final HOO_OrderRequest request = new HOO_OrderRequest("aCustomer", "JOB", "1.1.1999");

        // act
        final HOO_OrderResponse result = sut.sendOrder(request);

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
		final HOO_OrderRequest request1 = new HOO_OrderRequest("UnkownCustomer", "JOB", "10.10.1990");
		
		// act 
		final HOO_OrderResponse response1 = sut.sendOrder(request1);
		final HOO_HoroscopeRequest request2 = new HOO_HoroscopeRequest(response1.getOrderNumber());
		final HOO_HoroscopeResponse response2 = sut.fetchHoroscope(request2);
		
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
		final HOO_OrderRequest request1 = new HOO_OrderRequest("UnkownCustomer", "JOB", "10.10.1990");
		
		// act 
		final HOO_OrderResponse response1 = sut.sendOrder(request1);
		final HOO_PaymentRequest request2 = new HOO_PaymentRequest(response1.getOrderNumber(), true);
		sut.setPaidStatus(request2);
		final HOO_HoroscopeRequest request3 = new HOO_HoroscopeRequest(response1.getOrderNumber());
		final HOO_HoroscopeResponse response2 = sut.fetchHoroscope(request3);
		
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
		final HOO_OrderRequest request1 = new HOO_OrderRequest("Prepaid", "JOB", "10.10.1990");
		
		// act 
		final HOO_OrderResponse response1 = sut.sendOrder(request1);
		final HOO_HoroscopeRequest request2 = new HOO_HoroscopeRequest(response1.getOrderNumber());
		final HOO_HoroscopeResponse response2 = sut.fetchHoroscope(request2);
		
		// assert
		assertEquals("bill", "", response1.getBill());
		assertEquals("statusInfo", "Order " + response1.getOrderNumber() + " is paid. The horoscope is available.", response1.getStatusInfo());
		assertEquals("type", "Divide and rule!", response2.getHoroscopeText());
		assertEquals("statusInfo", "Order "  + response1.getOrderNumber() + " is paid.", response2.getStatusInfo());
	}
	
	@Test
	public void createsOverviewOfAllOrders() {
		// arrange
		final HOO_OrderRequest request1 = new HOO_OrderRequest("UnkownCustomer", "JOB", "10.10.1990");
		final HOO_OrderResponse response1 = sut.sendOrder(request1);
		final HOO_PaymentRequest request2 = new HOO_PaymentRequest(response1.getOrderNumber(), true);
		sut.setPaidStatus(request2);
		final HOO_OrderRequest request3 = new HOO_OrderRequest("UnkownCustomer", "LOVE", "10.10.1931");
		sut.sendOrder(request3);
		final HOO_OrderRequest request4 = new HOO_OrderRequest("Prepaid", "LOVE", "10.10.1991");
		sut.sendOrder(request4);
		final HOO_OrderRequest request5 = new HOO_OrderRequest("Prepaid", "MISC", "10.10.1991");
		HOO_OrderResponse response6 = sut.sendOrder(request5);
		final HOO_HoroscopeRequest request7 = new HOO_HoroscopeRequest(response6.getOrderNumber());
		sut.fetchHoroscope(request7);
		
		// act 
		String result = System.getProperty("line.separator") + sut.findAllOrders();
		
		// assert
		System.out.println(result);
		assertTrue("unexpected overview", result.contains("JOB                 yes/no           UnkownCustomer"));
		assertTrue("unexpected overview", result.contains("LOVE                no/no            UnkownCustomer"));
		assertTrue("unexpected overview", result.contains("LOVE                yes/no           Prepaid"));
		assertTrue("unexpected overview", result.contains("MISC                yes/yes          Prepaid"));
		assertFalse("unexpected overview", result.contains("no/yes"));
	}
	
}
