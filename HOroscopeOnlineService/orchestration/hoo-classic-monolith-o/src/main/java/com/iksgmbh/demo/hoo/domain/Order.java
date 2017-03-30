package com.iksgmbh.demo.hoo.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.iksgmbh.demo.hoo.domain.Horoscope.HoroscopeType;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;
import com.iksgmbh.demo.hoo.utils.DateStringUtil;

public class Order 
{	
    protected long orderNumber;
    protected String customerName;	
    protected HoroscopeType horoscopeType;
    private int ageOfTargetPerson;
    protected Date creationDateTime;
    protected BigDecimal price;	
    protected boolean paid;
    protected boolean horoscopeFetched;

	public Order(final long aId) {
		orderNumber = aId;
	}

	public Order(HOO_OrderRequest request) 
	{
		orderNumber = new Date().getTime();
		setHoroscopeType(request.getHoroscopeType());
		this.ageOfTargetPerson = DateStringUtil.calculateAgeFromYearOfDate(request.getBirthdayOfTargetPerson());
		this.customerName = request.getCustomerName();
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
