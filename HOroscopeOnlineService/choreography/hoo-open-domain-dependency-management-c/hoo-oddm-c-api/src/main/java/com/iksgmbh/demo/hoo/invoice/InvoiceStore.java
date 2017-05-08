package com.iksgmbh.demo.hoo.invoice;

import com.iksgmbh.demo.hoo.horoscope.Horoscope;
import com.iksgmbh.demo.hoo.order.Order;

/**
 * Factory and repository for invoices.
 * 
 * @author Reik Oberrath
 */
public interface InvoiceStore {

	void createNewInstance(Order aOrder, Horoscope aHoroscope);

	// void save(Invoice invoice);  was needed as API method in orchestration but not in choreography design

}