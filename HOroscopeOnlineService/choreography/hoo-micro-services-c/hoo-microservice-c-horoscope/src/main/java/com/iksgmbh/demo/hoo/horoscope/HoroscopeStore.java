package com.iksgmbh.demo.hoo.horoscope;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.iksgmbh.demo.hoo.api.utils.HOOServiceException;
import com.iksgmbh.demo.hoo.api.utils.HOOValidationUtil;
import com.iksgmbh.demo.hoo.api.utils.JaxbToStringUtil;
import com.iksgmbh.demo.hoo.horoscope.api.CreateHoroscopeRequest;
import com.iksgmbh.demo.hoo.horoscope.api.HOOHoroscopeRequest;
import com.iksgmbh.demo.hoo.horoscope.api.HOOHoroscopeResponse;
import com.iksgmbh.demo.hoo.horoscope.api.Horoscope;
import com.iksgmbh.demo.hoo.horoscope.dao.HoroscopeDAO;
import com.iksgmbh.demo.hoo.invoice.api.CreateInvoiceRequest;
import com.iksgmbh.demo.hoo.order.api.HoroscopeType;
import com.iksgmbh.demo.hoo.order.api.Order;
import com.iksgmbh.sql.pojomemodb.SqlPojoMemoDB;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.DefaultClientConfig;



/**
* Factory and repository for invoices and main class of the HOO-horoscope microservice. 
* This file contains the module contract of the horoscope module to its environment. 
* Exported functionality available from outside see annotated service methods.
* Imported functionality: see constant URI values.
* 
* @author Reik Oberrath
*/
@Path("/")
public class HoroscopeStore
{
    private static final Logger LOGGER = Logger.getLogger("HoroscopeServiceImpl");
	private static final String XML = MediaType.APPLICATION_XML;
	private static final String HOST_PORT = "localhost:8080";
	
	private static final BigDecimal DISCOUNT_FOR_KIDS = new BigDecimal(0.5);
	private static final BigDecimal SURCHARGE_FOR_OLDIES = new BigDecimal(0.1);
	private static final int AGE_LIMIT_KIDS = 10;
	private static final int AGE_LIMIT_OLDIES = 60;
	
	// Dependencies (imported module functionality) of this microservice/module:
    private static final String NEW_INVOICE_URI    = "http://" + HOST_PORT + "/HOOServiceInvoice/newInvoice";	
	private static final String SET_FETCHED_URI    = "http://" + HOST_PORT + "/HOOServiceOrder/order/{orderNumber}/status/horoscopeFetched";
	private static final String FIND_ORDER_URI     = "http://" + HOST_PORT + "/HOOServiceOrder/order/{orderNumber}";

    private HoroscopeDAO horoscopeDAO = new HoroscopeDAO(SqlPojoMemoDB.getConnection());
    private Client jerseyClient = Client.create( new DefaultClientConfig() );

    static {
		HOOValidationUtil.setXsdFileLocation("/HOO-Horoscope.xsd",      // schema location for tomcat runtime 
				                             "src/main/resources/HOO-Horoscope.xsd");  // location for unit test runtime
	}   	

	// #####################################################################
	//                          Service Methods
    //                 (Exported module functionality)
	// #####################################################################

    
    @POST
    @Path("/newHoroscope")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    /**
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceHoroscope/newHoroscope
     */
    public Horoscope createHoroscope(final CreateHoroscopeRequest request) throws HOOServiceException
    {
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method createHoroscope() called.");
        HOOValidationUtil.validateContractObject("CreateHoroscopeRequest", request);
        LOGGER.info("CreateHoroscopeRequest received: " + JaxbToStringUtil.toString(request));

        final Horoscope horoscope = createHoroscope(request.getOrder());
        
        try {
			horoscopeDAO.save(horoscope);
		} catch (SQLException e) {
			throw new HOOServiceException(e);
		}
        
        processInvoice(request.getOrder(), horoscope);
        
        LOGGER.info("Horoscope created: " + JaxbToStringUtil.toString(horoscope));
        LOGGER.info("---------------------------------------------------------------------------");
        
        return horoscope;
    }

