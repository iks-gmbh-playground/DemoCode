package com.iksgmbh.demo.hoo.api.horoscope;

import com.iksgmbh.demo.hoo.api.order.Order;
import com.iksgmbh.demo.hoo.api.requestresponse.HOO_HoroscopeRequest;
import com.iksgmbh.demo.hoo.api.requestresponse.HOO_HoroscopeResponse;

/**
 * Factory and repository for horoscopes.
 * 
 * @author Reik Oberrath
 */
public interface HoroscopeStore {
	
	void createNewInstance(Order aOrder);

	HOO_HoroscopeResponse fetchHoroscope(HOO_HoroscopeRequest request); 
		
}