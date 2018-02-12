package com.iksgmbh.demo.hoo.order;

import java.math.BigDecimal;
import java.util.Date;

import com.iksgmbh.demo.hoo.api.order.Order;
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

	public OrderImpl(final String horoscopeTypeAsString, 
					 final String aBirthdayOfTargetPerson,
					 final String aCustomerName) 
	{
		orderNumber = new Date().getTime();
		setHoroscopeType(horoscopeTypeAsString);
		this.ageOfTargetPerson = DateStringUtil.calculateAgeFromYearOfDate(aBirthdayOfTargetPerson);
		this.customerName = aCustomerName;
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
	

	@Override
	public long getOrderNumber() {
		return orderNumber;
	}

	@Override
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public Date getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	@Override
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public HoroscopeType getHoroscopeType() {
		return horoscopeType;
	}

	@Override
	public int getAgeOfTargetPerson() {
		return ageOfTargetPerson;
	}

	@Override
	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paidFlag) {
		this.paid = paidFlag;
	}

	public void setAgeOfTargetPerson(int ageOfTargetPerson) {
		this.ageOfTargetPerson = ageOfTargetPerson;
	}

	@Override
	public boolean isHoroscopeFetched() {
		return horoscopeFetched;
	}

	public void setHoroscopeFetched(boolean fetchedFlag) {
		this.horoscopeFetched = fetchedFlag;
	}
}
