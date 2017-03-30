package com.iksgmbh.demo.hoo;

import java.text.SimpleDateFormat;
import java.util.List;

import com.iksgmbh.demo.hoo.horoscope.Horoscope;
import com.iksgmbh.demo.hoo.horoscope.HoroscopeStore;
import com.iksgmbh.demo.hoo.invoice.Invoice;
import com.iksgmbh.demo.hoo.invoice.InvoiceStore;
import com.iksgmbh.demo.hoo.order.Order;
import com.iksgmbh.demo.hoo.order.OrderStore;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeResponse;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderResponse;
import com.iksgmbh.demo.hoo.requestresponse.HOO_PaymentRequest;

/**
 * HOO service for the OSGi version without using OSGi services.
 * 
 * @author Reik Oberrath
 */
public class HOroscopeOnlineService 
{
	private static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	OrderStore orderStore = new OrderStore();
	HoroscopeStore horoscopeStore = new HoroscopeStore();
	InvoiceStore invoiceStore = new InvoiceStore();	

	
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
		final List<Order> orders = orderStore.findAll();
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
		final Order order = orderStore.find(orderNumber);
		
		order.setPaid(request.isPaid());
		orderStore.updatePaymentStatus(order);
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
		
		// TO DO: the following three instructions belong together and should be combined to one transaction
		orderStore.save(order);
		horoscopeStore.save(horoscope);
		invoiceStore.save(invoice);
		
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
		final Order order = orderStore.find(orderNumber);
		final Horoscope horoscope = horoscopeStore.find(orderNumber);
		
		if ( order.isPaid() )  
		{
			order.setHoroscopeFetched(true);
			orderStore.updateIsHoroscopeFetchedStatus(order);
			
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

	private Order takeOrder(HOO_OrderRequest request) 
	{
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

