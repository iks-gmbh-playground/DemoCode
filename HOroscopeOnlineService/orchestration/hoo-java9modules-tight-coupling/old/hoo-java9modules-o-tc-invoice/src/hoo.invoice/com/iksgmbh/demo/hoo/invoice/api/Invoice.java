package com.iksgmbh.demo.hoo.invoice.api;

import java.math.BigDecimal;

import com.iksgmbh.demo.hoo.order.api.Order.HoroscopeType;

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