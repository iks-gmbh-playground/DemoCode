package com.iksgmbh.demo.hoo.horoscope.api;

import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.horoscope.HoroscopeStoreImpl;

/**
 * Factory and repository for horoscopes.
 * 
 * @author Reik Oberrath
 */
public interface HoroscopeStore {
	
	/*
	 * Unfortunaltely, this static field cannot be hid, since interfaces do not allow private fields.
	 * To avoid this public field, a new class HoroscopeStoreProvider could be used with a private static field and 
	 * implementing the static method getHoroscopeStore (see below).
	 */
	static HoroscopeStore instance = new HoroscopeStoreImpl();
	
	public Horoscope createNewInstance(Order aOrder);

	public void save(Horoscope horoscope);

	public Horoscope find(long orderNumber);
	
	/**
	 * Since Java 1.8 those static methods in interfaces are possible!
	 * If you do not like those methods in interfaces,
	 * implement this getter in an HoroscopeStoreProvider class.
	 */	
	public static HoroscopeStore getHoroscopeStore() {
		return instance;
	}	
		
}