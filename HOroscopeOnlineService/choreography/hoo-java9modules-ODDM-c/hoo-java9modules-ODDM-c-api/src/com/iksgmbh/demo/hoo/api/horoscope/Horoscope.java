package com.iksgmbh.demo.hoo.api.horoscope;

import java.math.BigDecimal;

import com.iksgmbh.demo.hoo.api.order.Order;
import com.iksgmbh.demo.hoo.api.order.Order.HoroscopeType;

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