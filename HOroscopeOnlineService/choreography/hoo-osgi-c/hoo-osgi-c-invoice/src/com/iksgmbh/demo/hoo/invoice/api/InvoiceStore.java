package com.iksgmbh.demo.hoo.invoice.api;

import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.order.api.Order;

/**
 * Factory and repository for invoices.
 * 
 * @author Reik Oberrath
 */
public interface InvoiceStore {

	void createNewInstance(Order aOrder, Horoscope aHoroscope);

}