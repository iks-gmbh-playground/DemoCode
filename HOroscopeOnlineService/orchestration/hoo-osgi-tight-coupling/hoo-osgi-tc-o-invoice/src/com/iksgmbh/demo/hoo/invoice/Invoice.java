package com.iksgmbh.demo.hoo.invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.iksgmbh.demo.hoo.horoscope.Horoscope;
import com.iksgmbh.demo.hoo.order.Order;
import com.iksgmbh.demo.hoo.order.Order.HoroscopeType;

public class Invoice 
{
	private static final BigDecimal BASIC_JOB_HOROSCOPE_PRICE = new BigDecimal("9.99");
	private static final BigDecimal BASIC_LOVE_HOROSCOPE_PRICE = new BigDecimal("5.49");
	private static final BigDecimal BASIC_MISC_HOROSCOPE_PRICE = new BigDecimal("10.49");
	
	public static final String PREPAID_CUSTOMER = "Prepaid";  // to do: should be read from DB 
	
	private long orderNumber;
	private String customerName;
	private HoroscopeType horoscopeType;
	private BigDecimal price;
	private BigDecimal invoiceFactor;
	private boolean isPrePaid;
	private String bill;
	
	public Invoice(long orderNumber,
			       String customerName,
			       String horoscopeTypeAsText, 
			       BigDecimal price, 
			       BigDecimal invoiceFactor,
			       boolean prePaid,
			       String bill) 
	{
		this.orderNumber = orderNumber;
		this.customerName = customerName;
		
		try {
			horoscopeType = HoroscopeType.valueOf(horoscopeTypeAsText);
		} catch (IllegalArgumentException e) {
			horoscopeType = HoroscopeType.MISC;
		}
		
		this.invoiceFactor = invoiceFactor;
		this.isPrePaid = prePaid;
		this.price = price;
		this.bill = bill;
	}

	public Invoice(Order aOrder, Horoscope aHoroscope) 
	{
		orderNumber = aOrder.getOrderNumber();
		customerName = aOrder.getCustomerName();
		horoscopeType = aOrder.getHoroscopeType();
		invoiceFactor = aHoroscope.getInvoiceFactor();
		isPrePaid = isPrePaid(customerName);
		determinePriceAndCreateBill();
	}
	
	private boolean isPrePaid(String customerName) {
		return customerName.equals(PREPAID_CUSTOMER);
	}

	private void determinePriceAndCreateBill() 
	{
		if (horoscopeType == HoroscopeType.JOB) {
			price = BASIC_JOB_HOROSCOPE_PRICE;
		} else if (horoscopeType == HoroscopeType.LOVE) {
			price = BASIC_LOVE_HOROSCOPE_PRICE;
		} else {
			price = BASIC_MISC_HOROSCOPE_PRICE;
		}
		
		price = price.multiply(invoiceFactor)
				     .setScale(2, RoundingMode.HALF_DOWN);
		
		bill = "Please pay " + price.toPlainString() + " Euro.";
	}

	public String getBill() {
		return bill;
	}

	public HoroscopeType getHoroscopeType() {
		return horoscopeType;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public BigDecimal getInvoiceFactor() {
		return invoiceFactor;
	}

	public boolean isPrePaid() {
		return isPrePaid;
	}

	public String getCustomerName() {
		return customerName;
	}

	public long getOrderNumber() {
		return orderNumber;
	}

}
