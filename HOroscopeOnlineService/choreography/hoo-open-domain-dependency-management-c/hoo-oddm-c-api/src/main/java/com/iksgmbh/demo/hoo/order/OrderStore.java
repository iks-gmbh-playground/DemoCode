package com.iksgmbh.demo.hoo.order;

import com.iksgmbh.demo.hoo.invoice.Invoice;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderResponse;
import com.iksgmbh.demo.hoo.requestresponse.HOO_PaymentRequest;

/**
 * Factory and repository for orders.
 * 
 * @author Reik Oberrath
 */
public interface OrderStore 
{	
	HOO_OrderResponse sendOrder(HOO_OrderRequest request); // this method has been added for implementing the choreography design
	// Order createNewInstance(HOO_OrderRequest request);  was needed as API method in orchestration but not in choreography design
	// void save(Order order);                             was needed as API method in orchestration but not in choreography design
	
	String findAllOrders();   // this method has been added for implementing the choreography design
	//List<Order> findAll();  was needed as API method in orchestration but not in choreography design

	void setPaidStatus(HOO_PaymentRequest request);   // this method has been added for implementing the choreography design
	//void updatePaymentStatus(Order order);          was needed as API method in orchestration but not in choreography design
	

	// the following methods are used from the horoscope module
	Order find(long orderNumber);
	void updateIsHoroscopeFetchedStatus(Order order);

	void saveInvoiceData(Invoice invoice);    // this method has been added for implementing the choreography design

}