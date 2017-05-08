package com.iksgmbh.demo.hoo.order.api.requestresponse;

import com.iksgmbh.demo.hoo.requestresponse.HOO_PaymentRequest;

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
