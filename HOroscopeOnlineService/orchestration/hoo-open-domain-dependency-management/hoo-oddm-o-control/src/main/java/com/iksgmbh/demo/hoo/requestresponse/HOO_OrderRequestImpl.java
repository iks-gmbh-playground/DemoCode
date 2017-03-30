package com.iksgmbh.demo.hoo.requestresponse;

import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequestImpl;

public class HOO_OrderRequestImpl implements HOO_OrderRequest  
{
	// remitter data
	private String customerName;
	
	// horoscope metadata
	private String horoscopeTypeAsString;
	
	// data on the person for which the horoscope refers
	private String birthdayOfTargetPerson;
	
	public HOO_OrderRequestImpl(final String aCustomerName, 
			               final String aHoroscopeTypeAsString, 
			               final String aBirthday) 
	{
		customerName = aCustomerName;
		horoscopeTypeAsString = aHoroscopeTypeAsString;
		birthdayOfTargetPerson = aBirthday;
	}

	@Override
	public String getHoroscopeType() {
		return horoscopeTypeAsString;
	}

	@Override
	public String getBirthdayOfTargetPerson() {
		return birthdayOfTargetPerson;
	}

	@Override
	public String getCustomerName() {
		return customerName;
	}
}
