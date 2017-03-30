package com.iksgmbh.demo.hoo.horoscope;

import java.sql.SQLException;

import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.order.api.Order.HoroscopeType;
import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.horoscope.api.HoroscopeStore;

import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Factory and repository for horoscopes.
 * 
 * @author Reik Oberrath
 */
public class HoroscopeStoreImpl implements HoroscopeStore 
{
	private HoroscopeDAO horoscopeDAO = new HoroscopeDAO(SqlPojoMemoDB.getConnection());
	
	@Override
	public Horoscope createNewInstance(Order aOrder) {
		return new HoroscopeImpl(aOrder);
	}

	@Override
	public void save(final Horoscope horoscope) 
	{
		try {
			horoscopeDAO.save(horoscope);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Horoscope find(long orderNumber) 
	{

		try {
			return horoscopeDAO.find(orderNumber);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		}
}
