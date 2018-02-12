package com.iksgmbh.demo.hoo.api.order;

import com.iksgmbh.demo.hoo.api.invoice.Invoice;
import com.iksgmbh.demo.hoo.api.requestresponse.HOO_OrderRequest;
import com.iksgmbh.demo.hoo.api.requestresponse.HOO_OrderResponse;
import com.iksgmbh.demo.hoo.api.requestresponse.HOO_PaymentRequest;

/**
 * Factory and repository for orders.
 * 
 * @author Reik Oberrath
 */
public interface OrderStore 
{	
	HOO_OrderResponse sendOrder(HOO_OrderRequest request); // this method has been added for implementing the choreography design
	
	String findAllOrders();   // this method has been added for implementing the choreography design

	void setPaidStatus(HOO_PaymentRequest request);   // this method has been added for implementing the choreography design
	

	// the following methods are used from the horoscope module
	Order find(long orderNumber);
	void updateIsHoroscopeFetchedStatus(Order order);

	void saveInvoiceData(Invoice invoice);    // this method has been added for implementing the choreography design
}