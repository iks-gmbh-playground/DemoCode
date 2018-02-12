package com.iksgmbh.demo.hoo.horoscope;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iksgmbh.demo.hoo.api.horoscope.Horoscope;
import com.iksgmbh.demo.hoo.api.horoscope.HoroscopeStore;
import com.iksgmbh.demo.hoo.api.invoice.InvoiceStore;
import com.iksgmbh.demo.hoo.api.order.Order;
import com.iksgmbh.demo.hoo.api.order.OrderStore;
import com.iksgmbh.demo.hoo.api.requestresponse.HOO_HoroscopeRequest;
import com.iksgmbh.demo.hoo.api.requestresponse.HOO_HoroscopeResponse;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Factory and repository for horoscopes.
 * 
 * @author Reik Oberrath
 */
@Service
public class HoroscopeStoreImpl implements HoroscopeStore 
{
	@Autowired
	private OrderStore orderStore;
	
	@Autowired
	private InvoiceStore invoiceStore;
	
	private HoroscopeDAO horoscopeDAO = new HoroscopeDAO(SqlPojoMemoDB.getConnection());
	
	@Override
	public void createNewInstance(Order aOrder) {
		final Horoscope horoscope = new HoroscopeImpl(aOrder);
		horoscope.createHoroscopeText(aOrder);
		save(horoscope);
		processInvoice(aOrder, horoscope);		
	}

	/**
	 * Customer method to request a horoscope to an order previously sent.
	 * @param request containing the order number
	 * @return response containing the horoscope text
	 */
	public HOO_HoroscopeResponse fetchHoroscope(HOO_HoroscopeRequest request) 
	{
		final long orderNumber = request.getOrderNumber();
		
		final Order order = orderStore.find(orderNumber);
		final Horoscope horoscope = find(orderNumber);
		
		if ( order.isPaid() )  
		{
			order.setHoroscopeFetched(true);
			orderStore.updateIsHoroscopeFetchedStatus(order);
			
			return new HOO_HoroscopeResponse("Order " + orderNumber + " is paid.", horoscope.getHoroscopeText());
		}
		
		return new HOO_HoroscopeResponse("Order " + orderNumber + " is not yet paid.", "");
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

	private void processInvoice(Order order, Horoscope horoscope) {
		invoiceStore.createNewInstance(order, horoscope);
	}
	
}
