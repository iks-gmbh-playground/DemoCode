package com.iksgmbh.demo.hoo;

import java.math.BigDecimal;

import com.iksgmbh.demo.hoo.Order;
import com.iksgmbh.demo.hoo.Order.HoroscopeType;

public class Horoscope 
{
	private static final BigDecimal DISCOUNT_FOR_KIDS = new BigDecimal(0.5);
	private static final BigDecimal DISCOUNT_FOR_OLDIES = new BigDecimal(0.2);
	private static final BigDecimal SURCHARGE_FOR_WORKING_PEOPLE = new BigDecimal(0.1);
	
	private long orderNumber;
	private HoroscopeType horoscopeType;
	private String horoscopeText;
	private BigDecimal invoiceFactor;

	public Horoscope(Order aOrder) {
		orderNumber = aOrder.getOrderNumber();
		horoscopeType = aOrder.getHoroscopeType();
		invoiceFactor = BigDecimal.ONE;
	}

	public Horoscope(final long aOrderNumber,
			         final String horoscopeTypeAsText, 
			         final String ahoroscopeText,
		             final BigDecimal invoiceFactor) 
	{
		this.orderNumber = aOrderNumber;
		
		try {
			horoscopeType = HoroscopeType.valueOf(horoscopeTypeAsText);
		} catch (IllegalArgumentException e) {
			horoscopeType = HoroscopeType.MISC;
		}
		
		this.horoscopeText = ahoroscopeText;
		this.invoiceFactor = invoiceFactor;
	}

	public void createHoroscopeText(final Order order) {
		if (horoscopeType == HoroscopeType.JOB) {
			horoscopeText = "Divide and rule!";
			invoiceFactor = invoiceFactor.add(SURCHARGE_FOR_WORKING_PEOPLE);
		} else if (horoscopeType == HoroscopeType.LOVE) {
			horoscopeText = "Don''t worry, be happy.";
		} else {
			horoscopeText = "Take it easy.";
		}
		
		if (order.getAgeOfTargetPerson() < 16 ) {
			invoiceFactor = invoiceFactor.subtract(DISCOUNT_FOR_KIDS);
		} else if (order.getAgeOfTargetPerson() > 70 ) {
			invoiceFactor = invoiceFactor.subtract(DISCOUNT_FOR_OLDIES);
		}
	}

	public String getHoroscopeText() {
		return horoscopeText;
	}

	public HoroscopeType getHoroscopeType() {
		return horoscopeType;
	}
	
	public BigDecimal getInvoiceFactor() {
		return invoiceFactor;
	}


	public long getOrderNumber() {
		return orderNumber;
	}
	
}
