package com.iksgmbh.demo.hoo;

import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.Test;

import com.iksgmbh.demo.hoo.HOroscopeOnlineService;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderResponse;

/**
 * Tests the classic monolith.
 * 
 * @author Reik Oberrath
 */
public class HOroscopeOnlineServiceTest {

	private HOroscopeOnlineService sut = new HOroscopeOnlineService(); 

	@Test
	public void calculatesBasicPriceForUnkownHoroscopeType() {
		// arrange
		final HOO_OrderRequest request = new HOO_OrderRequest("UnkownCustomer", "UnkownHoroscopeType", "10.10.1990");
		
		// act 
		final HOO_OrderResponse result = sut.sendOrder(request);
		
		// assert
		assertTrue("Unexpected Price", result.getBill().contains("10.49"));
	}
	
	@Test
	public void appliesDiscountForChilden() {
		// arrange
		DateTime d = new DateTime();
		final HOO_OrderRequest request = new HOO_OrderRequest("UnkownCustomer", "MISC", "1.1." + (d.getYear()-2));
		
		// act 
		final HOO_OrderResponse result = sut.sendOrder(request);
		
		// assert
		assertTrue("Unexpected Price", result.getBill().contains("5.24"));
	}

	@Test
	public void appliesSurchargeForOldies() {
		// arrange
		DateTime d = new DateTime();
		final HOO_OrderRequest request = new HOO_OrderRequest("UnkownCustomer", "MISC", "1.1." + (d.getYear()-99));
		
		// act 
		final HOO_OrderResponse result = sut.sendOrder(request);
		
		// assert
		assertTrue("Unexpected Price", result.getBill().contains("11.54"));
	}

	@Test
	public void calculatesBasicPriceForLoveHoroscope() {
		// arrange
		DateTime d = new DateTime();
		final HOO_OrderRequest request = new HOO_OrderRequest("UnkownCustomer", "LOVE", "1.1." + (d.getYear()-37));
		
		// act 
		final HOO_OrderResponse result = sut.sendOrder(request);
		
		// assert
		assertTrue("Unexpected Price", result.getBill().contains("5.49"));
	}	
	
}
