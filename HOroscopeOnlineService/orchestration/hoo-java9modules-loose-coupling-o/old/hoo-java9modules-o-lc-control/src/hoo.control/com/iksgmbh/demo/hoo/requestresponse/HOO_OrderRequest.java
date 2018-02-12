package com.iksgmbh.demo.hoo.requestresponse;

public class HOO_OrderRequest  
{
	// remitter data
	private String customerName;
	
	// horoscope metadata
	private String horoscopeTypeAsString;
	
	// data on the person for which the horoscope refers
	private String birthdayOfTargetPerson;
	
	public HOO_OrderRequest(final String aCustomerName, 
			                final String aHoroscopeTypeAsString, 
			                final String aBirthday) 
	{
		customerName = aCustomerName;
		horoscopeTypeAsString = aHoroscopeTypeAsString;
		birthdayOfTargetPerson = aBirthday;
	}

	public String getHoroscopeType() {
		return horoscopeTypeAsString;
	}

	public String getBirthdayOfTargetPerson() {
		return birthdayOfTargetPerson;
	}

	public String getCustomerName() {
		return customerName;
	}
}
