package com.iksgmbh.demo.hoo.requestresponse;

public class HOO_OrderResponse 
{
	private String statusInfo;
	private String bill;
	private long orderNumber;
	
	public HOO_OrderResponse(String aStatusInfo, String aBill, long aOrdernumber) {
		statusInfo = aStatusInfo;
		bill = aBill;
		orderNumber = aOrdernumber;
	}

	public String getBill() {
		return bill;
	}
	
	public String getStatusInfo() {
		return statusInfo;
	}

	public long getOrderNumber() {
		return orderNumber;
	}

}
