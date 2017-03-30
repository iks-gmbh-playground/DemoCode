package com.iksgmbh.demo.hoo;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.iksgmbh.demo.hoo.Horoscope;
import com.iksgmbh.demo.hoo.HoroscopeDAO;
import com.iksgmbh.demo.hoo.Invoice;
import com.iksgmbh.demo.hoo.InvoiceDAO;
import com.iksgmbh.demo.hoo.Order;
import com.iksgmbh.demo.hoo.OrderDAO;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeResponse;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderResponse;
import com.iksgmbh.demo.hoo.requestresponse.HOO_PaymentRequest;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;

/**
 * HOO service for the classic monolith.
 * 
 * @author Reik Oberrath
 */
public class HOroscopeOnlineService 
{
	private static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	private Connection conn = SqlPojoMemoDB.getConnection();
	private OrderDAO orderDAO = new OrderDAO(conn);
	private HoroscopeDAO horoscopeDAO = new HoroscopeDAO(conn);
	private InvoiceDAO invoiceDAO = new InvoiceDAO(conn);

	
	// #####################################################################
	//                          Service Methods
	// #####################################################################

	/**
	 * Administration method to view all orders previously created.
	 * 
	 * @return order overview
	 */
	public String findAllOrders()  
	{
		List<Order> orders;
		try {
			orders = orderDAO.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
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
		
		Order order;
		try {
			order = orderDAO.find(orderNumber);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		order.setPaid(request.isPaid());
		
		try {
			orderDAO.updatePaymentStatus(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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
		final Order order = takeOrder(request);
		final Horoscope horoscope = createHoroscope(order);
		final Invoice invoice = processInvoice(order, horoscope);
		
		order.setPrice(invoice.getPrice());
		order.setPaid( invoice.isPrePaid() );
		
		try {
			// TO DO: the following three instructions belong together and should be combined to one transaction
			orderDAO.save(order);
			horoscopeDAO.save(horoscope);
			invoiceDAO.save(invoice);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		if ( order.isPaid() )  {
			final String statusInfo = "Order " + order.getOrderNumber() + " is paid. The horoscope is available.";
			return new HOO_OrderResponse(statusInfo, "", order.getOrderNumber());
		}
		
		final String statusInfo = "Horoscope for order " + order.getOrderNumber() + " is not yet available. Please pay your bill.";
		return new HOO_OrderResponse(statusInfo, invoice.getBill(), order.getOrderNumber());
	}

	/**
	 * Customer method to request a horoscope to an order previously sent.
	 * @param request containing the order number
	 * @return response containing the horoscope text
	 */
	public HOO_HoroscopeResponse fetchHoroscope(HOO_HoroscopeRequest request) 
	{
		final long orderNumber = request.getOrderNumber();
		
		Order order;
		Horoscope horoscope;
		try {
			order = orderDAO.find(orderNumber);
			horoscope = horoscopeDAO.find(orderNumber);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		if ( order.isPaid() )  
		{
			order.setHoroscopeFetched(true);
			try {
				orderDAO.updateIsHoroscopeFetchedStatus(order);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			
			return new HOO_HoroscopeResponse("Order " + orderNumber + " is paid.", horoscope.getHoroscopeText());
		}
		
		return new HOO_HoroscopeResponse("Order " + orderNumber + " is not yet paid.", "");
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

	private Order takeOrder(HOO_OrderRequest request) {
		return new Order(request.getCustomerName(),
				         request.getHoroscopeType(),
				         request.getBirthdayOfTargetPerson());
	}

	private Horoscope createHoroscope(Order order) {
		Horoscope toReturn = new Horoscope(order);
		toReturn.createHoroscopeText(order);
		return toReturn;
	}

	private Invoice processInvoice(Order order, Horoscope horoscope) {
		return new Invoice(order, horoscope);
	}
	

	public static void main(String[] args) {

		// arrange
		String customer = "A new customer.";
		final String type = "JOB";
		final String birthdayOfTargetPerson = "22.11.1933";
		final HOO_OrderRequest request = new HOO_OrderRequest(customer, type, birthdayOfTargetPerson);

		// act
		final HOroscopeOnlineService hooService = new HOroscopeOnlineService();
		final HOO_OrderResponse result = hooService .sendOrder(request);

		// assert
		System.out.println("--------------------------------------------------------------");
		System.out.println("Sending order request with");
		System.out.println("HoroscopeType=" + type);
		System.out.println("BirthdayOfTargetPerson=" + birthdayOfTargetPerson);
		System.out.println("");
		System.out.println("Received order response contains");
		System.out.println("OrderNumber: " + result.getOrderNumber());
		System.out.println("StatusInfo: " + result.getStatusInfo());
		System.out.println("Bill: " + result.getBill());
		System.out.println("--------------------------------------------------------------");
		}	
}

