package com.iksgmbh.demo.hoo.api.invoice;

import com.iksgmbh.demo.hoo.api.horoscope.Horoscope;
import com.iksgmbh.demo.hoo.api.order.Order;

/**
 * Factory and repository for invoices.
 * 
 * @author Reik Oberrath
 */
public interface InvoiceStore {

	void createNewInstance(Order aOrder, Horoscope aHoroscope);

}