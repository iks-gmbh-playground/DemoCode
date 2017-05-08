package com.iksgmbh.demo.hoo.order;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.iksgmbh.demo.hoo.api.utils.HOOServiceException;
import com.iksgmbh.demo.hoo.api.utils.HOOServiceException.HOOErrorType;
import com.iksgmbh.demo.hoo.api.utils.HOOValidationUtil;
import com.iksgmbh.demo.hoo.api.utils.JaxbToStringUtil;
import com.iksgmbh.demo.hoo.api.utils.XMLGregorianCalendarUtil;
import com.iksgmbh.demo.hoo.horoscope.api.CreateHoroscopeRequest;
import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.invoice.api.Invoice;
import com.iksgmbh.demo.hoo.order.api.HOOOrderRequest;
import com.iksgmbh.demo.hoo.order.api.HOOOrderResponse;
import com.iksgmbh.demo.hoo.order.api.HOOPaymentRequest;
import com.iksgmbh.demo.hoo.order.api.HoroscopeType;
import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.demo.hoo.order.api.OrderList;
import com.iksgmbh.demo.hoo.order.dao.OrderDAO;
import com.iksgmbh.demo.hoo.order.utils.DateStringUtil;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
* Factory and repository for orders and main class of the HOO-order microservice. 
* This file contains the module contract of the order module to its environment. 
* Exported functionality available from outside see annotated service methods.
* Imported functionality: see constant URI values.
* 
* @author Reik Oberrath
*/
@Path("/")
public class OrderStore
{
    private static final Logger LOGGER = Logger.getLogger("OrderServiceImpl");
	private static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	private static final String XML = MediaType.APPLICATION_XML;
	private static final String HOST_PORT = "localhost:8080";
    
	// Dependencies (imported module functionality) of this microservice/module:
    private static final String NEW_HOROSCOPE_URI    = "http://" + HOST_PORT + "/HOOServiceHoroscope/newHoroscope";

    private OrderDAO orderDAO = new OrderDAO(SqlPojoMemoDB.getConnection());
    private Client jerseyClient = Client.create( new DefaultClientConfig() );
    
    static {
		HOOValidationUtil.setXsdFileLocation("/HOO-Order.xsd",      // schema location for tomcat runtime 
				                             "src/main/resources/HOO-Order.xsd");  // location for unit test runtime
	}    
    
    
	// #####################################################################
	//                          Service Methods
    //                 (Exported module functionality)
	// #####################################################################

	/**
	 * Customer method to create a new horoscope request.
	 * Triggers an horocsope and invoice creation.
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceOrder/sendOrder
	 * 
	 * @param request
	 * @return response
	 */
    @POST
    @Path("/sendOrder")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
	public HOOOrderResponse sendOrder(HOOOrderRequest request)  
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
    
    
    
	/**
	 * Administration method to view all orders previously created.
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceOrder/allOrders
	 * 
	 * @return order overview
	 */
    @GET
    @Path("/order/all")
    @Produces(MediaType.TEXT_PLAIN)
	public String findAllOrders()  
	{
		final List<Order> orders = findAll().getOrders();
		return createOverview(orders);
	}    
    
