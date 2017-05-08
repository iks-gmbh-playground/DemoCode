package com.iksgmbh.demo.hoo.horoscope;

import com.iksgmbh.demo.hoo.order.Order;

/**
 * Factory and repository for horoscopes.
 * 
 * @author Reik Oberrath
 */
public interface HoroscopeStore {
	
	public Horoscope createNewInstance(Order aOrder);

	public void save(Horoscope horoscope);

	public Horoscope find(long orderNumber);
		
}