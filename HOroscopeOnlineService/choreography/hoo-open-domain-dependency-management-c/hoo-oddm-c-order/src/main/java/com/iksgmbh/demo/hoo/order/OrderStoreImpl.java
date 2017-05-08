package com.iksgmbh.demo.hoo.order;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iksgmbh.demo.hoo.horoscope.HoroscopeStore;
import com.iksgmbh.demo.hoo.invoice.Invoice;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderResponse;
import com.iksgmbh.demo.hoo.requestresponse.HOO_PaymentRequest;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * Factory and repository for orders.
 * 
 * @author Reik Oberrath
 */
@Service
public class OrderStoreImpl implements OrderStore 
{
	private static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		
	@Autowired
	private HoroscopeStore horoscopeStore;
	
	private OrderDAO orderDAO = new OrderDAO(SqlPojoMemoDB.getConnection());
	
	public Order createNewInstance(HOO_OrderRequest request) {
		return new OrderImpl(request);
	}

	
	/**
	 * Administration method to view all orders previously created.
	 * 
	 * @return order overview
	 */
	public String findAllOrders()  
	{
		final List<Order> orders = findAll();
		return createOverview(orders);
	}
	
	
	/**
	 * Administration method to set an order to status PAID.
	 * 
	 * @param request
	 * @return response
	 */
	public void setPaidStatus(HOO_PaymentRequest request)  
	{
		final long orderNumber = request.getOrderNumber();
		final Order order = find(orderNumber);
		
		order.setPaid(request.isPaid());
		updatePaymentStatus(order);
	}
	
	/**
	 * Customer method to create a new horoscope request.
	 * Triggers an horocsope and invoice creation.
	 * 
	 * @param request
	 * @return response
	 */
	public HOO_OrderResponse sendOrder(HOO_OrderRequest request)  
	{
		Order order = createNewInstance(request);
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

	public List<Order> findAll() 
	{
		try {
			return orderDAO.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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

	public void updatePaymentStatus(final Order order) 
	{
		try {
			orderDAO.updatePaymentStatus(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void save(final Order order) 
	{
		try {
			orderDAO.saveOrUpdate(order);
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
		horoscopeStore.createNewInstance(aOrder);
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


}
