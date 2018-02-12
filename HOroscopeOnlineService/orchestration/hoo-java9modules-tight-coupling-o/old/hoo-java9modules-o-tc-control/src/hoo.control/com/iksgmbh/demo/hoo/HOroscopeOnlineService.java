package com.iksgmbh.demo.hoo;

import java.text.SimpleDateFormat;
import java.util.List;

import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.horoscope.api.HoroscopeStore;
import com.iksgmbh.demo.hoo.invoice.api.Invoice;
import com.iksgmbh.demo.hoo.invoice.api.InvoiceStore;
import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.order.api.OrderStore;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_HoroscopeResponse;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderRequest;
import com.iksgmbh.demo.hoo.requestresponse.HOO_OrderResponse;
import com.iksgmbh.demo.hoo.requestresponse.HOO_PaymentRequest;

import com.iksgmbh.demo.hoo.order.OrderStoreImpl;
import com.iksgmbh.demo.hoo.horoscope.HoroscopeStoreImpl;
import com.iksgmbh.demo.hoo.invoice.InvoiceStoreImpl;

/**
 * HOO service for the classic monolith.
 * 
 * @author Reik Oberrath
 */
public class HOroscopeOnlineService 
{
	private static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	OrderStore orderStore = new OrderStoreImpl();
	HoroscopeStore horoscopeStore = new HoroscopeStoreImpl();
	InvoiceStore invoiceStore = new InvoiceStoreImpl();	
	
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
	public HOO_OrderResponse sendOrder(final HOO_OrderRequest request)  
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

	private Order takeOrder(HOO_OrderRequest request) {
		return orderStore.createNewInstance(request.getCustomerName(),
				                            request.getHoroscopeType(), 
                                            request.getBirthdayOfTargetPerson());
	}

	private Horoscope createHoroscope(final Order aOrder) {
		Horoscope toReturn = horoscopeStore.createNewInstance(aOrder);
		toReturn.createHoroscopeText(aOrder);
		return toReturn;
	}

	private Invoice processInvoice(Order order, Horoscope horoscope) {
		return invoiceStore.createNewInstance(order, horoscope);
	}
	

	public static void main(String[] args) 
	{
		System.out.println("##################################################################");
		
		// arrange
		final HOroscopeOnlineService sut = new HOroscopeOnlineService(); 
		final HOO_OrderRequest request1a = new HOO_OrderRequest("UnkownCustomer", "JOB", "10.10.1990");
		final HOO_OrderResponse response1a = sut.sendOrder(request1a);
		sysOutOrder(request1a, response1a);
		
		System.out.println("Setting order " + response1a.getOrderNumber() + " to paid...");
		final HOO_PaymentRequest request1b = new HOO_PaymentRequest(response1a.getOrderNumber(), true);
		sut.setPaidStatus(request1b);
		System.out.println("Done.");
		
		final HOO_OrderRequest request2 = new HOO_OrderRequest("UnkownCustomer", "LOVE", "10.10.1931");
		final HOO_OrderResponse response2 = sut.sendOrder(request2);
		sysOutOrder(request2, response2);
		
		final HOO_OrderRequest request3 = new HOO_OrderRequest("Prepaid", "LOVE", "10.10.1991");
		final HOO_OrderResponse response3 = sut.sendOrder(request3);
		sysOutOrder(request3, response3);
		
		final HOO_OrderRequest request4a = new HOO_OrderRequest("Prepaid", "MISC", "10.10.1991");
		final HOO_OrderResponse response4a = sut.sendOrder(request4a);
		sysOutOrder(request4a, response4a);
		
		System.out.println("Fetching horoscope for order " + response4a.getOrderNumber() + "...");
		final HOO_HoroscopeRequest request4b = new HOO_HoroscopeRequest(response4a.getOrderNumber());
		sut.fetchHoroscope(request4b);
		System.out.println("Horoscope fetched!");
		
		// act 
		String result = System.getProperty("line.separator") + sut.findAllOrders();
		
		// assert
		System.out.println("");
		System.out.println("Overview of orders:");
		System.out.println(result);
		System.out.println("##################################################################");
	}
	
	private static void sysOutOrder(HOO_OrderRequest request, HOO_OrderResponse response) 
	{
		System.out.println("--------------------------------------------------------------");
		System.out.println("Sending order request with");
		System.out.println("CustomerName=" + request.getCustomerName());
		System.out.println("HoroscopeType=" + request.getHoroscopeType());
		System.out.println("BirthdayOfTargetPerson=" + request.getBirthdayOfTargetPerson());
		System.out.println("");
		System.out.println("Received order response contains");
		System.out.println("OrderNumber: " + response.getOrderNumber());
		System.out.println("StatusInfo: " + response.getStatusInfo());
		System.out.println("Bill: " + response.getBill());
		System.out.println("--------------------------------------------------------------");
	}		
}

