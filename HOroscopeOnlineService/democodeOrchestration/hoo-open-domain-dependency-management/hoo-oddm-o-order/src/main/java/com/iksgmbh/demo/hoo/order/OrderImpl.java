package com.iksgmbh.demo.hoo.order;

import java.math.BigDecimal;
import java.util.Date;

import com.iksgmbh.demo.hoo.horoscope.Horoscope.HoroscopeType;
import com.iksgmbh.demo.hoo.order.utils.DateStringUtil;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;

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

	public OrderImpl(HOO_OrderRequest request) 
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
