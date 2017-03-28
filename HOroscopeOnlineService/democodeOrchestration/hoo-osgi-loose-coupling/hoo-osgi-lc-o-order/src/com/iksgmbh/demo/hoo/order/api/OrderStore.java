package com.iksgmbh.demo.hoo.order.api;

import java.util.List;

/**
 * Factory and repository for orders.
 * 
 * @author Reik Oberrath
 */
public interface OrderStore {

	Order createNewInstance(String horoscopeTypeAsString, 
			                String aBirthdayOfTargetPerson,
			                String aCustomerName);

	List<Order> findAll();

	Order find(long orderNumber);

	void save(Order order);

	void updatePaymentStatus(Order order);

	void updateIsHoroscopeFetchedStatus(Order order);

}