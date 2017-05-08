package com.iksgmbh.demo.hoo.control;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.iksgmbh.demo.hoo.api.utils.HOOServiceException;
import com.iksgmbh.demo.hoo.api.utils.HOOValidationUtil;
import com.iksgmbh.demo.hoo.api.utils.JaxbToStringUtil;
import com.iksgmbh.demo.hoo.api.utils.XMLGregorianCalendarUtil;
import com.iksgmbh.demo.hoo.control.api.HOOHoroscopeRequest;
import com.iksgmbh.demo.hoo.control.api.HOOHoroscopeResponse;
import com.iksgmbh.demo.hoo.control.api.HOOOrderRequest;
import com.iksgmbh.demo.hoo.control.api.HOOOrderResponse;
import com.iksgmbh.demo.hoo.control.api.HOOPaymentRequest;
import com.iksgmbh.demo.hoo.horoscope.api.CreateHoroscopeRequest;
import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.invoice.api.CreateInvoiceRequest;
import com.iksgmbh.demo.hoo.invoice.api.Invoice;
import com.iksgmbh.demo.hoo.order.api.CreateOrderRequest;
import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.order.api.OrderList;
import com.iksgmbh.demo.hoo.order.api.PaymentRequest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
* Main class of the HOO-control microservice and the whole HOO-Service. 
* This file contains the module contract of the control module to its environment. 
* Exported functionality available from outside see annotated service methods.
* Imported functionality: see constant URI values.
* 
* @author Reik Oberrath
*/
@Path("/")
public class HOroscopeOnlineService
{
	private static final Logger LOGGER = Logger.getLogger("HOOServiceImpl");
    private static final String HOST_PORT = "localhost:8080";
    private static final String XML = MediaType.APPLICATION_XML;
	private static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	// Dependencies (imported module functionality) of this microservice/module:
    private static final String NEW_ORDER_URI        = "http://" + HOST_PORT + "/HOOServiceOrder/newOrder";
	private static final String SAVE_ORDER_URI       = "http://" + HOST_PORT + "/HOOServiceOrder/saveOrder";
	private static final String FIND_ORDER_URI       = "http://" + HOST_PORT + "/HOOServiceOrder/order/";
	private static final String FIND_HOROSCOPE_URI   = "http://" + HOST_PORT + "/HOOServiceHoroscope/horoscope/";
	private static final String SET_PAID_URI         = "http://" + HOST_PORT + "/HOOServiceOrder/order/status/paid";
	private static final String SET_FETCHED_URI      = "http://" + HOST_PORT + "/HOOServiceOrder/order/{orderNumber}/status/horoscopeFetched";
	private static final String FIND_ALL_ORDERS_URI  = "http://" + HOST_PORT + "/HOOServiceOrder/order/all";
    private static final String NEW_HOROSCOPE_URI    = "http://" + HOST_PORT + "/HOOServiceHoroscope/newHoroscope";
    private static final String NEW_INVOICE_URI      = "http://" + HOST_PORT + "/HOOServiceInvoice/newInvoice";


    private Client jerseyClient = Client.create( new DefaultClientConfig() );
    
    static {
		HOOValidationUtil.setXsdFileLocation("/HOO-Control.xsd",      // schema location for tomcat runtime 
				                             "src/main/resources/HOO-Control.xsd");  // location for unit test runtime
	}

	
	// #####################################################################
	//                          Service Methods
    //                 (Exported module functionality)
	// #####################################################################

    @GET
    @Path("/allOrders")
    @Produces(MediaType.TEXT_PLAIN)
    /**
     * Administration method to view all orders previously created.
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceControl/allOrders
     */
    public String findAllOrders() throws HOOServiceException
    {
    	LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method findAllOrders() called.");

        OrderList response = jerseyClient.resource(FIND_ALL_ORDERS_URI)
  	                                     .accept(MediaType.APPLICATION_XML).get(OrderList.class);
        
        List<Order> orders = response.getOrders();
        final String toReturn = createOverview(orders);
	
        LOGGER.info("Number of orders found: " + orders.size());
        LOGGER.info("---------------------------------------------------------------------------");
        
        return toReturn;
    }
    
    @PUT
    @Path("/payment")
    @Produces(MediaType.TEXT_PLAIN)
    /**
     * Administration method to set a payment to status PAID.
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceControl/payment
     */
	public void setPaidStatus(HOOPaymentRequest request)  
	{
       	LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method setPaidStatus() called.");   	
        
        final PaymentRequest paymentRequest = new PaymentRequest();
    	paymentRequest.setOrderNumber(request.getOrderNumber());
    	paymentRequest.setPaid(request.isPaid());

    	// delegate to order service
    	jerseyClient.resource(SET_PAID_URI).type(XML).put(paymentRequest);
    	
        LOGGER.info("Paid status of order " + request.getOrderNumber() + " set to " + request.isPaid() + ".");
        LOGGER.info("---------------------------------------------------------------------------");
  	}    
    
    @POST
    @Path("/newOrder")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    /**
	 * Customer method to create a new horoscope request.
	 * Triggers an horocsope and invoice creation.
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceControl/newOrder
     */
    public HOOOrderResponse sendOrder(final HOOOrderRequest request) throws HOOServiceException
    {
    	LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method sendOrder() called.");
        HOOValidationUtil.validateContractObject("HOOOrderRequest", request);
        LOGGER.info("Valid HOOOrderRequest received: " + JaxbToStringUtil.toString(request));

        final HOOOrderResponse response = createOrderResponse(request);
        
        HOOValidationUtil.validateContractObject("HOOOrderResponse", response);
        LOGGER.info("Valid HOOOrderResponse created: " + JaxbToStringUtil.toString(response));
        LOGGER.info("---------------------------------------------------------------------------");

        return response;
    }
    
