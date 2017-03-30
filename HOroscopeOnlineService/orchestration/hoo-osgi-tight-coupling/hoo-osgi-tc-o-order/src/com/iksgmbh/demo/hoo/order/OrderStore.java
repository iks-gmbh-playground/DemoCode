package com.iksgmbh.demo.hoo.order;

import java.sql.SQLException;
import java.util.List;

import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Factory and repository for orders.
 * 
 * @author Reik Oberrath
 */
public class OrderStore 
{
	private OrderDAO orderDAO = new OrderDAO(SqlPojoMemoDB.getConnection());
	
	public Order createNewInstance(final String aCustomerName, 
            					   final String aHoroscopeTypeAsString, 
            					   final String aBirthday) 
	{
		return new Order(aCustomerName, aHoroscopeTypeAsString, aBirthday);
	}

	public List<Order> findAll() 
	{
		try {
			return orderDAO.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Order find(long orderNumber) 
	{
		try {
			return orderDAO.find(orderNumber);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateIsHoroscopeFetchedStatus(final Order order) 
	{
		try {
			orderDAO.updateIsHoroscopeFetchedStatus(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void updatePaymentStatus(final Order order) 
	{
		try {
			orderDAO.updatePaymentStatus(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void save(final Order order) 
	{
		try {
			orderDAO.save(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
