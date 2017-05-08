package com.iksgmbh.demo.hoo.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Data Access Object of the order persistence layer.
 * 
 * @author Reik Oberrath
 */
@SuppressWarnings("static-access")
public class OrderDAO 
{
	public static final String CREATE_TABLE_ORDER_SQL = "create table Order (orderNumber NUMBER unique primary not null, "
			                                                              + "customerName VARCHAR2(100) not null, "
			                                                              + "horoscopeType VARCHAR2(10) not null, "
			                                                              + "ageOfTargetPerson NUMBER not null, "
			                                                              + "creationDateTime DATE not null,"
			                                                              + "price NUMBER(5,2),"
			                                                              + "paid BOOLEAN not null,"
			                                                              + "horoscopeFetched BOOLEAN not null)";
	static {
		try {
			SqlPojoMemoDB.getInstance().execute(CREATE_TABLE_ORDER_SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	private Connection conn;
	
	public OrderDAO(Connection aConnection) {
		conn = aConnection;
	}

	public void updatePaymentStatus(Order order) throws SQLException 
	{
		final PreparedStatement statement = conn.prepareStatement("update Order set paid=? where ordernumber=?");
		
		statement.setBoolean(1, order.isPaid());
		statement.setLong(2, order.getOrderNumber());
		
		statement.execute();
	}

	public void updateIsHoroscopeFetchedStatus(Order order) throws SQLException 
	{
		final PreparedStatement statement = conn.prepareStatement("update Order set horoscopeFetched=? where ordernumber=?");
		
		statement.setBoolean(1, order.isHoroscopeFetched());
		statement.setLong(2, order.getOrderNumber());
		
		statement.execute();
	}
	
	public void updateInvoiceData(Order order) throws SQLException 
	{
		final PreparedStatement statement = conn.prepareStatement("update Order set paid=?, price=? where ordernumber=?");
		
		statement.setBoolean(1, order.isPaid());
		statement.setBigDecimal(2, order.getPrice());
		statement.setLong(3, order.getOrderNumber());
		
		statement.execute();
	}	
	
	public void save(Order order) throws SQLException 
	{
		// The ordernumber must be unique and is derived from the creation timestamp.
		// Thus, the following sleep method is used to give the system time 
		// so that no duplicate database entries are created. 
		sleep(10);
		
		((OrderImpl)order).setCreationDateTime(new Date());
		
		final PreparedStatement statement = conn.prepareStatement("insert into Order ("
				+ "orderNumber, customerName, horoscopeType, ageOfTargetPerson, creationDateTime, price, paid, horoscopeFetched"
				+ ") values (?,?,?,?,?,?,?,?)");
		
		statement.setLong(1, order.getOrderNumber());
		statement.setString(2, order.getCustomerName());
		statement.setString(3, order.getHoroscopeType().name());
		statement.setInt(4, order.getAgeOfTargetPerson());
		statement.setDate(5, toSqlDate(order.getCreationDateTime()));
		statement.setBigDecimal(6, order.getPrice());
		statement.setBoolean(7, order.isPaid());
		statement.setBoolean(8, order.isHoroscopeFetched());	
		
		statement.execute();
	}
	
	private java.sql.Date toSqlDate(Date javaUtilDate) {
		if (javaUtilDate == null) return null;
		return new java.sql.Date(javaUtilDate.getTime());
	}
	
	/*
	 * The ordernumber must be unique and is derived from the creattion timestamp.
	 * Thus, this method is used to give the system time that no dublicate database entries are created. 
	 */
	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public List<Order> findAll() throws SQLException 
	{
		List<Order> toReturn = new ArrayList<Order>(); 
		PreparedStatement statement = conn.prepareStatement("select * from order");
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) 
		{
			Order order = createOrderFromResultSet(resultSet);
			toReturn.add(order);
		}
		
		return toReturn;
	}

	private Order createOrderFromResultSet(ResultSet resultSet) throws SQLException 
	{
		OrderImpl order = new OrderImpl(resultSet.getLong(1));
		
		order.setCustomerName( resultSet.getString(2) );
		order.setHoroscopeType(resultSet.getString(3));
		order.setAgeOfTargetPerson(resultSet.getInt(4));
		order.setCreationDateTime( resultSet.getDate(5) );
		order.setPrice( resultSet.getBigDecimal(6) ); 
		order.setPaid( resultSet.getBoolean(7) );
		order.setHoroscopeFetched( resultSet.getBoolean(8) );
		
		return order;
	}

	public Order find(long orderNumber) throws SQLException 
	{
		PreparedStatement statement = conn.prepareStatement("select * from order where ordernumber = " + orderNumber);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			return createOrderFromResultSet(resultSet);
		}
		return null;
	}
	
}
