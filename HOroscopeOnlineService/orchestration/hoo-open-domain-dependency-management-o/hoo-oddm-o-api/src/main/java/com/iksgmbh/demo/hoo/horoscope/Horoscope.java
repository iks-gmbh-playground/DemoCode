package com.iksgmbh.demo.hoo.horoscope;

import java.math.BigDecimal;

import com.iksgmbh.demo.hoo.order.Order;

/**
 * Interface of the horoscope domain entity.
 * 
 * @author Reik Oberrath
 */
public interface Horoscope {

	public enum HoroscopeType { MISC, LOVE, JOB };

	String getHoroscopeText();

	HoroscopeType getHoroscopeType();

	BigDecimal getInvoiceFactor();

	long getOrderNumber();

	void createHoroscopeText(Order aOrder);

}