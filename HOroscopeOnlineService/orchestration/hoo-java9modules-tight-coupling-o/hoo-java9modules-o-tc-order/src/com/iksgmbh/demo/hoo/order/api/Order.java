package com.iksgmbh.demo.hoo.order.api;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Interface of the order domain entity.
 * 
 * @author Reik Oberrath
 */
public interface Order 
{
	public enum HoroscopeType { MISC, LOVE, JOB };
	
	// GETTER methods
	
	long getOrderNumber();

	String getCustomerName();

	Date getCreationDateTime();

	BigDecimal getPrice();

	HoroscopeType getHoroscopeType();

	int getAgeOfTargetPerson();

	boolean isPaid();

	boolean isHoroscopeFetched();

	
	// SETTER methods
	
	void setPaid(boolean paid);

	void setPrice(BigDecimal price);

	void setHoroscopeFetched(boolean b);
}