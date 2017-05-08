package com.iksgmbh.demo.hoo.invoice;

import java.sql.SQLException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.invoice.api.Invoice;
import com.iksgmbh.demo.hoo.invoice.api.InvoiceStore;
import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.order.api.OrderStore;
import com.iksgmbh.demo.hoo.osgi.HooInvoiceActivator;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Factory and repository for invoices.
 * 
 * @author Reik Oberrath
 */
public class InvoiceStoreImpl implements InvoiceStore 
{
	private OrderStore orderStore;
	private InvoiceDAO invoiceDAO = new InvoiceDAO(SqlPojoMemoDB.getConnection());
	
	@Override
	public void createNewInstance(Order aOrder, Horoscope aHoroscope) {
		Invoice invoice = new InvoiceImpl(aOrder, aHoroscope);
		save(invoice);
		saveInvoiceDataWithOrder(invoice);
	}
	
	private void saveInvoiceDataWithOrder(Invoice invoice) {
		getOrderStore().saveInvoiceData(invoice);
	}	
	
	public void save(final Invoice invoice) 
	{
		try {
			invoiceDAO.save(invoice);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private OrderStore getOrderStore() 
	{
		if (orderStore == null)
		{
			final BundleContext bundleContext = HooInvoiceActivator.getContext();
			ServiceReference<OrderStore> serviceReference = (ServiceReference<OrderStore>) bundleContext.getServiceReference(OrderStore.class.getName());
			orderStore = (OrderStore) bundleContext.getService(serviceReference); 		
		}
		
		return orderStore;
	}
	
}
