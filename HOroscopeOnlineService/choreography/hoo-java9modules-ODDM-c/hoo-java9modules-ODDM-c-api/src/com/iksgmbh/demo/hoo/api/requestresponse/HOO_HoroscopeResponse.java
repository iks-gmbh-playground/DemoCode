package com.iksgmbh.demo.hoo.api.requestresponse;

public class HOO_HoroscopeResponse 
{
	private String statusInfo;
	private String horoscopeText;
	
	public HOO_HoroscopeResponse(String aStatusInfo, String aHoroscopeText) {
		statusInfo = aStatusInfo;
		horoscopeText = aHoroscopeText;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public String getHoroscopeText() {
		return horoscopeText;
	}
	
}
