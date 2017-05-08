package com.iksgmbh.demo.hoo.horoscope.api;

import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeResponse;

/**
 * Factory and repository for horoscopes.
 * 
 * @author Reik Oberrath
 */
public interface HoroscopeStore {
	
	void createNewInstance(Order aOrder);

	HOO_HoroscopeResponse fetchHoroscope(HOO_HoroscopeRequest request); 
		
}