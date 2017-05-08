package com.iksgmbh.demo.hoo.order;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.iksgmbh.demo.hoo.horoscope.api.HoroscopeStore;
import com.iksgmbh.demo.hoo.invoice.api.Invoice;
import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.order.api.OrderStore;
import com.iksgmbh.demo.hoo.osgi.HooOrderActivator;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderResponse;
import com.iksgmbh.demo.hoo.requestresponse.HOO_PaymentRequest;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Factory and repository for orders.
 * 
 * @author Reik Oberrath
 */
public class OrderStoreImpl implements OrderStore 
{	
	private static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	private HoroscopeStore horoscopeStore;
	private OrderDAO orderDAO = new OrderDAO(SqlPojoMemoDB.getConnection());
	
	// #####################################################################
	//                        Interface Methods
	// #####################################################################
	
	@Override
	public HOO_OrderResponse sendOrder(HOO_OrderRequest request) 
	{
		Order order = createNewInstance(request.getHoroscopeType(), 
				                        request.getBirthdayOfTargetPerson(), 
				                        request.getCustomerName());
		
		save(order);                // creates order in db with initial data
		createHoroscope(order);     // this will update the previously saved order by invoice data 
		order = find(order.getOrderNumber());
		
		if ( order.isPaid() )  {
			final String statusInfo = "Order " + order.getOrderNumber() + " is paid. The horoscope is available.";
			return new HOO_OrderResponse(statusInfo, "", order.getOrderNumber());
		}
		
		final String bill = createBill(order.getPrice());
		final String statusInfo = "Horoscope for order " + order.getOrderNumber() + " is not yet available. Please pay your bill.";
		return new HOO_OrderResponse(statusInfo, bill, order.getOrderNumber());
	}

	/**
	 * Administration method to view all orders previously created.
	 * 
	 * @return order overview
	 */
	@Override
	public String findAllOrders()  
	{
		final List<Order> orders = findAll();
		return createOverview(orders);
	}
	

	@Override
	public Order find(long orderNumber) 
	{
		try {
			return orderDAO.find(orderNumber);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateIsHoroscopeFetchedStatus(final Order order) 
	{
		try {
			orderDAO.updateIsHoroscopeFetchedStatus(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void setPaidStatus(HOO_PaymentRequest request) 
	{
		final Order order = find(request.getOrderNumber());
		order.setPaid(request.isPaid());
		
		try {
			orderDAO.updatePaymentStatus(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void saveInvoiceData(Invoice invoice) 
	{
		final Order order = find(invoice.getOrderNumber());
		
		order.setPrice(invoice.getPrice());
		order.setPaid(invoice.isPrePaid());
		
		try {
			orderDAO.updateInvoiceData(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
		
	}
	
	// #####################################################################
	//                          private Methods
	// #####################################################################
	
	private void save(final Order order) 
	{
		try {
			orderDAO.save(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private String createOverview(List<Order> orders) 
	{
		final StringBuilder sb = new StringBuilder("Date Time of Order       Horoscope Type      paid/fetched     Customer            Price      Order Number");
		sb.append(System.getProperty("line.separator"));
    	sb.append("-----------------------------------------------------------------------------------------------------------");
        
        for (Order order : orders) 
        {
			sb.append(System.getProperty("line.separator"));
        	sb.append(DEFAULT_DATE_FORMATTER.format(order.getCreationDateTime()));
        	sb.append("      ");
        	sb.append( addSpaceUntil(order.getHoroscopeType().name(), 20) );
        	String paidFetchedprice = order.isPaid() ? "yes" : "no";
        	paidFetchedprice += "/";
        	paidFetchedprice += order.isHoroscopeFetched() ? "yes" : "no";
        	sb.append( addSpaceUntil(paidFetchedprice, 17) );
        	sb.append( addSpaceUntil(order.getCustomerName(), 20) );
        	sb.append( addSpaceUntil(order.getPrice().toPlainString(), 11) );
        	sb.append( order.getOrderNumber());
		}

        return sb.toString().replaceAll("''", "'");
	}	
	
    private String addSpaceUntil(String s, int maxLength) {
    	while (s.length() < maxLength) {
    		s += " ";
    	}
    	return s;
    }
	
	private void createHoroscope(final Order aOrder) {
		getHoroscopeStore().createNewInstance(aOrder);
	}
	
	
    /**
     * TODO: Remove this method. Instead,
     * 		 add bill to db order table and save bill in order data with method saveInvoiceData.
     *       Reason: bill creation belongs to invoice and is already implemented there
     * @param order
     * @return
     */
	private String createBill(BigDecimal price) {
		return "Please pay " + price.toPlainString() + " Euro.";
	}

	@SuppressWarnings("unchecked")
	private HoroscopeStore getHoroscopeStore() 
	{
		if (horoscopeStore == null)
		{
			final BundleContext bundleContext = HooOrderActivator.getContext();
			ServiceReference<HoroscopeStore> serviceReference = (ServiceReference<HoroscopeStore>) bundleContext.getServiceReference(HoroscopeStore.class.getName());
			horoscopeStore = (HoroscopeStore) bundleContext.getService(serviceReference); 		
		}
		
		return horoscopeStore;
	}
	
	private Order createNewInstance(final String horoscopeTypeAsString, 
			                       final String aBirthdayOfTargetPerson,
			                       final String aCustomerName) 
	{
		return new OrderImpl(horoscopeTypeAsString, aBirthdayOfTargetPerson, aCustomerName);
	}	

	private List<Order> findAll() 
	{
		try {
			return orderDAO.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
