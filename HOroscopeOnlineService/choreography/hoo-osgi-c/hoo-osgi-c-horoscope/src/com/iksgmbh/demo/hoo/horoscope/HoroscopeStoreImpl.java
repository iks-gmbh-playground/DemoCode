package com.iksgmbh.demo.hoo.horoscope;

import java.sql.SQLException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.horoscope.api.HoroscopeStore;
import com.iksgmbh.demo.hoo.invoice.api.InvoiceStore;
import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.order.api.OrderStore;
import com.iksgmbh.demo.hoo.osgi.HooHoroscopeActivator;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeResponse;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Factory and repository for horoscopes.
 * 
 * @author Reik Oberrath
 */
public class HoroscopeStoreImpl implements HoroscopeStore 
{
	private OrderStore orderStore;
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
		
		final Order order = getOrderStore().find(orderNumber);
		final Horoscope horoscope = find(orderNumber);
		
		if ( order.isPaid() )  
		{
			order.setHoroscopeFetched(true);
			getOrderStore().updateIsHoroscopeFetchedStatus(order);
			
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
		getInvoiceStore().createNewInstance(order, horoscope);
	}
	
	@SuppressWarnings("unchecked")
	private OrderStore getOrderStore() 
	{
		if (orderStore == null)
		{
			final BundleContext bundleContext = HooHoroscopeActivator.getContext();
			ServiceReference<OrderStore> serviceReference = (ServiceReference<OrderStore>) bundleContext.getServiceReference(OrderStore.class.getName());
			orderStore = (OrderStore) bundleContext.getService(serviceReference); 		
		}
		
		return orderStore;
	}
	
	@SuppressWarnings("unchecked")
	private OrderStore getInvoiceStore() 
	{
		if (invoiceStore == null)
		{
			final BundleContext bundleContext = HooHoroscopeActivator.getContext();
			ServiceReference<InvoiceStore> serviceReference = (ServiceReference<InvoiceStore>) bundleContext.getServiceReference(InvoiceStore.class.getName());
			invoiceStore = (InvoiceStore) bundleContext.getService(serviceReference); 		
		}
		
		return invoiceStore;
	}	
}