    @GET
    @Path("/order/{orderNumber}")
    @Produces(MediaType.APPLICATION_XML)
    /**
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceHoroscope/order/{orderNumber}
     */
    public Order findOrderByOrderNumber(@PathParam("orderNumber") final long orderNumber) throws HOOServiceException
    {
    	LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method findOrderByOrderNumber() called with orderNumber=" + orderNumber);

        try {
			final Order order = orderDAO.find(orderNumber);
	        LOGGER.info("Order read from DB: " + JaxbToStringUtil.toString(order));
	        return order;
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
        finally  {
        	LOGGER.info("---------------------------------------------------------------------------");
        }
    }
    
    
    @PUT
    @Path("/order/{orderNumber}/status/horoscopeFetched")
    /**
     * Administration method to set a payment to status PAID.
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceOrder/order/{orderNumber}/status/horoscopeFetched
     */
	public void setHoroscopeFetched(@PathParam("orderNumber") final long orderNumber)  
	{
    	LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method setHoroscopeFetched() called.");
        
		Order order;
		try {
			order = orderDAO.find(orderNumber);
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
		
		order.setHoroscopeFetched(true);
		
		try {
			orderDAO.updateIsHoroscopeFetchedStatus(order);
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
		
	    LOGGER.info("HoroscopeFetched status of order " + orderNumber + " set to true.");
	    LOGGER.info("---------------------------------------------------------------------------");
	}    

    
    @PUT
    @Path("/order/status/paid")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_XML)
    /**
     * Administration method to set a payment to status PAID.
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceOrder/order/status/paid
     */
	public void setPaidStatus(HOOPaymentRequest request)  
	{
	  LOGGER.info("---------------------------------------------------------------------------");
      LOGGER.info("Service method setPaidStatus() called.");
      
		final long orderNumber = request.getOrderNumber();
		
		Order order;
		try {
			order = orderDAO.find(orderNumber);
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
		
		order.setPaid(request.isPaid());
		
		try {
			orderDAO.updatePaymentStatus(order);
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
		
	    LOGGER.info("Paid status of order " + orderNumber + " set to " + request.isPaid() + ".");
	    LOGGER.info("---------------------------------------------------------------------------");	
	}    

    @PUT
    @Path("/order/invoice")
    @Consumes(MediaType.APPLICATION_XML)
    /**
     * Method to store invoice data with the corresponding order.
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceOrder/order/invoice
     */
	public void saveInvoiceData(final Invoice invoice)  
	{
    	LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method saveInvoiceData() called for order " + invoice.getOrderNumber() + ".");
        
		Order order;
		try {
			order = orderDAO.find(invoice.getOrderNumber());
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
		
		order.setPrice(invoice.getPrice());
		order.setPaid(invoice.isPrePaid());
		
		try {
			orderDAO.updateInvoiceData(order);
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
		
	    LOGGER.info("InvoiceData of order " + invoice.getOrderNumber() + " saved.");
	    LOGGER.info("---------------------------------------------------------------------------");
	}    
    
    
	// #####################################################################
	//                          Internal Methods
	// #####################################################################    
    

    private HOOOrderResponse createOrderResponse(final HOOOrderRequest request) throws HOOServiceException
    {
    	Order order = createNewOrder(request);
    	saveOrder(order);           // creates order in db with initial data
    	createNewHoroscope(order);  // this will update the previously saved order by invoice data 
		order = findOrderByOrderNumber(order.getOrderNumber());
		
    	final HOOOrderResponse toReturn = new HOOOrderResponse();
    	toReturn.setOrderNumber(order.getOrderNumber());
    	
    	if ( order.isPaid() )  {
    		toReturn.setStatusInfo("Order " + order.getOrderNumber() + " is paid. The horoscope is available.");
        	toReturn.setBill("");
    	} else {
    		String bill = createBill(order.getPrice());
        	toReturn.setBill(bill);
    		toReturn.setStatusInfo("Horoscope for order " + order.getOrderNumber() + " is not yet available. Please pay your bill.");
    	}
    	
        return toReturn;
    }

    /**
     * TODO: Add bill to order table in the db and save bill in order data with method saveInvoiceData.
     *       Reason: bill creation belongs to invoice and is already implemented there
     * @param order
     * @return
     */
	private String createBill(BigDecimal price) {
		return "Please pay " + price.toPlainString() + " Euro.";
	}
 
    public OrderList findAll() throws HOOServiceException
    {
    	LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method findAll() called.");
        OrderList toReturn = new OrderList();

        try {
        	toReturn.getOrders().addAll( orderDAO.findAll() );
		} catch (SQLException e) {
			throw new HOOServiceException("Error finding orders.", HOOErrorType.UNEXPECTED, "Order");
		}
        LOGGER.info("List<Order> created with " + toReturn.getOrders().size() + " entries.");
        LOGGER.info("---------------------------------------------------------------------------");

        return toReturn;
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

	protected Order createNewOrder(final HOOOrderRequest request) 
	{
		final Order toReturn = orderDAO.createNewOrder();
		
		toReturn.setCustomerName(request.getCustomerName());
		
		toReturn.setAgeOfTargetPerson( DateStringUtil.calculateAgeFromYearOfDate(request.getBirthdayOfTargetPerson()) );
		
		try {
			toReturn.setHoroscopeType( HoroscopeType.valueOf(request.getHoroscopeType()) );
		} catch (IllegalArgumentException e) {
			toReturn.setHoroscopeType( HoroscopeType.MISC );
		}

    	return toReturn;
	}
    
	protected void createNewHoroscope(Order order) 
	{
		final CreateHoroscopeRequest horoscopeRequest = new CreateHoroscopeRequest();
		
    	horoscopeRequest.setOrder(order);
    	
    	jerseyClient.resource(NEW_HOROSCOPE_URI).type(XML).post(Horoscope.class, horoscopeRequest);
	}

	public void saveOrder(final Order order) 
	{
		try {
			orderDAO.save(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}	
}