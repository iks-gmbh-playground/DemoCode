package com.iksgmbh.demo.hoo.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iksgmbh.demo.hoo.api.utils.JaxbToStringUtil;
import com.iksgmbh.demo.hoo.api.utils.XMLGregorianCalendarUtil;
import com.iksgmbh.demo.hoo.control.api.HOOOrderRequest;
import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.invoice.api.Invoice;
import com.iksgmbh.demo.hoo.order.api.HoroscopeType;
import com.iksgmbh.demo.hoo.order.api.Order;

public class HOOControlTestImpl extends HOroscopeOnlineService
{
	private List<Order> savedOrders = new ArrayList<Order>();

	public void saveOrder(Order order)
	{
		savedOrders.add(order);
	}
	
	@Override
	public String findAllOrders() 
	{
		StringBuilder toReturn = new StringBuilder(); 
		for (Order order : savedOrders) {
			toReturn.append(JaxbToStringUtil.toString(order));
			toReturn.append(System.getProperty("line.separator"));
		}
		return toReturn.toString();
	}
	
	@Override
	protected Invoice createNewInvoice(Order order, Horoscope horoscope) 
	{
		Invoice invoice = new Invoice();
		invoice.setBill("Test Bill");
		invoice.setPrice(BigDecimal.ZERO);
		return invoice;
	}

	@Override
	protected Horoscope createNewHoroscope(Order order) 
	{
		Horoscope horoscope = new Horoscope();
		horoscope.setHoroscopeText("Test horosope");
		horoscope.setInvoiceFactor(BigDecimal.ZERO);
		return horoscope;
	}

	@Override
	protected Order createNewOrder(final HOOOrderRequest request) 
	{
		Order order = new Order();
		order.setAgeOfTargetPerson(33);
		order.setCreationDateTime(XMLGregorianCalendarUtil.toGregorianCaldendar(new Date()));
		order.setHoroscopeType(HoroscopeType.JOB);
		return order ;
	}	
}
