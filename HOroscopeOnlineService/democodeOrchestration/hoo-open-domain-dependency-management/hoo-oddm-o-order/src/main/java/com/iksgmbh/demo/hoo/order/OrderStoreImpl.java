package com.iksgmbh.demo.hoo.order;

import java.sql.SQLException;
import java.util.List;

import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Factory and repository for orders.
 * 
 * @author Reik Oberrath
 */
public class OrderStoreImpl implements OrderStore 
{
	private OrderDAO orderDAO = new OrderDAO(SqlPojoMemoDB.getConnection());
	
	@Override
	public Order createNewInstance(HOO_OrderRequest request) {
		return new OrderImpl(request);
	}

	@Override
	public List<Order> findAll() 
	{
		try {
			return orderDAO.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Order find(long orderNumber) 
	{
		try {
			return orderDAO.find(orderNumber);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateIsHoroscopeFetchedStatus(final Order order) 
	{
		try {
			orderDAO.updateIsHoroscopeFetchedStatus(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updatePaymentStatus(final Order order) 
	{
		try {
			orderDAO.updatePaymentStatus(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save(final Order order) 
	{
		try {
			orderDAO.save(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
