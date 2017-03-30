package com.iksgmbh.demo.hoo.invoice.api;

import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.invoice.InvoiceStoreImpl;

/**
 * Factory and repository for invoices.
 * 
 * @author Reik Oberrath
 */
public interface InvoiceStore {

	/*
	 * Unfortunaltely, this static field cannot be hid, since interfaces do not allow private fields.
	 * To avoid this public field, a new class InvoiceStoreProvider could be used with a private static field and 
	 * implementing the static method getInvoiceStore (see below).
	 */
	static InvoiceStore instance = new InvoiceStoreImpl();
	
	Invoice createNewInstance(Order aOrder, Horoscope aHoroscope);

	void save(Invoice invoice);

	/**
	 * Since Java 1.8 those static methods in interfaces are possible!
	 * If you do not like those methods in interfaces,
	 * implement this getter in an InvoiceStoreProvider class.
	 */
	public static InvoiceStore getInvoiceStore() {
		return instance;
	}	
}