    @PUT
    @Path("/horoscope")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    /**
	 * Customer method to request a horoscope to an order previously sent.
     * This service method will is available under http://" + HOST_PORT + "/HOOServiceHoroscope/horoscope
     */
    public HOOHoroscopeResponse fetchHoroscope(final HOOHoroscopeRequest request) throws HOOServiceException
    {
    	LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------");
        LOGGER.info("Service method fetchHoroscope() called.");
        HOOValidationUtil.validateContractObject("HOOHoroscopeRequest", request);
        LOGGER.info("Valid HOOHoroscopeRequest received: " + JaxbToStringUtil.toString(request));

    	final Order order = findOrder( request.getOrderNumber() );
        final HOOHoroscopeResponse response = createHoroscopeResponse(order);
        
        HOOValidationUtil.validateContractObject("HOOHoroscopeResponse", response);
        LOGGER.info("Valid HOOHoroscopeResponse created: " + JaxbToStringUtil.toString(response));
        LOGGER.info("---------------------------------------------------------------------------");

        return response;
    }
    
    
	// #####################################################################
	//                          Internal Methods
	// #####################################################################

    
    /**
     * Applies some simple business logic based on the order data to get a horoscope.
     * @param horoscopeType 
     * @param ageOfTargetPerson 
     * @param order
     * @return Horoscope
     */
	private Horoscope createHoroscope(final Order order) 
	{
        final Horoscope horoscope = new Horoscope();
        horoscope.setOrderNumber( order.getOrderNumber() );
        String horoscopeText;
		BigDecimal invoiceFactor = BigDecimal.ONE;
		
		if (order.getHoroscopeType() == HoroscopeType.JOB) {
			horoscopeText = "Divide and rule!";
		} else if (order.getHoroscopeType() == HoroscopeType.LOVE) {
			horoscopeText = "Don''t worry, be happy.";
		} else {
			horoscopeText = "Take it easy.";
		}
		
		horoscope.setHoroscopeText(horoscopeText);
		
		if (order.getAgeOfTargetPerson() < AGE_LIMIT_KIDS ) {
			invoiceFactor = invoiceFactor.subtract(DISCOUNT_FOR_KIDS);
		} else if (order.getAgeOfTargetPerson() > AGE_LIMIT_OLDIES ) {
			invoiceFactor = invoiceFactor.add(SURCHARGE_FOR_OLDIES);
		}	
		
		horoscope.setInvoiceFactor(invoiceFactor);
		horoscope.setHoroscopeType(order.getHoroscopeType());
		return horoscope;
	}
    
	private void processInvoice(Order order, Horoscope horoscope) 
	{
		final CreateInvoiceRequest invoiceRequest = new CreateInvoiceRequest();
		
    	invoiceRequest.setOrder(order);
    	invoiceRequest.setHoroscope(horoscope);
    	
    	jerseyClient.resource(NEW_INVOICE_URI).type(XML).put(invoiceRequest);
	}
	
	private Order findOrder(long orderNumber) 
	{
    	return jerseyClient.resource(FIND_ORDER_URI.replace("{orderNumber}", "" + orderNumber))
    			           .accept(XML).get(Order.class);
	}
	
	
	public Horoscope findHoroscope(long orderNumber) 
	{
		try {
			return horoscopeDAO.find(orderNumber);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

    private HOOHoroscopeResponse createHoroscopeResponse(final Order order) throws HOOServiceException
    {
    	final HOOHoroscopeResponse toReturn = new HOOHoroscopeResponse();
    	
		if ( order.isPaid() )  
		{
			final Horoscope horoscope = findHoroscope( order.getOrderNumber() );
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
}