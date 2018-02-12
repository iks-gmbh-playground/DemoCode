package com.iksgmbh.demo.hoo.horoscope;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iksgmbh.demo.hoo.api.horoscope.Horoscope;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Data Access Object of the horoscope persistence layer.
 * 
 * @author Reik Oberrath
 */
@SuppressWarnings("static-access")
public class HoroscopeDAO 
{
	public static final String CREATE_TABLE_HOROSCOPE_SQL = "create table Horoscope (orderNumber NUMBER unique primary not null, "
																	            + "horoscopeType VARCHAR2(10) not null, "
																	            + "horoscopeText VARCHAR2(1000) not null,"
																	            + "invoiceFactor NUMBER(5,2))";
																		
	private Connection conn;
	
	public HoroscopeDAO(Connection aConnection) {
		conn = aConnection;
	}
	
	static {
		try {
			SqlPojoMemoDB.getInstance().execute(CREATE_TABLE_HOROSCOPE_SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void save(final Horoscope horoscope) throws SQLException 
	{
		final PreparedStatement statement = conn.prepareStatement("insert into Horoscope ("
				+ "orderNumber, horoscopeType, horoscopeText, invoiceFactor) values ("
				+ "?,?,?,?)");
		
		statement.setLong(1, horoscope.getOrderNumber());
		statement.setString(2, horoscope.getHoroscopeType().name());
		statement.setString(3, horoscope.getHoroscopeText());
		statement.setBigDecimal(4, horoscope.getInvoiceFactor());
		
		statement.execute();
	}
	
	public Horoscope find(long orderNumber) throws SQLException 
	{
		PreparedStatement statement = conn.prepareStatement("select * from Horoscope where ordernumber = " + orderNumber);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			return createHoroscopeFromResultSet(resultSet);
		}
		return null;
	}
	
	private Horoscope createHoroscopeFromResultSet(ResultSet resultSet) throws SQLException 
	{
		return new HoroscopeImpl(resultSet.getLong(1), 
				                 resultSet.getString(2),
				                 resultSet.getString(3).replaceAll("''", "'"), 
				                 resultSet.getBigDecimal(4));
	
	}
	
	
}
