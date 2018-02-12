package com.iksgmbh.demo.hoo.order.api;

import java.util.List;

import com.iksgmbh.demo.hoo.order.OrderStoreImpl;

/**
 * Factory and repository for orders.
 * 
 * @author Reik Oberrath
 */
public interface OrderStore 
{
	/*
	 * Unfortunately, this static field cannot be hid, since interfaces do not allow private fields.
     * To avoid this public field, a new class OrderStoreProvider could be used with a private static field and 
	 * implementing the static method getOrderStore (see below).
	 */
	static OrderStore instance = new OrderStoreImpl();
	
	Order createNewInstance(String horoscopeTypeAsString, 
			                String aBirthdayOfTargetPerson,
			                String aCustomerName);

	List<Order> findAll();

	Order find(long orderNumber);

	void save(Order order);

	void updatePaymentStatus(Order order);

	void updateIsHoroscopeFetchedStatus(Order order);

	/**
	 * Since Java 1.8 those static methods in interfaces are possible!
	 * If you do not like those methods in interfaces,
	 * implement this getter in an OrderStoreProvider class.
	 */
	public static OrderStore getOrderStore() {
		return instance;
	}	
}