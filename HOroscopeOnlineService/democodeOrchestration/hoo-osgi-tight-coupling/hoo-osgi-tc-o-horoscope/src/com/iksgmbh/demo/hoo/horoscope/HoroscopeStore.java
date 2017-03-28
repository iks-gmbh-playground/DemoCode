package com.iksgmbh.demo.hoo.horoscope;

import java.sql.SQLException;

import com.iksgmbh.demo.hoo.horoscope.dao.HoroscopeDAO;
import com.iksgmbh.demo.hoo.order.Order;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Factory and repository for horoscopes.
 * 
 * @author Reik Oberrath
 */
public class HoroscopeStore 
{
	private HoroscopeDAO horoscopeDAO = new HoroscopeDAO(SqlPojoMemoDB.getConnection());
	
	public Horoscope createNewInstance(Order aOrder) {
		return new Horoscope(aOrder);
	}

	public void save(final Horoscope horoscope) 
	{
		try {
			horoscopeDAO.save(horoscope);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Horoscope find(long orderNumber) 
	{

		try {
			return horoscopeDAO.find(orderNumber);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
