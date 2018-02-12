package com.iksgmbh.demo.hoo.api.requestresponse;

public class HOO_PaymentRequest  
{
	private long orderNumber;
	private boolean paid;
	
	public HOO_PaymentRequest(long aOrderNumber, boolean isPaid) {
		orderNumber = aOrderNumber;
		paid = isPaid;
	}

	public long getOrderNumber() {
		return orderNumber;
	}

	public boolean isPaid() {
		return paid;
	}
}
