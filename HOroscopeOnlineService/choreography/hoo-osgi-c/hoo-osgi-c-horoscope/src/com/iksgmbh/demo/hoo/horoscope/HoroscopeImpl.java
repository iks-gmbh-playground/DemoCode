package com.iksgmbh.demo.hoo.horoscope;

import java.math.BigDecimal;

import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.order.api.Order.HoroscopeType;

/**
 * Implementation of the horoscope domain entity.
 * 
 * @author Reik Oberrath
 */
public class HoroscopeImpl implements Horoscope
{
	private static final BigDecimal DISCOUNT_FOR_KIDS = new BigDecimal(0.5);
	private static final BigDecimal SURCHARGE_FOR_OLDIES = new BigDecimal(0.1);
	private static final int AGE_LIMIT_KIDS = 10;
	private static final int AGE_LIMIT_OLDIES = 60;

	
	private long orderNumber;
	private HoroscopeType horoscopeType;
	private String horoscopeText;
	private BigDecimal invoiceFactor;

	public HoroscopeImpl(Order aOrder) {
		orderNumber = aOrder.getOrderNumber();
		horoscopeType = aOrder.getHoroscopeType();
		invoiceFactor = BigDecimal.ONE;
	}

	public HoroscopeImpl(final long aOrderNumber,
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

	public void createHoroscopeText(final Order order) 
	{
		if (horoscopeType == HoroscopeType.JOB) {
			horoscopeText = "Divide and rule!";
		} else if (horoscopeType == HoroscopeType.LOVE) {
			horoscopeText = "Don''t worry, be happy.";
		} else {
			horoscopeText = "Take it easy.";
		}
		
		if (order.getAgeOfTargetPerson() < AGE_LIMIT_KIDS ) {
			invoiceFactor = invoiceFactor.subtract(DISCOUNT_FOR_KIDS);
		} else if (order.getAgeOfTargetPerson() > AGE_LIMIT_OLDIES ) {
			invoiceFactor = invoiceFactor.add(SURCHARGE_FOR_OLDIES);
		}	
	}

	@Override
	public String getHoroscopeText() {
		return horoscopeText;
	}

	@Override
	public HoroscopeType getHoroscopeType() {
		return horoscopeType;
	}
	
	@Override
	public BigDecimal getInvoiceFactor() {
		return invoiceFactor;
	}


	@Override
	public long getOrderNumber() {
		return orderNumber;
	}
	
}
