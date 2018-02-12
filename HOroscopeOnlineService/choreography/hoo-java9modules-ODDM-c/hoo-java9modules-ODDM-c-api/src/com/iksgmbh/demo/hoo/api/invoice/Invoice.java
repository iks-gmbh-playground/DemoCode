package com.iksgmbh.demo.hoo.api.invoice;

import java.math.BigDecimal;

import com.iksgmbh.demo.hoo.api.order.Order.HoroscopeType;

/**
 * Interface of the invoice domain entity.
 * 
 * @author Reik Oberrath
 */
public interface Invoice {

	String getBill();

	HoroscopeType getHoroscopeType();

	BigDecimal getPrice();

	BigDecimal getInvoiceFactor();

	boolean isPrePaid();

	String getCustomerName();

	long getOrderNumber();
	
}