package com.iksgmbh.demo.hoo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Data Access Object of the invoice persistence layer.
 * 
 * @author Reik Oberrath
 */
@SuppressWarnings("static-access")
public class InvoiceDAO 
{
	public static final String CREATE_TABLE_INVOICE_SQL = "create table Invoice (orderNumber NUMBER unique primary not null, "
                                                                                + "customerName VARCHAR2(100) not null, "
			                                                                    + "horoscopeType VARCHAR2(10) not null, "
																		        + "price Number(5,2) not null, "
																		        + "invoiceFactor Number(5,2) not null, "
																		        + "prePaid BOOLEAN not null, "
                                                                                + "bill VARCHAR2(1000) not null)";
	private Connection conn;	
	
	public InvoiceDAO(Connection aConnection) {
		conn = aConnection;
	}
	
	static {
		try {
			SqlPojoMemoDB.getInstance().execute(CREATE_TABLE_INVOICE_SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void save(final Invoice invoice) throws SQLException 
	{
		final PreparedStatement statement = conn.prepareStatement("insert into Invoice ("
				+ "orderNumber, customerName, horoscopeType, price, invoiceFactor, prePaid, bill"
				+ ") values (?,?,?,?,?,?,?)");
		 									                                    
		statement.setLong(1, invoice.getOrderNumber());
		statement.setString(2, invoice.getCustomerName());
		statement.setString(3, invoice.getHoroscopeType().name());
		statement.setBigDecimal(4, invoice.getPrice());
		statement.setBigDecimal(5, invoice.getInvoiceFactor());
		statement.setBoolean(6, invoice.isPrePaid());
		statement.setString(7, invoice.getBill());
		
		statement.execute();
	}
	
	public Invoice find(long orderNumber) throws SQLException 
	{
		PreparedStatement statement = conn.prepareStatement("select * from Invoice where ordernumber = " + orderNumber);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			return createInvoiceFromResultSet(resultSet);
		}
		return null;
	}
	
	private Invoice createInvoiceFromResultSet(ResultSet resultSet) throws SQLException 
	{
		return new Invoice(resultSet.getLong(1),
		           resultSet.getString(2),
		           resultSet.getString(3),
       			   resultSet.getBigDecimal(4),
       			   resultSet.getBigDecimal(5),		           
       			   resultSet.getBoolean(6),
		           resultSet.getString(7));
	}
	
}
