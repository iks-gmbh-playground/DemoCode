package com.iksgmbh.demo.hoo.horoscope.api;

import java.math.BigDecimal;

import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.order.api.Order.HoroscopeType;

/**
 * Interface of the horoscope domain entity.
 * 
 * @author Reik Oberrath
 */
public interface Horoscope {

	String getHoroscopeText();

	HoroscopeType getHoroscopeType();

	BigDecimal getInvoiceFactor();

	long getOrderNumber();

	void createHoroscopeText(Order aOrder);

}