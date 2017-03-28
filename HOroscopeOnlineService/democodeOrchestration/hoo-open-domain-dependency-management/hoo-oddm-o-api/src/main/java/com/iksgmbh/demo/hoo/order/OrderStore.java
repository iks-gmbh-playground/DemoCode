package com.iksgmbh.demo.hoo.order;

import java.util.List;

import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;

/**
 * Factory and repository for orders.
 * 
 * @author Reik Oberrath
 */
public interface OrderStore {

	Order createNewInstance(HOO_OrderRequest request);

	List<Order> findAll();

	Order find(long orderNumber);

	void save(Order order);

	void updatePaymentStatus(Order order);

	void updateIsHoroscopeFetchedStatus(Order order);

}