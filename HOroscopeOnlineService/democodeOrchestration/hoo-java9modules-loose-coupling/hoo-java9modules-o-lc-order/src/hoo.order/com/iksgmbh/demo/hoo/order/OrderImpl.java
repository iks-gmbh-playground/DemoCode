package com.iksgmbh.demo.hoo.order;

import java.math.BigDecimal;
import java.util.Date;

import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.order.api.Order.HoroscopeType;
import com.iksgmbh.demo.hoo.order.utils.DateStringUtil;

/**
 * Implementation of the order domain entity.
 * 
 * @author Reik Oberrath
 */
public class OrderImpl implements Order 
{	
    protected long orderNumber;
    protected String customerName;	
    protected HoroscopeType horoscopeType;
    private int ageOfTargetPerson;
    protected Date creationDateTime;
    protected BigDecimal price;	
    protected boolean paid;
    protected boolean horoscopeFetched;

	public OrderImpl(final long aId) {
		orderNumber = aId;
	}

	public OrderImpl(final String aCustomerName, 
                     final String aHoroscopeTypeAsString, 
                     final String aBirthday) 
	{
		orderNumber = new Date().getTime();
		this.customerName = aCustomerName;
		setHoroscopeType(aHoroscopeTypeAsString);
		this.ageOfTargetPerson = DateStringUtil.calculateAgeFromYearOfDate(aBirthday);
	}

	public void setHoroscopeType(final String horoscopeTypeAsText) {
		try {
			horoscopeType = HoroscopeType.valueOf(horoscopeTypeAsText);
		} catch (IllegalArgumentException e) {
			horoscopeType = HoroscopeType.MISC;
		}
	}
	
	public void setHoroscopeType(final HoroscopeType aHoroscopeType) {
		horoscopeType = aHoroscopeType;
	}
	

	public long getOrderNumber() {
		return orderNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public HoroscopeType getHoroscopeType() {
		return horoscopeType;
	}

	public int getAgeOfTargetPerson() {
		return ageOfTargetPerson;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paidFlag) {
		this.paid = paidFlag;
	}

	public void setAgeOfTargetPerson(int ageOfTargetPerson) {
		this.ageOfTargetPerson = ageOfTargetPerson;
	}

	public boolean isHoroscopeFetched() {
		return horoscopeFetched;
	}

	public void setHoroscopeFetched(boolean fetchedFlag) {
		this.horoscopeFetched = fetchedFlag;
	}
}