    @PUT
    @Path("/horoscope")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    /**
	 * Customer method to request a horoscope to an order previously sent.
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceControl/horoscope
     */
    public HOOHoroscopeResponse fetchHoroscope(final HOOHoroscopeRequest request) throws HOOServiceException
    {
    	LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method fetchHoroscope() called.");
        HOOValidationUtil.validateContractObject("HOOHoroscopeRequest", request);
        LOGGER.info("Valid HOOHoroscopeRequest received: " + JaxbToStringUtil.toString(request));

    	final long orderNumber = request.getOrderNumber();
    	final Order order = findOrder(orderNumber);
        final HOOHoroscopeResponse response = createHoroscopeResponse(order);
        
        HOOValidationUtil.validateContractObject("HOOHoroscopeResponse", response);
        LOGGER.info("Valid HOOHoroscopeResponse created: " + JaxbToStringUtil.toString(response));
        LOGGER.info("---------------------------------------------------------------------------");

        return response;
    }
    

    
	// #####################################################################
	//                          Internal Methods
	// #####################################################################


    private HOOOrderResponse createOrderResponse(final HOOOrderRequest request) throws HOOServiceException
    {
    	// TO DO: the following three instructions belong together and should be ideally 
    	//        be bound to one transaction, which is not possible in a microservice architecture
    	final Order order = createNewOrder(request);
    	final Horoscope horoscope = createNewHoroscope(order);
    	final Invoice invoice = createNewInvoice(order, horoscope);

    	order.setPrice(invoice.getPrice());
    	order.setPaid( invoice.isPrePaid() );
    	
    	final HOOOrderResponse toReturn = new HOOOrderResponse();
    	toReturn.setOrderNumber(order.getOrderNumber());
    	
    	if ( order.isPaid() )  {
    		toReturn.setStatusInfo("Order " + order.getOrderNumber() + " is paid. The horoscope is available.");
        	toReturn.setBill("");
    	} else {
        	toReturn.setBill(invoice.getBill());
    		toReturn.setStatusInfo("Horoscope for order " + order.getOrderNumber() + " is not yet available. Please pay your bill.");
    	}
    	
    	saveOrder(order);
    	
        return toReturn;
    }
    
    private HOOHoroscopeResponse createHoroscopeResponse(final Order order) throws HOOServiceException
    {
    	final HOOHoroscopeResponse toReturn = new HOOHoroscopeResponse();
    	
		if ( order.isPaid() )  
		{
			final Horoscope horoscope = findHoroscope(order.getOrderNumber());
			toReturn.setHoroscopeText(horoscope.getHoroscopeText());
			toReturn.setStatusInfo("Order " + order.getOrderNumber() + " is paid.");
			
	    	// delegate to order service
	    	jerseyClient.resource(SET_FETCHED_URI.replace("{orderNumber}", "" + order.getOrderNumber())).put();
	    	LOGGER.info("HoroscopeFetched status for order " + order.getOrderNumber() + " set to true.");
		}
		else
		{
	    	toReturn.setHoroscopeText(""); 
	    	toReturn.setStatusInfo("Order " + order.getOrderNumber() + " is not yet paid.");
		}
    	
        return toReturn;
    }

    protected Horoscope findHoroscope(long orderNumber) 
	{
    	return jerseyClient.resource(FIND_HOROSCOPE_URI + orderNumber).accept(XML).get(Horoscope.class);
	}
    
    protected Order findOrder(long orderNumber) 
	{
    	return jerseyClient.resource(FIND_ORDER_URI + orderNumber).accept(XML).get(Order.class);
	}

    protected void saveOrder(Order order) 
	{
    	jerseyClient.resource(SAVE_ORDER_URI).type(XML).post(order);
	}

	protected Invoice createNewInvoice(Order order, Horoscope horoscope) 
	{
		final CreateInvoiceRequest invoiceRequest = new CreateInvoiceRequest();
		
    	invoiceRequest.setOrder(order);
    	invoiceRequest.setHoroscope(horoscope);
    	
    	return jerseyClient.resource(NEW_INVOICE_URI).type(XML)
                           .accept(XML).put(Invoice.class, invoiceRequest);
	}

	protected Horoscope createNewHoroscope(Order order) 
	{
		final CreateHoroscopeRequest horoscopeRequest = new CreateHoroscopeRequest();
		
    	horoscopeRequest.setOrder(order);
    	
    	return jerseyClient.resource(NEW_HOROSCOPE_URI).type(XML)
    			           .accept(XML).post(Horoscope.class, horoscopeRequest);
	}

	protected Order createNewOrder(final HOOOrderRequest request) 
	{
		final CreateOrderRequest orderRequest = new CreateOrderRequest();
		
		orderRequest.setCustomerName(request.getCustomerName());
    	orderRequest.setBirthdayOfTargetPerson(request.getBirthdayOfTargetPerson());
    	orderRequest.setHoroscopeType(request.getHoroscopeType());

    	// delegate to order service
    	return jerseyClient.resource(NEW_ORDER_URI).type(XML)
   	                       .accept(XML).put(Order.class, orderRequest);
	}


	private String createOverview(List<Order> orders) 
	{
		final StringBuilder sb = new StringBuilder("Date Time of Order       Horoscope Type      paid/fetched     Customer            Price      Order Number");
		sb.append(System.getProperty("line.separator"));
    	sb.append("-----------------------------------------------------------------------------------------------------------");
        
        for (Order order : orders) 
        {
			sb.append(System.getProperty("line.separator"));
        	sb.append(DEFAULT_DATE_FORMATTER.format(XMLGregorianCalendarUtil.toDate(order.getCreationDateTime())));
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
}