package com.iksgmbh.demo.hoo.requestresponse;

public class HOO_HoroscopeRequest  
{
	private long orderNumber;
	
	public HOO_HoroscopeRequest(long aOrderNumber) {
		orderNumber = aOrderNumber;
	}

	public long getOrderNumber() {
		return orderNumber;
	}
}
