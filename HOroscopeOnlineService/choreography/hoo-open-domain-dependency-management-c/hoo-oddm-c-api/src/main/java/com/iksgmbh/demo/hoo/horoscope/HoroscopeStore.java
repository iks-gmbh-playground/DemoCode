package com.iksgmbh.demo.hoo.horoscope;

import com.iksgmbh.demo.hoo.order.Order;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeResponse;

/**
 * Factory and repository for horoscopes.
 * 
 * @author Reik Oberrath
 */
public interface HoroscopeStore {
	
	HOO_HoroscopeResponse fetchHoroscope(HOO_HoroscopeRequest request); // this method has been added for implementing the choreography design

	void createNewInstance(Order aOrder);

	// the following methods are needed in the orchestration design but are
	// no more needed as API methods in the choreography design
//	void save(Horoscope horoscope);
//	Horoscope find(long orderNumber);
		
}